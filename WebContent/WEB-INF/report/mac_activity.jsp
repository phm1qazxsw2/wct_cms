<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.report.*,dbo.*"%>
<%!
	public class Item {
		Date d = null;
		String title = null;
		String color = null;
		public Item(Date d, String title, String color) {
			this.d = d;
			this.title = title;
			this.color = color;
		}
		public Date getTime() { return d; }
		public String getTitle() { return title; }
		public String getColor() { return color; }
	}
%>
<%
	HttpParams hp = new HttpParams(request);
	String mac = hp.getStr("mac", "00-90-E6-2C-32-FC");
	boolean isTest = hp.getInt("test", 0)==1;
	
	TChallengerMgr tcmgr = new TChallengerMgr(0);
	DeviceInfoMgr dimgr = new DeviceInfoMgr(0);
	MacInfoMgr mimgr = new MacInfoMgr(0);
	ActivateFailMgr failmgr = new ActivateFailMgr(0);
	VendorMgr vmgr = new VendorMgr(0);
	if (isTest) {
		tcmgr.setSource("db27test");
		dimgr.setSource("db27test");
		mimgr.setSource("db27test");
		failmgr.setSource("db27test");
		vmgr.setSource("db27test");
	}
	
	IpMap ipMap = new IpMap();

	List<Item> items = new ArrayList<Item>(); 	
	List<TChallenger> logins = tcmgr.retrieveList("device_mac='" + mac + "'", "");
	String ver = null;
	for (int i=0; i<logins.size(); i++) {
		TChallenger c = logins.get(i);
		if (ver!=null && c.getSoftware_code()!=null && !c.getSoftware_code().equals(ver))
			items.add(new Item(c.getChallenger_times_date(), "升级 ("+ver+"->"+c.getSoftware_code()+")", "yellow"));
		IpInfo ip = null;
		try { ip=ipMap.findIpInfo(c.getDevice_ip()); } catch (Exception e) {}
		String ipinfo = c.getDevice_ip() + ((ip==null)?"":(" (" + ip.getProvince() + ")"));
		items.add(new Item(c.getChallenger_times_date(), "鉴权 from " + ipinfo, "lightgreen"));
		ver = c.getSoftware_code();
	}
	
	
	DeviceInfo di = dimgr.find("device_mac='" + mac + "'");
	if (di!=null) {
		items.add(new Item(di.getEnabled_date(), "激活成功", "green"));
	}
	
	MacInfo mi = mimgr.find("device_mac='" + mac + "'");
	List<ActivateFail> fails = failmgr.retrieveList("device_mac='" + mac + "'", "");
	for (int i=0; i<fails.size(); i++) {
		ActivateFail f = fails.get(i);
		IpInfo ip = null;
		try { ip=ipMap.findIpInfo(f.getDevice_ip()); } catch (Exception e) {}
		MacInfo mi2 = null;
		if (f.getInfo().indexOf("已被使用")>=0) {
			mi2 = mimgr.find("device_mac='" + f.getDevice_mac() + "'");
		}
		String ipinfo = f.getDevice_ip() + ((ip==null)?"":(" (" + ip.getProvince() + ")"));
		items.add(new Item(f.getActive_times_date(), "激活失败 from " + ipinfo + 
			"<br>" + f.getInfo() + 
			"<br>" + f.getChip_code()+ "(本次激活之 chip_code)" + 
			((mi2!=null)?("<br>" + mi2.getChip_code() + "(" + mi2.getDevice_mac() + " 之前已绑定 "):"")
			,"red")); 
	}
	
	/*
	HeartBeatMgr hmgr = new HeartBeatMgr(0);
	hmgr.setSource("db150");
	
	List<HeartBeat> hbs = hmgr.retrieveList("device_mac='" + mac + "'", "order by id desc limit 300");
	for (int i=0; i<hbs.size(); i++) {
		HeartBeat hb = hbs.get(i);
		IpInfo ip = null;
		try { ip=ipMap.findIpInfo(hb.getDevice_ip()); } catch (Exception e) {}
		String ipinfo = hb.getDevice_ip() + ((ip==null)?"":(" (" + ip.getProvince() + ")"));
		items.add(new Item(hb.getActive_times_date(), "心跳 from " + ipinfo, "white"));
	}
	*/

	TmpHour5Mgr t3mgr = new TmpHour5Mgr(0);
	t3mgr.setSource("db152");
	List<TmpHour5> ths = t3mgr.retrieveList("device_mac='" + mac + "'", "order by id desc limit 300");
	for (int i=0; i<ths.size(); i++) {
		TmpHour5 tmp = ths.get(i);
		items.add(new Item(tmp.getCtime(), "鉴权统计 from " + tmp.getDevice_ip() + 
			":" + tmp.getProvince()+":" + tmp.getCity() + ":" + tmp.getVendor_code(), "lightblue"));
	}	
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	items = new Sorter(items).sortDescending("getTime", null);
	StringBuffer sb = new StringBuffer();
	sb.append("<table border=1>");
	for (int i=0; i<items.size(); i++) {
		Item item = items.get(i);
		sb.append("<tr bgcolor=\""+item.getColor()+"\">");
		String dstr = "";
		try { dstr = sdf.format(item.getTime()); } catch (Exception e) {}
		sb.append("<td>").append(dstr).append("</td>");
		sb.append("<td>").append(item.getTitle()).append("</td>");
		sb.append("</tr>");
	}
	sb.append("</table>");
	

	out.println(mac + " 详细信息:<br/>");
	out.println("chip_code=" + ((mi!=null)?mi.getChip_code():"找不到"));
	out.println("<br>chip_type=" + ((mi!=null)?mi.getChipType():"找不到"));
	out.println("<br>software_code=" + ((di!=null)?di.getSoftware_code():""));
	out.println("<br>vendor_code=" + ((di!=null)?di.getVendor_code():""));
	Vendor v = vmgr.find("vid='" + ((mi!=null)?mi.getVid():"xxx") + "'");
	out.println("<br>vendor=" + ((v==null)?"vid 没查到":v.getName()));
	out.println(sb.toString());
%>
