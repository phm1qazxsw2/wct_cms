<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.report.*,dbo.*"%>
<%
	HttpParams hp = new HttpParams(request);	
	int type = hp.getInt("type", 3); // week
	String model = hp.getStr("m", "");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH");
	SimpleDateFormat formatsdf = null;
	Date day = null;
	if (type==1) {
		formatsdf = sdf2;
		day = formatsdf.parse(hp.getStr("day", formatsdf.format(new Date())));
	}
	else {
		formatsdf = sdf;
		day = formatsdf.parse(hp.getStr("day", formatsdf.format(new Date())));
	}
	
	ReportDeviceMgr rdmgr = new ReportDeviceMgr(0);
	rdmgr.setSource("db152");
	
	String q = "select software_code as ver, sum(count) as c, " + 
		"DATE_FORMAT(ctime, '%Y-%m-%d') as d from r3_device_login " +
		"where type=3 and software_code like '05%'";
		
	if (model.length()>0)
		q += " and software_code like '%" + model + "'";
	
	q += " group by ver, d order by d asc, ver asc";
	
	List<Map<String, Object>> rlist = rdmgr.retrieveListMap(q);
 	
 	
	StringBuffer sb = new StringBuffer();
	sb.append("<table border=1>");
	int count = 0;
	for (int i=0; i<rlist.size(); i++) {
		Map<String, Object> m = rlist.get(i);
		sb.append("<tr>");
		sb.append("<td>").append(m.get("ver")).append("</td>");
		sb.append("<td>").append(m.get("c")).append("</td>");
		sb.append("<td>").append(m.get("d")).append("</td>");
		sb.append("</tr>");
	}
	System.out.println("## 3");	
	
	sb.append("</table>");
	out.println(sb.toString());
%>
