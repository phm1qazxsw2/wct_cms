package com.wct.report;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class RandioQRMgr extends dbo.Manager<RandioQR>
{
    private static RandioQRMgr _instance = null;

    RandioQRMgr() {}

    public synchronized static RandioQRMgr getInstance()
    {
        if (_instance==null) {
            _instance = new RandioQRMgr();
        }
        return _instance;
    }

    public RandioQRMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "randio_qr_scan";
    }

    protected Object makeBean()
    {
        return new RandioQR();
    }

    protected String getIdentifier(Object obj)
    {
        RandioQR o = (RandioQR) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        RandioQR item = (RandioQR) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	ip		 = rs.getString("ip");
            item.setIp(ip);
            java.util.Date	ctime		 = rs.getTimestamp("ctime");
            item.setCtime(ctime);
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
        RandioQR item = (RandioQR) obj;

        String ret = 
            "`ip`='" + ServerTool.escapeString(item.getIp()) + "'"
            + ",`ctime`=" + (((d=item.getCtime())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`ip`,`ctime`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        RandioQR item = (RandioQR) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getIp()) + "'"
            + "," + (((d=item.getCtime())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        RandioQR o = (RandioQR) obj;
        o.setId(auto_id);
    }
}
