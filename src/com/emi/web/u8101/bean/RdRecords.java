package com.emi.web.u8101.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class RdRecords implements Serializable {

	private static final long serialVersionUID = 4133871237266441552L;

	private int autoID;//收发记录子表标识  
	
	private int id;//收发记录主表标识  
	
	private String cinvCode;//存货编码

	private BigDecimal iAPrice ;//                            暂估金额（原币无税单价*数量）

	public BigDecimal getiAPrice() {
		return iAPrice;
	}

	private BigDecimal fACost ;
	private BigDecimal iOriTaxCost;
	private BigDecimal	iOriCost;
	private BigDecimal iOriMoney;
	private BigDecimal	iOriTaxPrice;
	private BigDecimal iTaxPrice;
	private BigDecimal	iSum;
	private BigDecimal	ioriSum ;//原币价税合计

	public BigDecimal getIoriSum() {
		return ioriSum;
	}

	public void setIoriSum(BigDecimal ioriSum) {
		this.ioriSum = ioriSum;
	}

	public BigDecimal getfACost() {
		return fACost;
	}

	public void setfACost(BigDecimal fACost) {
		this.fACost = fACost;
	}

	public BigDecimal getiOriTaxCost() {
		return iOriTaxCost;
	}

	public void setiOriTaxCost(BigDecimal iOriTaxCost) {
		this.iOriTaxCost = iOriTaxCost;
	}

	public BigDecimal getiOriCost() {
		return iOriCost;
	}

	public void setiOriCost(BigDecimal iOriCost) {
		this.iOriCost = iOriCost;
	}

	public BigDecimal getiOriMoney() {
		return iOriMoney;
	}

	public void setiOriMoney(BigDecimal iOriMoney) {
		this.iOriMoney = iOriMoney;
	}

	public BigDecimal getiOriTaxPrice() {
		return iOriTaxPrice;
	}

	public void setiOriTaxPrice(BigDecimal iOriTaxPrice) {
		this.iOriTaxPrice = iOriTaxPrice;
	}

	public BigDecimal getiTaxPrice() {
		return iTaxPrice;
	}

	public void setiTaxPrice(BigDecimal iTaxPrice) {
		this.iTaxPrice = iTaxPrice;
	}

	public BigDecimal getiSum() {
		return iSum;
	}

	public void setiSum(BigDecimal iSum) {
		this.iSum = iSum;
	}

	public int getbTaxCost() {
		return bTaxCost;
	}

	public void setbTaxCost(int bTaxCost) {
		this.bTaxCost = bTaxCost;
	}

	public String getChVencode() {
		return chVencode;
	}

	public void setChVencode(String chVencode) {
		this.chVencode = chVencode;
	}

	private int bTaxCost;//默认是1

	private String chVencode;//供应商编码

	public void setiAPrice(BigDecimal iAPrice) {
		this.iAPrice = iAPrice;
	}

	private BigDecimal iquantity;//数量
	
	private BigDecimal inum;//辅计量数量 
	
	private BigDecimal iunitCost;//单价  
	
	private BigDecimal iprice;//金额 
	
	private String cbatch;//批号  
	 
	private String cfree1;//存货自由项1  
	 
	private String cfree2;//存货自由项2 
	
	private int itrIds;//库存调拨单子表标识   TransVouchs autoID
	 
	private String cposition;//货位编码  
	
	private String cdefine23;//骆氏称重信息
	
	private String cdefine30;//自定义项10
	
//	private BigDecimal shoudIquantity;//应收发数量
	
	private String ioMoDID;//委外订单子表ID  
	private String ioMoMID;//委外订单用料表ID  
	private String comcode;//委外订单号  
	private String invcode;//产品编码
	
	private String imPoIds;//（对应生产订单材料表mom_moallocate allocateid）
	
	private String cbarvcode;//到货单号

	private String iarrsId;//采购到货单子表标识  
	private String cpoID;//订单号

	private String iPOsID;//采购订单子表标识  之前用 iordersId 后来改的
	private String ordercPoID;//采购订单单号
	
	private String idLsID;//发货退货单子表标识 
	private String cbdlcode;//发货单号 
	
	private String cmocode;//生产订单号
	private String imoseq;//生产订单行号
	
	private Timestamp dvdate;//失效日期
	private Timestamp dmadeDate; //生产日期
	private Integer imassDate; //质保期天数
	private Integer cmassUnit;//质保期单位 3标识天
	
	private Integer bvmiUsed;//代管消耗标识
	private String cvmivencode;//代管商编码
	
	private BigDecimal inQuantity;// 应收应发数量;
	private String dbarvdate;// 到货日期;
	private Integer irowno;// 单据体行号 
	
	private String itaxrate;//税率 


	public String getiPOsID() {
		return iPOsID;
	}

	public void setiPOsID(String iPOsID) {
		this.iPOsID = iPOsID;
	}

	public String getOrdercPoID() {
		return ordercPoID;
	}

	public void setOrdercPoID(String ordercPoID) {
		this.ordercPoID = ordercPoID;
	}

	public String getItaxrate() {
		return itaxrate;
	}




	public void setItaxrate(String itaxrate) {
		this.itaxrate = itaxrate;
	}

	public BigDecimal getInQuantity() {
		return inQuantity;
	}

	public void setInQuantity(BigDecimal inQuantity) {
		this.inQuantity = inQuantity;
	}


	public String getDbarvdate() {
		return dbarvdate;
	}

	public void setDbarvdate(String dbarvdate) {
		this.dbarvdate = dbarvdate;
	}

	public Integer getIrowno() {
		return irowno;
	}

	public void setIrowno(Integer irowno) {
		this.irowno = irowno;
	}

	public Integer getBvmiUsed() {
		return bvmiUsed;
	}

	public void setBvmiUsed(Integer bvmiUsed) {
		this.bvmiUsed = bvmiUsed;
	}

	public String getCvmivencode() {
		return cvmivencode;
	}

	public void setCvmivencode(String cvmivencode) {
		this.cvmivencode = cvmivencode;
	}

	public Timestamp getDmadeDate() {
		return dmadeDate;
	}

	public void setDmadeDate(Timestamp dmadeDate) {
		this.dmadeDate = dmadeDate;
	}

	public Integer getImassDate() {
		return imassDate;
	}

	public void setImassDate(Integer imassDate) {
		this.imassDate = imassDate;
	}

	public Integer getCmassUnit() {
		return cmassUnit;
	}

	public void setCmassUnit(Integer cmassUnit) {
		this.cmassUnit = cmassUnit;
	}

	public String getCdefine23() {
		return cdefine23;
	}

	public void setCdefine23(String cdefine23) {
		this.cdefine23 = cdefine23;
	}

	public Timestamp getDvdate() {
		return dvdate;
	}

	public void setDvdate(Timestamp dvdate) {
		this.dvdate = dvdate;
	}

	public String getCbdlcode() {
		return cbdlcode;
	}

	public void setCbdlcode(String cbdlcode) {
		this.cbdlcode = cbdlcode;
	}

	public String getIdLsID() {
		return idLsID;
	}

	public void setIdLsID(String idLsID) {
		this.idLsID = idLsID;
	}

	public String getImPoIds() {
		return imPoIds;
	}

	public void setImPoIds(String imPoIds) {
		this.imPoIds = imPoIds;
	}

	public String getCpoID() {
		return cpoID;
	}

	public void setCpoID(String cpoID) {
		this.cpoID = cpoID;
	}

	public String getCbarvcode() {
		return cbarvcode;
	}

	public void setCbarvcode(String cbarvcode) {
		this.cbarvcode = cbarvcode;
	}


//	public BigDecimal getShoudIquantity() {
//		return shoudIquantity;
//	}
//
//	public void setShoudIquantity(BigDecimal shoudIquantity) {
//		this.shoudIquantity = shoudIquantity;
//	}


	public String getComcode() {
		return comcode;
	}

	public void setComcode(String comcode) {
		this.comcode = comcode;
	}

	public String getInvcode() {
		return invcode;
	}

	public void setInvcode(String invcode) {
		this.invcode = invcode;
	}

	public int getItrIds() {
		return itrIds;
	}

	public void setItrIds(int itrIds) {
		this.itrIds = itrIds;
	}

	public int getAutoID() {
		return autoID;
	}

	public void setAutoID(int autoID) {
		this.autoID = autoID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCinvCode() {
		return cinvCode;
	}

	public void setCinvCode(String cinvCode) {
		this.cinvCode = cinvCode;
	}

	public BigDecimal getIquantity() {
		return iquantity;
	}

	public void setIquantity(BigDecimal iquantity) {
		this.iquantity = iquantity;
	}

	public BigDecimal getInum() {
		return inum;
	}

	public void setInum(BigDecimal inum) {
		this.inum = inum;
	}

	public BigDecimal getIunitCost() {
		return iunitCost;
	}

	public void setIunitCost(BigDecimal iunitCost) {
		this.iunitCost = iunitCost;
	}

	public BigDecimal getIprice() {
		return iprice;
	}

	public void setIprice(BigDecimal iprice) {
		this.iprice = iprice;
	}

	public String getCbatch() {
		return cbatch;
	}

	public void setCbatch(String cbatch) {
		this.cbatch = cbatch;
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

	public String getCposition() {
		return cposition;
	}

	public void setCposition(String cposition) {
		this.cposition = cposition;
	}

	public String getCdefine30() {
		return cdefine30;
	}

	public void setCdefine30(String cdefine30) {
		this.cdefine30 = cdefine30;
	}

	public String getIoMoDID() {
		return ioMoDID;
	}

	public void setIoMoDID(String ioMoDID) {
		this.ioMoDID = ioMoDID;
	}

	public String getIoMoMID() {
		return ioMoMID;
	}

	public void setIoMoMID(String ioMoMID) {
		this.ioMoMID = ioMoMID;
	}

	public String getIarrsId() {
		return iarrsId;
	}

	public void setIarrsId(String iarrsId) {
		this.iarrsId = iarrsId;
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
