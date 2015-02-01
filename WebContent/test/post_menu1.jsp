<%@ page language="java" import="java.util.*,java.net.*,java.io.*,org.jsoup.*,org.jsoup.nodes.*"
	pageEncoding="UTF-8" contentType="text/xml;charset=UTF-8"%><%

	String basePath = util.HttpUtil.getBasePath(request);
	
	String token = "234234234234";
String content = 
"<request method=\"getMovieMenus\">"+
"<parameters>"+
"<token>" + token + "</token>"+
"</parameters>"+
"</request>";	

	String result = util.HttpUtil.doPost(basePath + "ali/video/service", content);
	out.println(result);
%>