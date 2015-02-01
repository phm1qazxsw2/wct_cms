package com.wct.report;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class DeviceInfo
{

    private int   	device_id;
    private String   	chip_code;
    private String   	software_code;
    private String   	vendor_code;
    private int   	channel_code;
    private int   	is_enabled;
    private Date   	enabled_date;
    private String   	device_ip;
    private String   	device_mac;
    private int   	region_id;
    private int   	status;
    private String   	secrect_key;
    private Date   	create_date;
    private Date   	update_date;
    private String   	board_code;
    private int   	created_by;
    private int   	activate_times;
    private Date   	challenger_times_date;


    public DeviceInfo() {}


    public int   	getDevice_id   	() { return device_id; }
    public String   	getChip_code   	() { return chip_code; }
    public String   	getSoftware_code   	() { return software_code; }
    public String   	getVendor_code   	() { return vendor_code; }
    public int   	getChannel_code   	() { return channel_code; }
    public int   	getIs_enabled   	() { return is_enabled; }
    public Date   	getEnabled_date   	() { return enabled_date; }
    public String   	getDevice_ip   	() { return device_ip; }
    public String   	getDevice_mac   	() { return device_mac; }
    public int   	getRegion_id   	() { return region_id; }
    public int   	getStatus   	() { return status; }
    public String   	getSecrect_key   	() { return secrect_key; }
    public Date   	getCreate_date   	() { return create_date; }
    public Date   	getUpdate_date   	() { return update_date; }
    public String   	getBoard_code   	() { return board_code; }
    public int   	getCreated_by   	() { return created_by; }
    public int   	getActivate_times   	() { return activate_times; }
    public Date   	getChallenger_times_date   	() { return challenger_times_date; }


    public void 	setDevice_id   	(int device_id) { this.device_id = device_id; }
    public void 	setChip_code   	(String chip_code) { this.chip_code = chip_code; }
    public void 	setSoftware_code   	(String software_code) { this.software_code = software_code; }
    public void 	setVendor_code   	(String vendor_code) { this.vendor_code = vendor_code; }
    public void 	setChannel_code   	(int channel_code) { this.channel_code = channel_code; }
    public void 	setIs_enabled   	(int is_enabled) { this.is_enabled = is_enabled; }
    public void 	setEnabled_date   	(Date enabled_date) { this.enabled_date = enabled_date; }
    public void 	setDevice_ip   	(String device_ip) { this.device_ip = device_ip; }
    public void 	setDevice_mac   	(String device_mac) { this.device_mac = device_mac; }
    public void 	setRegion_id   	(int region_id) { this.region_id = region_id; }
    public void 	setStatus   	(int status) { this.status = status; }
    public void 	setSecrect_key   	(String secrect_key) { this.secrect_key = secrect_key; }
    public void 	setCreate_date   	(Date create_date) { this.create_date = create_date; }
    public void 	setUpdate_date   	(Date update_date) { this.update_date = update_date; }
    public void 	setBoard_code   	(String board_code) { this.board_code = board_code; }
    public void 	setCreated_by   	(int created_by) { this.created_by = created_by; }
    public void 	setActivate_times   	(int activate_times) { this.activate_times = activate_times; }
    public void 	setChallenger_times_date   	(Date challenger_times_date) { this.challenger_times_date = challenger_times_date; }

}
