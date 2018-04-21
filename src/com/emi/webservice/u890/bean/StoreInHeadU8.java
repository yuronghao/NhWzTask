package com.emi.webservice.u890.bean;

public class StoreInHeadU8 {

	private String vouchtype;//单据类型
	private String businesstype;//业务类型
	private String warehousecode;//仓库编码
	private String date;//单据日期
	private String receivecode;//收发类别编码
	private String departmentcode;//部门编码
	private String templatenumber;//模版号
	private String maker;//制单人变那么
	private String define10;//与wms入库单主表gid 对应
	private String providerCode;//供应商编码
	
	public String getVouchtype() {
		return vouchtype;
	}
	public void setVouchtype(String vouchtype) {
		this.vouchtype = vouchtype;
	}
	public String getBusinesstype() {
		return businesstype;
	}
	public void setBusinesstype(String businesstype) {
		this.businesstype = businesstype;
	}
	public String getWarehousecode() {
		return warehousecode;
	}
	public void setWarehousecode(String warehousecode) {
		this.warehousecode = warehousecode;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getReceivecode() {
		return receivecode;
	}
	public void setReceivecode(String receivecode) {
		this.receivecode = receivecode;
	}
	public String getDepartmentcode() {
		return departmentcode;
	}
	public void setDepartmentcode(String departmentcode) {
		this.departmentcode = departmentcode;
	}
	public String getTemplatenumber() {
		return templatenumber;
	}
	public void setTemplatenumber(String templatenumber) {
		this.templatenumber = templatenumber;
	}
	public String getMaker() {
		return maker;
	}
	public void setMaker(String maker) {
		this.maker = maker;
	}
	public String getDefine10() {
		return define10;
	}
	public void setDefine10(String define10) {
		this.define10 = define10;
	}
	public String getProviderCode() {
		return providerCode;
	}
	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}


	
}
