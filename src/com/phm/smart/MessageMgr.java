package com.phm.smart;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class MessageMgr extends dbo.Manager<Message>
{
    private static MessageMgr _instance = null;

    MessageMgr() {}

    public synchronized static MessageMgr getInstance()
    {
        if (_instance==null) {
            _instance = new MessageMgr();
        }
        return _instance;
    }

    public MessageMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "peter_message";
    }

    protected Object makeBean()
    {
        return new Message();
    }

    protected String getIdentifier(Object obj)
    {
        Message o = (Message) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        Message item = (Message) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            int	channel_id		 = rs.getInt("channel_id");
            item.setChannel_id(channel_id);
            String	title		 = rs.getString("title");
            item.setTitle(title);
            String	text		 = rs.getString("text");
            item.setText(text);
            java.util.Date	created		 = rs.getTimestamp("created");
            item.setCreated(created);
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
        Message item = (Message) obj;

        String ret = 
            "`channel_id`=" + item.getChannel_id()
            + ",`title`='" + ServerTool.escapeString(item.getTitle()) + "'"
            + ",`text`='" + ServerTool.escapeString(item.getText()) + "'"
            + ",`created`=" + (((d=item.getCreated())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`channel_id`,`title`,`text`,`created`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        Message item = (Message) obj;

        String ret = 
            "" + item.getChannel_id()
            + ",'" + ServerTool.escapeString(item.getTitle()) + "'"
            + ",'" + ServerTool.escapeString(item.getText()) + "'"
            + "," + (((d=item.getCreated())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        Message o = (Message) obj;
        o.setId(auto_id);
    }
}
