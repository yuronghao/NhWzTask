package com.emi.wms.bean;

import java.sql.Timestamp;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

public class SaleSend {
	
	@EmiColumn(name="pk",increment=true)
    private Integer pk;

	@EmiColumn(name="gid",ID=true)
    private String gid;

    private String billstate;
	
	private String billCode;

    private Timestamp billdate;
	
	private String idForSynchro;
	

	private String define1;//自定义项1
	
	
	public String getDefine1() {
		return define1;
	}

	public void setDefine1(String define1) {
		this.define1 = define1;
	}

	public Integer getPk() {
		return pk;
	}

	public void setPk(Integer pk) {
		this.pk = pk;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}


	public String getBillstate() {
		return billstate;
	}

	public void setBillstate(String billstate) {
		this.billstate = billstate;
	}

	public Timestamp getBilldate() {
		return billdate;
	}

	public void setBilldate(Timestamp billdate) {
		this.billdate = billdate;
	}


	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public String getIdForSynchro() {
		return idForSynchro;
	}

	public void setIdForSynchro(String idForSynchro) {
		this.idForSynchro = idForSynchro;
	}
	
	

}