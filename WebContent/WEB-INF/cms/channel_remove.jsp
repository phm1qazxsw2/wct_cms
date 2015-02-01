<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.vod.*,dbo.*"%>
<%
	HttpParams hp = new HttpParams(request);
	int channel_id = hp.getInt("channel_id", 0);
	
	boolean commit = false;
	int tran_id = 0;
		
	try {		
		tran_id = Manager.startTransaction();
		
		List<Video> videos = new VideoMgr(tran_id).retrieveList("channel_id=" + channel_id, "");
		String vids = new ConsId(videos).makeIds("getId");
		List<Episode> eps = new EpisodeMgr(tran_id).retrieveList("video_id in (" + vids + ")", "");
		String episodeIds = new ConsId(eps).makeIds("getId");
		new SrcUrlMgr(tran_id).executeSQL("delete from source_url where episode_id in (" + 
			episodeIds + ") or video_id in (" + vids + ")");
		new EpisodeMgr(tran_id).executeSQL("delete from episode where video_id in (" + vids + ")");
		new VideoMgr(tran_id).executeSQL("delete from video where id in (" + vids + ")");
		new VideoChannelMgr(tran_id).executeSQL("delete from video_channel where channel_id=" + channel_id);
		new ChannelMgr(tran_id).executeSQL("delete from channel where id=" + channel_id);
		
		Manager.commit(tran_id);
	    commit = true;
		
		out.println("{ \"success\":\"1\" }");
	}
	catch (Exception e) {
		e.printStackTrace();
		String msg = e.getMessage();
		out.println("{ \"error\":\"1\", msg:\"" + msg + "\" }");
	}
	finally {
	    if (!commit)
	        Manager.rollback(tran_id);
	}
%>
