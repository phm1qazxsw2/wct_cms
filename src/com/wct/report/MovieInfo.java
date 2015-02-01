package com.wct.report;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class MovieInfo
{

    private int   	id;
    private String   	name;
    private String   	url;
    private String   	type;


    public MovieInfo() {}


    public int   	getId   	() { return id; }
    public String   	getName   	() { return name; }
    public String   	getUrl   	() { return url; }
    public String   	getType   	() { return type; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setName   	(String name) { this.name = name; }
    public void 	setUrl   	(String url) { this.url = url; }
    public void 	setType   	(String type) { this.type = type; }

}
