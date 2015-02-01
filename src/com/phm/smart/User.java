package com.phm.smart;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class User
{

    private int   	id;
    private String   	phone;
    private String   	jpush_id;
    private String   	uuid;
    private Date   	u_time;


    public User() {}


    public int   	getId   	() { return id; }
    public String   	getPhone   	() { return phone; }
    public String   	getJpush_id   	() { return jpush_id; }
    public String   	getUuid   	() { return uuid; }
    public Date   	getU_time   	() { return u_time; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setPhone   	(String phone) { this.phone = phone; }
    public void 	setJpush_id   	(String jpush_id) { this.jpush_id = jpush_id; }
    public void 	setUuid   	(String uuid) { this.uuid = uuid; }
    public void 	setU_time   	(Date u_time) { this.u_time = u_time; }

    public static User getUserWithUuid(String uuid, String db_src) 
    	throws Exception
    {
    	if (uuid==null || uuid.length()==0)
    		return null;    	
    	UserMgr umgr = new UserMgr(0);
		umgr.setSource(db_src);
		
		return umgr.find("uuid='" + uuid + "'");		
    }

    public static User createUserWithUuid(String uuid, String db_src) 
		throws Exception
    {
    	UserMgr umgr = new UserMgr(0);
		umgr.setSource(db_src);
    	User u = new User();
    	u.setUuid(uuid);
    	u.setU_time(new java.util.Date());
    	umgr.create(u);
    	return u;
    }

}
