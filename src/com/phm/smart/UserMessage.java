package com.phm.smart;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class UserMessage
{

    private int   	id;
    private int   	user_id;
    private int   	message_id;
    private Date   	last_read;


    public UserMessage() {}


    public int   	getId   	() { return id; }
    public int   	getUser_id   	() { return user_id; }
    public int   	getMessage_id   	() { return message_id; }
    public Date   	getLast_read   	() { return last_read; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setUser_id   	(int user_id) { this.user_id = user_id; }
    public void 	setMessage_id   	(int message_id) { this.message_id = message_id; }
    public void 	setLast_read   	(Date last_read) { this.last_read = last_read; }

}
