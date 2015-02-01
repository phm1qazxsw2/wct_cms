

import java.io.*;
import java.util.*;
import java.text.*;
import dbo.DataSource;
import com.wct.report.*;

import util.*;

public class ProcessAppLogTest {
    public ProcessAppLogTest() {
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
	    	
	    	TmpHour5Mgr t5mgr = new TmpHour5Mgr(0);
	    	t5mgr.setSource("db152");
	    	
	    	/*
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
	    	int old_begin_id = 0;
	    	int old_end_id = 0;
	    	Map<String, List<TmpHour5>> thMap = null;
	    	List<TmpHour5> ths = null;
	    	while (true) {
	    		List<AppLog> als = amgr.retrieveList("id>" + start_id, "limit 100");
	    		if (als.size()==0) {
	    			System.out.println("no more app log to process.");
	    			System.exit(0);
	    		}
	    		/*
	    		 * 找到这100笔的最小和最大时间，从tmp_hour5一次选出来，从里面找资料
	    		 */
	    		/*
	    		Date d1 = als.get(0).getLeaveTime();
	    		Date d2 = als.get(0).getEnterTime();
	    		int a1 = 0;
	    		int a2 = 0;
	    		for (int i=0; i<als.size(); i++) {
	    			AppLog a = als.get(i);
	    			long duration = (a.getLeaveTime().getTime()-a.getEnterTime().getTime())/1000;
	    			if (duration>max_duration)
	    				continue;
	    			if (a.getEnterTime().compareTo(d1)<0) {
	    				d1 = a.getEnterTime();
	    				a1 = a.getId();
	    			}
	    			if (a.getEnterTime().compareTo(d2)>0) {
	    				d2 = a.getEnterTime();
	    				a2 = a.getId();
	    			}
	    		}
	    		
	    		int beginId = 0;
	    		int endId = 0;
	    		try {
	    			// 从 enterTime 的前6小时开始找登入信息
	    			Calendar c = Calendar.getInstance();
	    			c.setTime(d1);
	    			c.add(Calendar.HOUR, -6);
	    			beginId = t5mgr.retrieveList("ctime>='" + sdf_full.format(c.getTime()) + "'", "limit 1").get(0).getId();
	    		}
	    		catch (Exception e) {
	    			System.out.println("cannot find beginId in tmp_hour5 for log id:" + a1 + " " + sdf_full.format(d1));
	    			System.exit(0);
	    		}
	    		try {
	    			endId = t5mgr.retrieveList("ctime>='" + sdf_full.format(d2) + "'", "limit 1").get(0).getId();
	    		}
	    		catch (Exception e) {
	    			System.out.println("cannot find endId in tmp_hour5 for log id:" + a2 + " " + sdf_full.format(d2));
	    			System.exit(0);
	    		}
	    		
	    		String macs = new ConsId(als).makeStringIds("getMac");
	    		
	    		long t1 = new Date().getTime();
	    		System.out.print(" ## 1");
	    		//List<TmpHour5> ths = t5mgr.retrieveList("id>=" + beginId + 
	    		//	" and id<" + endId + " and device_mac in (" + macs + ")", "");
	    		if (thMap==null || beginId<old_begin_id || endId>old_end_id) {
		    		ths = t5mgr.retrieveList("id>=" + beginId + " and id<" + endId, "");
		    		long t2 = new Date().getTime();
		    		System.out.print(" ## 2(" + (t2-t1) + ",listsz="+ths.size()+")");
		    		thMap = new SortingMap(ths).doSortA("getDevice_mac");
		    		long t3 = new Date().getTime();
		    		System.out.print(" ## 3(" + (t3-t2) + ",mapsz="+thMap.size()+")");
	    		}
	    		else {
	    			System.out.print(" ## 4(same thMap)");
	    		}
	    		*/
	    		
	    		TmpAppLog tl = new TmpAppLog();
	    		int skip = 0;
	    		long t0 = new Date().getTime();
	    		for (int i=0; i<als.size(); i++) {
	    			AppLog a = als.get(i);
	    			long duration = (a.getLeaveTime().getTime()-a.getEnterTime().getTime())/1000;
	    			if (duration>max_duration)
	    				continue;
	    			/*
	    			ths = thMap.get(a.getMac().toUpperCase());
	    			if (ths==null) {
	    				System.out.print("\n#warning#:" + a.getId() + " " + a.getMac() +
	    						" " + sdf_full.format(a.getEnterTime()) +
 	    						" not found in tmp_hour5\n");
	    				skip ++;
	    				if (skip==als.size()) {
	    					System.out.println("No tmp_hour5 found any applog, please update tmp_hour5 first. exit.");
	    					System.exit(0);
	    				}
	    				continue;
	    			}
	    			*/
	    			long t1 = new Date().getTime();
	    			TmpHour5 th = t5mgr.retrieveList("device_mac='"+a.getMac()+"'", "").get(0);
	    			long t2 = new Date().getTime();
	    			System.out.print(" " + i + "("+(t2-t1)+")");
		    		tl.setOrg_id(a.getId());
		    		tl.setAppName(a.getAppName());
		    		tl.setChipType(th.getChipType());
		    		tl.setProvince(th.getProvince());
		    		tl.setCity(th.getCity());
		    		tl.setCommunications(th.getCommunications());
	    			tl.setEnterTime(a.getEnterTime());
	    			tl.setLeaveTime(a.getLeaveTime());
	    			tl.setDuration(duration);
	    			tl.setDevice_ip(th.getDevice_ip());
	    			tl.setMac(th.getDevice_mac());
	    			tl.setVendor_code(th.getVendor_code());
	    			tl.setSoftware_code(th.getSoftware_code());
	    			tmgr.create(tl);
	    			start_id = a.getId();
	    		}
	    		/*
	    		old_begin_id = beginId;
	    		old_end_id = endId;
	    		long t4 = new Date().getTime();
	    		System.out.print(" ## 4");
	    		*/
	    		long total_time = new Date().getTime() - t0;
	    		System.out.print("#applog proccessed till " + start_id + "("+total_time+")\n");
	    	}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
}
