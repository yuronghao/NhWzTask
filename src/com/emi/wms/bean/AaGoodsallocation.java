package com.emi.wms.bean;

import java.io.Serializable;
import java.sql.Timestamp;
import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name="AA_GoodsAllocation")
public class AaGoodsallocation implements Serializable {

	private static final long serialVersionUID = -1963123883264683163L;

	@EmiColumn(increment=true,name="pk")
    private Integer pk;
	@EmiColumn(ID=true,name="gid")
    private String gid;
	@EmiColumn(name="notes")
    private String notes;//备注
	@EmiColumn(name="code")
    private String code;//编码
	@EmiColumn(name="name")
    private String name;//名称
	@EmiColumn(name="whuid")
    private String whuid;//所属仓库uid
	
	private String whName;//所属仓库名称
	private String whcode;//所属仓库编码
	
    private Integer showorder;//显示顺序
    private Timestamp begintimes;
    private Timestamp endtimes;
    
    @EmiColumn(name="allocationbarcode")
    private String allocationbarcode;//货位条码
    @EmiColumn(name="istemp")
    private Integer istemp;//是否临时货位
    @EmiColumn(name="posgrade")
    private Integer posgrade;//货位等级
    @EmiColumn(name="posend")
    private Integer posend;//是否末级
    
	@EmiColumn(name="orggid")
    private String orggid;//所属组织UId
	@EmiColumn(name="sobgid")
	private String sobgid;//帐套编码
	@EmiColumn(name="isdel")
	private Integer isdel;//是否删除
	
	
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWhuid() {
		return whuid;
	}
	public void setWhuid(String whuid) {
		this.whuid = whuid;
	}
	public String getWhName() {
		return whName;
	}
	public void setWhName(String whName) {
		this.whName = whName;
	}
	public String getWhcode() {
		return whcode;
	}
	public void setWhcode(String whcode) {
		this.whcode = whcode;
	}
	public Integer getShoworder() {
		return showorder;
	}
	public void setShoworder(Integer showorder) {
		this.showorder = showorder;
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
	public String getAllocationbarcode() {
		return allocationbarcode;
	}
	public void setAllocationbarcode(String allocationbarcode) {
		this.allocationbarcode = allocationbarcode;
	}
	public Integer getIstemp() {
		return istemp;
	}
	public void setIstemp(Integer istemp) {
		this.istemp = istemp;
	}
	public Integer getPosgrade() {
		return posgrade;
	}
	public void setPosgrade(Integer posgrade) {
		this.posgrade = posgrade;
	}
	public Integer getPosend() {
		return posend;
	}
	public void setPosend(Integer posend) {
		this.posend = posend;
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
	public Integer getIsdel() {
		return isdel;
	}
	public void setIsdel(Integer isdel) {
		this.isdel = isdel;
	}
	
	
	
	
   
}