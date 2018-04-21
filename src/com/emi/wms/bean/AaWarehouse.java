package com.emi.wms.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name="AA_WareHouse")
public class AaWarehouse implements Serializable {

	private static final long serialVersionUID = 6130392809873713887L;

	@EmiColumn(increment=true,name="pk")
    private Integer pk;
	@EmiColumn(ID=true,name="gid")
    private String gid;//仓库UID
	@EmiColumn(name="notes")
    private String notes;//备注
	@EmiColumn(name="whcode")
    private String whcode;//仓库编码
	@EmiColumn(name="whname")
    private String whname;//仓库名称
	@EmiColumn(name="whaddr")
    private String whaddr;//仓库地址
	@EmiColumn(name="whtel")
    private String whtel;//仓库电话
	@EmiColumn(name="linkman")
    private String linkman;//仓库联系人
	@EmiColumn(name="longitude")
    private Double longitude;//仓库经度
	@EmiColumn(name="latitude")
    private Double latitude;//仓库纬度
	@EmiColumn(name="orggid")
    private String orggid;//所属组织UId
	@EmiColumn(name="sobgid")
	private String sobgid;//帐套编码
    private Timestamp begintimes;//生效时间
    private Timestamp endtimes;//失效时间
    @EmiColumn(name="barcode")
    private String barcode;//仓库条码
    @EmiColumn(name="whpos")
    private Integer whpos;//是否货位管理
    @EmiColumn(name="isDel")
    private Integer isDel;//是否删除
    
    @EmiColumn(name="bproxyWh")
    private Integer bproxyWh;//是否代管
    
    
	public Integer getBproxyWh() {
		return bproxyWh;
	}
	public void setBproxyWh(Integer bproxyWh) {
		this.bproxyWh = bproxyWh;
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
	public String getWhcode() {
		return whcode;
	}
	public void setWhcode(String whcode) {
		this.whcode = whcode;
	}
	public String getWhname() {
		return whname;
	}
	public void setWhname(String whname) {
		this.whname = whname;
	}
	public String getWhaddr() {
		return whaddr;
	}
	public void setWhaddr(String whaddr) {
		this.whaddr = whaddr;
	}
	public String getWhtel() {
		return whtel;
	}
	public void setWhtel(String whtel) {
		this.whtel = whtel;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public String getOrggid() {
		return orggid;
	}
	public void setOrggid(String orggid) {
		this.orggid = orggid;
	}
	public String getSobgid() {
		return sobgid;
	}
	public void setSobgid(String sobgid) {
		this.sobgid = sobgid;
	}
	public Timestamp getBegintimes() {
		return begintimes;
	}
	public void setBegintimes(Timestamp begintimes) {
		this.begintimes = begintimes;
	}
	public Timestamp getEndtimes() {
		return endtimes;
	}
	public void setEndtimes(Timestamp endtimes) {
		this.endtimes = endtimes;
	}
	public String getBarcode() {
		return barcode;
	}
	public void setBarcode(String barcode) {
		this.barcode = barcode;
	}
	public Integer getWhpos() {
		return whpos;
	}
	public void setWhpos(Integer whpos) {
		this.whpos = whpos;
	}
	public Integer getIsDel() {
		return isDel;
	}
	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}
    
  
    
  
}