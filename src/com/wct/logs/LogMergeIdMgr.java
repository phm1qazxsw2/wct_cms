package com.wct.logs;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class LogMergeIdMgr extends dbo.Manager<LogMergeId>
{
    private static LogMergeIdMgr _instance = null;

    LogMergeIdMgr() {}

    public synchronized static LogMergeIdMgr getInstance()
    {
        if (_instance==null) {
            _instance = new LogMergeIdMgr();
        }
        return _instance;
    }

    public LogMergeIdMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "log_merge_id";
    }

    protected Object makeBean()
    {
        return new LogMergeId();
    }

    protected String getIdentifier(Object obj)
    {
        LogMergeId o = (LogMergeId) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        LogMergeId item = (LogMergeId) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            int	last_id		 = rs.getInt("last_id");
            item.setLast_id(last_id);
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
        LogMergeId item = (LogMergeId) obj;

        String ret = 
            "`last_id`=" + item.getLast_id()

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`last_id`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        LogMergeId item = (LogMergeId) obj;

        String ret = 
            "" + item.getLast_id()

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        LogMergeId o = (LogMergeId) obj;
        o.setId(auto_id);
    }
}
