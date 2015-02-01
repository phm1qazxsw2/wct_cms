package com.wct.siteparser;

import util.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class LetvCrawler {
	public LetvCrawler() {
	}
	
	public void craw_drama() {
		try {
			for (int i = 1; i<2; i++) {
				Document doc = HttpUtil
					.getJsoupDoc("http://so.letv.com/list/c2_t-1_a-1_y-1_f-1_at1_o1_i-1_p" + i + ".html");
				
				System.out.println("#### page " + i);
				
				if (doc.text().indexOf("抱歉,没有找到符合的影片")>=0) {
					break;
				}
				
				// info2_box 是比较详细列表内容
				Elements iter1 = doc.select(".info2_box");
				for (Element info2_box : iter1) {
					try {
						Element thumb_anchor = info2_box.select(".imgA")
								.first();
						String vurl = thumb_anchor.attr("href");
						Element thumb_img = thumb_anchor.select("img").first();
						String vimg = thumb_img.attr("src");
						String title = thumb_img.attr("title");
						String num = thumb_anchor.select("i").first().text();
						String score = info2_box.select(".sorce i").first()
								.text();

						Element director_dd = info2_box.select("dd").get(1);
						Elements iter2 = director_dd.select("a");
						StringBuffer sb1 = new StringBuffer();
						for (Element a : iter2) {
							if (sb1.length() > 0)
								sb1.append(",");
							sb1.append(a.text());
						}

						Element actor_dd = info2_box.select("dd").get(2);
						Elements iter3 = actor_dd.select("a");
						StringBuffer sb2 = new StringBuffer();
						for (Element a : iter3) {
							if (sb2.length() > 0)
								sb2.append(",");
							sb2.append(a.text());
						}

						String year = info2_box.select(".year_dl a").first()
								.text();

						System.out.println("vurl=" + vurl + "\nvimg=" + vimg
								+ "\ntitle=" + title + "\nnum=" + num
								+ "\nscore=" + score + "\ndirectors="
								+ sb1.toString() + "\nactors=" + sb2.toString()
								+ "\nyear=" + year + "\n##################### episode info ######################");
						
						craw_drama_episodes(vurl);
						
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void craw_drama_episodes(String episodes_url) 
		throws Exception
	{ 
		Document doc = HttpUtil.getJsoupDoc(episodes_url); 
		Elements episodes = doc.select(".listText .w120");
		System.out.println("=== episode length=" + episodes.size());
		for (Element episode:episodes) {
			Element anchor = episode.select("a").first();
			String episode_url = anchor.attr("href");
			Element img = anchor.select("img").first();
			String img_src = img.attr("src");
			String title = img.attr("title");
			String duration = anchor.select(".time").first().text();
			Element dd = episode.select("dd").first();
			String episode_which = dd.select("a").first().text();
			//String desc = dd.select(".p2").first().text();
			System.out.println("episode_url=" + episode_url + "\nimg_src=" + img_src
					+ "\ntitle=" + title + 
					"\nduration=" + duration
					+ "\nepisode_which=" + episode_which + "\ndesc="
					+ "================================");
		} 
	}
	
	public void craw_movie() {
		try {
			// http://so.letv.com/list/c1_t-1_a-1_y-1_f-1_at1_o7_i-1_p1.html
			for (int i = 1; i<2; i++) {
				Document doc = HttpUtil
					.getJsoupDoc("http://so.letv.com/list/c1_t-1_a-1_y-1_f-1_at1_o7_i-1_p" + i + ".html");
				
				System.out.println("#### page " + i);
				
				if (doc.text().indexOf("抱歉,没有找到符合的影片")>=0) {
					break;
				}
				
				// info2_box 是比较详细列表内容
				Elements iter1 = doc.select(".info2_box");
				for (Element info2_box : iter1) {
					try {
						Element thumb_anchor = info2_box.select(".imgA")
								.first();
						String vurl = thumb_anchor.attr("href");
						Element thumb_img = thumb_anchor.select("img").first();
						String vimg = thumb_img.attr("src");
						
						String title = info2_box.select(".tit h1").first().text();
						String score = info2_box.select(".sorce i").first().text();
						
						Element director_dd = info2_box.select("dd").get(1);
						Elements iter2 = director_dd.select("a");
						StringBuffer sb1 = new StringBuffer();
						for (Element a : iter2) {
							if (sb1.length() > 0)
								sb1.append(",");
							sb1.append(a.text());
						}

						Element actor_dd = info2_box.select("dd").get(2);
						Elements iter3 = actor_dd.select("a");
						StringBuffer sb2 = new StringBuffer();
						for (Element a : iter3) {
							if (sb2.length() > 0)
								sb2.append(",");
							sb2.append(a.text());
						}

						String year = info2_box.select(".year_dl a").first()
								.text();

						System.out.println("vurl=" + vurl + "\nvimg=" + vimg
								+ "\ntitle=" + title
								+ "\nscore=" + score + "\ndirectors="
								+ sb1.toString() + "\nactors=" + sb2.toString()
								+ "\nyear=" + year + "\n##################### episode info ######################");
						
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void craw_entertainment() {
		try {
			// http://so.letv.com/list/c11_i-1_a-1_t-1_tv-1_o3_p1.html
			for (int i = 1; i<2; i++) {
				Document doc = HttpUtil
					.getJsoupDoc("http://so.letv.com/list/c11_i-1_a-1_t-1_tv-1_o3_p" + i + ".html");
				
				System.out.println("#### page " + i);
				
				if (doc.text().indexOf("抱歉,没有找到符合的影片")>=0) {
					break;
				}
				
				// info2_box 是比较详细列表内容
				Elements iter1 = doc.select(".info2_box");
				for (Element info2_box : iter1) {
					try {
						Element thumb_anchor = info2_box.select(".imgA")
								.first();
						String vurl = thumb_anchor.attr("href");
						Element thumb_img = thumb_anchor.select("img").first();
						String vimg = thumb_img.attr("src");
						String title = thumb_img.attr("alt");
						
						String score = info2_box.select(".sorce").first().text();
						
						Element director_dd = info2_box.select("dd").get(1);
						Elements iter2 = director_dd.select("a");
						StringBuffer sb1 = new StringBuffer();
						for (Element a : iter2) {
							if (sb1.length() > 0)
								sb1.append(",");
							sb1.append(a.text());
						}

						Element actor_dd = info2_box.select("dd").get(2);
						Elements iter3 = actor_dd.select("a");
						StringBuffer sb2 = new StringBuffer();
						for (Element a : iter3) {
							if (sb2.length() > 0)
								sb2.append(",");
							sb2.append(a.text());
						}

						String year = info2_box.select(".year_dl").first().text();

						System.out.println("vurl=" + vurl + "\nvimg=" + vimg
								+ "\ntitle=" + title 
								+ "\nscore=" + score + "\n主持人="
								+ sb1.toString()
								+ "\nyear=" + year + "\n##################### episode info ######################");
						
						craw_entertainment_episodes(vurl);
						
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
	
	public void craw_entertainment_episodes(String episodes_url) 
		throws Exception
	{ 
		Document doc = HttpUtil.getJsoupDoc(episodes_url); 
		Elements episodes = doc.select(".w120");
		System.out.println("=== episode length=" + episodes.size());
		for (Element episode:episodes) {
			Element anchor = episode.select("a").first();
			String episode_url = anchor.attr("href");
			String title = anchor.attr("title");
			String duration = "";
			try { duration=episode.select(".time").first().text(); } catch (Exception e) {}
			Element img = anchor.select("img").first();
			String img_src = img.attr("src");
			
			System.out.println("episode_url=" + episode_url 
					+ "\nimg_src=" + img_src
					+ "\ntitle=" + title 
					+ "\nduration=" + duration
					+ "\n================================");
		} 
	}
	

	public static void main(String[] args) {
		// new LetvCrawler().craw_drama();
		// new LetvCrawler().craw_movie();
		new LetvCrawler().craw_entertainment();
	}
}
