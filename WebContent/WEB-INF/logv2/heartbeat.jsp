<%@ page language="java" import="java.util.*,java.io.*"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%><%!
	static int counter = 0;	
	static Map<String, Usage> testMap = new HashMap<String, Usage>(10000);
	static class Usage {
		Date start = null;
		Date last = null;
		Usage(Date d) {
			this.start = d;
			this.last = d;
		}
	}
%>
<%
	BufferedReader br = new BufferedReader(new InputStreamReader(
		request.getInputStream(), "UTF-8"));
	StringBuffer sb = new StringBuffer();
	String line = null;
	while ((line=br.readLine())!=null) {
		sb.append(line);
	}
	String s = sb.toString();
	int c1 = s.indexOf("<mac>");
	int c2 = s.indexOf("</mac>");
	String mac = s.substring(c1+5, c2);
	Usage usage = testMap.get(mac);
	if (usage!=null) {
		int diff = (int) (new Date().getTime() - usage.last.getTime())/1000;
		if (diff>30*60) {
			
		}
	}
	else
		usage = new Usage(new Date());
	testMap.put(mac, usage);	
%>