<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%><%@ 
	page import="util.*,java.text.*,java.net.*,org.json.*"%><%
	HttpParams hp = new HttpParams(request);
		
	try {		
		SimpleDateFormat sdf = new SimpleDateFormat("dd HH:mm:ss");
		String q = request.getQueryString();
		String m3u8str = request.getParameter("format");
		boolean isM3u8 = false;
		if (m3u8str!=null && m3u8str.equals("m3u8"))
			isM3u8 = true;
		
		StringBuffer sb2 = new StringBuffer();
		String url = "";
		int dur = 0;
		
		if (q==null || q.length()==0) {
			out.println("param is empty");
			return;
		}		
		
		if (q.indexOf("youku")>=0) {
			url = "http://v.youku.com/v_show/id_XNTk1MzY5MDE2.html";
		}
		else if (q.indexOf("qq")>=0) {
			url = "http://v.qq.com/cover/d/d6mvesrx6j2ux4x.html";
		}
		else if (q.indexOf("sohu")>=0) {
			url = "http://tv.sohu.com/20130805/n383343037.shtml";
		}
		else if (q.indexOf("m1905")>=0) {
			url = "http://www.m1905.com/vod/play/547196.shtml";
		}
		
		String uurl = "http://223.203.192.27:8008/realAddress!getJson.do?url=" + URLEncoder.encode(url, "UTF-8");
System.out.println(uurl);		
		String content = URLConnector.getContent(uurl, 60000, "UTF-8");
		if (!isM3u8) {
			out.println(content);
			return;
		}
		else {		
			JSONObject root = new JSONObject(content);
			JSONArray secs = root.getJSONArray("sections");
			JSONObject s = (secs.length()<3)?secs.getJSONObject(secs.length()-1):secs.getJSONObject(2);
			JSONArray urls = s.getJSONArray("section");
					
			StringBuffer sb = new StringBuffer();
			sb.append("#EXTM3U").append("\n");
			sb.append("#EXT-X-TARGETDURATION:").append("#total_dur#").append("\n");
			sb.append("#EXT-X-VERSION:2").append("\n");
			sb.append("#EXT-X-DISCONTINUITY").append("\n");
			
			int total = 0;
			for (int i=0; i<urls.length(); i++) {
				String tmp_url = urls.getJSONObject(i).getString("url");
				try { dur = urls.getJSONObject(i).getInt("dur"); } catch (Exception e) {}
				sb.append("#EXTINF:").append(dur).append("\n");
				sb.append(tmp_url).append("\n");
				try { total += dur; } catch (Exception e) {}
			}
			sb.append("#EXT-X-ENDLIST").append("\n");
			out.println(sb.toString().replace("#total_dur#", total+""));
		}
	}
	catch (Exception e) {
		e.printStackTrace();
	}
	finally {
		
	}
%>
