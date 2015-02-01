package com.wct.vod;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class MenuItem
{

    private int   	id;
    private String   	name;
    private int   	hidden;


    public MenuItem() {}


    public int   	getId   	() { return id; }
    public String   	getName   	() { return name; }
    public int   	getHidden   	() { return hidden; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setName   	(String name) { this.name = name; }
    public void 	setHidden   	(int hidden) { this.hidden = hidden; }

}
