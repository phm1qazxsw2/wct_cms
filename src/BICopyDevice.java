

import java.io.*;
import java.util.*;
import java.text.*;
import dbo.DataSource;


public class BICopyDevice {
    public BICopyDevice() {
    }
    
    public static void main(String[] args) {
    	/*
    	 * 注意这个程序同时间不能超过一个
    	 * 所以需要检查下
    	 * 
    	 * 先从 150 找到 bi_device_login 找到最后一条，看时间是多少
    	 * 再到 28 找到大于该时间的第一条，看id多少
    	 * 从该id开始，一次100条读入，写到150，并打印
    	 * 
    	 */
    	File copying = null;
    	try {
    		
    		File path = new File(args[0]);
    		
	    	File dsource = new File(path, "WEB-INF/bi.datasource");
	    	copying = new File(path, "WEB-INF/device_copying");
	    	if (copying.exists()) {
	    		throw new Exception("Another is running, cannot run more than 1 instance");
	    	}
	    	copying.createNewFile();
	    	copying.deleteOnExit();
	    	
	        DataSource.setup(dsource.getAbsolutePath());
	        BIDeviceMgr bimgr = new BIDeviceMgr(0);
	        bimgr.setDataSourceName("outdb");	        
	        BIDevice last_device = bimgr.retrieveList("","order by id desc limit 1").get(0);
	        System.out.println(last_device.getId() + ":" + last_device.getMac() + ":" + last_device.getLoginDate());
	        
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        NmpspLoginMgr nmgr = new NmpspLoginMgr(0);
	        nmgr.setDataSourceName("main");
	        NmpspLogin firstrecord = nmgr.retrieveList("challenger_times_date>'" +
	        	sdf.format(last_device.getLoginDate()) + "'", "limit 1").get(0);

	        System.out.println(firstrecord.getId() + ":" + firstrecord.getDevice_mac() + ":" + firstrecord.getChallenger_times_date());
	        int start_id = firstrecord.getId();
	        int cur_id = start_id-1;
	        Date cur_time = null;
	        
	        bimgr.setDataSourceName("outdb");
	        BIDevice device = new BIDevice();
	        while (true) {
	            long t1 = new Date().getTime();	            
	        	List<NmpspLogin> inputs = nmgr.retrieveList("id>" + cur_id, 
	        			"limit 100");
	        	long t2 = new Date().getTime();
	        	if (inputs.size()==0)
	        		break;
	        	start_id = inputs.get(0).getId();
	        	for (int i=0; i<inputs.size(); i++) {
	        		NmpspLogin n = inputs.get(i);
	        		device.setMac(n.getDevice_mac().replace("-",""));
	        		device.setLoginDate(n.getChallenger_times_date());
	        		bimgr.create(device);
	        		cur_id = n.getId();
	        		cur_time = n.getChallenger_times_date();
	        	}	
	        	long t3 = new Date().getTime();
	        	System.out.println("processed "+inputs.size()+" records " + start_id + " to " + cur_id + " time="+ cur_time +" ("+(t3-t1)+"\t"+(t2-t1)+"\t"+(t3-t2)+")");
	        }
	        
	        
	        /*
	        NmpspLoginMgr nmgr = new NmpspLoginMgr(0);
	        List<NmpspLogin> nmlist = nmgr.retrieveList("id > 246029926", "limit 10");
	        for (NmpspLogin n : nmlist) {
	        	System.out.println(n.getDevice_mac() + ":" + n.getChallenger_times_date());
	        }
	        */
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
}
