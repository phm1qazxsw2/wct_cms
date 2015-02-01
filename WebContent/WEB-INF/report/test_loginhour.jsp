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
	String city = hp.getStr("city", "");
	ReportDeviceLoginMgr rdlmgr = new ReportDeviceLoginMgr(0);
	rdlmgr.setSource("db152");


	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	String q = 
	"select DATE_FORMAT(ctime, '%Y-%m-%d') as day, DATE_FORMAT(ctime, '%H') as hour," +
			" sum(count),sum(logins) as logins," +
		"round(sum(logins)/sum(count)) from r3_device_login " +
		"where type=1 ";
	if (city.length()>0)
		q += " and city='"+city+"'"; 
	q += " group by day, hour order by day asc;";  	
	
	
	List<Map<String, Object>> rs = rdlmgr.retrieveListMap(q);
	String[] attrs1 = { "day" };
	Map<String, Map<String,Object>> rs1Map = new SortingMap(rs).doSortKeySingleton(attrs1); 
	String[] attrs2 = { "day", "hour" };
	Map<String, Map<String,Object>> rs2Map = new SortingMap(rs).doSortKeySingleton(attrs2); 
	
	Iterator<String> iter = rs1Map.keySet().iterator();
	
	StringBuffer sb = new StringBuffer();
	sb.append("<table border=1>");
	sb.append("<tr><td>日期</td>");
	for (int i=0; i<24; i++) {
		sb.append("<td>").append(String.format("%02d", i)).append("</td>");
	} 
	sb.append("</tr>");
	while (iter.hasNext()) {
		String d = iter.next();
		sb.append("<tr>");
		sb.append("<td>").append(d).append("</td>");
		for (int i=0; i<24; i++) {
			Map<String,Object> m = rs2Map.get(d + "#" + String.format("%02d", i));
			sb.append("<td>").append((m!=null)?m.get("logins"):"").append("</td>");
		}
		sb.append("</tr>");
	}
	sb.append("</table>");
	out.println(sb.toString());
%>
