package com.emi.web.tplus.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.RowMapper;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.sys.init.Config;
import com.emi.web.tplus.bean.StCurrentStock;
import com.emi.web.tplus.bean.StRDRecordb;
import com.emi.web.tplus.bean.StRdRecord;
import com.emi.web.u890.bean.BasPart;
import com.emi.web.u890.bean.BomBom;
import com.emi.web.u890.bean.BomOpcomponent;
import com.emi.web.u890.bean.BomParent;
import com.emi.web.u890.bean.CurrentStock;
import com.emi.web.u890.bean.InvPosition;
import com.emi.web.u890.bean.RdRecord;
import com.emi.web.u890.bean.RdRecords;
import com.emi.web.u890.bean.StoreInBodyU8;
import com.emi.web.u890.bean.StoreInHeadU8;
import com.emi.web.u890.bean.StoreOutBodyU8;
import com.emi.web.u890.bean.StoreOutHeadU8;
import com.emi.web.u890.bean.TransVouch;
import com.emi.web.u890.bean.TransVouchs;
import com.emi.wms.bean.ProcurearrivalC;
import com.emi.wms.bean.SaleSendC;

public class DaoTplus extends BaseDao implements Serializable {

	private static final long serialVersionUID = 1069210670058724899L;

	/**
	 * @category 获得到货单子表gid
	 *2016 2016年4月23日下午3:49:45
	 *ProcurearrivalC
	 *宋银海
	 */
	public ProcurearrivalC getProcurearrivalC(String condition){
		String sql="select "+CommonUtil.colsFromBean(ProcurearrivalC.class)+" from WM_ProcureArrival_C where "+condition;
		return (ProcurearrivalC)this.emiQuery(sql, ProcurearrivalC.class);
	}
	
	/**
	 * @category 获得到货单子表列表
	 *2016 2016年4月23日下午3:49:45
	 *ProcurearrivalC
	 *宋银海
	 */
	public List<ProcurearrivalC> getProcurearrivalCList(String condition){
		String sql="select "+CommonUtil.colsFromBean(ProcurearrivalC.class)+" from WM_ProcureArrival_C where "+condition;
		return this.emiQueryList(sql, ProcurearrivalC.class);
	}
	
	//从用友中获得到货单信息
	public List<Map> getProcurearrivalCYonYou(String condition){
		//iCost本币无税单价                                                                   iMoney本币无税金额           iTaxPrice      本币税额    iSum 本币价税合计  
		//iOriCost 原币无税单价  iOriTaxCost 原币含税单价   iOriMoney 原币无税金额  iOriTaxPrice   原币税额    iOriSum  原币价税合计  
		String sql="select pas.cInvCode,pas.iQuantity,pas.iNum,pas.iCost,pas.iMoney,pas.iTaxPrice,pas.iSum,pas.cBatch,pas.itaxrate, "
				+ " pas.iOriCost,pas.iOriTaxCost,pas.iOriMoney,pas.iOriTaxPrice,pas.iOriSum,"
				+ " pas.autoid,"
				+ " pa.cvenCode,pa.id,pa.cCode,"
				+ " od.MODetailsID,"
				+ " om.cCode omcCode from "+Config.BUSINESSDATABASE+"PU_ArrivalVouch pa "
				+ " left join "+Config.BUSINESSDATABASE+"PU_ArrivalVouchs pas on pa.id=pas.id  "
				+ " left join "+Config.BUSINESSDATABASE+"OM_MODetails od on pas.iposid=od.MODetailsID"
				+ " left join "+Config.BUSINESSDATABASE+"OM_MOMain om on od.MOID=om.MOID where "+condition;
		return this.queryForList(sql);
	}
	
	
	
	/**
	 * @category 获得发货单子表gid
	 *2016 2016年4月23日下午3:49:45
	 *ProcurearrivalC
	 *宋银海
	 */
	public SaleSendC getSaleSendC(String condition){
		String sql="select "+CommonUtil.colsFromBean(SaleSendC.class)+" from WM_SaleSend_C where "+condition;
		return (SaleSendC)this.emiQuery(sql, SaleSendC.class);
	}
	
	/**
	 * @category 获得发货单子表列表
	 *2016 2016年4月23日下午3:49:45
	 *ProcurearrivalC
	 *宋银海
	 */
	public List<SaleSendC> getSaleSendCList(String condition){
		String sql="select "+CommonUtil.colsFromBean(SaleSendC.class)+" from WM_SaleSend_C where "+condition;
		return this.emiQueryList(sql, SaleSendC.class);
	}
	
	
	//从用友中获得发货单信息
	public List<Map> getSaleSendCYonYou(String condition){
		String sql="select dl.id,dl.code,dl.idcustomer,dl.idcurrency,dl.sourceVoucherCode from "+Config.BUSINESSDATABASE+"SA_SaleDelivery dl "
				+ "left join "+Config.BUSINESSDATABASE+"SA_SaleDelivery_b dls on dl.id=dls.idSaleDeliveryDTO  where "+condition;
		return this.queryForList(sql);
	}
	
	
	
	//采购入库时完善入库单主表
	public int[] updateRdrecordProcureArrival(final List<StoreInHeadU8> storeInHead) {
		//String sql="update "+Config.BUSINESSDATABASE+"rdrecord set cSource='采购到货单', ipurarriveid = '"+storeInHead.getIpurarriveid()+"',carVCode='"+storeInHead.getCarVCode()+"' where cdefine10='"+storeInHead.getDefine10()+"'";
		String sql="update "+Config.BUSINESSDATABASE+"rdrecord set cSource='采购到货单', ipurarriveid=?,carVCode=? where cdefine10=? ";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter(){
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				StoreInHeadU8 si=storeInHead.get(index);
				ps.setString(1, si.getIpurarriveid());
				ps.setString(2, si.getCarVCode());
				ps.setString(3, si.getDefine10());
			}
		
			@Override
			public int getBatchSize() {
				return storeInHead.size();
			}
		});
		
	}
	
	//采购入库时完善入库单子表
	public int[] updateRdrecordsProcureArrival(final List<StoreInBodyU8> storeInBody) {
		
		//String sql="update "+Config.BUSINESSDATABASE+"rdrecords set iArrsId =?,  iCost=?,iMoney=?,iTaxPrice=?,iSum=?,  iOriCost=?,iOriTaxCost=?,iOriMoney=?,iOriTaxPrice=?,iOriSum=?    where cDefine30=? ";
		
		String sql="update "+Config.BUSINESSDATABASE+"rdrecords set iArrsId =?,  iOriCost=?,iOriTaxCost=?,iOriMoney=?,iOriTaxPrice=?,ioriSum=?,itaxrate=?,   iTaxPrice=?,iSum=?  where cDefine30=? ";
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter(){
		
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				StoreInBodyU8 sobu8=storeInBody.get(index);
				ps.setInt(1, Integer.parseInt(sobu8.getIarrsId()));
				
				ps.setString(2, sobu8.getIoriCost().toString());
				ps.setString(3, sobu8.getIoriTaxCost().toString());
				ps.setString(4, sobu8.getIoriMoney().toString());
				ps.setString(5, sobu8.getIoriTaxPrice().toString());
				ps.setString(6, sobu8.getIoriSum().toString());
				ps.setString(7, CommonUtil.isNullObject(sobu8.getItaxrate())?"":sobu8.getItaxrate().toString());
				
//				ps.setString(2, sobu8.getIcost().toString());
//				ps.setString(3, sobu8.getImoney().toString());
				ps.setString(8, sobu8.getItaxPrice().toString());
				ps.setString(9, sobu8.getIsum().toString());
//				

				
				
				ps.setString(10, sobu8.getDefine30());
			}
		
			@Override
			public int getBatchSize() {
				return storeInBody.size();
				
			}
		});
		
	}
	
	//采购入库时反填到货单子表
	public int[] updatePuAppVouchs(final List<StoreInBodyU8> storeInBodyU8) {
		String sql="update "+Config.BUSINESSDATABASE+"PU_ArrivalVouchs set fValidInQuan=isnull(fValidInQuan,0)+?,fValidInNum=isnull(fValidInNum ,0)+? where Autoid=?";
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter(){
		
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				StoreInBodyU8 sob=storeInBodyU8.get(index);
				ps.setBigDecimal(1, BigDecimal.valueOf(Double.parseDouble(sob.getQuantity())));
				ps.setBigDecimal(2, CommonUtil.isNullObject(sob.getNumber())?BigDecimal.valueOf(0):BigDecimal.valueOf(Double.parseDouble(sob.getNumber())));
				ps.setInt(3, (Integer.parseInt(sob.getIarrsId())));
			}
		
			@Override
			public int getBatchSize() {
				return storeInBodyU8.size();
			}
		});
	}
	
	
	
	//采购入库时反填到货单子表
	public int[] backFillPuAppVouchs(final List<RdRecords> goods) {
		String sql="update "+Config.BUSINESSDATABASE+"PU_ArrivalVouchs set fValidInQuan=isnull(fValidInQuan,0)+?,fValidInNum=isnull(fValidInNum ,0)+? where Autoid=?";
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter(){
		
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				RdRecords sob=goods.get(index);
				ps.setBigDecimal(1, sob.getIquantity());
				ps.setBigDecimal(2, sob.getInum());
				ps.setInt(3, (Integer.parseInt(sob.getIarrsId())));
			}
		
			@Override
			public int getBatchSize() {
				return goods.size();
			}
		});
	}
	
	
	
	//销售出库时完善出库单主表
	public int[] updateRdrecordSaleOut(final List<StoreOutHeadU8> storeOutHead) {
		String sql="update "+Config.BUSINESSDATABASE+"rdrecord set cSource='发货单', iarriveid = ?,cBusCode=?,cdlCode=? where cdefine10=? ";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter(){
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				StoreOutHeadU8 soh=storeOutHead.get(index);
				ps.setString(1, soh.getIarriveid());
				ps.setString(2, soh.getCbusCode());
				ps.setString(3, soh.getCdlCode());
				ps.setString(4, soh.getDefine10());
			}
		
			@Override
			public int getBatchSize() {
				return storeOutHead.size();
			}
		});
	}
	
	
	//销售出库时完善出库单子表
	public int[] updateRdrecordsSaleOut(final List<StoreOutBodyU8> storeOutBodyU8) {
		
		String sql="update "+Config.BUSINESSDATABASE+"rdrecords set iDLsID =? where cDefine30=? ";
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter(){
		
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				StoreOutBodyU8 sobu8=storeOutBodyU8.get(index);
				ps.setString(1, sobu8.getIdLsID());
				ps.setString(2, sobu8.getDefine30());
			}
		
			@Override
			public int getBatchSize() {
				return storeOutBodyU8.size();
				
			}
		});
		
	}
	
	
	//销售出库时反填发货单子表
	public int[] updateDispatchLists(final List<StoreOutBodyU8> storeOutBodyU8) {
		String sql="update "+Config.BUSINESSDATABASE+"DispatchLists set fOutQuantity=isnull(fOutQuantity,0)+?,fOutNum=isnull(fOutNum,0)+? where idlsid=?";
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter(){
		
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				StoreOutBodyU8 sob=storeOutBodyU8.get(index);
				ps.setBigDecimal(1, BigDecimal.valueOf(Double.parseDouble(sob.getQuantity())));
				ps.setBigDecimal(2, CommonUtil.isNullObject(sob.getNumber())?BigDecimal.valueOf(0):BigDecimal.valueOf(Double.parseDouble(sob.getNumber())));
				ps.setString(3, sob.getIdLsID());
			}
		
			@Override
			public int getBatchSize() {
				return storeOutBodyU8.size();
			}
		});
	}
	
	//销售出库时反填发货单子表
	public int[] updateDispatchListsrds(final List<StRDRecordb> rdRecords) {
		String sql="update "+Config.BUSINESSDATABASE+"SA_SaleDelivery_b set saleOutQuantity=isnull(saleOutQuantity,0)+? where id=?";
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter(){
		
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				StRDRecordb sob=rdRecords.get(index);
				ps.setBigDecimal(1, sob.getBaseQuantity());
				ps.setString(2, sob.getSaleDeliveryId());
			}
		
			@Override
			public int getBatchSize() {
				return rdRecords.size();
			}
		});
	}
	
	//销售出库时反填发货单主表
	public int[] updateDispatchList(final List<StoreOutHeadU8> storeOutHead) {
		String sql="update "+Config.BUSINESSDATABASE+"DispatchList set cSaleOut= 'ST' where dlid=? ";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter(){
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				StoreOutHeadU8 soh=storeOutHead.get(index);
				ps.setString(1, soh.getCdlCode());
			}
		
			@Override
			public int getBatchSize() {
				return storeOutHead.size();
			}
		});
	}
	
	//销售出库时反填发货单主表
	public int[] updateDispatchListrd(final List<RdRecord> rdRecord) {
		String sql="update "+Config.BUSINESSDATABASE+"DispatchList set cSaleOut= 'ST' where dlid=? ";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter(){
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				RdRecord soh=rdRecord.get(index);
				ps.setString(1, soh.getCdLCode());
			}
		
			@Override
			public int getBatchSize() {
				return rdRecord.size();
			}
		});
	}
	
	
	//从用友中获得生产订单信息
	public List<Map> getMomOrderCYonYou(String condition){
		String sql="SELECT mo.moid,mo.moCode,mm.allocateId,bp.invCode,mod.invCode modInvCode FROM "+Config.BUSINESSDATABASE+"mom_order mo "+
					"LEFT JOIN "+Config.BUSINESSDATABASE+"mom_orderdetail mod on mod.moid=mo.moid "+
					"LEFT join "+Config.BUSINESSDATABASE+"mom_moallocate mm on mod.modid=mm.modid "+
					"LEFT JOIN "+Config.BUSINESSDATABASE+"bas_part bp on mm.componentid=bp.partid where "+condition;
		return this.queryForList(sql);
	}
	
	//从用友中获得生产订单信息
	public Map getMomOrderYonYou(String condition){
		String sql="SELECT mo.moid,mo.moCode FROM "+Config.BUSINESSDATABASE+"mom_order mo "+
					"LEFT JOIN "+Config.BUSINESSDATABASE+"mom_orderdetail mod on mod.moid=mo.moid "+
					" where "+condition;
		return this.queryForMap(sql);
	}
	
	
	//生产领料时完善出库单主表
	public int[] updateRdrecordScc(final List<StoreOutHeadU8> storeOutHead) {
		
		String sql="update "+Config.BUSINESSDATABASE+"rdrecord set  iproorderid = ?,cmpocode=? where cdefine10=? ";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter(){
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				StoreOutHeadU8 soh=storeOutHead.get(index);
				ps.setString(1, soh.getIproorderid());
				ps.setString(2, soh.getCmPoCode());
				ps.setString(3, soh.getDefine10());
			}
		
			@Override
			public int getBatchSize() {
				return storeOutHead.size();
			}
		});
		
	}
	
	//生产领料时完善出库单子表
	public int[] updateRdrecordsScc(final List<StoreOutBodyU8> body) {
		String sql="update "+Config.BUSINESSDATABASE+"rdrecords set impoids=? where cDefine30=? ";
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				StoreOutBodyU8 siob=body.get(index);
				ps.setString(1, siob.getImPoIds());
				ps.setString(2, siob.getDefine30());
				
			}
			
			@Override
			public int getBatchSize() {
				
				return body.size();
			}
		});
	}
	
	//生产领料时反填订单子件表
	public int[] updateMoallocate(final List<StoreOutBodyU8> body) {
		String sql="update "+Config.BUSINESSDATABASE+"mom_moallocate set issQty=isnull(issQty,0)+? where allocateId=? ";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				StoreOutBodyU8 siob=body.get(index);
				ps.setString(1, siob.getQuantity());
				//ps.setDouble(2, CommonUtil.isNullObject(siob.getNumber()) ? 0 : Double.valueOf(siob.getNumber()));
				ps.setString(2, siob.getImPoIds());
			}
			
			@Override
			public int getBatchSize() {
				return body.size();
			}
		});
	}
	
	
	//生产入库时完善入库单主表
	public int[] updateRdrecordScr(final List<StoreInHeadU8> heads) {
		
		String sql="update "+Config.BUSINESSDATABASE+"rdrecord set  iproorderid = ?,cmpocode=?,cSource='生产订单' where cdefine10=? ";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter(){
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				StoreInHeadU8 si=heads.get(index);
				ps.setString(1, si.getIproorderid());
				ps.setString(2, si.getCmpocode());
				ps.setString(3, si.getDefine10());
			}
		
			@Override
			public int getBatchSize() {
				return heads.size();
			}
		});
	}
	
	
	//生产入库时完善入库单子表
	public int[] updateRdrecordsScr(final List<StoreInBodyU8> storeInBodyU8) {
		String sql="update "+Config.BUSINESSDATABASE+"rdrecords set iMPoIds=?,iUnitCost=NULL,iPrice=NULL where cDefine30=? ";
	
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				StoreInBodyU8 siob=storeInBodyU8.get(index);
				ps.setString(1, siob.getImPoIds());
				ps.setString(2, siob.getDefine30());
				
			}
			
			@Override
			public int getBatchSize() {
				
				return storeInBodyU8.size();
			}
		});
	}
	
	//生产入库时反填订单已入库数量
	public int[] updateMomOrderdetail(final List<StoreInBodyU8> storeInBodyU8) {
		String sql="update "+Config.BUSINESSDATABASE+"mom_orderdetail set qualifiedInQty =isnull(qualifiedInQty ,0)+? where moDId=? ";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				StoreInBodyU8 siob=storeInBodyU8.get(index);
				ps.setString(1, siob.getQuantity());
				ps.setString(2, siob.getImPoIds());
			}
			
			@Override
			public int getBatchSize() {
				
				return storeInBodyU8.size();
			}
		});
	}
	
	
	
	/**
	 * @category 从用友中查询现存量
	 *2016 2016年5月3日上午10:50:11
	 *List<CurrentStock>
	 *宋银海
	 */
	public List<StCurrentStock> getAllocationStock(String condition){
		String sql="select id,idwarehouse,idinventory,batch,baseQuantity,subQuantity,  idbaseunit,recordDate,createdTime,updated,updatedBy from "+Config.BUSINESSDATABASE+"ST_CurrentStock where "+condition;
		
		return this.getJdbcTemplate().query(sql, new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				StCurrentStock cs=new StCurrentStock();
				cs.setId(rs.getString("id"));
				cs.setIdwarehouse(CommonUtil.Obj2String(rs.getObject("idwarehouse")));
				cs.setIdinventory(rs.getString("idinventory"));
				cs.setBatch(CommonUtil.Obj2String(rs.getObject("batch")));
				cs.setBaseQuantity(CommonUtil.BigDecimal2BigDecimal(rs.getBigDecimal("baseQuantity")) );
				cs.setSubQuantity(CommonUtil.BigDecimal2BigDecimal(rs.getBigDecimal("subQuantity")));
				cs.setIdbaseunit(CommonUtil.Obj2String(rs.getObject("idbaseunit")));
				cs.setRecordDate(CommonUtil.Obj2String(rs.getObject("recordDate")));
				cs.setCreatedTime(CommonUtil.Obj2String(rs.getObject("createdTime")));
				cs.setUpdated(CommonUtil.Obj2String(rs.getObject("updated")));
				cs.setUpdatedBy(CommonUtil.Obj2String(rs.getObject("updatedBy")));
				return cs;
			}
			
		});
	}
	
	
	/**
	 * @category 修改待发货数量
	 *2016 2016年5月3日上午10:50:11
	 *List<CurrentStock>
	 *宋银海
	 */
	public int[] batchUptFout(final List<CurrentStock> as) {
		
		String sql="update "+Config.BUSINESSDATABASE+"CurrentStock set fOutQuantity=isnull(fOutQuantity,0)-?, fInQuantity=isnull(fInQuantity,0)-? where autoid=?";

		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				CurrentStock cs=as.get(index);
				
				ps.setBigDecimal(1, CommonUtil.BigDecimal2BigDecimal(cs.getFoutQuantity()));
				ps.setBigDecimal(2, CommonUtil.BigDecimal2BigDecimal(cs.getFinQuantity()));
				ps.setInt(3, cs.getAutoID());
				
			}
			
			@Override
			public int getBatchSize() {
				return as.size();
			}
		});
		
	}
	
	//批量修改货位现存量
	public int[] batchUptCurrentStock(final List<StCurrentStock> as) {
		
		String sql="update "+Config.BUSINESSDATABASE+"ST_CurrentStock set baseQuantity=isnull(baseQuantity,0)+?,subQuantity=isnull(subQuantity,0)+? "
				+ "  where id=?";

		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				StCurrentStock cs=as.get(index);
				
				ps.setString(1, CommonUtil.BigDecimal2BigDecimal(cs.getBaseQuantity()).toPlainString());
				ps.setString(2, CommonUtil.BigDecimal2BigDecimal(cs.getSubQuantity()).toPlainString());
				ps.setString(3, cs.getId());
				
			}
			
			@Override
			public int getBatchSize() {
				return as.size();
			}
		});
		
	}
	
	//批量增加货位现存量
	public int[] batchInsertCurrentStock(final List<StCurrentStock> as) {
		
		String sql="insert into "+Config.BUSINESSDATABASE+"St_CurrentStock(id,idwarehouse,idinventory,batch,baseQuantity,subQuantity,  "
				+ " idbaseunit,recordDate,createdTime,updated,updatedBy,idmarketingOrgan) values(?,?,?,?,?,?,?,?,?,?,?,?) ";

		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				StCurrentStock cs=as.get(index);
				
				ps.setString(1, cs.getId());
				ps.setString(2, CommonUtil.Obj2String(cs.getIdwarehouse()));
				ps.setString(3, cs.getIdinventory());
				ps.setString(4, CommonUtil.isNullObject(cs.getBatch())?null:cs.getBatch());
				ps.setString(5, CommonUtil.isNullObject(cs.getBaseQuantity())?null:cs.getBaseQuantity().toPlainString());
				ps.setString(6, CommonUtil.isNullObject(cs.getSubQuantity())?null:cs.getSubQuantity().toPlainString());
				ps.setString(7, cs.getIdbaseunit());
				ps.setString(8, cs.getRecordDate());
				ps.setString(9, cs.getCreatedTime());
				ps.setString(10, cs.getUpdated());
				ps.setString(11, cs.getUpdatedBy());
				ps.setString(12, cs.getIdmarketingOrgan());
			}
			
			@Override
			public int getBatchSize() {
				return as.size();
			}
		});
		
	}
	
	

	/**
	 * @category 添加bom
	 * 2016年5月17日 下午2:33:34 
	 * @author zhuxiaochen
	 * @param bom_bom
	 * @param bom_parent
	 * @param bom_opcomponent
	 */
	public void addBom(BomBom bom_bom, BomParent bom_parent,
			List<BomOpcomponent> bom_opcomponent) {
		String[] sqls = new String[bom_opcomponent.size()*2+2];
		sqls[0] = "insert into "+Config.BUSINESSDATABASE+"bom_bom(BomId,BomType,Version,VersionDesc,VersionEffDate,VersionEndDate,CreateDate,CreateTime,Define14) values"
				+ "('"+bom_bom.getBomId()+"','"+bom_bom.getBomType()+"','"+bom_bom.getVersion()+"','"+bom_bom.getVersionDesc()+"',"+this.formatValue(bom_bom.getVersionEffDate())+""
						+ ","+this.formatValue(bom_bom.getVersionEndDate())+","+this.formatValue(bom_bom.getCreateTime())+","+this.formatValue(bom_bom.getCreateTime())+",'"+bom_bom.getLugGoodsId()+"')";
		sqls[1] = "insert into "+Config.BUSINESSDATABASE+"bom_parent(AutoId,BomId,ParentId) values('"+UUID.randomUUID().toString()+"',"+bom_parent.getBomId()+",'"+bom_parent.getParentId()+"')";
		
		int bom_opId = this.getBillId("00", Config.BUSINESSDATABASE.split("_")[1], "bom_opcomponent", bom_opcomponent.size())[1] -bom_opcomponent.size()+1;//("bom_opcomponent");
		int bom_opOptId = this.getBillId("00", Config.BUSINESSDATABASE.split("_")[1], "bom_opcomponentopt", bom_opcomponent.size())[1] -bom_opcomponent.size()+1;//("bom_opcomponent");
		//bom_opcomponent
		for(int i=0;i<bom_opcomponent.size();i++){
			BomOpcomponent bo = bom_opcomponent.get(i);
			sqls[i+2] = "insert into "+Config.BUSINESSDATABASE+"bom_opcomponent(OpComponentId,BomId,SortSeq,OpSeq,ComponentId,EffBegDate,EffEndDate,BaseQtyN,BaseQtyD,OptionsId) values("+bom_opId+","+bo.getBomId()+""
					+ ",'"+bo.getSortSeq()+"','"+bo.getOpSeq()+"','"+bo.getComponentId()+"',"+this.formatValue(bo.getEffBegDate())+","+this.formatValue(bo.getEffEndDate())+",'"+bo.getBaseQtyN()+"','"+bo.getBaseQtyD()+"'"
							+ ","+(bom_opOptId+i)+")";
			bom_opId ++ ;
		}
		//bom_opcomponentopt
		for(int i=0;i<bom_opcomponent.size();i++){
			sqls[i+bom_opcomponent.size()+2] = "insert into "+Config.BUSINESSDATABASE+"bom_opcomponentopt(OptionsId,MutexRule) values("+bom_opOptId+",2)";
			bom_opOptId ++ ;
		}
		this.bathUpdate(sqls);
	}

	public List<BasPart> getBasPartList(String invCodes) {
		String sql = "select partId,invCode,free1 from "+Config.BUSINESSDATABASE+"bas_part where 1=1";
		if(CommonUtil.notNullString(invCodes)){
			invCodes = invCodes.replaceAll(",", "','");
			sql += " and invCode in ('"+invCodes+"')";
		}
		return this.getJdbcTemplate().query(sql, new RowMapper<BasPart>() {
			@Override
			public BasPart mapRow(ResultSet rs, int rowNum) throws SQLException {
				BasPart bp = new BasPart();
				bp.setPartId(rs.getObject("partId")==null?0:rs.getInt("partId"));
				bp.setInvCode(rs.getObject("invCode")==null?null:rs.getString("invCode"));
				bp.setFree1(rs.getObject("free1")==null?null:rs.getString("free1"));
				return bp;
			}
		});
	}


	/**
	 * @category 获得主表子表 id    参数 remoteId 值一般为""; cacc_Id 为帐套编码  cvouchType 为单据类型 iamount 为步长  。返回值int[0]为主表id,int[1]为子表最大的id
	 *2016 2016年5月14日下午1:08:27
	 *String[]
	 *宋银海
	 */
	public int[] getBillId(final String remoteId,final String cacc_Id, final String cvouchType,final int iamount) {
		
		return (int[])this.getJdbcTemplate().execute(new ConnectionCallback() {

			@Override
			public Object doInConnection(Connection arg0) throws SQLException,
					DataAccessException {
				
				String psql = "{call "+Config.BUSINESSDATABASE+"sp_GetID(?,?,?,?,?,?)}"; 
				int[] currentIds=new int[2];
				
				CallableStatement call = arg0.prepareCall(psql);
				
				call.setString(1, remoteId);
				call.setString(2, cacc_Id);
				call.setString(3, cvouchType);
				call.setInt(4, iamount);
				call.registerOutParameter(5, Types.INTEGER);
				call.registerOutParameter(6, Types.INTEGER);
//				call.setInt(7, 1);
				call.execute();
				
				currentIds[0]=call.getInt(5);
				currentIds[1]=call.getInt(6);
				
	            call.close();
				return currentIds;
			}
			
			
		});
				
	}
	
	/**
	 * @category 获得编码
	 *2016 2016年5月14日下午1:39:52
	 *Map
	 *宋银海
	 */
//	public Map getVoucherHistory(String condition) {
//		String sql="select cNumber from "+Config.BUSINESSDATABASE+"VoucherHistory where  "+condition;
//		return this.queryForMap(sql);
//	}
	
	/**
	 * @category 修改编码
	 *2016 2016年5月14日下午1:39:52
	 *Map
	 *宋银海
	 */
//	public int uptVoucherHistory(String cnumber,String cardNumber) {
//		String sql="update "+Config.BUSINESSDATABASE+"VoucherHistory set cNumber='"+cnumber+"' where CardNumber='"+cardNumber+"'";
//		return this.update(sql);
//	}
	
	/**
	 * @category 根据条件删除原有BOM
	 * 2016年5月12日 下午5:18:24 
	 * @author zhuxiaochen
	 * @param lugGoodsId
	 * @param version
	 */
	public void deleteOldBom(String lugGoodsId, Integer version) {
		//删除bom_opcomponentopt
		String sql1 = "delete from "+Config.BUSINESSDATABASE+"bom_opcomponentopt where OptionsId in(select OptionsId from "+Config.BUSINESSDATABASE+"bom_opcomponent where BomId in"
				+ " (select BomId from "+Config.BUSINESSDATABASE+"bom_bom where Define14='"+lugGoodsId+"' and Version="+version+"))";
		//删除bom_opcomponent
		String sql2 = "delete from "+Config.BUSINESSDATABASE+"bom_opcomponent where BomId in"
				+ " (select BomId from "+Config.BUSINESSDATABASE+"bom_bom where Define14='"+lugGoodsId+"' and Version="+version+")";
		//删除bom_parent
		String sql3 = "delete from "+Config.BUSINESSDATABASE+"bom_parent where BomId in"
				+ " (select BomId from "+Config.BUSINESSDATABASE+"bom_bom where Define14='"+lugGoodsId+"' and Version="+version+")";
		//删除bom_bom
		String sql4 = "delete from "+Config.BUSINESSDATABASE+"bom_bom where Define14='"+lugGoodsId+"' and Version="+version+"";
		this.batchUpdate(new String[]{sql1,sql2,sql3,sql4});
	}
	
	/**
	 * @category 添加调拨单主表
	 *2016 2016年5月14日下午2:11:59
	 *int[]
	 *宋银海
	 */
	public int[] addTransVouch(final List<TransVouch> tv){
		String sql="insert into "+Config.BUSINESSDATABASE+"TransVouch(id,ctVCode,dtVDate,coWhCode,ciWhCode,cmaker,vt_id,cVerifyPerson) values(?,?,?,?,?,?,?,?) ";
		System.out.println(sql);
		return this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				TransVouch t=tv.get(index);
				ps.setInt(1, t.getId());
				ps.setString(2, t.getCtVCode());
				ps.setTimestamp(3, t.getDtVDate());
				ps.setString(4, t.getCoWhCode());
				ps.setString(5, t.getCiWhCode());
				ps.setString(6, t.getCmaker());
				ps.setInt(7, t.getVtid());
				ps.setString(8, t.getCverifyPerson());
			}
			
			@Override
			public int getBatchSize() {
				return tv.size();
			}
		});
	}
	
	
	/**
	 * @category 添加调拨单子表
	 *2016 2016年5月14日下午2:11:59
	 *int[]
	 *宋银海
	 */
	public int[] addTransVouchs(final List<TransVouchs> tvs){
		String sql="insert into "+Config.BUSINESSDATABASE+"TransVouchs(autoID,id,ctVCode,cinvCode,itVQuantity,itVNum,cassUnit,iinvexchrate,irowno,ctVBatch,cFree1,cFree2,coutposcode,cinposcode,RdsID) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		System.out.println(sql);
		return this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				TransVouchs t=tvs.get(index);
				ps.setInt(1, t.getAutoID());
				ps.setInt(2, t.getId());
				ps.setString(3, t.getCtVCode());
				ps.setString(4, t.getCinvCode());
				ps.setBigDecimal(5, t.getItVQuantity());
				ps.setBigDecimal(6, t.getItVNum());
				ps.setString(7, t.getCassUnit());
				ps.setBigDecimal(8, t.getIinvexchrate());
				ps.setInt(9, t.getIrowno());
				ps.setString(10, t.getCtVBatch());
				ps.setString(11, t.getCfree1());
				ps.setString(12, t.getCfree2());
				ps.setString(13, t.getCoutposcode());
				ps.setString(14, t.getCinposcode());
				ps.setString(15, null);
			}
			
			@Override
			public int getBatchSize() {
				return tvs.size();
			}
		});
	}
	
	
	/**
	 * @category 添加存货货位记录表
	 *2016 2016年5月16日上午9:39:38
	 *int[]
	 *宋银海
	 */
	public int[] addInvPosition(final List<InvPosition> ips){
		String sql="insert into "+Config.BUSINESSDATABASE+"InvPosition(rdID,rdsID,cwhCode,cposCode,cinvCode,cbatch,cfree1,cfree2,iquantity,inum,chandler,ddate,brdFlag) values(?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		System.out.println(sql);
		return this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				InvPosition ip=ips.get(index);
				ps.setInt(1,ip.getRdID());
				ps.setInt(2, ip.getRdsID());
				ps.setString(3, ip.getCwhCode());
				ps.setString(4, ip.getCposCode());
				ps.setString(5, ip.getCinvCode());
				ps.setString(6, CommonUtil.isNullObject(ip.getCbatch())?null:ip.getCbatch());
				ps.setString(7, CommonUtil.isNullObject(ip.getCfree1())?null:ip.getCfree1());
				ps.setString(8, CommonUtil.isNullObject(ip.getCfree2())?null:ip.getCfree2());
				ps.setBigDecimal(9, ip.getIquantity());
				ps.setBigDecimal(10, ip.getInum());
				ps.setString(11, ip.getChandler());
				ps.setTimestamp(12, ip.getDdate());
				ps.setInt(13, ip.getBrdFlag());
			}
			
			@Override
			public int getBatchSize() {
				return ips.size();
			}
		});
	}
	
	
	
	/**
	 * @category 添加入库单主表
	 *2016 2016年5月16日上午10:58:56
	 *int[]
	 *宋银海
	 */
	public int[] addRdRecord(final List<StRdRecord> rds){
		String sql="insert into "+Config.BUSINESSDATABASE+"ST_RDRecord(id,code,voucherState,rdDirectionFlag,accountState,voucherdate,maker,madedate,auditor,makerid,auditorid,auditeddate,"
				+ "accountingperiod,accountingyear,createdtime,idbusitype,idvouchertype,idwarehouse,idrdstyle,voucherYear,voucherPeriod,idmarketingOrgan,"
				+ " deliveryState,idpartner,idcurrency,idsettleCustomer,idsourceVoucherType,sourceVoucherId,sourceVoucherCode) "
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?  ,?,?,?,?,?,?,?) ";
		System.out.println(sql);
		return this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				StRdRecord rd=rds.get(index);
				ps.setString(1,rd.getId());
				ps.setString(2, rd.getCode());
				ps.setString(3, rd.getVoucherState());
				ps.setInt(4, rd.getRdDirectionFlag());
				ps.setString(5, rd.getAccountState());
				ps.setString(6, rd.getVoucherdate());
				ps.setString(7, rd.getMaker());
				ps.setString(8, rd.getMadedate());
				ps.setString(9, rd.getAuditor());
				ps.setString(10, rd.getMakerid());
				ps.setString(11, rd.getAuditorid());
				ps.setString(12, rd.getAuditeddate());
				ps.setInt(13, rd.getAccountingperiod());
				ps.setInt(14, rd.getAccountingyear());
				ps.setString(15, rd.getCreatedtime());
				ps.setString(16, rd.getIdbusitype());
				ps.setString(17, rd.getIdvouchertype());
				ps.setString(18, rd.getIdwarehouse());
				ps.setString(19, rd.getIdrdstyle());
				ps.setInt(20, rd.getVoucherYear());
				ps.setInt(21, rd.getVoucherPeriod());
				ps.setString(22, rd.getIdmarketingOrgan());
				
				ps.setString(23, rd.getDeliveryState());
				ps.setString(24, rd.getIdpartner());
				ps.setString(25, rd.getIdcurrency());
				ps.setString(26, rd.getIdsettleCustomer());
				ps.setString(27, rd.getIdsourceVoucherType());
				ps.setString(28, rd.getSourceVoucherId());
				ps.setString(29, rd.getSourceVoucherCode());
			}
			
			@Override
			public int getBatchSize() {
				return rds.size();
			}
		});
	}
	
	/**
	 * 添加入库单子表
	 *2016 2016年5月16日上午11:00:31
	 *int[]
	 *宋银海
	 */
	public int[] addRdRecords(final List<StRDRecordb> rrss){
		String sql="insert into "+Config.BUSINESSDATABASE+"ST_RDRecord_b(id,code,idinventory,idunit,idbaseunit,idwarehouse,batch,inventoryLocation,quantity,"
				+ " baseQuantity,subQuantity,createdtime,updated,updatedBy,idRDRecordDTO,price,basePrice,amount,"
				+ " sourceVoucherId,sourceVoucherCode,sourceVoucherDetailId,cumulativeSaleDispatchQuantity,idsourceVoucherType ) "
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?  ,?,?,?,?,?) ";
		System.out.println(sql);
		return this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				StRDRecordb rr=rrss.get(index);
				ps.setString(1, rr.getId());
				ps.setString(2, rr.getCode());
				ps.setString(3, rr.getIdinventory());
				ps.setString(4, rr.getIdunit());
				ps.setString(5, rr.getIdbaseunit());
				ps.setString(6, rr.getIdwarehouse());
				ps.setString(7, rr.getBatch());
				ps.setString(8, rr.getInventoryLocation());
				ps.setBigDecimal(9, rr.getQuantity());
				ps.setBigDecimal(10, rr.getBaseQuantity());
				ps.setBigDecimal(11, rr.getSubQuantity());
				ps.setString(12, rr.getCreatedtime());
				ps.setString(13, rr.getUpdated());
				ps.setString(14, rr.getUpdatedBy());
				ps.setString(15, rr.getIdRDRecordDTO());
				ps.setBigDecimal(16, new BigDecimal(0));
				ps.setBigDecimal(17, new BigDecimal(0));
				ps.setBigDecimal(18, new BigDecimal(0));
				
				ps.setString(19,rr.getSourceVoucherId());
				ps.setString(20, rr.getSourceVoucherCode());
				ps.setString(21, rr.getSourceVoucherDetailId());
				ps.setBigDecimal(22, rr.getCumulativeSaleDispatchQuantity());
				ps.setString(23, rr.getIdsourceVoucherType());
				
			}
			
			@Override
			public int getBatchSize() {
				return rrss.size();
			}
		});
	}
	
	
	//从用友中获得发货单信息
	public List<Map> getOMMOMaterials(String condition){
		String sql="select omn.moid,omn.ccode,omn.cvenCode,omd.cInvCode, "
				+ " omd.moDetailsID,"
				+ " oms.mOMaterialsID,oms.iQuantity from "+Config.BUSINESSDATABASE+"OM_MOMaterials oms "
				+ "left join "+Config.BUSINESSDATABASE+"OM_MODetails omd on oms.MoDetailsID=omd.MODetailsID "
				+ "left join "+Config.BUSINESSDATABASE+"OM_MOMain omn on omd.MOID=omn.MOID where "+condition;
		return this.queryForList(sql);
	}
	
	
	
	//委外领料反填已领料数量
	public int[] backFillMoMaterialsC(final List<RdRecords> goods) {
		String sql="update "+Config.BUSINESSDATABASE+"OM_MOMaterials set iSendQTY=isnull(iSendQTY,0)+? where MOMaterialsID=? ";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				RdRecords siob=goods.get(index);
				ps.setBigDecimal(1, siob.getIquantity());
				ps.setString(2, siob.getIoMoMID());
			}
			
			@Override
			public int getBatchSize() {
				return goods.size();
			}
		});
	}
	
	
	//委外领料反填产品表已领料数量
	public int[] backFillMoGoodssC(final List<RdRecords> goods) {
		String sql="update "+Config.BUSINESSDATABASE+"OM_MODetails set iMaterialSendQty=isnull(iMaterialSendQty,0)+? where MODetailsID=? ";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				RdRecords siob=goods.get(index);
				ps.setBigDecimal(1, siob.getIquantity());
				ps.setString(2, siob.getIoMoDID());
			}
			
			@Override
			public int getBatchSize() {
				return goods.size();
			}
		});
	}
	
	//工序退料时反填生产订单子表已领数量
	public int[] backFillMoallocate(final List<RdRecords> goods) {
		String sql="update "+Config.BUSINESSDATABASE+"mom_moallocate set issQty=isnull(issQty,0)+? where allocateId=? ";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				RdRecords siob=goods.get(index);
				ps.setBigDecimal(1, siob.getIquantity());
				//ps.setDouble(2, CommonUtil.isNullObject(siob.getNumber()) ? 0 : Double.valueOf(siob.getNumber()));
				ps.setString(2, siob.getImPoIds());
			}
			
			@Override
			public int getBatchSize() {
				return goods.size();
			}
		});
	}
	
	
	//返回单据号码MAP
	public Map getRDRecordCode(String condition){
		String sql="SELECT CAST (RIGHT ( max(code), 4) AS DECIMAL ) as code from "+Config.BUSINESSDATABASE+"ST_RDRecord where "+condition;
		return this.queryForMap(sql);
	}
	
	//返回收发类别
	public Map getrdstyle(String condition){
		String sql="SELECT gid from YM_RdStyle where "+condition;
		return this.queryForMap(sql);
	}
	
	//返回机构号
	public Map getIdmarketingOrgan(String condition){
		String sql="SELECT id from "+Config.BUSINESSDATABASE+"AA_MarketingOrgan where "+condition;
		return this.queryForMap(sql);
	}
	
	public List getsystemsetting(){
		String sql = "select * from YM_Settings ";
		return this.queryForList(sql);
	}
	
	public boolean updatesystemsetting(String synchroType,String eaiAddress,String eaiCode){
		try {
			String[] sqls = new String[3];
			sqls[0] = "update YM_Settings set paramValue = '"+synchroType+"' where setName = 'synchroType'";
			sqls[1] = "update YM_Settings set paramValue = '"+eaiAddress+"' where setName = 'eaiAddress'";
			sqls[2] = "update YM_Settings set paramValue = '"+eaiCode+"' where setName = 'eaiCode'";
			this.bathUpdate(sqls);
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	
	//获得编号规则
	public Map getVoucherNumber(String idvoucherType){
		String sql="select prefixion1,prefixion1length,prefixion1content,"
				+ " prefixion2,prefixion2length,prefixion2content,"
				+ " prefixion3,prefixion3length,prefixion3content,"
				+ " separator,serialnolength,startordenalnumber from "+Config.BUSINESSDATABASE+"SM_VoucherType where id='"+idvoucherType+"'";
		return this.queryForMap(sql);
	}
	
	/**
	 * @category 获得编码
	 *2016 2016年5月14日下午1:39:52
	 *Map
	 *宋银海
	 */
	public Map getVoucherHistory(Map param) {//此sql语句通过时间探查器跟踪得出
		String sql=" select max(distinct cast(RIGHT(t1.code,"+param.get("serialnolength").toString()+") as Decimal)) as startCode, max(cast(RIGHT(t1.code,"+param.get("serialnolength").toString()+") as Decimal)) as endCode "+ 
					" from "+Config.BUSINESSDATABASE+"ST_RDRecord as t1 where t1.code like '"+param.get("codelike").toString()+"' and ISNUMERIC(RIGHT(t1.code,"+param.get("serialnolength").toString()+")) = 1  and LEN(t1.code) - "+param.get("totallength").toString()+" = 0  and "+ 
					" t1.idvoucherType = '"+param.get("idvoucherType").toString()+"'";
		return this.queryForMap(sql);
	}
	
	
	
	
	
}
