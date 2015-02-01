package com.wct.report;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class ReportDevice
{

    private int   	id;
    private String   	vendor_code;
    private String   	chipType;
    private String   	software_code;
    private String   	province;
    private String   	city;
    private String   	communications;
    private Date   	ctime;
    private int   	count;
    private int   	logins;
    private int   	type;


    public ReportDevice() {}


    public int   	getId   	() { return id; }
    public String   	getVendor_code   	() { return vendor_code; }
    public String   	getChipType   	() { return chipType; }
    public String   	getSoftware_code   	() { return software_code; }
    public String   	getProvince   	() { return province; }
    public String   	getCity   	() { return city; }
    public String   	getCommunications   	() { return communications; }
    public Date   	getCtime   	() { return ctime; }
    public int   	getCount   	() { return count; }
    public int   	getLogins   	() { return logins; }
    public int   	getType   	() { return type; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setVendor_code   	(String vendor_code) { this.vendor_code = vendor_code; }
    public void 	setChipType   	(String chipType) { this.chipType = chipType; }
    public void 	setSoftware_code   	(String software_code) { this.software_code = software_code; }
    public void 	setProvince   	(String province) { this.province = province; }
    public void 	setCity   	(String city) { this.city = city; }
    public void 	setCommunications   	(String communications) { this.communications = communications; }
    public void 	setCtime   	(Date ctime) { this.ctime = ctime; }
    public void 	setCount   	(int count) { this.count = count; }
    public void 	setLogins   	(int logins) { this.logins = logins; }
    public void 	setType   	(int type) { this.type = type; }

    public static final int TYPE_HOUR   = 1;
    public static final int TYPE_DAY    = 2;
    public static final int TYPE_WEEK   = 3;
    public static final int TYPE_MONTH  = 4;
    
    public static final int TYPE_HOUR_FINISH   = 11;
    public static final int TYPE_DAY_FINISH    = 12;
    public static final int TYPE_WEEK_FINISH   = 13;
    public static final int TYPE_MONTH_FINISH  = 14;  
    
    public String getVendorName(Object arg)
    	throws Exception
    {
        VendorNameConverter nc = (VendorNameConverter) arg;
        return nc.getRealname(this.vendor_code);
    }     

}
