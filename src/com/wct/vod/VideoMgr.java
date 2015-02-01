package com.wct.vod;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class VideoMgr extends dbo.Manager<Video>
{
    private static VideoMgr _instance = null;

    VideoMgr() {}

    public synchronized static VideoMgr getInstance()
    {
        if (_instance==null) {
            _instance = new VideoMgr();
        }
        return _instance;
    }

    public VideoMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "video";
    }

    protected Object makeBean()
    {
        return new Video();
    }

    protected String getIdentifier(Object obj)
    {
        Video o = (Video) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        Video item = (Video) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	key		 = rs.getString("key");
            item.setKey(key);
            java.util.Date	updated		 = rs.getTimestamp("updated");
            item.setUpdated(updated);
            String	name		 = rs.getString("name");
            item.setName(name);
            String	pic		 = rs.getString("pic");
            item.setPic(pic);
            int	type		 = rs.getInt("type");
            item.setType(type);
            int	check_new		 = rs.getInt("check_new");
            item.setCheck_new(check_new);
            int	episode_num		 = rs.getInt("episode_num");
            item.setEpisode_num(episode_num);
            String	short_desc		 = rs.getString("short_desc");
            item.setShort_desc(short_desc);
            String	long_desc		 = rs.getString("long_desc");
            item.setLong_desc(long_desc);
            String	area		 = rs.getString("area");
            item.setArea(area);
            String	director		 = rs.getString("director");
            item.setDirector(director);
            String	actor		 = rs.getString("actor");
            item.setActor(actor);
            String	hao123url		 = rs.getString("hao123url");
            item.setHao123url(hao123url);
            int	year		 = rs.getInt("year");
            item.setYear(year);
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
        Video item = (Video) obj;

        String ret = 
            "`key`='" + ServerTool.escapeString(item.getKey()) + "'"
            + ",`updated`=" + (((d=item.getUpdated())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",`name`='" + ServerTool.escapeString(item.getName()) + "'"
            + ",`pic`='" + ServerTool.escapeString(item.getPic()) + "'"
            + ",`type`=" + item.getType()
            + ",`check_new`=" + item.getCheck_new()
            + ",`episode_num`=" + item.getEpisode_num()
            + ",`short_desc`='" + ServerTool.escapeString(item.getShort_desc()) + "'"
            + ",`long_desc`='" + ServerTool.escapeString(item.getLong_desc()) + "'"
            + ",`area`='" + ServerTool.escapeString(item.getArea()) + "'"
            + ",`director`='" + ServerTool.escapeString(item.getDirector()) + "'"
            + ",`actor`='" + ServerTool.escapeString(item.getActor()) + "'"
            + ",`hao123url`='" + ServerTool.escapeString(item.getHao123url()) + "'"
            + ",`year`=" + item.getYear()

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`key`,`updated`,`name`,`pic`,`type`,`check_new`,`episode_num`,`short_desc`,`long_desc`,`area`,`director`,`actor`,`hao123url`,`year`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        Video item = (Video) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getKey()) + "'"
            + "," + (((d=item.getUpdated())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",'" + ServerTool.escapeString(item.getName()) + "'"
            + ",'" + ServerTool.escapeString(item.getPic()) + "'"
            + "," + item.getType()
            + "," + item.getCheck_new()
            + "," + item.getEpisode_num()
            + ",'" + ServerTool.escapeString(item.getShort_desc()) + "'"
            + ",'" + ServerTool.escapeString(item.getLong_desc()) + "'"
            + ",'" + ServerTool.escapeString(item.getArea()) + "'"
            + ",'" + ServerTool.escapeString(item.getDirector()) + "'"
            + ",'" + ServerTool.escapeString(item.getActor()) + "'"
            + ",'" + ServerTool.escapeString(item.getHao123url()) + "'"
            + "," + item.getYear()

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        Video o = (Video) obj;
        o.setId(auto_id);
    }
}
