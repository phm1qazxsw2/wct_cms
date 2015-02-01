<%@ page language="java" import="java.util.*,java.net.*,java.io.*,org.jsoup.*,org.jsoup.nodes.*,org.jsoup.select.*"
	pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%><%

	String vod_url = (String) request.getSession().getAttribute("vod_url");
	String token = (String) request.getSession().getAttribute("token");
	if (vod_url==null || token==null) {
		out.println("cannot find vod_url, session expired");
		return;
	}

	util.HttpParams hp = new util.HttpParams(request);
	int id = hp.getInt("id", 0);
	
	String content = 
	
"<request method=\"getMovieSecondary\">"+
"<parameters>"+
	"<token>" + token + "</token>"+
	"<code>MOVIE</code>" +
	"<menuId>" + id + "</menuId>" +
"</parameters>"+
"</request>";	

	String result = util.HttpUtil.doPost2(vod_url, content);
	
	StringBuffer sb = new StringBuffer();	
	Document doc = Jsoup.parse(result);
	
	Elements menus = doc.select("secondary");
	for (Element e : menus) {
		sb.append("<a href=\"").append("posterwall?id=");
		StringTokenizer st = new StringTokenizer(e.text(), "|");
		String t = st.nextToken();
		sb.append(t);
		sb.append("&t=").append(new Date().getTime());
		sb.append("\">").append(e.text()).append("</a>");
		sb.append("<br/>");
	}
	
	out.println("二级菜单<br/>");
	out.println(sb.toString());
%>