package com.emi.web.u890.dao;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
import com.emi.common.util.DateUtil;
import com.emi.sys.init.Config;
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

public class DaoU890 extends BaseDao implements Serializable {

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
				+ " pas.autoid,pas.cDefine23 pasDefine23, pas.dVDate,pas.dPDate,pas.imassdate, pas.corderCode,"
				+ " pas.iposid, pas.iQuantity-pas.fValidInQuan as stillQuantity,pas.btaxCost,"
				+ " pas.cBatchProperty7,pas.cBatchProperty8,pas.cBatchProperty9,pas.cBatchProperty10, "
				+ " pa.cvenCode,pa.id,pa.cCode,pa.cDepCode,pa.cPersonCode,pa.cPTCode,  pa.cMemo,pa.dDate,pa.iTaxRate mainitaxrate,pa.cExch_Name, "
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
		String sql="select dl.dlid,dl.cDLCode,dls.idLsID,dl.cCusCode,dls.dvdate  ,dls.dMDate,dls.iMassDate,dls.cMassUnit   from "+Config.BUSINESSDATABASE+"DispatchList dl "
				+ "left join "+Config.BUSINESSDATABASE+"DispatchLists dls on dl.dlid=dls.dlid  where "+condition;
		return this.queryForList(sql);
	}
	
	
	//采购入库时完善入库单主表
	public int[] updateRdrecordProcureArrival(final List<StoreInHeadU8> storeInHead) {
		//String sql="update "+Config.BUSINESSDATABASE+"rdrecord set cSource='采购到货单', ipurarriveid = '"+storeInHead.getIpurarriveid()+"',carVCode='"+storeInHead.getCarVCode()+"' where cdefine10='"+storeInHead.getDefine10()+"'";
		String sql="update "+Config.BUSINESSDATABASE+"rdrecord set cSource='采购到货单',cPTCode=?, ipurarriveid=?,carVCode=?  ,cMemo=?,dARVDate=?,cExch_Name=?,iTaxRate=?,cRdCode=?    where cdefine10=? ";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter(){
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				StoreInHeadU8 si=storeInHead.get(index);
				ps.setString(1, si.getPurchasecode());
				ps.setString(2, si.getIpurarriveid());
				ps.setString(3, si.getCarVCode());
				
				ps.setString(4, si.getCmemo());
				ps.setString(5, si.getDaRVDate());
				ps.setString(6, si.getCexchName());
				ps.setString(7, si.getItaxRate());
				ps.setString(8, si.getReceivecode());
				
				ps.setString(9, si.getDefine10());
			}
		
			@Override
			public int getBatchSize() {
				return storeInHead.size();
			}
		});
		
	}
	
	
	public int[] updateRdrecordProcureArrivalRdcode(final List<StoreInHeadU8> storeInHead) {
		//String sql="update "+Config.BUSINESSDATABASE+"rdrecord set cSource='采购到货单', ipurarriveid = '"+storeInHead.getIpurarriveid()+"',carVCode='"+storeInHead.getCarVCode()+"' where cdefine10='"+storeInHead.getDefine10()+"'";
		String sql="update "+Config.BUSINESSDATABASE+"rdrecord set cRdCode=? where cdefine10=? ";
		return this.batchUpdate(sql, new BatchPreparedStatementSetter(){
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				StoreInHeadU8 si=storeInHead.get(index);
				ps.setString(1, si.getReceivecode());
				ps.setString(2, si.getDefine10());
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
		
		String sql="update "+Config.BUSINESSDATABASE+"rdrecords set iArrsId =?,  iOriCost=?,iOriTaxCost=?,iOriMoney=?,iOriTaxPrice=?,ioriSum=?,itaxrate=?,   iTaxPrice=?,iSum=?   ,iPOsID=?,iNQuantity=?,bTaxCost=?,cPOID=?,cbarvcode=?,dbarvdate=?   ,cBatchProperty7=?,cBatchProperty8=?,cBatchProperty9=?,cBatchProperty10=?  where cDefine30=? ";
		
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
				
				ps.setString(8, sobu8.getItaxPrice().toString());
				ps.setString(9, sobu8.getIsum().toString());
				
				ps.setString(10, sobu8.getIpOsID());
				ps.setString(11, sobu8.getInQuantity());
				ps.setString(12, sobu8.getBtaxCost());
				ps.setString(13, sobu8.getCpOID());
				ps.setString(14, sobu8.getCbarvcode());
				ps.setString(15, sobu8.getDbarvdate());
				
				ps.setString(16, sobu8.getCbatchProperty7());
				ps.setString(17, sobu8.getCbatchProperty8());
				ps.setString(18, sobu8.getCbatchProperty9());
				ps.setString(19, sobu8.getCbatchProperty10());
				
				ps.setString(20, sobu8.getDefine30());
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
	public int[] updateDispatchListsrds(final List<RdRecords> rdRecords) {
		String sql="update "+Config.BUSINESSDATABASE+"DispatchLists set fOutQuantity=isnull(fOutQuantity,0)+?,fOutNum=isnull(fOutNum,0)+? where idlsid=?";
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter(){
		
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				RdRecords sob=rdRecords.get(index);
				ps.setBigDecimal(1, sob.getIquantity());
				ps.setBigDecimal(2, CommonUtil.BigDecimal2BigDecimal(sob.getInum()));
				ps.setString(3, sob.getIdLsID());
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
		String sql="SELECT mo.moid,mo.moCode,mm.allocateId,mm.free1,mm.sortSeq,bp.invCode,mod.invCode modInvCode,mod.sortSeq modsortSeq FROM "+Config.BUSINESSDATABASE+"mom_order mo "+
					"LEFT JOIN "+Config.BUSINESSDATABASE+"mom_orderdetail mod on mod.moid=mo.moid "+
					"LEFT join "+Config.BUSINESSDATABASE+"mom_moallocate mm on mod.modid=mm.modid "+
					"LEFT JOIN "+Config.BUSINESSDATABASE+"bas_part bp on mm.componentid=bp.partid where "+condition;
		return this.queryForList(sql);
	}
	
	//从用友中获得生产订单信息
	public Map getMomOrderYonYou(String condition){
		String sql="SELECT mo.moid,mo.moCode,mod.sortSeq FROM "+Config.BUSINESSDATABASE+"mom_order mo "+
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
		
		//数据字典用的是iproorderid ,实际验证使用的是ipurorderid
		String sql="update "+Config.BUSINESSDATABASE+"rdrecord set  ipurorderid = ?,cmpocode=?,cSource='生产订单' where cdefine10=? ";
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
		String sql="update "+Config.BUSINESSDATABASE+"rdrecords set iMPoIds=?,iUnitCost=NULL,iPrice=NULL,cmocode=?,imoseq=? where cDefine30=? ";
	
		
		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				StoreInBodyU8 siob=storeInBodyU8.get(index);
				ps.setString(1, siob.getImPoIds());
				ps.setString(2, siob.getCmocode());
				ps.setString(3, siob.getImoseq());
				ps.setString(4, siob.getDefine30());
				
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
	public List<CurrentStock> getAllocationStock(String condition){
		String sql="select autoID,cwhCode,cinvCode,cbatch,iquantity,inum,cfree1,cfree2,foutQuantity,foutNum,dvdate,dmdate,imassDate ,cvmiVenCode from "+Config.BUSINESSDATABASE+"CurrentStock where "+condition;
		System.out.println(sql);
		return this.getJdbcTemplate().query(sql, new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				CurrentStock cs=new CurrentStock();
				cs.setAutoID(rs.getInt("autoID"));
				cs.setCwhCode(rs.getString("cwhCode"));
				cs.setCinvCode(rs.getString("cinvCode"));
				cs.setCbatch(CommonUtil.Obj2String(rs.getObject("cbatch")));
				cs.setIquantity(rs.getBigDecimal("iquantity"));
				cs.setInum(CommonUtil.isNullObject(rs.getObject("inum"))?BigDecimal.valueOf(0):rs.getBigDecimal("inum"));
				cs.setCfree1(CommonUtil.Obj2String(rs.getObject("cfree1")));
				cs.setCfree2(CommonUtil.Obj2String(rs.getObject("cfree2")));
				cs.setFoutQuantity(CommonUtil.isNullObject(rs.getObject("inum"))?BigDecimal.valueOf(0):rs.getBigDecimal("foutQuantity"));
				cs.setFoutNum(CommonUtil.isNullObject(rs.getObject("inum"))?BigDecimal.valueOf(0):rs.getBigDecimal("foutNum"));
				cs.setDvDate(CommonUtil.isNullObject(rs.getObject("dvdate"))?null:Timestamp.valueOf(rs.getString("dvdate")));
				cs.setDmDate(CommonUtil.isNullObject(rs.getObject("dmdate"))?null:Timestamp.valueOf(rs.getString("dmdate")));
				cs.setImassDate(CommonUtil.isNullObject(rs.getObject("imassDate"))?null:Integer.valueOf(rs.getString("imassDate")));
				cs.setCvmivencode(CommonUtil.isNullObject(rs.getObject("cvmiVenCode"))?null:rs.getString("cvmiVenCode"));
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
	public int[] batchUptCurrentStock(final List<CurrentStock> as) {
		
		String sql="update "+Config.BUSINESSDATABASE+"CurrentStock set iQuantity=isnull(iQuantity,0)+?,iNum=isnull(iNum,0)+?,"
				+ " fOutQuantity=isnull(fOutQuantity,0)-?, fInQuantity=isnull(fInQuantity,0)-? where autoid=?";

		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				CurrentStock cs=as.get(index);
				
				ps.setString(1, cs.getIquantity().toPlainString());
				ps.setString(2, CommonUtil.BigDecimal2BigDecimal(cs.getInum()).toPlainString());
				ps.setString(3, CommonUtil.BigDecimal2BigDecimal(cs.getFoutQuantity()).toPlainString());
				ps.setString(4, CommonUtil.BigDecimal2BigDecimal(cs.getFinQuantity()).toPlainString());
				ps.setInt(5, cs.getAutoID());
				
			}
			
			@Override
			public int getBatchSize() {
				return as.size();
			}
		});
		
	}
	
	//批量增加货位现存量
	public int[] batchInsertCurrentStock(final List<CurrentStock> as) {
		
		String sql="insert into "+Config.BUSINESSDATABASE+"CurrentStock(cwhCode,cinvCode,cbatch,iquantity,inum,cfree1,cfree2,ItemId, dVDate  ,dMdate,iMassDate,cMassUnit  ) values(?,?,?,?,?,?,?,? ,? ,?,?,?) ";

		return this.batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				
				CurrentStock cs=as.get(index);
				
				ps.setString(1, cs.getCwhCode());
				ps.setString(2, cs.getCinvCode());
				ps.setString(3, CommonUtil.isNullObject(cs.getCbatch())?"":cs.getCbatch());
				ps.setString(4, cs.getIquantity().toPlainString());
				ps.setString(5, CommonUtil.BigDecimal2BigDecimal(cs.getInum()).toPlainString());
				ps.setString(6, CommonUtil.isNullObject(cs.getCfree1())?null:cs.getCfree1());
				ps.setString(7, CommonUtil.isNullObject(cs.getCfree2())?null:cs.getCfree2());
				ps.setInt(8, index+1);
				ps.setTimestamp(9, CommonUtil.isNullObject(cs.getDvDate())?null:cs.getDvDate());
				ps.setTimestamp(10, cs.getDmDate());
				ps.setString(11, CommonUtil.isNullObject(cs.getImassDate())?null:cs.getImassDate().toString());
				ps.setString(12, CommonUtil.isNullObject(cs.getCmassUnit())?null:cs.getCmassUnit().toString());
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
		sqls[0] = "insert into "+Config.BUSINESSDATABASE+"bom_bom(BomId,BomType,Version,VersionDesc,VersionEffDate,VersionEndDate,CreateDate,CreateTime,Define14"
				+ ",CreateUser,ModifyDate,ModifyTime,ModifyUser,RelsDate,RelsTime,RelsUser) values"
				+ "('"+bom_bom.getBomId()+"','"+bom_bom.getBomType()+"','"+bom_bom.getVersion()+"','"+bom_bom.getVersionDesc()+"',"+this.formatValue(bom_bom.getVersionEffDate())+""
						+ ","+this.formatValue(bom_bom.getVersionEndDate())+","+this.formatValue(bom_bom.getCreateTime())+","+this.formatValue(bom_bom.getCreateTime())+",'"+bom_bom.getLugGoodsId()+"'"
						+ ",'"+bom_bom.getCreateUser()+"',"+this.formatValue(bom_bom.getModifyTime())+","+this.formatValue(bom_bom.getModifyTime())+",'"+bom_bom.getModifyUser()+"'"
						+","+this.formatValue(bom_bom.getRelsTime())+","+this.formatValue(bom_bom.getRelsTime())+",'"+bom_bom.getRelsUser()+"'"	
						+ ")";
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
		System.out.println(sql);
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
	public Map getVoucherHistory(String condition) {
		String sql="select cNumber from "+Config.BUSINESSDATABASE+"VoucherHistory where  "+condition;
		return this.queryForMap(sql);
	}
	
	/**
	 * @category 修改编码
	 *2016 2016年5月14日下午1:39:52
	 *Map
	 *宋银海
	 */
	public int uptVoucherHistory(String cnumber,String cardNumber) {
		String sql="update "+Config.BUSINESSDATABASE+"VoucherHistory set cNumber='"+cnumber+"' where CardNumber='"+cardNumber+"'";
		return this.update(sql);
	}
	
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
		String sql="insert into "+Config.BUSINESSDATABASE+"TransVouch(id,ctVCode,dtVDate,coWhCode,ciWhCode,cmaker,vt_id,cVerifyPerson  ,cIRdCode,cORdCode ,dVerifyDate ) values(?,?,?,?,?,?,?,?  ,?,? ,?) ";
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
				
				ps.setString(9, t.getCiRdCode());
				ps.setString(10, t.getCoRdCode());
				
				ps.setTimestamp(11, t.getDverifyDate());
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
		String sql="insert into "+Config.BUSINESSDATABASE+"TransVouchs(autoID,id,ctVCode,cinvCode,itVQuantity,itVNum,cassUnit,iinvexchrate,irowno,ctVBatch,cFree1,cFree2,"
				+ " coutposcode,cinposcode,RdsID,ddisDate ,dMadeDate,iMassDate,cMassUnit) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ,?,?,?) ";
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
				ps.setTimestamp(16, t.getDdisDate());
				ps.setTimestamp(17, t.getDmadeDate());
				
//				ps.setInt(18, t.getImassDate());
//				ps.setInt(19, t.getCmassUnit());
				
				ps.setString(18, CommonUtil.isNullObject(t.getImassDate())?null: t.getImassDate().toString() );
				ps.setString(19, CommonUtil.isNullObject(t.getCmassUnit())?null:t.getCmassUnit().toString() );
				
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
		String sql="insert into "+Config.BUSINESSDATABASE+"InvPosition(rdID,rdsID,cwhCode,cposCode,cinvCode,cbatch,cfree1,cfree2,iquantity,"
				+ "inum,chandler,ddate,brdFlag ,dVDate  ,dMadeDate,iMassDate,cMassUnit ,cvmivencode) values(?,?,?,?,?,?,?,?,?,?,?,?,?,? ,?,?,? ,?) ";
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
				ps.setTimestamp(14, CommonUtil.isNullObject(ip.getDvDate())?null:ip.getDvDate());
				ps.setTimestamp(15, ip.getDmadeDate());
				ps.setString(16, CommonUtil.isNullObject(ip.getImassDate())?null:ip.getImassDate().toString());
				ps.setString(17, CommonUtil.isNullObject(ip.getCmassUnit())?null:ip.getCmassUnit().toString());
				ps.setString(18, CommonUtil.isNullObject(ip.getCvmivencode())?null:ip.getCvmivencode().toString());
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
	public int[] addRdRecord(final List<RdRecord> rds){
		String sql="insert into "+Config.BUSINESSDATABASE+"RdRecord(id,brdFlag,cvouchType,cbusType,csource,cbusCode,cwhCode,ddate,ccode,crdCode,ccusCode,cvenCode,"
				+ "cmaker,vt_id,cdefine10,cHandler,cpsPcode,cmPoCode,ipurorderid  ,corderCode,caRVCode,ipurarriveid  ,cdLCode,iarriveid ,iexchRate ,cdepcode ,dveriDate, dnmaketime,dnverifytime  ,cPTCode,dARVDate,iTaxRate,cExch_Name) "
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ,?,?,?  ,?,?,? ,? ,? ,?,? ,?,?,?,?) ";
		System.out.println(sql);
		return this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				RdRecord rd=rds.get(index);
				ps.setInt(1,rd.getId());
				ps.setInt(2, rd.getBrdFlag());
				ps.setString(3, rd.getCvouchType());
				ps.setString(4, rd.getCbusType());
				ps.setString(5, rd.getCsource());
				ps.setString(6, rd.getCbusCode());
				ps.setString(7, rd.getCwhCode());
				ps.setTimestamp(8, rd.getDdate());
				ps.setString(9, rd.getCcode());
				ps.setString(10, CommonUtil.isNullObject(rd.getCrdCode())?null:rd.getCrdCode());
				ps.setString(11, CommonUtil.isNullObject(rd.getCcusCode())?null:rd.getCcusCode());
				ps.setString(12, CommonUtil.isNullObject(rd.getCvenCode())?null:rd.getCvenCode());
				ps.setString(13, rd.getCmaker());
				ps.setInt(14, rd.getVtid());
				ps.setString(15, rd.getCdefine10());
				ps.setString(16, rd.getcHandler());
				
				ps.setString(17, CommonUtil.isNullObject(rd.getCpsPcode())?null:rd.getCpsPcode());
				ps.setString(18, CommonUtil.isNullObject(rd.getCmPoCode())?null:rd.getCmPoCode());
				ps.setString(19, CommonUtil.isNullObject(rd.getIpurorderid())?null:rd.getIpurorderid());
				
				ps.setString(20, CommonUtil.isNullObject(rd.getCorderCode())?null:rd.getCorderCode());
				ps.setString(21, CommonUtil.isNullObject(rd.getCaRVCode())?null:rd.getCaRVCode());
				ps.setString(22, CommonUtil.isNullObject(rd.getIpurarriveid())?null:rd.getIpurarriveid());
				
				ps.setString(23, CommonUtil.isNullObject(rd.getCdLCode())?null:rd.getCdLCode());
				ps.setString(24, CommonUtil.isNullObject(rd.getIarriveid())?null:rd.getIarriveid());
				
				ps.setString(25, CommonUtil.isNullObject(rd.getIexchRate())?null:rd.getIexchRate());
				ps.setString(26, CommonUtil.isNullObject(rd.getCdepCode())?null:rd.getCdepCode());
				ps.setTimestamp(27, rd.getDveriDate());
				
				ps.setTimestamp(28, rd.getDnmaketime());
				ps.setTimestamp(29, rd.getDnverifytime());
				ps.setString(30, rd.getCpTCode());
				
				ps.setString(31, rd.getDaRVDate());
				ps.setString(32, rd.getItaxRate());
				ps.setString(33, rd.getCexchName());
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
	public int[] addRdRecords(final List<RdRecords> rrss){
		String sql="insert into "+Config.BUSINESSDATABASE+"RdRecords(autoID,id,cinvCode,iquantity,inum,iunitCost,iprice,cbatch,cfree1,"
				+ " cfree2,itrIds,cposition,cdefine30,ioMoDID,ioMoMID,comcode,invcode  ,iarrsId,cbarvcode,cpoID,imPoIds,idLsID,cbdlcode,dVDate,cdefine23 ,dMadeDate,iMassDate,cMassUnit ,cmocode,imoseq ,bcosting,bvmiUsed,cvmivencode  ,iNQuantity,dbarvdate,irowno,itaxrate) "
				+ " values(?,?,?,?,?,?,?,?,?,?,?,?,? ,?,?,?,? ,?,?,?,?,?,?,?,? ,?,?,? ,?,? ,?,?,?  ,?,?,?,?) ";
		System.out.println(sql);
		return this.getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter() {
			
			@Override
			public void setValues(PreparedStatement ps, int index) throws SQLException {
				RdRecords rr=rrss.get(index);
				ps.setInt(1, rr.getAutoID());
				ps.setInt(2, rr.getId());
				ps.setString(3, rr.getCinvCode());
				ps.setBigDecimal(4, rr.getIquantity());
				ps.setBigDecimal(5, rr.getInum());
				ps.setBigDecimal(6, rr.getIunitCost());
				ps.setBigDecimal(7, rr.getIprice());
				ps.setString(8, CommonUtil.isNullObject(rr.getCbatch())?null:rr.getCbatch());
				ps.setString(9, CommonUtil.isNullObject(rr.getCfree1())?null:rr.getCfree1());
				ps.setString(10, CommonUtil.isNullObject(rr.getCfree2())?null:rr.getCfree2());
				ps.setInt(11, rr.getItrIds());
				ps.setString(12, rr.getCposition());
				ps.setString(13, rr.getCdefine30());
				
				ps.setString(14, CommonUtil.isNullObject(rr.getIoMoDID())?null:rr.getIoMoDID());
				ps.setString(15, CommonUtil.isNullObject(rr.getIoMoMID())?null:rr.getIoMoMID());
				ps.setString(16, CommonUtil.isNullObject(rr.getComcode())?null:rr.getComcode());
				ps.setString(17, CommonUtil.isNullObject(rr.getInvcode())?null:rr.getInvcode());
				
				ps.setString(18, CommonUtil.isNullObject(rr.getIarrsId())?null:rr.getIarrsId());
				ps.setString(19, CommonUtil.isNullObject(rr.getCbarvcode())?null:rr.getCbarvcode());
				ps.setString(20, CommonUtil.isNullObject(rr.getCpoID())?null:rr.getCpoID());
				
				ps.setString(21, CommonUtil.isNullObject(rr.getImPoIds())?null:rr.getImPoIds());
				
				ps.setString(22, CommonUtil.isNullObject(rr.getIdLsID())?null:rr.getIdLsID());
				ps.setString(23, CommonUtil.isNullObject(rr.getCbdlcode())?null:rr.getCbdlcode());
				ps.setTimestamp(24, CommonUtil.isNullObject(rr.getDvdate())?null:rr.getDvdate());
				
				ps.setString(25, CommonUtil.isNullObject(rr.getCdefine23())?null:rr.getCdefine23() );
				
				ps.setTimestamp(26, CommonUtil.isNullObject(rr.getDmadeDate())?null:rr.getDmadeDate());
				ps.setString(27, CommonUtil.isNullObject(rr.getImassDate())?null: rr.getImassDate().toString() );
				ps.setString(28, CommonUtil.isNullObject(rr.getCmassUnit())?null:rr.getCmassUnit().toString() );
				
				ps.setString(29, CommonUtil.isNullObject(rr.getCmocode())?null:rr.getCmocode().toString() );
				ps.setString(30, CommonUtil.isNullObject(rr.getImoseq())?null:rr.getImoseq().toString() );
				
				ps.setInt(31, 1);//单据是否核算
				ps.setInt(32, rr.getBvmiUsed().intValue());
				ps.setString(33, CommonUtil.isNullObject(rr.getCvmivencode())?null:rr.getCvmivencode().toString() );
				
				
				ps.setBigDecimal(34, rr.getInQuantity());
				ps.setString(35, rr.getDbarvdate() );
				ps.setString(36, CommonUtil.isNullObject(rr.getIrowno())?null:rr.getIrowno().toString());
				ps.setString(37, rr.getItaxrate());
				
			}
			
			@Override
			public int getBatchSize() {
				return rrss.size();
			}
		});
	}
	
	
	//从用友中获得发货单信息
	public List<Map> getOMMOMaterials(String condition){
		String sql="select omn.moid,omn.ccode,omn.cvenCode,omd.cInvCode,omd.cDepCode,  "
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
	
	//获得编号规则
	public Map getVoucherNumber(String cardNumber){
		String sql="select prefix1,prefix1Len,prefix1Rule,"
				+ " prefix2,prefix2Len,prefix2Rule,"
				+ " prefix3,prefix3Len,prefix3Rule,"
				+ " glide,glideLen,glideRule from "+Config.BUSINESSDATABASE+"VoucherNumber where CardNumber='"+cardNumber+"'";
		return this.queryForMap(sql);
	}
	
	//获得满足流水条件的数目
	public int getLotnumbersequencegist(){
		String sql=" select count(1) from "+Config.BUSINESSDATABASE+"lotnumbersequencegist where cinvcode='all' and cinvtype='2' and isnull(csequencegistrule,'')='0{##}' "+ 
				" and isnull(csequencegist,'')='"+DateUtil.getCurrDate(DateUtil.FORMAT_FOUR)+"{##}' and isnull(iGrade,'')='' ";
		return this.queryForInt(sql);
	}
	
	//添加流水记录
	public int addLotnumbersequencegist(){
		String sql="insert into "+Config.BUSINESSDATABASE+"lotnumbersequencegist (cinvcode,cinvtype,csequencegistrule,csequencegist,iGrade,cnumber) "+  
					" values ('all','2','0{##}','"+DateUtil.getCurrDate(DateUtil.FORMAT_FOUR)+"{##}','','2') ";
		return this.update(sql);
	}
	
	//获得流水记录
	public Map getLotnumbersequencegistMap(){
		String sql="select cnumber from "+Config.BUSINESSDATABASE+"lotnumbersequencegist where cinvcode='all' and cinvtype='2' and isnull(csequencegistrule,'')='0{##}' "+
					" and isnull(csequencegist,'')='"+DateUtil.getCurrDate(DateUtil.FORMAT_FOUR)+"{##}' and isnull(iGrade,'')='' ";
		System.out.println(sql);
		return this.queryForMap(sql);
	}
	
	//修改流水记录
	public int updLotnumbersequencegist(int v){
		String sql="update "+Config.BUSINESSDATABASE+"lotnumbersequencegist set cnumber="+v+" where cinvcode='all' and cinvtype='2' and isnull(csequencegistrule,'')='0{##}' "+
					"and isnull(csequencegist,'')='"+DateUtil.getCurrDate(DateUtil.FORMAT_FOUR)+"{##}' and isnull(iGrade,'')=''"; 
		return this.update(sql);
	}
	
}
