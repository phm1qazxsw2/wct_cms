package com.wct.vod;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class EpisodeMgr extends dbo.Manager<Episode>
{
    private static EpisodeMgr _instance = null;

    EpisodeMgr() {}

    public synchronized static EpisodeMgr getInstance()
    {
        if (_instance==null) {
            _instance = new EpisodeMgr();
        }
        return _instance;
    }

    public EpisodeMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "episode";
    }

    protected Object makeBean()
    {
        return new Episode();
    }

    protected String getIdentifier(Object obj)
    {
        Episode o = (Episode) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        Episode item = (Episode) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            int	video_id		 = rs.getInt("video_id");
            item.setVideo_id(video_id);
            String	name		 = rs.getString("name");
            item.setName(name);
            String	num		 = rs.getString("num");
            item.setNum(num);
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
        Episode item = (Episode) obj;

        String ret = 
            "`video_id`=" + item.getVideo_id()
            + ",`name`='" + ServerTool.escapeString(item.getName()) + "'"
            + ",`num`='" + ServerTool.escapeString(item.getNum()) + "'"

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`video_id`,`name`,`num`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        Episode item = (Episode) obj;

        String ret = 
            "" + item.getVideo_id()
            + ",'" + ServerTool.escapeString(item.getName()) + "'"
            + ",'" + ServerTool.escapeString(item.getNum()) + "'"

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        Episode o = (Episode) obj;
        o.setId(auto_id);
    }
}
