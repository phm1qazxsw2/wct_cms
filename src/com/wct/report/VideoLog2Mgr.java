package com.wct.report;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class VideoLog2Mgr extends dbo.Manager<VideoLog2>
{
    private static VideoLog2Mgr _instance = null;

    VideoLog2Mgr() {}

    public synchronized static VideoLog2Mgr getInstance()
    {
        if (_instance==null) {
            _instance = new VideoLog2Mgr();
        }
        return _instance;
    }

    public VideoLog2Mgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "vod_video_log";
    }

    protected Object makeBean()
    {
        return new VideoLog2();
    }

    protected String getIdentifier(Object obj)
    {
        VideoLog2 o = (VideoLog2) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        VideoLog2 item = (VideoLog2) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	videoUrl		 = rs.getString("videoUrl");
            item.setVideoUrl(videoUrl);
            String	videoFeature		 = rs.getString("videoFeature");
            item.setVideoFeature(videoFeature);
            String	videoType		 = rs.getString("videoType");
            item.setVideoType(videoType);
            String	ip		 = rs.getString("ip");
            item.setIp(ip);
            String	appName		 = rs.getString("appName");
            item.setAppName(appName);
            java.util.Date	createTime		 = rs.getTimestamp("createTime");
            item.setCreateTime(createTime);
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
        VideoLog2 item = (VideoLog2) obj;

        String ret = 
            "`videoUrl`='" + ServerTool.escapeString(item.getVideoUrl()) + "'"
            + ",`videoFeature`='" + ServerTool.escapeString(item.getVideoFeature()) + "'"
            + ",`videoType`='" + ServerTool.escapeString(item.getVideoType()) + "'"
            + ",`ip`='" + ServerTool.escapeString(item.getIp()) + "'"
            + ",`appName`='" + ServerTool.escapeString(item.getAppName()) + "'"
            + ",`createTime`=" + (((d=item.getCreateTime())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`videoUrl`,`videoFeature`,`videoType`,`ip`,`appName`,`createTime`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        VideoLog2 item = (VideoLog2) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getVideoUrl()) + "'"
            + ",'" + ServerTool.escapeString(item.getVideoFeature()) + "'"
            + ",'" + ServerTool.escapeString(item.getVideoType()) + "'"
            + ",'" + ServerTool.escapeString(item.getIp()) + "'"
            + ",'" + ServerTool.escapeString(item.getAppName()) + "'"
            + "," + (((d=item.getCreateTime())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        VideoLog2 o = (VideoLog2) obj;
        o.setId(auto_id);
    }
}
