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

import com.emi.common.service.EmiPluginService;
import com.emi.common.util.CommonUtil;
import com.emi.sys.init.Config;
import com.emi.wms.bean.AaGoods;
import com.emi.wms.bean.AaProviderCustomer;
import com.emi.wms.bean.AaWarehouse;
import com.emi.wms.bean.BillType;
import com.emi.wms.bean.Classify;
import com.emi.wms.bean.OM_MOMain;
import com.emi.wms.bean.OM_MOMaterials;
import com.emi.wms.bean.Procurearrival;
import com.emi.wms.bean.ProcurearrivalC;
import com.emi.wms.bean.SaleSend;
import com.emi.wms.bean.SaleSendC;
import com.emi.wms.bean.Task;
import com.emi.wms.bean.Unit;
import com.emi.wms.bean.WmAllocationstock;
import com.emi.wms.bean.WmSaleorder;
import com.emi.wms.bean.WmSaleorderC;
import com.emi.wms.beanO2O.Customer;
import com.emi.wms.beanO2O.Goods;
import com.emi.wms.beanO2O.Goodsort;
import com.emi.wms.beanO2O.Saleorder;
import com.emi.wms.beanO2O.Saleorder_c;
import com.emi.wms.beanO2O.UnitO2O;
import com.emi.wms.synchro.dao.SynchroDaoO2O;
import com.emi.wms.synchro.dao.SynchroDaoO2Oexternal;
import com.emi.wms.synchro.dao.SynchroDaoU890;
import com.emi.wms.util.Constants;
import com.emi.wms.util.DateUtil;

public class SynchroServiceO2O extends EmiPluginService implements Serializable {
	

	private static final long serialVersionUID = -2162597524027074596L;
	private SynchroDaoO2O synchroDaoO2O;
	private SynchroDaoO2Oexternal synchroDaoO2Oexternal;//对应外部数据源
	
	public void setSynchroDaoO2O(SynchroDaoO2O synchroDaoO2O) {
		this.synchroDaoO2O = synchroDaoO2O;
	}
	
	public void setSynchroDaoO2Oexternal(SynchroDaoO2Oexternal synchroDaoO2Oexternal) {
		this.synchroDaoO2Oexternal = synchroDaoO2Oexternal;
	}



	//同步存货分类
	public void synchroInventoryClass(String orgGid,String sobGid){
		String condition=" ifnull(dealstats,0)<>1 ";
		List<Goodsort> goodsort= synchroDaoO2Oexternal.getInventoryClass(condition);//查询所有未处理的数据
		
		//循环处理erp中的数据
		for(Goodsort gs:goodsort){
			
			Classify classify=new Classify();
			classify.setGid(gs.getGid());
			classify.setParentid(gs.getParentGid());
			classify.setClassificationname(gs.getName());
			classify.setStylegid("03");
			classify.setOrgid(orgGid);
			classify.setSobid(sobGid);
			
			if(gs.getStats().intValue()==0){//新增
				synchroDaoO2O.addGoodsSort(classify);
			}else if(gs.getStats().intValue()==1){//修改
				synchroDaoO2O.updGoodsSort(classify);
			}else if(gs.getStats().intValue()==2){//删除
				synchroDaoO2O.deleteGoodsSort(classify);
			}
			
		}
		
		//修改状态置为3
		synchroDaoO2Oexternal.updateInventoryClassStats(goodsort);
	}
	
	//同步存货档案
	public void synchroInventory(String orgGid,String sobGid){
		String condition=" ifnull(dealstats,0)<>1 ";
		List<Goods> goods=synchroDaoO2Oexternal.getGoods(condition);
		
		//循环处理erp中的数据
		for(Goods gs:goods){
			
			AaGoods aaGoods=new AaGoods();
			aaGoods.setGid(gs.getGid());
			aaGoods.setGoodscode(gs.getGoodsCode());
			aaGoods.setGoodsname(gs.getGoodsName());
			aaGoods.setGoodssortuid(gs.getGoodsSortGid());
			aaGoods.setGoodsstandard(gs.getGoodsStandard());
			aaGoods.setGoodsunit(gs.getMainUnitGid());
			aaGoods.setCasscomunitcode(gs.getAssistUnitGid());
			aaGoods.setCstcomunitcode(gs.getAssistUnitGid());
			aaGoods.setOrggid(orgGid);
			aaGoods.setSobgid(sobGid);
			aaGoods.setPrice(gs.getPrice());
			
			if(gs.getStats().intValue()==0){//新增
				synchroDaoO2O.addGoods(aaGoods);
			}else if(gs.getStats().intValue()==1){//修改
				synchroDaoO2O.updGoods(aaGoods);
			}else if(gs.getStats().intValue()==2){//删除
				synchroDaoO2O.updGoods(aaGoods);//不能真删
			}
		}
		
		//修改状态置为3
		synchroDaoO2Oexternal.updateGoodsStats(goods);
		
	}
	
	//同步计量单位
	public void synchroUnit(String orgGid,String sobGid){
		
		String condition=" ifnull(dealstats,0)<>1 ";
		List<UnitO2O> unitO2O=synchroDaoO2Oexternal.getUnit(condition);
		
		//循环处理erp中的数据
		for(UnitO2O uo:unitO2O){
			
			Unit unit=new Unit();
			unit.setGid(uo.getGid().equalsIgnoreCase("0")?null:uo.getGid());
			unit.setUnitcode(CommonUtil.isNullObject(uo.getUnitCode())?null:uo.getUnitCode());
			unit.setUnitname(uo.getUnitName());
			unit.setOrgid(orgGid);
			unit.setSobid(sobGid);
			
			if(uo.getStats().intValue()==0){//新增
				synchroDaoO2O.addUnit(unit);
			}else if(uo.getStats().intValue()==1){//修改
				synchroDaoO2O.updUnit(unit);
			}else if(uo.getStats().intValue()==2){//删除
				synchroDaoO2O.deleteUnit(unit);
			}
		}
		
		//修改状态置为3
		synchroDaoO2Oexternal.updateUnitStats(unitO2O);
	}
	
	//客户档案
	public void synchroCustomer(String orgGid,String sobGid){
		
		String condition=" ifnull(dealstats,0)<>1 ";
		List<Customer> customer=synchroDaoO2Oexternal.getCustomer(condition);
		
		//循环处理erp中的数据
		for(Customer c:customer){
			
			AaProviderCustomer pc=new AaProviderCustomer();
			pc.setGid(c.getGid());
			pc.setPccode(CommonUtil.isNullObject(c.getCode())?null:c.getCode());
			pc.setPcname(c.getName());
			pc.setOrgid(orgGid);
			pc.setSobid(sobGid);
			
			if(c.getStats().intValue()==0){//新增
				synchroDaoO2O.addCustomer(pc);
			}else if(c.getStats().intValue()==1){//修改
				synchroDaoO2O.updCustomer(pc);
			}else if(c.getStats().intValue()==2){//删除
				synchroDaoO2O.deleteCustomer(pc);
			}
		}
		
		//修改状态置为3 
		synchroDaoO2Oexternal.updateCustomerStats(customer);
		
	}
	
	//销售订单主表
	public void synchroSaleorder(String orgGid,String sobGid){
		
		String condition=" ifnull(dealstats,0)<>1 ";
		List<Saleorder> saleorder=synchroDaoO2Oexternal.getOrder(condition);
		
		//循环处理erp中的数据
		for(Saleorder c:saleorder){
			
			WmSaleorder ws=new WmSaleorder();
			ws.setGid(c.getGid());
			ws.setBillcode(c.getBillCode());
			ws.setBilldate(c.getBillDate());
			ws.setCustomeruid(c.getCustomerGid());
			ws.setOrggid(orgGid);
			ws.setSobgid(sobGid);
			
			if(c.getStats().intValue()==0){//新增
				synchroDaoO2O.addOrder(ws);
			}else if(c.getStats().intValue()==1){//修改
				synchroDaoO2O.updOrder(ws);
			}else if(c.getStats().intValue()==2){//删除
				synchroDaoO2O.deleteOrder(ws);
			}
		}
		
		//修改状态置为3 
		synchroDaoO2Oexternal.updateSaleorderStats(saleorder);
	}
	
	
	//销售订单子表
	public void synchroSaleorderC(String orgGid,String sobGid){
		
		String condition=" ifnull(dealstats,0)<>1 ";
		List<Saleorder_c> saleorderC=synchroDaoO2Oexternal.getOrderC(condition);
		
		//循环处理erp中的数据
		for(Saleorder_c c:saleorderC){
			
			WmSaleorderC wc=new WmSaleorderC();
			wc.setGid(c.getGid());
			wc.setSaleOrderUid(c.getParentGid());
			wc.setGoodsUid(c.getGoodsGid());
			wc.setLocaltaxprice(c.getLocalTaxPrice());
			wc.setLocaltaxmoney(c.getLocalTaxMoney());
			wc.setNumber(c.getNumber());
			wc.setAssistNumber(c.getAssistNumber());
			
			if(c.getStats().intValue()==0){//新增
				synchroDaoO2O.addOrderC(wc);
			}else if(c.getStats().intValue()==1){//修改
				synchroDaoO2O.updOrderC(wc);
			}else if(c.getStats().intValue()==2){//删除
				synchroDaoO2O.deleteOrderC(wc);
			}
		}
		
		//修改状态置为3 
		synchroDaoO2Oexternal.updateSaleorderCStats(saleorderC);
	}
	
}
