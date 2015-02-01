<%@ page language="java" import="java.util.*,util.*,com.wct.ad.*"
	pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%!
	static int last_record_id = 0;
%>	
<%
	AdCollect c = new AdCollectMgr(0).find("id=1");
	int tmp = c.getLast_record_id();
	if (tmp==last_record_id) {
		response.sendError(500, "not update " + last_record_id);
		return;		
	}
	else {
		last_record_id = tmp;
		out.println("Ok");
	}
%>