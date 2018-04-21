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

import com.emi.cache.service.CacheCtrlService;
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
import com.emi.wms.synchro.dao.SynchroDaoTplus;
import com.emi.wms.util.Constants;
import com.emi.wms.util.DateUtil;

public class SynchroServiceTplus extends EmiPluginService implements Serializable {

	private static final long serialVersionUID = -2162597524027074596L;
	private SynchroDaoTplus synchroDaoTplus;
//	private CacheCtrlService cacheCtrlService;
	

	public void setSynchroDaoTplus(SynchroDaoTplus synchroDaoTplus) {
		this.synchroDaoTplus = synchroDaoTplus;
	}




	/**
	 * @category 同步存货分类
	 *2016 2016年3月2日下午1:27:45
	 *void
	 *宋银海
	 */
	public void synchroInventoryClass(String orgGid,String sobGid){
		synchroDaoTplus.deleteInventoryClass();//删除存货分类
		synchroDaoTplus.addInventoryClass();//增加存货分类
		synchroDaoTplus.updInventoryClass();//修改存货分类
//		synchroDaoTplus.updInventoryClassParentGid();//指定父id
		synchroDaoTplus.updInventoryClassOrgSob(orgGid, sobGid);//指定帐套 组织
	}
	
	
	/**
	 * @category 同步存货档案
	 *2016 2016年3月2日下午2:31:01
	 *void
	 *宋银海
	 */
	public void synchroInventory(String orgGid,String sobGid){
		synchroDaoTplus.deleteInventory();//删除存货档案
		synchroDaoTplus.addInventory();//增加存货档案
		synchroDaoTplus.updInventory();//修改存货档案
//		synchroDaoTplus.updInventoryInfor();//修改相关属性
		synchroDaoTplus.updInventoryOrgSob(orgGid, sobGid);//指定帐套组织
	}
	
	
	/**
	 * @category 同步部门
	 *2016 2016年3月2日下午3:30:15
	 *void
	 *宋银海
	 */
	public void synchroDepartment(String orgGid,String sobGid){
		synchroDaoTplus.deleteDepartment();//删除部门
		synchroDaoTplus.addDepartment();//增加部门
		synchroDaoTplus.uptDepartment();//修改部门
//		synchroDaoTplus.uptDepartmentParentGid();//修改自关联字段
		synchroDaoTplus.updDepartmentOrgSob(orgGid, sobGid);//指定帐套组织
	}
	
	/**
	 * @category 同步客户
	 *2016 2016年3月2日下午4:12:46
	 *void
	 *宋银海
	 */
	public void synchroCustomer(String orgGid,String sobGid){
		synchroDaoTplus.deleteCustomer();//删除客户
		synchroDaoTplus.addCustomer();//增加客户
		synchroDaoTplus.updCustomer();//修改客户
		synchroDaoTplus.updCustomerOrgSob(orgGid, sobGid);//指定帐套组织
	}
	
	
	/**
	 * @category 同步仓库
	 *2016 2016年3月2日下午5:02:21
	 *void
	 *宋银海
	 */
	public void synchroWareHouse(String orgGid,String sobGid){
		synchroDaoTplus.deleteWareHouse();//删除仓库
		synchroDaoTplus.addWareHouse();//增加仓库
		synchroDaoTplus.updWareHouse();//修改仓库
		synchroDaoTplus.updWareHouseOrgSob(orgGid, sobGid);
	}
	
	
	/**
	 * @category 同步货位
	 *2016 2016年3月2日下午5:16:36
	 *void
	 *宋银海
	 */
	public void synchroGoodsAllocation(String orgGid,String sobGid){
		
		//synchroDaoTplus.deleteGoodsAllocation();//删除货位
		synchroDaoTplus.addGoodsAllocation();//增加货位
		synchroDaoTplus.updGoodsAllocation();//修改货位
//		synchroDaoTplus.updGoodsAllocationInfor();//修改相关属性
		synchroDaoTplus.updGoodsAllocationOrgSob(orgGid, sobGid);
	}
	
	
	/**
	 * @category 同步单位
	 *2016 2016年3月3日上午8:47:33
	 *void
	 *宋银海
	 */
	public void synchroUnit(String orgGid,String sobGid){
		synchroDaoTplus.deleteUnit();//删除单位
		synchroDaoTplus.addUnit();//增加单位
		synchroDaoTplus.updUnit();//修改单位
		synchroDaoTplus.updUnitOrgSob(orgGid, sobGid);//指定组织 帐套
	}
	
	
	
	/**
	 * @category 同步供应商
	 *2016 2016年3月3日上午8:53:02
	 *void
	 *宋银海
	 */
	public void synchroProvider(String orgGid,String sobGid){
		synchroDaoTplus.deleteProvider();//删除供应商
		synchroDaoTplus.addProvider();//增加供应商
		synchroDaoTplus.updProvider();//修改供应商
		synchroDaoTplus.updProviderOrgSob(orgGid, sobGid);//指定组织 帐套
	}
	
	
	
	/**
	 * @category 同步用户表
	 *2016 2016年3月3日上午9:05:16
	 *void
	 *宋银海
	 */
	public void synchroUser(String orgGid,String sobGid){
		synchroDaoTplus.addUser();//增加用户
		synchroDaoTplus.updUser();//修改用户
		synchroDaoTplus.updUserOrgSob(orgGid, sobGid);//指定组织 帐套
	}
	
	
	/**
	 * @category 同步人员
	 *2016 2016年3月3日上午9:45:36
	 *void
	 *宋银海
	 */
	public void synchroPerson(String orgGid,String sobGid){
		synchroDaoTplus.deletePerson();//删除人员
		synchroDaoTplus.addPerson();//增加人员
		synchroDaoTplus.updPerson();//修改人员
//		synchroDaoTplus.updPersonInfor();//修改相关属性
		synchroDaoTplus.updPersonOrgSob(orgGid, sobGid);//指定组织 帐套
	}
	
	
	/**
	 * @category 同步收发类别
	 *2016 2016年4月23日下午3:07:40
	 *void
	 *宋银海
	 */
	public void synchroRdStyle(String orgGid,String sobGid){
		synchroDaoTplus.deleteRdStyle();//删除收发类别
		synchroDaoTplus.addRdStyle();//增加收发类别
		synchroDaoTplus.updRdStyle();//修改收发类别
		
		String condition=" depth=1 and code=1 ";
		Map rdStyle=synchroDaoTplus.getRdStyle(condition);
		synchroDaoTplus.updRdStyleS(rdStyle.get("id").toString());//修改收发类别   收标志
		
		condition=" depth=1 and code=2 ";
		rdStyle=synchroDaoTplus.getRdStyle(condition);
		synchroDaoTplus.updRdStyleF(rdStyle.get("id").toString());//修改收发类别   发标志
		
		synchroDaoTplus.updRdStyleOrgSob(orgGid, sobGid);//指定组织 帐套
	}
	
	/**
	 * @category 同步用户自定义项
	 *2016 2016年4月23日下午3:07:40
	 *void
	 *宋银海
	 */
	public void synchroUserDefine(String orgGid,String sobGid){
		synchroDaoTplus.deleteUserDefine();//删除用户自定义项
		synchroDaoTplus.addUserDefine();//增加用户自定义项
		synchroDaoTplus.updUserDefine();//修改用户自定义项
		synchroDaoTplus.updUserDefineOrgSob(orgGid, sobGid);//指定组织 帐套
	}
	
	/**
	 * @category 同步工作中心
	 *2016 2016年3月3日上午10:51:13
	 *void
	 *宋银海
	 */
	public void synchroWorkCenter(String orgGid,String sobGid){
		
		synchroDaoTplus.deleteWorkCenter();//删除工作中心
		synchroDaoTplus.addWorkCenter();//增加工作中心
		synchroDaoTplus.updWorkCenter();//修改工作中心
		synchroDaoTplus.updWorkCenterInfor();//修改相关属性
		synchroDaoTplus.updWorkCenterOrgSob(orgGid, sobGid);//指定组织 帐套
	}
	
	
	/**
	 * @category 同步标准工序
	 *2016 2016年3月3日下午3:11:33
	 *void
	 *宋银海
	 */
	public void synchroStandardprocess(String orgGid,String sobGid){
		synchroDaoTplus.deleteStandardprocess();//删除标准工序
		synchroDaoTplus.addStandardprocess();//增加标准工序
		synchroDaoTplus.updStandardprocess();//修改标准工序
		synchroDaoTplus.updStandardprocessOrgSob(orgGid, sobGid);//指定组织 帐套
	}
	
	
	/**
	 * @category 同步产品结构
	 *2016 2016年3月14日上午9:14:25
	 *void
	 *宋银海
	 */
	public void synchroProductStructure(){
		synchroDaoTplus.deleteProductStructure();//删除产品结构主表
		synchroDaoTplus.addProductStructure();//增加产品结构主表
		synchroDaoTplus.updProductStructure();//修改产品结构主表
		synchroDaoTplus.updProductStructureInfor();//修改关联字段
		
		synchroDaoTplus.deleteProductStructurec();//删除产品结构子表
		synchroDaoTplus.addProductStructurec();//增加产品结构子表
		synchroDaoTplus.updProductStructurec();//修改产品结构子表
		synchroDaoTplus.updProductStructurecInfor();//修改关联字段
	}
	
	
	/**
	 * @category 同步工艺路线
	 *2016 2016年3月15日上午9:07:35
	 *void
	 *宋银海
	 */
	public void synchroProcessRoute(){
		synchroDaoTplus.deleteProcessRoute();//删除工艺路线主表
		synchroDaoTplus.addProcessRoute();//增加工艺路线主表
		synchroDaoTplus.updProcessRoute();//修改工艺路线主表
		synchroDaoTplus.updProcessRouteInfor();//修改关联字段
				
		synchroDaoTplus.deleteProcessRouteC();//删除工艺路线子表
		synchroDaoTplus.addProcessRouteC();//增加工艺路线子表
		synchroDaoTplus.updProcessRouteC();//修改工艺路线子表
		synchroDaoTplus.updProcessRouteCInfor();//修改关联字段
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
//		List<Procurearrival> plTodelete=synchroDaoTplus.getProcurearrival(condition);
//		condition=" and cCode not in (select billCode from WM_ProcureArrival) ";获得需要增加的到货单主表
//		List<Procurearrival> plToAdd=synchroDaoTplus.getPl(condition);
		
		String condition=" and ps.autoid not in (select autoidForSynchro from WM_ProcureArrival_C) ";
		List<ProcurearrivalC> plsToAdd=synchroDaoTplus.getProcurearrivalC3(condition);//获得需要增加的到货单子表
		
		condition=" and pc.autoidForSynchro not in (select autoid from "+Config.BUSINESSDATABASE+"PU_ArrivalVouchs ) ";
		List<ProcurearrivalC> plsToDelete=synchroDaoTplus.getProcurearrivalC1(condition);//获得需要删除的到货单子表
		
		condition=" and isnull(pc.needCheck,'')<>isnull(paus8.bGsp,'') or isnull(pc.define22,'')<>isnull(paus8.cdefine22,'') ";
		List<ProcurearrivalC> plsToUpd=synchroDaoTplus.getProcurearrivalC2(condition);//获得需要修改的到货单子表
		
		condition=" and isnull(p.define1,'')<>isnull(pv.cDefine1,'') or isnull(p.stateForSynchro,-1)<>isnull(pv.iverifystateex,0) ";//获得需要修改的到货单主表 (主表define1发生变化的记录 ，审核状态发生变化的记录)
		List<Procurearrival> plToAdd=synchroDaoTplus.getPl(condition);
		
		
		//删除任务 billIdentityToDelete单据身份
		Set<String> billIdentityToDelete=new HashSet<String>();
		for(ProcurearrivalC pc:plsToAdd){
			billIdentityToDelete.add(pc.getBillIdentity());
		}
		
		for(ProcurearrivalC pc:plsToUpd){
			billIdentityToDelete.add(pc.getBillIdentity());
		}
		
		for(ProcurearrivalC pc:plsToDelete){
			billIdentityToDelete.add(pc.getBillIdentity());
		}
		
		for(Procurearrival p:plToAdd){
			billIdentityToDelete.add(p.getBillCode());
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
			plsToAddSecond=synchroDaoTplus.getProcurearrivalC3(condition);//获得发货单对应的到货单子表
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
					
					//获得参数判断是否是采用表头自定义项质检  还是标准的质检功能
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
				if(cbusTypeName.equalsIgnoreCase("普通采购")){
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
				if(cbusTypeName.equalsIgnoreCase("普通采购")){
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
		synchroDaoTplus.deleteProArrival();//删除到货单主表
		synchroDaoTplus.addProArrival();//增加到货单主表
		synchroDaoTplus.updProArrival();//修改到货单主表
		synchroDaoTplus.updProArrivalInfor();//修改相关字段
		
		//////////////////////////////////////////////////////////////////////////////////////////处理订单子表
		synchroDaoTplus.deleteProArrivalC();//删除到货单子表
		synchroDaoTplus.addProArrivalC();//增加到货单子单
		synchroDaoTplus.updProArrivalC();//修改到货单子单
		synchroDaoTplus.updProArrivalCInfor();//修改关联字段
		synchroDaoTplus.updProArrivalOrgSob(orgGid, sobGid);
		
		//根据billIdentityToDelete 删除任务
		if(!CommonUtil.isNullObject(billIdentity)){
			condition=" billIdentityForSynchro in "+billIdentity+" and taskTypeCodeForSynchro in('0008','0009','0010','0170')";
			synchroDaoTplus.deleteTask(condition);
		}
		
		//生成入库任务 或者 质检任务
		if(tasks.size()>0){
			synchroDaoTplus.createTask(tasks);
			synchroDaoTplus.updateTaskInfor();//跟WM_BillType 做关联
			synchroDaoTplus.updateTaskProArrivalBillGid();//修改采购出库对应billGid
		}
		
	}
	
	
	/**
	 * @category 同步销售发货单
	 *2016 2016年3月14日上午11:21:39
	 *void
	 *宋银海
	 */
	public void synchroSaleSend(String orgGid,String sobGid) throws Exception{
		
		String condition=" and dis.id not in (select gid from WM_SaleSend_C) ";
		List<SaleSendC> plsToAdd=synchroDaoTplus.getSaleSendC3(condition);//获得需要增加的发货单子表
		
		condition=" and ssc.gid not in (select id from "+Config.BUSINESSDATABASE+"SA_SaleDelivery_b ) ";
		List<SaleSendC> plsToDelete=synchroDaoTplus.getSaleSendC1(condition);//获得需要删除的发货单子表
		
		condition=" and isnull(ss.stateForSynchro,'')<>isnull(dl.auditor,'') ";//获得需要修改的发货单主表 (主表define1发生变化的记录 ，审核状态发生变化的记录)
		List<SaleSend> ssToAdd=synchroDaoTplus.getSs(condition);
		
		
		//删除任务 billIdentityToDelete单据身份
		Set<String> billIdentityToDelete=new HashSet<String>();
		for(SaleSendC ssc:plsToAdd){
			billIdentityToDelete.add(ssc.getBillIdentity());
		}
		
		for(SaleSendC ssc:plsToDelete){
			billIdentityToDelete.add(ssc.getBillIdentity());
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
			condition=" and d.code in "+billIdentity;
			plsToAddSecond=synchroDaoTplus.getSaleSendC3(condition);//获得删除的发货单对应的到货单子表
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
		synchroDaoTplus.deleteSaleSend();//删除发货单主表
		synchroDaoTplus.addSaleSend();//增加发货单主表
		synchroDaoTplus.updSaleSend();//修改发货单主表
//		synchroDaoTplus.updSaleSendInfor();//修改相关字段
		
		//////////////////////////////////////////////////////////////////////////////////////////处理订单子表
		synchroDaoTplus.deleteSaleSendC();//删除发货单子表
		synchroDaoTplus.addSaleSendC();//增加发货单子表
		synchroDaoTplus.updSaleSendC();//修改发货单子表
		synchroDaoTplus.updSaleSendCInfor();//修改相关字段
		synchroDaoTplus.updSaleSendOrgSob(orgGid, sobGid);
		
		//根据billIdentityToDelete 删除任务
		if(!CommonUtil.isNullObject(billIdentity)){
			condition=" billIdentityForSynchro in "+billIdentity +" and taskTypeCodeForSynchro in('0038','0039','0040')";
			synchroDaoTplus.deleteTask(condition);
		}
		
		//生成出库任务
		if(tasks.size()>0){
			synchroDaoTplus.createTask(tasks);
			synchroDaoTplus.updateTaskInfor();//跟WM_BillType 做关联
			synchroDaoTplus.updateTaskSaleSendBillGid();//修改销售出库对应billGid
		}
		
		System.out.println("同步发货单结束");
	}
	
	
	
	/**
	 * @category 同步生产订单
	 *2016 2016年3月14日下午2:33:35
	 *void
	 *宋银海
	 */
	public void synchroProduceOrder(String orgGid,String sobGid){
		//////////////////////////////////////////////////////////////////////////////////////////处理订单主表
		synchroDaoTplus.deleteProduceOrder();//删除生产订单主表
		synchroDaoTplus.addProduceOrder();//增加生产订单主表
		synchroDaoTplus.updProduceOrder();//修改生产订单主表
		
		//////////////////////////////////////////////////////////////////////////////////////////处理订单产品表
		synchroDaoTplus.deleteProduceOrderC();//删除生产订单产品表
		synchroDaoTplus.addProduceOrderC();//增加生产订单产品表
		synchroDaoTplus.updProduceOrderC();//修改生产订单产品表
		synchroDaoTplus.updProduceOrderCInfor();//修改生产订单产品表相关字段
		
		//////////////////////////////////////////////////////////////////////////////////////////处理订单材料表
		synchroDaoTplus.deleteProduceOrderC2();//删除生产订单材料表
		synchroDaoTplus.addProduceOrderC2();//增加生产订单材料表
		synchroDaoTplus.updProduceOrderC2();//修改生产订单材料表
		synchroDaoTplus.updProduceOrderC2Infor();//修改相关字段
		
		synchroDaoTplus.updProduceOrderOrgSob(orgGid, sobGid);//指定组织 帐套
		
	}
	
	//货位管理的仓库
	public String getWarehousePos(){
		String condition=" whPos=1 ";
		List<AaWarehouse> aaWarehouse=synchroDaoTplus.getAaWarehouse(condition);//启用货位的仓库
		
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
		List<AaWarehouse> aaWarehouse=synchroDaoTplus.getAaWarehouse(condition);//不启用货位的仓库
		
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
		
//		String whCodeNoPos=getWarehouseNoPos();
		String noPosYonYouCondition=" and cs.idwarehouse in (select id from "+Config.BUSINESSDATABASE+"AA_Warehouse where hasPosition =0 )";
		List<WmAllocationstock> wasYonYou=synchroDaoTplus.getWasYonYou(noPosYonYouCondition);//从用友中查询不启用货位的库存
		String noPosWmsCondition=" whCode in (select whcode from AA_Warehouse where whPos=0) ";
		List<WmAllocationstock> wasWms=synchroDaoTplus.getWasWms(noPosWmsCondition);//从wms中查询不启用货位的库存
		
		List<WmAllocationstock> toAddNoPos=new ArrayList<WmAllocationstock>();//对比需要增加的(不启用货位)
		List<WmAllocationstock> toDeleteNoPos=new ArrayList<WmAllocationstock>();//对比需要删除的（不启用货位）
		List<WmAllocationstock> toUptNoPos=new ArrayList<WmAllocationstock>();//对比需要修改的（不启用货位）
		
		for(WmAllocationstock wy:wasYonYou){
			
			boolean toAdd=true;
			for(WmAllocationstock ww:wasWms){
				if(wy.equalsNoPos(ww)){
					toAdd=false;
					
					if( wy.getNumber().compareTo(ww.getNumber()) !=0){
						wy.setGoodsallocationcode(wy.getWhcode());//对于不启用货位的仓库，将货位编码与仓库编码设置一致；
						
						ww.setNumber(CommonUtil.BigDecimal2BigDecimal(wy.getNumber()));
						toUptNoPos.add(ww);
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
		
		List<WmAllocationstock> wasYonYouPos=synchroDaoTplus.getWasYonYouPos("");//从用友中查询启用货位的库存
		List<WmAllocationstock> wasWmsPos=new ArrayList<WmAllocationstock>();
		String whCodePos=getWarehousePos();
		if(whCodePos.length()>0){
			String posWmsCondition=" whCode in "+whCodePos;
			wasWmsPos=synchroDaoTplus.getWasWms(posWmsCondition);//从wms中查询启用货位的库存
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
			synchroDaoTplus.deleteWmAllocationstock(toDeleteNoPos);
		}
		if(toDeletePos.size()>0){
			synchroDaoTplus.deleteWmAllocationstock(toDeletePos);
		}
		
		//修改
		if(toUptNoPos.size()>0){
			synchroDaoTplus.updateWmAllocationstock(toUptNoPos);
		}
		
		if(toUpdtePos.size()>0){
			synchroDaoTplus.updateWmAllocationstock(toUpdtePos);
		}
		
		//增加
		if(toAddNoPos.size()>0){
			synchroDaoTplus.insertWmAllocationstock(toAddNoPos,orgGid,sobGid);
		}
		
		if(toAddPos.size()>0){
			synchroDaoTplus.insertWmAllocationstock(toAddPos,orgGid,sobGid);
		}
		
		//修改关联字段
		synchroDaoTplus.updWmAllocationstockInfor();
		
	}
	
	

	
	/**
	 * @category 同步委外订单
	 *2016 2016年3月14日下午2:33:35
	 *void
	 *宋银海
	 */
	public void synchroOmOrder(String orgGid,String sobGid){
		
		
		String condition=" and me.MOMaterialsID not in (select mOMaterialsIDForSynchro from OM_Materials) ";
		List<OM_MOMaterials> omToAdd=synchroDaoTplus.getOMMaterials3(condition);//获得需要增加的材料单子表
		
		condition=" and om.mOMaterialsIDForSynchro not in (select MOMaterialsID from "+Config.BUSINESSDATABASE+"OM_MOMaterials ) ";
		List<OM_MOMaterials> omToDelete=synchroDaoTplus.getOMMaterials1(condition);//获得需要删除的材料单子表
		
		condition=" and isnull(mwms.stateForSynchro,-1)<>isnull(myy.cState,0) ";//获得需要修改的委外订单主表 (主表审核状态发生变化的记录)
		List<OM_MOMain> omToUpd=synchroDaoTplus.getMOMain(condition);
		
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
			omsToAddSecond=synchroDaoTplus.getOMMaterials3(condition);//获得发货单对应的到货单子表
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
		synchroDaoTplus.deleteOmOrder();//删除委外订单主表
		synchroDaoTplus.addOmOrder();//增加委外订单主表
		synchroDaoTplus.updOmOrder();//修改委外订单主表
		synchroDaoTplus.updOmOrderInfor();//修改委外订单主表相关字段
		
		//////////////////////////////////////////////////////////////////////////////////////////处理委外订单产品表
		synchroDaoTplus.deleteOmDetails();//删除委外订单产品表
		synchroDaoTplus.addOmDetails();//增加委外订单产品表
		synchroDaoTplus.updOmDetails();//修改委外订单产品表
		synchroDaoTplus.updOmDetailsInfor();//修改委外订单产品表相关字段
		
		//////////////////////////////////////////////////////////////////////////////////////////处理委外订单材料表
		synchroDaoTplus.deleteOMMaterials();//删除委外订单材料表
		synchroDaoTplus.addOMMaterials();//增加委外订单材料表
		synchroDaoTplus.updOMMaterials();//修改委外订单材料表
		synchroDaoTplus.updOMMaterialsInfor();//修改相关字段
		synchroDaoTplus.updOmOrderOrgSob(orgGid, sobGid);//指定组织 帐套
		
		//根据billIdentityToDelete 删除任务
		if(!CommonUtil.isNullObject(billIdentity)){
			condition=" billIdentityForSynchro in "+billIdentity+" and taskTypeCodeForSynchro in('0150')";
			synchroDaoTplus.deleteTask(condition);
		}
		
		//生成委外领料任务
		if(tasks.size()>0){
			synchroDaoTplus.createTask(tasks);
			synchroDaoTplus.updateTaskInfor();//跟WM_BillType 做关联
			synchroDaoTplus.updateTaskMeterialOutBillGid();//修改委外材料出库对应billGid
		}
		
	}
	
	
}
