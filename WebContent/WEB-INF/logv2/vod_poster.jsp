<%@ page language="java" session="false" import="com.wct.util.*,com.wct.logs.*,java.text.*,java.util.*,util.*"
	pageEncoding="UTF-8" contentType="text/plain;charset=UTF-8"%><%!
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
%><%
	int a=1;
	if (a>0)
		return; // 先關掉，只留 new_vooloe

	HttpParams hp = new HttpParams(request);
	String app = hp.getStr("app",null);
	if (app==null)
		return; 
	String mac = hp.getStr("mac", "AAAAAAAA");
	String menu1 = hp.getStr("menu1", "");
	String menu2 = hp.getStr("menu2", "");
	String pp = hp.getStr("page", "");		
	if (app.indexOf("smit")>=0) {
		String path = java.net.URLEncoder.encode("/smit_poster/" + menu1 +
				"/" + menu2 + "/" + pp, "UTF-8");
		String ga_url = "http://www.google-analytics.com/collect?v=1&tid=UA-49240218-2" +
			"&cid=" + mac + 
			"&t=pageview" +
			"&dp=" + path;
		response.sendRedirect(ga_url);
	}
%>