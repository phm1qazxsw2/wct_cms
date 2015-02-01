<%@ page language="java" import="java.util.*,java.net.*,java.io.*,org.jsoup.*,org.jsoup.nodes.*"
	pageEncoding="UTF-8" contentType="text/xml;charset=UTF-8"%><%

	String content = 
	
"<?xml version=\"1.0\" encoding=\"utf-8\"?>" + 
"<request method=\"getad\">" +
   "<parameters>" +
        "<chipId>00300038EE08D297000002E000000000</chipId>" +
        "<token>12345</token>" +
        "<adtype>PIC_180x150</adtype>" +
        "<appType>MMS</appType>" +
   "</parameters>" +
"</request>";	

	util.Tracer t = new util.Tracer();
	String result = util.HttpUtil.doPost("http://223.203.192.26:8099/ad/ali/service", content);
	out.println(result);
	System.out.println("t=" + t.printTimeDiff());
%>