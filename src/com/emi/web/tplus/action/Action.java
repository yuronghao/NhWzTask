package com.emi.web.tplus.action;


import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.emi.common.action.BaseAction;
import com.emi.common.util.CommonUtil;
import com.emi.sys.init.Config;
import com.emi.web.tplus.service.ServiceTplus;

public class Action extends BaseAction{

	private static final long serialVersionUID = -9174171476588684837L;
	private ServiceTplus serviceTplus;
	
	public void setServiceTplus(ServiceTplus serviceTplus) {
		this.serviceTplus = serviceTplus;
	}

	/**
	 * @category 添加工序材料出库、退库(直接插库)
	 *2016 2016年5月10日上午8:42:04
	 *void
	 *宋银海
	 */
	public void addProcessMaterialOut(){
		try {
			String json=getParameter("json");
			JSONObject jobj=JSONObject.fromObject(json);
			boolean ok=serviceTplus.addProcessMaterialOut(jobj);
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
	 * @category 添加工序成品入库
	 *2016 2016年5月10日上午8:42:04
	 *void
	 *宋银海
	 */
	public void addProductionWarehouse(){
		
		try {
			String json=getParameter("json");
			JSONObject jobj=JSONObject.fromObject(json);
			boolean ok=serviceTplus.addProductionWarehouse(jobj);
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
	 * @category 直接添加成品入库
	 *2016 2016年12月30日上午9:16:12
	 *void
	 *宋银海
	 */
	public void addProductionWarehouseDirect(){
		
		try {
			String json=getParameter("json");
			JSONObject jobj=JSONObject.fromObject(json);
			boolean ok=serviceTplus.addProductionWarehouseDirect(jobj);
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
	 * @category 添加销售出库单
	 *2016 2016年12月28日下午4:09:23
	 *void
	 *宋银海
	 */
	public void addSaleOut(){
		try {
			String json=getParameter("json");
			JSONObject jobj=JSONObject.fromObject(json);
			boolean ok=serviceTplus.addSaleOut(jobj);
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
	
	
	
	public String tosystemsetting(){
		List systemsetting = serviceTplus.getsystemsetting();
		for(int i=0;i<systemsetting.size();i++){
			if(!CommonUtil.isNullObject(((Map)systemsetting.get(i)).get("setName"))){
				setRequstAttribute(((Map)systemsetting.get(i)).get("setName").toString(), ((Map)systemsetting.get(i)).get("paramValue").toString());
			}
		}
		return "systemsetting";
	}

	public void updatesystemsetting(){
		try {
			String msg = "";
			boolean pass = true;
			
			if(pass){
				boolean suc = serviceTplus.updatesystemsetting(getParameter("synchroType"),getParameter("eaiAddress"),getParameter("eaiCode"));
				Config.synchroType= getParameter("synchroType");
				Config.eaiAddress= getParameter("eaiAddress");
				Config.EAICODE= getParameter("eaiCode");
				if(suc){
					getResponse().getWriter().write("success");
				}else{
					getResponse().getWriter().write("保存失败");
				}
			}else{
				getResponse().getWriter().write(msg);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	
	
	

	
	
	
}
