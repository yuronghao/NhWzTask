package com.emi.web.u890.bean;

public class TransvouchHeadU8 {

	private String iwhcode;//转入仓库编码
	private String owhcode;//转出仓库编码
	private String vtid;// 单据模板号
	private String date;//单据日期
	private String maker;//制单人
	private String define10;//与wms对应
	private String transappcode;//调拨申请单号
	
	public String getIwhcode() {
		return iwhcode;
	}
	public void setIwhcode(String iwhcode) {
		this.iwhcode = iwhcode;
	}
	public String getOwhcode() {
		return owhcode;
	}
	public void setOwhcode(String owhcode) {
		this.owhcode = owhcode;
	}
	public String getVtid() {
		return vtid;
	}
	public void setVtid(String vtid) {
		this.vtid = vtid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getMaker() {
		return maker;
	}
	public void setMaker(String maker) {
		this.maker = maker;
	}
	public String getDefine10() {
		return define10;
	}
	public void setDefine10(String define10) {
		this.define10 = define10;
	}
	public String getTransappcode() {
		return transappcode;
	}
	public void setTransappcode(String transappcode) {
		this.transappcode = transappcode;
	}
	
	
	
	
}
