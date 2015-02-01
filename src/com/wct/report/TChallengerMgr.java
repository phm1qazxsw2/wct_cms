package com.wct.report;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class TChallengerMgr extends dbo.Manager<TChallenger>
{
    private static TChallengerMgr _instance = null;

    TChallengerMgr() {}

    public synchronized static TChallengerMgr getInstance()
    {
        if (_instance==null) {
            _instance = new TChallengerMgr();
        }
        return _instance;
    }

    public TChallengerMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "t_cloud_challenger_info";
    }

    protected Object makeBean()
    {
        return new TChallenger();
    }

    protected String getIdentifier(Object obj)
    {
        TChallenger o = (TChallenger) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        TChallenger item = (TChallenger) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	device_mac		 = rs.getString("device_mac");
            item.setDevice_mac(device_mac);
            String	device_ip		 = rs.getString("device_ip");
            item.setDevice_ip(device_ip);
            java.util.Date	challenger_times_date		 = rs.getTimestamp("challenger_times_date");
            item.setChallenger_times_date(challenger_times_date);
            String	software_code		 = rs.getString("software_code");
            item.setSoftware_code(software_code);
            String	vendor_code		 = rs.getString("vendor_code");
            item.setVendor_code(vendor_code);
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
        TChallenger item = (TChallenger) obj;

        String ret = 
            "`device_mac`='" + ServerTool.escapeString(item.getDevice_mac()) + "'"
            + ",`device_ip`='" + ServerTool.escapeString(item.getDevice_ip()) + "'"
            + ",`challenger_times_date`=" + (((d=item.getChallenger_times_date())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",`software_code`='" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + ",`vendor_code`='" + ServerTool.escapeString(item.getVendor_code()) + "'"

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`device_mac`,`device_ip`,`challenger_times_date`,`software_code`,`vendor_code`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        TChallenger item = (TChallenger) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getDevice_mac()) + "'"
            + ",'" + ServerTool.escapeString(item.getDevice_ip()) + "'"
            + "," + (((d=item.getChallenger_times_date())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",'" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getVendor_code()) + "'"

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        TChallenger o = (TChallenger) obj;
        o.setId(auto_id);
    }
}
