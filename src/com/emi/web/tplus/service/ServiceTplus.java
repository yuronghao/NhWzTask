package com.emi.web.tplus.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.emi.cache.service.CacheCtrlService;
import com.emi.common.service.EmiPluginService;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.DateUtil;
import com.emi.web.tplus.bean.StCurrentStock;
import com.emi.web.tplus.bean.StRDRecordb;
import com.emi.web.tplus.bean.StRdRecord;
import com.emi.web.tplus.dao.DaoTplus;
import com.emi.web.u890.bean.CurrentStock;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaGoodsallocation;
import com.emi.wms.bean.AaWarehouse;
import com.emi.wms.bean.YmUser;
import com.emi.wms.util.Constants;

public class ServiceTplus  extends EmiPluginService {

	private CacheCtrlService cacheCtrlService;
	
	private DaoTplus daoTplus;
	
	public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
		this.cacheCtrlService = cacheCtrlService;
	}


	public void setDaoTplus(DaoTplus daoTplus) {
		this.daoTplus = daoTplus;
	}


	/**
	 * @category 添加销售出库单（直接操作库）
	 *2016 2016年3月15日上午9:32:20
	 *String
	 *宋银海
	 */
	public boolean addSaleOut(JSONObject jobj) throws Exception{
		
		JSONArray jsonArray=jobj.getJSONArray("wmsGoodsLists");
		
		String condition="  dl.id='"+jobj.getString("billUid")+"'";
		List<Map> maps=daoTplus.getSaleSendCYonYou(condition);
		
		boolean isBack=false;

		List<StRdRecord> rdRecord=new ArrayList<StRdRecord>();//出库单子表
		List<StRDRecordb> rdRecords=new ArrayList<StRDRecordb>();//出库单主表
		List<StCurrentStock> css=new ArrayList<StCurrentStock>();//现存量
//		List<InvPosition> ips=new ArrayList<InvPosition>();//存货货位记录表
			
		YmUser ymUser=cacheCtrlService.getUser(jobj.getString("userGid"));//用户信息
		String headUid=UUID.randomUUID().toString();
		
		Set<String> warehouse=new HashSet<String>();
		int i=0;
		for(Object obj:jsonArray){
			
			JSONObject prowctJson = (JSONObject) obj;
			AaGoodsallocation aaGoodsallocation=cacheCtrlService.getGoodsAllocation(prowctJson.getString("goodsAllocationUid"));//货位信息
			AaWarehouse	aaWarehouse=cacheCtrlService.getWareHouse(aaGoodsallocation.getWhuid());//仓库
			AaGoods aaGoods=cacheCtrlService.getGoods(prowctJson.getString("goodsUid"));//物品
			
			warehouse.add(aaWarehouse.getGid());
			
			StRDRecordb rds=new StRDRecordb();
			rds.setId(UUID.randomUUID().toString());
			rds.setCreatedtime(DateUtil.dateToString(new Date(), DateUtil.FORMAT_ONE));
			
			String code="000"+i;
			code=code.substring(code.length()-4, code.length());
			rds.setCode(code);
			i++;
			rds.setIdinventory(prowctJson.getString("goodsUid"));
			rds.setIdunit(aaGoods.getGoodsunit());
			rds.setIdbaseunit(aaGoods.getGoodsunit());
			
			if(CommonUtil.str2BigDecimal(prowctJson.getString("submitNum")).compareTo(new BigDecimal(0))==-1){//小于0
				isBack=true;
			}
			
			if(isBack){
				rds.setQuantity(CommonUtil.str2BigDecimal(prowctJson.getString("submitNum")).negate());//数量
				rds.setBaseQuantity(CommonUtil.str2BigDecimal(prowctJson.getString("submitNum")).negate());//主数量
			}else{
				rds.setQuantity(CommonUtil.str2BigDecimal(prowctJson.getString("submitNum")));//数量
				rds.setBaseQuantity(CommonUtil.str2BigDecimal(prowctJson.getString("submitNum")));//主数量
			}
			
			if(!CommonUtil.isNullObject(aaGoods.getCasscomunitcode())){
				if(isBack){
					rds.setSubQuantity(CommonUtil.str2BigDecimal(prowctJson.get("submitQuantity")).negate());//辅数量
				}else{
					rds.setSubQuantity(CommonUtil.str2BigDecimal(prowctJson.get("submitQuantity")));//辅数量
				}
				
			}else{
				rds.setSubQuantity(null);
			}
			
			rds.setIdwarehouse(aaWarehouse.getGid());
			if(aaWarehouse.getWhpos().intValue()==1 ){
				rds.setInventoryLocation(prowctJson.getString("goodsAllocationUid"));
			}else{
				rds.setInventoryLocation("");
			}
			
			rds.setBatch(CommonUtil.isNullObject(prowctJson.get("batch"))?"":prowctJson.get("batch").toString());
			
//			tempRdRecords.add(rds);
			rds.setIdRDRecordDTO(headUid);
			rds.setSaleDeliveryId(prowctJson.getString("saleSendCuid"));//关联销货单子表gid
			
			rds.setSourceVoucherId(maps.get(0).get("id").toString());
			rds.setSourceVoucherCode(maps.get(0).get("code").toString());
			rds.setSourceVoucherDetailId(prowctJson.getString("saleSendCuid"));
			rds.setCumulativeSaleDispatchQuantity(rds.getBaseQuantity());
			rds.setIdsourceVoucherType("5794D3DD-7FE4-4B5C-AF53-D21DDC13BF16");//目前是销货单，后面可能会有变化
			
			rdRecords.add(rds);
			
			StCurrentStock cs=new StCurrentStock();//货位现存量表
			
			cs.setIdinventory(rds.getIdinventory());
			cs.setIdwarehouse(rds.getIdwarehouse());
			cs.setBatch(rds.getBatch());
			cs.setBaseQuantity(rds.getBaseQuantity().negate());//退料时rds为负，cs需为正，货位现存量增加
			cs.setSubQuantity(CommonUtil.BigDecimal2BigDecimal(rds.getSubQuantity()).negate());
			cs.setIdbaseunit(rds.getIdbaseunit());
			cs.setRecordDate(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00");
			cs.setCreatedTime(DateUtil.dateToString(new Date(), DateUtil.LONG_DATE_FORMAT));
			cs.setUpdated(DateUtil.dateToString(new Date(), DateUtil.LONG_DATE_FORMAT));
			cs.setUpdatedBy(ymUser.getUserCode());
			
			css.add(cs);
			
		}
		
		String code=getbillCode("BB007F33-C0F0-4A19-8588-1E0E314D1F56");//单据类型id
		
		StRdRecord rdo=new StRdRecord();
		rdo.setId(headUid);
		rdo.setCode(code);   
		rdo.setVoucherState("CB61249F-8222-44E0-B177-61FBC108AB61");//单据状态
		rdo.setRdDirectionFlag(0);//收发标志  1入 0出
//		rdo.setAccountState("45964261-F94E-40CA-8643-B483034FEAAB");//核算状态
		rdo.setVoucherdate(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00");
		rdo.setMaker(ymUser.getUserCode());
		rdo.setMadedate(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00");
		rdo.setAuditor(ymUser.getUserCode());
		rdo.setMakerid(ymUser.getGid());
		rdo.setAuditorid(ymUser.getGid());
		rdo.setAuditeddate(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00");
		rdo.setAccountingperiod(DateUtil.getToMonth());
		rdo.setAccountingyear(DateUtil.getToYear());
		rdo.setCreatedtime(DateUtil.dateToString(new Date(), DateUtil.FORMAT_ONE));
		if(isBack){
			rdo.setIdbusitype("23FBABAD-1697-41AB-9FAC-8BA86B38FF01");//业务类型 对应表AA_BusiType 销售退货
		}else{
			rdo.setIdbusitype("DB58A9E1-D6AD-4C7A-8D07-23FDDDC51D98");//业务类型 对应表AA_BusiType 普通销售
		}
		
		rdo.setIdvouchertype("BB007F33-C0F0-4A19-8588-1E0E314D1F56");//单据类型 对应表SM_VoucherType 销售出库单
		
		if(warehouse.size()==1){
			rdo.setIdwarehouse(warehouse.iterator().next());
		}else{
			rdo.setIdwarehouse(null);
		}
		
		condition=" crdcode='"+jobj.getString("rdStyle")+"'";
		Map rdstyle=daoTplus.getrdstyle(condition);//收发类别
		rdo.setIdrdstyle(rdstyle.get("gid").toString());
		rdo.setVoucherYear(DateUtil.getToYear());
		rdo.setVoucherPeriod(DateUtil.getToMonth());
		rdo.setIdmarketingOrgan("4AD74463-E871-4DC1-BEB0-6E6EAA0A6386");//营销机构
		
		rdo.setDeliveryState("C7BDF907-F9DF-41FC-8F98-E4E5C2A920B7");//核销状态
		rdo.setIdpartner(maps.get(0).get("idcustomer").toString());//客户id
		rdo.setIdsettleCustomer(maps.get(0).get("idcustomer").toString());//核算客户
		rdo.setIdcurrency(maps.get(0).get("idcurrency").toString());//币种id
		rdo.setSourceVoucherId(maps.get(0).get("id").toString());//来源单据id
		rdo.setSourceVoucherCode(maps.get(0).get("code").toString());//来源单据号
		rdo.setIdsourceVoucherType("5794D3DD-7FE4-4B5C-AF53-D21DDC13BF16");//目前是销货单，后面可能会有变化
		
		rdRecord.add(rdo);
		
		
		daoTplus.addRdRecord(rdRecord);/////////////////////////////////////////////////////////////////////////添加其他出入库单主表
		daoTplus.addRdRecords(rdRecords);///////////////////////////////////////////////////////////////////添加其他出入库单子表
		
//		daoTplus.addInvPosition(ips);///////////////////////////////////////////////////////////////////////添加存货货位记录表
		
		List<StCurrentStock> csnew=getNewCurrentStockList(css);
		updCurrentStock(csnew);///////////////////////////////////////////////////////////////////////////修改仓库现存量
		
//		回填销货单
		updateSourceBillWhenSubmitWareHouse(rdRecords, Constants.TASKTYPE_XSCK);///////////////////////////////回填订单已领料数量
		
		return true;
	}
	
	/**
	 * @category 添加材料出库单
	 *2016 2016年12月28日下午4:11:38
	 *boolean
	 *宋银海
	 */
	public boolean addProcessMaterialOut(JSONObject jobj) throws Exception{
		
		JSONArray jsonArray=jobj.getJSONArray("wmsGoodsLists");
		
		boolean isBack=jobj.getBoolean("isBack");

		//从用友中查询订单信息
//		String condition="  mod.modid='"+jobj.getString("mainTableIdentity")+"'";
//		List<Map> maps=daoTplus.getMomOrderCYonYou(condition);
		
		
		List<StRdRecord> rdRecord=new ArrayList<StRdRecord>();//出库单主表
		List<StRDRecordb> rdRecords=new ArrayList<StRDRecordb>();//出库单子表
		List<StCurrentStock> css=new ArrayList<StCurrentStock>();//现存量
//		List<InvPosition> ips=new ArrayList<InvPosition>();//存货货位记录表
			
		YmUser ymUser=cacheCtrlService.getUser(jobj.getString("userGid"));//用户信息
		String headUid=UUID.randomUUID().toString();
		
		Set<String> warehouse=new HashSet<String>();
		int i=0;
		for(Object obj:jsonArray){
			
			JSONObject prowctJson = (JSONObject) obj;
			AaGoodsallocation aaGoodsallocation=cacheCtrlService.getGoodsAllocation(prowctJson.getString("goodsAllocationUid"));//货位信息
			AaWarehouse	aaWarehouse=cacheCtrlService.getWareHouse(aaGoodsallocation.getWhuid());//仓库
			AaGoods aaGoods=cacheCtrlService.getGoods(prowctJson.getString("goodsUid"));//物品
			
			warehouse.add(aaWarehouse.getGid());
			
			StRDRecordb rds=new StRDRecordb();
			rds.setId(UUID.randomUUID().toString());
			rds.setCreatedtime(DateUtil.dateToString(new Date(), DateUtil.FORMAT_ONE));
			
			String code="000"+i;
			code=code.substring(code.length()-4, code.length());
			rds.setCode(code);
			i++;
			rds.setIdinventory(prowctJson.getString("goodsUid"));
			rds.setIdunit(aaGoods.getGoodsunit());
			rds.setIdbaseunit(aaGoods.getGoodsunit());
			
			if(isBack){
				rds.setQuantity(CommonUtil.str2BigDecimal(prowctJson.getString("submitNum")).negate());//数量
				rds.setBaseQuantity(CommonUtil.str2BigDecimal(prowctJson.getString("submitNum")).negate());//主数量
			}else{
				rds.setQuantity(CommonUtil.str2BigDecimal(prowctJson.getString("submitNum")));//数量
				rds.setBaseQuantity(CommonUtil.str2BigDecimal(prowctJson.getString("submitNum")));//主数量
			}
			
			if(!CommonUtil.isNullObject(aaGoods.getCasscomunitcode())){
				if(isBack){
					rds.setSubQuantity(CommonUtil.str2BigDecimal(prowctJson.get("submitQuantity")).negate());//辅数量
				}else{
					rds.setSubQuantity(CommonUtil.str2BigDecimal(prowctJson.get("submitQuantity")));//辅数量
				}
				
			}else{
				rds.setSubQuantity(null);
			}
			
			rds.setIdwarehouse(aaWarehouse.getGid());
			if(aaWarehouse.getWhpos().intValue()==1 ){
				rds.setInventoryLocation(prowctJson.getString("goodsAllocationUid"));
			}else{
				rds.setInventoryLocation("");
			}
			
			rds.setBatch(CommonUtil.isNullObject(prowctJson.get("batch"))?"":prowctJson.get("batch").toString());
			
//					for(Map map :maps){
//						if(aaGoods.getGoodscode().equalsIgnoreCase(map.get("invCode").toString())){
//							rds.setImPoIds(map.get("allocateId").toString());
//							break;
//						}
//						
//					} 
//			
//			JSONArray cfreeArray=prowctJson.getJSONArray("cfree");
//			for(int m=0;m<cfreeArray.size();m++){//后续再完善
//				JSONObject cf = (JSONObject)cfreeArray.get(m);
//				if(cf.getString("colName").equalsIgnoreCase("cfree1")){
//					rds.setCfree1(CommonUtil.isNullObject(cf.get("value"))?"":cf.get("value").toString());
//				}else if(cf.getString("colName").equalsIgnoreCase("cfree2")){
//					rds.setCfree2(CommonUtil.isNullObject(cf.get("value"))?"":cf.get("value").toString());
//				}
//			}
			
//			tempRdRecords.add(rds);
			rds.setIdRDRecordDTO(headUid);
			rdRecords.add(rds);
			
			StCurrentStock cs=new StCurrentStock();//货位现存量表
			
			cs.setIdinventory(rds.getIdinventory());
			cs.setIdwarehouse(rds.getIdwarehouse());
			cs.setBatch(rds.getBatch());
//			cs.setCfree1(rds.getCfree1());
//			cs.setCfree2(rds.getCfree2());
			cs.setBaseQuantity(rds.getBaseQuantity().negate());//退料时rds为负，cs需为正，货位现存量增加
			cs.setSubQuantity(CommonUtil.BigDecimal2BigDecimal(rds.getSubQuantity()).negate());
			cs.setIdbaseunit(rds.getIdbaseunit());
			cs.setRecordDate(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00");
			cs.setCreatedTime(DateUtil.dateToString(new Date(), DateUtil.LONG_DATE_FORMAT));
			cs.setUpdated(DateUtil.dateToString(new Date(), DateUtil.LONG_DATE_FORMAT));
			cs.setUpdatedBy(ymUser.getUserCode());
			
			css.add(cs);
			
		}
		
//		String condition=" idvouchertype='8d7408ad-00b4-4b5b-a2eb-1e3e19386c0a' ";//对应表SM_VoucherType 材料出库单
//		Map codeMap=daoTplus.getRDRecordCode(condition);
//		String lastCode="0000"+( (CommonUtil.isNullObject(codeMap.get("code"))?0:Integer.parseInt(codeMap.get("code").toString()))+1);
//		lastCode=lastCode.substring(lastCode.length()-4, lastCode.length());
//		
//		int month=DateUtil.getToMonth(); 
//		String strMonth;
//		if(String.valueOf(month).length()==1){
//			strMonth="0"+String.valueOf(month);
//		}else{
//			strMonth=String.valueOf(month);
//		}
//		
//		lastCode="MD-"+DateUtil.getToYear()+"-"+strMonth+"-"+lastCode;
		String code=getbillCode("8d7408ad-00b4-4b5b-a2eb-1e3e19386c0a");
		
		StRdRecord rdo=new StRdRecord();
		rdo.setId(headUid);
		rdo.setCode(code);
		rdo.setVoucherState("CB61249F-8222-44E0-B177-61FBC108AB61");
		rdo.setRdDirectionFlag(0);//收发标志  1入 0出
		rdo.setAccountState("45964261-F94E-40CA-8643-B483034FEAAB");
		rdo.setVoucherdate(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00");
		rdo.setMaker(ymUser.getUserCode());
		rdo.setMadedate(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00");
		rdo.setAuditor(ymUser.getUserCode());
		rdo.setMakerid(ymUser.getGid());
		rdo.setAuditorid(ymUser.getGid());
		rdo.setAuditeddate(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00");
		rdo.setAccountingperiod(DateUtil.getToMonth());
		rdo.setAccountingyear(DateUtil.getToYear());
		rdo.setCreatedtime(DateUtil.dateToString(new Date(), DateUtil.FORMAT_ONE));
		if(isBack){
			rdo.setIdbusitype("00846D2E-0FE8-4A7A-ADF1-E95F2FB72F9D");//对应表AA_BusiType 直接退料
		}else{
			rdo.setIdbusitype("D1DB80EA-FD64-4AFE-8C7B-CF763DEB08A7");//对应表AA_BusiType 直接领料
		}
		
		rdo.setIdvouchertype("8D7408AD-00B4-4B5B-A2EB-1E3E19386C0A");//对应表SM_VoucherType 材料出库单
		
		if(warehouse.size()==1){
			rdo.setIdwarehouse(warehouse.iterator().next());
		}else{
			rdo.setIdwarehouse(null);
		}
		
		String condition=" crdcode='"+jobj.getString("rdStyle")+"'";
		Map rdstyle=daoTplus.getrdstyle(condition);//收发类别
		rdo.setIdrdstyle(rdstyle.get("gid").toString());
		rdo.setVoucherYear(DateUtil.getToYear());
		rdo.setVoucherPeriod(DateUtil.getToMonth());
		rdo.setIdmarketingOrgan("4AD74463-E871-4DC1-BEB0-6E6EAA0A6386");
		
		rdRecord.add(rdo);
		
		
		daoTplus.addRdRecord(rdRecord);/////////////////////////////////////////////////////////////////////////添加其他出入库单主表
		daoTplus.addRdRecords(rdRecords);///////////////////////////////////////////////////////////////////添加其他出入库单子表
		
//		daoTplus.addInvPosition(ips);///////////////////////////////////////////////////////////////////////添加存货货位记录表
		
		List<StCurrentStock> csnew=getNewCurrentStockList(css);
		updCurrentStock(csnew);///////////////////////////////////////////////////////////////////////////修改仓库现存量
		
//		t+中无生产订单无需回传
//		updateSourceBillWhenSubmitWareHouse(null,rdRecords, Constants.TASKTYPE_GXTL,null);///////////////////////////////回填订单已领料数量
		
		return true;
	}
	
	
	//合并相同的物料，返回新的货位现存量表
	public List<StCurrentStock> getNewCurrentStockList(List<StCurrentStock> css){
		List<StCurrentStock> csnew=new ArrayList<StCurrentStock>();
		
		for(StCurrentStock csold:css){
			boolean toAdd=true;
			for(StCurrentStock csn:csnew){
				if(csold.equals(csn)){
					csn.setBaseQuantity(CommonUtil.BigDecimal2BigDecimal(csn.getBaseQuantity()).add(CommonUtil.BigDecimal2BigDecimal(csold.getBaseQuantity())));
					csn.setSubQuantity(CommonUtil.BigDecimal2BigDecimal(csn.getSubQuantity()).add(CommonUtil.BigDecimal2BigDecimal(csold.getSubQuantity())));
					
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
	
	
	/**
	 * @category 添加工序成品入库单
	 *2016 2016年3月15日上午9:31:41
	 *String
	 *宋银海
	 */
	public boolean addProductionWarehouse(JSONObject jobj) throws Exception{
		
		
		JSONArray jsonArray=jobj.getJSONArray("wmsGoodsLists");
		
		//从用友中查询生产订单信息
//		String momOrderdetailMoDId=jobj.getString("mainTableIdentity");//mom_orderdetail MoDId
//		String condition=" mod.modid='"+momOrderdetailMoDId+"'";
//		Map order=daoTplus.getMomOrderYonYou(condition);
		
		
		List<StRdRecord> rdRecord=new ArrayList<StRdRecord>();//入库单子表
		List<StRDRecordb> rdRecords=new ArrayList<StRDRecordb>();//入库单主表
		List<StCurrentStock> css=new ArrayList<StCurrentStock>();//现存量
//		List<InvPosition> ips=new ArrayList<InvPosition>();//存货货位记录表
			
		YmUser ymUser=cacheCtrlService.getUser(jobj.getString("userGid"));//用户信息
		String headUid=UUID.randomUUID().toString();
		
		Set<String> warehouse=new HashSet<String>();
		int i=0;
		for(Object obj:jsonArray){
			
			JSONObject prowctJson = (JSONObject) obj;
			AaGoodsallocation aaGoodsallocation=cacheCtrlService.getGoodsAllocation(prowctJson.getString("goodsAllocationUid"));//货位信息
			AaWarehouse	aaWarehouse=cacheCtrlService.getWareHouse(aaGoodsallocation.getWhuid());//仓库
			AaGoods aaGoods=cacheCtrlService.getGoods(prowctJson.getString("goodsUid"));//物品
			
			warehouse.add(aaWarehouse.getGid());
			
			StRDRecordb rds=new StRDRecordb();
			rds.setId(UUID.randomUUID().toString());
			rds.setCreatedtime(DateUtil.dateToString(new Date(), DateUtil.FORMAT_ONE));
			
			String code="000"+i;
			code=code.substring(code.length()-4, code.length());
			rds.setCode(code);
			i++;
			rds.setIdinventory(prowctJson.getString("goodsUid"));
			rds.setIdunit(aaGoods.getGoodsunit());
			rds.setIdbaseunit(aaGoods.getGoodsunit());
			
			rds.setQuantity(CommonUtil.str2BigDecimal(prowctJson.getString("submitNum")));//数量
			rds.setBaseQuantity(CommonUtil.str2BigDecimal(prowctJson.getString("submitNum")));//主数量
			
			if(!CommonUtil.isNullObject(aaGoods.getCasscomunitcode())){
				rds.setSubQuantity(CommonUtil.str2BigDecimal(prowctJson.get("submitQuantity")));//辅数量
			}else{
				rds.setSubQuantity(null);
			}
			
			rds.setIdwarehouse(aaWarehouse.getGid());
			if(aaWarehouse.getWhpos().intValue()==1 ){
				rds.setInventoryLocation(prowctJson.getString("goodsAllocationUid"));
			}else{
				rds.setInventoryLocation("");
			}
			
			rds.setBatch(CommonUtil.isNullObject(prowctJson.get("batch"))?"":prowctJson.get("batch").toString());
			
//					for(Map map :maps){
//						if(aaGoods.getGoodscode().equalsIgnoreCase(map.get("invCode").toString())){
//							rds.setImPoIds(map.get("allocateId").toString());
//							break;
//						}
//						
//					} 
//			
//			JSONArray cfreeArray=prowctJson.getJSONArray("cfree");
//			for(int m=0;m<cfreeArray.size();m++){//后续再完善
//				JSONObject cf = (JSONObject)cfreeArray.get(m);
//				if(cf.getString("colName").equalsIgnoreCase("cfree1")){
//					rds.setCfree1(CommonUtil.isNullObject(cf.get("value"))?"":cf.get("value").toString());
//				}else if(cf.getString("colName").equalsIgnoreCase("cfree2")){
//					rds.setCfree2(CommonUtil.isNullObject(cf.get("value"))?"":cf.get("value").toString());
//				}
//			}
			
//			tempRdRecords.add(rds);
			rds.setIdRDRecordDTO(headUid);
			rdRecords.add(rds);
			
			StCurrentStock cs=new StCurrentStock();//货位现存量表
			
			cs.setIdinventory(rds.getIdinventory());
			cs.setIdwarehouse(rds.getIdwarehouse());
			cs.setBatch(rds.getBatch());
//			cs.setCfree1(rds.getCfree1());
//			cs.setCfree2(rds.getCfree2());
			cs.setBaseQuantity(rds.getBaseQuantity());
			cs.setSubQuantity(CommonUtil.BigDecimal2BigDecimal(rds.getSubQuantity()));
			cs.setIdbaseunit(rds.getIdbaseunit());
			cs.setRecordDate(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00");
			cs.setCreatedTime(DateUtil.dateToString(new Date(), DateUtil.LONG_DATE_FORMAT));
			cs.setUpdated(DateUtil.dateToString(new Date(), DateUtil.LONG_DATE_FORMAT));
			cs.setUpdatedBy(ymUser.getUserCode());
			cs.setIdmarketingOrgan("4AD74463-E871-4DC1-BEB0-6E6EAA0A6386");
			css.add(cs);
			
		}
		
//		String condition=" idvouchertype='5BC94E04-28DF-4B00-B809-198E3943BE16' ";//对应表SM_VoucherType 产成品入库单
//		Map codeMap=daoTplus.getRDRecordCode(condition);
//		String lastCode="0000"+( (CommonUtil.isNullObject(codeMap.get("code"))?0:Integer.parseInt(codeMap.get("code").toString()))+1);
//		lastCode=lastCode.substring(lastCode.length()-4, lastCode.length());
//		
//		int month=DateUtil.getToMonth(); 
//		String strMonth;
//		if(String.valueOf(month).length()==1){
//			strMonth="0"+String.valueOf(month);
//		}else{
//			strMonth=String.valueOf(month);
//		}
//		
//		lastCode="MC-"+DateUtil.getToYear()+"-"+strMonth+"-"+lastCode;
		
		String code=getbillCode("5BC94E04-28DF-4B00-B809-198E3943BE16");
		
		StRdRecord rdo=new StRdRecord();
		rdo.setId(headUid);
		rdo.setCode(code);
		rdo.setVoucherState("CB61249F-8222-44E0-B177-61FBC108AB61");//审核状态
		rdo.setRdDirectionFlag(1);//收发标志  1入 0出
		rdo.setAccountState("45964261-F94E-40CA-8643-B483034FEAAB");
		rdo.setVoucherdate(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00");
		rdo.setMaker(ymUser.getUserCode());
		rdo.setMadedate(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00");
		rdo.setAuditor(ymUser.getUserCode());
		rdo.setMakerid(ymUser.getGid());
		rdo.setAuditorid(ymUser.getGid());
		rdo.setAuditeddate(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00");
		rdo.setAccountingperiod(DateUtil.getToMonth());
		rdo.setAccountingyear(DateUtil.getToYear());
		rdo.setCreatedtime(DateUtil.dateToString(new Date(), DateUtil.FORMAT_ONE));
		rdo.setIdbusitype("7E95AF65-A08C-4EEF-858E-B797115D209F");//对应表AA_BusiType 自制加工
		rdo.setIdvouchertype("5BC94E04-28DF-4B00-B809-198E3943BE16");//对应表SM_VoucherType 产成品入库单
		
		if(warehouse.size()==1){
			rdo.setIdwarehouse(warehouse.iterator().next());
		}else{
			rdo.setIdwarehouse(null);
		}
		
		String condition=" crdcode='"+jobj.getString("rdStyle")+"'";
		Map rdstyle=daoTplus.getrdstyle(condition);//收发类别
		rdo.setIdrdstyle(rdstyle.get("gid").toString());//对应表AA_RDStyle
		rdo.setVoucherYear(DateUtil.getToYear());
		rdo.setVoucherPeriod(DateUtil.getToMonth());
		rdo.setIdmarketingOrgan("4AD74463-E871-4DC1-BEB0-6E6EAA0A6386");
		
		rdRecord.add(rdo);
		
		
		daoTplus.addRdRecord(rdRecord);/////////////////////////////////////////////////////////////////////////添加其他出入库单主表
		daoTplus.addRdRecords(rdRecords);///////////////////////////////////////////////////////////////////添加其他出入库单子表
		
//		daoTplus.addInvPosition(ips);///////////////////////////////////////////////////////////////////////添加存货货位记录表
		
		List<StCurrentStock> csnew=getNewCurrentStockList(css);
		updCurrentStock(csnew);///////////////////////////////////////////////////////////////////////////修改仓库现存量
		
//		t+中无生产订单无需回传
//		updateSourceBillWhenSubmitWareHouse(null,rdRecords, Constants.TASKTYPE_GXTL,null);///////////////////////////////回填订单已入库数量
		
		return true;
		
	}
	
	
	/**
	 * @category 直接添加成品入库
	 *2016 2016年12月30日上午9:16:12
	 *void
	 *宋银海
	 */
	public boolean addProductionWarehouseDirect(JSONObject jobj) throws Exception{
		
		
		JSONArray jsonArray=jobj.getJSONArray("wmsGoodsLists");
		
		//从用友中查询生产订单信息
//		String momOrderdetailMoDId=jobj.getString("mainTableIdentity");//mom_orderdetail MoDId
//		String condition=" mod.modid='"+momOrderdetailMoDId+"'";
//		Map order=daoTplus.getMomOrderYonYou(condition);
		
		
		List<StRdRecord> rdRecord=new ArrayList<StRdRecord>();//入库单子表
		List<StRDRecordb> rdRecords=new ArrayList<StRDRecordb>();//入库单主表
		List<StCurrentStock> css=new ArrayList<StCurrentStock>();//现存量
//		List<InvPosition> ips=new ArrayList<InvPosition>();//存货货位记录表
			
		YmUser ymUser=cacheCtrlService.getUser(jobj.getString("userGid"));//用户信息
		String headUid=UUID.randomUUID().toString();
		
		Set<String> warehouse=new HashSet<String>();
		int i=0;
		for(Object obj:jsonArray){
			
			JSONObject prowctJson = (JSONObject) obj;
			AaGoodsallocation aaGoodsallocation=cacheCtrlService.getGoodsAllocation(prowctJson.getString("goodsAllocationUid"));//货位信息
			AaWarehouse	aaWarehouse=cacheCtrlService.getWareHouse(aaGoodsallocation.getWhuid());//仓库
			AaGoods aaGoods=cacheCtrlService.getGoods(prowctJson.getString("goodsUid"));//物品
			
			warehouse.add(aaWarehouse.getGid());
			
			StRDRecordb rds=new StRDRecordb();
			rds.setId(UUID.randomUUID().toString());
			rds.setCreatedtime(DateUtil.dateToString(new Date(), DateUtil.FORMAT_ONE));
			
			String code="000"+i;
			code=code.substring(code.length()-4, code.length());
			rds.setCode(code);
			i++;
			rds.setIdinventory(prowctJson.getString("goodsUid"));
			rds.setIdunit(aaGoods.getGoodsunit());
			rds.setIdbaseunit(aaGoods.getGoodsunit());
			
			rds.setQuantity(CommonUtil.str2BigDecimal(prowctJson.getString("submitNum")));//数量
			rds.setBaseQuantity(CommonUtil.str2BigDecimal(prowctJson.getString("submitNum")));//主数量
			
			if(!CommonUtil.isNullObject(aaGoods.getCasscomunitcode())){
				rds.setSubQuantity(CommonUtil.str2BigDecimal(prowctJson.get("submitQuantity")));//辅数量
			}else{
				rds.setSubQuantity(null);
			}
			
			rds.setIdwarehouse(aaWarehouse.getGid());
			if(aaWarehouse.getWhpos().intValue()==1 ){
				rds.setInventoryLocation(prowctJson.getString("goodsAllocationUid"));
			}else{
				rds.setInventoryLocation("");
			}
			
			rds.setBatch(CommonUtil.isNullObject(prowctJson.get("batch"))?"":prowctJson.get("batch").toString());
			
//					for(Map map :maps){
//						if(aaGoods.getGoodscode().equalsIgnoreCase(map.get("invCode").toString())){
//							rds.setImPoIds(map.get("allocateId").toString());
//							break;
//						}
//						
//					} 
//			
//			JSONArray cfreeArray=prowctJson.getJSONArray("cfree");
//			for(int m=0;m<cfreeArray.size();m++){//后续再完善
//				JSONObject cf = (JSONObject)cfreeArray.get(m);
//				if(cf.getString("colName").equalsIgnoreCase("cfree1")){
//					rds.setCfree1(CommonUtil.isNullObject(cf.get("value"))?"":cf.get("value").toString());
//				}else if(cf.getString("colName").equalsIgnoreCase("cfree2")){
//					rds.setCfree2(CommonUtil.isNullObject(cf.get("value"))?"":cf.get("value").toString());
//				}
//			}
			
//			tempRdRecords.add(rds);
			rds.setIdRDRecordDTO(headUid);
			rdRecords.add(rds);
			
			StCurrentStock cs=new StCurrentStock();//货位现存量表
			
			cs.setIdinventory(rds.getIdinventory());
			cs.setIdwarehouse(rds.getIdwarehouse());
			cs.setBatch(rds.getBatch());
//			cs.setCfree1(rds.getCfree1());
//			cs.setCfree2(rds.getCfree2());
			cs.setBaseQuantity(rds.getBaseQuantity());
			cs.setSubQuantity(CommonUtil.BigDecimal2BigDecimal(rds.getSubQuantity()));
			cs.setIdbaseunit(rds.getIdbaseunit());
			cs.setRecordDate(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00");
			cs.setCreatedTime(DateUtil.dateToString(new Date(), DateUtil.LONG_DATE_FORMAT));
			cs.setUpdated(DateUtil.dateToString(new Date(), DateUtil.LONG_DATE_FORMAT));
			cs.setUpdatedBy(ymUser.getUserCode());
			cs.setIdmarketingOrgan("4AD74463-E871-4DC1-BEB0-6E6EAA0A6386");
			css.add(cs);
			
		}
		
//		String condition=" idvouchertype='5BC94E04-28DF-4B00-B809-198E3943BE16' ";//对应表SM_VoucherType 产成品入库单
//		Map codeMap=daoTplus.getRDRecordCode(condition);
//		String lastCode="0000"+( (CommonUtil.isNullObject(codeMap.get("code"))?0:Integer.parseInt(codeMap.get("code").toString()))+1);
//		lastCode=lastCode.substring(lastCode.length()-4, lastCode.length());
//		
//		int month=DateUtil.getToMonth(); 
//		String strMonth;
//		if(String.valueOf(month).length()==1){
//			strMonth="0"+String.valueOf(month);
//		}else{
//			strMonth=String.valueOf(month);
//		}
//		
//		lastCode="MC-"+DateUtil.getToYear()+"-"+strMonth+"-"+lastCode;
		
		String code=getbillCode("5BC94E04-28DF-4B00-B809-198E3943BE16");
		
		StRdRecord rdo=new StRdRecord();
		rdo.setId(headUid);
		rdo.setCode(code);
		rdo.setVoucherState("CB61249F-8222-44E0-B177-61FBC108AB61");//审核状态
		rdo.setRdDirectionFlag(1);//收发标志  1入 0出
		rdo.setAccountState("45964261-F94E-40CA-8643-B483034FEAAB");
		rdo.setVoucherdate(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00");
		rdo.setMaker(ymUser.getUserCode());
		rdo.setMadedate(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00");
		rdo.setAuditor(ymUser.getUserCode());
		rdo.setMakerid(ymUser.getGid());
		rdo.setAuditorid(ymUser.getGid());
		rdo.setAuditeddate(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00");
		rdo.setAccountingperiod(DateUtil.getToMonth());
		rdo.setAccountingyear(DateUtil.getToYear());
		rdo.setCreatedtime(DateUtil.dateToString(new Date(), DateUtil.FORMAT_ONE));
		rdo.setIdbusitype("7E95AF65-A08C-4EEF-858E-B797115D209F");//对应表AA_BusiType 自制加工
		rdo.setIdvouchertype("5BC94E04-28DF-4B00-B809-198E3943BE16");//对应表SM_VoucherType 产成品入库单
		
		if(warehouse.size()==1){
			rdo.setIdwarehouse(warehouse.iterator().next());
		}else{
			rdo.setIdwarehouse(null);
		}
		
		String condition=" crdcode='"+jobj.getString("rdStyle")+"'";
		Map rdstyle=daoTplus.getrdstyle(condition);//收发类别
		rdo.setIdrdstyle(rdstyle.get("gid").toString());//对应表AA_RDStyle
		rdo.setVoucherYear(DateUtil.getToYear());
		rdo.setVoucherPeriod(DateUtil.getToMonth());
		rdo.setIdmarketingOrgan("4AD74463-E871-4DC1-BEB0-6E6EAA0A6386");
		
		rdRecord.add(rdo);
		
		
		daoTplus.addRdRecord(rdRecord);/////////////////////////////////////////////////////////////////////////添加其他出入库单主表
		daoTplus.addRdRecords(rdRecords);///////////////////////////////////////////////////////////////////添加其他出入库单子表
		
//		daoTplus.addInvPosition(ips);///////////////////////////////////////////////////////////////////////添加存货货位记录表
		
		List<StCurrentStock> csnew=getNewCurrentStockList(css);
		updCurrentStock(csnew);///////////////////////////////////////////////////////////////////////////修改仓库现存量
		
//		t+中无生产订单无需回传
//		updateSourceBillWhenSubmitWareHouse(null,rdRecords, Constants.TASKTYPE_GXTL,null);///////////////////////////////回填订单已入库数量
		
		return true;
		
	}
	
	
	
	
	/**
	 * @category 修改现存量表待发货数量 
	 *2016 2016年4月7日下午4:21:51
	 *boolean
	 *宋银海
	 */
	public boolean updateCurrentStock(List<CurrentStock> asList) {
		
//		StringBuffer sbf=new StringBuffer();
//		for(CurrentStock vi:asList){
//			sbf.append("'"+vi.getCinvCode()+"',");
//		}
//		
//		String condition=sbf.toString();
//		if(condition.length()>0){
//			condition=condition.substring(0, condition.length()-1);
//			condition=" cinvCode in ("+condition+")";
//		}
//		
//		//查询
//		List<CurrentStock> currentStock=daoTplus.getAllocationStock(condition);
//		
//		List<CurrentStock> toUpdate=new ArrayList<CurrentStock>();
//		
//		for(CurrentStock as:asList){
//			boolean toA=true;
//			for(CurrentStock asnow:currentStock){
//				if(as.equals(asnow)){//方法重写
//					
//					as.setAutoID(asnow.getAutoID());
//					toUpdate.add(as);
//					
//					toA=false;
//					break;
//				}
//			}
//			
//		}
//		
//		//修改
//		if(toUpdate.size()>0){
//			daoTplus.batchUptFout(toUpdate);
//		}
//		
		return true;
	}


	
	
	
	
	/**
	 * @category 修改货位现存量
	 *2016 2016年4月7日下午4:21:51
	 *boolean
	 *宋银海
	 */
	public boolean updCurrentStock(List<StCurrentStock> asList) {
		
		StringBuffer sbf=new StringBuffer();
		for(StCurrentStock vi:asList){
			sbf.append("'"+vi.getIdinventory()+"',");
		}
		
		String condition=sbf.toString();
		if(condition.length()>0){
			condition=condition.substring(0, condition.length()-1);
			condition=" idinventory in ("+condition+")";
		}
		
		List<StCurrentStock> currentStock=daoTplus.getAllocationStock(condition);//查询
		
		List<StCurrentStock> toUpdate=new ArrayList<StCurrentStock>();//修改
		List<StCurrentStock> toADD=new ArrayList<StCurrentStock>();//增加
		
		for(StCurrentStock as:asList){
			boolean toA=true;
			for(StCurrentStock asnow:currentStock){
				if(as.equals(asnow)){//方法重写
					
					as.setId(asnow.getId());
					toUpdate.add(as);
					
					toA=false;
					break;
				}
			}
			
			if(toA){
				as.setId(UUID.randomUUID().toString());
				toADD.add(as);
			}
			
			
		}
		
		//修改
		if(toUpdate.size()>0){
			daoTplus.batchUptCurrentStock(toUpdate);
		}
		
		if(toADD.size()>0){
			daoTplus.batchInsertCurrentStock(toADD);
		}
		
		return true;
	}
	
	public List getsystemsetting(){
		return daoTplus.getsystemsetting();
	}
	
	public boolean updatesystemsetting(String synchroType,String eaiAddress,String eaiCode){
		return daoTplus.updatesystemsetting(synchroType,eaiAddress,eaiCode);
	}
	
	
	public boolean updateSourceBillWhenSubmitWareHouse(List<StRDRecordb>  goods,String billType){//type in入库 out出库
		
		if(billType.equalsIgnoreCase(Constants.TASKTYPE_XSCK)){
//			updateCurrentStock(css);
			daoTplus.updateDispatchListsrds(goods);
		}
		
		return true;
	}
	
	
	
	//根据规则获得单据编号
	public String getbillCode(String idvoucherType){
		Map rule=daoTplus.getVoucherNumber(idvoucherType);//获得订单规则
		
		StringBuffer sbf=new StringBuffer();
		if(!CommonUtil.isNullObject(rule.get("prefixion1"))){/////////////////////////第一前缀 
			if(rule.get("prefixion1").toString().equalsIgnoreCase("VC01")){//单据代码
				sbf.append(rule.get("prefixion1content").toString());
			}
		}
		
		if(!CommonUtil.isNullObject(rule.get("separator"))){//分隔符
			sbf.append(rule.get("separator").toString());
		}
		
		if(!CommonUtil.isNullObject(rule.get("prefixion2"))){/////////////////////////第二前缀 
			if(rule.get("prefixion2").toString().equalsIgnoreCase("VC02")){//单据日期
				
				int year=DateUtil.getToYear();
				int month=DateUtil.getToMonth();
				int day=DateUtil.getToday();
				String strMonth;
				String strDay;
				if(String.valueOf(month).length()==1){
					strMonth="0"+String.valueOf(month);
				}else{
					strMonth=String.valueOf(month);
				}
				
				if(String.valueOf(day).length()==1){
					strDay="0"+String.valueOf(day);
				}else{
					strDay=String.valueOf(day);
				}
				
				if(rule.get("prefixion2content").toString().equalsIgnoreCase("YMD") ){
					sbf.append(String.valueOf(year));//年
					if(!CommonUtil.isNullObject(rule.get("separator"))){//分隔符
						sbf.append(rule.get("separator").toString());
					}
					sbf.append(strMonth);//月
					if(!CommonUtil.isNullObject(rule.get("separator"))){//分隔符
						sbf.append(rule.get("separator").toString());
					}
					sbf.append(strDay);//日
					
					if(!CommonUtil.isNullObject(rule.get("separator"))){//分隔符
						sbf.append(rule.get("separator").toString());
					}
				}
				
				if(rule.get("prefixion2content").toString().equalsIgnoreCase("YM") ){
					sbf.append(String.valueOf(year));//年
					if(!CommonUtil.isNullObject(rule.get("separator"))){//分隔符
						sbf.append(rule.get("separator").toString());
					}
					sbf.append(strMonth);//月
					if(!CommonUtil.isNullObject(rule.get("separator"))){//分隔符
						sbf.append(rule.get("separator").toString());
					}
					
				}
				
				
			}
		}
		//////////////////////////第三前缀 视项目情况待完善
		
		//////////////////////流水glideLen
		Map param=new HashMap();
		param.put("serialnolength", rule.get("serialnolength").toString());
		param.put("codelike", sbf.toString()+"%");
		int totalLength=sbf.toString().length()+Integer.parseInt(rule.get("serialnolength").toString());
		param.put("totallength", totalLength);
		param.put("idvoucherType", idvoucherType);
		
		Map rdVoucherHistory=daoTplus.getVoucherHistory(param);
		
		int rdVoucherId=(CommonUtil.isNullObject(rdVoucherHistory.get("startCode"))?0:Integer.parseInt(rdVoucherHistory.get("startCode").toString()))+1;
		
		String rdOutBillCode="000000000"+rdVoucherId;
		rdOutBillCode=rdOutBillCode.substring(rdOutBillCode.length()-Integer.parseInt(rule.get("serialnolength").toString()), rdOutBillCode.length());
		
		sbf.append(rdOutBillCode);
		return sbf.toString();
	}
	
	
}
