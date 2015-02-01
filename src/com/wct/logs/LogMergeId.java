package com.wct.logs;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class LogMergeId
{

    private int   	id;
    private int   	last_id;


    public LogMergeId() {}


    public int   	getId   	() { return id; }
    public int   	getLast_id   	() { return last_id; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setLast_id   	(int last_id) { this.last_id = last_id; }

}
