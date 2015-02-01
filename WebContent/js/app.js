var url_prefix = 'http://223.203.192.27:8008/wcttool/smart';
//var url_prefix = 'http://localhost:8080/wct_cms/smart';
var backend_channel_url = '/backend/channels';
var backend_message_send = '/backend/message/send';


var m = angular.module('MyApp', ['ui.bootstrap','dialogs']);    
m.controller('MsgController', function($scope, $http, $dialogs, $rootScope, ChannelService) {
	
	$scope.message = "";
	ChannelService.get(function(channels){
		$scope.channels = channels;
	})
			
	$scope.doSubmit = function() {
		var err = '';
		if ($scope.channel==null) {			
			err += "<li>請選擇發送對象</li>";
		}
		if (!$scope.title || $scope.title.trim().length==0) {
			err += "<li>請輸入标题</li>";
		}
		if (!$scope.message || $scope.message.trim().length==0) {
			err += "<li>請輸入內容</li>";
		}
		if (err.length>0) {
			$dialogs.error(err);
			return;
		}
		var url = url_prefix + backend_message_send;
		var p = new Object;
		p.c = $scope.channel.id;
		p.t = $scope.title;
		p.m = $scope.message;
		
		$dialogs.wait("發送信息中，請等待",100);
		$http.post(url, p).success(function success(){
			$scope.channel = null;
			$scope.title = "";
			$scope.message = "";
			$rootScope.$broadcast('dialogs.wait.complete');
			$dialogs.notify('完成','您的信息发送成功!');
		}).error(function error(data, status){
			$rootScope.$broadcast('dialogs.wait.complete');
			$dialogs.error("服務器發生錯誤 status＝" + status);
		})
	}
})   

.factory('ChannelService', function($http){
	return {
		get:function(success_cb) {
			var url = url_prefix + backend_channel_url;
			$http.get(url).success(function(channels) {
				success_cb && success_cb(channels);
			})
			.error(function(data, status) {
				alert('error calling ' + backend_channel_url + ' status=' + status);
			})
		}
	}
})