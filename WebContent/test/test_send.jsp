<%@ page language="java" import="com.sendgrid.*,java.util.*" 
	pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%
	SendGrid sendgrid = new SendGrid("phmhenry", "phm188588");
	System.out.println("## 1");
	SendGrid.Email email = new SendGrid.Email();
	System.out.println("## 2");	
	email.addTo("nicepeter@gmail.com");
	System.out.println("## 3");
	email.addToName("PPPPeter");
	System.out.println("## 4");
	email.setFrom("henry.kung@phm.com.tw");
	System.out.println("## 5");
	email.setSubject("Hello World");
	System.out.println("## 6");
	email.setText("My first email through SendGrid");
	System.out.println("## 7");
	sendgrid.send(email);
%>