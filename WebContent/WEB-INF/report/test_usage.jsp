<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.report.*,dbo.*"%>
<%!
	public String getWeekday(int w) {
		switch (w) {
		case 1: return "日"; 
		case 2: return "一"; 
		case 3: return "二"; 
		case 4: return "三"; 
		case 5: return "四"; 
		case 6: return "五"; 
		case 7: return "六"; 
		}
		return "";
	}
%>
<%
	HttpParams hp = new HttpParams(request);
	String city = hp.getStr("city", "");
	DailyLoginsMgr dlmgr = new DailyLoginsMgr(0);
	dlmgr.setSource("db152");
	
	// 列出2014年第一季度及1、2、3月，户均日均的开机频次数据，包括：
    // 户均开机频次在1次、2次、3次、4次、5次及以上的分布

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	String q = 
	"select day,hours,sum(count) as count from usage_day_area where count>10 ";
	
	if (city.length()>0)
		q += " and city='" + city + "'"; 
	
	q += " group by day, hours order by day desc, hours desc";
    
    List<Map<String,Object>> rs = dlmgr.retrieveListMap(q);
    Date d1 = null;
    Date d2 = null;
    int maxhour = 0;
    for (int i=0; i<rs.size(); i++) {
    	Map<String,Object> m = rs.get(i);
		Date tmp1 = sdf.parse((String)m.get("day"));
		if (d1==null || tmp1.compareTo(d1)<0)
			d1 = tmp1;
		if (d2==null || tmp1.compareTo(d2)>0)
			d2 = tmp1;
		int hours = (int)((Long)m.get("hours")).longValue();
		if (hours>maxhour)
			maxhour = hours;
    } 
    
    String[] attrs = { "day", "hours" };
	Map<String, Map<String,Object>> rsMap = new SortingMap(rs).doSortKeySingleton(attrs);    
    
    
	StringBuffer sb = new StringBuffer();
	sb.append("<table border=1>");
	sb.append("<tr><td>日期/小时数</td>");
	for (int i=1; i<=maxhour; i++) {
		sb.append("<td>").append(i).append("</td>");
	}
	sb.append("</tr>");
	
    Calendar c = Calendar.getInstance();
    c.setTime(d1);
	for (Date d = c.getTime(); d.compareTo(d2)<=0; c.add(Calendar.DATE, 1),d=c.getTime()) {
		sb.append("<tr>");
		sb.append("<td>").append(sdf.format(d)).append("&nbsp;").
			append(getWeekday(c.get(Calendar.DAY_OF_WEEK))).append("</td>");
		for (int i=1; i<=maxhour; i++) {
			Map<String,Object> m = rsMap.get(sdf.format(d)+"#"+i);
			sb.append("<td>").append((m!=null)?m.get("count"):"").append("</td>");
		}
		sb.append("</tr>");
	}
	sb.append("</table>");
	out.println(sb.toString());
%>
