package com.emi.wms.synchro.dao;

import com.emi.common.dao.BaseDao;
import com.emi.common.util.CommonUtil;
import com.emi.sys.init.Config;
import com.emi.wms.bean.*;
import com.emi.wms.util.Constants;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class SynchroDaoU8101 extends BaseDao {
    public Map getOrgGid(){
        String sql="select gid from AA_Org order by pk desc ";
        return this.queryForMap(sql);
    }

    public Map getSobGid(){
        String sql="select gid from MES_WM_AccountingInform order by pk desc ";
        return this.queryForMap(sql);
    }


    public int deleteInventory() {
        String sql="delete from AA_Goods where goodsCode not in (select cInvCode from "+ Config.BUSINESSDATABASE+"Inventory )";
        return this.update(sql);
    }

    public int addInventory() {
        String sql="insert into AA_Goods(gid,goodsCode,goodsName,goodsStandard,unitForSynchro,sortGidForSynchro,bInvBach,defWareHouseForSynchro,cassComUnitForSynchro,goodsBarCode,cstComUnitForSynchro, isInvQuality,massDate,cfree1,cfree2,  invWeight,weightRate,minPackNum,grossWeight) "+
                "SELECT newid(),cInvCode,cInvName,cInvStd,cComUnitCode,cInvCCode,bInvBatch,cDefWareHouse,cAssComUnitCode,cInvCode,cSTComUnitCode, bInvQuality,iMassDate,bfree1,bfree2,  iInvWeight,flength,fWidth,fHeight from "+Config.BUSINESSDATABASE+"Inventory "+
                "where 1=1  and cInvCode not in (select goodsCode from AA_Goods) ";
        return this.update(sql);
    }

    public int updInventory() {
        String sql="update AA_Goods set goodsName=cInvName,goodsStandard=cInvStd,unitForSynchro=cComUnitCode,sortGidForSynchro=cInvCCode,"
                + " bInvBach=it.bInvBatch,defWareHouseForSynchro=it.cDefWareHouse,cassComUnitForSynchro=it.cAssComUnitCode,cstComUnitForSynchro=it.cSTComUnitCode,goodsBarCode=goodsCode,"
                + " isInvQuality=bInvQuality,massDate=iMassDate,cfree1=bfree1,cfree2=bfree2, "
                + " invWeight=iInvWeight,weightRate=flength,minPackNum=fWidth,grossWeight=fHeight "+
                " from AA_Goods gs LEFT JOIN "+Config.BUSINESSDATABASE+"Inventory it on gs.goodsCode=it.cInvCode "+
                " where isnull(goodsName,'')<>it.cInvName OR "+
                " isnull(goodsStandard,'')<>it.cInvStd OR "+
                " isnull(unitForSynchro,'')<>it.cComUnitCode or "+
                " isnull(sortGidForSynchro,'')<>it.cInvCCode OR "+
                " isnull(bInvBach,'')<>it.bInvBatch OR "+
                " isnull(gs.defWareHouseForSynchro,'')<>it.cDefWareHouse or "+
                " isnull(gs.cassComUnitForSynchro,'')<>it.cAssComUnitCode or "
                + " isnull(gs.cstComUnitForSynchro,'')<>it.cSTComUnitCode or "
                + " isnull(gs.invWeight,0)<>isnull(it.iInvWeight,0) or "
                + " isnull(gs.weightRate,0)<>isnull(it.flength,0) or "
                + " isnull(gs.grossWeight,0)<>isnull(it.fHeight,0) or "
                + " isnull(gs.minPackNum,0)<>isnull(it.fWidth,0) or "
                + " isnull(gs.isInvQuality,0)<>isnull(it.bInvQuality,0) or "
                + " isnull(gs.massDate,0)<>isnull(it.iMassDate,0) or "
                + " isnull(gs.goodsBarCode,'')='' or "
                + " isnull(gs.cfree1,0)<> bfree1 or"
                + " isnull(gs.cfree2,0)<> bfree2 ";
        return this.update(sql);
    }

    /**
     * @category 更新存货档案相关字段
     *int
     * cassComUnitCode
     */
    public int updInventoryInfor() {
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
     *int
     */
    public int updInventoryOrgSob(String orgGid, String sobGid) {
        String sql="update AA_Goods set orggid='"+orgGid+"', sobgId='"+sobGid+"' where isnull(sobgId,'')='' or isnull(orggid,'')='' ";
        return this.update(sql);
    }

    public int deleteInventoryClass() {
        String sql="delete from classify where styleGid='03' and isnull(codeForSynchro,'') not in (select cInvCCode from "+Config.BUSINESSDATABASE+"InventoryClass )";
        System.out.println(sql);
        return this.update(sql);
    }

    public int addInventoryClass() {
        String sql="insert into classify(gid,classificationName,styleGid,codeForSynchro,layerForSynchro) "+
                "SELECT newid(),cInvCName,'03',cInvCCode,iInvCGrade from "+Config.BUSINESSDATABASE+"InventoryClass "+
                "where 1=1  and cInvCCode not in (select codeForSynchro from classify where styleGid='03') ";
        return this.update(sql);
    }

    public int updInventoryClass() {
        String sql="update classify set classificationName=cInvCName,layerForSynchro=iInvCGrade "+
                "from classify cf LEFT JOIN "+Config.BUSINESSDATABASE+"InventoryClass ic on cf.codeForSynchro=ic.cInvCCode "+
                "where 1=1 and styleGid='03' and "
                + " (classificationName != cInvCName or layerForSynchro!=iInvCGrade )";
        return this.update(sql);
    }

    public int updInventoryClassParentGid() {
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

    public int updInventoryClassOrgSob(String orgGid, String sobGid) {
        String sql="update classify set orgid='"+orgGid+"', sobId='"+sobGid+"' where isnull(sobId,'')='' or isnull(orgid,'')='' ";
        return this.update(sql);
    }

    public int deleteUnit() {
        String sql="delete from unit where isnull(unitCode,'') not in (select cComunitCode from "+Config.BUSINESSDATABASE+"ComputationUnit ) ";
        return this.update(sql);
    }

    public int addUnit() {
        String sql="insert into unit(gid,unitCode,unitName) "+
                " select newid(),cComunitCode,cComUnitName  FROM "+Config.BUSINESSDATABASE+"ComputationUnit "+
                " where  cComunitCode not in (select unitCode from unit)";

        return this.update(sql);
    }

    public int updUnit() {
        String sql="update unit set unitName=cu.cComUnitName from unit u "+
                "left join "+Config.BUSINESSDATABASE+"ComputationUnit cu on u.unitCode=cu.cComunitCode "+
                "where isnull(u.unitName,'')<>cu.cComUnitName ";
        return this.update(sql);
    }

    public int updUnitOrgSob(String orgGid, String sobGid) {
        String sql="update unit set orgid='"+orgGid+"', sobId='"+sobGid+"' where isnull(sobId,'')='' or isnull(orgid,'')='' ";
        return this.update(sql);
    }

    public int deleteWareHouse() {
        String sql="delete from AA_WareHouse where whCode not in (select cWhCode from "+Config.BUSINESSDATABASE+"Warehouse ) ";
        return this.update(sql);
    }

    public int addWareHouse() {
        String sql="insert into AA_WareHouse(gid,whCode,whName,whPos,bproxyWh) select newid(),cWhCode,cWhName,bWhPos,bproxyWh FROM "+Config.BUSINESSDATABASE+"Warehouse "+
                "where cWhCode not in (select whCode from AA_WareHouse) ";
        return this.update(sql);
    }

    public int updWareHouse() {
        String sql="update AA_WareHouse set whName=w.cWhName,whPos=w.bWhPos,bproxyWh=w.bproxyWh from AA_WareHouse aw "+
                "left join "+Config.BUSINESSDATABASE+"Warehouse w on aw.whCode=w.cWhCode "+
                "where isnull(aw.whName,'')<>w.cWhName OR "+
                "isnull(aw.whPos,'')<>w.bWhPos or "+
                "isnull(aw.bproxyWh,-1)<>w.bproxyWh ";
        return this.update(sql);
    }

    public int updWareHouseOrgSob(String orgGid, String sobGid) {
        String sql="update AA_WareHouse set orggid='"+orgGid+"', sobgId='"+sobGid+"' where isnull(sobgId,'')='' or isnull(orggid,'')='' ";
        return this.update(sql);
    }

    public int addGoodsAllocation() {
        String sql="insert into AA_GoodsAllocation(gid,code,name,whForSynchro,allocationBarCode,posEnd,posGrade)  select newid(),cPosCode,cPosName,cWhCode,isnull(cbarcode,cPosCode),bposEnd,iposGrade  FROM "+Config.BUSINESSDATABASE+"Position "+
                "where cPosCode not in (select code from AA_GoodsAllocation);";
        return this.update(sql);
    }

    public int updGoodsAllocation() {
        String sql="update AA_GoodsAllocation set name=p.cPosName,whForSynchro=p.cWhCode,allocationBarCode=isnull(p.cbarcode,p.cPosCode),"
                + " posEnd=bposEnd,posGrade=iposGrade from  AA_GoodsAllocation ga "+
                "left join "+Config.BUSINESSDATABASE+"Position p on ga.code=p.cPosCode "+
                "where isnull(ga.name,'')<>p.cPosName OR "+
                "isnull(ga.whForSynchro,'')<>p.cWhCode or "
                + " isnull(ga.posEnd,0)<>p.bposEnd or"
                + " isnull(ga.posGrade,0)<>p.iposGrade or "
                + " isnull(ga.allocationBarCode,'')<>isnull(p.cbarcode,p.cPosCode) ";
        return this.update(sql);
    }

    public int updGoodsAllocationInfor() {
        String sql="UPDATE AA_GoodsAllocation "+
                "SET whUid = wh.gid "+
                "FROM AA_GoodsAllocation ga "+
                "LEFT JOIN AA_WareHouse wh ON "+
                "ga.whForSynchro=wh.whCode "+
                "WHERE  isnull(ga.whUid,'')<>isnull(wh.gid,'') and isnull(whForSynchro,'')<>'' ";
        return this.update(sql);
    }

    public int updGoodsAllocationOrgSob(String orgGid, String sobGid) {
        String sql="update AA_GoodsAllocation set orggid='"+orgGid+"', sobgId='"+sobGid+"' where isnull(sobgId,'')='' or isnull(orggid,'')='' ";
        return this.update(sql);
    }

    public int deletePurchaseType() {
        String sql="delete from AA_PurchaseType where cPTCode not in (select cPTCode from "+Config.BUSINESSDATABASE+"PurchaseType ) ";
        return this.update(sql);

    }

    public int addPurchaseType() {
        String sql="insert into AA_PurchaseType(gid,cPTCode,cPTName,cRdCode,bDefault,bPFDefault,bPTMPS_MRP)  select newid(),cPTCode,cPTName,cRdCode,bDefault,bPFDefault,bPTMPS_MRP  FROM "+Config.BUSINESSDATABASE+"PurchaseType "+
                "where cPTCode not in (select cPTCode from AA_PurchaseType);";
        return this.update(sql);
    }

    public int updPurchaseType() {
        String sql="update AA_PurchaseType   set cPTName = pu.cPTName " +
                "from AA_PurchaseType p " +
                "left join "+Config.BUSINESSDATABASE+"PurchaseType pu on p.cPTCode = pu.cPTCode " +
                "where isnull(p.cPTCode, '') <> '' and  isnull(p.cPTName, '') <> isnull(pu.cPTName, '')";
        return this.update(sql);

    }

    public int updPurchaseTypeOrgSob(String orgGid, String sobGid) {
        String sql="update AA_PurchaseType set orggid='"+orgGid+"', sobgId='"+sobGid+"' where isnull(sobgId,'')='' or isnull(orggid,'')='' ";
        return this.update(sql);

    }

    public int deleteRdStyle() {
        String sql="delete from YM_RdStyle where codeForSynchro not in (select crdcode from "+Config.BUSINESSDATABASE+"Rd_Style ) ";
        return this.update(sql);

    }

    public int addRdStyle() {
        String sql="insert into YM_RdStyle(gid,crdCode,crdName,irdFlag,irdGrade,brdEnd,codeForSynchro)  select newid(),cRdCode,cRdName,bRdFlag,iRdGrade,bRdEnd,cRdCode  FROM "+Config.BUSINESSDATABASE+"Rd_Style "+
                "where  cRdCode not in (select crdCode from YM_RdStyle)";
        return this.update(sql);
    }

    public int updRdStyle() {
        String sql="update YM_RdStyle set crdCode=rs.cRdCode,crdName=rs.cRdName,irdFlag=rs.bRdFlag,irdGrade=rs.iRdGrade,brdEnd=rs.bRdEnd,codeForSynchro=rs.cRdCode  from YM_RdStyle rd "+
                "left join "+Config.BUSINESSDATABASE+"Rd_Style rs on rd.codeForSynchro=rs.cRdCode "+
                "where isnull(rd.crdName,'')<>rs.cRdName OR "+
                "isnull(rd.irdFlag,'')<>rs.bRdFlag or "
                + " isnull(rd.irdGrade,'')<>rs.iRdGrade or"
                + " isnull(rd.brdEnd,'')<>rs.bRdEnd ";
        return this.update(sql);
    }

    public int updRdStyleOrgSob(String orgGid, String sobGid) {
        String sql="update YM_RdStyle set orggid='"+orgGid+"', sobgId='"+sobGid+"' where isnull(sobgId,'')='' or isnull(orggid,'')='' ";
        return this.update(sql);
    }

    public int deletePerson() {
        String sql="delete from AA_Person where perCode not in (select cPsn_Num from "+Config.BUSINESSDATABASE+"hr_hi_person ) ";
        return this.update(sql);
    }

    public int addPerson() {
        String sql="insert into AA_Person(gid,perCode,perName,depCodeForSynchro)  select newid(),cPsn_Num,cPsn_Name,cDept_num  FROM "+Config.BUSINESSDATABASE+"hr_hi_person "+
                "where  cPsn_Num not in (select perCode from AA_Person)";
        return this.update(sql);
    }

    public int updPerson() {
        String sql="update AA_Person set perName=p.cPsn_Name,depCodeForSynchro=p.cDept_num,barcode='P'+ap.perCode from AA_Person ap "+
                "left join "+Config.BUSINESSDATABASE+"hr_hi_person p on ap.perCode=p.cPsn_Num "+
                "where isnull(ap.perName,'')<>p.cPsn_Name OR "+
                "isnull(ap.depCodeForSynchro,'')<>p.cDept_num or "
                + "isnull(ap.barcode,'')<>'P'+ap.perCode  " ;
        return this.update(sql);
    }

    public int updPersonInfor() {
        String sql="UPDATE AA_Person "+
                "SET depGid = d.gid "+
                "FROM AA_Person p "+
                "LEFT JOIN AA_Department d ON "+
                "p.depCodeForSynchro=d.depCode "+
                "WHERE  isnull(p.depGid,'')<>isnull(d.gid,'') ";
        return this.update(sql);
    }


    public int updPersonOrgSob(String orgGid, String sobGid) {
        String sql="update AA_Person set orggid='"+orgGid+"', sobgId='"+sobGid+"' where isnull(sobgId,'')='' or isnull(orggid,'')='' ";
        return this.update(sql);
    }

    public int deleteDepartment() {
        String sql="delete from AA_Department where depCode not in (select cDepCode from "+Config.BUSINESSDATABASE+"Department )";
        return this.update(sql);
    }

    public int addDepartment() {
        String sql="insert into AA_Department(gid,depCode,depName,last,layer) select newid(),cDepCode,cDepName,bDepEnd,iDepGrade FROM "+Config.BUSINESSDATABASE+"Department "+
                " where cDepCode not in (select depCode from AA_Department);";
        return this.update(sql);

    }

    public int uptDepartment() {
        String sql="update AA_Department set depName=d.cDepName,last=d.bDepEnd,layer=d.iDepGrade from AA_Department ad "+
                "left join "+Config.BUSINESSDATABASE+"Department d on ad.depCode=d.cDepCode "+
                "where isnull(ad.depName,'')<>d.cDepName OR "+
                "isnull(ad.last,'')<>d.bDepEnd OR "+
                "isnull(ad.layer,'')<>d.iDepGrade;";
        return this.update(sql);

    }

    public int uptDepartmentParentGid() {
        String sql="UPDATE AA_Department "+
                "SET depParentUid = dAnother.gid "+
                "FROM AA_Department d "+
                "LEFT JOIN (SELECT gid,depCode,layer from AA_Department)dAnother ON "+
                "SUBSTRING(d.depCode, 1, LEN(dAnother.depCode )) = dAnother.depCode "+
                "AND d.layer=dAnother.layer+1 "+
                "WHERE  isnull(d.depParentUid,'')<>isnull(dAnother.gid,'') ";
        return this.update(sql);

    }

    public int updDepartmentOrgSob(String orgGid, String sobGid) {
        String sql="update AA_Department set orggid='"+orgGid+"', sobgId='"+sobGid+"' where isnull(sobgId,'')='' or isnull(orggid,'')='' ";
        return this.update(sql);
    }

    public int deleteProvider() {
        String sql="delete from AA_Provider_Customer where pcCode not in (select cVenCode  from "+Config.BUSINESSDATABASE+"Vendor ) and flagForSynchro='p' ";
        return this.update(sql);
    }

    public int addProvider() {
        String sql="insert into AA_Provider_Customer(gid,pcCode,pcName,flagForSynchro)  select newid(),cVenCode,cVenName,'p'  FROM "+Config.BUSINESSDATABASE+"Vendor "+
                "where  cVenCode not in (select pcCode from AA_Provider_Customer where flagForSynchro='p' ) ";
        return this.update(sql);
    }

    public int updProvider() {
        String sql="update AA_Provider_Customer set pcName=v.cVenName from AA_Provider_Customer pc "+
                "left join "+Config.BUSINESSDATABASE+"Vendor v on pc.pcCode=v.cVenCode "+
                "where isnull(pc.pcName,'')<>v.cVenName "
                + " and pc.flagForSynchro='p' ";
        return this.update(sql);
    }

    public int updProviderOrgSob(String orgGid, String sobGid) {
        String sql="update AA_Provider_Customer set orgid='"+orgGid+"', sobId='"+sobGid+"' where isnull(sobId,'')='' or isnull(orgid,'')='' ";
        return this.update(sql);
    }

    public int deleteProviderClass() {
        String sql="delete from AA_ProviderClass where cVCCode not in (select cVCCode  from "+Config.BUSINESSDATABASE+"VendorClass ) ";
        return this.update(sql);
    }

    public int addProviderClass() {
        String sql="insert into AA_ProviderClass(gid,cVCCode,cVCName,iVCGrade,bVCEnd)  select newid(),cVCCode,cVCName,iVCGrade,bVCEnd  FROM "+Config.BUSINESSDATABASE+"VendorClass "+
                "where  cVCCode not in (select cVCCode from "+Config.BUSINESSDATABASE+"VendorClass  ) ";
        return this.update(sql);
    }

    public int uptProviderClass() {
        String sql="update AA_ProviderClass set cVCName=v.cVCName from AA_ProviderClass pc "+
                "left join "+Config.BUSINESSDATABASE+"VendorClass v on pc.cVCCode=v.cVCCode "+
                "where isnull(pc.cVCName,'')<>v.cVCName ";
        return this.update(sql);
    }

    public int updProviderClassOrgSob(String orgGid, String sobGid) {
        String sql="update AA_ProviderClass set orgid='"+orgGid+"', sobId='"+sobGid+"' where isnull(sobId,'')='' or isnull(orgid,'')='' ";
        return this.update(sql);
    }


    public List<ProcureorderC> getProcureorderC3(String condition) {
        String sql="select ps.ID,iQuantity,bGsp,p.cPOID ,p.dPODate ,p.cDefine1,p.iverifystateex,p.cbusType,ps.cDefine22 from "+Config.BUSINESSDATABASE+"PO_Podetails ps "
                + " left join "+Config.BUSINESSDATABASE+"PO_Pomain p on ps.POID=p.POID where 1=1 "+condition;

        return this.getJdbcTemplate().query(sql, new RowMapper(){

            @Override
            public Object mapRow(ResultSet rs, int arg1) throws SQLException {
                ProcureorderC pc=new ProcureorderC();
                pc.setNumber(rs.getBigDecimal("iQuantity"));
                pc.setBillIdentity(rs.getString("cPOID"));
                pc.setMaindefine1(CommonUtil.isNullObject(rs.getObject("cDefine1"))?"":rs.getString("cDefine1"));
                pc.setBillIDate(rs.getTimestamp("dPODate"));
                pc.setIverifystateex(rs.getInt("iverifystateex"));
                pc.setCbusTypeName(rs.getString("cbusType"));
                pc.setBodydefine22(CommonUtil.Obj2String(rs.getObject("cDefine22")));
                return pc;
            }

        });

    }

    public List<ProcureorderC> getProcureorderC1(String condition) {
        String sql="select pc.gid,pc.procureOrderUid,pc.number,pc.idForSynchro,p.billCode,p.define1 from WM_ProcureOrder_C pc "
                + " left join WM_ProcureOrder p on pc.procureOrderUid=p.gid where 1=1 "+condition;


        return this.getJdbcTemplate().query(sql, new RowMapper(){

            @Override
            public Object mapRow(ResultSet rs, int arg1) throws SQLException {
                ProcureorderC pc=new ProcureorderC();
                pc.setBillIdentity(rs.getString("billCode"));
                pc.setMaindefine1(CommonUtil.isNullObject(rs.getObject("define1"))?"":rs.getString("define1"));
                return pc;

            }

        });


    }

    public List<ProcureorderC> getProcureorderC2(String condition) {
        String sql="select pc.gid,pc.procurearrivaluid,pc.number,pc.needcheck,pc.assistNumber,pc.idForSynchro,p.billCode,p.define1 from WM_ProcureArrival_C pc "
                + " left join WM_ProcureArrival p on pc.procurearrivaluid=p.gid "
                + " left join "+Config.BUSINESSDATABASE+"PU_ArrivalVouchs paus8 on pc.autoidForSynchro=paus8.autoid where 1=1 "+condition;

        return this.getJdbcTemplate().query(sql, new RowMapper(){

            @Override
            public Object mapRow(ResultSet rs, int arg1) throws SQLException {
                ProcureorderC pc=new ProcureorderC();
                pc.setBillIdentity(rs.getString("billCode"));
                pc.setMaindefine1(CommonUtil.isNullObject(rs.getObject("define1"))?"":rs.getString("define1"));
                return pc;
            }
        });


    }

    public List<Procureorder> getPl(String condition) {
        String sql="SELECT pv.cPOID ,pv.iverifystateex ,pv.dPODate ,pv.POID  from WM_ProcureOrder p "
                + " left join "+Config.BUSINESSDATABASE+"PO_Pomain pv on p.autoidForSynchro=pv.POID where 1=1 "+condition;

        return this.getJdbcTemplate().query(sql, new RowMapper(){

            @Override
            public Object mapRow(ResultSet rs, int arg1) throws SQLException {
                Procureorder p=new Procureorder();
                p.setIdForSynchro(rs.getString("POID"));
                p.setBillCode(rs.getString("cPOID"));
                p.setBillstate(rs.getString("iverifystateex"));
                p.setBilldate(rs.getTimestamp("dPODate"));
                return p;
            }

        });

    }

    public int deleteProOrder() {
        String sql="delete from WM_ProcureOrder where autoidForSynchro not in (select POID  from "+Config.BUSINESSDATABASE+"PO_Pomain  )";
        return this.update(sql);
    }

    public int addProOrder() {
        String sql="insert into WM_ProcureOrder(gid,depCodeForSynchro,supplierCodeForSynchro,billCode,billState,billDate," +
                " autoidForSynchro,define1,  exchangeRate,rate,personCodeForSynchro,notes,stateForSynchro,ptcodeForSynchro,currency," +
                " recordPersonName,recordDate,recordTime,auditPersonName,auditDate,auditTime,auditState" +
                ") "+
                " SELECT newid(),cDepCode,cVenCode,cPOID,iverifystateex,dPODate,POID,cdefine1,nflat,iTaxRate,cPersonCode,cMemo,iverifystateex,cPTCode,cexch_name," +
                " cMaker,cmaketime,cmaketime,cVerifier,cAuditDate,cAuditTime,iverifystateex   " +
                "   from  "+Config.BUSINESSDATABASE+"PO_Pomain "+
                " where POID not in (select autoidForSynchro from WM_ProcureOrder) ";
        return this.update(sql);
    }

    public int updProOrder() {
        String sql =  " update WM_ProcureOrder set depCodeForSynchro=cDepCode,supplierCodeForSynchro=cVenCode, " +
                " billState=iverifystateex,billDate=dPODate,define1=cDefine1,billcode=cPOID, exchangeRate = nflat , " +
                " rate=iTaxRate,personCodeForSynchro=cPersonCode,notes=cMemo,stateForSynchro=iverifystateex,ptcodeForSynchro = cPTCode ,currency=cexch_name, " +
                " recordPersonName = cMaker,recordDate=cmaketime,recordTime= cmaketime,auditPersonName=cVerifier,auditDate =cAuditDate, auditTime=cAuditTime,auditState=iverifystateex "+
                "  from WM_ProcureOrder pa  " +
                " LEFT JOIN "+Config.BUSINESSDATABASE+"PO_Pomain   pau8 on pa.autoidForSynchro=pau8.POID " +
                " where isnull(pa.depCodeForSynchro,'')<>isnull(pau8.cDepCode,'')  or " +
                " isnull(pa.supplierCodeForSynchro,'')<>isnull(pau8.cVenCode,'') or " +
                " isnull(pa.billState,'')<>isnull(pau8.iverifystateex,'') or " +
                " isnull(pa.billDate,'')<>isnull(pau8.dPODate,'') or " +
                " isnull(pa.define1,'')<>isnull(pau8.cDefine1,'') or " +
                " isnull(pa.exchangeRate,0)<>isnull(pau8.nflat,0) or " +
                " isnull(pa.rate,0)<>isnull(pau8.iTaxRate,0) or " +
                " isnull(pa.personCodeForSynchro,'')<>isnull(pau8.cPersonCode,'') or " +
                " isnull(pa.notes,'')<>isnull(pau8.cMemo,'') or  " +
                " isnull(pa.ptcodeForSynchro,'')<>isnull(pau8.cPTCode,'') or  " +

                " isnull(pa.recordPersonName,'')<>isnull(pau8.cMaker,'') or  " +
                " isnull(pa.recordDate,'')<>isnull(pau8.cmaketime,'') or  " +
                " isnull(pa.recordTime,'')<>isnull(pau8.cmaketime,'') or  " +
                " isnull(pa.auditPersonName,'')<>isnull(pau8.cVerifier,'') or  " +
                " isnull(pa.auditDate,'')<>isnull(pau8.cAuditDate,'') or  " +
                " isnull(pa.auditTime,'')<>isnull(pau8.cAuditTime,'') or  " +
                " isnull(pa.auditState,'')<>isnull(pau8.iverifystateex,'') or  " +


                " isnull(pa.billcode,'')<>isnull(pau8.cPOID,'') " ;
        return this.update(sql);

    }

    public int updProOrderInfor() {
        String sql =  " UPDATE WM_ProcureOrder " +
                " SET departmentUid = d.gid,supplierUid=pc.gid,personUid=pn.gid,procureType = pt.gid " +
                "  FROM WM_ProcureOrder p " +
                " LEFT JOIN AA_Department d ON p.depCodeForSynchro=d.depCode " +
                " left join AA_Provider_Customer pc on p.supplierCodeForSynchro=pc.pcCode " +
                " left join aa_person pn on p.personCodeForSynchro=pn.perCode " +
                " left join AA_PurchaseType pt on p.ptcodeForSynchro = pt.cPTCode "+
                " WHERE  (isnull(p.departmentUid,'')<>isnull(d.gid,'')  " +
                " or isnull(p.supplierUid,'')<>isnull(pc.gid,'') " +
                " or isnull(p.personUid,'')<>isnull(pn.gid,'')  " +
                " or isnull(p.procureType,'')<>isnull(pt.gid,'') ) "+
                " and pc.flagForSynchro='p'  " ;
        return this.update(sql);

    }

    public int deleteProOrderC() {
        String sql="delete from WM_ProcureOrder_C where autoidForSynchro not in (select ID  from "+Config.BUSINESSDATABASE+"PO_Podetails  )";
        return this.update(sql);
    }

    public int addProOrderC() {
       String sql =  " insert into WM_ProcureOrder_C(gid,number,originalTaxPrice,originalTaxMoney, " +
                " originalNotaxPrice,originalNotaxMoney,originalTax, " +
                " localTaxMoney,localNotaxPrice,localNotaxMoney,localTax, " +
                " putinNumber,planAOG,autoidForSynchro,idForSynchro,goodsCodeForSynchro,iQuotedPrice,localTaxPrice) " +
                " select newid(),iQuantity,iTaxPrice ,iSum , " +
                " iUnitPrice ,iMoney ,iTax , " +
                " iNatSum ,iNatUnitPrice ,iNatMoney ,iNatTax , " +
                " CAST(iReceivedQTY as DECIMAL(28,6)),dArriveDate ,ID ,POID ,cInvCode ,iQuotedPrice  ,iTaxPrice" +
                "   from "+Config.BUSINESSDATABASE+"PO_Podetails " +
                " where id not in (select autoidForSynchro from WM_ProcureOrder_C) " ;

        return this.update(sql);
    }

    public int updProOrderC() {
        String sql = " update WM_ProcureOrder_C set goodsCodeForSynchro=cInvCode,number=iQuantity, " +
                " originalTaxPrice=iTaxPrice,originalTaxMoney=iSum, " +
                " originalNotaxPrice=iUnitPrice,originalNotaxMoney=iMoney,originalTax=iTax, " +
                " localTaxMoney=iNatSum,localNotaxPrice=iNatUnitPrice,localNotaxMoney=iNatMoney,localTax=iNatTax,  " +
                " putinNumber=CAST(iReceivedQTY as DECIMAL(28,6)),planAOG = paus8.dArriveDate,iQuotedPrice = paus8.iQuotedPrice ,localTaxPrice = iTaxPrice " +
                " from WM_ProcureOrder_C pac LEFT JOIN "+Config.BUSINESSDATABASE+"PO_Podetails paus8 on pac.autoidForSynchro=paus8.id " +
                " where isnull(pac.goodsCodeForSynchro,'')<>isnull(paus8.cInvCode,'')  or " +
                "  isnull(pac.number,0)<>isnull(paus8.iQuantity,0) or  " +
                " 				 isnull(pac.originalNotaxPrice,0)<>isnull(paus8.iUnitPrice,0) or  " +
                " 				 isnull(pac.originalNotaxMoney,0)<>isnull(paus8.iMoney,0) or  " +
                " 				 isnull(pac.localNotaxPrice,0)<>isnull(paus8.iNatUnitPrice,0) or  " +
                " 				 isnull(pac.localNotaxMoney,0)<>isnull(paus8.iNatMoney,0) or  " +
                " 				 isnull(CAST(pac.putinNumber as DECIMAL(28,6)),0)<>isnull(CAST(paus8.iReceivedQTY as DECIMAL(28,6)),0) or  " +
                " 				 isnull(pac.planAOG,0)<>isnull(paus8.dArriveDate,0) or  " +
                " isnull(pac.iQuotedPrice,0)<>isnull(paus8.iQuotedPrice,0)  or " +
        " isnull(pac.localTaxPrice,0)<>isnull(paus8.iTaxPrice,0)  " ;
        return this.update(sql);

    }

    public int updProOrderCInfor() {
        String sql= " UPDATE WM_ProcureOrder_C   " +
                " SET goodsUid = gs.gid,  " +
                " procureOrderUid=p.gid  " +
                " FROM WM_ProcureOrder_C pc  " +
                " LEFT JOIN AA_Goods gs ON pc.goodsCodeForSynchro=gs.goodsCode  " +
                " left join WM_ProcureOrder p on pc.idForSynchro=p.autoidForSynchro  " +
                " WHERE  isnull(pc.goodsUid,'')<>isnull(gs.gid,'') " +
                " or isnull(pc.procureOrderUid,'')='' " ;

        return this.update(sql);
    }

    public int updProOrderOrgSob(String orgGid, String sobGid) {
        String sql="update WM_ProcureOrder set orggid='"+orgGid+"', sobgId='"+sobGid+"' where isnull(sobgId,'')='' or isnull(orggid,'')='' ";
        return this.update(sql);
    }

    public int deleteTask(String condition) {
        String sql="delete from wm_task where "+condition;
        return this.update(sql);

    }

    public boolean createTask(List<Task> tasks) {
        return this.emiInsert(tasks);
    }

    public int updateTaskInfor() {
        String sql="update WM_Task SET taskTypeUid=bt.gid,taskName=bt.billName from WM_Task wt "
                + " LEFT JOIN WM_BillType bt ON wt.taskTypeCodeForSynchro=bt.billCode "
                + " WHERE isnull(wt.taskTypeUid,'')='' ";
        return this.update(sql);
    }

    public int updateTaskProOrderBillGid() {
        String sql="update WM_Task SET billGid=pa.gid from WM_Task wt "
                + " LEFT JOIN WM_ProcureOrder pa ON wt.billIdentityForSynchro=pa.billCode "
                + " WHERE wt.taskTypeCodeForSynchro in ('"+ Constants.TASKTYPE_CGRK+"','"+Constants.TASKTYPE_CGZJ+"','"+Constants.TASKTYPE_CGTHCK+"','"+Constants.TASKTYPE_WWCPRK+"','"+Constants.TASKTYPE_WWCPZJ+"')"
                + " and isnull(wt.billGid,'')='' ";
        return this.update(sql);
    }

    public int deleProOrderInvalidTask() {
        String sql =  " update WM_Task set state=2 where billGid in " +
                " ( " +
                " 	SELECT  gid from WM_ProcureOrder where gid not in ( " +
                " 		SELECT procureOrderUid from WM_ProcureOrder_C where abs(number) <> abs(isnull(putinNumber,0))  " +
                " 	) " +
                " ) " +
                " and WM_Task.state=0 " ;
        return this.update(sql);
    }

    public int addUser() {
        String sql="insert into YM_User(gid,userCode,userName)  select newid(),'P'+cUser_Id,cUser_Name  FROM "+Config.BUSINESSDATABASE+"UA_User "+
                "where 'p'+cUser_Id not in (select userCode from YM_User) ";
        return this.update(sql);
    }

    public int updUser() {
        String sql="update YM_User set userName=u.cUser_Name from YM_User yu "+
                "left join "+Config.BUSINESSDATABASE+"UA_User u on yu.userCode=u.cUser_Id "+
                "where isnull(yu.userName,'')<>u.cUser_Name ";
        return this.update(sql);
    }

    public int updUserOrgSob(String orgGid, String sobGid) {
        String sql="update YM_User set orggid='"+orgGid+"', sobgId='"+sobGid+"' where isnull(sobgId,'')='' or isnull(orggid,'')='' ";
        return this.update(sql);
    }
}
