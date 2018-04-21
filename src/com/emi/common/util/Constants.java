package com.emi.common.util;

public class Constants  {
	public static final int SUCCESS_VALUE = 1; // 成功
	public static final int FAIL_VALUE = 0; // 失败

	public static final int RIGHT_WEB = 0; // 权限类型-网页版权限
	public static final int RIGHT_MOB = 1; // 权限类型-手机版权限

	public static final String SET_SYSTEMS = "rightSystems"; // 权限管理的系统

	public static final String SYSNAME_WMS = "wms"; // 系统名称简称 仓储管理系统
	public static final String SYSNAME_MES = "mes"; // 系统名称简称 生产管理系统

	// ////////////////////////////////////////////////////////////////////////////
	public static final String CACHE_GOODS = "goods";// 物料在缓存中的头标识
	public static final String CACHE_STOCK = "stock";// 物料现存量在缓存中的头标识
	public static final String CACHE_DEPARTMENT = "department";// 部门在缓存中的头标识
	public static final String CACHE_PERSON = "person";// 人员在缓存中的头标识
	public static final String CACHE_WAREHOUSE = "warehouse";// 仓库在缓存中的头标识
	public static final String CACHE_GOODSALLOCATION = "goodsallocation";// 货位在缓存中的头标识
	public static final String CACHE_PROVIDERCUSTOMER = "providercustomer";// 客商在缓存中的头标识
	public static final String CACHE_MESWORKCENTER = "mesworkcenter";// 工作中心在缓存中的头标识
	public static final String CACHE_MESWORKSTATION = "mesworkstation";// 工作中心在缓存中的头标识
	public static final String CACHE_MESEQUIPMENT = "mesequipment";// 设备在缓存中的头标识
	public static final String CACHE_MOULD = "mould";// 模具在缓存中的头标识
	public static final String CACHE_GROUP = "group";// 设备在缓存中的头标识
	public static final String CACHE_MESWORKEQUIPMENTLIST = "mesworkequipmentlist";// 工作中心所属的设备
	public static final String CACHE_MESSTANDARDPROCESS="messtandardprocess";//工序在缓存中的头标识
	public static final String CACHE_ALLOCATIONSTOCK="allocationstock";//货位现存量在缓存中的头标识
	public static final String CACHE_ALLOCATIONSTOCKLIST="allocationstocklist";//货位现存量List在缓存中的头标识
	public static final String CACHE_USER="user";//用户在缓存中的头标识
	public static final String CACHE_SESSION="session";//session在缓存中的头标识
	public static final String CACHE_ORG="org";//组织在缓存中的头标识
	public static final String CACHE_CLASSIFY="classify";//类别在缓存中的头标识
	public static final String CACHE_UNIT="unit";//单位在缓存中的头标识

	
	
	public static void main(String[] args) {
		String str = "AllocationStockList";
		System.out.println(str.toUpperCase());
		System.out.println(str.toLowerCase());
	}

	/*
	 * ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓ 功能模块的代码
	 * ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	 */
	public static final String RI_CODE_USER = "wms_010101"; // 用户信息
	public static final String RI_CODE_ORG = "wms_010201"; // 组织信息
	public static final String RI_CODE_DEPARTMENT = "wms_010202"; // 部门信息
	public static final String RI_CODE_PERSON = "wms_010203"; // 人员信息
	public static final String RI_CODE_RIGHT = "wms_010301"; // 模块管理
	public static final String RI_CODE_ROLE = "wms_010302"; // 角色管理
	public static final String RI_CODE_FLOWBEGIN = "flow_0101"; // 流程发起
	public static final String RI_CODE_TODO = "flow_0102"; // 待办工作
	public static final String RI_CODE_DONE = "flow_0103"; // 已办工作
	public static final String RI_CODE_FLOWDESIGN = "flow_0201"; // 流程设计
	public static final String RI_CODE_PROJECT = "ms_0101"; // 工程管理
	public static final String RI_CODE_SUBJECT = "ms_0102"; // 项目管理
	public static final String RI_CODE_PROJECTFILE = "ms_0103"; // 文件上传
	public static final String RI_CODE_PAYREPORT = "ms_0104"; // 计量支付上报
	public static final String RI_CODE_PAY = "ms_0105"; // 付款
	public static final String RI_CODE_PROVIDERSORT = "wms_020301"; // 供应商分类
	public static final String RI_CODE_PROVIDER = "wms_020302"; // 供应商
	/*
	 * ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑ 功能模块的代码
	 * ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	 */
	
	
	   ////////////////////////////////////////////////////////////单据来源开始
		public static String BILLGIDSOURCE_CGDH="10"; //单据来源：采购到货
		public static String BILLGIDSOURCE_CGZJ="20"; //单据来源：采购质检
		public static String BILLGIDSOURCE_XSZJ="28"; //单据来源：销售发货质检
		public static String BILLGIDSOURCE_XSFH="30"; //单据来源：销售发货
		public static String BILLGIDSOURCE_SCDD="40"; //单据来源：生产订单
		public static String BILLGIDSOURCE_SCDDGY="50"; //单据来源：生产订单工艺表子表
		public static String BILLGIDSOURCE_PGD="55"; //单据来源：派工单
		public static String BILLGIDSOURCE_BGZJ="60"; //单据来源：报工质检
	  ////////////////////////////////////////////////////////////单据来源结束

}
