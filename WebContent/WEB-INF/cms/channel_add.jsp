<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.vod.*,dbo.*"%>
<%
	HttpParams hp = new HttpParams(request);
	String channel_name = hp.getStr("name", "unknown name");
	
	boolean commit = false;
	int tran_id = 0;
		
	try {		
		tran_id = Manager.startTransaction();
		
		Channel c = new Channel();			
		c.setName(channel_name);
		new ChannelMgr(tran_id).create(c);
		
		Manager.commit(tran_id);
    	commit = true;		
    	out.println("{ \"success\":\"1\" }");
	}
	catch (Exception e) {
		e.printStackTrace();
		String msg = e.getMessage();
		out.println("{ \"error\":\"1\", msg:\"" + msg + "\" }");
	}
	finally {
	    if (!commit)
	        Manager.rollback(tran_id);
	}
%>
