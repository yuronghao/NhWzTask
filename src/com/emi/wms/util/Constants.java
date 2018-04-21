package com.emi.wms.util;

public class Constants
{
	public static String databaseType = "sqlServer";
	public static String dataChangeMessage = "该数据已被修改，请重新打开";
	public static final int SUCCESS_VALUE = 1;// 成功
	public static final int FAIL_VALUE = 0;// 失败
	public static final int STEP_PREVIOUS = 0;//上一步
	public static final int STEP_NEXT = 1;//上一步
	
	////////////////////////////////////////////////////////////任务类型开始
	public static String TASKTYPE_CGZJ = "0008";//采购质检
	public static String TASKTYPE_CGTHCK = "0009";//采购退货出库
	public static String TASKTYPE_CGRK = "0010";//任务类型：采购入库
	public static String TASKTYPE_CPZJ = "0019";//成品质检
	public static String TASKTYPE_CPRK = "0020";//任务类型：成品入库
	public static String TASKTYPE_QTRK = "0030";//任务类型：其他入库
	public static String TASKTYPE_XSZJ = "0038";//任务类型：销售出库质检
	public static String TASKTYPE_XSTHRK = "0039";//销售退货入库
	public static String TASKTYPE_XSCK = "0040";//任务类型：销售出库
	public static String TASKTYPE_CLCK = "0050";//任务类型：材料出库
	public static String TASKTYPE_QTCK = "0060";//任务类型：其他出库
	public static String TASKTYPE_CGDH = "0070";//任务类型：采购到货
	public static String TASKTYPE_PD = "0080";//任务类型：盘点
	public static String TASKTYPE_DBRK = "0090";//任务类型：调拨入库
	public static String TASKTYPE_DBCK = "0100";//任务类型：调拨出库
	public static String TASKTYPE_XSFH = "0110";//任务类型：销售发货
	public static String TASKTYPE_CGDD = "0120";//任务类型:采购订单
	public static String TASKTYPE_XSDD = "0130";//任务类型:销售订单
	public static String TASKTYPE_SCDD = "0140";//生产订单
	public static String TASKTYPE_WWCLCK = "0150";//委外材料出库
	public static String TASKTYPE_WWCPZJ = "0160";//委外成品质检
	public static String TASKTYPE_WWCPRK = "0170";//委外成品入库
	public static String TASKTYPE_WWDD = "0180";//委外订单
	public static String TASKTYPE_BGD = "0190";//报工单
	public static String TASKTYPE_BGZJ = "0200";//报工质检
	public static String TASKTYPE_GXLL = "0210";//工序领料
	public static String TASKTYPE_GXTL = "0220";//工序退料
	public static String TASKTYPE_DDGYZB = "0145";//订单工艺路线子表
   ////////////////////////////////////////////////////////////任务类型结束

	//任务状态    0：未开始    1：进行中   2：挂起   3：结束
	
	public static final int UNFINISHED = 0;//未开始
	public static final int STILL = 1;//进行中
	public static final int HANG = 2;//挂起
	public static final int FINISHED = 3;//结束


   ////////////////////////////////////////////////////////////单据来源开始
    public static String BILLGIDSOURCE_CGD="5"; //单据来源：采购订单
	public static String BILLGIDSOURCE_CGDH="10"; //单据来源：采购到货单
	public static String BILLGIDSOURCE_CGZJ="20"; //单据来源：采购质检单
	public static String BILLGIDSOURCE_XSZJ="28"; //单据来源：销售发货质检单
	public static String BILLGIDSOURCE_XSFH="30"; //单据来源：销售发货单
	public static String BILLGIDSOURCE_SCDD="40"; //单据来源：生产订单
	public static String BILLGIDSOURCE_SCDDGY="50"; //单据来源：生产订单工艺表子表
	public static String BILLGIDSOURCE_PGD="55"; //单据来源：派工单
	public static String BILLGIDSOURCE_BGZJ="60"; //单据来源：报工质检单
	public static String BILLGIDSOURCE_BGD="65"; //单据来源：报工单
	public static String BILLGIDSOURCE_CPZJ="70"; //单据来源：产品质检单
	public static String BILLGIDSOURCE_WWDD="75"; //单据来源：委外订单
	public static String BILLGIDSOURCE_WWZJ="80"; //单据来源：委外质检
	public static String BILLGIDSOURCE_WWDH="85"; //单据来源：委外到货
  ////////////////////////////////////////////////////////////单据来源开始
	
	
	
  ///////////////////////////////////////////////////////////定义一米单据编号前缀
	public static String EMXSCK = "10";//任务类型：销售出库
	public static String EMICLCK = "15";//任务类型：材料出库
	public static String EMIQTCK = "25";//任务类型：其他出库
	public static String EMIQTRK = "30";//任务类型：其他入库
	public static String EMIDB = "20";//任务类型：调拨单
	public static String EMICGRK = "35";//任务类型：采购入库
	

}
