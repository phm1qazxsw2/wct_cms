<%@ page language="java" import="java.util.*" pageEncoding="BIG5"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
  </head>
  <script src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
  <script>
  	$(function(){
  		var data = '<request method="register">' +
'<parameters>'+
'<decive_type>1</decive_type>'+
'<device_model>12345</device_model>'+
'<device_id>00300038EE08D297000002E000000000</device_id>'+
'<mac>00-90-E6-00-78-CB</mac>'+
'<key>146EF19026F52921015F129CA15C04B5522638086912B5B3</key>'+
'<code>1</code>'+
'</parameters>'+
'</request>';
		$.post('http://223.203.193.149:8008/nmpsp_server/ali/service', data, function(data) {
			alert(data);
		})  	
  	})
  </script>
  
  <body>
    This is my JSP page. 2<br>
  </body>
</html>
