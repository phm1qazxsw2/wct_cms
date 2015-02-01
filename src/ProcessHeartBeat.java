

import java.io.*;
import java.util.*;
import java.text.*;
import dbo.DataSource;
import com.wct.report.*;


import util.*;

import com.alibaba.odps.tunnel.*;
import com.alibaba.odps.tunnel.io.Record;
import com.alibaba.odps.tunnel.io.RecordWriter;

import com.wct.report.*;

public class ProcessHeartBeat {
	
	private static String endpoint = "http://dt.odps.aliyun.com";
	private static String accessId = "dgFIHNLaIjVB8gx6";
	private static String accessKey = "9FCmsha42GkUHq2fSMXOpfLEJ42OAH";

	private static String project = "widecloud";
	private static String table = "device_beat";
	
    public ProcessHeartBeat() {
    }
    
    public static void main(String[] args) {
    	/*
    	 * 
    	 */
    	File process_heartbeat = null;
    	try {
    		if (args.length!=2) {
    			System.out.println("Usage: java ProcessHeartBeat .. <28#234233432>");
    			System.out.println("Usage: java ProcessHeartBeat .. <150#234233432>");
    			System.exit(0);
    		}
    		File path = new File(args[0]);
    		
	    	File dsource = new File(path, "WEB-INF/bi.datasource");
	    	
	    	DataSource.setup(dsource.getAbsolutePath());
	    	DataSource.reset(false);
	    	
	    	HeartBeatMgr hbmgr = new HeartBeatMgr(0);
	    	String filename = null;
	    	String db = null;
    		String[] ids = args[1].split("#");
    		if (ids[0].equals("150")) {
    			hbmgr.setSource("db150");
    			filename = "process_heartbeat150";
    			db = "150";
    		}
    		else {
    			filename = "process_heartbeat28";
    			db = "28";
    		}
    		
    		process_heartbeat = new File(path, "WEB-INF/" + filename);
	    	if (process_heartbeat.exists()) {
	    		throw new Exception("Another is running, cannot run more than 1 instance");
	    	}
	    	process_heartbeat.createNewFile();
	    	process_heartbeat.deleteOnExit();
	    	
    		long start_id =  Long.parseLong(ids[1]);    	
	    	
	    	IpMap ipmap = new IpMap();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			Account account = new Account("aliyun",accessId,accessKey);
			Configuration cfg = new Configuration(account, endpoint);
			DataTunnel tunnel = new DataTunnel(cfg);
			Upload up = null;
			String old_part = "";
			RecordWriter writer = null;
			RecordSchema schema = null;
    		
    		while (true) {
	    		List<HeartBeat> hbs = hbmgr.retrieveList("id>" + start_id, "limit 1000");
	    		if (hbs.size()==0) {
	    			System.out.println("no heartbeat found, finish for this time");
	    			break;
	    		}
	    		SoftJoin sj = new SoftJoin(hbs, "getDevice_mac", true);
	    		List<MacInfo> dms = sj.doJoin(new MacInfo(), "device_mac", "", 0);
	    		Map<String, MacInfo> macMap = new SortingMap(dms).doSortSingleton("getDevice_mac");
	    		Map<String, DeviceInfo> deviceMap = new SortingMap(sj.doJoin(
	    				new DeviceInfo(), "device_mac", "", 0)).doSortSingleton("getDevice_mac");
	    		String ipstr = new ConsId(hbs).makeIds("getDevice_ip");
    			Map<String, IpInfo> ipMap = ipmap.get(ipstr, ",");
    			
	    		for (int i=0; i<hbs.size(); i++) {
	    			HeartBeat hb = hbs.get(i);
	    			MacInfo mi = macMap.get(hb.getDevice_mac());
	    			DeviceInfo di = deviceMap.get(hb.getDevice_mac());
	    			IpInfo ip = ipMap.get(hb.getDevice_ip());
	    			
	    			String dest_part = "d=" + sdf.format(hb.getActive_times_date());
	    			if (old_part.compareTo(dest_part)!=0) {
	    				if (writer!=null) {
	    					writer.close();
	    		            Long[] blocks = {(long) 0};
	    					up.complete(blocks);
	    				}
	    				up = tunnel.createUpload(project, table, dest_part);
	    				writer = up.openRecordWriter(0);
	    				schema = up.getSchema();
	    				old_part = dest_part;
	    			}
	    			String id = up.getUploadId();
	    			Record r = new Record(schema.getColumnCount());
	    			r.setString(0, db + "#" + hb.getId()); //id
	    			r.setString(1, hb.getDevice_mac()); //mac
	    			r.setString(2, (mi!=null)?mi.getChipType():""); //software_code
	    			r.setString(3, (di!=null)?di.getVendor_code():"");	//vendor_code
	    			r.setString(4, hb.getDevice_ip()); //ip
	    			r.setString(5, (ip!=null)?ip.getProvince():""); //province
	    			r.setString(6, (ip!=null)?ip.getCity():""); //city
	    			r.setString(7, (ip!=null)?ip.getCommunications():""); //comm
	    			r.setDatetime(8, hb.getActive_times_date()); //time
	    			writer.write(r);
	    			
	    			start_id = hb.getId();
	    		}
	    		System.out.println("# processed till start_id=" + start_id);
    		}
    		if (writer!=null) {
				writer.close();
	            Long[] blocks = {(long) 0};
				up.complete(blocks);
			}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
}
