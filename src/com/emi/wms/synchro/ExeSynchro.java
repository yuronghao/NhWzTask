package com.emi.wms.synchro;

import java.util.Map;
import java.util.TimerTask;

import com.emi.common.util.CommonUtil;
import com.emi.sys.init.Config;
import com.emi.wms.synchro.service.*;

public class ExeSynchro extends TimerTask {
	
	private SynchroServiceU890 synchroServiceU8;
	private SynchroServiceTplus synchroServiceTplus;
	private SynchroServiceO2O synchroServiceO2O;
	private SynchroServiceT6 synchroServiceT6;

	private SynchroServiceU8101 synchroServiceU8101;




	public void setSynchroServiceU8101(SynchroServiceU8101 synchroServiceU8101) {
		this.synchroServiceU8101 = synchroServiceU8101;
	}

	public void setSynchroServiceU8(SynchroServiceU890 synchroServiceU8) {
		this.synchroServiceU8 = synchroServiceU8;
	}

	public void setSynchroServiceTplus(SynchroServiceTplus synchroServiceTplus) {
		this.synchroServiceTplus = synchroServiceTplus;
	}

	public void setSynchroServiceO2O(SynchroServiceO2O synchroServiceO2O) {
		this.synchroServiceO2O = synchroServiceO2O;
	}
	
	public void setSynchroServiceT6(SynchroServiceT6 synchroServiceT6) {
		this.synchroServiceT6 = synchroServiceT6;
	}

	@Override
	public void run() {
		
		Map orgMap=synchroServiceU8.getOrgGid();
		String orgGid="";//组织gid
		if(!CommonUtil.isNullObject(orgMap)){
			orgGid=orgMap.get("gid").toString();
		}
		
		Map sobMap=synchroServiceU8.getSobGid();
		String sobGid="";//gid
		if(!CommonUtil.isNullObject(sobMap)){
			sobGid=sobMap.get("gid").toString();
		}
		
		if(Config.synchroType.equalsIgnoreCase("t+")){
			
			try{
				synchroServiceTplus.synchroInventoryClass(orgGid,sobGid);//同步存货分类
				synchroServiceTplus.synchroUnit(orgGid,sobGid);//同步计量单位
				synchroServiceTplus.synchroInventory(orgGid,sobGid);//同步存货档案
				synchroServiceTplus.synchroDepartment(orgGid,sobGid);//同步部门
				synchroServiceTplus.synchroCustomer(orgGid,sobGid);//客户档案
				synchroServiceTplus.synchroWareHouse(orgGid,sobGid);//同步仓库
				synchroServiceTplus.synchroGoodsAllocation(orgGid,sobGid);//同步货位
//				synchroServiceTplus.synchroProvider(orgGid,sobGid);//同步供应商
				synchroServiceTplus.synchroUser(orgGid,sobGid);//同步用户
				synchroServiceTplus.synchroPerson(orgGid,sobGid);//同步人员
				synchroServiceTplus.synchroRdStyle(orgGid, sobGid);//同步出入库类别
				synchroServiceTplus.synchroAllocationStock(orgGid, sobGid);//同步现存量
				
				try{
					synchroServiceTplus.synchroSaleSend(orgGid,sobGid);//同步发货单
				}catch(Exception e){
					e.printStackTrace();
				}
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}else if(Config.synchroType.equalsIgnoreCase("t6")){
			try{
				
				synchroServiceT6.synchroInventoryClass(orgGid, sobGid);//同步存货分类
				synchroServiceT6.synchroUnit(orgGid, sobGid);//同步计量单位
				synchroServiceT6.synchroInventory(orgGid, sobGid);//同步存货档案
				synchroServiceT6.synchroDepartment(orgGid, sobGid);//同步部门
				synchroServiceT6.synchroWareHouse(orgGid, sobGid);//同步仓库
				synchroServiceT6.synchroGoodsAllocation(orgGid, sobGid);//同步货位
				synchroServiceT6.synchroUser(orgGid, sobGid);//同步用户
				synchroServiceT6.synchroPerson(orgGid, sobGid);//同步人员
				synchroServiceT6.synchroProduceOrder(orgGid,sobGid);//同步生产订单
				
			}catch(Exception e){
				e.printStackTrace();
			}
			
		}else if(Config.synchroType.equalsIgnoreCase("u890")){
			
			try {
				synchroServiceU8.noTransynchroInventoryClass(orgGid,sobGid);//同步存货分类
				synchroServiceU8.noTransynchroUnit(orgGid,sobGid);//同步计量单位
				synchroServiceU8.noTransynchroInventory(orgGid,sobGid);//同步存货档案
				synchroServiceU8.noTransynchroDepartment(orgGid,sobGid);//同步部门
				synchroServiceU8.noTransynchroCustomer(orgGid,sobGid);//客户档案
				synchroServiceU8.noTransynchroWareHouse(orgGid,sobGid);//同步仓库
				synchroServiceU8.noTransynchroGoodsAllocation(orgGid,sobGid);//同步货位
				synchroServiceU8.noTransynchroProvider(orgGid,sobGid);//同步供应商
				synchroServiceU8.noTransynchroUser(orgGid,sobGid);//同步用户
				synchroServiceU8.noTransynchroPerson(orgGid,sobGid);//同步人员
				synchroServiceU8.noTransynchroRdStyle(orgGid, sobGid);//同步出入库类别
				synchroServiceU8.noTransynchroUserDefine(orgGid, sobGid);//同步自定义项
				
/*				不需要同步
 * 				synchroServiceU8.synchroWorkCenter(orgGid,sobGid);//同步工作中心
				synchroServiceU8.synchroStandardprocess(orgGid,sobGid);//同步标准工序
				synchroServiceU8.synchroProductStructure();//同步bom
				synchroServiceU8.synchroProcessRoute();//同步工艺路线
*/			
				
				try{
					synchroServiceU8.synchroProArrival(orgGid,sobGid);//同步到货单（包括委外）
				}catch(Exception e){
					e.printStackTrace();
				}
				
				try{
					synchroServiceU8.synchroSaleSend(orgGid,sobGid);//同步发货单
				}catch(Exception e){
					e.printStackTrace();
				}
				
				try{
					synchroServiceU8.synchroProduceOrder(orgGid,sobGid);//同步生产订单
				}catch(Exception e){
					e.printStackTrace();
				}
				
				try{
					synchroServiceU8.synchroOmOrder(orgGid, sobGid);//同步委外订单
				}catch(Exception e){
					e.printStackTrace();
				}
				
				System.out.println("同步业务结束，不包含库存");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(Config.synchroType.equalsIgnoreCase("o2o")){
//			try{
//				synchroServiceO2O.synchroInventoryClass(orgGid, sobGid);//同步存货分类
//				synchroServiceO2O.synchroInventory(orgGid, sobGid);//同步存货档案
//				synchroServiceO2O.synchroUnit(orgGid, sobGid);//同步单位
//				synchroServiceO2O.synchroCustomer(orgGid, sobGid);//同步客户
//				synchroServiceO2O.synchroSaleorder(orgGid, sobGid);//同步订单主表
//				synchroServiceO2O.synchroSaleorderC(orgGid, sobGid);//同步订单子表
//			}catch(Exception e){
//				e.printStackTrace();
//			}

		}else if(Config.synchroType.equalsIgnoreCase("u8101")){
			try{
				synchroServiceU8101.synchroUnit(orgGid, sobGid);//同步单位
				synchroServiceU8101.synchroUser(orgGid, sobGid);//同步用户
				synchroServiceU8101.synchroInventoryClass(orgGid, sobGid);//同步存货分类
				synchroServiceU8101.synchroInventory(orgGid, sobGid);//同步存货档案
				synchroServiceU8101.synchroWareHouse(orgGid, sobGid);//同步仓库
				synchroServiceU8101.synchroRdStyle(orgGid, sobGid); //同步收发类别
				synchroServiceU8101.synchroPurchaseType(orgGid, sobGid); //同步采购类型
				synchroServiceU8101.synchroGoodsAllocation(orgGid, sobGid);//同步货位
				synchroServiceU8101.synchroProviderClass(orgGid, sobGid);//同步供应商分类档案
				synchroServiceU8101.synchroProvider(orgGid, sobGid);//同步供应商档案
				synchroServiceU8101.synchroDepartment(orgGid, sobGid);//同步部门档案
				synchroServiceU8101.synchroPerson(orgGid, sobGid);//同步人员档案

			}catch(Exception e){
				e.printStackTrace();
			}


			try{
				synchroServiceU8101.synchroProOrder(orgGid,sobGid);//同步采购订单
			}catch (Exception e){
				e.printStackTrace();
			}

		}
		
	}


	
}
