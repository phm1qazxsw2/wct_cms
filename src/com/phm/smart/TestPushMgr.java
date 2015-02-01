package com.phm.smart;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class TestPushMgr extends dbo.Manager<TestPush>
{
    private static TestPushMgr _instance = null;

    TestPushMgr() {}

    public synchronized static TestPushMgr getInstance()
    {
        if (_instance==null) {
            _instance = new TestPushMgr();
        }
        return _instance;
    }

    public TestPushMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "peter_test_push";
    }

    protected Object makeBean()
    {
        return new TestPush();
    }

    protected String getIdentifier(Object obj)
    {
        TestPush o = (TestPush) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        TestPush item = (TestPush) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            int	type		 = rs.getInt("type");
            item.setType(type);
            String	uuid		 = rs.getString("uuid");
            item.setUuid(uuid);
            String	token		 = rs.getString("token");
            item.setToken(token);
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
        TestPush item = (TestPush) obj;

        String ret = 
            "`type`=" + item.getType()
            + ",`uuid`='" + ServerTool.escapeString(item.getUuid()) + "'"
            + ",`token`='" + ServerTool.escapeString(item.getToken()) + "'"
            + ",`u_time`=" + (((d=item.getU_time())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`type`,`uuid`,`token`,`u_time`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        TestPush item = (TestPush) obj;

        String ret = 
            "" + item.getType()
            + ",'" + ServerTool.escapeString(item.getUuid()) + "'"
            + ",'" + ServerTool.escapeString(item.getToken()) + "'"
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
        TestPush o = (TestPush) obj;
        o.setId(auto_id);
    }
}
