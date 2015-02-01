<%@ page language="java" import="java.util.*"
	contentType="text/xml;charset=UTF-8" pageEncoding="UTF-8"%><%@ 
page import="util.*,java.text.*,com.wct.vod.*,java.io.*,org.jsoup.*,org.jsoup.nodes.*"%><%
	ApiHelper ah = new ApiHelper();
	if (!request.getMethod().equals("POST")) {		
		out.println(ah.getErrorResponse());		
		return;
	}
	
	String body = HttpUtil.getPostBody(request);
	Document doc = Jsoup.parse(body, "UTF-8");
	Element req = doc.select("request").first();
	String method = req.attr("method");
	
	if (method.equals("getMovieMenus")) {
		String ret = ah.getMovieMenus("MyPlayer");
		out.println(ret);
		return;
	}
	else if (method.equals("getMovieSecondary")) {
		int menuId = 1;
		try { menuId = Integer.parseInt(doc.select("menuId").first().text()); } 
		catch (Exception e){} 
		String ret = ah.getMovieSecondary(menuId);
		out.println(ret);
		return;
	}
	else if (method.equals("getMovie")) {
		int menuId = 1;
		try { menuId = Integer.parseInt(doc.select("menuId").first().text()); } 
		catch (Exception e) {}
		
		int curpage = 0;
		try { curpage = Integer.parseInt(doc.select("currentPage").first().text()); }
		catch (Exception e){}
		 
		int pageSize = 0;
		try { pageSize = Integer.parseInt(doc.select("pageSize").first().text()); }
		catch (Exception e){} 
		
		String ret = ah.getMovie(menuId, curpage, pageSize);
		out.println(ret);
		return;
	}
%>
