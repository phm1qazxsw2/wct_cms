<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.logs.*,dbo.*"%>
<%
	HttpParams hp = new HttpParams(request);		
	Date d = hp.getDate("d", new Date());
	Calendar c = Calendar.getInstance();
	c.setTime(d);
	c.add(Calendar.DATE, 1);
	Date n = c.getTime();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
	
	try {		
		List<LogSmit> logs = new LogSmitMgr(0).retrieveList("ctime>='" + 
			sdf.format(d) + "' and ctime<'" + sdf.format(n) + "'", "");
		StringBuffer sb = new StringBuffer();
		sb.append("<H1>");
		sb.append(sdf.format(d));
		sb.append("</H1>");
		sb.append("<table border=1>");
		sb.append("<tr align=center>");
		sb.append("<td>").append("time").append("</td>");
		sb.append("<td>").append("mac").append("</td>");
		sb.append("<td>").append("ip").append("</td>");
		sb.append("<td>").append("name").append("</td>");
		sb.append("<td>").append("seconds").append("</td>");
		sb.append("</tr>");
		for (int i=0; i<logs.size(); i++) {
			LogSmit s = logs.get(i);
			sb.append("<tr>");
			sb.append("<td>").append(sdf2.format(s.getCtime())).append("</td>");
			sb.append("<td>").append(s.getMac()).append("</td>");
			sb.append("<td>").append(s.getIp()).append("</td>");
			sb.append("<td>").append(s.getVideo()).append("</td>");
			sb.append("<td>").append(s.getSecs()).append("</td>");
			sb.append("</tr>");
		}
		sb.append("</table>");
		out.println(sb.toString());
	}
	catch (Exception e) {
		e.printStackTrace();
	}
%>
