<%@ page language="java" import="com.wct.util.*,com.wct.logs.*,java.text.*,java.util.*,util.*"
	pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%><%!
	static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
%><%!
	private static CacheMap<String, LogUrl> url_cache = new CacheMap<String, LogUrl>(10000);
	private static CacheMap<String, LogIp> ip_cache = new CacheMap<String, LogIp>(80000);
	private static CacheMap<String, LogMac> mac_cache = new CacheMap<String, LogMac>(80000);
	private static CacheMap<String, LogVideo> video_cache = new CacheMap<String, LogVideo>(10000);
	
	static String get_video_name(String app, String channel, String video, String no)
	{
		StringBuffer sb = new StringBuffer();
    	if (app!=null && app.length()>0) {
    		if (sb.length()>0 && sb.charAt(sb.length()-1)!='/')
    			sb.append('/');
    		sb.append(app);
    	}
    	if (channel!=null && channel.length()>0) {
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
	String tmp1 = request.getParameter("channel");
	HttpParams hp = new HttpParams(request);
	String url = hp.getStr("url",null);
	if (url==null || url.indexOf("puteasy")<0)
		return;
		 
	LogUrlMgr umgr = new LogUrlMgr(0);
	LogIpMgr ipmgr = new LogIpMgr(0);
	LogMacMgr macmgr = new LogMacMgr(0); 
    LogQosMgr qmgr = new LogQosMgr(0);
    LogVideoMgr vmgr = new LogVideoMgr(0);
            	
    String ip = request.getRemoteAddr();
	LogIp log_ip = ip_cache.get(ip);
	 	
	if (log_ip==null) {
    	try {
     	log_ip = ipmgr.retrieveList("ip='" + ip + "'", 
     				"order by id desc").get(0); 
    	} catch (java.lang.IndexOutOfBoundsException e) {
    		log_ip = new LogIp();
     		log_ip.setIp(ip);
     		ipmgr.create(log_ip);
    	}
    	ip_cache.put(ip, log_ip);
    }
    String mac = hp.getStr("mac", "xxxx");
    LogMac log_mac = mac_cache.get(mac);
    if (log_mac==null) {
    	try {
    		log_mac = macmgr.retrieveList("mac='" + mac + "'", 
  					"order by id desc").get(0);
    	} catch (java.lang.IndexOutOfBoundsException e) {
    		log_mac = new LogMac();
     		log_mac.setMac(mac);
     		macmgr.create(log_mac);
    	}
    	mac_cache.put(mac, log_mac);
    }	
		
	LogUrl log_url = url_cache.get(url);
    if (log_url==null) {
    	try {
    		log_url = umgr.retrieveList("url='" + 
    			dbo.ServerTool.escapeString(url) + "'", 
				"order by id desc").get(0);
    	} catch (java.lang.IndexOutOfBoundsException e) {
    		log_url = new LogUrl();
    		log_url.setUrl(url);
    		log_url.setMd5(util.Md5Util.Md5(url));
     		umgr.create(log_url);
    	}
    	url_cache.put(url, log_url);
    }
    
    String appstr = hp.getStr("app","");
    String channel = hp.getStr("channel","");
    String video = hp.getStr("video","");
    String no = hp.getStr("no","");
    String video_name = get_video_name(appstr, channel, video, no);
	LogVideo log_video = video_cache.get(video_name);
	if (log_video==null) {
		try {
			log_video = vmgr.retrieveList("name='" + 
				dbo.ServerTool.escapeString(video_name) + "'", "order by id desc").get(0);
		} catch (java.lang.IndexOutOfBoundsException e) {
			log_video = new LogVideo();
	 		log_video.setName(video_name);
	 		vmgr.create(log_video);
		}
		video_cache.put(video_name, log_video);
	}
    
	LogQos q = new LogQos();
    q.setCtime(new Date());
    q.setIpId(log_ip.getId());
    q.setMacId(log_mac.getId());
    q.setEvent(ProcessLogs.EVENT_VOD_PLAY_QOS);
    q.setUrlId(log_url.getId());
    q.setVideoId(log_video.getId());
    
    int startType = hp.getInt("startType",0);
    int startStatus = hp.getInt("startStatus",0);
    int startTime = hp.getInt("startTime",0);
    int bufferNum = hp.getInt("bufferNum", 0);
    int bufferTime = hp.getInt("bufferTime", 0);
    int playtime = hp.getInt("playTime",0);
    try { q.setOrgUrlId(hp.getInt("urlId",0)); } catch (Exception e) {}
    try { q.setTotalTime(hp.getInt("totalTime",0)); } catch (Exception e) {}
    try { q.setStartTime(startTime); } catch (Exception e) {}
    try { q.setPlayTime(playtime); } catch (Exception e) {}
    try { q.setStartType(startType); } catch (Exception e) {}
    try { q.setStartStatus(startStatus); } catch (Exception e) {}
    try { q.setBufferNum(bufferNum); } catch (Exception e) {}
    try { q.setBufferTime(bufferTime); } catch (Exception e) {}
    qmgr.create(q);
    
    String ga_url = null;
    if (startStatus==0) { // success
	    ga_url = "http://www.google-analytics.com/collect?v=1&tid=UA-49240218-3&cid=" + mac +
	    	"&t=event&ec=play_ok" + 
	    	"&ea=" + video_name;
	    	System.out.println("#ok " + video_name + " playtime=" + playtime + " startTime=" + startTime);
    }
    else { // 播不出来
    	ga_url = "http://www.google-analytics.com/collect?v=1&tid=UA-49240218-3&cid=" + mac +
    	"&t=event&ec=play_fail" +
    	"&ea=" + video_name;
    	System.out.println("#fail " + video_name);
    }
    response.sendRedirect(ga_url);
%>