package com.emi.wms.bean;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

import java.sql.Timestamp;

@EmiTable(name="WM_ProcureOrder")
public class Procureorder {//采购订单

    @EmiColumn(name="pk",increment=true)
    private Integer pk;

    @EmiColumn(name="gid",ID=true)
    private String gid;

    @EmiColumn(name="supplierUid")
    private String supplierUid;//供应商uid


    @EmiColumn(name="billstate")
    private String billstate;

    @EmiColumn(name="billCode")
    private String billCode;

    @EmiColumn(name="billdate")
    private Timestamp billdate;

    @EmiColumn(name="idForSynchro")
    private String idForSynchro;

    public String getIdForSynchro() {
        return idForSynchro;
    }

    public void setIdForSynchro(String idForSynchro) {
        this.idForSynchro = idForSynchro;
    }

    public Integer getPk() {
        return pk;
    }

    public void setPk(Integer pk) {
        this.pk = pk;
    }

    public String getGid() {
        return gid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public String getSupplierUid() {
        return supplierUid;
    }

    public void setSupplierUid(String supplierUid) {
        this.supplierUid = supplierUid;
    }

    public String getBillstate() {
        return billstate;
    }

    public void setBillstate(String billstate) {
        this.billstate = billstate;
    }

    public String getBillCode() {
        return billCode;
    }

    public void setBillCode(String billCode) {
        this.billCode = billCode;
    }

    public Timestamp getBilldate() {
        return billdate;
    }

    public void setBilldate(Timestamp billdate) {
        this.billdate = billdate;
    }
}
