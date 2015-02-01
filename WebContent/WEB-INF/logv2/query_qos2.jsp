<%@ page language="java" import="com.wct.util.*,com.wct.logs.*,java.text.*,java.util.*,util.*"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%><%!
	static SimpleDateFormat sdf = new SimpleDateFormat("MM-dd HH:mm:ss");
	static SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd");
	static String printTime(int secs) {
		StringBuilder ret = new StringBuilder();
		int hrs = secs/3600;
		if (hrs>0) {
			ret.append(hrs).append("hr ");
			secs = secs - hrs*3600;
		}
		int mins = secs/60;
		if (mins>0) {
			ret.append(mins).append("min ");
			secs = secs - mins*60;
		}
		ret.append(secs);
		return ret.toString();
	}
%><%
	HttpParams hp = new HttpParams(request);
	String mac = hp.getStr("mac", null);
	if (mac==null || mac.length()==0) {
		out.println("need provide mac");
		return;
	}
	
	int id = -1;
	int len = 100;
	
	StringBuilder outer = new StringBuilder();
	outer.append("<table border=1>\n");
	
	StringBuilder sb = new StringBuilder();
	Calendar c = Calendar.getInstance();
	Calendar c1 = Calendar.getInstance();
	Calendar c2 = Calendar.getInstance();
	Date start = null;
	int row = 0;
	PArrayList<LogWatch> unsort_logs = new PArrayList<LogWatch>();
	while (true) {
		List<LogWatch> tmp = new LogWatchMgr(0).retrieveList("id>" + id + 
			" and mac='" + mac + "'", "limit " + len);
		if (tmp.size()==0)
			break;
		unsort_logs.concate(tmp);
		id = tmp.get(tmp.size()-1).getId();
	}
	
	
	for (int i=0; i<unsort_logs.size(); i++) {
		LogWatch log = unsort_logs.get(i);
		Date end = log.getCtime();
		c.setTime(end);
		c.add(Calendar.SECOND, 0-log.getSecs());

		start = c.getTime();
		c1.setTime(start);
		if (c1.get(Calendar.DATE)!=c2.get(Calendar.DATE) && row>0) {				
			outer.append("<tr><td valign=top>");
			outer.append(sdf1.format(c2.getTime()));
			outer.append("</td><td>");
			
			outer.append("<table border=1 cellspadding=1 cellspacing=1>\n");
			outer.append("<tr>");
			outer.append("<td>id</td><td>ip</td><td>起始</td><td>秒</td><td>终止</td>");
			outer.append("<td>app</td><td>video</td><td>channel</td>");
			outer.append("</tr>\n");
			outer.append(sb);
			outer.append("</table>");
			
			outer.append("</td></tr>");	
			sb = new StringBuilder();
		}
		c2.setTime(start);

		float s = (float) ((log.getSecs()>3600)?3600:log.getSecs());
		int f = (int) (0xFF * (1-(s/3600)));
		String color = String.format("bgcolor=#FF%02X%02X", f, f);
		sb.append("<tr ").append(color).append(">");
		sb.append("<td>").append(log.getId()).append("</td>");
		sb.append("<td>").append(log.getIp()).append("</td>");
		sb.append("<td>").append(sdf.format(start)).append("</td>");
		
		sb.append("<td align=right>").append(printTime(log.getSecs())).append("&nbsp;&nbsp;</td>");
		sb.append("<td>").append(sdf.format(end)).append("</td>");						
		sb.append("<td>").append(log.getApp()).append("</td>");
		sb.append("<td>").append(log.getVideo()).append("</td>");
		sb.append("<td>").append(log.getChannel()).append("</td>");
		sb.append("</tr>\n");
		row ++;
	}
	
	outer.append("<tr><td valign=top>");
	outer.append(sdf1.format(start));
	outer.append("</td><td>");
	
	outer.append("<table border=1 cellspadding=1 cellspacing=1>\n");
	outer.append("<tr>");
	outer.append("<td>id</td><td>ip</td><td>起始</td><td>秒</td><td>终止</td>");
	outer.append("<td>app</td><td>video</td><td>channel</td>");
	outer.append("</tr>\n");
	outer.append(sb);
	outer.append("</table>");
	
	outer.append("</td></tr>");	
	outer.append("</table>");	
	out.println(outer.toString());
	
	sb = new StringBuilder();
	outer = new StringBuilder();
	outer.append("<br/><br/>\n");
	outer.append("<table border=1>\n");
	
	List<LogWatch> logs = new ArrayList<LogWatch>();
	LogWatch tmp = unsort_logs.get(0);
	for (int i=1; i<unsort_logs.size(); i++) {
		LogWatch l = unsort_logs.get(i);
		boolean same = l.getChannel().equals(tmp.getChannel()) && 
			l.getVideo().equals(tmp.getVideo()) &&
			l.getApp().equals(tmp.getApp());
		if (same) {
			tmp.setSecs(tmp.getSecs() + l.getSecs());
		}
		else {		
			logs.add(tmp);
			tmp = l;
		}
	} 
	
	c = Calendar.getInstance();
	c1 = Calendar.getInstance();
    c2 = Calendar.getInstance();
	
	for (int i=0; i<logs.size(); i++) {
		LogWatch log = logs.get(i);
		Date end = log.getCtime();
		c.setTime(end);
		c.add(Calendar.SECOND, 0-log.getSecs());

		start = c.getTime();
		c1.setTime(start);
		if (c1.get(Calendar.DATE)!=c2.get(Calendar.DATE) && row>0) {				
			outer.append("<tr><td valign=top>");
			outer.append(sdf1.format(c2.getTime()));
			outer.append("</td><td>");
			
			outer.append("<table border=1 cellspadding=1 cellspacing=1>\n");
			outer.append("<tr>");
			outer.append("<td>id</td><td>ip</td><td>起始</td><td>秒</td><td>终止</td>");
			outer.append("<td>app</td><td>video</td><td>channel</td>");
			outer.append("</tr>\n");
			outer.append(sb);
			outer.append("</table>");
			
			outer.append("</td></tr>");	
			sb = new StringBuilder();
		}
		c2.setTime(start);

		float s = (float) ((log.getSecs()>3600)?3600:log.getSecs());
		int f = (int) (0xFF * (1-(s/3600)));
		String color = String.format("bgcolor=#FF%02X%02X", f, f);
		sb.append("<tr ").append(color).append(">");
		sb.append("<td>").append(log.getId()).append("</td>");
		sb.append("<td>").append(log.getIp()).append("</td>");
		sb.append("<td>").append(sdf.format(start)).append("</td>");
		
		sb.append("<td align=right>").append(printTime(log.getSecs())).append("&nbsp;&nbsp;</td>");
		sb.append("<td>").append(sdf.format(end)).append("</td>");						
		sb.append("<td>").append(log.getApp()).append("</td>");
		sb.append("<td>").append(log.getVideo()).append("</td>");
		sb.append("<td>").append(log.getChannel()).append("</td>");
		sb.append("</tr>\n");
		row ++;
		id = log.getId();
	}
	
	outer.append("<tr><td valign=top>");
	outer.append(sdf1.format(start));
	outer.append("</td><td>");
	
	outer.append("<table border=1 cellspadding=1 cellspacing=1>\n");
	outer.append("<tr>");
	outer.append("<td>id</td><td>ip</td><td>起始</td><td>秒</td><td>终止</td>");
	outer.append("<td>app</td><td>video</td><td>channel</td>");
	outer.append("</tr>\n");
	outer.append(sb);
	outer.append("</table>");
	
	outer.append("</td></tr>");	
	outer.append("</table>");	
	out.println(outer.toString());	
%>