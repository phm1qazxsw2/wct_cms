<%@ page language="java" import="java.util.*,util.*,org.jsoup.*,org.jsoup.nodes.*,org.jsoup.select.*"
	pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%><%
	
String content = 
"<request method=\"login\">"+
	"<parameters>"+
		"<userName>6992166118</userName>"+
		"<password>jto69921</password>"+
		"<decive_type>1</decive_type>"+
		"<device_model>12345</device_model>"+
		"<device_id>00300038EE08D297000002E000000000</device_id>"+
		"<mac>00-90-E6-00-78-CB</mac>"+
		"<key>146EF19026F52921015F129CA15C04B5522638086912B5B3</key>"+
	"</parameters>"+
"</request>";	

	HttpParams hp = new HttpParams(request);
	String api_url = hp.getStr("api_url", null);
	if (api_url==null || api_url.length()==0) {
		out.println("Need api_url");
		return;
	}
	
	String login_url = api_url + "/ali/service";
	String vod_url = api_url + "/ali/video/service"; 
		
	String result = util.HttpUtil.doPost2(login_url, content);
	Document doc = Jsoup.parse(result); 
	String token = doc.select("token").first().text();
	
	request.getSession().setAttribute("vod_url", vod_url);
	request.getSession().setAttribute("token", token);

	content = 
"<request method=\"getMovieMenus\">"+
"<parameters>"+
"<token>" + token + "</token>"+
"</parameters>"+
"</request>";	 

	result = util.HttpUtil.doPost2(vod_url, content);

	StringBuffer sb = new StringBuffer();	
	doc = Jsoup.parse(result);
	Elements menus = doc.select("menu");
	for (Element e : menus) {
		sb.append("<a href=\"").append("menu2?id=");
		StringTokenizer st = new StringTokenizer(e.text(), "|");
		String t = st.nextToken();
		sb.append(t);
		sb.append("&t=").append(new Date().getTime());
		sb.append("\">").append(e.text()).append("</a>");
		sb.append("<br/>");
	}

	out.println("一级菜单<br/>");
	out.println(sb.toString());
%>