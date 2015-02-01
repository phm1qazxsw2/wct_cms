package com.wct.report;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class RandioQrScanMgr extends dbo.Manager<RandioQrScan>
{
    private static RandioQrScanMgr _instance = null;

    RandioQrScanMgr() {}

    public synchronized static RandioQrScanMgr getInstance()
    {
        if (_instance==null) {
            _instance = new RandioQrScanMgr();
        }
        return _instance;
    }

    public RandioQrScanMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "randio_qr_scan";
    }

    protected Object makeBean()
    {
        return new RandioQrScan();
    }

    protected String getIdentifier(Object obj)
    {
        RandioQrScan o = (RandioQrScan) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        RandioQrScan item = (RandioQrScan) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	ip		 = rs.getString("ip");
            item.setIp(ip);
            java.util.Date	ctime		 = rs.getTimestamp("ctime");
            item.setCtime(ctime);
            int	type		 = rs.getInt("type");
            item.setType(type);
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
        RandioQrScan item = (RandioQrScan) obj;

        String ret = 
            "`ip`='" + ServerTool.escapeString(item.getIp()) + "'"
            + ",`ctime`=" + (((d=item.getCtime())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",`type`=" + item.getType()

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`ip`,`ctime`,`type`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        RandioQrScan item = (RandioQrScan) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getIp()) + "'"
            + "," + (((d=item.getCtime())!=null)?("'"+df.format(d)+"'"):"NULL")
            + "," + item.getType()

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        RandioQrScan o = (RandioQrScan) obj;
        o.setId(auto_id);
    }
}
