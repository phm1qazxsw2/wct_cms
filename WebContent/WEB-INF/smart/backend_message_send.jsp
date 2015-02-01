<%@ page language="java" import="dbo.*,util.*,com.phm.smart.*,java.util.*,
	cn.jpush.api.push.*,cn.jpush.api.push.model.*,cn.jpush.api.push.model.audience.*"
	pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%
	try {
		System.out.println("Content-Type=" + request.getHeader("Content-Type"));
		
		HttpParams hp = new HttpParams(request);
		
		System.out.println(hp.getInfo());
		
		int channel_id = hp.getInt("c", 0);
		String title = hp.getStr("t", "");
		String message = hp.getStr("m", "");
		
		System.out.println("#1 c=" + channel_id + " t=" + title + " m=" + message);
		
		if (channel_id==0 || title.length()==0 || message.length()==0) {
			out.println("{ \"code\":\"b001\", \"error\":\"wrong params\" }");
			response.setStatus(500);
			return;
		}
						
		boolean commit = false;
	    int tran_id = Manager.startTransaction("db152");
	    try { 
			MessageMgr mmgr = new MessageMgr(tran_id);
			mmgr.setSource("db152");
			UserChannelMgr ucmgr = new UserChannelMgr(tran_id);
			ucmgr.setSource("db152");

			com.phm.smart.Message m = new com.phm.smart.Message();
			m.setChannel_id(channel_id);
			m.setTitle(title);
			m.setText(message);
			m.setCreated(new Date());
			mmgr.create(m);
	    
			ucmgr.executeSQL("update peter_user_channel set unread=unread+1 where channel_id=" + 
				channel_id);
			
			Manager.commit(tran_id);
	        commit = true;
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
		
	    UserChannelMgr ucmgr = new UserChannelMgr(0);
		ucmgr.setSource("db152");
		UserMgr umgr = new UserMgr(0);
		umgr.setSource("db152");
		ChannelMgr cmgr = new ChannelMgr(0);
		cmgr.setSource("db152");
		
		Channel c = cmgr.find("id=" + channel_id);
		
		System.out.println("#2");
	    
	    
		List<UserChannel> ucs = ucmgr.retrieveList("channel_id=" + channel_id, "");
		String userIds = new ConsId<String, UserChannel>(ucs).makeIds("getUser_id");
		List<User> users = umgr.retrieveList("id in ("+userIds+")", "");
				
		String MASTER_SECRET = "22dd994d7ef5feb57c2ab8b4";
		String APP_KEY = "5cd9e3aeb0f7ee260b69feba";
				
		System.out.println("#3");
		
		String str = c.getName() + "：" + title;
		
		PushPayload payload = PushPayload.newBuilder()
	        .setPlatform(Platform.ios())
	        .setAudience(Audience.all())
	        .setNotification(cn.jpush.api.push.model.notification.Notification.alert(str))
	        .setOptions(Options.newBuilder()
                    .setApnsProduction(true)
                    .build())
	        .build();
		
		PushClient pushClient = new PushClient(MASTER_SECRET, APP_KEY);
		pushClient.sendPush(payload);
		
		System.out.println("#4");
		
		out.println("{}");
	}
	catch (Exception e) {
		e.printStackTrace();
		throw e;
	}
%>