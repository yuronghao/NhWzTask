package com.emi.web.u8101.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class StoreInBodyU8101 implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1612059282593930706L;
	private String inventorycode;//物料编码
	private String shouldquantity;//（应该）数量
	private String quantity;//(主)数量
	private String number;//(辅)件数
	private String cmassunitname;//主计量单位名称
	private String csTComUnitCode;//库存辅计量单位编码
	private String csTComUnitName;//库存辅计量单位名称
	private String irate;//换算率
	private String define23;//骆氏称重信息
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
	
	private String cfree1;//自由项目1
	private String cfree2;//自由项目2
	private BigDecimal itaxrate;//税率
	private String batch;//批次
	
	private String iarrsId;//采购到货单子表标识   PU_ArrivalVouchs autoid
	private String imPoIds;//生产订单子表标识  mom_orderdetail modid
	
	private String makedate;//生产日期
	private String validdate;//失效日期
	private String imassDate;//保质期天数
	private String massunit;//保质期单位
	
	private String cmocode;//生产订单号
	private String imoseq;//生产订单行号
	
	//采购入库更新相关字段
	private String ipOsID;//采购订单子表标识
	private String inQuantity;//剩余数量
	private String btaxCost;//记税方式 
	private String cpOID;//  采购订单号  
	private String cbarvcode;// 到货单号  
	private String dbarvdate;//到货日期
	//采购入库更新相关字段
	
	
	private String cbatchProperty7;//批次属性1
	private String cbatchProperty8;//批次属性1
	private String cbatchProperty9;//批次属性1
	private String cbatchProperty10;//批次属性1
	
	
	
	public String getCbatchProperty7() {
		return cbatchProperty7;
	}
	public void setCbatchProperty7(String cbatchProperty7) {
		this.cbatchProperty7 = cbatchProperty7;
	}
	public String getCbatchProperty8() {
		return cbatchProperty8;
	}
	public void setCbatchProperty8(String cbatchProperty8) {
		this.cbatchProperty8 = cbatchProperty8;
	}
	public String getCbatchProperty9() {
		return cbatchProperty9;
	}
	public void setCbatchProperty9(String cbatchProperty9) {
		this.cbatchProperty9 = cbatchProperty9;
	}
	public String getCbatchProperty10() {
		return cbatchProperty10;
	}
	public void setCbatchProperty10(String cbatchProperty10) {
		this.cbatchProperty10 = cbatchProperty10;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getMassunit() {
		return massunit;
	}
	public String getInQuantity() {
		return inQuantity;
	}
	public void setInQuantity(String inQuantity) {
		this.inQuantity = inQuantity;
	}
	public String getIpOsID() {
		return ipOsID;
	}
	public void setIpOsID(String ipOsID) {
		this.ipOsID = ipOsID;
	}
	public String getBtaxCost() {
		return btaxCost;
	}
	public void setBtaxCost(String btaxCost) {
		this.btaxCost = btaxCost;
	}
	public String getCpOID() {
		return cpOID;
	}
	public void setCpOID(String cpOID) {
		this.cpOID = cpOID;
	}
	public String getCbarvcode() {
		return cbarvcode;
	}
	public void setCbarvcode(String cbarvcode) {
		this.cbarvcode = cbarvcode;
	}
	public String getDbarvdate() {
		return dbarvdate;
	}
	public void setDbarvdate(String dbarvdate) {
		this.dbarvdate = dbarvdate;
	}
	public void setMassunit(String massunit) {
		this.massunit = massunit;
	}
	public String getMakedate() {
		return makedate;
	}
	public void setMakedate(String makedate) {
		this.makedate = makedate;
	}
	public String getImassDate() {
		return imassDate;
	}
	public void setImassDate(String imassDate) {
		this.imassDate = imassDate;
	}
	public String getDefine23() {
		return define23;
	}
	public void setDefine23(String define23) {
		this.define23 = define23;
	}
	public String getValiddate() {
		return validdate;
	}
	public void setValiddate(String validdate) {
		this.validdate = validdate;
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
	public String getImPoIds() {
		return imPoIds;
	}
	public void setImPoIds(String imPoIds) {
		this.imPoIds = imPoIds;
	}
	public String getIarrsId() {
		return iarrsId;
	}
	public void setIarrsId(String iarrsId) {
		this.iarrsId = iarrsId;
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
	public BigDecimal getItaxrate() {
		return itaxrate;
	}
	public void setItaxrate(BigDecimal itaxrate) {
		this.itaxrate = itaxrate;
	}
	public String getBatch() {
		return batch;
	}
	public void setBatch(String batch) {
		this.batch = batch;
	}
	public String getCmocode() {
		return cmocode;
	}
	public void setCmocode(String cmocode) {
		this.cmocode = cmocode;
	}
	public String getImoseq() {
		return imoseq;
	}
	public void setImoseq(String imoseq) {
		this.imoseq = imoseq;
	}
	
	
	
	
	
	
}
