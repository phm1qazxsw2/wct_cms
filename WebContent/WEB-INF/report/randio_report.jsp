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
		
		List<MovieInfo> landi_infos = mmgr.retrieveList("type='landi'","order by id");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -7);
		
		Date start = hp.getDate("d1", c.getTime());
		Date end = hp.getDate("d2", new Date());
		
		c.setTime(end);
		c.add(Calendar.DATE, 1);
		Date onedayafter = c.getTime();
		
		long t1 = new Date().getTime();
		System.out.println("#landi# t1");
		ArrayList<VideoLog> logs = vmgr.retrieveSQLList("select videoUrl,count(*) as pageview," +
			"COUNT(DISTINCT ip) as visit, DATE_FORMAT(createTime,'%Y-%m-%d') as createDate " +
			"from vod_video_log where createTime>='"+sdf.format(start)+
			"' and createTime<'"+sdf.format(onedayafter)+"' and videoFeature='landi'" +
			" group by videoUrl,createDate order by videoUrl,createDate");
		long t2 = new Date().getTime();
		System.out.println("#landi# time=" + (t2-t1));
		Map<String, VideoLog> logMap = new SortingMap(logs).doSortSingleton("getKey");
			
		StringBuffer sb = new StringBuffer();
		sb.append("蓝迪统计数字<br>");		
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
		for (int i=0; i<landi_infos.size(); i++) {
			MovieInfo mi = landi_infos.get(i);
			c.setTime(start);
			sb.append("<tr align=right>");
			sb.append("<td>").append(i+1).append("</td>");
			sb.append("<td align=left>").append(mi.getName()).append("</td>"); 
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
		
		RandioQRMgr rmgr = new RandioQRMgr(0);
		rmgr.setSource("db152");
		
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		IpMap ipmap = new IpMap();
		List<RandioQR> qrs = rmgr.retrieveList("","");
		String ipstr = new ConsId(qrs).makeIds("getIp");
		Map<String, IpInfo> ipMap = ipmap.get(ipstr, ",");		
		StringBuffer sb2 = new StringBuffer();
		sb2.append("<table border=1>");
		Date d = null;
		for (int i=0; i<qrs.size(); i++) {
			RandioQR r = qrs.get(i);
			IpInfo ip = ipMap.get(r.getIp());
			sb2.append("<tr");
			if (d==null || r.getCtime().getDay()!=d.getDay())
				sb2.append(" bgcolor=\"pink\"");
			sb2.append(">");
			sb2.append("<td>").append(i+1).append("</td>");
			sb2.append("<td>").append(r.getIp()).append("</td>");
			sb2.append("<td>").append((ip!=null && ip.getProvince().length()>0)?(ip.getProvince()+"/"+ip.getCity()):"").append("</td>");
			sb2.append("<td>").append(sdf2.format(r.getCtime())).append("</td>");
			sb2.append("</tr>");
			d = r.getCtime();
		}
		sb2.append("</table>"); 

		out.println(sb.toString());
		out.println("<br><br>QR scan records<br>");
		out.println(sb2.toString());
	}
	catch (Exception e) {
		e.printStackTrace();
	}
%>
