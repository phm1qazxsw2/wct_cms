package util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.regex.Matcher;  
import java.util.regex.Pattern;

import org.jsoup.*;
import org.jsoup.nodes.*;

import java.net.*;
import java.io.*;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.*;

public class HttpUtil
{
	private static String global_webapp_basePath = null;
	public static int getInt(ServletRequest r, String attr, int not_found_value)
	{
		try {
			return Integer.parseInt(r.getParameter(attr).trim());
		}
		catch (Exception e) {}
		return not_found_value;
	}
	
	public static long getLong(ServletRequest r, String attr, long not_found_value)
	{
		try {
			return Long.parseLong(r.getParameter(attr).trim());
		}
		catch (Exception e) {}
		return not_found_value;
	}
	
	public static String getBasePath(HttpServletRequest request) {
		if (request==null) {
			if (global_webapp_basePath!=null)
				return global_webapp_basePath;
			throw new RuntimeException("request cannot be null if global_webapp_basePath has not been calculated");
		}
		StringBuffer sb = new StringBuffer();
		sb.append(request.getScheme()).append("://").append(request.getServerName());
		if (request.getScheme().equals("http") && request.getServerPort()!=80) {
			sb.append(":").append(request.getServerPort());
		}
		if (request.getScheme().equals("https") && request.getServerPort()!=443) {
			sb.append(":").append(request.getServerPort());
		}
		sb.append(request.getContextPath()).append('/');
		global_webapp_basePath = sb.toString();
		return global_webapp_basePath;
	}
	 
	public static int getIntMust(ServletRequest r, String attr)
		throws ParameterNotFound
	{
		int ret = getInt(r, attr, -1);
		if (ret==-1)
			throw new ParameterNotFound(attr + " not found!");
		return ret;
	}
	
	public static float getFloat(ServletRequest r, String attr, float not_found_value)
	{
		try {
			return Float.parseFloat(r.getParameter(attr));
		}
		catch (Exception e) {}
		return not_found_value;
	}
	
	public static String getCookie(HttpServletRequest req, String name) throws Exception 
	{
	    Cookie[] cks = req.getCookies();
	    String ret = null;
	    for (int i=0; cks!=null && i<cks.length; i++)
	    {
	        if (cks[i].getName().equals(name))
	        {
	            ret = cks[i].getValue();
	        }
	    } 	
	    return ret;
	}

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
	public static Date getDate(ServletRequest r, String attr, Date not_found_value)
	{
		try {
			String dstr = r.getParameter(attr);	
			return sdf.parse(r.getParameter(attr));
		}
		catch (Exception e) {
		}
		return not_found_value;
	}

	
	public static float getFloatMust(ServletRequest r, String attr)
		throws ParameterNotFound
	{
		float ret = getFloat(r, attr, (float)-9999999);
		if (ret==(float)-9999999)
			throw new ParameterNotFound(attr + " not found!");
		return ret;
	}
	
	public static String getStr(ServletRequest r, String attr, String not_found_value)
	{
		String v = r.getParameter(attr);
		if (v==null)
			return not_found_value;
		return v;
	}
	
	public static String getStr(ServletRequest r, String attr)
	{
		return getStr(r, attr, null);
	}
	
	public static String getStrMust(ServletRequest r, String attr)
		throws ParameterNotFound
	{
		String v = getStr(r, attr, null);
		if (v==null)
			throw new ParameterNotFound(attr + " not found!");
		return v;
	}

    public static String getIntIds(HttpServletRequest r, String attr)
    {
        String values[] = r.getParameterValues(attr);
        if (values==null)
            return "";
        String ret = "";
        for (int i=0; i<values.length; i++) {
            if (i>0)
                ret += ",";
            ret += values[i];
        }
        return ret;
    } 
    
    static class NotifyThread extends Thread 
    {
    	String urlstr = null;
    	NotifyThread(String url) {
    		this.urlstr = url;
    	}
    	public void run() {
    		try {
System.out.println("## HttpUtil sendNotify urlstr=" + this.urlstr);   			
    			util.URLConnector.getContent(this.urlstr, 10000, "UTF-8");
    		}
    		catch (Exception e) {
    			e.printStackTrace();
    		}
		}
    }

    public static void sendNotify(String msg) {
	    try {
	        String notify_str = sdf.format(new java.util.Date())+"\n"+msg;
	        String urlstr = "http://218.32.77.180:8090/a/notify-oxbc.jsp?url=&msg=" + 
	        	java.net.URLEncoder.encode(notify_str, "UTF-8");
	        new NotifyThread(urlstr).start();
	    }
	    catch (Exception ee) 
	    {}	
    }

    public static void sendSkypeNotify(String msg, String path) {
	    try {
	        String notify_str = path + " --- " + msg;
	        String urlstr = "http://218.32.77.180:8090/a/notify-yikuair.jsp?url=&msg=" + 
	        	java.net.URLEncoder.encode(notify_str, "UTF-8");	        
	        new NotifyThread(urlstr).start();
	    }
	    catch (Exception ee) 
	    {
	    	ee.printStackTrace();
	    }	
    }
    
    public static void sendErrorNotify(Exception e, String path) {
	    try {
	        ByteArrayOutputStream bout = new ByteArrayOutputStream();
	        PrintWriter pr = new PrintWriter(new OutputStreamWriter(bout));
	        e.printStackTrace(pr);
	        pr.flush();
	        bout.flush();
	        String str = new String(bout.toByteArray());
	        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        String notify_str = "["+path+" exception] " + sdf.format(new java.util.Date())+"\n"+str;
	        String urlstr = "http://218.32.77.180:8090/a/notify-oxbc.jsp?url=&msg=" + 
	        	java.net.URLEncoder.encode(notify_str, "UTF-8");
	        bout.close();
	        pr.close();
	        new NotifyThread(urlstr).start();
	    }
	    catch (Exception ee) 
	    {
	    }	
    }
    
    public static String addTrailingSlash(String url) {
    	if (url==null || url.length()==0)
    		return "";
    	if (url.charAt(url.length()-1)!='/')
    		return url + "/";
    	return url;
    }
    
    static boolean isIP(String host) {
    	for (int i=0; i<host.length(); i++) {
    		char c = host.charAt(i);
    		if ((c<='9' && c>='0') || (c=='.') || (c==':'))
    			continue;
    		else
    			return false;
    	}
    	return true;
    }
    
    public static String getDomain(String url) {
    	if (url==null || url.length()==0)
    		return "";
    	int c1 = url.indexOf("http://");
    	if (c1>=0) {
    		c1 = c1 + 7;
    	}
    	else if ((c1 = url.indexOf("https://"))>=0) {
    		c1 += c1 += 8;
    	}
    	else 
    		c1 = 0;
    	
    	int c2 = url.indexOf("/", c1);
    	if (c2<0)
    		c2 = url.length();
    	String host = url.substring(c1, c2);
    	// if 数字，ruturn 数字，if domain, 回后两个点

        if (isIP(host)) {
            return host;  
        } 
        
    	String[] chunks = host.split("\\.");
    	if (chunks.length<3)
    		return host;
          
    	StringBuffer ret = new StringBuffer();
    	for (int i=(chunks.length-2); i<chunks.length; i++) {
    		if (ret.length()>0)
    			ret.append('.');
    		ret.append(chunks[i]);
    	}
    	return ret.toString();
    }
    
    public static Document getJsoupDoc(String url)
    	throws Exception
	{
	    Document doc = null;
		for (int tries=0; tries<3; tries++) {
			try {
				doc = Jsoup.connect(url).timeout(10000).get();
				break;
			}
			catch (Exception e) {
				e.printStackTrace();
				Thread.sleep(2000);	
			}
		}
		if (doc==null)
			throw new Exception("doc exceed 3 tries, program stop.." + url);
		
		return doc;
	}
    
    public static String doPost2(String url, String body) 
		throws Exception
	{
    	Tracer t = new Tracer();
    	HttpClient client = new HttpClient(); 
    	PostMethod post = new PostMethod(url);
    	t.checkpoint("x");
    	post.setRequestBody(body);
    	int statusCode = client.executeMethod(post);
    	t.checkpoint("y");
    	//String result = new String(post.getResponseBody(),"UTF-8");
    	InputStream in = new BufferedInputStream(post.getResponseBodyAsStream());
    	ByteArrayOutputStream bout = new ByteArrayOutputStream();
    	byte[] buf = new byte[1024];
    	int c = 0;
    	while ((c=in.read(buf))>0) {
    		bout.write(buf,0,c);
    	}
    	String result = new String(bout.toByteArray(),"UTF-8");
    	t.checkpoint("z");
    	return result;
	}    

    public static String doPost(String url, String body) 
    	throws Exception
	{
    	Tracer t = new Tracer();
    	java.net.URL postUrl = new java.net.URL(url);
    	t.checkpoint("a");
		HttpURLConnection connection = (HttpURLConnection) postUrl
				.openConnection();
		t.checkpoint("b");
		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");
		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);
		t.checkpoint("c");
		connection.connect();
		t.checkpoint("A");
				
		OutputStreamWriter ow = new OutputStreamWriter(
				new DataOutputStream(connection.getOutputStream()), "UTF-8");
		char[] Str2 = new char[body.length()];
		body.getChars(0, body.length(),Str2, 0);
		t.checkpoint("B");
		
		ow.write(Str2);
		ow.flush();
		ow.close();
		t.checkpoint("C");
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				connection.getInputStream(), "UTF-8"));
		
		StringBuffer sb = new StringBuffer();
		
		String line;
		while ((line = reader.readLine()) != null) {
			sb.append(line);
			sb.append("\n");
		}
		t.checkpoint("D");
		
		reader.close();
		connection.disconnect();
		t.checkpoint("E");
		
		return sb.toString();
	}
    
	
	public static String getPostBody(ServletRequest r)
		throws Exception
	{
		BufferedInputStream br = new BufferedInputStream(r.getInputStream());
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int c = 0;
		while ((c=br.read(buf,0,1024))>0) {			
			bos.write(buf,0,c);			
		}
		return new String(bos.toByteArray());
	}    
}
