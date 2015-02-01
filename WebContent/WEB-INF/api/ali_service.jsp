<%@ page language="java" session="false" import="java.util.*"
	contentType="text/xml;charset=UTF-8" pageEncoding="UTF-8"%><%@ 
page import="util.*,java.text.*,java.io.*,org.jsoup.*,org.jsoup.nodes.*"%><%

	System.out.println("## ali/service 1");
	
	if (!request.getMethod().equals("POST")) {		
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<response method=\"ali_service\">");
		sb.append("<attributes><error>E000</error><reason>not a POST request</reason></attributes>");
		sb.append("</response>");
		out.println(sb.toString());	
		System.out.println("## ali/service 2");
		return;
	}
	
	String body = HttpUtil.getPostBody(request);
	Document doc = Jsoup.parse(body, "UTF-8");
	Element req = doc.select("request").first();
	String method = req.attr("method");
	
	if (method==null) {
		System.out.println("methos is null");
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<response method=\"ali_service\">");
		sb.append("<attributes><error>E000</error><reason>method is null</reason></attributes>");
		sb.append("</response>");
		out.println(sb.toString());	
		System.out.println("## ali/service 3");
		return;
	}
	
	if (method.equals("getChallenge")) {
		System.out.println("method is getChallenge");
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<response method=\"getChallenge\">");
    	sb.append("<attributes>");
    	sb.append("<challenger>1234567890</challenger>");
    	sb.append("</attributes >");
		sb.append("</response>");
		out.println(sb.toString());
		System.out.println("## ali/service 4");
		return;
	}
	else if (method.equals("checkResponseEx")) {
		System.out.println("method is checkResponseEx");
		StringBuilder sb = new StringBuilder();
sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
sb.append("<response method=\"checkResponseEx\">");
sb.append("    <attributes>");
sb.append("        <token>1234567890</token>");
sb.append("        <isRegistered>true</isRegistered>");
sb.append("        <softwareId>99.99.9999</softwareId>");
sb.append("        <needDeviceUpdate>false</needDeviceUpdate>");
sb.append("        <updateVersionInfo>false</updateVersionInfo>");
sb.append("        <location></location>");
sb.append("        <device-updates>");
sb.append("        </device-updates>");
sb.append("        <configs>");
sb.append("            <property name=\"turn_service_url\">http://lunbo.playergetlist.com/nmpsp_server/ali/turn/service</property>");
sb.append("            <property name=\"serviceList_interval\">240</property>");
sb.append("            <property name=\"mms_service_url\">http://42.121.0.203:8080/tvims/servlet/imsServiceServlet</property>");
sb.append("            <property name=\"serviceList_url\">http://logx.widecloud.com.cn/ali/service</property>");
sb.append("        </configs>");
sb.append("    </attributes>");
sb.append("</response>");
		out.println(sb.toString());
		System.out.println("## ali/service 5");
		return;
	}
	else {
		System.out.println("method is " + method);
	}
%>
