package util;

import java.util.*;
import java.text.*;


public class DateUtil {

	/**
	 * ��ȡ��ǰϵͳʱ�䣬������ʽ��ʾ
	 * @return
	 */
	public static String getDateStr(String format)
	{
		Date dt = new Date(System.currentTimeMillis());
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		String dtStr = sdf.format(dt);
		return dtStr;
	}
	
	public static Date getDate(String dateStr,String format)
	{
		if(format==null)
		{
			format = "yyyy-MM-dd HH:mm:ss";
		}
		Date d = null;
		SimpleDateFormat sf = new SimpleDateFormat(format);
		try {
			d = new Date(sf.parse(dateStr).getTime());
		} catch (Exception e) {
			d = null;
		}
		return d;
	}
	
	public static String getDateStr(Date dt,String format)
	{
		if (dt==null)
			return "";
		if(format==null)
		{
			format = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		String dtStr = sdf.format(dt);
		return dtStr;
	}
	
	private static String[] wdays = {"周日","周一","周二","周三","周四","周五","周六"};
	private static SimpleDateFormat sdf_normal = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdf_pretty = new SimpleDateFormat("MM月dd日");
	public static String getPrettyDate1(String datestr)
	{
		try {
			Date d = sdf_normal.parse(datestr); 
			Calendar c = Calendar.getInstance();
			c.setTime(d);
			int w = c.get(Calendar.DAY_OF_WEEK);
			return wdays[w-1] + " " + sdf_pretty.format(d);
		}
		catch (Exception e) {
		}
		return "";
	}
	
	public static int compareTo(String datestr, Date target)
	{
		try {
			Date d = sdf_normal.parse(datestr); 
			return d.compareTo(target); 
		}
		catch (Exception e) {
		}
		return 0;
	}
	
	private static Calendar _c = Calendar.getInstance();
	private static SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
	public static Date nextday(Date d)
		throws Exception
	{
		_c.setTime(d);
		_c.add(Calendar.DATE, 1);
		return datef.parse(datef.format(_c.getTime()));
	}
}
