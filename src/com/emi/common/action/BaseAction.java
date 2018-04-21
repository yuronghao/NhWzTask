package com.emi.common.action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.SessionAware;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CSVUtils;
import com.emi.common.util.CommonUtil;
import com.emi.sys.core.format.EmiJsonObj;
import com.emi.sys.file.EmiFileOption;
import com.emi.sys.init.Config;
import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("rawtypes")
public class BaseAction extends ActionSupport implements SessionAware{
	private static final long serialVersionUID = -5300158034452776588L;
	private Map<String,Object> session;
	private BaseDao baseDao;
	public BaseDao getBaseDao() {
		return baseDao;
	}

	public void setBaseDao(BaseDao baseDao) {
		this.baseDao = baseDao;
	}
	public void setSession(Map<String, Object> session) {
		this.session=session;
	}

	public Map<String, Object> getSession() {
		return session;
	}

	public HttpServletRequest getRequest(){
		HttpServletRequest request = ServletActionContext.getRequest();
		return request;
	}
	
	public HttpServletResponse getResponse(){
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		return response;
	}
	
	public JSONObject getJsonObject(){
		return getJsonObject(true);
	}
	
	public JSONArray getJsonArray(){
		return getJsonArray(true);
	}
	
	public JSONObject getJsonObject(boolean printJson){
		StringBuffer sb = new StringBuffer();
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getRequest().getInputStream(),"utf-8"));
			String temp;
			while((temp=br.readLine())!=null){
				sb.append(temp);
			}
			br.close();
			result = sb.toString();
			if(printJson){
				System.out.println(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONObject jsonObject = JSONObject.fromObject(result);
		return jsonObject;
	}
	
	public JSONArray getJsonArray(boolean printJson){
		StringBuffer sb = new StringBuffer();
		String result = "";
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(getRequest().getInputStream(),"utf-8"));
			String temp;
			while((temp=br.readLine())!=null){
				sb.append(temp);
			}
			br.close();
			result = sb.toString();
			if(printJson){
				System.out.println(result);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		JSONArray jsonA = JSONArray.fromObject(result);
		return jsonA;
	}
	
	/*
	 * 获取request参数
	 */
	public String getParameter(String key){
		String param = getRequest().getParameter(key);
		if (param != null) {
			param = param.trim();
			param = param.replaceAll("<", "&lt;");
			param = param.replaceAll(">", "&gt;");
		}
		return param;
	}
	
	/*
	 * 设置request参数
	 */
	public void setRequstAttribute(String key,Object value){
		getRequest().setAttribute(key, value);
	}
	
	public int getPageIndex(){
		int pageIndex = 1;
		String page = getRequest().getParameter("pno");
		if (!CommonUtil.isNullString(page)) {
			pageIndex = Integer.parseInt(page);
		}
		return pageIndex < 0 ? 1 : pageIndex;
	}
	
	public int getPageSize() {
		int size = Config.PAGESIZE_WEB;
		try {
			String s = getRequest().getParameter("pageSize");
			if (!CommonUtil.isNullString(s)) {
				//如果有值，存入cookie中
				size = Integer.parseInt(s);
			}else{
				
			}
			/*setRequstAttribute("pageSize", size);*/
		} catch (Exception e) {
			e.printStackTrace();
			/*setRequstAttribute("pageSize", size);*/
			return size;
		}
		return getPageSize(size);
	}
	public int getPageSize(int size) {
		return size < 1 ? 1 : size;
	}
	
	public void writeError(){
		try {
			getResponse().getWriter().write("{\"success\":\"0\"}");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void writeSuccess(){
		try {
			getResponse().getWriter().write("{\"success\":\"1\"}");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void resWrite(String str){
		try {
			getResponse().getWriter().write(str);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public void outputFile(String fileName,String filePath,InputStream inStream,ServletOutputStream sos) throws IOException{
		sos = getResponse().getOutputStream();
		String filename = URLEncoder.encode(fileName, "UTF-8");
		getResponse().setCharacterEncoding("UTF-8");
		
//		if (online) { // 在线打开方式
//            URL u = new URL("file:///" + filePath);
//            getResponse().setContentType(u.openConnection().getContentType());
//            getResponse().setHeader("Content-Disposition", "inline; filename=" + fileName);
//            // 文件名应该编码成UTF-8
//        } else { // 纯下载方式
//        	getResponse().setContentType("application/x-msdownload");
        	getResponse().setContentType("application/octet-stream");
        	getResponse().setHeader("Content-Disposition","attachment;filename=" + filename);
//        }
		// 循环取出流中的数据
		byte[] b = new byte[1024 * 8];
		int len;

		while ((len = inStream.read(b)) > 0)
			sos.write(b, 0, len);
	}
	
	public void forbideMsg(){
		try {
			getResponse().getWriter().write("document.write('<script>alert(\"没有权限\");</script>')");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 分页上的导出数据 csv格式，逗号隔开
	 * column_cfg  格式：{"name:姓名","address:联系地址"}
	 * 2015年8月20日 下午5:14:06
	 * @param 数据
	 * @param 导出的文件名
	 * @return
	 */
	public String exportData(List<Object> dataList,String exportName){
		InputStream in = null;
		File file = null;
		exportName += ".csv";
		try {
			String column_str = getParameter("emi_export_column");
			JSONObject column_cfg_json = JSONObject.fromObject(column_str);
			
			List<String> stringList = new ArrayList<String>();
			List<String> col_code = new ArrayList<String>();//列的英文字段名
			String col_name = "";//列中文描述,用逗号隔开（即表头）
			
			Iterator it = column_cfg_json.keys();  
			while (it.hasNext()) {  
                String key = (String) it.next();  
                String value = column_cfg_json.getString(key);
                col_code.add(key);
                col_name += value+",";
            }  
			col_name = CommonUtil.cutLastString(col_name, ",");
			//将List<Object>转成List<String>
			//1、先加表头
			stringList.add(col_name);
			//2、数据
			JSONObject json = null;
			for(Object obj : dataList){
	//			if(obj instanceof )
				json = EmiJsonObj.fromObject(obj);
				String data = "";
				for(String code : col_code){
					data += CommonUtil.Obj2String("\""+json.get(code))+"\",";
				}
				data = CommonUtil.cutLastString(data, ",");
				stringList.add(data);
			}
			String filepath = EmiFileOption.getBasePath()+UUID.randomUUID().toString()+".csv";
			/*
			 * 导出到磁盘
			 */
			CSVUtils.exportCsv(new File(filepath), stringList);
			/*
			 * 下载
			 */
			file = new File(filepath);
            // 一次读多个字节
            byte[] tempbytes = new byte[1024*1024];
            int byteread = 0;
            in = new FileInputStream(filepath);
            // 读入多个字节到字节数组中，byteread为一次读入的字节数
            getResponse().addHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(exportName, "UTF-8").replace("+","%20")+"\"" );
//            getResponse().addHeader("Content-Length", "" + file.length());
            getResponse().setContentType("application/octet-stream");

            while ((byteread = in.read(tempbytes)) != -1) {
            	getResponse().getOutputStream().write(tempbytes, 0, byteread);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (in != null) {
            try {
                in.close();
                file.delete();
            } catch (IOException e1) {
            }
        }
		return null;
	}
	
	public boolean isEmiExport(){
		if("1".equals(getParameter("exportData"))){
			return true;
		}
		return false;
	}
	
	/**
	 * 获取下一个序列的值，序列的值也随之改变成该值 2015年6月29日 下午5:19:20
	 *
	 * @return
	 */
	public String nextSequenceVal(String seq_name) {
		return baseDao.nextSeqVal(seq_name);
	}
	
	
}
