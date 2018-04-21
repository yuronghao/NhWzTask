package com.emi.wms.beanO2O;

import java.io.Serializable;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name = "aa_customer")
public class Customer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8286928537853582823L;
	
	@EmiColumn(name = "pk", increment = true)
	private Integer pk;//客户ID
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;//客户gid
	
	@EmiColumn(name = "code")
	private String code;//客户编号
	
	@EmiColumn(name = "name")
	private String name;//客户名称
	
	@EmiColumn(name = "stats")
	private Integer stats;//状态

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

	public Integer getStats() {
		return stats;
	}

	public void setStats(Integer stats) {
		this.stats = stats;
	}
	
	
}
