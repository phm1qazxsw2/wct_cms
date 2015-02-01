function setBasePath(bp) {
    _basePath = bp;
}

function channel_info_init() {
	$('input[name=video_submit]').bind('click', function(){
		var p = new Object;
		p.vurl = $.trim($('input[name=video_url]').val());
		if (p.vurl.length==0) {
			alert('video url 值为空');
			return;
		}
		p.channel_id = $('input[name=channel_id]').val();
		p.rand = new Date().getTime();
		$.post(_basePath + 'cms/video/add', p, function(data) {
			var r = eval('(' + data + ')');
			if (r.error) {
				alert('错误发生:' + r.msg);
			}
			else {
				alert('done!');
				history.go(0);
			}
		})
	})
	
	$('.del-video').click(function(e) {
		e.preventDefault();
		if (!confirm('确定要删除?')) {
			return;
		}
		var p = new Object;
		p.video_id = $(this).attr('video_id');
		p.channel_id = $('input[name=channel_id]').val();
		p.rand = new Date().getTime();
		$.post(_basePath + 'cms/video/remove', p, function(data) {
			var r = eval('(' + data + ')');
			if (r.error) {
				alert('错误发生:' + r.msg);
			}
			else {
				alert('done!');
				history.go(0);
			}
		})
	})
	
	var p = new Object;
	p.channel_id = $('input[name=channel_id]').val();
	$.get(_basePath + 'cms/channel/menus', p, function(data) {
		$('#menu-area').html(data);
		$('#menu-channel-add').click(function(e){
			var sel = $('select[name=add_id]').get(0);
			var param = new Object;
			param.menu_id = sel[sel.selectedIndex].value;
			if (param.menu_id==0) {
				alert('请选择一菜单');
				return;
			}
			param.channel_id = $('input[name=channel_id]').val();
			param.rand = new Date().getTime();
			$.post(_basePath + 'cms/channel/menu/add', param, function(data) {
				var r = eval('(' + data + ')');
				if (r.error) {
					alert('错误发生:' + r.msg);
				}
				else {
					alert('done!');
					history.go(0);
				}
			})
		})
		$('.unconnect').click(function(e){
			e.preventDefault();
			if (!confirm('确定去除菜单关联?')) {
				return;
			}
			var p = new Object;
			p.menu_id = $(this).attr('menu_id');
			p.channel_id = $(this).attr('channel_id');
			$.post(_basePath + 'cms/channel/menu/remove', p, function(data) {
				var r = eval('(' + data + ')');
				if (r.error) {
					alert('错误发生:' + r.msg);
				}
				else {
					alert('done!');
					history.go(0);
				}
			})
		})
	})
}

function channel_list_init() {
	$('input[name=channel_submit]').click(function(e){
		var p = new Object;
		p.name = $('input[name=channel_name]').val();
		if (p.name.length==0) {
			alert('请输入名称');
			return;
		}
		p.rand = new Date().getTime();
		$.post(_basePath + 'cms/channel/add', p, function(data) {
			var r = eval('(' + data + ')');
			if (r.error) {
				alert('错误发生:' + r.msg);
			}
			else {
				alert('done!');
				history.go(0);
			}
		})
	})
	
	$('.del-channel').click(function(e) {
		e.preventDefault();
		if (!confirm('确定删除？'))
			return;
		var p = new Object;
		p.channel_id = $(this).attr('channel_id');
		p.rand = new Date().getTime();
		$.post(_basePath + 'cms/channel/remove', p, function(data) {
			var r = eval('(' + data + ')');
			if (r.error) {
				alert('错误发生:' + r.msg);
			}
			else {
				alert('done!');
				history.go(0);
			}
		})
	})
}