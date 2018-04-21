package com.emi.wms.bean;

import java.sql.Timestamp;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name="WM_ProcureArrival")
public class Procurearrival {
	
	@EmiColumn(name="pk",increment=true)
    private Integer pk;

	@EmiColumn(name="gid",ID=true)
    private String gid;

	@EmiColumn(name="departmentuid")
    private String departmentuid;

	@EmiColumn(name="billstate")
    private String billstate;
	
	@EmiColumn(name="billCode")
	private String billCode;

	@EmiColumn(name="billdate")
    private Timestamp billdate;
	
	@EmiColumn(name="idForSynchro")
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

	public String getDepartmentuid() {
		return departmentuid;
	}

	public void setDepartmentuid(String departmentuid) {
		this.departmentuid = departmentuid;
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