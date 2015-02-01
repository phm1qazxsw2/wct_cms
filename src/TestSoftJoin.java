

import java.io.*;
import java.util.*;
import java.text.*;
import dbo.DataSource;
import com.wct.report.*;

import util.*;

public class TestSoftJoin {
    public TestSoftJoin() {
    }
    
    public static void main(String[] args) {
    	/*
    	 * 
    	 * 
    	 */
    	try {
    		
    		File path = new File(args[0]);
    		
	    	File dsource = new File(path, "WEB-INF/bi.datasource");
	    	
	    	
	    	DataSource.setup(dsource.getAbsolutePath());
	    	DataSource.reset(false);
	    	
    		List<TChallenger> tests = new TChallengerMgr(0).retrieveList("","order by id desc limit 10");
    		SoftJoin sj = new SoftJoin(tests, "getDevice_mac", true);
    		List<DeviceInfo> dis = sj.doJoin(new DeviceInfo(), "device_mac", "", 0);
    		System.out.println("# 1");
    		for (int i=0; i<dis.size(); i++) {
    			DeviceInfo di = dis.get(i);
    			System.out.println(di.getDevice_id() + ":" + di.getDevice_mac() + ":" + di.getSoftware_code());
    		}
    		System.out.println("# 2");
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
}
