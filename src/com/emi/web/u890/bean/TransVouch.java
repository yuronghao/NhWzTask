package com.emi.web.u890.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

//调拨单主表实体
public class TransVouch implements Serializable {

	private static final long serialVersionUID = -9138263432080305840L;

	private int id;//库存调拨单主表标识
	
	private String ctVCode;//库存调拨单号
	
	private Timestamp dtVDate;//单据日期 
	
	private Timestamp dverifyDate;//审核日期
	
	private String coWhCode;//转出仓库编码
	
	private String ciWhCode;//转入仓库编码  
	
	private String cmaker;//制单人
	
	private String cverifyPerson;//审核人
	
	private int vtid;//单据模版号
	
	private String ciRdCode;//入库类别编码 
	
	private String coRdCode;//出库类别编码 
	
	

	
	public Timestamp getDverifyDate() {
		return dverifyDate;
	}

	public void setDverifyDate(Timestamp dverifyDate) {
		this.dverifyDate = dverifyDate;
	}

	public String getCiRdCode() {
		return ciRdCode;
	}

	public void setCiRdCode(String ciRdCode) {
		this.ciRdCode = ciRdCode;
	}

	public String getCoRdCode() {
		return coRdCode;
	}

	public void setCoRdCode(String coRdCode) {
		this.coRdCode = coRdCode;
	}

	public String getCverifyPerson() {
		return cverifyPerson;
	}

	public void setCverifyPerson(String cverifyPerson) {
		this.cverifyPerson = cverifyPerson;
	}

	public String getCtVCode() {
		return ctVCode;
	}

	public void setCtVCode(String ctVCode) {
		this.ctVCode = ctVCode;
	}


	
	
	public Timestamp getDtVDate() {
		return dtVDate;
	}

	public void setDtVDate(Timestamp dtVDate) {
		this.dtVDate = dtVDate;
	}

	public String getCoWhCode() {
		return coWhCode;
	}

	public void setCoWhCode(String coWhCode) {
		this.coWhCode = coWhCode;
	}

	public String getCiWhCode() {
		return ciWhCode;
	}

	public void setCiWhCode(String ciWhCode) {
		this.ciWhCode = ciWhCode;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
