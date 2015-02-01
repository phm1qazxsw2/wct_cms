package com.wct.report;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class IpInfoMgr extends dbo.Manager<IpInfo>
{
    private static IpInfoMgr _instance = null;

    IpInfoMgr() {}

    public synchronized static IpInfoMgr getInstance()
    {
        if (_instance==null) {
            _instance = new IpInfoMgr();
        }
        return _instance;
    }

    public IpInfoMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "t_cloud_ip_info";
    }

    protected Object makeBean()
    {
        return new IpInfo();
    }

    protected String getIdentifier(Object obj)
    {
        IpInfo o = (IpInfo) obj;
        return "`id` = "+o.getId();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        IpInfo item = (IpInfo) obj;
        try {
            int	id		 = rs.getInt("id");
            item.setId(id);
            String	begIp		 = rs.getString("begIp");
            item.setBegIp(begIp);
            String	endIp		 = rs.getString("endIp");
            item.setEndIp(endIp);
            String	country		 = rs.getString("country");
            item.setCountry(country);
            String	province		 = rs.getString("province");
            item.setProvince(province);
            String	city		 = rs.getString("city");
            item.setCity(city);
            String	region		 = rs.getString("region");
            item.setRegion(region);
            String	communications		 = rs.getString("communications");
            item.setCommunications(communications);
            long	begIpNum		 = 0;try { begIpNum = Long.parseLong(new String(rs.getBytes("begIpNum"))); } catch (Exception ee) {}
            item.setBegIpNum(begIpNum);
            long	endIpNum		 = 0;try { endIpNum = Long.parseLong(new String(rs.getBytes("endIpNum"))); } catch (Exception ee) {}
            item.setEndIpNum(endIpNum);
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
        IpInfo item = (IpInfo) obj;

        String ret = 
            "`begIp`='" + ServerTool.escapeString(item.getBegIp()) + "'"
            + ",`endIp`='" + ServerTool.escapeString(item.getEndIp()) + "'"
            + ",`country`='" + ServerTool.escapeString(item.getCountry()) + "'"
            + ",`province`='" + ServerTool.escapeString(item.getProvince()) + "'"
            + ",`city`='" + ServerTool.escapeString(item.getCity()) + "'"
            + ",`region`='" + ServerTool.escapeString(item.getRegion()) + "'"
            + ",`communications`='" + ServerTool.escapeString(item.getCommunications()) + "'"
            + ",`begIpNum`=" + item.getBegIpNum()
            + ",`endIpNum`=" + item.getEndIpNum()

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`begIp`,`endIp`,`country`,`province`,`city`,`region`,`communications`,`begIpNum`,`endIpNum`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        IpInfo item = (IpInfo) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getBegIp()) + "'"
            + ",'" + ServerTool.escapeString(item.getEndIp()) + "'"
            + ",'" + ServerTool.escapeString(item.getCountry()) + "'"
            + ",'" + ServerTool.escapeString(item.getProvince()) + "'"
            + ",'" + ServerTool.escapeString(item.getCity()) + "'"
            + ",'" + ServerTool.escapeString(item.getRegion()) + "'"
            + ",'" + ServerTool.escapeString(item.getCommunications()) + "'"
            + "," + item.getBegIpNum()
            + "," + item.getEndIpNum()

        ;
        return ret;
    }
    protected boolean isAutoId()
    {
        return true;
    }

    protected void setAutoId(Object obj, int auto_id)
    {
        IpInfo o = (IpInfo) obj;
        o.setId(auto_id);
    }
}
