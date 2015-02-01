package com.wct.report;

import java.util.*;

public class VendorNameConverter {
	boolean isVendor = false; 
	Map<String, String> m = new HashMap<String, String>();
	public VendorNameConverter(String queryby) {
		if (queryby.equals("vendor")) {
			isVendor = true;
		}
	}
	public String getRealname(String id) 
		throws Exception
	{
		if (!isVendor) {
			return id;
		}
		try {
			String name = m.get(id);
			if (name==null){
				// 改成用 tmp_hour3 比较快
				TmpHour5Mgr tmgr = new TmpHour5Mgr(0);
				tmgr.setSource("db152");
				TmpHour5 th = tmgr.retrieveList("vendor_code='" + id + "'", "order by id desc limit 1").get(0);
				MacInfo mi = new MacInfoMgr(0).find("device_mac='" + th.getDevice_mac() + "'");
				Vendor v = new VendorMgr(0).retrieveList("vid='" + mi.getVid() + "'", 
					"order by id desc limit 1").get(0);
				name = (v!=null)?v.getName():id;
				if (name.equals("ALi内购机"))
					System.out.println(id + "-" + name + "-" + th.getDevice_mac());
				m.put(id, name);
			}
			return name;
		}
		catch (Exception e) {
			//e.printStackTrace();
			return "(" + id + ")";
		}
	}
}
