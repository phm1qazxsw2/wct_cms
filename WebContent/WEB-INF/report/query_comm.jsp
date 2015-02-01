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
	String comm = hp.getStr("comm", "联通");
		
	ReportDeviceMgr rdmgr = new ReportDeviceMgr(0);
	rdmgr.setSource("db152");
	
	int start = 0;
	Map<Date, Map<String, List<ReportDevice>>> renderMap = 
		new LinkedHashMap<Date, Map<String, List<ReportDevice>>>();
	Map<String, Integer> getterMap = new HashMap<String, Integer>();
		
	int type = ReportDevice.TYPE_WEEK;
	int end_type = ReportDevice.TYPE_WEEK_FINISH;
	String queryGetter = "getProvince";
	int max = 30000;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
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
		
		List<ReportDevice> rds = rdmgr.
			retrieveList("id>=" + beginId + " and id<" + endId + " and type=" + type
				+ " and communications like '%" + comm + "%'", "");
		
		Map<String, List<ReportDevice>> rdsMap = new SortingMap(rds).doSortA(queryGetter);
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
		sb.append("<td>").append(sdf.format(d)).append("</td>");
		int subtotal = 0;
		int other = 0;
		int logins = 0;
		for (int i=0; i<items.size(); i++) {
			Item item = items.get(i);
			List<ReportDevice> rds2 = rdsMap.get(item.getId());
			int round = 0;
			for (int j=0; rds2!=null && j<rds2.size(); j++) {
				ReportDevice rd = rds2.get(j);
				round += rd.getCount();
				logins += rd.getLogins();
			}
			if (i<max) {
				sb.append("<td>").append((round==0)?"":(round+"")).append("</td>");
			}
			else
				other += round;
		    subtotal += round;
		}
		if (items.size()>max) {
			sb.append("<td>").append(other).append("</td>");
		}
		sb.append("<td>").append((subtotal==0)?"":(subtotal+"")).append("</td>");
		sb.append("<td>").append((logins==0)?"":(logins+"")).append("</td>");
		sb.append("<td>").append((subtotal>0)?(logins/subtotal+""):"").append("</td>");
		sb.append("</tr>");
	}
	sb.append("</tr>");
	sb.append("</table>");
	out.println("查询运营商：" + comm);
	out.println(sb.toString());
%>
