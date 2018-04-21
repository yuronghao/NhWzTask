package com.emi.wms.beanO2O;

import java.io.Serializable;
import java.math.BigDecimal;

import com.emi.sys.core.annotation.EmiColumn;
import com.emi.sys.core.annotation.EmiTable;

@EmiTable(name = "aa_goodssize")
public class Goods implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3068120424663199778L;
	
	@EmiColumn(name = "pk", increment = true)
	private Integer pk;//物品ID
	
	@EmiColumn(name = "gid", ID = true)
	private String gid;//物品uid
	
	@EmiColumn(name = "goodsCode")
	private String goodsCode;//物品编码
	
	@EmiColumn(name = "goodsName")
	private String goodsName;//物品名称
	
	@EmiColumn(name = "mainUnitGid")
	private String mainUnitGid;//主单位gid
	
	@EmiColumn(name = "assistUnitGid")
	private String assistUnitGid;//辅单位Gid
	
	@EmiColumn(name = "goodsSortGid")
	private String goodsSortGid;//物品分类
	
	@EmiColumn(name = "goodsStandard")
	private String goodsStandard;//规格型号
	
	@EmiColumn(name = "stats")
	private Integer stats;//状态
	
	@EmiColumn(name = "price")
	private BigDecimal price;
	

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
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

	public String getGoodsCode() {
		return goodsCode;
	}

	public void setGoodsCode(String goodsCode) {
		this.goodsCode = goodsCode;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getMainUnitGid() {
		return mainUnitGid;
	}

	public void setMainUnitGid(String mainUnitGid) {
		this.mainUnitGid = mainUnitGid;
	}

	public String getAssistUnitGid() {
		return assistUnitGid;
	}

	public void setAssistUnitGid(String assistUnitGid) {
		this.assistUnitGid = assistUnitGid;
	}

	public String getGoodsSortGid() {
		return goodsSortGid;
	}

	public void setGoodsSortGid(String goodsSortGid) {
		this.goodsSortGid = goodsSortGid;
	}

	public String getGoodsStandard() {
		return goodsStandard;
	}

	public void setGoodsStandard(String goodsStandard) {
		this.goodsStandard = goodsStandard;
	}

	public Integer getStats() {
		return stats;
	}

	public void setStats(Integer stats) {
		this.stats = stats;
	}
	
	
	
}
