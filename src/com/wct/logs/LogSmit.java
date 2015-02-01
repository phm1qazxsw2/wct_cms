package com.wct.logs;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class LogSmit
{

    private int   	id;
    private String   	mac;
    private String   	ip;
    private int   	secs;
    private String   	video;
    private Date   	ctime;


    public LogSmit() {}


    public int   	getId   	() { return id; }
    public String   	getMac   	() { return mac; }
    public String   	getIp   	() { return ip; }
    public int   	getSecs   	() { return secs; }
    public String   	getVideo   	() { return video; }
    public Date   	getCtime   	() { return ctime; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setMac   	(String mac) { this.mac = mac; }
    public void 	setIp   	(String ip) { this.ip = ip; }
    public void 	setSecs   	(int secs) { this.secs = secs; }
    public void 	setVideo   	(String video) { this.video = video; }
    public void 	setCtime   	(Date ctime) { this.ctime = ctime; }

}
