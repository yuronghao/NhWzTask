package com.emi.web.u890.bean;

import java.util.Date;
import java.util.List;

public class BomBom {
	private Integer bomId;
	private Integer bomType;
	private Integer version;
	private String versionDesc;
	private Date versionEffDate=new Date();
	private Date versionEndDate=new Date();
	private Date createTime=new Date();
	private String createUser;
	private Date modifyTime=new Date();
	private String modifyUser;
	private Integer status;
	private String relsUser;
	private Date relsTime=new Date();
	
	private BomParent bomParent;
	private List<BomOpcomponent> bomOpcomponentList;
	
	private String lugGoodsId;//lugErp的入库或半成品或成品id  存到Define14字段
	
	public Integer getBomType() {
		return bomType;
	}
	public void setBomType(Integer bomType) {
		this.bomType = bomType;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getVersionDesc() {
		return versionDesc;
	}
	public void setVersionDesc(String versionDesc) {
		this.versionDesc = versionDesc;
	}
	public Date getVersionEffDate() {
		return versionEffDate;
	}
	public void setVersionEffDate(Date versionEffDate) {
		this.versionEffDate = versionEffDate;
	}
	public Date getVersionEndDate() {
		return versionEndDate;
	}
	public void setVersionEndDate(Date versionEndDate) {
		this.versionEndDate = versionEndDate;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
	public String getModifyUser() {
		return modifyUser;
	}
	public void setModifyUser(String modifyUser) {
		this.modifyUser = modifyUser;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getRelsUser() {
		return relsUser;
	}
	public void setRelsUser(String relsUser) {
		this.relsUser = relsUser;
	}
	public Date getRelsTime() {
		return relsTime;
	}
	public void setRelsTime(Date relsTime) {
		this.relsTime = relsTime;
	}
	public Integer getBomId() {
		return bomId;
	}
	public void setBomId(Integer bomId) {
		this.bomId = bomId;
	}
	public String getLugGoodsId() {
		return lugGoodsId;
	}
	public void setLugGoodsId(String lugGoodsId) {
		this.lugGoodsId = lugGoodsId;
	}
	public BomParent getBomParent() {
		return bomParent;
	}
	public void setBomParent(BomParent bomParent) {
		this.bomParent = bomParent;
	}
	public List<BomOpcomponent> getBomOpcomponentList() {
		return bomOpcomponentList;
	}
	public void setBomOpcomponentList(List<BomOpcomponent> bomOpcomponentList) {
		this.bomOpcomponentList = bomOpcomponentList;
	}
	
	
}
