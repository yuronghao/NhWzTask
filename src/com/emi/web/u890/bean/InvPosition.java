package com.emi.web.u890.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class InvPosition implements Serializable{

	private static final long serialVersionUID = 7849862261347154673L;
	
	private int rdsID;//收发记录子表标识 
	
	private int rdID;//收发记录主表标识 
	
	private String cwhCode;//仓库编码  
	
	private String cposCode;//货位编码  
	
	private String cinvCode;//存货编码 
	
	private String cbatch;//批号  
	 
	private String cfree1;//存货自由项1  
	 
	private String cfree2;//存货自由项1  

	private BigDecimal iquantity;//数量  
	 
	private BigDecimal inum;//辅计量数量 
	 
	private String chandler;//经手人  
	 
	private Timestamp ddate;//单据日期  
	
	private Timestamp dvDate;//失效日期 
	
	private Timestamp dmadeDate;//生产日期
	
	private Integer imassDate;//保质期天数 
	
	private Integer cmassUnit;//保质期单位
	
	private int brdFlag;//收发标志  1收 0发
	
	private String cvmivencode;//代管商编码
	

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

	public Timestamp getDvDate() {
		return dvDate;
	}

	public void setDvDate(Timestamp dvDate) {
		this.dvDate = dvDate;
	}

	public int getRdsID() {
		return rdsID;
	}

	public void setRdsID(int rdsID) {
		this.rdsID = rdsID;
	}

	public int getRdID() {
		return rdID;
	}

	public void setRdID(int rdID) {
		this.rdID = rdID;
	}

	public String getCwhCode() {
		return cwhCode;
	}

	public void setCwhCode(String cwhCode) {
		this.cwhCode = cwhCode;
	}

	public String getCposCode() {
		return cposCode;
	}

	public void setCposCode(String cposCode) {
		this.cposCode = cposCode;
	}

	public String getCinvCode() {
		return cinvCode;
	}

	public void setCinvCode(String cinvCode) {
		this.cinvCode = cinvCode;
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

	public String getChandler() {
		return chandler;
	}

	public void setChandler(String chandler) {
		this.chandler = chandler;
	}

	public Timestamp getDdate() {
		return ddate;
	}

	public void setDdate(Timestamp ddate) {
		this.ddate = ddate;
	}

	public int getBrdFlag() {
		return brdFlag;
	}

	public void setBrdFlag(int brdFlag) {
		this.brdFlag = brdFlag;
	}
	
	
	
}
