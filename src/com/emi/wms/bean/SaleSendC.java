package com.emi.wms.bean;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name="WM_SaleSend_C")
public class SaleSendC {
	
	@EmiColumn(name="pk",increment=true)
    private Integer pk;

	@EmiColumn(name="gid",ID=true)
    private String gid;

	@EmiColumn(name="salesendUid")
    private String salesendUid;

	@EmiColumn(name="number")
    private BigDecimal number;//主数量
	
	@EmiColumn(name="idForSynchro")
	private String idForSynchro;
	
	@EmiColumn(name="autoidForSynchro")
	private String autoidForSynchro;

	//单据编码或者id
	private String billIdentity;
	
	//单据日期
	private Timestamp billIDate;
	
	//自定义项1
	private String maindefine1;//主表自定义项1
	
	private String bodydefine22;//子表自定义项1
	
	private String cverifier;//单据审核状态  对应用友 DispatchList cVerifier
	
	
	
	
	public String getCverifier() {
		return cverifier;
	}

	public void setCverifier(String cverifier) {
		this.cverifier = cverifier;
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

	public String getSalesendUid() {
		return salesendUid;
	}

	public void setSalesendUid(String salesendUid) {
		this.salesendUid = salesendUid;
	}

	public BigDecimal getNumber() {
		return number;
	}

	public void setNumber(BigDecimal number) {
		this.number = number;
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

	public String getAutoidForSynchro() {
		return autoidForSynchro;
	}

	public void setAutoidForSynchro(String autoidForSynchro) {
		this.autoidForSynchro = autoidForSynchro;
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
	
	
	


   
    
}