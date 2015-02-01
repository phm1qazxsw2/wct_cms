

import java.io.*;
import java.util.*;
import java.text.*;
import dbo.DataSource;
import com.wct.report.*;
import util.*;
import com.wct.report.*;

public class ProcessTmpHour {
    public ProcessTmpHour() {
    }
    
    public static void main(String[] args) {
    	/*
    	 * 
    	 */
    	File process_hour = null;
    	try {
    		
    		File path = new File(args[0]);
    		
	    	File dsource = new File(path, "WEB-INF/bi.datasource");
	    	process_hour = new File(path, "WEB-INF/process_hour");
	    	if (process_hour.exists()) {
	    		throw new Exception("Another is running, cannot run more than 1 instance");
	    	}
	    	process_hour.createNewFile();
	    	process_hour.deleteOnExit();
	    	
	    	if (args.length!=2) {
	    		System.out.println("Usage: java ProcessTmpHour <root> <process_type>");
	    		System.exit(0);
	    	}
	    	String process_type = args[1];
	    	
	    	int end_mark = 0;
	    	int processtype = 0;
	    	int calendar_type = 0;
	    	SimpleDateFormat sdf2 = null;
	    	SimpleDateFormat sdf_full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	
	    	System.out.println("## here1");
	    	if (process_type.equals("hour")) {
	    		processtype = ReportDeviceLogin.TYPE_HOUR;
	    		end_mark = ReportDeviceLogin.TYPE_HOUR_FINISH;
	    		calendar_type = Calendar.HOUR;
	    		sdf2 = new SimpleDateFormat("yyyy-MM-dd HH");
	    	} else if (process_type.equals("day")) {
	    		processtype = ReportDeviceLogin.TYPE_DAY;
	    		end_mark = ReportDeviceLogin.TYPE_DAY_FINISH;
	    		calendar_type = Calendar.DATE;
	    		sdf2 = new SimpleDateFormat("yyyy-MM-dd");
	    	} else if (process_type.equals("week")) {
	    		processtype = ReportDeviceLogin.TYPE_WEEK;
	    		end_mark = ReportDeviceLogin.TYPE_WEEK_FINISH;
	    		calendar_type = Calendar.DATE;
	    		sdf2 = new SimpleDateFormat("yyyy-MM-dd");
	    	} else if (process_type.equals("month")) {
	    		processtype = ReportDeviceLogin.TYPE_MONTH;
	    		end_mark = ReportDeviceLogin.TYPE_MONTH_FINISH;
	    		calendar_type = Calendar.MONTH;
	    		sdf2 = new SimpleDateFormat("yyyy-MM");
	    	} else {
	    		System.out.println("unknown process type: " + process_type);
	    		System.exit(0);
	    	}
	    	
	    	DataSource.setup(dsource.getAbsolutePath());
	    	DataSource.reset(false);
	    	
	    	System.out.println("## here2");
	    	
    		TmpHour5Mgr tmgr = new TmpHour5Mgr(0);
    		tmgr.setSource("db152");
    		ReportDeviceLoginMgr rdmgr = new ReportDeviceLoginMgr(0);
    		rdmgr.setSource("db152");
    		TmpHourReportMgr thrmgr = new TmpHourReportMgr(0);
    		thrmgr.setSource("db152");
    		
    		/*
    		 * 检查上次做到哪，删掉之后没有 end_mark 的从那里开始 
    		 */
    		System.out.println("## here3");
    		while (true) {
	    		List<ReportDeviceLogin> chks = rdmgr.retrieveList("type="+ end_mark, 
	    				"order by id desc limit 1");
	    		if (chks.size()==0) {
	    			System.out.println("cannot find last ending mark for process type:" + process_type);
	    			System.exit(0);
	    		}
	    		if (chks.size()>0) {
	    			// 找到最后一笔之后的删掉重做
	    			System.out.print("deleting for type=" + processtype + " id>" + 
	    					sdf2.format(chks.get(0).getCtime()));
	    			rdmgr.executeSQL("delete from r3_device_login where id>" + chks.get(0).getId() +
	    					" and type=" + processtype);
	    			System.out.println(" deleted..");
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
	    		
	    		List<TmpHour5> t1s = tmgr.retrieveList("ctime>='" + sdf_full.format(d1) + "'", "limit 1"); 
	    		List<TmpHour5> t2s = tmgr.retrieveList("ctime>='" + sdf_full.format(d2) + "'", "limit 1");
	    		if (t1s.size()==0) {
	    			System.out.println("TmpHour3 " + sdf2.format(d1) + " not found, processing stop for " + sdf2.format(d1));
	    			System.exit(0);
	    		}
	    		if (t2s.size()==0) {
	    			System.out.println("TmpHour3 " + sdf2.format(d2) + " not found, processing stop for " + sdf2.format(d1));
	    			System.exit(0);
	    		}
	    		int id1 = t1s.get(0).getId();
	    		int id2 = t2s.get(0).getId();
	    		System.out.print("#1 ");
	    		// 一小时鉴权5次以上的算不正常
	    		String q3 = "(select * from tmp_hour5 where id>=" + id1 + 
	    		                " and id<" + id2 + " and count<5 order by id desc) as t2";
	    		String q2 = "(select device_mac,vendor_code,software_code,chipType,province,city," +
	    		               "communications, sum(count) as sum1 from " + q3 +
	    		                  " group by device_mac) as t";
	    		String q1 = "select vendor_code,software_code,chipType,province,city," +
	    					   "communications,count(*) as count,sum(sum1) as sum from " + q2 +
	    					       " group by vendor_code,software_code,chipType,province,city,communications;";
	    		List<TmpHourReport> hrs = thrmgr.retrieveSQLList(q1);	
	    		System.out.print("#2 ");
	    		ReportDeviceLogin rd =new ReportDeviceLogin();
				rd.setCtime(d1);
				rd.setType(processtype);
	    		for (int i=0; i<hrs.size(); i++) {
	    			TmpHourReport r = hrs.get(i);
	    			rd.setSoftware_code(r.getSoftware_code());
	    			rd.setChipType(r.getChipType());
	    			rd.setProvince(r.getProvince());
	    			rd.setCity(r.getCity());
	    			rd.setVendor_code(r.getVendor_code());
	    			rd.setCommunications(r.getCommunications());
	    			rd.setCount(r.getCount());
	    			rd.setLogins(r.getSum());
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
