package com.phm.smart;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class UserChannelMgr extends dbo.Manager<UserChannel>
{
    private static UserChannelMgr _instance = null;

    UserChannelMgr() {}

    public synchronized static UserChannelMgr getInstance()
    {
        if (_instance==null) {
            _instance = new UserChannelMgr();
        }
        return _instance;
    }

    public UserChannelMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "peter_user_channel";
    }

    protected Object makeBean()
    {
        return new UserChannel();
    }

    protected String getIdentifier(Object obj)
    {
        UserChannel o = (UserChannel) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        UserChannel item = (UserChannel) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            int	user_id		 = rs.getInt("user_id");
            item.setUser_id(user_id);
            int	channel_id		 = rs.getInt("channel_id");
            item.setChannel_id(channel_id);
            String	subtitle		 = rs.getString("subtitle");
            item.setSubtitle(subtitle);
            int	unread		 = rs.getInt("unread");
            item.setUnread(unread);
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
        UserChannel item = (UserChannel) obj;

        String ret = 
            "`user_id`=" + item.getUser_id()
            + ",`channel_id`=" + item.getChannel_id()
            + ",`subtitle`='" + ServerTool.escapeString(item.getSubtitle()) + "'"
            + ",`unread`=" + item.getUnread()

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`user_id`,`channel_id`,`subtitle`,`unread`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        UserChannel item = (UserChannel) obj;

        String ret = 
            "" + item.getUser_id()
            + "," + item.getChannel_id()
            + ",'" + ServerTool.escapeString(item.getSubtitle()) + "'"
            + "," + item.getUnread()

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        UserChannel o = (UserChannel) obj;
        o.setId(auto_id);
    }
}
