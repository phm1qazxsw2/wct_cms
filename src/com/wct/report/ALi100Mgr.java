package com.wct.report;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class ALi100Mgr extends dbo.Manager<ALi100>
{
    private static ALi100Mgr _instance = null;

    ALi100Mgr() {}

    public synchronized static ALi100Mgr getInstance()
    {
        if (_instance==null) {
            _instance = new ALi100Mgr();
        }
        return _instance;
    }

    public ALi100Mgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "ali100";
    }

    protected Object makeBean()
    {
        return new ALi100();
    }

    protected String getIdentifier(Object obj)
    {
        ALi100 o = (ALi100) obj;
        return null;
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        ALi100 item = (ALi100) obj;
        try {
            String	device_mac		 = rs.getString("device_mac");
            item.setDevice_mac(device_mac);
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	name		 = rs.getString("name");
            item.setName(name);
            String	mac		 = rs.getString("mac");
            item.setMac(mac);
            String	area		 = rs.getString("area");
            item.setArea(area);
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
        ALi100 item = (ALi100) obj;

        String ret = 
            "`device_mac`='" + ServerTool.escapeString(item.getDevice_mac()) + "'"
            + ",`id`=" + item.getId()
            + ",`name`='" + ServerTool.escapeString(item.getName()) + "'"
            + ",`mac`='" + ServerTool.escapeString(item.getMac()) + "'"
            + ",`area`='" + ServerTool.escapeString(item.getArea()) + "'"

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`device_mac`,`id`,`name`,`mac`,`area`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        ALi100 item = (ALi100) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getDevice_mac()) + "'"
            + "," + item.getId()
            + ",'" + ServerTool.escapeString(item.getName()) + "'"
            + ",'" + ServerTool.escapeString(item.getMac()) + "'"
            + ",'" + ServerTool.escapeString(item.getArea()) + "'"

        ;
        return ret;
    }
}
