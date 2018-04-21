package com.emi.wms.synchro.service;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.json.JSONArray;

import com.emi.common.service.EmiPluginService;
import com.emi.common.util.CommonUtil;
import com.emi.sys.init.Config;
import com.emi.wms.bean.AaWarehouse;
import com.emi.wms.bean.BillType;
import com.emi.wms.bean.OM_MOMain;
import com.emi.wms.bean.OM_MOMaterials;
import com.emi.wms.bean.Procurearrival;
import com.emi.wms.bean.ProcurearrivalC;
import com.emi.wms.bean.SaleSend;
import com.emi.wms.bean.SaleSendC;
import com.emi.wms.bean.Task;
import com.emi.wms.bean.WmAllocationstock;
import com.emi.wms.synchro.dao.SynchroDaoO2O;
import com.emi.wms.synchro.dao.SynchroDaoU890;
import com.emi.wms.util.Constants;
import com.emi.wms.util.DateUtil;

public class SynchroServiceO2Oexternal extends EmiPluginService implements Serializable {

	private static final long serialVersionUID = -2162597524027074596L;
	private SynchroDaoO2O synchroDaoO2O;
	
	public void setSynchroDaoO2O(SynchroDaoO2O synchroDaoO2O) {
		this.synchroDaoO2O = synchroDaoO2O;
	}
	
	
	

	
}
