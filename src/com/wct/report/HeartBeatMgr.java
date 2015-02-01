package com.wct.report;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class HeartBeatMgr extends dbo.Manager<HeartBeat>
{
    private static HeartBeatMgr _instance = null;

    HeartBeatMgr() {}

    public synchronized static HeartBeatMgr getInstance()
    {
        if (_instance==null) {
            _instance = new HeartBeatMgr();
        }
        return _instance;
    }

    public HeartBeatMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "t_cloud_online_info";
    }

    protected Object makeBean()
    {
        return new HeartBeat();
    }

    protected String getIdentifier(Object obj)
    {
        HeartBeat o = (HeartBeat) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        HeartBeat item = (HeartBeat) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	chip_code		 = rs.getString("chip_code");
            item.setChip_code(chip_code);
            String	software_code		 = rs.getString("software_code");
            item.setSoftware_code(software_code);
            String	vendor_code		 = rs.getString("vendor_code");
            item.setVendor_code(vendor_code);
            String	device_mac		 = rs.getString("device_mac");
            item.setDevice_mac(device_mac);
            String	device_ip		 = rs.getString("device_ip");
            item.setDevice_ip(device_ip);
            String	info		 = rs.getString("info");
            item.setInfo(info);
            java.util.Date	active_times_date		 = rs.getTimestamp("active_times_date");
            item.setActive_times_date(active_times_date);
            String	vid		 = rs.getString("vid");
            item.setVid(vid);
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
        HeartBeat item = (HeartBeat) obj;

        String ret = 
            "`chip_code`='" + ServerTool.escapeString(item.getChip_code()) + "'"
            + ",`software_code`='" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + ",`vendor_code`='" + ServerTool.escapeString(item.getVendor_code()) + "'"
            + ",`device_mac`='" + ServerTool.escapeString(item.getDevice_mac()) + "'"
            + ",`device_ip`='" + ServerTool.escapeString(item.getDevice_ip()) + "'"
            + ",`info`='" + ServerTool.escapeString(item.getInfo()) + "'"
            + ",`active_times_date`=" + (((d=item.getActive_times_date())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",`vid`='" + ServerTool.escapeString(item.getVid()) + "'"

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`chip_code`,`software_code`,`vendor_code`,`device_mac`,`device_ip`,`info`,`active_times_date`,`vid`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        HeartBeat item = (HeartBeat) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getChip_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getVendor_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getDevice_mac()) + "'"
            + ",'" + ServerTool.escapeString(item.getDevice_ip()) + "'"
            + ",'" + ServerTool.escapeString(item.getInfo()) + "'"
            + "," + (((d=item.getActive_times_date())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",'" + ServerTool.escapeString(item.getVid()) + "'"

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        HeartBeat o = (HeartBeat) obj;
        o.setId(auto_id);
    }
}
