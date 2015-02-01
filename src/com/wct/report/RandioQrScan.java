package com.wct.report;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class RandioQrScan
{

    private int   	id;
    private String   	ip;
    private Date   	ctime;
    private int   	type;


    public RandioQrScan() {}


    public int   	getId   	() { return id; }
    public String   	getIp   	() { return ip; }
    public Date   	getCtime   	() { return ctime; }
    public int   	getType   	() { return type; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setIp   	(String ip) { this.ip = ip; }
    public void 	setCtime   	(Date ctime) { this.ctime = ctime; }
    public void 	setType   	(int type) { this.type = type; }

    public final static int TYPE_RANDIO = 0;
    public final static int TYPE_REDBULL = 11;
    public final static int TYPE_GAME1 = 12;

}
