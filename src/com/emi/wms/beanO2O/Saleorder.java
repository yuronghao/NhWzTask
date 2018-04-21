package com.emi.wms.beanO2O;

import java.io.Serializable;
import java.sql.Timestamp;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;
@EmiTable(name="aa_saleorder")
public class Saleorder implements Serializable{

	
	private static final long serialVersionUID = 8897354005993517026L;

	@EmiColumn(name = "pk", increment = true)
	private Integer pk;//订单id
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;//订单uid
	
	@EmiColumn(name = "billCode")
	private String billCode;
	
	@EmiColumn(name = "billDate")
	private Timestamp billDate;
	
	@EmiColumn(name = "customerGid")
	private String customerGid;
	
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

	public String getBillCode() {
		return billCode;
	}

	public void setBillCode(String billCode) {
		this.billCode = billCode;
	}

	public Timestamp getBillDate() {
		return billDate;
	}

	public void setBillDate(Timestamp billDate) {
		this.billDate = billDate;
	}

	public String getCustomerGid() {
		return customerGid;
	}

	public void setCustomerGid(String customerGid) {
		this.customerGid = customerGid;
	}

	public Integer getStats() {
		return stats;
	}

	public void setStats(Integer stats) {
		this.stats = stats;
	}
	
	
}
