package com.emi.web.u8101.dao;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.sys.init.Config;
import com.emi.web.u8101.bean.*;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Map;

public class DaoU8101 extends BaseDao implements Serializable {
    private static final long serialVersionUID = 1069210670058724899L;

    public List<Map> getProcureorderCYonYou(String condition) {
//iCost本币无税单价                                                                   iMoney本币无税金额           iTaxPrice      本币税额    iSum 本币价税合计
        //iOriCost 原币无税单价  iOriTaxCost 原币含税单价   iOriMoney 原币无税金额  iOriTaxPrice   原币税额    iOriSum  原币价税合计
        String sql  = " SELECT po.cDepCode,po.cVenCode,po.cPOID,po.iverifystateex,po.dPODate,po.POID,po.cdefine1,po.nflat,po.iTaxRate,po.cPersonCode,po.cMemo,  "+
                " pd.iQuantity, CAST(pd.iQuantity as DECIMAL(28,6))-CAST(pd.iReceivedQTY as DECIMAL(28,6)) as stillQuantity, pd.iTaxPrice ,pd.iSum , "+
                " pd.iUnitPrice ,pd.iMoney ,pd.iTax , "+
                " pd.iNatSum ,pd.iNatUnitPrice ,pd.iNatMoney ,pd.iNatTax , "+
                " pd.iReceivedQTY,pd.dArriveDate ,pd.ID ,pd.cInvCode ,pd.iQuotedPrice ,pd.iPerTaxRate, "+
                " po.cPTCode,po.cexch_name,po.iTaxRate as mainitaxrate  " +
                " from "+ Config.BUSINESSDATABASE+"PO_Pomain po  "+
                " left join "+ Config.BUSINESSDATABASE+"PO_Podetails pd on po.POID = pd.POID  where" +condition;
        return this.queryForList(sql);

    }

    public List<CurrentStock> getAllocationStock(String condition) {
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
                cs.setInum(CommonUtil.isNullObject(rs.getObject("inum"))? BigDecimal.valueOf(0):rs.getBigDecimal("inum"));
                cs.setCfree1(CommonUtil.Obj2String(rs.getObject("cfree1")));
                cs.setCfree2(CommonUtil.Obj2String(rs.getObject("cfree2")));
                cs.setFoutQuantity(CommonUtil.isNullObject(rs.getObject("inum"))?BigDecimal.valueOf(0):rs.getBigDecimal("foutQuantity"));
                cs.setFoutNum(CommonUtil.isNullObject(rs.getObject("inum"))?BigDecimal.valueOf(0):rs.getBigDecimal("foutNum"));
                cs.setDvDate(CommonUtil.isNullObject(rs.getObject("dvdate"))?null: Timestamp.valueOf(rs.getString("dvdate")));
                cs.setDmDate(CommonUtil.isNullObject(rs.getObject("dmdate"))?null:Timestamp.valueOf(rs.getString("dmdate")));
                cs.setImassDate(CommonUtil.isNullObject(rs.getObject("imassDate"))?null:Integer.valueOf(rs.getString("imassDate")));
                cs.setCvmivencode(CommonUtil.isNullObject(rs.getObject("cvmiVenCode"))?null:rs.getString("cvmiVenCode"));
                return cs;
            }

        });
    }

    public int[] updateRdrecordProcureArrival(List<StoreInHeadU8101> storeInHead) {
        //String sql="update "+Config.BUSINESSDATABASE+"rdrecord set cSource='采购到货单', ipurarriveid = '"+storeInHead.getIpurarriveid()+"',carVCode='"+storeInHead.getCarVCode()+"' where cdefine10='"+storeInHead.getDefine10()+"'";
        String sql="update "+Config.BUSINESSDATABASE+"rdrecord set cSource='采购到货单',cPTCode=?, ipurarriveid=?,carVCode=?  ,cMemo=?,dARVDate=?,cExch_Name=?,iTaxRate=?,cRdCode=?    where cdefine10=? ";
        return this.batchUpdate(sql, new BatchPreparedStatementSetter(){

            @Override
            public void setValues(PreparedStatement ps, int index) throws SQLException {

                StoreInHeadU8101 si=storeInHead.get(index);
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

    public int[] updateRdrecordsProcureArrival(List<StoreInBodyU8101> storeInBodys) {
        //String sql="update "+Config.BUSINESSDATABASE+"rdrecords set iArrsId =?,  iCost=?,iMoney=?,iTaxPrice=?,iSum=?,  iOriCost=?,iOriTaxCost=?,iOriMoney=?,iOriTaxPrice=?,iOriSum=?    where cDefine30=? ";

        String sql="update "+Config.BUSINESSDATABASE+"rdrecords set iArrsId =?,  iOriCost=?,iOriTaxCost=?,iOriMoney=?,iOriTaxPrice=?,ioriSum=?,itaxrate=?,   iTaxPrice=?,iSum=?   ,iPOsID=?,iNQuantity=?,bTaxCost=?,cPOID=?,cbarvcode=?,dbarvdate=?   ,cBatchProperty7=?,cBatchProperty8=?,cBatchProperty9=?,cBatchProperty10=?  where cDefine30=? ";

        return this.batchUpdate(sql, new BatchPreparedStatementSetter(){

            @Override
            public void setValues(PreparedStatement ps, int index) throws SQLException {

                StoreInBodyU8101 sobu8=storeInBodys.get(index);
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
                return storeInBodys.size();

            }
        });


    }

    public int[] updatePuAppVouchs(List<StoreInBodyU8101> storeInBodys) {
        String sql="update "+Config.BUSINESSDATABASE+"PU_ArrivalVouchs set fValidInQuan=isnull(fValidInQuan,0)+?,fValidInNum=isnull(fValidInNum ,0)+? where Autoid=?";

        return this.batchUpdate(sql, new BatchPreparedStatementSetter(){

            @Override
            public void setValues(PreparedStatement ps, int index) throws SQLException {

                StoreInBodyU8101 sob=storeInBodys.get(index);
                ps.setBigDecimal(1, BigDecimal.valueOf(Double.parseDouble(sob.getQuantity())));
                ps.setBigDecimal(2, CommonUtil.isNullObject(sob.getNumber())?BigDecimal.valueOf(0):BigDecimal.valueOf(Double.parseDouble(sob.getNumber())));
                ps.setInt(3, (Integer.parseInt(sob.getIarrsId())));
            }

            @Override
            public int getBatchSize() {
                return storeInBodys.size();
            }
        });
    }

    public int[] addRdRecord(List<RdRecord> rds) {
        String sql="insert into "+Config.BUSINESSDATABASE+"RdRecord01(id,brdFlag,cvouchType,cbusType,csource,cbusCode,cwhCode,ddate,ccode,crdCode,ccusCode,cvenCode,"
                + "cmaker,vt_id,cdefine10,cHandler,ipurorderid  ,corderCode,caRVCode,ipurarriveid  ,cdLCode,iarriveid ,iexchRate ,cdepcode ,dveriDate, dnmaketime,dnverifytime  ,cPTCode,dARVDate,iTaxRate,cExch_Name) "
                + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ,?,?  ,?,?,? ,? ,? ,?,? ,?,?,?,?,?) ";
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

//                ps.setString(17, CommonUtil.isNullObject(rd.getCpsPcode())?null:rd.getCpsPcode());
//                ps.setString(17, CommonUtil.isNullObject(rd.getCmPoCode())?null:rd.getCmPoCode());
                ps.setString(17, CommonUtil.isNullObject(rd.getIpurorderid())?null:rd.getIpurorderid());

                ps.setString(18, CommonUtil.isNullObject(rd.getCorderCode())?null:rd.getCorderCode());
                ps.setString(19, CommonUtil.isNullObject(rd.getCaRVCode())?null:rd.getCaRVCode());
                ps.setString(20, CommonUtil.isNullObject(rd.getIpurarriveid())?null:rd.getIpurarriveid());

                ps.setString(21, CommonUtil.isNullObject(rd.getCdLCode())?null:rd.getCdLCode());
                ps.setString(22, CommonUtil.isNullObject(rd.getIarriveid())?null:rd.getIarriveid());

                ps.setString(23, CommonUtil.isNullObject(rd.getIexchRate())?null:rd.getIexchRate());
                ps.setString(24, CommonUtil.isNullObject(rd.getCdepCode())?null:rd.getCdepCode());
                ps.setTimestamp(25, rd.getDveriDate());

                ps.setTimestamp(26, rd.getDnmaketime());
                ps.setTimestamp(27, rd.getDnverifytime());
                ps.setString(28, rd.getCpTCode());

                ps.setString(29, rd.getDaRVDate());
                ps.setString(30, rd.getItaxRate());
                ps.setString(31, rd.getCexchName());
            }

            @Override
            public int getBatchSize() {
                return rds.size();
            }
        });


    }

    public int[] addRdRecords(List<RdRecords> rrss) {
        String sql="insert into "+Config.BUSINESSDATABASE+"RdRecords01(autoID,id,cInvCode,iquantity,inum,iunitCost,iprice,cbatch,cfree1,"
                + " cfree2,cposition,cdefine30,ioMoDID,ioMoMID,comcode  ,iarrsId,cbarvcode,cpoID,dVDate,cdefine23 ,dMadeDate,iMassDate,cMassUnit  ,bcosting,bvmiUsed, " +
                " cvmivencode  ,iNQuantity,dbarvdate,irowno,itaxrate,      iAPrice,iPOsID,fACost,chVencode,iOriTaxCost,iOriCost,iOriMoney," +
                " iOriTaxPrice,iTaxPrice,iSum,bTaxCost,ioriSum) "
                + " values(?,?,?,?,?,?,?,?,?,?,?,? ,?,?,?,? ,?,?,?,?,?,?,?,?,?,?,?,? ,?,?,? ,?,?,?,?,?,?,?,?,?,?,?) ";
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
//                ps.setInt(11, rr.getItrIds());
                ps.setString(11, rr.getCposition());
                ps.setString(12, rr.getCdefine30());

                ps.setString(13, CommonUtil.isNullObject(rr.getIoMoDID())?null:rr.getIoMoDID());
                ps.setString(14, CommonUtil.isNullObject(rr.getIoMoMID())?null:rr.getIoMoMID());
                ps.setString(15, CommonUtil.isNullObject(rr.getComcode())?null:rr.getComcode());
//                ps.setString(16, CommonUtil.isNullObject(rr.getInvcode())?null:rr.getInvcode());

                ps.setString(16, CommonUtil.isNullObject(rr.getIarrsId())?null:rr.getIarrsId());
                ps.setString(17, CommonUtil.isNullObject(rr.getCbarvcode())?null:rr.getCbarvcode());
                ps.setString(18, CommonUtil.isNullObject(rr.getCpoID())?null:rr.getCpoID());

//                ps.setString(21, CommonUtil.isNullObject(rr.getImPoIds())?null:rr.getImPoIds());

//                ps.setString(22, CommonUtil.isNullObject(rr.getIdLsID())?null:rr.getIdLsID());
//                ps.setString(23, CommonUtil.isNullObject(rr.getCbdlcode())?null:rr.getCbdlcode());
                ps.setTimestamp(19, CommonUtil.isNullObject(rr.getDvdate())?null:rr.getDvdate());

                ps.setString(20, CommonUtil.isNullObject(rr.getCdefine23())?null:rr.getCdefine23() );

                ps.setTimestamp(21, CommonUtil.isNullObject(rr.getDmadeDate())?null:rr.getDmadeDate());
                ps.setString(22, CommonUtil.isNullObject(rr.getImassDate())?null: rr.getImassDate().toString() );
                ps.setString(23, CommonUtil.isNullObject(rr.getCmassUnit())?null:rr.getCmassUnit().toString() );

//                ps.setString(29, CommonUtil.isNullObject(rr.getCmocode())?null:rr.getCmocode().toString() );
//                ps.setString(30, CommonUtil.isNullObject(rr.getImoseq())?null:rr.getImoseq().toString() );

                ps.setInt(24, 1);//单据是否核算
                ps.setInt(25, 0);
                ps.setString(26, CommonUtil.isNullObject(rr.getCvmivencode())?null:rr.getCvmivencode().toString() );


                ps.setBigDecimal(27, rr.getInQuantity());
                ps.setString(28, rr.getDbarvdate() );
                ps.setString(29, CommonUtil.isNullObject(rr.getIrowno())?null:rr.getIrowno().toString());
                ps.setString(30, rr.getItaxrate());



                ps.setBigDecimal(31, rr.getiAPrice());
                ps.setString(32, rr.getiPOsID());
                ps.setBigDecimal(33, rr.getfACost());
                ps.setString(34, rr.getChVencode());
                ps.setBigDecimal(35, rr.getiOriTaxCost());
                ps.setBigDecimal(36, rr.getiOriCost());
                ps.setBigDecimal(37, rr.getiOriMoney());
                ps.setBigDecimal(38, rr.getiOriTaxPrice());
                ps.setBigDecimal(39, rr.getiTaxPrice());
                ps.setBigDecimal(40, rr.getiSum());
                ps.setInt(41, rr.getbTaxCost());
                ps.setBigDecimal(42, rr.getIoriSum());
            }

            @Override
            public int getBatchSize() {
                return rrss.size();
            }
        });

    }

    public int[] backFillPOPodetails(List<RdRecords> goods) {
        String sql="update "+Config.BUSINESSDATABASE+"PO_Podetails set iReceivedQTY=ISNULL(CAST(iReceivedQTY as DECIMAL(28,6)), 0)+? ,iReceivedMoney = iUnitPrice*(ISNULL(CAST(iReceivedQTY as DECIMAL(28,6)), 0)+?)  where ID=?";

        System.err.println("============"+sql);
        return this.batchUpdate(sql, new BatchPreparedStatementSetter(){

            @Override
            public void setValues(PreparedStatement ps, int index) throws SQLException {

                RdRecords sob=goods.get(index);
                ps.setBigDecimal(1, sob.getIquantity());
                ps.setBigDecimal(2, sob.getIquantity());
                ps.setInt(3, (Integer.parseInt(sob.getiPOsID())));
            }

            @Override
            public int getBatchSize() {
                return goods.size();
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

    public int[] addRdRecord11(List<RdRecord> rds) {
        String sql="insert into "+Config.BUSINESSDATABASE+"RdRecord11(id,brdFlag,cvouchType,cbusType,csource,cbusCode,cwhCode,ddate,ccode,crdCode,ccusCode,cvenCode,"
                + "cmaker,vt_id,cdefine10,cHandler,ipurorderid  ,corderCode,caRVCode  ,cdLCode ,iexchRate ,cdepcode ,dveriDate, dnmaketime,dnverifytime  , " +
                " cPTCode,dARVDate,cExch_Name,cMemo) "
                + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ,?,?  ,?,?,? ,? ,? ,?,? ,?,?,?) ";

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

//                ps.setString(17, CommonUtil.isNullObject(rd.getCpsPcode())?null:rd.getCpsPcode());
//                ps.setString(17, CommonUtil.isNullObject(rd.getCmPoCode())?null:rd.getCmPoCode());
                ps.setString(17, CommonUtil.isNullObject(rd.getIpurorderid())?null:rd.getIpurorderid());

                ps.setString(18, CommonUtil.isNullObject(rd.getCorderCode())?null:rd.getCorderCode());
                ps.setString(19, CommonUtil.isNullObject(rd.getCaRVCode())?null:rd.getCaRVCode());

                ps.setString(20, CommonUtil.isNullObject(rd.getCdLCode())?null:rd.getCdLCode());

                ps.setString(21, CommonUtil.isNullObject(rd.getIexchRate())?null:rd.getIexchRate());
                ps.setString(22, CommonUtil.isNullObject(rd.getCdepCode())?null:rd.getCdepCode());
                ps.setTimestamp(23, rd.getDveriDate());

                ps.setTimestamp(24, rd.getDnmaketime());
                ps.setTimestamp(25, rd.getDnverifytime());
                ps.setString(26, rd.getCpTCode());

                ps.setString(27, rd.getDaRVDate());
                ps.setString(28, rd.getCexchName());
                ps.setString(29, rd.getcMemo());
            }

            @Override
            public int getBatchSize() {
                return rds.size();
            }
        });



    }

    public int[] addRdRecords11(List<RdRecords> rrss) {
        String sql="insert into "+Config.BUSINESSDATABASE+"RdRecords11(autoID,id,cInvCode,iquantity,inum,iunitCost,iprice,cbatch,cfree1,"
                + " cfree2,cposition,cdefine30,ioMoDID,ioMoMID,comcode  ,dVDate,cdefine23 ,dMadeDate,iMassDate,cMassUnit  ,bcosting,bvmiUsed,cvmivencode  ,iNQuantity,irowno) "
                + " values(?,?,?,?,?,?,?,?,?,?,?,? ,?,?,?,? ,?,?,?,?,? ,?,?,? ,?) ";
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
//                ps.setInt(11, rr.getItrIds());
                ps.setString(11, rr.getCposition());
                ps.setString(12, rr.getCdefine30());

                ps.setString(13, CommonUtil.isNullObject(rr.getIoMoDID())?null:rr.getIoMoDID());
                ps.setString(14, CommonUtil.isNullObject(rr.getIoMoMID())?null:rr.getIoMoMID());
                ps.setString(15, CommonUtil.isNullObject(rr.getComcode())?null:rr.getComcode());
//                ps.setString(16, CommonUtil.isNullObject(rr.getInvcode())?null:rr.getInvcode());

//                ps.setString(16, CommonUtil.isNullObject(rr.getIarrsId())?null:rr.getIarrsId());
//                ps.setString(17, CommonUtil.isNullObject(rr.getCbarvcode())?null:rr.getCbarvcode());
//                ps.setString(18, CommonUtil.isNullObject(rr.getCpoID())?null:rr.getCpoID());

//                ps.setString(21, CommonUtil.isNullObject(rr.getImPoIds())?null:rr.getImPoIds());

//                ps.setString(22, CommonUtil.isNullObject(rr.getIdLsID())?null:rr.getIdLsID());
//                ps.setString(23, CommonUtil.isNullObject(rr.getCbdlcode())?null:rr.getCbdlcode());
                ps.setTimestamp(16, CommonUtil.isNullObject(rr.getDvdate())?null:rr.getDvdate());

                ps.setString(17, CommonUtil.isNullObject(rr.getCdefine23())?null:rr.getCdefine23() );

                ps.setTimestamp(18, CommonUtil.isNullObject(rr.getDmadeDate())?null:rr.getDmadeDate());
                ps.setString(19, CommonUtil.isNullObject(rr.getImassDate())?null: rr.getImassDate().toString() );
                ps.setString(20, CommonUtil.isNullObject(rr.getCmassUnit())?null:rr.getCmassUnit().toString() );

//                ps.setString(29, CommonUtil.isNullObject(rr.getCmocode())?null:rr.getCmocode().toString() );
//                ps.setString(30, CommonUtil.isNullObject(rr.getImoseq())?null:rr.getImoseq().toString() );

                ps.setInt(21, 1);//单据是否核算
                ps.setInt(22, 0);
                ps.setString(23, CommonUtil.isNullObject(rr.getCvmivencode())?null:rr.getCvmivencode().toString() );


                ps.setBigDecimal(24, rr.getInQuantity());
//                ps.setString(28, rr.getDbarvdate() );
                ps.setString(25, CommonUtil.isNullObject(rr.getIrowno())?null:rr.getIrowno().toString());
//                ps.setString(30, rr.getItaxrate());
            }

            @Override
            public int getBatchSize() {
                return rrss.size();
            }
        });
    }
}
