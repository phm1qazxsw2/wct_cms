<%@ page language="java" import="java.util.*,java.net.*,java.io.*,org.jsoup.*,
    org.jsoup.nodes.*,org.jsoup.select.*"
	pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%><%

	String vod_url = (String) request.getSession().getAttribute("vod_url");
	String token = (String) request.getSession().getAttribute("token");
	if (vod_url==null || token==null) {
		out.println("cannot find vod_url, session expired");
		return;
	}

	util.HttpParams hp = new util.HttpParams(request);
	String id = hp.getStr("id", "");

	String content = 
	
"<request method=\"getRecommandDetail\">" +
"<parameters>" +
	"<token>" + token + "</token>" +
	"<movieId>" + id + "</movieId>" +
"</parameters>"+
"</request>";	

	String result = util.HttpUtil.doPost2(vod_url, content);
	//out.println(result);

	StringBuffer sb = new StringBuffer();	
	Document doc = Jsoup.parse(result);
	String name = doc.select("name").first().text();
	String director = doc.select("director").first().text();
	String protagonist = doc.select("protagonist").first().text();
	String type = doc.select("type").first().text();
	String videoType = doc.select("videoType").first().text();
	String area = doc.select("area").first().text();
	String year = doc.select("year").first().text();
	String genre = doc.select("genre").first().text();
	String mold = doc.select("mold").first().text();
	String imageUrl = doc.select("imageUrl").first().text();
	String describe = doc.select("describe").first().text();
	String totalNumber = doc.select("totalNumber").first().text();
	
	Elements relevance = doc.select("relevance");
	if (relevance.size()==0) {
		// 电影
		Elements urls = doc.select("url");
		int i = 0;
		for (Element e : urls) {
			i ++;
			sb.append(i).append("&nbsp;[").append(e.attr("providerCode")).append("]&nbsp;");
			sb.append(e.text());
			sb.append("<br>");
		}
	}
	else {
		// 聚集
		for (Element e : relevance) {
			String num = e.attr("number");
			Elements urls = e.select("url");
			int i = 0;
			sb.append("集数:").append(num).append("&nbsp;[").append(e.attr("name")).
				append("]&nbsp;").append("<br/>");
			for (Element e2 : urls) {
				i ++;
				sb.append(i);
				sb.append("&nbsp;[").append(e2.attr("providerCode")).append("]&nbsp;");
				sb.append(e2.text());
				sb.append("<br>");
			}
			sb.append("<br><br/>");
		}
	}
	
	out.println("<br>name:" + name);
	out.println("<br/>director:" + director);
	out.println("<br/>protagonist:" + protagonist);
	out.println("<br/>type:" + type);
	out.println("<br/>videoType:" + videoType);
	out.println("<br/>area:" + area);
	out.println("<br/>year:" + year);
	out.println("<br/>genre:" + genre); 
	out.println("<br/><img src=\""); 
	out.println(imageUrl);
	out.println("\">");
	out.println("<br/>describe:" + describe);
	out.println("<br/>");
	out.println("<br/>");
	out.println(sb.toString());
%>