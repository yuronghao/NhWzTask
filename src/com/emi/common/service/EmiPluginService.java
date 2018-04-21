package com.emi.common.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.emi.common.dao.CommonFileUploadDao;
import com.emi.common.dao.EmiPluginDao;
import com.emi.common.util.CommonUtil;
import com.emi.common.util.Constants;
import com.emi.common.util.DateUtil;
import com.emi.common.util.FileUploadUtils;
import com.emi.sys.file.bean.Record_doc;
import com.emi.sys.util.SysPropertites;
import com.emi.wms.bean.Task;

public class EmiPluginService {
	private EmiPluginDao emiPluginDao;

	public void setEmiPluginDao(EmiPluginDao emiPluginDao) {
		this.emiPluginDao = emiPluginDao;
	}

	public String getPrePageGid(String tableName, String idColumn,
			String thisGid, String condition) {
		return emiPluginDao.getPrePageGid(tableName,idColumn,thisGid,condition);
	}

	public String getNextPageGid(String tableName, String idColumn,
			String thisGid, String condition) {
		return emiPluginDao.getNextPageGid(tableName, idColumn,thisGid,condition);
	}

	public String getFirstPageGid(String tableName, String idColumn,
			String thisGid, String condition) {
		return emiPluginDao.getFirstPageGid(tableName,idColumn,thisGid,condition);
	}

	public String getLastPageGid(String tableName, String idColumn,
			String thisGid, String condition) {
		return emiPluginDao.getLastPageGid(tableName,idColumn,thisGid,condition);
	}
	
	//根据货位查询仓库
	public Map getWareHouseByGoodsAllocationGid(String gid){
		String condition=" and ga.gid='"+gid+"'";
		return emiPluginDao.getWareHouseByGoodsAllocationGid(condition);
	}
	
	/**
	 * @category 获得参数
	 *2016 2016年4月23日上午9:27:26
	 *Map
	 *宋银海
	 */
	public Map getymsetting(String setName){
		String condition=" setName='"+setName+"'";
		return emiPluginDao.getymsetting(condition);
	}
	
	/**
	 * @category 根据gid 获得任务
	 *2016 2016年4月23日下午3:35:29
	 *Task
	 *宋银海
	 */
	public Task getTaskByGid(String gid){
		return emiPluginDao.getTaskByGid(gid);
	}
	
	
	/**
	 * @category 调用本地存储过程 返回编码
	 *2016 2016年4月11日下午3:30:20
	 *String
	 *宋银海
	 */
	public String getBillId(String billType){
		
		int year=DateUtil.getToYear();
		int month=DateUtil.getToMonth();
		String strMonth;
		if(String.valueOf(month).length()==1){
			strMonth="0"+String.valueOf(month);
		}else{
			strMonth=String.valueOf(month);
		}
		
		String currentId=emiPluginDao.getBillId(billType, year+strMonth);
		
		String billId=billType+year+strMonth+currentId;
		
		return billId;
	}
	
	
	public String getBillIdForYonYou(String billType){
		
		int year=DateUtil.getToYear();
		int month=DateUtil.getToMonth();
		String strMonth;
		if(String.valueOf(month).length()==1){
			strMonth="0"+String.valueOf(month);
		}else{
			strMonth=String.valueOf(month);
		}
		
		String currentId=emiPluginDao.getBillIdForYonYou(billType, year+strMonth);
		
		String billId=billType+year+strMonth+currentId;
		
		return billId;
	}
	
}