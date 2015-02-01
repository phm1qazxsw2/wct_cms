<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.vod.*,dbo.*"%>
<%
	HttpParams hp = new HttpParams(request);
	int channel_id = hp.getInt("channel_id", 0);
	int menu_id = hp.getInt("menu_id", 0);
	
	boolean commit = false;
	int tran_id = 0;
		
	try {		
		tran_id = Manager.startTransaction();
		
		MenuChannel mc = new MenuChannelMgr(tran_id).
			find("channel_id=" + channel_id + " and menu_id=" + menu_id); 

		if (mc==null) {
			out.println("{ \"success\":\"0\" }");
			return;
		}
			
		Object[] objs = { mc };
		new MenuChannelMgr(tran_id).remove(objs);
		
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
