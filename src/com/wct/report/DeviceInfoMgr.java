package com.wct.report;


import dbo.*;
import java.util.Date;
import java.text.*;
import java.sql.*;

public class DeviceInfoMgr extends dbo.Manager<DeviceInfo>
{
    private static DeviceInfoMgr _instance = null;

    DeviceInfoMgr() {}

    public synchronized static DeviceInfoMgr getInstance()
    {
        if (_instance==null) {
            _instance = new DeviceInfoMgr();
        }
        return _instance;
    }

    public DeviceInfoMgr(int tran_id) throws Exception {
        super(tran_id);
    }

    protected String getTableName()
    {
        return "t_cloud_device_info";
    }

    protected Object makeBean()
    {
        return new DeviceInfo();
    }

    protected String getIdentifier(Object obj)
    {
        DeviceInfo o = (DeviceInfo) obj;
        return "`device_id` = "+o.getDevice_id();
    }

    protected void fillBean(ResultSet rs, Object obj, Connection con)
        throws Exception
    {
        DeviceInfo item = (DeviceInfo) obj;
        try {
            int	device_id		 = rs.getInt("device_id");
            item.setDevice_id(device_id);
            String	chip_code		 = rs.getString("chip_code");
            item.setChip_code(chip_code);
            String	software_code		 = rs.getString("software_code");
            item.setSoftware_code(software_code);
            String	vendor_code		 = rs.getString("vendor_code");
            item.setVendor_code(vendor_code);
            int	channel_code		 = rs.getInt("channel_code");
            item.setChannel_code(channel_code);
            int	is_enabled		 = rs.getInt("is_enabled");
            item.setIs_enabled(is_enabled);
            java.util.Date	enabled_date		 = rs.getTimestamp("enabled_date");
            item.setEnabled_date(enabled_date);
            String	device_ip		 = rs.getString("device_ip");
            item.setDevice_ip(device_ip);
            String	device_mac		 = rs.getString("device_mac");
            item.setDevice_mac(device_mac);
            int	region_id		 = rs.getInt("region_id");
            item.setRegion_id(region_id);
            int	status		 = rs.getInt("status");
            item.setStatus(status);
            String	secrect_key		 = rs.getString("secrect_key");
            item.setSecrect_key(secrect_key);
            java.util.Date	create_date		 = rs.getTimestamp("create_date");
            item.setCreate_date(create_date);
            java.util.Date	update_date		 = rs.getTimestamp("update_date");
            item.setUpdate_date(update_date);
            String	board_code		 = rs.getString("board_code");
            item.setBoard_code(board_code);
            int	created_by		 = rs.getInt("created_by");
            item.setCreated_by(created_by);
            int	activate_times		 = rs.getInt("activate_times");
            item.setActivate_times(activate_times);
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
        DeviceInfo item = (DeviceInfo) obj;

        String ret = 
            "`chip_code`='" + ServerTool.escapeString(item.getChip_code()) + "'"
            + ",`software_code`='" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + ",`vendor_code`='" + ServerTool.escapeString(item.getVendor_code()) + "'"
            + ",`channel_code`=" + item.getChannel_code()
            + ",`is_enabled`=" + item.getIs_enabled()
            + ",`enabled_date`=" + (((d=item.getEnabled_date())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",`device_ip`='" + ServerTool.escapeString(item.getDevice_ip()) + "'"
            + ",`device_mac`='" + ServerTool.escapeString(item.getDevice_mac()) + "'"
            + ",`region_id`=" + item.getRegion_id()
            + ",`status`=" + item.getStatus()
            + ",`secrect_key`='" + ServerTool.escapeString(item.getSecrect_key()) + "'"
            + ",`create_date`=" + (((d=item.getCreate_date())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",`update_date`=" + (((d=item.getUpdate_date())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",`board_code`='" + ServerTool.escapeString(item.getBoard_code()) + "'"
            + ",`created_by`=" + item.getCreated_by()
            + ",`activate_times`=" + item.getActivate_times()
            + ",`challenger_times_date`=" + (((d=item.getChallenger_times_date())!=null)?("'"+df.format(d)+"'"):"NULL")

        ;
        return ret;
    }

    protected String getInsertString()
    {
         return  "`chip_code`,`software_code`,`vendor_code`,`channel_code`,`is_enabled`,`enabled_date`,`device_ip`,`device_mac`,`region_id`,`status`,`secrect_key`,`create_date`,`update_date`,`board_code`,`created_by`,`activate_times`,`challenger_times_date`";
    }

    protected String getCreateString(Object obj)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date d;
        DeviceInfo item = (DeviceInfo) obj;

        String ret = 
            "'" + ServerTool.escapeString(item.getChip_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getSoftware_code()) + "'"
            + ",'" + ServerTool.escapeString(item.getVendor_code()) + "'"
            + "," + item.getChannel_code()
            + "," + item.getIs_enabled()
            + "," + (((d=item.getEnabled_date())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",'" + ServerTool.escapeString(item.getDevice_ip()) + "'"
            + ",'" + ServerTool.escapeString(item.getDevice_mac()) + "'"
            + "," + item.getRegion_id()
            + "," + item.getStatus()
            + ",'" + ServerTool.escapeString(item.getSecrect_key()) + "'"
            + "," + (((d=item.getCreate_date())!=null)?("'"+df.format(d)+"'"):"NULL")
            + "," + (((d=item.getUpdate_date())!=null)?("'"+df.format(d)+"'"):"NULL")
            + ",'" + ServerTool.escapeString(item.getBoard_code()) + "'"
            + "," + item.getCreated_by()
            + "," + item.getActivate_times()
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
        DeviceInfo o = (DeviceInfo) obj;
        o.setDevice_id(auto_id);
    }
}
