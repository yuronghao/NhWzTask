package com.emi.web.u890.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.emi.common.action.BaseAction;
import com.emi.common.util.Base64;
import com.emi.sys.core.format.EmiJsonArray;
import com.emi.sys.core.format.EmiJsonObj;
import com.emi.web.u890.bean.BasPart;
import com.emi.web.u890.service.ServiceU890;

public class Action extends BaseAction{

	private static final long serialVersionUID = -9174171476588684837L;
	private ServiceU890 serviceU890;
	
	public void setServiceU890(ServiceU890 serviceU890) {
		this.serviceU890 = serviceU890;
	}



	/**
	 * @category 添加采购入库
	 *2016 2016年4月22日上午9:04:05
	 *void
	 *宋银海
	 */
	public void addPoWareHouse(){
		
		try {
			String json=getParameter("json");
			JSONObject jobj=JSONObject.fromObject(json);
			boolean ok=serviceU890.addPoWareHouse(jobj);
			if(ok){
				this.writeSuccess();
			}else{
				this.writeError();
			}
			
		} catch (Exception e){
			e.printStackTrace();
			this.writeError();
		}
	} 
	
	
	/**
	 * @category 添加销售出库(通过xml 插库)  不建议使用，如果存在多个仓库，事务不会回滚  目前未被使用
	 *2016 2016年4月22日下午1:02:21
	 *void
	 *宋银海
	 */
	public void addSaleOutXml(){
		
		try {
			String json=getParameter("json");
			JSONObject jobj=JSONObject.fromObject(json);
			boolean ok=serviceU890.addSaleOutXml(jobj);
			if(ok){
				this.writeSuccess();
			}else{
				this.writeError();
			}
		} catch (Exception e){
			e.printStackTrace();
			this.writeError();
		}
	} 
	
	
	/**
	 * @category 添加销售出库(直接插库);
	 *2016 2016年4月22日下午1:02:21
	 *void
	 *宋银海
	 */
	public void addSaleOut(){
		
		try {
			String json=getParameter("json");
			JSONObject jobj=JSONObject.fromObject(json);
			boolean ok=serviceU890.addSaleOut(jobj);
			if(ok){
				this.writeSuccess();
			}else{
				this.writeError();
			}
		} catch (Exception e){
			e.printStackTrace();
			this.writeError();
		}
	} 
	
	/**
	 * @category 添加BOM
	 * 2016年5月9日 上午8:53:30 
	 * @author zhuxiaochen
	 */
	public void addBom(){
		try {
			String json=getParameter("json");
			JSONArray jobj=EmiJsonArray.fromObject(json);
			serviceU890.addBom(jobj);
			this.writeSuccess();
		} catch (Exception e){
			e.printStackTrace();
			this.writeError();
		}
	}
	
	/**
	 * @category 得到物料清单(bas_part)
	 * 2016年5月11日 上午11:22:56 
	 * @author zhuxiaochen
	 */
	public void getBasPartList(){
		try {
			String invCodes = Base64.getFromBase64(getParameter("invCodes"));
			List<BasPart> partList = serviceU890.getBasPartList(invCodes);
			Map map = new HashMap();
			if(partList==null){
				map.put("success", 0);
			}else{
				map.put("success", 1);
				map.put("data", partList);
			}
			
			this.resWrite(EmiJsonObj.fromObject(map).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * @category 添加工序材料出库(通过xml 插库)  不建议使用，如果存在多个仓库，事务不会回滚      目前未被使用
	 *2016 2016年5月10日上午8:42:04
	 *void
	 *宋银海
	 */
	public void addProcessMaterialOutXml(){
		
		try {
			String json=getParameter("json");
			JSONObject jobj=JSONObject.fromObject(json);
			boolean ok=serviceU890.addProcessMaterialOutXml(jobj);
			if(ok){
				this.writeSuccess();
			}else{
				this.writeError();
			}
		} catch (Exception e){
			e.printStackTrace();
			this.writeError();
		}
	} 
	
	/**
	 * @category 添加工序材料出库、退库(直接插库)  android 提交生产退料   数量为+
	 *2016 2016年5月10日上午8:42:04
	 *void
	 *宋银海
	 */
	public void addProcessMaterialOut(){
		try {
			String json=getParameter("json");
			JSONObject jobj=JSONObject.fromObject(json);
			boolean ok=serviceU890.addProcessMaterialOut(jobj);
			if(ok){
				this.writeSuccess();
			}else{
				this.writeError();
			}
		} catch (Exception e){
			e.printStackTrace();
			this.writeError();
		}
	} 
	
	/**
	 * @category 添加工序成品入库(xml)
	 *2016 2016年5月10日上午8:42:04
	 *void
	 *宋银海
	 */
	public void addProductionWarehouse(){
		
		try {
			String json=getParameter("json");
			JSONObject jobj=JSONObject.fromObject(json);
			boolean ok=serviceU890.addProductionWarehouse(jobj);
			if(ok){
				this.writeSuccess();
			}else{
				this.writeError();
			}
		} catch (Exception e){
			e.printStackTrace();
			this.writeError();
		}
	} 
	
	
	/**
	 * @category 添加调拨单（直接插库）
	 *2016 2016年5月10日上午8:42:04
	 *void
	 *宋银海
	 */
	public void addTransVouch(){
		
		try {
			String json=getParameter("json");
			JSONObject jobj=JSONObject.fromObject(json);
			boolean ok=serviceU890.addTransVouch(jobj);
			if(ok){
				this.writeSuccess();
			}else{
				this.writeError();
			}
		} catch (Exception e){
			e.printStackTrace();
			this.writeError();
		}
	} 
	
	
	
	/**
	 * @category 添加委外材料出库（直接插库）
	 *2016 2016年4月22日下午1:02:21
	 *void
	 *宋银海
	 */
	public void addOmMeterialOut(){
		
		try {
			String json=getParameter("json");
			JSONObject jobj=JSONObject.fromObject(json);
			boolean ok=serviceU890.addOmMeterialOut(jobj);
			if(ok){
				this.writeSuccess();
			}else{
				this.writeError();
			}
		} catch (Exception e){
			e.printStackTrace();
			this.writeError();
		}
	} 
	
		
	
	
	/**
	 * @category 添加委外成品入库（直接插库）
	 *2016 2016年4月22日上午9:04:05
	 *void
	 *宋银海
	 */
	public void addOmProductIn(){
		
		try {
			String json=getParameter("json");
			JSONObject jobj=JSONObject.fromObject(json);
			boolean ok=serviceU890.addOmProductIn(jobj);
			if(ok){
				this.writeSuccess();
			}else{
				this.writeError();
			}
			
		} catch (Exception e){
			e.printStackTrace();
			this.writeError();
		}
	} 
	
	/**
	 * @category 获得批次
	 *2016 2016年4月22日上午9:04:05
	 *void
	 *宋银海
	 */
	public void getBatchCode(){
		try {
			String batch=serviceU890.getBatchCode();
			this.resWrite(batch);
			
		} catch (Exception e){
			e.printStackTrace();
			this.resWrite("");
		}
	}
	
	
}
