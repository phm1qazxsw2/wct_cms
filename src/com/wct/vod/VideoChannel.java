package com.wct.vod;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class VideoChannel
{

    private int   	id;
    private int   	video_id;
    private int   	channel_id;


    public VideoChannel() {}


    public int   	getId   	() { return id; }
    public int   	getVideo_id   	() { return video_id; }
    public int   	getChannel_id   	() { return channel_id; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setVideo_id   	(int video_id) { this.video_id = video_id; }
    public void 	setChannel_id   	(int channel_id) { this.channel_id = channel_id; }

}
