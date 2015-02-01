<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,java.net.*,com.wct.report.*,dbo.*"%>
<%
	long t1 = new Date().getTime();
	new com.wct.util.Logger().log("activemq log test");
	System.out.println("## tt=" + (new Date().getTime()-t1));	
%>
