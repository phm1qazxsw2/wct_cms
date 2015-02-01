package com.wct.vod;


import java.util.*;
import java.sql.*;
import java.util.Date;


public class SrcUrl
{

    private int   	id;
    private int   	episode_id;
    private int   	video_id;
    private String   	site;
    private String   	url;


    public SrcUrl() {}


    public int   	getId   	() { return id; }
    public int   	getEpisode_id   	() { return episode_id; }
    public int   	getVideo_id   	() { return video_id; }
    public String   	getSite   	() { return site; }
    public String   	getUrl   	() { return url; }


    public void 	setId   	(int id) { this.id = id; }
    public void 	setEpisode_id   	(int episode_id) { this.episode_id = episode_id; }
    public void 	setVideo_id   	(int video_id) { this.video_id = video_id; }
    public void 	setSite   	(String site) { this.site = site; }
    public void 	setUrl   	(String url) { this.url = url; }

}
