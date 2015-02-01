<%@ page language="java" import="util.*,dbo.*,com.phm.smart.*,java.util.*"
	pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>	
<%
	try {
		String uuid = request.getParameter("uuid");		
		User u = User.getUserWithUuid(uuid, "db152");
		if (u==null) {
			out.println("{ \"code\":\"e002\", \"error\":\"user not found\" }");
			response.setStatus(500);
			return;
		}
		
		HttpParams hp = new HttpParams(request);
		int message_id = hp.getInt("m", 0);

		System.out.println("## in front/message/setread user=" + 
				u.getId() + " message=" + message_id);

		if (message_id==0) {			
			out.println("{ \"code\":\"e001\", \"error\":\"wrong params\" }");
			response.setStatus(500);
			return;
		}
		
		boolean commit = false;
	    int tran_id = Manager.startTransaction("db152");
	    try {            
			UserMessageMgr ummgr = new UserMessageMgr(tran_id);
			ummgr.setSource("db152");
			UserChannelMgr ucmgr = new UserChannelMgr(tran_id);
			ucmgr.setSource("db152");	
			MessageMgr mmgr = new MessageMgr(tran_id);
			mmgr.setSource("db152");
			
			boolean set_unread = false;
			UserMessage um = ummgr.find("user_id=" + u.getId() + 
					" and message_id=" + message_id);
			Message m = mmgr.find("id=" + message_id);
			
			if (um==null) {
				um = new UserMessage();
				um.setMessage_id(message_id);
				um.setUser_id(u.getId());
				um.setLast_read(new Date());
				ummgr.create(um);
				set_unread = true;
			}
			else {
				if (um.getLast_read()==null)
					set_unread = true;					
			}
			
			if (set_unread) {
				ucmgr.executeSQL("update peter_user_channel " + 
					"set unread=unread-1 where user_id=" + u.getId() + 
						" and channel_id=" + m.getChannel_id() + " and unread>0");
			}
			
			Manager.commit(tran_id);
	        commit = true;

	        out.println("{}");
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    	out.println("{ \"code\":\"e999\", \"error\":\"unknow error\" }");
			response.setStatus(500);
			return;
	    }
	    finally {
	        if (!commit)
	            Manager.rollback(tran_id);
	    }	
	    
	}
	catch (Exception e) {
		e.printStackTrace(System.out);
		throw e;
	}
%>