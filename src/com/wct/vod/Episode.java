package com.wct.vod;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class Episode
{

    private int   	id;
    private int   	video_id;
    private String   	name;
    private String   	num;


    public Episode() {}


    public int   	getId   	() { return id; }
    public int   	getVideo_id   	() { return video_id; }
    public String   	getName   	() { return name; }
    public String   	getNum   	() { return num; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setVideo_id   	(int video_id) { this.video_id = video_id; }
    public void 	setName   	(String name) { this.name = name; }
    public void 	setNum   	(String num) { this.num = num; }

}
