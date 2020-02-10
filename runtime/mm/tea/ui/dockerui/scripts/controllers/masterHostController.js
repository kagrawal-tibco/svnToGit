/**
 * Created by Priyanka on 28/04/16
 */
beConfigModule.controller("masterHostCtrl",['$scope','$location','$q','$http','$rootScope','$timeout', 'teaLocation', 'teaObjectService', 'teaScopeDecorator','ReferenceObjectConfigService','StorageService','teaEventNotifications','utilService',
                                       function ($scope,$location,$q,$http,$rootScope,$timeout, teaLocation, teaObjectService, teaScopeDecorator ,ReferenceObjectConfigService,StorageService,teaEventNotifications,utilService){
	$('#loaderdiv').show(); 
	$rootScope.loadingComplete.then(function( apps ){
		$('#loaderdiv').hide(); 
		$scope.search={};
		$scope.check={};
		$scope.filteredValues={};
		$scope.filteredValues.allInstancesChecked = false;
		$scope.masterHost=ReferenceObjectConfigService.getSelectedMasterHost();
		$scope.extrenalJars={};
		$scope.extrenalJars.files=[];
		$scope.beHomeUsed=[];
		createBreadCrumbs();
		$scope.viewType ="instances";
		function createBreadCrumbs(){
			/*Creating breadCrumbs*/
			ReferenceObjectConfigService.setBreadCrumbObject();
			var leftNavObject =ReferenceObjectConfigService.getSelectedNavObject();
			ReferenceObjectConfigService.getBreadCrumbObject().push({name:'Machine Management',type:{name : 'Machine Management'}});
			ReferenceObjectConfigService.getBreadCrumbObject().push($scope.masterHost);
			if(Storage)
				localStorage.setItem("breadCrumbs",JSON.stringify(ReferenceObjectConfigService.getBreadCrumbObject()));
			$scope.breadCrumbs=ReferenceObjectConfigService.getBreadCrumbObject();
		}
		
		var subscribe = true;
	    $scope.notify = function (angularEvent, events) {
	    	getMasterHostInstances();
	    }
	    $scope.$on('teaEventNotifications', $scope.notify);
	    $scope.doSubscribe = function() {
	        if (subscribe) {
	            subscribe = false;
	            angular.forEach($scope.masterHostInstances, function(instance) {
	            	teaEventNotifications.addObjectIdToSubscription(instance.agentId, window.tea.location.info.agentType, instance.type.name, "", [Event.STATUS_CHANGE]);
	            })
	            teaEventNotifications.subscribe();
	        }
	    }
	    
		function getInstances(){
			$('#loaderdiv').show(); 
			var defer = $q.defer();
			teaObjectService.invoke({
					'agentID'  : $scope.masterHost.agentId,
					'agentType': $scope.masterHost.type.agentType,
					'objectType': $scope.masterHost.type.name,           		
					'objectKey': $scope.masterHost.key,
					'operation' : 'getHostInstances',
					'methodType':'READ'
            	}).then(function (instances) {
            		var oldFilteredList = angular.copy($scope.masterHostInstances);
            		angular.forEach(instances, function(instance,index) {
        				if(instance.status == "Running" && instance.upTime!= -1){
        					var upTime=utilService.getFormattedDate(instance.upTime);
        				}else
        					var upTime = "";
        				instances[index].upTime=upTime;
        				instances[index].status={state:instance.status};
        				instances[index].config={cpuUsage:instance.cpuUsage,memoryUsage:instance.memoryUsage,healthStatus:instance.healthStatus,status:instance.status};
        				
        				if(oldFilteredList && oldFilteredList.length == instances.length){ //to keep previous group selection
        					instances[index].isChecked =oldFilteredList[index].isChecked;
        				}else
        					$scope.filteredValues.allInstancesChecked = false;
        				
        				
            		});	
            		var be=$scope.masterHost.config.be;
            		if(typeof instances!=='undefined' && null!==instances && instances.length>0){
    				    for(var i=0;i<instances.length;i++){
    				    	var instance=instances[i];
    				    	if(typeof instance!=='undefined' && isBEId(be,instance.beId)){
    				    	}
    				    }
    					
    				}else{

    					for (var i=0;i<be.length;i++){
    						$scope.beHomeUsed[be[i].id]=false;
    					}    				
    				}
            		defer.resolve(instances);
            	}, function (failReason) {
            		defer.reject('Error retrieving machine instances');
            	});
			return defer.promise;
		}
		function isBEId(be,instanceBEId){
			for (var i=0;i<be.length;i++){
				if(be[i].id===instanceBEId){
					$scope.beHomeUsed[be[i].id]=true;
				}else{
					$scope.beHomeUsed[be[i].id]=false;
				}
			}
		}
		function getMasterHostInstances(){
			$('#loaderdiv').show(); 
			var defferedResp = getInstances();
			defferedResp.then(function (instances) {
				$scope.masterHostInstances = instances;
				$scope.doSubscribe();
				$scope.showIndividualOperations();
				$('#loaderdiv').hide(); 
	        }, function (failReason) {
	        	$('#loaderdiv').hide(); 
        		$scope.notification = {
			            severity: 'error',
			            msg :'Error retrieving instances',
			            show: true
			    };
	        });
		}
		$scope.sort = {
	            column: '',
	            descending: false
	    }
		$scope.onSortCall = function(column) {
            var sort = $scope.sort;
 
            if (sort.column == column) {
                sort.descending = !sort.descending;
            }else if(sort.column == ''){
            	 sort.column = column;
                 sort.descending =  !sort.descending;
            }else {
                sort.column = column;
                sort.descending = false;
            }
        }
		$scope.filterFunction = function(instance){
			if(($scope.search.name == undefined || $scope.search.name == null || $scope.search.name == "" || (angular.uppercase(instance.name).indexOf(angular.uppercase($scope.search.name)) > -1)) &&
			   ($scope.search.status == undefined || $scope.search.status == null || $scope.search.status == "" || ((angular.uppercase(instance.status.state).indexOf(angular.uppercase($scope.search.status)) > -1)|| (angular.uppercase(instance.config.healthStatus).indexOf(angular.uppercase($scope.search.status)) > -1))) &&
			   ($scope.search.jmxPort == undefined || $scope.search.jmxPort == null || $scope.search.jmxPort == "" || (angular.uppercase(instance.jmxPort + '').indexOf(angular.uppercase($scope.search.jmxPort)) > -1)) &&
			   ($scope.search.puId == undefined || $scope.search.puId == null || $scope.search.puId == "" || (angular.uppercase(instance.puId).indexOf(angular.uppercase($scope.search.puId)) > -1)) &&
			   ($scope.search.processId == undefined || $scope.search.processId == null || $scope.search.processId == "" || (angular.uppercase(instance.processId + '').indexOf(angular.uppercase($scope.search.processId)) > -1)) &&
			   ($scope.search.appName == undefined || $scope.search.appName == null || $scope.search.appName == "" || (angular.uppercase(instance.basicAttributes.appName + '').indexOf(angular.uppercase($scope.search.appName)) > -1)) &&
			   ($scope.search.deploymentStatus == undefined || $scope.search.deploymentStatus == null || $scope.search.deploymentStatus == "" || (angular.uppercase(instance.deploymentStatus).indexOf(angular.uppercase($scope.search.deploymentStatus)) > -1))){
				return true;
			}
			else
				return false;
		}
		$scope.selectAllInstances = function () {			
            for (var i = 0; i < $scope.masterHostInstances.length; i++) {
            		$scope.masterHostInstances[i].isChecked = $scope.filteredValues.allInstancesChecked;
            }
		}
		$scope.selectInstanceEntity = function () {          
			for (var i = 0; i < $scope.masterHostInstances.length; i++) {
				if (!$scope.masterHostInstances[i].isChecked) {
					$scope.filteredValues.allInstancesChecked = false;
					return;
				}
			}          
			$scope.filteredValues.allInstancesChecked = true;
		}
		function getSelectedInstancesList(){
    		var selectedInstancesList = []; 
			if($scope.masterHostInstances!=undefined){
				for (var i = 0; i < $scope.masterHostInstances.length; i++) {
		            if ($scope.masterHostInstances[i].isChecked) {
		                var instance = $scope.masterHostInstances[i];
		                selectedInstancesList.push(instance.key);	                   
		            }
		        } 
    		}
			return selectedInstancesList;
		}
		$scope.startInstances = function(){
			$('#loaderdiv').show(); 
			var selectedInstancesList = getSelectedInstancesList(); 
			teaObjectService.invoke({
				'agentID'  : $scope.masterHost.agentId,
				'agentType': $scope.masterHost.type.agentType,
				'objectType': $scope.masterHost.type.name,           		
				'objectKey': $scope.masterHost.key,
				'operation' : 'start',
				'methodType': 'UPDATE',
				'params': {
					instances : selectedInstancesList
				}       			
			}).then(function (data) {
				$('#loaderdiv').hide(); 
			}, function (failReason) {
				$('#loaderdiv').hide(); 
				$scope.notification = {
		            severity: 'error',
		            msg : failReason.message,
		            show: true
				};
			});	 
		}
		$scope.stopInstances = function(){
			$('#loaderdiv').show(); 
			var selectedInstancesList = getSelectedInstancesList(); 
			teaObjectService.invoke({
				'agentID'  : $scope.masterHost.agentId,
				'agentType': $scope.masterHost.type.agentType,
				'objectType': $scope.masterHost.type.name,           		
				'objectKey': $scope.masterHost.key,
				'operation' : 'stop',
				'methodType': 'UPDATE',
				'params': {
					instances : selectedInstancesList
				}       			
			}).then(function (data) {
				$('#loaderdiv').hide(); 
			}, function (failReason) {
				$('#loaderdiv').hide(); 
				$scope.notification = {
		            severity: 'error',
		            msg : failReason.message,
		            show: true
				};
			});	 
		}
		$scope.killInstances = function(){
			$('#loaderdiv').show(); 
			var selectedInstancesList = getSelectedInstancesList(); 
			teaObjectService.invoke({
				'agentID'  : $scope.masterHost.agentId,
				'agentType': $scope.masterHost.type.agentType,
				'objectType': $scope.masterHost.type.name,           		
				'objectKey': $scope.masterHost.key,
				'operation' : 'kill',
				'methodType': 'UPDATE',
				'params': {
					instances : selectedInstancesList
				}       			
			}).then(function (data) {
				$('#loaderdiv').hide(); 
			}, function (failReason) {
				$('#loaderdiv').hide(); 
				$scope.notification = {
		            severity: 'error',
		            msg : failReason.message,
		            show: true
				};
			});	 
		}
		$scope.dismiss =function(){
			$scope.notification.show = false;
			if(!utilService.getAgentReachable()){
				$location.path("/");
			}
		}
		$scope.reloadPage = function(){
			getMasterHostInstances();
		}
		$scope.showIndividualOperations = function(){
			var selectedInstances =[];
			$scope.check.allStarted = true;
			$scope.check.allStopped = true;
			$scope.check.allKilled= true;
			for (var i = 0; i < $scope.masterHostInstances.length; i++) {
	            if ($scope.masterHostInstances[i].isChecked && $scope.masterHostInstances[i].status.state == 'Stopped') {
	            	$scope.check.allStarted = false;
	            }
	            if ($scope.masterHostInstances[i].isChecked && $scope.masterHostInstances[i].status.state == 'Running') {
	            	$scope.check.allStopped = false;
	            }
	            if ($scope.masterHostInstances[i].isChecked && ($scope.masterHostInstances[i].status.state == 'Running' || $scope.masterHostInstances[i].status.state == 'Starting' || $scope.masterHostInstances[i].status.state == 'Stopping')) {
	            	$scope.check.allKilled = false;
	            }
	        } 
		}
		$scope.showOperations = function(){
	    	return getSelectedInstancesList().length > 0; 
	    }
		//Check user has permission or not
		$scope.hasPermission = function(permission){
			var privileges=ReferenceObjectConfigService.getReferenceObject('PRIVILEGES');
			return utilService.checkPermission(permission,privileges);
		}; 
		$scope.browseFile = function(id){
			$('#'+id).click();
		}
		$scope.setPathValue = function(id,fileId){
			$('#'+id).val($('#'+fileId).val().replace(/^.*[\\\/]/, ''));
		} 
		$scope.showUploadExternalJars = function(){
			$scope.isError = false;
			$scope.extrenalJars.files =[];
			$scope.extrenalJars.beId ="";
			$scope.extrenalJars.zipFileName="";
			$scope.extrenalJars.isErrorWhileUploading = false;
			$scope.extrenalJars.errorMessage="";
			$('#jarFiles').val("");
			$('#selectedFile').val("");
			$('#uploadExternalJarsModal').modal({
				show: true,
				keyboard: false,
				backdrop: 'static'
			});
		}
		
		$scope.finishUpload = function(){
			if(angular.isUndefined($scope.extrenalJars.files) || $scope.extrenalJars.files.length===0){
				$scope.extrenalJars.isErrorWhileUploading = true;
				$scope.extrenalJars.errorMessage="upload files";
				return;
			}
			$('#loaderdiv').show(); 			
			
			for(var i=0;i<$scope.extrenalJars.files.length;i++){
				var fd = new FormData();
				fd.append("file", $scope.extrenalJars.files[i]._file);					
				$http.post("/teas/fileupload/", fd, {
		            transformRequest: angular.identity,
		            headers: {
		                "Content-Type": undefined
		            }
		        }).success(function(data, status, headers, config) {        	  
		 			 	teaObjectService.invoke({
			 				'agentID'  : $scope.masterHost.agentId,
				            'agentType': $scope.masterHost.type.agentType,
			           		'objectType': $scope.masterHost.type.name,           		
			           		'objectKey': $scope.masterHost.key,
			            	'operation': 'uploadExternalJars',
			                'methodType': 'UPDATE',
			                'params': {                	
			                	jarFiles : data[0],
			                	beId:$scope.extrenalJars.beId
				             } 
			 			}).then(function (message) { 
			 				$('#loaderdiv').hide();
			 				$scope.extrenalJars.isErrorWhileUploading = false;
			 				$scope.extrenalJars.errorMessage="";
			 				$('#jarFiles').val("");
			 				$('#selectedFile').val("");
			 				$('.modal-backdrop').hide();
			 				$('#uploadExternalJarsModal').modal('hide');
			 		    }, function (failReason) {
		 		    		$('#loaderdiv').hide(); 
		 		    		$scope.extrenalJars.isErrorWhileUploading = true;
							$scope.extrenalJars.errorMessage=failReason.message;
		 		    	}); 
		        });
			}
			
		}
		 $scope.setViewType = function(type){
		    	$scope.viewType = type;
		    }
		    $scope.isSet = function (viewType) {
		        return $scope.viewType === viewType;
		    };
		getMasterHostInstances();
	}, function (failReason) {
		$('#loaderdiv').hide(); 
    })
   
}]);	
	