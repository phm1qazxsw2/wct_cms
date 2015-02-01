

import java.io.*;
import java.util.*;
import java.text.*;
import dbo.DataSource;
import com.wct.report.*;

import util.*;

public class ProcessAppLog {
    public ProcessAppLog() {
    }
    
    public static void main(String[] args) {
    	/*
    	 * 
    	 * 
    	 */
    	File process_applog = null;
    	try {
    		
    		File path = new File(args[0]);
    		
	    	File dsource = new File(path, "WEB-INF/bi.datasource");
	    	process_applog = new File(path, "WEB-INF/process_applog");
	    	if (process_applog.exists()) {
	    		throw new Exception("Another is running, cannot run more than 1 instance");
	    	}
	    	process_applog.createNewFile();
	    	process_applog.deleteOnExit();
	    	
	    	DataSource.setup(dsource.getAbsolutePath());
	    	DataSource.reset(false);
	    	
	    	AppLogMgr amgr = new AppLogMgr(0);
	    	amgr.setSource("db150");
	    	
	    	TmpAppLogMgr tmgr = new TmpAppLogMgr(0);
	    	tmgr.setSource("db152");
	    	
	    	
	    	/*d
	    	MacInfoMgr mimgr = new MacInfoMgr(0);
	    	IpMap ipMap = new IpMap();
	    	*/
	    	
	    	SimpleDateFormat sdf_full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	
	    	List<TmpAppLog> tls = tmgr.retrieveList("", "order by id desc limit 1");
	    	int start_id = 0;
	    	if (tls.size()>0) {
	    		start_id = tls.get(0).getOrg_id();
	    	}
	    	long max_duration = 24*60*60;
	    	Map<String, List<TmpHour5>> thMap = null;
	    	IpMap ipmap = new IpMap();
	    	
	    	while (true) {
	    		List<AppLog> als = null;
	    		try {
	    			als = amgr.retrieveList("id>" + start_id, "limit 100");
	    		}
	    		catch (Exception e) {
	    			start_id += 100; // 跳过100
	    			continue;
	    		}
	    		if (als.size()==0) {
	    			System.out.println("no more app log to process.");
	    			System.exit(0);
	    		}
	    		
	    		long t1 = new Date().getTime();
	    		SoftJoin sj = new SoftJoin(als, "getMac", true);
	    		Map<String, DeviceInfo> deviceMap = new SortingMap(sj.doJoin(
	    			new DeviceInfo(), "device_mac", "", 0)).doSortSingleton("getDevice_mac");
	    		long t2 = new Date().getTime();
	    		System.out.print(" ## 2(" + (t2-t1) + ")");
	    		Map<String, MacInfo> macMap = new SortingMap(sj.doJoin(
	    			new MacInfo(), "device_mac", "", 0)).doSortSingleton("getDevice_mac");
	    		long t3 = new Date().getTime();
	    		System.out.print(" ## 3(" + (t3-t2) + ")");
	    				
	    		TmpAppLog tl = new TmpAppLog();
	    		int skip = 0;
	    		for (int i=0; i<als.size(); i++) {
	    			AppLog a = als.get(i);
	    			String mac = a.getMac().toUpperCase();
	    			if (a.getLeaveTime()==null || a.getEnterTime()==null)
	    				continue;
	    			long duration = (a.getLeaveTime().getTime()-a.getEnterTime().getTime())/1000;
	    			if (duration>max_duration || duration<0)
	    				continue;
	    			
	    			MacInfo mi = macMap.get(mac);
	    			DeviceInfo di = deviceMap.get(mac);
	    			IpInfo ip = null;
	    			try { ip = ipmap.findIpInfo(di.getDevice_ip()); } catch (Exception e) {}
		    		tl.setOrg_id(a.getId());
		    		tl.setAppName(a.getAppName());
		    		tl.setChipType((mi!=null)?mi.getChipType():"");
		    		tl.setProvince((ip!=null)?ip.getProvince():"");
		    		tl.setCity((ip!=null)?ip.getCity():"");
		    		tl.setCommunications((ip!=null)?ip.getCommunications():"");
	    			tl.setEnterTime(a.getEnterTime());
	    			tl.setLeaveTime(a.getLeaveTime());
	    			tl.setDuration(duration);
	    			tl.setDevice_ip((di!=null)?di.getDevice_ip():"");
	    			tl.setMac(mac);
	    			tl.setVendor_code((di!=null)?di.getVendor_code():"");
	    			tl.setSoftware_code((di!=null)?di.getSoftware_code():"");
	    			tmgr.create(tl);
	    			start_id = a.getId();
	    		}
	    		
	    		long total_time = new Date().getTime() - t1;
	    		System.out.print("#applog proccessed till " + start_id + "("+total_time+")\n");
	    	}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
}
