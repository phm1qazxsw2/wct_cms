package com.wct.logs;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class LogMergeMgr extends dbo.Manager<LogMerge>
{
    private static LogMergeMgr _instance = null;

    LogMergeMgr() {}

    public synchronized static LogMergeMgr getInstance()
    {
        if (_instance==null) {
            _instance = new LogMergeMgr();
        }
        return _instance;
    }

    public LogMergeMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "log_merge";
    }

    protected Object makeBean()
    {
        return new LogMerge();
    }

    protected String getIdentifier(Object obj)
    {
        LogMerge o = (LogMerge) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        LogMerge item = (LogMerge) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	mac		 = rs.getString("mac");
            item.setMac(mac);
            String	ip		 = rs.getString("ip");
            item.setIp(ip);
            java.util.Date	start		 = rs.getTimestamp("start");
            item.setStart(start);
            java.util.Date	end		 = rs.getTimestamp("end");
            item.setEnd(end);
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
        LogMerge item = (LogMerge) obj;

        String ret = 
            "`mac`='" + ServerTool.escapeString(item.getMac()) + "'"
            + ",`ip`='" + ServerTool.escapeString(item.getIp()) + "'"
            + ",`start`=" + (((d=item.getStart())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",`end`=" + (((d=item.getEnd())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",`app`='" + ServerTool.escapeString(item.getApp()) + "'"
            + ",`video`='" + ServerTool.escapeString(item.getVideo()) + "'"
            + ",`channel`='" + ServerTool.escapeString(item.getChannel()) + "'"
            + ",`ctime`=" + (((d=item.getCtime())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`mac`,`ip`,`start`,`end`,`app`,`video`,`channel`,`ctime`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        LogMerge item = (LogMerge) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getMac()) + "'"
            + ",'" + ServerTool.escapeString(item.getIp()) + "'"
            + "," + (((d=item.getStart())!=null)?("'"+df.format(d)+"'"):"NULL")
            + "," + (((d=item.getEnd())!=null)?("'"+df.format(d)+"'"):"NULL")
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
        LogMerge o = (LogMerge) obj;
        o.setId(auto_id);
    }
}
