package com.ever.smit.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import java.util.TimeZone;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
public class StringUtils {
	public final static Logger logger = Logger.getLogger(StringUtils.class);
	public static boolean isEmpty(Object pObj) {
		if (pObj == null)
			return true;
		if (pObj == "")
			return true;
		if (pObj instanceof String) {
			if (((String) pObj).length() == 0) {
				return true;
			}
		} else if (pObj instanceof Collection) {
			if (((Collection) pObj).size() == 0) {
				return true;
			}
		} else if (pObj instanceof Map) {
			if (((Map) pObj).size() == 0) {
				return true;
			}
		}
		return false;
	}
	
	public static boolean isNum(String str){
		if(StringUtils.isEmpty(str))
			return false;
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
	
	/**
	 * 检查src中是否包含字符串contain
	 * @param src
	 * @param contain
	 * @return
	 */
	public static boolean IsIndexChar(String src,String contain ){
		if(StringUtils.isEmpty(contain)||StringUtils.isEmpty(src))
			return false;
		String []val=contain.split(",");
		for(String v:val){
			if(src.indexOf(v)==-1){
				return false;
			}
		}
		return true;
	}

	public static String toChinese(String strvalue) {
		try {
			if (strvalue == null) {
				return "";
			} else {
				strvalue = new String(strvalue.getBytes("ISO8859_1"), "GBK");
				return strvalue;
			}
		} catch (Exception e) {
			return "";
		}
	}

	public static String getRandom(int lenght) {
		long nowtime = System.currentTimeMillis();
		Random random = new Random(nowtime);
		byte[] bi = new byte[lenght];
		random.nextBytes(bi);
		String randomNumber = "";
		for (byte b : bi) {
			char c = (b + "").charAt(("" + b).length() - 1);
			randomNumber += c;
		}
		return randomNumber;
	}
	public static String getRandomLetter(int index){
		String randomLetter="";
	      for (int i = 1; i <= index; i++) {  
	    	  randomLetter+=getRandomChar('a', 'z');  
	        }
		return randomLetter; 
	}
	public static String getRandomLetter(){
		String randomLetter="";
	      for (int i = 1; i <= 3; i++) {  
	    	  randomLetter+=getRandomChar('a', 'z');  
	        }
		return randomLetter; 
	}
	
	/**
	 * 生成5位数随机验证码
	 * @return
	 */
	public static String gen5Random() {
		int[] array = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		Random rand = new Random();
		for (int i = 10; i > 1; i--) {
			int index = rand.nextInt(i);
			int tmp = array[index];
			array[index] = array[i - 1];
			array[i - 1] = tmp;
		}
		int result = 0;
		for (int i = 0; i < 5; i++)
			result = result * 10 + array[i];
		String rtnPwd = result + "";

		while (rtnPwd.length() < 5)
			rtnPwd = "0" + rtnPwd;
		return rtnPwd;
	}
	
	
	/**
	 * 获取随机token
	 * @return
	 */
	public static String getRandomToken() {
		String token = "";
		Random random = new Random(System.currentTimeMillis());
		for (int i = 0; i < 4; i++) {
			token += Integer.toHexString(random.nextInt());
		}
		return token;
	}
	
	 public static char getRandomChar(char ch1, char ch2) {  
	        if (ch1 > ch2)  
	            return 0;  
	        // return (char) (ch1 + new Random().nextDouble() * (ch2 - ch1 + 1));  
	        return (char) (ch1 + Math.random() * (ch2 - ch1 + 1));  
	    }  
	
	
	// 对输入的字符串进行一次编码转换，防止SQL注入
	public static String StringtoSql(String str) {
		str = nullToString(str, "");
		try {
			str = str.trim().replace('\'', (char) 1);
		} catch (Exception e) {
			return "";
		}
		return str;
	}

	// 对字符串进行二次编码转换，防止出库时异常
	public static String SqltoString(String str) {
		str = nullToString(str, "");
		try {
			str = str.replace((char) 1, '\'').trim();
		} catch (Exception e) {
			return "";
		}
		return str;
	}

	// 对字符串进行Unicode编码
	public static String toUnicode(String strvalue) {
		try {
			if (strvalue == null) {
				return null;
			} else {
				strvalue = new String(strvalue.getBytes("GBK"), "ISO8859_1");
				return strvalue;
			}
		} catch (Exception e) {
			return "";
		}
	}

	// 判断是否为当前时间
	public static boolean compareNowTime(String date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date d = null;
		try {
			d = format.parse(date); // 返回一个Date类的实例
		} catch (ParseException ex) {
		}
		if (System.currentTimeMillis() - 259200000 < d.getTime()) {
			return true;
		}
		return false;
	}

	// 判断用户输入的是否是数字或字母
	public static boolean isID(String str) {
		if (str != null && str.length() > 0) {
			if (str.charAt(0) < 57 && str.charAt(0) > 48) {
				return false;
			}
			for (int i = 0; i < str.length(); i++) {
				if (str.charAt(i) < 65 && str.charAt(i) > 57
						|| str.charAt(i) > 90 && str.charAt(i) < 97
						&& str.charAt(i) != 95 || str.charAt(i) > 122
						|| str.charAt(i) < 48) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	// 对输入数据中的HTML字符进行转换
	public static final String escapeHTMLTags(String input) {
		if (input == null || input.length() == 0) {
			return input;
		}
		StringBuffer buf = new StringBuffer(input.length());
		char ch = ' ';
		for (int i = 0; i < input.length(); i++) {
			ch = input.charAt(i);
			if (ch == '<') {
				buf.append("&lt;");
			} else if (ch == '>') {
				buf.append("&gt;");
			} else {
				buf.append(ch);
			}
		}
		String str = buf.toString();
		str = str.replace(" ", "&nbsp;");
		str = str.replace("\r\n", "<br>");
		return str;
	}

	// 处理字符串中的空值
	public static final String nullToString(String v, String toV) {
		if (v == null) {
			v = toV;
		}
		return v;
	}

	// 对SQL语句中输入的空值进行处理
	public static final String SqlToLink(String str) {
		str = StringUtils.nullToString(str, "");
		if ("".equals(str)) {
			str = " LIKE '%' ";
		} else {
			str = (" LIKE '%" + str + "%' ");
		}
		return str;
	}

	// 将整型值转换为字符串
	public static final String SqlToLink(int i) {
		String str = "";
		try {
			str = new Integer(i).toString();
		} catch (Exception e) {
		}
		if (i == -1) {
			str = "";
		}
		return StringUtils.SqlToLink(str);
	}
	//转换性别1=男、0=女
	public static final String ConvertGender(String val){
		if(StringUtils.isEmpty(val)){
			return "请修改个人资料";
		}else{
			if(val.equals("1")){
				return "男";
			}else if(val.equals("0")){
				return "女";
			}
		}
		return val;
	}
	
    /**
     * 获取当前时间
     * @return
     */
	public static final Date getDayTime() {
		try {
			return  new Date(System.currentTimeMillis());
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public static final String getDayTimeStr() {
		try {
			return  Constants.sfs.format(new Date(System.currentTimeMillis()));
		} catch (Exception e) {
			return null;
		}
	}

    /**
     * 获取当天开始时间
     * @return
     */
	public static final String getDayStartTime() {
		try {
			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
			date = Constants.sfs.parse(Constants.sfs.format(date));
			return  Constants.sf.format(date);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 获取当天结束时间
	 * @return
	 */
	public static final String getDayEndTime() {
		try {
			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
			date = Constants.sfs.parse(Constants.sfs.format(date));
			cal.setTime(date);
			cal.add(Calendar.DAY_OF_MONTH, 1);
			cal.add(Calendar.SECOND, -1);
			date = cal.getTime();
			return Constants.sf.format(date);
		} catch (ParseException e) {
			return null;
		}
	}
	/**
	 * 将年月日时分秒截取小时分钟
	 * @return
	 */
	public static final String ConvertMS(String val) {
		try {
			if(!StringUtils.isEmpty(val)){
				return Constants.sfm.format(Constants.sf.parse(val));
			}
		} catch (ParseException e) {
			return null;
		}
		return null;
	}
	

	/**
	 * 判断文件是否为图片文件
	 * @param srcFileName
	 * @return
	 */
	private boolean isImage(String srcFileName) {
		boolean flag = false;
		FileInputStream imgFile = null;
		try {
			imgFile = new FileInputStream(srcFileName);
			BufferedImage bufreader = ImageIO.read(imgFile);
			int width = bufreader.getWidth();
			int height = bufreader.getHeight();
			if (width == 0 || height == 0) {
				flag = false;
			} else {
				flag = true;
			}
			imgFile.close();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
	/**
	 * 获取0时区的时间
	 * @return
	 */
	public static String getTime(){
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sf.setTimeZone(TimeZone.getTimeZone("GMT+0"));
		return sf.format(new Date().getTime());
	}
	
	public static String request(String urlBuf, String xml) throws Exception {
		URL url = new URL(urlBuf);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url
				.openConnection();
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setDoInput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setConnectTimeout(500000);
		httpURLConnection.setReadTimeout(500000);
		httpURLConnection.connect();
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
				httpURLConnection.getOutputStream(), "UTF-8"));
		out.write(xml);
		out.flush();

		int code = httpURLConnection.getResponseCode();

		BufferedReader br = new BufferedReader(new InputStreamReader(
				(InputStream) httpURLConnection.getInputStream()));
		String line = null;
		StringBuilder sb = new StringBuilder();
		while ((line = br.readLine()) != null) {
			sb.append(line);
		}
		
		return sb.toString();
	}
	/**
	 * 文件转换为byte
	 * @param f
	 * @return
	 */
	public static byte[] getBytesFromFile(File f){
        if (f == null){
            return null;
        }
        try{
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            for (int n;(n = stream.read(b)) != -1;) {
            	out.write(b, 0, n);
            }
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e){
        }
        return null;
    }
	
	// http POST请求方法
	public static String doPostRequest(String reqUrl,String params) {
		URL u = null;
		HttpURLConnection con = null;
		//尝试发送请求
		try {
			System.out.println("====params=="+params);
		u = new URL(reqUrl);
		con = (HttpURLConnection) u.openConnection();
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setUseCaches(false);
		con.setRequestProperty("Content-Type", "application/json");
		OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
		osw.write(params);
		osw.flush();
		osw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		if (con != null) {
			con.disconnect();
		}
		}
		 
		//读取返回内容
		StringBuffer buffer = new StringBuffer();
		try {
		BufferedReader br = new BufferedReader(new InputStreamReader(con
		.getInputStream(), "UTF-8"));
		String temp;
		while ((temp = br.readLine()) != null) {
			buffer.append(temp);
			buffer.append("\n");
		}
		} catch (Exception e) {
		e.printStackTrace();
		}
		 
		return buffer.toString();
	}
	
	// http POST请求方法
	public static String doPostRequest(String reqUrl, Map<String, String> params) {
		URL u = null;
		HttpURLConnection con = null;
		//构建请求参数
		StringBuffer sb = new StringBuffer();
		if(params!=null){
		for (Entry<String, String> e : params.entrySet()) {
			sb.append(e.getKey());
			sb.append("=");
			sb.append(e.getValue());
			sb.append("&");
		}
		sb.substring(0, sb.length() - 1);
		}
		System.out.println("send_url:"+reqUrl);
		System.out.println("send_data:"+sb.toString());
		//尝试发送请求
		try {
		u = new URL(reqUrl);
		con = (HttpURLConnection) u.openConnection();
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setUseCaches(false);
		con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
		osw.write(sb.toString());
		osw.flush();
		osw.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		if (con != null) {
			con.disconnect();
		}
		}
		 
		//读取返回内容
		StringBuffer buffer = new StringBuffer();
		try {
		BufferedReader br = new BufferedReader(new InputStreamReader(con
		.getInputStream(), "UTF-8"));
		String temp;
		while ((temp = br.readLine()) != null) {
			buffer.append(temp);
			buffer.append("\n");
		}
		} catch (Exception e) {
		e.printStackTrace();
		}
		 
		return buffer.toString();
	}
	
	// http Get请求方法
	
/*	public static String doGetRequest(String reqUrl){
		try{
		HttpGet httpget = new HttpGet(reqUrl);
		// 建立HttpPost对象
		HttpResponse response = new DefaultHttpClient().execute(httpget);
		// 发送GET,并返回一个HttpResponse对象，相对于POST，省去了添加NameValuePair数组作参数
		if (response.getStatusLine().getStatusCode() == 200) {// 如果状态码为200,就是正常返回
			String result = EntityUtils.toString(response.getEntity(),"UTF-8");
			// 得到返回的字符串
			return result;
			// 打印输出
		}
		}catch (Exception e) {
			logger.error(e);
		}
		return "";
	}*/
	public static String doGetRequest(String reqUrl) {
		HttpURLConnection url_con = null;
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(reqUrl);
			url_con = (HttpURLConnection) url.openConnection();
			url_con.setRequestMethod("GET");
			url_con.setConnectTimeout(900000);
			url_con.setReadTimeout(900000);
			url_con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
			url_con.connect();
			InputStream in = url_con.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(in,"UTF-8"));
			String line = null;
			while ((line = rd.readLine()) != null) {
				sb.append(line);
			}
			rd.close();
			in.close();
		} catch (Exception e) {
			logger.error("doGetRequest errors:",e);
		} finally {
			if (url_con != null) {
				url_con.disconnect();
			}
		}
		return sb.toString();
	}
	
	public static String covnertUrl (String imageUrl){
		if(imageUrl==null)
			return imageUrl;
		if(imageUrl.trim().startsWith("group"))
			return Constants.path+imageUrl;
		//else if(imageUrl.trim().startsWith("http://v.guozitv.com"))
			//return Constants.hdpfanspath+imageUrl+"&w="+Constants.APP_WEB_ICON_WIDTH+"&zc=1&h="+Constants.APP_WEB_ICON_HEIHGT;
		else if(imageUrl.trim().startsWith("http"))
			return imageUrl;
		else
			return Constants.path+imageUrl;  
		
	/*	if("group".startsWith(imageUrl)){
			return Constants.path+imageUrl;
		}else if("http".startsWith(imageUrl)){
			return imageUrl;
		}else{
			return Constants.path+imageUrl;
		}*/
	}
	
	public static String dataProcess(String val){
		if(StringUtils.isEmpty(val)){
			return "\u00A0";
		}else{
			return val;
		}
	}

	public static  String red(int args) {
		try {
			if (args == 1) {
				return "YES";
			} else {
				return "NO";
			}
		} catch (Exception e) {
			return "NO";
		}
	}
    /**
     *date1 >date2  >0
     *date1 =date2  =0
     *date1 <date2  <0
     * @param queryDate
     * @param currentDate
     * @return
     */
    public static int compare (String queryDate,String currentDate){
       return queryDate.compareTo(currentDate);
    }
	public static String ConvertPalyUrl(String url){
		if(StringUtils.isEmpty(url))
			return "\u00A0";
		/*else if(url.trim().startsWith("http"))
			return URLEncodeUtils.encodeURL(url);*/
		else
			return url.trim();
	}
	public static void main(String[] args) {
		System.out.println(doGetRequest("://v.guozitv.com/tv/letv/1/3141").indexOf("http"));
		//System.out.println(IsIndexChar("<?xml version='1.0' encoding='UTF-8' standalone='no'?><response method='getMovie'><attributes>","totalNumber,movieId"));
	}
	
/*	private static String request(String urlBuf, String xml) {
		URL url = new URL(urlBuf);
		HttpURLConnection httpURLConnection = (HttpURLConnection) url
				.openConnection();
		httpURLConnection.setDoOutput(true);
		httpURLConnection.setDoInput(true);
		httpURLConnection.setRequestMethod("POST");
		httpURLConnection.setConnectTimeout(100000);//100s
		httpURLConnection.setReadTimeout(100000);
		httpURLConnection.connect();
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
				httpURLConnection.getOutputStream(), "UTF-8"));
		out.write(xml);
		out.flush();
		BufferedReader br = new BufferedReader(new InputStreamReader(
				(InputStream) httpURLConnection.getInputStream()));
		String line = null;
		StringBuilder buffer = new StringBuilder();
		while ((line = br.readLine()) != null) {
			buffer.append(line);
		}
		
		return buffer.toString();
	}*/


}
