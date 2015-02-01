package com.wct.report;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class UpgradeFail
{

    private int   	id;
    private String   	mac;
    private String   	curSoftware;
    private String   	toSoftware;
    private int   	flow;
    private Date   	createDate;
    private String   	errorCode;


    public UpgradeFail() {}


    public int   	getId   	() { return id; }
    public String   	getMac   	() { return mac; }
    public String   	getCurSoftware   	() { return curSoftware; }
    public String   	getToSoftware   	() { return toSoftware; }
    public int   	getFlow   	() { return flow; }
    public Date   	getCreateDate   	() { return createDate; }
    public String   	getErrorCode   	() { return errorCode; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setMac   	(String mac) { this.mac = mac; }
    public void 	setCurSoftware   	(String curSoftware) { this.curSoftware = curSoftware; }
    public void 	setToSoftware   	(String toSoftware) { this.toSoftware = toSoftware; }
    public void 	setFlow   	(int flow) { this.flow = flow; }
    public void 	setCreateDate   	(Date createDate) { this.createDate = createDate; }
    public void 	setErrorCode   	(String errorCode) { this.errorCode = errorCode; }

    public String getUpper_mac() {
    	return mac.toUpperCase();
    }


}
