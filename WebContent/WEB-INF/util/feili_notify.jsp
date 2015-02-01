<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ 
	page import="util.*,java.text.*,java.io.*,util.*"%><%
	System.out.println("#feili# in Feili notify");
	String ret_url = request.getParameter("ret_url");
	if (ret_url!=null) {
		response.sendRedirect(ret_url);
		return;
	}
%>
no ret_url found