<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.report.*,dbo.*"%>
<%
	HttpParams hp = new HttpParams(request);
	int c = hp.getInt("c", 0);
	
	RandioQrScan rs = new RandioQrScan();
	rs.setIp(request.getRemoteAddr());
	rs.setCtime(new Date());
	RandioQrScanMgr rsmgr = new RandioQrScanMgr(0);
	rsmgr.setSource("db152");
	
	if (c==0) {
		rs.setType(RandioQrScan.TYPE_REDBULL);
		rsmgr.create(rs);
		response.sendRedirect("http://static.adwo.com/ad/201406/redbull/index.html?cid=gyld");
		return;
	}
	else {
		rs.setType(RandioQrScan.TYPE_GAME1);
		rsmgr.create(rs);
		response.sendRedirect("https://itunes.apple.com/cn/app/suo-lian-zhan-ji-guo-min-ji/id842503726?mt=8&uo=4");
		return;
	}
%>