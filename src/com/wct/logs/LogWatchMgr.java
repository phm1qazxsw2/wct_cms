package com.wct.logs;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class LogWatchMgr extends dbo.Manager<LogWatch>
{
    private static LogWatchMgr _instance = null;

    LogWatchMgr() {}

    public synchronized static LogWatchMgr getInstance()
    {
        if (_instance==null) {
            _instance = new LogWatchMgr();
        }
        return _instance;
    }

    public LogWatchMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "log_watch3";
    }

    protected Object makeBean()
    {
        return new LogWatch();
    }

    protected String getIdentifier(Object obj)
    {
        LogWatch o = (LogWatch) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        LogWatch item = (LogWatch) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	mac		 = rs.getString("mac");
            item.setMac(mac);
            String	ip		 = rs.getString("ip");
            item.setIp(ip);
            int	secs		 = rs.getInt("secs");
            item.setSecs(secs);
            String	app		 = rs.getString("app");
            item.setApp(app);
            String	video		 = rs.getString("video");
            item.setVideo(video);
            String	channel		 = rs.getString("channel");
            item.setChannel(channel);
            java.util.Date	ctime		 = rs.getTimestamp("ctime");
            item.setCtime(ctime);
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
        LogWatch item = (LogWatch) obj;

        String ret = 
            "`mac`='" + ServerTool.escapeString(item.getMac()) + "'"
            + ",`ip`='" + ServerTool.escapeString(item.getIp()) + "'"
            + ",`secs`=" + item.getSecs()
            + ",`app`='" + ServerTool.escapeString(item.getApp()) + "'"
            + ",`video`='" + ServerTool.escapeString(item.getVideo()) + "'"
            + ",`channel`='" + ServerTool.escapeString(item.getChannel()) + "'"
            + ",`ctime`=" + (((d=item.getCtime())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`mac`,`ip`,`secs`,`app`,`video`,`channel`,`ctime`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        LogWatch item = (LogWatch) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getMac()) + "'"
            + ",'" + ServerTool.escapeString(item.getIp()) + "'"
            + "," + item.getSecs()
            + ",'" + ServerTool.escapeString(item.getApp()) + "'"
            + ",'" + ServerTool.escapeString(item.getVideo()) + "'"
            + ",'" + ServerTool.escapeString(item.getChannel()) + "'"
            + "," + (((d=item.getCtime())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        LogWatch o = (LogWatch) obj;
        o.setId(auto_id);
    }
}
