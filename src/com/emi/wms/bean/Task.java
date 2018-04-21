package com.emi.wms.bean;

import java.sql.Timestamp;
import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name="wm_task")
public class Task {

	@EmiColumn(name="pk",increment=true)
    private Integer pk;
	
	@EmiColumn(name="gid",ID=true)
    private String gid;
	
	@EmiColumn(name="notes")
    private String notes;
	
	@EmiColumn(name="taskname")
    private String taskname;
	
	@EmiColumn(name="state")
    private Integer state;//任务状态    0：未开始    1：进行中   2：挂起   3：结束
	
	@EmiColumn(name="locked")
    private Integer locked;
	
	@EmiColumn(name="completetime")
    private Timestamp completetime;
	
	@EmiColumn(name="billGid")
    private String billGid;
	
	@EmiColumn(name="billCode")
	private String billCode;
	
	@EmiColumn(name="billGidSource")
	private String billGidSource;
	
	@EmiColumn(name="taskTypeUid")
    private String taskTypeUid;
	
	@EmiColumn(name="sobgid")
    private String sobgid;
	
	@EmiColumn(name="orggid")
    private String orggid;
	
	@EmiColumn(name="billIdentityForSynchro")
	private String billIdentityForSynchro;
	
	@EmiColumn(name="taskTypeCodeForSynchro")
	private String taskTypeCodeForSynchro;

	@EmiColumn(name="distributeTime")
	private Timestamp distributeTime;
	
	
	
	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public String getBillGidSource() {
		return billGidSource;
	}

	public void setBillGidSource(String billGidSource) {
		this.billGidSource = billGidSource;
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getTaskname() {
		return taskname;
	}

	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public Timestamp getCompletetime() {
		return completetime;
	}

	public void setCompletetime(Timestamp completetime) {
		this.completetime = completetime;
	}

	public String getBillGid() {
		return billGid;
	}

	public void setBillGid(String billGid) {
		this.billGid = billGid;
	}

	public String getTaskTypeUid() {
		return taskTypeUid;
	}

	public void setTaskTypeUid(String taskTypeUid) {
		this.taskTypeUid = taskTypeUid;
	}

	public String getSobgid() {
		return sobgid;
	}

	public void setSobgid(String sobgid) {
		this.sobgid = sobgid;
	}

	public String getOrggid() {
		return orggid;
	}

	public void setOrggid(String orggid) {
		this.orggid = orggid;
	}

	public String getBillIdentityForSynchro() {
		return billIdentityForSynchro;
	}

	public void setBillIdentityForSynchro(String billIdentityForSynchro) {
		this.billIdentityForSynchro = billIdentityForSynchro;
	}

	public String getTaskTypeCodeForSynchro() {
		return taskTypeCodeForSynchro;
	}

	public void setTaskTypeCodeForSynchro(String taskTypeCodeForSynchro) {
		this.taskTypeCodeForSynchro = taskTypeCodeForSynchro;
	}

	public Timestamp getDistributeTime() {
		return distributeTime;
	}

	public void setDistributeTime(Timestamp distributeTime) {
		this.distributeTime = distributeTime;
	}




	
   
}