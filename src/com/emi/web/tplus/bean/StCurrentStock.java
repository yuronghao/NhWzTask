package com.emi.web.tplus.bean;

import java.io.Serializable;
import java.math.BigDecimal;

import com.emi.common.util.CommonUtil;
import com.emi.wms.bean.WmAllocationstock;

public class StCurrentStock implements Serializable{

	private static final long serialVersionUID = 3568690942642981608L;

	private String id;
	
	private String idwarehouse;//仓库id
	
	private String idinventory;//物品id
	
	private String batch;//批次
	
	private BigDecimal baseQuantity;//结存数量
	
	private BigDecimal subQuantity;//结存辅计量数量
	

	private String idbaseunit;//主计量单位id
	
	private String recordDate;//记录日期
	
	private String createdTime;//创建时间
	
	private String updated;//更新时间
	
	private String updatedBy;//更新者

	private String idmarketingOrgan;//营销机构id
	
	
	
	
	public String getIdmarketingOrgan() {
		return idmarketingOrgan;
	}

	public void setIdmarketingOrgan(String idmarketingOrgan) {
		this.idmarketingOrgan = idmarketingOrgan;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIdwarehouse() {
		return idwarehouse;
	}

	public void setIdwarehouse(String idwarehouse) {
		this.idwarehouse = idwarehouse;
	}

	public String getIdinventory() {
		return idinventory;
	}

	public void setIdinventory(String idinventory) {
		this.idinventory = idinventory;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public BigDecimal getBaseQuantity() {
		return baseQuantity;
	}

	public void setBaseQuantity(BigDecimal baseQuantity) {
		this.baseQuantity = baseQuantity;
	}

	public BigDecimal getSubQuantity() {
		return subQuantity;
	}

	public void setSubQuantity(BigDecimal subQuantity) {
		this.subQuantity = subQuantity;
	}

	public String getIdbaseunit() {
		return idbaseunit;
	}

	public void setIdbaseunit(String idbaseunit) {
		this.idbaseunit = idbaseunit;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
	}

	public String getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(String createdTime) {
		this.createdTime = createdTime;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}









	@Override
	public boolean equals(Object obj) {
		
		if(this==obj){
			return true;
		}
		
		if(obj instanceof StCurrentStock){
			StCurrentStock newObj=(StCurrentStock) obj;
			
			if(
				//物品id
				((CommonUtil.isNullObject(this.getIdinventory())?"":this.getIdinventory()).equalsIgnoreCase(CommonUtil.isNullObject(newObj.getIdinventory())?"":newObj.getIdinventory()) )
				&&
				//仓库
				((CommonUtil.isNullObject(this.getIdwarehouse())?"":this.getIdwarehouse()).equalsIgnoreCase(CommonUtil.isNullObject(newObj.getIdwarehouse())?"":newObj.getIdwarehouse()))
				&&
				//批次
				((CommonUtil.isNullObject(this.getBatch())?"":this.getBatch()).equalsIgnoreCase(CommonUtil.isNullObject(newObj.getBatch())?"":newObj.getBatch()))
				
				){
				
				return true;
			}else{

				
			}
			
		}
		
		return false;
	}
	
	
}
