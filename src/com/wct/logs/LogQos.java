package com.wct.logs;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class LogQos
{

    private int   	id;
    private int   	event;
    private int   	macId;
    private int   	ipId;
    private Date   	ctime;
    private int   	orgUrlId;
    private int   	urlId;
    private int   	videoId;
    private int   	totalTime;
    private int   	startTime;
    private int   	playTime;
    private int   	startType;
    private int   	startStatus;
    private int   	bufferNum;
    private int   	bufferTime;


    public LogQos() {}


    public int   	getId   	() { return id; }
    public int   	getEvent   	() { return event; }
    public int   	getMacId   	() { return macId; }
    public int   	getIpId   	() { return ipId; }
    public Date   	getCtime   	() { return ctime; }
    public int   	getOrgUrlId   	() { return orgUrlId; }
    public int   	getUrlId   	() { return urlId; }
    public int   	getVideoId   	() { return videoId; }
    public int   	getTotalTime   	() { return totalTime; }
    public int   	getStartTime   	() { return startTime; }
    public int   	getPlayTime   	() { return playTime; }
    public int   	getStartType   	() { return startType; }
    public int   	getStartStatus   	() { return startStatus; }
    public int   	getBufferNum   	() { return bufferNum; }
    public int   	getBufferTime   	() { return bufferTime; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setEvent   	(int event) { this.event = event; }
    public void 	setMacId   	(int macId) { this.macId = macId; }
    public void 	setIpId   	(int ipId) { this.ipId = ipId; }
    public void 	setCtime   	(Date ctime) { this.ctime = ctime; }
    public void 	setOrgUrlId   	(int orgUrlId) { this.orgUrlId = orgUrlId; }
    public void 	setUrlId   	(int urlId) { this.urlId = urlId; }
    public void 	setVideoId   	(int videoId) { this.videoId = videoId; }
    public void 	setTotalTime   	(int totalTime) { this.totalTime = totalTime; }
    public void 	setStartTime   	(int startTime) { this.startTime = startTime; }
    public void 	setPlayTime   	(int playTime) { this.playTime = playTime; }
    public void 	setStartType   	(int startType) { this.startType = startType; }
    public void 	setStartStatus   	(int startStatus) { this.startStatus = startStatus; }
    public void 	setBufferNum   	(int bufferNum) { this.bufferNum = bufferNum; }
    public void 	setBufferTime   	(int bufferTime) { this.bufferTime = bufferTime; }

}
