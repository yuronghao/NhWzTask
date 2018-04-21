package com.emi.web.tplus.bean;

import java.io.Serializable;

public class StRdRecord implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7349491444358954102L;

	private String id;
	
	private String code;
	
	private String sourceVoucherId;//来源单据id
	
	private String sourceVoucherCode;//来源单据号
	
	private String voucherState;//单据状态
	
	private int rdDirectionFlag;//出入库方向
	
	private String accountState;//核算状态
	
	private String voucherdate;//单据日期
	
	private String maker;//制单人
	
	private String madedate;//制单日期
	
	private String auditor;//审核人
	
	private String makerid;//制单人id
	
	private String auditorid;//审核人id
	
	private String auditeddate;//审核日期
	
	private int accountingperiod;//会计期间
	
	private int accountingyear;//会计年度
	
	private String createdtime;//创建时间
	
	private String idbusitype;//业务类型
	
	private String idvouchertype;//单据类型
	
	private String idwarehouse;//仓库id
	
	private String idrdstyle;//出入库类别id
	
	private int voucherYear;//单据所在年
	
	private int voucherPeriod;//单据所在月
	
	private String idmarketingOrgan;//营销机构id
	
	private String deliveryState;//销货状态
	
	private String idpartner;//客户id
	
	private String idcurrency;//币种id
	
	private String idsettleCustomer;//结算客户
	
	private String idsourceVoucherType;//来源单据类型id
	
	
	public String getIdsourceVoucherType() {
		return idsourceVoucherType;
	}

	public void setIdsourceVoucherType(String idsourceVoucherType) {
		this.idsourceVoucherType = idsourceVoucherType;
	}

	public String getDeliveryState() {
		return deliveryState;
	}

	public void setDeliveryState(String deliveryState) {
		this.deliveryState = deliveryState;
	}

	public String getIdpartner() {
		return idpartner;
	}

	public void setIdpartner(String idpartner) {
		this.idpartner = idpartner;
	}

	public String getIdcurrency() {
		return idcurrency;
	}

	public void setIdcurrency(String idcurrency) {
		this.idcurrency = idcurrency;
	}

	public String getIdsettleCustomer() {
		return idsettleCustomer;
	}

	public void setIdsettleCustomer(String idsettleCustomer) {
		this.idsettleCustomer = idsettleCustomer;
	}

	public String getIdmarketingOrgan() {
		return idmarketingOrgan;
	}

	public void setIdmarketingOrgan(String idmarketingOrgan) {
		this.idmarketingOrgan = idmarketingOrgan;
	}

	public String getCreatedtime() {
		return createdtime;
	}

	public void setCreatedtime(String createdtime) {
		this.createdtime = createdtime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getSourceVoucherId() {
		return sourceVoucherId;
	}

	public void setSourceVoucherId(String sourceVoucherId) {
		this.sourceVoucherId = sourceVoucherId;
	}

	public String getSourceVoucherCode() {
		return sourceVoucherCode;
	}

	public void setSourceVoucherCode(String sourceVoucherCode) {
		this.sourceVoucherCode = sourceVoucherCode;
	}

	public String getVoucherState() {
		return voucherState;
	}

	public void setVoucherState(String voucherState) {
		this.voucherState = voucherState;
	}

	public int getRdDirectionFlag() {
		return rdDirectionFlag;
	}

	public void setRdDirectionFlag(int rdDirectionFlag) {
		this.rdDirectionFlag = rdDirectionFlag;
	}

	public String getAccountState() {
		return accountState;
	}

	public void setAccountState(String accountState) {
		this.accountState = accountState;
	}

	public String getVoucherdate() {
		return voucherdate;
	}

	public void setVoucherdate(String voucherdate) {
		this.voucherdate = voucherdate;
	}

	public String getMaker() {
		return maker;
	}

	public void setMaker(String maker) {
		this.maker = maker;
	}

	public String getMadedate() {
		return madedate;
	}

	public void setMadedate(String madedate) {
		this.madedate = madedate;
	}

	public String getAuditor() {
		return auditor;
	}

	public void setAuditor(String auditor) {
		this.auditor = auditor;
	}

	public String getMakerid() {
		return makerid;
	}

	public void setMakerid(String makerid) {
		this.makerid = makerid;
	}

	public String getAuditorid() {
		return auditorid;
	}

	public void setAuditorid(String auditorid) {
		this.auditorid = auditorid;
	}

	public String getAuditeddate() {
		return auditeddate;
	}

	public void setAuditeddate(String auditeddate) {
		this.auditeddate = auditeddate;
	}

	public int getAccountingperiod() {
		return accountingperiod;
	}

	public void setAccountingperiod(int accountingperiod) {
		this.accountingperiod = accountingperiod;
	}

	public int getAccountingyear() {
		return accountingyear;
	}

	public void setAccountingyear(int accountingyear) {
		this.accountingyear = accountingyear;
	}

	public String getIdbusitype() {
		return idbusitype;
	}

	public void setIdbusitype(String idbusitype) {
		this.idbusitype = idbusitype;
	}

	public String getIdvouchertype() {
		return idvouchertype;
	}

	public void setIdvouchertype(String idvouchertype) {
		this.idvouchertype = idvouchertype;
	}

	public String getIdwarehouse() {
		return idwarehouse;
	}

	public void setIdwarehouse(String idwarehouse) {
		this.idwarehouse = idwarehouse;
	}

	public String getIdrdstyle() {
		return idrdstyle;
	}

	public void setIdrdstyle(String idrdstyle) {
		this.idrdstyle = idrdstyle;
	}

	public int getVoucherYear() {
		return voucherYear;
	}

	public void setVoucherYear(int voucherYear) {
		this.voucherYear = voucherYear;
	}

	public int getVoucherPeriod() {
		return voucherPeriod;
	}

	public void setVoucherPeriod(int voucherPeriod) {
		this.voucherPeriod = voucherPeriod;
	}
	
	
	
	
}
