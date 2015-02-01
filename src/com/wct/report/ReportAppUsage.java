package com.wct.report;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class ReportAppUsage
{

    private int   	id;
    private String   	appName;
    private String   	vendor_code;
    private String   	chipType;
    private String   	software_code;
    private String   	province;
    private String   	city;
    private String   	communications;
    private Date   	ctime;
    private long   	seconds;
    private int   	type;


    public ReportAppUsage() {}


    public int   	getId   	() { return id; }
    public String   	getAppName   	() { return appName; }
    public String   	getVendor_code   	() { return vendor_code; }
    public String   	getChipType   	() { return chipType; }
    public String   	getSoftware_code   	() { return software_code; }
    public String   	getProvince   	() { return province; }
    public String   	getCity   	() { return city; }
    public String   	getCommunications   	() { return communications; }
    public Date   	getCtime   	() { return ctime; }
    public long   	getSeconds   	() { return seconds; }
    public int   	getType   	() { return type; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setAppName   	(String appName) { this.appName = appName; }
    public void 	setVendor_code   	(String vendor_code) { this.vendor_code = vendor_code; }
    public void 	setChipType   	(String chipType) { this.chipType = chipType; }
    public void 	setSoftware_code   	(String software_code) { this.software_code = software_code; }
    public void 	setProvince   	(String province) { this.province = province; }
    public void 	setCity   	(String city) { this.city = city; }
    public void 	setCommunications   	(String communications) { this.communications = communications; }
    public void 	setCtime   	(Date ctime) { this.ctime = ctime; }
    public void 	setSeconds   	(long seconds) { this.seconds = seconds; }
    public void 	setType   	(int type) { this.type = type; }

    public static final int TYPE_HOUR   = 1;
    public static final int TYPE_DAY    = 2;
    public static final int TYPE_WEEK   = 3;
    public static final int TYPE_MONTH  = 4;
    
    private static java.text.SimpleDateFormat sdf =  new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    public String getKey() {
    	StringBuffer sb = new StringBuffer();
        sb.append(appName).append("#").append(vendor_code).append("#").append(chipType);
        sb.append("#").append(software_code).append("#").append(province).append("#").append(city).append("#");
        sb.append(communications).append("#").append(sdf.format(ctime));
        return sb.toString();
    }
    
    public String getKeyIdentifier() {
    	StringBuffer sb = new StringBuffer();
        sb.append("appName='").append(appName).append("'");
        sb.append(" and ");
        sb.append("vendor_code='").append(vendor_code).append("'");
        sb.append(" and ");
        sb.append("chipType='").append(chipType).append("'");
        sb.append(" and ");
        sb.append("software_code='").append(software_code).append("'");
        sb.append(" and ");
        sb.append("province='").append(province).append("'");
        sb.append(" and ");
        sb.append("city='").append(city).append("'");
        sb.append(" and ");
        sb.append("communications='").append(communications).append("'");
        sb.append(" and ");
        sb.append("ctime='").append(sdf.format(ctime)).append("'");
        return sb.toString();
    }

}
