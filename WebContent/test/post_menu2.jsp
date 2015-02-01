<%@ page language="java" import="java.util.*,java.net.*,java.io.*,org.jsoup.*,org.jsoup.nodes.*"
	pageEncoding="UTF-8" contentType="text/xml;charset=UTF-8"%><%
	
	String token = "23432423423423";
	String content = 
	
"<request method=\"getMovieSecondary\">"+
"<parameters>"+
	"<token>" + token + "</token>"+
	"<code>MOVIE</code>" +
	"<menuId>1</menuId>" +
"</parameters>"+
"</request>";	

	String result = util.HttpUtil.doPost(util.HttpUtil.getBasePath(request) + "ali/video/service", content);
	out.println(result);
%>