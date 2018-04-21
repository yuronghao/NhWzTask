package com.emi.web.u890.bean;

public class TransvouchBodyU8 {

	private String inventorycode;//物料编码
	private String quantity;//数量（主）
	private String cmassunitname;//主计量单位名称
	private String cSTComUnitCode;//库存辅计量单位编码
	private String cSTComUnitName;//库存辅计量单位名称
	private String irate;//换算率
	//private String define30;//在插入用友销售出库单时，记录wms对应gid(心愿已用)
	private String transappids;//调拨申请单子表ID
	private String number;//件数（辅）
	private String define31;
	
	public String getInventorycode() {
		return inventorycode;
	}
	public void setInventorycode(String inventorycode) {
		this.inventorycode = inventorycode;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getCmassunitname() {
		return cmassunitname;
	}
	public void setCmassunitname(String cmassunitname) {
		this.cmassunitname = cmassunitname;
	}
//	public String getDefine30() {
//		return define30;
//	}
//	public void setDefine30(String define30) {
//		this.define30 = define30;
//	}
	public String getTransappids() {
		return transappids;
	}
	public void setTransappids(String transappids) {
		this.transappids = transappids;
	}
	public String getcSTComUnitCode() {
		return cSTComUnitCode;
	}
	public void setcSTComUnitCode(String cSTComUnitCode) {
		this.cSTComUnitCode = cSTComUnitCode;
	}
	public String getcSTComUnitName() {
		return cSTComUnitName;
	}
	public void setcSTComUnitName(String cSTComUnitName) {
		this.cSTComUnitName = cSTComUnitName;
	}
	public String getIrate() {
		return irate;
	}
	public void setIrate(String irate) {
		this.irate = irate;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getDefine31() {
		return define31;
	}
	public void setDefine31(String define31) {
		this.define31 = define31;
	}
	
	
	
	
}
