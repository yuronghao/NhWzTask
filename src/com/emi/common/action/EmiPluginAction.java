package com.emi.common.action;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import com.emi.common.service.EmiPluginService;


/**
 * 文件上传插件
 * 2015年6月24日11:06:30
 */
public class EmiPluginAction extends BaseAction {
	private static final long serialVersionUID = 5873260984216825353L;
	private EmiPluginService emiPluginService;
	
	public EmiPluginService getEmiPluginService() {
		return emiPluginService;
	}


	public void setEmiPluginService(EmiPluginService emiPluginService) {
		this.emiPluginService = emiPluginService;
	}

	/**
	 * @category 单据分页的各个id
	 * 2015年12月15日 上午9:55:23 
	 * @author 朱晓陈
	 */
	@SuppressWarnings("rawtypes")
	public void getPageTurnIds(){
		try {
			String tableName = getParameter("tableName");//表名
			String idColumn = getParameter("idColumn");	//id的字段名
			String thisGid = getParameter("thisGid");	//当前数据的id
			String condition = getParameter("condition");	//其他过滤条件
//			JSONObject condition_json = JSONObject.fromObject(condition); 
			
			String pre_id = emiPluginService.getPrePageGid(tableName,idColumn,thisGid,condition);
			String next_id = emiPluginService.getNextPageGid(tableName,idColumn,thisGid,condition);
			String first_id = emiPluginService.getFirstPageGid(tableName,idColumn,thisGid,condition);
			String last_id = emiPluginService.getLastPageGid(tableName,idColumn,thisGid,condition);
			
			Map map = new HashMap();
			map.put("first_id", first_id);
			map.put("pre_id", pre_id);
			map.put("next_id", next_id);
			map.put("last_id", last_id);
			resWrite(JSONObject.fromObject(map).toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
}
