package util;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;
import java.io.*;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.*;  
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.fileupload.disk.*;

import org.json.*;
 
public class HttpParams
{
    private final static String password = "jiw3f3io";
    public static String encryptParams(Map<String, String> params)
        throws Exception
    {
        Iterator<String> iter = params.keySet().iterator();
        StringBuffer sb = new StringBuffer();
        while (iter.hasNext()) {
            String k = iter.next();
            String v = params.get(k);
            if (sb.length()>0)
                sb.append("&");
            sb.append(k);
            sb.append('=');
            sb.append(URLEncoder.encode(v, "UTF-8"));
        }
        String str = sb.toString();
        // return str;
        return "__=" + SymmetricCipher.encodeECBAsHexString(password, str);
    }

    private Map<String, String> params = null;
    private ServletRequest r = null;
    boolean encoded = false;
    boolean isMultipart = false;
    Map<String, String> avs = null;
    Map<String, FileItem> fileitemMap = null;
    JSONObject json = null;
//    private boolean isMobile = false;
    public HttpParams(ServletRequest r)
        throws Exception
    {
        this.r = r;
        
        HttpServletRequest hreq = ((HttpServletRequest)r);
        String content_type = hreq.getHeader("Content-Type");        
        
        hreq.setCharacterEncoding("UTF-8");
        isMultipart = ServletFileUpload.isMultipartContent(hreq);
        if (isMultipart) {
	        FileItemFactory factory = new DiskFileItemFactory();
	        ServletFileUpload upload = new ServletFileUpload(factory);
	        upload.setHeaderEncoding("UTF-8");
	        List<FileItem> items = upload.parseRequest(hreq);
	        Iterator<FileItem> itr = items.iterator();
	        avs = new HashMap<String, String>();
	        fileitemMap = new HashMap<String, FileItem>();
	        while (itr.hasNext()) {
	            FileItem item = (FileItem) itr.next();
	            String fieldName = item.getFieldName();
	            if (item.isFormField()) {
	                avs.put(fieldName, item.getString("UTF-8"));
	            } else {
	            	fileitemMap.put(fieldName, item);
	            }
	        }        
        }
        else if (content_type!=null && content_type.indexOf("json")>0) {
        	BufferedReader streamReader = new BufferedReader(
        			new InputStreamReader(hreq.getInputStream(), "UTF-8")); 
            StringBuilder responseStrBuilder = new StringBuilder();
            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            json = new JSONObject(responseStrBuilder.toString());
        }
        else {
	        String s = ((HttpServletRequest)this.r).getParameter("__");
	        if (s!=null) {
	            encoded = true;
	            String str = SymmetricCipher.decodeECBString(password, SymmetricCipher.hexStringToByteArray(s));
	            String[] pairs = str.split("&");
	            params = new HashMap<String, String>();
	            try {
		            for (int i=0; i<pairs.length; i++) {
		                String[] tokens = pairs[i].split("=");
		                params.put(tokens[0], URLDecoder.decode(tokens[1], "UTF-8"));
		            }
	            }
	            catch (Exception e) {}
	        }
        }
//        String key = getStr("appkey", null); 
//        isMobile = (key!=null && key.equals("3619919282"));
    }
    
    public HttpParams(String urlEncodedParams)
    	throws Exception
    {
    	String[] pairs = urlEncodedParams.split("&");
    	params = new HashMap<String, String>();
    	encoded = true; 
    	for (int i=0; i<pairs.length; i++) {
    		String pair = pairs[i];
    		String[] tokens = pair.split("=");
    		try {
    			System.out.println(tokens[0] + "=" + tokens[1]);
    			params.put(tokens[0], java.net.URLDecoder.decode(tokens[1], "UTF-8"));
    		}
    		catch (Exception e) {}
    	}
    }
    
    public boolean isEncoded() {
    	return encoded;
    }

    public int getInt(String attr, int not_found_value)
    {
        try {
            if (isMultipart) 
            	return Integer.parseInt(avs.get(attr).trim());
            else if (json!=null) 
            	return json.getInt(attr);
            else if (!encoded) 
            	return HttpUtil.getInt(this.r, attr, not_found_value);
            else	
            	return Integer.parseInt(params.get(attr).trim());
		}
		catch (Exception e) {}
		return not_found_value;
    }
    
    public long getLong(String attr, long not_found_value)
    {
        try {
            if (isMultipart) 
            	return Long.parseLong(avs.get(attr).trim());
            else if (json!=null)
            	return json.getLong(attr);
            else if (!encoded) 
            	return HttpUtil.getLong(this.r, attr, not_found_value);
            else
            	return Long.parseLong(params.get(attr).trim());
		}
		catch (Exception e) {}
		return not_found_value;
    }

    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
    public Date getDate(String attr, Date not_found_value)
    {
        if (!encoded) 
            
 
        try {
        	if (isMultipart) 
            	return sdf.parse(avs.get(attr));
            else if (json!=null)
            	return sdf.parse(json.getString(attr));
        	else if (!encoded) 
        		return HttpUtil.getDate(this.r, attr, not_found_value);
            else
            	return sdf.parse(params.get(attr));
		}
		catch (Exception e) {}
		return not_found_value;
    }
    
    public float getFloat(String attr, float not_found_value)
    {
        try {
        	if (isMultipart) 
            	return Float.parseFloat(avs.get(attr));
            else if (json!=null)
            	return (float) json.getDouble(attr);
        	else if (!encoded) 
        		return HttpUtil.getFloat(this.r, attr, not_found_value);
            else
            	return Float.parseFloat(params.get(attr));
		}
		catch (Exception e) {}
		return not_found_value;
    }

	public String getStr(String attr, String not_found_value)
	{
		String v = null;
						
		try {
        	if (isMultipart) 
            	v = avs.get(attr);
            else if (json!=null)
            	v = json.getString(attr);
        	else if (!encoded) 
        		v = HttpUtil.getStr(this.r, attr, not_found_value);
            else
            	v = params.get(attr);
		}
		catch (Exception e) {}
		
		if (v==null)
			return not_found_value;
		
//		if (isMobile) {
//			// 小丘传的字符串都有 url encoded, 所以要 decode 一边
//			try {
//				v = java.net.URLDecoder.decode(v, "UTF-8");
//			}
//			catch (java.io.UnsupportedEncodingException e) {}
//		}
		
		return v;
	}
	
	public String getInfo()
		throws Exception
	{
		StringBuffer sb = new StringBuffer();
		if (encoded) {
			sb.append("type:encoded\n");
			Iterator<String> iter = params.keySet().iterator();
			while (iter.hasNext()) {
				String name = iter.next();
		        if (name.equals("undefined"))
		            continue;
		        String value = params.get(name);
		        sb.append(name + "=" + value + "\n");
			}
		}
		else if (isMultipart) {
			sb.append("type:multi-part\n");
			Iterator<String> iter = fileitemMap.keySet().iterator();
			sb.append("\n");
			while (iter.hasNext()) {
				String field = iter.next();
				FileItem fi = fileitemMap.get(field);
				sb.append("FILE<" + field + "> filename=" + fi.getName() + " size=" + fi.getSize() + "\n");
			}
			iter = avs.keySet().iterator();
			while (iter.hasNext()) {
				String field = iter.next();
				String value = avs.get(field);
				sb.append(field + "=" + value + "\n");
			}			
		}
		else if (json!=null) {
			sb.append("type:json\n");
			sb.append(json.toString());
		}
		else {
			sb.append("type:normal\n");
		    Enumeration e = r.getParameterNames();
		    while (e.hasMoreElements()) {
		        String name = (String) e.nextElement();
		        if (name.equals("undefined"))
		            continue;
		        String value = r.getParameter(name);
		        sb.append(name + "=" + value + "\n");
		    }
		}
		return sb.toString();
	}
	
	public FileItem getFileItem(String attr)
		throws Exception
	{
		return fileitemMap.get(attr);
	}
	
	public Map<String, FileItem> getFileItmeMap() 
		throws Exception
	{
		return fileitemMap;
	}

}
