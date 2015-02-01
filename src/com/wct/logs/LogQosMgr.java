package com.wct.logs;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class LogQosMgr extends dbo.Manager<LogQos>
{
    private static LogQosMgr _instance = null;

    LogQosMgr() {}

    public synchronized static LogQosMgr getInstance()
    {
        if (_instance==null) {
            _instance = new LogQosMgr();
        }
        return _instance;
    }

    public LogQosMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "log_qos";
    }

    protected Object makeBean()
    {
        return new LogQos();
    }

    protected String getIdentifier(Object obj)
    {
        LogQos o = (LogQos) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        LogQos item = (LogQos) obj;
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
            int	orgUrlId		 = rs.getInt("orgUrlId");
            item.setOrgUrlId(orgUrlId);
            int	urlId		 = rs.getInt("urlId");
            item.setUrlId(urlId);
            int	videoId		 = rs.getInt("videoId");
            item.setVideoId(videoId);
            int	totalTime		 = rs.getInt("totalTime");
            item.setTotalTime(totalTime);
            int	startTime		 = rs.getInt("startTime");
            item.setStartTime(startTime);
            int	playTime		 = rs.getInt("playTime");
            item.setPlayTime(playTime);
            int	startType		 = rs.getInt("startType");
            item.setStartType(startType);
            int	startStatus		 = rs.getInt("startStatus");
            item.setStartStatus(startStatus);
            int	bufferNum		 = rs.getInt("bufferNum");
            item.setBufferNum(bufferNum);
            int	bufferTime		 = rs.getInt("bufferTime");
            item.setBufferTime(bufferTime);
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
        LogQos item = (LogQos) obj;

        String ret = 
            "`event`=" + item.getEvent()
            + ",`macId`=" + item.getMacId()
            + ",`ipId`=" + item.getIpId()
            + ",`ctime`=" + (((d=item.getCtime())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",`orgUrlId`=" + item.getOrgUrlId()
            + ",`urlId`=" + item.getUrlId()
            + ",`videoId`=" + item.getVideoId()
            + ",`totalTime`=" + item.getTotalTime()
            + ",`startTime`=" + item.getStartTime()
            + ",`playTime`=" + item.getPlayTime()
            + ",`startType`=" + item.getStartType()
            + ",`startStatus`=" + item.getStartStatus()
            + ",`bufferNum`=" + item.getBufferNum()
            + ",`bufferTime`=" + item.getBufferTime()

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`event`,`macId`,`ipId`,`ctime`,`orgUrlId`,`urlId`,`videoId`,`totalTime`,`startTime`,`playTime`,`startType`,`startStatus`,`bufferNum`,`bufferTime`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        LogQos item = (LogQos) obj;

        String ret = 
            "" + item.getEvent()
            + "," + item.getMacId()
            + "," + item.getIpId()
            + "," + (((d=item.getCtime())!=null)?("'"+df.format(d)+"'"):"NULL")
            + "," + item.getOrgUrlId()
            + "," + item.getUrlId()
            + "," + item.getVideoId()
            + "," + item.getTotalTime()
            + "," + item.getStartTime()
            + "," + item.getPlayTime()
            + "," + item.getStartType()
            + "," + item.getStartStatus()
            + "," + item.getBufferNum()
            + "," + item.getBufferTime()

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        LogQos o = (LogQos) obj;
        o.setId(auto_id);
    }
}
