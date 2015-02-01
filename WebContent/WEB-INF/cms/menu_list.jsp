<%@ page language="java" import="java.util.*"
	contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="util.*,java.text.*,com.wct.vod.*"%>
<%
	String basePath = HttpUtil.getBasePath(request);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat minf = new SimpleDateFormat("HH:mm");
	VodHelper vh = new VodHelper();

	List<MenuItem> items = vh.getMenuItems();
	Map<Integer, List<MenuChannel>> mcMap = new SortingMap(
			new MenuChannelMgr(0).retrieveList("", ""))
			.doSortA("getMenu_id");
	Map<Integer, Channel> chanMap = new SortingMap(new ChannelMgr(0)
			.retrieveList("", "")).doSortSingleton("getId");
	Map<Integer, List<VideoChannel>> vidchanMap = new SortingMap(
			new VideoChannelMgr(0).retrieveList("", ""))
			.doSortA("getChannel_id");

	StringBuffer sb = new StringBuffer();

	sb.append("<table border=0>");
	sb.append("<tr>");
	sb.append("<th>编号</th>");
	sb.append("<th>是否隐藏</th>");
	sb.append("<th>菜单名称</th>");
	sb.append("<th>关联频道</th>");
	sb.append("</tr>");

	for (int i = 0; i < items.size(); i++) {
		MenuItem mi = items.get(i);

		sb.append("<tr align=center>");
		sb.append("<td>").append(mi.getId()).append("</td>");
		sb.append("<td>").append(mi.getHidden() == 1 ? "隐藏" : "")
				.append("</td>");
		sb.append("<td align=left>").append(mi.getName()).append(
				"</td>");
		sb.append("<td>");
		List<MenuChannel> mcs = mcMap.get(mi.getId());
		for (int j = 0; mcs != null && j < mcs.size(); j++) {
			MenuChannel mc = mcs.get(j);
			Channel c = chanMap.get(mc.getChannel_id());
			if (c == null)
				continue;
			List<VideoChannel> vcs = vidchanMap.get(c.getId());
			sb.append("&nbsp;").append("<a href=\"")
					.append(basePath).append("cms/channel/info?cid=")
					.append(c.getId()).append("\">")
					.append(c.getName()).append("</a>(").append(
							(vcs == null) ? 0 : vcs.size()).append(")")
					.append("&nbsp;");
		}
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
			$(channel_list_init)
		</script>
	</head>
	<body>
		<!-- Fixed navbar -->
		<div class="navbar navbar-fixed-top">
			<div class="container">
				<a class="navbar-brand" href="#">VOD后台</a>
				<div class="nav-collapse collapse">
					<ul class="nav navbar-nav">
						<li class="active">
							<a href="#">菜单列表</a>
						</li>
						<li>
							<a href="<%=basePath%>cms/channel/list">频道列表</a>
						</li>
					</ul>
				</div>
			</div>
		</div>

		<div class="container">
			<p>
				<%=sb.toString()%>
			</p>
		</div>
	</body>
</html>

