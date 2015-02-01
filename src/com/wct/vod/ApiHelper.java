package com.wct.vod;

import java.io.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class ApiHelper {

	public String getErrorResponse()
		throws Exception
	{
		String[] attrs = { "error" };
		String[] values = { "E000" }; 
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		sb.append("<response method=\"ali_service\">");
		sb.append("<attributes><error>E000</error></attributes>");
		sb.append("</response>");
		return sb.toString();
	}
	
	public Document getMenuDoc()
		throws Exception
	{
		File f = new File(this.getClass().getResource("menu.xml").getFile());
		Document doc = Jsoup.parse(f, "UTF-8");
		return doc;
	}
	
	public String getMovieMenus(String model)
		throws Exception
	{
		Document doc = getMenuDoc();
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
		sb.append("<response method=\"getMovieMenus\">");
		sb.append("<attributes>");
		sb.append("<menus>");

		String selector = "." + model + " container";
		Elements containers = doc.select(selector);
		for (Element e : containers) {
			sb.append("<menu>");
			sb.append(e.attr("id")).append("|").append(e.attr("name")).append("|MOVIE|");
			sb.append((e.select("item").size()>0)?"YES":"NO");
			sb.append("|NO");
			sb.append("</menu>");
		}
		sb.append("</menus>");
		sb.append("</attributes>");
		sb.append("</response>");
		
		return sb.toString();
	}
	
	public String getMovieSecondary(int container_id) 
		throws Exception
	{
		Document doc = getMenuDoc();
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
		sb.append("<response method=\"getMovieSecondary\">");
		sb.append("<attributes>");
		sb.append("<secondarys>");

		String selector = "container[id=" + container_id + "] item";
		Elements items = doc.select(selector);
		for (Element item : items) {
			sb.append("<secondary>");
			String pclass = item.parent().attr("class");
			sb.append(item.attr("id")).append("|").append(item.attr("name")).append("|");
			sb.append("MOVIE|YES|NO");
			sb.append("</secondary>");
		}
		sb.append("</secondarys>");
		sb.append("</attributes>");
		sb.append("</response>");
		
		return sb.toString();
		
	}
	
	public String getMovie(int menuId, int curPage, int pageSize)
		throws Exception
	{
		Document doc = getMenuDoc();
		
		
		int num = pageSize;
		
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>");
		sb.append("<response method=\"getMovie\">");
		sb.append("<attributes>");
		sb.append("<totalNumber>").append(num).append("</totalNumber>");
		
		
		sb.append("</attributes>");
		sb.append("</response>");
		
		return sb.toString();
	}
}
