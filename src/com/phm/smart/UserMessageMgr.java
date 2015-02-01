package com.phm.smart;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class UserMessageMgr extends dbo.Manager<UserMessage>
{
    private static UserMessageMgr _instance = null;

    UserMessageMgr() {}

    public synchronized static UserMessageMgr getInstance()
    {
        if (_instance==null) {
            _instance = new UserMessageMgr();
        }
        return _instance;
    }

    public UserMessageMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "peter_user_message";
    }

    protected Object makeBean()
    {
        return new UserMessage();
    }

    protected String getIdentifier(Object obj)
    {
        UserMessage o = (UserMessage) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        UserMessage item = (UserMessage) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            int	user_id		 = rs.getInt("user_id");
            item.setUser_id(user_id);
            int	message_id		 = rs.getInt("message_id");
            item.setMessage_id(message_id);
            java.util.Date	last_read		 = rs.getTimestamp("last_read");
            item.setLast_read(last_read);
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
        UserMessage item = (UserMessage) obj;

        String ret = 
            "`user_id`=" + item.getUser_id()
            + ",`message_id`=" + item.getMessage_id()
            + ",`last_read`=" + (((d=item.getLast_read())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`user_id`,`message_id`,`last_read`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        UserMessage item = (UserMessage) obj;

        String ret = 
            "" + item.getUser_id()
            + "," + item.getMessage_id()
            + "," + (((d=item.getLast_read())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        UserMessage o = (UserMessage) obj;
        o.setId(auto_id);
    }
}
