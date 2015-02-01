package util;

import javax.servlet.*;
import javax.servlet.http.*;

import java.util.*;
import java.net.*;

public class SocialUtil
{
	private HttpServletRequest req = null;
	private HttpServletResponse resp = null;
	public SocialUtil(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp; 
	}
	public String getlink(String type, String title, String desc, String link, String pic)
		throws Exception
	{
		
		StringBuffer sb = new StringBuffer();
		if (type.equals("qq")) {
			sb.append("http://sns.qzone.qq.com/cgi-bin/qzshare/cgi_qzshare_onekey?url="); 
			sb.append(URLEncoder.encode(link, "UTF-8").replace("+","%20"));
			sb.append("&showcount=0&desc=").append(URLEncoder.encode(desc,"UTF-8").replace("+","%20")); 
			sb.append("&summary=%20&title=").append(URLEncoder.encode(title,"UTF-8").replace("+","%20")); 
			sb.append("&site=&pics=");
			sb.append(URLEncoder.encode(pic,"UTF-8")).append("&style=201&otype=share");
		}
		else if (type.equals("renren")) {
			sb.append("http://widget.renren.com/dialog/share?url="); 
			sb.append(URLEncoder.encode(link, "UTF-8").replace("+","%20"));
			sb.append("&title=").append(URLEncoder.encode(title,"UTF-8").replace("+","%20")); 
			sb.append("&description=").append(URLEncoder.encode(desc,"UTF-8").replace("+","%20"));
			sb.append("&pic=" + URLEncoder.encode(pic,"UTF-8"));
			sb.append("&message="); //+ URLEncoder.encode(msg,"UTF-8").replace("+","%20");
		}
		else if (type.equals("weibo")) {
			sb.append("http://service.weibo.com/share/share.php?url="); 
			sb.append(URLEncoder.encode(link, "UTF-8").replace("+","%20"));
			sb.append("&title=").append(URLEncoder.encode(title,"UTF-8").replace("+","%20")); 
			sb.append("&pic=" + URLEncoder.encode(pic,"UTF-8"));
		}
		
		return sb.toString();
	}
}
