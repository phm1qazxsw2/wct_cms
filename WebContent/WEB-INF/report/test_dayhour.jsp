<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.report.*,dbo.*"%>
<%!
	public String getWeekday(int w) {
		switch (w) {
		case 1: return "日"; 
		case 2: return "一"; 
		case 3: return "二"; 
		case 4: return "三"; 
		case 5: return "四"; 
		case 6: return "五"; 
		case 7: return "六"; 
		}
		return "";
	}
%>
<%
	HttpParams hp = new HttpParams(request);
	Date day = hp.getDate("day", null);
	ReportDeviceLoginMgr rdlmgr = new ReportDeviceLoginMgr(0);
	rdlmgr.setSource("db152");
	if (day==null) {
		out.println("day is missing");
		return;
	}
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	String q = "select id from r3_device_login where ctime>='" + 
		sdf.format(day) + "' and type=1 limit 1";
	Map<String, Object> tmp1 = rdlmgr.retrieveListMap(q).get(0);
	long id1 = ((Long)tmp1.get("id")).longValue();
	Calendar c = Calendar.getInstance();
	c.setTime(day);
	c.add(Calendar.DATE, 1);
	q = "select id from r3_device_login where ctime>='" + 
		sdf.format(c.getTime()) + "' and type=1 limit 1";
	Map<String, Object> tmp2 = rdlmgr.retrieveListMap(q).get(0);
	long id2 = ((Long)tmp2.get("id")).longValue();
	

	q = "select DATE_FORMAT(ctime, '%H') as hour, count(*) as count, sum(logins) as logins" + 
		" from r3_device_login where id>=" + id1 + 
		" and id<" + id2 + " and type=1 group by hour;";
	
	List<Map<String, Object>> rs = rdlmgr.retrieveListMap(q);
	
	StringBuffer sb = new StringBuffer();
	sb.append("<table border=1>");
	sb.append("<tr><td>DeviceLogin</td>");
	for (int i=0; i<rs.size(); i++) {
		sb.append("<td>").append(rs.get(i).get("count")).append("</td>");
	} 
	sb.append("</tr>");
	sb.append("</table>");
	out.println(sb.toString());
%>	
