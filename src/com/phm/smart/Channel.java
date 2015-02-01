package com.phm.smart;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class Channel
{

    private int   	id;
    private String   	name;
    private String   	icon;


    public Channel() {}


    public int   	getId   	() { return id; }
    public String   	getName   	() { return name; }
    public String   	getIcon   	() { return icon; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setName   	(String name) { this.name = name; }
    public void 	setIcon   	(String icon) { this.icon = icon; }

}
