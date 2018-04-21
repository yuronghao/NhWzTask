package com.emi.web.u8101.service;

import com.emi.cache.service.CacheCtrlService;
import com.emi.common.service.EmiPluginService;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.DateUtil;
import com.emi.sys.init.Config;
import com.emi.web.u8101.dao.DaoU8101;
import com.emi.web.u8101.U8101xml;
import com.emi.web.u8101.bean.CurrentStock;
import com.emi.web.u8101.bean.StoreInBodyU8101;
import com.emi.web.u8101.bean.StoreInHeadU8101;
import com.emi.web.u8101.bean.RdRecord;
import com.emi.web.u8101.bean.RdRecords;
import com.emi.webservice.YonYouUtil;
import com.emi.wms.bean.*;
import com.emi.wms.util.Constants;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

public class ServiceU8101 extends EmiPluginService {
    private CacheCtrlService cacheCtrlService;

    private DaoU8101 daoU8101;

    public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
        this.cacheCtrlService = cacheCtrlService;
    }


    public DaoU8101 getDaoU8101() {
        return daoU8101;
    }

    public void setDaoU8101(DaoU8101 daoU8101) {
        this.daoU8101 = daoU8101;
    }


    /**
    * @Desc
    * @author yurh
    * @create 2018-03-08 17:51:58
    **/
    public boolean addPoWareHouse(JSONObject jobj) throws Exception{

        JSONArray jsonArray=jobj.getJSONArray("wmsGoodsLists");

        //从用友中查询采购订单信息
        String condition=" po.POID='"+jobj.getString("mainTableIdentity")+"'";
        List<Map> maps=daoU8101.getProcureorderCYonYou(condition);

        Set<String> warehouse=new HashSet<String>();
        for(Object obj:jsonArray){
            JSONObject prowctJson = (JSONObject) obj;
            AaGoodsallocation aaGoodsallocation=cacheCtrlService.getGoodsAllocation(prowctJson.getString("goodsAllocationUid"));//货位信息
            if(aaGoodsallocation != null){
                warehouse.add(aaGoodsallocation.getWhcode());
            }

        }


        Iterator<String> it=warehouse.iterator();
        List<RdRecords> rdRecords=new ArrayList<RdRecords>();//入库单子表
        List<RdRecord> rdRecord=new ArrayList<RdRecord>();//入库单主表
        List<CurrentStock> css=new ArrayList<CurrentStock>();//现存量
        List<RdRecords> tempRdRecords=new ArrayList<RdRecords>();

        String dataBaseName= Config.BUSINESSDATABASE;
        String[] strs=dataBaseName.split("_");

        YmUser ymUser=cacheCtrlService.getUser(jobj.getString("userGid"));//用户信息

        while(it.hasNext()){
            String ckCode=it.next();
            int k=0;//用来存储子表，存储过程步长
            int irowno=1;//

            for(Object obj:jsonArray){
                JSONObject prowctJson = (JSONObject) obj;
                AaGoodsallocation aaGoodsallocation=cacheCtrlService.getGoodsAllocation(prowctJson.getString("goodsAllocationUid"));//货位信息
                AaWarehouse	aaWarehouse=cacheCtrlService.getWareHouse(aaGoodsallocation.getWhuid());

                if(ckCode.equalsIgnoreCase(aaWarehouse.getWhcode())){
                    AaGoods aaGoods=cacheCtrlService.getGoods(prowctJson.getString("goodsUid"));

                    RdRecords rds=new RdRecords();


                    rds.setCinvCode(CommonUtil.isNullObject(prowctJson.get("goodsCode"))?"":prowctJson.get("goodsCode").toString());
                    rds.setIquantity(CommonUtil.str2BigDecimal(prowctJson.getString("submitNum")));
                    rds.setInum(null);//辅计量数量
                    rds.setCdefine30(CommonUtil.isNullObject(prowctJson.get("gid"))?"":prowctJson.get("gid").toString());//一般存本地库的子表gid （约定俗成，宋总说的）

                    if(aaWarehouse.getWhpos().intValue()==1 ){
                        rds.setCposition(aaGoodsallocation.getCode());
                    }else{
                        rds.setCposition("");
                    }

                    rds.setCbatch(CommonUtil.isNullObject(prowctJson.get("batch"))?"":prowctJson.get("batch").toString());


                    for(Map map :maps){
                        if(prowctJson.getString("childTableIdentity").equalsIgnoreCase(map.get("id").toString())){

                            rds.setiPOsID(map.get("id").toString());//采购订单子表标识
                            rds.setOrdercPoID(map.get("cPOID").toString());//采购订单单号


                            rds.setIunitCost(CommonUtil.str2BigDecimal(map.get("iUnitPrice").toString()));//原币无税单价
                            rds.setIprice((CommonUtil.str2BigDecimal(map.get("iUnitPrice").toString()).multiply(CommonUtil.str2BigDecimal(prowctJson.getString("submitNum")))).setScale(2,BigDecimal.ROUND_HALF_UP));//原币无税金额（原币无税单价*数量）
                            rds.setiAPrice((CommonUtil.str2BigDecimal(map.get("iUnitPrice").toString()).multiply(CommonUtil.str2BigDecimal(prowctJson.getString("submitNum")))).setScale(2,BigDecimal.ROUND_HALF_UP));//暂估金额（原币无税单价*数量）
                            rds.setCpoID(map.get("cPOID").toString());//订单号 采购订单单号
                            rds.setfACost(CommonUtil.str2BigDecimal(map.get("iUnitPrice").toString()));//暂估单价（原币无税单价）
                            rds.setChVencode(map.get("cVenCode").toString());//采购订单 供应商编码U8编码
                            rds.setiOriTaxCost(CommonUtil.str2BigDecimal(map.get("iTaxPrice").toString()));//原币含税单价
                            rds.setiOriCost(CommonUtil.str2BigDecimal(map.get("iUnitPrice").toString()));//原币无税单价
                            rds.setiOriMoney((CommonUtil.str2BigDecimal(map.get("iUnitPrice").toString()).multiply(CommonUtil.str2BigDecimal(prowctJson.getString("submitNum")))).setScale(2,BigDecimal.ROUND_HALF_UP));//原币无税金额
                            rds.setiOriTaxPrice((CommonUtil.str2BigDecimal(map.get("iTaxPrice").toString()).multiply(CommonUtil.str2BigDecimal(prowctJson.getString("submitNum"))).subtract(rds.getIprice())).setScale(2,BigDecimal.ROUND_HALF_UP));//原币税额
                            rds.setiTaxPrice(rds.getiOriTaxPrice());//本币税额
                            rds.setiSum((CommonUtil.str2BigDecimal(map.get("iTaxPrice").toString()).multiply(CommonUtil.str2BigDecimal(prowctJson.getString("submitNum")))).setScale(2,BigDecimal.ROUND_HALF_UP));//本币价税合计
                            rds.setbTaxCost(1);//记税方式，，传入默认值 ‘1’
                            rds.setIoriSum(rds.getiSum());//原币价税合计

//                            rds.setCpoID(CommonUtil.Obj2String(map.get("omcCode")));//委外订单号
//                            rds.setIoMoDID(CommonUtil.Obj2String(map.get("MODetailsID")));//委外订单子表ID
                            rds.setItaxrate(CommonUtil.isNullObject(maps.get(0).get("iPerTaxRate"))?null:maps.get(0).get("iPerTaxRate").toString());//税率
                            rds.setInQuantity(CommonUtil.isNullObject(map.get("stillQuantity"))?null:CommonUtil.str2BigDecimal(map.get("stillQuantity").toString()));// 应收应发数量;
//                            rds.setDbarvdate(CommonUtil.isNullObject(map.get("dPODate"))?null:map.get("dPODate").toString().substring(0, 10));// 到货日期;
                            rds.setIrowno(irowno);// 单据体行号 
                            break;
                        }
                    }

                    JSONArray cfreeArray=prowctJson.getJSONArray("cfree");
                    if(cfreeArray != null){
                        for(int m=0;m<cfreeArray.size();m++){//后续再完善
                            JSONObject cf = (JSONObject)cfreeArray.get(m);
                            if(cf.getString("colName").equalsIgnoreCase("cfree1")){
                                rds.setCfree1(CommonUtil.isNullObject(cf.get("value"))?"":cf.get("value").toString());
                            }else if(cf.getString("colName").equalsIgnoreCase("cfree2")){
                                rds.setCfree2(CommonUtil.isNullObject(cf.get("value"))?"":cf.get("value").toString());
                            }
                        }
                    }


                    if((CommonUtil.isNullObject(aaGoods.getIsInvQuality())?0:aaGoods.getIsInvQuality().intValue())==1 ){//有效期管理
                        String massDate=DateUtil.nextDay(prowctJson.getInt("massDate"), DateUtil.LONG_DATE_FORMAT)+" 00:00:00";
                        rds.setDvdate(Timestamp.valueOf(massDate));
                    }



                    tempRdRecords.add(rds);
                    rdRecords.add(rds);

                    CurrentStock cs=new CurrentStock();
                    cs.setCinvCode(rds.getCinvCode());
                    cs.setCwhCode(ckCode);
                    cs.setCbatch(rds.getCbatch());
                    cs.setCfree1(rds.getCfree1());
                    cs.setCfree2(rds.getCfree2());
                    cs.setIquantity(rds.getIquantity());
                    cs.setInum(rds.getInum());
                    cs.setCvmivencode(rds.getCvmivencode());
                    if((CommonUtil.isNullObject(aaGoods.getIsInvQuality())?0:aaGoods.getIsInvQuality().intValue())==1 ){
                        cs.setDvDate(rds.getDvdate());
                    }

                    // TODO cs.setFoutNum(foutNum);无换算 固定换算率 浮动换算率需要区分
                    css.add(cs);
                    irowno++;
                    k++;
                }

            }

            int[] idOuts=daoU8101.getBillId("", strs[1], "rd", k);//调用存储过程获得入库单主子表id
            int n=1;
            for(RdRecords rds:tempRdRecords){
                rds.setAutoID(100000000+(idOuts[1]-k+n));
                rds.setId(100000000+idOuts[0]);
                n++;
            }


            RdRecord rdo=new RdRecord();
            rdo.setId(100000000+idOuts[0]);
            rdo.setBrdFlag(1); //收发标志 0 发 1收
            rdo.setCvouchType("01");
            rdo.setCbusType("普通采购");
            rdo.setCsource("采购订单");
            rdo.setCwhCode(ckCode);
            rdo.setDdate(Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00"));
            rdo.setCcode(getBillIdForYonYou(Constants.EMICGRK));
            rdo.setCmaker(ymUser.getUserName());
            rdo.setcHandler(ymUser.getUserName());
            rdo.setDveriDate(Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00"));//审核日期
            rdo.setDnverifytime(null);
            rdo.setCdefine10(UUID.randomUUID().toString());
            rdo.setCvenCode(maps.get(0).get("cvenCode").toString());
//            rdo.setCorderCode(CommonUtil.Obj2String(maps.get(0).get("omcCode")));//委外订单号
//            rdo.setCaRVCode(maps.get(0).get("cCode").toString());//采购到货单号
            rdo.setCorderCode(maps.get(0).get("cPOID").toString());//采购订单号
            rdo.setIpurorderid(maps.get(0).get("POID").toString());//采购订单主表标识
            rdo.setVtid(27);
            rdo.setIexchRate("1");
            rdo.setCrdCode(jobj.getString("rdStyle"));
            rdo.setCdepCode(maps.get(0).get("cDepCode").toString());
            rdo.setCpTCode(CommonUtil.isNullObject(maps.get(0).get("cPTCode"))?null:maps.get(0).get("cPTCode").toString());

//            rdo.setDaRVDate(maps.get(0).get("dDate").toString());//到货日期
            rdo.setCexchName(CommonUtil.isNullObject(maps.get(0).get("cExch_Name"))?null:maps.get(0).get("cExch_Name").toString());
            rdo.setItaxRate(CommonUtil.isNullObject(maps.get(0).get("mainitaxrate"))?null:maps.get(0).get("mainitaxrate").toString());
            rdo.setDnmaketime(Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.FORMAT_ONE)));//制单日期
            rdo.setDnverifytime(Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.FORMAT_ONE)));//审核时间
            rdRecord.add(rdo);
        }

        daoU8101.addRdRecord(rdRecord);/////////////////////////////////////////////////////////////////////////采购入库单主表
        daoU8101.addRdRecords(tempRdRecords);///////////////////////////////////////////////////////////////////采购入库单子表


        List<CurrentStock> csnew=getNewCurrentStockList(css);
        updCurrentStock(csnew);///////////////////////////////////////////////////////////////////////////修改仓库现存量

        updateSourceBillWhenSubmitWareHouse(null,rdRecords, Constants.TASKTYPE_CGRK,null);//////////////////////回填采购订单已入库数量

        return true;


    }

    /**
    * @Desc 回填单据
    * @author yurh
    * @create 2018-03-09 15:04:03
    **/
    public boolean updateSourceBillWhenSubmitWareHouse(List<RdRecord> rd, List<RdRecords> goods, String billType, List<CurrentStock> css){//type in入库 out出库
        if(billType.equalsIgnoreCase(Constants.TASKTYPE_CGRK)){//采购订单 反填采购订单已出库库数量
            daoU8101.backFillPOPodetails(goods);
        }



        return true;
    }

    private boolean updCurrentStock(List<CurrentStock> asList) {
        StringBuffer sbf=new StringBuffer();
        for(CurrentStock vi:asList){
            sbf.append("'"+vi.getCinvCode()+"',");
        }

        String condition=sbf.toString();
        if(condition.length()>0){
            condition=condition.substring(0, condition.length()-1);
            condition=" cinvCode in ("+condition+")";
        }

        List<CurrentStock> currentStock=daoU8101.getAllocationStock(condition);//查询

        List<CurrentStock> toUpdate=new ArrayList<CurrentStock>();//修改
        List<CurrentStock> toADD=new ArrayList<CurrentStock>();//增加

        for(CurrentStock as:asList){
            boolean toA=true;
            for(CurrentStock asnow:currentStock){
                if(as.equals(asnow)){//方法重写

                    as.setAutoID(asnow.getAutoID());
                    toUpdate.add(as);

                    toA=false;
                    break;
                }
            }

            if(toA){
                toADD.add(as);
            }


        }

        //修改
        if(toUpdate.size()>0){
            daoU8101.batchUptCurrentStock(toUpdate);
        }

        if(toADD.size()>0){
            daoU8101.batchInsertCurrentStock(toADD);
        }

        return true;


    }


    //合并相同的物料，返回新的货位现存量表
    public List<CurrentStock> getNewCurrentStockList(List<CurrentStock> css){
        List<CurrentStock> csnew=new ArrayList<CurrentStock>();

        for(CurrentStock csold:css){
            boolean toAdd=true;
            for(CurrentStock csn:csnew){
                if(csold.equals(csn)){
                    csn.setIquantity(CommonUtil.BigDecimal2BigDecimal(csn.getIquantity()).add(CommonUtil.BigDecimal2BigDecimal(csold.getIquantity())));
                    csn.setInum(CommonUtil.BigDecimal2BigDecimal(csn.getInum()).add(CommonUtil.BigDecimal2BigDecimal(csold.getInum())));
                    csn.setFoutQuantity(CommonUtil.BigDecimal2BigDecimal(csn.getFoutQuantity()).add(CommonUtil.BigDecimal2BigDecimal(csold.getFoutQuantity())));
                    csn.setFoutNum(CommonUtil.BigDecimal2BigDecimal(csn.getFoutNum()).add(CommonUtil.BigDecimal2BigDecimal(csold.getFoutNum())));

                    csn.setFinQuantity(CommonUtil.BigDecimal2BigDecimal(csn.getFinQuantity()).add(CommonUtil.BigDecimal2BigDecimal(csold.getFinQuantity())));
                    csn.setFinNum(CommonUtil.BigDecimal2BigDecimal(csn.getFinNum()).add(CommonUtil.BigDecimal2BigDecimal(csold.getFinNum())));

                    toAdd=false;
                    break;
                }
            }
            if(toAdd){
                csnew.add(csold);
            }
        }

        return csnew;
    }




    //采购入库完善以及回填上游相关单据
    public boolean updateSourceWhenProcureArrivalIn(List<StoreInHeadU8101> storeInHeads, List<StoreInBodyU8101> storeInBodys){
        daoU8101.updateRdrecordProcureArrival(storeInHeads);
        daoU8101.updateRdrecordsProcureArrival(storeInBodys);
        daoU8101.updatePuAppVouchs(storeInBodys);
        return true;
    }

    public boolean addMeterialOut(JSONObject jobj) {
        JSONArray jsonArray=jobj.getJSONArray("wmsGoodsLists");
        String condition="  ";
        //从用友中查询到货单信息
//        String condition="  omn.moid='"+jobj.getString("mainTableIdentity")+"'";
//        List<Map> maps=daoU8101.getOMMOMaterials(condition);

        Set<String> warehouse=new HashSet<String>();
        for(Object obj:jsonArray){
            JSONObject prowctJson = (JSONObject) obj;
//            AaGoodsallocation aaGoodsallocation=cacheCtrlService.getGoodsAllocation(prowctJson.getString("goodsAllocationUid"));//货位信息 (货位只有一个默认)
            warehouse.add(jobj.getString("whCode"));
        }

        Iterator<String> it=warehouse.iterator();
        List<RdRecords> rdRecords=new ArrayList<RdRecords>();//出库单子表
        List<RdRecord> rdRecord=new ArrayList<RdRecord>();//出库单主表
        List<CurrentStock> css=new ArrayList<CurrentStock>();//现存量


        String dataBaseName=Config.BUSINESSDATABASE;
        String[] strs=dataBaseName.split("_");

		/*condition=" CardNumber='0412'  ";//提交材料出库时使用 0412
		Map rdVoucherHistory=daoU890.getVoucherHistory(condition);*/

        YmUser ymUser=cacheCtrlService.getUser(jobj.getString("userGid"));//用户信息

        AaDepartment ad=cacheCtrlService.getDepartment(jobj.getString("dptGid"));//领用部门信息

        AaDepartment usead=cacheCtrlService.getDepartment(jobj.getString("dptGid"));//使用部门信息

        int i=1;//用来记录主表单号
        int rdOutVoucherId=0;
        while(it.hasNext()){
            String ckCode=it.next();
            int bvmiUsed=0;//代管消耗标识
			/*rdOutVoucherId=Integer.parseInt(rdVoucherHistory.get("cNumber").toString())+i;
			String rdOutBillCode=getbillCode("0412");*/

            int k=0;//用来存储子表，存储过程步长

            List<RdRecords> tempRdRecords=new ArrayList<RdRecords>();

            for(Object obj:jsonArray){
                JSONObject prowctJson = (JSONObject) obj;
//                AaGoodsallocation aaGoodsallocation=cacheCtrlService.getGoodsAllocation(prowctJson.getString("goodsAllocationUid"));//货位信息
//                AaWarehouse	aaWarehouse=cacheCtrlService.getWareHouse(aaGoodsallocation.getWhuid());

                if(ckCode.equalsIgnoreCase(jobj.getString("whCode"))){
                    AaGoods aaGoods=cacheCtrlService.getGoods(prowctJson.getString("goodsUid"));

                    RdRecords rds=new RdRecords();

//                    if(aaWarehouse.getBproxyWh().intValue()==1){
//                        bvmiUsed=1;
//                    }
//
//                    if(aaWarehouse.getBproxyWh().intValue()==1){
//                        rds.setCvmivencode(CommonUtil.Obj2String(prowctJson.get("cvMIVenCode")));//代管商编码
//                    }

                    rds.setBvmiUsed(bvmiUsed);
                    rds.setCinvCode(CommonUtil.isNullObject(prowctJson.get("goodsCode"))?"":prowctJson.get("goodsCode").toString());
                    rds.setIquantity(CommonUtil.str2BigDecimal(prowctJson.getString("submitNum")));//主数量
                    if(!CommonUtil.isNullObject(aaGoods.getCasscomunitcode())){
                        rds.setInum(CommonUtil.str2BigDecimal(prowctJson.get("submitQuantity")));//辅数量
                    }else{
                        rds.setInum(null);
                    }

                    rds.setCdefine30(CommonUtil.isNullObject(prowctJson.get("gid"))?"":prowctJson.get("gid").toString());

//                    if(aaWarehouse.getWhpos().intValue()==1 ){
//                        rds.setCposition(aaGoodsallocation.getCode());
//                    }else{
//                        rds.setCposition("");
//                    }

                    rds.setCbatch(CommonUtil.isNullObject(prowctJson.get("batch"))?"":prowctJson.get("batch").toString());

//                    for(Map map :maps){
//                        if(prowctJson.getString("childTableIdentity").equalsIgnoreCase(map.get("mOMaterialsID").toString())){
////							rds.setShoudIquantity(BigDecimal.valueOf(Double.parseDouble(map.get("iQuantity").toString())));
//                            rds.setIoMoDID(map.get("moDetailsID").toString());//委外订单子表ID
//                            rds.setIoMoMID(map.get("mOMaterialsID").toString());//委外订单用料表ID
//                            rds.setComcode(map.get("ccode").toString());
//                            rds.setInvcode(map.get("cInvCode").toString());
//                            break;
//                        }
//                    }

                    JSONArray cfreeArray=prowctJson.getJSONArray("cfree");
                    for(int m=0;m<cfreeArray.size();m++){//后续再完善
                        JSONObject cf = (JSONObject)cfreeArray.get(m);
                        if(cf.getString("colName").equalsIgnoreCase("cfree1")){
                            rds.setCfree1(CommonUtil.isNullObject(cf.get("value"))?"":cf.get("value").toString());
                        }else if(cf.getString("colName").equalsIgnoreCase("cfree2")){
                            rds.setCfree2(CommonUtil.isNullObject(cf.get("value"))?"":cf.get("value").toString());
                        }
                    }

                    //有效期管理
                    if((CommonUtil.isNullObject(aaGoods.getIsInvQuality())?0:aaGoods.getIsInvQuality().intValue())==1  ){

                        condition=" cinvcode='"+rds.getCinvCode()+"' and cwhcode='"+ckCode+"' and cbatch='"+rds.getCbatch()+"'";
                        if(!CommonUtil.isNullObject(rds.getCfree1())){
                            condition=condition+" and cfree1='"+rds.getCfree1()+"'";
                        }
                        if(!CommonUtil.isNullObject(rds.getCfree2())){
                            condition=condition+" and cfree2='"+rds.getCfree2()+"'";
                        }
                        List<CurrentStock> cs=daoU8101.getAllocationStock(condition);

                        if(cs.size()==1){
                            rds.setDmadeDate(cs.get(0).getDmDate());
                            rds.setDvdate(cs.get(0).getDvDate());
                            rds.setImassDate(cs.get(0).getImassDate());
                            rds.setCmassUnit(3);
                        }else{
                            System.out.println("存在多个有效期的库存");
                            return false;
                        }

                    }


                    tempRdRecords.add(rds);
                    rdRecords.add(rds);

                    CurrentStock cs=new CurrentStock();//货位现存量表
                    cs.setCinvCode(rds.getCinvCode());
                    cs.setCwhCode(ckCode);
                    cs.setCbatch(rds.getCbatch());
                    cs.setCfree1(rds.getCfree1());
                    cs.setCfree2(rds.getCfree2());
                    cs.setIquantity(rds.getIquantity().negate());//相反
                    cs.setInum(CommonUtil.isNullObject(rds.getInum())?new BigDecimal(0):rds.getInum().negate());//相反
                    cs.setDvDate(rds.getDvdate());//失效期
                    cs.setDmDate(rds.getDmadeDate());
                    cs.setImassDate(rds.getImassDate());
                    cs.setCmassUnit(3);
                    cs.setCvmivencode(rds.getCvmivencode());
                    // TODO cs.setFoutNum(foutNum);无换算 固定换算率 浮动换算率需要区分
                    css.add(cs);


                    k++;
                }

            }

            int[] idOuts=daoU8101.getBillId("", strs[1], "rd", k);//调用存储过程获得其他出库单主子表id

            int n=1;
            for(RdRecords rds:tempRdRecords){
                rds.setAutoID(100000000+(idOuts[1]-k+n));
                rds.setId(100000000+idOuts[0]);
                n++;

            }


            RdRecord rdo=new RdRecord();
            rdo.setId(100000000+idOuts[0]);
            rdo.setBrdFlag(0);//收发标志 0 发 1收
            rdo.setCvouchType("11");//单据类型编码
            rdo.setCbusType("领料");//业务类型
            rdo.setCsource("库存");//单据来源
            rdo.setCwhCode(ckCode);
            rdo.setDdate(Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00"));
            rdo.setCcode(getBillIdForYonYou(Constants.EMICLCK));
            rdo.setCmaker(ymUser.getUserName());
            rdo.setcHandler(ymUser.getUserName());
            rdo.setDveriDate(Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00"));
            rdo.setCdefine10(UUID.randomUUID().toString());
//            rdo.setCvenCode(maps.get(0).get("cvenCode").toString());// 供应商编码
            rdo.setVtid(65);
//            rdo.setCpsPcode(maps.get(0).get("cInvCode").toString());//父项产品编码
//            rdo.setCmPoCode(maps.get(0).get("ccode").toString());//生产订单编号
//            rdo.setIpurorderid(maps.get(0).get("moid").toString());//采购订单主表标识
            rdo.setCrdCode(jobj.getString("rdStyle"));// 收发类别编码
            rdo.setCdepCode(ad.getDepcode());
            rdo.setcMemo(usead.getDepname());
            rdRecord.add(rdo);

            i++;
        }

//		daoU890.uptVoucherHistory(String.valueOf(rdOutVoucherId), "0412");//修改其它入出库编号记录 //提交材料出库时使用 0412

        daoU8101.addRdRecord11(rdRecord);/////////////////////////////////////////////////////////////////////////添加其他出入库单主表
        daoU8101.addRdRecords11(rdRecords);///////////////////////////////////////////////////////////////////添加其他出入库单子表

//        daoU8101.addInvPosition(ips);///////////////////////////////////////////////////////////////////////添加存货货位记录表

        List<CurrentStock> csnew=getNewCurrentStockList(css);
        updCurrentStock(csnew);///////////////////////////////////////////////////////////////////////////修改仓库现存量

//        updateSourceBillWhenSubmitWareHouse(null,rdRecords, Constants.TASKTYPE_WWCLCK,null);///////////////////////////////回填订单已领料数量

        return true;
    }


}
