package com.wct.report;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class IpInfo
{

    private int   	id;
    private String   	begIp;
    private String   	endIp;
    private String   	country;
    private String   	province;
    private String   	city;
    private String   	region;
    private String   	communications;
    private long   	begIpNum;
    private long   	endIpNum;


    public IpInfo() {}


    public int   	getId   	() { return id; }
    public String   	getBegIp   	() { return begIp; }
    public String   	getEndIp   	() { return endIp; }
    public String   	getCountry   	() { return country; }
    public String   	getProvince   	() { return province; }
    public String   	getCity   	() { return city; }
    public String   	getRegion   	() { return region; }
    public String   	getCommunications   	() { return communications; }
    public long   	getBegIpNum   	() { return begIpNum; }
    public long   	getEndIpNum   	() { return endIpNum; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setBegIp   	(String begIp) { this.begIp = begIp; }
    public void 	setEndIp   	(String endIp) { this.endIp = endIp; }
    public void 	setCountry   	(String country) { this.country = country; }
    public void 	setProvince   	(String province) { this.province = province; }
    public void 	setCity   	(String city) { this.city = city; }
    public void 	setRegion   	(String region) { this.region = region; }
    public void 	setCommunications   	(String communications) { this.communications = communications; }
    public void 	setBegIpNum   	(long begIpNum) { this.begIpNum = begIpNum; }
    public void 	setEndIpNum   	(long endIpNum) { this.endIpNum = endIpNum; }

}
