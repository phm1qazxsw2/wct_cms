package com.wct.report;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class DailyLoginsMgr extends dbo.Manager<DailyLogins>
{
    private static DailyLoginsMgr _instance = null;

    DailyLoginsMgr() {}

    public synchronized static DailyLoginsMgr getInstance()
    {
        if (_instance==null) {
            _instance = new DailyLoginsMgr();
        }
        return _instance;
    }

    public DailyLoginsMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "r3_daily_logins";
    }

    protected Object makeBean()
    {
        return new DailyLogins();
    }

    protected String getIdentifier(Object obj)
    {
        DailyLogins o = (DailyLogins) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        DailyLogins item = (DailyLogins) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	vendor_code		 = rs.getString("vendor_code");
            item.setVendor_code(vendor_code);
            String	chipType		 = rs.getString("chipType");
            item.setChipType(chipType);
            String	software_code		 = rs.getString("software_code");
            item.setSoftware_code(software_code);
            String	province		 = rs.getString("province");
            item.setProvince(province);
            String	city		 = rs.getString("city");
            item.setCity(city);
            String	communications		 = rs.getString("communications");
            item.setCommunications(communications);
            int	count		 = rs.getInt("count");
            item.setCount(count);
            int	logins		 = rs.getInt("logins");
            item.setLogins(logins);
            int	times		 = rs.getInt("times");
            item.setTimes(times);
            java.util.Date	ctime		 = rs.getTimestamp("ctime");
            item.setCtime(ctime);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    protected String getSaveString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        DailyLogins item = (DailyLogins) obj;

        String ret = 
            "`vendor_code`='" + ServerTool.escapeString(item.getVendor_code()) + "'"
            + ",`chipType`='" + ServerTool.escapeString(item.getChipType()) + "'"
            + ",`software_code`='" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + ",`province`='" + ServerTool.escapeString(item.getProvince()) + "'"
            + ",`city`='" + ServerTool.escapeString(item.getCity()) + "'"
            + ",`communications`='" + ServerTool.escapeString(item.getCommunications()) + "'"
            + ",`count`=" + item.getCount()
            + ",`logins`=" + item.getLogins()
            + ",`times`=" + item.getTimes()
            + ",`ctime`=" + (((d=item.getCtime())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`vendor_code`,`chipType`,`software_code`,`province`,`city`,`communications`,`count`,`logins`,`times`,`ctime`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        DailyLogins item = (DailyLogins) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getVendor_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getChipType()) + "'"
            + ",'" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getProvince()) + "'"
            + ",'" + ServerTool.escapeString(item.getCity()) + "'"
            + ",'" + ServerTool.escapeString(item.getCommunications()) + "'"
            + "," + item.getCount()
            + "," + item.getLogins()
            + "," + item.getTimes()
            + "," + (((d=item.getCtime())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        DailyLogins o = (DailyLogins) obj;
        o.setId(auto_id);
    }
}
