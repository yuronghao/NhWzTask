/*
 * Title: Tools.java
 * Company: EMI
 * Author: xiaochen zhu
 * Date: May 18, 2014 8:27:36 PM
 * Version: 4.0
 */
package com.emi.webservice;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.emi.sys.init.Config;

import sun.net.www.protocol.http.HttpURLConnection;


/**
 * 常用工具类.
 * 
 * @version 1.0
 * @since 2014-5-14
 */
public class YonYouUtil
{	

	/**
	 * 获得用友接口地址
	 *@return
	 *2014年9月9日
	 *HttpURLConnection
	 *SYH
	 */
	public static HttpURLConnection getU8(){
		HttpURLConnection con=null;
		try{
	        URL url = new URL("http://"+Config.eaiAddress+"/U8EAI/import.asp");
	        con = (HttpURLConnection)url.openConnection();
	        con.setConnectTimeout(3000000);
	        con.setReadTimeout(3000000);
	        con.setDoInput(true);
	        con.setDoOutput(true);
	        con.setAllowUserInteraction(false);
	        con.setUseCaches(false);
	        con.setRequestMethod("POST");
	        con.setRequestProperty("Content-type","application/x-www-form-urlencoded");
	    	
		}catch(Exception e){
			e.printStackTrace();
		}
		return con;
	}
	
	
	
	
	/**
	 * 获得插库之后responsesXml字符串
	 * @param in
	 * @return
	 *String
	 *syh
	 *2014年11月05日
	 */
	public static String getResponseXml(InputStream in){
		
		StringBuilder sb=null;
		try{

			 BufferedReader br = new BufferedReader(new InputStreamReader(in,"utf-8"));
			 sb = new StringBuilder();
			 String s = null;
			 while ((s = br.readLine()) != null) {
				sb.append(s);
			 }	
			
		}catch(Exception e){
			e.printStackTrace();
		}

		return sb.toString();
	}
	
	/**
	 * 解析是否成功插入
	 * @param responseXml
	 * @return
	 *String
	 *Administrator
	 *2014年11月05日
	 */
	public static String getSucceedInfor(String responseXml){
		String infor="";
		try{
			 //开始解析xml(判断是否成功插入)
			 Document doc = null;
			// 获取根节点
			 doc = DocumentHelper.parseText(responseXml); 
			 Element rootElt = doc.getRootElement(); 
			 //获取NewDataSet节点
			 Iterator iterNewDataSet = rootElt.elementIterator("item");
			 
			 Element headEle = (Element) iterNewDataSet.next(); 
			 
			 infor=headEle.attributeValue("succeed");
		}catch(Exception e){
			e.printStackTrace();
		}

		return infor;
	}
	
	
	/**
	 *（核心方法） 开始向用友数据库写入，并返回写入信息(是否成功，succeed=0表示成功)
	 * @param requestXml
	 * @return
	 *String
	 *syh
	 *2014年11月05日
	 */
	public static String eaiWriteInfor(String requestXml){
		String succeedInfor="";
		try{
			HttpURLConnection con=YonYouUtil.getU8();
			OutputStream out = con.getOutputStream();//发送Request消息
	   	 	DataOutputStream dos = new DataOutputStream(out);
			dos.write(requestXml.getBytes("UTF-8"));
			 //获取Response消息
			 InputStream in = con.getInputStream();
			 String responseXml =YonYouUtil.getResponseXml(in);   
			 System.out.println(responseXml);
			
			 //开始解析xml(判断是否成功插入)
			 succeedInfor=YonYouUtil.getSucceedInfor(responseXml);
		}catch(Exception e){
			e.printStackTrace();
		}
		return succeedInfor;
	}
	
	

	
	
}