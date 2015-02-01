<%@ page language="java" import="util.*,com.phm.smart.*"
	pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%
	HttpParams hp = new HttpParams(request);
	String token = hp.getStr("token", null);
	String uuid = hp.getStr("uuid", null);
	int type = hp.getInt("type", 0);
	if (token==null || uuid==null || type==0) {
		out.println("{ \"code\":\"e001\", \"error\":\"wrong params\" }");
		response.setStatus(500);
		return;
	}
	
	try {
		TestPushMgr tmgr = new TestPushMgr(0);
		tmgr.setSource("db152");	
		int create = 0;
		System.out.println("uuid=" + uuid);
		System.out.println("token=" + token);
		TestPush d = tmgr.find("uuid='" + uuid + "'");
		if (d==null) {
			d = tmgr.find("token='" + token + "'");
			if (d==null) {
				d = new TestPush();
				create = 1;
			}
		}
		d.setToken(token);
		d.setUuid(uuid);
		d.setType(type);
		System.out.println("##1");
		d.setU_time(new java.util.Date());
		
		if (create==1) {
			System.out.println("##2");
			tmgr.create(d);
		}	
		else {
			System.out.println("##3");
			tmgr.save(d);
		}
		
		out.println("{ \"status\":\"success\" }");
	}
	catch (Exception e) {
		e.printStackTrace(System.out);
		throw e;
	}
%>	
