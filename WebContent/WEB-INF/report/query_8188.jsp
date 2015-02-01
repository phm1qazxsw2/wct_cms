<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.report.*,dbo.*"%>
<%
	HttpParams hp = new HttpParams(request);		
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd HH:mm");
	
	try {	
		GY8188Mgr gimgr = new GY8188Mgr(0);
		gimgr.setSource("gy152_8188");
		
		List<GY8188> gys = gimgr.retrieveList("buildDate<'2001-01-01'", "");
		System.out.println("## gys size=" + gys.size());
		SoftJoin sj = new SoftJoin(gys, "getChipId", true);
		List<DeviceInfo> dis = sj.doJoin(new DeviceInfo(), "chip_code", "", 0); 
		Map<String, DeviceInfo> diMap = new SortingMap(dis).doSortSingleton("getChip_code");
		for (int i=0; i<gys.size(); i++) {
			GY8188 gy = gys.get(i);
			DeviceInfo di = diMap.get(gy.getChipId());
			gy.setVendor((di!=null)?di.getVendor_code():"");
		}
		Map<String, List<GY8188>> gyMap = new SortingMap(gys).doSortA("getVendor");
		Iterator<String> iter = gyMap.keySet().iterator();
		StringBuffer sb = new StringBuffer();
		sb.append("<table border=1>");
		while (iter.hasNext()) {
			String name = iter.next();
			gys = gyMap.get(name);
			sb.append("<tr>");
			sb.append("<td>").append(name).append("</td>");
			sb.append("<td>").append((gys!=null)?gys.size():0).append("</td>");
			sb.append("</tr>");
		}
		sb.append("</table>");
		System.out.println("## 2");
		out.println(sb.toString());
	}
	catch (Exception e) {
		e.printStackTrace();
	}
%>
