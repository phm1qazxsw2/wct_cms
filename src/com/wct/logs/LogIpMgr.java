package com.wct.logs;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class LogIpMgr extends dbo.Manager<LogIp>
{
    private static LogIpMgr _instance = null;

    LogIpMgr() {}

    public synchronized static LogIpMgr getInstance()
    {
        if (_instance==null) {
            _instance = new LogIpMgr();
        }
        return _instance;
    }

    public LogIpMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "log_ip";
    }

    protected Object makeBean()
    {
        return new LogIp();
    }

    protected String getIdentifier(Object obj)
    {
        LogIp o = (LogIp) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        LogIp item = (LogIp) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	ip		 = rs.getString("ip");
            item.setIp(ip);
            String	province		 = rs.getString("province");
            item.setProvince(province);
            String	city		 = rs.getString("city");
            item.setCity(city);
            String	communications		 = rs.getString("communications");
            item.setCommunications(communications);
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
        LogIp item = (LogIp) obj;

        String ret = 
            "`ip`='" + ServerTool.escapeString(item.getIp()) + "'"
            + ",`province`='" + ServerTool.escapeString(item.getProvince()) + "'"
            + ",`city`='" + ServerTool.escapeString(item.getCity()) + "'"
            + ",`communications`='" + ServerTool.escapeString(item.getCommunications()) + "'"

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`ip`,`province`,`city`,`communications`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        LogIp item = (LogIp) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getIp()) + "'"
            + ",'" + ServerTool.escapeString(item.getProvince()) + "'"
            + ",'" + ServerTool.escapeString(item.getCity()) + "'"
            + ",'" + ServerTool.escapeString(item.getCommunications()) + "'"

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        LogIp o = (LogIp) obj;
        o.setId(auto_id);
    }
}
