

import java.io.*;
import java.util.*;
import java.text.*;
import dbo.DataSource;
import com.wct.report.*;
import util.*;

public class BIImportDeviceActivate {
    public BIImportDeviceActivate() {
    }
    
    public static void main(String[] args) {
    	/*
    	 * 注意这个程序同时间不能超过一个
    	 * 所以需要检查下
    	 * 
    	 */
    	File device_activate_running = null;
    	try {
    		
    		File path = new File(args[0]);
    		
	    	File dsource = new File(path, "WEB-INF/bi.datasource");
	    	device_activate_running = new File(path, "WEB-INF/device_activate_running");
	    	if (device_activate_running.exists()) {
	    		throw new Exception("Another is running, cannot run more than 1 instance");
	    	}
	    	device_activate_running.createNewFile();
	    	device_activate_running.deleteOnExit();
	    	
	        DataSource.setup(dsource.getAbsolutePath());
	        
	        DeviceInfoMgr dmgr = new DeviceInfoMgr(0);
	        MacInfoMgr mmgr = new MacInfoMgr(0);
	        IpInfoMgr iimgr = new IpInfoMgr(0);
	        
	        int start = -1;
	        int len = 100;
	        while (true) {
	        	List<DeviceInfo> dis = dmgr.retrieveList("device_id>" + 
	        			start, "order by device_id limit " + len);
	        	if (dis.size()==0)
	        		break;
	        	
	        	String macstr = new ConsId(dis).makeStringIds("getDevice_mac");
	        	Map<String, MacInfo> macMap = new SortingMap(mmgr.retrieveList(
	        			"device_mac in (" + macstr + ")", "")).
	        				doSortSingleton("getDevice_mac");

	        	for (DeviceInfo di:dis) {
	        		MacInfo mi = macMap.get(di.getDevice_mac());
	        		if (di.getDevice_ip()!=null && di.getDevice_ip().length()>10) {
	        			/*
		        		List<IpInfo> iis = iimgr.retrieveList(
		        				"begIpNum<=INET_ATON('"+di.getDevice_ip()+
		        				"') and endIpNum>=INET_ATON('"+di.getDevice_ip()+"')","");
		        		if (iis.size()>1) {
		        			for (int j=0; j<iis.size(); j++)
		        				System.out.print(iis.get(j).getProvince()+iis.get(j).getCity() + " ");
		        			System.out.print("|");
		        		}
		        		*/
	        			
	        		}
	        	}
	        	start = dis.get(dis.size()-1).getDevice_id();
	        	System.out.println("processed " + len + " records " + start);
	        }
	        
	        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
}
