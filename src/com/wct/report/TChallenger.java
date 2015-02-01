package com.wct.report;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class TChallenger
{

    private int   	id;
    private String   	device_mac;
    private String   	device_ip;
    private Date   	challenger_times_date;
    private String   	software_code;
    private String   	vendor_code;


    public TChallenger() {}


    public int   	getId   	() { return id; }
    public String   	getDevice_mac   	() { return device_mac; }
    public String   	getDevice_ip   	() { return device_ip; }
    public Date   	getChallenger_times_date   	() { return challenger_times_date; }
    public String   	getSoftware_code   	() { return software_code; }
    public String   	getVendor_code   	() { return vendor_code; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setDevice_mac   	(String device_mac) { this.device_mac = device_mac; }
    public void 	setDevice_ip   	(String device_ip) { this.device_ip = device_ip; }
    public void 	setChallenger_times_date   	(Date challenger_times_date) { this.challenger_times_date = challenger_times_date; }
    public void 	setSoftware_code   	(String software_code) { this.software_code = software_code; }
    public void 	setVendor_code   	(String vendor_code) { this.vendor_code = vendor_code; }

}
