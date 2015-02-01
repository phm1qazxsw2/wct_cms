<%@ page language="java" import="java.util.*"
	contentType="text/xml;charset=UTF-8" pageEncoding="UTF-8"%><%@ 
	page import="util.*,java.text.*,java.net.*,org.json.*"%><%
	HttpParams hp = new HttpParams(request);
		
	try {		
//System.out.println("## 6 " + request.getRemoteAddr());
		boolean isJson = hp.getStr("type","").equals("json");

		SimpleDateFormat sdf = new SimpleDateFormat("dd HH:mm:ss");
		String q = request.getQueryString();
		StringBuffer sb2 = new StringBuffer();
		String url = "";
		int dur = 0;
		if (q.charAt(0)=='2') {
			//惊天战神
			url = "http://www.letv.com/ptv/pplay/71739/1.html";
			//url = "http://220.181.155.70/12/13/61/2121684787.0.letv?crypt=55aa7f2e108&b=381&nlh=3072&bf=23&gn=820&p2p=1&video_type=flv&opck=1&check=0&tm=1375988400&key=c0807561e0b2f4ec7d04b404dff8ab02&proxy=3702889411&cips=219.142.192.130&geo=CN-1-13-1&lgn=letv&s=3&df=12/13/61/2121684787.0.flv&br=381";
			dur = 6522;
		}
		else if (q.charAt(0)=='3') {
			//逆世界
			url = "http://www.letv.com/ptv/pplay/86449/1.html";
			//url = "http://220.181.155.74/28/12/54/letv-uts/2422026-AVC-254616-AAC-31586-5775120-215619945-0d8b7b06ea371869a93f525a34d5ab74-1365805883824.letv?crypt=28aa7f2e82&b=299&nlh=3072&bf=22&gn=820&p2p=1&video_type=flv&opck=1&check=0&tm=1375988400&key=39efb429c224f3f36ff529bb04370fc4&proxy=3702889382&cips=219.142.192.130&geo=CN-1-13-1&lgn=letv&s=3&df=28/12/54/letv-uts/2422026-AVC-254616-AAC-31586-5775120-215619945-0d8b7b06ea371869a93f525a34d5ab74-1365805883824.flv&br=299";
			dur = 5775;
		}
		else if (q.charAt(0)=='7') {
			//叶问
			url = "http://www.letv.com/ptv/pplay/85414/1.html";
			//url = "http://f8.r.56.com/f8.c66.56.com/flvdownload/6/1/126339879289hd_vga.mp4?v=1&t=nVpXXRuAWxGjq5nlSnF_vw&r=6335&e=1376041941";
			dur = 6338;
		}
		else if (q.charAt(0)=='8') {
			//消失的子弹
			url = "http://www.letv.com/ptv/pplay/73883/1.html";
			//url = "http://220.181.155.69/30/32/35/letv-uts/1771472-AVC-254077-AAC-31586-6468240-241056099-d5605d7e427afe7f70a8c475aee9ae11-1360135886291.letv?crypt=41aa7f2e95&b=298&nlh=3072&bf=26&gn=820&p2p=1&video_type=flv&opck=1&check=0&tm=1375992000&key=1b5a2ebc594d6a04bf2b0a0a13e739f7&proxy=3702889384&cips=219.142.192.130&geo=CN-1-13-1&lgn=letv&s=3&df=30/32/35/letv-uts/1771472-AVC-254077-AAC-31586-6468240-241056099-d5605d7e427afe7f70a8c475aee9ae11-1360135886291.flv&br=298";
			dur = 6522;
		}
		else if (q.charAt(0)=='9') {
			//伦敦大道
			url = "http://v.pps.tv/play_35KALI.html";
			//url = "http://hurl.ppstv.com/ugc/6/24/668dfaa0db497e24f7b9a7f8653fdd003e0550a8/668dfaa0db497e24f7b9a7f8653fdd003e0550a8.mp4";
			dur = 6182;
		}
		else if (q.charAt(0)=='a') {
			//天地英雄->雷神
			url = "http://www.letv.com/ptv/vplay/2081835.html";
			//url = "http://220.181.155.70/12/13/61/2121684787.0.letv?crypt=55aa7f2e126&b=381&nlh=3072&bf=26&gn=820&p2p=1&video_type=flv&opck=1&check=0&tm=1375970400&key=686ca36a766b253d2f9271a146848496&proxy=3702889411&cips=219.142.192.134&geo=CN-1-13-1&lgn=letv&s=3&df=12/13/61/2121684787.0.flv&br=381";
			dur = 7993;
		}
		else if (q.charAt(0)=='x') {
			//猛虎行动
			url = "http://v.pps.tv/play_32Y7ZZ.html";
			//url = "http://hurl.ppstv.com/ugc/8/be/841edd0776c4f2da36ced424fe97bad1e71ab487/841edd0776c4f2da36ced424fe97bad1e71ab487.mp4";
			dur = 7993;
		}
		else if (q.charAt(0)=='b') {
			//白蛇传说
			url = "http://www.letv.com/ptv/pplay/41248/1.html";
			//url = "http://220.181.155.75/18/39/60/letv-uts/764521-AVC-254602-AAC-47200-6135710-243321648-63601db3221a0f7e0e645dee2fe562f5-1351803741639.letv?crypt=67aa7f2e104&b=317&nlh=3072&bf=26&gn=820&p2p=1&video_type=flv&opck=1&check=0&tm=1375993800&key=6a414d9d913f1a9d8be446dc79cfc9ea&proxy=3702879666&cips=219.142.192.130&geo=CN-1-13-1&lgn=letv&s=3&df=18/39/60/letv-uts/764521-AVC-254602-AAC-47200-6135710-243321648-63601db3221a0f7e0e645dee2fe562f5-1351803741639.flv&br=317";
			dur = 6135;
		}
		else if (q.charAt(0)=='c') {
			//观音山
			url = "http://www.letv.com/ptv/pplay/32010/1.html";
			//url = "http://220.181.155.65/30/25/15/letv-uts/1679263-AVC-254719-AAC-45528-6069840-239490219-f5573db29eeca8fbf97e6666dccc7f1e-1358726446095.letv?crypt=89aa7f2e105&b=316&nlh=3072&bf=27&gn=820&p2p=1&video_type=flv&opck=1&check=0&tm=1375993800&key=ffb0372bda8450bb59257416af0c91bd&proxy=3702889384&cips=219.142.192.130&geo=CN-1-13-1&lgn=letv&s=3&df=30/25/15/letv-uts/1679263-AVC-254719-AAC-45528-6069840-239490219-f5573db29eeca8fbf97e6666dccc7f1e-1358726446095.flv&br=316";
			dur = 6069;
		}
		else if (q.charAt(0)=='d') {
			//唐山大兄
			url = "http://www.letv.com/ptv/pplay/75886/1.html";
			//url = "http://hurl.ppstv.com/ugc/f/e5/f95b6c75afff46c158da201860dfada5f4ba9151/f95b6c75afff46c158da201860dfada5f4ba9151.mp4";
			dur = 5871;
		}
		else if (q.charAt(0)=='e') {
			//我们需要性-》浑身是劲
			url = "http://www.letv.com/ptv/vplay/1803950.html?dyhuiyuanduxiang";
			//url = "http://hurl.ppstv.com/ugc/c/bc/cc37a48912fbd8c01b7a1e5018abf106c8301620/cc37a48912fbd8c01b7a1e5018abf106c8301620.mp4";
			dur = 6522;
		}
		else if (q.charAt(0)=='y') {
			//sohu直播 
			String uu = "http://tv.zhi.in/sohu.asp?type=" + ((isJson)?"json":"xml");
			if (q.length()>1) {
				if (q.charAt(1)=='1')
					uu = "http://tv.zhi.in/sohu.asp?channel=1&type=" + ((isJson)?"json":"xml");
				else if (q.charAt(1)=='2')
					uu = "http://tv.zhi.in/sohu.asp?channel=2&type=" + ((isJson)?"json":"xml");
				else if (q.charAt(1)=='3')
					uu = "http://tv.zhi.in/sohu.asp?channel=3&type=" + ((isJson)?"json":"xml");
			}
			System.out.println(uu);	
			String content = URLConnector.getContent(uu, 10000, "UTF-8");
			out.println(content);
			System.out.println(sdf.format(new Date()) + " ## output sohu");	
			return;
		}
		else if (q.charAt(0)=='_') {
			url = q.substring(1);
			System.out.println("## _ " + url);
			dur = 123;
		}

		if (url.length()==0) {
			//System.out.println(sdf.format(new Date()) + " ## problem url");
			return;
		}
		
		String uurl = "http://223.203.192.27:8008/realAddress!getJson.do?url=" + URLEncoder.encode(url, "UTF-8");
System.out.println(uurl);		
		String content = URLConnector.getContent(uurl, 10000, "UTF-8");
		JSONObject root = new JSONObject(content);
		JSONArray secs = root.getJSONArray("sections");
		JSONObject s = (secs.length()<3)?secs.getJSONObject(secs.length()-1):secs.getJSONObject(2);
		JSONArray urls = s.getJSONArray("section");
				
		if (!isJson) {
System.out.println(sdf.format(new Date()) + " ## output xml");	
			StringBuffer sb = new StringBuffer();
			int total = 0;
			sb.append("<data>");
			sb.append("<clarity>hd</clarity>");
			sb.append("<clarity>hc</clarity>");
				sb.append("<url>");
					for (int i=0; i<urls.length(); i++) {
						String tmp_url = urls.getJSONObject(i).getString("url");
						dur = urls.getJSONObject(i).getInt("dur");
						sb.append("<foobar>");
							sb.append("<![CDATA[");
							sb.append(tmp_url);
							sb.append("]]>");
						sb.append("</foobar>");
						sb.append("<dur>").append(dur).append("</dur>");
						try { total += dur; } catch (Exception e) {}
					}
				sb.append("</url>");
				sb.append("<total_dur>").append(total).append("</total_dur>");
			sb.append("</data>");
			System.out.println(sb.toString());
			out.println(sb.toString());
		}
		else {		
System.out.println(sdf.format(new Date()) + " ## output json");		
			StringBuffer sb = new StringBuffer();
			sb.append("{");
			sb.append("\"stateNum\":\"200\"");
			sb.append(",\"stateMessage\":\"服务正常\"");
			sb.append(",\"totleType\":\"1\"");
			sb.append(",\"sections\":");
				sb.append("{");
					sb.append("\"count\":\"1\"");
					sb.append(",\"section\":[");
						for (int i=0; i<urls.length(); i++) {
							if (i>0)
								sb.append(",");
							sb.append("\"").append(urls.getJSONObject(i).getString("url")).append("\"");
						}
					sb.append("]");
					sb.append(",\"total_dur\":\"").append(dur).append("\"");
					sb.append(",\"type\":\"1\"");
				sb.append("}");
			sb.append("}");
			System.out.println(sb.toString());
			out.print(sb.toString());
		}
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	finally {
		
	}
%>
