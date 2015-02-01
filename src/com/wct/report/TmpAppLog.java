package com.wct.report;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class TmpAppLog
{

    private int   	id;
    private int   	org_id;
    private String   	mac;
    private String   	appName;
    private Date   	enterTime;
    private Date   	leaveTime;
    private long   	duration;
    private String   	device_ip;
    private String   	vendor_code;
    private String   	chipType;
    private String   	software_code;
    private String   	province;
    private String   	city;
    private String   	communications;
    private int   	processed;


    public TmpAppLog() {}


    public int   	getId   	() { return id; }
    public int   	getOrg_id   	() { return org_id; }
    public String   	getMac   	() { return mac; }
    public String   	getAppName   	() { return appName; }
    public Date   	getEnterTime   	() { return enterTime; }
    public Date   	getLeaveTime   	() { return leaveTime; }
    public long   	getDuration   	() { return duration; }
    public String   	getDevice_ip   	() { return device_ip; }
    public String   	getVendor_code   	() { return vendor_code; }
    public String   	getChipType   	() { return chipType; }
    public String   	getSoftware_code   	() { return software_code; }
    public String   	getProvince   	() { return province; }
    public String   	getCity   	() { return city; }
    public String   	getCommunications   	() { return communications; }
    public int   	getProcessed   	() { return processed; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setOrg_id   	(int org_id) { this.org_id = org_id; }
    public void 	setMac   	(String mac) { this.mac = mac; }
    public void 	setAppName   	(String appName) { this.appName = appName; }
    public void 	setEnterTime   	(Date enterTime) { this.enterTime = enterTime; }
    public void 	setLeaveTime   	(Date leaveTime) { this.leaveTime = leaveTime; }
    public void 	setDuration   	(long duration) { this.duration = duration; }
    public void 	setDevice_ip   	(String device_ip) { this.device_ip = device_ip; }
    public void 	setVendor_code   	(String vendor_code) { this.vendor_code = vendor_code; }
    public void 	setChipType   	(String chipType) { this.chipType = chipType; }
    public void 	setSoftware_code   	(String software_code) { this.software_code = software_code; }
    public void 	setProvince   	(String province) { this.province = province; }
    public void 	setCity   	(String city) { this.city = city; }
    public void 	setCommunications   	(String communications) { this.communications = communications; }
    public void 	setProcessed   	(int processed) { this.processed = processed; }

}
