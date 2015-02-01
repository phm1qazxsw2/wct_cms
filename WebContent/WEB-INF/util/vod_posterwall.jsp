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
	
"<request method=\"getMovie\">"+
"<parameters>"+
	"<token>" + token + "</token>"+
	"<code>MOVIE</code>" +
	"<menuId>"+id+"</menuId>" +
	"<currentPage>0</currentPage>" +
	"<pageSize>10000</pageSize>" +
	"<currCount>0</currCount>" +
"</parameters>"+
"</request>";	

	String result = util.HttpUtil.doPost2(vod_url, content);
	StringBuffer sb = new StringBuffer();	
	Document doc = Jsoup.parse(result);
	String total = doc.select("totalNumber").first().text();
	Elements movies = doc.select("movie");
	int i = 0;
	for (Element e : movies) {
		i ++;
		String movieId = e.select("movieId").first().text();
		String name = e.select("name").first().text();
		sb.append(i).append("&nbsp;");
		sb.append("<a href=\"").append("video/detail?id=");
		sb.append(movieId);
		sb.append("&t=").append(new Date().getTime());
		sb.append("\">").append(name).append("</a>");
		sb.append("<br/>");
	}
	out.println("海报墙");
	out.println("<br/>total:" + total);
	out.println("<br/>");
	out.println(sb.toString());
%>