package com.emi.web.u890.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.apache.poi.ss.formula.functions.Now;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import com.emi.cache.service.CacheCtrlService;
import com.emi.common.service.EmiPluginService;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.DateUtil;
import com.emi.sys.core.format.EmiJsonArray;
import com.emi.sys.core.format.EmiJsonObj;
import com.emi.sys.init.Config;
import com.emi.web.u890.U890xml;
import com.emi.web.u890.bean.BasPart;
import com.emi.web.u890.bean.BomBom;
import com.emi.web.u890.bean.BomOpcomponent;
import com.emi.web.u890.bean.BomParent;
import com.emi.web.u890.bean.CurrentStock;
import com.emi.web.u890.bean.InvPosition;
import com.emi.web.u890.bean.RdRecord;
import com.emi.web.u890.bean.RdRecords;
import com.emi.web.u890.bean.StoreInBodyU8;
import com.emi.web.u890.bean.StoreInHeadU8;
import com.emi.web.u890.bean.StoreOutBodyU8;
import com.emi.web.u890.bean.StoreOutHeadU8;
import com.emi.web.u890.bean.TransVouch;
import com.emi.web.u890.bean.TransVouchs;
import com.emi.web.u890.dao.DaoU890;
import com.emi.webservice.YonYouUtil;
import com.emi.wms.bean.AaDepartment;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaGoodsallocation;
import com.emi.wms.bean.AaWarehouse;
import com.emi.wms.bean.WmAllocationstock;
import com.emi.wms.bean.YmUser;
import com.emi.wms.util.Constants;

public class ServiceU890  extends EmiPluginService {

	private CacheCtrlService cacheCtrlService;
	
	private DaoU890 daoU890;
	
	public void setCacheCtrlService(CacheCtrlService cacheCtrlService) {
		this.cacheCtrlService = cacheCtrlService;
	}

	public void setDaoU890(DaoU890 daoU890) {
		this.daoU890 = daoU890;
	}


	/**
	 * @category 添加入库单
	 *2016 2016年3月15日上午9:31:41
	 *String
	 *宋银海
	 */
	public boolean addPoWareHouse(JSONObject jobj) throws Exception{
		
		JSONArray jsonArray=jobj.getJSONArray("wmsGoodsLists");
		
		//从用友中查询到货单信息
		String condition=" pa.id='"+jobj.getString("mainTableIdentity")+"'";
		List<Map> maps=daoU890.getProcurearrivalCYonYou(condition);
		
		Set<String> warehouse=new HashSet<String>();
		for(Object obj:jsonArray){
			JSONObject prowctJson = (JSONObject) obj;
			AaGoodsallocation aaGoodsallocation=cacheCtrlService.getGoodsAllocation(prowctJson.getString("goodsAllocationUid"));//货位信息
			warehouse.add(aaGoodsallocation.getWhcode());
		}
		
		
		Iterator<String> it=warehouse.iterator();
		String requestXml="";
		
		List<StoreInHeadU8> storeInHeads=new ArrayList<StoreInHeadU8>();
		List<StoreInBodyU8> storeInBodys=new ArrayList<StoreInBodyU8>();
		
		while(it.hasNext()){
			String ckCode=it.next();
			String chilid="";
			for(Object obj:jsonArray){
				JSONObject prowctJson = (JSONObject) obj;
				
				AaGoodsallocation aaGoodsallocation=cacheCtrlService.getGoodsAllocation(prowctJson.getString("goodsAllocationUid"));//货位信息
				AaWarehouse	aaWarehouse=cacheCtrlService.getWareHouse(aaGoodsallocation.getWhuid());
				
				if(ckCode.equalsIgnoreCase(aaWarehouse.getWhcode())){
					
					AaGoods aaGoods=cacheCtrlService.getGoods(prowctJson.getString("goodsUid"));
					StoreInBodyU8 storeInBody=new StoreInBodyU8();
					storeInBody.setInventorycode(CommonUtil.isNullObject(prowctJson.get("goodsCode"))?"":prowctJson.get("goodsCode").toString());
					storeInBody.setShouldquantity(CommonUtil.isNullObject(prowctJson.get("submitNum"))?"":prowctJson.get("submitNum").toString());
					storeInBody.setQuantity(CommonUtil.isNullObject(prowctJson.get("submitNum"))?"":prowctJson.get("submitNum").toString());
					storeInBody.setCmassunitname(aaGoods.getUnitName());//
					storeInBody.setCsTComUnitCode(CommonUtil.Obj2String(aaGoods.getCstcomunitcode()));//库存辅计量单位编码
					storeInBody.setCsTComUnitName(CommonUtil.Obj2String(aaGoods.getCstComUnitName()));//库存辅计量单位名称
					if(!CommonUtil.isNullObject(aaGoods.getCstcomunitcode())){
						storeInBody.setNumber(CommonUtil.isNullObject(prowctJson.get("submitQuantity"))?"":prowctJson.get("submitQuantity").toString());//辅数量
						storeInBody.setIrate(Double.toString(prowctJson.getDouble("submitNum")/prowctJson.getDouble("submitQuantity")));//换算率
					}else{
						storeInBody.setNumber("");
						storeInBody.setIrate("");//换算率
					}

					storeInBody.setDefine30(CommonUtil.isNullObject(prowctJson.get("gid"))?"":prowctJson.get("gid").toString());
					
					if(aaWarehouse.getWhpos().intValue()==1){
						storeInBody.setPosition(aaGoodsallocation.getCode());
					}else{
						storeInBody.setPosition("");
					}
					
					for(Map map:maps){
						if(prowctJson.getString("childTableIdentity").equalsIgnoreCase(map.get("autoid").toString())){
							
							storeInBody.setCbatchProperty7(CommonUtil.isNullObject(map.get("cBatchProperty7"))?null:map.get("cBatchProperty7").toString());
							storeInBody.setCbatchProperty8(CommonUtil.isNullObject(map.get("cBatchProperty8"))?null:map.get("cBatchProperty8").toString());
							storeInBody.setCbatchProperty9(CommonUtil.isNullObject(map.get("cBatchProperty9"))?null:map.get("cBatchProperty9").toString());
							storeInBody.setCbatchProperty10(CommonUtil.isNullObject(map.get("cBatchProperty10"))?null:map.get("cBatchProperty10").toString());
							
							storeInBody.setIoriCost(CommonUtil.isNullObject(map.get("iOriCost"))?BigDecimal.valueOf(0):BigDecimal.valueOf(Double.valueOf(map.get("iOriCost").toString())) ); //原币无税单价  
							storeInBody.setIoriTaxCost(CommonUtil.isNullObject(map.get("iOriTaxCost"))?BigDecimal.valueOf(0):BigDecimal.valueOf(Double.valueOf(map.get("iOriTaxCost").toString())) ); //原币含税单价   
							storeInBody.setIoriMoney(CommonUtil.isNullObject(map.get("iOriCost"))?BigDecimal.valueOf(0):BigDecimal.valueOf(  Double.valueOf(map.get("iOriCost").toString())*prowctJson.getDouble("submitNum") )); //原币无税金额  
							storeInBody.setIoriSum(CommonUtil.isNullObject(map.get("iOriTaxCost"))?BigDecimal.valueOf(0):BigDecimal.valueOf( Double.valueOf(map.get("iOriTaxCost").toString())*prowctJson.getDouble("submitNum") ));  //原币价税合计
							storeInBody.setIoriTaxPrice(BigDecimal.valueOf(storeInBody.getIoriSum().doubleValue()-storeInBody.getIoriMoney().doubleValue()) );   //原币税额  
							
							storeInBody.setIcost(CommonUtil.isNullObject(map.get("iCost"))?BigDecimal.valueOf(0):BigDecimal.valueOf(Double.valueOf(map.get("iCost").toString()))); //本币无税单价                                                                  
							storeInBody.setImoney(CommonUtil.isNullObject(map.get("iCost"))?BigDecimal.valueOf(0):BigDecimal.valueOf( Double.valueOf(map.get("iCost").toString())*prowctJson.getDouble("submitNum") ));//本币无税金额           
							storeInBody.setIsum(storeInBody.getIoriSum()); //本币价税合计  
							storeInBody.setItaxPrice(storeInBody.getIoriTaxPrice());      //本币税额
							
							storeInBody.setItaxrate(CommonUtil.isNullObject(map.get("itaxrate"))?BigDecimal.valueOf(0):BigDecimal.valueOf(Double.valueOf(map.get("itaxrate").toString())) );//税率
							storeInBody.setDefine23(CommonUtil.isNullObject(map.get("pasDefine23"))?"":map.get("pasDefine23").toString());
							storeInBody.setBatch(CommonUtil.isNullObject(prowctJson.get("batch"))?"":prowctJson.get("batch").toString());
							
							
							//更新采购入库
							storeInBody.setIpOsID(CommonUtil.isNullObject(map.get("iposid"))?null:map.get("iposid").toString());
							storeInBody.setInQuantity(CommonUtil.isNullObject(map.get("stillQuantity"))?null:map.get("stillQuantity").toString());
							storeInBody.setBtaxCost(CommonUtil.isNullObject(map.get("btaxCost"))?null:map.get("btaxCost").toString());
							storeInBody.setCpOID(CommonUtil.isNullObject(map.get("corderCode"))?null:map.get("corderCode").toString());
							storeInBody.setCbarvcode(CommonUtil.isNullObject(map.get("cCode"))?null:map.get("cCode").toString());
							storeInBody.setDbarvdate(CommonUtil.isNullObject(map.get("dDate"))?null:map.get("dDate").toString().substring(0, 10));
							
							
							//有效期管理
							if((CommonUtil.isNullObject(prowctJson.get("isInvQuality"))?0:prowctJson.getInt("isInvQuality"))==1  ){
								if(prowctJson.getDouble("submitNum")<0){//退库
									if(!CommonUtil.isNullObject(map.get("dPDate"))){
										storeInBody.setMakedate(map.get("dPDate").toString().substring(0, 10));//生产日期
										storeInBody.setValiddate(map.get("dVDate").toString().substring(0, 10));//失效日期
										
									}else{
										
										condition=" cinvcode='"+storeInBody.getInventorycode()+"' and cwhcode='"+ckCode+"' and cbatch='"+storeInBody.getBatch()+"'";
										if(!CommonUtil.isNullObject(storeInBody.getCfree1())){
											condition=condition+" and cfree1='"+storeInBody.getCfree1()+"'";
										}
										if(!CommonUtil.isNullObject(storeInBody.getCfree2())){
											condition=condition+" and cfree2='"+storeInBody.getCfree2()+"'";
										}
										List<CurrentStock> cs=daoU890.getAllocationStock(condition);
										if(cs.size()==1){
											storeInBody.setMakedate(CommonUtil.isNullObject(cs.get(0).getDmDate())?null:cs.get(0).getDmDate().toString().substring(0, 10));
											storeInBody.setValiddate(CommonUtil.isNullObject(cs.get(0).getDvDate())?null:cs.get(0).getDvDate().toString().substring(0, 10));
										}else{
											System.out.println("存在多个有效期的库存");
											return false;
										}
									}
									
									storeInBody.setImassDate(prowctJson.getString("massDate"));//有效期天数
									storeInBody.setMassunit("3");//有效期单位  3表示天
									
								}else{
									
									if(!CommonUtil.isNullObject(map.get("dPDate"))){
										storeInBody.setMakedate(map.get("dPDate").toString().substring(0, 10));//生产日期
										storeInBody.setValiddate(map.get("dVDate").toString().substring(0, 10));//失效日期
										
									}else{
										storeInBody.setMakedate(DateUtil.dateToString(new Date(), DateUtil.LONG_DATE_FORMAT));
										String massDate=DateUtil.nextDay(prowctJson.getInt("massDate"), DateUtil.LONG_DATE_FORMAT);
										storeInBody.setValiddate(massDate);
									}
									
									storeInBody.setImassDate(prowctJson.getString("massDate"));//有效期天数
									storeInBody.setMassunit("3");//有效期单位  3表示天
								}
							}
							
							break;
						}
					}
					
					JSONArray cfreeArray=prowctJson.getJSONArray("cfree");
					for(int i=0;i<cfreeArray.size();i++){//后续再完善
						JSONObject cf = (JSONObject)cfreeArray.get(i);
						if(cf.getString("colName").equalsIgnoreCase("cfree1")){
							storeInBody.setCfree1(CommonUtil.isNullObject(cf.get("value"))?"":cf.get("value").toString());
						}else if(cf.getString("colName").equalsIgnoreCase("cfree2")){
							storeInBody.setCfree2(CommonUtil.isNullObject(cf.get("value"))?"":cf.get("value").toString());
						}
					}
					
					
					chilid=chilid+U890xml.getStoreInBodyXml(storeInBody);
					
					
					storeInBody.setIarrsId(prowctJson.getString("childTableIdentity"));
					storeInBodys.add(storeInBody);
				}
			}
			
			YmUser ymUser=cacheCtrlService.getUser(jobj.getString("userGid"));//用户信息
			String xml="";
			StoreInHeadU8 storeInHead=new StoreInHeadU8();
			storeInHead.setVouchtype("01");
			storeInHead.setBusinesstype("普通采购");
			storeInHead.setWarehousecode(ckCode);
			storeInHead.setDate(DateUtil.dateToString(new Date(), DateUtil.LONG_DATE_FORMAT));
			storeInHead.setReceivecode(CommonUtil.isNullObject(jobj.get("rdStyle"))?"":jobj.get("rdStyle").toString());//入库类别
			
			storeInHead.setTemplatenumber("27");
			storeInHead.setMaker(ymUser.getUserName());
			storeInHead.setDefine10(UUID.randomUUID().toString());
			storeInHead.setProviderCode(maps.get(0).get("cvenCode").toString());
			storeInHead.setIpurarriveid(maps.get(0).get("id").toString());
			storeInHead.setCarVCode(maps.get(0).get("cCode").toString());
			
			//业务员
			storeInHead.setDepartmentcode(CommonUtil.Obj2String(maps.get(0).get("cDepCode")));//部门
			storeInHead.setPurchasecode(CommonUtil.isNullObject(maps.get(0).get("cPTCode"))?null:maps.get(0).get("cPTCode").toString());//采购类型
			storeInHead.setPersoncode(CommonUtil.Obj2String(maps.get(0).get("cPersonCode")));//业务员
			
			storeInHead.setCorderCode(CommonUtil.isNullObject(maps.get(0).get("corderCode"))?null:maps.get(0).get("corderCode").toString());
			storeInHead.setCmemo(CommonUtil.isNullObject(maps.get(0).get("cMemo"))?null:maps.get(0).get("cMemo").toString() );
			storeInHead.setDaRVDate(maps.get(0).get("dDate").toString());
			storeInHead.setCexchName(CommonUtil.isNullObject(maps.get(0).get("cExch_Name"))?null:maps.get(0).get("cExch_Name").toString());
			storeInHead.setItaxRate(CommonUtil.isNullObject(maps.get(0).get("mainitaxrate"))?null:maps.get(0).get("mainitaxrate").toString());
			
			
			storeInHeads.add(storeInHead);
			
			xml=U890xml.getStoreInHeadXml(storeInHead);
			xml=U890xml.connectHeadBodyIn(xml, chilid);//拼接字符串
			requestXml=requestXml+xml;
		}
		
		requestXml=U890xml.connectHeadBodyIn(requestXml);
		System.out.println(requestXml);
		String succeedInfor=YonYouUtil.eaiWriteInfor(requestXml);
		 
		if(!succeedInfor.equalsIgnoreCase("0")){
			return false;
		}
		
		if(succeedInfor.equalsIgnoreCase("0")){
			updateSourceWhenProcureArrivalIn(storeInHeads, storeInBodys);//回填上游单据
			
		}
		
		return true;
	}
	
	
	//采购入库完善以及回填上游相关单据
	public boolean updateSourceWhenProcureArrivalIn(List<StoreInHeadU8> storeInHeads,List<StoreInBodyU8> storeInBodys){
		daoU890.updateRdrecordProcureArrival(storeInHeads);
		daoU890.updateRdrecordsProcureArrival(storeInBodys);
		daoU890.updatePuAppVouchs(storeInBodys);
		return true;
	}
	
	
	
	
	
	/**
	 * @category 添加销售出库(通过xml 插库)  不建议使用，如果存在多个仓库，事务不会回滚
	 *2016 2016年4月22日下午1:01:30
	 *boolean
	 *宋银海
	 */
	public boolean addSaleOutXml(JSONObject jobj) throws Exception {
		
		JSONArray jsonArray=jobj.getJSONArray("wmsGoodsLists");

		//从用友中查询到货单信息
		String condition="  dl.dlid='"+jobj.getString("mainTableIdentity")+"'";
		List<Map> maps=daoU890.getSaleSendCYonYou(condition);
		
		boolean isBack=false;//是否是退库
		
		String customerCode=maps.get(0).get("cCusCode").toString();
		
		Set<String> warehouse=new HashSet<String>();
		for(Object obj:jsonArray){
			JSONObject prowctJson = (JSONObject) obj;
			AaGoodsallocation aaGoodsallocation=cacheCtrlService.getGoodsAllocation(prowctJson.getString("goodsAllocationUid"));//货位信息
			warehouse.add(aaGoodsallocation.getWhcode());
		}
		
		Iterator<String> it=warehouse.iterator();
		String requestXml="";
		List<StoreOutBodyU8> storeOutBodyU8=new ArrayList<StoreOutBodyU8>();
		List<StoreOutHeadU8> storeOutHeads=new ArrayList<StoreOutHeadU8>();
		List<CurrentStock> css=new ArrayList<CurrentStock>();
		while(it.hasNext()){
			String ckCode=it.next();
			String chilid="";
			
			String iarriveid="";
			String cbusCode="";
			String cdlCode="";
			
			for(Object obj:jsonArray){
				JSONObject prowctJson = (JSONObject) obj;
				AaGoodsallocation aaGoodsallocation=cacheCtrlService.getGoodsAllocation(prowctJson.getString("goodsAllocationUid"));//货位信息
				AaWarehouse	aaWarehouse=cacheCtrlService.getWareHouse(aaGoodsallocation.getWhuid());
				
				if(ckCode.equalsIgnoreCase(aaWarehouse.getWhcode())){
					AaGoods aaGoods=cacheCtrlService.getGoods(prowctJson.getString("goodsUid"));
					
					StoreOutBodyU8 storeOutBody=new StoreOutBodyU8();
					storeOutBody.setInventorycode(CommonUtil.isNullObject(prowctJson.get("goodsCode"))?"":prowctJson.get("goodsCode").toString());
					storeOutBody.setShouldquantity(CommonUtil.isNullObject(prowctJson.get("submitNum"))?"":prowctJson.get("submitNum").toString());
					storeOutBody.setQuantity(CommonUtil.isNullObject(prowctJson.get("submitNum"))?"":prowctJson.get("submitNum").toString());
					
					if(prowctJson.getDouble("submitNum")<0){
						isBack=true;
					}
					
					storeOutBody.setCmassunitname(aaGoods.getUnitName());//
					storeOutBody.setCsTComUnitCode(aaGoods.getCstcomunitcode());//库存辅计量单位编码
					storeOutBody.setCsTComUnitName(aaGoods.getCstcomunitcode());//库存辅计量单位名称
					if(!CommonUtil.isNullObject(aaGoods.getCasscomunitcode())){
						storeOutBody.setNumber(CommonUtil.isNullObject(prowctJson.get("submitQuantity"))?"":prowctJson.get("submitQuantity").toString());//辅数量
						storeOutBody.setIrate(Double.toString(prowctJson.getDouble("submitNum")/prowctJson.getDouble("submitQuantity")));//换算率
					}else{
						storeOutBody.setNumber("");
						storeOutBody.setIrate("");//换算率
					}
	
					storeOutBody.setDefine30(CommonUtil.isNullObject(prowctJson.get("gid"))?"":prowctJson.get("gid").toString());
				
					if(aaWarehouse.getWhpos().intValue()==1 ){
						storeOutBody.setPosition(aaGoodsallocation.getCode());
					}else{
						storeOutBody.setPosition("");
					}
					
					storeOutBody.setBatch(CommonUtil.isNullObject(prowctJson.get("batch"))?"":prowctJson.get("batch").toString());
					
					for(Map map :maps){
						if(prowctJson.getString("childTableIdentity").equalsIgnoreCase(map.get("idLsID").toString())){
							if(iarriveid.equalsIgnoreCase("")){
								iarriveid=map.get("cDLCode").toString();
							}
							if(cbusCode.equalsIgnoreCase("")){
								cbusCode=map.get("cDLCode").toString();
							}
							if(cdlCode.equalsIgnoreCase("")){
								cdlCode=map.get("dlid").toString();
							}
							
							storeOutBody.setIdLsID(map.get("idLsID").toString());
							
							break;
						}
					} 
					
					JSONArray cfreeArray=prowctJson.getJSONArray("cfree");
					for(int i=0;i<cfreeArray.size();i++){//后续再完善
						JSONObject cf = (JSONObject)cfreeArray.get(i);
						if(cf.getString("colName").equalsIgnoreCase("cfree1")){
							storeOutBody.setCfree1(CommonUtil.isNullObject(cf.get("value"))?"":cf.get("value").toString());
						}else if(cf.getString("colName").equalsIgnoreCase("cfree2")){
							storeOutBody.setCfree2(CommonUtil.isNullObject(cf.get("value"))?"":cf.get("value").toString());
						}
					}
					
					storeOutBodyU8.add(storeOutBody);
					
					chilid=chilid+U890xml.getStoreOutBodyXml(storeOutBody);
					
					CurrentStock cs=new CurrentStock();
					cs.setCinvCode(storeOutBody.getInventorycode());
					cs.setCwhCode(ckCode);
					cs.setCbatch(storeOutBody.getBatch());
					cs.setCfree1(storeOutBody.getCfree1());
					cs.setCfree2(storeOutBody.getCfree2());
					if(isBack){//如果退库
						cs.setFinQuantity(BigDecimal.valueOf(Math.abs(Double.parseDouble(storeOutBody.getQuantity()))));
					}else{
						cs.setFoutQuantity(BigDecimal.valueOf(Math.abs(Double.parseDouble(storeOutBody.getQuantity()))));
					}
					
					// TODO cs.setFoutNum(foutNum);无换算 固定换算率 浮动换算率需要区分
					css.add(cs);
					
				} 
			}
			
			YmUser ymUser=cacheCtrlService.getUser(jobj.getString("userGid"));//用户信息
			
			String xml="";
			StoreOutHeadU8 storeOutHead=new StoreOutHeadU8();
			storeOutHead.setVouchtype("32");
			storeOutHead.setBusinesstype("普通销售");
			storeOutHead.setSource("发货单");
			storeOutHead.setWarehousecode(ckCode);
			storeOutHead.setDate(DateUtil.dateToString(new Date(), DateUtil.LONG_DATE_FORMAT));
			storeOutHead.setReceivecode(CommonUtil.isNullObject(jobj.get("rdStyle"))?"":jobj.get("rdStyle").toString());
			storeOutHead.setDepartmentcode("");
			storeOutHead.setCustomercode(customerCode);
			storeOutHead.setConsignmentcode(CommonUtil.isNullObject(jobj.get("consignmentcode"))?"":jobj.get("consignmentcode").toString());
			storeOutHead.setTemplatenumber("87");
			storeOutHead.setMaker(ymUser.getUserName());
			storeOutHead.setDefine10(UUID.randomUUID().toString());
			storeOutHead.setCshipAddress(CommonUtil.isNullObject(jobj.get("cshipAddress"))?"":jobj.get("cshipAddress").toString());
			storeOutHead.setCmemo(CommonUtil.isNullObject(jobj.get("memo"))?"":jobj.get("memo").toString());
			storeOutHead.setCpersonCode(CommonUtil.isNullObject(jobj.get("cpersonCode"))?"":jobj.get("cpersonCode").toString());
			
			storeOutHead.setIarriveid(iarriveid);
			storeOutHead.setCbusCode(cbusCode);
			storeOutHead.setCdlCode(cdlCode);
			
			xml=U890xml.getStoreOutHeadXml(storeOutHead);
			xml=U890xml.connectHeadBodyOut(xml, chilid);//拼接请求字符串
			requestXml=requestXml+xml;
			storeOutHeads.add(storeOutHead);
		}
		requestXml=U890xml.connectHeadBodyOut(requestXml);
		System.out.println(requestXml);
		String succeedInfor=YonYouUtil.eaiWriteInfor(requestXml);
		
		if(!succeedInfor.equalsIgnoreCase("0")){
			return false;//故意报错，事务回滚
		}
		
		if(succeedInfor.equalsIgnoreCase("0")){
			updateSourceWhenSaleOut(css,storeOutHeads, storeOutBodyU8);
		}
		
		return true;
	}
	
	
	//销售出库完善以及回填上游相关单据
	public boolean updateSourceWhenSaleOut(List<CurrentStock> css,List<StoreOutHeadU8> storeOutHead,List<StoreOutBodyU8> storeOutBodyU8){
		updateCurrentStock(css);//修改待发货数量
		daoU890.updateRdrecordSaleOut(storeOutHead);
		daoU890.updateRdrecordsSaleOut(storeOutBodyU8);
		daoU890.updateDispatchLists(storeOutBodyU8);
		daoU890.updateDispatchList(storeOutHead);//如果不执行，对应生成的出库单没法删除
		return true;
	}
	
	
	
	/**
	 * @category 添加销售出库(直接插库)
	 *2016 2016年4月22日下午1:01:30
	 *boolean
	 *宋银海
	 */
	public boolean addSaleOut(JSONObject jobj) throws Exception {
		
		JSONArray jsonArray=jobj.getJSONArray("wmsGoodsLists");
		
		boolean isBack=false;//是否是退库
		
		//从用友中查询到货单信息
		String condition="  dl.dlid='"+jobj.getString("mainTableIdentity")+"'";
		List<Map> maps=daoU890.getSaleSendCYonYou(condition);
		
		String customerCode=maps.get(0).get("cCusCode").toString();
		
		Set<String> warehouse=new HashSet<String>();
		for(Object obj:jsonArray){
			JSONObject prowctJson = (JSONObject) obj;
			AaGoodsallocation aaGoodsallocation=cacheCtrlService.getGoodsAllocation(prowctJson.getString("goodsAllocationUid"));//货位信息
			warehouse.add(aaGoodsallocation.getWhcode());
		}
		
		Iterator<String> it=warehouse.iterator();
		List<RdRecords> rdRecords=new ArrayList<RdRecords>();//出库单子表
		List<RdRecord> rdRecord=new ArrayList<RdRecord>();//出库单主表
		List<CurrentStock> css=new ArrayList<CurrentStock>();//现存量
		List<InvPosition> ips=new ArrayList<InvPosition>();//存货货位记录表
		
		String dataBaseName=Config.BUSINESSDATABASE;
		String[] strs=dataBaseName.split("_");
		
/*		会出现重复，暂时改掉
 * 		condition=" CardNumber='0303'  "; //提交销售出库时使用 0303
		Map rdVoucherHistory=daoU890.getVoucherHistory(condition);*/
		
		YmUser ymUser=cacheCtrlService.getUser(jobj.getString("userGid"));//用户信息
		
/*		会出现重复，暂时改掉
 * 		int i=1;//用来记录主表单号
		int rdOutVoucherId=0;*/
		
		while(it.hasNext()){
			String ckCode=it.next();
			
			/*会出现重复，暂时改掉 rdOutVoucherId=Integer.parseInt(rdVoucherHistory.get("cNumber").toString())+i;
			String rdOutBillCode=getbillCode("0303");//获得单据编号*/
		
			int k=0;//用来存储子表，存储过程步长
			
			List<RdRecords> tempRdRecords=new ArrayList<RdRecords>();
			
			for(Object obj:jsonArray){
				JSONObject prowctJson = (JSONObject) obj;
				AaGoodsallocation aaGoodsallocation=cacheCtrlService.getGoodsAllocation(prowctJson.getString("goodsAllocationUid"));//货位信息
				AaWarehouse	aaWarehouse=cacheCtrlService.getWareHouse(aaGoodsallocation.getWhuid());
				
				if(ckCode.equalsIgnoreCase(aaWarehouse.getWhcode())){
					AaGoods aaGoods=cacheCtrlService.getGoods(prowctJson.getString("goodsUid"));
					
					RdRecords rds=new RdRecords();
					rds.setCinvCode(CommonUtil.isNullObject(prowctJson.get("goodsCode"))?"":prowctJson.get("goodsCode").toString());
					rds.setIquantity(CommonUtil.str2BigDecimal(prowctJson.getString("submitNum")));//主数量
					
					if(rds.getIquantity().compareTo(new BigDecimal(0))==-1){//小于0
						isBack=true;
					}
					
					if(!CommonUtil.isNullObject(aaGoods.getCasscomunitcode())){
						rds.setInum(CommonUtil.str2BigDecimal(prowctJson.get("submitQuantity")));//辅数量
						
					}else{
						rds.setInum(null);
					}
					
					rds.setCdefine30(CommonUtil.isNullObject(prowctJson.get("gid"))?"":prowctJson.get("gid").toString());
				
					if(aaWarehouse.getWhpos().intValue()==1 ){
						rds.setCposition(aaGoodsallocation.getCode());
					}else{
						rds.setCposition("");
					}
					
					rds.setCbatch(CommonUtil.isNullObject(prowctJson.get("batch"))?"":prowctJson.get("batch").toString());
					
					for(Map map :maps){
						if(prowctJson.getString("childTableIdentity").equalsIgnoreCase(map.get("idLsID").toString())){
							
							rds.setIdLsID(map.get("idLsID").toString());
							rds.setCbdlcode(map.get("cDLCode").toString());
							
							//有效期管理
							if((CommonUtil.isNullObject(aaGoods.getIsInvQuality())?0:aaGoods.getIsInvQuality().intValue())==1  ){
								
								rds.setDmadeDate(CommonUtil.isNullObject(map.get("dMDate"))?null:Timestamp.valueOf(map.get("dMDate").toString()));
								rds.setDvdate(CommonUtil.isNullObject(map.get("dvdate"))?null:Timestamp.valueOf(map.get("dvdate").toString()));
								rds.setImassDate(CommonUtil.isNullObject(map.get("iMassDate"))?null:Integer.valueOf(map.get("iMassDate").toString()));
								rds.setCmassUnit(3);
							}
							
							break;
						}
					} 
					
					JSONArray cfreeArray=prowctJson.getJSONArray("cfree");
					for(int m=0;m<cfreeArray.size();m++){//后续再完善
						JSONObject cf = (JSONObject)cfreeArray.get(m);
						if(cf.getString("colName").equalsIgnoreCase("cfree1")){
							rds.setCfree1(CommonUtil.isNullObject(cf.get("value"))?"":cf.get("value").toString());
						}else if(cf.getString("colName").equalsIgnoreCase("cfree2")){
							rds.setCfree2(CommonUtil.isNullObject(cf.get("value"))?"":cf.get("value").toString());
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
					cs.setInum(CommonUtil.BigDecimal2BigDecimal(rds.getInum()).negate());//json中有正有负  这边取相反的数据
					
					if(isBack){//如果退库
						cs.setFinQuantity(rds.getIquantity().abs());
					}else{//正常出库
						cs.setFoutQuantity(rds.getIquantity().abs());
					}
					
					// TODO cs.setFoutNum(foutNum);无换算 固定换算率 浮动换算率需要区分
					
					if((CommonUtil.isNullObject(aaGoods.getIsInvQuality())?0:aaGoods.getIsInvQuality().intValue())==1){//有效期
						cs.setDmDate(rds.getDmadeDate());
						cs.setDvDate(rds.getDvdate());
						cs.setImassDate(rds.getImassDate());
						cs.setCmassUnit(3);
					}
					
					css.add(cs);
					
					k++;
				} 
			}
			
			int[] idOuts=daoU890.getBillId("", strs[1], "rd", k);//调用存储过程获得其他出库单主子表id
			
			int n=1;
			for(RdRecords rds:tempRdRecords){
				rds.setAutoID(100000000 +(idOuts[1]-k+n));
				rds.setId(100000000+idOuts[0]);
				n++;
				
				InvPosition ipout=new InvPosition();//存货货位记录表（出）
				if(!CommonUtil.isNullObject(rds.getCposition())){//出库仓库
					ipout.setRdID(rds.getId());
					ipout.setRdsID(rds.getAutoID());
					ipout.setCwhCode(ckCode);
					ipout.setCposCode(rds.getCposition());
					ipout.setCinvCode(rds.getCinvCode());
					ipout.setCbatch(rds.getCbatch());
					ipout.setCfree1(rds.getCfree1());
					ipout.setCfree2(rds.getCfree2());
					ipout.setIquantity(rds.getIquantity());
					ipout.setInum(rds.getInum());
					ipout.setChandler(ymUser.getUserName());
					ipout.setDdate(Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00"));
					ipout.setBrdFlag(0);//收发标志 0 发 1收
					
					ipout.setDvDate(rds.getDvdate());//有效期
					ipout.setDmadeDate(rds.getDmadeDate());
					ipout.setImassDate(rds.getImassDate());
					ipout.setCmassUnit(rds.getCmassUnit());
					
					ips.add(ipout);
				}
				
			}
			
			RdRecord rdo=new RdRecord();
			rdo.setId(100000000+idOuts[0]);
			rdo.setBrdFlag(0);//收发标志   0 发 1收
			rdo.setCvouchType("32");
			rdo.setCbusType("普通销售");
			rdo.setCsource("发货单");
			rdo.setCwhCode(ckCode);
			rdo.setDdate(Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00"));
			rdo.setCcode(getBillIdForYonYou(Constants.EMXSCK));
			rdo.setCmaker(ymUser.getUserName());
			rdo.setcHandler(ymUser.getUserName());
			rdo.setDveriDate(Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00"));
			rdo.setCdefine10(UUID.randomUUID().toString());
//			rdo.setCvenCode(maps.get(0).get("cvenCode").toString());
			rdo.setCcusCode(customerCode);
			rdo.setVtid(87);
			rdo.setCbusCode(maps.get(0).get("cDLCode").toString());//对应业务单号  销售出库时对应销售发货单号
			rdo.setCdLCode(maps.get(0).get("dlid").toString() );//发货退货单主表标识 
			rdo.setIarriveid(maps.get(0).get("cDLCode").toString());//发货退货单号 
			rdo.setCrdCode(jobj.getString("rdStyle"));
			rdRecord.add(rdo);
			
			/*i++;*/
			
		}
		
		/*daoU890.uptVoucherHistory(String.valueOf(rdOutVoucherId), "0303");//修改其它入出库编号记录
*/		
		daoU890.addRdRecord(rdRecord);/////////////////////////////////////////////////////////////////////////添加其他出入库单主表
		daoU890.addRdRecords(rdRecords);///////////////////////////////////////////////////////////////////添加其他出入库单子表
		
		daoU890.addInvPosition(ips);///////////////////////////////////////////////////////////////////////添加存货货位记录表
		
		List<CurrentStock> csnew=getNewCurrentStockList(css);
		updCurrentStock(csnew);///////////////////////////////////////////////////////////////////////////修改仓库现存量
		
		updateSourceBillWhenSubmitWareHouse(rdRecord,rdRecords, Constants.TASKTYPE_XSCK,csnew);///////////////////////////////回填发货单已领料数量
		
		return true;
	}
	
	
	
	/**
	 * @category 添加材料出库单（通过xml插库）
	 *2016 2016年3月15日上午9:32:20
	 *String
	 *宋银海
	 */
	public boolean addProcessMaterialOutXml(JSONObject jobj) throws Exception{
		
		JSONArray jsonArray=jobj.getJSONArray("wmsGoodsLists");

		//从用友中查询订单信息
		String condition="  mod.modid='"+jobj.getString("mainTableIdentity")+"'";
		List<Map> maps=daoU890.getMomOrderCYonYou(condition);
		
		Set<String> warehouse=new HashSet<String>();
		for(Object obj:jsonArray){
			JSONObject prowctJson = (JSONObject) obj;
			AaGoodsallocation aaGoodsallocation=cacheCtrlService.getGoodsAllocation(prowctJson.getString("goodsAllocationUid"));//货位信息
			warehouse.add(aaGoodsallocation.getWhcode());
		}
		
		Iterator<String> it=warehouse.iterator();
		String requestXml="";
		List<StoreOutBodyU8> storeOutBodyU8=new ArrayList<StoreOutBodyU8>();
		List<StoreOutHeadU8> storeOutHeads=new ArrayList<StoreOutHeadU8>();
		List<CurrentStock> css=new ArrayList<CurrentStock>();
		while(it.hasNext()){
			String ckCode=it.next();
			String chilid="";
			
			for(Object obj:jsonArray){
				JSONObject prowctJson = (JSONObject) obj;
				AaGoodsallocation aaGoodsallocation=cacheCtrlService.getGoodsAllocation(prowctJson.getString("goodsAllocationUid"));//货位信息
				AaWarehouse	aaWarehouse=cacheCtrlService.getWareHouse(aaGoodsallocation.getWhuid());
				
				if(ckCode.equalsIgnoreCase(aaWarehouse.getWhcode())){
					AaGoods aaGoods=cacheCtrlService.getGoods(prowctJson.getString("goodsUid"));
					
					StoreOutBodyU8 storeOutBody=new StoreOutBodyU8();
					storeOutBody.setInventorycode(CommonUtil.isNullObject(prowctJson.get("goodsCode"))?"":prowctJson.get("goodsCode").toString());
					storeOutBody.setShouldquantity(CommonUtil.isNullObject(prowctJson.get("submitNum"))?"":prowctJson.get("submitNum").toString());
					storeOutBody.setQuantity(CommonUtil.isNullObject(prowctJson.get("submitNum"))?"":prowctJson.get("submitNum").toString());
					storeOutBody.setCmassunitname(aaGoods.getUnitName());//
					storeOutBody.setCsTComUnitCode(aaGoods.getCstcomunitcode());//库存辅计量单位编码
					storeOutBody.setCsTComUnitName(aaGoods.getCstcomunitcode());//库存辅计量单位名称
					if(!CommonUtil.isNullObject(aaGoods.getCasscomunitcode())){
						storeOutBody.setNumber(CommonUtil.isNullObject(prowctJson.get("submitQuantity"))?"":prowctJson.get("submitQuantity").toString());//辅数量
						storeOutBody.setIrate(Double.toString(prowctJson.getDouble("submitNum")/prowctJson.getDouble("submitQuantity")));//换算率
					}else{
						storeOutBody.setNumber("");
						storeOutBody.setIrate("");//换算率
					}
	
					storeOutBody.setDefine30(CommonUtil.isNullObject(prowctJson.get("gid"))?"":prowctJson.get("gid").toString());
				
					if(aaWarehouse.getWhpos().intValue()==1 ){
						storeOutBody.setPosition(aaGoodsallocation.getCode());
					}else{
						storeOutBody.setPosition("");
					}
					
					storeOutBody.setBatch(CommonUtil.isNullObject(prowctJson.get("batch"))?"":prowctJson.get("batch").toString());
					
					for(Map map :maps){
						if(aaGoods.getGoodscode().equalsIgnoreCase(map.get("invCode").toString())){
							storeOutBody.setImPoIds(map.get("allocateId").toString());
							break;
						}
					} 
					
					JSONArray cfreeArray=prowctJson.getJSONArray("cfree");
					for(int i=0;i<cfreeArray.size();i++){//后续再完善
						JSONObject cf = (JSONObject)cfreeArray.get(i);
						if(cf.getString("colName").equalsIgnoreCase("cfree1")){
							storeOutBody.setCfree1(CommonUtil.isNullObject(cf.get("value"))?"":cf.get("value").toString());
						}else if(cf.getString("colName").equalsIgnoreCase("cfree2")){
							storeOutBody.setCfree2(CommonUtil.isNullObject(cf.get("value"))?"":cf.get("value").toString());
						}
					}
					storeOutBodyU8.add(storeOutBody);
					
					CurrentStock cs=new CurrentStock();
					cs.setCinvCode(storeOutBody.getInventorycode());
					cs.setCwhCode(ckCode);
					cs.setCbatch(storeOutBody.getBatch());
					cs.setCfree1(storeOutBody.getCfree1());
					cs.setCfree2(storeOutBody.getCfree2());
					cs.setFoutQuantity(BigDecimal.valueOf(Double.parseDouble(storeOutBody.getQuantity())));
					// TODO cs.setFoutNum(foutNum);无换算 固定换算率 浮动换算率需要区分
					css.add(cs);
					
					chilid=chilid+U890xml.getStoreOutBodyXml(storeOutBody);
				} 
			}
			
			YmUser ymUser=cacheCtrlService.getUser(jobj.getString("userGid"));//用户信息
			
			String xml="";
			StoreOutHeadU8 storeOutHead=new StoreOutHeadU8();
			storeOutHead.setVouchtype("11");
			storeOutHead.setBusinesstype("领料");
			storeOutHead.setSource("生产订单");
			storeOutHead.setWarehousecode(ckCode);
			storeOutHead.setDate(DateUtil.dateToString(new Date(), DateUtil.LONG_DATE_FORMAT));
			storeOutHead.setReceivecode(CommonUtil.isNullObject(jobj.get("rdStyle"))?"":jobj.get("rdStyle").toString());
			storeOutHead.setDepartmentcode("");
			storeOutHead.setCustomercode("");
			storeOutHead.setConsignmentcode(CommonUtil.isNullObject(jobj.get("consignmentcode"))?"":jobj.get("consignmentcode").toString());
			storeOutHead.setTemplatenumber("65");
			storeOutHead.setMaker(ymUser.getUserName());
			storeOutHead.setDefine10(UUID.randomUUID().toString());
			storeOutHead.setCshipAddress(CommonUtil.isNullObject(jobj.get("cshipAddress"))?"":jobj.get("cshipAddress").toString());
			storeOutHead.setCmemo(CommonUtil.isNullObject(jobj.get("memo"))?"":jobj.get("memo").toString());
			storeOutHead.setCpersonCode(CommonUtil.isNullObject(jobj.get("cpersonCode"))?"":jobj.get("cpersonCode").toString());
			
			storeOutHead.setIproorderid(maps.get(0).get("moid").toString());
			storeOutHead.setCmPoCode(maps.get(0).get("moCode").toString());
			
			xml=U890xml.getStoreOutHeadXml(storeOutHead);
			xml=U890xml.connectHeadBodyOut(xml, chilid);//拼接请求字符串
			requestXml=requestXml+xml;
			storeOutHeads.add(storeOutHead);
		}
		requestXml=U890xml.connectHeadBodyOut(requestXml);
		System.out.println(requestXml);
		String succeedInfor=YonYouUtil.eaiWriteInfor(requestXml);
		
		if(!succeedInfor.equalsIgnoreCase("0")){
			return false;//故意报错，事务回滚
		}
		
		if(succeedInfor.equalsIgnoreCase("0")){
			updateSourceWhenProcessMaterialOut(css,storeOutHeads, storeOutBodyU8);
		}
		
		return true;
	}
	
	
	//工序材料出库完善以及回填上游相关单据
	public boolean updateSourceWhenProcessMaterialOut(List<CurrentStock> css,List<StoreOutHeadU8> storeOutHead,List<StoreOutBodyU8> storeOutBodyU8){
		daoU890.updateRdrecordScc(storeOutHead);
		daoU890.updateRdrecordsScc(storeOutBodyU8);
		daoU890.updateMoallocate(storeOutBodyU8);
		return true;
	}
	
	
	
	
	/**
	 * @category 添加材料出库单（直接操作库）
	 *2016 2016年3月15日上午9:32:20
	 *String
	 *宋银海
	 */
	public boolean addProcessMaterialOut(JSONObject jobj) throws Exception{
		
		JSONArray jsonArray=jobj.getJSONArray("wmsGoodsLists");
		
		boolean isBack=jobj.getBoolean("isBack");//退料

		//从用友中查询订单信息
		String condition="  mod.modid='"+jobj.getString("mainTableIdentity")+"'";
		List<Map> maps=daoU890.getMomOrderCYonYou(condition);
		
		Set<String> warehouse=new HashSet<String>();
		for(Object obj:jsonArray){
			JSONObject prowctJson = (JSONObject) obj;
			AaGoodsallocation aaGoodsallocation=cacheCtrlService.getGoodsAllocation(prowctJson.getString("goodsAllocationUid"));//货位信息
			warehouse.add(aaGoodsallocation.getWhcode());
		}
		
		Iterator<String> it=warehouse.iterator();
		List<RdRecords> rdRecords=new ArrayList<RdRecords>();//出库单子表
		List<RdRecord> rdRecord=new ArrayList<RdRecord>();//出库单主表
		List<CurrentStock> css=new ArrayList<CurrentStock>();//现存量
		List<InvPosition> ips=new ArrayList<InvPosition>();//存货货位记录表
		
		String dataBaseName=Config.BUSINESSDATABASE;
		String[] strs=dataBaseName.split("_");
		
		/*condition=" CardNumber='0412'  "; //提交材料出库时使用 0412
		Map rdVoucherHistory=daoU890.getVoucherHistory(condition);*/
		
		YmUser ymUser=cacheCtrlService.getUser(jobj.getString("userGid"));//用户信息
		
		AaDepartment ad=cacheCtrlService.getDepartment(jobj.getString("dptGid"));//部门信息
		
/*		int i=1;//用来记录主表单号
		int rdOutVoucherId=0;*/
		
		
		
		while(it.hasNext()){
			String ckCode=it.next();
			int bvmiUsed=0;//代管消耗标识
			
			/*rdOutVoucherId=Integer.parseInt(rdVoucherHistory.get("cNumber").toString())+i;
			String rdOutBillCode=getbillCode("0412");*/
			
			int k=0;//用来存储子表，存储过程步长
			
			List<RdRecords> tempRdRecords=new ArrayList<RdRecords>();
			
			for(Object obj:jsonArray){
				JSONObject prowctJson = (JSONObject) obj;
				AaGoodsallocation aaGoodsallocation=cacheCtrlService.getGoodsAllocation(prowctJson.getString("goodsAllocationUid"));//货位信息
				AaWarehouse	aaWarehouse=cacheCtrlService.getWareHouse(aaGoodsallocation.getWhuid());
				if(aaWarehouse.getBproxyWh().intValue()==1){
					bvmiUsed=1;
				}
				
				if(ckCode.equalsIgnoreCase(aaWarehouse.getWhcode())){
					AaGoods aaGoods=cacheCtrlService.getGoods(prowctJson.getString("goodsUid"));
					
					RdRecords rds=new RdRecords();
					rds.setBvmiUsed(Integer.valueOf(bvmiUsed));
					if(bvmiUsed==1){
						rds.setCvmivencode(CommonUtil.Obj2String(prowctJson.get("cvMIVenCode")));//代管商编码
					}
					rds.setCinvCode(CommonUtil.isNullObject(prowctJson.get("goodsCode"))?"":prowctJson.get("goodsCode").toString());//所领材料
					
					rds.setInvcode(maps.get(0).get("modInvCode").toString());//对应的生产订单产品编码
					
					if(isBack){
						rds.setIquantity(CommonUtil.str2BigDecimal(prowctJson.getString("submitNum")).negate());//主数量
					}else{
						rds.setIquantity(CommonUtil.str2BigDecimal(prowctJson.getString("submitNum")));//主数量
					}
					
					if(!CommonUtil.isNullObject(aaGoods.getCasscomunitcode())){
						if(isBack){
							rds.setInum(CommonUtil.str2BigDecimal(prowctJson.get("submitQuantity")).negate());//辅数量
						}else{
							rds.setInum(CommonUtil.str2BigDecimal(prowctJson.get("submitQuantity")));//辅数量
						}
						
					}else{
						rds.setInum(null);
					}
					
					rds.setCdefine30(CommonUtil.isNullObject(prowctJson.get("gid"))?"":prowctJson.get("gid").toString());
				
					if(aaWarehouse.getWhpos().intValue()==1 ){
						rds.setCposition(aaGoodsallocation.getCode());
					}else{
						rds.setCposition("");
					}
					
					rds.setCbatch(CommonUtil.isNullObject(prowctJson.get("batch"))?"":prowctJson.get("batch").toString());
					
					JSONArray cfreeArray=prowctJson.getJSONArray("cfree");
					for(int m=0;m<cfreeArray.size();m++){//后续再完善
						JSONObject cf = (JSONObject)cfreeArray.get(m);
						if(cf.getString("colName").equalsIgnoreCase("cfree1")){
							rds.setCfree1(CommonUtil.isNullObject(cf.get("value"))?"":cf.get("value").toString());
						}else if(cf.getString("colName").equalsIgnoreCase("cfree2")){
							rds.setCfree2(CommonUtil.isNullObject(cf.get("value"))?"":cf.get("value").toString());
						}
					}
					
					//因为工艺路线中的物料无法跟u8中的物料做精确的比对，只能按照物料号+自由项匹配
					for(Map map :maps){
						if(aaGoods.getGoodscode().equalsIgnoreCase(map.get("invCode").toString()) ){
							if(CommonUtil.Obj2String(rds.getCfree1()).equalsIgnoreCase(CommonUtil.Obj2String(map.get("free1")))){//目前骆氏就涉及到1个，后续再完善
								rds.setImPoIds(map.get("allocateId").toString());
								rds.setCmocode(map.get("moCode").toString());
								rds.setImoseq(map.get("modsortSeq").toString());
								break;
							}
						}
					} 
					
					
					//有效期管理
					if((CommonUtil.isNullObject(aaGoods.getIsInvQuality())?0:aaGoods.getIsInvQuality().intValue())==1  ){
						
						if(isBack){//退料
							
							rds.setDmadeDate(CommonUtil.isNullObject(prowctJson.get("dmdate"))?null:Timestamp.valueOf(prowctJson.getString("dmdate")));
							rds.setDvdate(CommonUtil.isNullObject(prowctJson.get("dvdate"))?null:Timestamp.valueOf(prowctJson.getString("dvdate")));
							rds.setImassDate(Integer.valueOf(prowctJson.getString("massDate")));
							rds.setCmassUnit(3);
							
						}else{//正常领料
							condition=" cinvcode='"+rds.getCinvCode()+"' and cwhcode='"+ckCode+"' and cbatch='"+rds.getCbatch()+"'";
							if(!CommonUtil.isNullObject(rds.getCfree1())){
								condition=condition+" and cfree1='"+rds.getCfree1()+"'";
							}
							if(!CommonUtil.isNullObject(rds.getCfree2())){
								condition=condition+" and cfree2='"+rds.getCfree2()+"'";
							}
							List<CurrentStock> cs=daoU890.getAllocationStock(condition);
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
							
					}
					
					//称重管理
					if(CommonUtil.BigDecimal2BigDecimal(aaGoods.getInvWeight()).compareTo(new BigDecimal(0))==1){
						
						BigDecimal submitweight=CommonUtil.str2BigDecimal(prowctJson.get("submitweight"));//毛重
						BigDecimal grossWeight=CommonUtil.isNullObject(aaGoods.getGrossWeight())?new BigDecimal(0):aaGoods.getGrossWeight() ;//单个框子重
						BigDecimal minPackNum=CommonUtil.isNullObject(aaGoods.getMinPackNum())?new BigDecimal(1):aaGoods.getMinPackNum();//最小包装量
						
						BigDecimal allGrossNum=BigDecimal.valueOf(Math.ceil(rds.getIquantity().divide(minPackNum, 2, BigDecimal.ROUND_HALF_UP).doubleValue()));//所有框子个数 +1取整数
						BigDecimal allGrossWeight=allGrossNum.multiply(grossWeight);//所有框子重
						
						rds.setCdefine23(submitweight.subtract(allGrossWeight).toPlainString());
					
					}
					
					
					
					tempRdRecords.add(rds);
					rdRecords.add(rds);
					
					CurrentStock cs=new CurrentStock();//货位现存量表
					cs.setCinvCode(rds.getCinvCode());
					cs.setCwhCode(ckCode);
					cs.setCbatch(CommonUtil.Obj2String(rds.getCbatch()));
					cs.setCfree1(rds.getCfree1());
					cs.setCfree2(rds.getCfree2());
					cs.setIquantity(rds.getIquantity().negate());//退料时rds为负，cs需为正，货位现存量增加
					cs.setInum(CommonUtil.BigDecimal2BigDecimal(rds.getInum()).negate());
					cs.setDvDate(rds.getDvdate());/////////////////////////////////////////有效期
					cs.setDmDate(rds.getDmadeDate());
					cs.setImassDate(rds.getImassDate());
					cs.setCmassUnit(rds.getCmassUnit());
					cs.setCvmivencode(rds.getCvmivencode());
					
					// TODO cs.setFoutNum(foutNum);无换算 固定换算率 浮动换算率需要区分
					css.add(cs);
					
					k++;
				} 
			}
			
			int[] idOuts=daoU890.getBillId("", strs[1], "rd", k);//调用存储过程获得其他出库单主子表id
			
			int n=1;
			for(RdRecords rds:tempRdRecords){
				rds.setAutoID(100000000+(idOuts[1]-k+n));
				rds.setId(100000000+idOuts[0]);
				n++;
				
				InvPosition ipout=new InvPosition();//存货货位记录表（出）
				if(!CommonUtil.isNullObject(rds.getCposition())){//出库仓库
					ipout.setRdID(rds.getId());
					ipout.setRdsID(rds.getAutoID());
					ipout.setCwhCode(ckCode);
					ipout.setCposCode(rds.getCposition());
					ipout.setCinvCode(rds.getCinvCode());
					ipout.setCbatch(rds.getCbatch());
					ipout.setCfree1(rds.getCfree1());
					ipout.setCfree2(rds.getCfree2());
					ipout.setIquantity(rds.getIquantity());
					ipout.setInum(rds.getInum());
					ipout.setChandler(ymUser.getUserName());
					ipout.setDdate(Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00"));
					ipout.setDvDate(rds.getDvdate());//////////////////////有效期
					ipout.setDmadeDate(rds.getDmadeDate());
					ipout.setImassDate(rds.getImassDate());
					ipout.setCmassUnit(rds.getCmassUnit());
					ipout.setCvmivencode(rds.getCvmivencode());
					
					ipout.setBrdFlag(0);//收发标志 0 发 1收
					
					ips.add(ipout);
				}
				
			}
			
			RdRecord rdo=new RdRecord();
			rdo.setId(100000000+idOuts[0]);
			rdo.setBrdFlag(0);//收发标志   0 发 1收
			rdo.setCrdCode(jobj.getString("rdStyle"));	
			rdo.setCvouchType("11");
			rdo.setCbusType("领料");
			rdo.setCsource("生产订单");
			rdo.setCwhCode(ckCode);
			rdo.setDdate(Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00"));
			rdo.setDnmaketime(Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.FORMAT_ONE)));
			rdo.setCcode(getBillIdForYonYou(Constants.EMICLCK));
			rdo.setCmaker(ymUser.getUserName());
			rdo.setcHandler(ymUser.getUserName());
			rdo.setDveriDate(Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00"));
			rdo.setDnverifytime(rdo.getDnmaketime());
			rdo.setCdefine10(UUID.randomUUID().toString());
//			rdo.setCvenCode(maps.get(0).get("cvenCode").toString());
			rdo.setVtid(65);
			rdo.setCpsPcode(maps.get(0).get("modInvCode").toString());//产品编码
			rdo.setCmPoCode(maps.get(0).get("moCode").toString());//生产订单编号
			rdo.setIproorderid(maps.get(0).get("moid").toString());//生产订单主表标识  
			rdo.setIpurorderid(maps.get(0).get("moid").toString());//通过模拟数据 该字段记录生产订单主表标识
			rdo.setCrdCode(jobj.getString("rdStyle"));
			rdo.setCdepCode(ad.getDepcode());
			rdRecord.add(rdo);
			
			/*i++;*/
			
		}
		
		/*daoU890.uptVoucherHistory(String.valueOf(rdOutVoucherId), "0412");//修改其它入出库编号记录
*/		
		daoU890.addRdRecord(rdRecord);/////////////////////////////////////////////////////////////////////////添加其他出入库单主表
		daoU890.addRdRecords(rdRecords);///////////////////////////////////////////////////////////////////添加其他出入库单子表
		
		daoU890.addInvPosition(ips);///////////////////////////////////////////////////////////////////////添加存货货位记录表
		
		List<CurrentStock> csnew=getNewCurrentStockList(css);
		updCurrentStock(csnew);///////////////////////////////////////////////////////////////////////////修改仓库现存量
		
		updateSourceBillWhenSubmitWareHouse(null,rdRecords, Constants.TASKTYPE_GXTL,null);///////////////////////////////回填订单已领料数量
		
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
	
	
	/**
	 * @category 添加工序成品入库单
	 *2016 2016年3月15日上午9:31:41
	 *String
	 *宋银海
	 */
	public boolean addProductionWarehouse(JSONObject jobj) throws Exception{
		
		JSONArray jsonArray=jobj.getJSONArray("wmsGoodsLists");
		
		//从用友中查询生产订单信息
		String momOrderdetailMoDId=jobj.getString("mainTableIdentity");//mom_orderdetail MoDId
		String condition=" mod.modid='"+momOrderdetailMoDId+"'";
		Map order=daoU890.getMomOrderYonYou(condition);
		
		
		AaDepartment ad=cacheCtrlService.getDepartment(jobj.getString("dptGid"));//部门信息
		
		Set<String> warehouse=new HashSet<String>();
		for(Object obj:jsonArray){
			JSONObject prowctJson = (JSONObject) obj;
			AaGoodsallocation aaGoodsallocation=cacheCtrlService.getGoodsAllocation(prowctJson.getString("goodsAllocationUid"));//货位信息
			warehouse.add(aaGoodsallocation.getWhcode());
		}
		
		Iterator<String> it=warehouse.iterator();
		String requestXml="";
		
		List<StoreInHeadU8> storeInHeads=new ArrayList<StoreInHeadU8>();
		List<StoreInBodyU8> storeInBodys=new ArrayList<StoreInBodyU8>();
		while(it.hasNext()){
			String ckCode=it.next();
			String chilid="";
			for(Object obj:jsonArray){
				JSONObject prowctJson = (JSONObject) obj;
				
				AaGoodsallocation aaGoodsallocation=cacheCtrlService.getGoodsAllocation(prowctJson.getString("goodsAllocationUid"));//货位信息
				AaWarehouse	aaWarehouse=cacheCtrlService.getWareHouse(aaGoodsallocation.getWhuid());
				
				if(ckCode.equalsIgnoreCase(aaWarehouse.getWhcode())){
					
					AaGoods aaGoods=cacheCtrlService.getGoods(prowctJson.getString("goodsUid"));
					StoreInBodyU8 storeInBody=new StoreInBodyU8();
					storeInBody.setInventorycode(CommonUtil.isNullObject(prowctJson.get("goodsCode"))?"":prowctJson.get("goodsCode").toString());
					storeInBody.setShouldquantity(CommonUtil.isNullObject(prowctJson.get("submitNum"))?"":prowctJson.get("submitNum").toString());
					storeInBody.setQuantity(CommonUtil.isNullObject(prowctJson.get("submitNum"))?"":prowctJson.get("submitNum").toString());
					storeInBody.setCmassunitname(aaGoods.getUnitName());//
					storeInBody.setCsTComUnitCode(aaGoods.getCstcomunitcode());//库存辅计量单位编码
					storeInBody.setCsTComUnitName(aaGoods.getCstComUnitName());//库存辅计量单位名称
					if(!CommonUtil.isNullObject(aaGoods.getCstcomunitcode())){
						storeInBody.setNumber(CommonUtil.isNullObject(prowctJson.get("submitQuantity"))?"":prowctJson.get("submitQuantity").toString());//辅数量
						storeInBody.setIrate(Double.toString(prowctJson.getDouble("submitNum")/prowctJson.getDouble("submitQuantity")));//换算率
					}else{
						storeInBody.setNumber("");
						storeInBody.setIrate("");//换算率
					}

					storeInBody.setDefine30(CommonUtil.isNullObject(prowctJson.get("gid"))?"":prowctJson.get("gid").toString());
					
					aaWarehouse=cacheCtrlService.getWareHouse(aaGoodsallocation.getWhuid());
					if(aaWarehouse.getWhpos().intValue()==1){
						storeInBody.setPosition(aaGoodsallocation.getCode());
					}else{
						storeInBody.setPosition("");
					}
					
					
					storeInBody.setBatch(CommonUtil.isNullObject(prowctJson.get("batch"))?"":prowctJson.get("batch").toString());
					
					JSONArray cfreeArray=prowctJson.getJSONArray("cfree");
					for(int i=0;i<cfreeArray.size();i++){//后续再完善
						JSONObject cf = (JSONObject)cfreeArray.get(i);
						if(cf.getString("colName").equalsIgnoreCase("cfree1")){
							storeInBody.setCfree1(CommonUtil.isNullObject(cf.get("value"))?"":cf.get("value").toString());
						}else if(cf.getString("colName").equalsIgnoreCase("cfree2")){
							storeInBody.setCfree2(CommonUtil.isNullObject(cf.get("value"))?"":cf.get("value").toString());
						}
					}
					
					//有效期管理
					if((CommonUtil.isNullObject(prowctJson.get("isInvQuality"))?0:prowctJson.getInt("isInvQuality"))==1  ){
						if(prowctJson.getDouble("submitNum")<0){//暂时无成品退库
							
//							condition=" cinvcode='"+storeInBody.getInventorycode()+"' and cwhcode='"+ckCode+"' and cbatch='"+storeInBody.getBatch()+"'";
//							if(!CommonUtil.isNullObject(storeInBody.getCfree1())){
//								condition=condition+" and cfree1='"+storeInBody.getCfree1()+"'";
//							}
//							if(!CommonUtil.isNullObject(storeInBody.getCfree2())){
//								condition=condition+" and cfree2='"+storeInBody.getCfree2()+"'";
//							}
//							List<CurrentStock> cs=daoU890.getAllocationStock(condition);
//							if(cs.size()==1){
////								storeInBody.setMakedate(makedate);
//								storeInBody.setValiddate(CommonUtil.isNullObject(cs.get(0).getDvDate())?DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT):cs.get(0).getDvDate().toString().substring(0, 10));
//							
//								storeInBody.setImassDate(prowctJson.getString("massDate"));//有效期天数
//								storeInBody.setMassunit("3");//有效期单位  3表示天
//							}else{
//								System.out.println("存在多个有效期的库存");
//								return false;
//							}
							
						}else{
							
							storeInBody.setMakedate(DateUtil.dateToString(new Date(), DateUtil.LONG_DATE_FORMAT));
							String massDate=DateUtil.nextDay(prowctJson.getInt("massDate"), DateUtil.LONG_DATE_FORMAT);
							storeInBody.setValiddate(massDate);
							
							storeInBody.setImassDate(prowctJson.getString("massDate"));//有效期天数
							storeInBody.setMassunit("3");//有效期单位  3表示天
						}
						
					}
					
					storeInBody.setImPoIds(momOrderdetailMoDId);
					storeInBody.setCmocode(order.get("moCode").toString());
					storeInBody.setImoseq(order.get("sortSeq").toString());
					
					storeInBodys.add(storeInBody);
					chilid=chilid+U890xml.getStoreInBodyXml(storeInBody);
				}
			}
			
			YmUser ymUser=cacheCtrlService.getUser(jobj.getString("userGid"));//用户信息
			String xml="";
			StoreInHeadU8 storeInHead=new StoreInHeadU8();
			storeInHead.setVouchtype("10");
			storeInHead.setBusinesstype("成品入库");
			storeInHead.setWarehousecode(ckCode);
			storeInHead.setDate(DateUtil.dateToString(new Date(), DateUtil.LONG_DATE_FORMAT));
			storeInHead.setReceivecode(CommonUtil.isNullObject(jobj.get("rdStyle"))?"":jobj.get("rdStyle").toString());//入库类别
			storeInHead.setDepartmentcode(ad.getDepcode());
			storeInHead.setTemplatenumber("63");
			storeInHead.setMaker(ymUser.getUserName());
			storeInHead.setDefine10(UUID.randomUUID().toString());
			storeInHead.setIproorderid(order.get("moid").toString());
			storeInHead.setCmpocode(order.get("moCode").toString());
			
			storeInHeads.add(storeInHead);
			
			xml=U890xml.getStoreInHeadXml(storeInHead);
			xml=U890xml.connectHeadBodyIn(xml, chilid);//拼接字符串
			requestXml=requestXml+xml;
		}
		
		requestXml=U890xml.connectHeadBodyIn(requestXml);
		System.out.println(requestXml);
		String succeedInfor=YonYouUtil.eaiWriteInfor(requestXml);
		 
		if(!succeedInfor.equalsIgnoreCase("0")){
			return false;
		}
		
		if(succeedInfor.equalsIgnoreCase("0")){
			updateSourceWhenProductIn(storeInHeads, storeInBodys);//回填上游单据
		}
		
		return true;
	}
	
	
	//产品入库完善以及回填上游相关单据
	public boolean updateSourceWhenProductIn(List<StoreInHeadU8> storeInHeads,List<StoreInBodyU8> storeInBodys){
		daoU890.updateRdrecordScr(storeInHeads);
		daoU890.updateRdrecordsScr(storeInBodys);
		daoU890.updateMomOrderdetail(storeInBodys);
		return true;
	}
	
	
	
	
	/**
	 * @category 修改现存量表待发货数量 
	 *2016 2016年4月7日下午4:21:51
	 *boolean
	 *宋银海
	 */
	public boolean updateCurrentStock(List<CurrentStock> asList) {
		
		StringBuffer sbf=new StringBuffer();
		for(CurrentStock vi:asList){
			sbf.append("'"+vi.getCinvCode()+"',");
		}
		
		String condition=sbf.toString();
		if(condition.length()>0){
			condition=condition.substring(0, condition.length()-1);
			condition=" cinvCode in ("+condition+")";
		}
		
		//查询
		List<CurrentStock> currentStock=daoU890.getAllocationStock(condition);
		
		List<CurrentStock> toUpdate=new ArrayList<CurrentStock>();
		
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
			
		}
		
		//修改
		if(toUpdate.size()>0){
			daoU890.batchUptFout(toUpdate);
		}
		
		return true;
	}

	/**
	 * @category 添加bom
	 * 2016年5月9日 下午4:34:11 
	 * @author zhuxiaochen
	 * @param jobj
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void addBom(JSONArray jobj) {
		for(Object o : jobj){
			//查询下一个bomId
			int bomId = daoU890.getBillId("00", Config.BUSINESSDATABASE.split("_")[1], "bom_bom", 1)[1];//("bom_bom");
			
			JsonConfig jsonConfig = new JsonConfig();  //建立配置文件
			jsonConfig.setIgnoreDefaultExcludes(false);  //设置默认忽略
			jsonConfig.setExcludes(new String[]{"bomParent", "bomOpcomponentList"}); 
			
			JSONObject jo = (JSONObject) o;
			BomBom bom_bom = (BomBom) EmiJsonObj.toBean(JSONObject.fromObject(jo,jsonConfig), BomBom.class);
			
			/*
			 * 根据物料id及版本号查询是否已有相关的bom，有则先删除
			 */
			daoU890.deleteOldBom(bom_bom.getLugGoodsId(),bom_bom.getVersion());
			
			BomParent bom_parent = (BomParent) EmiJsonObj.toBean(jo.getJSONObject("bomParent"), BomParent.class);
			List<BomOpcomponent> bom_opcomponent = (List<BomOpcomponent>) EmiJsonArray.toCollection(jo.getJSONArray("bomOpcomponentList"), BomOpcomponent.class);
			
			//设置bomId
			bom_bom.setBomId(bomId);
			bom_parent.setBomId(bomId);
			for(BomOpcomponent bo : bom_opcomponent){
				bo.setBomId(bomId);
			}
			/*
			 * 增加bom数据
			 */
			daoU890.addBom(bom_bom,bom_parent,bom_opcomponent);
		}
		
	}

	/**
	 * @category 物料清单bas_part的数据
	 * 2016年5月11日 上午11:29:02 
	 * @author zhuxiaochen
	 * @param invCodes 
	 * @return
	 */
	public List<BasPart> getBasPartList(String invCodes) {
		return daoU890.getBasPartList(invCodes);
	}
	
	
	
	/**
	 * @category 添加工序成品入库单
	 *2016 2016年3月15日上午9:31:41
	 *String
	 *宋银海
	 */
	public boolean addTransVouch(JSONObject jobj) throws Exception{
		
		JSONArray jsarray=jobj.getJSONArray("data");
		
		String userId=jobj.getString("userId");
		YmUser user=cacheCtrlService.getUser(userId);
		
		TransVouch tv=new TransVouch();
		
/*		String condition=" CardNumber='0304'  ";
		Map trVoucherHistory=daoU890.getVoucherHistory(condition);
		int trVoucherId=Integer.parseInt(trVoucherHistory.get("cNumber").toString())+1;
		String trBillCode=getbillCode("0304");*/
		
		
/*		condition=" CardNumber='0302'  ";
		Map rdoVoucherHistory=daoU890.getVoucherHistory(condition);
		int rdOutVoucherId=Integer.parseInt(rdoVoucherHistory.get("cNumber").toString())+1;
		String rdOutBillCode=getbillCode("0302");*/
		
/*		condition=" CardNumber='0301'  ";
		Map rdiVoucherHistory=daoU890.getVoucherHistory(condition);
		int rdInVoucherId=Integer.parseInt(rdiVoucherHistory.get("cNumber").toString())+1;
		String rdInBillCode=getbillCode("0301");*/
		
		tv.setCtVCode(getBillIdForYonYou(Constants.EMIDB));
		tv.setCmaker(user.getUserName());
		tv.setCverifyPerson(user.getUserName());
		tv.setDtVDate(Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00"));
		tv.setDverifyDate(Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00"));
		tv.setVtid(89);
		tv.setCiRdCode(CommonUtil.Obj2String(jobj.get("inRdStyle")));//入库类别
		tv.setCoRdCode(CommonUtil.Obj2String(jobj.get("outRdStyle")));//出库类别
		
		String dataBaseName=Config.BUSINESSDATABASE;
		String[] strs=dataBaseName.split("_");
		int[] ids=daoU890.getBillId("", strs[1], "tr", jsarray.size());//调用存储过程获得调拨单主子表id
		int[] idOuts=daoU890.getBillId("", strs[1], "rd", jsarray.size());//调用存储过程获得其他出库单主子表id
		int[] idInts=daoU890.getBillId("", strs[1], "rd", jsarray.size());//调用存储过程获得其他入库单主子表id
		
		List<TransVouchs> tvs=new ArrayList<TransVouchs>();//调拨单子表
		List<RdRecords> rdsOutIn=new ArrayList<RdRecords>();//其它出入库单子表
		
		List<InvPosition> ips=new ArrayList<InvPosition>();//存货货位记录表
		List<CurrentStock> css=new ArrayList<CurrentStock>();//仓库现存量
		
		String coWhCode="";//拨出仓库
		String ciWhCode="";//拨入仓库
		
		int j=1;
		for(Object obj:jsarray){
			
			JSONObject jo=(JSONObject)obj;
			AaGoods aaGoods=cacheCtrlService.getGoods(jo.getString("goodsUid"));
			
			AaGoodsallocation og=cacheCtrlService.getGoodsAllocation(jo.getString("outgoodsAllocationUid"));//调拨出货位
			AaWarehouse ow=cacheCtrlService.getWareHouse(og.getWhuid());//调拨出仓库
			if(coWhCode.equals("")){
				coWhCode=ow.getWhcode();
			}
			
			AaGoodsallocation ig=cacheCtrlService.getGoodsAllocation(jo.getString("ingoodsAllocationUid"));//调拨入货位
			AaWarehouse iw=cacheCtrlService.getWareHouse(ig.getWhuid());//调拨入仓库
			if(ciWhCode.equals("")){
				ciWhCode=iw.getWhcode();
			}
			
			TransVouchs t=new TransVouchs();//调拨单子表
			t.setAutoID(100000000+(ids[1]-jsarray.size()+j));
			t.setId(100000000+ids[0]);
			t.setCtVCode(tv.getCtVCode());
			t.setCinvCode(aaGoods.getGoodscode());
			t.setCassUnit(CommonUtil.Obj2String(aaGoods.getCstcomunitcode()));
			
			if((CommonUtil.isNullObject(ow.getWhpos())?0:ow.getWhpos().intValue())==1){//出库仓库
				t.setCoutposcode(og.getCode());
			}
			
			if((CommonUtil.isNullObject(iw.getWhpos())?0:iw.getWhpos().intValue())==1){//入库仓库
				t.setCinposcode(ig.getCode());
			}
			
			t.setCtVBatch(CommonUtil.Obj2String(jo.getString("batch")));
			
			JSONArray cfreeArray=jo.getJSONArray("cfree");
			for(int i=0;i<cfreeArray.size();i++){//后续再完善
				JSONObject cf = (JSONObject)cfreeArray.get(i);
				if(cf.getString("colName").equalsIgnoreCase("cfree1")){
					t.setCfree1(CommonUtil.isNullObject(cf.get("value"))?"":cf.get("value").toString());
				}else if(cf.getString("colName").equalsIgnoreCase("cfree2")){
					t.setCfree2(CommonUtil.isNullObject(cf.get("value"))?"":cf.get("value").toString());
				}
			}
			
			t.setIrowno(j);
			t.setItVQuantity(BigDecimal.valueOf(jo.getDouble("suboutnum")));//主数量
			tvs.add(t);
			
			if(!CommonUtil.isNullObject(aaGoods.getCstcomunitcode())){
				t.setItVNum(BigDecimal.valueOf(jo.getDouble("suboutassnum")));//辅
				t.setIinvexchrate(BigDecimal.valueOf(jo.getDouble("suboutnum")).divide(BigDecimal.valueOf(jo.getDouble("suboutassnum")), 4, BigDecimal.ROUND_HALF_UP));
			}
			
			
			if((CommonUtil.isNullObject(aaGoods.getIsInvQuality())?0:aaGoods.getIsInvQuality().intValue())==1 ){//有效期管理
				
				String condition=" cinvcode='"+t.getCinvCode()+"' and cwhcode='"+coWhCode+"' and cbatch='"+t.getCtVBatch()+"'";
				if(!CommonUtil.isNullObject(t.getCfree1())){
					condition=condition+" and cfree1='"+t.getCfree1()+"'";
				}
				if(!CommonUtil.isNullObject(t.getCfree2())){
					condition=condition+" and cfree2='"+t.getCfree2()+"'";
				}
				List<CurrentStock> cs=daoU890.getAllocationStock(condition);
				if(cs.size()==1){
					t.setDmadeDate(cs.get(0).getDmDate());//生产日期
					t.setDdisDate(cs.get(0).getDvDate());//失效日期
					t.setImassDate(cs.get(0).getImassDate());//有效期天数
					t.setCmassUnit(3);//单位
				
				}else{
					System.out.print("关于失效日期，存在多个库存");
					return false;
				}
					
			}
			
			
			RdRecords rout=new RdRecords();//出库单子表
			rout.setAutoID(100000000+(idOuts[1]-jsarray.size()+j));
			rout.setId(100000000+idOuts[0]);
			rout.setCinvCode(aaGoods.getGoodscode());
			rout.setIquantity(t.getItVQuantity());
			if(!CommonUtil.isNullObject(aaGoods.getCstcomunitcode())){
				rout.setInum(t.getItVNum());
			}
			
			rout.setCbatch(t.getCtVBatch());
			rout.setCfree1(t.getCfree1());
			rout.setCfree2(t.getCfree2());
			rout.setItrIds(t.getAutoID());
			rout.setCposition(t.getCoutposcode());
			rout.setCdefine30(UUID.randomUUID().toString());
			if((CommonUtil.isNullObject(aaGoods.getIsInvQuality())?0:aaGoods.getIsInvQuality().intValue())==1 ){
				rout.setDmadeDate(t.getDmadeDate());
				rout.setDvdate(t.getDdisDate());
				rout.setImassDate(t.getImassDate());
				rout.setCmassUnit(3);
			}
			rdsOutIn.add(rout);
			
			CurrentStock cs=new CurrentStock();//现存量出
			cs.setCwhCode(coWhCode);
			cs.setCinvCode(rout.getCinvCode());
			cs.setCbatch(CommonUtil.Obj2String(rout.getCbatch()));
			cs.setIquantity(new BigDecimal(0).subtract(rout.getIquantity()));
			cs.setInum(new BigDecimal(0).subtract(CommonUtil.isNullObject(rout.getInum())?new BigDecimal(0):rout.getInum()));
			cs.setCfree1(rout.getCfree1());
			cs.setCfree2(rout.getCfree2());
			if((CommonUtil.isNullObject(aaGoods.getIsInvQuality())?0:aaGoods.getIsInvQuality().intValue())==1 ){
				cs.setDmDate(t.getDmadeDate());
				cs.setDvDate(t.getDdisDate());
				cs.setImassDate(t.getImassDate());
				cs.setCmassUnit(t.getCmassUnit());
			}
			css.add(cs);
			
			RdRecords rin=new RdRecords();//入库单子表
			rin.setAutoID(100000000+(idInts[1]-jsarray.size()+j));
			rin.setId(100000000+idInts[0]);
			rin.setCinvCode(aaGoods.getGoodscode());
			rin.setIquantity(t.getItVQuantity());
			if(!CommonUtil.isNullObject(aaGoods.getCstcomunitcode())){
				rin.setInum(t.getItVNum());
			}
			
			rin.setCbatch(t.getCtVBatch());
			rin.setCfree1(t.getCfree1());
			rin.setCfree2(t.getCfree2());
			rin.setItrIds(t.getAutoID());
			rin.setCposition(t.getCinposcode());
			rin.setCdefine30(UUID.randomUUID().toString());
			if((CommonUtil.isNullObject(aaGoods.getIsInvQuality())?0:aaGoods.getIsInvQuality().intValue())==1 ){
				
				rin.setDmadeDate(t.getDmadeDate());
				rin.setDvdate(t.getDdisDate());
				rin.setImassDate(t.getImassDate());
				rin.setCmassUnit(3);
			}

			rdsOutIn.add(rin);
			
			cs=new CurrentStock();//现存量入
			cs.setCwhCode(ciWhCode);
			cs.setCinvCode(rin.getCinvCode());
			cs.setCbatch(rin.getCbatch());
			cs.setIquantity(rin.getIquantity());
			cs.setInum(rin.getInum());
			cs.setCfree1(rin.getCfree1());
			cs.setCfree2(rin.getCfree2());
			if((CommonUtil.isNullObject(aaGoods.getIsInvQuality())?0:aaGoods.getIsInvQuality().intValue())==1 ){
				cs.setDmDate(t.getDmadeDate());
				cs.setDvDate(t.getDdisDate());
				cs.setImassDate(t.getImassDate());
				cs.setCmassUnit(3);
			}
			css.add(cs);
			
			InvPosition ipout=new InvPosition();//存货货位记录表（出）
			if((CommonUtil.isNullObject(ow.getWhpos())?0:ow.getWhpos().intValue())==1){//出库仓库
				ipout.setRdID(rout.getId());
				ipout.setRdsID(rout.getAutoID());
				ipout.setCwhCode(coWhCode);
				ipout.setCposCode(rout.getCposition());
				ipout.setCinvCode(rout.getCinvCode());
				ipout.setCbatch(rout.getCbatch());
				ipout.setCfree1(rout.getCfree1());
				ipout.setCfree2(rout.getCfree2());
				ipout.setIquantity(rout.getIquantity());
				ipout.setInum(rout.getInum());
				ipout.setChandler(user.getUserName());
				ipout.setDdate(Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00"));
				ipout.setBrdFlag(0);
				if((CommonUtil.isNullObject(aaGoods.getIsInvQuality())?0:aaGoods.getIsInvQuality().intValue())==1 ){
					ipout.setDmadeDate(t.getDmadeDate());
					ipout.setDvDate(t.getDdisDate());
					ipout.setImassDate(t.getImassDate());
					ipout.setCmassUnit(3);
				}
				ips.add(ipout);
				
			}
			
			InvPosition ipin=new InvPosition();//存货货位记录表（入）
			if((CommonUtil.isNullObject(iw.getWhpos())?0:iw.getWhpos().intValue())==1){//入库仓库
				ipin.setRdID(rin.getId());
				ipin.setRdsID(rin.getAutoID());
				ipin.setCwhCode(ciWhCode);
				ipin.setCposCode(rin.getCposition());
				ipin.setCinvCode(rin.getCinvCode());
				ipin.setCbatch(rin.getCbatch());
				ipin.setCfree1(rin.getCfree1());
				ipin.setCfree2(rin.getCfree2());
				ipin.setIquantity(rin.getIquantity());
				ipin.setInum(rin.getInum());
				ipin.setChandler(user.getUserName());
				ipin.setDdate(Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00"));
				ipin.setBrdFlag(1);
				if((CommonUtil.isNullObject(aaGoods.getIsInvQuality())?0:aaGoods.getIsInvQuality().intValue())==1 ){
					ipin.setDmadeDate(t.getDmadeDate());
					ipin.setDvDate(t.getDdisDate());
					ipin.setImassDate(t.getImassDate());
					ipin.setCmassUnit(3);
				}
				ips.add(ipin);
			}
			
			j++;
		}
		tv.setId(100000000+ids[0]);
		tv.setCoWhCode(coWhCode);
		tv.setCiWhCode(ciWhCode);
		
		
		List<RdRecord> rds=new ArrayList<RdRecord>();
		RdRecord rdo=new RdRecord();
		rdo.setId(100000000+idOuts[0]);
		rdo.setBrdFlag(0); //收发标志 0 发 1收
		rdo.setCvouchType("09");
		rdo.setCbusType("调拨出库");
		rdo.setCsource("调拨");
		rdo.setCbusCode(tv.getCtVCode());
		rdo.setCwhCode(coWhCode);
		rdo.setDdate(Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00"));
		rdo.setCcode(getBillIdForYonYou(Constants.EMIQTCK));
		rdo.setCmaker(user.getUserName());
		rdo.setcHandler(user.getUserName());
		rdo.setDveriDate(Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00"));
		rdo.setCdefine10(UUID.randomUUID().toString());
		rdo.setVtid(85);
		rdo.setCrdCode(tv.getCoRdCode());//出库类别
		rds.add(rdo);
		
		RdRecord rdi=new RdRecord();
		rdi.setId(100000000+idInts[0]);
		rdi.setBrdFlag(1); //收发标志 0 发 1收
		rdi.setCvouchType("08");
		rdi.setCbusType("调拨入库");
		rdi.setCsource("调拨");
		rdi.setCbusCode(tv.getCtVCode());
		rdi.setCwhCode(ciWhCode);
		rdi.setDdate(Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00"));
		rdi.setCcode(getBillIdForYonYou(Constants.EMIQTRK));
		rdi.setCmaker(user.getUserName());
		rdi.setcHandler(user.getUserName());
		rdi.setDveriDate(Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00"));
		rdi.setCdefine10(UUID.randomUUID().toString());
		rdi.setVtid(67);
		rdi.setCrdCode(tv.getCiRdCode());//入库类别
		rds.add(rdi);
		
		/*daoU890.uptVoucherHistory(String.valueOf(trVoucherId), "0304");//修改调拨单编号记录
*/
		List<TransVouch> tvhs=new ArrayList<TransVouch>();
		tvhs.add(tv);
		daoU890.addTransVouch(tvhs);////////////////////////////////////////////////////////////////////////////添加调拨单主表
		daoU890.addTransVouchs(tvs);////////////////////////////////////////////////////////////////////////////添加调拨单子表
		
		/*daoU890.uptVoucherHistory(String.valueOf(rdOutVoucherId), "0302");//修改其它出库编号记录
		daoU890.uptVoucherHistory(String.valueOf(rdInVoucherId), "0301");//修改其它入库编号记录
*/		
		daoU890.addRdRecord(rds);/////////////////////////////////////////////////////////////////////////添加其他出入库单主表
		daoU890.addRdRecords(rdsOutIn);///////////////////////////////////////////////////////////////////添加其他出入库单子表
		
		daoU890.addInvPosition(ips);///////////////////////////////////////////////////////////////////////添加存货货位记录表
		updCurrentStock(css);///////////////////////////////////////////////////////////////////////////修改仓库现存量
		
		return true;
	}
	
	
	
	
	/**
	 * @category 添加委外材料出库
	 *2016 2016年4月22日下午1:01:30
	 *boolean
	 *宋银海
	 */
	public boolean addOmMeterialOut(JSONObject jobj) throws Exception {
		
		JSONArray jsonArray=jobj.getJSONArray("wmsGoodsLists");

		//从用友中查询到货单信息
		String condition="  omn.moid='"+jobj.getString("mainTableIdentity")+"'";
		List<Map> maps=daoU890.getOMMOMaterials(condition);
		
		Set<String> warehouse=new HashSet<String>();
		for(Object obj:jsonArray){
			JSONObject prowctJson = (JSONObject) obj;
			AaGoodsallocation aaGoodsallocation=cacheCtrlService.getGoodsAllocation(prowctJson.getString("goodsAllocationUid"));//货位信息
			warehouse.add(aaGoodsallocation.getWhcode());
		}
		
		Iterator<String> it=warehouse.iterator();
		List<RdRecords> rdRecords=new ArrayList<RdRecords>();//出库单子表
		List<RdRecord> rdRecord=new ArrayList<RdRecord>();//出库单主表
		List<CurrentStock> css=new ArrayList<CurrentStock>();//现存量
		List<InvPosition> ips=new ArrayList<InvPosition>();//存货货位记录表
		
		
		String dataBaseName=Config.BUSINESSDATABASE;
		String[] strs=dataBaseName.split("_");
		
		/*condition=" CardNumber='0412'  ";//提交材料出库时使用 0412
		Map rdVoucherHistory=daoU890.getVoucherHistory(condition);*/
		
		YmUser ymUser=cacheCtrlService.getUser(jobj.getString("userGid"));//用户信息
		
		AaDepartment ad=cacheCtrlService.getDepartment(jobj.getString("dptGid"));//部门信息
		
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
				AaGoodsallocation aaGoodsallocation=cacheCtrlService.getGoodsAllocation(prowctJson.getString("goodsAllocationUid"));//货位信息
				AaWarehouse	aaWarehouse=cacheCtrlService.getWareHouse(aaGoodsallocation.getWhuid());
				
				if(ckCode.equalsIgnoreCase(aaWarehouse.getWhcode())){
					AaGoods aaGoods=cacheCtrlService.getGoods(prowctJson.getString("goodsUid"));
					
					RdRecords rds=new RdRecords();
					
					if(aaWarehouse.getBproxyWh().intValue()==1){
						bvmiUsed=1;
					}
					
					if(aaWarehouse.getBproxyWh().intValue()==1){
						rds.setCvmivencode(CommonUtil.Obj2String(prowctJson.get("cvMIVenCode")));//代管商编码
					}
					
					rds.setBvmiUsed(bvmiUsed);
					rds.setCinvCode(CommonUtil.isNullObject(prowctJson.get("goodsCode"))?"":prowctJson.get("goodsCode").toString());
					rds.setIquantity(CommonUtil.str2BigDecimal(prowctJson.getString("submitNum")));//主数量
					if(!CommonUtil.isNullObject(aaGoods.getCasscomunitcode())){
						rds.setInum(CommonUtil.str2BigDecimal(prowctJson.get("submitQuantity")));//辅数量
					}else{
						rds.setInum(null);
					}
	
					rds.setCdefine30(CommonUtil.isNullObject(prowctJson.get("gid"))?"":prowctJson.get("gid").toString());
				
					if(aaWarehouse.getWhpos().intValue()==1 ){
						rds.setCposition(aaGoodsallocation.getCode());
					}else{
						rds.setCposition("");
					}
					
					rds.setCbatch(CommonUtil.isNullObject(prowctJson.get("batch"))?"":prowctJson.get("batch").toString());
					
					for(Map map :maps){
						if(prowctJson.getString("childTableIdentity").equalsIgnoreCase(map.get("mOMaterialsID").toString())){
//							rds.setShoudIquantity(BigDecimal.valueOf(Double.parseDouble(map.get("iQuantity").toString())));
							rds.setIoMoDID(map.get("moDetailsID").toString());//委外订单子表ID  
							rds.setIoMoMID(map.get("mOMaterialsID").toString());//委外订单用料表ID
							rds.setComcode(map.get("ccode").toString());
							rds.setInvcode(map.get("cInvCode").toString());
							break;
						}
					} 
					
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
						List<CurrentStock> cs=daoU890.getAllocationStock(condition);
						
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
			
			int[] idOuts=daoU890.getBillId("", strs[1], "rd", k);//调用存储过程获得其他出库单主子表id
			
			int n=1;
			for(RdRecords rds:tempRdRecords){
				rds.setAutoID(100000000+(idOuts[1]-k+n));
				rds.setId(100000000+idOuts[0]);
				n++;
				
				InvPosition ipout=new InvPosition();//存货货位记录表（出）
				if(!CommonUtil.isNullObject(rds.getCposition())){//出库仓库
					ipout.setRdID(rds.getId());
					ipout.setRdsID(rds.getAutoID());
					ipout.setCwhCode(ckCode);
					ipout.setCposCode(rds.getCposition());
					ipout.setCinvCode(rds.getCinvCode());
					ipout.setCbatch(rds.getCbatch());
					ipout.setCfree1(rds.getCfree1());
					ipout.setCfree2(rds.getCfree2());
					ipout.setIquantity(rds.getIquantity());
					ipout.setInum(rds.getInum());
					ipout.setChandler(ymUser.getUserName());
					ipout.setDdate(Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00"));
					ipout.setBrdFlag(0);//收发标志 0 发 1收
					ipout.setDvDate(rds.getDvdate());//失效期
					ipout.setDmadeDate(rds.getDmadeDate());
					ipout.setImassDate(rds.getImassDate());
					ipout.setCmassUnit(rds.getCmassUnit());
					ipout.setCvmivencode(rds.getCvmivencode());
					ips.add(ipout);
				}
				
			}
			
			
			RdRecord rdo=new RdRecord();
			rdo.setId(100000000+idOuts[0]);
			rdo.setBrdFlag(0);//收发标志 0 发 1收
			rdo.setCvouchType("11");
			rdo.setCbusType("委外发料");
			rdo.setCsource("委外订单");
			rdo.setCwhCode(ckCode);
			rdo.setDdate(Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00"));
			rdo.setCcode(getBillIdForYonYou(Constants.EMICLCK));
			rdo.setCmaker(ymUser.getUserName());
			rdo.setcHandler(ymUser.getUserName());
			rdo.setDveriDate(Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00"));
			rdo.setCdefine10(UUID.randomUUID().toString());
			rdo.setCvenCode(maps.get(0).get("cvenCode").toString());
			rdo.setVtid(65);
			rdo.setCpsPcode(maps.get(0).get("cInvCode").toString());
			rdo.setCmPoCode(maps.get(0).get("ccode").toString());
			rdo.setIpurorderid(maps.get(0).get("moid").toString());
			rdo.setCrdCode(jobj.getString("rdStyle"));
			rdo.setCdepCode(ad.getDepcode());
			rdRecord.add(rdo);
			
			i++;
		}
		
//		daoU890.uptVoucherHistory(String.valueOf(rdOutVoucherId), "0412");//修改其它入出库编号记录 //提交材料出库时使用 0412
		
		daoU890.addRdRecord(rdRecord);/////////////////////////////////////////////////////////////////////////添加其他出入库单主表
		daoU890.addRdRecords(rdRecords);///////////////////////////////////////////////////////////////////添加其他出入库单子表
		
		daoU890.addInvPosition(ips);///////////////////////////////////////////////////////////////////////添加存货货位记录表
		
		List<CurrentStock> csnew=getNewCurrentStockList(css);
		updCurrentStock(csnew);///////////////////////////////////////////////////////////////////////////修改仓库现存量
		
		updateSourceBillWhenSubmitWareHouse(null,rdRecords, Constants.TASKTYPE_WWCLCK,null);///////////////////////////////回填订单已领料数量
		
		return true;
	}
	
	
	/**
	 * @category 回填单据
	 *2016 2016年5月19日下午4:58:00
	 *boolean
	 *宋银海
	 */
	public boolean updateSourceBillWhenSubmitWareHouse(List<RdRecord> rd, List<RdRecords> goods,String billType,List<CurrentStock> css){//type in入库 out出库
		if(billType.equalsIgnoreCase(Constants.TASKTYPE_GXTL)){//工序退料 反填生产订单材料出库子二表已出库库数量
			daoU890.backFillMoallocate(goods);
		}
		
		if(billType.equalsIgnoreCase(Constants.TASKTYPE_WWCLCK)){//反填委外材料出库子二表已出库库数量
			daoU890.backFillMoMaterialsC(goods);
			daoU890.backFillMoGoodssC(goods);
		}
		if(billType.equalsIgnoreCase(Constants.TASKTYPE_WWCPRK)){//反填委外订单产品表已入库库数量
			daoU890.backFillPuAppVouchs(goods);
		}
		
		if(billType.equalsIgnoreCase(Constants.TASKTYPE_XSCK)){
//			updateCurrentStock(css);
			daoU890.updateDispatchListsrds(goods);
			daoU890.updateDispatchListrd(rd);//如果不执行，对应生成的出库单没法删除
		}
		
		return true;
	}
	
	
	/**
	 * @category 添加委外成品入库单
	 *2016 2016年3月15日上午9:31:41
	 *String
	 *宋银海
	 */
	public boolean addOmProductIn(JSONObject jobj) throws Exception{
		
		
		JSONArray jsonArray=jobj.getJSONArray("wmsGoodsLists");

		//从用友中查询到货单信息
		//从用友中查询到货单信息
		String condition=" pa.id='"+jobj.getString("mainTableIdentity")+"'";
		List<Map> maps=daoU890.getProcurearrivalCYonYou(condition);
		
		Set<String> warehouse=new HashSet<String>();
		for(Object obj:jsonArray){
			JSONObject prowctJson = (JSONObject) obj;
			AaGoodsallocation aaGoodsallocation=cacheCtrlService.getGoodsAllocation(prowctJson.getString("goodsAllocationUid"));//货位信息
			warehouse.add(aaGoodsallocation.getWhcode());
		}
		
		Iterator<String> it=warehouse.iterator();
		List<RdRecords> rdRecords=new ArrayList<RdRecords>();//入库单子表
		List<RdRecord> rdRecord=new ArrayList<RdRecord>();//入库单主表
		List<CurrentStock> css=new ArrayList<CurrentStock>();//现存量
		List<InvPosition> ips=new ArrayList<InvPosition>();//存货货位记录表
		
		
		String dataBaseName=Config.BUSINESSDATABASE;
		String[] strs=dataBaseName.split("_");
		
/*		condition=" CardNumber='24'  ";//提交委外采购入库时使用 24
		Map rdVoucherHistory=daoU890.getVoucherHistory(condition);*/
		
		YmUser ymUser=cacheCtrlService.getUser(jobj.getString("userGid"));//用户信息
		
		int i=1;//用来记录主表单号
		int rdVoucherId=0;
		while(it.hasNext()){
			String ckCode=it.next();
			int bvmiUsed=0;//代管消耗标识
			
			/*rdVoucherId=Integer.parseInt(rdVoucherHistory.get("cNumber").toString())+i;
			String rdOutBillCode=getbillCode("24");*/
		
			int k=0;//用来存储子表，存储过程步长
			int irowno=1;//
			
			List<RdRecords> tempRdRecords=new ArrayList<RdRecords>();
			
			for(Object obj:jsonArray){
				JSONObject prowctJson = (JSONObject) obj;
				AaGoodsallocation aaGoodsallocation=cacheCtrlService.getGoodsAllocation(prowctJson.getString("goodsAllocationUid"));//货位信息
				AaWarehouse	aaWarehouse=cacheCtrlService.getWareHouse(aaGoodsallocation.getWhuid());
				
				if(ckCode.equalsIgnoreCase(aaWarehouse.getWhcode())){
					AaGoods aaGoods=cacheCtrlService.getGoods(prowctJson.getString("goodsUid"));
					
					RdRecords rds=new RdRecords();
					
					if(aaWarehouse.getBproxyWh().intValue()==1){
						bvmiUsed=1;
					}
					
					if(aaWarehouse.getBproxyWh().intValue()==1){//是否是代管
						rds.setCvmivencode(maps.get(0).get("cvenCode").toString());
					}
					
					rds.setBvmiUsed(bvmiUsed);
					rds.setCinvCode(CommonUtil.isNullObject(prowctJson.get("goodsCode"))?"":prowctJson.get("goodsCode").toString());
					rds.setIquantity(CommonUtil.str2BigDecimal(prowctJson.getString("submitNum")));
					if(!CommonUtil.isNullObject(aaGoods.getCasscomunitcode())){
						rds.setInum(CommonUtil.str2BigDecimal(prowctJson.get("submitQuantity")));//辅数量
					}else{
						rds.setInum(null);
					}
	
					rds.setCdefine30(CommonUtil.isNullObject(prowctJson.get("gid"))?"":prowctJson.get("gid").toString());
				
					if(aaWarehouse.getWhpos().intValue()==1 ){
						rds.setCposition(aaGoodsallocation.getCode());
					}else{
						rds.setCposition("");
					}
					
					rds.setCbatch(CommonUtil.isNullObject(prowctJson.get("batch"))?"":prowctJson.get("batch").toString());
					

					for(Map map :maps){
						if(prowctJson.getString("childTableIdentity").equalsIgnoreCase(map.get("autoid").toString())){

							rds.setIarrsId(map.get("autoid").toString());//采购到货单子表标识  
							rds.setCbarvcode(map.get("cCode").toString());//到货单号  
							
							rds.setCpoID(CommonUtil.Obj2String(map.get("omcCode")));//委外订单号 
							rds.setIoMoDID(CommonUtil.Obj2String(map.get("MODetailsID")));//委外订单子表ID
							
							rds.setItaxrate(CommonUtil.isNullObject(maps.get(0).get("itaxrate"))?null:maps.get(0).get("itaxrate").toString());
							rds.setInQuantity(CommonUtil.isNullObject(map.get("stillQuantity"))?null:CommonUtil.str2BigDecimal(map.get("stillQuantity").toString()));
							rds.setDbarvdate(CommonUtil.isNullObject(map.get("dDate"))?null:map.get("dDate").toString().substring(0, 10));
							rds.setIrowno(irowno);
							break;
						}
					} 
					
					JSONArray cfreeArray=prowctJson.getJSONArray("cfree");
					for(int m=0;m<cfreeArray.size();m++){//后续再完善
						JSONObject cf = (JSONObject)cfreeArray.get(m);
						if(cf.getString("colName").equalsIgnoreCase("cfree1")){
							rds.setCfree1(CommonUtil.isNullObject(cf.get("value"))?"":cf.get("value").toString());
						}else if(cf.getString("colName").equalsIgnoreCase("cfree2")){
							rds.setCfree2(CommonUtil.isNullObject(cf.get("value"))?"":cf.get("value").toString());
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
					
					k++;
					irowno++;
				} 
				
			}
			
			int[] idOuts=daoU890.getBillId("", strs[1], "rd", k);//调用存储过程获得入库单主子表id
			
			int n=1;
			for(RdRecords rds:tempRdRecords){
				rds.setAutoID(100000000+(idOuts[1]-k+n));
				rds.setId(100000000+idOuts[0]);
				n++;
				
				InvPosition ipin=new InvPosition();//存货货位记录表（入）
				if(!CommonUtil.isNullObject(rds.getCposition())){//入库仓库
					ipin.setRdID(rds.getId());
					ipin.setRdsID(rds.getAutoID());
					ipin.setCwhCode(ckCode);
					ipin.setCposCode(rds.getCposition());
					ipin.setCinvCode(rds.getCinvCode());
					ipin.setCbatch(rds.getCbatch());
					ipin.setCfree1(rds.getCfree1());
					ipin.setCfree2(rds.getCfree2());
					ipin.setIquantity(rds.getIquantity());
					ipin.setInum(rds.getInum());
					ipin.setChandler(ymUser.getUserName());
					ipin.setDdate(Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00"));
					ipin.setDvDate(rds.getDvdate());//有效期
					ipin.setBrdFlag(1); //收发标志 0 发 1收
					ipin.setCvmivencode(rds.getCvmivencode());
					ips.add(ipin);
				}
			}
			
			
			RdRecord rdo=new RdRecord();
			rdo.setId(100000000+idOuts[0]);
			rdo.setBrdFlag(1); //收发标志 0 发 1收
			rdo.setCvouchType("01");
			rdo.setCbusType("委外加工");
			rdo.setCsource("委外到货单");
			rdo.setCwhCode(ckCode);
			rdo.setDdate(Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00"));
			rdo.setCcode(getBillIdForYonYou(Constants.EMICGRK));
			rdo.setCmaker(ymUser.getUserName());
//			rdo.setcHandler(ymUser.getUserName());
			rdo.setcHandler(null);
//			rdo.setDveriDate(Timestamp.valueOf(DateUtil.getCurrDate(DateUtil.LONG_DATE_FORMAT)+" 00:00:00"));
			rdo.setDveriDate(null);
			rdo.setDnverifytime(null);
			rdo.setCdefine10(UUID.randomUUID().toString());
			rdo.setCvenCode(maps.get(0).get("cvenCode").toString());
			rdo.setCorderCode(CommonUtil.Obj2String(maps.get(0).get("omcCode")));//委外订单号 
			rdo.setCaRVCode(maps.get(0).get("cCode").toString());//采购到货单号 
			rdo.setIpurarriveid(maps.get(0).get("id").toString());//采购到货单主表标识  
			rdo.setVtid(27);
			rdo.setIexchRate("1");
			rdo.setCrdCode(jobj.getString("rdStyle"));
			rdo.setCdepCode(maps.get(0).get("cDepCode").toString());
			rdo.setCpTCode(CommonUtil.isNullObject(maps.get(0).get("cPTCode"))?null:maps.get(0).get("cPTCode").toString());
			
			rdo.setDaRVDate(maps.get(0).get("dDate").toString());
			rdo.setCexchName(CommonUtil.isNullObject(maps.get(0).get("cExch_Name"))?null:maps.get(0).get("cExch_Name").toString());
			rdo.setItaxRate(CommonUtil.isNullObject(maps.get(0).get("mainitaxrate"))?null:maps.get(0).get("mainitaxrate").toString());
			
			rdRecord.add(rdo);
			
			i++;
		}
		
		/*daoU890.uptVoucherHistory(String.valueOf(rdVoucherId), "24");//修改其它入出库编号记录 //提交委外采购入库时使用 24
*/		
		daoU890.addRdRecord(rdRecord);/////////////////////////////////////////////////////////////////////////添加其他出入库单主表
		daoU890.addRdRecords(rdRecords);///////////////////////////////////////////////////////////////////添加其他出入库单子表
		
		daoU890.addInvPosition(ips);///////////////////////////////////////////////////////////////////////添加存货货位记录表
		
		List<CurrentStock> csnew=getNewCurrentStockList(css);
		updCurrentStock(csnew);///////////////////////////////////////////////////////////////////////////修改仓库现存量
		
		updateSourceBillWhenSubmitWareHouse(null,rdRecords, Constants.TASKTYPE_WWCPRK,null);//////////////////////回填到货单已入库数量
		
		return true;
	}
	
	
	
	/**
	 * @category 修改货位现存量
	 *2016 2016年4月7日下午4:21:51
	 *boolean
	 *宋银海
	 */
	public boolean updCurrentStock(List<CurrentStock> asList) {
		
		StringBuffer sbf=new StringBuffer();
		for(CurrentStock vi:asList){
			sbf.append("'"+vi.getCinvCode()+"',");
		}
		
		String condition=sbf.toString();
		if(condition.length()>0){
			condition=condition.substring(0, condition.length()-1);
			condition=" cinvCode in ("+condition+")";
		}
		
		List<CurrentStock> currentStock=daoU890.getAllocationStock(condition);//查询
		
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
			daoU890.batchUptCurrentStock(toUpdate);
		}
		
		if(toADD.size()>0){
			daoU890.batchInsertCurrentStock(toADD);
		}
		
		return true;
	}
	
	//根据规则获得单据编号
	public String getbillCode(String cardNumber){
		Map rule=daoU890.getVoucherNumber(cardNumber);//获得订单规则
		
		StringBuffer sbf=new StringBuffer();
		if(!CommonUtil.isNullObject(rule.get("prefix1"))){/////////////////////////第一前缀 
			if(rule.get("prefix1").toString().equalsIgnoreCase("手工输入")){
				sbf.append(rule.get("prefix1Rule").toString());
			}else if(rule.get("prefix1").toString().equalsIgnoreCase("远程号")){
				sbf.append("00");
			}
		}
		
		if(!CommonUtil.isNullObject(rule.get("prefix2"))){/////////////////////////第二前缀 
			if(rule.get("prefix2").toString().equalsIgnoreCase("日期")){
				
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
				
				if(rule.get("prefix2Rule").toString().equalsIgnoreCase("年月") && rule.get("prefix2Len").toString().equalsIgnoreCase("4")){
					sbf.append(String.valueOf(year).substring(2, 4)).append(strMonth);
				}else if(rule.get("prefix2Rule").toString().equalsIgnoreCase("年月日") && rule.get("prefix2Len").toString().equalsIgnoreCase("6")){
					sbf.append(String.valueOf(year).substring(2, 4)).append(strMonth).append(strDay);
				}
				
			}
		}
		//////////////////////////第三前缀 视项目情况待完善
		
		//////////////////////流水glideLen
		String condition=" CardNumber='"+cardNumber+"'";
		Map rdVoucherHistory=daoU890.getVoucherHistory(condition);
		int rdVoucherId=Integer.parseInt(rdVoucherHistory.get("cNumber").toString())+1;
		
		String rdOutBillCode="000000000"+rdVoucherId;
		rdOutBillCode=rdOutBillCode.substring(rdOutBillCode.length()-Integer.parseInt(rule.get("glideLen").toString()), rdOutBillCode.length());
		
		sbf.append(rdOutBillCode);
		return sbf.toString();
	}
	
	/**
	 * @category 获得批次
	 *2016 2016年4月22日上午9:04:05
	 *void
	 *宋银海
	 */
	public String getBatchCode(){
		String strResult="";
		int i=daoU890.getLotnumbersequencegist();
		if(i==0){
			daoU890.addLotnumbersequencegist();
			strResult=DateUtil.getCurrDate(DateUtil.FORMAT_FOUR)+"001";
			return strResult;
		}else if(i==1){
			
			Map map=daoU890.getLotnumbersequencegistMap();
			
			int j=Integer.parseInt(map.get("cnumber").toString());
			
			String number=("000"+String.valueOf(j));
			number=number.substring(number.length()-3, number.length());
			strResult=DateUtil.getCurrDate(DateUtil.FORMAT_FOUR)+number;
			j++;
			
			daoU890.updLotnumbersequencegist(j);
			return strResult;
		}
		return "";
	}
	
	
	
	
}
