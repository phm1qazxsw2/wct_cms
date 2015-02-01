package com.wct.vod;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class ChannelMgr extends dbo.Manager<Channel>
{
    private static ChannelMgr _instance = null;

    ChannelMgr() {}

    public synchronized static ChannelMgr getInstance()
    {
        if (_instance==null) {
            _instance = new ChannelMgr();
        }
        return _instance;
    }

    public ChannelMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "channel";
    }

    protected Object makeBean()
    {
        return new Channel();
    }

    protected String getIdentifier(Object obj)
    {
        Channel o = (Channel) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        Channel item = (Channel) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	name		 = rs.getString("name");
            item.setName(name);
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
        Channel item = (Channel) obj;

        String ret = 
            "`name`='" + ServerTool.escapeString(item.getName()) + "'"

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`name`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        Channel item = (Channel) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getName()) + "'"

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        Channel o = (Channel) obj;
        o.setId(auto_id);
    }
}
