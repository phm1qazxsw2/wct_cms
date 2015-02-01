<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,java.net.*,com.wct.report.*,dbo.*"%>
<%
	HttpParams hp = new HttpParams(request);		
	String ipstr = hp.getStr("ips", "");
	int n = hp.getInt("n", 1000);
	
	try {
		String[] ips = ipstr.split("\n");
		List<String> iplist = new ArrayList<String>();
		/*
		for (int i=0; i<ips.length; i++) {
			if (ips[i].trim().length()==0)
				continue;
			iplist.add(ips[i]);
		}
		*/
		List<DeviceInfo> dinfos = new DeviceInfoMgr(0).retrieveList("", "limit " + n);
		
		for (int i=0; i<dinfos.size(); i++) {
			iplist.add(dinfos.get(i).getDevice_ip());
		}
		
		
		IpMap m = new IpMap();
		Map<String, IpInfo> ipmap = m.get(iplist);
		StringBuffer sb = new StringBuffer();
		sb.append("<table border=1>");
		int i = 1;
		for (String ip : iplist) {
			IpInfo info = ipmap.get(ip);
			sb.append("<tr>");
			sb.append("<td>").append(i++).append("</td>");
			sb.append("<td>").append(ip).append("</td><td>");
			if (info!=null)
				sb.append(info.getId() + ":" + m.inet_aton((Inet4Address)InetAddress.getByName(ip)) + ":" + info.getProvince() + ":" + info.getCity() + ":" + info.getCommunications());
			sb.append("</td></tr>");		
		}
		out.println(sb.toString());
	}
	catch (Exception e) {
		e.printStackTrace();
	}
%>
