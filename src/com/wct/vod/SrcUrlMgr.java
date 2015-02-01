package com.wct.vod;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class SrcUrlMgr extends dbo.Manager<SrcUrl>
{
    private static SrcUrlMgr _instance = null;

    SrcUrlMgr() {}

    public synchronized static SrcUrlMgr getInstance()
    {
        if (_instance==null) {
            _instance = new SrcUrlMgr();
        }
        return _instance;
    }

    public SrcUrlMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "source_url";
    }

    protected Object makeBean()
    {
        return new SrcUrl();
    }

    protected String getIdentifier(Object obj)
    {
        SrcUrl o = (SrcUrl) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        SrcUrl item = (SrcUrl) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            int	episode_id		 = rs.getInt("episode_id");
            item.setEpisode_id(episode_id);
            int	video_id		 = rs.getInt("video_id");
            item.setVideo_id(video_id);
            String	site		 = rs.getString("site");
            item.setSite(site);
            String	url		 = rs.getString("url");
            item.setUrl(url);
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
        SrcUrl item = (SrcUrl) obj;

        String ret = 
            "`episode_id`=" + item.getEpisode_id()
            + ",`video_id`=" + item.getVideo_id()
            + ",`site`='" + ServerTool.escapeString(item.getSite()) + "'"
            + ",`url`='" + ServerTool.escapeString(item.getUrl()) + "'"

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`episode_id`,`video_id`,`site`,`url`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        SrcUrl item = (SrcUrl) obj;

        String ret = 
            "" + item.getEpisode_id()
            + "," + item.getVideo_id()
            + ",'" + ServerTool.escapeString(item.getSite()) + "'"
            + ",'" + ServerTool.escapeString(item.getUrl()) + "'"

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        SrcUrl o = (SrcUrl) obj;
        o.setId(auto_id);
    }
}
