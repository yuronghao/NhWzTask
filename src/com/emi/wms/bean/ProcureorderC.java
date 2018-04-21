package com.emi.wms.bean;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

import java.math.BigDecimal;
import java.sql.Timestamp;

@EmiTable(name="WM_ProcureOrder_C")
public class ProcureorderC {
    @EmiColumn(name="pk",increment=true)
    private Integer pk;

    @EmiColumn(name="gid",ID=true)
    private String gid;

    @EmiColumn(name="procureOrderUid")
    private  String procureOrderUid;

    @EmiColumn(name="number")
    private BigDecimal number;//数量

    @EmiColumn(name="receiveNumber")
    private BigDecimal receiveNumber;//已到货数量

    @EmiColumn(name="putinNumber")
    private BigDecimal putinNumber;//已入库数量



    private String billIdentity;//单据编码或者id

    private Timestamp billIDate;//单据日期

    private String cbusTypeName;//业务类型名称

    private String maindefine1;//主表自定义项1

    private String bodydefine22;//子表自定义项1

    private Integer iverifystateex;//单据审核状态

    public String getBillIdentity() {
        return billIdentity;
    }

    public void setBillIdentity(String billIdentity) {
        this.billIdentity = billIdentity;
    }

    public String getCbusTypeName() {
        return cbusTypeName;
    }

    public void setCbusTypeName(String cbusTypeName) {
        this.cbusTypeName = cbusTypeName;
    }

    public String getMaindefine1() {
        return maindefine1;
    }

    public void setMaindefine1(String maindefine1) {
        this.maindefine1 = maindefine1;
    }

    public String getBodydefine22() {
        return bodydefine22;
    }

    public void setBodydefine22(String bodydefine22) {
        this.bodydefine22 = bodydefine22;
    }

    public Integer getIverifystateex() {
        return iverifystateex;
    }

    public void setIverifystateex(Integer iverifystateex) {
        this.iverifystateex = iverifystateex;
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

    public String getProcureOrderUid() {
        return procureOrderUid;
    }

    public void setProcureOrderUid(String procureOrderUid) {
        this.procureOrderUid = procureOrderUid;
    }

    public BigDecimal getNumber() {
        return number;
    }

    public void setNumber(BigDecimal number) {
        this.number = number;
    }

    public BigDecimal getReceiveNumber() {
        return receiveNumber;
    }

    public void setReceiveNumber(BigDecimal receiveNumber) {
        this.receiveNumber = receiveNumber;
    }

    public BigDecimal getPutinNumber() {
        return putinNumber;
    }

    public void setPutinNumber(BigDecimal putinNumber) {
        this.putinNumber = putinNumber;
    }

    public Timestamp getBillIDate() {
        return billIDate;
    }

    public void setBillIDate(Timestamp billIDate) {
        this.billIDate = billIDate;
    }
}
