

import java.io.*;
import java.util.*;
import java.text.*;
import dbo.DataSource;
import com.wct.report.*;
import util.*;
import com.wct.report.*;

public class ProcessDeviceInfo {
    public ProcessDeviceInfo() {
    }
    
    public static void main(String[] args) {
    	/*
    	 * 
    	 */
    	File process_device_info = null;
    	try {
    		
    		File path = new File(args[0]);
    		
	    	File dsource = new File(path, "WEB-INF/bi.datasource");
	    	process_device_info = new File(path, "WEB-INF/process_device_info");
	    	if (process_device_info.exists()) {
	    		throw new Exception("Another is running, cannot run more than 1 instance");
	    	}
	    	process_device_info.createNewFile();
	    	process_device_info.deleteOnExit();
	    	
	    	if (args.length!=1) {
	    		System.out.println("Usage: java ProcessTmpDevice <root>");
	    		System.exit(0);
	    	}
	    	
	    	DataSource.setup(dsource.getAbsolutePath());
	    	DataSource.reset(false);
	    	
	    	DeviceInfoMgr dmgr = new DeviceInfoMgr(0);
    		TmpDeviceMgr tmgr = new TmpDeviceMgr(0);
    		tmgr.setSource("db152");
    		
    		// 从上次做到的 mac 开始
    		int start_id = 0;
    		List<TmpDevice> tds = tmgr.retrieveList("", "order by id desc limit 10");
    		for (int i=0; i<tds.size(); i++) {
    			try {
    				DeviceInfo di = dmgr.find("device_mac='" + tds.get(i).getDevice_mac() + "'");
    				start_id = di.getDevice_id();
    				break;
    			}
    			catch (Exception e) {
    			}
    		}
    		if (start_id==0) {
    			System.out.println("Error: start_id=0");
    			System.exit(0);
    		}
    			
    		while (true) {
	    		List<DeviceInfo> dis = dmgr.retrieveList("device_id>" + start_id, "limit 1000");
	    		if (dis.size()==0) {
	    			System.out.println("no bigger device_id found, finish for this time");
	    			System.exit(0);
	    		}
	    		SoftJoin sj = new SoftJoin(dis, "getDevice_mac", true);
	    		List<MacInfo> dms = sj.doJoin(new MacInfo(), "device_mac", "", 0);
	    		Map<String, MacInfo> macMap = new SortingMap(dms).doSortSingleton("getDevice_mac");
	    		TmpDevice td = new TmpDevice();
	    		for (int i=0; i<dis.size(); i++) {
	    			DeviceInfo d = dis.get(i);
	    			td.setDevice_mac(d.getDevice_mac());
	    			td.setSoftware_code(d.getSoftware_code());
	    			td.setVendor_code(d.getVendor_code());
	    			MacInfo mi = macMap.get(d.getDevice_mac());
	    			String chipType = (mi!=null)?mi.getChipType():"";
	    			td.setChipType(chipType);
	    			td.setActivate_time(d.getCreate_date());
	    			tmgr.create(td);
	    			start_id = d.getDevice_id();
	    		}
	    		System.out.println("# processed till start_id=" + start_id);
    		}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
}
