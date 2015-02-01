<%@ page language="java" import="java.util.*"
	contentType="text/xml;charset=UTF-8" pageEncoding="UTF-8"%><%@ 
	page import="util.*,java.text.*,java.io.*,util.*"%><%
	System.out.println("#tt# in syncad2#2");
	String ret_url = request.getParameter("ret_url");
	if (ret_url!=null) {
		response.sendRedirect(ret_url);
	}
%>