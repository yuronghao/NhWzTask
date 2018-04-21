package com.emi.web.u890.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class TransVouchs implements Serializable {

	private static final long serialVersionUID = -4497546234866984895L;

	private int autoID;//库存调拨单子表标识 
	
	private int id;//库存调拨单主表标识 
	
	private String ctVCode;//库存调拨单号  
	
	private String cinvCode;//存货编码 
	
	private BigDecimal itVQuantity;//数量  
	
	private BigDecimal itVNum;//辅计量数量 
	
	private String cassUnit;//辅计量单位编码  
	
	private BigDecimal iinvexchrate;//换算率  
	
	private int irowno;//单据体行号 
	
	private String ctVBatch;//批号 
	
	private String cfree1;//存货自由项1 
	
	private String cfree2;//存货自由项1 
	
	private String coutposcode;//调出货位
	
	private String cinposcode;//调入货位  
	
	private Timestamp dmadeDate;//生产日期
	private Timestamp ddisDate;//失效日期 
	private Integer imassDate;//质保期天数
	private Integer cmassUnit;//质保期单位 3标识天 
	
	

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

	public Timestamp getDdisDate() {
		return ddisDate;
	}

	public void setDdisDate(Timestamp ddisDate) {
		this.ddisDate = ddisDate;
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

	public String getCtVCode() {
		return ctVCode;
	}

	public void setCtVCode(String ctVCode) {
		this.ctVCode = ctVCode;
	}

	public String getCinvCode() {
		return cinvCode;
	}

	public void setCinvCode(String cinvCode) {
		this.cinvCode = cinvCode;
	}

	public BigDecimal getItVQuantity() {
		return itVQuantity;
	}

	public void setItVQuantity(BigDecimal itVQuantity) {
		this.itVQuantity = itVQuantity;
	}

	public BigDecimal getItVNum() {
		return itVNum;
	}

	public void setItVNum(BigDecimal itVNum) {
		this.itVNum = itVNum;
	}

	public String getCassUnit() {
		return cassUnit;
	}

	public void setCassUnit(String cassUnit) {
		this.cassUnit = cassUnit;
	}

	public BigDecimal getIinvexchrate() {
		return iinvexchrate;
	}

	public void setIinvexchrate(BigDecimal iinvexchrate) {
		this.iinvexchrate = iinvexchrate;
	}

	public int getIrowno() {
		return irowno;
	}

	public void setIrowno(int irowno) {
		this.irowno = irowno;
	}

	public String getCtVBatch() {
		return ctVBatch;
	}

	public void setCtVBatch(String ctVBatch) {
		this.ctVBatch = ctVBatch;
	}

	public String getCoutposcode() {
		return coutposcode;
	}

	public void setCoutposcode(String coutposcode) {
		this.coutposcode = coutposcode;
	}

	public String getCinposcode() {
		return cinposcode;
	}

	public void setCinposcode(String cinposcode) {
		this.cinposcode = cinposcode;
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
	
	
	
	
}
