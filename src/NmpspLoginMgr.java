

import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class NmpspLoginMgr extends dbo.Manager<NmpspLogin>
{
    private static NmpspLoginMgr _instance = null;

    NmpspLoginMgr() {}

    public synchronized static NmpspLoginMgr getInstance()
    {
        if (_instance==null) {
            _instance = new NmpspLoginMgr();
        }
        return _instance;
    }

    public NmpspLoginMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "t_cloud_challenger_info";
    }

    protected Object makeBean()
    {
        return new NmpspLogin();
    }

    protected String getIdentifier(Object obj)
    {
        NmpspLogin o = (NmpspLogin) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        NmpspLogin item = (NmpspLogin) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	device_mac		 = rs.getString("device_mac");
            item.setDevice_mac(device_mac);
            java.util.Date	challenger_times_date		 = rs.getTimestamp("challenger_times_date");
            item.setChallenger_times_date(challenger_times_date);
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
        NmpspLogin item = (NmpspLogin) obj;

        String ret = 
            "`device_mac`='" + ServerTool.escapeString(item.getDevice_mac()) + "'"
            + ",`challenger_times_date`=" + (((d=item.getChallenger_times_date())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`device_mac`,`challenger_times_date`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        NmpspLogin item = (NmpspLogin) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getDevice_mac()) + "'"
            + "," + (((d=item.getChallenger_times_date())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        NmpspLogin o = (NmpspLogin) obj;
        o.setId(auto_id);
    }
}
