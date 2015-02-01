package com.wct.report;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class ReportDeviceLoginMgr extends dbo.Manager<ReportDeviceLogin>
{
    private static ReportDeviceLoginMgr _instance = null;

    ReportDeviceLoginMgr() {}

    public synchronized static ReportDeviceLoginMgr getInstance()
    {
        if (_instance==null) {
            _instance = new ReportDeviceLoginMgr();
        }
        return _instance;
    }

    public ReportDeviceLoginMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "r3_device_login";
    }

    protected Object makeBean()
    {
        return new ReportDeviceLogin();
    }

    protected String getIdentifier(Object obj)
    {
        ReportDeviceLogin o = (ReportDeviceLogin) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        ReportDeviceLogin item = (ReportDeviceLogin) obj;
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
            java.util.Date	ctime		 = rs.getTimestamp("ctime");
            item.setCtime(ctime);
            int	count		 = rs.getInt("count");
            item.setCount(count);
            int	logins		 = rs.getInt("logins");
            item.setLogins(logins);
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
        ReportDeviceLogin item = (ReportDeviceLogin) obj;

        String ret = 
            "`vendor_code`='" + ServerTool.escapeString(item.getVendor_code()) + "'"
            + ",`chipType`='" + ServerTool.escapeString(item.getChipType()) + "'"
            + ",`software_code`='" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + ",`province`='" + ServerTool.escapeString(item.getProvince()) + "'"
            + ",`city`='" + ServerTool.escapeString(item.getCity()) + "'"
            + ",`communications`='" + ServerTool.escapeString(item.getCommunications()) + "'"
            + ",`ctime`=" + (((d=item.getCtime())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",`count`=" + item.getCount()
            + ",`logins`=" + item.getLogins()
            + ",`type`=" + item.getType()

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`vendor_code`,`chipType`,`software_code`,`province`,`city`,`communications`,`ctime`,`count`,`logins`,`type`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        ReportDeviceLogin item = (ReportDeviceLogin) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getVendor_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getChipType()) + "'"
            + ",'" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getProvince()) + "'"
            + ",'" + ServerTool.escapeString(item.getCity()) + "'"
            + ",'" + ServerTool.escapeString(item.getCommunications()) + "'"
            + "," + (((d=item.getCtime())!=null)?("'"+df.format(d)+"'"):"NULL")
            + "," + item.getCount()
            + "," + item.getLogins()
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
        ReportDeviceLogin o = (ReportDeviceLogin) obj;
        o.setId(auto_id);
    }
}
