
import com.wct.ad.*;
import java.io.*;
import java.util.*;
import java.text.*;
import dbo.DataSource;
import com.wct.report.*;

import util.*;

public class ProcessAdCollect {
    public ProcessAdCollect() {
    }
    
    public static void main(String[] args) {
    	
    	List<String> queue = new ArrayList<String>();
    	List<String> queue2 = new ArrayList<String>();
    	
    	File process_adcollect = null;
    	try {
    		
    		if (args.length!=2) {
    			System.out.println("Usage: java ProcessAdCollect <path> <collect_ad_id>");
    			return;
    		}
    		File path = new File(args[0]);
    		
	    	File dsource = new File(path, "WEB-INF/datasource");
	    	process_adcollect = new File(path, "WEB-INF/adcollect");
	    	if (process_adcollect.exists()) {
	    		throw new Exception("Another is running, cannot run more than 1 instance");
	    	}
	    	process_adcollect.createNewFile();
	    	process_adcollect.deleteOnExit();
	    	
	    	DataSource.setup(dsource.getAbsolutePath());
	    	DataSource.reset(false);
	    	
	    	int collectId = Integer.parseInt(args[1]);
	    	
	    	SimpleDateFormat sdf = new SimpleDateFormat("dd HH:mm:ss");
	    	
	    	AdCollect collect = new AdCollectMgr(0).retrieveList("id=" +collectId + " and status=1","").get(0);
	    	String[] urls = collect.getReporting_urls().split("\n");
	    	
	    	System.out.println("## Collector urls");
	    	for (int j=0; j<urls.length; j++) {
				String url = urls[j];
				System.out.println(url);
	    	}
	    	
	    	for (int i=0; i<20; i++) {
	    		new Thread(new ReportThread(i,queue)).start();
	    	}
	    	
	    	for (int i=0; i<20; i++) {
	    		new Thread(new ReportThread(i,queue2)).start();
	    	}
	    	
	    	try {
	    		while (true) {
	    			int last_record_id = collect.getLast_record_id();
	    			List<AdRecord> records = new AdRecordMgr(0).retrieveList("id>" + 
	    					last_record_id + " and ad_id in (" + collect.getAd_ids() + ")", "limit 100");
	    			// System.out.println("[" + sdf.format(new Date()) + "]## collecting for " + collect.getAd_id() + "," + records.size() + " records found from " + last_record_id);
	    			if (records.size()>0) {
		    			try {
			    			for (int i=0; i<records.size(); i++) {
			    				AdRecord r = records.get(i);
			    				collect.setLast_record_id(r.getId());
			    				// 如果超过5分钟 就不上报了
			    				if ((new Date().getTime() - r.getAdvert_time().getTime())<5*60000) {
				    				String md5 = util.Md5Util.Md5(r.getDevice_code());
				    				String ip = r.getDevice_ip();
				    				for (int j=0; j<urls.length; j++) {
				    					String url = urls[j];
				    					url = url.replace("%mac%", md5).replace("%ip%", ip);
				    					if (j%urls.length==0) {
					    					synchronized (queue) {
					    						queue.add(url);
					    					}
				    					}
				    					if (j%urls.length==1) {
					    					synchronized (queue2) {
					    						queue2.add(url);
					    					}
				    					}
				    				}
				    				System.out.println("## reporting ad " + r.getId());			    				
			    				}
			    				else
			    					System.out.println("## skipping ad " + r.getId());	
			    			}
		    			}
		    			finally {
		    				new AdCollectMgr(0).save(collect);
		    			}
	    			}
		    		Thread.sleep(500);
	    		}
	    	}
	    	catch (Exception e) {
	    		e.printStackTrace();
	    	}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    

    static class ReportThread implements Runnable {
    	
    	int id;
    	List<String> q;
    	
    	public ReportThread(int id, List<String>queue) {
    		this.id = id;
    		q = queue;
    	}

    	public void run() {
    		while (true) {
	    		try {
	    			String url = null;
	    			synchronized(q) {
	    				if (q.size()>0)
	    					url = q.remove(0);
	    			}
	    			if (url!=null) {
	    				System.out.println(this.id + ":" + url);
	    				String result = URLConnector.getContent(url, 5000, "UTF-8");
	    			}
    				else
    					Thread.sleep(500);
	    		}
	    		catch (Exception e) {
	    			e.printStackTrace();
	    		}
    		}
    	}
    }
}

