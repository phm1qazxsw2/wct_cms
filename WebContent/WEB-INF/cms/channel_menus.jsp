<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.vod.*"%>
<%
	HttpParams hp = new HttpParams(request);
	int channel_id = hp.getInt("channel_id", 0);
	
	try {
		VodHelper vh = new VodHelper();
		StringBuffer sb = new StringBuffer();
		List<MenuItem> menus = vh.getMenuItems();
		
		List<MenuChannel> mcs = new MenuChannelMgr(0).retrieveList("channel_id=" + channel_id, "");
		if (mcs.size()==0) {
			sb.append("目前没有关联任何菜单<br/><br/>");
		}
		else {
			sb.append("<h2>关联菜单<h2>");
			sb.append("<table border=0>");
			for (int i=0; i<mcs.size(); i++) {
				MenuChannel mc = mcs.get(i);
				sb.append("<tr><td>");
				sb.append(vh.getMenuName(mc.getMenu_id()));
				sb.append("<td><a class=\"unconnect\" href=\"#\" menu_id=\"")
					.append(mc.getMenu_id())
					.append("\" channel_id=\"")
					.append(mc.getChannel_id())
					.append("\">去关联</a></td>");
				sb.append("</td></tr>");
			}
			sb.append("</table>");
		}
		
		sb.append("<select name=\"add_id\">"); 
		sb.append("<option value=\"0\">-----请选择-----</option>");
		for (MenuItem mi:menus) {
			sb.append("<option value=\"").append(mi.getId()).append("\">")
				.append(mi.getName()).append("</option>");	
		}
		sb.append("</select> ");
		sb.append("<input type=button id=\"menu-channel-add\" value=\"新增关联\">");
		out.println(sb.toString());
	}
	catch (Exception e) {
		e.printStackTrace();
		String msg = e.getMessage();
		out.println("{ \"error\":\"1\", msg:\"" + msg + "\" }");
	}
%>
