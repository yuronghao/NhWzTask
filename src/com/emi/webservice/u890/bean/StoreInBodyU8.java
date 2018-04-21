package com.emi.webservice.u890.bean;

import java.math.BigDecimal;

public class StoreInBodyU8 {

	private String inventorycode;//物料编码
	private String shouldquantity;//（应该）数量
	private String quantity;//(主)数量
	private String number;//(辅)件数
	private String cmassunitname;//主计量单位名称
	private String csTComUnitCode;//库存辅计量单位编码
	private String csTComUnitName;//库存辅计量单位名称
	private String irate;//换算率
	private String define30;//在插入用友销售出库单时，记录wms对应gid
	private String position;//货位
	
	
	private BigDecimal icost; //本币无税单价                                                                  
	private BigDecimal imoney;//本币无税金额           
	private BigDecimal itaxPrice;      //本币税额    
	private BigDecimal isum; //本币价税合计  
	
	private BigDecimal ioriCost; //原币无税单价  
	private BigDecimal ioriTaxCost; //原币含税单价   
	private BigDecimal ioriMoney; //原币无税金额  
	private BigDecimal ioriTaxPrice;   //原币税额    
	private BigDecimal ioriSum;  //原币价税合计
	
	private BigDecimal itaxrate;//税率
	private String batch;//批次
	
	
	
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public BigDecimal getItaxrate() {
		return itaxrate;
	}
	public void setItaxrate(BigDecimal itaxrate) {
		this.itaxrate = itaxrate;
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
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
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
	public BigDecimal getIcost() {
		return icost;
	}
	public void setIcost(BigDecimal icost) {
		this.icost = icost;
	}
	public BigDecimal getImoney() {
		return imoney;
	}
	public void setImoney(BigDecimal imoney) {
		this.imoney = imoney;
	}
	public BigDecimal getItaxPrice() {
		return itaxPrice;
	}
	public void setItaxPrice(BigDecimal itaxPrice) {
		this.itaxPrice = itaxPrice;
	}
	public BigDecimal getIsum() {
		return isum;
	}
	public void setIsum(BigDecimal isum) {
		this.isum = isum;
	}
	public BigDecimal getIoriCost() {
		return ioriCost;
	}
	public void setIoriCost(BigDecimal ioriCost) {
		this.ioriCost = ioriCost;
	}
	public BigDecimal getIoriTaxCost() {
		return ioriTaxCost;
	}
	public void setIoriTaxCost(BigDecimal ioriTaxCost) {
		this.ioriTaxCost = ioriTaxCost;
	}
	public BigDecimal getIoriMoney() {
		return ioriMoney;
	}
	public void setIoriMoney(BigDecimal ioriMoney) {
		this.ioriMoney = ioriMoney;
	}
	public BigDecimal getIoriTaxPrice() {
		return ioriTaxPrice;
	}
	public void setIoriTaxPrice(BigDecimal ioriTaxPrice) {
		this.ioriTaxPrice = ioriTaxPrice;
	}
	public BigDecimal getIoriSum() {
		return ioriSum;
	}
	public void setIoriSum(BigDecimal ioriSum) {
		this.ioriSum = ioriSum;
	}
	
	
	
	
	
	
}
