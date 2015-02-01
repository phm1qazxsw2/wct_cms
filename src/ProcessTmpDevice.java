

import java.io.*;
import java.util.*;
import java.text.*;
import dbo.DataSource;
import com.wct.report.*;
import util.*;
import com.wct.report.*;

public class ProcessTmpDevice {
    public ProcessTmpDevice() {
    }
    
    public static void main(String[] args) {
    	/*
    	 * 
    	 */
    	File process_tmp_device = null;
    	try {
    		
    		File path = new File(args[0]);
    		
	    	File dsource = new File(path, "WEB-INF/bi.datasource");
	    	process_tmp_device = new File(path, "WEB-INF/process_tmp_device");
	    	if (process_tmp_device.exists()) {
	    		throw new Exception("Another is running, cannot run more than 1 instance");
	    	}
	    	process_tmp_device.createNewFile();
	    	process_tmp_device.deleteOnExit();
	    	
	    	if (args.length!=2) {
	    		System.out.println("Usage: java ProcessTmpDevice <root> <process_type>");
	    		System.exit(0);
	    	}
	    	String process_type = args[1];
	    	
	    	int end_mark = 0;
	    	int processtype = 0;
	    	int calendar_type = 0;
	    	SimpleDateFormat sdf2 = null;
	    	
	    	if (process_type.equals("hour")) {
	    		processtype = ReportDevice.TYPE_HOUR;
	    		end_mark = ReportDevice.TYPE_HOUR_FINISH;
	    		calendar_type = Calendar.HOUR;
	    		sdf2 = new SimpleDateFormat("yyyy-MM-dd HH");
	    	} else if (process_type.equals("day")) {
	    		processtype = ReportDevice.TYPE_DAY;
	    		end_mark = ReportDevice.TYPE_DAY_FINISH;
	    		calendar_type = Calendar.DATE;
	    		sdf2 = new SimpleDateFormat("yyyy-MM-dd");
	    	} else if (process_type.equals("week")) {
	    		processtype = ReportDevice.TYPE_WEEK;
	    		end_mark = ReportDevice.TYPE_WEEK_FINISH;
	    		calendar_type = Calendar.DATE;
	    		sdf2 = new SimpleDateFormat("yyyy-MM-dd");
	    	} else if (process_type.equals("month")) {
	    		processtype = ReportDevice.TYPE_MONTH;
	    		end_mark = ReportDevice.TYPE_MONTH_FINISH;
	    		calendar_type = Calendar.MONTH;
	    		sdf2 = new SimpleDateFormat("yyyy-MM");
	    	} else {
	    		System.out.println("unknown process type: " + process_type);
	    		System.exit(0);
	    	}
	    	
	    	DataSource.setup(dsource.getAbsolutePath());
	    	DataSource.reset(false);
	    	
    		TmpDeviceMgr tmgr = new TmpDeviceMgr(0);
    		tmgr.setSource("db152");
    		ReportDeviceActivateMgr rdmgr = new ReportDeviceActivateMgr(0); 
    		rdmgr.setSource("db152");
    		TmpDeviceReportMgr thrmgr = new TmpDeviceReportMgr(0);
    		thrmgr.setSource("db152");
    		
    		/*
    		 * 检查上次做到哪，删掉之后没有 end_mark 的从那里开始 
    		 */
    		while (true) {
	    		List<ReportDeviceActivate> chks = rdmgr.retrieveList("type="+ end_mark, 
	    				"order by id desc limit 1");
	    		if (chks.size()==0) {
	    			System.out.println("cannot find last ending mark for process type:" + process_type);
	    			System.exit(0);
	    		}
	    		if (chks.size()>0) {
	    			// 找到最后一笔之后的删掉重做
	    			rdmgr.executeSQL("delete from r2_device_activate where id>" + chks.get(0).getId() +
	    					" and type=" + processtype);
	    			System.out.println("deleted for type=" + processtype + " id>" + 
	    					sdf2.format(chks.get(0).getCtime()));
	    		}
	    		Calendar c = Calendar.getInstance();
	    		Date d1=null, d2=null;
	    		System.out.print("ptype=" + processtype + " ");
	    		if (processtype==1 || processtype==2 || processtype==4) {
		    		c.setTime(chks.get(0).getCtime());
		    		c.add(calendar_type, 1);
		    		d1 = c.getTime();
		    		c.add(calendar_type, 1);
		    		d2 = c.getTime();
	    		}
	    		else {
	    			c.setTime(chks.get(0).getCtime());
	    			c.add(calendar_type, 1);
	    			while (c.get(Calendar.DAY_OF_WEEK)!=2) { // find next monday beginning
	    				c.add(Calendar.DATE, 1);
	    			}
	    			d1 = c.getTime();
	    			System.out.println("#week starting:" +  sdf2.format(d1));
	    			c.add(Calendar.DATE, 7);
	    			d2 = c.getTime();
	    		}
	    		
	    		List<TmpDevice> t1s = tmgr.retrieveList("activate_time>='" + sdf2.format(d1) + "'", "limit 1"); 
	    		List<TmpDevice> t2s = tmgr.retrieveList("activate_time>='" + sdf2.format(d2) + "'", "limit 1");
	    		if (t1s.size()==0) {
	    			System.out.println("TmpDevice " + sdf2.format(d1) + " not found, processing stop for " + sdf2.format(d1));
	    			System.exit(0);
	    		}
	    		if (t2s.size()==0) {
	    			System.out.println("TmpDevice " + sdf2.format(d2) + " not found, processing stop for " + sdf2.format(d1));
	    			System.exit(0);
	    		}
	    		int id1 = t1s.get(0).getId();
	    		int id2 = t2s.get(0).getId();
	    		System.out.print("#1 ");
	    		List<TmpDeviceReport> hrs = thrmgr.retrieveSQLList(
	    			"select vendor_code,software_code,chipType,count(*) as count " +
	    		    "from (select vendor_code,software_code,chipType from tmp_device_info where id>=" + id1 + " and id<" + id2 + " group by device_mac) as t " + 
	    			"group by vendor_code,software_code,chipType;");	
	    		System.out.print("#2 ");
	    		ReportDeviceActivate rd =new ReportDeviceActivate();
				rd.setCtime(d1);
				rd.setType(processtype);
	    		for (int i=0; i<hrs.size(); i++) {
	    			TmpDeviceReport r = hrs.get(i);
	    			rd.setSoftware_code(r.getSoftware_code());
	    			rd.setChipType(r.getChipType());
	    			rd.setVendor_code(r.getVendor_code());
	    			rd.setCount(r.getCount());
	    			rdmgr.create(rd);
	    		}
	    		rd.setType(end_mark);
				rdmgr.create(rd);
				System.out.println("# processed " + sdf2.format(d1));
    		}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
}
