package com.wct.report;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class UpgradeFailMgr extends dbo.Manager<UpgradeFail>
{
    private static UpgradeFailMgr _instance = null;

    UpgradeFailMgr() {}

    public synchronized static UpgradeFailMgr getInstance()
    {
        if (_instance==null) {
            _instance = new UpgradeFailMgr();
        }
        return _instance;
    }

    public UpgradeFailMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "t_cloud_updatefailinfo";
    }

    protected Object makeBean()
    {
        return new UpgradeFail();
    }

    protected String getIdentifier(Object obj)
    {
        UpgradeFail o = (UpgradeFail) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        UpgradeFail item = (UpgradeFail) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	mac		 = rs.getString("mac");
            item.setMac(mac);
            String	curSoftware		 = rs.getString("curSoftware");
            item.setCurSoftware(curSoftware);
            String	toSoftware		 = rs.getString("toSoftware");
            item.setToSoftware(toSoftware);
            int	flow		 = rs.getInt("flow");
            item.setFlow(flow);
            java.util.Date	createDate		 = rs.getTimestamp("createDate");
            item.setCreateDate(createDate);
            String	errorCode		 = rs.getString("errorCode");
            item.setErrorCode(errorCode);
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
        UpgradeFail item = (UpgradeFail) obj;

        String ret = 
            "`mac`='" + ServerTool.escapeString(item.getMac()) + "'"
            + ",`curSoftware`='" + ServerTool.escapeString(item.getCurSoftware()) + "'"
            + ",`toSoftware`='" + ServerTool.escapeString(item.getToSoftware()) + "'"
            + ",`flow`=" + item.getFlow()
            + ",`createDate`=" + (((d=item.getCreateDate())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",`errorCode`='" + ServerTool.escapeString(item.getErrorCode()) + "'"

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`mac`,`curSoftware`,`toSoftware`,`flow`,`createDate`,`errorCode`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        UpgradeFail item = (UpgradeFail) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getMac()) + "'"
            + ",'" + ServerTool.escapeString(item.getCurSoftware()) + "'"
            + ",'" + ServerTool.escapeString(item.getToSoftware()) + "'"
            + "," + item.getFlow()
            + "," + (((d=item.getCreateDate())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",'" + ServerTool.escapeString(item.getErrorCode()) + "'"

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        UpgradeFail o = (UpgradeFail) obj;
        o.setId(auto_id);
    }
}
