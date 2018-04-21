package com.emi.sys.init;

import java.sql.ResultSet;

import com.emi.common.util.DBHelper;



public class Config {

    private static Config config = null;

	public static int PAGESIZE_WEB = 8;// web端每页条数,初始化时会根据配置文件改变
	public static int PAGESIZE_MOB = 10;// 移动端每页条数,初始化时会根据配置文件改变
	public static String[] SYS_NAMES = {};// 参与权限管理的系统名,初始化时会根据配置文件改变
	
	public static String synchroType;// 接口类型
	public static String eaiAddress;//接口地址
	public static String EAICODE;//接口系统编码
	public static String BUSINESSDATABASE;//业务数据名
//	public static String SYSTEMDATABASE;//系统数据名
	static String sql = null;  
    static DBHelper db1 = null;  
    static ResultSet ret = null;
	
    // 2.step
    private Config(){
        System.out.println("CONFIG 初始化!");
        try {
			this.initParameter();
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    // 1.step
    public static void getInstance(){
        if( config == null ){
            config = new Config();            
        }       
    }
    
    // 3.step
    private void initParameter() throws Exception{
        System.out.println("读取配置文件信息 !!!");
		Configuration cfg = new Configuration("text.properties");
		String pageSize_web = cfg.getValue("pageSize.web");
		String pageSize_mob = cfg.getValue("pageSize.mobile");
		String rmSys = cfg.getValue("rm.sysNames");
		
		sql = "select * from YM_Settings ";
		 String synchroType="";
		 String eaiAddress="";
		 String eaiCode="";
       try {
			db1 = new DBHelper(sql);
			ret = db1.pst.executeQuery();
			while (ret.next()) {  
				if(ret.getString(3).equals("synchroType"))
				synchroType = ret.getString(4);  
				if(ret.getString(3).equals("eaiAddress"))
					eaiAddress = ret.getString(4);
				if(ret.getString(3).equals("eaiCode"))
					eaiCode = ret.getString(4);
			}
			ret.close();  
			db1.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
		
		Configuration cfg2 = new Configuration("jdbc.properties");
		String busiNessDataBase=cfg2.getValue("jdbc2.url");
//		String systemDataBase=cfg2.getValue("jdbc3.url");
		String[] busiNessDatabases=busiNessDataBase.split("=");
//		String[] systemDatabases=systemDataBase.split("=");
		
		try {
			Config.PAGESIZE_MOB = Integer.parseInt(pageSize_mob);
			Config.PAGESIZE_WEB = Integer.parseInt(pageSize_web);
			Config.SYS_NAMES = rmSys.split(",");
			
			Config.EAICODE=eaiCode;
			Config.synchroType= synchroType;
			Config.eaiAddress=eaiAddress;
			Config.BUSINESSDATABASE=busiNessDatabases[1]+".dbo.";
//			Config.SYSTEMDATABASE=systemDatabases[1]+".dbo.";
			
		} catch (NumberFormatException e) {
			System.out.println("pageSize值有误，将自动设置成默认值");
		}finally{
			
		}
        
    }


        
}



