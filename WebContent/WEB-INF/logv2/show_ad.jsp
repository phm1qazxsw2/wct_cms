<%@ page language="java" session="false" import="com.wct.util.*,com.wct.logs.*,java.text.*,java.util.*,util.*"
	pageEncoding="UTF-8" contentType="text/plain;charset=UTF-8"%><%
	HttpParams hp = new HttpParams(request);
	int ad_id = hp.getInt("ad_id", 0);
	String mac = hp.getStr("mac", "xxx");
	
	if (ad_id==81 || ad_id==82) {
		if (ad_id==81) {
			String url = "http://www.google-analytics.com/collect?v=1&tid=UA-49240218-5&cid=" + mac +
			    	"&t=pageview&dp=" + java.net.URLEncoder.encode("/安沃/iOS手游1", "UTF-8"); 		
			response.sendRedirect(url);
			return;
		}
		else {
			String url = "http://www.google-analytics.com/collect?v=1&tid=UA-49240218-5&cid=" + mac +
			    	"&t=pageview&dp=" + java.net.URLEncoder.encode("/安沃/iOS手游1/82", "UTF-8"); 		
			response.sendRedirect(url);
			return;
		}		
	}
	else if (ad_id==69) {
		String url = "http://www.google-analytics.com/collect?v=1&tid=UA-49240218-5&cid=" + mac +
		    	"&t=pageview&dp=" + java.net.URLEncoder.encode("/Gospel/test开机视频", "UTF-8"); 		
		response.sendRedirect(url);
		return;
	}
	else if (ad_id==83) {
		String url = "http://www.google-analytics.com/collect?v=1&tid=UA-49240218-5&cid=" + mac +
		    	"&t=pageview&dp=" + java.net.URLEncoder.encode("/Test/中国梦", "UTF-8"); 		
		response.sendRedirect(url);
		return;
	}
%>