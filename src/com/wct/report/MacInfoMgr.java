package com.wct.report;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class MacInfoMgr extends dbo.Manager<MacInfo>
{
    private static MacInfoMgr _instance = null;

    MacInfoMgr() {}

    public synchronized static MacInfoMgr getInstance()
    {
        if (_instance==null) {
            _instance = new MacInfoMgr();
        }
        return _instance;
    }

    public MacInfoMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "t_cloud_mac_info";
    }

    protected Object makeBean()
    {
        return new MacInfo();
    }

    protected String getIdentifier(Object obj)
    {
        MacInfo o = (MacInfo) obj;
        return null;
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        MacInfo item = (MacInfo) obj;
        try {
            String	device_mac		 = rs.getString("device_mac");
            item.setDevice_mac(device_mac);
            String	chip_code		 = rs.getString("chip_code");
            item.setChip_code(chip_code);
            int	status		 = rs.getInt("status");
            item.setStatus(status);
            String	vid		 = rs.getString("vid");
            item.setVid(vid);
            int	oemId		 = rs.getInt("oemId");
            item.setOemId(oemId);
            String	orderNo		 = rs.getString("orderNo");
            item.setOrderNo(orderNo);
            int	needVid		 = rs.getInt("needVid");
            item.setNeedVid(needVid);
            java.util.Date	createDate		 = rs.getTimestamp("createDate");
            item.setCreateDate(createDate);
            String	chipType		 = rs.getString("chipType");
            item.setChipType(chipType);
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
        MacInfo item = (MacInfo) obj;

        String ret = 
            "`device_mac`='" + ServerTool.escapeString(item.getDevice_mac()) + "'"
            + ",`chip_code`='" + ServerTool.escapeString(item.getChip_code()) + "'"
            + ",`status`=" + item.getStatus()
            + ",`vid`='" + ServerTool.escapeString(item.getVid()) + "'"
            + ",`oemId`=" + item.getOemId()
            + ",`orderNo`='" + ServerTool.escapeString(item.getOrderNo()) + "'"
            + ",`needVid`=" + item.getNeedVid()
            + ",`createDate`=" + (((d=item.getCreateDate())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",`chipType`='" + ServerTool.escapeString(item.getChipType()) + "'"

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`device_mac`,`chip_code`,`status`,`vid`,`oemId`,`orderNo`,`needVid`,`createDate`,`chipType`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        MacInfo item = (MacInfo) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getDevice_mac()) + "'"
            + ",'" + ServerTool.escapeString(item.getChip_code()) + "'"
            + "," + item.getStatus()
            + ",'" + ServerTool.escapeString(item.getVid()) + "'"
            + "," + item.getOemId()
            + ",'" + ServerTool.escapeString(item.getOrderNo()) + "'"
            + "," + item.getNeedVid()
            + "," + (((d=item.getCreateDate())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",'" + ServerTool.escapeString(item.getChipType()) + "'"

        ;
        return ret;
    }
}
