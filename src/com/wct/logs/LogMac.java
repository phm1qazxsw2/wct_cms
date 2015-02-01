package com.wct.logs;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class LogMac
{

    private int   	id;
    private String   	mac;


    public LogMac() {}


    public int   	getId   	() { return id; }
    public String   	getMac   	() { return mac; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setMac   	(String mac) { this.mac = mac; }

}
