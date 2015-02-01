<%@ page language="java" import="moniting.*,util.*"
	pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%
	HttpParams hp = new HttpParams(request);
	int sleep = hp.getInt("sleep", 100);
	VodMonitor v = new VodMonitor(sleep);
	v.check();
	out.println(v.output());
%>