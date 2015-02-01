

import java.io.*;
import java.util.*;
import java.text.*;
import dbo.DataSource;
import com.wct.report.*;

import util.*;

public class ProcessChallengerInfo5 {
    public ProcessChallengerInfo5() {
    }
    
    public static void main(String[] args) {
    	/*
    	 * 
    	 * 
    	 */
    	File process_login = null;
    	try {
    		
    		File path = new File(args[0]);
    		
	    	File dsource = new File(path, "WEB-INF/bi.datasource");
	    	process_login = new File(path, "WEB-INF/process_login5");
	    	if (process_login.exists()) {
	    		throw new Exception("Another is running, cannot run more than 1 instance");
	    	}
	    	process_login.createNewFile();
	    	process_login.deleteOnExit();
	    	
	    	DataSource.setup(dsource.getAbsolutePath());
	    	DataSource.reset(false);
	    	Calendar c = Calendar.getInstance();
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH");
	    	SimpleDateFormat sdf3 = new SimpleDateFormat("dd HH:mm:ss");
	    	
	    	TmpHour5Mgr tmgr = new TmpHour5Mgr(0);
	    	tmgr.setSource("db152");
	    	
	    	/*
	    	 * 找目标表（tmp_hour5）最后一条。找到后以该小时为准汇入。
	    	 * 如果里面没资料，就不用管
	    	 * 如果里面有资料，要先把那一小时的资料删掉（因为要重新汇）
	    	 */
	    	Date d = null;
    		System.out.print("## a ");
	    	List<TmpHour5> tmps = tmgr.retrieveList("", "order by id desc limit 1");
    		System.out.print("## b tmps.size=" + tmps.size());
	    	if (tmps.size()>0) {
	    		d = tmps.get(0).getCtime();
	    		System.out.print("## c " + sdf2.format(tmps.get(0).getCtime()));
	    		tmps = tmgr.retrieveList("ctime>='" + 
	    			sdf2.format(tmps.get(0).getCtime()) + "'", "limit 1");
	    		System.out.println("#found tmp_hour5 last record " + 
	    				sdf2.format(d) + " deleting from " + tmps.get(0).getId());
	    		tmgr.executeSQL("delete from tmp_hour5 where id>=" + tmps.get(0).getId());
	    		System.out.println("#done deleting");
	    	}
	    	if (d==null) {
	    		System.out.println("cannot find export point in tmp_hour5");
	    		System.exit(0);
	    	}
	    	IpMap ipmap = new IpMap();
    		c.setTime(d);
	    	while (true) {
	    		TChallenger t1 = new TChallengerMgr(0).retrieveList("challenger_times_date>='"
	    	    	+ sdf2.format(d) + "'", "limit 1").get(0);
	    		Date old = d;
	    		c.add(Calendar.HOUR, 1);
	    		d = c.getTime();
	    		if (d.compareTo(new Date())>=0)
	    			break;
	    		System.out.print("## 1 ");
	    		TChallenger t2 = new TChallengerMgr(0).retrieveList("challenger_times_date>'"
			    	+ sdf2.format(d) + "'", "limit 1").get(0);
	    		List<TChallenger> hourlys = new TChallengerMgr(0).
	    			retrieveList("id>=" + t1.getId() + " and id<" + t2.getId(), "");
	    		System.out.print("## 2("+hourlys.size()+")");
	    		
	    		String macs = new ConsId(hourlys).makeStringIds("getDevice_mac");
	    		System.out.print("## 2a ");
	    		SoftJoin sj = new SoftJoin(hourlys, "getDevice_mac", true);
    			Map<String, DeviceInfo> deviceMap = new SortingMap(sj.doJoin(
    				new DeviceInfo(), "device_mac", "", 0)).doSortSingleton("getDevice_mac");
    			System.out.print("## 3 ");
    			Map<String, MacInfo> macMap = new SortingMap(sj.doJoin(
    				new MacInfo(), "device_mac", "", 0)).doSortSingleton("getDevice_mac");
    			System.out.print("## 4 ");
    			String ipstr = new ConsId(hourlys).makeIds("getDevice_ip");
    			Map<String, IpInfo> ipMap = ipmap.get(ipstr, ",");
    			System.out.print("## 5 ");
	    		Map<String, List<TChallenger>> chaMap = new SortingMap(hourlys).
	    			doSortA("getDevice_mac");
	    		System.out.println("## 6 ");
	    		System.out.println("[" + sdf3.format(new Date()) + "]" + 
	    				sdf2.format(old) + " t1=" + t1.getId() + " t2=" + t2.getId()
	    			+ " size=" + chaMap.size());
	    		
	    		TmpHour5 td = new TmpHour5();
	    		Iterator<String> iter = chaMap.keySet().iterator();
	    		while (iter.hasNext()) {
	    			String mac = iter.next();
	    			List<TChallenger> chas = chaMap.get(mac);
	    			DeviceInfo di = deviceMap.get(mac);
    				MacInfo mi = macMap.get(mac);
    				IpInfo ip = ipMap.get(chas.get(0).getDevice_ip());
    				
	    			td.setDevice_mac(mac);
	    			td.setDevice_ip(chas.get(0).getDevice_ip());
	    			td.setChipType((mi==null)?"":mi.getChipType());
	    			td.setCity((ip==null)?"":ip.getCity());
	    			td.setProvince((ip==null)?"":ip.getProvince());
	    			td.setSoftware_code((di==null)?"":di.getSoftware_code());
	    			td.setVendor_code((di==null)?"":di.getVendor_code());
	    			td.setCommunications((ip==null)?"":ip.getCommunications());
	    			td.setCount(chas.size());
	    			td.setCtime(old);
	    			tmgr.create(td);
	    		}
	    	}
    	}
    	catch (Exception e) {
    		e.printStackTrace();
    	}
    }
    
}
