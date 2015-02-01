package com.wct.logs;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class LogRecordMgr extends dbo.Manager<LogRecord>
{
    private static LogRecordMgr _instance = null;

    LogRecordMgr() {}

    public synchronized static LogRecordMgr getInstance()
    {
        if (_instance==null) {
            _instance = new LogRecordMgr();
        }
        return _instance;
    }

    public LogRecordMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "log_record";
    }

    protected Object makeBean()
    {
        return new LogRecord();
    }

    protected String getIdentifier(Object obj)
    {
        LogRecord o = (LogRecord) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        LogRecord item = (LogRecord) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            int	event		 = rs.getInt("event");
            item.setEvent(event);
            int	macId		 = rs.getInt("macId");
            item.setMacId(macId);
            int	ipId		 = rs.getInt("ipId");
            item.setIpId(ipId);
            java.util.Date	ctime		 = rs.getTimestamp("ctime");
            item.setCtime(ctime);
            int	videoId		 = rs.getInt("videoId");
            item.setVideoId(videoId);
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
        LogRecord item = (LogRecord) obj;

        String ret = 
            "`event`=" + item.getEvent()
            + ",`macId`=" + item.getMacId()
            + ",`ipId`=" + item.getIpId()
            + ",`ctime`=" + (((d=item.getCtime())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",`videoId`=" + item.getVideoId()

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`event`,`macId`,`ipId`,`ctime`,`videoId`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        LogRecord item = (LogRecord) obj;

        String ret = 
            "" + item.getEvent()
            + "," + item.getMacId()
            + "," + item.getIpId()
            + "," + (((d=item.getCtime())!=null)?("'"+df.format(d)+"'"):"NULL")
            + "," + item.getVideoId()

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        LogRecord o = (LogRecord) obj;
        o.setId(auto_id);
    }
}
