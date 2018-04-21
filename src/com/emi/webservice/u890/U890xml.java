package com.emi.webservice.u890;

import com.emi.common.util.CommonUtil;
import com.emi.sys.init.Config;
import com.emi.webservice.u890.bean.StoreInBodyU8;
import com.emi.webservice.u890.bean.StoreInHeadU8;
import com.emi.webservice.u890.bean.StoreOutBodyU8;
import com.emi.webservice.u890.bean.StoreOutHeadU8;
import com.emi.webservice.u890.bean.TransvouchBodyU8;
import com.emi.webservice.u890.bean.TransvouchHeadU8;



public class U890xml {

	/**
	 *RdRecord  收发记录主表
	 * @param storeInHead
	 * @return
	 *String
	 *Administrator
	 *2014年10月14日
	 */
	public static String  getStoreInHeadXml(StoreInHeadU8 storeInHead){
		
		String requestXml="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
				"<ufinterface sender=\""+Config.EAICODE+"\" receiver=\"u8\" roottag=\"storein\" docid=\"88298976\" proc=\"add\" codeexchanged=\"N\" exportneedexch=\"N\" display=\"入库单\" family=\"库存管理\">"+
				    "<storein>"+
				        "<header>"+
				            "<id></id>"+
				            "<receiveflag>1</receiveflag>"+
				            "<vouchtype>"+storeInHead.getVouchtype()+"</vouchtype>"+
				            "<businesstype>"+storeInHead.getBusinesstype()+"</businesstype>"+
				            "<source></source>"+
				            "<businesscode />"+
				            "<warehousecode>"+storeInHead.getWarehousecode()+"</warehousecode>"+
				            "<date>"+storeInHead.getDate()+"</date>"+
				            "<code>0000000001</code>"+
				            "<receivecode>"+storeInHead.getReceivecode()+"</receivecode>"+ 
				            "<departmentcode>"+storeInHead.getDepartmentcode()+"</departmentcode>"+
				            "<personcode />"+
				            "<purchasetypecode />"+
				            "<saletypecode />"+
				            "<customercode />"+
				            "<vendorcode>"+storeInHead.getProviderCode()+"</vendorcode>"+
				            "<ordercode />"+
				            "<quantity />"+
				            "<arrivecode />"+
				            "<billcode />"+
				            "<consignmentcode />"+
				            "<arrivedate />"+
				            "<checkcode />"+
				            "<checkdate />"+
				            "<checkperson />"+
				            "<templatenumber>63</templatenumber>"+
				            "<serial />"+
				            "<handler />"+
				            "<memory />"+
				            "<maker>"+storeInHead.getMaker()+"</maker>"+
				            "<chandler />"+
				            "<define1 />"+
				            "<define2 />"+
				            "<define3 />"+
				            "<define4 />"+
				            "<define5 />"+
				            "<define6 />"+
				            "<define7 />"+
				            "<define8 />"+
				            "<define9 />"+
				            "<define10>"+storeInHead.getDefine10()+"</define10>"+
				            "<define11 />"+
				            "<define12 />"+
				            "<define13 />"+
				            "<define14 />"+
				            "<define15 />"+
				            "<define16 />"+
				            "<auditdate />"+
				            "<taxrate />"+
				            "<exchname />"+
				            "<exchrate>"+1+"</exchrate>"+
				            "<contact />"+
				            "<phone />"+
				            "<mobile />"+
				            "<address />"+
				            "<conphone />"+
				            "<conmobile />"+
				            "<deliverunit />"+
				            "<contactname />"+
				            "<officephone />"+
				            "<mobilephone />"+
				            "<psnophone />"+
				            "<psnmobilephone />"+
				            "<shipaddress />"+
				            "<addcode />"+
				        "</header>";
		
		return requestXml;
	}
	
	/**
	 * RdRecords  收发记录主表
	 * @param storeInBody
	 * @return
	 *String
	 *Administrator
	 *2014年10月14日
	 */
	public static String getStoreInBodyXml(StoreInBodyU8 storeInBody){
		//"<position>"+goodAllocation.get("cPosCode").toString()+"</position>"+  (心愿如果存在货位)
		String chilid="<entry>"+
                "<id></id>"+ 
                "<autoid></autoid>"+
                "<barcode />"+
                "<inventorycode>"+storeInBody.getInventorycode()+"</inventorycode>"+
                "<free1 />"+
                "<free2 />"+
                "<free3 />"+
                "<free4 />"+
                "<free5 />"+
                "<free6 />"+
                "<free7 />"+
                "<free8 />"+
                "<free9 />"+
                "<free10 />"+
                "<shouldquantity>"+storeInBody.getShouldquantity()+"</shouldquantity>"+
                "<shouldnumber />"+
                "<quantity>"+storeInBody.getQuantity()+"</quantity>"+
                "<cmassunitname>"+storeInBody.getCmassunitname()+"</cmassunitname>"+
                "<assitantunit>"+storeInBody.getCsTComUnitCode()+"</assitantunit>"+
                "<assitantunitname>"+storeInBody.getCsTComUnitName()+"</assitantunitname>"+
                "<irate>"+storeInBody.getIrate()+"</irate>"+
                "<number>"+storeInBody.getNumber()+"</number>"+
                "<price>"+(CommonUtil.isNullObject(storeInBody.getIcost())?"":storeInBody.getIcost()) +"</price>"+
                "<cost>"+(CommonUtil.isNullObject(storeInBody.getImoney())?"":storeInBody.getImoney())+"</cost>"+
                "<plancost />"+
                "<planprice />"+
                "<serial>"+(CommonUtil.isNullObject(storeInBody.getBatch())?"":storeInBody.getBatch())+"</serial>"+
                "<makedate />"+
                "<validdate />"+
                "<transitionid />"+
                "<subbillcode />"+
                "<subpurchaseid />"+
                "<position>"+(CommonUtil.isNullObject(storeInBody.getPosition())?"":storeInBody.getPosition())+"</position>"+
                "<itemclasscode />"+
                "<itemclassname />"+
                "<itemcode />"+
                "<itemname />"+
                "<define22 />"+
                "<define23 />"+
                "<define24 />"+
                "<define25 />"+
                "<define26 />"+
                "<define27 />"+
                "<define28 />"+
                "<define29 />"+
                "<define30>"+storeInBody.getDefine30()+"</define30>"+
                "<define31></define31>"+
                "<define32 />"+
                "<define33 />"+
                "<define34 />"+
                "<define35 />"+
                "<define36 />"+
                "<define37 />"+
                "<subconsignmentid />"+
                "<delegateconsignmentid />"+
                "<subproducingid></subproducingid>"+
                "<subcheckid />"+
                "<cRejectCode />"+
                "<iRejectIds />"+
                "<cCheckPersonCode />"+
                "<dCheckDate />"+
                "<cCheckCode />"+
                "<iMassDate />"+
                "<ioritaxcost></ioritaxcost>"+
                "<ioricost></ioricost>"+
                "<iorimoney></iorimoney>"+
                "<ioritaxprice></ioritaxprice>"+
                "<iorisum></iorisum>"+
                "<taxrate></taxrate>"+
                "<taxprice></taxprice>"+
                "<isum></isum>"+
                "<massunit />"+
            "</entry>";
		
		return chilid;
	}
	
	/**
	 * RdRecords  收发记录主表
	 * @param storeOutHead
	 * @return
	 *String
	 *Administrator
	 *2014年10月14日
	 */

	public static String getStoreOutHeadXml(StoreOutHeadU8 storeOutHead){
   	 String requestXml=
   	  "<storeout>"+
	         "<header>"+
	            "<id></id>"+
	            "<receiveflag>0</receiveflag>"+
	            "<vouchtype>"+storeOutHead.getVouchtype()+"</vouchtype>"+
	            "<businesstype>"+storeOutHead.getBusinesstype()+"</businesstype>"+
	            "<source>"+storeOutHead.getSource()+"</source>"+
	            "<businesscode></businesscode>"+
	            "<warehousecode>"+storeOutHead.getWarehousecode()+"</warehousecode>"+
	            "<date>"+storeOutHead.getDate()+"</date>"+
	            "<code>0000000001</code>"+
	            "<receivecode>"+storeOutHead.getReceivecode()+"</receivecode>"+
	            "<departmentcode>"+storeOutHead.getDepartmentcode()+"</departmentcode>"+
	            "<personcode>"+storeOutHead.getCpersonCode()+"</personcode>"+
	            "<purchasetypecode />"+
	            "<saletypecode></saletypecode>"+
	            "<customercode>"+storeOutHead.getCustomercode()+"</customercode>"+
	            "<customerccode></customerccode>"+
	            "<cacauthcode></cacauthcode>"+
	            "<vendorcode />"+
	            "<ordercode />"+
	            "<quantity />"+
	            "<arrivecode />"+
	            "<billcode />"+
	            "<consignmentcode>"+storeOutHead.getConsignmentcode()+"</consignmentcode>"+
	            "<arrivedate />"+
	            "<checkcode />"+
	            "<checkdate />"+
	            "<checkperson />"+
	            "<templatenumber>"+storeOutHead.getTemplatenumber()+"</templatenumber>"+
	            "<serial />"+
	            "<handler/>"+
	            "<memory>"+storeOutHead.getCmemo()+"</memory>"+
	            "<maker>"+storeOutHead.getMaker()+"</maker>"+
	            "<chandler/>"+
	            "<define1 />"+
	            "<define2 />"+
	            "<define3 />"+
	            "<define4 />"+
	            "<define5 />"+
	            "<define6 />"+
	            "<define7 />"+
	            "<define8 />"+
	            "<define9 />"+
	            "<define10>"+storeOutHead.getDefine10()+"</define10>"+
	            "<define11 />"+
	            "<define12 />"+
	            "<define13 />"+
	            "<define14 />"+
	            "<define15 />"+
	            "<define16 />"+
	            "<auditdate></auditdate>"+
	            "<taxrate />"+
	            "<exchname />"+
	            "<exchrate />"+
	            "<discounttaxtype>0</discounttaxtype>"+
	            "<contact/>"+
	            "<phone />"+
	            "<mobile />"+
	            "<address/>"+
	            "<conphone />"+
	            "<conmobile />"+
	            "<deliverunit />"+
	            "<contactname />"+
	            "<officephone />"+
	            "<mobilephone />"+
	            "<psnophone />"+
	            "<psnmobilephone />"+
	            "<shipaddress>"+storeOutHead.getCshipAddress()+"</shipaddress>"+
	            "<addcode />"+
	            "<iscomplement>0</iscomplement>"+
	        "</header>";
   	 
   	 return requestXml;
	}
	
	/**
	 * RdRecords  收发记录子表
	 * @param storeOutBody
	 * @return
	 *String
	 *Administrator
	 *2014年10月14日
	 */
	public static String getStoreOutBodyXml(StoreOutBodyU8 storeOutBody){
		
		String chilid=
	            "<entry>"+
	                "<id></id>"+
	                "<barcode />"+
	                "<inventorycode>"+storeOutBody.getInventorycode()+"</inventorycode>"+
	                "<free1 />"+
	                "<free2 />"+
	                "<free3 />"+
	                "<free4 />"+
	                "<free5 />"+
	                "<free6 />"+
	                "<free7 />"+
	                "<free8 />"+
	                "<free9 />"+
	                "<free10 />"+
	                "<shouldquantity>"+storeOutBody.getShouldquantity()+"</shouldquantity>"+
	                "<shouldnumber>"+storeOutBody.getShouldnumber()+"</shouldnumber>"+
	                "<quantity>"+storeOutBody.getQuantity()+"</quantity>"+
	                "<cmassunitname>"+storeOutBody.getCmassunitname()+"</cmassunitname>"+
	                "<assitantunit>"+storeOutBody.getCsTComUnitCode()+"</assitantunit>"+
	                "<assitantunitname>"+storeOutBody.getCsTComUnitName()+"</assitantunitname>"+
	                "<irate>"+storeOutBody.getIrate()+"</irate>"+
	                "<number>"+storeOutBody.getNumber()+"</number>"+
	                "<price />"+
	                "<cost />"+
	                "<plancost />"+
	                "<planprice />"+
	                "<serial />"+
	                "<makedate />"+
	                "<validdate />"+
	                "<transitionid />"+
	                "<subbillcode />"+
	                "<subpurchaseid />"+
	                "<position>"+storeOutBody.getPosition()+"</position>"+
	                "<itemclasscode />"+
	                "<itemclassname />"+
	                "<itemcode />"+
	                "<itemname />"+
	                "<define22></define22>"+
	                "<define23 />"+
	                "<define24 />"+
	                "<define25 />"+
	                "<define26 />"+
	                "<define27 />"+
	                "<define28 />"+
	                "<define29 />"+
	                "<define30></define30>"+
	                "<define31></define31>"+
	                "<define32 />"+
	                "<define33></define33>"+
	                "<define34 />"+
	                "<define35 />"+
	                "<define36 />"+
	                "<define37 />"+
	                "<subconsignmentid></subconsignmentid>"+
	                "<delegateconsignmentid />"+
	                "<subproducingid />"+
	                "<subcheckid />"+
	                "<cRejectCode />"+
	                "<iRejectIds />"+
	                "<cCheckPersonCode />"+
	                "<dCheckDate />"+
	                "<cCheckCode />"+
	                "<iMassDate />"+
	                "<ioritaxcost />"+
	                "<ioricost />"+
	                "<iorimoney />"+
	                "<ioritaxprice />"+
	                "<iorisum />"+
	                "<taxrate>0</taxrate>"+
	                "<taxprice />"+
	                "<isum />"+
	                "<massunit />"+
	                "<vmivencode />"+
	                "<whpersoncode />"+
	                "<whpersonname />"+
	                "<batchproperty1 />"+
	                "<batchproperty2 />"+
	                "<batchproperty3 />"+
	                "<batchproperty4 />"+
	                "<batchproperty5 />"+
	                "<batchproperty6 />"+
	                "<batchproperty7 />"+
	                "<batchproperty8 />"+
	                "<batchproperty9 />"+
	                "<batchproperty10 />"+
	                "<iexpiratdatecalcu>0</iexpiratdatecalcu>"+
	                "<dexpirationdate />"+
	                "<cexpirationdate />"+
	            "</entry>";
		
		return chilid;
	}
	
	/**
	 * 挑拨单主表
	 * @param transvouchHead
	 * @return
	 *String
	 *Administrator
	 *2014年10月14日
	 */
	
	public static String getTransvouchHeadXml(TransvouchHeadU8 transvouchHead){
		
		String requestXml="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
		"<ufinterface sender=\""+Config.EAICODE+"\" receiver=\"u8\" roottag=\"transvouch\" docid=\"117497742\" proc=\"add\" codeexchanged=\"N\" exportneedexch=\"N\" paginate=\"0\" display=\"调拨单\" family=\"库存管理\" >"+
		  "<transvouch>"+
		    "<header>"+
		      "<btransflag/>"+
		      "<accounter/>"+
		      "<idepcode/>"+
		      "<odepcode/>"+
		      "<irdcode/>"+
		      "<ordcode/>"+
		      "<iwhcode>"+transvouchHead.getIwhcode()+"</iwhcode>"+
		      "<inwhaddress/>"+
		      "<inwhphone/>"+
		      "<inwhperson/>"+
		      "<owhcode>"+transvouchHead.getOwhcode()+"</owhcode>"+
		      "<outwhaddress/>"+
		      "<outwhphone/>"+
		      "<outwhperson/>"+
		      "<personcode/>"+
		      "<pspcode/>"+
		      "<tvcode>0000000001</tvcode>"+
		      "<mpocode/>"+
		      "<id></id>"+
		      "<proorderid/>"+
		      "<quantity></quantity>"+
		      "<vtid>89</vtid>"+
		      "<date>"+transvouchHead.getDate()+"</date>"+
		      "<memory/>"+
		      "<auditperson/>"+
		      "<auditdate/>"+
		      "<maker>"+transvouchHead.getMaker()+"</maker>"+
		      "<define1/>"+
		      "<define2/>"+
		      "<define3/>"+
		      "<define4/>"+
		      "<define5/>"+
		      "<define6/>"+
		      "<define7/>"+
		      "<define8/>"+
		      "<define9/>"+
		      "<define10>"+transvouchHead.getDefine10()+"</define10>"+
		      "<define11/>"+
		      "<define12/>"+
		      "<define13/>"+
		      "<define14/>"+
		      "<define15/>"+
		      "<define16/>"+
		      "<free1/>"+
		      "<free2/>"+
		      "<free3/>"+
		      "<free4/>"+
		      "<free5/>"+
		      "<free6/>"+
		      "<free7/>"+
		      "<free8/>"+
		      "<free9/>"+
		      "<free10/>"+
		      "<ordertype/>"+
		      "<transappcode>"+transvouchHead.getTransappcode()+"</transappcode>"+
		      "<version/>"+
		      "<bomid/>"+
		      "<csource>1</csource>"+
		      "<itransflag>正向</itransflag>"+
		    "</header>";
		
		return requestXml;
		
	}
	
	
	/**
	 * 调拨单子表
	 * @param transvouchBody
	 * @return
	 *String
	 *Administrator
	 *2014年10月14日
	 */
	public static String getTransvouchBodyXml(TransvouchBodyU8 transvouchBody){
		//"<inposcode>"+goodAllocation.get("cPosCode").toString()+"</inposcode>"+如果用到货位
		String chilid="<entry>"+
		        "<id></id>"+
		        "<barcode/>"+
		        "<inventorycode>"+transvouchBody.getInventorycode()+"</inventorycode>"+
		        "<free1/>"+
		        "<free2/>"+
		        "<free3/>"+
		        "<free4/>"+
		        "<free5/>"+
		        "<free6/>"+
		        "<free7/>"+
		        "<free8/>"+
		        "<free9/>"+
		        "<free10/>"+ 
		        "<quantity>"+transvouchBody.getQuantity()+"</quantity>"+
		        "<cmassunitname>"+transvouchBody.getCmassunitname()+"</cmassunitname>"+
		        "<assitantunit>"+transvouchBody.getcSTComUnitCode()+"</assitantunit>"+
		        "<assitantunitname>"+transvouchBody.getcSTComUnitName()+"</assitantunitname>"+
		        "<irate>"+transvouchBody.getIrate()+"</irate>"+
		        "<number>"+transvouchBody.getNumber()+"</number>"+
		        "<actualcost/>"+
		        "<actualprice/>"+
		        "<plancost/>"+
		        "<price/>"+
		        "<define22/>"+
		        "<define23/>"+
		        "<define24/>"+
		        "<define25/>"+
		        "<define26/>"+
		        "<define27/>"+
		        "<define28/>"+
		        "<define29/>"+
		        "<define30></define30>"+
		        "<define31>"+transvouchBody.getDefine31()+"</define31>"+
		        "<define32/>"+
		        "<define33/>"+
		        "<define34/>"+
		        "<define35/>"+
		        "<define36/>"+
		        "<define37/>"+
		        "<subconsignmentid></subconsignmentid>"+
		        "<bvencode/>"+
		        "<invouchcode/>"+
		        "<imassdate/>"+
		        "<serial/>"+
		        "<makedate/>"+
		        "<validdate/>"+
		        "<massunit/>"+
		        "<tvcode>0000000001</tvcode>"+
		        "<salecost></salecost>"+
		        "<saleprice></saleprice>"+
		        "<mpoids/>"+
		        "<rdsid/>"+
		        "<itemclasscode/>"+
		        "<itemclassname/>"+
		        "<itemcode/>"+
		        "<itemname/>"+
		        "<transappids>"+transvouchBody.getTransappids()+"</transappids>"+
		        "<sodid/>"+
		        "<saletype/>"+
		        "<vmivencode/>"+
		        "<inposcode></inposcode>"+
		        "<outposcode></outposcode>"+
		        "<batchproperty1/>"+
		        "<batchproperty2/>"+
		        "<batchproperty3/>"+
		        "<batchproperty4/>"+
		        "<batchproperty5/>"+
		        "<batchproperty6/>"+
		        "<batchproperty7/>"+
		        "<batchproperty8/>"+
		        "<batchproperty9/>"+
		        "<batchproperty10/>"+
		        "<iexpiratdatecalcu>0</iexpiratdatecalcu>"+
		        "<dexpirationdate/>"+
		        "<cexpirationdate/>"+
		      "</entry>";
		
		return chilid;
		
	}
	
	
	
	
	/**
	 * 拼接字符串
	 * @param head
	 * @param body
	 * @return
	 *String
	 *Administrator
	 *2014年11月05日
	 */
	public static String connectHeadBodyIn(String head,String body){
		String requestXml="";
		body="<body>"+body+"</body>";
		requestXml=head+body+"</storein></ufinterface>";
		return requestXml;
	}
	
	
	
	public static String connectHeadBodyOut(String head,String body){
		String requestXml="";
		body="<body>"+body+"</body>";
		requestXml=head+body+"</storeout>";
		return requestXml;
	}
	
	
	public static String connectHeadBodyOut(String HeadBodyOut){
		HeadBodyOut="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+ 
						"<ufinterface sender=\""+Config.EAICODE+"\" receiver=\"u8\" roottag=\"storeout\" docid=\"645103096\" proc=\"add\" codeexchanged=\"N\" exportneedexch=\"N\" display=\"出库单\" family=\"库存管理\" >"+
							HeadBodyOut+
						"</ufinterface>";
		return HeadBodyOut;
	}
	
	
	public static String connectHeadBodyTransvouch(String head,String body){
		String requestXml="";
		body="<body>"+body+"</body>";
		requestXml=head+body+"</transvouch></ufinterface>";
		return requestXml;
	}
	
	
}
