<%@ page language="java" import="com.wct.util.*,com.wct.logs.*,java.text.*,java.util.*,util.*"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%><%!
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
%><%
	HttpParams hp = new HttpParams(request);
	String mac = hp.getStr("mac", null);
	if (mac==null || mac.length()==0) {
		out.println("need provide mac");
		return;
	}
	LogMacMgr macmgr = new LogMacMgr(0);
	LogQosMgr qosmgr = new LogQosMgr(0);
	LogVideoMgr vmgr = new LogVideoMgr(0);
	
	List<LogMac> macs = macmgr.retrieveList("mac='" + mac + "'", "");
	String macIds = new ConsId(macs).makeIds("getId");
	List<LogQos> qoss = qosmgr.retrieveList("macId in (" + macIds + ")", "order by id desc");
	String videoIds = new ConsId(qoss).makeIds("getVideoId");
	Map<Integer, LogVideo> videoMap = new SortingMap(vmgr.retrieveList("id in (" + 
		videoIds + ")", "")).doSortSingleton("getId"); 
	StringBuffer sb = new StringBuffer();
	sb.append("<table border=1>");
	sb.append("<tr><td>Channel</td><td>Report Time</td><td>startStatus</td>");
	sb.append("<td>startTime</td><td>bufferNum</td><td>bufferTime</td><td>playTime</td></tr>");
	for (int i=0; i<qoss.size(); i++) {
		sb.append("<tr align=center>");
		LogQos q = qoss.get(i);
		LogVideo v = videoMap.get(q.getVideoId());
		sb.append("<td align=left>").append(v.getName()).append("</td>");
		sb.append("<td>").append(sdf.format(q.getCtime())).append("</td>");
		sb.append("<td>").append(q.getStartStatus()).append("</td>");
		if (q.getStartStatus()==1) {
			sb.append("<td>").append(q.getStartTime()).append("</td>");
			sb.append("<td>").append(q.getBufferNum()).append("</td>");
			sb.append("<td>").append(q.getBufferTime()).append("</td>");
			sb.append("<td>").append(q.getPlayTime()).append("</td>");
		}
		else {
			sb.append("<td colspan=4></td>");
		}
		sb.append("</tr>");
	}
	sb.append("</table>");
	out.println(sb.toString());
%>