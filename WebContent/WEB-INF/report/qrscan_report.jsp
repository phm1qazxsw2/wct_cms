<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.report.*,dbo.*"%>
<%
	HttpParams hp = new HttpParams(request);		
	
	try {
		int type = hp.getInt("t", 12);
		RandioQRMgr rmgr = new RandioQRMgr(0);
		rmgr.setSource("db152");
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		IpMap ipmap = new IpMap();
		List<RandioQR> qrs = rmgr.retrieveList("type=" + type,"");
		String ipstr = new ConsId(qrs).makeIds("getIp");
		Map<String, IpInfo> ipMap = ipmap.get(ipstr, ",");		
		StringBuffer sb2 = new StringBuffer();
		sb2.append("<table border=1>");
		Date d = null;
		for (int i=0; i<qrs.size(); i++) {
			RandioQR r = qrs.get(i);
			IpInfo ip = ipMap.get(r.getIp());
			sb2.append("<tr");
			if (d==null || r.getCtime().getDay()!=d.getDay())
				sb2.append(" bgcolor=\"pink\"");
			sb2.append(">");
			sb2.append("<td>").append(i+1).append("</td>");
			sb2.append("<td>").append(r.getIp()).append("</td>");
			sb2.append("<td>").append((ip!=null && ip.getProvince().length()>0)?(ip.getProvince()+"/"+ip.getCity()):"").append("</td>");
			sb2.append("<td>").append(sdf2.format(r.getCtime())).append("</td>");
			sb2.append("</tr>");
			d = r.getCtime();
		}
		sb2.append("</table>"); 

		out.println("<br><br>QR scan records<br>");
		out.println(sb2.toString());
	}
	catch (Exception e) {
		e.printStackTrace();
	}
%>
