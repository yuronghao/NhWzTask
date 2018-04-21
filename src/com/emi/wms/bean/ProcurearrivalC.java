package com.emi.wms.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name="WM_ProcureArrival_C")
public class ProcurearrivalC {
	
	@EmiColumn(name="pk",increment=true)
    private Integer pk;

	@EmiColumn(name="gid",ID=true)
    private String gid;

	@EmiColumn(name="procurearrivaluid")
    private String procurearrivaluid;

	@EmiColumn(name="number")
    private BigDecimal number;//主数量

	@EmiColumn(name="needcheck")
    private Integer needcheck;

	@EmiColumn(name="assistNumber")
    private BigDecimal assistNumber;//辅数量
	
	@EmiColumn(name="idForSynchro")
	private String idForSynchro;
	
	
	@EmiColumn(name="autoidForSynchro")
	private String autoidForSynchro;

	private String billIdentity;//单据编码或者id
	
	private Timestamp billIDate;//单据日期
	
	private String cbusTypeName;//业务类型名称
	
	private String maindefine1;//主表自定义项1
	
	private String bodydefine22;//子表自定义项1
	
	private Integer iverifystateex;//单据审核状态 
	
	
	
	public String getCbusTypeName() {
		return cbusTypeName;
	}

	public void setCbusTypeName(String cbusTypeName) {
		this.cbusTypeName = cbusTypeName;
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

	public String getProcurearrivaluid() {
		return procurearrivaluid;
	}

	public void setProcurearrivaluid(String procurearrivaluid) {
		this.procurearrivaluid = procurearrivaluid;
	}

	public BigDecimal getNumber() {
		return number;
	}

	public void setNumber(BigDecimal number) {
		this.number = number;
	}

	public Integer getNeedcheck() {
		return needcheck;
	}

	public void setNeedcheck(Integer needcheck) {
		this.needcheck = needcheck;
	}

	public String getIdForSynchro() {
		return idForSynchro;
	}

	public void setIdForSynchro(String idForSynchro) {
		this.idForSynchro = idForSynchro;
	}

	public String getBillIdentity() {
		return billIdentity;
	}

	public void setBillIdentity(String billIdentity) {
		this.billIdentity = billIdentity;
	}

	public Timestamp getBillIDate() {
		return billIDate;
	}

	public void setBillIDate(Timestamp billIDate) {
		this.billIDate = billIDate;
	}


	public String getMaindefine1() {
		return maindefine1;
	}

	public void setMaindefine1(String maindefine1) {
		this.maindefine1 = maindefine1;
	}

	public String getAutoidForSynchro() {
		return autoidForSynchro;
	}

	public void setAutoidForSynchro(String autoidForSynchro) {
		this.autoidForSynchro = autoidForSynchro;
	}

	public BigDecimal getAssistNumber() {
		return assistNumber;
	}

	public void setAssistNumber(BigDecimal assistNumber) {
		this.assistNumber = assistNumber;
	}


   
    
}