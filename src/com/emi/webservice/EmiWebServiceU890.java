package com.emi.webservice;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.emi.common.util.CommonUtil;
import com.emi.sys.init.Config;
import com.emi.webservice.u890.U890xml;
import com.emi.webservice.u890.bean.StoreInBodyU8;
import com.emi.webservice.u890.bean.StoreInHeadU8;
import com.emi.webservice.u890.bean.StoreOutBodyU8;
import com.emi.webservice.u890.bean.StoreOutHeadU8;



@WebService(serviceName="emiWebService",targetNamespace="http://webservice.emi.com/")
@SOAPBinding(style = Style.RPC)
public class EmiWebServiceU890 {
	

	/**
	 * @category 添加入库单
	 *2016 2016年3月15日上午9:31:41
	 *String
	 *宋银海
	 */
	@WebMethod(operationName = "addPoWareHouse")
	public String addPoWareHouse(@WebParam(name = "head")String head,@WebParam(name = "body")String body) {
		
		String chilid="";
		JSONArray jsonArray=JSONArray.fromObject(body);
		for(Object obj:jsonArray){
			
			JSONObject prowctJson = (JSONObject) obj;
			
			StoreInBodyU8 storeInBody=new StoreInBodyU8();
			storeInBody.setInventorycode(CommonUtil.isNullObject(prowctJson.get("inventorycode"))?"":prowctJson.get("inventorycode").toString());
			storeInBody.setShouldquantity(CommonUtil.isNullObject(prowctJson.get("shouldquantity"))?"":prowctJson.get("shouldquantity").toString());
			storeInBody.setQuantity(CommonUtil.isNullObject(prowctJson.get("quantity"))?"":prowctJson.get("quantity").toString());
			storeInBody.setCmassunitname(CommonUtil.isNullObject(prowctJson.get("cmassunitname"))?"":prowctJson.get("cmassunitname").toString());
		
			storeInBody.setCsTComUnitCode(CommonUtil.isNullObject(prowctJson.get("csTComUnitCode"))?"":prowctJson.get("csTComUnitCode").toString());//库存辅计量单位编码
			storeInBody.setCsTComUnitName(CommonUtil.isNullObject(prowctJson.get("csTComUnitName"))?"":prowctJson.get("csTComUnitName").toString());//库存辅计量单位名称
			storeInBody.setIrate(CommonUtil.isNullObject(prowctJson.get("irate"))?"":prowctJson.get("irate").toString());//换算率
			if(!CommonUtil.isNullObject(prowctJson.get("csTComUnitCode"))){
				storeInBody.setNumber(CommonUtil.isNullObject(prowctJson.get("number"))?"":prowctJson.get("number").toString());
			}else{
				storeInBody.setNumber("");
			}

			storeInBody.setDefine30(CommonUtil.isNullObject(prowctJson.get("define30"))?"":prowctJson.get("define30").toString());
			chilid=chilid+U890xml.getStoreInBodyXml(storeInBody);
			
		}
		
		
		///////////////////////////////////////////////获得getStoreInHeadXml开始
		String requestXml="";
		JSONObject jobj=JSONObject.fromObject(head);
		StoreInHeadU8 storeInHead=new StoreInHeadU8();
		storeInHead.setVouchtype("10");
		storeInHead.setBusinesstype("成品入库");
		storeInHead.setWarehousecode(CommonUtil.isNullObject(jobj.get("warehousecode"))?"":jobj.get("warehousecode").toString());
		storeInHead.setDate(CommonUtil.isNullObject(jobj.get("date"))?"":jobj.get("date").toString());
		storeInHead.setReceivecode(CommonUtil.isNullObject(jobj.get("receivecode"))?"":jobj.get("receivecode").toString());//入库类别
		storeInHead.setDepartmentcode(CommonUtil.isNullObject(jobj.get("departmentcode"))?"":jobj.get("departmentcode").toString());
		storeInHead.setTemplatenumber("63");
		storeInHead.setMaker(CommonUtil.isNullObject(jobj.get("maker"))?"":jobj.get("maker").toString());
		storeInHead.setDefine10(CommonUtil.isNullObject(jobj.get("define10"))?"":jobj.get("define10").toString());
		requestXml=U890xml.getStoreInHeadXml(storeInHead);
		//拼接字符串
		requestXml=U890xml.connectHeadBodyIn(requestXml, chilid);
		System.out.println(requestXml);
        ///////////////////////////////////////////////获得getStoreInHeadXml结束
		
		String succeedInfor=YonYouUtil.eaiWriteInfor(requestXml);
		 
		if(!succeedInfor.equalsIgnoreCase("0")){
			 //故意报错，事务回滚
			 //orderService.updateRollBack();
		}
		
		return orderAuditXml(true, "数据保存成功");
	}
	
	
	/**
	 * @category 添加成品入库
	 *2016 2016年3月15日上午9:57:46
	 *String
	 *宋银海
	 */
	@WebMethod(operationName = "addProWareHouse")
	public String addProWareHouse(@WebParam(name = "head")String head,@WebParam(name = "body")String body) {
		return null;
	}
	
	
	/**
	 * @category 添加其它入库
	 *2016 2016年3月15日上午9:59:45
	 *String
	 *宋银海
	 */
	@WebMethod(operationName = "addOtherWareHouse")
	public String addOtherWareHouse(@WebParam(name = "head")String head,@WebParam(name = "body")String body) {
		return null;
	}
	
	
	
	
	
	
	
	
	/**
	 * @category 添加材料出库单
	 *2016 2016年3月15日上午9:32:20
	 *String
	 *宋银海
	 */
	@WebMethod(operationName = "addMaterial")
	public String addMaterial(@WebParam(name = "head")String head,@WebParam(name = "body")String body) {
		
		String chilid="";
		JSONArray jsonArray=JSONArray.fromObject(body);
		for(Object obj:jsonArray){
			
			JSONObject prowctJson = (JSONObject) obj;
		
			StoreOutBodyU8 storeOutBody=new StoreOutBodyU8();
			storeOutBody.setInventorycode(CommonUtil.isNullObject(prowctJson.get("inventorycode"))?"":prowctJson.get("inventorycode").toString());
			storeOutBody.setQuantity(CommonUtil.isNullObject(prowctJson.get("quantity"))?"":prowctJson.get("quantity").toString());
			storeOutBody.setCmassunitname(CommonUtil.isNullObject(prowctJson.get("cmassunitname"))?"":prowctJson.get("cmassunitname").toString());
			storeOutBody.setShouldquantity(CommonUtil.isNullObject(prowctJson.get("shouldquantity"))?"":prowctJson.get("shouldquantity").toString());
																				   
			storeOutBody.setCsTComUnitCode(CommonUtil.isNullObject(prowctJson.get("csTComUnitCode"))?"":prowctJson.get("csTComUnitCode").toString());//库存辅计量单位编码
			storeOutBody.setCsTComUnitName(CommonUtil.isNullObject(prowctJson.get("csTComUnitName"))?"":prowctJson.get("csTComUnitName").toString());//库存辅计量单位名称
			storeOutBody.setIrate(CommonUtil.isNullObject(prowctJson.get("irate"))?"":prowctJson.get("irate").toString());//换算率
			
			if(!CommonUtil.isNullObject(prowctJson.get("csTComUnitCode"))){
				storeOutBody.setShouldnumber(CommonUtil.isNullObject(prowctJson.get("shouldnumber"))?"":prowctJson.get("shouldnumber").toString());
				storeOutBody.setNumber(CommonUtil.isNullObject(prowctJson.get("number"))?"":prowctJson.get("number").toString());
			}else{
				storeOutBody.setShouldnumber("");
				storeOutBody.setNumber("");
			}
			
			storeOutBody.setDefine30(CommonUtil.isNullObject(prowctJson.get("define30"))?"":prowctJson.get("define30").toString());
			//storeOutBody.setIdlsid(CommonUtil.isNullObject(prowctJson.get("idFromSourseBill"))?"":prowctJson.get("idFromSourseBill").toString());
			
			chilid=chilid+U890xml.getStoreOutBodyXml(storeOutBody);
			
		}
		
		String requestXml="";
		JSONObject jobj=JSONObject.fromObject(head);
		StoreOutHeadU8 storeOutHead=new StoreOutHeadU8();
		storeOutHead.setVouchtype("32");
		storeOutHead.setBusinesstype("普通销售");
		storeOutHead.setSource("发货单");
		storeOutHead.setWarehousecode(CommonUtil.isNullObject(jobj.get("warehousecode"))?"":jobj.get("warehousecode").toString());
		storeOutHead.setDate(CommonUtil.isNullObject(jobj.get("date"))?"":jobj.get("date").toString());
		storeOutHead.setReceivecode("21");
		storeOutHead.setDepartmentcode(CommonUtil.isNullObject(jobj.get("departmentcode"))?"":jobj.get("departmentcode").toString());
		storeOutHead.setCustomercode(CommonUtil.isNullObject(jobj.get("customercode"))?"":jobj.get("customercode").toString());
		storeOutHead.setConsignmentcode(CommonUtil.isNullObject(jobj.get("consignmentcode"))?"":jobj.get("consignmentcode").toString());
		storeOutHead.setTemplatenumber("87");
		storeOutHead.setMaker(CommonUtil.isNullObject(jobj.get("maker"))?"":jobj.get("maker").toString());
		storeOutHead.setDefine10(CommonUtil.isNullObject(jobj.get("define10"))?"":jobj.get("define10").toString());
		storeOutHead.setCshipAddress(CommonUtil.isNullObject(jobj.get("cshipAddress"))?"":jobj.get("cshipAddress").toString());
		storeOutHead.setCmemo(CommonUtil.isNullObject(jobj.get("memo"))?"":jobj.get("memo").toString());
		storeOutHead.setCpersonCode(CommonUtil.isNullObject(jobj.get("cpersonCode"))?"":jobj.get("cpersonCode").toString());
		
		requestXml=U890xml.getStoreOutHeadXml(storeOutHead);
		//拼接请求字符串
		requestXml=U890xml.connectHeadBodyOut(requestXml, chilid);
		
		
		String succeedInfor=YonYouUtil.eaiWriteInfor(requestXml);
		 
		if(!succeedInfor.equalsIgnoreCase("0")){
			 //故意报错，事务回滚
			 //orderService.updateRollBack();
		}
		
		return orderAuditXml(true, "数据保存成功");
	}
	
	
	/**
	 * @category 添加销售出库
	 *2016 2016年3月15日上午10:02:07
	 *String
	 *宋银海
	 */
	@WebMethod(operationName = "addSale")
	public String addSale(@WebParam(name = "xml") String xml) {
		
		System.out.println(xml);
		return orderAuditXml(true, "数据保存成功");
	}
	
	
	/**
	 * @category 添加其他出库
	 *2016 2016年3月15日上午10:03:43
	 *String
	 *宋银海
	 */
	@WebMethod(operationName = "addOtherOut")
	public String addOtherOut(@WebParam(name = "xml") String xml) {
		
		System.out.println(xml);
		return orderAuditXml(true, "数据保存成功");
	}
	
	
	
	
	
	
	
	
	private String orderAuditXml(boolean suc,String msg){
		String ret = "";
		try {
			Document document = DocumentHelper.createDocument();	
			Element root = document.addElement("parameters");  
			// 添加节点
			Element flag = root.addElement("success"); 
//			flag.addAttribute("key", "success"); 
//			flag.addCDATA(suc?"True":"False"); 
			flag.addText(suc?"True":"False");
			
			Element message = root.addElement("message"); 
//			message.addAttribute("key", "message"); 
//			message.addCDATA(msg);
			message.addText(msg);
						
			StringWriter stringWriter = new StringWriter();  
			//设置文件编码  
			OutputFormat xmlFormat = new OutputFormat();  
			xmlFormat.setEncoding("UTF-8"); 
			
			XMLWriter xmlWriter = new XMLWriter(stringWriter,xmlFormat);  
			xmlWriter.write(document);  
			xmlWriter.close(); 
			
			ret = stringWriter.toString();
			System.out.println(stringWriter.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return ret;
	}
	

	
	public static void main(String[] args) {
		try {
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
	        org.apache.cxf.endpoint.Client client = dcf.createClient("http://localhost:8082/erp/services/emiWebService?wsdl");
	        Object[] objects=client.invoke("addGoodsSort", "123456789"); 
//	        System.out.println(objects[0].toString());
//			orderAuditXml(false,"日期格式化错误");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
