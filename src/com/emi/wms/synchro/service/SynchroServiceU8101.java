package com.emi.wms.synchro.service;

import com.emi.cache.dao.CacheCtrldao;
import com.emi.common.service.EmiPluginService;
import com.emi.common.util.CommonUtil;
import com.emi.sys.init.Config;
import com.emi.wms.bean.*;
import com.emi.wms.synchro.dao.SynchroDaoU8101;
import com.emi.wms.synchro.dao.SynchroDaoU890;
import com.emi.wms.util.Constants;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.*;

public class SynchroServiceU8101 extends EmiPluginService implements Serializable {
    private static final long serialVersionUID = -2162597524027074596L;
    private SynchroDaoU8101 synchroDaoU8101;
    private CacheCtrldao cacheCtrldao;

    public SynchroDaoU8101 getSynchroDaoU8101() {
        return synchroDaoU8101;
    }

    public void setSynchroDaoU8101(SynchroDaoU8101 synchroDaoU8101) {
        this.synchroDaoU8101 = synchroDaoU8101;
    }

    public CacheCtrldao getCacheCtrldao() {
        return cacheCtrldao;
    }

    public void setCacheCtrldao(CacheCtrldao cacheCtrldao) {
        this.cacheCtrldao = cacheCtrldao;
    }

    public Map getOrgGid(){
        return synchroDaoU8101.getOrgGid();
    }

    public Map getSobGid(){
        return synchroDaoU8101.getSobGid();
    }



    /**
     * @category 同步存货档案
     *2016 2016年3月2日下午2:31:01
     *void
     *宋银海
     */
    public void synchroInventory(String orgGid,String sobGid){
        synchroDaoU8101.deleteInventory();//删除存货档案
        synchroDaoU8101.addInventory();//增加存货档案
        synchroDaoU8101.updInventory();//修改存货档案
        synchroDaoU8101.updInventoryInfor();//修改相关属性
        synchroDaoU8101.updInventoryOrgSob(orgGid, sobGid);//指定帐套组织
    }


    /**
     * @category 同步存货分类
     *2016 2016年3月2日下午1:27:45
     *void
     *宋银海
     */
    public void synchroInventoryClass(String orgGid,String sobGid){
        synchroDaoU8101.deleteInventoryClass();//删除存货分类
        synchroDaoU8101.addInventoryClass();//增加存货分类
        synchroDaoU8101.updInventoryClass();//修改存货分类
        synchroDaoU8101.updInventoryClassParentGid();//指定父id
        synchroDaoU8101.updInventoryClassOrgSob(orgGid, sobGid);//指定帐套 组织
    }



    /**
     * @category 同步单位
     *2016 2016年3月3日上午8:47:33
     *void
     *宋银海
     */
    public void synchroUnit(String orgGid,String sobGid){
        synchroDaoU8101.deleteUnit();//删除单位
        synchroDaoU8101.addUnit();//增加单位
        synchroDaoU8101.updUnit();//修改单位
        synchroDaoU8101.updUnitOrgSob(orgGid, sobGid);//指定组织 帐套
    }


    /**
     * @category 同步仓库
     *2016 2016年3月2日下午5:02:21
     *void
     */
    public void synchroWareHouse(String orgGid,String sobGid){
        synchroDaoU8101.deleteWareHouse();//删除仓库
        synchroDaoU8101.addWareHouse();//增加仓库
        synchroDaoU8101.updWareHouse();//修改仓库
        synchroDaoU8101.updWareHouseOrgSob(orgGid, sobGid);
    }


    /**
     * @category 同步货位
     *2016 2016年3月2日下午5:16:36
     *void
     */
    public void synchroGoodsAllocation(String orgGid,String sobGid){
        //synchroDaoU8.deleteGoodsAllocation();//删除货位
        synchroDaoU8101.addGoodsAllocation();//增加货位
        synchroDaoU8101.updGoodsAllocation();//修改货位
        synchroDaoU8101.updGoodsAllocationInfor();//修改相关属性
        synchroDaoU8101.updGoodsAllocationOrgSob(orgGid, sobGid);
    }



    /**
    * @Desc 同步采购类型
    * @author yurh
    * @create 2018-02-05 10:46:47
    **/
    public void synchroPurchaseType(String orgGid,String sobGid){
        synchroDaoU8101.deletePurchaseType();//删除采购类型
        synchroDaoU8101.addPurchaseType();//增加采购类型
        synchroDaoU8101.updPurchaseType();//修改采购类型
        synchroDaoU8101.updPurchaseTypeOrgSob(orgGid, sobGid);
    }



    /**
    * @Desc 同步收发类别（出入库类别）
    * @author yurh
    * @create 2018-02-20 17:37:22
    **/
    public void synchroRdStyle(String orgGid,String sobGid){
        synchroDaoU8101.deleteRdStyle();//删除收发类别
        synchroDaoU8101.addRdStyle();//增加收发类别
        synchroDaoU8101.updRdStyle();//修改收发类别
        synchroDaoU8101.updRdStyleOrgSob(orgGid, sobGid);//指定组织 帐套
    }
//    public void synchroRdStyle(String orgGid,String sobGid){
//        synchroDaoU8101.deleteRdStyle();//删除采购类型
//        synchroDaoU8101.addRdStyle();//增加采购类型
//        synchroDaoU8101.updRdStyle();//修改采购类型
//        synchroDaoU8101.updRdStyleOrgSob(orgGid, sobGid);
//
//    }


    /**
    * @Desc 同步人员档案
    * @author yurh
    * @create 2018-02-20 17:46:38
    **/
    public void synchroPerson(String orgGid,String sobGid){
        synchroDaoU8101.deletePerson();//删除人员
        synchroDaoU8101.addPerson();//增加人员
        synchroDaoU8101.updPerson();//修改人员
        synchroDaoU8101.updPersonInfor();//修改相关属性
        synchroDaoU8101.updPersonOrgSob(orgGid, sobGid);//指定组织 帐套
    }

    /**
    * @Desc 同步部门档案
    * @author yurh
    * @create 2018-02-20 17:50:23
    **/
    public void synchroDepartment(String orgGid,String sobGid){
        synchroDaoU8101.deleteDepartment();//删除部门
        synchroDaoU8101.addDepartment();//增加部门
        synchroDaoU8101.uptDepartment();//修改部门
        synchroDaoU8101.uptDepartmentParentGid();//修改自关联字段
        synchroDaoU8101.updDepartmentOrgSob(orgGid, sobGid);//指定帐套组织
    }



    /**
    * @Desc 同步供应商分类档案
    * @author yurh
    * @create 2018-02-20 18:32:21
    **/
    public void synchroProviderClass(String orgGid,String sobGid){
        synchroDaoU8101.deleteProviderClass();//删除供应商分类档案
        synchroDaoU8101.addProviderClass();//增加供应商分类档案
        synchroDaoU8101.uptProviderClass();//修改供应商分类档案
//      synchroDaoU8101.uptDepartmentParentGid();//修改自关联字段
        synchroDaoU8101.updProviderClassOrgSob(orgGid, sobGid);//指定帐套组织
    }





    /**
    * @Desc 同步供应商档案
    * @author yurh
    * @create 2018-02-20 18:17:36
    **/
    public void synchroProvider(String orgGid,String sobGid){
        synchroDaoU8101.deleteProvider();//删除供应商
        synchroDaoU8101.addProvider();//增加供应商
        synchroDaoU8101.updProvider();//修改供应商
        synchroDaoU8101.updProviderOrgSob(orgGid, sobGid);//指定组织 帐套
    }



    /**
    * @Desc 同步用户
    * @author yurh
    * @create 2018-03-03 10:27:24
    **/
    public void synchroUser(String orgGid,String sobGid){
        synchroDaoU8101.addUser();//增加用户
        synchroDaoU8101.updUser();//修改用户
        synchroDaoU8101.updUserOrgSob(orgGid, sobGid);//指定组织 帐套
    }


    /**
    * @Desc 同步采购订单
    * @author yurh
    * @create 2018-02-26 09:42:49
    **/
    public void synchroProOrder(String orgGid,String sobGid) throws Exception {

        String condition=" and ((ISNULL(CAST(iQuantity as DECIMAL(28,6)), 0)-ISNULL(CAST(iReceivedQTY as DECIMAL(28,6)), 0)) <> 0 and ps.id not in (select autoidForSynchro from WM_ProcureOrder_C)) ";
        List<ProcureorderC> plsToAdd=synchroDaoU8101.getProcureorderC3(condition);//获得需要增加的订单子表

          condition=" and pc.autoidForSynchro not in (select ID  from "+ Config.BUSINESSDATABASE+"PO_Podetails ) ";
        List<ProcureorderC> plsToDelete=synchroDaoU8101.getProcureorderC1(condition);//获得需要删除的订单子表


        condition=" and  isnull(p.stateForSynchro,-1)<>isnull(pv.iverifystateex,-1) ";//获得需要修改的订单主表 (审核状态发生变化的记录)
        List<Procureorder> plToAdd=synchroDaoU8101.getPl(condition);

        //删除任务 billIdentityToDelete单据身份
        Set<String> billIdentityToDelete=new HashSet<String>();

        for(ProcureorderC pc:plsToAdd){
            if(!CommonUtil.isNullObject(pc.getBillIdentity())){
                billIdentityToDelete.add(pc.getBillIdentity());
            }
        }

        for(ProcureorderC pc:plsToDelete){
            if(!CommonUtil.isNullObject(pc.getBillIdentity())){
                billIdentityToDelete.add(pc.getBillIdentity());
            }
        }

        for(Procureorder p:plToAdd){
            if(!CommonUtil.isNullObject(p.getBillCode())){
                billIdentityToDelete.add(p.getBillCode());
            }
        }


        ///////////////////////////////////////////////////////////////////////////////////////////////////////处理任务（只有增加 和 删除）
        Iterator<String> iterator=billIdentityToDelete.iterator();
        StringBuffer sbf=new StringBuffer();
        while(iterator.hasNext()){
            sbf.append("'"+iterator.next()+"',");
        };
        String billIdentity=sbf.toString();
        if(billIdentity.length()>0){
            billIdentity=billIdentity.substring(0, billIdentity.length()-1);
            billIdentity="("+billIdentity+")";
        }

        List<ProcureorderC> plsToAddSecond=null;
        if(!CommonUtil.isNullObject(billIdentity)){
            condition=" and p.cPOID in "+billIdentity;
            plsToAddSecond=synchroDaoU8101.getProcureorderC3(condition);//获得订单对应的订单子表
        }



        Iterator<String> iteratorTask=billIdentityToDelete.iterator();
        List<Task> tasks=new ArrayList<Task>();
        while(iteratorTask.hasNext()){
            Task task=null;
            boolean toCreateTaskIn=true;//创建入库任务
            boolean auditFlag=false;//审核标志
            String billIdent=iteratorTask.next();
            String cbusTypeName="";//业务类型名称
            String billId=null;
            Timestamp completetime=null;



            for(ProcureorderC pcAdd:plsToAddSecond){//遍历有变动的U8采购订单子表（1、订单子表发生删除 2、订单主表状态发生改变  影响本地任务）
                if(billIdent.equalsIgnoreCase(pcAdd.getBillIdentity())){

                    if(cbusTypeName.equalsIgnoreCase("")){
                        cbusTypeName=pcAdd.getCbusTypeName();
                    }

                    if(pcAdd.getIverifystateex().intValue()==2){
                        auditFlag=true;
                    }

                    if(billId==null){
                        billId=pcAdd.getBillIdentity();
                        completetime=pcAdd.getBillIDate();
                    }

                }

            }



            if(auditFlag&&toCreateTaskIn){
                task=new Task();
                if(cbusTypeName.equalsIgnoreCase("普通采购") || cbusTypeName.equalsIgnoreCase("代管采购") || cbusTypeName.equalsIgnoreCase("固定资产") ){
                    task.setTaskTypeCodeForSynchro(Constants.TASKTYPE_CGRK);
                    task.setBillGidSource(Constants.BILLGIDSOURCE_CGD);
                }
                task.setCompletetime(completetime);
                task.setDistributeTime(new Timestamp(new Date().getTime()));
                task.setState(0);
                task.setBillIdentityForSynchro(billId);
                task.setBillCode(billId);
                task.setSobgid(sobGid);
                task.setOrggid(orgGid);
                tasks.add(task);
            }
        }

        //////////////////////////////////////////////////////////////////////////////////////////处理订单主表
        synchroDaoU8101.deleteProOrder();//删除订单主表
        synchroDaoU8101.addProOrder();//增加订单主表
        synchroDaoU8101.updProOrder();//修改订单主表
        synchroDaoU8101.updProOrderInfor();//修改相关字段

        //////////////////////////////////////////////////////////////////////////////////////////处理订单子表
        synchroDaoU8101.deleteProOrderC();//删除订单子表
        synchroDaoU8101.addProOrderC();//增加订单子单
        synchroDaoU8101.updProOrderC();//修改订单子单
        synchroDaoU8101.updProOrderCInfor();//修改关联字段
        synchroDaoU8101.updProOrderOrgSob(orgGid, sobGid);


        //根据billIdentityToDelete 删除任务
        if(!CommonUtil.isNullObject(billIdentity)){
            condition=" billIdentityForSynchro in "+billIdentity ;
            synchroDaoU8101.deleteTask(condition);
        }

        //生成入库任务
        if(tasks.size()>0){
            synchroDaoU8101.createTask(tasks);
            synchroDaoU8101.updateTaskInfor();//跟WM_BillType 做关联
            synchroDaoU8101.updateTaskProOrderBillGid();//修改采购出库对应billGid
        }

        //删除已经在u8中完成的任务
        synchroDaoU8101.deleProOrderInvalidTask();



    }

























}
