package com.wct.report;

import java.util.*;
import java.net.*;

public class IpMap 
{
   static List<IpInfo> ipdb1 = null; 
   public IpMap() {
	   long t1 = new Date().getTime();
	   if (ipdb1==null) {
		   ipdb1 = new IpInfoMgr().retrieveList("","order by begIpNum asc");
	   }
   }
   
   public static long inet_aton(Inet4Address add) {
		byte[] bytes = add.getAddress();
		long result = 0;
		for (byte b : bytes) {
			if ((b & 0x80L) != 0) {
				result += 256L + b;
			} else {
				result += b;
			}
			result <<= 8;
		}
		result >>= 8;
		return result;
	}
   
   public IpInfo findIpInfo(String ip4) 
   	   throws Exception
   {
	   InetAddress ip_addr = InetAddress.getByName(ip4.trim());
	   long iplong = inet_aton((Inet4Address)ip_addr);
	   int l = 0;
	   int h = ipdb1.size()-1;
	   int m = 0;
	   int smaller = 0;
	   while (l<h) {
		   m = (l+h)/2;
		   long v = ipdb1.get(m).getBegIpNum();
		   if (v==iplong) {
			   smaller = m;
			   break;
		   }
		   if (v<iplong) {
			   l = m + 1;
			   if (ipdb1.get(l).getBegIpNum()>iplong) {
				   smaller = m;
				   break;
			   }
		   }
		   if (v>iplong) {
			   h = m -1 ;
			   if (ipdb1.get(h).getBegIpNum()<iplong) {
				   smaller = h;
				   break;
			   }
		   }
	   }
	   
	   if (ipdb1.get(smaller).getEndIpNum()>=iplong)
		   return ipdb1.get(smaller);
	   return null;
   }
   
   public Map<String, IpInfo> get(String ipstr, String sep) 
	   throws Exception
   {
	   String[] ips = ipstr.split(sep);
	   List<String> iplist = new ArrayList<String>();
	   for (int i=0; i<ips.length; i++) {
		   iplist.add(ips[i]);
	   }
	   return get(iplist);
   }
   
   public Map<String, IpInfo> get(List<String> ips) 
   	   throws Exception
   {
	   Map<String, IpInfo> ret = new HashMap<String, IpInfo>(ips.size());
	   for (int i=0; i<ips.size(); i++) {
		   String ip = ips.get(i);
		   try {
			   IpInfo info = findIpInfo(ip);
			   /*
			   if (info==null) {
				   System.out.println(ip + "-> null");
			   }
			   else {
				   System.out.println(ip + "->" + info.getProvince() + ":" + info.getCity());
			   }
			   */
			   ret.put(ip, info);
		   }
		   catch (Exception e) {
			   System.out.println("Warning: error in get ip:" + e.getMessage() + " skip this ip lookup");
		   }
	   }
	   return ret;
   }
}
