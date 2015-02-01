package com.ever.smit.util;

import java.text.SimpleDateFormat;

/**
 * 
 * @author lisp
 * 
 */
public class Constants {

    public static String DEFAULT_KEY = "ac1bd8a43da15ea02898133b8d901965";
    public static SimpleDateFormat sf = new SimpleDateFormat(
	    "yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat sfs = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat sfm = new SimpleDateFormat("HH:mm");
    public static String path = "http://223.203.193.157:8888/";
    public static String hdpfanspath = "http://v.guozitv.com/img.php?src=";
    public static String Localitypath = "http://223.203.193.157:8888/";
    public static final int APP_WEB_ICON_WIDTH = 193;
    public static final int APP_WEB_ICON_HEIHGT = 283;
    public static final String SUCCESS = "1";
    public static final String FALL = "0";
    public static String TARGETPATH = "http://221.228.203.230:28080";
    public static String BINDADDR = "/sso/devices/bind.action";
    public static String REGISTERADDR = "/sso/user/register.action";
    public static String SMS = "/sso/user/sms.action";
    public static String DETAIL = "/sso/user/detail.action";
    public static String UPDATE = "/sso/user/update.action";
    public static String VIP = "/sso/buy/vip.action";
    public static String PLAY = "/sso/content/play.action";
    public static String ADD = "/sso/pay/add.action";
    public static String CATHOME = "/sso/index/catHome.action";
    public static String CHNNELCATEGORY = "/sso/content/channelCategory.action";
    public static String EVENTMATCH = "/sso/content/eventMatch.action";
    public static String CONTENTLIST = "/sso/channel/contentList.action";
    public static String CONTENTDETAIL = "/sso/content/detail.action";
    public static String EPISODEITEMLIST = "/sso/content/episodeItemList.action";
    public static String VIPLIST = "/sso/vip/list.action";
    public static String EVENTROUNDLIST="/sso/content/eventRoundList.action";

    public static String DRMTYPE = "loco";
    public static String SERIAL = "serial";
    static int cacheInterval = 0;
     public static String DEVICEINFO="777";
     public static void setDEVICEINFO(String dEVICEINFO) {
     DEVICEINFO = dEVICEINFO;
     }

    public static String PUBLISHID = "widecloud";
    public static String IPADDR = "59.108.115.251";
    public static String PROMOTER = "widecloud";
    public static String CLIENT = "stb";
    public static String OSVERSION = "666";
    public static String EXTUSER = "555";
    public static String smitPlayUrl="";
    public static String EPL="epl";
    
    
    //错误代码列表
    
    

	public void setEXTUSER(String eXTUSER) {
	EXTUSER = eXTUSER;
    }

    public void setOSVERSION(String oSVERSION) {
	OSVERSION = oSVERSION;
    }

    public void setCLIENT(String client) {
	CLIENT = client;
    }

    public void setPUBLISHID(String pUBLISHID) {
	PUBLISHID = pUBLISHID;
    }

    public void setDRMTYPE(String dRMTYPE) {
	DRMTYPE = dRMTYPE;
    }

    public int getCacheInterval() {
	return cacheInterval;
    }

    public void setCacheInterval(int cacheInterval) {
	Constants.cacheInterval = cacheInterval;
    }

    public static String getPROMOTER() {
	return PROMOTER;
    }

    public void setPROMOTER(String promoter) {
	PROMOTER = promoter;
    }


	public  void setSmitPlayUrl(String smit_Play_Url) {
		smitPlayUrl = smit_Play_Url;
	}

}
