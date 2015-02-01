package com.wct.logs;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class LogSmitMgr extends dbo.Manager<LogSmit>
{
    private static LogSmitMgr _instance = null;

    LogSmitMgr() {}

    public synchronized static LogSmitMgr getInstance()
    {
        if (_instance==null) {
            _instance = new LogSmitMgr();
        }
        return _instance;
    }

    public LogSmitMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "log_smit";
    }

    protected Object makeBean()
    {
        return new LogSmit();
    }

    protected String getIdentifier(Object obj)
    {
        LogSmit o = (LogSmit) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        LogSmit item = (LogSmit) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	mac		 = rs.getString("mac");
            item.setMac(mac);
            String	ip		 = rs.getString("ip");
            item.setIp(ip);
            int	secs		 = rs.getInt("secs");
            item.setSecs(secs);
            String	video		 = rs.getString("video");
            item.setVideo(video);
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
        LogSmit item = (LogSmit) obj;

        String ret = 
            "`mac`='" + ServerTool.escapeString(item.getMac()) + "'"
            + ",`ip`='" + ServerTool.escapeString(item.getIp()) + "'"
            + ",`secs`=" + item.getSecs()
            + ",`video`='" + ServerTool.escapeString(item.getVideo()) + "'"
            + ",`ctime`=" + (((d=item.getCtime())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`mac`,`ip`,`secs`,`video`,`ctime`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        LogSmit item = (LogSmit) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getMac()) + "'"
            + ",'" + ServerTool.escapeString(item.getIp()) + "'"
            + "," + item.getSecs()
            + ",'" + ServerTool.escapeString(item.getVideo()) + "'"
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
        LogSmit o = (LogSmit) obj;
        o.setId(auto_id);
    }
}
