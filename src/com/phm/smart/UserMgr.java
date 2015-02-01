package com.phm.smart;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class UserMgr extends dbo.Manager<User>
{
    private static UserMgr _instance = null;

    UserMgr() {}

    public synchronized static UserMgr getInstance()
    {
        if (_instance==null) {
            _instance = new UserMgr();
        }
        return _instance;
    }

    public UserMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "peter_user";
    }

    protected Object makeBean()
    {
        return new User();
    }

    protected String getIdentifier(Object obj)
    {
        User o = (User) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        User item = (User) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	phone		 = rs.getString("phone");
            item.setPhone(phone);
            String	jpush_id		 = rs.getString("jpush_id");
            item.setJpush_id(jpush_id);
            String	uuid		 = rs.getString("uuid");
            item.setUuid(uuid);
            java.util.Date	u_time		 = rs.getTimestamp("u_time");
            item.setU_time(u_time);
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
        User item = (User) obj;

        String ret = 
            "`phone`='" + ServerTool.escapeString(item.getPhone()) + "'"
            + ",`jpush_id`='" + ServerTool.escapeString(item.getJpush_id()) + "'"
            + ",`uuid`='" + ServerTool.escapeString(item.getUuid()) + "'"
            + ",`u_time`=" + (((d=item.getU_time())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`phone`,`jpush_id`,`uuid`,`u_time`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        User item = (User) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getPhone()) + "'"
            + ",'" + ServerTool.escapeString(item.getJpush_id()) + "'"
            + ",'" + ServerTool.escapeString(item.getUuid()) + "'"
            + "," + (((d=item.getU_time())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        User o = (User) obj;
        o.setId(auto_id);
    }
}
