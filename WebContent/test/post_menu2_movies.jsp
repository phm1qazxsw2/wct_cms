<%@ page language="java" import="java.util.*,java.net.*,java.io.*,org.jsoup.*,org.jsoup.nodes.*"
	pageEncoding="UTF-8" contentType="text/xml;charset=UTF-8"%>
<%
	String token = "342342342343";
	
	String content = 
	
"<request method=\"getMovie\">"+
"<parameters>"+
	"<token>" + token + "</token>"+
	"<code>MOVIE</code>" +
	"<menuId>105</menuId>" +
	"<currentPage>0</currentPage>" +
	"<pageSize>12</pageSize>" +
	"<currCount>0</currCount>" +
"</parameters>"+
"</request>";	


	String result = util.HttpUtil.doPost(util.HttpUtil.getBasePath(request) + "ali/video/service", content);
	out.println(result);
%>