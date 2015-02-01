package util;

import javax.servlet.ServletRequest;
import javax.servlet.http.*;

public class CookieUtil
{
	private HttpServletRequest req = null;
	private HttpServletResponse resp = null;
	public CookieUtil(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp; 
	}
	
	public int getCookieInt(String attr, int defvalue)
	throws Exception
	{
		String v = getCookie(attr, null);
		try {
			return Integer.parseInt(v);
		}
		catch (Exception e) {}
		return defvalue;
	}	
	
	public String getCookie(String attr, String defvalue)
		throws Exception
	{
		javax.servlet.http.Cookie[] cks = req.getCookies(); 
	    String ret = defvalue;
	    attr = get_norm_attr(attr);
	    for (int i=0; cks!=null && i<cks.length; i++)
	    {
	        if (cks[i].getName().equals(attr)) {
	        	ret = java.net.URLDecoder.decode(cks[i].getValue(), "UTF-8");
	        }
	    }
	    return ret;
	}
	
	public String getCookie(String attr)
		throws Exception
	{
		return getCookie(attr, null);
	}
	
	private String get_norm_attr(String attr) {
		String webappPath = req.getContextPath();
		if (webappPath.length()==0)
			return "/" + attr;
		return webappPath + "/" + attr;
	}
	
	public void setCookie(String attr, String value)
		throws Exception
	{
		attr = get_norm_attr(attr);
		javax.servlet.http.Cookie c = new javax.servlet.http.Cookie(attr, 
				java.net.URLEncoder.encode(value, "UTF-8"));
		c.setPath("/");
		resp.addCookie(c);
	}
	
	public void setCookie(String attr, String value, int days)
		throws Exception
	{
		attr = get_norm_attr(attr);
		javax.servlet.http.Cookie c = new javax.servlet.http.Cookie(attr, 
				java.net.URLEncoder.encode(value, "UTF-8"));
		c.setPath("/");
		if (days>0)
	    	c.setMaxAge(days*86400);
		resp.addCookie(c);
	}
}
