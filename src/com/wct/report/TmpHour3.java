package com.wct.report;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class TmpHour3
{

    private int   	id;
    private String   	device_mac;
    private String   	device_ip;
    private String   	vendor_code;
    private String   	chipType;
    private String   	software_code;
    private String   	province;
    private String   	city;
    private String   	communications;
    private Date   	ctime;
    private int   	count;


    public TmpHour3() {}


    public int   	getId   	() { return id; }
    public String   	getDevice_mac   	() { return device_mac; }
    public String   	getDevice_ip   	() { return device_ip; }
    public String   	getVendor_code   	() { return vendor_code; }
    public String   	getChipType   	() { return chipType; }
    public String   	getSoftware_code   	() { return software_code; }
    public String   	getProvince   	() { return province; }
    public String   	getCity   	() { return city; }
    public String   	getCommunications   	() { return communications; }
    public Date   	getCtime   	() { return ctime; }
    public int   	getCount   	() { return count; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setDevice_mac   	(String device_mac) { this.device_mac = device_mac; }
    public void 	setDevice_ip   	(String device_ip) { this.device_ip = device_ip; }
    public void 	setVendor_code   	(String vendor_code) { this.vendor_code = vendor_code; }
    public void 	setChipType   	(String chipType) { this.chipType = chipType; }
    public void 	setSoftware_code   	(String software_code) { this.software_code = software_code; }
    public void 	setProvince   	(String province) { this.province = province; }
    public void 	setCity   	(String city) { this.city = city; }
    public void 	setCommunications   	(String communications) { this.communications = communications; }
    public void 	setCtime   	(Date ctime) { this.ctime = ctime; }
    public void 	setCount   	(int count) { this.count = count; }

}
