package com.emi.wms.beanO2O;

import java.io.Serializable;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name = "aa_unit")
public class UnitO2O implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5113637183312524512L;

	@EmiColumn(name = "pk", increment = true)
	private Integer pk;//订单子id
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;//订单uid
	
	@EmiColumn(name = "unitCode")
	private String unitCode;
	
	@EmiColumn(name = "unitName")
	private String unitName;
	
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

	public String getUnitCode() {
		return unitCode;
	}

	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Integer getStats() {
		return stats;
	}

	public void setStats(Integer stats) {
		this.stats = stats;
	}
	
	
}
