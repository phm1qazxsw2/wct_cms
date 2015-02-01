package com.wct.logs;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class LogMacMgr extends dbo.Manager<LogMac>
{
    private static LogMacMgr _instance = null;

    LogMacMgr() {}

    public synchronized static LogMacMgr getInstance()
    {
        if (_instance==null) {
            _instance = new LogMacMgr();
        }
        return _instance;
    }

    public LogMacMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "log_mac";
    }

    protected Object makeBean()
    {
        return new LogMac();
    }

    protected String getIdentifier(Object obj)
    {
        LogMac o = (LogMac) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        LogMac item = (LogMac) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	mac		 = rs.getString("mac");
            item.setMac(mac);
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
        LogMac item = (LogMac) obj;

        String ret = 
            "`mac`='" + ServerTool.escapeString(item.getMac()) + "'"

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`mac`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        LogMac item = (LogMac) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getMac()) + "'"

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        LogMac o = (LogMac) obj;
        o.setId(auto_id);
    }
}
