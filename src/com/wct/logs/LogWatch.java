package com.wct.logs;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class LogWatch
{

    private int   	id;
    private String   	mac;
    private String   	ip;
    private int   	secs;
    private String   	app;
    private String   	video;
    private String   	channel;
    private Date   	ctime;


    public LogWatch() {}


    public int   	getId   	() { return id; }
    public String   	getMac   	() { return mac; }
    public String   	getIp   	() { return ip; }
    public int   	getSecs   	() { return secs; }
    public String   	getApp   	() { return app; }
    public String   	getVideo   	() { return video; }
    public String   	getChannel   	() { return channel; }
    public Date   	getCtime   	() { return ctime; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setMac   	(String mac) { this.mac = mac; }
    public void 	setIp   	(String ip) { this.ip = ip; }
    public void 	setSecs   	(int secs) { this.secs = secs; }
    public void 	setApp   	(String app) { this.app = app; }
    public void 	setVideo   	(String video) { this.video = video; }
    public void 	setChannel   	(String channel) { this.channel = channel; }
    public void 	setCtime   	(Date ctime) { this.ctime = ctime; }

}
