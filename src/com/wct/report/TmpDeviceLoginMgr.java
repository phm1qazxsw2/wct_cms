package com.wct.report;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class TmpDeviceLoginMgr extends dbo.Manager<TmpDeviceLogin>
{
    private static TmpDeviceLoginMgr _instance = null;

    TmpDeviceLoginMgr() {}

    public synchronized static TmpDeviceLoginMgr getInstance()
    {
        if (_instance==null) {
            _instance = new TmpDeviceLoginMgr();
        }
        return _instance;
    }

    public TmpDeviceLoginMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "tmp_device_login";
    }

    protected Object makeBean()
    {
        return new TmpDeviceLogin();
    }

    protected String getIdentifier(Object obj)
    {
        TmpDeviceLogin o = (TmpDeviceLogin) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        TmpDeviceLogin item = (TmpDeviceLogin) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	device_mac		 = rs.getString("device_mac");
            item.setDevice_mac(device_mac);
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
            java.util.Date	slottime		 = rs.getTimestamp("slottime");
            item.setSlottime(slottime);
            int	slottype		 = rs.getInt("slottype");
            item.setSlottype(slottype);
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
        TmpDeviceLogin item = (TmpDeviceLogin) obj;

        String ret = 
            "`device_mac`='" + ServerTool.escapeString(item.getDevice_mac()) + "'"
            + ",`vendor_code`='" + ServerTool.escapeString(item.getVendor_code()) + "'"
            + ",`chipType`='" + ServerTool.escapeString(item.getChipType()) + "'"
            + ",`software_code`='" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + ",`province`='" + ServerTool.escapeString(item.getProvince()) + "'"
            + ",`city`='" + ServerTool.escapeString(item.getCity()) + "'"
            + ",`slottime`=" + (((d=item.getSlottime())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",`slottype`=" + item.getSlottype()
            + ",`count`=" + item.getCount()

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`device_mac`,`vendor_code`,`chipType`,`software_code`,`province`,`city`,`slottime`,`slottype`,`count`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        TmpDeviceLogin item = (TmpDeviceLogin) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getDevice_mac()) + "'"
            + ",'" + ServerTool.escapeString(item.getVendor_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getChipType()) + "'"
            + ",'" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getProvince()) + "'"
            + ",'" + ServerTool.escapeString(item.getCity()) + "'"
            + "," + (((d=item.getSlottime())!=null)?("'"+df.format(d)+"'"):"NULL")
            + "," + item.getSlottype()
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
        TmpDeviceLogin o = (TmpDeviceLogin) obj;
        o.setId(auto_id);
    }
}
