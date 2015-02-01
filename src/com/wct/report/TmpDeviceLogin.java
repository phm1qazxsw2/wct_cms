package com.wct.report;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class TmpDeviceLogin
{

    private int   	id;
    private String   	device_mac;
    private String   	vendor_code;
    private String   	chipType;
    private String   	software_code;
    private String   	province;
    private String   	city;
    private Date   	slottime;
    private int   	slottype;
    private int   	count;


    public TmpDeviceLogin() {}


    public int   	getId   	() { return id; }
    public String   	getDevice_mac   	() { return device_mac; }
    public String   	getVendor_code   	() { return vendor_code; }
    public String   	getChipType   	() { return chipType; }
    public String   	getSoftware_code   	() { return software_code; }
    public String   	getProvince   	() { return province; }
    public String   	getCity   	() { return city; }
    public Date   	getSlottime   	() { return slottime; }
    public int   	getSlottype   	() { return slottype; }
    public int   	getCount   	() { return count; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setDevice_mac   	(String device_mac) { this.device_mac = device_mac; }
    public void 	setVendor_code   	(String vendor_code) { this.vendor_code = vendor_code; }
    public void 	setChipType   	(String chipType) { this.chipType = chipType; }
    public void 	setSoftware_code   	(String software_code) { this.software_code = software_code; }
    public void 	setProvince   	(String province) { this.province = province; }
    public void 	setCity   	(String city) { this.city = city; }
    public void 	setSlottime   	(Date slottime) { this.slottime = slottime; }
    public void 	setSlottype   	(int slottype) { this.slottype = slottype; }
    public void 	setCount   	(int count) { this.count = count; }

}
