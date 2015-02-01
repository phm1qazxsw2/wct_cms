package com.wct.ad;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class AdCollectMgr extends dbo.Manager<AdCollect>
{
    private static AdCollectMgr _instance = null;

    AdCollectMgr() {}

    public synchronized static AdCollectMgr getInstance()
    {
        if (_instance==null) {
            _instance = new AdCollectMgr();
        }
        return _instance;
    }

    public AdCollectMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "ad_collect";
    }

    protected Object makeBean()
    {
        return new AdCollect();
    }

    protected String getIdentifier(Object obj)
    {
        AdCollect o = (AdCollect) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        AdCollect item = (AdCollect) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	ad_ids		 = rs.getString("ad_ids");
            item.setAd_ids(ad_ids);
            int	ad_id		 = rs.getInt("ad_id");
            item.setAd_id(ad_id);
            int	last_record_id		 = rs.getInt("last_record_id");
            item.setLast_record_id(last_record_id);
            String	reporting_urls		 = rs.getString("reporting_urls");
            item.setReporting_urls(reporting_urls);
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
        AdCollect item = (AdCollect) obj;

        String ret = 
            "`ad_ids`='" + ServerTool.escapeString(item.getAd_ids()) + "'"
            + ",`ad_id`=" + item.getAd_id()
            + ",`last_record_id`=" + item.getLast_record_id()
            + ",`reporting_urls`='" + ServerTool.escapeString(item.getReporting_urls()) + "'"

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`ad_ids`,`ad_id`,`last_record_id`,`reporting_urls`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        AdCollect item = (AdCollect) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getAd_ids()) + "'"
            + "," + item.getAd_id()
            + "," + item.getLast_record_id()
            + ",'" + ServerTool.escapeString(item.getReporting_urls()) + "'"

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        AdCollect o = (AdCollect) obj;
        o.setId(auto_id);
    }
}
