package com.wct.ad;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class AdRecord
{

    private int   	id;
    private String   	device_code;
    private String   	device_ip;
    private int   	ad_id;
    private Date   	advert_time;
    private int   	counts;


    public AdRecord() {}


    public int   	getId   	() { return id; }
    public String   	getDevice_code   	() { return device_code; }
    public String   	getDevice_ip   	() { return device_ip; }
    public int   	getAd_id   	() { return ad_id; }
    public Date   	getAdvert_time   	() { return advert_time; }
    public int   	getCounts   	() { return counts; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setDevice_code   	(String device_code) { this.device_code = device_code; }
    public void 	setDevice_ip   	(String device_ip) { this.device_ip = device_ip; }
    public void 	setAd_id   	(int ad_id) { this.ad_id = ad_id; }
    public void 	setAdvert_time   	(Date advert_time) { this.advert_time = advert_time; }
    public void 	setCounts   	(int counts) { this.counts = counts; }

}
