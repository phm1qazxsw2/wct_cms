<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.report.*,dbo.*"%>
<%
	TmpHourReportMgr thrmgr = new TmpHourReportMgr(0);
    thrmgr.setSource("db152");
	List<TmpHourReport> hrs = thrmgr.retrieveSQLList(
 		//"select vendor_code,software_code,chipType,province,city,communications,count(*) as count,sum(sum1) as sum from (select vendor_code,software_code,chipType,province,city,communications, sum(count) as sum1 from tmp_hour3 where id>=191315543 and id<234953127 group by device_mac) as t where vendor_code='L201' and province='北京' group by vendor_code,software_code,chipType,province,city,communications;");
 		//"select vendor_code,software_code,chipType,province,city,communications,count(*) as count,sum(sum1) as sum from (select vendor_code,software_code,chipType,province,city,communications, sum(count) as sum1 from tmp_hour3 where id>=178411025 and id<222526113 and vendor_code='L201' and province='北京' group by device_mac) as t group by vendor_code,software_code,chipType,province,city,communications;");
 		
 		"select vendor_code,software_code,chipType,province,city,communications,count(*) as count,sum(sum1) as sum "+ 
"from (select vendor_code,software_code,chipType,province,city,communications, sum(count) as sum1 " + 
"	 from tmp_hour3 where id>=137205642 and id<178411025 and count<5 group by device_mac) as t "+ 
"where province='北京' and vendor_code='L201' group by vendor_code,software_code,chipType,province,city,communications"
		/*
		"select vendor_code,software_code,chipType,province,city,communications, sum(count) as count, count(*) as sum " + 
	 "from tmp_hour3 where id>=137205642 and id<178411025 and count<5 and province='北京' and vendor_code='L201' group by device_mac "
	 	*/
	 );
		
 	int total = 0;
 	StringBuffer sb = new StringBuffer();
 	sb.append("<table border=1>");	
	for (int i=0; i<hrs.size(); i++) {
	    TmpHourReport r = hrs.get(i);
	    sb.append("<tr>");
	    sb.append("<td>").append(r.getVendor_code()).append("</td>");
	    sb.append("<td>").append(r.getProvince()).append("</td>");
	    sb.append("<td>").append(r.getCity()).append("</td>");
	    sb.append("<td>").append(r.getChipType()).append("</td>");
	    sb.append("<td>").append(r.getSoftware_code()).append("</td>");
	    sb.append("<td>").append(r.getCommunications()).append("</td>");
	    sb.append("<td>").append(r.getCount()).append("</td>");
	    sb.append("</tr>");
	    total += r.getCount();
	}
	sb.append("</table>");
 	out.println("查询 Gospel 北京 3/1~4/1 活跃数## ");
 	out.println(sb.toString());
	out.println("total=" + total);
	out.println("<br>lines=" + hrs.size());
%>
