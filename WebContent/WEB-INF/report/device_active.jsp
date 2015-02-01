<%@ page language="java" import="java.util.*"
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
	String queryby = hp.getStr("queryby", "vendor");
	String city = hp.getStr("city", "");
	String province = hp.getStr("province", "");
	String queryGetter = "getVendorName";
	int max = hp.getInt("max", 10);
	if (queryby.equals("chip"))
		queryGetter = "getChipType";
	if (queryby.equals("area"))
		queryGetter = "getProvince";
	if (queryby.equals("comm"))
		queryGetter = "getCommunications";
		
		
		
	ReportDeviceMgr rdmgr = new ReportDeviceMgr(0);
	rdmgr.setSource("db152");
	
	SimpleDateFormat sdf = null;
	SimpleDateFormat sdf_week_aux = new SimpleDateFormat("MM-dd");
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
	Map<Date, Map<String, List<ReportDevice>>> renderMap = 
		new LinkedHashMap<Date, Map<String, List<ReportDevice>>>();
	Map<String, Integer> getterMap = new HashMap<String, Integer>();
		
	VendorNameConverter nc = new VendorNameConverter(queryby);
	while (true) {
		List<ReportDevice> rds1 = rdmgr.
			retrieveList("id>" + start + " and type=" + type, "order by id asc limit 1");
		if (rds1.size()==0) {
			break;
		}
		int beginId = rds1.get(0).getId();
		Date d1 = rds1.get(0).getCtime();
		List<ReportDevice> rds2 = rdmgr.
			retrieveList("id>" + beginId + " and type=" + end_type, "order by id asc limit 1");
		if (rds2.size()==0) {
			break;
		}
		int endId = rds2.get(0).getId();
		start = endId;
		
		String q = "id>=" + beginId + " and id<" + endId + " and type=" + type;
		if (city.length()>0)
			q += " and city='" + city + "'";
		if (province.length()>0)
			q += " and province='" + province + "'";
		List<ReportDevice> rds = rdmgr.
			retrieveList(q, "");
		
		Map<String, List<ReportDevice>> rdsMap = null;
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
			List<ReportDevice> rds3 = rdsMap.get(id);
			int c = 0;
			for (int i=0; rds3!=null && i<rds3.size(); i++) {
				c += rds3.get(i).getCount();
			}
			getterMap.put(id, new Integer(ii.intValue()+c));
		}
	}
	
	List<Item> items = new ArrayList<Item>();
	Iterator<String> iter2 = getterMap.keySet().iterator();
	while (iter2.hasNext()) {
		String id = iter2.next();
		Integer ii = getterMap.get(id);
		items.add(new Item(id, ii.intValue())); 
	}
	items = new Sorter(items).sortDescending("getCount", null);
	
	Calendar c = Calendar.getInstance();
	StringBuffer sb = new StringBuffer();
	sb.append("<table border=1>");
	sb.append("<tr><td></td>");
	for (int i=0; i<max && i<items.size(); i++) {
		Item item = items.get(i);
		sb.append("<td>").append(item.getId()).append("</td>");
	}
	if (items.size()>max) {
		sb.append("<td>其他</td>");
	}
	sb.append("<td>Unique Mac</td><td>开机次数</td><td>平均</td></tr>");
	
	Iterator<Date> iter = renderMap.keySet().iterator();
	while (iter.hasNext()) {
		Date d = iter.next();
		Map<String, List<ReportDevice>> rdsMap = renderMap.get(d);
		sb.append("<tr>");
		sb.append("<td>").append(sdf.format(d));
		if (type==ReportDevice.TYPE_WEEK) {
			c.setTime(d);
			c.add(Calendar.DATE, 6);
			sb.append(" to ").append(sdf_week_aux.format(c.getTime()));
		}
		sb.append("</td>");
		int subtotal = 0;
		int other = 0;
		int logins = 0;
		StringBuffer detail_sb = new StringBuffer();
		for (int i=0; i<items.size(); i++) {
			Item item = items.get(i);
			List<ReportDevice> rds2 = rdsMap.get(item.getId());
			Map<String, List<ReportDevice>> tmpMap = new SortingMap(rds2).doSortA("getVendor_code");
			int round = 0;
			for (int j=0; rds2!=null && j<rds2.size(); j++) {
				ReportDevice rd = rds2.get(j);
				round += rd.getCount();
				logins += rd.getLogins();
			}
			if (i<max) {
				sb.append("<td>").append(round);				
				sb.append("</td>");
			}
			else
				other += round;
		    subtotal += round;
		}
		if (items.size()>max) {
			sb.append("<td>").append(other).append("</td>");
		}
		sb.append("<td>").append(subtotal).append("</td>");
		sb.append("<td>").append(logins).append("</td>");
		sb.append("<td>").append(logins/subtotal).append("</td>");
		sb.append("</tr>");
	}
	sb.append("</tr>");
	sb.append("</table>");
	out.println(sb.toString());
%>
