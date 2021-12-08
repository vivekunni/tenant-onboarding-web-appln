'use strict'

var module = angular.module('demo.controllers', []);
module.controller("UserController", [ "$scope", "UserService",
		function($scope, UserService) {

			$scope.userDto = {
				userId : null,
				userName : null,
				storyPercent : null,
				workdToday : null,
				workTomo : null,
				impediments : null
			};
		
			UserService.getUserById(1).then(function(value) {
				console.log(value.data);
			}, function(reason) {
				console.log("error occured");
			}, function(value) {
				console.log("no callback");
			});

			$scope.saveUser = function() {
				
			  if(($scope.userDto.jiraID != null) && ($scope.userDto.reqEmail != null) && ($scope.userDto.appln != null) && ($scope.userDto.env != null) && ($scope.userDto.clientTenantID != null) && ($scope.userDto.spnName != null) && ($scope.userDto.adSvc != null)) {	
				
				if (confirm("Press OK to confirm Tenant ID submission!")) {								
					UserService.saveUser($scope.userDto).then(function() {
						console.log("works");
						UserService.getAllUsers().then(function(value) {						
    					console.log(value);
						console.log(value.data);						
						$scope.allUsers= value.data;
						console.log($scope.allUsers);	
						alert ("Tenant ID Successfully Submitted!"); 					
					}, function(reason) {
						console.log(reason);
						console.log("error occured");
					}, function(value) {
						console.log(value);
						console.log("no callback");
					});

					$scope.userDto = {
						userId : null,
						userName : null,
						storyPercent : null,
						workdToday : null,
						workTomo : null,
						impediments : null
					};
				}, function(reason) {
					console.log("error occured");
				}, function(value) {
					console.log("no callback");
				});
			} else {
    			alert ("Tenant ID Submission Cancelled!");
 			}
 		  }
		}
		
	}]);