<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.vod.*"%>
<%
	HttpParams hp = new HttpParams(request);
	int channel_id = hp.getInt("channel_id", 0);
	String vurl = hp.getStr("vurl", null);
	
	try {
		if (channel_id==0) {
			throw new Exception("channel id is 0");
		}
		if (vurl==null || vurl.length()==0) {
			throw new Exception("vurl is missing");
		}
		
		int c = vurl.indexOf("id=");
		if (c<0) {
			throw new Exception("not a valid url");
		}
		
		int c2 = vurl.indexOf("&", c+1);
		String vid = (c2>0)?vurl.substring(c+3, c2):vurl.substring(c+3);
		
		System.out.println("key='hao123-" + vid + "'");
		
		Video v = new VideoMgr(0).find("`key`='hao123-" + vid + "'");
		if (v==null) {
			VodHelper vh = new VodHelper();
			v = vh.grep(vurl);
		}
		
		List<SrcUrl> srcurls = new SrcUrlMgr(0).retrieveList("video_id=" + v.getId()
			+ " and site in ('youku.com', 'qq.com', 'letv.com')","");
		if (srcurls.size()==0) {
			out.println("{ \"error\":\"2\", msg:\"此片尚无可看视频，可能是预告海报, 或是没有可用的源(youku.com, letv.com, qq.com)\" }");
			return;
		}
		
		if (new VideoChannelMgr(0).numOfRows("video_id=" + v.getId() + " and channel_id=" + channel_id)==0) {
			VideoChannel vc = new VideoChannel();
			vc.setChannel_id(channel_id);
			vc.setVideo_id(v.getId());
			new VideoChannelMgr(0).create(vc);
		}
		
		out.println("{ \"success\":\"1\" }");
	}
	catch (Exception e) {
		e.printStackTrace();
		String msg = e.getMessage();
		out.println("{ \"error\":\"1\", msg:\"" + msg + "\" }");
	}
%>
