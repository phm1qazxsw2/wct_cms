<%@ page language="java" import="java.util.*"
	contentType="text/xml;charset=UTF-8" pageEncoding="UTF-8"%><%@ 
page import="util.*,java.text.*,com.wct.vod.*,java.io.*,org.jsoup.*,org.jsoup.nodes.*"%><%
	ApiHelper ah = new ApiHelper();
	if (!request.getMethod().equals("POST")) {		
		out.println(ah.getErrorResponse());		
		return;
	}
	
	String body = HttpUtil.getPostBody(request);
	Document doc = Jsoup.parse(body, "UTF-8");
	Element req = doc.select("request").first();
	String method = req.attr("method");
	String ret = "";
	
	if (method.equals("downloadGame")) {
		String mac = doc.select("mac").first().text();
		String gameID = doc.select("gameID").first().text();
		String cell = doc.select("cellphone").first().text();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
System.out.println(sdf.format(new Date()) + " gameID=" + gameID + " cell=" + cell);
		ret = 
		"<?xml version='1.0' encoding='utf-8'?>" + 
		"<response method='downloadGame'>" + 
		"<attributes>" + 
		   "<result>S000</result>" + 
		"</attributes>" +                   
		"</response>"; 
	}
	else {
		ret = "<?xml version='1.0' encoding='utf-8'?><response method='Unknown'><attributes><result>S000</result></attributes></response>"; 
	}
	out.println(ret);
	
/*
接口定义如下： 
请求的xml结构 
<?xml version='1.0' encoding='utf-8'?> 
<request method='downloadGame'> 
<parameters> 
 <mac>00-90-e6-00-08-45</mac> 
 <gameID>XXX</gameID> 
 <cellphone>139XXXXXXXX</cellphone> 
</parameters>                   
</request> 

返回的xml结构 
<?xml version='1.0' encoding='utf-8'?> 
<response method='downloadGame'> 
<attributes> 
 <result>S000</result> 
</attributes>                   
</response> 
其中：result=S000，表示成功； 
   result=E200 表示手机号不正确， 
   result=E201 游戏id不存在 
   result=E000 未知错误 
   ... 
错误的第一个字母为E 

同时请提供一个服务端的接口地址，终端调用，调试阶段可以与轮播服务器接口地址一样。 
*/	
%>
