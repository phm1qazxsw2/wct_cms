package com.wct.report;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class TmpDeviceMgr extends dbo.Manager<TmpDevice>
{
    private static TmpDeviceMgr _instance = null;

    TmpDeviceMgr() {}

    public synchronized static TmpDeviceMgr getInstance()
    {
        if (_instance==null) {
            _instance = new TmpDeviceMgr();
        }
        return _instance;
    }

    public TmpDeviceMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "tmp_device_info";
    }

    protected Object makeBean()
    {
        return new TmpDevice();
    }

    protected String getIdentifier(Object obj)
    {
        TmpDevice o = (TmpDevice) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        TmpDevice item = (TmpDevice) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	software_code		 = rs.getString("software_code");
            item.setSoftware_code(software_code);
            String	vendor_code		 = rs.getString("vendor_code");
            item.setVendor_code(vendor_code);
            String	device_mac		 = rs.getString("device_mac");
            item.setDevice_mac(device_mac);
            String	chipType		 = rs.getString("chipType");
            item.setChipType(chipType);
            java.util.Date	activate_time		 = rs.getTimestamp("activate_time");
            item.setActivate_time(activate_time);
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
        TmpDevice item = (TmpDevice) obj;

        String ret = 
            "`software_code`='" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + ",`vendor_code`='" + ServerTool.escapeString(item.getVendor_code()) + "'"
            + ",`device_mac`='" + ServerTool.escapeString(item.getDevice_mac()) + "'"
            + ",`chipType`='" + ServerTool.escapeString(item.getChipType()) + "'"
            + ",`activate_time`=" + (((d=item.getActivate_time())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`software_code`,`vendor_code`,`device_mac`,`chipType`,`activate_time`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        TmpDevice item = (TmpDevice) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getVendor_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getDevice_mac()) + "'"
            + ",'" + ServerTool.escapeString(item.getChipType()) + "'"
            + "," + (((d=item.getActivate_time())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        TmpDevice o = (TmpDevice) obj;
        o.setId(auto_id);
    }
}
