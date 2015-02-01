<%@ page language="java" session="false" import="com.wct.util.*,com.wct.logs.*,java.text.*,java.util.*,util.*"
	pageEncoding="UTF-8" contentType="text/plain;charset=UTF-8"%><%!
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
%><%!
	static String get_video_name(String app, String channel, String video, String no)
	{
		StringBuffer sb = new StringBuffer();
    	if (app!=null && app.length()>0) {
    		sb.append(app);
    	}
    	if (channel!=null && channel.length()>0 && !channel.equals("/")) {
    		if (sb.length()>0 && sb.charAt(sb.length()-1)!='/')
    			sb.append('/');
    		sb.append(channel);
    	}
    	if (video!=null && video.length()>0) {
    		if (sb.length()>0 && sb.charAt(sb.length()-1)!='/')
    			sb.append('/');
    		sb.append(video);
    	}	
    	if (no!=null && no.length()>0) {
    		sb.append(' ');
    		sb.append(no);
    	}
    	return sb.toString();
	}
%><%
	//數據量大 先關掉,只留 new_vooloe
	int a=1;
	if (a>0)
		return;

	HttpParams hp = new HttpParams(request);
	String event = hp.getStr("event", "");
	String mac = hp.getStr("mac", "1");
	String app = hp.getStr("app", "a");
	String channel = hp.getStr("channel", "c");
	String video = hp.getStr("video", "v");
	String no = hp.getStr("no", "");
	
	
	if (event.equals("IN_LUNBO")) {
		//http://www.google-analytics.com/collect?v=1&tid=UA-49240218-2&cid=${mac}&
		//t=pageview&dp=%2Flunbo%2F${channel}%2F${video}
		String vname = get_video_name(null, channel, video, null);
		String dp = "/lunbo/" + vname;		
		//System.out.println("#1#" + event + ":" + dp);
		String ga_url = "http://www.google-analytics.com/collect?v=1&tid=UA-49240218-2&cid=" +
			mac + "&t=pageview&dp=" + java.net.URLEncoder.encode(dp, "UTF-8");	
		response.sendRedirect(ga_url);
		return;	
	}
	else if (event.equals("IN_IMS_CHANNEL")) {
		//http://www.google-analytics.com/collect?v=1&tid=UA-49240218-2&cid=${mac}
		//&t=pageview&dp=%2Fims%2F${video}
		String dp = "/ims/" + video;		
		//System.out.println("#2#" + event + ":" + dp);
		String ga_url = "http://www.google-analytics.com/collect?v=1&tid=UA-49240218-2&cid=" +
			mac + "&t=pageview&dp=" + java.net.URLEncoder.encode(dp, "UTF-8");	
		response.sendRedirect(ga_url);
		return;	
	}
	else { 
		//http://www.google-analytics.com/collect?v=1&tid=UA-49240218-2&cid=${mac}
		//&t=pageview&dp=%2Fvod%2F${app}%2F${channel}%2F${video}${no}
		String vname = get_video_name(app, channel, video, no);
		String dp = "/vod/" + vname;
		//System.out.println("#3#" + event + ":" + dp);
		String ga_url = "http://www.google-analytics.com/collect?v=1&tid=UA-49240218-2&cid=" +
			mac + "&t=pageview&dp=" + java.net.URLEncoder.encode(dp, "UTF-8");	
		response.sendRedirect(ga_url);
		return;
	}
	
	// 确认下 BSWEE 有使用 vod
	//HttpParams hp = new HttpParams(request);
	//String vid = hp.getStr("vid",null);
	//if (vid==null)
		//return; 
	//if (vid.indexOf("bb-44-66")>=0)
		//System.out.println("vid=bb-44-66");
%>