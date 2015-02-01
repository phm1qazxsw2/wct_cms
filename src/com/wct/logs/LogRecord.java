package com.wct.logs;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class LogRecord
{

    private int   	id;
    private int   	event;
    private int   	macId;
    private int   	ipId;
    private Date   	ctime;
    private int   	videoId;


    public LogRecord() {}


    public int   	getId   	() { return id; }
    public int   	getEvent   	() { return event; }
    public int   	getMacId   	() { return macId; }
    public int   	getIpId   	() { return ipId; }
    public Date   	getCtime   	() { return ctime; }
    public int   	getVideoId   	() { return videoId; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setEvent   	(int event) { this.event = event; }
    public void 	setMacId   	(int macId) { this.macId = macId; }
    public void 	setIpId   	(int ipId) { this.ipId = ipId; }
    public void 	setCtime   	(Date ctime) { this.ctime = ctime; }
    public void 	setVideoId   	(int videoId) { this.videoId = videoId; }

}
