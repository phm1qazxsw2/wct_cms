package com.phm.smart;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class TestPush
{

    private int   	id;
    private int   	type;
    private String   	uuid;
    private String   	token;
    private Date   	u_time;


    public TestPush() {}


    public int   	getId   	() { return id; }
    public int   	getType   	() { return type; }
    public String   	getUuid   	() { return uuid; }
    public String   	getToken   	() { return token; }
    public Date   	getU_time   	() { return u_time; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setType   	(int type) { this.type = type; }
    public void 	setUuid   	(String uuid) { this.uuid = uuid; }
    public void 	setToken   	(String token) { this.token = token; }
    public void 	setU_time   	(Date u_time) { this.u_time = u_time; }

    public static final int TYPE_ANDROID = 1 ;
    public static final int TYPE_IOS     = 2 ;
 


}
