<%@ page language="java" import="java.util.*,com.wct.report.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.report.*,dbo.*"%>
<%! 
	public class Item {
		String id = null;
		int count = 0;
		Item(String id, int count) {
			this.id = id;
			this.count = count;
		}
		String getId() {
			return this.id;
		}
		public int getCount() {
			return this.count;
		}
	} 
%>
<%
	HttpParams hp = new HttpParams(request);
	int type=hp.getInt("type", 3);
	int end_type = type + 10;
	int max = hp.getInt("max", 10);
	int print_sum = hp.getInt("sum", 0);
	String queryby = hp.getStr("queryby", "vendor");
	String queryGetter = "getVendorName";
	if (queryby.equals("chip"))
		queryGetter = "getChipType";
	else if (queryby.equals("ver"))
		queryGetter = "getVer";
		
	ReportDeviceActivateMgr rdamgr = new ReportDeviceActivateMgr(0);
	rdamgr.setSource("db152");
	
	SimpleDateFormat sdf = null;
	SimpleDateFormat sdf_week_aux = new SimpleDateFormat("MM-dd");
	String display = "";
	if (type==ReportDevice.TYPE_HOUR) {
   		sdf = new SimpleDateFormat("yyyy-MM-dd HH");
   	} else if (type==ReportDevice.TYPE_DAY) {
   		sdf = new SimpleDateFormat("yyyy-MM-dd");
   	} else if (type==ReportDevice.TYPE_WEEK) {
   		sdf = new SimpleDateFormat("yyyy-MM-dd");   		
   	} else if (type==ReportDevice.TYPE_MONTH) {
   		sdf = new SimpleDateFormat("yyyy-MM");
   	} else {
   		System.out.println("unknown process type: " + type);
   		System.exit(0);
   	}

	int start = 0;
	Map<Date, Map<String, List<ReportDeviceActivate>>> renderMap = 
		new LinkedHashMap<Date, Map<String, List<ReportDeviceActivate>>>();
	Map<String, Integer> getterMap = new HashMap<String, Integer>();
		
	VendorNameConverter nc = new VendorNameConverter(queryby);
	while (true) {
		List<ReportDeviceActivate> rds1 = rdamgr.
			retrieveList("id>" + start + " and type=" + type, "order by id asc limit 1");
		if (rds1.size()==0) {
			break;
		}
		int beginId = rds1.get(0).getId();
		Date d1 = rds1.get(0).getCtime();
		List<ReportDeviceActivate> rds2 = rdamgr.
			retrieveList("id>" + beginId + " and type=" + end_type, "order by id asc limit 1");
		if (rds2.size()==0) {
			break;
		}
		int endId = rds2.get(0).getId();
		start = endId;
		
		List<ReportDeviceActivate> rds = rdamgr.
			retrieveList("id>=" + beginId + " and id<" + endId + " and type=" + type, "");
		
		Map<String, List<ReportDeviceActivate>> rdsMap = null;
		if (queryby.equals("vendor"))
			rdsMap = new SortingMap(rds).doSortA(queryGetter, nc);
		else
			rdsMap = new SortingMap(rds).doSortA(queryGetter);
		renderMap.put(d1, rdsMap);
		
		Iterator<String> iter = rdsMap.keySet().iterator();
		while (iter.hasNext()) {
			String id = iter.next();
			Integer ii = getterMap.get(id);
			if (ii==null) 
				ii = new Integer(0);
			List<ReportDeviceActivate> rds3 = rdsMap.get(id);
			int c = 0;
			for (int i=0; rds3!=null && i<rds3.size(); i++) {
				c += rds3.get(i).getCount();
			}
			getterMap.put(id, new Integer(ii.intValue()+c));
		}
	}
	
	StringBuffer sb = new StringBuffer();
	sb.append("<table border=1>");
	sb.append("<tr><td></td>");
	
	List<Item> items = new ArrayList<Item>();
	Iterator<String> iter2 = getterMap.keySet().iterator();
	while (iter2.hasNext()) {
		String id = iter2.next();
		Integer ii = getterMap.get(id);
		items.add(new Item(id, ii.intValue()));
	}
	items = new Sorter(items).sortDescending("getCount", null);
	for (int i=0; i<max && i<items.size(); i++) {
		Item item = items.get(i);
		sb.append("<td>").append(item.getId()).append("</td>");
	}
	if (items.size()>max) {
		sb.append("<td>其他</td>");
	}

	sb.append("<td>Subtotal</td></tr>");
	Calendar c = Calendar.getInstance();
	int[] totals = new int[getterMap.size()+1];
	Iterator<Date> iter = renderMap.keySet().iterator();
	// rendarMap 有每一天（周，月），某个 queryby 的 list
	while (iter.hasNext()) {
		Date d = iter.next();
		Map<String, List<ReportDeviceActivate>> rdsMap = renderMap.get(d);
		sb.append("<tr>");
		sb.append("<td>").append(sdf.format(d));
		if (type==ReportDevice.TYPE_WEEK) {
			c.setTime(d);
			c.add(Calendar.DATE, 6);
			sb.append(" to ").append(sdf_week_aux.format(c.getTime()));
		}
		sb.append("</td>");
		iter2 = getterMap.keySet().iterator();
		int subtotal = 0;
		int other = 0;
		int i=0;
		for (; i<items.size(); i++) {
			Item item = items.get(i);
			List<ReportDeviceActivate> rds2 = rdsMap.get(item.getId());
			int round = 0;
			for (int j=0; rds2!=null && j<rds2.size(); j++) {
				ReportDeviceActivate rd = rds2.get(j);
				round += rd.getCount();
			}
			
			int display_num = 0;
			if (print_sum==1) {
			    totals[i] += round;
				display_num = totals[i];
			    subtotal += totals[i];
			}
			else {
			    subtotal += round;
			    totals[i] += round;
				display_num = round;
			}
			
			if (i<max) {
				sb.append("<td>").append(display_num).append("</td>");
			}
			else
				other += display_num;

		}
		if (items.size()>max) {
			sb.append("<td>").append(other).append("</td>");
		}
		sb.append("<td>").append(subtotal).append("</td>");
		sb.append("</tr>");
		totals[i] += subtotal;
	}
	sb.append("<tr>");
	sb.append("<td align=right>总计</td>");
	int other_total = 0;
	for (int i=0; i<totals.length-1; i++) {
		if (i<max) {
			sb.append("<td>").append(totals[i]).append("</td>");
		}
		else
			other_total += totals[i];
	}
	if (max<items.size()) {
		sb.append("<td>").append(other_total).append("</td>");
	}
	sb.append("<td>").append(totals[totals.length-1]).append("</td>");
	sb.append("</tr>");
	sb.append("</table>");
	out.println(sb.toString());
%>
