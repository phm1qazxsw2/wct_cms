package com.wct.ad;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class AdCollect
{

    private int   	id;
    private String   	ad_ids;
    private int   	ad_id;
    private int   	last_record_id;
    private String   	reporting_urls;


    public AdCollect() {}


    public int   	getId   	() { return id; }
    public String   	getAd_ids   	() { return ad_ids; }
    public int   	getAd_id   	() { return ad_id; }
    public int   	getLast_record_id   	() { return last_record_id; }
    public String   	getReporting_urls   	() { return reporting_urls; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setAd_ids   	(String ad_ids) { this.ad_ids = ad_ids; }
    public void 	setAd_id   	(int ad_id) { this.ad_id = ad_id; }
    public void 	setLast_record_id   	(int last_record_id) { this.last_record_id = last_record_id; }
    public void 	setReporting_urls   	(String reporting_urls) { this.reporting_urls = reporting_urls; }

}
