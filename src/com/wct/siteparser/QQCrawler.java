package com.wct.siteparser;

import util.*;

import org.json.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class QQCrawler {
	public QQCrawler() {
	}

	public void craw_drama() {
		try {
			for (int i = 0; i<1; i++) {
				Document doc = HttpUtil
					.getJsoupDoc("http://v.qq.com/list/2_-1_-1_-1_1_1_"+i+"_10_-1_-1_0.html");
				
				if (doc.text().indexOf("很抱歉，没有找到任何视频内容")>=0) {
					break;
				}
				System.out.println("#### page " + i);
				
				
				// info2_box 是比较详细列表内容
				Elements iter1 = doc.select(".mod_item");
				for (Element mod_item : iter1) {
					try {
						Element thumb_anchor = mod_item.select(".mod_pic a").first();
						String vurl = thumb_anchor.attr("href");
						Element thumb_img = thumb_anchor.select("img").first();
						String vimg = thumb_img.attr("src");
						String title = thumb_img.attr("alt");
						
						String num = thumb_anchor.select(".figure_mask").first().text();
						String clarity = "";
						try { clarity = thumb_anchor.select(".mark_sd").first().text(); } 
						catch (Exception e1) {}
						
						Element score_element = mod_item.select(".mod_scores").first();
						String comment_num = score_element.select(".mod_number").first().text();
						String score = score_element.select(".c_txt3").first().text();
						
						Element info_element = mod_item.select(".mod_info").first();
						String year = "";
						try { year = info_element.select(".date a").first().text(); }
						catch (Exception e2) {}
						String area = "";
						try { area = info_element.select(".area a").first().text(); }
						catch (Exception e2) {}
						
						Elements cats = info_element.select(".category a");
						StringBuffer sb1 = new StringBuffer();
						for (Element c:cats) {
							if (sb1.length()>0)
								sb1.append(",");
							sb1.append(c.text());
						}
						
						String play_num = info_element.select(".play_time").first().text();
						
						Elements directors = info_element.select(".director a");
						StringBuffer sb2 = new StringBuffer();
						for (Element d:directors) {
							if (sb2.length()>0)
								sb2.append(",");
							sb2.append(d.text());
						}
						
						Elements performers = info_element.select(".performer a");
						StringBuffer sb3 = new StringBuffer();
						for (Element p:performers) {
							if (sb3.length()>0)
								sb3.append(",");
							sb3.append(p.text());
						}
						
						System.out.println("vurl=" + vurl);
//								
//								+ "\nvimg=" + vimg
//								+ "\ntitle=" + title + "\nnum=" + num
//								+ "\nscore=" + score
//								+ "\ncategorys=" + sb1.toString() 
//								+ "\ndirectors=" + sb2.toString()
//								+ "\nactors=" + sb3.toString()
//								+ "\nyear=" + year 
//								+ "\nclarity=" + clarity 
//								+ "\nyear=" + year
//								+ "\narea=" + area
//								+ "\nplay_num=" + play_num
//								+ "\ncomment_num=" + comment_num
//								+ "\n#####################");
						
						craw_episodes(vurl);
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	public void craw_episodes(String episodes_url) 
		throws Exception
	{
		Document doc = HttpUtil.getJsoupDoc(episodes_url);
		Element refresh = doc.select("meta[http-equiv=refresh]").first();
		if (refresh!=null) {
			String urlstr = refresh.attr("content");
			int c = urlstr.indexOf("=");
			episodes_url = urlstr.substring(c+1);
			doc = HttpUtil.getJsoupDoc(episodes_url);
		}
		
		// 一些宣传中的片子可能用 script 取得
		int c1 = doc.html().indexOf("http://v.qq.com/c/");
		if (c1>=0) {
			int c2 = doc.html().indexOf("v=",c1);
			String script_url = doc.html().substring(c1, c2+2) + new java.util.Date().getTime();
			craw_episodes_script(script_url);
			return;
		}
		
		Elements li_list = doc.select(".mod_tv_play_list .tab_cont li");
		System.out.println("=== episodes_url=" + episodes_url + " episodes length=" + li_list.size());
		for (Element li:li_list) {
			Element a = li.select("a").first();
			String episode_url = a.attr("href");
			String episode_title = li.select("p").first().text();
			String duration = a.select(".video_time").first().text();
			String img = a.select("img").first().attr("src");
			
			System.out.println("episode_url=" + episode_url 
					+ "\ntitle=" + episode_title 
					+ "\nduration=" + duration 
					+ "\nimg=" + img 
					+ "================================");
		}
	}
	
	public void craw_movie() {
		try {
			// http://v.qq.com/list/1_-1_-1_-1_1_1_0_10_0_-1_0.html
			for (int i = 0; i<1; i++) {
				Document doc = HttpUtil
					.getJsoupDoc("http://v.qq.com/list/1_-1_-1_-1_1_1_"+i+"_10_-1_-1_0.html");
				
				if (doc.text().indexOf("很抱歉，没有找到任何视频内容")>=0) {
					break;
				}
				System.out.println("#### page " + i);
				
				// info2_box 是比较详细列表内容
				Elements iter1 = doc.select(".mod_item");
				for (Element mod_item : iter1) {
					try {
						Element thumb_anchor = mod_item.select(".mod_pic a").first();
						String vurl = thumb_anchor.attr("href");
						Element thumb_img = thumb_anchor.select("img").first();
						String vimg = thumb_img.attr("src");
						String title = thumb_img.attr("alt");
						
						String clarity = "";
						try { clarity = thumb_anchor.select(".mark_sd").first().text(); } 
						catch (Exception e1) {}
						
						Element score_element = mod_item.select(".mod_scores").first();
						String comment_num = score_element.select(".mod_number").first().text();
						String score = score_element.select(".c_txt3").first().text();
						
						Element info_element = mod_item.select(".mod_info").first();
						String year = "";
						try { year = info_element.select(".date a").first().text(); }
						catch (Exception e2) {}
						String area = "";
						try { area = info_element.select(".area a").first().text(); }
						catch (Exception e2) {}
						
						Elements cats = info_element.select(".category a");
						StringBuffer sb1 = new StringBuffer();
						for (Element c:cats) {
							if (sb1.length()>0)
								sb1.append(",");
							sb1.append(c.text());
						}
						
						String play_num = info_element.select(".play_time").first().text();
						
						Elements directors = info_element.select(".director a");
						StringBuffer sb2 = new StringBuffer();
						for (Element d:directors) {
							if (sb2.length()>0)
								sb2.append(",");
							sb2.append(d.text());
						}
						
						Elements performers = info_element.select(".performer a");
						StringBuffer sb3 = new StringBuffer();
						for (Element p:performers) {
							if (sb3.length()>0)
								sb3.append(",");
							sb3.append(p.text());
						}
						
						System.out.println("vurl=" + vurl
								+ "\nvimg=" + vimg
								+ "\ntitle=" + title 
								+ "\nscore=" + score
								+ "\ncategorys=" + sb1.toString() 
								+ "\ndirectors=" + sb2.toString()
								+ "\nactors=" + sb3.toString()
								+ "\nyear=" + year 
								+ "\nclarity=" + clarity 
								+ "\nyear=" + year
								+ "\narea=" + area
								+ "\nplay_num=" + play_num
								+ "\ncomment_num=" + comment_num
								+ "\n#####################");
						
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
		
	
	public void craw_episodes_script(String script_url) 
		throws Exception
	{
		String str = util.URLConnector.getContent(script_url, 10000, "UTF-8");
		int c1 = str.indexOf("=");
		int c2 = str.indexOf("};/*");
		String jsonstr = str.substring(c1+1,c2+1);
		JSONObject jobj = new JSONObject(jsonstr);
		int total = jobj.getInt("total");
		System.out.println("=== episodes_url=" + script_url + " episodes length=" + total);
		JSONArray episodes = jobj.getJSONArray("vidlist");
		for (int i=0; i<episodes.length(); i++) {
			JSONObject e = episodes.getJSONObject(i);
			String episode_url = "http://v.qq.com" + e.getString("playUrl");
			String episode_title = e.getString("title");
			int duration = e.getInt("duration");
			String img = e.getString("pic");
			
			System.out.println("episode_url=" + episode_url 
					+ "\ntitle=" + episode_title 
					+ "\nduration=" + duration 
					+ "\nimg=" + img 
					+ "\n================================");
		}
	}
	
	public static void main(String[] args) {
		//new QQCrawler().craw_drama();
		new QQCrawler().craw_movie();
	}
}
