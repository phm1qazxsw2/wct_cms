<%@ page language="java" import="java.util.*"
	contentType="text/xml;charset=UTF-8" pageEncoding="UTF-8"%><%@ 
	page import="util.*,java.text.*,java.io.*,util.*"%><%
	System.out.println("#tt# in syncad3");
StringBuffer sb = new StringBuffer();	
sb.append("<response method=\"syncad\">")
   .append("<attributes>")
      .append("<interval_min>60</interval_min>")
      .append("<ads>")
         //.append("<ad>")
         //   .append("<adtype>VIDEO_1280x720</adtype>")
         //   .append("<adcategory>2</adcategory>")
         //   .append("<adid>93</adid>")
         //   .append("<adtext>http://oss.aliyuncs.com/newad/20140110151954.mkv</adtext>")
         //.append("</ad>")
      .append("</ads>")
   .append("</attributes>")
.append("</response>");
	out.println(sb.toString());
%>