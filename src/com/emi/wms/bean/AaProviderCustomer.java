package com.emi.wms.bean;

import java.io.Serializable;
import java.util.Date;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;
@EmiTable(name="AA_Provider_Customer")
public class AaProviderCustomer implements Serializable {


	private static final long serialVersionUID = 3163472525016607754L;


	@EmiColumn(name="pk" ,increment=true)
    private Integer pk;//客商ID


	@EmiColumn(name = "gid", ID = true)
    private String gid;//客商Uid


	@EmiColumn(name="pcCode" )
    private String pccode;//客商编号


	@EmiColumn(name="pcName" )
    private String pcname;//客商名称


	@EmiColumn(name="addr" )
    private String addr;//客商地址


	@EmiColumn(name="beginTimes" )
    private Date begintimes;//启用时间


	@EmiColumn(name="endTimes" )
    private Date endtimes;//停用时间


	@EmiColumn(name="soulationId" )
    private String soulationid;//属性方案Id


	@EmiColumn(name="sobId" )
    private String sobid;//帐套ID

	@EmiColumn(name="orgId" )
    private String orgid;//组织ID
	
	@EmiColumn(name="customerId" )
    private String customerId;//客户ID
	
	@EmiColumn(name="providerId" )
    private String providerId;//供应商ID
	
	@EmiColumn(name="isDel" )
    private String isDel;//是否删除

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

	public String getPccode() {
		return pccode;
	}

	public void setPccode(String pccode) {
		this.pccode = pccode;
	}

	public String getPcname() {
		return pcname;
	}

	public void setPcname(String pcname) {
		this.pcname = pcname;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public Date getBegintimes() {
		return begintimes;
	}

	public void setBegintimes(Date begintimes) {
		this.begintimes = begintimes;
	}

	public Date getEndtimes() {
		return endtimes;
	}

	public void setEndtimes(Date endtimes) {
		this.endtimes = endtimes;
	}

	public String getSoulationid() {
		return soulationid;
	}

	public void setSoulationid(String soulationid) {
		this.soulationid = soulationid;
	}

	public String getSobid() {
		return sobid;
	}

	public void setSobid(String sobid) {
		this.sobid = sobid;
	}

	public String getOrgid() {
		return orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getIsDel() {
		return isDel;
	}

	public void setIsDel(String isDel) {
		this.isDel = isDel;
	}
	

	

    
}