package com.wct.report;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class VideoLog
{

    private String   	videoUrl;
    private int   	pageview;
    private int   	visit;
    private Date   	createDate;


    public VideoLog() {}


    public String   	getVideoUrl   	() { return videoUrl; }
    public int   	getPageview   	() { return pageview; }
    public int   	getVisit   	() { return visit; }
    public Date   	getCreateDate   	() { return createDate; }


    public void 	setVideoUrl   	(String videoUrl) { this.videoUrl = videoUrl; }
    public void 	setPageview   	(int pageview) { this.pageview = pageview; }
    public void 	setVisit   	(int visit) { this.visit = visit; }
    public void 	setCreateDate   	(Date createDate) { this.createDate = createDate; }
	private static java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
    public String getKey() {
    	return this.videoUrl + "#" + sdf.format(this.createDate);
    } 

}
