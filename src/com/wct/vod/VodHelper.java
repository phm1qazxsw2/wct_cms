package com.wct.vod;

import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;
import org.json.*;
import util.HttpUtil;
import java.io.*;
import java.util.*;

public class VodHelper {
	
	public VodHelper() {}
	
	public Video grep(String vurl)
		throws Exception
	{
		Video v = null;
		if (vurl.indexOf("dianshi_intro")>0) {
			// 电视 http://v.hao123.com/dianshi_intro/?page=1&id=19191
			Document doc = HttpUtil.getJsoupDoc(vurl);
			Elements divs = doc.select(".desc-title");
			String title = doc.select("#videoTitle").attr("value");
			int c1 = title.indexOf("（更新");
			int check_new = 0;
			if (c1>0) {
				title = title.substring(0, c1);
				check_new = 1;
			}
			
			String directors = "";
			String actors = "";
			String area = "";
			for (Element e:divs) {
				String str = e.text();
				if (str.indexOf("主演：")>=0) {
					actors = e.nextSibling().toString().replace("&nbsp;", ",");
				}
				else if (str.indexOf("导演：")>=0) {
					directors = e.nextSibling().toString().replace("&nbsp;", ",");
				}
				else if (str.indexOf("地区：")>=0) {
					area = e.nextSibling().toString().replace("&nbsp;", ",");
				}
			}
			String pic = doc.select(".poster-a img").first().attr("src");
			String long_desc = doc.select("input[name=longIntro]").attr("value");
			String short_desc = doc.select("input[name=shortIntro]").attr("value");
			String videoId = doc.select("#videoId").attr("value");
			
			System.out.println("title=" + title
					+ "\n directors=" + directors
					+ "\n actors=" + actors
					+ "\n area=" + area
					+ "\n pic=" + pic
					+ "\n long_desc=" + long_desc
					+ "\n short_desc=" + short_desc);
			
			v = new VideoMgr(0).find("`key`='hao123-" + videoId + "'");
			if (v==null) {
				v = new Video();
				v.setKey("hao123-" + videoId);
				v.setType(Video.TYPE_DRAMA);
				v.setHao123url(vurl);
				new VideoMgr(0).create(v);
			}
			
			v.setName(title);
			v.setShort_desc(short_desc);
			v.setLong_desc(long_desc);
			v.setPic(pic);
			v.setArea(area);
			v.setActor(actors);
			v.setDirector(directors);
			v.setUpdated(new Date());
			v.setCheck_new(check_new);
			
			String source_info_url = "http://v.hao123.com/dianshi_intro/?dtype=tvPlayUrl&service=json&id=";			
			JSONArray ja = new JSONArray(util.URLConnector.getContent(source_info_url + videoId, 10000, "UTF-8"));
			
			int episode_num = 0;
			int counter = 0;
			Map<Integer, Episode> eMap = new HashMap<Integer, Episode>();
			
			for (int i=0; i<ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				JSONObject jo1 = jo.getJSONObject("site_info");
				String name = jo1.getString("name");
				String site = jo1.getString("site");
				System.out.println("site=" + site + " name=" + name);
				JSONArray jarr = jo.getJSONArray("episodes");
				Episode e = null;
				for (int j=0; j<jarr.length(); j++) {
					JSONObject jo2 = jarr.getJSONObject(j);
					String single_title = jo2.getString("single_title");
					String url = jo2.getString("url");
					int episode = jo2.getInt("episode");
					
					if (i==0) {
						e = new EpisodeMgr(0).find("video_id=" + v.getId() + " and num='" + episode + "'");
						if (e==null) {
							e = new Episode();
							e.setVideo_id(v.getId());
							e.setNum(episode+"");
							e.setName(single_title);
							new EpisodeMgr(0).create(e);
						}
						eMap.put(j, e);
					}
					else
						e = eMap.get(j);
					
					SrcUrl u = new SrcUrlMgr(0).find("episode_id=" + e.getId() + " and site='" + site + "'");
					if (u==null) {
						u = new SrcUrl();
						u.setSite(site);
						u.setEpisode_id(e.getId());
						u.setUrl(url);
						u.setVideo_id(v.getId());
						new SrcUrlMgr(0).create(u);
					}
					counter ++;
					episode_num = jarr.length();
//					int total = ja.length() * jarr.length();
//					System.out.println("done " + counter + "/" + total);
				}				
			}
			
			v.setEpisode_num(episode_num); 
			new VideoMgr(0).save(v);			
		}
		else if (vurl.indexOf("dianying_intro")>0) {
			// 电影 http://v.hao123.com/dianying_intro/?id=108639&page=1&frp=browse
			Document doc = HttpUtil.getJsoupDoc(vurl);
			Elements divs = doc.select(".desc-title");
			String title = doc.select("#videoTitle").attr("value");
			String alias = "";
			String directors = "";
			String actors = "";
			String area = "";
			for (Element e:divs) {
				String str = e.text();
				if (str.indexOf("别名：")>=0) {
					alias = e.nextSibling().toString().replace("&nbsp;", ",");
					//title = e.nextElementSibling().text().replace("&nbsp;", ",");
				}
				else if (str.indexOf("主演：")>=0) {
					actors = e.nextSibling().toString().replace("&nbsp;", ",");
				}
				else if (str.indexOf("导演：")>=0) {
					directors = e.nextSibling().toString().replace("&nbsp;", ",");
				}
				else if (str.indexOf("地区：")>=0) {
					area = e.nextSibling().toString().replace("&nbsp;", ",");
				}
			}
			String pic = doc.select(".poster-a img").first().attr("src");
			String long_desc = doc.select("input[name=longIntro]").attr("value");
			String short_desc = doc.select("input[name=shortIntro]").attr("value");
			int year = 0;
			try {
				String yearstr = doc.select(".year").first().text();
				System.out.println("yearstr=" + yearstr + " " + (int)yearstr.charAt(0));
				yearstr = yearstr.replace("\u00A0", "");
				year = Integer.parseInt(yearstr); 
			} 
			catch (Exception e) {}
			String videoId = doc.select("#videoId").attr("value");
			
			v = new VideoMgr(0).find("`key`='hao123-" + videoId + "'");
			if (v==null) {
				v = new Video();
				v.setKey("hao123-" + videoId);
				v.setType(Video.TYPE_MOVIE);
				v.setHao123url(vurl);
				new VideoMgr(0).create(v);
			}
			
			String source_info_url = "http://v.hao123.com/dianying_intro/?dtype=playUrl&service=json&id=";
			JSONArray ja = new JSONArray(util.URLConnector.getContent(source_info_url + videoId, 10000, "UTF-8"));
			for (int i=0; i<ja.length(); i++) {
				JSONObject jo = ja.getJSONObject(i);
				String link = jo.getString("link");
				String site = jo.getString("site");
				String name = jo.getString("name");
				int speed = jo.getInt("speed");
				
				SrcUrl u = new SrcUrlMgr(0).find("video_id=" + v.getId() + " and site='" + site + "'");
				if (u==null) {
					u = new SrcUrl();
					u.setSite(site);
					u.setVideo_id(v.getId());
					u.setUrl(link);
					new SrcUrlMgr(0).create(u);
				}
			}
			
			v.setName(title);
			v.setShort_desc(short_desc);
			v.setLong_desc(long_desc);
			v.setPic(pic);
			v.setArea(area);
			v.setActor(actors);
			v.setDirector(directors);
			v.setYear(year);
			v.setUpdated(new Date());
			new VideoMgr(0).save(v);
		}
		else if (vurl.indexOf("dongman_intro")>0) {
			// 动漫 http://v.hao123.com/dongman_intro/?id=847&page=1&frp=browse
			Document doc = HttpUtil.getJsoupDoc(vurl);
			
			String html = doc.html();
			int c1 = html.indexOf("aldJson");
			int c2 = html.indexOf("=", c1+1);
			int c3 = html.indexOf("[", c2+1);
			int c4 = html.indexOf("}];", c3);
			String jsonstr = html.substring(c3, c4+2);
			JSONObject jo = new JSONArray(jsonstr).getJSONObject(0);
			
			String vid = jo.getString("id");
			String title = jo.getString("title");
			String rating = jo.getString("rating");
			String desc = jo.getString("brief");
			String year = jo.getString("al_date");
			String pic = jo.getString("big_poster");
			StringBuffer sb = new StringBuffer();
			JSONArray ja1 = jo.getJSONArray("type");
			for (int i=0; i<ja1.length(); i++) {
				if (sb.length()>0)
					sb.append(",");
				sb.append(ja1.getString(i));
			}
			StringBuffer sb2 = new StringBuffer();
			JSONArray ja2 = jo.getJSONArray("area");
			for (int i=0; i<ja2.length(); i++) {
				if (sb2.length()>0)
					sb2.append(",");
				sb2.append(ja2.getString(i));
			}
			StringBuffer sb3 = new StringBuffer();
			JSONArray ja3 = jo.getJSONArray("director");
			for (int i=0; i<ja3.length(); i++) {
				if (sb3.length()>0)
					sb3.append(",");
				sb3.append(ja3.getString(i));
			}
			StringBuffer sb4 = new StringBuffer();
			JSONArray ja4 = jo.getJSONArray("leader");
			for (int i=0; i<ja4.length(); i++) {
				if (sb4.length()>0)
					sb4.append(",");
				sb4.append(ja4.getString(i));
			}
						
			v = new VideoMgr(0).find("`key`='hao123-" + vid + "'");
			if (v==null) {
				v = new Video();
				v.setKey("hao123-" + vid);
				v.setType(Video.TYPE_ANIMATION);
				v.setHao123url(vurl);
				new VideoMgr(0).create(v);
			}
			
			v.setName(title);
			v.setLong_desc(desc);
			v.setPic(pic);
			v.setArea(sb2.toString());
			v.setActor(sb4.toString());
			v.setDirector(sb3.toString());
			v.setUpdated(new Date());
			
			JSONArray sites = jo.getJSONArray("sites");
			
			JSONObject jo1 = sites.getJSONObject(0);
			String site_name = jo1.getString("site_name");
			String site_url = jo1.getString("site_url");
			System.out.println("site=" + site_name + " site_url=" + site_url);
			
			int episode_num = 0;
			int counter = 0;
			Map<Integer, Episode> eMap = new HashMap<Integer, Episode>();
			
			String site_prefix = "http://video.baidu.com/hcomicsingles/?id=<id>&site=<site>";
			for (int i=0; i<sites.length(); i++) {
				jo1 = sites.getJSONObject(i);
				site_name = jo1.getString("site_name");
				site_url = jo1.getString("site_url");
				
				String episode_url2 = site_prefix.replace("<id>", vid).replace("<site>",site_url);
				System.out.println("site=" + site_name + " site_url=" + site_url + " episode_url2=" + episode_url2);
				JSONObject json_site = new JSONObject(util.URLConnector.getContent(episode_url2, 10000, "UTF-8"));
				
				JSONArray jarr = json_site.getJSONArray("videos");
				Episode e = null;
				
				for (int j=0; j<jarr.length(); j++) {
					JSONObject jo2 = jarr.getJSONObject(j);
					String single_title = jo2.getString("single_title");
					String url = jo2.getString("url");
					int episode = jo2.getInt("episode");

					if (i==0) {
						e = new EpisodeMgr(0).find("video_id=" + v.getId() + " and num='" + episode + "'");
						if (e==null) {
							e = new Episode();
							e.setVideo_id(v.getId());
							e.setNum(episode+"");
							e.setName(single_title);
							new EpisodeMgr(0).create(e);
						}
						eMap.put(j, e);
					}
					else
						e = eMap.get(j);
					
					SrcUrl u = new SrcUrlMgr(0).find("episode_id=" + e.getId() + " and site='" + site_url + "'");
					if (u==null) {
						u = new SrcUrl();
						u.setSite(site_url);
						u.setEpisode_id(e.getId());
						u.setUrl(url);
						u.setVideo_id(v.getId());
						new SrcUrlMgr(0).create(u);
					}
					counter ++;
					episode_num = jarr.length();
//					int total = sites.length() * jarr.length();
//					System.out.println("done " + counter + "/" + total);
				}
			}
			
			v.setEpisode_num(episode_num); 
			new VideoMgr(0).save(v);
		}
		else if (vurl.indexOf("zongyi_intro")>0) {
			// 综艺 http://v.hao123.com/zongyi_intro/?id=7695&page=1&frp=browse
			Document doc = HttpUtil.getJsoupDoc(vurl);
			
			String html = doc.html();
			int c1 = html.indexOf("aldJson");
			int c2 = html.indexOf("=", c1+1);
			int c3 = html.indexOf("[", c2+1);
			int c4 = html.indexOf("}];", c3);
			String jsonstr = html.substring(c3, c4+2);
			JSONObject jo = new JSONArray(jsonstr).getJSONObject(0);
			
			String vid = jo.getString("id");
			String title = jo.getString("title");
			String rating = jo.getString("rating");
			String desc = jo.getString("brief");
			String year = jo.getString("al_date");
			String pic = jo.getString("big_poster");
			StringBuffer sb = new StringBuffer();
			JSONArray ja1 = jo.getJSONArray("type");
			for (int i=0; i<ja1.length(); i++) {
				if (sb.length()>0)
					sb.append(",");
				sb.append(ja1.getString(i));
			}
			StringBuffer sb2 = new StringBuffer();
			JSONArray ja2 = jo.getJSONArray("area");
			for (int i=0; i<ja2.length(); i++) {
				if (sb2.length()>0)
					sb2.append(",");
				sb2.append(ja2.getString(i));
			}
			StringBuffer sb3 = new StringBuffer();
			JSONArray ja3 = jo.getJSONArray("host");
			for (int i=0; i<ja3.length(); i++) {
				if (sb3.length()>0)
					sb3.append(",");
				sb3.append(ja3.getString(i));
			}
			
			v = new VideoMgr(0).find("`key`='hao123-" + vid + "'");
			if (v==null) {
				v = new Video();
				v.setKey("hao123-" + vid);
				v.setType(Video.TYPE_ENTERTAINMENT);
				v.setHao123url(vurl);
				new VideoMgr(0).create(v);
			}
			
			v.setName(title);
			v.setLong_desc(desc);
			v.setPic(pic);
			v.setArea(sb2.toString());
			v.setActor(sb3.toString());
			v.setUpdated(new Date());
			
			JSONArray sites = jo.getJSONArray("sites");
			JSONArray years = jo.getJSONArray("years");
			
			int episode_num = 0;
			int counter = 0;
			Map<Integer, Episode> eMap = new HashMap<Integer, Episode>();
			
			String site_prefix = "http://video.baidu.com/htvshowsingles/?id=<id>&year=<year>&site=<site>";
			for (int i=0; i<sites.length(); i++) {
				for (int y=0; y<years.length(); y++) {
					String cur_year = years.getString(y);
					JSONObject jo1 = sites.getJSONObject(i);
					String site_name = jo1.getString("site_name");
					String site_url = jo1.getString("site_url");
					
					String episode_url2 = site_prefix.replace("<id>", vid)
						.replace("<site>",site_url).replace("<year>", cur_year);
					System.out.println("site=" + site_name + " site_url=" + site_url + 
							" episode_url2=" + episode_url2 + " year=" + cur_year);
					JSONObject json_site = new JSONObject(util.URLConnector.getContent(
							episode_url2, 10000, "UTF-8"));
					
					String return_year = json_site.getString("year");
					if (!return_year.equals(cur_year)) {
						System.out.println(site_url + " has no year " + cur_year);
						continue;
					}
					
					JSONArray jarr = json_site.getJSONArray("videos");
					Episode e = null;

					for (int j=0; j<jarr.length(); j++) {
						JSONObject jo2 = jarr.getJSONObject(j);
						String single_title = jo2.getString("title");
						String url = jo2.getString("url");
						String episode = jo2.getString("episode");
						StringBuffer sb4 = new StringBuffer();
						JSONArray ja4 = jo2.getJSONArray("guest");
						for (int k=0; k<ja4.length(); k++) {
							if (sb4.length()>0)
								sb4.append(",");
							sb4.append(ja4.getString(k));
						}
						
						if (i==0) {
							e = new EpisodeMgr(0).find("video_id=" + v.getId() + " and num='" + episode + "'");
							if (e==null) {
								e = new Episode();
								e.setVideo_id(v.getId());
								e.setNum(episode+"");
								e.setName(single_title);
								new EpisodeMgr(0).create(e);
							}
							eMap.put(j, e);
						}
						else
							e = eMap.get(j);
						
						SrcUrl u = new SrcUrlMgr(0).find("episode_id=" + e.getId() + " and site='" + site_url + "'");
						if (u==null) {
							u = new SrcUrl();
							u.setSite(site_url);
							u.setEpisode_id(e.getId());
							u.setUrl(url);
							u.setVideo_id(v.getId());
							new SrcUrlMgr(0).create(u);
						}
						counter ++;
						episode_num = jarr.length();
//						int total = sites.length() * years.length() * jarr.length();
//						System.out.println("done " + counter + "/" + total);
					}
				}
			}
			
			v.setEpisode_num(episode_num); 
			new VideoMgr(0).save(v);
		}
		
		else			
			throw new Exception("video url 格式不支援");
		
		return v;
	}
	
	public String getMenuName(int menu_id)
		throws Exception
	{
		if (menuMap==null)
			getMenuItems();
		MenuItem m = menuMap.get(menu_id);
		if (m==null)
			return "";
		return m.getName();
	}
	
	private Map<Integer, MenuItem> menuMap = null;

	public List<MenuItem> getMenuItems()
		throws Exception
	{
		List<MenuItem> ret = new ArrayList<MenuItem>(100);
		menuMap = new HashMap<Integer, MenuItem>(100);
		
		Document doc = new ApiHelper().getMenuDoc(); 
		Elements items = doc.select("item");
		for (Element item:items) {
			Element parent = item.parent();
			Element grand = parent.parent();
			MenuItem i = new MenuItem();
			i.setId(Integer.parseInt(item.attr("id")));
			
			StringBuffer sb = new StringBuffer();
			if (grand.tagName().equals("model")) {
				sb.append(grand.attr("class")).append("->");			
				sb.append(parent.attr("name")).append("->");
			}
			else if (parent.tagName().equals("model")) {
				sb.append(parent.attr("class")).append("->");
			}
			sb.append(item.attr("name"));
			i.setName(sb.toString());
			
			int hidden = 0;
			if (item.attr("class").indexOf("hidden")>=0)
				hidden = 1;
			i.setHidden(hidden);
			ret.add(i);
			menuMap.put(i.getId(), i);
		}
		return ret;
	}
}
