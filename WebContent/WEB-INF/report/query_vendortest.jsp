<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.report.*,dbo.*"%>
<%
	TmpHour3Mgr tmgr = new TmpHour3Mgr(0);
    tmgr.setSource("db152");
    
    int id1 = 27433;
    int id2 = 1553109;
	
	TmpHourReportMgr thrmgr = new TmpHourReportMgr(0);
    		thrmgr.setSource("db152");

	List<Map<String, Object>> rlist = thrmgr.retrieveListMap(
		"select device_mac,vendor_code,software_code,chipType,province,city,communications, sum(count) as sum1 from " +
		    "(select * from tmp_hour4 where id>=" + 
 		     id1 + " and id<" + id2 + " and count<5 order by id desc) as t2 group by device_mac"
 	);
 	
 	List<Map<String, Object>> rlist2 = thrmgr.retrieveListMap(
		"select device_mac,vendor_code,software_code,chipType,province,city,communications, sum(count) as sum1 from " +
		    "(select * from tmp_hour4 where id>=" + 
 		     id1 + " and id<" + id2 + " and count<5 and province='北京' and vendor_code='L201' order by id desc) as t2 group by device_mac"
 	);
 	
 	int bj_num = 0;
 	Map<String, String> m_bj = new HashMap<String, String>();
 	for (int i=0; i<rlist.size(); i++) {
 		Map<String, Object> m = rlist.get(i);
 		String mac = (String) m.get("device_mac");
 		String province = (String) m.get("province");
 		String vendor = (String) m.get("vendor_code");
		if (province!=null && vendor!=null && province.equals("北京") && (vendor.equals("L201"))) {
			m_bj.put(mac, mac);
		}
 	}
 	
 	StringBuffer sb = new StringBuffer();
 	sb.append("<table border=1>");
 	int out_num = 0;
 	for (int i=0; i<rlist2.size(); i++) {
 		Map<String, Object> m = rlist2.get(i);
 		String mac = (String) m.get("device_mac");
 		sb.append("<tr");
 		if (m_bj.remove(mac)==null) {
 			sb.append(" bgcolor=\"pink\"");
 			out_num ++;
 		}
 		sb.append(">");
 		sb.append("<td>").append(i+1).append("</td>");
 		sb.append("<td>").append(mac).append("</td>");
 		sb.append("</tr>");
 	}
 	Iterator<String> iter = m_bj.keySet().iterator();
 	while (iter.hasNext()) {
 		String mac = iter.next();
 		sb.append("<tr bgcolor=\"lightyellow\">");
 		sb.append("<td>").append(mac).append("</td>");
 		sb.append("</tr>");
 	}
 	sb.append("</table>");
	out.println("<br/>");
 	out.println(sb.toString());
	out.println("out_num=" +  out_num);	

%>
