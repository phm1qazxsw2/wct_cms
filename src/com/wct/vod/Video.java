package com.wct.vod;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class Video
{

    private int   	id;
    private String   	key;
    private Date   	updated;
    private String   	name;
    private String   	pic;
    private int   	type;
    private int   	check_new;
    private int   	episode_num;
    private String   	short_desc;
    private String   	long_desc;
    private String   	area;
    private String   	director;
    private String   	actor;
    private String   	hao123url;
    private int   	year;


    public Video() {}


    public int   	getId   	() { return id; }
    public String   	getKey   	() { return key; }
    public Date   	getUpdated   	() { return updated; }
    public String   	getName   	() { return name; }
    public String   	getPic   	() { return pic; }
    public int   	getType   	() { return type; }
    public int   	getCheck_new   	() { return check_new; }
    public int   	getEpisode_num   	() { return episode_num; }
    public String   	getShort_desc   	() { return short_desc; }
    public String   	getLong_desc   	() { return long_desc; }
    public String   	getArea   	() { return area; }
    public String   	getDirector   	() { return director; }
    public String   	getActor   	() { return actor; }
    public String   	getHao123url   	() { return hao123url; }
    public int   	getYear   	() { return year; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setKey   	(String key) { this.key = key; }
    public void 	setUpdated   	(Date updated) { this.updated = updated; }
    public void 	setName   	(String name) { this.name = name; }
    public void 	setPic   	(String pic) { this.pic = pic; }
    public void 	setType   	(int type) { this.type = type; }
    public void 	setCheck_new   	(int check_new) { this.check_new = check_new; }
    public void 	setEpisode_num   	(int episode_num) { this.episode_num = episode_num; }
    public void 	setShort_desc   	(String short_desc) { this.short_desc = short_desc; }
    public void 	setLong_desc   	(String long_desc) { this.long_desc = long_desc; }
    public void 	setArea   	(String area) { this.area = area; }
    public void 	setDirector   	(String director) { this.director = director; }
    public void 	setActor   	(String actor) { this.actor = actor; }
    public void 	setHao123url   	(String hao123url) { this.hao123url = hao123url; }
    public void 	setYear   	(int year) { this.year = year; }

    public static final int TYPE_MOVIE = 1;
    public static final int TYPE_DRAMA = 2;
    public static final int TYPE_ENTERTAINMENT = 3;
    public static final int TYPE_ANIMATION = 4;
    
    public String getTypeName() {
        switch (this.type) {
        case TYPE_MOVIE: return "电影";
        case TYPE_DRAMA: return "电视剧";
        case TYPE_ENTERTAINMENT: return "综艺";
        case TYPE_ANIMATION: return "动漫";
        }
        return "";
    }
    


}
