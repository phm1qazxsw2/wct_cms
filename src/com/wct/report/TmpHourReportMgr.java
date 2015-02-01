package com.wct.report;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class TmpHourReportMgr extends dbo.Manager<TmpHourReport>
{
    private static TmpHourReportMgr _instance = null;

    TmpHourReportMgr() {}

    public synchronized static TmpHourReportMgr getInstance()
    {
        if (_instance==null) {
            _instance = new TmpHourReportMgr();
        }
        return _instance;
    }

    public TmpHourReportMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "tmp_hour2";
    }

    protected Object makeBean()
    {
        return new TmpHourReport();
    }

    protected String getIdentifier(Object obj)
    {
        TmpHourReport o = (TmpHourReport) obj;
        return null;
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        TmpHourReport item = (TmpHourReport) obj;
        try {
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
            int	count		 = rs.getInt("count");
            item.setCount(count);
            int	sum		 = rs.getInt("sum");
            item.setSum(sum);
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
        TmpHourReport item = (TmpHourReport) obj;

        String ret = 
            "`vendor_code`='" + ServerTool.escapeString(item.getVendor_code()) + "'"
            + ",`chipType`='" + ServerTool.escapeString(item.getChipType()) + "'"
            + ",`software_code`='" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + ",`province`='" + ServerTool.escapeString(item.getProvince()) + "'"
            + ",`city`='" + ServerTool.escapeString(item.getCity()) + "'"
            + ",`communications`='" + ServerTool.escapeString(item.getCommunications()) + "'"
            + ",`count`=" + item.getCount()
            + ",`sum`=" + item.getSum()

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`vendor_code`,`chipType`,`software_code`,`province`,`city`,`communications`,`count`,`sum`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        TmpHourReport item = (TmpHourReport) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getVendor_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getChipType()) + "'"
            + ",'" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getProvince()) + "'"
            + ",'" + ServerTool.escapeString(item.getCity()) + "'"
            + ",'" + ServerTool.escapeString(item.getCommunications()) + "'"
            + "," + item.getCount()
            + "," + item.getSum()

        ;
        return ret;
    }
}
