package com.wct.report;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class VideoLog2
{

    private int   	id;
    private String   	videoUrl;
    private String   	videoFeature;
    private String   	videoType;
    private String   	ip;
    private String   	appName;
    private Date   	createTime;


    public VideoLog2() {}


    public int   	getId   	() { return id; }
    public String   	getVideoUrl   	() { return videoUrl; }
    public String   	getVideoFeature   	() { return videoFeature; }
    public String   	getVideoType   	() { return videoType; }
    public String   	getIp   	() { return ip; }
    public String   	getAppName   	() { return appName; }
    public Date   	getCreateTime   	() { return createTime; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setVideoUrl   	(String videoUrl) { this.videoUrl = videoUrl; }
    public void 	setVideoFeature   	(String videoFeature) { this.videoFeature = videoFeature; }
    public void 	setVideoType   	(String videoType) { this.videoType = videoType; }
    public void 	setIp   	(String ip) { this.ip = ip; }
    public void 	setAppName   	(String appName) { this.appName = appName; }
    public void 	setCreateTime   	(Date createTime) { this.createTime = createTime; }

}
