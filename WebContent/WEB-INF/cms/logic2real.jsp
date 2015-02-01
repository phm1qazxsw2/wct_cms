<%@ page language="java" import="java.util.*"
	contentType="text/xml;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,java.net.*,org.json.*"%><%
	HttpParams hp = new HttpParams(request);
		
	try {		
System.out.println("## in logic2real");	
		String url = request.getParameter("url");
		
		StringBuffer sb = new StringBuffer();
sb.append("<data>");
sb.append("<url>");
sb.append("<foobar><![CDATA[http://220.181.155.70/12/13/61/2121684787.0.letv?crypt=55aa7f2e126&b=381&nlh=3072&bf=26&gn=820&p2p=1&video_type=flv&opck=1&check=0&tm=1375970400&key=686ca36a766b253d2f9271a146848496&proxy=3702889411&cips=219.142.192.134&geo=CN-1-13-1&lgn=letv&s=3&df=12/13/61/2121684787.0.flv&br=381]]></foobar>");
sb.append("<dur>6522</dur></url>");
sb.append("<total_dur>6522</total_dur>");
sb.append("</data>");
System.out.println("## here1");
		out.println(sb.toString());
		
/*		
		if (url==null) {			
			return;
		}
		String uurl = "http://223.203.192.27:8008/realAddress!getJson.do?url=" + URLEncoder.encode(url, "UTF-8");
System.out.println("#### 1 " + uurl);

		String content = URLConnector.getContent(uurl, 10000, "UTF-8");
		JSONObject root = new JSONObject(content);
		JSONArray secs = root.getJSONArray("sections");
		JSONObject s = (secs.length()<3)?secs.getJSONObject(secs.length()-1):secs.getJSONObject(2);
		JSONArray urls = s.getJSONArray("section");
		
		StringBuffer sb = new StringBuffer();
		int total = 0;
		sb.append("<data>");
			sb.append("<url>");
				for (int i=0; i<urls.length(); i++) {
					String tmp_url = urls.getJSONObject(i).getString("url");
					String dur = urls.getJSONObject(i).getString("dur");
					sb.append("<foobar>");
						sb.append("<![CDATA[");
						sb.append(tmp_url);
						sb.append("]]>");
					sb.append("</foobar>");
					sb.append("<dur>").append(dur).append("</dur>");
					try { total += Integer.parseInt(dur); } catch (Exception e) {}
				}
			sb.append("</url>");
			sb.append("<total_dur>").append(total).append("</total_dur>");
		sb.append("</data>");
		out.println(sb.toString());
*/		

/*		
		String content = URLConnector.getContent(uurl, 10000, "UTF-8");
		JSONObject root = new JSONObject(content);
		int state = root.getInt("stateNum");
		if (state!=200) {
			out.print(content);
			return;
		}
		
System.out.println("## 1");
		int totleType = root.getInt("totleType");		
		JSONArray secs = root.getJSONArray("sections");
		JSONObject s = (secs.length()<3)?secs.getJSONObject(secs.length()-1):secs.getJSONObject(2);
		
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"stateNum\":\"200\"");
		sb.append(",\"stateMessage\":\"服务正常\"");
		sb.append(",\"totleType\":\"1\"");
		sb.append(",\"sections\":");
System.out.println("## 2");
			sb.append("{");
				sb.append("\"count\":\"").append(s.getInt("count")).append("\"");
				sb.append(",\"section\":[");
					JSONArray urls = s.getJSONArray("section");
					for (int i=0; i<urls.length(); i++) {
						if (i>0)
							sb.append(",");
						sb.append("\"").append(urls.getJSONObject(i).getString("url")).append("\"");
					}
				sb.append("]");
				sb.append(",\"total_dur\":\"\"");
				sb.append(",\"type\":\"1\"");
			sb.append("}");
		sb.append("}");
		out.print(sb.toString());
System.out.println("## 3");
*/
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	finally {
		
	}
%>
