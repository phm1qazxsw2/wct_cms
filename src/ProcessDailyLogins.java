

import java.io.*;
import java.util.*;
import java.text.*;
import dbo.DataSource;
import com.wct.report.*;
import util.*;
import com.wct.report.*;

public class ProcessDailyLogins {
    public ProcessDailyLogins() {
    }
    
    public static void main(String[] args) {
    	/*
    	 * 
    	 */
    	File process_dailylogins = null;
    	try {
    		
    		File path = new File(args[0]);
    		
	    	File dsource = new File(path, "WEB-INF/bi.datasource");
	    	process_dailylogins = new File(path, "WEB-INF/process_dailylogins");
	    	if (process_dailylogins.exists()) {
	    		throw new Exception("Another is running, cannot run more than 1 instance");
	    	}
	    	process_dailylogins.createNewFile();
	    	process_dailylogins.deleteOnExit();
	    	
	    	if (args.length!=1) {
	    		System.out.println("Usage: java ProcessDailyLogins <root>");
	    		System.exit(0);
	    	}
	    	
	    	DataSource.setup(dsource.getAbsolutePath());
	    	DataSource.reset(false);
	    	
	    	System.out.println("## here2");
	    	
    		TmpHour5Mgr tmgr = new TmpHour5Mgr(0);
    		tmgr.setSource("db152");
    	
    		DailyLoginsMgr dlmgr = new DailyLoginsMgr(0);
    		dlmgr.setSource("db152");
    		
    		SimpleDateFormat sdf_full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    		
    		/*
    		 * 检查上次做到哪，删掉之后没有 end_mark 的从那里开始 
    		 */
    		while (true) {
	    		List<DailyLogins> chks = dlmgr.retrieveList("", "order by id desc limit 1");
	    		int start_id = 0;
	    		int end_id = 0;
	    		Date d = chks.get(0).getCtime();
    			Calendar c = Calendar.getInstance();
    			c.setTime(d);
    			c.add(Calendar.DATE, 1);
    			d = c.getTime();
    			TmpHour5 tmp1 = tmgr.retrieveList("ctime>='"+
    					sdf_full.format(d)+"'", "limit 1").get(0);
    			c.add(Calendar.DATE, 1);
    			TmpHour5 tmp2 = tmgr.retrieveList("ctime>='"+
    					sdf_full.format(c.getTime())+"'", "limit 1").get(0);
    			start_id = tmp1.getId();
    			end_id = tmp2.getId();
	    			
	    		if (start_id>=end_id) {
	    			System.out.println("start_id=" + start_id + ">" + "end_id=" + end_id);
	    			break;
	    		}
	    		
	    		long t1 = new Date().getTime();
	    		System.out.print("#1 ");
	    		String q2 = "(select device_mac,vendor_code,software_code,chipType,province," +
	    			"communications,city,sum(count) as logins " +
	    				" from tmp_hour5 where id>=" + start_id + " and id<" + end_id + 
	    				         " and count<10 group by device_mac) as t1";
	    		String q1 = "select 1 as id, vendor_code,software_code,chipType,province,communications,city," +
	    			"count(*) as count, sum(logins) as logins, ceiling(sum(logins)/count(*)) as times," +
	    			"'" + sdf_full.format(d) + "' as ctime " +
	    				" from " + q2 +
	    				" group by software_code,chipType,province,communications,city;";
	    		
	    		List<DailyLogins> dls = dlmgr.retrieveSQLList(q1);	
	    		for (int i=0; i<dls.size(); i++) {
	    			DailyLogins dl = dls.get(i);
	    			dlmgr.create(dl);
	    		}
	    		long t2 = new Date().getTime();
	    		System.out.print("#2 (" + (t2-t1) + ")");
				System.out.println("# processed " + sdf_full.format(d));
    		}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
}
