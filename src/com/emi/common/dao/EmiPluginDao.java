package com.emi.common.dao;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Iterator;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;

import net.sf.json.JSONObject;

import com.emi.common.util.CommonUtil;
import com.emi.wms.bean.Task;

@SuppressWarnings("rawtypes")
public class EmiPluginDao extends BaseDao{
	String pk = "pk";
	/**
	 * @category 上张 gid
	 * 2015年12月15日 上午10:30:28 
	 * @author 朱晓陈
	 * @param tableName 表名
	 * @param idColumn id字段名
	 * @param thisGid 当前id
	 * @param condition 
	 * @return
	 */
	public String getPrePageGid(String tableName, String idColumn,
			String thisGid, String condition) {
		String sql = "select top(1) "+idColumn+" from "+tableName+" "
				+ "where "+pk+">(select "+pk+" from "+tableName+" where "+idColumn+"='"+thisGid+"') "
				+ condition
				+ " order by "+pk+" asc";
		Map map = this.queryForMap(sql);
		if(map == null){
			return "";
		}else{
			return CommonUtil.Obj2String(map.get(idColumn)) ;
		}
	}

	/**
	 * @category 下张 gid
	 * 2015年12月15日 上午10:30:28 
	 * @author 朱晓陈
	 * @param tableName 表名
	 * @param idColumn id字段名
	 * @param thisGid 当前id
	 * @return
	 */
	public String getNextPageGid(String tableName, String idColumn,
			String thisGid, String condition) {
		String sql = "select top(1) "+idColumn+" from "+tableName+" "
				+ "where "+pk+"<(select "+pk+" from "+tableName+" where "+idColumn+"='"+thisGid+"') "
				+ condition
				+ " order by "+pk+" desc";
		Map map = this.queryForMap(sql);
		if(map == null){
			return "";
		}else{
			return CommonUtil.Obj2String(map.get(idColumn)) ;
		}
	}

	/**
	 * @category 首张 gid
	 * 2015年12月15日 上午10:30:28 
	 * @author 朱晓陈
	 * @param tableName 表名
	 * @param idColumn id字段名
	 * @param thisGid 当前id
	 * @return
	 */
	public String getFirstPageGid(String tableName, String idColumn,
			String thisGid, String condition) {
		String sql = "select top(1) "+idColumn+" from "+tableName+" "
				+ "where "+pk+">(select "+pk+" from "+tableName+" where "+idColumn+"='"+thisGid+"') "
				+ condition
				+ " order by "+pk+" desc";
		Map map = this.queryForMap(sql);
		if(map == null){
			return "";
		}else{
			return CommonUtil.Obj2String(map.get(idColumn)) ;
		}
	}

	/**
	 * @category 末张 gid
	 * 2015年12月15日 上午10:30:28 
	 * @author 朱晓陈
	 * @param tableName 表名
	 * @param idColumn id字段名
	 * @param thisGid 当前id
	 * @return
	 */
	public String getLastPageGid(String tableName, String idColumn,
			String thisGid, String condition) {
		String sql = "select top(1) "+idColumn+" from "+tableName+" "
				+ "where "+pk+"<(select "+pk+" from "+tableName+" where "+idColumn+"='"+thisGid+"') "
				+ condition
				+ " order by "+pk+" asc";
		Map map = this.queryForMap(sql);
		if(map == null){
			return "";
		}else{
			return CommonUtil.Obj2String(map.get(idColumn)) ;
		}
	}
	
	/*
	 * 将条件json转成sql
	 
	private String formatCondition(JSONObject condition_json) {
		String conditionSql = "";
		Iterator it = condition_json.keys();
		while (it.hasNext()) {
			String key = (String) it.next();
            String value = condition_json.getString(key);
            conditionSql += " and "+key+"='"+value+"'";
		}
		return conditionSql;
	}*/


	//根据货位查询仓库
	public Map getWareHouseByGoodsAllocationGid(String condition){
		String sql="SELECT wh.whCode from AA_GoodsAllocation ga LEFT JOIN AA_WareHouse wh on ga.whUid=wh.gid where "+condition;
		return this.queryForMap(sql);
	}
	
	
	/**
	 * @category 获得参数
	 *2016 2016年4月23日上午9:28:33
	 *Map
	 *宋银海
	 */
	public Map getymsetting(String condition){
		String sql ="select paramValue from YM_Settings where "+condition;
		return (Map)this.queryForMap(sql);
	}
	
	/**
	 * @category 根据gid 获得任务
	 *2016 2016年4月23日下午3:35:29
	 *Task
	 *宋银海
	 */
	public Task getTaskByGid(String condition){
		String sql ="select "+CommonUtil.colsFromBean(Task.class)+" from wm_task where "+condition;
		return (Task)this.emiQuery(sql, Task.class);
	}
	
	/**
	 * @category 调用本地存储过程 返回编码
	 *2016 2016年4月11日下午3:30:20
	 *String
	 *宋银海
	 */
	public String getBillId(final String billType, final String preFix) {
		return this.getJdbcTemplate().execute(new ConnectionCallback() {
			@Override
			public Object doInConnection(Connection arg0) throws SQLException,DataAccessException {
				
				String psql = "{call YMProc_GetVouchGlide(?,?,?)}"; 
				String currentId=null;
				
				CallableStatement call = arg0.prepareCall(psql);
				
				call.setString(1, billType);
				call.setString(2, preFix);
				call.registerOutParameter(3, Types.NVARCHAR);
				call.execute();
				
	            currentId=call.getString(3);
				
	            call.close();
				return currentId;
			}
		}).toString();
	}
	
	
	public String getBillIdForYonYou(final String billType, final String preFix) {
		return this.getJdbcTemplate().execute(new ConnectionCallback() {
			@Override
			public Object doInConnection(Connection arg0) throws SQLException,DataAccessException {
				
				String psql = "{call YMProc_GetVouchGlideForYonYou(?,?,?)}"; 
				String currentId=null;
				
				CallableStatement call = arg0.prepareCall(psql);
				
				call.setString(1, billType);
				call.setString(2, preFix);
				call.registerOutParameter(3, Types.NVARCHAR);
				call.execute();
				
	            currentId=call.getString(3);
				
	            call.close();
				return currentId;
			}
		}).toString();
	}
	
}
