package moniting;

import util.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

public class VodMonitor {
	StringBuffer sb;
	int sleeptime;
	public VodMonitor(int sleeptime) {
		sb = new StringBuffer();
		this.sleeptime = sleeptime;
	}	
	
	public String getLinkMenu2(String name, String id)
		throws Exception
	{
		StringBuffer l = new StringBuffer();
		l.append("<a target=_blank href=\"").append("post_menu2.jsp?id=").append(id).append("\">").append(name).append("</a>");
		return l.toString();
	}
	
	public String getLinkPage(String name, String id)
		throws Exception
	{
		StringBuffer l = new StringBuffer();
		l.append("<a target=_blank href=\"").append("post_menu2_movies.jsp?id=").append(id).append("\">").append(name).append("</a>");
		return l.toString();
	}
	
	public String getDetail(String id)
		throws Exception
	{
		StringBuffer l = new StringBuffer();
		l.append("<a target=_blank href=\"").append("post_movie_detail.jsp?id=").append(id).append("\">").append(id).append("</a>");
		return l.toString();
	}
	
	public String getLink(String url)
		throws Exception
	{
		StringBuffer l = new StringBuffer();
		l.append("<a target=_blank href=\"").append(url).append("\">").append(url).append("</a>");
		return l.toString();
	}
	
	void sleep() throws Exception
	{
		Thread.sleep(sleeptime);
	}
	
	public void check() 
		throws Exception
	{
		String server = "http://223.203.193.149";
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

		int page_size = 10;
		String result = util.HttpUtil.doPost2(server + "/nmpsp_server/ali/service", content);
		Document doc = Jsoup.parse(result);
		String token = doc.select("token").first().text();
		// 一级目录
		String content_meun1 = 
			"<request method=\"getMovieMenus\">"+
			"<parameters>"+
			"<token>" + token + "</token>"+
			"</parameters>"+
			"</request>";	
		
		String content_menu2 = 				
			"<request method=\"getMovieSecondary\">"+
			"<parameters>"+
				"<token>" + token + "</token>"+
				"<code>MOVIE</code>" +
				"<menuId>#menu1_id#</menuId>" +
			"</parameters>"+
			"</request>";			

		String content_moviepage = 
			"<request method=\"getMovie\">"+
			"<parameters>"+
				"<token>" + token + "</token>"+
				"<code>MOVIE</code>" +
				"<menuId>#menu2_id#</menuId>" +
				"<currentPage>#page#</currentPage>" +
				"<pageSize>" + page_size + "</pageSize>" +
				"<currCount>0</currCount>" +
			"</parameters>"+
			"</request>";
		
		String movie_detail = 
			"<request method=\"getRecommandDetail\">" +
			"<parameters>" +
				"<token>" + token + "</token>" +
				"<movieId>#movie_id#</movieId>" +
			"</parameters>"+
			"</request>";				
		
		result = util.HttpUtil.doPost2(server + "/nmpsp_server/ali/video/service", content_meun1);
		doc = Jsoup.parse(result);
		Elements iter1 = doc.select("menus menu");
		sb.append("<pre>\n");
		
		int num1 = 0;
		int total = 0;
		int size1 = iter1.size();
		
		for (int i=0; i<iter1.size(); i++) {
			num1 ++;
			try {
				Element e=iter1.get(i);
				String str = e.text();
				int c = str.indexOf("|");
				int c2 = str.indexOf("|",c+1);
				String id = str.substring(0,c);
				String name = str.substring(c+1,c2);
				sb.append(getLinkMenu2(name,id)).append("\n");
				String cnt2 = content_menu2.replace("#menu1_id#", id+"");
				String result2 = util.HttpUtil.doPost2(server + "/nmpsp_server/ali/video/service", cnt2);
				Document doc2 = Jsoup.parse(result2);
				Elements iter2 = doc2.select("secondarys secondary");
				int num2 = 0;
				int size2 = iter2.size();
				for (int j=0; j<iter2.size(); j++) {
					num2 ++;
					try {
						Element e2 = iter2.get(j);
						String str2 = e2.text();
						int c3 = str2.indexOf("|");
						int c4 = str2.indexOf("|",c3+1);
						String id2 = str2.substring(0,c3);
						String name2 = str2.substring(c3+1,c4);
						sb.append("\t\t" + getLinkPage(name2,id2)).append("\n");
						String cnt3 = content_moviepage.replace("#menu2_id#", id2+"").
							replace("#page#", "0");
						String result3 = util.HttpUtil.doPost2(server + "/nmpsp_server/ali/video/service", cnt3);
						
						Document doc3 = Jsoup.parse(result3);
						Elements iter3 = doc3.select("movie");							
						//for (int k=0; k<iter3.size(); k++) {
						int num3 = 0;
						int size3 = iter3.size();
						for (int k=0; k<page_size && k<iter3.size(); k++) {
							num3 ++;
							try {
								Element e3 = iter3.get(k);
								String movieId = e3.select("movieId").first().text();
								String movieName = e3.select("name").first().text();
								String cnt4 = movie_detail.replace("#movie_id#", movieId);
								String result4 = util.HttpUtil.doPost2(server + "/nmpsp_server/ali/video/service", cnt4);
								sb.append("\t\t\tmovieId=" + getDetail(movieId) + " name=" + movieName).append("\n");
								Document doc4 = Jsoup.parse(result4);
								Elements iter4 = doc4.select("relevance");
								if (iter4.size()==0) {
									// MOVIE
									//for (int m=0; m<iter4.size(); m++) {
									Elements iter5 = doc4.select("urls url");
									for (int m=0; m<2 && m<iter5.size(); m++) {
										try {
											Element e4 = iter5.get(m);
											String url = e4.text();
											String site = e4.attr("providerCode");
											sb.append("\t\t\t\tsite=" + site + " url=" + getLink(url)).append("\n");
											total ++;
											sleep();
										} 
										catch (Exception x3) {
											x3.printStackTrace();
										}
									}
								}
								else {
									// 劇集
									Elements iter5 = doc4.select("relevance");
									for (int m=0; m<3 && m<iter5.size(); m++) {
										try {
											Element e5 = iter5.get(m);
											String suffix = e5.attr("number");
											Elements iter6 = e5.select("urls url");
											for (int n=0; n<2 && n<iter6.size(); n++) {
												Element e6 = iter6.get(n);
												String url = e6.text();
												String site = e6.attr("providerCode");
												sb.append("\t\t\t\tsuffix=" + suffix + " site=" + site + " url=" + getLink(url)).append("\n");
												total ++;
												sleep();
											}
										} 
										catch (Exception x3) {
											x3.printStackTrace();
										}
									}
								}
							} 
							catch (Exception x3) {
								x3.printStackTrace();
							}
							System.out.println(total + "/1757");
						}
					}
					catch (Exception x2) {
						x2.printStackTrace();
					}
				}
			}
			catch (Exception x) {
				x.printStackTrace();
				break;
			}
		}
		sb.append("</pre>\n");
	}
	
	public String output() {
		return sb.toString();
	}
	
	public static void main(String[] args)
	{
		try {
			VodMonitor v = new VodMonitor(100);
			v.check();
			System.out.println(v.output());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
