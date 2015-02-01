package com.wct.report;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class RandioQR
{

    private int   	id;
    private String   	ip;
    private Date   	ctime;


    public RandioQR() {}


    public int   	getId   	() { return id; }
    public String   	getIp   	() { return ip; }
    public Date   	getCtime   	() { return ctime; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setIp   	(String ip) { this.ip = ip; }
    public void 	setCtime   	(Date ctime) { this.ctime = ctime; }

}
