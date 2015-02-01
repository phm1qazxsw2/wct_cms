<%@ page language="java" import="java.util.*"
	contentType="text/xml;charset=UTF-8" pageEncoding="UTF-8"%><%@ 
	page import="util.*,java.text.*,java.io.*"%><%
	BufferedReader br = new BufferedReader(new InputStreamReader(
		request.getInputStream(), "UTF-8"));
	StringBuffer sb = new StringBuffer();
	String line = null;
	while ((line=br.readLine())!=null) {
		sb.append(line);
	}
	System.out.println("#123# " + sb.toString());
	String s = sb.toString();
	int c1 = s.indexOf("<mac>");
	int c2 = s.indexOf("</mac>");
	String mac = s.substring(c1+5, c2);
	
	c1 = s.indexOf("<vid>");
	c2 = s.indexOf("</vid>");
	String vid = s.substring(c1+5, c2);
	
	//out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?><response method=\"sendOnlineData\">" + 
	//"<attributes><heart_rate>1</heart_rate></attributes></response>");
	String ga_url = "http://www.google-analytics.com/collect?v=1&tid=UA-49240218-8&cid=" + mac +
	    	"&t=pageview&dp=" + java.net.URLEncoder.encode("/" + vid, "UTF-8");
	response.sendRedirect(ga_url);
%>