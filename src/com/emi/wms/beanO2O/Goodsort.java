package com.emi.wms.beanO2O;

import java.io.Serializable;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name = "aa_goodsort")
public class Goodsort implements Serializable{

	private static final long serialVersionUID = 5780138109804821862L;

	@EmiColumn(name = "pk", increment = true)
	private Integer pk;//分类id
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;//分类uid
	
	@EmiColumn(name = "name")
	private String name;//类别名称
	
	@EmiColumn(name = "parentGid")
	private String parentGid;//父uuid标识符
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParentGid() {
		return parentGid;
	}

	public void setParentGid(String parentGid) {
		this.parentGid = parentGid;
	}

	public Integer getStats() {
		return stats;
	}

	public void setStats(Integer stats) {
		this.stats = stats;
	}
	
	
	
}
