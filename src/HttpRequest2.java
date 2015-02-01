

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//import com.service.AESOperator;

/**
 *
 * @author xiliu.zeng
 */
public class HttpRequest2 {
    private static Log log = LogFactory.getLog(HttpRequest2.class);
    /** Creates a new instance of HttpRequest */
    public HttpRequest2() {
    }

    public static String doPost(String reqUrl, String xmlInfo,String recvEncoding){
        
        System.out.println("xmlInfo === "+xmlInfo); //44461111111111111111111111111121 55-55-55-55-09-66
        
        HttpURLConnection url_con = null;
        String responseContent = null;
        OutputStreamWriter wr = null; 
        Map rtnMap = new HashMap();
        
    	long start = System.currentTimeMillis();
    	Date begDate = new Date(start);
        try
        {
            URL url = new URL(reqUrl);
            
            //initProxy();
            
            url_con = (HttpURLConnection) url.openConnection();
            //url_con.setRequestProperty("Referer", "http://tv.cntv.cn/epg");   
            url_con.setRequestMethod("POST");
//            System.setProperty("sun.net.client.defaultConnectTimeout", String
//                    .valueOf(HttpRequestThread.connectTimeOut));
//            System.setProperty("sun.net.client.defaultReadTimeout", String
//                    .valueOf(HttpRequestThread.readTimeOut)); 
            url_con.setConnectTimeout(6000);  
            url_con.setReadTimeout(6000);
            
            
            url_con.setDoOutput(true);
//            byte[] b = xmlInfo.getBytes();
//            url_con.getOutputStream().write(b, 0, b.length);
//            url_con.getOutputStream().flush();
//            url_con.getOutputStream().close();
            
            wr = new OutputStreamWriter(url_con.getOutputStream(),recvEncoding);  
            //System.out.println(url_con.getHeaderFields().toString());
            wr.write(xmlInfo.toString());  
            wr.flush();  
            //System.out.println(url_con.getHeaderFields().toString());
            InputStream in = url_con.getInputStream();
            
                        
            BufferedReader rd = new BufferedReader(new InputStreamReader(in,
                    recvEncoding));
            String tempLine = rd.readLine();
            StringBuffer tempStr = new StringBuffer();
            String crlf=System.getProperty("line.separator");
            while (tempLine != null){
                tempStr.append(tempLine);
                tempStr.append(crlf);
                tempLine = rd.readLine();
            }
            
            responseContent = tempStr.toString();
            //System.out.println("responseContent == "+responseContent);
            rd.close();
            in.close();
        }
        catch (Exception e){
            e.printStackTrace();
            return e.getMessage();
        }finally{
        	if(wr!=null){  
        		try {
					wr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
        	} 
            if (url_con != null){
                url_con.disconnect();
            }
        }

        return responseContent;
    }
  
    public static void doPost1(String reqUrl, String xmlInfo,String recvEncoding){
        
        System.out.println("xmlInfo === "+xmlInfo); //44461111111111111111111111111121 55-55-55-55-09-66
        
        HttpURLConnection url_con = null;
        String responseContent = null;
        OutputStreamWriter wr = null; 
        Map rtnMap = new HashMap();
        
    	long start = System.currentTimeMillis();
    	Date begDate = new Date(start);
        try
        {
            URL url = new URL(reqUrl);
            
            //initProxy();
            
            url_con = (HttpURLConnection) url.openConnection();
            //url_con.setRequestProperty("Referer", "http://tv.cntv.cn/epg");   
            url_con.setRequestMethod("POST");
//            System.setProperty("sun.net.client.defaultConnectTimeout", String
//                    .valueOf(HttpRequestThread.connectTimeOut));
//            System.setProperty("sun.net.client.defaultReadTimeout", String
//                    .valueOf(HttpRequestThread.readTimeOut)); 
            url_con.setConnectTimeout(6000);  
            url_con.setReadTimeout(6000);
            
            
            url_con.setDoOutput(true);
//            byte[] b = xmlInfo.getBytes();
//            url_con.getOutputStream().write(b, 0, b.length);
//            url_con.getOutputStream().flush();
//            url_con.getOutputStream().close();
            
            wr = new OutputStreamWriter(url_con.getOutputStream(),recvEncoding);  
            //System.out.println(url_con.getHeaderFields().toString());
            wr.write(xmlInfo.toString());  
            wr.flush();  
            //System.out.println(url_con.getHeaderFields().toString());
            InputStream in = url_con.getInputStream();
            
            System.out.println("--------------- 开始 -------------------");
            saveFile(in,"d:/guangdong-DX-3-area.zip");
            System.out.println("--------------- 完毕 -------------------");
            in.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }finally{
        	if(wr!=null){  
        		try {
					wr.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
        	} 
            if (url_con != null){
                url_con.disconnect();
            }
        }
    }
  
	public static void saveFile(InputStream stream,String filePath) {
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
			byte[] a = new byte[32];
			stream.read(a);
			byte[] b = new byte[1000];
			int n;
			while ((n = stream.read(b)) != -1)
				out.write(b, 0, n);

			File newFile = new File(filePath);
	        OutputStream os = new FileOutputStream(newFile);
	        os.write(out.toByteArray());  //把流一次性写入一个文件里面
	        os.flush();
	        os.close();
	        stream.close();
	        out.close();
	        //newFile.delete();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    public static void initProxy() {
    	if(true){
	        final String username ="username"; 
	        final String password ="password"; 
	        
	        Authenticator.setDefault(new Authenticator() { 
	            protected PasswordAuthentication getPasswordAuthentication() { 
	                return new PasswordAuthentication(username, 
	                        new String(password).toCharArray()); 
	            } 
	        }); 
	
	        System.setProperty("http.proxyType", "4"); 
	        System.setProperty("http.proxyPort", Integer.toString(3128)); 
	        System.setProperty("http.proxyHost", "192.168.9.1"); 
	        System.setProperty("http.proxySet", "true"); 
    	}
    } 
    
    public static void main(String[] args) {
    	String xmlInfo = "<?xml version='1.0' encoding='utf-8'?><request method='getLiveList'><parameters><version>01.00</version><mac>00-90-e6-00-00-00</mac><reqDataType>1</reqDataType><urlLevel>3</urlLevel></parameters></request>";
    	//String url = "http://imsarea.widecloud.com.cn/gy_ims/ims/interfaces/aliServlet";
    	//String url = "http://imsarea.widecloud.com.cn/gy_ims/ims/interfaces/aliServlet";
    	String url = "http://imstv.playergetlist.com:8080/tvims/ims/interfaces/aliServlet";
    	Long d1 = new Date().getTime();
    	String temp = HttpRequest2.doPost(url,xmlInfo,"UTF-8");
    	
        System.out.println(temp + " " + (new Date().getTime()-d1));
    }
    
}
