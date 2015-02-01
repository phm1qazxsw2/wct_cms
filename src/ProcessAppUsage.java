

import java.io.*;
import java.util.*;
import java.text.*;
import dbo.DataSource;
import com.wct.report.*;

import util.*;

public class ProcessAppUsage {
    public ProcessAppUsage() {
    }
    
    public static void main(String[] args) {
    	/*
    	 * 
    	 * 
    	 */
    	File process_appusage = null;
    	try {
    		
    		File path = new File(args[0]);
    		
	    	File dsource = new File(path, "WEB-INF/bi.datasource");
	    	process_appusage = new File(path, "WEB-INF/process_appusage");
	    	if (process_appusage.exists()) {
	    		throw new Exception("Another is running, cannot run more than 1 instance");
	    	}
	    	process_appusage.createNewFile();
	    	process_appusage.deleteOnExit();
	    	
	    	DataSource.setup(dsource.getAbsolutePath());
	    	DataSource.reset(false);
	    	
	    	TmpAppLogMgr tmgr = new TmpAppLogMgr(0);
	    	tmgr.setSource("db152");
	    	
	    	ReportAppUsageMgr ramgr = new ReportAppUsageMgr(0);
	    	ramgr.setSource("db152");
	    	
	    	SimpleDateFormat sdf_full = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	
	    	List<TmpAppLog> tls = tmgr.retrieveList("processed!=0", "limit 1");
	    	int start_id = 0;
	    	if (tls.size()>0) {
	    		start_id = tls.get(0).getId();
	    	}
	    	
	    	for (int c=0; c<1; c++) {
	    		long t1 = new Date().getTime();
	    		int end_id = start_id + 1000;
	    		String ctime_date = "DATE_FORMAT(ctime,'%Y-%m-%d') as ctime";
	    		String groupby = "`appName`,`vendor_code`,`chipType`,`software_code`,`province`,`city`"
	    			+ ",`communications`,`ctime`";
	    		List<ReportAppUsage> raus = ramgr.retrieveSQLList(
	    				" select " + groupby.replace("`ctime`", ctime_date) + ",sum(duration) as seconds " + 
	    				" from tmp_applog where id>=" + start_id + " and id<" + end_id +
	    				" group by " + groupby) ;
	    		if (raus.size()==0) {
	    			System.out.println("no more app log to process.");
	    			System.exit(0);
	    		}

	    		// 找到这段时间有那几天，把这些 ReportAppUsage 都抓进来Map查
	    		Map<String, ReportAppUsage> usageMap = null;
	    		{
		    		Map<String, Date> tmpdMap = new HashMap<String, Date>();
		    		for (int i=0; i<raus.size(); i++) {
		    			tmpdMap.put(sdf_full.format(raus.get(i).getCtime()), raus.get(i).getCtime());
		    		}
		    		StringBuffer sb = new StringBuffer();
		    		Iterator<String> iter = tmpdMap.keySet().iterator();
		    		while (iter.hasNext()) {
		    			if (sb.length()>0)
		    				sb.append(",");
		    			sb.append("'").append(sdf_full.format(iter.next())).append("'");
		    		}
		    		usageMap = new SortingMap(ramgr.retrieveList("ctime in (" + 
		    				sb.toString() + ")", "")).doSortSingleton("getKey");
	    		}
	    		
	    		for (int i=0; i<raus.size(); i++) {
	    			ReportAppUsage rau = raus.get(i);
	    			if (usageMap.get(rau.getKey())==null) {
	    				ramgr.create(rau);
	    				usageMap.put(rau.getKey(), rau);
	    			}
	    			else {
	    				String update_sql = "update r3_app_usage set seconds=seconds+" + rau.getSeconds() +
	    					" where " + rau.getKeyIdentifier();
	    				ramgr.executeSQL(update_sql);
	    			}
	    		}
	    		
	    		// 每天多少 mac，使用 seconds
	    		String q1 = "(select mac,province,city,communications,"+
	    			"DATE_FORMAT(enterTime,'%Y-%m-%d') as d, sum(duration) as s "+
	    			"from tmp_applog where id>=1 and id<1000000 "+
	    			"group by mac,d) as t";
	    		String q2 = "select province,city,communications,d,count(*) as c," + 
	    			"sum(s) as s2 from " + q1 +
	    			" group by province,city,communications,d";
	    		List<Map<String,Object>> rs = tmgr.retrieveListMap(q2);
	    		
	    		
	    		tmgr.executeSQL("update tmp_applog set processed=1 where id>" + 
	    			start_id + " and id<" + end_id);
    			start_id = end_id;
	    		long total_time = new Date().getTime() - t1;
	    		System.out.print("#applog proccessed till " + start_id + "("+total_time+")\n");
	    	}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
}
