<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.report.*,dbo.*"%>
<%
	HttpParams hp = new HttpParams(request);		
	
	try {		
		MovieInfoMgr mmgr = new MovieInfoMgr(0);
		mmgr.setSource("bendb");
		VideoLogMgr vmgr = new VideoLogMgr(0);
		vmgr.setSource("bendb");
		
		DecimalFormat mnf = new DecimalFormat("###,###,###"); 
		
		List<MovieInfo> tennis_infos = mmgr.retrieveList("type='tennis'","order by id");
		List<MovieInfo> yoga_infos = mmgr.retrieveList("type='yoga'","order by id");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -7);
		
		Date start = hp.getDate("d1", c.getTime());
		Date end = hp.getDate("d2", new Date());
		
		c.setTime(end);
		c.add(Calendar.DATE, 1);
		Date onedayafter = c.getTime();
		
		long t1 = new Date().getTime();
		System.out.println("#ben# t1");
		ArrayList<VideoLog> logs = vmgr.retrieveSQLList("select videoUrl,count(*) as pageview," +
			"COUNT(DISTINCT ip) as visit, DATE_FORMAT(createTime,'%Y-%m-%d') as createDate " +
			"from vod_video_log where createTime>='"+sdf.format(start)+
			"' and createTime<'"+sdf.format(onedayafter)+"' " +
			" group by videoUrl,createDate order by videoUrl,createDate");
		long t2 = new Date().getTime();
		System.out.println("#ben# time=" + (t2-t1));
		Map<String, VideoLog> logMap = new SortingMap(logs).doSortSingleton("getKey");
			
		StringBuffer sb = new StringBuffer();
		sb.append("网球统计数字<br>");		
		sb.append("<table border=1>");
		sb.append("<tr><td></td><td></td>");
		c.setTime(start);
		int days = 0;
		for (Date d = c.getTime(); d.compareTo(onedayafter)<0; c.add(Calendar.DATE,1),d = c.getTime()) {
			sb.append("<td>").append(sdf.format(d)).append("</td>");
			days ++;
		}
		sb.append("<td>总数</td>");
		sb.append("<td>平均</td>");
		sb.append("</tr>");
		int[] uvisit_day = new int[days];
		for (int i=0; i<tennis_infos.size(); i++) {
			MovieInfo mi = tennis_infos.get(i);
			c.setTime(start);
			sb.append("<tr align=right>");
			sb.append("<td>").append(i+1).append("</td>");
			sb.append("<td align=left>").append(mi.getName()).append("</td>"); 
			//sb.append("<td>").append(mi.getUrl()).append("</td>");
			int uvisit_row = 0;
			days = 0;
			for (Date d = c.getTime(); d.compareTo(onedayafter)<0; 
				c.add(Calendar.DATE,1),d = c.getTime()) {
				VideoLog v = logMap.get(mi.getUrl() + "#" + sdf.format(d));
				int pview = (v==null)?0:v.getPageview();
				int uvisit = (v==null)?0:v.getVisit();
				uvisit_day[days] += uvisit;
				uvisit_row += uvisit;
				sb.append("<td>");
				sb.append(mnf.format(uvisit));
				sb.append("</td>");
				days ++;
			}
			sb.append("<td>");
			sb.append(mnf.format(uvisit_row));
			sb.append("</td>");
			sb.append("<td>");
			sb.append(mnf.format(uvisit_row/days));
			sb.append("</td>");
			sb.append("</tr>");
		}	
		sb.append("<tr align=right><td></td><td align=left>节目总计</td>");
		c.setTime(start);
		int total = 0;
		for (int i=0; i<days; i++) {
			sb.append("<td>").append(uvisit_day[i]).append("</td>");
			total += uvisit_day[i];
		}
		sb.append("<td>").append(mnf.format(total)).append("</td>");
		sb.append("<td>").append(mnf.format(total/uvisit_day.length)).append("</td>");
		sb.append("</tr>");
		sb.append("</table>");	

		System.out.println("#ben# 3");
		StringBuffer sb2 = new StringBuffer();
		sb2.append("<br><br>瑜伽统计数字<br>");
		sb2.append("<table border=1>");
		sb2.append("<tr><td></td><td></td>");
		c.setTime(start);
		days = 0;
		for (Date d = c.getTime(); d.compareTo(onedayafter)<0; c.add(Calendar.DATE,1),d = c.getTime()) {
			sb2.append("<td>").append(sdf.format(d)).append("</td>");
			days ++;
		}
		System.out.println("#ben# 3.5");
		sb2.append("<td>总数</td>");
		sb2.append("<td>平均</td>");
		sb2.append("</tr>");
		uvisit_day = new int[days];
		for (int i=0; i<yoga_infos.size(); i++) {
			MovieInfo mi = yoga_infos.get(i);
			c.setTime(start);
			sb2.append("<tr align=right>");
			sb2.append("<td>").append(i+1).append("</td>");
			sb2.append("<td align=left>").append(mi.getName()).append("</td>"); 
			//sb2.append("<td>").append(mi.getUrl()).append("</td>");
			int uvisit_row = 0;
			days = 0;
			for (Date d = c.getTime(); d.compareTo(onedayafter)<0; c.add(Calendar.DATE,1),d = c.getTime()) {
				VideoLog v = logMap.get(mi.getUrl() + "#" + sdf.format(d));
				int pview = (v==null)?0:v.getPageview();
				int uvisit = (v==null)?0:v.getVisit();
				uvisit_day[days] += uvisit;
				uvisit_row += uvisit;
				sb2.append("<td>");
				sb2.append(mnf.format(uvisit));
				sb2.append("</td>");
				days ++;
			}
			sb2.append("<td>");
			sb2.append(mnf.format(uvisit_row));
			sb2.append("</td>");
			sb2.append("<td>");
			sb2.append(mnf.format(uvisit_row/days));
			sb2.append("</td>");
			sb2.append("</tr>");
		}
		System.out.println("#ben# 3.6");
		sb2.append("<tr align=right><td></td><td align=left>节目总计</td>");
		c.setTime(start);
		total = 0;
		for (int i=0; i<days; i++) {
			sb2.append("<td>").append(uvisit_day[i]).append("</td>");
			total += uvisit_day[i];
		}
		sb2.append("<td>").append(mnf.format(total)).append("</td>");
		sb2.append("<td>").append(mnf.format(total/days)).append("</td>");
		sb2.append("</tr>");
		sb2.append("</table>");	

		System.out.println("#ben# 4");
		out.println(sb.toString());
		out.println("\n\n");
		out.println(sb2.toString());
	}
	catch (Exception e) {
		e.printStackTrace();
	}
%>
