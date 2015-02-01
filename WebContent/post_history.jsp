<%@ page language="java" import="java.util.*,java.net.*,java.io.*,org.jsoup.*,org.jsoup.nodes.*"
	pageEncoding="UTF-8" contentType="text/xml;charset=UTF-8"%>
<%
String content = 
"<request method=\"login\">"+
"<parameters>"+
"<userName>6992166118</userName>"+
"<password>jto69921</password>"+
"<decive_type>1</decive_type>"+
"<device_model>12345</device_model>"+
"<device_id>00300038EE08D297000002E000000000</device_id>"+
"<mac>00-90-E6-00-78-CB</mac>"+
"<key>146EF19026F52921015F129CA15C04B5522638086912B5B3</key>"+
"</parameters>"+
"</request>";	

	String result = util.HttpUtil.doPost("http://223.203.193.149:8008/nmpsp_server/ali/service", content);
	Document doc = Jsoup.parse(result);
	String token = doc.select("token").first().text();

System.out.println("## result=" + result);

	content = 
"<request method=\"watchHistory\">"+
"<parameters>"+
	"<token>" + token + "</token>"+
"</parameters>"+
"</request>";

	result = util.HttpUtil.doPost("http://223.203.193.149:8008/nmpsp_server/ali/video/service", content);
	out.println(result);
%>