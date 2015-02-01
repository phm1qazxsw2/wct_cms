package com.wct.report;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class TmpDeviceReport
{

    private String   	vendor_code;
    private String   	chipType;
    private String   	software_code;
    private int   	count;


    public TmpDeviceReport() {}


    public String   	getVendor_code   	() { return vendor_code; }
    public String   	getChipType   	() { return chipType; }
    public String   	getSoftware_code   	() { return software_code; }
    public int   	getCount   	() { return count; }


    public void 	setVendor_code   	(String vendor_code) { this.vendor_code = vendor_code; }
    public void 	setChipType   	(String chipType) { this.chipType = chipType; }
    public void 	setSoftware_code   	(String software_code) { this.software_code = software_code; }
    public void 	setCount   	(int count) { this.count = count; }

}
