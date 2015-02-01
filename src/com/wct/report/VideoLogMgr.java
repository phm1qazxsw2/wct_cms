package com.wct.report;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class VideoLogMgr extends dbo.Manager<VideoLog>
{
    private static VideoLogMgr _instance = null;

    VideoLogMgr() {}

    public synchronized static VideoLogMgr getInstance()
    {
        if (_instance==null) {
            _instance = new VideoLogMgr();
        }
        return _instance;
    }

    public VideoLogMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "##";
    }

    protected Object makeBean()
    {
        return new VideoLog();
    }

    protected String getIdentifier(Object obj)
    {
        VideoLog o = (VideoLog) obj;
        return null;
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        VideoLog item = (VideoLog) obj;
        try {
            String	videoUrl		 = rs.getString("videoUrl");
            item.setVideoUrl(videoUrl);
            int	pageview		 = rs.getInt("pageview");
            item.setPageview(pageview);
            int	visit		 = rs.getInt("visit");
            item.setVisit(visit);
            java.util.Date	createDate		 = rs.getTimestamp("createDate");
            item.setCreateDate(createDate);
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
        VideoLog item = (VideoLog) obj;

        String ret = 
            "`videoUrl`='" + ServerTool.escapeString(item.getVideoUrl()) + "'"
            + ",`pageview`=" + item.getPageview()
            + ",`visit`=" + item.getVisit()
            + ",`createDate`=" + (((d=item.getCreateDate())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`videoUrl`,`pageview`,`visit`,`createDate`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        VideoLog item = (VideoLog) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getVideoUrl()) + "'"
            + "," + item.getPageview()
            + "," + item.getVisit()
            + "," + (((d=item.getCreateDate())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }
}
