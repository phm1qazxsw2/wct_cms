<%@ page language="java" import="util.*,com.phm.smart.*,java.util.*"
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
		
		UserChannelMgr ucmgr = new UserChannelMgr(0);
		ChannelMgr cmgr = new ChannelMgr(0);
		ucmgr.setSource("db152");	
		cmgr.setSource("db152");
	
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		
		List<UserChannel> ucs = ucmgr.retrieveList("","");
		String cIds = new ConsId<String,UserChannel>(ucs).makeIds("getChannel_id");
		Map<Integer, Channel> cmap = new SortingMap<Integer, Channel>(
			cmgr.retrieveList("id in ("+cIds+")", "")).doSortSingleton("getId");
		
		for (int i=0; i<ucs.size(); i++) {
			UserChannel uc = ucs.get(i);
			Channel c = cmap.get(uc.getChannel_id());
			if (i>0)
				sb.append(",");
			sb.append("{");
			sb.append("\"id\":\"").append(uc.getChannel_id()).append("\",");
			sb.append("\"subtitle\":\"").append(uc.getSubtitle()).append("\",");
			sb.append("\"unread\":").append(uc.getUnread()).append(",");
			sb.append("\"icon\":\"").append(c.getIcon()).append("\""); 
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