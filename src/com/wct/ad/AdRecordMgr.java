package com.wct.ad;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class AdRecordMgr extends dbo.Manager<AdRecord>
{
    private static AdRecordMgr _instance = null;

    AdRecordMgr() {}

    public synchronized static AdRecordMgr getInstance()
    {
        if (_instance==null) {
            _instance = new AdRecordMgr();
        }
        return _instance;
    }

    public AdRecordMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "t_cloud_ad_advert_record";
    }

    protected Object makeBean()
    {
        return new AdRecord();
    }

    protected String getIdentifier(Object obj)
    {
        AdRecord o = (AdRecord) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        AdRecord item = (AdRecord) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	device_code		 = rs.getString("device_code");
            item.setDevice_code(device_code);
            String	device_ip		 = rs.getString("device_ip");
            item.setDevice_ip(device_ip);
            int	ad_id		 = rs.getInt("ad_id");
            item.setAd_id(ad_id);
            java.util.Date	advert_time		 = rs.getTimestamp("advert_time");
            item.setAdvert_time(advert_time);
            int	counts		 = rs.getInt("counts");
            item.setCounts(counts);
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
        AdRecord item = (AdRecord) obj;

        String ret = 
            "`device_code`='" + ServerTool.escapeString(item.getDevice_code()) + "'"
            + ",`device_ip`='" + ServerTool.escapeString(item.getDevice_ip()) + "'"
            + ",`ad_id`=" + item.getAd_id()
            + ",`advert_time`=" + (((d=item.getAdvert_time())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",`counts`=" + item.getCounts()

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`device_code`,`device_ip`,`ad_id`,`advert_time`,`counts`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        AdRecord item = (AdRecord) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getDevice_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getDevice_ip()) + "'"
            + "," + item.getAd_id()
            + "," + (((d=item.getAdvert_time())!=null)?("'"+df.format(d)+"'"):"NULL")
            + "," + item.getCounts()

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        AdRecord o = (AdRecord) obj;
        o.setId(auto_id);
    }
}
