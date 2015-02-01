package com.wct.logs;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class LogUrlMgr extends dbo.Manager<LogUrl>
{
    private static LogUrlMgr _instance = null;

    LogUrlMgr() {}

    public synchronized static LogUrlMgr getInstance()
    {
        if (_instance==null) {
            _instance = new LogUrlMgr();
        }
        return _instance;
    }

    public LogUrlMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "log_url";
    }

    protected Object makeBean()
    {
        return new LogUrl();
    }

    protected String getIdentifier(Object obj)
    {
        LogUrl o = (LogUrl) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        LogUrl item = (LogUrl) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            int	type		 = rs.getInt("type");
            item.setType(type);
            String	md5		 = rs.getString("md5");
            item.setMd5(md5);
            String	url		 = rs.getString("url");
            item.setUrl(url);
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
        LogUrl item = (LogUrl) obj;

        String ret = 
            "`type`=" + item.getType()
            + ",`md5`='" + ServerTool.escapeString(item.getMd5()) + "'"
            + ",`url`='" + ServerTool.escapeString(item.getUrl()) + "'"

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`type`,`md5`,`url`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        LogUrl item = (LogUrl) obj;

        String ret = 
            "" + item.getType()
            + ",'" + ServerTool.escapeString(item.getMd5()) + "'"
            + ",'" + ServerTool.escapeString(item.getUrl()) + "'"

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        LogUrl o = (LogUrl) obj;
        o.setId(auto_id);
    }
}
