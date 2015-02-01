<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.report.*,dbo.*"%>
<%
	HttpParams hp = new HttpParams(request);		
	try {	
		long t1 = new Date().getTime();
		
		VideoLog2Mgr vmgr = new VideoLog2Mgr(0);
		vmgr.setSource("bendb");	
		TmpHour3Mgr tmgr = new TmpHour3Mgr(0);
		tmgr.setSource("db152");
		
		System.out.print("## 1 ");
		List<VideoLog2> vlogs = vmgr.retrieveList(
			"createTime>='2014-03-14' and createTime<'2014-03-15'", "");
		System.out.print("## 2 ");
		TmpHour3 th = tmgr.retrieveList("ctime>='2014-03-13 12:00:00'", "limit 1").get(0);
		System.out.print("## 3 ");
		int start_id = th.getId();
		th = tmgr.retrieveList("ctime>'2014-03-15'", "limit 1").get(0);
		int end_id = th.getId();
		System.out.print("## 4 ");
		
		String filter = "id>="+start_id + " and id<" + end_id;
		SoftJoin sj = new SoftJoin(vlogs, "getIp", true);
		List<TmpHour3> tmps = sj.doJoin(tmgr, "device_ip",  filter, "");
		System.out.print("## 5 ");
		Map<String, List<TmpHour3>> thMap = new SortingMap(tmps).doSortA("getDevice_ip");
		System.out.print("## 6 ");
		
		StringBuffer sb = new StringBuffer();
		for (int i=0; i<vlogs.size(); i++) {
			VideoLog2 v = vlogs.get(i);
			List<TmpHour3> ths = thMap.get(v.getIp());
			sb.append(v.getIp() + ":" + ((ths==null)?"N/A":ths.get(0).getVendor_code()));
			sb.append("<br/>\n");
		}
		System.out.println("## 7 ");
		out.println(sb.toString());
	}
	catch (Exception e) {
		e.printStackTrace();
	}
%>
