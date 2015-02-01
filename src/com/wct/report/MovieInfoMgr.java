package com.wct.report;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class MovieInfoMgr extends dbo.Manager<MovieInfo>
{
    private static MovieInfoMgr _instance = null;

    MovieInfoMgr() {}

    public synchronized static MovieInfoMgr getInstance()
    {
        if (_instance==null) {
            _instance = new MovieInfoMgr();
        }
        return _instance;
    }

    public MovieInfoMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "t_movie_info";
    }

    protected Object makeBean()
    {
        return new MovieInfo();
    }

    protected String getIdentifier(Object obj)
    {
        MovieInfo o = (MovieInfo) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        MovieInfo item = (MovieInfo) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	name		 = rs.getString("name");
            item.setName(name);
            String	url		 = rs.getString("url");
            item.setUrl(url);
            String	type		 = rs.getString("type");
            item.setType(type);
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
        MovieInfo item = (MovieInfo) obj;

        String ret = 
            "`name`='" + ServerTool.escapeString(item.getName()) + "'"
            + ",`url`='" + ServerTool.escapeString(item.getUrl()) + "'"
            + ",`type`='" + ServerTool.escapeString(item.getType()) + "'"

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`name`,`url`,`type`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        MovieInfo item = (MovieInfo) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getName()) + "'"
            + ",'" + ServerTool.escapeString(item.getUrl()) + "'"
            + ",'" + ServerTool.escapeString(item.getType()) + "'"

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        MovieInfo o = (MovieInfo) obj;
        o.setId(auto_id);
    }
}
