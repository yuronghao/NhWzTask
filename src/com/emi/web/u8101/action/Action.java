package com.emi.web.u8101.action;

import com.emi.common.action.BaseAction;
import com.emi.web.u8101.service.ServiceU8101;
import com.emi.web.u890.service.ServiceU890;
import net.sf.json.JSONObject;

public class Action extends BaseAction {
    private static final long serialVersionUID = -9174171476588684837L;
    private ServiceU8101 serviceU8101;

    public ServiceU8101 getServiceU8101() {
        return serviceU8101;
    }

    public void setServiceU8101(ServiceU8101 serviceU8101) {
        this.serviceU8101 = serviceU8101;
    }



    /**
    * @Desc 添加采购入库
    * @author yurh
    * @create 2018-03-08 17:15:32
    **/
    public void addPoWareHouse(){

        try {
            String json=getParameter("json");
            JSONObject jobj=JSONObject.fromObject(json);
            boolean ok=serviceU8101.addPoWareHouse(jobj);
            if(ok){
                this.writeSuccess();
            }else{
                this.writeError();
            }

        } catch (Exception e){
            e.printStackTrace();
            this.writeError();
        }
    }



    /**
     * @category 添加材料出库（直接插库）
     *2016 2016年4月22日下午1:02:21
     *void
     *宋银海
     */
    public void addMeterialOut(){

        try {
            String json=getParameter("json");
            JSONObject jobj=JSONObject.fromObject(json);
            boolean ok=serviceU8101.addMeterialOut(jobj);
            if(ok){
                this.writeSuccess();
            }else{
                this.writeError();
            }
        } catch (Exception e){
            e.printStackTrace();
            this.writeError();
        }
    }








}
