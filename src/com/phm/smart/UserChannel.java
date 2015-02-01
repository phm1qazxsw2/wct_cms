package com.phm.smart;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class UserChannel
{

    private int   	id;
    private int   	user_id;
    private int   	channel_id;
    private String   	subtitle;
    private int   	unread;


    public UserChannel() {}


    public int   	getId   	() { return id; }
    public int   	getUser_id   	() { return user_id; }
    public int   	getChannel_id   	() { return channel_id; }
    public String   	getSubtitle   	() { return subtitle; }
    public int   	getUnread   	() { return unread; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setUser_id   	(int user_id) { this.user_id = user_id; }
    public void 	setChannel_id   	(int channel_id) { this.channel_id = channel_id; }
    public void 	setSubtitle   	(String subtitle) { this.subtitle = subtitle; }
    public void 	setUnread   	(int unread) { this.unread = unread; }

}
