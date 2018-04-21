package com.emi.wms.synchro.dao;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.sys.init.Config;
import com.emi.wms.bean.AaWarehouse;
import com.emi.wms.bean.BillType;
import com.emi.wms.bean.OM_MOMain;
import com.emi.wms.bean.OM_MOMaterials;
import com.emi.wms.bean.Procurearrival;
import com.emi.wms.bean.ProcurearrivalC;
import com.emi.wms.bean.SaleSend;
import com.emi.wms.bean.SaleSendC;
import com.emi.wms.bean.Task;
import com.emi.wms.bean.WmAllocationstock;
import com.emi.wms.util.Constants;

public class SynchroDaoTplus extends BaseDao {
	
	
	public Map getOrgGid(){
		String sql="select gid from AA_Org order by pk desc ";
		return this.queryForMap(sql);
	}
	
	public Map getSobGid(){
		String sql="select gid from MES_WM_AccountingInform order by pk desc ";
		return this.queryForMap(sql);
	}
	
	/**
	 * @category 删除存货分类
	 *2016 2016年3月2日下午1:31:49
	 *int
	 *宋银海
	 */
	public int deleteInventoryClass(){
		String sql="delete from classify where styleGid='03' and gid not in (select id from "+Config.BUSINESSDATABASE+"AA_InventoryClass )";
		System.out.println(sql);
		return this.update(sql);
	}
	
	/**
	 * @category 增加存货分类
	 *2016 2016年3月2日下午1:55:35
	 *int
	 *Administrator
	 */
	public int addInventoryClass(){
		String sql="insert into classify(gid,classificationName,styleGid,parentid) "+
				"SELECT id,name,'03',idparent from "+Config.BUSINESSDATABASE+"AA_InventoryClass "+
				"where 1=1  and id not in (select gid from classify where styleGid='03' ) ";
		return this.update(sql);
	}
	
	/**
	 * @category 修改存货分类
	 *2016 2016年3月2日下午2:21:26
	 *int
	 *宋银海
	 */
	public int updInventoryClass(){
		String sql="update classify set classificationName=name,parentid=idparent "+
					"from classify cf LEFT JOIN "+Config.BUSINESSDATABASE+"AA_InventoryClass ic on cf.gid=ic.id "+
					"where 1=1 and styleGid='03' and "
					+ " (classificationName != name or isnull(parentid,NULL)!=isnull(idparent,NULL) )";
		return this.update(sql);
	}
	
	/**
	 * @category 设置自关联的gid
	 *2016 2016年3月4日下午3:43:05
	 *int
	 *宋银海
	 */
	public int updInventoryClassParentGid(){
		String sql="UPDATE classify "+
					"SET parentid = cfAnother.gid "+
					"FROM classify cf "+
					"LEFT JOIN (SELECT gid,codeForSynchro,layerForSynchro from classify where styleGid='03')cfAnother ON "+
					"SUBSTRING(cf.codeForSynchro, 1, LEN(cfAnother.codeForSynchro )) = cfAnother.codeForSynchro "+
					"AND cf.layerForSynchro=cfAnother.layerForSynchro+1 "+
					"WHERE 1 = 1 "+
					"AND cf.styleGid = '03' "+
					"and isnull(cf.parentid,'')<>isnull(cfAnother.gid,'') ";
		return this.update(sql);
	}
	
	/**
	 * @category 指定组织帐套
	 *2016 2016年4月5日上午8:51:38
	 *int
	 *宋银海
	 */
	public int updInventoryClassOrgSob(String orgGid,String sobGid){
		String sql="update classify set orgid='"+orgGid+"', sobId='"+sobGid+"' where isnull(sobId,'')='' or isnull(orgid,'')='' ";
		return this.update(sql);
	}
	
	
	
	/**
	 * @category 删除存货档案
	 *2016 2016年3月2日下午2:31:45
	 *int
	 *宋银海
	 */
	public int deleteInventory(){
		String sql="delete from AA_Goods where gid not in (select id from "+Config.BUSINESSDATABASE+"AA_Inventory )";
		return this.update(sql);
	}
	
	/**
	 * @category 增加存货档案
	 *2016 2016年3月2日下午2:34:59
	 *int
	 *宋银海
	 */
	public int addInventory(){
		String sql="insert into AA_Goods(gid,goodsCode,goodsName,goodsStandard,goodsUnit,goodsSortUid,bInvBach,cdefWareHouse,goodsBarCode,cstComUnitCode) "+
				"SELECT id,code,name,specification,idunit,idinventoryclass,isBatch,idwarehouse,code,case issingleUnit when 1 THEN null else idUnitByStock  end from "+Config.BUSINESSDATABASE+"AA_Inventory "+
				"where 1=1  and id not in (select gid from AA_Goods) ";
		return this.update(sql);
	}
	
	/**
	 * @category 修改存货档案
	 *2016 2016年3月2日下午3:02:34
	 *int
	 *宋银海
	 */
	public int updInventory(){
		String sql="update AA_Goods set goodsCode=code,goodsName=name,goodsStandard=specification,goodsUnit=idunit,goodsSortUid=idinventoryclass,"
				+ " bInvBach=isBatch,cdefWareHouse=idwarehouse,cstComUnitCode=case issingleUnit when 1 THEN null else idUnitByStock  end,goodsBarCode=code "+
				" from AA_Goods gs LEFT JOIN "+Config.BUSINESSDATABASE+"AA_Inventory it on gs.gid=it.id "+
				" where isnull(goodsCode,'')<>it.code OR "+
				" isnull(goodsName,'')<>it.name OR "+
				" isnull(goodsStandard,'')<>it.specification or "+
				" goodsUnit <>it.idunit OR "+
				" goodsSortUid <>it.idinventoryclass OR "+
				" isnull(gs.bInvBach,0)<>it.isBatch or "+
				" gs.cdefWareHouse<>it.idwarehouse or "
				+ " gs.cstComUnitCode <>it.idUnitByStock or "
				+ " isnull(gs.goodsBarCode,'')='' ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 更新存货档案相关字段
	 *2016 2016年3月4日下午4:13:59
	 *int
	 *宋银海 cassComUnitCode
	 */
	public int updInventoryInfor(){
		String sql="UPDATE AA_Goods "+
					"SET goodsUnit=u.gid,goodsSortUid=c.gid,cassComUnitCode=fu.gid,cstComUnitCode=stfu.gid,cdefWareHouse=wh.gid "+
					"FROM AA_Goods gs "+
					"LEFT JOIN unit u on gs.unitForSynchro=u.unitCode "+
					"LEFT JOIN unit fu on gs.cassComUnitForSynchro=fu.unitCode "+
					"LEFT JOIN unit stfu on gs.cstComUnitForSynchro=stfu.unitCode "+ 
					"LEFT JOIN classify c on gs.sortGidForSynchro=c.codeForSynchro "+ 
					"left join AA_WareHouse wh on gs.defWareHouseForSynchro=wh.whCode "+
					"WHERE isnull(gs.goodsUnit,'')<>isnull(u.gid,'') "+
					"or isnull(gs.goodsSortUid,'')<>isnull(c.gid,'') "+
					"or isnull(gs.cassComUnitCode,'')<> isnull(fu.gid,'') "+
					"or isnull(gs.cstComUnitCode,'')<> isnull(stfu.gid,'') "+
					"or isnull(gs.cdefWareHouse,'')<> isnull(wh.gid,'') ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 指定组织帐套
	 *2016 2016年4月5日上午8:52:37
	 *int
	 *宋银海
	 */
	public int updInventoryOrgSob(String orgGid,String sobGid){
		String sql="update AA_Goods set orggid='"+orgGid+"', sobgId='"+sobGid+"' where isnull(sobgId,'')='' or isnull(orggid,'')='' ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 删除部门
	 *2016 2016年3月2日下午3:42:02
	 *int
	 *宋银海
	 */
	public int deleteDepartment(){
		String sql="delete from AA_Department where gid not in (select id from "+Config.BUSINESSDATABASE+"AA_Department )";
		return this.update(sql);
	}
	
	/**
	 * @category 增加部门
	 *2016 2016年3月2日下午3:55:24
	 *int
	 *宋银海
	 */
	public int addDepartment(){
		String sql="insert into AA_Department(gid,depCode,depName,last,layer,depParentUid) select id,code,name,isEndNode,depth,idparent FROM "+Config.BUSINESSDATABASE+"AA_Department "+
					" where id not in (select gid from AA_Department);";
		return this.update(sql);
	}
	
	/**
	 * @category 修改部门
	 *2016 2016年3月2日下午3:56:09
	 *int
	 *宋银海
	 */
	public int uptDepartment(){
		String sql="update AA_Department set depCode=code,depName=d.name,last=d.isEndNode,layer=d.depth,depParentUid=idparent from AA_Department ad "+
					"left join "+Config.BUSINESSDATABASE+"AA_Department d on ad.gid=d.id "+
					"where isnull(ad.depCode,'')<>d.code OR "
					+ " isnull(ad.depName,'')<>d.name OR "+
					" isnull(ad.last,'')<>d.isEndNode OR "+
					" isnull(ad.layer,'')<>d.depth or "
					+ "isnull(ad.depParentUid,NULL)<>isnull(d.idparent,NULL)  ";
		return this.update(sql);
	}
	
	/**
	 * @category 更改部门自关联gid
	 *2016 2016年3月4日下午4:22:40
	 *int
	 *宋银海
	 */
	public int uptDepartmentParentGid(){
		String sql="UPDATE AA_Department "+
					"SET depParentUid = dAnother.gid "+ 
					"FROM AA_Department d "+
					"LEFT JOIN (SELECT gid,depCode,layer from AA_Department)dAnother ON "+ 
					"SUBSTRING(d.depCode, 1, LEN(dAnother.depCode )) = dAnother.depCode "+
					"AND d.layer=dAnother.layer+1 "+
					"WHERE  isnull(d.depParentUid,'')<>isnull(dAnother.gid,'') ";
		return this.update(sql);
	}
	
	
	
	/**
	 * @category 指定帐套组织
	 *2016 2016年4月5日上午8:57:20
	 *int
	 *宋银海
	 */
	public int updDepartmentOrgSob(String orgGid,String sobGid){
		String sql="update AA_Department set orggid='"+orgGid+"', sobgId='"+sobGid+"' where isnull(sobgId,'')='' or isnull(orggid,'')='' ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 删除客户
	 *2016 2016年3月2日下午4:14:51
	 *int
	 *宋银海
	 */
	public int deleteCustomer(){
		String sql="delete from AA_Provider_Customer where gid not in (select id from "+Config.BUSINESSDATABASE+"AA_Partner where partnerType='0C90A0C3-960D-493A-869D-72D97189E4FC' ) and flagForSynchro='c' ";
		return this.update(sql);
	}
	
	/**
	 * @category 删除供应商
	 *2016 2016年3月3日上午8:53:55
	 *int
	 *宋银海
	 */
	public int deleteProvider(){
		String sql="delete from AA_Provider_Customer where pcCode not in (select cVenCode  from "+Config.BUSINESSDATABASE+"Vendor ) and flagForSynchro='p' ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 增加客户
	 *2016 2016年3月2日下午4:30:37
	 *int
	 *宋银海
	 */
	public int addCustomer(){
		String sql="insert into AA_Provider_Customer(gid,pcCode,pcName,addr,flagForSynchro)  select id,code,name,shipmentAddress,'c'  FROM "+Config.BUSINESSDATABASE+"AA_Partner "+
					"where  partnerType='0C90A0C3-960D-493A-869D-72D97189E4FC' and id not in (select gid from AA_Provider_Customer where flagForSynchro='c' ) ";
		return this.update(sql);
	}
	
	/**
	 * @category 增加供应商
	 *2016 2016年3月3日上午8:56:44
	 *int
	 *宋银海
	 */
	public int addProvider(){
		String sql="insert into AA_Provider_Customer(gid,pcCode,pcName,flagForSynchro)  select newid(),cVenCode,cVenName,'p'  FROM "+Config.BUSINESSDATABASE+"Vendor "+
					"where  cVenCode not in (select pcCode from AA_Provider_Customer where flagForSynchro='p' ) ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改客户
	 *2016 2016年3月2日下午4:56:35
	 *int
	 *宋银海
	 */
	public int updCustomer(){
		String sql="update AA_Provider_Customer set pcName=c.name,addr=c.shipmentAddress from AA_Provider_Customer pc "+
					"left join "+Config.BUSINESSDATABASE+"AA_Partner c on pc.gid=c.id "+
					"where isnull(pc.pcName,'')<>c.name "
					+ " and pc.flagForSynchro='c' ";
		return this.update(sql);
	}
	
	/**
	 * 指定帐套组织
	 *2016 2016年4月5日下午2:08:01
	 *int
	 *宋银海
	 */
	public int updCustomerOrgSob(String orgGid,String sobGid){
		String sql="update AA_Provider_Customer set orgid='"+orgGid+"', sobId='"+sobGid+"' where isnull(sobId,'')='' or isnull(orgid,'')='' ";
		return this.update(sql);
	}
	
	/**
	 * @category 修改供应商
	 *2016 2016年3月3日上午8:59:40
	 *int
	 *宋银海
	 */
	public int updProvider(){
		String sql="update AA_Provider_Customer set pcName=v.cVenName from AA_Provider_Customer pc "+
					"left join "+Config.BUSINESSDATABASE+"Vendor v on pc.pcCode=v.cVenCode "+
					"where isnull(pc.pcName,'')<>v.cVenName "
					+ " and pc.flagForSynchro='p' ";
		return this.update(sql);
	}
	
	/**
	 * @category 指定组织帐套
	 *2016 2016年4月5日下午2:38:40
	 *int
	 *宋银海
	 */
	public int updProviderOrgSob(String orgGid,String sobGid){
		String sql="update AA_Provider_Customer set orgid='"+orgGid+"', sobId='"+sobGid+"' where isnull(sobId,'')='' or isnull(orgid,'')='' ";
		return this.update(sql);
	}
	
	/**
	 * @category 删除仓库
	 *2016 2016年3月2日下午5:03:03
	 *int
	 *宋银海
	 */
	public int deleteWareHouse(){
		String sql="delete from AA_WareHouse where gid not in (select id from "+Config.BUSINESSDATABASE+"AA_Warehouse ) ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 增加仓库
	 *2016 2016年3月2日下午5:05:43
	 *int
	 *宋银海
	 */
	public int addWareHouse(){
		String sql="insert into AA_WareHouse(gid,whCode,whName,whPos) select id,code,name,hasPosition FROM "+Config.BUSINESSDATABASE+"AA_Warehouse "+
					"where id not in (select gid from AA_WareHouse) ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改仓库
	 *2016 2016年3月2日下午5:11:23
	 *int
	 *宋银海
	 */
	public int updWareHouse(){
		
		String sql="update AA_WareHouse set whCode=code, whName=w.name,whPos=w.hasPosition from AA_WareHouse aw "+
					"left join "+Config.BUSINESSDATABASE+"AA_Warehouse w on aw.gid=w.id "+
					"where isnull(aw.whCode,'')<>w.code OR "
					+ " isnull(aw.whName,'')<>w.name OR "+
					"isnull(aw.whPos,0)<>w.hasPosition ";
		return this.update(sql);
	}
	
	/**
	 * 指定帐套组织
	 *2016 2016年4月5日下午2:23:33
	 *int
	 *宋银海
	 */
	public int updWareHouseOrgSob(String orgGid,String sobGid){
		String sql="update AA_WareHouse set orggid='"+orgGid+"', sobgId='"+sobGid+"' where isnull(sobgId,'')='' or isnull(orggid,'')='' ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 删除货位
	 *2016 2016年3月2日下午5:22:50
	 *int
	 *宋银海
	 */
	public int deleteGoodsAllocation(){
		String sql="delete from AA_GoodsAllocation where gid not in (select id from "+Config.BUSINESSDATABASE+"AA_InventoryLocation ) ";
		return this.update(sql);
	}
	
	/**
	 * @category 增加货位
	 *2016 2016年3月2日下午5:29:36
	 *int
	 *宋银海
	 */
	public int addGoodsAllocation(){
		String sql="insert into AA_GoodsAllocation(gid,code,name,whUid,allocationBarCode,posEnd,posGrade)  "
				+ " select id,code,name,idWarehouse,code,isEndNode,depth  FROM "+Config.BUSINESSDATABASE+"AA_InventoryLocation "+
					"where id not in (select gid from AA_GoodsAllocation);";
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改货位
	 *2016 2016年3月2日下午5:30:11
	 *int
	 *宋银海
	 */
	public int updGoodsAllocation(){
		String sql="update AA_GoodsAllocation set code=p.code,name=p.name,whUid=p.idWarehouse,allocationBarCode=p.code,"
				+ " posEnd=isEndNode,posGrade=depth from  AA_GoodsAllocation ga "+
					"left join "+Config.BUSINESSDATABASE+"AA_InventoryLocation p on ga.gid=p.id "+
					"where isnull(ga.code,'')<>p.code OR "
					+ " isnull(ga.name,'')<>p.name OR "+
					" ga.whUid<>p.idWarehouse or "
					+ " isnull(ga.posEnd,0)<>p.isEndNode or "
					+ " isnull(ga.posGrade,0)<>p.depth  ";
		return this.update(sql);
	}
	
	/**
	 * @category 修改货位相关属性字段
	 *2016 2016年3月4日下午4:45:44
	 *int
	 *宋银海
	 */
	public int updGoodsAllocationInfor(){
		String sql="UPDATE AA_GoodsAllocation "+
					"SET whUid = wh.gid "+
					"FROM AA_GoodsAllocation ga "+
					"LEFT JOIN AA_WareHouse wh ON "+
					"ga.whForSynchro=wh.whCode "+
					"WHERE  isnull(ga.whUid,'')<>isnull(wh.gid,'') and isnull(whForSynchro,'')<>'' ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 指定帐套组织
	 *2016 2016年4月5日下午2:25:55
	 *int
	 *宋银海
	 */
	public int updGoodsAllocationOrgSob(String orgGid,String sobGid){
		String sql="update AA_GoodsAllocation set orggid='"+orgGid+"', sobgId='"+sobGid+"' where isnull(sobgId,'')='' or isnull(orggid,'')='' ";
		return this.update(sql);
	}
	
	
	
	/**
	 * @category 删除计量单位
	 *2016 2016年3月3日上午8:33:09
	 *int
	 *宋银海
	 */
	public int deleteUnit(){
		String sql="delete from unit where isnull(gid,'') not in (select id from "+Config.BUSINESSDATABASE+"AA_Unit ) ";
		return this.update(sql);
	}
	
	/**
	 * @category 增加计量单位
	 *2016 2016年3月3日上午8:37:25
	 *int
	 *宋银海
	 */
	public int addUnit(){
		
		String sql="insert into unit(gid,unitCode,unitName) "+
				   " select id,code,name  FROM "+Config.BUSINESSDATABASE+"AA_Unit "+
				   " where  id not in (select gid from unit)";
		
		return this.update(sql);
	}
	
	/**
	 * @category 修改计量单位
	 *2016 2016年3月3日上午8:42:00
	 *int
	 *宋银海
	 */
	public int updUnit(){
		String sql="update unit set unitName=cu.name,unitCode=cu.code from unit u "+
					"left join "+Config.BUSINESSDATABASE+"AA_Unit cu on u.gid=cu.id "+
					"where isnull(u.unitName,'')<>cu.name or "
					+ " isnull(u.unitCode,'')<>cu.code ";
		return this.update(sql);
	}
	
	/**
	 * @category 指定组织帐套
	 *2016 2016年4月5日上午8:47:54
	 *int
	 *宋银海
	 */
	public int updUnitOrgSob(String orgGid,String sobGid){
		String sql="update unit set orgid='"+orgGid+"', sobId='"+sobGid+"' where isnull(sobId,'')='' or isnull(orgid,'')='' ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 删除用户
	 *2016 2016年3月3日上午9:06:06
	 *int
	 *宋银海
	 */
	public int deleteUser(){
		String sql="";
		return this.update(sql);
	}
	
	/**
	 * @category 增加用户
	 *2016 2016年3月3日上午9:29:29
	 *int
	 *宋银海
	 */
	public int addUser(){
		String sql="insert into YM_User(gid,userCode,userName)  select id,code,name FROM "+Config.BUSINESSDATABASE+"EAP_User "+
					"where id not in (select gid from YM_User) ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改用户
	 *2016 2016年3月3日上午9:41:12
	 *int
	 *宋银海
	 */
	public int updUser(){
		String sql="update YM_User set userCode=code,userName=u.name from YM_User yu "+
					"left join "+Config.BUSINESSDATABASE+"EAP_User u on yu.gid=u.id "+
					"where isnull(yu.userName,'')<>u.name or "
					+ " isnull(yu.userCode,'')<>u.code ";
		return this.update(sql);
	}
	
	/**
	 * @category 指定帐套组织
	 *2016 2016年4月5日下午2:43:38
	 *int
	 *宋银海
	 */
	public int updUserOrgSob(String orgGid,String sobGid){
		String sql="update YM_User set orggid='"+orgGid+"', sobgId='"+sobGid+"' where isnull(sobgId,'')='' or isnull(orggid,'')='' ";
		return this.update(sql);
	}
	
	/**
	 * @category 删除人员
	 *2016 2016年3月3日上午9:48:14
	 *int
	 *宋银海
	 */
	public int deletePerson(){
		String sql="delete from AA_Person where gid not in (select id from "+Config.BUSINESSDATABASE+"AA_Person ) ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 增加人员
	 *2016 2016年3月3日上午9:53:41
	 *int
	 *宋银海
	 */
	public int addPerson(){
		String sql="insert into AA_Person(gid,perCode,perName,depGid)  select id,code,name,iddepartment  FROM "+Config.BUSINESSDATABASE+"AA_Person "+
					"where  id not in (select gid from AA_Person)";
		return this.update(sql);
	}
	
	/**
	 * @category 修改人员
	 *2016 2016年3月3日上午9:56:36
	 *int
	 *宋银海
	 */
	public int updPerson(){
		String sql="update AA_Person set perCode=code,perName=p.name,depGid=p.iddepartment,barcode='P'+code from AA_Person ap "+
					"left join "+Config.BUSINESSDATABASE+"AA_Person p on ap.gid=p.id "+
					"where isnull(ap.perCode,'')<>p.code or "
					+ " isnull(ap.perName,'')<>p.name or "+
					" ap.depGid <>p.iddepartment or "
					+ "isnull(ap.barcode,'')<>'P'+p.code " ;
		return this.update(sql);
	}
	
	
	/**
	 * @category 更改人员相关属性
	 *2016 2016年3月4日下午4:56:48
	 *int
	 *宋银海
	 */
	public int updPersonInfor(){
		String sql="UPDATE AA_Person "+
					"SET depGid = d.gid "+
					"FROM AA_Person p "+
					"LEFT JOIN AA_Department d ON "+ 
					"p.depCodeForSynchro=d.depCode "+
					"WHERE  isnull(p.depGid,'')<>isnull(d.gid,'') ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 指定帐套组织
	 *2016 2016年4月5日下午2:45:12
	 *int
	 *宋银海
	 */
	public int updPersonOrgSob(String orgGid,String sobGid){
		String sql="update AA_Person set orggid='"+orgGid+"', sobgId='"+sobGid+"' where isnull(sobgId,'')='' or isnull(orggid,'')='' ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 删除收发类别
	 *2016 2016年4月23日下午3:13:42
	 *int
	 *宋银海
	 */
	public int deleteRdStyle(){
		String sql="delete from YM_RdStyle where gid not in (select id from "+Config.BUSINESSDATABASE+"AA_RDStyle ) ";
		return this.update(sql);
	}
	
	/**
	 * @category 删除用户自定义项
	 *2016 2016年4月23日下午3:13:42
	 *int
	 *宋银海
	 */
	public int deleteUserDefine(){
		String sql="delete from AA_UserDefine where code not in (select cAlias from "+Config.BUSINESSDATABASE+"UserDefine ) ";
		return this.update(sql);
	}
	
	/**
	 * @category 增加收发类别
	 *2016 2016年3月3日上午9:53:41
	 *int
	 *宋银海
	 */
	public int addRdStyle(){
		String sql="insert into YM_RdStyle(gid,crdCode,crdName,irdGrade,brdEnd,codeForSynchro)  select id,code,name,depth,isEndNode,inId  FROM "+Config.BUSINESSDATABASE+"AA_RDStyle "+
					"where  id not in (select gid from YM_RdStyle)";
		return this.update(sql);
	}
	
	/**
	 * @category 增加用户自定义项
	 *2016 2016年3月3日上午9:53:41
	 *int
	 *宋银海
	 */
	public int addUserDefine(){
		String sql="insert into AA_UserDefine(gid,code,value)  select newid(),cAlias,cValue  FROM "+Config.BUSINESSDATABASE+"UserDefine "+
					"where  cAlias not in (select code from AA_UserDefine)";
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改收发类别
	 *2016 2016年3月3日上午9:56:36
	 *int
	 *宋银海
	 */
	public int updRdStyle(){
		String sql="update YM_RdStyle set crdCode=rs.code,crdName=rs.name,irdGrade=rs.depth,brdEnd=rs.isEndNode,codeForSynchro=inId from YM_RdStyle rd "+
					"left join "+Config.BUSINESSDATABASE+"AA_RDStyle rs on rd.gid=rs.id "+
					"where isnull(rd.crdCode,'')<>rs.code OR "
					+ " isnull(rd.crdName,'')<>rs.name OR "
					+ " isnull(rd.irdGrade,0)<>rs.depth or "
					+ " isnull(rd.brdEnd,0)<>rs.isEndNode or "
					+ " isnull(rd.codeForSynchro,0)<>rs.inId ";
		return this.update(sql);
	}
	
	//从t+ 查询收发类别
	public Map getRdStyle(String condition){
		String sql="select id,code,name from "+Config.BUSINESSDATABASE+"AA_RDStyle where "+condition;
		return this.queryForMap(sql);
	}
	
	//修改收发类别   收标志
	public int updRdStyleS(String sId){
		String sql="update YM_RdStyle set irdflag =1 where codeForSynchro like '"+sId+"%' and isnull(irdflag,-1) !=1 ";
		return this.update(sql);
	}
	
	//修改收发类别   发标志
	public int updRdStyleF(String sId){
		String sql="update YM_RdStyle set irdflag =0 where codeForSynchro like '"+sId+"%' and isnull(irdflag,-1) !=0 ";
		return this.update(sql);
	}
	
	/**
	 * @category 修改用户自定义项
	 *2016 2016年3月3日上午9:56:36
	 *int
	 *宋银海
	 */
	public int updUserDefine(){
		String sql="update AA_UserDefine set value=cValue from AA_UserDefine udwms "+
					"left join "+Config.BUSINESSDATABASE+"UserDefine udyy on udwms.code=udyy.cAlias "+
					"where isnull(udwms.value,'')<>udyy.cValue ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 指定帐套组织
	 *2016 2016年4月5日下午2:45:12
	 *int
	 *宋银海
	 */
	public int updRdStyleOrgSob(String orgGid,String sobGid){
		String sql="update YM_RdStyle set orggid='"+orgGid+"', sobgId='"+sobGid+"' where isnull(sobgId,'')='' or isnull(orggid,'')='' ";
		return this.update(sql);
	}
	
	/**
	 * @category 指定帐套组织
	 *2016 2016年4月5日下午2:45:12
	 *int
	 *宋银海
	 */
	public int updUserDefineOrgSob(String orgGid,String sobGid){
		String sql="update AA_UserDefine set orggid='"+orgGid+"', sobgId='"+sobGid+"' where isnull(sobgId,'')='' or isnull(orggid,'')='' ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 删除工作中心
	 *2016 2016年3月3日上午10:33:27
	 *int
	 *宋银海
	 */
	public int deleteWorkCenter(){
		String sql="delete from MES_AA_WorkCenter where wccode not in (select wccode from "+Config.BUSINESSDATABASE+"sfc_workcenter ) ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 增加工作中心
	 *2016 2016年3月3日上午10:39:37
	 *int
	 *宋银海
	 */
	public int addWorkCenter(){
		String sql="insert into MES_AA_WorkCenter(gid,wccode,wcname,depCodeForSynchro) select newid(), wccode,description,deptCode  FROM "+Config.BUSINESSDATABASE+"sfc_workcenter "+
					"where  wccode not in (select wccode from MES_AA_WorkCenter)";
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改工作中心
	 *2016 2016年3月3日上午10:46:07
	 *int
	 *宋银海
	 */
	public int updWorkCenter(){
		String sql="update MES_AA_WorkCenter set wcname=wk.description,depCodeForSynchro=wk.deptCode from MES_AA_WorkCenter aw "+
					"left join "+Config.BUSINESSDATABASE+"sfc_workcenter wk  on aw.wccode=wk.wccode "+
					"where isnull(aw.wcname,'')<>wk.description OR "+
					"isnull(aw.depCodeForSynchro,'')<>wk.deptCode";
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改工作中心相关属性
	 *2016 2016年3月7日上午8:39:37
	 *int
	 *宋银海
	 */
	public int updWorkCenterInfor(){
		String sql="UPDATE MES_AA_WorkCenter "+
					"SET depCode = d.gid "+
					"FROM MES_AA_WorkCenter wc "+
					"LEFT JOIN AA_Department d ON "+
					"wc.depCodeForSynchro=d.depCode "+
					"WHERE  isnull(wc.depCode,'')<>isnull(d.gid,'')";
		return this.update(sql);
	}
	
	
	/**
	 * @category 指定帐套组织
	 *2016 2016年4月5日下午2:47:09
	 *int
	 *宋银海
	 */
	public int updWorkCenterOrgSob(String orgGid,String sobGid){
		String sql="update MES_AA_WorkCenter set orggid='"+orgGid+"', sobgId='"+sobGid+"' where isnull(sobgId,'')='' or isnull(orggid,'')='' ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 删除标准工序
	 *2016 2016年3月3日上午10:53:27
	 *int
	 *宋银海
	 */
	public int deleteStandardprocess(){
		String sql="delete from mes_wm_standardprocess where opcode not in (select opCode from "+Config.BUSINESSDATABASE+"sfc_operation ) ";
		return this.update(sql);
	}
	
	/**
	 * @category 增加标准工序
	 *2016 2016年3月3日下午2:49:38
	 *int
	 *宋银海
	 */
	public int addStandardprocess(){
		String sql="insert into mes_wm_standardprocess(gid,opcode,opname)  select newid(),opCode,description FROM "+Config.BUSINESSDATABASE+"sfc_operation "+
					"where  opCode not in (select opcode from mes_wm_standardprocess) ";
		return this.update(sql);
	}
	
	/**
	 * @category 修改标准工序
	 *2016 2016年3月3日下午2:58:10
	 *int
	 *宋银海
	 */
	public int updStandardprocess(){
		String sql="update mes_wm_standardprocess set opname=so.description from mes_wm_standardprocess sd "+
					"left join "+Config.BUSINESSDATABASE+"sfc_operation so on sd.opcode=so.opcode "+
					"where isnull(sd.opname,'')<>so.description";
		return this.update(sql);
	}
	
	/**
	 * @category 指定帐套组织
	 *2016 2016年4月5日下午2:49:14
	 *int
	 *宋银海
	 */
	public int updStandardprocessOrgSob(String orgGid,String sobGid){
		String sql="update mes_wm_standardprocess set orggid='"+orgGid+"', sobgId='"+sobGid+"' where isnull(sobgId,'')='' or isnull(orggid,'')='' ";
		return this.update(sql);
	}
	
	/**
	 * @category 删除产品结构主表
	 *2016 2016年3月14日上午9:16:12
	 *int
	 *宋银海
	 */
	public int deleteProductStructure(){
		String sql="delete from MES_WM_ProductStructure where autoidForSynchro not in (select BomId FROM "+Config.BUSINESSDATABASE+"bom_bom)";
		return this.update(sql);
	}
	
	
	/**
	 * @category 增加产品结构主表
	 *2016 2016年3月23日上午8:44:13
	 *int
	 *宋银海
	 */
	public int addProductStructure(){
		String sql="insert into MES_WM_ProductStructure(gid,goodsCodeForSynchro,autoidForSynchro) "+ 
					" select newid(),bpt.InvCode,bb.BomId FROM "+Config.BUSINESSDATABASE+"bom_bom bb "+
					" LEFT JOIN "+Config.BUSINESSDATABASE+"bom_parent bp ON bp.BomId=bb.BomId "+
					" LEFT JOIN "+Config.BUSINESSDATABASE+"bas_part bpt ON bp.ParentId=bpt.PartId "+
					" where  bb.BomId not in (select autoidForSynchro from MES_WM_ProductStructure);";
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改产品结构主表
	 *2016 2016年3月23日上午9:03:01
	 *int
	 *宋银海
	 */
	public int updProductStructure(){
		String sql="update MES_WM_ProductStructure set goodsCodeForSynchro= bpt.InvCode from MES_WM_ProductStructure ps "+
					"LEFT JOIN "+Config.BUSINESSDATABASE+"bom_bom bb ON ps.autoidForSynchro=bb.BomId "+
					"LEFT JOIN "+Config.BUSINESSDATABASE+"bom_parent bp ON bp.BomId=bb.BomId "+
					"LEFT JOIN "+Config.BUSINESSDATABASE+"bas_part bpt ON bp.ParentId=bpt.PartId "+
					"where isnull(ps.goodsCodeForSynchro,'')<>bpt.InvCode ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改产品结构主表信息
	 *2016 2016年3月23日上午10:34:45
	 *int
	 *宋银海
	 */
	public int updProductStructureInfor(){
		
		String sql="UPDATE MES_WM_ProductStructure  "+
				" SET goodsUid = gs.gid "+
				" FROM MES_WM_ProductStructure ps "+
				" LEFT JOIN AA_Goods gs ON ps.goodsCodeForSynchro=gs.goodsCode "+
				" WHERE  isnull(ps.goodsUid,'')<>isnull(gs.gid,'') ";
		
		return this.update(sql);
	}
	
	
	/**
	 * @category 删除产品结构子表
	 *2016 2016年3月23日上午9:24:01
	 *int
	 *宋银海
	 */
	public int deleteProductStructurec(){
		String sql="delete from MES_WM_ProductStructureC where autoidForSynchro not in (select OpComponentId  FROM "+Config.BUSINESSDATABASE+"bom_opcomponent) ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 增加产品结构子表
	 *2016 2016年3月23日上午9:25:03
	 *int
	 *宋银海
	 */
	public int addProductStructurec(){
		String sql="insert into MES_WM_ProductStructureC(gid,goodsCodeForSynchro,mainAmount,autoidForSynchro,idForSynchro) "+  
					"select newid(),bp.InvCode, round(bo.BaseQtyN/bo.BaseQtyD,2), bo.OpComponentId,bo.BomId FROM "+Config.BUSINESSDATABASE+"bom_opcomponent bo "+
					"LEFT JOIN "+Config.BUSINESSDATABASE+"bas_part bp ON bo.ComponentId=bp.PartId "+
					"where bo.OpComponentId not in (select autoidForSynchro from MES_WM_ProductStructureC);";
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改产品结构
	 *2016 2016年3月23日上午10:30:19
	 *int
	 *宋银海
	 */
	public int updProductStructurec(){
		String sql="update MES_WM_ProductStructureC set goodsCodeForSynchro=bp.InvCode,mainAmount=round(bo.BaseQtyN/bo.BaseQtyD,2) "+
					"from MES_WM_ProductStructureC psc "+
					"LEFT JOIN "+Config.BUSINESSDATABASE+"bom_opcomponent bo ON psc.autoidForSynchro=bo.OpComponentId "+
					"LEFT JOIN "+Config.BUSINESSDATABASE+"bas_part bp ON bo.ComponentId=bp.PartId "+
					"where isnull(goodsCodeForSynchro,'')<>bp.InvCode OR "+
					"isnull(mainAmount,0)<>round(bo.BaseQtyN/bo.BaseQtyD,2) or "
					+ "isnull(idForSynchro,0)<>bo.BomId ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改产品结构子表关联信息
	 *2016 2016年3月23日上午10:46:12
	 *int
	 *宋银海
	 */
	public int updProductStructurecInfor(){
		   
		String sql="UPDATE MES_WM_ProductStructureC  "+
				" SET goodsUid = gs.gid,parentUid=p.gid "+
				" FROM MES_WM_ProductStructureC pc "+
				" LEFT JOIN AA_Goods gs ON pc.goodsCodeForSynchro=gs.goodsCode "+
				" left join MES_WM_ProductStructure p on pc.idForSynchro=p.autoidForSynchro "+
				" WHERE  isnull(pc.goodsUid,'')<>isnull(gs.gid,'') "+
				" or isnull(pc.parentUid,'')='' ";
		
		return this.update(sql);
	}
	

	
	/**
	 * @category 删除工艺路线主表
	 *2016 2016年3月23日下午3:38:16
	 *int
	 *宋银海
	 */
	public int deleteProcessRoute(){
		String sql="delete from MES_WM_ProcessRoute where autoidForSynchro not in (select PRoutingId  FROM "+Config.BUSINESSDATABASE+"sfc_prouting)";
		return this.update(sql);
	}
	
	
	/**
	 * @category 增加工艺路线主表
	 *2016 2016年3月23日下午3:30:43
	 *int
	 *宋银海
	 */
	public int addProcessRoute(){
		String sql="insert into MES_WM_ProcessRoute(gid,routcode,effdate,routname,goodsCodeForSynchro,autoidForSynchro) "+ 
					"select newid(),bp.InvCode,sp.VersionEffDate,bp.InvCode,bp.InvCode,sp.PRoutingId FROM "+Config.BUSINESSDATABASE+"sfc_prouting sp "+
					"LEFT JOIN "+Config.BUSINESSDATABASE+"sfc_proutingpart spt ON sp.PRoutingId=spt.PRoutingId "+
					"LEFT JOIN "+Config.BUSINESSDATABASE+"bas_part bp ON spt.PartId=bp.PartId "+
					"where sp.PRoutingId not in (select autoidForSynchro from MES_WM_ProcessRoute) ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改工艺路线主表
	 *2016 2016年3月23日下午3:40:24
	 *int
	 *宋银海
	 */
	public int updProcessRoute(){
		String sql="update MES_WM_ProcessRoute set routcode=bp.InvCode,effdate=sp.VersionEffDate,routname=bp.InvCode,goodsCodeForSynchro=bp.InvCode from MES_WM_ProcessRoute wpr "+
					"LEFT JOIN "+Config.BUSINESSDATABASE+"sfc_prouting sp ON wpr.autoidForSynchro=sp.PRoutingId "+
					"LEFT JOIN "+Config.BUSINESSDATABASE+"sfc_proutingpart spt ON sp.PRoutingId=spt.PRoutingId "+
					"LEFT JOIN "+Config.BUSINESSDATABASE+"bas_part bp ON spt.PartId=bp.PartId "+
					"where isnull(wpr.goodsCodeForSynchro,'')<>bp.InvCode ";
		return this.update(sql);
	}
	
	/**
	 * @category 修改工艺路线主表信息
	 *2016 2016年3月23日下午4:58:28
	 *int
	 *宋银海
	 */
	public int updProcessRouteInfor(){
		
		String sql="UPDATE MES_WM_ProcessRoute  "+
				" SET goodsUid = gs.gid "+
				" FROM MES_WM_ProcessRoute pr "+
				" LEFT JOIN AA_Goods gs ON pr.goodsCodeForSynchro=gs.goodsCode "+
				" WHERE  isnull(pr.goodsUid,'')<>isnull(gs.gid,'') ";
		
		return this.update(sql);
	}
	
	
	
	/**
	 * @category 增加工艺路线子表
	 *2016 2016年3月23日下午4:30:34
	 *int
	 *宋银海
	 */
	public int addProcessRouteC(){
		String sql="insert into MES_WM_ProcessRouteC(gid,cindex,opcodeForSynchro,autoidForSynchro,idForSynchro) "+
					"select newid(),OpSeq,op.OpCode,PRoutingDId,PRoutingId FROM "+Config.BUSINESSDATABASE+"sfc_proutingdetail spd "+
					"LEFT JOIN "+Config.BUSINESSDATABASE+"sfc_operation op ON spd.OperationId=op.OperationId "+
					"where  PRoutingDId not in (select autoidForSynchro from MES_WM_ProcessRouteC); ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 删除工艺路线子表
	 *2016 2016年3月23日下午4:35:28
	 *int
	 *宋银海
	 */
	public int deleteProcessRouteC(){
		String sql="delete from MES_WM_ProcessRouteC where autoidForSynchro not in (select PRoutingDId FROM "+Config.BUSINESSDATABASE+"sfc_proutingdetail)";
		return this.update(sql);
	}
	
	/**
	 * @category 修改工艺路线子表
	 *2016 2016年3月23日下午4:39:50
	 *int
	 *宋银海
	 */
	public int updProcessRouteC(){
		String sql="update MES_WM_ProcessRouteC set cindex=OpSeq,opcodeForSynchro=op.OpCode,idForSynchro=PRoutingId "+
					"from MES_WM_ProcessRouteC prc "+
					"LEFT JOIN "+Config.BUSINESSDATABASE+"sfc_proutingdetail spd ON prc.autoidForSynchro=spd.PRoutingDId "+
					"LEFT JOIN "+Config.BUSINESSDATABASE+"sfc_operation op ON spd.OperationId=op.OperationId "+
					"where isnull(prc.cindex,'')<>spd.OpSeq OR "+
					"isnull(prc.opcodeForSynchro,'')<>op.OpCode OR "+
					"isnull(prc.idForSynchro,'')<>PRoutingId ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改工艺路线子表信息
	 *2016 2016年3月23日下午5:02:10
	 *int
	 *宋银海
	 */
	public int updProcessRouteCInfor(){
		String sql="UPDATE MES_WM_ProcessRouteC "+
					"SET opGid = sp.gid,routGid=p.gid "+
					"FROM MES_WM_ProcessRouteC pc "+
					"LEFT JOIN MES_WM_StandardProcess sp ON pc.opcodeForSynchro=sp.opcode "+
					"left join MES_WM_ProcessRoute p on pc.idForSynchro=p.autoidForSynchro  "+
					"WHERE  isnull(pc.opGid,'')<>isnull(sp.gid,'') "+
					"or isnull(pc.routGid,'')='' ";
		return this.update(sql);
	}
	
	
	
	/**
	 * @category 删除到货单主表
	 *2016 2016年1月5日下午3:51:44
	 *int
	 *宋银海
	 */
	public int deleteProArrival(){
		String sql="delete from WM_ProcureArrival where billCode not in (select cCode from "+Config.BUSINESSDATABASE+"PU_ArrivalVouch )";
		return this.update(sql);
	}
	
	
	/**
	 * @category 增加采购到货单主表
	 *2016 2016年1月5日下午2:59:04
	 *int
	 *宋银海
	 */
	public int addProArrival(){
		String sql="insert into WM_ProcureArrival(gid,depCodeForSynchro,supplierCodeForSynchro,billCode,billState,billDate,autoidForSynchro,define1,stateForSynchro) "+
					"SELECT newid(),cDepCode,cVenCode,cCode,iverifystate,dDate,id,cDefine1,iverifystateex  from "+Config.BUSINESSDATABASE+"PU_ArrivalVouch "+
					"where cCode not in (select billCode from WM_ProcureArrival) ";
		return this.update(sql);
	}
	
	/**
	 * @category 修改采购到货单主表
	 *2016 2016年1月5日下午3:59:37
	 *int
	 *宋银海
	 */
	public int updProArrival(){
		String sql="update WM_ProcureArrival set depCodeForSynchro=cDepCode,supplierCodeForSynchro=cVenCode,billState=iverifystateex,billDate=dDate,define1=cDefine1,stateForSynchro=iverifystateex "+
					"from WM_ProcureArrival pa LEFT JOIN "+Config.BUSINESSDATABASE+"PU_ArrivalVouch pau8 on pa.billCode=pau8.ccode "+
					"where isnull(pa.depCodeForSynchro,'')<>isnull(pau8.cDepCode,'')  or "
					+ " isnull(pa.supplierCodeForSynchro,'')<>isnull(pau8.cVenCode,'') or "
					+ " isnull(pa.billState,'')<>isnull(pau8.iverifystateex,'') or "
					+ " isnull(pa.billDate,'')<>isnull(pau8.dDate,'') or "
					+ " isnull(pa.define1,'')<>isnull(pau8.cDefine1,'') or "
					+ " isnull(pa.stateForSynchro,-1)<>isnull(pau8.iverifystateex,0)";
		return this.update(sql);
	}

	
	/**
	 * @category 修改采购到货单主表相关字段
	 *2016 2016年3月21日上午10:27:38
	 *int
	 *宋银海
	 */
	public int updProArrivalInfor(){
		String sql="UPDATE WM_ProcureArrival "+
				" SET departmentUid = d.gid,supplierUid=pc.gid "+
				" FROM WM_ProcureArrival p "+
				" LEFT JOIN AA_Department d ON p.depCodeForSynchro=d.depCode "+
				" left join AA_Provider_Customer pc on p.supplierCodeForSynchro=pc.pcCode "+
				" WHERE  (isnull(p.departmentUid,'')<>isnull(d.gid,'') "+
				" or isnull(p.supplierUid,'')<>isnull(pc.gid,'')) "+
				" and pc.flagForSynchro='p' ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 删除到货单子表
	 *2016 2016年1月5日下午5:09:04
	 *int
	 *宋银海
	 */
	public int deleteProArrivalC(){
		String sql="delete from WM_ProcureArrival_C where autoidForSynchro not in (select autoid from "+Config.BUSINESSDATABASE+"PU_ArrivalVouchs )";
		return this.update(sql);
	}
	
	
	/**
	 * @category 增加到货单子表
	 *2016 2016年3月14日上午11:01:13
	 *int
	 *宋银海
	 */
	public int addProArrivalC(){
		String sql="insert into WM_ProcureArrival_C(gid,autoidForSynchro,goodsCodeForSynchro,number,assistNumber,unitPrice,totalPrice,needCheck,"
				+ "originalTaxPrice,originalTaxMoney,originalNotaxPrice,originalNotaxMoney,originalTax,"
				+ "localTaxPrice,localTaxMoney,localNotaxPrice,localNotaxMoney,localTax,"
				+ "putinNumber,idForSynchro,batch,putinAssistNumber,define22,posIDForSynchro) select newid(),autoid,cInvCode,iQuantity,iNum,iCost,iMoney,bGsp,"
				+ "iOriTaxCost,iOriSum,iOriCost,iOriMoney,iOriTaxPrice,"
				+ "iOriTaxCost,iSum,iCost,iMoney,iTaxPrice,"
				+ "fValidInQuan,id,cbatch,fValidInNum,cdefine22,iPOsID  from "+Config.BUSINESSDATABASE+"PU_ArrivalVouchs  "
				+ " where autoid not in (select autoidForSynchro from WM_ProcureArrival_C) ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改到货单子表
	 *2016 2016年3月14日上午11:01:59
	 *int
	 *宋银海
	 */
	public int updProArrivalC(){
		
		String sql="update WM_ProcureArrival_C set goodsCodeForSynchro=cInvCode,number=iQuantity,assistNumber=iNum,unitPrice=iCost,totalPrice=iMoney,needCheck=bGsp, "+
				" originalTaxPrice=iOriTaxCost,originalTaxMoney=iOriSum,originalNotaxPrice=iOriCost,originalNotaxMoney=iOriMoney,originalTax=iOriTaxPrice,"
				+ "localTaxPrice=iOriTaxCost,localTaxMoney=iSum,localNotaxPrice=iCost,localNotaxMoney=iMoney,localTax=iTaxPrice, "
				+ "putinNumber=fValidInQuan,batch=cbatch,putinAssistNumber=fValidInNum,define22=cdefine22,posIDForSynchro=iPOsID "
				+ " from WM_ProcureArrival_C pac LEFT JOIN "+Config.BUSINESSDATABASE+"PU_ArrivalVouchs paus8 on pac.autoidForSynchro=paus8.autoid "+
				"where isnull(pac.goodsCodeForSynchro,'')<>isnull(paus8.cInvCode,'')  or "
				+ " isnull(pac.number,0)<>isnull(paus8.iQuantity,0) or "
				+ " isnull(pac.assistNumber,0)<>isnull(paus8.iNum,0) or "
				+ " isnull(pac.unitPrice,0)<>isnull(paus8.iCost,0) or "
				+ " isnull(pac.totalPrice,0)<>isnull(paus8.iMoney,0) or "
				+ " isnull(pac.needCheck,'')<>isnull(paus8.bGsp,'') or "
				+ " isnull(pac.originalNotaxPrice,0)<>isnull(paus8.iOriCost,0) or "
				+ " isnull(pac.originalNotaxMoney,0)<>isnull(paus8.iOriMoney,0) or "
				+ " isnull(pac.localNotaxPrice,0)<>isnull(paus8.iCost,0) or "
				+ " isnull(pac.localNotaxMoney,0)<>isnull(paus8.iMoney,0) or "
				+ " isnull(pac.putinNumber,0)<>isnull(paus8.fValidInQuan,0) or "
				+ " isnull(pac.putinAssistNumber,0)<>isnull(paus8.fValidInNum,0) or "
				+ " isnull(pac.batch,'')<>isnull(paus8.cbatch,'') or "
				+ " isnull(pac.define22,'')<>isnull(paus8.cdefine22,'') or"
				+ " isnull(pac.posIDForSynchro,0)<>isnull(paus8.iPOsID,0) ";
		
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改采购到货单子表相关字段
	 *2016 2016年3月21日上午10:43:16
	 *int
	 *宋银海
	 */
	public int updProArrivalCInfor(){
		
		String sql="UPDATE WM_ProcureArrival_C  "+
				" SET goodsUid = gs.gid, "+
				" procureArrivalUid=p.gid "+
				" FROM WM_ProcureArrival_C pc "+
				" LEFT JOIN AA_Goods gs ON pc.goodsCodeForSynchro=gs.goodsCode "+
				" left join WM_ProcureArrival p on pc.idForSynchro=p.autoidForSynchro "+
				" WHERE  isnull(pc.goodsUid,'')<>isnull(gs.gid,'') "+
				" or isnull(pc.procureArrivalUid,'')='' ";
		
		return this.update(sql);
	}
	
	/**
	 * @category 指定组织帐套
	 *2016 2016年4月5日下午2:55:05
	 *int
	 *宋银海
	 */
	public int updProArrivalOrgSob(String orgGid,String sobGid){
		String sql="update WM_ProcureArrival set orggid='"+orgGid+"', sobgId='"+sobGid+"' where isnull(sobgId,'')='' or isnull(orggid,'')='' ";
		return this.update(sql);
	}
	
	/**
	 * @category 删除发货单主表
	 *2016 2016年3月14日上午11:25:36
	 *int
	 *宋银海
	 */
	public int deleteSaleSend(){
		String sql="delete from WM_SaleSend where autoidForSynchro not in (select id from "+Config.BUSINESSDATABASE+"SA_SaleDelivery )";
		return this.update(sql);
	}
	
	/**
	 * @category 增加发货单主表
	 *2016 2016年3月14日下午1:20:31
	 *int
	 *宋银海
	 */
	public int addSaleSend(){
		String sql="insert into WM_SaleSend(gid,customerUid,billCode,stateForSynchro,billDate) "+
					"SELECT id,idcustomer,code,auditor,voucherdate from "+Config.BUSINESSDATABASE+"SA_SaleDelivery "+
					"where id not in (select gid from WM_SaleSend) ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改发货单主表
	 *2016 2016年3月14日下午1:35:20
	 *int
	 *宋银海
	 */
	public int updSaleSend(){
		String sql="update WM_SaleSend set customerUid=idcustomer,stateForSynchro=auditor,billDate=voucherdate "+
					"from WM_SaleSend ss LEFT JOIN "+Config.BUSINESSDATABASE+"SA_SaleDelivery dis on ss.gid=dis.id "+
					"where ss.customerUid<>dis.idcustomer  or "
					+ " isnull(ss.stateForSynchro,'')<>isnull(dis.auditor,'') or "
					+ " isnull(ss.billDate,'')<>isnull(dis.voucherdate,'') ";
		return this.update(sql);
	}
	
	
	
	/**
	 * @category 删除发货单子表
	 *2016 2016年3月14日下午1:41:49
	 *int
	 *宋银海
	 */
	public int deleteSaleSendC(){
		String sql="delete from WM_SaleSend_C where gid not in (select id from "+Config.BUSINESSDATABASE+"SA_SaleDelivery_b )";
		return this.update(sql);
	}
	
	
	/**
	 * @category 增加发货单子表
	 *2016 2016年3月14日下午1:47:17
	 *int
	 *宋银海
	 */
	public int addSaleSendC(){
		String sql="insert into WM_SaleSend_C(gid,goodsUid,number,"
				+ "originalTaxPrice,originalTaxMoney,originalNotaxPrice,originalNotaxMoney,originalTax,"
				+ "localTaxPrice,localTaxMoney,localNotaxMoney,localTax,"
				+ "putoutNum,wareHouseForSynchro,batch,salesendUid) select id,idinventory,baseQuantity,"
				+ "origTaxPrice,origTaxAmount,origDiscountPrice,origDiscountAmount,origTax,"
				+ "taxPrice,taxAmount,discountAmount,tax,"
				+ "saleOutQuantity,idwarehouse,batch,idSaleDeliveryDTO from "+Config.BUSINESSDATABASE+"SA_SaleDelivery_b  "
				+ " where id  not in (select gid from WM_SaleSend_C) ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改发货单子表
	 *2016 2016年3月14日下午1:59:39
	 *int
	 *宋银海
	 */
	public int updSaleSendC(){
		
		String sql="update WM_SaleSend_C set goodsUid=idinventory,number=baseQuantity, "+
				" originalTaxPrice=origTaxPrice,originalTaxMoney=origTaxAmount,originalNotaxPrice=origDiscountPrice,originalNotaxMoney=origDiscountAmount,originalTax=origTax,"
				+ "localTaxPrice=taxPrice,localTaxMoney=taxAmount,localNotaxMoney=discountAmount,localTax=tax, "
				+ "putoutNum=saleOutQuantity,wareHouseForSynchro=idwarehouse,batch=diss.batch "
				+ " from WM_SaleSend_C ssc LEFT JOIN "+Config.BUSINESSDATABASE+"SA_SaleDelivery_b diss on ssc.gid=diss.id "+
				"where ssc.goodsUid <>diss.idinventory  or "
				+ " isnull(ssc.number,0)<>isnull(diss.baseQuantity,0) or "
				+ " isnull(ssc.originalNotaxPrice,0)<>isnull(diss.origDiscountPrice,0) or "
				+ " isnull(ssc.originalNotaxMoney,0)<>isnull(diss.origDiscountAmount,0) or "
				+ " isnull(ssc.localTaxPrice,0)<>isnull(diss.taxPrice,0) or "
				+ " isnull(ssc.localNotaxMoney,0)<>isnull(diss.discountAmount,0) or "
				+ " isnull(ssc.putoutNum,0)<>isnull(diss.saleOutQuantity,0) or "
				+ " isnull(ssc.wareHouseForSynchro,'11111111-1111-1111-1111-111111111111') <>ISNULL(diss.idwarehouse,'11111111-1111-1111-1111-111111111111') or "
				+ " isnull(ssc.batch,'')<>isnull(diss.batch,'')  ";
		
		return this.update(sql);
	}
	
	
	/**
	 * @category 删除生产订单主表
	 *2016 2016年3月14日下午2:44:48
	 *int
	 *宋银海
	 */
	public int deleteProduceOrder(){
		String sql="delete from WM_ProduceOrder where autoidForSynchro not in (select MoId from "+Config.BUSINESSDATABASE+"mom_order )";
		return this.update(sql);
	}
	
	
	/**
	 * @category 删除委外订单主表
	 *2016 2016年3月14日下午2:44:48
	 *int
	 *宋银海
	 */
	public int deleteOmOrder(){
		String sql="delete from OM_Main where moidForSynchro not in (select MoId from "+Config.BUSINESSDATABASE+"OM_MOMain )";
		return this.update(sql);
	}
	
	
	/**
	 * @category 增加生产订单主表
	 *2016 2016年3月14日下午2:49:02
	 *int
	 *宋银海
	 */
	public int addProduceOrder(){
		String sql="INSERT INTO WM_ProduceOrder (gid,billCode,billDate,autoidForSynchro ) "+
					"SELECT newid(),MoCode,CreateDate,MoId FROM "+Config.BUSINESSDATABASE+"mom_order "+
					"WHERE MoId NOT IN (SELECT autoidForSynchro FROM WM_ProduceOrder) ";
		return this.update(sql);
	}
	
	/**
	 * @category 增加委外订单主表
	 *2016 2016年3月14日下午2:49:02
	 *int
	 *宋银海
	 */
	public int addOmOrder(){
		String sql="INSERT INTO OM_Main(gid,billCode,billDate,supplierCodeForSynchro,moidForSynchro,stateForSynchro ) "+
					"SELECT newid(),cCode,dDate,cVenCode,moid,cState FROM "+Config.BUSINESSDATABASE+"OM_MOMain "+
					"WHERE moid NOT IN (SELECT moidForSynchro FROM OM_Main) ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改生产订单主表
	 *2016 2016年3月14日下午2:55:52
	 *int
	 *宋银海
	 */
	public int updProduceOrder(){
		String sql="update WM_ProduceOrder set billCode=MoCode,billDate=CreateDate "+
					"from WM_ProduceOrder wpo LEFT JOIN "+Config.BUSINESSDATABASE+"mom_order mo on wpo.autoidForSynchro=mo.MoId "+
					"where isnull(wpo.billCode,'')<>isnull(mo.MoCode,'') or "
					+ " isnull(wpo.billDate,'')<>isnull(mo.CreateDate,'') ";
					
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改委外订单主表
	 *2016 2016年3月14日下午2:55:52
	 *int
	 *宋银海
	 */
	public int updOmOrder(){
		String sql="update OM_Main set billCode=cCode,billDate=dDate,supplierCodeForSynchro=cVenCode "+
					"from OM_Main omm LEFT JOIN "+Config.BUSINESSDATABASE+"OM_MOMain ommyy on omm.moidForSynchro=ommyy.MoId "+
					"where isnull(omm.billCode,'')<>isnull(ommyy.cCode,'') or "
					+ " isnull(omm.billDate,'')<>isnull(ommyy.dDate,'') or "
					+ " isnull(omm.supplierCodeForSynchro,'')<>isnull(ommyy.cVenCode,'') ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改委外订单相关字段
	 *2016 2016年3月21日下午3:59:55
	 *int
	 *宋银海
	 */
	public int updOmOrderInfor(){
		String sql="UPDATE OM_Main "+
				" SET supplierUid=pc.gid "+
				" FROM OM_Main m "+
				" left join AA_Provider_Customer pc on m.supplierCodeForSynchro=pc.pcCode "+
				" WHERE  isnull(m.supplierUid,'')<>isnull(pc.gid,'') "+
				" and pc.flagForSynchro='p' ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 删除生产订单产品表
	 *2016 2016年3月14日下午3:07:27
	 *int
	 *宋银海
	 */
	public int deleteProduceOrderC(){
		String sql="delete from WM_ProduceOrder_C where autoidForSynchro not in (select MoDId from "+Config.BUSINESSDATABASE+"mom_orderdetail )";
		return this.update(sql);
	}
	
	
	/**
	 * @category 删除委外订单产品表
	 *2016 2016年3月14日下午3:07:27
	 *int
	 *宋银海
	 */
	public int deleteOmDetails(){
		String sql="delete from OM_Details where moDetailsIdSynchro not in (select MODetailsID from "+Config.BUSINESSDATABASE+"OM_MODetails )";
		return this.update(sql);
	}
	
	
	/**
	 * @category 增加生产订单产品表
	 *2016 2016年3月14日下午3:19:15
	 *int
	 *宋银海
	 */
	public int addProduceOrderC(){
		
		String sql="insert into WM_ProduceOrder_C(gid,autoidForSynchro,goodsCodeForSynchro,number,completedNum,startDate,endDate,isCheck,depCodeForSynchro,cfree1,cfree2) "+
					"select newid(),od.MoDId,InvCode,Qty,QualifiedInQty,StartDate,DueDate,QcFlag,MDeptCode,free1,free2 from "+Config.BUSINESSDATABASE+"mom_orderdetail od "+
					"LEFT JOIN "+Config.BUSINESSDATABASE+"mom_morder mod ON od.MoDId =mod.MoDId "+
					"where od.MoDId not in (select autoidForSynchro from WM_ProduceOrder_C) ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 增加委外订单产品表
	 *2016 2016年3月14日下午3:19:15
	 *int
	 *宋银海
	 */
	public int addOmDetails(){
		
		String sql="insert into OM_Details(gid,moDetailsIdSynchro,moidForSynchro,goodsCodeForSynchro,number,assistNumber,cfree1,cfree2) "+
					"select newid(),MODetailsID,MOID,cInvCode,iQuantity,iNum,cFree1,cFree2 from "+Config.BUSINESSDATABASE+"OM_MODetails  "+
					"where MODetailsID not in (select moDetailsIdSynchro from OM_Details) ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改生产订单产品表
	 *2016 2016年3月14日下午3:32:31
	 *int
	 *宋银海
	 */
	public int updProduceOrderC(){
		String sql="update WM_ProduceOrder_C set goodsCodeForSynchro=InvCode,number=Qty,completedNum=QualifiedInQty,isCheck=QcFlag,startDate=mod.StartDate,endDate=DueDate,"
				+ " idForSynchro=od.moid,depCodeForSynchro=MDeptCode,cfree1=free1,cfree2=free2 "+
					"from WM_ProduceOrder_C wpoc LEFT JOIN "+Config.BUSINESSDATABASE+"mom_orderdetail od on wpoc.autoidForSynchro=od.MoDId "+
					"left join "+Config.BUSINESSDATABASE+"mom_morder mod ON od.MoDId =mod.MoDId "+
					"where isnull(wpoc.goodsCodeForSynchro,'')<>isnull(od.InvCode,'')  or "+
					"isnull(wpoc.number,0)<>isnull(od.Qty,0) or "+
					"isnull(wpoc.idForSynchro,'')<>isnull(od.moid,'') or "+
					"isnull(wpoc.completedNum,0)<>isnull(od.QualifiedInQty,0) or "+
					"isnull(wpoc.isCheck,'')<>isnull(od.QcFlag,'') or "+
					"isnull(wpoc.depCodeForSynchro,'')<>isnull(od.MDeptCode,'') or "+
					"isnull(wpoc.startDate,'')<>isnull(mod.StartDate,'') or "+
					"isnull(wpoc.endDate,'')<>isnull(mod.DueDate,'') or "
					+ "isnull(wpoc.cfree1,'')<>isnull(od.free1,'') or "
					+ "isnull(wpoc.cfree2,'')<>isnull(od.free2,'') ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改委外订单产品表
	 *2016 2016年3月14日下午3:32:31
	 *int
	 *宋银海
	 */
	public int updOmDetails(){
		String sql="update OM_Details set goodsCodeForSynchro=cInvCode,number=iQuantity,assistNumber=iNum,cfree1=omdyy.cFree1,cfree2=omdyy.cFree2,moidForSynchro=MOID "+
					"from OM_Details omd LEFT JOIN "+Config.BUSINESSDATABASE+"OM_MODetails omdyy on omd.moDetailsIdSynchro=omdyy.MODetailsID "+
					"where isnull(omd.goodsCodeForSynchro,'')<>isnull(omdyy.cInvCode,'')  or "+
					"isnull(omd.number,0)<>isnull(omdyy.iQuantity,0) or "+
					"isnull(omd.assistNumber,0)<>isnull(omdyy.iNum,0) or "+
					"isnull(omd.cfree1,'')<>isnull(omdyy.cFree1,'') or "+
					"isnull(omd.cfree2,'')<>isnull(omdyy.cFree2,'') or "
					+ "isnull(omd.moidForSynchro,'')<>isnull(omdyy.MOID,'')";
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改生产订单产品表相关属性
	 *2016 2016年3月24日上午11:00:21
	 *int
	 *宋银海
	 */
	public int updProduceOrderCInfor(){
		String sql="UPDATE WM_ProduceOrder_C "+
					"SET goodsUid = gs.gid, produceOrderUid=po.gid,depUid=d.gid "+
					"FROM WM_ProduceOrder_C pc "+
					"LEFT JOIN AA_Goods gs ON pc.goodsCodeForSynchro=gs.goodsCode "+
					"left join WM_ProduceOrder po on po.autoidForSynchro=pc.idForSynchro "+
					"left join AA_Department d ON pc.depCodeForSynchro=d.depCode "+
					"WHERE  isnull(pc.goodsUid,'')<>isnull(gs.gid,'') "+
					"or isnull(pc.produceOrderUid,'')='' ";
		
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改委外订单产品表相关属性
	 *2016 2016年3月24日上午11:00:21
	 *int
	 *宋银海
	 */
	public int updOmDetailsInfor(){
		String sql="UPDATE OM_Details "+
					"SET goodsGid = gs.gid, moMainGid=om.gid "+
					"FROM OM_Details ods "+
					"LEFT JOIN AA_Goods gs ON ods.goodsCodeForSynchro=gs.goodsCode "+
					"left join OM_Main om on ods.moidForSynchro=om.moidForSynchro "+
					"WHERE  isnull(ods.goodsGid,'')<>isnull(gs.gid,'') "+
					"or isnull(ods.moMainGid,'')='' ";
		
		return this.update(sql);
	}
	
	
	/**
	 * @category 删除生产订单材料表
	 *2016 2016年3月14日下午4:00:53
	 *int
	 *宋银海
	 */
	public int deleteProduceOrderC2(){
		String sql="delete from WM_ProduceOrder_C2 where autoidForSynchro not in (select AllocateId from "+Config.BUSINESSDATABASE+"mom_moallocate )";
		return this.update(sql);
	}
	
	
	/**
	 * @category 删除委外订单材料表
	 *2016 2016年3月14日下午4:00:53
	 *int
	 *宋银海
	 */
	public int deleteOMMaterials(){
		String sql="delete from OM_Materials where MOMaterialsIDForSynchro not in (select MOMaterialsID from "+Config.BUSINESSDATABASE+"OM_MOMaterials )";
		return this.update(sql);
	}
	
	
	/**
	 * @category 增加生产订单材料表
	 *2016 2016年3月14日下午4:05:30
	 *int
	 *宋银海
	 */
	public int addProduceOrderC2(){
		
		String sql="insert into WM_ProduceOrder_C2(gid,autoidForSynchro,goodsCodeForSynchro,number,receivedNum,idForSynchro) "+
					"select newid(),AllocateId,InvCode,Qty,IssQty,MoDId  from "+Config.BUSINESSDATABASE+"mom_moallocate "+ 
					"where AllocateId not in (select autoidForSynchro from WM_ProduceOrder_C2) ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 增加生产订单材料表
	 *2016 2016年3月14日下午4:05:30
	 *int
	 *宋银海
	 */
	public int addOMMaterials(){
		
		String sql="insert into OM_Materials(gid,mOMaterialsIDForSynchro,moDetailsIDForSynchro,mOIDForSynchro,goodsCodeForSynchro,number,cfree1,cfree2,receivedNum,receivedAssistNum) "+
					"select newid(),MOMaterialsID,MoDetailsID,MOID,cInvCode,iQuantity,cFree1,cFree2,iSendQTY,iSendNum  from "+Config.BUSINESSDATABASE+"OM_MOMaterials "+ 
					"where MOMaterialsID not in (select mOMaterialsIDForSynchro from OM_Materials) ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改生产订单材料表
	 *2016 2016年3月14日下午4:13:30
	 *int
	 *宋银海
	 */
	public int updProduceOrderC2(){
		String sql="update WM_ProduceOrder_C2 set goodsCodeForSynchro=InvCode,number=Qty,receivedNum=IssQty "+
					"from WM_ProduceOrder_C2 wpoc2 LEFT JOIN "+Config.BUSINESSDATABASE+"mom_moallocate mol on wpoc2.autoidForSynchro=mol.AllocateId "+
					"where isnull(wpoc2.goodsCodeForSynchro,'')<>isnull(mol.InvCode,'') or "+
					"isnull(wpoc2.number,0)<>isnull(mol.Qty,0) or "+
					"isnull(wpoc2.receivedNum,0)<>isnull(mol.IssQty,0) ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改委外订单材料表
	 *2016 2016年3月14日下午4:13:30
	 *int
	 *宋银海
	 */
	public int updOMMaterials(){
		String sql="update OM_Materials set goodsCodeForSynchro=cInvCode,number=iQuantity,cfree1=omsyy.cFree1,cfree2=omsyy.cFree2,moDetailsIDForSynchro=MoDetailsID,mOIDForSynchro=MOID,"
				+ " receivedNum=iSendQTY, receivedAssistNum=iSendNum "+
					"from OM_Materials oms LEFT JOIN "+Config.BUSINESSDATABASE+"OM_MOMaterials omsyy on oms.mOMaterialsIDForSynchro=omsyy.MOMaterialsID "+
					"where isnull(oms.goodsCodeForSynchro,'')<>isnull(omsyy.cInvCode,'') or "+
					"isnull(oms.number,0)<>isnull(omsyy.iQuantity,0) or "+
					"isnull(oms.cfree1,'')<>isnull(omsyy.cFree1,'') or "
					+ "isnull(oms.cfree2,'')<>isnull(omsyy.cFree2,'') or "
					+ "isnull(oms.moDetailsIDForSynchro,'')<>isnull(omsyy.MoDetailsID,'') or "
					+ "isnull(oms.mOIDForSynchro,'')<>isnull(omsyy.MOID,'') or "
					+ "isnull(oms.receivedNum,0)<>isnull(omsyy.iSendQTY,0) or "
					+ "isnull(oms.receivedAssistNum,0)<>isnull(omsyy.iSendNum,0) ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改生产订单材料表相关属性
	 *2016 2016年3月24日下午3:59:09
	 *int
	 *宋银海
	 */
	public int updProduceOrderC2Infor(){
		String sql="UPDATE WM_ProduceOrder_C2  "+
				" SET goodsUid = gs.gid, produceOrderCuid=pc.gid "+
				" FROM WM_ProduceOrder_C2 pc2 "+
				" LEFT JOIN AA_Goods gs ON pc2.goodsCodeForSynchro=gs.goodsCode "+
				" left join WM_ProduceOrder_c pc on pc2.idForSynchro=pc.autoidForSynchro "+
				" WHERE  isnull(pc.goodsUid,'')<>isnull(gs.gid,'') "+
				" or isnull(pc2.produceOrderCuid,'')='' ";
		
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改委外订单材料表相关属性
	 *2016 2016年3月24日下午3:59:09
	 *int
	 *宋银海
	 */
	public int updOMMaterialsInfor(){
		String sql="UPDATE OM_Materials  "+
				" SET goodsGid = gs.gid, moDetailsGid=ods.gid "+
				" FROM OM_Materials oms "+
				" LEFT JOIN AA_Goods gs ON oms.goodsCodeForSynchro=gs.goodsCode "+
				" left join OM_Details ods on ods.moDetailsIdSynchro=oms.moDetailsIDForSynchro "+
				" WHERE  isnull(oms.goodsGid,'')<>isnull(gs.gid,'') "+
				" or isnull(oms.moDetailsGid,'')='' ";
		
		return this.update(sql);
	}
	
	/**
	 * @category 指定组织 帐套
	 *2016 2016年4月5日下午3:02:56
	 *int
	 *宋银海
	 */
	public int updProduceOrderOrgSob(String orgGid,String sobGid){
		String sql="update WM_ProduceOrder set orggid='"+orgGid+"', sobgId='"+sobGid+"' where isnull(sobgId,'')='' or isnull(orggid,'')='' ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 指定组织 帐套
	 *2016 2016年4月5日下午3:02:56
	 *int
	 *宋银海
	 */
	public int updOmOrderOrgSob(String orgGid,String sobGid){
		String sql="update OM_Main set orggid='"+orgGid+"', sobgId='"+sobGid+"' where isnull(sobgId,'')='' or isnull(orggid,'')='' ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 生成任务
	 *2016 2016年3月15日下午4:30:22
	 *int
	 *宋银海
	 */
	public boolean createTask(Task wt){
		return this.emiInsert(wt);
	}
	
	public boolean createTask(List<Task> wts){
		return this.emiInsert(wts);
	}
	
	/**
	 * @category 修改任务相关信息
	 *2016 2016年3月24日下午5:47:07
	 *int
	 *宋银海
	 */
	public int updateTaskInfor(){
		String sql="update WM_Task SET taskTypeUid=bt.gid,taskName=bt.billName from WM_Task wt "
				+ " LEFT JOIN WM_BillType bt ON wt.taskTypeCodeForSynchro=bt.billCode "
				+ " WHERE isnull(wt.taskTypeUid,'')='' ";
		return this.update(sql);
	}
	
	
	/**
	 * @category 修改采购任务对应billGid
	 *2016 2016年3月24日下午5:54:37
	 *int
	 *宋银海
	 */
	public int updateTaskProArrivalBillGid(){
		String sql="update WM_Task SET billGid=pa.gid from WM_Task wt "
				+ " LEFT JOIN WM_ProcureArrival pa ON wt.billIdentityForSynchro=pa.billCode "
				+ " WHERE wt.taskTypeCodeForSynchro in ('"+Constants.TASKTYPE_CGRK+"','"+Constants.TASKTYPE_CGZJ+"','"+Constants.TASKTYPE_CGTHCK+"','"+Constants.TASKTYPE_WWCPRK+"','"+Constants.TASKTYPE_WWCPZJ+"')"
				+ " and isnull(wt.billGid,'')='' ";
		return this.update(sql);
	}
	
	/**
	 * @category 修改销售出库对应billGid
	 *2016 2016年3月25日上午8:22:17
	 *int
	 *宋银海
	 */
	public int updateTaskSaleSendBillGid(){
		String sql="update WM_Task SET billGid=ss.gid from WM_Task wt "
				+ " LEFT JOIN WM_SaleSend ss ON wt.billIdentityForSynchro=ss.billCode "
				+ " WHERE wt.taskTypeCodeForSynchro in ('"+Constants.TASKTYPE_XSCK+"','"+Constants.TASKTYPE_XSZJ+"','"+Constants.TASKTYPE_XSTHRK+"')"
				+ " and isnull(wt.billGid,'')='' ";
		return this.update(sql);
	}
	
	/**
	 * @category 修改材料出库任务对应billGid
	 *2016 2016年3月24日下午5:54:37
	 *int
	 *宋银海
	 */
	public int updateTaskMeterialOutBillGid(){
		String sql="update WM_Task SET billGid=om.gid from WM_Task wt "
				+ " LEFT JOIN OM_Main om ON wt.billIdentityForSynchro=om.billCode "
				+ " WHERE wt.taskTypeCodeForSynchro in ('"+Constants.TASKTYPE_WWCLCK+"')"
				+ " and isnull(wt.billGid,'')='' ";
		return this.update(sql);
	}
	
	/**
	 * @category 删除任务
	 *2016 2016年3月21日上午9:39:54
	 *int
	 *宋银海
	 */
	public int deleteTask(String condition){
		String sql="delete from wm_task where "+condition;
		return this.update(sql);
	}
	
	
	
	/**
	 * @category 获得任务类型
	 *2016 2016年3月18日下午2:42:40
	 *WmBillType
	 *宋银海
	 */
	public BillType getBillType(String condition){
		String sql="select "+CommonUtil.colsFromBean(BillType.class)+" from wm_billtype where 1=1 "+condition;
		return (BillType)this.emiQuery(sql, BillType.class);
	}
	
	public List<BillType> getBillTypeList(String condition){
		String sql="select "+CommonUtil.colsFromBean(BillType.class)+" from wm_billtype where 1=1 "+condition;
		return this.emiQueryList(sql, BillType.class);
	}
	
	/**
	 * @category 查询到货单
	 *2016 2016年3月18日下午3:36:27
	 *List<Procurearrival>
	 *宋银海
	 */
	public List<Procurearrival> getProcurearrival(String condition){
		String sql="select "+CommonUtil.colsFromBean(Procurearrival.class)+" from WM_ProcureArrival where 1=1 "+condition;
		return this.emiQueryList(sql, Procurearrival.class);
	}
	
	//查询到货单
	@SuppressWarnings("unchecked")
	public List<Procurearrival> getPl(String condition){
		
		String sql="SELECT pv.cCode,pv.iverifystate,pv.dDate,pv.id,pv.cdefine1 from WM_ProcureArrival p "
				+ " left join "+Config.BUSINESSDATABASE+"PU_ArrivalVouch pv on p.autoidForSynchro=pv.id where 1=1 "+condition;
		return this.getJdbcTemplate().query(sql, new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Procurearrival p=new Procurearrival();
				p.setIdForSynchro(rs.getString("id"));
				p.setBillCode(rs.getString("cCode"));
				p.setBillstate(rs.getString("iverifystate"));
				p.setBilldate(rs.getTimestamp("dDate"));
				p.setDefine1(CommonUtil.isNullObject(rs.getObject("cdefine1"))?"":rs.getString("cdefine1"));
				return p;
			}
			
		});
		
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////到货单子表开始
	/**
	 * @category 查询到货单子表
	 *2016 2016年3月18日下午3:37:58
	 *List<ProcurearrivalC>
	 *宋银海
	 */
	public List<ProcurearrivalC> getProcurearrivalC1(String condition){
		String sql="select pc.gid,pc.procurearrivaluid,pc.number,pc.needcheck,pc.assistNumber,pc.idForSynchro,p.billCode,p.define1 from WM_ProcureArrival_C pc "
				+ " left join WM_ProcureArrival p on pc.procurearrivaluid=p.gid where 1=1 "+condition;
		
		return this.getJdbcTemplate().query(sql, new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				ProcurearrivalC pc=new ProcurearrivalC();
				pc.setBillIdentity(rs.getString("billCode"));
				pc.setMaindefine1(CommonUtil.isNullObject(rs.getObject("define1"))?"":rs.getString("define1"));
				return pc;
				
			}
			
		});
		
	}
	
	//查询到货单子表
	public List<ProcurearrivalC> getProcurearrivalC2(String condition){
		String sql="select pc.gid,pc.procurearrivaluid,pc.number,pc.needcheck,pc.assistNumber,pc.idForSynchro,p.billCode,p.define1 from WM_ProcureArrival_C pc "
				+ " left join WM_ProcureArrival p on pc.procurearrivaluid=p.gid "
				+ " left join "+Config.BUSINESSDATABASE+"PU_ArrivalVouchs paus8 on pc.autoidForSynchro=paus8.autoid where 1=1 "+condition;
		
		return this.getJdbcTemplate().query(sql, new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				ProcurearrivalC pc=new ProcurearrivalC();
				pc.setBillIdentity(rs.getString("billCode"));
				pc.setMaindefine1(CommonUtil.isNullObject(rs.getObject("define1"))?"":rs.getString("define1"));
				return pc;
			}
		});
	}
	
	//查询到货单子表
	@SuppressWarnings("unchecked")
	public List<ProcurearrivalC> getProcurearrivalC3(String condition){
		String sql="select ps.autoid,iQuantity,bGsp,p.cCode,p.dDate,p.cDefine1,p.iverifystateex,p.cbusType,ps.cDefine22 from "+Config.BUSINESSDATABASE+"PU_ArrivalVouchs ps "
				+ " left join "+Config.BUSINESSDATABASE+"PU_ArrivalVouch p on ps.id=p.id where 1=1 "+condition;
		
		return this.getJdbcTemplate().query(sql, new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				ProcurearrivalC pc=new ProcurearrivalC();
				pc.setNumber(rs.getBigDecimal("iQuantity"));
				pc.setBillIdentity(rs.getString("cCode"));
				pc.setMaindefine1(CommonUtil.isNullObject(rs.getObject("cDefine1"))?"":rs.getString("cDefine1"));
				pc.setBillIDate(rs.getTimestamp("dDate"));
				pc.setIverifystateex(rs.getInt("iverifystateex"));
				pc.setCbusTypeName(rs.getString("cbusType"));
				pc.setBodydefine22(CommonUtil.Obj2String(rs.getObject("cDefine22")));
				return pc;
			}
			
		});
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////到货单子表结束	

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////委外材料表开始
	//查询委外订单
	public List<OM_MOMain> getMOMain(String condition){
		
		String sql="SELECT myy.cCode from OM_Main mwms "
				+ " left join "+Config.BUSINESSDATABASE+"OM_MOMain myy on mwms.moidForSynchro=myy.MOID where 1=1 "+condition;
		return this.getJdbcTemplate().query(sql, new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				OM_MOMain o=new OM_MOMain();
				o.setBillCode(rs.getString("cCode"));
				return o;
			}
			
		});
		
	}
	
	
	/**
	 * @category 查询委外订单材料表
	 *2016 2016年3月18日下午3:37:58
	 *List<ProcurearrivalC>
	 *宋银海
	 */
	public List<OM_MOMaterials> getOMMaterials1(String condition){
		String sql="select m.billCode from OM_Materials om "
				+ " left join OM_Details od on om.moDetailsGid=od.gid"
				+ " left join OM_Main m on od.moMainGid=m.gid where 1=1 "+condition;
		
		return this.getJdbcTemplate().query(sql, new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				OM_MOMaterials om=new OM_MOMaterials();
				om.setBillIdentity(rs.getString("billCode"));
				return om;
				
			}
			
		});
		
	}
	
	
	//查询委外订单材料表
	public List<OM_MOMaterials> getOMMaterials3(String condition){
		String sql="select mm.cCode,mm.cState,mm.ddate from "+Config.BUSINESSDATABASE+"OM_MOMaterials me "
				+ " left join "+Config.BUSINESSDATABASE+"OM_MOMain mm on me.MOID=mm.MOID where 1=1 "+condition;
		
		return this.getJdbcTemplate().query(sql, new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				OM_MOMaterials oms=new OM_MOMaterials();
				oms.setBillIdentity(rs.getString("cCode"));
				oms.setState(rs.getInt("cState"));
				oms.setBillIDate(rs.getTimestamp("ddate"));
				return oms;
			}
			
		});
	}
	
	
	//查询到货单子表
	public List<OM_MOMaterials> getOMMaterials2(String condition){
		String sql="select pc.gid,pc.procurearrivaluid,pc.number,pc.needcheck,pc.assistNumber,pc.idForSynchro,p.billCode,p.define1 from WM_ProcureArrival_C pc "
				+ " left join WM_ProcureArrival p on pc.procurearrivaluid=p.gid "
				+ " left join "+Config.BUSINESSDATABASE+"PU_ArrivalVouchs paus8 on pc.autoidForSynchro=paus8.autoid where 1=1 "+condition;
		
		return this.getJdbcTemplate().query(sql, new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				ProcurearrivalC pc=new ProcurearrivalC();
				pc.setBillIdentity(rs.getString("billCode"));
				pc.setMaindefine1(CommonUtil.isNullObject(rs.getObject("define1"))?"":rs.getString("define1"));
				return pc;
			}
		});
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////委外材料表结束		

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////发货单子表开始	
	
	
	/**
	 * 查询发货单子表（获得需要删除的发货单子表）
	 *2016 2016年3月21日下午2:18:59
	 *List<SaleSendC>
	 *宋银海
	 */
	public List<SaleSendC> getSaleSendC1(String condition){
		String sql="select ssc.gid,ssc.salesendUid,ssc.number,ssc.idForSynchro,ss.billCode,ss.define1 from WM_SaleSend_C ssc "
				+ " left join WM_SaleSend ss on ssc.salesendUid=ss.gid where 1=1 "+condition;
		
		System.out.println(sql);
		
		return this.getJdbcTemplate().query(sql, new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				SaleSendC ssc=new SaleSendC();
				ssc.setMaindefine1("");
				ssc.setBillIdentity(rs.getString("billCode"));
				return ssc;
				
			}
			
		});
		
	}
	
	//查询到货单子表（获得需要修改的发货单子表）
	public List<SaleSendC> getSaleSendC2(String condition){
		String sql="select ssc.gid,ssc.salesendUid,ssc.number,ssc.idForSynchro,ss.billCode from WM_SaleSend_C ssc "
				+ " left join WM_SaleSend ss on ssc.salesendUid=ss.gid "
				+ " left join "+Config.BUSINESSDATABASE+"DispatchLists dis on ssc.autoidForSynchro=dis.autoid where 1=1 "+condition;
		
		return this.getJdbcTemplate().query(sql, new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				SaleSendC ssc=new SaleSendC();
				ssc.setBillIdentity(rs.getString("billCode"));
				return ssc;
			}
		});
	}
	
	
	//查询发货单子表（获得需要增加的发货单子表）
	public List<SaleSendC> getSaleSendC3(String condition){
		String sql="select dis.id,dis.baseQuantity,d.code,d.voucherDate,d.auditor from "+Config.BUSINESSDATABASE+"SA_SaleDelivery_b dis "
				+ " left join "+Config.BUSINESSDATABASE+"SA_SaleDelivery d on dis.idSaleDeliveryDTO=d.id where 1=1 "+condition;
		
		System.out.println(sql);
		
		return this.getJdbcTemplate().query(sql, new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				SaleSendC ssc=new SaleSendC();
				ssc.setBillIdentity(rs.getString("code"));
				ssc.setMaindefine1("");
				ssc.setNumber(rs.getBigDecimal("baseQuantity"));
				ssc.setBillIDate(rs.getTimestamp("voucherDate"));
				ssc.setCverifier(CommonUtil.Obj2String(rs.getObject("auditor")));
				return ssc;
			}
			
		});
	}
	
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////发货单子表结束	
	
	/**
	 * @category 修改发货单主表相关信息
	 *2016 2016年3月21日下午2:49:51
	 *int
	 *宋银海
	 */
	public int updSaleSendInfor(){
		String sql="UPDATE WM_SaleSend "+
				"SET customerUid=pc.gid "
				+ " FROM WM_SaleSend ss "
				+ " left join AA_Provider_Customer pc on ss.customerCodeForSynchro=pc.pcCode "
				+ " WHERE   isnull(ss.customerUid,'')<>isnull(pc.gid,'') "
				+ " and pc.flagForSynchro='c' ";
		return this.update(sql);
	}
	
	
	
	/**
	 * @category 修改发货单子表相关信息
	 *2016 2016年3月21日下午3:04:38
	 *int
	 *宋银海
	 */
	public int updSaleSendCInfor(){
		
		String sql="UPDATE WM_SaleSend_C  "+
				" SET whCode = wh.whCode  "+
				" FROM WM_SaleSend_C ssc "+
				" LEFT JOIN AA_WareHouse wh ON ssc.wareHouseForSynchro=wh.gid "+
				" where isnull(ssc.whCode, '') <> isnull(wh.whCode, '') ";
		
		return this.update(sql);
	}
	
	
	/**
	 * @category 指定组织帐套
	 *2016 2016年4月5日下午2:56:58
	 *int
	 *宋银海
	 */
	public int updSaleSendOrgSob(String orgGid,String sobGid){
		String sql="update WM_SaleSend set orggid='"+orgGid+"', sobgId='"+sobGid+"' where isnull(sobgId,'')='' or isnull(orggid,'')='' ";
		return this.update(sql);
	}
	
	
	
	@SuppressWarnings("unchecked")
	public List<SaleSend> getSs(String condition){
		
		String sql="SELECT dl.code,ss.define1 from WM_SaleSend ss "
				+ " left join "+Config.BUSINESSDATABASE+"SA_SaleDelivery dl on ss.gid=dl.id where 1=1 "+condition;
		
		System.out.println(sql);
		return this.getJdbcTemplate().query(sql, new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				SaleSend s=new SaleSend();
				
				s.setBillCode(rs.getString("code"));
				s.setDefine1("");
				return s;
			}
			
		});
		
	}
	
	/**
	 * @category 查询仓库
	 *2016 2016年4月28日下午1:24:52
	 *List<AaWarehouse>
	 *宋银海
	 */
	public List<AaWarehouse> getAaWarehouse(String condition){
		String sql="select "+CommonUtil.colsFromBean(AaWarehouse.class)+" from AA_WareHouse where "+condition;
		return this.emiQueryList(sql, AaWarehouse.class);
	}
	
	
	/**
	 * @category 从用友查询仓库现存量
	 *2016 2016年4月29日下午2:44:13
	 *List<WmAllocationstock>
	 *宋银海
	 */
	public List<WmAllocationstock> getWasYonYou(String condition){
		String sql="select wh.code,it.code goodsCode,cs.idwarehouse,cs.idinventory,cs.batch,cs.baseQuantity,cs.subQuantity from "+Config.BUSINESSDATABASE+"ST_CurrentStock cs"
				+ " left join "+Config.BUSINESSDATABASE+"AA_Warehouse wh on cs.idwarehouse=wh.id "
				+ " left join "+Config.BUSINESSDATABASE+"AA_Inventory it on cs.idinventory=it.id where cs.idwarehouse is not NULL "+condition;
		return this.getJdbcTemplate().query(sql, new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				WmAllocationstock was=new WmAllocationstock();
				was.setWhcode(rs.getString("code"));
				was.setGoodsuid(rs.getString("idinventory"));
				was.setGoodscode(rs.getString("goodsCode"));
				was.setBatch(CommonUtil.Obj2String(rs.getString("batch")));
				was.setNumber(CommonUtil.isNullObject(rs.getObject("baseQuantity"))?BigDecimal.valueOf(0):rs.getBigDecimal("baseQuantity"));
				was.setAssistnum(CommonUtil.isNullObject(rs.getObject("subQuantity"))?BigDecimal.valueOf(0):rs.getBigDecimal("subQuantity"));
				return was;
			}
			
		});
	}
	
	
	/**
	 * @category 从wms查询仓库现存量
	 *2016 2016年4月29日下午2:44:13
	 *List<WmAllocationstock>
	 *宋银海
	 */
	public List<WmAllocationstock> getWasWms(String condition){
		String sql="select gid,goodsAllocationUid,goodsAllocationCode,whCode,goodsUid,goodsCode,batch,number,assistNum,cfree1,cfree2 from WM_AllocationStock where "+condition;
		return this.getJdbcTemplate().query(sql, new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				WmAllocationstock was=new WmAllocationstock();
				was.setGid(rs.getString("gid"));
				was.setGoodsallocationuid(rs.getString("goodsAllocationUid"));
				was.setGoodsallocationcode(rs.getString("goodsAllocationCode"));
				was.setWhcode(rs.getString("whCode"));
				was.setGoodsuid(rs.getString("goodsUid"));
				was.setGoodscode(rs.getString("goodsCode"));
				was.setBatch(CommonUtil.Obj2String(rs.getString("batch")));
				was.setNumber(rs.getBigDecimal("number"));
				was.setAssistnum(CommonUtil.isNullObject(rs.getObject("assistNum"))?BigDecimal.valueOf(0):rs.getBigDecimal("assistNum"));
				was.setCfree1(CommonUtil.Obj2String(rs.getString("cfree1")));
				was.setCfree2(CommonUtil.Obj2String(rs.getString("cfree2")));
				return was;
			}
			
		});
	}
	
	
	/**
	 * @category 从用友查询仓库现存量
	 *2016 2016年4月29日下午2:44:13
	 *List<WmAllocationstock>
	 *宋银海
	 */
	public List<WmAllocationstock> getWasYonYouPos(String condition){
		
		String sql="SELECT la.idinventory,la.idinvlocation,la.idwarehouse,baseQuantity,subQuantity,batch,freeItem0,freeItem1,i.code inventoryCode "+
					",il.code inventoryLocationCode,wh.code warehouseCode from "+Config.BUSINESSDATABASE+"ST_LocationAccount la "+
					"LEFT JOIN "+Config.BUSINESSDATABASE+"AA_Inventory i on la.idinventory=i.id "+
					"LEFT JOIN "+Config.BUSINESSDATABASE+"AA_InventoryLocation il on la.idinvlocation=il.id "+
					"LEFT JOIN "+Config.BUSINESSDATABASE+"AA_Warehouse wh on la.idwarehouse=wh.id ";
		
		return this.getJdbcTemplate().query(sql, new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				
				
				WmAllocationstock was=new WmAllocationstock();
				was.setGoodsallocationcode(rs.getString("inventoryLocationCode"));
				was.setWhcode(rs.getString("warehouseCode"));
				was.setGoodscode(rs.getString("inventoryCode"));
				was.setBatch(CommonUtil.Obj2String(rs.getString("batch")));
				was.setNumber(rs.getBigDecimal("baseQuantity"));
				was.setAssistnum(CommonUtil.isNullObject(rs.getObject("subQuantity"))?BigDecimal.valueOf(0):rs.getBigDecimal("subQuantity"));
				was.setCfree1(CommonUtil.Obj2String(rs.getString("freeItem0")));
				was.setCfree2(CommonUtil.Obj2String(rs.getString("freeItem1")));
				return was;
			}
			
		});
	}
	
	
	
	/**
	 * @category 根据gid 删除现存量
	 * @param toDelete
	 * @return
	 * 宋银海
	 */
	public int[] deleteWmAllocationstock(final List<WmAllocationstock> toDelete){
		String sql="delete from WM_AllocationStock where gid=? ";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				WmAllocationstock wa=toDelete.get(index);
				ps.setString(1, wa.getGid());
			}
			
			@Override
			public int getBatchSize() {
				return toDelete.size();
			}
		});
	}
	
	
	/**
	 * @category 根据gid 修改现存量
	 * @param toUptNoPos
	 * @return
	 * 宋银海
	 */
	public int[] updateWmAllocationstock(final List<WmAllocationstock> toUptNoPos){
		String sql="update WM_AllocationStock set number=? where gid=? ";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				WmAllocationstock wa=toUptNoPos.get(index);
				ps.setString(1, CommonUtil.BigDecimal2BigDecimal(wa.getNumber()).toPlainString());
				ps.setString(2, wa.getGid());
			}
			
			@Override
			public int getBatchSize() {
				return toUptNoPos.size();
			}
		});
	}
	
	/**
	 * @category 增加现存量
	 * @param toDelete
	 * @return
	 * 宋银海
	 */
	public int[] insertWmAllocationstock(final List<WmAllocationstock> toDelete,final String orgGid,final String sobGid){
		String sql="insert into WM_AllocationStock(gid,goodsCode,batch,number,whCode,goodsAllocationCode,cfree1,cfree2,sobGid,orgGid) values (?,?,?,?,?,?,?,?,?,?) ";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				WmAllocationstock wa=toDelete.get(index);
				ps.setString(1, UUID.randomUUID().toString());
				ps.setString(2, wa.getGoodscode());
				ps.setString(3, wa.getBatch());
				ps.setBigDecimal(4, wa.getNumber());
				ps.setString(5, wa.getWhcode());
				ps.setString(6, wa.getGoodsallocationcode());
				ps.setString(7, wa.getCfree1());
				ps.setString(8, wa.getCfree2());
				ps.setString(9, sobGid);
				ps.setString(10, orgGid);
			}
			
			@Override
			public int getBatchSize() {
				return toDelete.size();
			}
		});
	}
	
	
	public int updWmAllocationstockInfor(){
		String sql="UPDATE WM_AllocationStock  "+
				" SET goodsUid = gs.gid, goodsAllocationUid=ga.gid "+
				" FROM WM_AllocationStock was "+
				" left join AA_Goods gs ON was.goodsCode=gs.goodsCode "+
				" left join AA_GoodsAllocation ga on was.goodsAllocationCode=ga.code "+
				" WHERE  isnull(was.goodsUid,'')='' "+
				" or isnull(was.goodsAllocationUid,'')='' ";
		return this.update(sql);
	}
	
	
	
}
