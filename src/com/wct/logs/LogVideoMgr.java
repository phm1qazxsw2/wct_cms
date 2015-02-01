package com.wct.logs;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class LogVideoMgr extends dbo.Manager<LogVideo>
{
    private static LogVideoMgr _instance = null;

    LogVideoMgr() {}

    public synchronized static LogVideoMgr getInstance()
    {
        if (_instance==null) {
            _instance = new LogVideoMgr();
        }
        return _instance;
    }

    public LogVideoMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "log_video";
    }

    protected Object makeBean()
    {
        return new LogVideo();
    }

    protected String getIdentifier(Object obj)
    {
        LogVideo o = (LogVideo) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        LogVideo item = (LogVideo) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            int	app		 = rs.getInt("app");
            item.setApp(app);
            String	name		 = rs.getString("name");
            item.setName(name);
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
        LogVideo item = (LogVideo) obj;

        String ret = 
            "`app`=" + item.getApp()
            + ",`name`='" + ServerTool.escapeString(item.getName()) + "'"

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`app`,`name`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        LogVideo item = (LogVideo) obj;

        String ret = 
            "" + item.getApp()
            + ",'" + ServerTool.escapeString(item.getName()) + "'"

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        LogVideo o = (LogVideo) obj;
        o.setId(auto_id);
    }
}
