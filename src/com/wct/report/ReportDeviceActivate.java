package com.wct.report;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class ReportDeviceActivate
{

    private int   	id;
    private String   	vendor_code;
    private String   	chipType;
    private String   	software_code;
    private Date   	ctime;
    private int   	count;
    private int   	type;


    public ReportDeviceActivate() {}


    public int   	getId   	() { return id; }
    public String   	getVendor_code   	() { return vendor_code; }
    public String   	getChipType   	() { return chipType; }
    public String   	getSoftware_code   	() { return software_code; }
    public Date   	getCtime   	() { return ctime; }
    public int   	getCount   	() { return count; }
    public int   	getType   	() { return type; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setVendor_code   	(String vendor_code) { this.vendor_code = vendor_code; }
    public void 	setChipType   	(String chipType) { this.chipType = chipType; }
    public void 	setSoftware_code   	(String software_code) { this.software_code = software_code; }
    public void 	setCtime   	(Date ctime) { this.ctime = ctime; }
    public void 	setCount   	(int count) { this.count = count; }
    public void 	setType   	(int type) { this.type = type; }

    public String getVendorName(Object arg)
    	throws Exception
    {
        VendorNameConverter nc = (VendorNameConverter) arg;
        return nc.getRealname(this.vendor_code);
    }
    
    public String getVer() {
		int c = software_code.lastIndexOf(".");
		if (c<0)
			return software_code;
		return software_code.substring(0, c);
    }

}
