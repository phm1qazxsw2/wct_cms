

import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class BIDeviceMgr extends dbo.Manager<BIDevice>
{
    private static BIDeviceMgr _instance = null;

    BIDeviceMgr() {}

    public synchronized static BIDeviceMgr getInstance()
    {
        if (_instance==null) {
            _instance = new BIDeviceMgr();
        }
        return _instance;
    }

    public BIDeviceMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "bi_device_login";
    }

    protected Object makeBean()
    {
        return new BIDevice();
    }

    protected String getIdentifier(Object obj)
    {
        BIDevice o = (BIDevice) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        BIDevice item = (BIDevice) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	mac		 = rs.getString("mac");
            item.setMac(mac);
            java.util.Date	loginDate		 = rs.getTimestamp("loginDate");
            item.setLoginDate(loginDate);
            String	ip		 = rs.getString("ip");
            item.setIp(ip);
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
        BIDevice item = (BIDevice) obj;

        String ret = 
            "`mac`='" + ServerTool.escapeString(item.getMac()) + "'"
            + ",`loginDate`=" + (((d=item.getLoginDate())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",`ip`='" + ServerTool.escapeString(item.getIp()) + "'"

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`mac`,`loginDate`,`ip`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        BIDevice item = (BIDevice) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getMac()) + "'"
            + "," + (((d=item.getLoginDate())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",'" + ServerTool.escapeString(item.getIp()) + "'"

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        BIDevice o = (BIDevice) obj;
        o.setId(auto_id);
    }
}
