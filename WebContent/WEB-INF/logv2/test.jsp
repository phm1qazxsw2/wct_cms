<%@ page language="java" import="java.io.*"
	pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String t = request.getParameter("t");
	out.println(t);
%>