<%@ page language="java" import="com.aliyun.openservices.*,
	com.aliyun.openservices.ots.*,com.aliyun.openservices.ots.model.*"
	pageEncoding="UTF-8" contentType="text/plain;charset=UTF-8"%>
<%
	final String accessId = "KumrkHWYv6vg1GdI";
    final String accessKey = "pMvyLeHADeuG9PHHH3tlBggXiQYr5w";
    OTSClient client = new OTSClient(accessId, accessKey); 
    final String tableName = "logv2";
	
	TableMeta tm = new TableMeta(tableName);
    tm.addPrimaryKey("from", PrimaryKeyType.STRING);
    tm.addPrimaryKey("time", PrimaryKeyType.STRING);
    

    client.createTable(tm);
%>