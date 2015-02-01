<%@ page language="java" import="util.*,com.phm.smart.*"
	pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<html lang="en" ng-app="myApp">
  <head>
    <meta charset="UTF-8" />
    <title>Document</title>
    <script type="text/javascript" src="http://cdn.pubnub.com/pubnub-3.7.4.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.0.7/angular.min.js"></script>
    <style>
    	.test {
    		display:block;
    	}
    	.msg-input {
    		width:200px; height:100px;
    	}
    </style>
    <script>
    
	    var pubnub = PUBNUB.init({
	        subscribe_key: 'sub-c-0ef22b3e-8c0f-11e4-98f5-02ee2ddab7fe',
	        publish_key: 'pub-c-6f184a5d-5621-47fc-96ae-8f5ee9a90fa6',
	    });
    
    	angular.module('myApp',[])
    	.controller('MsgController', function($scope, $http) {
    		$scope.school = 12;
    		$scope.doSubmit = function() {
    			var msg = $scope.message;
    			var channel_name = 'apns-test' + new Date().getTime();
    			$http.get("../get/devices").success(function(data){
    				var device_num = 0;
					for (var i=0; i<data.length; i++) {
						var token = data[i].token;
						pubnub.mobile_gw_provision ({
				            device_id: token,
				            channel : channel_name,
				            op: 'add',
				            gw_type: 'apns',
				            error : function(msg){console.log(msg);},
				            callback : function(msg){device_num++;}
				        });						
					}
					var tmp = setInterval(function(){
						console.log("data length=" + data.length + " device_num=" + device_num);
						if (device_num==data.length) {
							alert("before sending msg " + msg);
							clearInterval(tmp);
							
							var message = PNmessage();
					        message.pubnub = pubnub;
					        message.callback = function(msg) {
					            alert(msg + ' sent');
					            $scope.message = "";
					            $scope.$apply();
					        };
					        message.error = function (msg){
					            console.log(msg);
					        };
					        message.channel = channel_name;
					        message.apns = {
					            alert:msg
					        };
					        message.publish();					        
						}
					},50);
					
    			}).error(function(err){
    				alert(err);
    			})
    		}
    	})
    </script>
  </head>
  
  <div ng-controller="MsgController">
  	<form ng-submit="doSubmit()">
	  <label class="test">Test Push:</label>
	  <select class="test" ng-model="school">
	  	<option value="11">惠文幼兒園</option>
	  	<option value="12">惠文安親班</option>
	  </select>
	  <textarea class="test msg-input" ng-model="message"></textarea>  
	  <input  class="test" type="submit" value="Submit" />
	</form>
  </div>
</html>
