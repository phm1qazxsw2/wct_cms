package com.wct.report;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class GY8188Mgr extends dbo.Manager<GY8188>
{
    private static GY8188Mgr _instance = null;

    GY8188Mgr() {}

    public synchronized static GY8188Mgr getInstance()
    {
        if (_instance==null) {
            _instance = new GY8188Mgr();
        }
        return _instance;
    }

    public GY8188Mgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "t_cloud_device_info_ex";
    }

    protected Object makeBean()
    {
        return new GY8188();
    }

    protected String getIdentifier(Object obj)
    {
        GY8188 o = (GY8188) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        GY8188 item = (GY8188) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	chipId		 = rs.getString("chipId");
            item.setChipId(chipId);
            String	wifiInfo		 = rs.getString("wifiInfo");
            item.setWifiInfo(wifiInfo);
            java.util.Date	buildDate		 = rs.getTimestamp("buildDate");
            item.setBuildDate(buildDate);
            String	netType		 = rs.getString("netType");
            item.setNetType(netType);
            java.util.Date	createTime		 = rs.getTimestamp("createTime");
            item.setCreateTime(createTime);
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
        GY8188 item = (GY8188) obj;

        String ret = 
            "`chipId`='" + ServerTool.escapeString(item.getChipId()) + "'"
            + ",`wifiInfo`='" + ServerTool.escapeString(item.getWifiInfo()) + "'"
            + ",`buildDate`=" + (((d=item.getBuildDate())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",`netType`='" + ServerTool.escapeString(item.getNetType()) + "'"
            + ",`createTime`=" + (((d=item.getCreateTime())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`chipId`,`wifiInfo`,`buildDate`,`netType`,`createTime`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        GY8188 item = (GY8188) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getChipId()) + "'"
            + ",'" + ServerTool.escapeString(item.getWifiInfo()) + "'"
            + "," + (((d=item.getBuildDate())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",'" + ServerTool.escapeString(item.getNetType()) + "'"
            + "," + (((d=item.getCreateTime())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        GY8188 o = (GY8188) obj;
        o.setId(auto_id);
    }
}
