<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.report.*,dbo.*"%>
<%
	HttpParams hp = new HttpParams(request);
	String vid = hp.getStr("vid", "L201");
	String area = hp.getStr("area", "");
	//Date d1 = hp.getDate("start", null);
	//Date d2 = hp.getDate("end", null);
	//if (d1==null || d2==null) {
	//	out.println("请提供 start 和 end 日期");
	//	return;
	//}
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH");
	
	ReportDeviceLoginMgr rdmgr = new ReportDeviceLoginMgr(0);
	rdmgr.setSource("db152");
	
	String q = "vendor_code='" + vid
		// + "' and type=1 and ctime>='" + sdf.format(d1) + "' and ctime<'" + sdf.format(d2) +
		+ "' and type=2 and ctime='2014-03-01'";
	if (area.length()>0)
		q += " and province='" + area + "'";
	
	List<ReportDeviceLogin> rds = rdmgr.retrieveList(// 查2月 
		q, "");
	StringBuffer sb = new StringBuffer();
	int count = 0;
	sb.append("<table border=1>");
	for (int i=0; i<rds.size(); i++) {
		ReportDeviceLogin rd = rds.get(i);
		count += rd.getCount();
		sb.append("<tr>");
		sb.append("<td>").append(rd.getVendor_code()).append("</td>");
	    sb.append("<td>").append(rd.getProvince()).append("</td>");
	    sb.append("<td>").append(rd.getChipType()).append("</td>");
	    sb.append("<td>").append(rd.getSoftware_code()).append("</td>");
	    sb.append("<td>").append(rd.getCommunications()).append("</td>");
	    sb.append("<td>").append(rd.getCount()).append("</td>");
	    sb.append("</tr>");
	}
	sb.append("</table>");
	out.println("2014-03-01 日活跃数<br>");
	out.println("<br>Vid=" + vid);
	out.println("<br>Area=" + area);
	out.println("<br>Total Macs=" + count);
	out.println(sb.toString());
%>
