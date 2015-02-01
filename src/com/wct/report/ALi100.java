package com.wct.report;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class ALi100
{

    private String   	device_mac;
    private int   	id;
    private String   	name;
    private String   	mac;
    private String   	area;


    public ALi100() {}


    public String   	getDevice_mac   	() { return device_mac; }
    public int   	getId   	() { return id; }
    public String   	getName   	() { return name; }
    public String   	getMac   	() { return mac; }
    public String   	getArea   	() { return area; }


    public void 	setDevice_mac   	(String device_mac) { this.device_mac = device_mac; }
    public void 	setId   	(int id) { this.id = id; }
    public void 	setName   	(String name) { this.name = name; }
    public void 	setMac   	(String mac) { this.mac = mac; }
    public void 	setArea   	(String area) { this.area = area; }

}
