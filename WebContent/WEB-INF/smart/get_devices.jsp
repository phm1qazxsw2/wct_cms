<%@ page language="java" import="util.*,com.phm.smart.*,java.util.*"
	pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%
	try {
		TestPushMgr tmgr = new TestPushMgr(0);
		tmgr.setSource("db152");	
	
		System.out.println("##1");
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		
		List<TestPush> devices = tmgr.retrieveList("","");
		for (int i=0; i<devices.size(); i++) {
			TestPush d = devices.get(i);
			if (i>0)
				sb.append(",");
			sb.append("{");
			sb.append("\"id\":\"").append(d.getId()).append("\",");
			sb.append("\"uuid\":\"").append(d.getUuid()).append("\",");
			sb.append("\"token\":\"").append(d.getToken()).append("\",");
			sb.append("\"type\":\"").append(d.getType()).append("\"");
			sb.append("}");
		}
		
		sb.append("]");
		out.println(sb.toString());
	}
	catch (Exception e) {
		e.printStackTrace(System.out);
		throw e;
	}
%>