package com.wct.vod;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class Channel
{

    private int   	id;
    private String   	name;


    public Channel() {}


    public int   	getId   	() { return id; }
    public String   	getName   	() { return name; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setName   	(String name) { this.name = name; }

}
