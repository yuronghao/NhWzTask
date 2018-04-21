package com.emi.web.u890.bean;

import java.io.Serializable;
import java.sql.Timestamp;

public class RdRecord implements Serializable {

	private static final long serialVersionUID = 1679880496846802455L;

	private int id;//收发记录主表标识
	
	private int brdFlag;//收发标志   //收发标志 0 发 1收
	
	private String cvouchType;//单据类型编码 
	
	private String cbusType; //业务类型
	
	private String csource;//单据来源
	
	private String cwhCode;//仓库编码
	
	private Timestamp  ddate;//单据日期  
	
	private Timestamp dnmaketime;//制单时间
	
	private String  ccode;//收发单据号  
	
	private String crdCode;// 收发类别编码 
	
	private String ccusCode;// 客户编码  
	
	private String cvenCode;// 供应商编码  
	
	private String cmaker;//制单人编码
	
	private String cHandler;//审核人编码
	
	private Timestamp dveriDate;//审核日期
	
	private Timestamp dnverifytime;//审核时间
	
	private int vtid;//单据模版号
	
	private String cdefine10;//自定义项10  
	
	private String iexchRate;//汇率 
	
	private String cdepCode;//部门编码
	
	private String cmPoCode;//生产订单编号
	private String iproorderid;//生产订单主表标识  
	private String ipurorderid;//采购订单主表标识 
	private String cpsPcode;//父项产品编码 
	
	private String corderCode;//采购订单号 
	private String caRVCode;//采购到货单号 
	private String ipurarriveid;//采购到货单主表标识  
	
	private String cbusCode;//对应业务单号  销售出库时对应销售发货单号
	private String cdLCode; //发货退货单主表标识 
	private String iarriveid;//发货退货单号  
	
	private String cpTCode;//采购类型编码 
	
	private String daRVDate;//到货日期
	private String cexchName;//币种名称
	private String itaxRate;//税率
	
	
	
	public String getDaRVDate() {
		return daRVDate;
	}

	public void setDaRVDate(String daRVDate) {
		this.daRVDate = daRVDate;
	}

	public String getCexchName() {
		return cexchName;
	}

	public void setCexchName(String cexchName) {
		this.cexchName = cexchName;
	}

	public String getItaxRate() {
		return itaxRate;
	}

	public void setItaxRate(String itaxRate) {
		this.itaxRate = itaxRate;
	}

	public String getCpTCode() {
		return cpTCode;
	}

	public void setCpTCode(String cpTCode) {
		this.cpTCode = cpTCode;
	}

	public Timestamp getDnmaketime() {
		return dnmaketime;
	}

	public void setDnmaketime(Timestamp dnmaketime) {
		this.dnmaketime = dnmaketime;
	}

	public Timestamp getDnverifytime() {
		return dnverifytime;
	}

	public void setDnverifytime(Timestamp dnverifytime) {
		this.dnverifytime = dnverifytime;
	}

	public String getCdepCode() {
		return cdepCode;
	}

	public void setCdepCode(String cdepCode) {
		this.cdepCode = cdepCode;
	}

	public String getIexchRate() {
		return iexchRate;
	}

	public void setIexchRate(String iexchRate) {
		this.iexchRate = iexchRate;
	}

	public String getIarriveid() {
		return iarriveid;
	}

	public void setIarriveid(String iarriveid) {
		this.iarriveid = iarriveid;
	}

	public String getCdLCode() {
		return cdLCode;
	}

	public void setCdLCode(String cdLCode) {
		this.cdLCode = cdLCode;
	}

	public String getIproorderid() {
		return iproorderid;
	}

	public void setIproorderid(String iproorderid) {
		this.iproorderid = iproorderid;
	}

	public String getCorderCode() {
		return corderCode;
	}

	public void setCorderCode(String corderCode) {
		this.corderCode = corderCode;
	}

	public String getCaRVCode() {
		return caRVCode;
	}

	public void setCaRVCode(String caRVCode) {
		this.caRVCode = caRVCode;
	}

	public String getIpurarriveid() {
		return ipurarriveid;
	}

	public void setIpurarriveid(String ipurarriveid) {
		this.ipurarriveid = ipurarriveid;
	}

	public String getCmPoCode() {
		return cmPoCode;
	}

	public void setCmPoCode(String cmPoCode) {
		this.cmPoCode = cmPoCode;
	}

	public String getIpurorderid() {
		return ipurorderid;
	}

	public void setIpurorderid(String ipurorderid) {
		this.ipurorderid = ipurorderid;
	}

	public String getCpsPcode() {
		return cpsPcode;
	}

	public void setCpsPcode(String cpsPcode) {
		this.cpsPcode = cpsPcode;
	}

	public String getcHandler() {
		return cHandler;
	}

	public void setcHandler(String cHandler) {
		this.cHandler = cHandler;
	}

	public String getCbusCode() {
		return cbusCode;
	}

	public void setCbusCode(String cbusCode) {
		this.cbusCode = cbusCode;
	}

	public String getCdefine10() {
		return cdefine10;
	}

	public void setCdefine10(String cdefine10) {
		this.cdefine10 = cdefine10;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getBrdFlag() {
		return brdFlag;
	}

	public void setBrdFlag(int brdFlag) {
		this.brdFlag = brdFlag;
	}

	public String getCvouchType() {
		return cvouchType;
	}

	public void setCvouchType(String cvouchType) {
		this.cvouchType = cvouchType;
	}

	public String getCbusType() {
		return cbusType;
	}

	public void setCbusType(String cbusType) {
		this.cbusType = cbusType;
	}

	public String getCsource() {
		return csource;
	}

	public void setCsource(String csource) {
		this.csource = csource;
	}

	public String getCwhCode() {
		return cwhCode;
	}

	public void setCwhCode(String cwhCode) {
		this.cwhCode = cwhCode;
	}

	public Timestamp getDdate() {
		return ddate;
	}

	public void setDdate(Timestamp ddate) {
		this.ddate = ddate;
	}

	public String getCcode() {
		return ccode;
	}

	public void setCcode(String ccode) {
		this.ccode = ccode;
	}

	public String getCrdCode() {
		return crdCode;
	}

	public void setCrdCode(String crdCode) {
		this.crdCode = crdCode;
	}

	public String getCcusCode() {
		return ccusCode;
	}

	public void setCcusCode(String ccusCode) {
		this.ccusCode = ccusCode;
	}

	public String getCvenCode() {
		return cvenCode;
	}

	public void setCvenCode(String cvenCode) {
		this.cvenCode = cvenCode;
	}

	public String getCmaker() {
		return cmaker;
	}

	public void setCmaker(String cmaker) {
		this.cmaker = cmaker;
	}

	public int getVtid() {
		return vtid;
	}

	public void setVtid(int vtid) {
		this.vtid = vtid;
	}

	public Timestamp getDveriDate() {
		return dveriDate;
	}

	public void setDveriDate(Timestamp dveriDate) {
		this.dveriDate = dveriDate;
	}
	
	
	
	
}
