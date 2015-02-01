package com.wct.report;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class TmpDeviceReportMgr extends dbo.Manager<TmpDeviceReport>
{
    private static TmpDeviceReportMgr _instance = null;

    TmpDeviceReportMgr() {}

    public synchronized static TmpDeviceReportMgr getInstance()
    {
        if (_instance==null) {
            _instance = new TmpDeviceReportMgr();
        }
        return _instance;
    }

    public TmpDeviceReportMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "tmp_device";
    }

    protected Object makeBean()
    {
        return new TmpDeviceReport();
    }

    protected String getIdentifier(Object obj)
    {
        TmpDeviceReport o = (TmpDeviceReport) obj;
        return null;
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        TmpDeviceReport item = (TmpDeviceReport) obj;
        try {
            String	vendor_code		 = rs.getString("vendor_code");
            item.setVendor_code(vendor_code);
            String	chipType		 = rs.getString("chipType");
            item.setChipType(chipType);
            String	software_code		 = rs.getString("software_code");
            item.setSoftware_code(software_code);
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
        TmpDeviceReport item = (TmpDeviceReport) obj;

        String ret = 
            "`vendor_code`='" + ServerTool.escapeString(item.getVendor_code()) + "'"
            + ",`chipType`='" + ServerTool.escapeString(item.getChipType()) + "'"
            + ",`software_code`='" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + ",`count`=" + item.getCount()

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`vendor_code`,`chipType`,`software_code`,`count`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        TmpDeviceReport item = (TmpDeviceReport) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getVendor_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getChipType()) + "'"
            + ",'" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + "," + item.getCount()

        ;
        return ret;
    }
}
