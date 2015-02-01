package com.wct.report;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class UpgradeOk
{

    private int   	id;
    private String   	mac;
    private String   	curSoftware;
    private String   	toSoftware;
    private String   	md5;
    private int   	upgradeTime;
    private int   	downloadSpeed;
    private Date   	createDate;


    public UpgradeOk() {}


    public int   	getId   	() { return id; }
    public String   	getMac   	() { return mac; }
    public String   	getCurSoftware   	() { return curSoftware; }
    public String   	getToSoftware   	() { return toSoftware; }
    public String   	getMd5   	() { return md5; }
    public int   	getUpgradeTime   	() { return upgradeTime; }
    public int   	getDownloadSpeed   	() { return downloadSpeed; }
    public Date   	getCreateDate   	() { return createDate; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setMac   	(String mac) { this.mac = mac; }
    public void 	setCurSoftware   	(String curSoftware) { this.curSoftware = curSoftware; }
    public void 	setToSoftware   	(String toSoftware) { this.toSoftware = toSoftware; }
    public void 	setMd5   	(String md5) { this.md5 = md5; }
    public void 	setUpgradeTime   	(int upgradeTime) { this.upgradeTime = upgradeTime; }
    public void 	setDownloadSpeed   	(int downloadSpeed) { this.downloadSpeed = downloadSpeed; }
    public void 	setCreateDate   	(Date createDate) { this.createDate = createDate; }

    public String getUpper_mac() {
    	return mac.toUpperCase();
    }


}
