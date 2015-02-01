<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.vod.*,dbo.*"%>
<%
	HttpParams hp = new HttpParams(request);
	int video_id = hp.getInt("video_id", 0);
	
	boolean commit = false;
	int tran_id = 0;
		
	try {		
		tran_id = Manager.startTransaction();
		
		new VideoChannelMgr(tran_id).executeSQL("delete from video_channel where " +
			"video_id=" + video_id);

		List<Episode> eps = new EpisodeMgr(tran_id).retrieveList("video_id=" + video_id, "");
		String episodeIds = new ConsId(eps).makeIds("getId");
		new SrcUrlMgr(tran_id).executeSQL("delete from source_url where episode_id in (" + 
			episodeIds + ") or video_id=" + video_id);
		new EpisodeMgr(tran_id).executeSQL("delete from episode where video_id=" + video_id);
		new VideoMgr(tran_id).executeSQL("delete from video where id=" + video_id);

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
