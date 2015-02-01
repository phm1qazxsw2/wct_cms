package com.wct.report;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class UpgradeOkMgr extends dbo.Manager<UpgradeOk>
{
    private static UpgradeOkMgr _instance = null;

    UpgradeOkMgr() {}

    public synchronized static UpgradeOkMgr getInstance()
    {
        if (_instance==null) {
            _instance = new UpgradeOkMgr();
        }
        return _instance;
    }

    public UpgradeOkMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "t_cloud_softwareinfo";
    }

    protected Object makeBean()
    {
        return new UpgradeOk();
    }

    protected String getIdentifier(Object obj)
    {
        UpgradeOk o = (UpgradeOk) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        UpgradeOk item = (UpgradeOk) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	mac		 = rs.getString("mac");
            item.setMac(mac);
            String	curSoftware		 = rs.getString("curSoftware");
            item.setCurSoftware(curSoftware);
            String	toSoftware		 = rs.getString("toSoftware");
            item.setToSoftware(toSoftware);
            String	md5		 = rs.getString("md5");
            item.setMd5(md5);
            int	upgradeTime		 = rs.getInt("upgradeTime");
            item.setUpgradeTime(upgradeTime);
            int	downloadSpeed		 = rs.getInt("downloadSpeed");
            item.setDownloadSpeed(downloadSpeed);
            java.util.Date	createDate		 = rs.getTimestamp("createDate");
            item.setCreateDate(createDate);
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
        UpgradeOk item = (UpgradeOk) obj;

        String ret = 
            "`mac`='" + ServerTool.escapeString(item.getMac()) + "'"
            + ",`curSoftware`='" + ServerTool.escapeString(item.getCurSoftware()) + "'"
            + ",`toSoftware`='" + ServerTool.escapeString(item.getToSoftware()) + "'"
            + ",`md5`='" + ServerTool.escapeString(item.getMd5()) + "'"
            + ",`upgradeTime`=" + item.getUpgradeTime()
            + ",`downloadSpeed`=" + item.getDownloadSpeed()
            + ",`createDate`=" + (((d=item.getCreateDate())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`mac`,`curSoftware`,`toSoftware`,`md5`,`upgradeTime`,`downloadSpeed`,`createDate`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        UpgradeOk item = (UpgradeOk) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getMac()) + "'"
            + ",'" + ServerTool.escapeString(item.getCurSoftware()) + "'"
            + ",'" + ServerTool.escapeString(item.getToSoftware()) + "'"
            + ",'" + ServerTool.escapeString(item.getMd5()) + "'"
            + "," + item.getUpgradeTime()
            + "," + item.getDownloadSpeed()
            + "," + (((d=item.getCreateDate())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        UpgradeOk o = (UpgradeOk) obj;
        o.setId(auto_id);
    }
}
