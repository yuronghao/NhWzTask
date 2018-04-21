package com.emi.wms.synchro;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

import com.emi.common.util.CommonUtil;
import com.emi.sys.init.Config;
import com.emi.wms.bean.AaWarehouse;
import com.emi.wms.bean.WmAllocationstock;
import com.emi.wms.synchro.dao.SynchroDaoU890;
import com.emi.wms.synchro.service.SynchroServiceO2O;
import com.emi.wms.synchro.service.SynchroServiceT6;
import com.emi.wms.synchro.service.SynchroServiceTplus;
import com.emi.wms.synchro.service.SynchroServiceU890;

public class ExeSynchroAllocationStock extends TimerTask {
	
	private SynchroServiceU890 synchroServiceU8;
	private SynchroDaoU890 synchroDaoU8;
	
	public void setSynchroServiceU8(SynchroServiceU890 synchroServiceU8) {
		this.synchroServiceU8 = synchroServiceU8;
	}
	

	public void setSynchroDaoU8(SynchroDaoU890 synchroDaoU8) {
		this.synchroDaoU8 = synchroDaoU8;
	}



	@Override
	public void run() {
		
		Map orgMap=synchroServiceU8.getOrgGid();
		String orgGid="";//组织gid
		if(!CommonUtil.isNullObject(orgMap)){
			orgGid=orgMap.get("gid").toString();
		}
		
		Map sobMap=synchroServiceU8.getSobGid();
		String sobGid="";//gid
		if(!CommonUtil.isNullObject(sobMap)){
			sobGid=sobMap.get("gid").toString();
		}
		
		if(Config.synchroType.equalsIgnoreCase("t+")){
			
		}else if(Config.synchroType.equalsIgnoreCase("t6")){

		}else if(Config.synchroType.equalsIgnoreCase("u890")){
			try {
				//太仓仓库都不启用货位
				String whCodeNoPos=getWarehouseNoPos();
				String noPosYonYouCondition=" cWhCode in "+whCodeNoPos;
				List<WmAllocationstock> wasYonYou=new ArrayList<WmAllocationstock>();
				if(!CommonUtil.isNullObject(whCodeNoPos)){
					wasYonYou=synchroDaoU8.getWasYonYou(noPosYonYouCondition);//从用友中查询不启用货位的库存
				}
				
				String noPosWmsCondition=" whCode in "+whCodeNoPos;
				List<WmAllocationstock> wasWms=new ArrayList<WmAllocationstock>();
				if(!CommonUtil.isNullObject(whCodeNoPos)){
					wasWms=synchroDaoU8.getWasWms(noPosWmsCondition);//从wms中查询不启用货位的库存
				}
				
				List<WmAllocationstock> toAddNoPos=new ArrayList<WmAllocationstock>();//对比需要增加的(不启用货位)
				List<WmAllocationstock> toDeleteNoPos=new ArrayList<WmAllocationstock>();//对比需要删除的（不启用货位）
				List<WmAllocationstock> toUpdateNoPos=new ArrayList<WmAllocationstock>();//对比需要修改的（不启用货位）
				
				for(WmAllocationstock wy:wasYonYou){
					boolean toAdd=true;
					for(WmAllocationstock ww:wasWms){
						if(wy.equalsNoPos(ww)){
							toAdd=false;
							
							if( wy.getNumber().compareTo(ww.getNumber()) !=0){
								wy.setGoodsallocationcode(wy.getWhcode());//对于不启用货位的仓库，将货位编码与仓库编码设置一致；
								ww.setNumber(CommonUtil.BigDecimal2BigDecimal(wy.getNumber()));//需要修改的数量
								toUpdateNoPos.add(ww);
							}
							break;
						}
					}
					
					if(toAdd){
						wy.setGoodsallocationcode(wy.getWhcode());//对于不启用货位的仓库，将货位编码与仓库编码设置一致；
						toAddNoPos.add(wy);
					}
					
				}
				
				for(WmAllocationstock ww:wasWms){
					boolean toDelte=true;
					for(WmAllocationstock wy:wasYonYou){
						if(ww.equalsNoPos(wy)){
							toDelte=false;
							break;
						}
					}
					
					if(toDelte){
						toDeleteNoPos.add(ww);
					}
				}
				
				////////////////////////////////////////////////////////////////////////////////////////////////////////处理货位管理的仓库
				
				List<WmAllocationstock> wasYonYouPos=synchroDaoU8.getWasYonYouPos("");//从用友中查询启用货位的库存
				List<WmAllocationstock> wasWmsPos=new ArrayList<WmAllocationstock>();
				String whCodePos=getWarehousePos();
				if(whCodePos.length()>0){
					String posWmsCondition=" whCode in "+whCodePos;
					wasWmsPos=synchroDaoU8.getWasWms(posWmsCondition);//从wms中查询启用货位的库存
				}
				
				List<WmAllocationstock> toAddPos=new ArrayList<WmAllocationstock>();//对比需要增加的(启用货位)
				List<WmAllocationstock> toDeletePos=new ArrayList<WmAllocationstock>();//对比需要删除的（启用货位）
				List<WmAllocationstock> toUpdtePos=new ArrayList<WmAllocationstock>();//对比需要修改的（启用货位）
				
				for(WmAllocationstock wy:wasYonYouPos){
					boolean toAdd=true;
					for(WmAllocationstock ww:wasWmsPos){
						if(wy.equalsPos(ww)){
							toAdd=false;
							
							if(wy.getNumber().compareTo(ww.getNumber()) !=0){
								ww.setNumber(CommonUtil.BigDecimal2BigDecimal(wy.getNumber()));
								toUpdtePos.add(ww);
							}
							break;
						}
					}
					
					if(toAdd){
						toAddPos.add(wy);
					}
					
				}
				
				for(WmAllocationstock ww:wasWmsPos){
					boolean toDelte=true;
					for(WmAllocationstock wy:wasYonYouPos){
						if(ww.equalsPos(wy)){
							toDelte=false;
							break;
						}
					}
					
					if(toDelte){
						toDeletePos.add(ww);
					}
				}
				
				//删除
				if(toDeleteNoPos.size()>0){
					synchroDaoU8.deleteWmAllocationstock(toDeleteNoPos);
				}
				if(toDeletePos.size()>0){
					synchroDaoU8.deleteWmAllocationstock(toDeletePos);
				}
				
				//修改
				if(toUpdateNoPos.size()>0){
					synchroDaoU8.updateWmAllocationstock(toUpdateNoPos);
				}
				
				if(toUpdtePos.size()>0){
					synchroDaoU8.updateWmAllocationstock(toUpdtePos);
				}
				
				//增加
				if(toAddNoPos.size()>0){
					synchroDaoU8.insertWmAllocationstock(toAddNoPos,orgGid,sobGid);
				}
				if(toAddPos.size()>0){
					synchroDaoU8.insertWmAllocationstock(toAddPos,orgGid,sobGid);
				}
				
				//修改关联字段
				synchroDaoU8.updWmAllocationstockInfor();
				
			
				System.out.println("同步库存结束");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}else if(Config.synchroType.equalsIgnoreCase("u8101")){
			try {

				String whCodeNoPos=getWarehouseNoPos();
				String noPosYonYouCondition=" cWhCode in "+whCodeNoPos;
				List<WmAllocationstock> wasYonYou=new ArrayList<WmAllocationstock>();
				if(!CommonUtil.isNullObject(whCodeNoPos)){
					wasYonYou=synchroDaoU8.getWasYonYou(noPosYonYouCondition);//从用友中查询不启用货位的库存
				}

				String noPosWmsCondition=" whCode in "+whCodeNoPos;
				List<WmAllocationstock> wasWms=new ArrayList<WmAllocationstock>();
				if(!CommonUtil.isNullObject(whCodeNoPos)){
					wasWms=synchroDaoU8.getWasWms(noPosWmsCondition);//从wms中查询不启用货位的库存
				}

				List<WmAllocationstock> toAddNoPos=new ArrayList<WmAllocationstock>();//对比需要增加的(不启用货位)
				List<WmAllocationstock> toDeleteNoPos=new ArrayList<WmAllocationstock>();//对比需要删除的（不启用货位）
				List<WmAllocationstock> toUpdateNoPos=new ArrayList<WmAllocationstock>();//对比需要修改的（不启用货位）

				for(WmAllocationstock wy:wasYonYou){
					boolean toAdd=true;
					for(WmAllocationstock ww:wasWms){
						if(wy.equalsNoPos(ww)){
							toAdd=false;

							if( wy.getNumber().compareTo(ww.getNumber()) !=0){
								wy.setGoodsallocationcode(wy.getWhcode());//对于不启用货位的仓库，将货位编码与仓库编码设置一致；
								ww.setNumber(CommonUtil.BigDecimal2BigDecimal(wy.getNumber()));//需要修改的数量
								toUpdateNoPos.add(ww);
							}
							break;
						}
					}

					if(toAdd){
						wy.setGoodsallocationcode(wy.getWhcode());//对于不启用货位的仓库，将货位编码与仓库编码设置一致；
						toAddNoPos.add(wy);
					}

				}

				for(WmAllocationstock ww:wasWms){
					boolean toDelte=true;
					for(WmAllocationstock wy:wasYonYou){
						if(ww.equalsNoPos(wy)){
							toDelte=false;
							break;
						}
					}

					if(toDelte){
						toDeleteNoPos.add(ww);
					}
				}

				////////////////////////////////////////////////////////////////////////////////////////////////////////处理货位管理的仓库

				List<WmAllocationstock> wasYonYouPos=synchroDaoU8.getWasYonYouPos("");//从用友中查询启用货位的库存
				List<WmAllocationstock> wasWmsPos=new ArrayList<WmAllocationstock>();
				String whCodePos=getWarehousePos();
				if(whCodePos.length()>0){
					String posWmsCondition=" whCode in "+whCodePos;
					wasWmsPos=synchroDaoU8.getWasWms(posWmsCondition);//从wms中查询启用货位的库存
				}

				List<WmAllocationstock> toAddPos=new ArrayList<WmAllocationstock>();//对比需要增加的(启用货位)
				List<WmAllocationstock> toDeletePos=new ArrayList<WmAllocationstock>();//对比需要删除的（启用货位）
				List<WmAllocationstock> toUpdtePos=new ArrayList<WmAllocationstock>();//对比需要修改的（启用货位）

				for(WmAllocationstock wy:wasYonYouPos){
					boolean toAdd=true;
					for(WmAllocationstock ww:wasWmsPos){
						if(wy.equalsPos(ww)){
							toAdd=false;

							if(wy.getNumber().compareTo(ww.getNumber()) !=0){
								ww.setNumber(CommonUtil.BigDecimal2BigDecimal(wy.getNumber()));
								toUpdtePos.add(ww);
							}
							break;
						}
					}

					if(toAdd){
						toAddPos.add(wy);
					}

				}

				for(WmAllocationstock ww:wasWmsPos){
					boolean toDelte=true;
					for(WmAllocationstock wy:wasYonYouPos){
						if(ww.equalsPos(wy)){
							toDelte=false;
							break;
						}
					}

					if(toDelte){
						toDeletePos.add(ww);
					}
				}

				//删除
				if(toDeleteNoPos.size()>0){
					synchroDaoU8.deleteWmAllocationstock(toDeleteNoPos);
				}
				if(toDeletePos.size()>0){
					synchroDaoU8.deleteWmAllocationstock(toDeletePos);
				}

				//修改
				if(toUpdateNoPos.size()>0){
					synchroDaoU8.updateWmAllocationstock(toUpdateNoPos);
				}

				if(toUpdtePos.size()>0){
					synchroDaoU8.updateWmAllocationstock(toUpdtePos);
				}

				//增加
				if(toAddNoPos.size()>0){
					synchroDaoU8.insertWmAllocationstock(toAddNoPos,orgGid,sobGid);
				}
				if(toAddPos.size()>0){
					synchroDaoU8.insertWmAllocationstock(toAddPos,orgGid,sobGid);
				}

				//修改关联字段
				synchroDaoU8.updWmAllocationstockInfor();


				System.out.println("同步库存结束");
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		
	}

	
	public String getWarehouseNoPos(){
		String condition=" whPos=0 ";
		List<AaWarehouse> aaWarehouse=synchroDaoU8.getAaWarehouse(condition);//不启用货位的仓库
		
		StringBuffer sb=new StringBuffer();
		for(AaWarehouse wh:aaWarehouse){
			sb.append("'"+wh.getWhcode()+"',");
		}
		String whCode=sb.toString();
		if(whCode.length()>0){
			whCode="("+whCode.substring(0, whCode.length()-1)+")";
		}
		return whCode;
	}
	
	
	//货位管理的仓库
	public String getWarehousePos(){
		String condition=" whPos=1 ";
		List<AaWarehouse> aaWarehouse=synchroDaoU8.getAaWarehouse(condition);//启用货位的仓库
		
		StringBuffer sb=new StringBuffer();
		for(AaWarehouse wh:aaWarehouse){
			sb.append("'"+wh.getWhcode()+"',");
		}
		String whCode=sb.toString();
		if(whCode.length()>0){
			whCode="("+whCode.substring(0, whCode.length()-1)+")";
		}
		return whCode;
	}

	
}
