package com.wct.report;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class ReportDeviceActivateMgr extends dbo.Manager<ReportDeviceActivate>
{
    private static ReportDeviceActivateMgr _instance = null;

    ReportDeviceActivateMgr() {}

    public synchronized static ReportDeviceActivateMgr getInstance()
    {
        if (_instance==null) {
            _instance = new ReportDeviceActivateMgr();
        }
        return _instance;
    }

    public ReportDeviceActivateMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "r2_device_activate";
    }

    protected Object makeBean()
    {
        return new ReportDeviceActivate();
    }

    protected String getIdentifier(Object obj)
    {
        ReportDeviceActivate o = (ReportDeviceActivate) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        ReportDeviceActivate item = (ReportDeviceActivate) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	vendor_code		 = rs.getString("vendor_code");
            item.setVendor_code(vendor_code);
            String	chipType		 = rs.getString("chipType");
            item.setChipType(chipType);
            String	software_code		 = rs.getString("software_code");
            item.setSoftware_code(software_code);
            java.util.Date	ctime		 = rs.getTimestamp("ctime");
            item.setCtime(ctime);
            int	count		 = rs.getInt("count");
            item.setCount(count);
            int	type		 = rs.getInt("type");
            item.setType(type);
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
        ReportDeviceActivate item = (ReportDeviceActivate) obj;

        String ret = 
            "`vendor_code`='" + ServerTool.escapeString(item.getVendor_code()) + "'"
            + ",`chipType`='" + ServerTool.escapeString(item.getChipType()) + "'"
            + ",`software_code`='" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + ",`ctime`=" + (((d=item.getCtime())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",`count`=" + item.getCount()
            + ",`type`=" + item.getType()

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`vendor_code`,`chipType`,`software_code`,`ctime`,`count`,`type`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        ReportDeviceActivate item = (ReportDeviceActivate) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getVendor_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getChipType()) + "'"
            + ",'" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + "," + (((d=item.getCtime())!=null)?("'"+df.format(d)+"'"):"NULL")
            + "," + item.getCount()
            + "," + item.getType()

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        ReportDeviceActivate o = (ReportDeviceActivate) obj;
        o.setId(auto_id);
    }
}
