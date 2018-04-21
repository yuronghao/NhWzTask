package com.emi.web.u890.bean;

public class StoreOutHeadU8 {

	private String vouchtype;//单据类型
	private String businesstype;//业务类型
	private String source;//单据来源   库存
	private String warehousecode;//仓库编码
	private String date;//单据日期
	private String receivecode;//收发类别编码
	private String departmentcode;//部门编码
	private String customercode;//客户编码
	private String consignmentcode;//发货单号
	private String templatenumber;//模版号
	private String maker;//制单人
	private String define10;//与wms主表相对应
	
	private String cshipAddress;//发货地址
	private String cmemo;//备注
	private String cpersonCode;//业务员
	
	private String iarriveid;//DispatchList cDLCode 发货退货单号
	private String cbusCode;//DispatchList cDLCode
	private String cdlCode;//DispatchList DLID  发货退货单主表标识
	
	private String iproorderid;//mom_order moCode 生产订单主表标识 
	private String cmPoCode;//mom_order moId 生产订单编号 
	
	
	
	public String getCbusCode() {
		return cbusCode;
	}
	public void setCbusCode(String cbusCode) {
		this.cbusCode = cbusCode;
	}
	public String getCdlCode() {
		return cdlCode;
	}
	public void setCdlCode(String cdlCode) {
		this.cdlCode = cdlCode;
	}
	public String getIarriveid() {
		return iarriveid;
	}
	public void setIarriveid(String iarriveid) {
		this.iarriveid = iarriveid;
	}
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
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
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
	public String getCustomercode() {
		return customercode;
	}
	public void setCustomercode(String customercode) {
		this.customercode = customercode;
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
	public String getConsignmentcode() {
		return consignmentcode;
	}
	public void setConsignmentcode(String consignmentcode) {
		this.consignmentcode = consignmentcode;
	}
	public String getCshipAddress() {
		return cshipAddress;
	}
	public void setCshipAddress(String cshipAddress) {
		this.cshipAddress = cshipAddress;
	}
	public String getCmemo() {
		return cmemo;
	}
	public void setCmemo(String cmemo) {
		this.cmemo = cmemo;
	}
	public String getCpersonCode() {
		return cpersonCode;
	}
	public void setCpersonCode(String cpersonCode) {
		this.cpersonCode = cpersonCode;
	}
	public String getIproorderid() {
		return iproorderid;
	}
	public void setIproorderid(String iproorderid) {
		this.iproorderid = iproorderid;
	}
	public String getCmPoCode() {
		return cmPoCode;
	}
	public void setCmPoCode(String cmPoCode) {
		this.cmPoCode = cmPoCode;
	}



	
	
}
