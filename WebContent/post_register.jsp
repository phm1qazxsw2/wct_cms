<%@ page language="java" import="java.util.*,java.net.*,java.io.*,org.json.*"
	pageEncoding="UTF-8" contentType="text/xml;charset=UTF-8"%>
<%

	String content = "<request method=\"register\">"
		+ "<parameters>"
		+ "<decive_type>1</decive_type>"
		+ "<device_model>12345</device_model>" 
		+ "<device_id>00300038EE08D297000002E000000000</device_id>"
		+ "<mac>00-90-E6-00-78-CB</mac>"
		+ "<key>146EF19026F52921015F129CA15C04B5522638086912B5B3</key>"
		+ "<code>1</code>" + "</parameters>" + "</request>";

	String result = util.HttpUtil.doPost("http://223.203.193.149:8008/nmpsp_server/ali/service", content);
	out.println(result);

%>