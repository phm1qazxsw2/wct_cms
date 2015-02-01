package com.wct.report;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class ActivateFailMgr extends dbo.Manager<ActivateFail>
{
    private static ActivateFailMgr _instance = null;

    ActivateFailMgr() {}

    public synchronized static ActivateFailMgr getInstance()
    {
        if (_instance==null) {
            _instance = new ActivateFailMgr();
        }
        return _instance;
    }

    public ActivateFailMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "t_cloud_active_info";
    }

    protected Object makeBean()
    {
        return new ActivateFail();
    }

    protected String getIdentifier(Object obj)
    {
        ActivateFail o = (ActivateFail) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        ActivateFail item = (ActivateFail) obj;
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
        ActivateFail item = (ActivateFail) obj;

        String ret = 
            "`chip_code`='" + ServerTool.escapeString(item.getChip_code()) + "'"
            + ",`software_code`='" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + ",`vendor_code`='" + ServerTool.escapeString(item.getVendor_code()) + "'"
            + ",`device_mac`='" + ServerTool.escapeString(item.getDevice_mac()) + "'"
            + ",`device_ip`='" + ServerTool.escapeString(item.getDevice_ip()) + "'"
            + ",`info`='" + ServerTool.escapeString(item.getInfo()) + "'"
            + ",`active_times_date`=" + (((d=item.getActive_times_date())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`chip_code`,`software_code`,`vendor_code`,`device_mac`,`device_ip`,`info`,`active_times_date`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        ActivateFail item = (ActivateFail) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getChip_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getVendor_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getDevice_mac()) + "'"
            + ",'" + ServerTool.escapeString(item.getDevice_ip()) + "'"
            + ",'" + ServerTool.escapeString(item.getInfo()) + "'"
            + "," + (((d=item.getActive_times_date())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        ActivateFail o = (ActivateFail) obj;
        o.setId(auto_id);
    }
}
