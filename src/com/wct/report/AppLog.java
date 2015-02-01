package com.wct.report;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class AppLog
{

    private int   	id;
    private String   	chipId;
    private String   	mac;
    private String   	appName;
    private Date   	enterTime;
    private Date   	leaveTime;
    private Date   	createTime;


    public AppLog() {}


    public int   	getId   	() { return id; }
    public String   	getChipId   	() { return chipId; }
    public String   	getMac   	() { return mac; }
    public String   	getAppName   	() { return appName; }
    public Date   	getEnterTime   	() { return enterTime; }
    public Date   	getLeaveTime   	() { return leaveTime; }
    public Date   	getCreateTime   	() { return createTime; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setChipId   	(String chipId) { this.chipId = chipId; }
    public void 	setMac   	(String mac) { this.mac = mac; }
    public void 	setAppName   	(String appName) { this.appName = appName; }
    public void 	setEnterTime   	(Date enterTime) { this.enterTime = enterTime; }
    public void 	setLeaveTime   	(Date leaveTime) { this.leaveTime = leaveTime; }
    public void 	setCreateTime   	(Date createTime) { this.createTime = createTime; }

}
