<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.report.*,dbo.*"%>
<%
	HttpParams hp = new HttpParams(request);
	String vid = hp.getStr("vid", "");
	String ver = hp.getStr("ver", "");
	String chip = hp.getStr("chip", "");
	String area = hp.getStr("area", "");
	String above = hp.getStr("above", "");
	String comm = hp.getStr("comm", "");
	int show = hp.getInt("show", 0);
	int type = hp.getInt("type", 2); // day
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
	
	//Date d1 = hp.getDate("start", null);
	//Date d2 = hp.getDate("end", null);
	//if (d1==null || d2==null) {
	//	out.println("请提供 start 和 end 日期");
	//	return;
	//}
	
	
	ReportDeviceMgr rdmgr = new ReportDeviceMgr(0);
	rdmgr.setSource("db152");
	
	StringBuffer q = new StringBuffer();
	if (vid.length()>0) {
		q.append("vendor_code='").append(vid).append("'");
	}
	if (q.length()>0) {
		q.append(" and ");
	}
	q.append("type=" + type + " and ctime='" + formatsdf.format(day) + "'");
	if (ver.length()>0) {
		q.append(" and software_code like '" + ver + "%'");
	}
	if (above.length()>0) {
		q.append(" and software_code>='" + above + "'");
	}
	if (area.length()>0)
		q.append(" and (province like '%"+area+"%' or city like '%"+area+"%')");
	if (comm.length()>0)
		q.append(" and communications like '%" + comm + "%'");
		
	if (chip.length()>0)
		q.append(" and chipType='" + chip + "'");
		
	System.out.println("## 1");	
	List<ReportDevice> rds = rdmgr.retrieveList(q.toString(), ""); 
	System.out.println("## 2");	
	
	StringBuffer sb = new StringBuffer();
	sb.append("<table border=1>");
	int count = 0;
	for (int i=0; i<rds.size(); i++) {
		ReportDevice rd = rds.get(i);
		sb.append("<tr>");
		sb.append("<td>").append(formatsdf.format(rd.getCtime())).append("</td>");
		sb.append("<td>").append(rd.getVendor_code()).append("</td>");
		sb.append("<td>").append(rd.getSoftware_code()).append("</td>");
		sb.append("<td>").append(rd.getProvince()).append("</td>");
		sb.append("<td>").append(rd.getCity()).append("</td>");
		sb.append("<td>").append(rd.getCommunications()).append("</td>");
		sb.append("<td>").append(rd.getCount()).append("</td>");
		sb.append("<td>").append(rd.getLogins()).append("</td>");
		sb.append("</tr>");
		count += rd.getCount();
	}
	System.out.println("## 3");	
	
	sb.append("</table>");
	out.println("<br>ver=" + ver);
	out.println("<br>area=" + area);
	out.println("<br>q=" + q.toString());
	if (type==1) {
		out.println("<br>小时活跃" + formatsdf.format(day));
	}
	else if (type==2) {
		out.println("<br>日活跃 " + formatsdf.format(day));
	} else if (type==3) {
		out.println("<br>周活跃 week of " + formatsdf.format(day));
	} else if (type==4) {
		out.println("<br>月活跃 month of " + formatsdf.format(day));
	}
	
	out.println("<br>Total Macs=" + count + " 加减10%（同个盒子该段时间内职能取一个地区采样，因此会有误差）");
	if (show==1)
		out.println(sb.toString());
%>
