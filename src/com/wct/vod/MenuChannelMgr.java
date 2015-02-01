package com.wct.vod;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class MenuChannelMgr extends dbo.Manager<MenuChannel>
{
    private static MenuChannelMgr _instance = null;

    MenuChannelMgr() {}

    public synchronized static MenuChannelMgr getInstance()
    {
        if (_instance==null) {
            _instance = new MenuChannelMgr();
        }
        return _instance;
    }

    public MenuChannelMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "menu_channel";
    }

    protected Object makeBean()
    {
        return new MenuChannel();
    }

    protected String getIdentifier(Object obj)
    {
        MenuChannel o = (MenuChannel) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        MenuChannel item = (MenuChannel) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            int	menu_id		 = rs.getInt("menu_id");
            item.setMenu_id(menu_id);
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
        MenuChannel item = (MenuChannel) obj;

        String ret = 
            "`menu_id`=" + item.getMenu_id()
            + ",`channel_id`=" + item.getChannel_id()

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`menu_id`,`channel_id`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        MenuChannel item = (MenuChannel) obj;

        String ret = 
            "" + item.getMenu_id()
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
        MenuChannel o = (MenuChannel) obj;
        o.setId(auto_id);
    }
}
