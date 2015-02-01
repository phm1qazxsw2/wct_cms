<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.vod.*"%>
<%
	String basePath = HttpUtil.getBasePath(request);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat minf = new SimpleDateFormat("HH:mm");
	VodHelper vh = new VodHelper();

	// get videos 
	List<Channel> channels = new ChannelMgr(0).retrieveList("", "");
	Map<Integer, List<MenuChannel>> mcsMap = new SortingMap(
			new MenuChannelMgr(0).retrieveList("", ""))
			.doSortA("getChannel_id");
	Map<Integer, List<VideoChannel>> vcsMap = new SortingMap(
			new VideoChannelMgr(0).retrieveList("", ""))
			.doSortA("getChannel_id");
	Map<String, MenuItem> menuMap = new SortingMap(vh.getMenuItems())
			.doSortSingleton("getId");

	StringBuffer sb = new StringBuffer();
	if (channels.size() == 0) {
		sb.append("目前没有任何频道");
	} else {
		sb.append("<table border=0>");
		sb.append("<tr>");
		sb.append("<th>编号</th>");
		sb.append("<th>名称</th>");
		sb.append("<th>影片数</th>");
		sb.append("<th>上次更新时间</th>");
		sb.append("<th>关联菜单</th>");
		sb.append("<th></th>");
		sb.append("</tr>");

		for (int i = 0; i < channels.size(); i++) {
			Channel c = channels.get(i);
			List<MenuChannel> mcs = mcsMap.get(c.getId());
			List<VideoChannel> vcs = vcsMap.get(c.getId());

			sb.append("<tr align=center>");
			sb.append("<td>").append(c.getId()).append("</td>");
			sb.append("<td>").append("<a href=\"")
					.append(basePath).append("cms/channel/info?cid=")
					.append(c.getId()).append("\">")
					.append(c.getName()).append("</td>");
			sb.append("<td>").append((vcs == null) ? 0 : vcs.size())
					.append("</td>");
			sb.append("<td>").append("").append("</td>");

			StringBuffer sb2 = new StringBuffer();
			for (int j = 0; mcs != null && j < mcs.size(); j++) {
				MenuItem mi = menuMap.get(mcs.get(j).getMenu_id());
				if (sb2.length() > 0)
					sb2.append("<br/>");
				sb2.append(mi.getName());
			}
			sb.append("<td align=left>").append(sb2.toString()).append(
					"</td>");
			sb
					.append("<td>")
					.append(
							"<a href=\"#\" class=\"del-channel\" channel_id=\"")
					.append(c.getId()).append("\">").append("删除")
					.append("</a></td>");
			sb.append("</tr>");
		}
		sb.append("</table>");
	}
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
			$(channel_list_init)
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
						<li class="active">
							<a href="#">频道列表</a>
						</li>
					</ul>
				</div>
			</div>
		</div>

		<div class="container">
			<p>
				<%=sb.toString()%>
				<br />
				名称：
				<br />
				<input type=text name="channel_name" style="width: 250px">
				<input type=button name="channel_submit" value="新增频道">
			</p>
		</div>
	</body>
</html>

