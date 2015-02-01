<%@ page language="java" import="util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%
	String base = HttpUtil.getBasePath(request);
%>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Compose Message</title>
    <link rel='stylesheet prefetch' href='https://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css'>    
    <link rel="stylesheet" href="<%=base%>css/smart.css"/>
    
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.1.5/angular.min.js"></script>
    <script src="http://angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.6.0.js" type="text/javascript"></script>
    <script src="http://m-e-conroy.github.io/angular-dialog-service/javascripts/dialogs.min.js" type="text/javascript"></script>        
    <script src="<%=base%>js/app.js"></script>
  </head>
  
<body  ng-app="MyApp">  
  <div ng-controller="MsgController" class="row" style="margin:10px 10px 10px 10px">
    <div class="col-md-6">  
	  	<h3>Push Message Demo Page</h3>
	  	<hr>
		<p>
		    <label>发送對象</label> 
		    <select class="form-control" ng-model="channel" 
		    	ng-options="c.name group by c.type for c in channels">
		    	<option value="">--請選擇--</option>
		    </select>
		</p>
		<p>
		    <label>標題</label> 
		    <input ng-model="title" class="form-control" placeholder="">
		</p>
		<p>
		    <label>內容</label> 
		    <textarea ng-model="message" class="form-control" placeholder=""></textarea>
		</p>
		<p>
			<button type="button" class="btn btn-primary" ng-click="doSubmit()">
				Submit
			</button>
		</p>
	</div>
	<div class="col-md-6">
	</div>	
  </div>
  
  
</body>
</html>
