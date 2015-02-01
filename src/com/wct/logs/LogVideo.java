package com.wct.logs;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class LogVideo
{

    private int   	id;
    private int   	app;
    private String   	name;


    public LogVideo() {}


    public int   	getId   	() { return id; }
    public int   	getApp   	() { return app; }
    public String   	getName   	() { return name; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setApp   	(int app) { this.app = app; }
    public void 	setName   	(String name) { this.name = name; }

}
