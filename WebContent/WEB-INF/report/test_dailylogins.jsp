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
	"select times, sum(count) as count, DATE_FORMAT(ctime,'%Y-%m-%d') as cday " + 
	"from r3_daily_logins ";
	if (city.length()>0)
		q += " where city='" + city + "'";
	q +=" group by cday, times order by cday";  	
	
	StringBuffer sb = new StringBuffer();
	sb.append("<table border=1>");
	sb.append("<tr><td>日期</td>");
	for (int i=1; i<=10; i++) {
		sb.append("<td>").append(i).append("次").append("</td>");
	}
	sb.append("</tr>");
	List<Map<String, Object>> rs = dlmgr.retrieveListMap(q);
	
	String[] keys1 = {"cday"};
	Map<String, List<Map<String, Object>>> rs1Map = new SortingMap(rs).doSortKeyA(keys1);
	String[] keys2 = {"cday","times"};
	Map<String, Map<String, Object>> rs2Map = new SortingMap(rs).doSortKeySingleton(keys2); 
	
	
	Calendar c = Calendar.getInstance();
	Iterator<String> iter = rs1Map.keySet().iterator();
	while (iter.hasNext()) {
		String dstr = iter.next();
		Date d = sdf.parse(dstr);
		c.setTime(d);
		if (c.get(Calendar.MONTH)>2)
			continue;
		sb.append("<tr>");
		sb.append("<td>").append(dstr).append("&nbsp;").
			append(getWeekday(c.get(Calendar.DAY_OF_WEEK))).append("</td>");
		for (int i=1; i<=10; i++) {
			String key = dstr + "#" + i;
			Map<String, Object> m = rs2Map.get(key);
			sb.append("<td>").append((m==null)?"":m.get("count")).append("</td>");			
		}
		sb.append("</tr>");
	}
	sb.append("</table>");
	out.println(sb.toString());
%>
