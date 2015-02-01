package com.wct.report;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class ActivateFail
{

    private int   	id;
    private String   	chip_code;
    private String   	software_code;
    private String   	vendor_code;
    private String   	device_mac;
    private String   	device_ip;
    private String   	info;
    private Date   	active_times_date;


    public ActivateFail() {}


    public int   	getId   	() { return id; }
    public String   	getChip_code   	() { return chip_code; }
    public String   	getSoftware_code   	() { return software_code; }
    public String   	getVendor_code   	() { return vendor_code; }
    public String   	getDevice_mac   	() { return device_mac; }
    public String   	getDevice_ip   	() { return device_ip; }
    public String   	getInfo   	() { return info; }
    public Date   	getActive_times_date   	() { return active_times_date; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setChip_code   	(String chip_code) { this.chip_code = chip_code; }
    public void 	setSoftware_code   	(String software_code) { this.software_code = software_code; }
    public void 	setVendor_code   	(String vendor_code) { this.vendor_code = vendor_code; }
    public void 	setDevice_mac   	(String device_mac) { this.device_mac = device_mac; }
    public void 	setDevice_ip   	(String device_ip) { this.device_ip = device_ip; }
    public void 	setInfo   	(String info) { this.info = info; }
    public void 	setActive_times_date   	(Date active_times_date) { this.active_times_date = active_times_date; }

}
