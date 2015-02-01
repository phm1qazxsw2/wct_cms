package com.wct.vod;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class MenuChannel
{

    private int   	id;
    private int   	menu_id;
    private int   	channel_id;


    public MenuChannel() {}


    public int   	getId   	() { return id; }
    public int   	getMenu_id   	() { return menu_id; }
    public int   	getChannel_id   	() { return channel_id; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setMenu_id   	(int menu_id) { this.menu_id = menu_id; }
    public void 	setChannel_id   	(int channel_id) { this.channel_id = channel_id; }

}
