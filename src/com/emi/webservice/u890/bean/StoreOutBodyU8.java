package com.emi.webservice.u890.bean;

public class StoreOutBodyU8 {

	private String inventorycode;//物料编码
	private String shouldquantity;//应发数量
	private String shouldnumber;//应发件数
	private String quantity;//数量（主计量数量）
	private String number;//件数(辅)
	private String cmassunitname;//主计量单位名称
	private String csTComUnitCode;//库存辅计量单位编码
	private String csTComUnitName;//库存辅计量单位名称
	private String irate;//换算率
	private String define30;//在插入用友销售出库单时，记录wms对应gid
	private String position;//货位
	
	public String getInventorycode() {
		return inventorycode;
	}
	public void setInventorycode(String inventorycode) {
		this.inventorycode = inventorycode;
	}
	public String getShouldquantity() {
		return shouldquantity;
	}
	public void setShouldquantity(String shouldquantity) {
		this.shouldquantity = shouldquantity;
	}
	public String getShouldnumber() {
		return shouldnumber;
	}
	public void setShouldnumber(String shouldnumber) {
		this.shouldnumber = shouldnumber;
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

	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getDefine30() {
		return define30;
	}
	public void setDefine30(String define30) {
		this.define30 = define30;
	}

	public String getIrate() {
		return irate;
	}
	public void setIrate(String irate) {
		this.irate = irate;
	}

	public String getCsTComUnitCode() {
		return csTComUnitCode;
	}
	public void setCsTComUnitCode(String csTComUnitCode) {
		this.csTComUnitCode = csTComUnitCode;
	}
	public String getCsTComUnitName() {
		return csTComUnitName;
	}
	public void setCsTComUnitName(String csTComUnitName) {
		this.csTComUnitName = csTComUnitName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	
	

	
	
	
	
	
}
