<%@ page language="java" import="util.*,com.phm.smart.*,java.util.*"
	pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%
	try {
		HttpParams hp = new HttpParams(request);
		String phone = hp.getStr("phone", null);
		String uuid = hp.getStr("uuid", null);
		String jpushId = hp.getStr("jpush_id", null);
		
		System.out.println("## in front/user/set user=" + uuid);
		
		if (uuid==null) {			
			out.println("{ \"code\":\"e001\", \"error\":\"wrong params\" }");
			response.setStatus(500);
			return;
		}
		UserMgr umgr = new UserMgr(0);
		umgr.setSource("db152");
		
		User u = User.getUserWithUuid(uuid, "db152");
		if (u==null)
			u = User.createUserWithUuid(uuid, "db152"); 
		if (phone!=null)
			u.setPhone(phone);
		if (jpushId!=null)
			u.setJpush_id(jpushId);
		u.setU_time(new Date());
		umgr.save(u);
		out.println("{ \"status\":\"success\" }");
	}
	catch (Exception e) {
		e.printStackTrace(System.out);
		throw e;
	}
%>