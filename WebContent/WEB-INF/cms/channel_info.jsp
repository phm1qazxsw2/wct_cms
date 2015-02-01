<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.vod.*"%>
<%
	String basePath = HttpUtil.getBasePath(request);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat minf = new SimpleDateFormat("HH:mm");
	HttpParams hp = new HttpParams(request);
	int cid = hp.getInt("cid", 0);
	Channel c = new ChannelMgr(0).find("id=" + cid);

	// get videos 
	List<VideoChannel> vcs = new VideoChannelMgr(0).retrieveList(
			"channel_id=" + cid, "");
	String videoIds = new ConsId(vcs).makeIds("getVideo_id");
	List<Video> vids = new VideoMgr(0).retrieveList("id in ("
			+ videoIds + ")", "");
	Map<Integer, List<SrcUrl>> srcurlMap = new SortingMap(
			new SrcUrlMgr(0).retrieveList("video_id in (" + videoIds
					+ ")", "")).doSortA("getVideo_id");

	StringBuffer sb = new StringBuffer();
	sb.append("<table border=0>");
	sb.append("<tr>");
	sb.append("<th>编号</th>");
	sb.append("<th>类型</th>");
	sb.append("<th>名称</th>");
	sb.append("<th>采集</th>");
	sb.append("<th>海报</th>");
	sb.append("<th>地区</th>");
	sb.append("<th>影片信息</th>");
	sb.append("<th>片源</th>");
	sb.append("<th></th>");
	sb.append("</tr>");

	for (int i = 0; i < vids.size(); i++) {
		Video vid = vids.get(i);
		sb.append("<tr align=center>");
		sb.append("<td>").append(vid.getId()).append("</td>");
		sb.append("<td>").append(vid.getTypeName()).append("</td>");
		sb.append("<td>").append(vid.getName()).append("</td>");
		sb.append("<td>").append(datef.format(vid.getUpdated()))
				.append("</td>");
		sb.append("<td>").append("<img class=\"smallpic\" src=\"")
				.append(vid.getPic()).append("\">").append("</td>");
		sb.append("<td>").append(vid.getArea()).append("</td>");

		sb.append("<td>");
		{
			if (vid.getType() == Video.TYPE_MOVIE) {
				sb.append(TextUtil.getInt(vid.getYear())).append("年");
			} else {
				if (vid.getCheck_new() == 1)
					sb.append("更新至");
				else
					sb.append("共");
				sb.append(vid.getEpisode_num());
			}
		}
		sb.append("</td>");
		sb.append("<td>");
		{
			List<SrcUrl> urls = srcurlMap.get(vid.getId());
			if (urls == null)
				urls = new ArrayList<SrcUrl>();
			Map<String, List<SrcUrl>> siteurlMap = new SortingMap(urls)
					.doSortA("getSite");
			List<SrcUrl> uu = siteurlMap.get("youku.com");
			if (uu != null) {
				sb.append("&nbsp;").append("<a target=_blank href=\"")
						.append(uu.get(0).getUrl()).append("\">")
						.append("youku").append("</a>&nbsp;");
			}
			uu = siteurlMap.get("letv.com");
			if (uu != null) {
				sb.append("&nbsp;").append("<a target=_blank href=\"")
						.append(uu.get(0).getUrl()).append("\">")
						.append("letv").append("</a>&nbsp;");
			}
			uu = siteurlMap.get("qq.com");
			if (uu != null) {
				sb.append("&nbsp;").append("<a target=_blank href=\"")
						.append(uu.get(0).getUrl()).append("\">")
						.append("qq").append("</a>&nbsp;");
			}
		}
		sb.append("</td>");
		sb.append("<td>");
		sb.append("<a target=_blank href=\"")
				.append(vid.getHao123url()).append("\">源</a> | ");
		sb.append("<a href=\"#\" class=\"del-video\" video_id=\"")
				.append(vid.getId()).append("\">删除</a>");
		sb.append("</td>");
		sb.append("</tr>");
	}
	sb.append("</table>");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<link href="<%=basePath%>dist/css/bootstrap.css" rel="stylesheet">
		<link href="<%=basePath%>css/vod.css" rel="stylesheet" type="text/css">
		<script type="text/javascript"
			src="<%=basePath%>js/jquery-1.7.2.min.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/jquery.blockUI.js"></script>
		<script type="text/javascript" src="<%=basePath%>js/wctcms.js"></script>
		<script type="text/javascript">
			setBasePath('<%=basePath%>');
			$(channel_info_init)
		</script>
	</head>
	<body>
		<div class="navbar navbar-fixed-top">
			<div class="container">
				<a class="navbar-brand" href="#">VOD后台</a>
				<div class="nav-collapse collapse">
					<ul class="nav navbar-nav">
						<li>
							<a href="<%=basePath%>cms/menu/list">菜单列表</a>
						</li>
						<li>
							<a href="<%=basePath%>cms/channel/list">频道列表</a>
						</li>
						<li class="active">
							<a href="#">频道信息</a>
						</li>
					</ul>
				</div>
			</div>
		</div>

		<div class="container">
			<h2>
				频道名称
			</h2>
			<input type=text name="channel_name" value="<%=c.getName()%>"
				style="width: 200px">
			<input type=button name="channel_submit" value="修改">
			<div id="menu-area">
			</div>
			<br />
			<br />
			<input type=hidden name="channel_id" value="<%=cid%>">
			<input type=text name="video_url" class="input_txt">
			<input type=button name="video_submit" value="新增视频">
			<br />
			<br />
			<%=sb.toString()%>
		</div>
	</body>
</html>

