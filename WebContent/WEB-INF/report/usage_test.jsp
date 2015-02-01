<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.report.*,dbo.*"%>
<%
	HttpParams hp = new HttpParams(request);		
	
	try {		
		TmpAppLogMgr tmgr = new TmpAppLogMgr(0);
		tmgr.setSource("db152");
	
		long t1 = new Date().getTime();
		// 每天多少 mac，使用 seconds
   		String q1 = "(select mac,province,city,communications,"+
   			"DATE_FORMAT(enterTime,'%Y-%m-%d') as d, sum(duration) as s "+
   			"from tmp_applog where enterTime>='2014-01-01' and enterTime<'2014-02-01' and duration>0 "+
   			//"from tmp_applog where id>=10000000 and id<11000000 and duration>0 "+
   			"group by mac,d) as t";
   		String q2 = "select province,city,d,count(*) as c," + 
   			"sum(s) as s2,1 from " + q1 +
   			" group by province,city,d order by c desc,d desc";
   			
   		StringBuffer sb = new StringBuffer();
   		sb.append("<table border=1>");
   		List<Map<String,Object>> rs = tmgr.retrieveListMap(q2);
   		for (int i=0; i<rs.size(); i++) {
   			Map<String,Object> r = rs.get(i);
   			sb.append("<tr>");
   			sb.append("<td>").append(r.get("d")).append("</td>");
   			sb.append("<td>").append(r.get("province")).append("</td>");
   			sb.append("<td>").append(r.get("city")).append("</td>");
   			//sb.append("<td>").append(r.get("communications")).append("</td>");
   			sb.append("<td>").append(r.get("c")).append("</td>");
   			sb.append("<td>").append(r.get("s2")).append("</td>");
   			String avg = "";
   			try {
   				double sum = ((Double)r.get("s2")).doubleValue();
   				long count = ((Long)r.get("c")).longValue();
   				avg = (((int) sum/count)/60) + "";
   			} catch (Exception e) {
   				e.printStackTrace();
   			}
   			sb.append("<td>").append(avg).append("</td>");
   			sb.append("</tr>");
   		}
   		sb.append("</table>");
   		out.println("time:" + (new Date().getTime()-t1));
   		out.println(sb.toString());
	}
	catch (Exception e) {
		e.printStackTrace();
	}
%>
