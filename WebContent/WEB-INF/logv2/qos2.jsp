<%@ page language="java" session="false" import="com.wct.util.*,com.wct.logs.*,com.wct.report.*,java.text.*,java.util.*,util.*"
	pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%><%!
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
	
	public String getRemoteIP(HttpServletRequest request) {  
		if (request.getHeader("x-forwarded-for") == null) {  
			return request.getRemoteAddr();  
		}  
		String[] ips = request.getHeader("x-forwarded-for").split(",");
		for (int i=0; i<ips.length; i++) {
			String ip = ips[i];
			if (ip.indexOf("10.")>=0 && ip.indexOf("192.")>=0)
				continue;
			return ip;
		}
		return "1.1.1.1";
	}
	
	static class QosThread implements Runnable {
    	
    	List<Qos> q;
    	
    	public QosThread(List<Qos>queue) {
    		q = queue;
    	}

		List<Qos> qlist = new ArrayList<Qos>(10000);
    	public void run() {
    		while (true) {
	    		try {
	    			qlist.clear();
	    			synchronized(q) {
	    				for (int i=0; q.size()>0 && i<10000; i++) {
	    					qlist.add((Qos) q.remove(0));
	    				}
    					if (q.size()>10000)
    						System.out.println(q.size());
	    			}
	    			if (qlist.size()==0) {
    					Thread.sleep(500);
    				}
    				else {
    					long t1 = new Date().getTime();
    					StringBuilder sb = new StringBuilder();
    					sb.append("INSERT INTO log_watch3 (`mac`,`ip`,");
    					sb.append("`secs`,`app`,`video`,`channel`,`ctime`) VALUES ");
    					for (int i=0; i<qlist.size(); i++) {
    						Qos qos = qlist.get(i);
    						if (i>0)
    							sb.append(',');
    						sb.append('(');
    						sb.append('\'').append(qos.mac).append('\'').append(',');
    						sb.append('\'').append(qos.ip).append('\'').append(',');
    						sb.append(qos.secs).append(',');
    						sb.append('\'').append(qos.app).append('\'').append(',');
    						String v = dbo.ServerTool.escapeString(qos.video);
    						if (v.length()>40)
    							v = v.substring(0,39);
    						sb.append('\'').append(v).append('\'').append(',');
    						sb.append('\'').append(dbo.ServerTool.escapeString(qos.channel)).append('\'').append(',');
    						sb.append('\'').append(sdf.format(qos.ctime)).append('\'');
    						sb.append(')');
    					}
    					long t2 = new Date().getTime();
    					//new LogWatchMgr(0).executeSQL(sb.toString());
    					long t3 = new Date().getTime();
    					if ((t3-t1)>1000) {
    						System.out.println("t3-t2=" + (t3-t2) + "t2-t1=" + (t2-t1) + "t3-t1=" + (t3-t1));
    					}
    				}
	    		}
	    		catch (Exception e) {
	    			e.printStackTrace();
	    		}
    		}
    	}
    }
    
    static class Qos {
    	String app, video, channel, ip, mac;
    	Date ctime;
    	int secs;
    	Qos(String app, String video, String channel, String mac, String ip, int secs, Date ctime) {
    		this.app = app;
    		this.video = video;
    		this.channel = channel;
    		this.secs = secs; 
    		this.mac = mac;
    		this.ip = ip;
    		this.ctime = ctime;
    	}
    }
    
    static boolean init = false;
    static List<Qos> queue = new ArrayList<Qos>(); 
    static Map<String, String> vooleMap = new HashMap<String, String>(100000);
    static DeviceInfoMgr dimgr = null;
%><%
	int startType = 0; 
	try { Integer.parseInt(request.getParameter("startType"));} catch (Exception e) {} 
    int startStatus = 0;
    try { Integer.parseInt(request.getParameter("startStatus"));} catch (Exception e) {} 
    int playtime = 0;
    try { playtime=Integer.parseInt(request.getParameter("playTime"));} catch (Exception e) {} 
    if (startStatus!=0 || playtime<=0 || playtime>20000) {
    	//错误的不跟踪
    	return;
    }
	
	long t1 = new Date().getTime();    
	String url = request.getParameter("url");
    String mac = request.getParameter("mac");
    String event = request.getParameter("event");
    String ip = getRemoteIP(request);
	 	
    String appstr = request.getParameter("app");
    String channel = request.getParameter("channel");
    String video = request.getParameter("video");
    String no = request.getParameter("no"); 
    String video_name = get_video_name(appstr, channel, video, no);
    
    if (dimgr==null) {
    	dimgr = new DeviceInfoMgr(0);
    	dimgr.setSource("db170");
    }
    
    //#############
    // spawn 10 threads to write Qos data
    synchronized (queue) {
	    if (!init) {
    		new Thread(new QosThread(queue)).start();
	    	init = true;
	    }
	}
	
	if (url!=null && url.indexOf("puteasy")>=0) {
		appstr = "ocean";
	}
   	queue.add(new Qos(appstr, video+no, channel, mac, ip, playtime, new Date()));
    //#############
    
    if (appstr.equals("vlmms")) {
    	if (playtime<30) {
	    	//错误的不跟踪, 看不到30秒的不跟踪
	    	return;
	    }
		//goto google analytics log newvoole
		StringBuilder path = new StringBuilder();
    	path.append("/newvoole/vlmms/");
    	path.append(video_name);
    	String ga_url = "http://www.google-analytics.com/collect?v=1&tid=UA-49240218-2&cid=" + mac +
    		   "&t=pageview&dp=" + java.net.URLEncoder.encode(path.toString(), "UTF-8");
    	response.sendRedirect(ga_url);
    	return;
	}
    if (appstr.equals("voole")) {
    	if (playtime<30) {
	    	//错误的不跟踪, 看不到30秒的不跟踪
	    	return;
	    }
    	//check version see if above 5.70	
    	// if yes goto google analytics log newvoole
    	String ver = vooleMap.get(mac);
    	if (ver==null) {
    		try {
    			long ta = new Date().getTime();
    			DeviceInfo dis = dimgr.retrieveList(
    				"device_mac='" + mac + "'","").get(0);
    			ver = dis.getSoftware_code();
    			vooleMap.put(mac, ver);
    			long tb = new Date().getTime();
    			System.out.println("vmapsize=" + vooleMap.size() + "(" + (tb-ta) + ") ms");
    		}
    		catch (Exception e) {
				System.out.println("### error in getting device " + e.getMessage()); 			
    		}
    	}
    	if (ver.compareTo("05.70")>0) {
    		//goto google analytics log newvoole
    		StringBuilder path = new StringBuilder();
    		path.append("/newvoole/vod/");
    		path.append(video_name);
    		String ga_url = "http://www.google-analytics.com/collect?v=1&tid=UA-49240218-2&cid=" + mac +
    		    	"&t=pageview&dp=" + java.net.URLEncoder.encode(path.toString(), "UTF-8");
    		response.sendRedirect(ga_url);
    		return;
    	}
    }
    
    if (event.equals("IMS_PLAY_QOS")) {
    	int a = 1; // 2014-1-10 added by peter, 不跟踪了减少GA数
    	if (a>0)
    		return;

    	if (playtime<180) {
	    	//错误的不跟踪, 看不到180秒的不跟踪
	    	return;
	    }
    
	    StringBuilder path = new StringBuilder();
	    if (url.indexOf("puteasy")>=0) {
	    	path.append("/ims/ocean/");
	    }
	    else {
	    	path.append("/ims/ali/");
	    }
	    path.append(video_name);
	    
	    String ga_url = null;
	    if (startStatus==0) { // success
		    ga_url = "http://www.google-analytics.com/collect?v=1&tid=UA-49240218-2&cid=" + mac +
		    	"&t=pageview&dp=" + java.net.URLEncoder.encode(path.toString(), "UTF-8");
		    response.sendRedirect(ga_url);
	    }
	}
	else if (event.equals("VOD_PLAY_QOS")) {
	    if (startStatus==0) { // success
	    	if (video_name.indexOf("smit")>=0) {		    	
			    LogSmit s = new LogSmit();
			    s.setCtime(new Date());
			    s.setMac(mac);
			    s.setIp(ip);
			    s.setSecs(playtime);
			    s.setVideo(video_name);
			    new LogSmitMgr(0).create(s);
			}
	    }
	}
%> 