package com.emi.web.u8101;

import com.emi.common.util.CommonUtil;
import com.emi.sys.init.Config;
import com.emi.web.u8101.bean.StoreInBodyU8101;
import com.emi.web.u8101.bean.StoreInHeadU8101;

public class U8101xml {

    /**
     *RdRecord  收发记录主表
     * @param storeInHead
     * @return
     *String
     *Administrator
     *2014年10月14日
     */

    public static String  getStoreInHeadXml(StoreInHeadU8101 storeInHead){
        String requestXml="<storein>"+
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
                "<personcode>"+CommonUtil.Obj2String(storeInHead.getPersoncode())+"</personcode>"+
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
     * RdRecords  收发记录主表 (出入库记录)
     * @param storeInBody
     * @return
     *String
     *Administrator
     *2014年10月14日
     */
    public static String getStoreInBodyXml(StoreInBodyU8101 storeInBody){
        //"<position>"+goodAllocation.get("cPosCode").toString()+"</position>"+  (心愿如果存在货位)
        String chilid="<entry>"+
                "<id></id>"+
                "<autoid></autoid>"+
                "<barcode />"+
                "<inventorycode>"+storeInBody.getInventorycode()+"</inventorycode>"+
                "<free1>"+ CommonUtil.Obj2String(storeInBody.getCfree1())+"</free1>"+
                "<free2>"+CommonUtil.Obj2String(storeInBody.getCfree2())+"</free2>"+
                "<free3 />"+
                "<free4 />"+
                "<free5 />"+
                "<free6 />"+
                "<free7 />"+
                "<free8 />"+
                "<free9 />"+
                "<free10 />"+
                "<shouldquantity></shouldquantity>"+
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
                "<makedate>"+(CommonUtil.isNullObject(storeInBody.getMakedate())?"":storeInBody.getMakedate())+"</makedate>"+
                "<validdate>"+(CommonUtil.isNullObject(storeInBody.getValiddate())?"":storeInBody.getValiddate())+"</validdate>"+
                "<transitionid />"+
                "<subbillcode />"+
                "<subpurchaseid></subpurchaseid>"+
                "<position>"+(CommonUtil.isNullObject(storeInBody.getPosition())?"":storeInBody.getPosition())+"</position>"+
                "<itemclasscode />"+
                "<itemclassname />"+
                "<itemcode />"+
                "<itemname />"+
                "<define22 />"+
                "<define23>"+storeInBody.getDefine23()+"</define23>"+
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
                "<iMassDate>"+(CommonUtil.isNullObject(storeInBody.getImassDate())?"":storeInBody.getImassDate())+"</iMassDate>"+
                "<ioritaxcost></ioritaxcost>"+
                "<ioricost></ioricost>"+
                "<iorimoney></iorimoney>"+
                "<ioritaxprice></ioritaxprice>"+
                "<iorisum></iorisum>"+
                "<taxrate></taxrate>"+
                "<taxprice></taxprice>"+
                "<isum></isum>"+
                "<massunit>"+(CommonUtil.isNullObject(storeInBody.getMassunit())?"":storeInBody.getMassunit())+"</massunit>"+
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
        requestXml=head+body+"</storein>";
        return requestXml;
    }



    public static String connectHeadBodyIn(String HeadBodyIn){
        HeadBodyIn="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
                "<ufinterface sender=\""+ Config.EAICODE+"\" receiver=\"u8\" roottag=\"storein\" docid=\"88298976\" proc=\"add\" codeexchanged=\"N\" exportneedexch=\"N\" display=\"入库单\" family=\"库存管理\">"+
                HeadBodyIn+
                "</ufinterface>";
        return HeadBodyIn;
    }

}
