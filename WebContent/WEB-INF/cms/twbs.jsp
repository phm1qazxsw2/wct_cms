<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.vod.*"%>
<%
	String basePath = HttpUtil.getBasePath(request);
%>	
<!DOCTYPE html>
<html>
  <head>
    <title>Bootstrap 101 Template</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="<%=basePath%>dist/css/bootstrap.min.css" rel="stylesheet" media="screen">
  </head>
  <body>
    <h1>Hello, world!</h1>

    <script src="http://code.jquery.com/jquery.js"></script>
    <script src="<%=basePath%>dist/js/bootstrap.min.js"></script>
    <script src="<%=basePath%>dist/js/respond.js"></script>
  </body>
</html>