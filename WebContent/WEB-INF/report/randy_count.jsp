<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.report.*,dbo.*"%>
<%
	RandioQrScan rs = new RandioQrScan();
	rs.setIp(request.getRemoteAddr());
	rs.setCtime(new Date());
	RandioQrScanMgr rsmgr = new RandioQrScanMgr(0);
	rsmgr.setSource("db152");
	rsmgr.create(rs);
	response.setStatus(302);
	response.addHeader("Location", "http://www.randylo.com/WapRandy/randy.html");
	//http://mp.weixin.qq.com/s?__biz=MzA3NDA5NzAzOA==&mid=200140920&idx=3&sn=26b26c343e56ba83c27207ef90c2afb2&scene=1&from=singlemessage&isappinstalled=0#rd
%>