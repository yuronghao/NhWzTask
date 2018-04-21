package com.emi.wms.synchro.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import com.emi.cache.dao.CacheCtrldao;
import com.emi.common.service.EmiPluginService;
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
import com.emi.wms.synchro.dao.SynchroDaoU890;
import com.emi.wms.util.Constants;
import com.emi.wms.util.DateUtil;

public class SynchroServiceU890 extends EmiPluginService implements Serializable {

	private static final long serialVersionUID = -2162597524027074596L;
	private SynchroDaoU890 synchroDaoU8;
	
	private CacheCtrldao cacheCtrldao;

	public void setCacheCtrldao(CacheCtrldao cacheCtrldao) {
		this.cacheCtrldao = cacheCtrldao;
	}
	
	
	public void setSynchroDaoU8(SynchroDaoU890 synchroDaoU8) {
		this.synchroDaoU8 = synchroDaoU8;
	}


	public Map getOrgGid(){
		return synchroDaoU8.getOrgGid();
	}
	
	public Map getSobGid(){
		return synchroDaoU8.getSobGid();
	}
	
	
	/**
	 * @category 同步存货分类
	 *2016 2016年3月2日下午1:27:45
	 *void
	 *宋银海
	 */
	public void synchroInventoryClass(String orgGid,String sobGid){
		synchroDaoU8.deleteInventoryClass();//删除存货分类
		synchroDaoU8.addInventoryClass();//增加存货分类
		synchroDaoU8.updInventoryClass();//修改存货分类
		synchroDaoU8.updInventoryClassParentGid();//指定父id
		synchroDaoU8.updInventoryClassOrgSob(orgGid, sobGid);//指定帐套 组织
	}
	
	public void noTransynchroInventoryClass(String orgGid,String sobGid){
		int i=synchroDaoU8.deleteInventoryClass();//删除存货分类
		
		int j=synchroDaoU8.addInventoryClass();//增加存货分类
		
		int k=synchroDaoU8.updInventoryClass();//修改存货分类
		
		synchroDaoU8.updInventoryClassParentGid();//指定父id
		synchroDaoU8.updInventoryClassOrgSob(orgGid, sobGid);//指定帐套 组织
		
		if(i>0 || j>0 || k>0){
			cacheCtrldao.setClassify();
		}
	}
	
	
	/**
	 * @category 同步存货档案
	 *2016 2016年3月2日下午2:31:01
	 *void
	 *宋银海
	 */
	public void synchroInventory(String orgGid,String sobGid){
		synchroDaoU8.deleteInventory();//删除存货档案
		synchroDaoU8.addInventory();//增加存货档案
		synchroDaoU8.updInventory();//修改存货档案
		synchroDaoU8.updInventoryInfor();//修改相关属性
		synchroDaoU8.updInventoryOrgSob(orgGid, sobGid);//指定帐套组织
	}
	
	public void noTransynchroInventory(String orgGid,String sobGid){
		int i=synchroDaoU8.deleteInventory();//删除存货档案
		int j=synchroDaoU8.addInventory();//增加存货档案
		int k=synchroDaoU8.updInventory();//修改存货档案
		synchroDaoU8.updInventoryInfor();//修改相关属性
		synchroDaoU8.updInventoryOrgSob(orgGid, sobGid);//指定帐套组织
		
		if(i>0 || j>0 || k>0){
			cacheCtrldao.setGoods();
		}
	}
	
	
	/**
	 * @category 同步部门
	 *2016 2016年3月2日下午3:30:15
	 *void
	 *宋银海
	 */
	public void synchroDepartment(String orgGid,String sobGid){
		synchroDaoU8.deleteDepartment();//删除部门
		synchroDaoU8.addDepartment();//增加部门
		synchroDaoU8.uptDepartment();//修改部门
		synchroDaoU8.uptDepartmentParentGid();//修改自关联字段
		synchroDaoU8.updDepartmentOrgSob(orgGid, sobGid);//指定帐套组织
	}
	
	public void noTransynchroDepartment(String orgGid,String sobGid){
		int i=synchroDaoU8.deleteDepartment();//删除部门
		int j=synchroDaoU8.addDepartment();//增加部门
		int k=synchroDaoU8.uptDepartment();//修改部门
		synchroDaoU8.uptDepartmentParentGid();//修改自关联字段
		synchroDaoU8.updDepartmentOrgSob(orgGid, sobGid);//指定帐套组织
		if(i>0 || j>0 || k>0){
			cacheCtrldao.setDepartments();
		}
	}
	
	/**
	 * @category 同步客户
	 *2016 2016年3月2日下午4:12:46
	 *void
	 *宋银海
	 */
	public void synchroCustomer(String orgGid,String sobGid){
		synchroDaoU8.deleteCustomer();//删除客户
		synchroDaoU8.addCustomer();//增加客户
		synchroDaoU8.updCustomer();//修改客户
		synchroDaoU8.updCustomerOrgSob(orgGid, sobGid);//指定帐套组织
	}
	
	public void noTransynchroCustomer(String orgGid,String sobGid){
		int i=synchroDaoU8.deleteCustomer();//删除客户
		int j=synchroDaoU8.addCustomer();//增加客户
		int k=synchroDaoU8.updCustomer();//修改客户
		synchroDaoU8.updCustomerOrgSob(orgGid, sobGid);//指定帐套组织
		
		if(i>0 || j>0 || k>0){
			cacheCtrldao.setProviderCustomer();
		}
	}
	
	/**
	 * @category 同步仓库
	 *2016 2016年3月2日下午5:02:21
	 *void
	 *宋银海
	 */
	public void synchroWareHouse(String orgGid,String sobGid){
		synchroDaoU8.deleteWareHouse();//删除仓库
		synchroDaoU8.addWareHouse();//增加仓库
		synchroDaoU8.updWareHouse();//修改仓库
		synchroDaoU8.updWareHouseOrgSob(orgGid, sobGid);
	}
	
	
	public void noTransynchroWareHouse(String orgGid,String sobGid){
		int i=synchroDaoU8.deleteWareHouse();//删除仓库
		int j=synchroDaoU8.addWareHouse();//增加仓库
		int k=synchroDaoU8.updWareHouse();//修改仓库
		synchroDaoU8.updWareHouseOrgSob(orgGid, sobGid);
		
		if(i>0 || j>0 || k>0){
			cacheCtrldao.setWareHouses();
		}
	}
	
	/**
	 * @category 同步货位
	 *2016 2016年3月2日下午5:16:36
	 *void
	 *宋银海
	 */
	public void synchroGoodsAllocation(String orgGid,String sobGid){
		
		//synchroDaoU8.deleteGoodsAllocation();//删除货位
		synchroDaoU8.addGoodsAllocation();//增加货位
		synchroDaoU8.updGoodsAllocation();//修改货位
		synchroDaoU8.updGoodsAllocationInfor();//修改相关属性
		synchroDaoU8.updGoodsAllocationOrgSob(orgGid, sobGid);
	}
	
	
	public void noTransynchroGoodsAllocation(String orgGid,String sobGid){
		
		//synchroDaoU8.deleteGoodsAllocation();//删除货位
		int i=synchroDaoU8.addGoodsAllocation();//增加货位
		int j=synchroDaoU8.updGoodsAllocation();//修改货位
		synchroDaoU8.updGoodsAllocationInfor();//修改相关属性
		synchroDaoU8.updGoodsAllocationOrgSob(orgGid, sobGid);
		

		if(i>0 || j>0){
			cacheCtrldao.setGoodsAllocation();
		}
	}
	
	
	/**
	 * @category 同步单位
	 *2016 2016年3月3日上午8:47:33
	 *void
	 *宋银海
	 */
	public void synchroUnit(String orgGid,String sobGid){
		synchroDaoU8.deleteUnit();//删除单位
		synchroDaoU8.addUnit();//增加单位
		synchroDaoU8.updUnit();//修改单位
		synchroDaoU8.updUnitOrgSob(orgGid, sobGid);//指定组织 帐套
	}
	
	public void noTransynchroUnit(String orgGid,String sobGid){
		int i=synchroDaoU8.deleteUnit();//删除单位
		int j=synchroDaoU8.addUnit();//增加单位
		int k=synchroDaoU8.updUnit();//修改单位
		synchroDaoU8.updUnitOrgSob(orgGid, sobGid);//指定组织 帐套
		
		if(i>0 || j>0 || k>0){
			cacheCtrldao.setUnit();
		}
	}
	
	
	/**
	 * @category 同步供应商
	 *2016 2016年3月3日上午8:53:02
	 *void
	 *宋银海
	 */
	public void synchroProvider(String orgGid,String sobGid){
		synchroDaoU8.deleteProvider();//删除供应商
		synchroDaoU8.addProvider();//增加供应商
		synchroDaoU8.updProvider();//修改供应商
		synchroDaoU8.updProviderOrgSob(orgGid, sobGid);//指定组织 帐套
	}
	
	
	public void noTransynchroProvider(String orgGid,String sobGid){
		int i=synchroDaoU8.deleteProvider();//删除供应商
		int j=synchroDaoU8.addProvider();//增加供应商
		int k=synchroDaoU8.updProvider();//修改供应商
		synchroDaoU8.updProviderOrgSob(orgGid, sobGid);//指定组织 帐套
		
		if(i>0 || j>0 || k>0){
			cacheCtrldao.setProviderCustomer();
		}
	}
	
	
	/**
	 * @category 同步用户表
	 *2016 2016年3月3日上午9:05:16
	 *void
	 *宋银海
	 */
	public void synchroUser(String orgGid,String sobGid){
		synchroDaoU8.addUser();//增加用户
		synchroDaoU8.updUser();//修改用户
		synchroDaoU8.updUserOrgSob(orgGid, sobGid);//指定组织 帐套
	}
	
	public void noTransynchroUser(String orgGid,String sobGid){
		int i=synchroDaoU8.addUser();//增加用户
		int j=synchroDaoU8.updUser();//修改用户
		synchroDaoU8.updUserOrgSob(orgGid, sobGid);//指定组织 帐套
		if(i>0 || j>0){
			 cacheCtrldao.setUser();
		}
	}
	
	
	/**
	 * @category 同步人员
	 *2016 2016年3月3日上午9:45:36
	 *void
	 *宋银海
	 */
	public void synchroPerson(String orgGid,String sobGid){
		synchroDaoU8.deletePerson();//删除人员
		synchroDaoU8.addPerson();//增加人员
		synchroDaoU8.updPerson();//修改人员
		synchroDaoU8.updPersonInfor();//修改相关属性
		synchroDaoU8.updPersonOrgSob(orgGid, sobGid);//指定组织 帐套
	}
	
	public void noTransynchroPerson(String orgGid,String sobGid){
		int i=synchroDaoU8.deletePerson();//删除人员
		int j=synchroDaoU8.addPerson();//增加人员
		int k=synchroDaoU8.updPerson();//修改人员
		synchroDaoU8.updPersonInfor();//修改相关属性
		synchroDaoU8.updPersonOrgSob(orgGid, sobGid);//指定组织 帐套
		
		if(i>0 || j>0 || k>0){
			cacheCtrldao.setPersons();
		}
	}
	
	
	/**
	 * @category 同步收发类别
	 *2016 2016年4月23日下午3:07:40
	 *void
	 *宋银海
	 */
	public void synchroRdStyle(String orgGid,String sobGid){
		synchroDaoU8.deleteRdStyle();//删除收发类别
		synchroDaoU8.addRdStyle();//增加收发类别
		synchroDaoU8.updRdStyle();//修改收发类别
		synchroDaoU8.updRdStyleOrgSob(orgGid, sobGid);//指定组织 帐套
	}
	
	
	public void noTransynchroRdStyle(String orgGid,String sobGid){
		int i=synchroDaoU8.deleteRdStyle();//删除收发类别
		int j=synchroDaoU8.addRdStyle();//增加收发类别
		int k=synchroDaoU8.updRdStyle();//修改收发类别
		synchroDaoU8.updRdStyleOrgSob(orgGid, sobGid);//指定组织 帐套
		

	}
	
	/**
	 * @category 同步用户自定义项
	 *2016 2016年4月23日下午3:07:40
	 *void
	 *宋银海
	 */
	public void synchroUserDefine(String orgGid,String sobGid){
		synchroDaoU8.deleteUserDefine();//删除用户自定义项
		synchroDaoU8.addUserDefine();//增加用户自定义项
		synchroDaoU8.updUserDefine();//修改用户自定义项
		synchroDaoU8.updUserDefineOrgSob(orgGid, sobGid);//指定组织 帐套
	}
	
	
	public void noTransynchroUserDefine(String orgGid,String sobGid){
		synchroDaoU8.deleteUserDefine();//删除用户自定义项
		synchroDaoU8.addUserDefine();//增加用户自定义项
		synchroDaoU8.updUserDefine();//修改用户自定义项
		synchroDaoU8.updUserDefineOrgSob(orgGid, sobGid);//指定组织 帐套
	}
	
	/**
	 * @category 同步工作中心
	 *2016 2016年3月3日上午10:51:13
	 *void
	 *宋银海
	 */
	public void synchroWorkCenter(String orgGid,String sobGid){
		
		synchroDaoU8.deleteWorkCenter();//删除工作中心
		synchroDaoU8.addWorkCenter();//增加工作中心
		synchroDaoU8.updWorkCenter();//修改工作中心
		synchroDaoU8.updWorkCenterInfor();//修改相关属性
		synchroDaoU8.updWorkCenterOrgSob(orgGid, sobGid);//指定组织 帐套
	}
	
	
	/**
	 * @category 同步标准工序
	 *2016 2016年3月3日下午3:11:33
	 *void
	 *宋银海
	 */
	public void synchroStandardprocess(String orgGid,String sobGid){
		synchroDaoU8.deleteStandardprocess();//删除标准工序
		synchroDaoU8.addStandardprocess();//增加标准工序
		synchroDaoU8.updStandardprocess();//修改标准工序
		synchroDaoU8.updStandardprocessOrgSob(orgGid, sobGid);//指定组织 帐套
	}
	
	
	/**
	 * @category 同步产品结构
	 *2016 2016年3月14日上午9:14:25
	 *void
	 *宋银海
	 */
	public void synchroProductStructure(){
		synchroDaoU8.deleteProductStructure();//删除产品结构主表
		synchroDaoU8.addProductStructure();//增加产品结构主表
		synchroDaoU8.updProductStructure();//修改产品结构主表
		synchroDaoU8.updProductStructureInfor();//修改关联字段
		
		synchroDaoU8.deleteProductStructurec();//删除产品结构子表
		synchroDaoU8.addProductStructurec();//增加产品结构子表
		synchroDaoU8.updProductStructurec();//修改产品结构子表
		synchroDaoU8.updProductStructurecInfor();//修改关联字段
	}
	
	
	/**
	 * @category 同步工艺路线
	 *2016 2016年3月15日上午9:07:35
	 *void
	 *宋银海
	 */
	public void synchroProcessRoute(){
		synchroDaoU8.deleteProcessRoute();//删除工艺路线主表
		synchroDaoU8.addProcessRoute();//增加工艺路线主表
		synchroDaoU8.updProcessRoute();//修改工艺路线主表
		synchroDaoU8.updProcessRouteInfor();//修改关联字段
				
		synchroDaoU8.deleteProcessRouteC();//删除工艺路线子表
		synchroDaoU8.addProcessRouteC();//增加工艺路线子表
		synchroDaoU8.updProcessRouteC();//修改工艺路线子表
		synchroDaoU8.updProcessRouteCInfor();//修改关联字段
	}
	
	
	/**
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @category 同步采购到货单
	 *2016 2016年1月5日上午10:55:49
	 *void
	 *宋银海
	 */
	public void synchroProArrival(String orgGid,String sobGid) throws Exception {
		
		//DateUtil.nextDay(-90, "yyyy-MM-dd")
//		String condition=" and billCode not in (select cCode from "+Config.BUSINESSDATABASE+"PU_ArrivalVouch ) ";获得需要删除的到货单主表
//		List<Procurearrival> plTodelete=synchroDaoU8.getProcurearrival(condition);
//		condition=" and cCode not in (select billCode from WM_ProcureArrival) ";获得需要增加的到货单主表
//		List<Procurearrival> plToAdd=synchroDaoU8.getPl(condition);
		
		String condition=" and ps.autoid not in (select autoidForSynchro from WM_ProcureArrival_C) ";
		List<ProcurearrivalC> plsToAdd=synchroDaoU8.getProcurearrivalC3(condition);//获得需要增加的到货单子表
		
		condition=" and pc.autoidForSynchro not in (select autoid from "+Config.BUSINESSDATABASE+"PU_ArrivalVouchs ) ";
		List<ProcurearrivalC> plsToDelete=synchroDaoU8.getProcurearrivalC1(condition);//获得需要删除的到货单子表
		
		condition=" and isnull(pc.needCheck,'')<>isnull(paus8.bGsp,'') or isnull(pc.define22,'')<>isnull(paus8.cdefine22,'') ";
		List<ProcurearrivalC> plsToUpd=synchroDaoU8.getProcurearrivalC2(condition);//获得需要修改的到货单子表
		
		condition=" and isnull(p.define1,'')<>isnull(pv.cDefine1,'') or isnull(p.stateForSynchro,-1)<>isnull(pv.iverifystateex,-1) ";//获得需要修改的到货单主表 (主表define1发生变化的记录 ，审核状态发生变化的记录)
		List<Procurearrival> plToAdd=synchroDaoU8.getPl(condition);
		
		
		//删除任务 billIdentityToDelete单据身份
		Set<String> billIdentityToDelete=new HashSet<String>();
		for(ProcurearrivalC pc:plsToAdd){
			if(!CommonUtil.isNullObject(pc.getBillIdentity())){
				billIdentityToDelete.add(pc.getBillIdentity());
			}
		}
		
		for(ProcurearrivalC pc:plsToUpd){
			if(!CommonUtil.isNullObject(pc.getBillIdentity())){
				billIdentityToDelete.add(pc.getBillIdentity());
			}
		}
		
		for(ProcurearrivalC pc:plsToDelete){
			if(!CommonUtil.isNullObject(pc.getBillIdentity())){
				billIdentityToDelete.add(pc.getBillIdentity());
			}
		}
		
		for(Procurearrival p:plToAdd){
			if(!CommonUtil.isNullObject(p.getBillCode())){
				billIdentityToDelete.add(p.getBillCode());
			}
		}
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////处理任务（只有增加 和 删除）
		Iterator<String> iterator=billIdentityToDelete.iterator();
		StringBuffer sbf=new StringBuffer();
		while(iterator.hasNext()){
			sbf.append("'"+iterator.next()+"',");
		};
		String billIdentity=sbf.toString();
		if(billIdentity.length()>0){
			billIdentity=billIdentity.substring(0, billIdentity.length()-1);
			billIdentity="("+billIdentity+")";
		}
		
		List<ProcurearrivalC> plsToAddSecond=null;
		if(!CommonUtil.isNullObject(billIdentity)){
			condition=" and p.cCode in "+billIdentity;
			plsToAddSecond=synchroDaoU8.getProcurearrivalC3(condition);//获得发货单对应的到货单子表
		}
		
		Iterator<String> iteratorTask=billIdentityToDelete.iterator();
		List<Task> tasks=new ArrayList<Task>();
		while(iteratorTask.hasNext()){
			Task task=null;
			boolean toCreateTaskIn=false;//创建入库任务
			boolean toCreateTaskCheck=false;//创建质检任务
			boolean returnFlag=false;//退货标志
			boolean auditFlag=false;//审核标志
			String billIdent=iteratorTask.next();
			String cbusTypeName="";//业务类型名称
			
			String billId=null;
			Timestamp completetime=null;
			for(ProcurearrivalC pcAdd:plsToAddSecond){
				if(billIdent.equalsIgnoreCase(pcAdd.getBillIdentity())){
					
					if(cbusTypeName.equalsIgnoreCase("")){
						cbusTypeName=pcAdd.getCbusTypeName();
					}
					
					if(pcAdd.getIverifystateex().intValue()==2){
						auditFlag=true;
					}
					
					if(billId==null){
						billId=pcAdd.getBillIdentity();
						completetime=pcAdd.getBillIDate();
					}
					
					if(pcAdd.getNumber().doubleValue()<0){
						returnFlag=true;
					}
					
					//获得参数判断是否是采用表头自定义项质检  还是标准的质检功能     表头质检"是"无论表体情况都是质检；表头质检"否"，再考虑表体是否质检。
					Map headParamMap=getymsetting("headCheckFlag");
					Map bodyeParamMap=getymsetting("bodyCheckFlag");
					
					if(headParamMap.get("paramValue").toString().startsWith("define")){//表头定义质检
						
						Class<?> clz=pcAdd.getClass();
						Method headMethod=clz.getMethod("getMain"+headParamMap.get("paramValue").toString(),null);
						String headDefineValue=CommonUtil.Obj2String((String)headMethod.invoke(pcAdd, null));
						
						if(headDefineValue.equalsIgnoreCase("是")){
							toCreateTaskCheck=true;
							break;
						}else{
							
							if(bodyeParamMap.get("paramValue").toString().startsWith("define")){
								
								Method bodyMethod=clz.getMethod("getBody"+bodyeParamMap.get("paramValue").toString(),null);
								String bodyDefineValue=CommonUtil.Obj2String((String)bodyMethod.invoke(pcAdd, null));
								
								if(bodyDefineValue.equalsIgnoreCase("是")){
									toCreateTaskCheck=true;
								}else{
									toCreateTaskIn=true;
								}
							}
							
						}
						
					}else if(bodyeParamMap.get("paramValue").toString().startsWith("define")){//表体定义质检
						
						Class<?> clz=pcAdd.getClass();
						if(bodyeParamMap.get("paramValue").toString().startsWith("define")){
							Method bodyMethod=clz.getMethod("getBody"+bodyeParamMap.get("paramValue").toString(),null);
							String bodyDefineValue=(String)bodyMethod.invoke(pcAdd, null);
							
							if(bodyDefineValue.equalsIgnoreCase("是")){
								toCreateTaskCheck=true;
							}else{
								toCreateTaskIn=true;
							}
						} 
						
					}else{
						if((CommonUtil.isNullObject(pcAdd.getNeedcheck())?0:pcAdd.getNeedcheck().doubleValue())==1){//标准功能质检任务
							toCreateTaskCheck=true;
						}else{                                                                                      //普通入库任务
							toCreateTaskIn=true;
						}
					}

				}
				
				if(toCreateTaskIn && toCreateTaskCheck){
					break;
				}
			}
			
			if(auditFlag&&toCreateTaskIn){
				task=new Task();
				if(cbusTypeName.equalsIgnoreCase("普通采购") || cbusTypeName.equalsIgnoreCase("代管采购") || cbusTypeName.equalsIgnoreCase("固定资产") ){
					if(returnFlag){
						task.setTaskTypeCodeForSynchro(Constants.TASKTYPE_CGTHCK);
					}else{
						task.setTaskTypeCodeForSynchro(Constants.TASKTYPE_CGRK);
					}
					task.setBillGidSource(Constants.BILLGIDSOURCE_CGDH);
					
				}else if(cbusTypeName.equalsIgnoreCase("委外加工")){
					task.setTaskTypeCodeForSynchro(Constants.TASKTYPE_WWCPRK);
					task.setBillGidSource(Constants.BILLGIDSOURCE_WWDH);
				}

				task.setCompletetime(completetime);
				task.setDistributeTime(new Timestamp(new Date().getTime()));
				task.setState(0);
				task.setBillIdentityForSynchro(billId);
				task.setBillCode(billId);
				task.setSobgid(sobGid);
				task.setOrggid(orgGid);
				tasks.add(task);
			}
			
			if(auditFlag&&toCreateTaskCheck){
				task=new Task();
				if(cbusTypeName.equalsIgnoreCase("普通采购") || cbusTypeName.equalsIgnoreCase("代管采购") || cbusTypeName.equalsIgnoreCase("固定资产") ){
					task.setTaskTypeCodeForSynchro(Constants.TASKTYPE_CGZJ);
					task.setBillGidSource(Constants.BILLGIDSOURCE_CGDH);
				}else if(cbusTypeName.equalsIgnoreCase("委外加工")){
					task.setTaskTypeCodeForSynchro(Constants.TASKTYPE_WWCPZJ);
					task.setBillGidSource(Constants.BILLGIDSOURCE_WWDH);
				}
				
				
				task.setCompletetime(completetime);
				task.setDistributeTime(new Timestamp(new Date().getTime()));
				task.setState(0);
				task.setBillIdentityForSynchro(billId);
				task.setBillCode(billId);
				task.setSobgid(sobGid);
				task.setOrggid(orgGid);
				tasks.add(task);				
			}
		}
		
		//////////////////////////////////////////////////////////////////////////////////////////处理订单主表
		synchroDaoU8.deleteProArrival();//删除到货单主表
		synchroDaoU8.addProArrival();//增加到货单主表
		synchroDaoU8.updProArrival();//修改到货单主表
		synchroDaoU8.updProArrivalInfor();//修改相关字段
		
		//////////////////////////////////////////////////////////////////////////////////////////处理订单子表
		synchroDaoU8.deleteProArrivalC();//删除到货单子表
		synchroDaoU8.addProArrivalC();//增加到货单子单
		synchroDaoU8.updProArrivalC();//修改到货单子单
		synchroDaoU8.updProArrivalCInfor();//修改关联字段
		synchroDaoU8.updProArrivalOrgSob(orgGid, sobGid);
		
		//根据billIdentityToDelete 删除任务
		if(!CommonUtil.isNullObject(billIdentity)){
			condition=" billIdentityForSynchro in "+billIdentity+" and taskTypeCodeForSynchro in('0008','0009','0010','0170')";
			synchroDaoU8.deleteTask(condition);
		}
		
		//生成入库任务 或者 质检任务
		if(tasks.size()>0){
			synchroDaoU8.createTask(tasks);
			synchroDaoU8.updateTaskInfor();//跟WM_BillType 做关联
			synchroDaoU8.updateTaskProArrivalBillGid();//修改采购出库对应billGid
		}
		
		//删除已经在u8中完成的任务
		synchroDaoU8.deleProArrivalInvalidTask();
		
		
	}
	
	
	/**
	 * @category 同步销售发货单
	 *2016 2016年3月14日上午11:21:39
	 *void
	 *宋银海
	 */
	public void synchroSaleSend(String orgGid,String sobGid) throws Exception{
		
		String condition=" and dis.iDLsID not in (select autoidForSynchro from WM_SaleSend_C) ";
		List<SaleSendC> plsToAdd=synchroDaoU8.getSaleSendC3(condition);//获得需要增加的发货单子表
		
		condition=" and ssc.autoidForSynchro not in (select iDLsID from "+Config.BUSINESSDATABASE+"DispatchLists ) ";
		List<SaleSendC> plsToDelete=synchroDaoU8.getSaleSendC1(condition);//获得需要删除的发货单子表
		
		condition=" and  isnull(ssc.define22,'')<>isnull(dis.cdefine22,'') ";
		List<SaleSendC> plsToUpd=synchroDaoU8.getSaleSendC2(condition);//获得需要修改的发货单子表
		
		condition=" and (isnull(ss.define1,'')<>isnull(dl.cDefine1,'') or isnull(ss.stateForSynchro,'')<>isnull(dl.cVerifier,'')) ";//获得需要修改的发货单主表 (主表define1发生变化的记录 ，审核状态发生变化的记录)
		List<SaleSend> ssToAdd=synchroDaoU8.getSs(condition);
		
		
		//删除任务 billIdentityToDelete单据身份
		Set<String> billIdentityToDelete=new HashSet<String>();
		for(SaleSendC ssc:plsToAdd){
			billIdentityToDelete.add(ssc.getBillIdentity());
		}
		
		for(SaleSendC ssc:plsToDelete){
			billIdentityToDelete.add(ssc.getBillIdentity());
		}
		
		for(SaleSendC pc:plsToUpd){
			if(!CommonUtil.isNullObject(pc.getBillIdentity())){
				billIdentityToDelete.add(pc.getBillIdentity());
			}
			
		}
		
		for(SaleSend ss:ssToAdd){
			if(!CommonUtil.isNullObject(ss.getBillCode())){
				billIdentityToDelete.add(ss.getBillCode());
			}
			
		}
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////处理任务（只有增加 和 删除）
		Iterator<String> iterator=billIdentityToDelete.iterator();
		StringBuffer sbf=new StringBuffer();
		while(iterator.hasNext()){
			sbf.append("'"+iterator.next()+"',");
		};
		String billIdentity=sbf.toString();
		if(billIdentity.length()>0){
			billIdentity=billIdentity.substring(0, billIdentity.length()-1);
			billIdentity="("+billIdentity+")";
		}
		
		List<SaleSendC> plsToAddSecond=null;
		if(!CommonUtil.isNullObject(billIdentity)){
			condition=" and d.cDLCode in "+billIdentity;
			plsToAddSecond=synchroDaoU8.getSaleSendC3(condition);//获得删除的发货单对应的到货单子表
		}
		
		
		Iterator<String> iteratorTask=billIdentityToDelete.iterator();
		List<Task> tasks=new ArrayList<Task>();
		while(iteratorTask.hasNext()){
			Task task=null;
			boolean toCreateTaskOut=false;//销售出库
			boolean returnFlag=false;//退货标志
			boolean toCreateTaskCheck=false;//销售质检
			boolean auditFlag=false;//审核标志
			String billIdent=iteratorTask.next();
			
			String billId=null;
			Timestamp completetime=null;
			for(SaleSendC pcAdd:plsToAddSecond){
				if(billIdent.equalsIgnoreCase(pcAdd.getBillIdentity())){
					
					if(!CommonUtil.isNullObject(pcAdd.getCverifier())){
						auditFlag=true;
					}
					
					if(billId==null){
						billId=pcAdd.getBillIdentity();
						completetime=pcAdd.getBillIDate();
					}
					
					if(pcAdd.getNumber().doubleValue()<0){
						returnFlag=true;
					}
					
					//获得参数判断是否是采用表头自定义项质检  还是标准的质检功能
//					Map paramMap=getymsetting("headCheckFlag");
//					if(paramMap.get("paramValue").toString().startsWith("define")){//表头定义质检
//						
//						String defineValue1="";
//						Class<?> clz=pcAdd.getClass();
//						try{
//							Method method=clz.getMethod("get"+paramMap.get("paramValue").toString().substring(0, 1).toUpperCase()+paramMap.get("paramValue").toString().substring(1),null);
//							defineValue1=(String)method.invoke(pcAdd, null);
//							
//						}catch(Exception e){
//							e.printStackTrace();
//						}
//						
//						
//						if(defineValue1.equalsIgnoreCase("是")){
//							toCreateTaskCheck=true;
//							break;
//						}else{
//							toCreateTaskOut=true;
//							break;
//						}
//						
//					}else{
//						toCreateTaskOut=true;
//						break;
//					}
					
					//获得参数判断是否是采用表头自定义项质检  还是标准的质检功能     表头质检"是"无论表体情况都是质检；表头质检"否"，再考虑表体是否质检。
					Map headParamMap=getymsetting("headCheckFlag");
					Map bodyeParamMap=getymsetting("bodyCheckFlag");
					
					if(headParamMap.get("paramValue").toString().startsWith("define")){//表头定义质检
						
						Class<?> clz=pcAdd.getClass();
						Method headMethod=clz.getMethod("getMain"+headParamMap.get("paramValue").toString(),null);
						String headDefineValue=CommonUtil.Obj2String((String)headMethod.invoke(pcAdd, null));
						
						if(headDefineValue.equalsIgnoreCase("是")){
							toCreateTaskCheck=true;
							break;
						}else{
							
							if(bodyeParamMap.get("paramValue").toString().startsWith("define")){
								
								Method bodyMethod=clz.getMethod("getBody"+bodyeParamMap.get("paramValue").toString(),null);
								String bodyDefineValue=CommonUtil.Obj2String((String)bodyMethod.invoke(pcAdd, null));
								
								if(bodyDefineValue.equalsIgnoreCase("是")){
									toCreateTaskCheck=true;
								}else{
									toCreateTaskOut=true;
								}
							}
							
						}
						
					}else if(bodyeParamMap.get("paramValue").toString().startsWith("define")){//表体定义质检
						
						Class<?> clz=pcAdd.getClass();
						if(bodyeParamMap.get("paramValue").toString().startsWith("define")){
							Method bodyMethod=clz.getMethod("getBody"+bodyeParamMap.get("paramValue").toString(),null);
							String bodyDefineValue=(String)bodyMethod.invoke(pcAdd, null);
							
							if(bodyDefineValue.equalsIgnoreCase("是")){
								toCreateTaskCheck=true;
							}else{
								toCreateTaskOut=true;
							}
						} 
						
					}

				}
				
				if(toCreateTaskOut && toCreateTaskCheck){
					break;
				}
					
			}
			
			if(auditFlag&&toCreateTaskOut){//普通销售出库
				task=new Task();
				if(returnFlag){
					task.setTaskTypeCodeForSynchro(Constants.TASKTYPE_XSTHRK);
				}else{
					task.setTaskTypeCodeForSynchro(Constants.TASKTYPE_XSCK);
				}
				
				task.setCompletetime(completetime);
				task.setDistributeTime(new Timestamp(new Date().getTime()));
				task.setState(0);
				task.setBillIdentityForSynchro(billId);
				task.setBillCode(billId);
				task.setBillGidSource(Constants.BILLGIDSOURCE_XSFH);
				task.setSobgid(sobGid);
				task.setOrggid(orgGid);
				tasks.add(task);
			}
			
			if(auditFlag&&toCreateTaskCheck){//销售质检
				task=new Task();
				task.setTaskTypeCodeForSynchro(Constants.TASKTYPE_XSZJ);
				task.setCompletetime(completetime);
				task.setDistributeTime(new Timestamp(new Date().getTime()));
				task.setState(0);
				task.setBillIdentityForSynchro(billId);
				task.setBillCode(billId);
				task.setBillGidSource(Constants.BILLGIDSOURCE_XSFH);
				task.setSobgid(sobGid);
				task.setOrggid(orgGid);
				tasks.add(task);
			}
			
		}
		
		//////////////////////////////////////////////////////////////////////////////////////////处理订单主表
		synchroDaoU8.deleteSaleSend();//删除发货单主表
		synchroDaoU8.addSaleSend();//增加发货单主表
		synchroDaoU8.updSaleSend();//修改发货单主表
		synchroDaoU8.updSaleSendInfor();//修改相关字段
		
		//////////////////////////////////////////////////////////////////////////////////////////处理订单子表
		synchroDaoU8.deleteSaleSendC();//删除发货单子表
		synchroDaoU8.addSaleSendC();//增加发货单子表
		synchroDaoU8.updSaleSendC();//修改发货单子表
		synchroDaoU8.updSaleSendCInfor();//修改相关字段
		synchroDaoU8.updSaleSendOrgSob(orgGid, sobGid);
		
		//根据billIdentityToDelete 删除任务
		if(!CommonUtil.isNullObject(billIdentity)){
			condition=" billIdentityForSynchro in "+billIdentity +" and taskTypeCodeForSynchro in('0038','0039','0040')";
			synchroDaoU8.deleteTask(condition);
		}
		
		//生成出库任务
		if(tasks.size()>0){
			synchroDaoU8.createTask(tasks);
			synchroDaoU8.updateTaskInfor();//跟WM_BillType 做关联
			synchroDaoU8.updateTaskSaleSendBillGid();//修改销售出库对应billGid
		}
		
		
	}
	
	
	
	/**
	 * @category 同步生产订单
	 *2016 2016年3月14日下午2:33:35
	 *void
	 *宋银海
	 */
	public void synchroProduceOrder(String orgGid,String sobGid){
		//////////////////////////////////////////////////////////////////////////////////////////处理订单主表
		synchroDaoU8.deleteProduceOrder();//删除生产订单主表
		synchroDaoU8.addProduceOrder();//增加生产订单主表
		synchroDaoU8.updProduceOrder();//修改生产订单主表
		
		//////////////////////////////////////////////////////////////////////////////////////////处理订单产品表
		synchroDaoU8.deleteProduceOrderC();//删除生产订单产品表
		synchroDaoU8.addProduceOrderC();//增加生产订单产品表
		synchroDaoU8.updProduceOrderC();//修改生产订单产品表
		synchroDaoU8.updProduceOrderCInfor();//修改生产订单产品表相关字段
		
		//////////////////////////////////////////////////////////////////////////////////////////处理订单材料表
		synchroDaoU8.deleteProduceOrderC2();//删除生产订单材料表
		synchroDaoU8.addProduceOrderC2();//增加生产订单材料表
		synchroDaoU8.updProduceOrderC2();//修改生产订单材料表
		synchroDaoU8.updProduceOrderC2Infor();//修改相关字段
		
		synchroDaoU8.updProduceOrderOrgSob(orgGid, sobGid);//指定组织 帐套
		
	}
	
	//货位管理的仓库
	public String getWarehousePos(){
		String condition=" whPos=1 ";
		List<AaWarehouse> aaWarehouse=synchroDaoU8.getAaWarehouse(condition);//启用货位的仓库
		
		StringBuffer sb=new StringBuffer();
		for(AaWarehouse wh:aaWarehouse){
			sb.append("'"+wh.getWhcode()+"',");
		}
		String whCode=sb.toString();
		if(whCode.length()>0){
			whCode="("+whCode.substring(0, whCode.length()-1)+")";
		}
		return whCode;
	}
	
	//没启用货位管理的仓库
	public String getWarehouseNoPos(){
		String condition=" whPos=0 ";
		List<AaWarehouse> aaWarehouse=synchroDaoU8.getAaWarehouse(condition);//不启用货位的仓库
		
		StringBuffer sb=new StringBuffer();
		for(AaWarehouse wh:aaWarehouse){
			sb.append("'"+wh.getWhcode()+"',");
		}
		String whCode=sb.toString();
		if(whCode.length()>0){
			whCode="("+whCode.substring(0, whCode.length()-1)+")";
		}
		return whCode;
	}
	
	/**
	 * @category 同步货位现存量
	 *2016 2016年4月28日下午1:10:23
	 *void
	 *宋银海
	 */
	public void synchroAllocationStock(String orgGid,String sobGid){
		
		String whCodeNoPos=getWarehouseNoPos();
		String noPosYonYouCondition=" cWhCode in "+whCodeNoPos;
		List<WmAllocationstock> wasYonYou=new ArrayList<WmAllocationstock>();
		if(!CommonUtil.isNullObject(whCodeNoPos)){
			wasYonYou=synchroDaoU8.getWasYonYou(noPosYonYouCondition);//从用友中查询不启用货位的库存
		}
		
		String noPosWmsCondition=" whCode in "+whCodeNoPos;
		List<WmAllocationstock> wasWms=new ArrayList<WmAllocationstock>();
		if(!CommonUtil.isNullObject(whCodeNoPos)){
			wasWms=synchroDaoU8.getWasWms(noPosWmsCondition);//从wms中查询不启用货位的库存
		}
		
		List<WmAllocationstock> toAddNoPos=new ArrayList<WmAllocationstock>();//对比需要增加的(不启用货位)
		List<WmAllocationstock> toDeleteNoPos=new ArrayList<WmAllocationstock>();//对比需要删除的（不启用货位）
		List<WmAllocationstock> toUpdateNoPos=new ArrayList<WmAllocationstock>();//对比需要修改的（不启用货位）
		
		for(WmAllocationstock wy:wasYonYou){
			boolean toAdd=true;
			for(WmAllocationstock ww:wasWms){
				if(wy.equalsNoPos(ww)){
					toAdd=false;
					
					if( wy.getNumber().compareTo(ww.getNumber()) !=0){
						wy.setGoodsallocationcode(wy.getWhcode());//对于不启用货位的仓库，将货位编码与仓库编码设置一致；
						ww.setNumber(CommonUtil.BigDecimal2BigDecimal(wy.getNumber()));//需要修改的数量
						toUpdateNoPos.add(ww);
//						toAddNoPos.add(wy);
//						toDeleteNoPos.add(ww);
					}
					break;
				}
			}
			
			if(toAdd){
				wy.setGoodsallocationcode(wy.getWhcode());//对于不启用货位的仓库，将货位编码与仓库编码设置一致；
				toAddNoPos.add(wy);
			}
			
		}
		
		for(WmAllocationstock ww:wasWms){
			boolean toDelte=true;
			for(WmAllocationstock wy:wasYonYou){
				if(ww.equalsNoPos(wy)){
					toDelte=false;
					break;
				}
			}
			
			if(toDelte){
				toDeleteNoPos.add(ww);
			}
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////处理货位管理的仓库
		
		List<WmAllocationstock> wasYonYouPos=synchroDaoU8.getWasYonYouPos("");//从用友中查询启用货位的库存
		List<WmAllocationstock> wasWmsPos=new ArrayList<WmAllocationstock>();
		String whCodePos=getWarehousePos();
		if(whCodePos.length()>0){
			String posWmsCondition=" whCode in "+whCodePos;
			wasWmsPos=synchroDaoU8.getWasWms(posWmsCondition);//从wms中查询启用货位的库存
		}
		
		List<WmAllocationstock> toAddPos=new ArrayList<WmAllocationstock>();//对比需要增加的(启用货位)
		List<WmAllocationstock> toDeletePos=new ArrayList<WmAllocationstock>();//对比需要删除的（启用货位）
		List<WmAllocationstock> toUpdtePos=new ArrayList<WmAllocationstock>();//对比需要修改的（启用货位）
		
		for(WmAllocationstock wy:wasYonYouPos){
			boolean toAdd=true;
			for(WmAllocationstock ww:wasWmsPos){
				if(wy.equalsPos(ww)){
					toAdd=false;
					
					if(wy.getNumber().compareTo(ww.getNumber()) !=0){
//						toAddPos.add(wy);
//						toDeletePos.add(ww);
						
						ww.setNumber(CommonUtil.BigDecimal2BigDecimal(wy.getNumber()));
						toUpdtePos.add(ww);
					}
					break;
				}
			}
			
			if(toAdd){
				toAddPos.add(wy);
			}
			
		}
		
		for(WmAllocationstock ww:wasWmsPos){
			boolean toDelte=true;
			for(WmAllocationstock wy:wasYonYouPos){
				if(ww.equalsPos(wy)){
					toDelte=false;
					break;
				}
			}
			
			if(toDelte){
				toDeletePos.add(ww);
			}
		}
		
		//删除
		if(toDeleteNoPos.size()>0){
			synchroDaoU8.deleteWmAllocationstock(toDeleteNoPos);
		}
		if(toDeletePos.size()>0){
			synchroDaoU8.deleteWmAllocationstock(toDeletePos);
		}
		
		//修改
		if(toUpdateNoPos.size()>0){
			synchroDaoU8.updateWmAllocationstock(toUpdateNoPos);
		}
		
		if(toUpdtePos.size()>0){
			synchroDaoU8.updateWmAllocationstock(toUpdtePos);
		}
		
		//增加
		if(toAddNoPos.size()>0){
			synchroDaoU8.insertWmAllocationstock(toAddNoPos,orgGid,sobGid);
		}
		if(toAddPos.size()>0){
			synchroDaoU8.insertWmAllocationstock(toAddPos,orgGid,sobGid);
		}
		
		//修改关联字段
		synchroDaoU8.updWmAllocationstockInfor();
		
	}
	
	

	
	/**
	 * @category 同步委外订单
	 *2016 2016年3月14日下午2:33:35
	 *void
	 *宋银海
	 */
	public void synchroOmOrder(String orgGid,String sobGid){
		
		
		String condition=" and me.MOMaterialsID not in (select mOMaterialsIDForSynchro from OM_Materials) ";
		List<OM_MOMaterials> omToAdd=synchroDaoU8.getOMMaterials3(condition);//获得需要增加的材料单子表
		
		condition=" and om.mOMaterialsIDForSynchro not in (select MOMaterialsID from "+Config.BUSINESSDATABASE+"OM_MOMaterials ) ";
		List<OM_MOMaterials> omToDelete=synchroDaoU8.getOMMaterials1(condition);//获得需要删除的材料单子表
		
		condition=" and isnull(mwms.stateForSynchro,-1)<>isnull(myy.cState,0) ";//获得需要修改的委外订单主表 (主表审核状态发生变化的记录)
		List<OM_MOMain> omToUpd=synchroDaoU8.getMOMain(condition);
		
		//删除任务 billIdentityToDelete单据身份
		Set<String> billIdentityToDelete=new HashSet<String>();
		for(OM_MOMaterials om:omToAdd){
			billIdentityToDelete.add(om.getBillIdentity());
		}
		
		for(OM_MOMaterials om:omToDelete){
			billIdentityToDelete.add(om.getBillIdentity());
		}
		
		for(OM_MOMain m:omToUpd){
			if(!CommonUtil.isNullObject(m.getBillCode())){
				billIdentityToDelete.add(m.getBillCode());
			}
		}
		
		///////////////////////////////////////////////////////////////////////////////////////////////////////处理任务（只有增加 和 删除）
		Iterator<String> iterator=billIdentityToDelete.iterator();
		StringBuffer sbf=new StringBuffer();
		while(iterator.hasNext()){
			sbf.append("'"+iterator.next()+"',");
		};
		String billIdentity=sbf.toString();
		if(billIdentity.length()>0){
			billIdentity=billIdentity.substring(0, billIdentity.length()-1);
			billIdentity="("+billIdentity+")";
		}
		
		List<OM_MOMaterials> omsToAddSecond=null;
		if(!CommonUtil.isNullObject(billIdentity)){
			condition=" and mm.cCode in "+billIdentity;
			omsToAddSecond=synchroDaoU8.getOMMaterials3(condition);//获得发货单对应的到货单子表
		}
		
		Iterator<String> iteratorTask=billIdentityToDelete.iterator();
		List<Task> tasks=new ArrayList<Task>();
		while(iteratorTask.hasNext()){
			Task task=null;
			boolean toCreateTaskMeterialOut=false;//创建材料出库任务
			boolean auditFlag=false;//审核标志
			String billIdent=iteratorTask.next();
			
			String billId=null;
			Timestamp completetime=null;
			for(OM_MOMaterials pcAdd:omsToAddSecond){
				if(billIdent.equalsIgnoreCase(pcAdd.getBillIdentity())){
					
					if(pcAdd.getState().intValue()==1){//判断是否审核
						auditFlag=true;
						toCreateTaskMeterialOut=true;
						
						if(billId==null){
							billId=pcAdd.getBillIdentity();
							completetime=pcAdd.getBillIDate();
						}
						
						break;
					}
				}
				
			}
			
			if(auditFlag&&toCreateTaskMeterialOut){
				task=new Task();
				task.setTaskTypeCodeForSynchro(Constants.TASKTYPE_WWCLCK);
				task.setCompletetime(completetime);
				task.setDistributeTime(new Timestamp(new Date().getTime()));
				task.setState(0);
				task.setBillIdentityForSynchro(billId);
				task.setBillCode(billId);
				task.setBillGidSource(Constants.BILLGIDSOURCE_WWDD);
				task.setSobgid(sobGid);
				task.setOrggid(orgGid);
				tasks.add(task);
			}
		}
		
		//////////////////////////////////////////////////////////////////////////////////////////处理委外订单主表
		synchroDaoU8.deleteOmOrder();//删除委外订单主表
		synchroDaoU8.addOmOrder();//增加委外订单主表
		synchroDaoU8.updOmOrder();//修改委外订单主表
		synchroDaoU8.updOmOrderInfor();//修改委外订单主表相关字段
		
		//////////////////////////////////////////////////////////////////////////////////////////处理委外订单产品表
		synchroDaoU8.deleteOmDetails();//删除委外订单产品表
		synchroDaoU8.addOmDetails();//增加委外订单产品表
		synchroDaoU8.updOmDetails();//修改委外订单产品表
		synchroDaoU8.updOmDetailsInfor();//修改委外订单产品表相关字段
		
		//////////////////////////////////////////////////////////////////////////////////////////处理委外订单材料表
		synchroDaoU8.deleteOMMaterials();//删除委外订单材料表
		synchroDaoU8.addOMMaterials();//增加委外订单材料表
		synchroDaoU8.updOMMaterials();//修改委外订单材料表
		synchroDaoU8.updOMMaterialsInfor();//修改相关字段
		synchroDaoU8.updOmOrderOrgSob(orgGid, sobGid);//指定组织 帐套
		
		//根据billIdentityToDelete 删除任务
		if(!CommonUtil.isNullObject(billIdentity)){
			condition=" billIdentityForSynchro in "+billIdentity+" and taskTypeCodeForSynchro in('0150')";
			synchroDaoU8.deleteTask(condition);
		}
		
		//生成委外领料任务
		if(tasks.size()>0){
			synchroDaoU8.createTask(tasks);
			synchroDaoU8.updateTaskInfor();//跟WM_BillType 做关联
			synchroDaoU8.updateTaskMeterialOutBillGid();//修改委外材料出库对应billGid
		}
		
	}
	
	
}
