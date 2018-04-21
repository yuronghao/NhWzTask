package com.emi.web.u890.bean;

import com.emi.sys.core.annotation.EmiColumn;

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
	
	private String cfree1;//自由项目1
	private String cfree2;//自由项目2
	private String batch;//批次
	private String cwhCode;//仓库编码
	
	private String idLsID;//dispatchLists dlid  //年度结转时不会发生变化
	private String imPoIds; //mom_moallocate iMPoIds  生产订单子表标识  
	
	
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getCfree1() {
		return cfree1;
	}
	public void setCfree1(String cfree1) {
		this.cfree1 = cfree1;
	}
	public String getCfree2() {
		return cfree2;
	}
	public void setCfree2(String cfree2) {
		this.cfree2 = cfree2;
	}
	public String getIdLsID() {
		return idLsID;
	}
	public void setIdLsID(String idLsID) {
		this.idLsID = idLsID;
	}
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
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getCmassunitname() {
		return cmassunitname;
	}
	public void setCmassunitname(String cmassunitname) {
		this.cmassunitname = cmassunitname;
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
	public String getIrate() {
		return irate;
	}
	public void setIrate(String irate) {
		this.irate = irate;
	}
	public String getDefine30() {
		return define30;
	}
	public void setDefine30(String define30) {
		this.define30 = define30;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getImPoIds() {
		return imPoIds;
	}
	public void setImPoIds(String imPoIds) {
		this.imPoIds = imPoIds;
	}
	
	
	
	
	
	
	
}
