package com.wct.report;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class TmpHour3Mgr extends dbo.Manager<TmpHour3>
{
    private static TmpHour3Mgr _instance = null;

    TmpHour3Mgr() {}

    public synchronized static TmpHour3Mgr getInstance()
    {
        if (_instance==null) {
            _instance = new TmpHour3Mgr();
        }
        return _instance;
    }

    public TmpHour3Mgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "tmp_hour4";
    }

    protected Object makeBean()
    {
        return new TmpHour3();
    }

    protected String getIdentifier(Object obj)
    {
        TmpHour3 o = (TmpHour3) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        TmpHour3 item = (TmpHour3) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	device_mac		 = rs.getString("device_mac");
            item.setDevice_mac(device_mac);
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
            java.util.Date	ctime		 = rs.getTimestamp("ctime");
            item.setCtime(ctime);
            int	count		 = rs.getInt("count");
            item.setCount(count);
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
        TmpHour3 item = (TmpHour3) obj;

        String ret = 
            "`device_mac`='" + ServerTool.escapeString(item.getDevice_mac()) + "'"
            + ",`device_ip`='" + ServerTool.escapeString(item.getDevice_ip()) + "'"
            + ",`vendor_code`='" + ServerTool.escapeString(item.getVendor_code()) + "'"
            + ",`chipType`='" + ServerTool.escapeString(item.getChipType()) + "'"
            + ",`software_code`='" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + ",`province`='" + ServerTool.escapeString(item.getProvince()) + "'"
            + ",`city`='" + ServerTool.escapeString(item.getCity()) + "'"
            + ",`communications`='" + ServerTool.escapeString(item.getCommunications()) + "'"
            + ",`ctime`=" + (((d=item.getCtime())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",`count`=" + item.getCount()

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`device_mac`,`device_ip`,`vendor_code`,`chipType`,`software_code`,`province`,`city`,`communications`,`ctime`,`count`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        TmpHour3 item = (TmpHour3) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getDevice_mac()) + "'"
            + ",'" + ServerTool.escapeString(item.getDevice_ip()) + "'"
            + ",'" + ServerTool.escapeString(item.getVendor_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getChipType()) + "'"
            + ",'" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getProvince()) + "'"
            + ",'" + ServerTool.escapeString(item.getCity()) + "'"
            + ",'" + ServerTool.escapeString(item.getCommunications()) + "'"
            + "," + (((d=item.getCtime())!=null)?("'"+df.format(d)+"'"):"NULL")
            + "," + item.getCount()

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        TmpHour3 o = (TmpHour3) obj;
        o.setId(auto_id);
    }
}
