package com.phm.smart;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class Message
{

    private int   	id;
    private int   	channel_id;
    private String   	title;
    private String   	text;
    private Date   	created;


    public Message() {}


    public int   	getId   	() { return id; }
    public int   	getChannel_id   	() { return channel_id; }
    public String   	getTitle   	() { return title; }
    public String   	getText   	() { return text; }
    public Date   	getCreated   	() { return created; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setChannel_id   	(int channel_id) { this.channel_id = channel_id; }
    public void 	setTitle   	(String title) { this.title = title; }
    public void 	setText   	(String text) { this.text = text; }
    public void 	setCreated   	(Date created) { this.created = created; }

}
