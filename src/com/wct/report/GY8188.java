package com.wct.report;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class GY8188
{

    private int   	id;
    private String   	chipId;
    private String   	wifiInfo;
    private Date   	buildDate;
    private String   	netType;
    private Date   	createTime;


    public GY8188() {}


    public int   	getId   	() { return id; }
    public String   	getChipId   	() { return chipId; }
    public String   	getWifiInfo   	() { return wifiInfo; }
    public Date   	getBuildDate   	() { return buildDate; }
    public String   	getNetType   	() { return netType; }
    public Date   	getCreateTime   	() { return createTime; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setChipId   	(String chipId) { this.chipId = chipId; }
    public void 	setWifiInfo   	(String wifiInfo) { this.wifiInfo = wifiInfo; }
    public void 	setBuildDate   	(Date buildDate) { this.buildDate = buildDate; }
    public void 	setNetType   	(String netType) { this.netType = netType; }
    public void 	setCreateTime   	(Date createTime) { this.createTime = createTime; }

    private String vendor;
    public void setVendor(String v) { vendor=v; };
    public String getVendor() { return vendor; }
    
    private String software_code;
    public void setSoftware_code(String v) { software_code=v; };
    public String getSoftware_code() { return software_code; }

}
