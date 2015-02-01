package com.wct.logs;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class LogIp
{

    private int   	id;
    private String   	ip;
    private String   	province;
    private String   	city;
    private String   	communications;


    public LogIp() {}


    public int   	getId   	() { return id; }
    public String   	getIp   	() { return ip; }
    public String   	getProvince   	() { return province; }
    public String   	getCity   	() { return city; }
    public String   	getCommunications   	() { return communications; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setIp   	(String ip) { this.ip = ip; }
    public void 	setProvince   	(String province) { this.province = province; }
    public void 	setCity   	(String city) { this.city = city; }
    public void 	setCommunications   	(String communications) { this.communications = communications; }

}
