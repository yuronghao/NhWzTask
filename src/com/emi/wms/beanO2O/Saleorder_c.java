package com.emi.wms.beanO2O;

import java.io.Serializable;
import java.math.BigDecimal;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name="aa_saleorder_c")
public class Saleorder_c implements Serializable{

	
	private static final long serialVersionUID = 7875996141874322551L;

	@EmiColumn(name = "pk", increment = true)
	private Integer pk;//订单子id
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;//订单uid
	
	@EmiColumn(name = "parentGid")
	private String parentGid;
	
	@EmiColumn(name = "goodsGid")
	private String goodsGid;
	
	@EmiColumn(name = "localTaxPrice")
	private BigDecimal localTaxPrice;
	
	@EmiColumn(name = "localTaxMoney")
	private BigDecimal localTaxMoney;
	
	@EmiColumn(name = "number")
	private BigDecimal number;
	
	@EmiColumn(name = "assistNumber")
	private BigDecimal assistNumber;
	
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

	public String getParentGid() {
		return parentGid;
	}

	public void setParentGid(String parentGid) {
		this.parentGid = parentGid;
	}

	public String getGoodsGid() {
		return goodsGid;
	}

	public void setGoodsGid(String goodsGid) {
		this.goodsGid = goodsGid;
	}

	public BigDecimal getLocalTaxPrice() {
		return localTaxPrice;
	}

	public void setLocalTaxPrice(BigDecimal localTaxPrice) {
		this.localTaxPrice = localTaxPrice;
	}

	public BigDecimal getLocalTaxMoney() {
		return localTaxMoney;
	}

	public void setLocalTaxMoney(BigDecimal localTaxMoney) {
		this.localTaxMoney = localTaxMoney;
	}

	public BigDecimal getNumber() {
		return number;
	}

	public void setNumber(BigDecimal number) {
		this.number = number;
	}

	public BigDecimal getAssistNumber() {
		return assistNumber;
	}

	public void setAssistNumber(BigDecimal assistNumber) {
		this.assistNumber = assistNumber;
	}

	public Integer getStats() {
		return stats;
	}

	public void setStats(Integer stats) {
		this.stats = stats;
	}
	
	
}
