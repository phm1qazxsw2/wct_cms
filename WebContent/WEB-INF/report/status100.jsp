<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.report.*,dbo.*"%>
<%
	HttpParams hp = new HttpParams(request);		
	String ver = hp.getStr("ver", "05.43");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd HH:mm");
	
	try {	
		ALi100Mgr amgr = new ALi100Mgr(0);
		List<ALi100> alis = amgr.retrieveList("","order by id asc");
		String macs = new ConsId(alis).makeStringIds("getDevice_mac");
		List<DeviceInfo> devices = new DeviceInfoMgr(0).retrieveList(
			"device_mac in (" + macs + ")", "");
		Map<String, DeviceInfo> deviceMap = new SortingMap(devices).
			doSortSingleton("getDevice_mac");
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -7);
		TChallenger t = new TChallengerMgr(0).retrieveList("challenger_times_date>'"
			    	+ sdf.format(c.getTime()) + "'", "limit 1").get(0);
		System.out.println("##1 t.id=" + t.getId());
		List<TChallenger> tinfos = new TChallengerMgr(0).retrieveList("id>" + t.getId()
			+ " and device_mac in (" + macs + ")", "order by id desc");
		System.out.println("##2");
		Map<String, List<TChallenger>> tinfoMap = 
			new SortingMap(tinfos).doSortA("getDevice_mac");
			
		UpgradeOkMgr uokmgr = new UpgradeOkMgr(0);
		uokmgr.setSource("db150");
		UpgradeFailMgr ufailmgr = new UpgradeFailMgr(0);
		ufailmgr.setSource("db150");
		//GY8188Mgr gymgr = new GY8188Mgr(0);
		//gymgr.setSource("db152");
			
		Map<String, List<UpgradeOk>> upokMap = new SortingMap(uokmgr.
			retrieveList("mac in (" + macs.toLowerCase() + ")", "order by id desc")).doSortA("getUpper_mac");
		System.out.println("##3");
		Map<String, List<UpgradeFail>> upfailMap = new SortingMap(ufailmgr.
			retrieveList("mac in (" + macs.toLowerCase() + ")", "order by id desc")).doSortA("getUpper_mac");
		System.out.println("##4 upokMap.size=" + upokMap.size() + " upfailMap=" + upfailMap.size());
		
		String chipIds = new ConsId(devices).makeStringIds("getChip_code");
		//Map<String, GY8188> gyMap = new SortingMap(gymgr.retrieveList("","")).
		//	doSortSingleton("getChipId");
		
		StringBuffer sb = new StringBuffer();
		sb.append("*上次升级成功记录有些没记到，和设备版本不一致，正在调查<br>");
		sb.append("<table border=1>");
		sb.append("<tr><td></td><td></td><td>领用人</td><td>版本</td><td>7天开机次数</td>");
		sb.append("<td>上次升级成功</td><td>上次升级失败</td></tr>");
		for (int i=0; i<alis.size(); i++) {
			ALi100 a = alis.get(i);
			DeviceInfo d = deviceMap.get(a.getDevice_mac());
			List<TChallenger> tcs = tinfoMap.get(a.getDevice_mac());
			List<UpgradeOk> uoks = upokMap.get(a.getDevice_mac());
			List<UpgradeFail> ufails = upfailMap.get(a.getDevice_mac());
			boolean upgraded = (d!=null && d.getSoftware_code()!=null)?
				(d.getSoftware_code().indexOf(ver)>=0):false;
			
			sb.append("<tr>");
			sb.append("<td>").append(i+1).append("</td>");
			sb.append("<td nowrap>");
			sb.append(a.getDevice_mac());
			sb.append("</td>");
			sb.append("<td nowrap>");
			sb.append(a.getName());
			sb.append("</td>");
			sb.append("<td nowrap").append((upgraded)?"":" bgcolor=\"pink\"").append(">");
			sb.append((d==null)?"":d.getSoftware_code());
			sb.append("</td>");			
			sb.append("<td nowrap>");
			sb.append((tcs==null)?0:tcs.size());
			if (tcs!=null) {
				sb.append(" &nbsp;(");
				sb.append(sdf2.format(tcs.get(0).getChallenger_times_date()));
				sb.append(")");
			}
			sb.append("</td>");			
			sb.append("<td nowrap>");
			if (uoks!=null) {
				sb.append(uoks.get(0).getCurSoftware());
				sb.append("->");
				sb.append(uoks.get(0).getToSoftware());
				sb.append("(");
				sb.append(sdf2.format(uoks.get(0).getCreateDate()));
				sb.append(")");
			}
			sb.append("</td>");
			sb.append("<td nowrap>");
			if (ufails!=null) {
				sb.append(ufails.get(0).getCurSoftware());
				sb.append("->");
				sb.append(ufails.get(0).getToSoftware());
				sb.append("(");
				sb.append(sdf2.format(ufails.get(0).getCreateDate()));
				sb.append(")");
			}
			sb.append("</td>");			
			sb.append("</tr>");
		}
		sb.append("</table>");
		out.println(sb.toString());
	}
	catch (Exception e) {
		e.printStackTrace();
	}
%>
