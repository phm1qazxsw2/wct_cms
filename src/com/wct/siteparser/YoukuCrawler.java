package com.wct.siteparser;

import util.*;

import java.util.regex.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.json.*;

public class YoukuCrawler {
	public YoukuCrawler() {
	}
	
	public void craw_drama() {
		try {			
			for (int page = 0; page<1; page++) {
				Document doc = HttpUtil
					.getJsoupDoc("http://tv.youku.com/search/index/_page40177_"+page+"_cmodid_40177");
				
				System.out.println("#### page " + page);
				
				// info2_box 是比较详细列表内容
				Elements iter1 = doc.select(".p");
				for (Element p_div : iter1) {
					try {
						Element anchor = p_div.select(".p_link a").first();
						
						String vurl = anchor.attr("href");
						String title = anchor.attr("title");

						Element thumb_img = p_div.select(".p_thumb img").first();
						String vimg = thumb_img.attr("src");
						
						String num = "";
						try { num=p_div.select(".p_status").first().text(); }
						catch (Exception e1) {}
						
						String clarity = "";
						try { clarity = p_div.select(".p_ishd span").first().attr("title"); } 
						catch (Exception e1) {}
						
						String score = "";
						try { score = p_div.select(".p_rating .num").first().text(); } 
						catch (Exception e1) {}
						
						String comment_num = "";
						try { comment_num = p_div.select(".p_rating .rating span").first().attr("title"); } 
						catch (Exception e1) {}
						
						Element spop = p_div.nextElementSibling();
						Element script = spop.select("script").first();
						String script_text = script.html();
						int c1 = script_text.indexOf("{");
						int c2 = script_text.indexOf("};", c1);
						script_text = script_text.substring(c1, c2+1);
						
						JSONObject obj = new JSONObject(script_text);
						String area = "";
						try { area = obj.getJSONObject("area").getString("name"); } 
						catch (Exception e1) {}
						
						String director = obj.getJSONObject("director").getString("name");
						StringBuffer sb1 = new StringBuffer();
						JSONArray actors = obj.getJSONArray("performer");
						for (int i=0; i<actors.length(); i++) {
							JSONObject a = actors.getJSONObject(i);
							if (sb1.length()>0)
								sb1.append(",");
							sb1.append(a.getString("name"));
						}
						
						String catstr = obj.getString("show_type");
						StringBuffer sb2 = new StringBuffer();
						int i=0;
						while (true) {
							c1 = catstr.indexOf(">", i);
							c2 = catstr.indexOf("</a>", c1+1);
							if (c1<0 || c2<0)
								break;
							if (sb2.length()>0)
								sb2.append(",");
							sb2.append(catstr.substring(c1+1, c2));
							i = c2+5;
						}
						
						System.out.println("vurl=" + vurl);
//								+ "\nvimg=" + vimg
//								+ "\ntitle=" + title + "\nnum=" + num
//								+ "\nscore=" + score
//								+ "\ncategorys=" + sb2.toString() 
//								+ "\ndirectors=" + director
//								+ "\nactors=" + sb1.toString()
//								+ "\nclarity=" + clarity 
//								+ "\narea=" + area
//								+ "\ncomment_num=" + comment_num
//								+ "\n#####################");
						
						craw_episodes(vurl);
						
					} catch (Exception e) {
						e.printStackTrace();
					}	
				}
				Element next = doc.select(".sPageL .next a").first();
				if (next==null)
					break;				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void craw_episodes(String episodes_url) 
		throws Exception
	{
		// http://www.youku.com/show_page/id_z60aa6bb8e57011e2b8b7.html
		int c1 = episodes_url.indexOf("/id_");
		int c2 = episodes_url.indexOf(".html");
		String youku_id = episodes_url.substring(c1+1, c2);
		String showpoint_url = "http://www.youku.com/show_point/" + youku_id + ".html?tab=0&divid=point_reload_";
		
		int i=1; // 每次最多会拿40个
		while (true) {
			String tmpurl = showpoint_url + i;
			Document doc = HttpUtil.getJsoupDoc(tmpurl);
			Elements episodes = doc.select(".aspect_con .item");
			if (episodes.size()==0)
				break;
			for (Element e:episodes) {
				Element a = e.select(".link a").first();
				if (a==null) {
					System.out.println("a is null url=" + tmpurl);
				}
				String episode_title = a.attr("title");
				String episode_url = a.attr("href");
				String img = e.select(".thumb img").first().attr("src");
				String duration = e.select(".time .num").first().text();
				String desc = e.select(".desc").first().text();
				i++;
				System.out.println("episode_url=" + episode_url 
						+ "\ntitle=" + episode_title 
						+ "\nduration=" + duration 
						+ "\nimg=" + img 
						+ "\ndesc=" + desc 
						+ "\n================================");
			}
		}
	}

	public void craw_movie() {
		try {			
			// http://movie.youku.com/search/index2/_page63561_2_cmodid_63561
			for (int page = 0; page<1; page++) {
				Document doc = HttpUtil
					.getJsoupDoc("http://movie.youku.com/search/index2/_page63561_"+page+"_cmodid_63561");
				
				System.out.println("#### page " + page);
				
				// info2_box 是比较详细列表内容
				Elements iter1 = doc.select(".p");
				for (Element p_div : iter1) {
					try {
						Element anchor = p_div.select(".p_link a").first();
						
						String vurl = anchor.attr("href");
						String title = anchor.attr("title");

						Element thumb_img = p_div.select(".p_thumb img").first();
						String vimg = thumb_img.attr("src");
						
						String num = "";
						try { num=p_div.select(".p_status").first().text(); }
						catch (Exception e1) {}
						
						String clarity = "";
						try { clarity = p_div.select(".p_ishd span").first().attr("title"); } 
						catch (Exception e1) {}
						
						String score = "";
						try { score = p_div.select(".p_rating .num").first().text(); } 
						catch (Exception e1) {}
						
						String comment_num = "";
						try { comment_num = p_div.select(".p_rating .rating span").first().attr("title"); } 
						catch (Exception e1) {}
						
						Element spop = p_div.nextElementSibling();
						Element script = spop.select("script").first();
						String script_text = script.html();
						int c1 = script_text.indexOf("{");
						int c2 = script_text.indexOf("};", c1);
						script_text = script_text.substring(c1, c2+1);
						
						JSONObject obj = new JSONObject(script_text);
						String area = "";
						try { area = obj.getJSONObject("area").getString("name"); } 
						catch (Exception e1) {}
						
						String director = obj.getJSONObject("director").getString("name");
						StringBuffer sb1 = new StringBuffer();
						JSONArray actors = obj.getJSONArray("performer");
						for (int i=0; i<actors.length(); i++) {
							JSONObject a = actors.getJSONObject(i);
							if (sb1.length()>0)
								sb1.append(",");
							sb1.append(a.getString("name"));
						}
						
						String catstr = obj.getString("show_type");
						StringBuffer sb2 = new StringBuffer();
						int i=0;
						while (true) {
							c1 = catstr.indexOf(">", i);
							c2 = catstr.indexOf("</a>", c1+1);
							if (c1<0 || c2<0)
								break;
							if (sb2.length()>0)
								sb2.append(",");
							sb2.append(catstr.substring(c1+1, c2));
							i = c2+5;
						}
						
						System.out.println("vurl=" + vurl
								+ "\nvimg=" + vimg
								+ "\ntitle=" + title + "\nnum=" + num
								+ "\nscore=" + score
								+ "\ncategorys=" + sb2.toString() 
								+ "\ndirectors=" + director
								+ "\nactors=" + sb1.toString()
								+ "\nclarity=" + clarity 
								+ "\narea=" + area
								+ "\ncomment_num=" + comment_num
								+ "\n#####################");
						
					} catch (Exception e) {
						e.printStackTrace();
					}	
				}
				Element next = doc.select(".sPageL .next a").first();
				if (next==null)
					break;				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		//new YoukuCrawler().craw_drama();
		new YoukuCrawler().craw_movie();
	}
}
