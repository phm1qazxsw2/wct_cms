package com.wct.report;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class TmpDevice
{

    private int   	id;
    private String   	software_code;
    private String   	vendor_code;
    private String   	device_mac;
    private String   	chipType;
    private Date   	activate_time;


    public TmpDevice() {}


    public int   	getId   	() { return id; }
    public String   	getSoftware_code   	() { return software_code; }
    public String   	getVendor_code   	() { return vendor_code; }
    public String   	getDevice_mac   	() { return device_mac; }
    public String   	getChipType   	() { return chipType; }
    public Date   	getActivate_time   	() { return activate_time; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setSoftware_code   	(String software_code) { this.software_code = software_code; }
    public void 	setVendor_code   	(String vendor_code) { this.vendor_code = vendor_code; }
    public void 	setDevice_mac   	(String device_mac) { this.device_mac = device_mac; }
    public void 	setChipType   	(String chipType) { this.chipType = chipType; }
    public void 	setActivate_time   	(Date activate_time) { this.activate_time = activate_time; }

}
