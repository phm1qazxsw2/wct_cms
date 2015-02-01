<%@ page language="java" import="util.*,com.phm.smart.*,java.util.*"
	pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%
	try {
		HttpParams hp = new HttpParams(request);
		
		ChannelMgr cmgr = new ChannelMgr(0);
		cmgr.setSource("db152");
		
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		
		List<Channel> channels = cmgr.retrieveList("","");
		for (int i=0; i<channels.size(); i++) {
			Channel c = channels.get(i);
			if (i>0)
				sb.append(",");
			sb.append("{");
			sb.append("\"id\":\"").append(c.getId()).append("\",");
			sb.append("\"name\":\"").append(TextUtil.escapeString(c.getName())).append("\",");
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