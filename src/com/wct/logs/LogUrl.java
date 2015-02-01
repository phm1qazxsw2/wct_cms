package com.wct.logs;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class LogUrl
{

    private int   	id;
    private int   	type;
    private String   	md5;
    private String   	url;


    public LogUrl() {}


    public int   	getId   	() { return id; }
    public int   	getType   	() { return type; }
    public String   	getMd5   	() { return md5; }
    public String   	getUrl   	() { return url; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setType   	(int type) { this.type = type; }
    public void 	setMd5   	(String md5) { this.md5 = md5; }
    public void 	setUrl   	(String url) { this.url = url; }

}
