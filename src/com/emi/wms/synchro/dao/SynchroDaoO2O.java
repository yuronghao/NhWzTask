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
import com.emi.wms.util.Constants;

public class SynchroDaoO2O extends BaseDao {
	
	
	//添加分类
	public boolean addGoodsSort(Classify classify){
		return this.emiInsert(classify);
	}

	//修改分类
	public boolean updGoodsSort(Classify classify){
		return this.emiUpdate(classify);
	}
	
	//删除分类
	public int deleteGoodsSort(Classify classify){
		String sql="delete from classify where gid='"+classify.getGid()+"'";
		return this.update(sql);
	}
	
	
	//添加物品
	public boolean addGoods(AaGoods goods){
		return this.emiInsert(goods);
	}

	//修改物品
	public boolean updGoods(AaGoods goods){
		return this.emiUpdate(goods);
	}
	
	//删除物品
	public int deleteGoods(AaGoods goods){
		String sql="delete from aa_goods where gid='"+goods.getGid()+"'";
		return this.update(sql);
	}
	
	
	
	//添加单位
	public boolean addUnit(Unit unit){
		return this.emiInsert(unit);
	}

	//修改单位
	public boolean updUnit(Unit unit){
		return this.emiUpdate(unit);
	}
	
	//删除单位
	public int deleteUnit(Unit unit){
		String sql="delete from unit where gid='"+unit.getGid()+"'";
		return this.update(sql);
	}
	
	
	//添加客户
	public boolean addCustomer(AaProviderCustomer customer){
		return this.emiInsert(customer);
	}

	//修改客户
	public boolean updCustomer(AaProviderCustomer customer){
		return this.emiUpdate(customer);
	}
	
	//删除客户
	public int deleteCustomer(AaProviderCustomer customer){
		String sql="delete from AA_Provider_Customer where gid='"+customer.getGid()+"'";
		return this.update(sql);
	}
	
	
	
	//添加订单
	public boolean addOrder(WmSaleorder wmSaleorder){
		return this.emiInsert(wmSaleorder);
	}

	//修改订单
	public boolean updOrder(WmSaleorder wmSaleorder){
		return this.emiUpdate(wmSaleorder);
	}
	
	//删除订单
	public int deleteOrder(WmSaleorder wmSaleorder){
		String sql="delete from WM_SaleOrder where gid='"+wmSaleorder.getGid()+"'";
		return this.update(sql);
	}
	
	
	
	//添加订单子表
	public boolean addOrderC(WmSaleorderC wmSaleorderC){
		return this.emiInsert(wmSaleorderC);
	}

	//修改订单子表
	public boolean updOrderC(WmSaleorderC wmSaleorderC){
		return this.emiUpdate(wmSaleorderC);
	}
	
	//删除订单子表
	public int deleteOrderC(WmSaleorderC wmSaleorderC){
		String sql="delete from WM_SaleOrder_C where gid='"+wmSaleorderC.getGid()+"'";
		return this.update(sql);
	}
	
	
	
}
