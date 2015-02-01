package com.wct.logs;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

import dbo.DataSource;
import util.*;
import java.util.*;

public class ProcessLogs  {
	
	private static String queue_url = null;
	private final static String queue_name = "wct_log";
	private static CacheMap<String, LogIp> ip_cache = new CacheMap<String, LogIp>(50000);
	private static CacheMap<String, LogMac> mac_cache = new CacheMap<String, LogMac>(50000);
	private static CacheMap<String, LogVideo> video_cache = new CacheMap<String, LogVideo>(50000);
	private static CacheMap<String, LogUrl> url_cache = new CacheMap<String, LogUrl>(50000);
	
	public final static int EVENT_IN_IMS_CHANNEL = 3;
	public final static int EVENT_IN_LUNBO = 4;
	public final static int EVENT_IN_VOD = 5;
	public final static int EVENT_IMS_PLAY_QOS = 15;
	public final static int EVENT_VOD_PLAY_QOS = 16;
	
	
	public static void thread(Runnable runnable, boolean daemon) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.setDaemon(daemon);
        brokerThread.start();
    }
	
	public static void main(String[] args) throws Exception {
		if (args.length!=1) {
    		System.out.println("Usage: java ProcessLogs <queue_url>");
    		System.exit(0);
    	}
		File dsource = new File("datasource");
    	DataSource.setup(dsource.getAbsolutePath());
    	queue_url = args[0];
    	DataSource.reset(false);

    	Thread.sleep(500);
		thread(new LogConsumer(), false);
	}
	
	public static Map<String, String> parseParams(String urlEncodedParams)
		throws Exception
	{
		String[] pairs = urlEncodedParams.split("&");
		Map<String, String> ret = new HashMap<String, String>();
		for (int i=0; i<pairs.length; i++) {
			String pair = pairs[i];
			String[] tokens = pair.split("=");
			try {
//				System.out.println(tokens[0] + "=" + tokens[1]);
				ret.put(tokens[0], java.net.URLDecoder.decode(tokens[1], "UTF-8"));
			}
			catch (Exception e) {}
		}
		return ret;
	}
	
	public static int getEventId(String event)
	{
		try {
			if (event.equals("DEVICE_LOGIN"))
				return 1; // EVENT_DEVICE_LOGIN;
			else if (event.equals("RC_PRESSED"))
				return 2; // EVENT_RC_PRESSED;
			else if (event.equals("IN_IMS_CHANNEL"))
				return 3; // EVENT_IN_IMS_CHANNEL;
			else if (event.equals("IN_LUNBO"))
				return 4; // EVENT_IN_LUNBO;
			else if (event.equals("IN_VOD"))
				return 5; // EVENT_IN_VOD;
			else if (event.equals("VOD_INFO_ENTERED"))
				return 6; // EVENT_VOD_INFO_ENTERED;
			else if (event.equals("VOD_PLAYED"))
				return 7; // EVENT_VOD_PLAYED;
			else if (event.equals("VOD_MENU2_ENTERED"))
				return 8; // EVENT_VOD_MENU2_ENTERED;
			else if (event.equals("VOD_POSTER_ENTERED"))
				return 9; // EVENT_VOD_POSTER_ENTERED;
			else if (event.equals("CHANNEL_ENTERED"))
				return 10; // EVENT_CHANNEL_ENTERED;
			else if (event.equals("AD_DISPLAYED"))
				return 11; // EVENT_AD_DISPLAYED;
			else if (event.equals("VAST_DISPLAYED"))
				return 12; // EVENT_VAST_DISPLAYED;
			else if (event.equals("VAST_INPUT_DISPLAYED"))
				return 13; // EVENT_VAST_INPUT_DISPLAYED;
			else if (event.equals("VAST_SUBMIT"))
				return 14; // EVENT_VAST_SUBMIT;
			else if (event.equals("IMS_PLAY_QOS"))
				return 15; // EVENT_IMS_PLAY_QOS;
			else if (event.equals("VOD_PLAY_QOS"))
				return 16; // EVENT_VOD_PLAY_QOS;
			else if (event.equals("VOD_ACCOUNT_ENTERED"))
				return 17; // EVENT_VOD_ACCOUNT_ENTERED;
			else if (event.equals("VOD_REGISTER_SUBMIT"))
				return 18; // EVENT_VOD_REGISTER_SUBMIT;
			else if (event.equals("VOD_VALIDATECODE_SUBMIT"))
				return 19; // EVENT_VOD_VALIDATECODE_SUBMIT;
			else if (event.equals("VOD_ORDER"))
				return 20; // EVENT_VOD_ORDER;
			else if (event.equals("VOD_VOUCHER"))
				return 21; // EVENT_VOD_VOUCHER;
			else if (event.equals("VOD_VOUCHER_SUBMIT"))
				return 22; // EVENT_VOD_VOUCHER_SUBMIT;
		}
		catch (Exception e) {}
		return 0;
	}
	
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
	
	public static class LogConsumer implements Runnable, ExceptionListener {
    	
        public void run() {
            try {
 
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(queue_url);
                Connection connection = connectionFactory.createConnection();
                connection.start();
                connection.setExceptionListener(this);
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                Destination destination = session.createQueue(queue_name);
                MessageConsumer consumer = session.createConsumer(destination);
                LogIpMgr ipmgr = new LogIpMgr(0);
                LogMacMgr macmgr = new LogMacMgr(0); 
                LogVideoMgr vmgr = new LogVideoMgr(0);
                LogRecordMgr rmgr = new LogRecordMgr(0);
                LogUrlMgr umgr = new LogUrlMgr(0);
                LogQosMgr qmgr = new LogQosMgr(0);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 
                // Wait for a message
                while (true) {
	                Message message = consumer.receive(1000);
	                if (message==null) {
	                	Thread.sleep(10000);	                	
	                	continue;
	                }
	                if (message instanceof TextMessage) {
	                    TextMessage textMessage = (TextMessage) message;
	                    String text = textMessage.getText();
	                    String[] strs = text.split("\t");
	                    int app = 0;
	                    try {
		                    String timestr = strs[0];
		                    String ip = strs[1];
		                    Map<String, String> m = parseParams(strs[2]);
		                    String event = m.get("event");
		                    String mac = m.get("mac");
		                    String chipType = m.get("chipType");
	//	                    System.out.println(text);
	//	                    System.out.println("time=" + timestr + "  ip=" + ip + 
	//	                    		" event=" + event + " mac=" + mac + " chipType=" + chipType);
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
		                    
		                    String appstr = m.get("app");
		                    String channel = m.get("channel");
		                    String video = m.get("video");
		                    String no = m.get("no");
		                    int eventId = getEventId(event);
		                    if (eventId==EVENT_IN_IMS_CHANNEL)
		                    	appstr = "/ims";
		                    else if (eventId==EVENT_IN_LUNBO)
		                    	appstr = "/lunbo";
		                    String video_name = get_video_name(appstr, channel, video, no);
		                    LogVideo log_video = video_cache.get(video_name);
		                    if (log_video==null) {
		                    	try {
		                    		log_video = vmgr.retrieveList("name='" + 
		                    			dbo.ServerTool.escapeString(video_name) + "'", 
                						"order by id desc").get(0);
		                    	} catch (java.lang.IndexOutOfBoundsException e) {
		                    		log_video = new LogVideo();
			                    	log_video.setName(video_name);
			                    	vmgr.create(log_video);
		                    	}
		                    	video_cache.put(video_name, log_video);
		                    }
		                    
		                    if (eventId==EVENT_IMS_PLAY_QOS || eventId==EVENT_VOD_PLAY_QOS) {
		                    	String url = m.get("url");
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
		                    	
		                    	LogQos q = new LogQos();
			                    q.setCtime(sdf.parse(timestr));
			                    q.setIpId(log_ip.getId());
			                    q.setMacId(log_mac.getId());
			                    q.setEvent(eventId);
			                    q.setUrlId(log_url.getId());
			                    q.setVideoId(log_video.getId());
			                    try { q.setOrgUrlId(Integer.parseInt(m.get("urlId"))); } catch (Exception e) {}
			                    try { q.setTotalTime(Integer.parseInt(m.get("totalTime"))); } catch (Exception e) {}
			                    try { q.setStartTime(Integer.parseInt(m.get("startTime"))); } catch (Exception e) {}
			                    try { q.setPlayTime(Integer.parseInt(m.get("playTime"))); } catch (Exception e) {}
			                    try { q.setStartType(Integer.parseInt(m.get("startType"))); } catch (Exception e) {}
			                    try { q.setStartStatus(Integer.parseInt(m.get("startStatus"))); } catch (Exception e) {}
			                    qmgr.create(q);
		                    }
		                    else {
			                    LogRecord r = new LogRecord();
			                    r.setCtime(sdf.parse(timestr));
			                    r.setIpId(log_ip.getId());
			                    r.setMacId(log_mac.getId());
			                    r.setEvent(eventId);
			                    r.setVideoId(log_video.getId());
			                    rmgr.create(r);
		                    }
	                    }
	                    catch (Exception e) {
	                    	e.printStackTrace();
	                    }
	                } else {
	                    System.out.println("Received: " + message);
	                }
                }
//                consumer.close();
//                session.close();
//                connection.close();
            } catch (Exception e) {
                System.out.println("Caught: " + e);
                e.printStackTrace();
            }
        }
 
        public synchronized void onException(JMSException ex) {
            System.out.println("JMS Exception occured.  Shutting down client.");
        }
    }
	//select a.id,event,mac,ip,d.name from log_record a,log_ip b,log_mac c,log_video d where a.ipId=b.id and a.macId=c.id and a.videoId=d.id order by a.id desc limit 10;
}
