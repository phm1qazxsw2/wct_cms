<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.report.*,dbo.*"%>
<%!
	class counter {
		int ok_num = 0;
		int extra_ok_num = 0;
		int fail_num_full = 0;
		int fail_num_incr = 0;
		int fail_num_unknown = 0;
		
		int all_full = 0, all_incr=0, all_unknown = 0;
		int s1_full = 0, s1_incr=0, s1_unknown = 0;
		int s2_full = 0, s2_incr=0, s2_unknown = 0;
		int s3_full = 0, s3_incr=0, s3_unknown = 0;
		int sn_full = 0, sn_incr=0, sn_unknown = 0;
		
		String fullmd5 = null;
		String incrmd5 = null;
		String incr_above = null;
		DecimalFormat mnf = new DecimalFormat("##.##");
		
		counter(List<UpgradeOk> oks, String fullmd5, String incrmd5, String incr_above) {
			ok_num = oks.size();
			this.fullmd5 = fullmd5;
			this.incrmd5 = incrmd5;
			this.incr_above = incr_above;
			for (int i=0; i<oks.size(); i++) {
				UpgradeOk ok = oks.get(i);
				if (fullmd5.indexOf(ok.getMd5())>=0)
					all_full ++;
				else if (incrmd5.indexOf(ok.getMd5())>=0)
					all_incr ++;
				else
					all_unknown ++;
			}
		}
		
		void setok(List<UpgradeFail> fs, UpgradeOk ok) {
			if (fs.size()==1) {
				if (ok!=null && fullmd5.indexOf(ok.getMd5())>=0) {
					s1_full ++;
					if (all_full>0)
						all_full --;
				}
				else if (ok!=null && incrmd5.indexOf(ok.getMd5())>=0) {	
					s1_incr ++;
					if (all_incr>0)
						all_incr --;
				}
				else if (incr_above!=null) {
				    if (fs.get(0).getCurSoftware().compareTo(incr_above)>0) {
				    	s1_incr ++;
				    }
				    else {
				    	s1_full ++;
				    }
				} else
					s1_unknown ++;
			}
			else if (fs.size()==2) {
				if (ok!=null && fullmd5.indexOf(ok.getMd5())>=0) {
					s2_full ++;
					if (all_full>0)
						all_full --;
				}
				else if (ok!=null && incrmd5.indexOf(ok.getMd5())>=0) {	
					s2_incr ++;
					if (all_incr>0)
						all_incr --;
				}
				else if (incr_above!=null) {
				    if (fs.get(0).getCurSoftware().compareTo(incr_above)>0) {
				    	s2_incr ++;
				    }
				    else {
				    	s2_full ++;
				    }
				} else
					s2_unknown ++;
			}
			else if (fs.size()==3) {
				if (ok!=null && fullmd5.indexOf(ok.getMd5())>=0) {
					s3_full ++;
					if (all_incr>0)
						all_incr --;
				}
				else if (ok!=null && incrmd5.indexOf(ok.getMd5())>=0)	{
					s3_incr ++;
					if (all_full>0)
						all_full --;
				}
				else if (incr_above!=null) {
				    if (fs.get(0).getCurSoftware().compareTo(incr_above)>0) {
				    	s3_incr ++;
				    }
				    else {
				    	s3_full ++;
				    }
				} else
					s3_unknown ++;
			}
			else {
				if (ok!=null && fullmd5.indexOf(ok.getMd5())>=0) {
					sn_full ++;
					if (all_full>0)
						all_full --;
				}
				else if (ok!=null && incrmd5.indexOf(ok.getMd5())>=0) {	
					sn_incr ++;
					if (all_incr>0)
						all_incr --;
				}
				else if (incr_above!=null) {
				    if (fs.get(0).getCurSoftware().compareTo(incr_above)>0) {
				    	sn_incr ++;
				    }
				    else {
				    	sn_full ++;
				    }
				} else
					sn_unknown ++;
			}
		}
		void setok_makeup(List<UpgradeFail> fs) {
			setok(fs, null);
			extra_ok_num ++;
		}
		void addfail(List<UpgradeFail> fs) {
			if (incr_above!=null) {
			    if (fs.get(0).getCurSoftware().compareTo(incr_above)>0) {
			    	fail_num_incr ++;
			    }
			    else {
			    	fail_num_full ++;
			    }
			} else
				fail_num_unknown ++;
		}
		int total() {
			return ok_num + fail_num() + extra_ok_num;
		}
		
		int success0() {
			return ok_num + extra_ok_num - 
				(s1_full + s1_incr + s1_unknown 
				+ s2_full + s2_incr + s2_unknown
				+ s3_full + s3_incr + s3_unknown
				+ sn_full + sn_incr + sn_unknown);
		}
		String success0_percent() {
			if (total()==0)
				return "-";
			return mnf.format((float)100*(float)success0()/(float)total());
		}
		int success0_full() {
			return all_full;
		}
		String success0_full_percent() {
			if (full_total()==0)
				return "-";
			return mnf.format((float)100*(float)all_full/(float)full_total());
		}
		int success0_incr() {
			return all_incr;
		}
		String success0_incr_percent() {
			if (incr_total()==0)
				return "-";
			return mnf.format((float)100*(float)all_incr/(float)incr_total());
		}
		
		int success1() {
			return s1_full + s1_incr + s1_unknown;
		}
		String success1_percent() {
			if (total()==0)
				return "-";
			return mnf.format((float)100*(float)success1()/(float)total());
		}
		int full_total() {
			return success0_full() + success1_full() + success2_full() + success3_full() + successn_full()
				+ failnum_full();
		}
		int incr_total() {
			return success0_incr() + success1_incr() + success2_incr() + success3_incr() + successn_incr()
				+ failnum_incr();
		}
		int success1_full() {
			return s1_full;
		}
		String success1_full_percent() {
			if (full_total()==0)
				return "-";
			return mnf.format((float)100*(float)s1_full/(float)full_total());
		}
		int success1_incr() {
			return s1_incr;
		}
		String success1_incr_percent() {
			if (incr_total()==0)
				return "-";
			return mnf.format((float)100*(float)s1_incr/(float)incr_total());
		}
		int success1_unknown() {
			return s1_unknown;
		}
		
		int success2() {
			return s2_full + s2_incr + s2_unknown;
		}
		String success2_percent() {
			if (total()==0)
				return "-";
			return mnf.format((float)100*(float)success2()/(float)total());
		}
		int success2_full() {
			return s2_full;
		}
		String success2_full_percent() {
			if (full_total()==0)
				return "-";
			return mnf.format((float)100*(float)s2_full/(float)full_total());
		}
		int success2_incr() {
			return s2_incr;
		}
		String success2_incr_percent() {
			if (incr_total()==0)
				return "-";
			return mnf.format((float)100*(float)s2_incr/(float)incr_total());
		}
		int success2_unknown() {
			return s2_unknown;
		}
		
		int success3() {
			return s3_full + s3_incr + s3_unknown;
		}
		String success3_percent() {
			if (total()==0)
				return "-";
			return mnf.format((float)100*(float)success3()/(float)total());
		}
		int success3_full() {
			return s3_full;
		}
		String success3_full_percent() {
			if (full_total()==0)
				return "-";
			return mnf.format((float)100*(float)s3_full/(float)full_total());
		}
		int success3_incr() {
			return s3_incr;
		}
		String success3_incr_percent() {
			if (incr_total()==0)
				return "-";
			return mnf.format((float)100*(float)s3_incr/(float)incr_total());
		}
		int success3_unknown() {
			return s3_unknown;
		}
		
		int successn() {
			return sn_full + sn_incr + sn_unknown;
		}
		String successn_percent() {
			if (total()==0)
				return "-";
			return mnf.format((float)100*(float)successn()/(float)total());
		}
		int successn_full() {
			return sn_full;
		}
		String successn_full_percent() {
			if (full_total()==0)
				return "-";
			return mnf.format((float)100*(float)sn_full/(float)full_total());
		}
		int successn_incr() {
			return sn_incr;
		}
		String successn_incr_percent() {
			if (incr_total()==0)
				return "-";
			return mnf.format((float)100*(float)sn_incr/(float)incr_total());
		}
		int successn_unknown() {
			return sn_unknown;
		}
		
		int failnum_full() {
			return fail_num_full;
		}
		String failnum_full_percent() {
			if (full_total()==0)
				return "-";
			return mnf.format((float)100*(float)fail_num_full/(float)full_total());
		}
		int failnum_incr() {
			return fail_num_incr;
		}
		String failnum_incr_percent() {
			if (incr_total()==0)
				return "-";
			return mnf.format((float)100*(float)fail_num_incr/(float)incr_total());
		}
		int failnum_unknown() {
			return fail_num_unknown;
		}
		int fail_num() {
			return fail_num_full + fail_num_incr + fail_num_unknown;
		}
		String fail_percent() {
			if (total()==0)
				return "-";
			return mnf.format((float)100*(float)fail_num()/(float)total());
		}
	}
%>
<%
	HttpParams hp = new HttpParams(request);		
	Date d = hp.getDate("d", new Date());
	String ver = hp.getStr("ver", "05.42.L201");
	String fullmd5 = hp.getStr("fullmd5", "cd464c8598e6f11956e6e035c348e45c");
	String incrmd5 = hp.getStr("incrmd5", "c14d2ff8338de36c3ea21ed5546aa3df");
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdf2 = new SimpleDateFormat("MM-dd HH:mm");
	String incr_above = hp.getStr("incr_above", null);
	DecimalFormat mnf = new DecimalFormat("##.##");
	
	int tran_id = 0;
		
	try {
		UpgradeFailMgr fmgr = new UpgradeFailMgr(tran_id);
		fmgr.setSource("main");	
		UpgradeOkMgr okmgr = new UpgradeOkMgr(tran_id);
		okmgr.setSource("main");
		DeviceInfoMgr dimgr = new DeviceInfoMgr(tran_id);
		
		long t0 = new Date().getTime();
		List<UpgradeFail> fails = fmgr.retrieveList("createDate>'" + 
			sdf.format(d) + "' and toSoftware like '"+ver+"%'", "order by id desc");
			
		long t1 = new Date().getTime();
		
		List<UpgradeOk> oks = okmgr.retrieveList("createDate>'" + sdf.format(d) + 
			"' and toSoftware like '"+ver+"%'", "");
		long t2 = new Date().getTime();
		
		Map<String, UpgradeOk> oksMap = new SortingMap(oks).doSortSingleton("getMac");			
		Map<String, List<UpgradeFail>> failMap = new SortingMap(fails).doSortA("getMac");			
		
		StringBuffer sb = new StringBuffer();
		sb.append("<table border=1>");
		sb.append("<tr align=center><td>Mac</td><td nowrap>失败次数</td>");
		sb.append("<td>From</td><td width=\"40%\"></td></tr>");
		
		int fail_num = 0;
		counter c = new counter(oks, fullmd5, incrmd5, incr_above);
		
		Iterator<String> iter = failMap.keySet().iterator();
		while (iter.hasNext()) {
			String fmac = iter.next();
			UpgradeOk o = oksMap.get(fmac);
			List<UpgradeFail> fs = failMap.get(fmac);
			boolean success = false; 
			boolean makeup_success = false;
			if (o!=null) {
				success = true;
			}
			else {
				DeviceInfo di = dimgr.find("device_mac='" + fmac + "'");
				if (di!=null) {
					if (di.getSoftware_code().indexOf(ver)>=0) {
						makeup_success = true;
					}		
				}
			}			
			sb.append("<tr align=center ");
			sb.append((success)?"":(makeup_success)?"bgcolor=\"yellow\"":"bgcolor=\"pink\"");
			sb.append("><td nowrap>");
			sb.append(fmac).append("</td><td>")
				.append(fs.size()).append("</td>");
			sb.append("<td>");
			sb.append(fs.get(0).getCurSoftware());
			sb.append("</td>");
			sb.append("<td align=left>");
			
			if (!success) {
				if (makeup_success) {
					sb.append("后来成功");
					c.setok_makeup(fs);
				}
				else {
					sb.append("失败中 时间:");
					c.addfail(fs);
				}
				for (UpgradeFail uf:fs) {
					sb.append("<br>").append(sdf2.format(uf.getCreateDate())).append(" ").append(uf.getFlow()).append(" Err:").append(uf.getErrorCode());
				}
			}
			else {
				sb.append("稍后成功");
				c.setok(fs, o);
			}
			sb.append("</td>");
			sb.append("</tr>");
		}
		sb.append("</table>");

		long t3 = new Date().getTime();
		
		//int s1 = oks.size()-(success_fail1+success_fail2+success_fail3+success_failn);
		//int total = oks.size() + fail_num;
		
		StringBuffer sb1 = new StringBuffer();
		sb1.append("<table border=1>");
		sb1.append("<tr align=center><td></td><td></td><td></td><td>全量</td><td></td>");
		sb1.append("<td>增量</td><td></td><td>不确定</td>");
		sb1.append("<tr><td>一次升级成功次数</td>");			
		sb1.append("<td>").append(c.success0()).append("</td>");
		sb1.append("<td>").append(c.success0_percent()).append("%</td>");
		sb1.append("<td>").append(c.success0_full()).append("</td>");
		sb1.append("<td>").append(c.success0_full_percent()).append("%</td>");
		sb1.append("<td>").append(c.success0_incr()).append("</td>");
		sb1.append("<td>").append(c.success0_incr_percent()).append("%</td>");
		sb1.append("</tr>");
		sb1.append("<tr><td>一次失败升级成功次数</td>");			
		sb1.append("<td>").append(c.success1()).append("</td>");
		sb1.append("<td>").append(c.success1_percent()).append("%</td>");
		sb1.append("<td>").append(c.success1_full()).append("</td>");
		sb1.append("<td>").append(c.success1_full_percent()).append("%</td>");
		sb1.append("<td>").append(c.success1_incr()).append("</td>");
		sb1.append("<td>").append(c.success1_incr_percent()).append("%</td>");
		sb1.append("<td>").append(c.success1_unknown()).append("</td>");
		sb1.append("</tr>");
		sb1.append("<tr><td>二次失败升级成功次数</td>");			
		sb1.append("<td>").append(c.success2()).append("</td>");
		sb1.append("<td>").append(c.success2_percent()).append("%</td>");
		sb1.append("<td>").append(c.success2_full()).append("</td>");
		sb1.append("<td>").append(c.success2_full_percent()).append("%</td>");
		sb1.append("<td>").append(c.success2_incr()).append("</td>");
		sb1.append("<td>").append(c.success2_incr_percent()).append("%</td>");
		sb1.append("<td>").append(c.success2_unknown()).append("</td>");
		sb1.append("</tr>");
		sb1.append("<tr><td>三次失败升级成功次数</td>");			
		sb1.append("<td>").append(c.success3()).append("</td>");
		sb1.append("<td>").append(c.success3_percent()).append("%</td>");
		sb1.append("<td>").append(c.success3_full()).append("</td>");
		sb1.append("<td>").append(c.success3_full_percent()).append("%</td>");
		sb1.append("<td>").append(c.success3_incr()).append("</td>");
		sb1.append("<td>").append(c.success3_incr_percent()).append("%</td>");
		sb1.append("<td>").append(c.success3_unknown()).append("</td>");
		sb1.append("</tr>");
		sb1.append("<tr><td>更多次失败但升级成功次数</td>");			
		sb1.append("<td>").append(c.successn()).append("</td>");
		sb1.append("<td>").append(c.successn_percent()).append("%</td>");
		sb1.append("<td>").append(c.successn_full()).append("</td>");
		sb1.append("<td>").append(c.successn_full_percent()).append("%</td>");
		sb1.append("<td>").append(c.successn_incr()).append("</td>");
		sb1.append("<td>").append(c.successn_incr_percent()).append("%</td>");
		sb1.append("<td>").append(c.successn_unknown()).append("</td>");
		sb1.append("</tr>");
		sb1.append("<tr><td>仍失败中</td>");	
		sb1.append("<td>").append(c.fail_num()).append("</td>");
		sb1.append("<td>").append(c.fail_percent()).append("%</td>");
		sb1.append("<td>").append(c.failnum_full()).append("</td>");
		sb1.append("<td>").append(c.failnum_full_percent()).append("%</td>");
		sb1.append("<td>").append(c.failnum_incr()).append("</td>");
		sb1.append("<td>").append(c.failnum_incr_percent()).append("%</td>");
		sb1.append("<td>").append(c.failnum_unknown()).append("</td>");		
		sb1.append("</tr>");
		sb1.append("<tr><td>Total</td>");			
		sb1.append("<td>").append(c.total()).append("</td>");
		sb1.append("<td>100%</td>");
		int fullnum = c.success0_full()+ c.success1_full()+c.success2_full()
			+c.success3_full()+c.successn_full()+c.failnum_full();
		int incrnum = c.success0_incr()+ c.success1_incr()+c.success2_incr()
			+c.success3_incr()+c.successn_incr()+c.failnum_incr();
		sb1.append("<td>").append(fullnum).append("</td>");
		sb1.append("<td>").append(100).append("%</td>");
		sb1.append("<td>").append(incrnum).append("</td>");		
		sb1.append("<td>").append(100).append("%</td>");
		sb1.append("</tr>");
		sb1.append("</table>");
		
		out.println("<br>查询时间:" + sdf2.format(new Date()));
		out.println("<br>查询版本:" + ver);
		out.println("<br>全量MD5:" + fullmd5);
		out.println("<br>增量MD5:" + incrmd5);
		out.println("<br>版本以上算增量" + incr_above);
		out.println("<br>开始查询日期:" + sdf.format(d));
		out.println("<br><br>");
		out.println(sb1.toString());
		out.println("<br><br>");
		out.println(sb.toString());
	}
	catch (Exception e) {
		e.printStackTrace();
	}
%>
