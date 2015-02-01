package com.wct.report;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class MacInfo
{

    private String   	device_mac;
    private String   	chip_code;
    private int   	status;
    private String   	vid;
    private int   	oemId;
    private String   	orderNo;
    private int   	needVid;
    private Date   	createDate;
    private String   	chipType;


    public MacInfo() {}


    public String   	getDevice_mac   	() { return device_mac; }
    public String   	getChip_code   	() { return chip_code; }
    public int   	getStatus   	() { return status; }
    public String   	getVid   	() { return vid; }
    public int   	getOemId   	() { return oemId; }
    public String   	getOrderNo   	() { return orderNo; }
    public int   	getNeedVid   	() { return needVid; }
    public Date   	getCreateDate   	() { return createDate; }
    public String   	getChipType   	() { return chipType; }


    public void 	setDevice_mac   	(String device_mac) { this.device_mac = device_mac; }
    public void 	setChip_code   	(String chip_code) { this.chip_code = chip_code; }
    public void 	setStatus   	(int status) { this.status = status; }
    public void 	setVid   	(String vid) { this.vid = vid; }
    public void 	setOemId   	(int oemId) { this.oemId = oemId; }
    public void 	setOrderNo   	(String orderNo) { this.orderNo = orderNo; }
    public void 	setNeedVid   	(int needVid) { this.needVid = needVid; }
    public void 	setCreateDate   	(Date createDate) { this.createDate = createDate; }
    public void 	setChipType   	(String chipType) { this.chipType = chipType; }

}
