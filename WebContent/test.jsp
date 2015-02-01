<%@ page language="java" import="util.*"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	HttpParams hp = new HttpParams(request);
	System.out.println(hp.getInfo());
%>