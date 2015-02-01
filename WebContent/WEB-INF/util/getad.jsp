<%@ page language="java" import="java.util.*,util.*,org.jsoup.*,org.jsoup.nodes.*,org.jsoup.select.*"
	pageEncoding="UTF-8" contentType="text/xml;charset=UTF-8"%><%
	
String content = 
"<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
"<request method=\"syncad\">" +
	"<parameters>" +
		"<token>00000000</token>" +
		"<chipId>00300038CE19971F0000025200000000</chipId>" +
		"<adtype>VIDEO_1280x720|PIC_180x150|PIC_1024x768|TXT_120</adtype>"+
		"<records>" +
      		"<record>1|1|VIDEO_1280x720</record>"+
			"<record>2|6|PIC_180x150</record>"+
      		"<record>3|0|PIC_1024x768</record>"+
			"<record>296|5|TXT_120</record>"+
		"</records>"+
	"</parameters>"+
"</request>";	

	String ad_url = "http://223.203.192.26:8299/ad/ali/service"; 
	String result = util.HttpUtil.doPost2(ad_url, content);
	out.println(result);
%>