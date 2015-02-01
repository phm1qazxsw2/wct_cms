package com.wct.vod;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class VideoChannelMgr extends dbo.Manager<VideoChannel>
{
    private static VideoChannelMgr _instance = null;

    VideoChannelMgr() {}

    public synchronized static VideoChannelMgr getInstance()
    {
        if (_instance==null) {
            _instance = new VideoChannelMgr();
        }
        return _instance;
    }

    public VideoChannelMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "video_channel";
    }

    protected Object makeBean()
    {
        return new VideoChannel();
    }

    protected String getIdentifier(Object obj)
    {
        VideoChannel o = (VideoChannel) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        VideoChannel item = (VideoChannel) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            int	video_id		 = rs.getInt("video_id");
            item.setVideo_id(video_id);
            int	channel_id		 = rs.getInt("channel_id");
            item.setChannel_id(channel_id);
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
        VideoChannel item = (VideoChannel) obj;

        String ret = 
            "`video_id`=" + item.getVideo_id()
            + ",`channel_id`=" + item.getChannel_id()

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`video_id`,`channel_id`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        VideoChannel item = (VideoChannel) obj;

        String ret = 
            "" + item.getVideo_id()
            + "," + item.getChannel_id()

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        VideoChannel o = (VideoChannel) obj;
        o.setId(auto_id);
    }
}
