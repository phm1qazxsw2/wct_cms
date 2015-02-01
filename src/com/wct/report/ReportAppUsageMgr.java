package com.wct.report;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class ReportAppUsageMgr extends dbo.Manager<ReportAppUsage>
{
    private static ReportAppUsageMgr _instance = null;

    ReportAppUsageMgr() {}

    public synchronized static ReportAppUsageMgr getInstance()
    {
        if (_instance==null) {
            _instance = new ReportAppUsageMgr();
        }
        return _instance;
    }

    public ReportAppUsageMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "r3_app_usage";
    }

    protected Object makeBean()
    {
        return new ReportAppUsage();
    }

    protected String getIdentifier(Object obj)
    {
        ReportAppUsage o = (ReportAppUsage) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        ReportAppUsage item = (ReportAppUsage) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	appName		 = rs.getString("appName");
            item.setAppName(appName);
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
            long	seconds		 = 0;try { seconds = Long.parseLong(new String(rs.getBytes("seconds"))); } catch (Exception ee) {}
            item.setSeconds(seconds);
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
        ReportAppUsage item = (ReportAppUsage) obj;

        String ret = 
            "`appName`='" + ServerTool.escapeString(item.getAppName()) + "'"
            + ",`vendor_code`='" + ServerTool.escapeString(item.getVendor_code()) + "'"
            + ",`chipType`='" + ServerTool.escapeString(item.getChipType()) + "'"
            + ",`software_code`='" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + ",`province`='" + ServerTool.escapeString(item.getProvince()) + "'"
            + ",`city`='" + ServerTool.escapeString(item.getCity()) + "'"
            + ",`communications`='" + ServerTool.escapeString(item.getCommunications()) + "'"
            + ",`ctime`=" + (((d=item.getCtime())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",`seconds`=" + item.getSeconds()
            + ",`type`=" + item.getType()

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`appName`,`vendor_code`,`chipType`,`software_code`,`province`,`city`,`communications`,`ctime`,`seconds`,`type`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        ReportAppUsage item = (ReportAppUsage) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getAppName()) + "'"
            + ",'" + ServerTool.escapeString(item.getVendor_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getChipType()) + "'"
            + ",'" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getProvince()) + "'"
            + ",'" + ServerTool.escapeString(item.getCity()) + "'"
            + ",'" + ServerTool.escapeString(item.getCommunications()) + "'"
            + "," + (((d=item.getCtime())!=null)?("'"+df.format(d)+"'"):"NULL")
            + "," + item.getSeconds()
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
        ReportAppUsage o = (ReportAppUsage) obj;
        o.setId(auto_id);
    }
}
