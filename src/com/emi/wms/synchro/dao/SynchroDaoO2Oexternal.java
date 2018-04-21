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
import com.emi.wms.bean.WmSaleorder;
import com.emi.wms.beanO2O.Customer;
import com.emi.wms.beanO2O.Goods;
import com.emi.wms.beanO2O.Goodsort;
import com.emi.wms.beanO2O.Saleorder;
import com.emi.wms.beanO2O.Saleorder_c;
import com.emi.wms.beanO2O.UnitO2O;
import com.emi.wms.util.Constants;

public class SynchroDaoO2Oexternal extends BaseDao {
	
	
	//查询分类
	public List<Goodsort> getInventoryClass(String condition){
		String sql="select "+CommonUtil.colsFromBean(Goodsort.class)+" from aa_goodsort where "+condition+" order by pk ";
		return this.emiQueryList(sql, Goodsort.class);
	}

	//修改分类
	public int[] updateInventoryClassStats(final List<Goodsort> goodsort){
		String sql="update aa_goodsort set dealstats=1 where pk=?";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				Goodsort gt=goodsort.get(index);
				
				ps.setInt(1, gt.getPk());
			}
			
			@Override
			public int getBatchSize() {
				return goodsort.size();
			}
		});
	}
	
	//查询物品
	public List<Goods> getGoods(String condition){
		String sql="select "+CommonUtil.colsFromBean(Goods.class)+" from aa_goodssize where "+condition+" order by pk ";
		return this.emiQueryList(sql, Goods.class);
	}
	
	//修改分类
	public int[] updateGoodsStats(final List<Goods> goods){
		String sql="update aa_goodssize set dealstats=1 where pk=?";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				Goods gt=goods.get(index);
				
				ps.setInt(1, gt.getPk());
			}
			
			@Override
			public int getBatchSize() {
				return goods.size();
			}
		});
	}
	
	
	//查询计量单位
	public List<UnitO2O> getUnit(String condition){
		String sql="select "+CommonUtil.colsFromBean(UnitO2O.class)+" from aa_unit where "+condition+" order by pk ";
		return this.emiQueryList(sql, UnitO2O.class);
	}
	
	//修改计量单位
	public int[] updateUnitStats(final List<UnitO2O> unitO2O){
		String sql="update aa_unit set dealstats=1 where pk=?";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				UnitO2O u=unitO2O.get(index);
				
				ps.setInt(1, u.getPk());
			}
			
			@Override
			public int getBatchSize() {
				return unitO2O.size();
			}
		});
	}
	
	
	//查询客户
	public List<Customer> getCustomer(String condition){
		String sql="select "+CommonUtil.colsFromBean(Customer.class)+" from aa_customer where "+condition+" order by pk ";
		return this.emiQueryList(sql, Customer.class);
	}
	
	//修改客户
	public int[] updateCustomerStats(final List<Customer> customer){
		String sql="update aa_customer set dealstats=1 where pk=?";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				Customer c=customer.get(index);
				
				ps.setInt(1, c.getPk());
			}
			
			@Override
			public int getBatchSize() {
				return customer.size();
			}
		});
	}
	
	
	//查询订单主表
	public List<Saleorder> getOrder(String condition){
		String sql="select "+CommonUtil.colsFromBean(Saleorder.class)+" from aa_saleorder where "+condition+" order by pk ";
		return this.emiQueryList(sql, Saleorder.class);
	}
	
	//修改订单主表
	public int[] updateSaleorderStats(final List<Saleorder> saleorder){
		String sql="update aa_saleorder set dealstats=1 where pk=?";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				Saleorder s=saleorder.get(index);
				
				ps.setInt(1, s.getPk());
			}
			
			@Override
			public int getBatchSize() {
				return saleorder.size();
			}
		});
	}
	
	
	
	//查询订单子表
	public List<Saleorder_c> getOrderC(String condition){
		String sql="select "+CommonUtil.colsFromBean(Saleorder_c.class)+" from aa_saleorder_c where "+condition+" order by pk ";
		return this.emiQueryList(sql, Saleorder_c.class);
	}
	
	//修改订单子表
	public int[] updateSaleorderCStats(final List<Saleorder_c> saleorderC){
		String sql="update aa_saleorder_c set dealstats=1 where pk=?";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				Saleorder_c s=saleorderC.get(index);
				
				ps.setInt(1, s.getPk());
			}
			
			@Override
			public int getBatchSize() {
				return saleorderC.size();
			}
		});
	}
	
	
}
