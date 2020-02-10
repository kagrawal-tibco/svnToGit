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
		$scope.checkedApplications = {};
		$scope.filteredValues.allInstancesChecked = false;
		$scope.masterHost=ReferenceObjectConfigService.getSelectedMasterHost();
		$scope.appObject = apps;
		$scope.extrenalJars={};
		$scope.extrenalJars.files=[];
		$scope.appInstances = [];
		$scope.applicationInstance = {};
		$scope.beHomeUsed=[];
		createBreadCrumbs();
		$scope.viewType ="instances";
		$scope.newHostObj = {};
		$scope.hostToEdit = {};
					
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
		$scope.getApplicationInstanceDetails = function(
				applicationName) {
			getMasterHostInstances();
			var count  = 0;
			$scope.appInstances = [];
			for(var i = 0; i < $scope.masterHostInstances.length; i++){
				if($scope.masterHostInstances[i].basicAttributes.applicationName == applicationName.name){
					$scope.appInstances[count] = $scope.masterHostInstances[i];
			         count = count + 1;
				}
			}
			$scope.applicationInstance[applicationName.name] = $scope.appInstances;
		}
		
		function getMasterHostInstances(){
			$('#loaderdiv').show(); 
			var defferedResp = getInstances();
			defferedResp.then(function (instances) {
				$scope.masterHostInstances = instances;
				$scope.doSubscribe();
				$scope.showIndividualOperations();
				var apps = $scope.appObject;
			    $scope.appObject = [];
			    var count = 0;
			    $scope.app = null;
				var isAppExist = true;
				var behosts = ReferenceObjectConfigService.getReferenceObject("beHosts");
				angular.forEach(behosts, function(host){
					if(angular.equals(host.name, $scope.masterHost.name))
						angular.copy(host.config.be, $scope.masterHost.config.be);
				});
				ReferenceObjectConfigService.addReferenceObject("beHosts", behosts);
				angular.forEach($scope.masterHostInstances, function(instance){
				   angular.forEach(apps, function(app){
					   if(instance.basicAttributes.applicationName == app.name){
							   $scope.app = app;
					    }
				   });
				   angular.forEach($scope.appObject, function(appob){
					    if(!(angular.equals($scope.app, appob))){
						    	isAppExist = false;
						}
				   });				   
				   if(count == 0){
					   $scope.appObject[count] = $scope.app;
					   count = count + 1; 
					}
				   else if(!isAppExist){
					   $scope.appObject[count] = $scope.app;
					   count = count + 1;
					   isAppExist = true;
					}
				  });				  
				  
				  for(var i = 0; i < $scope.appObject.length; i++){
					   $scope.checkedApplications[$scope.appObject[i].name] = false;
				   }
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
		$scope.selectAllInstances = function (application) {
            var isAllApplicationsChecked = true;			
			for (var i = 0; i < $scope.applicationInstance[application.name].length; i++) {
				$scope.applicationInstance[application.name][i].isChecked = $scope.checkedApplications[application.name];
			}
                 
			 angular.forEach($scope.checkedApplications, function(value, key) {
				 if($scope.checkedApplications[key] && isAllApplicationsChecked)
					 isAllApplicationsChecked = true;
				 else
					 isAllApplicationsChecked = false;
			 });
			 
			 if(isAllApplicationsChecked)
				 $scope.filteredValues.allInstancesChecked = true;
			 else
				 $scope.filteredValues.allInstancesChecked = false;
				 
		}
		
		$scope.selectAllApplicationInstances = function () {			
            angular.forEach($scope.applicationInstance, function(value, key) {
            	 for(var i = 0; i < value.length; i++){
            		value[i].isChecked =  $scope.filteredValues.allInstancesChecked;
            	 }
            });
			
			 angular.forEach($scope.checkedApplications, function(value, key) {
				 if($scope.filteredValues.allInstancesChecked)
				   $scope.checkedApplications[key] = true;
			     else
					 $scope.checkedApplications[key] = false;
					 
			 });
		}
		
		$scope.selectInstanceEntity = function (application) {          
			for (var i = 0; i < $scope.applicationInstance[application.name].length; i++) {
				if (!$scope.applicationInstance[application.name][i].isChecked) {
					$scope.checkedApplications[application.name] = false;
					if($scope.filteredValues.allInstancesChecked)
						   $scope.filteredValues.allInstancesChecked = false;
					return;
				}
			}          
			$scope.checkedApplications[application.name] = true;
			var isAllApplicationsChecked = true;
			angular.forEach($scope.checkedApplications, function(value, key) {
				 if($scope.checkedApplications[key] && isAllApplicationsChecked)
					 isAllApplicationsChecked = true;
				 else
					 isAllApplicationsChecked = false;
			 });
			 
			 if(isAllApplicationsChecked)
				 $scope.filteredValues.allInstancesChecked = true;
			
		}
		function getSelectedInstancesList(){
    		var selectedInstancesList = []; 			
			angular.forEach($scope.applicationInstance, function(value, key) {
           	 for(var i = 0; i < value.length; i++){
           		if (value[i].isChecked) {
	                var instance = value[i];
	                selectedInstancesList.push(instance.key);	                   
	            }
           	 }
           });
			
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
		
		$scope.startInstance = function(appinstance){
			  $('#loaderdiv').show(); 
	          var key = appinstance.key;
			  angular.forEach($scope.appObject, function(appob){
				 if((angular.equals(appinstance.basicAttributes.applicationName, appob.name))){
					$scope.appName = appob;
				 }
			  });
	          teaObjectService.reference({
	               'agentID'  : $scope.appName.agentId,
	               'agentType': $scope.appName.type.agentType,
	               'objectType': $scope.appName.type.name,
	               'objectKey': $scope.appName.key,
	               'reference' : 'ServiceInstances'
	          }).then(function (instances) {
	               for(var i = 0; i < instances.length; i++){
					  if(instances[i].key == key)
						 $scope.instance = instances[i];
				   }
				   
				  teaObjectService.invoke({
				     'agentID'  : $scope.instance.agentId,
	                 'agentType': $scope.instance.type.agentType,
	      		     'objectType': $scope.instance.type.name,           		
	      		     'objectKey': $scope.instance.key,
	      		     'operation': 'start',
	                 'methodType': 'UPDATE'
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
			   }, function (failReason) {
	            		defer.reject('Error retrieving app instances');
	           });						
			 
			}
		
		$scope.stopInstance = function(appinstance){
		  $('#loaderdiv').show(); 
          var key = appinstance.key;
		  angular.forEach($scope.appObject, function(appob){
			 if((angular.equals(appinstance.basicAttributes.applicationName, appob.name))){
				$scope.appName = appob;
			 }
		  });
          teaObjectService.reference({
               'agentID'  : $scope.appName.agentId,
               'agentType': $scope.appName.type.agentType,
               'objectType': $scope.appName.type.name,
               'objectKey': $scope.appName.key,
               'reference' : 'ServiceInstances'
          }).then(function (instances) {
               for(var i = 0; i < instances.length; i++){
				  if(instances[i].key == key)
					 $scope.instance = instances[i];
			   }
			   
			  teaObjectService.invoke({
			     'agentID'  : $scope.instance.agentId,
                 'agentType': $scope.instance.type.agentType,
      		     'objectType': $scope.instance.type.name,           		
      		     'objectKey': $scope.instance.key,
      		     'operation': 'stop',
                 'methodType': 'UPDATE'
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
		   }, function (failReason) {
            		defer.reject('Error retrieving app instances');
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
			for(var i = 0; i < $scope.appObject.length; i++){
				$scope.getApplicationInstanceDetails($scope.appObject[i]);
			}
			$scope.filteredValues.allInstancesChecked = false;
		}
		$scope.showIndividualOperations = function(){
			var selectedInstances =[];
			$scope.check.allStarted = true;
			$scope.check.allStopped = true;
			$scope.check.allKilled= true;
			angular.forEach($scope.applicationInstance, function(value, key) {
           	 for(var i = 0; i < value.length; i++){
           		if (value[i].isChecked && value[i].status.state == 'Stopped') {
	            	$scope.check.allStarted = false;
	            }
	            if (value[i].isChecked && value[i].status.state == 'Running') {
	            	$scope.check.allStopped = false;
	            }
	            if (value[i].isChecked && (value[i].status.state == 'Running' || value[i].status.state == 'Starting' || value[i].status.state == 'Stopping')) {
	            	$scope.check.allKilled = false;
	            }
           	 }
           });
			
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
		$scope.removeBEDetails=function(index){
				$scope.newHostObj.be.splice(index, 1); 
				$scope.isSaveEnabled = true;
		}
		$scope.editHost = function(){
			$('#loaderdiv').show();
			$scope.isError = false;		
			$scope.errorMessage = '';
			var isExist = false;
			var masterHost= {hostName:$scope.newHostObj.hostName,hostId:$scope.newHostObj.hostId,hostIp:$scope.newHostObj.ipAdress,os:$scope.newHostObj.hostOS,userName:$scope.newHostObj.username,password:$scope.newHostObj.password,be:$scope.newHostObj.be,sshPort:$scope.newHostObj.sshPort,deploymentPath:$scope.newHostObj.deploymentPath}
			if(undefined===$scope.newHostObj.username||null==$scope.newHostObj.username||$.trim($scope.newHostObj.username)===''){
				$('#loaderdiv').hide();
				$scope.isError = true;
				$scope.errorMessage ="User name is required";
			}
			else{
				if(undefined!=$scope.newHostObj.be&& null!=$scope.newHostObj.be&&$scope.newHostObj.be.length>0){		
					for(var i=0;i<$scope.newHostObj.be.length;i++){		
						var beDetails=$scope.newHostObj.be[i];		
						for(var j=i+1;j<$scope.newHostObj.be.length;j++){		
							var nextBEDetails = $scope.newHostObj.be[j];		
							
							if($.trim(beDetails.beHome).replace(/\\/g,"/")===$.trim(nextBEDetails.beHome).replace(/\\/g,"/")){		
								isExist=true;		
									break;											
							}		
						}								
								
					}		
					if(isExist===true){	
						$('#loaderdiv').hide();
						$scope.isError = true;		
						$scope.errorMessage = "There are duplicate BE homes.";		
					}		
				}
				//Remove empty be home details
				if(undefined!=$scope.newHostObj.be&& null!=$scope.newHostObj.be&&$scope.newHostObj.be.length>0){		
					for(var i=0;i<$scope.newHostObj.be.length;i++){		
						var beDetails=$scope.newHostObj.be[i];	
						if($.trim(beDetails.beHome)=='undefined' || $.trim(beDetails.beHome)=='' || $.trim(beDetails.beTra)=='undefined' || $.trim(beDetails.beTra)==''){
							$scope.newHostObj.be.splice(i, 1);
						}
					}
				}
				angular.copy($scope.newHostObj.be, $scope.hostToEdit.config.be);
				if($scope.isError==false){
					teaObjectService.invoke({
						'agentID'  : $scope.hostToEdit.agentId,
						'agentType': $scope.hostToEdit.type.agentType,
						'objectType': $scope.hostToEdit.type.name,           		
						'objectKey': $scope.hostToEdit.key,
						'operation' : 'edit',
						'methodType':'UPDATE',
						'params': {                	
							masterHost:masterHost,
							version:$scope.hostToEdit.config.version
						}      		
					}).then(function (message) {
						$('#loaderdiv').hide();
						$('#createHost').modal('hide');
						$('#beHomesView').modal('hide');
						/*
						 * $scope.notification = { severity: 'info', msg : message,
						 * show: true };
						 */
					}, function (failReason) {
						$('#loaderdiv').hide();
						$scope.isError = true;
						$scope.errorMessage = failReason.message;
					});	 
			 }
			}
		 }
		$scope.closeModal=function(){
			if(typeof $scope.hostToEdit!=='undefined')
				$scope.newHostObj.be= $scope.hostToEdit.config.be;
			else 
				$scope.newHostObj.be=[];	
				var masterHost= {hostName:$scope.newHostObj.hostName,hostId:$scope.newHostObj.hostId,hostIp:$scope.newHostObj.ipAdress,os:$scope.newHostObj.hostOS,userName:$scope.newHostObj.username,password:$scope.newHostObj.password,be:$scope.newHostObj.be,sshPort:$scope.newHostObj.sshPort,deploymentPath:$scope.newHostObj.deploymentPath}
				teaObjectService.invoke({
						'agentID'  : $scope.hostToEdit.agentId,
						'agentType': $scope.hostToEdit.type.agentType,
						'objectType': $scope.hostToEdit.type.name,           		
						'objectKey': $scope.hostToEdit.key,
						'operation' : 'edit',
						'methodType':'UPDATE',
						'params': {                	
							masterHost:masterHost,
							version:$scope.hostToEdit.config.version
						}      		
					}).then(function (message) {
						$('#loaderdiv').hide();
						$('#beHomesView').modal('hide');
					}, function (failReason) {
						$('#loaderdiv').hide();
						$scope.isError = true;
						$scope.errorMessage = failReason.message;
					});	 
		 } 
		$scope.discoverBEHome=function(){
			$('#loaderdiv').show(); 
			$scope.hostToEdit = $scope.masterHost;
			var selectedHostsList = [];
			selectedHostsList.push($scope.masterHost.key);
			teaObjectService.invoke({
				'agentType': 'BusinessEvents',
			    'objectType': 'BusinessEvents',
			    'objectKey' :'BusinessEvents',
			    'operation' : 'discoverBEHome',
				'methodType':'UPDATE',
				'params': {
					hosts : selectedHostsList
		       	}
			}).then(function (host) {	
				$('#loaderdiv').hide(); 
				$scope.isSaveEnabled = false;
				$scope.isCreate=false;
				$scope.isError = false;
				$scope.newHostObj.ipAdress=$scope.hostToEdit.config.hostIp;
				$scope.newHostObj.hostOS=$scope.hostToEdit.config.os;
				$scope.newHostObj.username=$scope.hostToEdit.config.userName;
				$scope.newHostObj.password=$scope.hostToEdit.config.password;
				$scope.newHostObj.sshPort=$scope.hostToEdit.config.sshPort;
				$scope.newHostObj.hostName=$scope.hostToEdit.name;
				$scope.newHostObj.deploymentPath=$scope.hostToEdit.config.deploymentPath;
				$scope.newHostObj.beHome=$scope.hostToEdit.config.beHome;
				$scope.newHostObj.beTra=$scope.hostToEdit.config.beTra;
				$scope.newHostObj.be = host.be;
				$('#beHomesView').modal({
						show: true,
						keyboard: false,
						backdrop: 'static'
				});
				
			}, function (failReason) {
				$('#loaderdiv').hide(); 
				console.log("failed");
				$scope.notification = {
					      severity: 'error',
					      msg : failReason.message,
					      show: true
					};
			});	 
		 
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
	