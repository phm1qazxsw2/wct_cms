package com.wct.report;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class AppLogMgr extends dbo.Manager<AppLog>
{
    private static AppLogMgr _instance = null;

    AppLogMgr() {}

    public synchronized static AppLogMgr getInstance()
    {
        if (_instance==null) {
            _instance = new AppLogMgr();
        }
        return _instance;
    }

    public AppLogMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "t_cloud_appactionlog";
    }

    protected Object makeBean()
    {
        return new AppLog();
    }

    protected String getIdentifier(Object obj)
    {
        AppLog o = (AppLog) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        AppLog item = (AppLog) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	chipId		 = rs.getString("chipId");
            item.setChipId(chipId);
            String	mac		 = rs.getString("mac");
            item.setMac(mac);
            String	appName		 = rs.getString("appName");
            item.setAppName(appName);
            java.util.Date	enterTime		 = rs.getTimestamp("enterTime");
            item.setEnterTime(enterTime);
            java.util.Date	leaveTime		 = rs.getTimestamp("leaveTime");
            item.setLeaveTime(leaveTime);
            java.util.Date	createTime		 = rs.getTimestamp("createTime");
            item.setCreateTime(createTime);
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
        AppLog item = (AppLog) obj;

        String ret = 
            "`chipId`='" + ServerTool.escapeString(item.getChipId()) + "'"
            + ",`mac`='" + ServerTool.escapeString(item.getMac()) + "'"
            + ",`appName`='" + ServerTool.escapeString(item.getAppName()) + "'"
            + ",`enterTime`=" + (((d=item.getEnterTime())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",`leaveTime`=" + (((d=item.getLeaveTime())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",`createTime`=" + (((d=item.getCreateTime())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`chipId`,`mac`,`appName`,`enterTime`,`leaveTime`,`createTime`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        AppLog item = (AppLog) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getChipId()) + "'"
            + ",'" + ServerTool.escapeString(item.getMac()) + "'"
            + ",'" + ServerTool.escapeString(item.getAppName()) + "'"
            + "," + (((d=item.getEnterTime())!=null)?("'"+df.format(d)+"'"):"NULL")
            + "," + (((d=item.getLeaveTime())!=null)?("'"+df.format(d)+"'"):"NULL")
            + "," + (((d=item.getCreateTime())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        AppLog o = (AppLog) obj;
        o.setId(auto_id);
    }
}
