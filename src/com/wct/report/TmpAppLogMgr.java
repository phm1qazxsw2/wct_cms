package com.wct.report;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class TmpAppLogMgr extends dbo.Manager<TmpAppLog>
{
    private static TmpAppLogMgr _instance = null;

    TmpAppLogMgr() {}

    public synchronized static TmpAppLogMgr getInstance()
    {
        if (_instance==null) {
            _instance = new TmpAppLogMgr();
        }
        return _instance;
    }

    public TmpAppLogMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "tmp_applog";
    }

    protected Object makeBean()
    {
        return new TmpAppLog();
    }

    protected String getIdentifier(Object obj)
    {
        TmpAppLog o = (TmpAppLog) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        TmpAppLog item = (TmpAppLog) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            int	org_id		 = rs.getInt("org_id");
            item.setOrg_id(org_id);
            String	mac		 = rs.getString("mac");
            item.setMac(mac);
            String	appName		 = rs.getString("appName");
            item.setAppName(appName);
            java.util.Date	enterTime		 = rs.getTimestamp("enterTime");
            item.setEnterTime(enterTime);
            java.util.Date	leaveTime		 = rs.getTimestamp("leaveTime");
            item.setLeaveTime(leaveTime);
            long	duration		 = 0;try { duration = Long.parseLong(new String(rs.getBytes("duration"))); } catch (Exception ee) {}
            item.setDuration(duration);
            String	device_ip		 = rs.getString("device_ip");
            item.setDevice_ip(device_ip);
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
            int	processed		 = rs.getInt("processed");
            item.setProcessed(processed);
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
        TmpAppLog item = (TmpAppLog) obj;

        String ret = 
            "`org_id`=" + item.getOrg_id()
            + ",`mac`='" + ServerTool.escapeString(item.getMac()) + "'"
            + ",`appName`='" + ServerTool.escapeString(item.getAppName()) + "'"
            + ",`enterTime`=" + (((d=item.getEnterTime())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",`leaveTime`=" + (((d=item.getLeaveTime())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",`duration`=" + item.getDuration()
            + ",`device_ip`='" + ServerTool.escapeString(item.getDevice_ip()) + "'"
            + ",`vendor_code`='" + ServerTool.escapeString(item.getVendor_code()) + "'"
            + ",`chipType`='" + ServerTool.escapeString(item.getChipType()) + "'"
            + ",`software_code`='" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + ",`province`='" + ServerTool.escapeString(item.getProvince()) + "'"
            + ",`city`='" + ServerTool.escapeString(item.getCity()) + "'"
            + ",`communications`='" + ServerTool.escapeString(item.getCommunications()) + "'"
            + ",`processed`=" + item.getProcessed()

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`org_id`,`mac`,`appName`,`enterTime`,`leaveTime`,`duration`,`device_ip`,`vendor_code`,`chipType`,`software_code`,`province`,`city`,`communications`,`processed`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        TmpAppLog item = (TmpAppLog) obj;

        String ret = 
            "" + item.getOrg_id()
            + ",'" + ServerTool.escapeString(item.getMac()) + "'"
            + ",'" + ServerTool.escapeString(item.getAppName()) + "'"
            + "," + (((d=item.getEnterTime())!=null)?("'"+df.format(d)+"'"):"NULL")
            + "," + (((d=item.getLeaveTime())!=null)?("'"+df.format(d)+"'"):"NULL")
            + "," + item.getDuration()
            + ",'" + ServerTool.escapeString(item.getDevice_ip()) + "'"
            + ",'" + ServerTool.escapeString(item.getVendor_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getChipType()) + "'"
            + ",'" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getProvince()) + "'"
            + ",'" + ServerTool.escapeString(item.getCity()) + "'"
            + ",'" + ServerTool.escapeString(item.getCommunications()) + "'"
            + "," + item.getProcessed()

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        TmpAppLog o = (TmpAppLog) obj;
        o.setId(auto_id);
    }
}
