

import java.io.*;
import java.util.*;
import java.text.*;
import dbo.DataSource;
import com.wct.logs.*;

import util.*;

public class ProcessLogMerge {
    public ProcessLogMerge() {
    }
        
    static Map<String, LogMerge> mm = new LinkedHashMap<String, LogMerge>(10000);
    
    public static boolean canMerge(LogMerge lm, LogWatch lw) {
    	boolean c1 = (lm.getApp()==null&&lw.getApp()==null) || (lm.getApp()!=null && lw.getApp()!=null && lm.getApp().equals(lw.getApp()));
    	if (!c1)
    		return false;
    	boolean c2 = (lm.getChannel()==null&&lw.getChannel()==null) || (lm.getChannel()!=null && lw.getChannel()!=null && lm.getChannel().equals(lw.getChannel()));
    	if (!c2)
    		return false;
    	boolean c3 = (lm.getVideo()==null&&lw.getVideo()==null) || (lm.getVideo()!=null && lw.getVideo()!=null && lm.getVideo().equals(lw.getVideo()));
    	if (!c3)
    		return false;
    	
    	
    	
    	return c1 && c2 && c3;
    }
    
    public static void main(String[] args) {
    	/*
    	 * 
    	 * 
    	 */
    	File process_logmerge = null;
    	try {
    		
    		File path = new File(args[0]);
    		
	    	File dsource = new File(path, "WEB-INF/datasource");
	    	process_logmerge = new File(path, "WEB-INF/process_logmerge");
	    	if (process_logmerge.exists()) {
	    		throw new Exception("Another is running, cannot run more than 1 instance");
	    	}
	    	process_logmerge.createNewFile();
	    	process_logmerge.deleteOnExit();
	    	
	    	LogMergeId mid = new LogMergeIdMgr(0).retrieveList("","").get(0);
	    	int id = mid.getLast_id();
	    	int len = 1000;
	    	
	    	// 处理一条新纪录的时，找到上一条记录(同一个mac的)，如果是同一次的节目(channel,app,video相同),就累加上去 
	    	// 累加放在内存，如果内存资料多到一个程度，处理完的id和目前内存的状态一起写入到数据库。
	    	// 所以还没写到数据库的下次会重新处理（数据库当做commit）
	    	
	    	while (true) {
	    		List<LogWatch> logs = new LogWatchMgr(0).retrieveList("id>" + id, "limit " + len);
	    		if (logs.size()==0)
	    			break;
	    		
	    		for (int i=0; i<logs.size(); i++) {
	    			LogWatch log = logs.get(i);
	    			String mac = log.getMac();
	    			
	    			LogMerge lm = mm.get(mac);
	    			if (lm==null) {
	    				// 如果不在mm里面，从db找上一个mac出来，看能否merge（这里需要优化）
	    				List<LogMerge> mlist = new LogMergeMgr(0).retrieveList("mac='" + 
	    						mac + "'", "order by id desc limit 1");
	    				if (mlist.size()==0) {
	    					lm = new LogMerge();
	    					lm.setCreate(true);
	    				}
	    				else {
	    					lm = mlist.get(0);
	    					if (canMerge(lm, log)) {
	    						//lm.set
	    					}
	    					else {
	    						
	    					}
	    				}
	    					
	    					
	    				}
	    			}
	    			
	    			
	    			//id = log.getId();
	    		}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
}
