<%@ page language="java" import="util.*,com.phm.smart.*,java.util.*"
	pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>	
<%
	try {
		int num = 10;
		String uuid = request.getParameter("uuid");		
		User u = User.getUserWithUuid(uuid, "db152");
		if (u==null) {
			out.println("{ \"code\":\"e002\", \"error\":\"user not found\" }");
			response.setStatus(500);
			return;
		}
		
		HttpParams hp = new HttpParams(request);
		int channel_id = hp.getInt("c", 0);

		System.out.println("## in front/channel/messages user=" + 
				u.getId() + " channel=" + channel_id);

		if (channel_id==0) {			
			out.println("{ \"code\":\"e001\", \"error\":\"wrong params\" }");
			response.setStatus(500);
			return;
		}
		
		MessageMgr mmgr = new MessageMgr(0);
		UserMessageMgr ummgr = new UserMessageMgr(0);
		ChannelMgr cmgr = new ChannelMgr(0);
		mmgr.setSource("db152");	
		ummgr.setSource("db152");
		cmgr.setSource("db152");
		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Channel c = cmgr.find("id=" + channel_id);
		List<Message> msgs = mmgr.retrieveList("channel_id=" + channel_id, 
			"order by id desc limit " + num);
		
		String msgIds = new ConsId<String, Message>(msgs).makeIds("getId");
		Map<Integer, UserMessage> umMap = new SortingMap<Integer,UserMessage>(ummgr.
			retrieveList("user_id=" + u.getId() + " and message_id in (" + 
				msgIds + ")", "")).doSortSingleton("getMessage_id");			
				
		
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"channel\":{");
		sb.append("\"id\":\"").append(c.getId()).append("\",");
		sb.append("\"name\":\"").append(TextUtil.encodeHtml(c.getName())).append("\"");
		sb.append("},");
		sb.append("\"messages\":[");
		
		for (int i=0; i<msgs.size(); i++) {
			Message m = msgs.get(i);
			UserMessage um = umMap.get(m.getId());		
			if (i>0)
				sb.append(",");
			sb.append("{");
			sb.append("\"id\":\"").append(m.getId()).append("\","); 
			sb.append("\"title\":\"").append(TextUtil.encodeHtml(m.getTitle())).append("\",");
			sb.append("\"text\":\"").append(TextUtil.encodeHtml(m.getText())).append("\",");
			sb.append("\"created\":\"").append(sdf.format(m.getCreated())).append("\"");
			if (um!=null)
				sb.append(",\"last_read\":\"").append(sdf.format(um.getLast_read())).append("\"");
			sb.append("}");
		}
		
		sb.append("]");
		sb.append("}");
		
		out.println(sb.toString());
	}
	catch (Exception e) {
		e.printStackTrace(System.out);
		throw e;
	}
%>