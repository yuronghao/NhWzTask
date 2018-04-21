package com.emi.web.tplus.bean;

import java.io.Serializable;
import java.math.BigDecimal;

public class StRDRecordb implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4970459516059077308L;

	private String id;
	
	private String code;
	
	private String idinventory;//物料id
	
	private String idunit;//计量单位id
	
	private String idbaseunit;//主计量单位id
	
	private String idwarehouse;//仓库id
	
	private String batch;//批次
	
	private String inventoryLocation;//货位
	
	private BigDecimal quantity;//数量
	
	private BigDecimal baseQuantity;//主计量数量
	
	private BigDecimal subQuantity;//辅计量数量
	
	private String sourceVoucherId;//来源单据id
	
	private String sourceVoucherCode;//来源单据号
	
	private String sourceVoucherDetailId;//来源单据明细id
	
	private String createdtime;//创建时间
	
	private String updated;//更新时间
	
	private String updatedBy;//更新者
	
	private String idRDRecordDTO;//出入库主表id
	
	private String priuserdefnvc3;//跟wms传过来的值相互匹配
	
	private String saleDeliveryId;//关联销货单明细id
	
	private BigDecimal cumulativeSaleDispatchQuantity;//累计销货数量
	
	private String idsourceVoucherType;//来源单据类型id
	
	
	

	public BigDecimal getCumulativeSaleDispatchQuantity() {
		return cumulativeSaleDispatchQuantity;
	}

	public void setCumulativeSaleDispatchQuantity(
			BigDecimal cumulativeSaleDispatchQuantity) {
		this.cumulativeSaleDispatchQuantity = cumulativeSaleDispatchQuantity;
	}

	public String getIdsourceVoucherType() {
		return idsourceVoucherType;
	}

	public void setIdsourceVoucherType(String idsourceVoucherType) {
		this.idsourceVoucherType = idsourceVoucherType;
	}

	public String getSaleDeliveryId() {
		return saleDeliveryId;
	}

	public void setSaleDeliveryId(String saleDeliveryId) {
		this.saleDeliveryId = saleDeliveryId;
	}

	public String getPriuserdefnvc3() {
		return priuserdefnvc3;
	}

	public void setPriuserdefnvc3(String priuserdefnvc3) {
		this.priuserdefnvc3 = priuserdefnvc3;
	}

	public BigDecimal getSubQuantity() {
		return subQuantity;
	}

	public void setSubQuantity(BigDecimal subQuantity) {
		this.subQuantity = subQuantity;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String getInventoryLocation() {
		return inventoryLocation;
	}

	public void setInventoryLocation(String inventoryLocation) {
		this.inventoryLocation = inventoryLocation;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getBaseQuantity() {
		return baseQuantity;
	}

	public void setBaseQuantity(BigDecimal baseQuantity) {
		this.baseQuantity = baseQuantity;
	}

	public String getSourceVoucherId() {
		return sourceVoucherId;
	}

	public void setSourceVoucherId(String sourceVoucherId) {
		this.sourceVoucherId = sourceVoucherId;
	}

	public String getSourceVoucherCode() {
		return sourceVoucherCode;
	}

	public void setSourceVoucherCode(String sourceVoucherCode) {
		this.sourceVoucherCode = sourceVoucherCode;
	}

	public String getSourceVoucherDetailId() {
		return sourceVoucherDetailId;
	}

	public void setSourceVoucherDetailId(String sourceVoucherDetailId) {
		this.sourceVoucherDetailId = sourceVoucherDetailId;
	}

	public String getCreatedtime() {
		return createdtime;
	}

	public void setCreatedtime(String createdtime) {
		this.createdtime = createdtime;
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

	public String getIdRDRecordDTO() {
		return idRDRecordDTO;
	}

	public void setIdRDRecordDTO(String idRDRecordDTO) {
		this.idRDRecordDTO = idRDRecordDTO;
	}

	public String getIdinventory() {
		return idinventory;
	}

	public void setIdinventory(String idinventory) {
		this.idinventory = idinventory;
	}

	public String getIdunit() {
		return idunit;
	}

	public void setIdunit(String idunit) {
		this.idunit = idunit;
	}

	public String getIdbaseunit() {
		return idbaseunit;
	}

	public void setIdbaseunit(String idbaseunit) {
		this.idbaseunit = idbaseunit;
	}

	public String getIdwarehouse() {
		return idwarehouse;
	}

	public void setIdwarehouse(String idwarehouse) {
		this.idwarehouse = idwarehouse;
	}
	
	
	
}
