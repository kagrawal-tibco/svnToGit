beConfigModule.controller("instanceController",['$scope','$location','$window','$http','fileUpload','$q','$rootScope', '$timeout','teaLocation', 'teaObjectService','ReferenceObjectConfigService','teaEventNotifications','StorageService','utilService','MetricViewService',
                                       function ($scope,$location,$window,$http,fileUpload,$q,$rootScope, $timeout, teaLocation, teaObjectService,ReferenceObjectConfigService,teaEventNotifications,StorageService,utilService,MetricViewService){
	$('#loaderdiv').show(); 
	$rootScope.loadingComplete.then(function( apps ){
			$scope.auditInfo ={};
			$('#loaderdiv').hide(); 
			$scope.instance = ReferenceObjectConfigService.getSelectedInstance();
			$scope.appHosts=ReferenceObjectConfigService.getReferenceObject(ReferenceObjectConfigService.getSelectedApp().name+"_Host");
			$scope.beHosts=ReferenceObjectConfigService.getReferenceObject("beHosts");
			$scope.appProcessingUnits=ReferenceObjectConfigService.getReferenceObject(ReferenceObjectConfigService.getSelectedApp().name+"_ProcessingUnits");
			$scope.selectedApp = ReferenceObjectConfigService.getSelectedApp(); 
			$scope.runtimePattern = {};
			$scope.runtimePattern.pattern="";
			$scope.runtimePattern.logLevel="";
			$scope.isChartRefreshed = 0;
			$scope.configurationChangesFlags ={};
			$scope.configurationChangesFlags.isSystemVariableChanged = false;
			$scope.configurationChangesFlags.isJVMPropChanged = false;
			$scope.configurationChangesFlags.isBEPropChanged = false;
			$scope.configurationChangesFlags.isDeployLogLevelChanged = false;
			$scope.configurationChangesFlags.isRuntimeLogLevelChanged = false;
			$scope.configurationChangesFlags.isGlobalVariableChanged = false;
			createBreadCrumbs();
			function createBreadCrumbs(){
				/* Creating breadCrumbs */
				ReferenceObjectConfigService.setBreadCrumbObject();
				var leftNavObject =ReferenceObjectConfigService.getSelectedNavObject();
				if(leftNavObject == 'instances')
		 	   		ReferenceObjectConfigService.getBreadCrumbObject().push({name:'All Instances',type:{name : 'instances'}});
				else if(leftNavObject == 'machines'){
					ReferenceObjectConfigService.getBreadCrumbObject().push({name:'Machines',type:{name : 'machines'}});
					ReferenceObjectConfigService.getBreadCrumbObject().push(ReferenceObjectConfigService.getSelectedHost());
				}else if(leftNavObject == 'processing units'){
					ReferenceObjectConfigService.getBreadCrumbObject().push({name:'Processing Units',type:{name : 'processing units'}});
					ReferenceObjectConfigService.getBreadCrumbObject().push(ReferenceObjectConfigService.getSelectedProcessingUnit());
				}else if(leftNavObject == 'agent classes'){
					ReferenceObjectConfigService.getBreadCrumbObject().push({name:'Agent Classes',type:{name : 'agent classes'}});
					ReferenceObjectConfigService.getBreadCrumbObject().push(ReferenceObjectConfigService.getSelectedProcessingUnitAgent());
				}
				ReferenceObjectConfigService.getBreadCrumbObject().push($scope.instance);
				if(Storage)
					localStorage.setItem("breadCrumbs",JSON.stringify(ReferenceObjectConfigService.getBreadCrumbObject()));
				$scope.breadCrumbs=ReferenceObjectConfigService.getBreadCrumbObject();
			}
			
			$scope.showChildren=false;
			$scope.processingUnitMethods = StorageService.getPuMethods();
			$scope.instanceInfo=[];
			$scope.globalVariables=[];
			$scope.jvmProperties=[];
			$scope.systemVariables=[];
			$scope.beprops=[];
			$scope.loggersInfo=[];
			$scope.allLogLevels=[];
			$scope.selectedInstancesAgents = [];
			$scope.isOnAgentView= false;
			$scope.configurationType  = "global";
			$scope.copiedInstanceObject={};
	
			$scope.hotDeploy={};
			$scope.hotDeployObj={};
			$scope.hotDeployObj.zipFile=undefined;
			$scope.hotDeploy.earfilesCreateApp =undefined;
			$scope.vrfName;
			$scope.implName;
			if($scope.instance.status.state=='Running')
				$scope.viewType  = "monitoring";
			else
				$scope.viewType  = "configuration";
			$scope.notification = {
		            severity: 'info',
		            msg : '',
		            show: false
		    };
			$scope.dismiss =function(){
		   		$scope.notification.show = false;
		   		if(!utilService.getAgentReachable()){
					$location.path("/");
				}
		   	}
			$scope.browseFile = function(id){
				$('#'+id).click();
			}
			$scope.setPathValue = function(id,fileId){
				$('#'+id).val($('#'+fileId).val().replace(/^.*[\\\/]/, ''));
			} 
			$scope.getFileDetails = function (e) {
	            $scope.classFilesArray = [];
	            $scope.classFileNames="";
	            $scope.$apply(function () {
	                for (var i = 0; i < e.files.length; i++) {
	                    $scope.classFilesArray.push(e.files[i])
	                    $scope.classFileNames = $scope.classFileNames  + e.files[i].name+ ",";
	                }
	            });
	        }
			$scope.startInstance = function(){	
					$('#loaderdiv').show(); 
		 			 teaObjectService.invoke({
		 				'agentID'  : $scope.instance.agentId,
			            'agentType': $scope.instance.type.agentType,
		           		'objectType': $scope.instance.type.name,           		
		           		'objectKey': $scope.instance.key,
		            	'operation': 'start',
		                'methodType': 'UPDATE'
		 			 }).then(function (data) { 
		 				$('#loaderdiv').hide(); 
		 				getInstanceInfo();
		 				getAllAgentsInfo();
		 			 }, function (failReason) {
		 				$('#loaderdiv').hide(); 
		 				$scope.notification = {
		 			            severity: 'error',
		 			            msg : failReason.message,
		 			            show: true
		 			    };
		 			 });       		
			}
			$scope.stopInstance = function(){
					$('#loaderdiv').show(); 
				 	teaObjectService.invoke({
						'agentID'  : $scope.instance.agentId,
			            'agentType': $scope.instance.type.agentType,
		        		'objectType': $scope.instance.type.name,           		
		        		'objectKey': $scope.instance.key,
		        		'operation': 'stop',
		        		'methodType': 'UPDATE'
					 }).then(function (data) { 	
						 $('#loaderdiv').hide(); 
						getInstanceInfo();
						getAllAgentsInfo();
					 }, function (failReason) { 
						 $('#loaderdiv').hide(); 
						 $scope.notification = {
			 			            severity: 'error',
			 			            msg : failReason.message,
			 			            show: true
			 			  };
					 });    
			}
			$scope.killInstance = function(){
				$('#loaderdiv').show(); 
			 	teaObjectService.invoke({
					'agentID'  : $scope.instance.agentId,
		            'agentType': $scope.instance.type.agentType,
	        		'objectType': $scope.instance.type.name,           		
	        		'objectKey': $scope.instance.key,
	        		'operation': 'KillServiceInstance',
	        		'methodType': 'UPDATE'
				 }).then(function (data) { 	
					 $('#loaderdiv').hide(); 
					 getInstanceInfo();
					 getAllAgentsInfo();
				 }, function (failReason) { 
					 $('#loaderdiv').hide(); 
					 $scope.notification = {
		 			            severity: 'error',
		 			            msg : failReason.message,
		 			            show: true
		 			  };
				 });
			}
			$scope.deployInstance = function(){
				$('#loaderdiv').show();					
			 	teaObjectService.invoke({
					'agentID'  : $scope.instance.agentId,
		            'agentType': $scope.instance.type.agentType,
	        		'objectType': $scope.instance.type.name,           		
	        		'objectKey': $scope.instance.key,
	        		'operation': 'deploy',
	        		'methodType': 'UPDATE'
				 }).then(function (data) {
					$('#loaderdiv').hide(); 
					getInstanceInfo();
					// getLoggersInfo();
					getLogPatternsAndLogLevel();	
					getGlobalVariables();
					getSystemVariables();
					getBEProperties();
					getJVMProperties();
					/*
					 * $scope.notification = { severity: 'info', msg : data,
					 * show: true };
					 */
				 }, function (failReason) { 
					$('#loaderdiv').hide(); 
					$scope.notification = {
		 			            severity: 'error',
		 			            msg : failReason.message,
		 			            show: true
		 			};
				 });    
			}			
			$scope.undeployInstance = function(){
					$('#loaderdiv').show(); 
				 	teaObjectService.invoke({
						'agentID'  : $scope.instance.agentId,
			            'agentType': $scope.instance.type.agentType,
		        		'objectType': $scope.instance.type.name,           		
		        		'objectKey': $scope.instance.key,
		        		'operation': 'undeploy',
		        		'methodType': 'UPDATE'
					 }).then(function (data) { 	
						 $('#loaderdiv').hide(); 
						 getInstanceInfo();
						 // getLoggersInfo();
						getLogPatternsAndLogLevel();	
						getGlobalVariables();
						getSystemVariables();
						getBEProperties();
						getJVMProperties();
						/*
						 * $scope.notification = { severity: 'info', msg : data,
						 * show: true };
						 */
					 }, function (failReason) {
						 $('#loaderdiv').hide(); 
						 $scope.notification = {
			 			            severity: 'error',
			 			            msg : failReason.message,
			 			            show: true
			 			};
					 });    
			} 
			$scope.reDeployInstance = function(){
				$('#loaderdiv').show(); 
			 	teaObjectService.invoke({
					'agentID'  : $scope.instance.agentId,
		            'agentType': $scope.instance.type.agentType,
		    		'objectType': $scope.instance.type.name,           		
		    		'objectKey': $scope.instance.key,
		    		'operation': 'redeploy',
		    		'methodType': 'UPDATE'
				 }).then(function (data) { 	
					$('#loaderdiv').hide(); 
					getInstanceInfo();
					// getLoggersInfo();
					getGlobalVariables();
					getSystemVariables();
					getBEProperties();
					getJVMProperties();
					/*
					 * $scope.notification = { severity: 'info', msg : data,
					 * show: true };
					 */
				 }, function (failReason) { 
					 $('#loaderdiv').hide(); 
					 $scope.notification = {
					            severity: 'error',
					            msg : failReason.message,
					            show: true
					};
				 });    
			} 
			var subscribe = true;

			$scope.$watch('object', function (object) {
		        if (object) {
		            $scope.addToFav(object);
		            $scope.doSubscribe();
		        }
		    });

		    $scope.notify = function (angularEvent, events) {
		    	if(events[0].type == "STATUS_CHANGE"  ){
			    	if(events[0].data.status == "Running")
			    		getGCDetails();
			    	var deferredRequest =getInstanceInfo();
			    	deferredRequest.then(function (members) {
			    		if(members.status=='Running')
							$scope.viewType  = "monitoring";
						else
							$scope.viewType  = "configuration";
			        }, function (failReason) {
			        	
			        });
			    	getAllAgentsInfo();
		    }
		    }
		    $scope.$on('teaEventNotifications', $scope.notify);

		    $scope.doSubscribe = function() {
		        if (subscribe) {
		            subscribe = false;
		            teaEventNotifications.addObjectIdToSubscription($scope.object.agentId, window.tea.location.info.agentType, $scope.object.type.name, "", [Event.STATUS_CHANGE]);
		            teaEventNotifications.subscribe();
		        }
		    }
		    $scope.setViewType = function(type){
		    	$scope.viewType = type;
		    }
		    $scope.isSet = function (viewType) {
		        return $scope.viewType === viewType;
		    };
		    $scope.setConfigurationType = function(type){
		    	$scope.configurationType = type;
		    }
		    $scope.isSetCofigType=function(type){
		    	return $scope.configurationType === type;
		    }
			function getInstanceInfo(){
				var defer = $q.defer();
				$('#loaderdiv').show(); 
				teaObjectService.invoke({
					'agentID'  : $scope.instance.agentId,
		            'agentType': $scope.instance.type.agentType,
		       		'objectType': $scope.instance.type.name,           		
		       		'objectKey': $scope.instance.key,
		       		'operation' : 'getInstanceInfo',
		       		'methodType':'READ'
				}).then(function (members) {
					$scope.instanceInfo=members;
					var beHome="";
					var beDeatils=$scope.instanceInfo.host.be;
					for(var i=0;i<beDeatils.length;i++){
						var beDetail =beDeatils[i];
						if(typeof beDetail!=='undefined' && null!=beDetail){
							if(beDetail.id === $scope.instanceInfo.beId){
								beHome=beDetail.beHome;
								break;
							}
						}
					}
					$scope.beHome=beHome;
					defer.resolve(members);
					/*
					 * if($scope.instanceInfo.deploymentPath.indexOf("\\") >
					 * -1){ $scope.instanceInfo.deploymentPath =
					 * $scope.instanceInfo.deploymentPath + "\\" +
					 * ReferenceObjectConfigService.getSelectedApp().name; }else
					 * if($scope.instanceInfo.deploymentPath.indexOf("/") > -1)
					 * $scope.instanceInfo.deploymentPath =
					 * $scope.instanceInfo.deploymentPath + "/" +
					 * ReferenceObjectConfigService.getSelectedApp().name;
					 */
					
					if($scope.instanceInfo.status == "Running" && $scope.instanceInfo.upTime!= -1){
    					var upTime=utilService.getFormattedDate($scope.instanceInfo.upTime);
    				}else
    					var upTime = "";
					$scope.instanceInfo.upTime = upTime;
					$('#loaderdiv').hide();
				}, function (failReason) {
					$('#loaderdiv').hide();
					defer.reject('failed');
				});
				return defer.promise;
			}	
			/*
			 * function getLoggersInfo(){ teaObjectService.invoke({ 'agentID' :
			 * $scope.instance.agentId, 'agentType':
			 * $scope.instance.type.agentType, 'objectType':
			 * $scope.instance.type.name, 'objectKey': $scope.instance.key,
			 * 'operation' : 'getLoggerLogLevels', 'methodType':'READ'
			 * }).then(function (loggers) { $scope.loggersInfo=loggers;
			 * $('#loaderdiv').hide(); }, function (failReason) {
			 * $('#loaderdiv').hide(); console.log("failed"); }); }
			 */
			function getGlobalVariables(){
				teaObjectService.invoke({
					'agentID'  : $scope.instance.agentId,
		            'agentType': $scope.instance.type.agentType,
		       		'objectType': $scope.instance.type.name,           		
		       		'objectKey': $scope.instance.key,
		       		'operation' : 'getGlobalVariables',
		       		'methodType':'READ'
				}).then(function (globleVariables) {
					$scope.globalVariables=globleVariables;	
					$('#loaderdiv').hide();
				}, function (failReason) {
					$('#loaderdiv').hide();
					console.log("failed");
				});	 
			}
			/*
			 * $scope.isGlobalVariableChange = function(){
			 * $scope.isGlobalVariableChanged = false; for(var i =0 ; i <
			 * $scope.globalVariables.nameValuePairs.nameValuePair.length ;
			 * i++){ var newValue =
			 * $scope.globalVariables.nameValuePairs.nameValuePair[i].value; var
			 * oldValue =
			 * $scope.tempGlobalVariable.nameValuePairs.nameValuePair[i].value;
			 * if((!(newValue == "" && oldValue== null ) && (newValue !=
			 * oldValue)) ||
			 * ($scope.globalVariables.nameValuePairs.nameValuePair[i].isDeleted
			 * !=$scope.tempGlobalVariable.nameValuePairs.nameValuePair[i].isDeleted
			 * )){ $scope.isGlobalVariableChanged = true; break; } } }
			 */
			function getSystemVariables(){
				teaObjectService.invoke({
					'agentID'  : $scope.instance.agentId,
		            'agentType': $scope.instance.type.agentType,
		       		'objectType': $scope.instance.type.name,           		
		       		'objectKey': $scope.instance.key,
		       		'operation' : 'getSystemVariables',
		       		'methodType':'READ'
				}).then(function (systemVariables) {
					$scope.systemVariables=systemVariables;	
					$('#loaderdiv').hide();
				}, function (failReason) {
					$('#loaderdiv').hide();
					console.log("failed");
				});	 
			}
			function getBEProperties(){
				teaObjectService.invoke({
					'agentID'  : $scope.instance.agentId,
		            'agentType': $scope.instance.type.agentType,
		       		'objectType': $scope.instance.type.name,           		
		       		'objectKey': $scope.instance.key,
		       		'operation' : 'getBEProperties',
		       		'methodType':'READ'
				}).then(function (bepropeties) {
					$scope.beprops=bepropeties;	
					$('#loaderdiv').hide();
				}, function (failReason) {
					$('#loaderdiv').hide();
					console.log("failed");
				});	 
			}
			function getJVMProperties(){
				teaObjectService.invoke({
					'agentID'  : $scope.instance.agentId,
		            'agentType': $scope.instance.type.agentType,
		       		'objectType': $scope.instance.type.name,           		
		       		'objectKey': $scope.instance.key,
		       		'operation' : 'getJVMProperties',
		       		'methodType':'READ'
				}).then(function (jvmProperties) {
					$scope.jvmProperties=jvmProperties;	
					$('#loaderdiv').hide();
				}, function (failReason) {
					$('#loaderdiv').hide();
					console.log("failed");
				});	 
			}
			$scope.saveJVMProperties = function(){
				var minSizeInBytes=0 , maxSizeInBytes=0;
				for(var i=0;i< $scope.jvmProperties.nameValuePairs.nameValuePair.length;i++){
					if($scope.jvmProperties.nameValuePairs.nameValuePair[i].name == 'java.heap.size.initial'){
						var value = "";
						if($scope.jvmProperties.nameValuePairs.nameValuePair[i].value!=null && $scope.jvmProperties.nameValuePairs.nameValuePair[i].value !=""){
							value = $scope.jvmProperties.nameValuePairs.nameValuePair[i].value;
						}else
							value = $scope.jvmProperties.nameValuePairs.nameValuePair[i].deployedValue;
						minSizeInBytes = utilService.convertMemoryToByte(value);
					}else if($scope.jvmProperties.nameValuePairs.nameValuePair[i].name == 'java.heap.size.max'){
						var value = "";
						if($scope.jvmProperties.nameValuePairs.nameValuePair[i].value!=null && $scope.jvmProperties.nameValuePairs.nameValuePair[i].value !=""){
							value = $scope.jvmProperties.nameValuePairs.nameValuePair[i].value;
						}else
							value = $scope.jvmProperties.nameValuePairs.nameValuePair[i].deployedValue;
						maxSizeInBytes = utilService.convertMemoryToByte(value);
					}
				}
				if(minSizeInBytes>maxSizeInBytes){
					$scope.notification = {
					        severity: 'error',
					        msg : 'Min heap size can not be greater than max heap size',
					        show: true
						};
					return;
				}
				teaObjectService.invoke({
					'agentID'  : $scope.instance.agentId,
		            'agentType': $scope.instance.type.agentType,
		       		'objectType': $scope.instance.type.name,           		
		       		'objectKey': $scope.instance.key,
		       		'operation' : 'saveJVMProperties',
		       		'methodType':'UPDATE',
		       		'params': {                	
		       			jvmProperties : $scope.jvmProperties
		             	} 
				}).then(function (data) {
					getInstanceInfo();
					$scope.configurationChangesFlags.isJVMPropChanged = false;
					/*
					 * $scope.notification = { severity: 'info', msg : data,
					 * show: true };
					 */			
				}, function (failReason) {
					$scope.notification = {
				        severity: 'error',
				        msg : failReason.message,
				        show: true
					};	
				});	 
			}
			function getAllLogLevels(){
				teaObjectService.invoke({
					'agentID'  : $scope.instance.agentId,
		            'agentType': $scope.instance.type.agentType,
		       		'objectType': $scope.instance.type.name,           		
		       		'objectKey': $scope.instance.key,
		       		'operation' : 'getLogLevels',
		       		'methodType':'READ'
				}).then(function (levels) {
					$scope.allLogLevels=levels;	
					$('#loaderdiv').hide();
				}, function (failReason) {
					$('#loaderdiv').hide();
					console.log("failed");
				});	 
			}
			
			$scope.saveGlobalVariables = function(){
				teaObjectService.invoke({
					'agentID'  : $scope.instance.agentId,
		            'agentType': $scope.instance.type.agentType,
		       		'objectType': $scope.instance.type.name,           		
		       		'objectKey': $scope.instance.key,
		       		'operation' : 'saveGlobalVariables',
		       		'methodType':'UPDATE',
		       		'params': {                	
		       			globalVariables : $scope.globalVariables
		             	} 
				}).then(function (data) {
					$scope.tempGlobalVariable = angular.copy($scope.globalVariables);
					$scope.configurationChangesFlags.isGlobalVariableChanged = false;
					getInstanceInfo();
					getGlobalVariables();
					/*
					 * $scope.notification = { severity: 'info', msg : data,
					 * show: true };
					 */			
				}, function (failReason) {
					$scope.notification = {
				        severity: 'error',
				        msg : failReason.message,
				        show: true
					};	
				});	 
			}
			$scope.isPropertyAlreadyExists = function (name,index,propertyList) {
				for(var i=0;i<propertyList.nameValuePairs.nameValuePair.length;i++){
				    if(index != i && propertyList.nameValuePairs.nameValuePair[i].name==name){
				       return true;
				    }
				}
				return false;
			}
			$scope.saveSystemVariables = function(){
				var propertiesToSave ={name:ReferenceObjectConfigService.selectedAppName,nameValuePairs:{nameValuePair:[]},type:"SYSTEM_VARIABLES"};
				for(var i=0;i<$scope.systemVariables.nameValuePairs.nameValuePair.length;i++){
					var isAlreadyExists = false;
					var propertyToKeep = null ; 
					for(var j=0;j<$scope.systemVariables.nameValuePairs.nameValuePair.length;j++){
						if(j!=i && $scope.systemVariables.nameValuePairs.nameValuePair[i].name == $scope.systemVariables.nameValuePairs.nameValuePair[j].name){
							if(($scope.systemVariables.nameValuePairs.nameValuePair[i]['isDeleted'] && !$scope.systemVariables.nameValuePairs.nameValuePair[j]['isDeleted']) || (!$scope.systemVariables.nameValuePairs.nameValuePair[i]['isDeleted'] && !$scope.systemVariables.nameValuePairs.nameValuePair[j]['isDeleted'] && i < j))
								isAlreadyExists = true;
						}
					}
					if(!isAlreadyExists){
						delete $scope.systemVariables.nameValuePairs.nameValuePair[i]['isNew'];
						propertiesToSave.nameValuePairs.nameValuePair.push($scope.systemVariables.nameValuePairs.nameValuePair[i]);
					}
				}
				teaObjectService.invoke({
					'agentID'  : $scope.instance.agentId,
		            'agentType': $scope.instance.type.agentType,
		       		'objectType': $scope.instance.type.name,           		
		       		'objectKey': $scope.instance.key,
		       		'operation' : 'saveSystemVariables',
		       		'methodType':'UPDATE',
		       		'params': {                	
		       			systemVariables : propertiesToSave
		             	} 
				}).then(function (data) {
					getInstanceInfo();
					getSystemVariables();
					$scope.configurationChangesFlags.isSystemVariableChanged = false;
					/*
					 * $scope.notification = { severity: 'info', msg : data,
					 * show: true };
					 */			
				}, function (failReason) {
					$scope.notification = {
				        severity: 'error',
				        msg : failReason.message,
				        show: true
					};	
				});	 
			}

			$scope.saveBEProperties = function(){
				var propertiesToSave ={name:ReferenceObjectConfigService.selectedAppName,nameValuePairs:{nameValuePair:[]},type:"BE_PROPERTIES"};
				for(var i=0;i<$scope.beprops.nameValuePairs.nameValuePair.length;i++){
					var isAlreadyExists = false;
					var propertyToKeep = null ; 
					for(var j=0;j<$scope.beprops.nameValuePairs.nameValuePair.length;j++){
						if(j!=i && $scope.beprops.nameValuePairs.nameValuePair[i].name == $scope.beprops.nameValuePairs.nameValuePair[j].name){
							if(($scope.beprops.nameValuePairs.nameValuePair[i]['isDeleted'] && !$scope.beprops.nameValuePairs.nameValuePair[j]['isDeleted']) || (!$scope.beprops.nameValuePairs.nameValuePair[i]['isDeleted'] && !$scope.beprops.nameValuePairs.nameValuePair[j]['isDeleted'] && i < j))
								isAlreadyExists = true;
						}
					}
					if(!isAlreadyExists){
						delete $scope.beprops.nameValuePairs.nameValuePair[i]['isNew'];
						propertiesToSave.nameValuePairs.nameValuePair.push($scope.beprops.nameValuePairs.nameValuePair[i]);
					}
				}
				teaObjectService.invoke({
					'agentID'  : $scope.instance.agentId,
		            'agentType': $scope.instance.type.agentType,
		       		'objectType': $scope.instance.type.name,           		
		       		'objectKey': $scope.instance.key,
		       		'operation' : 'saveBEProperties',
		       		'methodType':'UPDATE',
		       		'params': {                	
		       			beprops : propertiesToSave
		             	} 
				}).then(function (data) {
					getInstanceInfo();
					getBEProperties();
					$scope.configurationChangesFlags.isBEPropChanged = false;
					/*
					 * $scope.notification = { severity: 'info', msg : data,
					 * show: true };
					 */			
				}, function (failReason) {
					$scope.notification = {
				        severity: 'error',
				        msg : failReason.message,
				        show: true
					};	
				});	 
			}
			$scope.addNewSystemVariable = function(){		
				var dummy = {defaultValue : null ,deployedValue: null,description:null,isDeleted:false,name:null,value:null,hasDefaultValue:false,isNew:true};
				if($scope.systemVariables == null || $scope.systemVariables.length==0){
					var dummyObject ={name:ReferenceObjectConfigService.selectedAppName,nameValuePairs:{nameValuePair:[]},type:"SYSTEM_VARIABLES"};
					$scope.systemVariables=dummyObject;
				}
					
				$scope.systemVariables.nameValuePairs.nameValuePair.push(dummy);
			}
			$scope.removeSystemVariable = function(sysProps){
				sysProps.isDeleted=true;		   
			}	
			$scope.removeGlobalVariables = function(gv){
				gv.isDeleted=true;	   
			}	
			$scope.removeBEProperty = function(beProps){
				beProps.isDeleted=true;
			}	
			$scope.addNewBEProperty = function(){		
				var dummy = {defaultValue : null ,deployedValue: null,description:null,isDeleted:false,name:null,value:null,hasDefaultValue:false,isNew:true};
				if($scope.beprops == null || $scope.beprops.length==0){
					var dummyObject ={name:ReferenceObjectConfigService.selectedAppName,nameValuePairs:{nameValuePair:[]},type:"BE_PROPERTIES"};
					$scope.beprops=dummyObject;					
				}
			
				$scope.beprops.nameValuePairs.nameValuePair.push(dummy);
			}
			
			$scope.serachFunction = function(searchText){
				return searchText;
			}
			$scope.showCopyInstanceModal = function(){
				$scope.instanceData = {};
				$scope.isCopy = true;
				$scope.isEdit = false;
				$scope.instanceData.operation = "Copy Instance :";
				$scope.instanceData.instanceName = "copyOf"+$scope.instance.name;
				$scope.instanceData.hostName=$scope.instanceInfo.host.hostName;
				$scope.instanceData.processingUnit=$scope.instanceInfo.puId;
				$scope.instanceData.deploymentPath =$scope.instanceInfo.deploymentPath;
				$scope.instanceData.jmxPort =$scope.instanceInfo.jmxPort;
				$scope.instanceData.jmxUserName = $scope.instanceInfo.jmxUserName;
				$scope.instanceData.jmxPassword = $scope.instanceInfo.jmxPassword;
				$scope.instanceData.version= $scope.instanceInfo.host.application.version
				$scope.instanceData.beId=$scope.instanceInfo.beId;
				var selectedHost;
				for(var i=0;i< $scope.beHosts.length ; i++){
					if($scope.instanceData.hostName == $scope.beHosts[i].name){
						selectedHost = $scope.beHosts[i];
						break;
					}
				}
				// $scope.beDetails=selectedHost.config.be;
				$scope.beDetails=[];
				var beVersion = $scope.selectedApp.config.appVersion;
				for(var i=0;i<selectedHost.config.be.length;i++){
					if(selectedHost.config.be[i].version !="undefined" && selectedHost.config.be[i].version != null && selectedHost.config.be[i].version.indexOf(beVersion) !== -1)
						$scope.beDetails.push(selectedHost.config.be[i]);
				}
				$scope.hostAuthenticated=selectedHost.config.authenticated;
				$scope.instanceData.deploymentPathList = selectedHost.config.deploymentPath;
				$('#createInstance').modal({
					show: true,
					keyboard: false,
					backdrop: 'static'
				});
			}
			$scope.showHotDeployModal = function(){
				$scope.hotDeploy.earfilesCreateApp =undefined;
				$scope.hotDeploy.isFileUploaded = true;
				$scope.isError = false;
				$('#earfilepathforHotDeploy').val("");
				$('#earfileforHotDeploy').val("");
				$('#hotDeployInstance').modal({
					show: true,
					keyboard: false,
					backdrop: 'static'
				});
			}
			$scope.showHotDeployRTModal = function(){
				$scope.isError = false;
				$scope.hotDeployObj.zipFile =undefined;
				$scope.hotDeployObj.zipFileName="";
				$scope.hotDeployObj.step2Title="RT Details";
				$scope.hotDeployObj.isHotDeployClasses =false;
				$scope.hotDeployObj.isErrorWhileUploading = false;
				$scope.hotDeployObj.isLoadAndDeploySelected = false;
				$scope.hotDeployObj.hotDeployType = "Rule Templates";
				$scope.hotDeployObj.errorMessage="";
				$scope.hotDeployObj.agentName="";
            	$scope.hotDeployObj.projectName="";
            	$scope.hotDeployObj.rtfqn="";
            	$scope.hotDeployObj.ruleTemplateDeployDir=$scope.instance.config.ruleTemplateDeployDir;
				$('#classFiles').val("");
				$('#selectClassFile').val("");
				$('#uploadClassesModal').modal({
					show: true,
					keyboard: false,
					backdrop: 'static'
				});
			}
			$scope.showHotDeployClassesModal=function(){	
				$scope.isError = false;
				$scope.hotDeployObj.zipFile =undefined;
				$scope.hotDeployObj.zipFileName="";
				$scope.hotDeployObj.step2Title="VRF Details";
				$scope.hotDeployObj.isHotDeployClasses =true;
				$scope.hotDeployObj.isErrorWhileUploading = false;
				$scope.hotDeployObj.isLoadAndDeploySelected = false;
				$scope.hotDeployObj.hotDeployType = "Decision Tables Classes";
				$scope.hotDeployObj.errorMessage="";
				$scope.hotDeployObj.vrfURI="";
				$scope.hotDeployObj.implName="";
				$('#classFiles').val("");
				$('#selectClassFile').val("");
				$('#uploadClassesModal').modal({
					show: true,
					keyboard: false,
					backdrop: 'static'
				});
			}
			$scope.checkForValidForm = function(){
				if($scope.hotDeployObj.isHotDeployClasses){
					if(($scope.hotDeployObj.vrfURI == "" && $scope.hotDeployObj.implName=="") || ($scope.hotDeployObj.vrfURI != "" && $scope.hotDeployObj.implName!=""))
						return true;
					else
						return false;
				}else{
					if(($scope.hotDeployObj.projectName == "" && $scope.hotDeployObj.rtfqn=="") || ($scope.hotDeployObj.projectName != "" && $scope.hotDeployObj.rtfqn!=""))
						return true;
					else
						return false;
				}
			}
			$scope.navigateToUploadClassesOrRT = function(){
				$scope.hotDeployObj.zipFileName=$scope.hotDeployObj.zipFile.name;
				$scope.hotDeployObj.isLoadAndDeploySelected = false;
				$scope.hotDeployObj.errorMessage="";
			}
			$scope.finishUpload = function(){
				$scope.hotDeployObj.isLoadAndDeploySelected = false;
				if($scope.hotDeployObj.isHotDeployClasses)
					$scope.uploadClasses();
				else
					$scope.uploadRuleTemplates();
			}
			$scope.navigateToLoadAndDeploy = function(){
				$scope.hotDeployObj.isLoadAndDeploySelected = true;
				if($scope.hotDeployObj.isHotDeployClasses)
					$scope.uploadClasses();
				else
					$scope.uploadRuleTemplates();
			}
			$scope.uploadClasses = function(){
				if(angular.isUndefined($scope.hotDeployObj.zipFile)){
					$scope.hotDeployObj.isErrorWhileUploading = true;
					$scope.hotDeployObj.isLoadAndDeploySelected = false;
					$scope.hotDeployObj.errorMessage="upload files";
					return;
				}
 				if($scope.hotDeployObj.isLoadAndDeploySelected)
 					$('#modalDiv').show(); 
 				else{
 					$('#uploadClassesModal').modal('hide');
 					$('#loaderdiv').show(); 
 				}
				var fd = new FormData();
				fd.append("file", $scope.hotDeployObj.zipFile);					
				$http.post("/teas/fileupload/", fd, {
		            transformRequest: angular.identity,
		            headers: {
		                "Content-Type": undefined
		            }
		        }).success(function(data, status, headers, config) {        	  
		 			 	teaObjectService.invoke({
			 				'agentID'  : $scope.instance.agentId,
				            'agentType': $scope.instance.type.agentType,
			           		'objectType': $scope.instance.type.name,           		
			           		'objectKey': $scope.instance.key,
			            	'operation': 'uploadClasses',
			                'methodType': 'UPDATE',
			                'params': {                	
			                	classFiles : data[0]
				             } 
			 			}).then(function (message) { 
			 				if($scope.hotDeployObj.isLoadAndDeploySelected){
			 					$('#modalDiv').hide(); 
			 					$('#uploadClassesModal').modal({
									show: true,
									keyboard: false,
									backdrop: 'static'
								});
				 				$scope.hotDeployObj.isLoadAndDeploySelected = true;
				 				$scope.hotDeployObj.isErrorWhileUploading = false;
			 				}else{
			 					$('#loaderdiv').hide();
			 					/*
								 * $scope.notification = { severity: 'info', msg :
								 * message, show: true };
								 */
			 				}
			 		    }, function (failReason) {
			 		    	if($scope.hotDeployObj.isLoadAndDeploySelected){
			 		    		$('#modalDiv').hide(); 
			 		    		$scope.hotDeployObj.isErrorWhileUploading = true;
				 		    	$scope.hotDeployObj.isLoadAndDeploySelected = false;
								$scope.hotDeployObj.errorMessage="failReason.message"; 
			 		    	}else{
			 		    		$('#loaderdiv').hide();
			 		    		$scope.notification = {
				 			            severity: 'error',
				 			            msg : failReason.message,
				 			            show: true
				 			    };
			 		    	}
			 		    }); 
		        });
			}
			$scope.loadAndDeployClasses = function(){
				$('#loaderdiv').show(); 
				teaObjectService.invoke({
	 				'agentID'  : $scope.instance.agentId,
		            'agentType': $scope.instance.type.agentType,
	           		'objectType': $scope.instance.type.name,           		
	           		'objectKey': $scope.instance.key,
	            	'operation': 'loadAndDeployClasses',
	                'methodType': 'UPDATE',
	                'params': {                	
	                	vrfURI: $scope.hotDeployObj.vrfURI,
	                	implName:$scope.hotDeployObj.implName
		             }
	 			}).then(function (message) { 
	 				$('#loaderdiv').hide();
	 				$('#uploadClassesModal').modal('hide');
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
			$scope.uploadRuleTemplates = function(){
				if(angular.isUndefined($scope.hotDeployObj.zipFile)){
					$scope.hotDeployObj.isErrorWhileUploading = true;
					$scope.hotDeployObj.isLoadAndDeploySelected = false;
					$scope.hotDeployObj.errorMessage="upload files";
					return;
				}
 				if($scope.hotDeployObj.isLoadAndDeploySelected)
 					$('#modalDiv').show(); 
 				else{
 					$('#uploadClassesModal').modal('hide');
 					$('#loaderdiv').show(); 
 				}
				var fd = new FormData();
				fd.append("file", $scope.hotDeployObj.zipFile);					
				$http.post("/teas/fileupload/", fd, {
		            transformRequest: angular.identity,
		            headers: {
		                "Content-Type": undefined
		            }
		        }).success(function(data, status, headers, config) {        	  
		 			 	teaObjectService.invoke({
			 				'agentID'  : $scope.instance.agentId,
				            'agentType': $scope.instance.type.agentType,
			           		'objectType': $scope.instance.type.name,           		
			           		'objectKey': $scope.instance.key,
			            	'operation': 'uploadRuleTemplates',
			                'methodType': 'UPDATE',
			                'params': {                	
			                	rtFiles : data[0]
				             } 
			 			}).then(function (message) { 
			 				if($scope.hotDeployObj.isLoadAndDeploySelected){
			 					$('#modalDiv').hide(); 
			 					$('#uploadClassesModal').modal({
									show: true,
									keyboard: false,
									backdrop: 'static'
								});
				 				$scope.hotDeployObj.isLoadAndDeploySelected = true;
				 				$scope.hotDeployObj.isErrorWhileUploading = false;
			 				}else{
			 					$('#loaderdiv').hide();
			 					/*
								 * $scope.notification = { severity: 'info', msg :
								 * message, show: true };
								 */
			 				}
			 		    }, function (failReason) {
			 		    	if($scope.hotDeployObj.isLoadAndDeploySelected){
			 		    		$('#modalDiv').hide(); 
			 		    		$scope.hotDeployObj.isErrorWhileUploading = true;
				 		    	$scope.hotDeployObj.isLoadAndDeploySelected = false;
								$scope.hotDeployObj.errorMessage="failReason.message"; 
			 		    	}else{
			 		    		$('#loaderdiv').hide();
			 		    		$scope.notification = {
				 			            severity: 'error',
				 			            msg : failReason.message,
				 			            show: true
				 			    };
			 		    	}
			 		    }); 
		        });
			}
			$scope.loadAndDeployRuleTemplates = function(){
				$('#loaderdiv').show(); 
				teaObjectService.invoke({
	 				'agentID'  : $scope.instance.agentId,
		            'agentType': $scope.instance.type.agentType,
	           		'objectType': $scope.instance.type.name,           		
	           		'objectKey': $scope.instance.key,
	            	'operation': 'loadAndDeployRuleTemplates',
	                'methodType': 'UPDATE',
	                'params': {                	
	                	agentName: $scope.hotDeployObj.agentName,
	                	projectName:$scope.hotDeployObj.projectName,
	                	ruleTemplateInstanceFQN:$scope.hotDeployObj.rtfqn
		             }
	 			}).then(function (message) { 
	 				$('#loaderdiv').hide();
	 				$('#uploadClassesModal').modal('hide');
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
			$scope.hotDeployApp = function(earfile){
				if(angular.isUndefined(earfile)){
					$scope.hotDeploy.isFileUploaded = false;
					return;
				}
				$('#loaderdiv').show(); 
				var fd = new FormData();       
		        fd.append("file", earfile);
				$http.post("/teas/fileupload/", fd, {
		            transformRequest: angular.identity,
		            headers: {
		                "Content-Type": undefined
		            }
		        }).success(function(data, status, headers, config) {        	  
		 			 teaObjectService.invoke({
		 				'agentID'  : $scope.instance.agentId,
			            'agentType': $scope.instance.type.agentType,
			       		'objectType': $scope.instance.type.name,           		
			       		'objectKey': $scope.instance.key,
		            	'operation': 'hotdeploy',
		                'methodType': 'UPDATE',
		                'params': {                	
		                	earpath : data[0]
		                 	}      		
		 			 }).then(function (resp) { 		
		 				$('#loaderdiv').hide();
		 				$('#hotDeployInstance').modal('hide'); 
		 				getInstanceInfo();
		 				/*
						 * $scope.notification = { severity: 'info', msg : resp,
						 * show: true };
						 */
		 			 }, function (failReason) {
		 				$('#loaderdiv').hide();  					
		 				$scope.isError = true;
	 					$scope.errorMessage = failReason.message;
		 			 });
		        });
			}
			$scope.copyInstance = function(){
				$('#createInstance').modal('hide');
				$('#loaderdiv').show();
				teaObjectService.invoke({
					'agentID'  : $scope.instance.agentId,
		            'agentType': $scope.instance.type.agentType,
		       		'objectType': $scope.instance.type.name,           		
		       		'objectKey': $scope.instance.key,
		       		'operation' : 'copyInstance',
		       		'methodType':'UPDATE',
		       		'params': {                	
		       			instanceName : $scope.instanceData.instanceName,
		       			processingUnit:$scope.instanceData.processingUnit,
		       			hostId:$scope.instanceData.hostName,
		       			jmxPort		 : $scope.instanceData.jmxPort,
		       			deploymentPath:$scope.instanceData.deploymentPath,
					    jmxUserName :$scope.instanceData.jmxUserName,
						jmxPassword : $scope.instanceData.jmxPassword,
						version : $scope.selectedApp.config.version,
						beId : $scope.instanceData.beId 
		             	} 
				}).then(function (data) {
					$('#loaderdiv').hide();
					$('.modal-backdrop').hide();
					teaObjectService.reference({
	                	'agentID'  : $scope.selectedApp.agentId,
	                	'agentType': $scope.selectedApp.type.agentType,
	                	'objectType': $scope.selectedApp.type.name,
	                	'objectKey': $scope.selectedApp.key,
	                	'reference' : 'ServiceInstances'
	            	}).then(function (instances) {
	            		 for(var i=0 ; i < instances.length ; i++ ){
	     	            	var instanceObj =instances[i];
	     	            	if(instanceObj.name == $scope.instanceData.instanceName){
	     	            		var copiedInstance = instanceObj;
	     	            		$scope.showInstance(copiedInstance);
	     	            		break;
	     	            	}
	     	            }	
	            	}, function (failReason) {
	            		defer.reject('Error retrieving instances');
	                });
					/*
					 * $scope.notification = { severity: 'info', msg : data,
					 * show: true };
					 */		
				}, function (failReason) {
					$('#loaderdiv').hide();
					$('.modal-backdrop').hide();
					$scope.notification = {
				            severity: 'error',
				            msg : failReason.message,
				            show: true
				    };	
			    });	 
			}
			 $scope.showInstance = function(instance){
		  		   ReferenceObjectConfigService.setSelectedInstance(instance); 	
		  		   if(Storage)
		  			   localStorage.setItem("selectedInstance",JSON.stringify(instance));
		 		   $scope.instance = ReferenceObjectConfigService.getSelectedInstance();
				   createBreadCrumbs();
				   if($scope.instance.status.state=='Running')
						$scope.viewType  = "monitoring";
					else
						$scope.viewType  = "configuration";
				   $scope.reloadPage();
		 	    }
			 $scope.downloadFromURL = function (downloadurl) {
	                window.location.href = downloadurl + "?download=true";
	         };
			 $scope.downloadLog = function(isASLog){
				 if(isASLog){
					 $scope.downloadASLogs();
				 }else{
					 $scope.downloadLogs();
				 }
			 }
			$scope.downloadLogs = function(){
				$('#loaderdiv').show(); 
				teaObjectService.invoke({
					'agentID'  : $scope.instance.agentId,
		            'agentType': $scope.instance.type.agentType,
		       		'objectType': $scope.instance.type.name,           		
		       		'objectKey': $scope.instance.key,
		       		'operation' : 'downloadLog',
		       		'methodType':'READ'
				}).then(function (data) {
					$('#loaderdiv').hide(); 
					var url = window.location.protocol + "//" + window.location.host + '/tea/' + data;
	                // download the file from the URL
	                $scope.downloadFromURL(url);
					/*
					 * $scope.notification = { severity: 'info', msg : "Log
					 * downloaded successfully", show: true };
					 */			
				}, function (failReason) {
					$('#loaderdiv').hide(); 
					$scope.notification = {
				            severity: 'error',
				            msg : failReason.message,
				            show: true
				    };
			    });	 
			}
			$scope.downloadASLogs = function(){
				$('#loaderdiv').show(); 
				teaObjectService.invoke({
					'agentID'  : $scope.instance.agentId,
		            'agentType': $scope.instance.type.agentType,
		       		'objectType': $scope.instance.type.name,           		
		       		'objectKey': $scope.instance.key,
		       		'operation' : 'downloadASLog',
		       		'methodType':'READ'
				}).then(function (data) {
					$('#loaderdiv').hide(); 
					var url = window.location.protocol + "//" + window.location.host + '/tea/' + data;
	                // download the file from the URL
	                $scope.downloadFromURL(url);
				}, function (failReason) {
					$('#loaderdiv').hide(); 
					$scope.notification = {
				            severity: 'error',
				            msg : failReason.message,
				            show: true
				    };
			    });	 
			}
			$scope.getOperationInfo = function(method,group){
			    	$scope.operation = method;
			    	$scope.group = group;
			    	$scope.parameters = method.arg;
			    	$scope.result={};
			    	$scope.showResultPanel = false;			    	
			}
			$scope.invokeOperation = function(method){				
				$scope.parametersArray ={};
				var validationErrors = [];
				var index = 0;
				for(var i=0 ; i< $scope.parameters.length ; i++){

					var param = $scope.parameters[i];
					// For parameters other than session name
					if (('Session' !== param.name
							&& 'sessionName' !== param.name )) {
						if (typeof param.required !== 'undefined'
								&& param.required) {
							if (typeof param.defaultvalue !== 'undefined'
									&& null !== param.defaultvalue
									&& '' !== $
											.trim(param.defaultvalue)) {
								$scope.parametersArray[param.name] = param.defaultvalue;
							} else {
								validationErrors[index] = param.name
										+ ' is required';
								index++;
							}
						}else{
							$scope.parametersArray[param.name] = param.defaultvalue;
						}

					} else {
						// For session name
					
						if (typeof param.required !== 'undefined'
								&& param.required) {
							if($scope.selectedInstancesAgents.length !==0){

								paramValue = "";
								var k = 0;
								for (var j = 0; j < $scope.selectedInstancesAgents.length; j++) {
									var value = $scope.selectedInstancesAgents[j];
									paramValue += value;
									if (k < $scope.selectedInstancesAgents.length - 1) {
										paramValue += ',';
									}
									k++;
								}
								$scope.parametersArray[param.name] = paramValue;
							}else{

								validationErrors[index] = param.name
										+ ' is required';
								index++;
							
							}
							
						} else {
							paramValue = "";
							var k = 0;
							for (var j = 0; j < $scope.selectedInstancesAgents.length; j++) {
								var value = $scope.selectedInstancesAgents[j];
								paramValue += value;
								if (k < $scope.selectedInstancesAgents.length - 1) {
									paramValue += ',';
								}
								k++;
							}
							$scope.parametersArray[param.name] = paramValue;
						}
					}
				}
				// Show validation error
				if (validationErrors.length !== 0) {
					var message = '';
					for (var i = 0; i < validationErrors.length; i++) {
						var validationError = validationErrors[i];
						message += validationError
								+ "\n";
					}
					$scope.notification = {
						severity : 'error',
						msg : message,
						show : true
					};
					return;
				}
				$scope.result;
				teaObjectService.invoke({
					'agentID'  : $scope.instance.agentId,
		            'agentType': $scope.instance.type.agentType,
		       		'objectType': $scope.instance.type.name,           		
		       		'objectKey': $scope.instance.key,
		       		'operation' : 'invoke',
		       		'methodType':'READ',
		       		'params': {                	
		       			entityName : "Process",
		       			methodGroup : $scope.group,
						methodName : $scope.operation.name,
						params:$scope.parametersArray
		             } 
				}).then(function (info) {
					$scope.result =info;	
					$scope.showResultPanel = true;
				}, function (failReason) {					
					$scope.result={};
					$scope.notification = {
				            severity: 'error',
				            msg : failReason['message'],
				            show: true
					}});	 
				
			}
			 var getGCDetails=function (){
				 var timer;
				teaObjectService.invoke({
					'agentID'  : $scope.instance.agentId,
		            'agentType': $scope.instance.type.agentType,
		       		'objectType': $scope.instance.type.name,           		
		       		'objectKey': $scope.instance.key,
		       		'operation' : 'getGCDetails',
		       		'methodType':'READ'
				}).then(function (gcDetails) {
					timer = $timeout(getGCDetails, 60000);
					$scope.gcDetails = gcDetails;
					$('#loaderdiv').hide();
				}, function (failReason) {
					$('#loaderdiv').hide();
					console.log("failed");
				});	 
				$scope.$on("$destroy",function(event) {
                    $timeout.cancel(timer);
                }); 
			}
			 
			var getCPUUsage=function (){
				teaObjectService.invoke({
						'agentID'  : $scope.instance.agentId,
			            'agentType': $scope.instance.type.agentType,
			       		'objectType': $scope.instance.type.name,           		
			       		'objectKey': $scope.instance.key,
			       		'operation' : 'getCPUUsage',
			       		'methodType':'READ'
				}).then(function (cpuUsage) {						
						$scope.cpuUsage = cpuUsage;					
				}, function (failReason) {
						console.log("failed");
				});	 
			}
			var getMemeoryPools=function (){
				teaObjectService.invoke({
						'agentID'  : $scope.instance.agentId,
			            'agentType': $scope.instance.type.agentType,
			       		'objectType': $scope.instance.type.name,           		
			       		'objectKey': $scope.instance.key,
			       		'operation' : 'getMemeoryPools',
			       		'methodType':'READ'
				}).then(function (memeoryPools) {						
						$scope.memeoryPoolsList = memeoryPools;	
						$scope.memoryPoolName = $scope.memeoryPoolsList[0];
						$('#loaderdiv').hide();
				}, function (failReason) {
						console.log("failed");
						$('#loaderdiv').hide();
				});	 
			}
			
			function getInstancesAgents(){
				teaObjectService.reference({
					'agentID'  : $scope.instance.agentId,
		            'agentType': $scope.instance.type.agentType,
		       		'objectType': $scope.instance.type.name,           		
		       		'objectKey': $scope.instance.key,
		       		'reference' : 'Agent'
				}).then(function (agents) {						
					$scope.instanceAgents = agents;	
					getAllAgentsInfo();
					$('#loaderdiv').hide();
				}, function (failReason) {
					console.log("failed");
					$('#loaderdiv').hide();
				});	 
			}
			function getAllAgentsInfo(){
				var arr = [];
				angular.forEach($scope.instanceAgents, function(instanceAgent,index) {
					arr.push(getInstanceAgentInfo(instanceAgent,index));
				});	
				$q.all(arr).then(function (ret) {
					$scope.instanceAgentsWithInfo = angular.copy($scope.instanceAgents);
				});
			}
			function getInstanceAgentInfo(instanceAgent,index){
				var defer = $q.defer();
				teaObjectService.invoke({
					'agentID'  : instanceAgent.agentId,
					'agentType': instanceAgent.type.agentType,
					'objectType': instanceAgent.type.name,           		
					'objectKey': instanceAgent.key,
					'operation' : 'getAgentInfo',
					'methodType':'READ'
				}).then(function (info) {			
					$scope.instanceAgents[index].config =info;
					defer.resolve(info);
				}, function (failReason) {
					console.log("failed");
					defer.reject('Error retrieving info');
				});	
				return defer.promise;
			}
			/*
			 * function getInstanceAudit(){ teaObjectService.invoke({ 'agentID' :
			 * $scope.instance.agentId, 'agentType':
			 * $scope.instance.type.agentType, 'objectType':
			 * $scope.instance.type.name, 'objectKey': $scope.instance.key,
			 * 'operation' : 'getInstanceAudit', 'methodType':'READ'
			 * }).then(function (info) { $scope.auditInfo=info;
			 * $scope.tableParams = new ngTableParams({ page: 1, count: 20 },{
			 * total: $scope.auditInfo.auditRecord.length, getData:
			 * function($defer, params) { var orderedData =
			 * $scope.auditInfo.auditRecord; params.total(orderedData.length);
			 * $defer.resolve(orderedData.slice((params.page() - 1) *
			 * params.count(), params.page() * params.count())); } });
			 * angular.forEach($scope.auditInfo.auditRecord, function(rec,index) {
			 * var date = new Date(rec.performedOn); var curr_date =
			 * date.getDate(); var curr_month = date.getMonth() + 1; var
			 * curr_year = date.getFullYear();
			 * $scope.auditInfo.auditRecord[index].performedOn =curr_date + "-" +
			 * curr_month + "-" + curr_year; }); $('#loaderdiv').hide(); },
			 * function (failReason) { console.log("failed");
			 * $('#loaderdiv').hide(); }); }
			 */
			$scope.saveOrEditInstance = function(){
				if($scope.isCopy){
					$scope.copyInstance();
				}else{
					$scope.editInstance();
				}
			}
			$scope.showEditInstance = function(){
				$scope.instanceData = {};
				$scope.isEdit = true;
				$scope.isCopy = false;
				if(!$scope.instanceInfo.deployed)
					$scope.instanceData.operation = "Edit PU Instance :";
				else
					$scope.instanceData.operation = "";
				$scope.instanceData.instanceName = $scope.instance.name;
				$scope.instanceData.hostName=$scope.instanceInfo.host.hostName;
				$scope.instanceData.processingUnit=$scope.instanceInfo.puId;
				$scope.instanceData.deploymentPath =$scope.instanceInfo.deploymentPath;
				$scope.instanceData.jmxPort =$scope.instanceInfo.jmxPort;
				$scope.instanceData.jmxUserName = $scope.instanceInfo.jmxUserName;
				$scope.instanceData.jmxPassword = $scope.instanceInfo.jmxPassword;
				$scope.instanceData.beId=$scope.instanceInfo.beId;
				$scope.instanceData.deployed=$scope.instanceInfo.deployed;
				$scope.instanceData.status = $scope.instance.config.status;
				
				$scope.instanceData.version = $scope.instance.config.version;
				var selectedHost;
				for(var i=0;i< $scope.beHosts.length ; i++){
					if($scope.instanceData.hostName == $scope.beHosts[i].name){
						selectedHost = $scope.beHosts[i];
						break;
					}
				}
				$scope.beDetails=[];
				var beVersion = $scope.selectedApp.config.appVersion;
				for(var i=0;i<selectedHost.config.be.length;i++){
					if(selectedHost.config.be[i].version !="undefined" && selectedHost.config.be[i].version != null && selectedHost.config.be[i].version.indexOf(beVersion) !== -1)
						$scope.beDetails.push(selectedHost.config.be[i]);
				}
				$scope.hostAuthenticated=selectedHost.config.authenticated;
				$scope.instanceData.deploymentPathList = selectedHost.config.deploymentPath;
				$('#createInstance').modal({
					show: true,
					keyboard: false,
					backdrop: 'static'
				});
			}
			$scope.showLoadAndDeployClasses = function(){
				$scope.instanceData = {};
				$scope.instanceData.operation = "Load And Deploy Classes";
				
				$('#loadAndDeployClassesDiv').modal({
					show: true,
					keyboard: false,
					backdrop: 'static'
				});
			}
			$scope.changedefaultDeploymentPath = function(){
				$scope.instanceData.deploymentPath = $scope.instanceData.deploymentPathList;
			}
			$scope.setDeploymentPath=function(){
				var selectedHost;
				for(var i=0;i< $scope.beHosts.length ; i++){
					if($scope.instanceData.hostName == $scope.beHosts[i].name){
						selectedHost = $scope.beHosts[i];
						break;
					}
				}
				$scope.beDetails=[];
				var beVersion = $scope.selectedApp.config.appVersion;
				for(var i=0;i<selectedHost.config.be.length;i++){
					if(selectedHost.config.be[i].version !="undefined" && selectedHost.config.be[i].version != null && selectedHost.config.be[i].version.indexOf(beVersion) !== -1)
						$scope.beDetails.push(selectedHost.config.be[i]);
				}
				$scope.hostAuthenticated=selectedHost.config.authenticated;
				$scope.instanceData.deploymentPathList = selectedHost.config.deploymentPath;
				if(selectedHost.config.deploymentPath && selectedHost.config.deploymentPath!=null && selectedHost.config.deploymentPath!='')
					$scope.instanceData.deploymentPath =selectedHost.config.deploymentPath;
				var jmxPort=$scope.instanceData.jmxPort;
				
				if(jmxPort===undefined || $.trim(jmxPort)===''){
					var isPortSet=false;
					$.each(selectedHost.config.jmxPortMap, function(key, val) {
						if(!isPortSet){
							if(!val){
								jmxPort=key;
								isPortSet=true;
							}else{
								if(jmxPort<key)
									jmxPort=key;	
							}
						}
						
					});
					if(!isPortSet)
						jmxPort++;
					$scope.instanceData.jmxPort=jmxPort;
				}
			}
			$scope.editInstance = function(){
				$('#createInstance').modal('hide');
				$('#loaderdiv').show();
				teaObjectService.invoke({
					'agentID'  : $scope.instance.agentId,
					'agentType': $scope.instance.type.agentType,
					'objectType': $scope.instance.type.name,           		
					'objectKey': $scope.instance.key,
					'operation' : 'edit',
					'methodType': 'UPDATE',
					'params': {                	
						processingUnit : $scope.instanceData.processingUnit,                	 
						hostId : $scope.instanceData.hostName,
						jmxPort :$scope.instanceData.jmxPort,
						deploymentPath : $scope.instanceData.deploymentPath,
					    jmxUserName :$scope.instanceData.jmxUserName,
						jmxPassword : $scope.instanceData.jmxPassword ,
						version : $scope.instanceData.version ,
						beId : $scope.instanceData.beId 
             		} 
				}).then(function (data) {
					$('#loaderdiv').hide();
					getInstanceInfo();
					var leftNavObject =ReferenceObjectConfigService.getSelectedNavObject();
					if(leftNavObject == "machines"){
						var selectedHost;
						for(var i=0;i< $scope.appHosts.length ; i++){
							if($scope.instanceData.hostName == $scope.appHosts[i].name){
								selectedHost = $scope.appHosts[i];
								break;
							}
						}
						ReferenceObjectConfigService.setSelectedHost(selectedHost);
						createBreadCrumbs();
					}
					if(leftNavObject == "processing units"){
						var selectedPU;
						for(var i=0;i< $scope.appProcessingUnits.length ; i++){
							if($scope.instanceData.processingUnit == $scope.appProcessingUnits[i].name){
								selectedPU = $scope.appProcessingUnits[i];
								break;
							}
						}
						ReferenceObjectConfigService.setSelectedProcessingUnit(selectedPU);
						createBreadCrumbs();
					}
					/*
					 * $scope.notification = { severity: 'info', msg : data,
					 * show: true };
					 */
				}, function (failReason) {
					$('#loaderdiv').hide();
					$scope.notification = {
							severity: 'error',
							msg : failReason.message,
							show: true
					};
				});
			}
			$scope.reloadPage = function(){
				$('#loaderdiv').show(); 
				// getInstanceAudit();
				getInstancesAgents();
				getMemeoryPools();
				getGCDetails();			
				getInstanceInfo();					
				// //getLoggersInfo();
				getAllLogLevels();
				getGlobalVariables();
				getSystemVariables();
				getBEProperties();
				getJVMProperties();
				getLogPatternsAndLogLevel();
				$scope.isChartRefreshed++;
				$scope.configurationChangesFlags ={};
				$scope.configurationChangesFlags.isSystemVariableChanged = false;
				$scope.configurationChangesFlags.isJVMPropChanged = false;
				$scope.configurationChangesFlags.isBEPropChanged = false;
				$scope.configurationChangesFlags.isDeployLogLevelChanged = false;
				$scope.configurationChangesFlags.isRuntimeLogLevelChanged = false;
				$scope.configurationChangesFlags.isGlobalVariableChanged = false;
				$scope.runtimePattern = {};
				$scope.runtimePattern.pattern="";
				$scope.runtimePattern.logLevel="";
			}
			$scope.$on('reloadInstancePage', function(e) {  
				$scope.$parent.showAppInstancesPage();            
		    });
			$scope.showThreadDump = function(){
				$('#loaderdiv').show(); 
				teaObjectService.invoke({
					'agentID'  : $scope.instance.agentId,
					'agentType': $scope.instance.type.agentType,
					'objectType': $scope.instance.type.name,           		
					'objectKey': $scope.instance.key,
					'operation' : 'getThreadDump',
					'methodType': 'READ'
				}).then(function (data) {
					$('#loaderdiv').hide(); 
					$scope.threadDump = data;
					var blob = new Blob([ $scope.threadDump ], { type : 'text/plain' });
					$scope.url = (window.URL || window.webkitURL).createObjectURL( blob );
					$('#threadDump').modal({
				        show: true,
				        keyboard: false,
				        backdrop: 'static'
				    });
				}, function (failReason) {
					$('#loaderdiv').hide(); 
				});	
			}
			$scope.showLogDetailsPanel = function(isASLog){
				$('#loaderdiv').show(); 
				$scope.isASLog=isASLog;
				$scope.numberOfLines = 25;
				$scope.showLogDetails($scope.numberOfLines,$scope.isASLog);
			}
			$scope.showLogDetails = function(lines,isASLog){
				teaObjectService.invoke({
					'agentID'  : $scope.instance.agentId,
					'agentType': $scope.instance.type.agentType,
					'objectType': $scope.instance.type.name,           		
					'objectKey': $scope.instance.key,
					'operation' : 'fireTailCommand',
					'methodType': 'READ',
					'params' :{
						numberofLines : lines.toString(),
						isASLog : isASLog
					}
				}).then(function (data) {
					$scope.numberOfLines = lines;
					$scope.isASLog = isASLog;
					$('#loaderdiv').hide(); 
					$scope.logDetails = data + "\n\n\n\n\n\n\n\n\n\n\n\n"; // empty
																			// lines
																			// added
																			// so
																			// that
																			// last
																			// lines
																			// should
																			// be
																			// readable
																			// BE-24366
					var blob = new Blob([ $scope.logDetails ], { type : 'text/plain' });
					$scope.logUrl = (window.URL || window.webkitURL).createObjectURL( blob );
					$('#tailLogPanel').modal({
				        show: true,
				        keyboard: false,
				        backdrop: 'static'
				    });
				}, function (failReason) {
					$('#loaderdiv').hide(); 
				});	
			}
			$scope.setupDownloadLink = function(link, code) {
			    link.href = 'data:text/plain;charset=utf-8,' + encodeURIComponent(code);
			};
			$scope.instanceAgentsDropDownLoad = function() {
				$('#instanceAgentsDropDown').multiselect({
									includeSelectAllOption : true,
									enableFiltering : true
				});
			};
			$scope.rebuild = function() {
				$('#instanceAgentsDropDown').multiselect('rebuild');
			};
			$scope.addSelectedInstanceAgents = function() {	
				$scope.selectedInstancesAgents=[];
				$('#instanceAgentsDropDown :selected').each(function(i,selected) {
					$scope.selectedInstancesAgents[i] = $(selected).text();
				});
			};
			// Check user has permission or not
			$scope.hasPermission = function(permission){
				var privileges=ReferenceObjectConfigService.getReferenceObject('PRIVILEGES');
				return utilService.checkPermission(permission,privileges);
			};  
			function getLogPatternsAndLogLevel(){
				teaObjectService.invoke({
					'agentID'  : $scope.instance.agentId,
					'agentType': $scope.instance.type.agentType,
					'objectType': $scope.instance.type.name,           		
					'objectKey': $scope.instance.key,
					'operation' : 'getLogPatternsAndLogLevel',
					'methodType': 'READ'
				}).then(function (data) {
					 $scope.logPatternsAndLogLevel = data;
				}, function (failReason) {
				});	
			}
			$scope.addNewDeployLevelPattern = function(){
				var dummy = {defaultValue : null ,deployedValue: null,description:null,isDeleted:false,name:null,value:null};
				if($scope.logPatternsAndLogLevel == null){
					var dummyObject ={name:$scope.instance.name,nameValuePairs:{nameValuePair:[]},type:"LOG_PATTERNS"};
					$scope.logPatternsAndLogLevel=dummyObject;
				}
				$scope.logPatternsAndLogLevel.nameValuePairs.nameValuePair.push(dummy);
			}
			$scope.removeDeployLevelPattern = function(data){
			    data.isDeleted=true;
			}
			$scope.saveDeployLevelPattern = function(){
				teaObjectService.invoke({
					'agentID'  : $scope.instance.agentId,
		            'agentType': $scope.instance.type.agentType,
		       		'objectType': $scope.instance.type.name,           		
		       		'objectKey': $scope.instance.key,
		       		'operation' : 'saveLogPattern',
		       		'methodType':'UPDATE',
		       		'params': {                	
		       			logPatternAndLevel : $scope.logPatternsAndLogLevel
		             	} 
				}).then(function (data) {
					getInstanceInfo();
					$scope.configurationChangesFlags.isDeployLogLevelChanged = false;
					/*
					 * $scope.notification = { severity: 'info', msg : data,
					 * show: true };
					 */			
				}, function (failReason) {
					$scope.notification = {
				        severity: 'error',
				        msg : failReason.message,
				        show: true
					};	
				});	 
			}
			$scope.applyRuntimeLogLevelPattern = function(){
				var dummyObject ={};
				dummyObject[$scope.runtimePattern.pattern] =$scope.runtimePattern.logLevel;				
				teaObjectService.invoke({
					'agentID'  : $scope.instance.agentId,
		            'agentType': $scope.instance.type.agentType,
		       		'objectType': $scope.instance.type.name,           		
		       		'objectKey': $scope.instance.key,
		       		'operation' : 'applyLogPattern',
		       		'methodType':'UPDATE',
		       		'params': {                	
		       			logPatternsAndLevel : dummyObject
		             	} 
				}).then(function (data) {
					getInstanceInfo();
					// //getLoggersInfo();
					$scope.runtimePattern = {};
					$scope.runtimePattern.pattern="";
					$scope.runtimePattern.logLevel="";
					$scope.configurationChangesFlags.isRuntimeLogLevelChanged = false;
					/*
					 * $scope.notification = { severity: 'info', msg : data,
					 * show: true };
					 */			
				}, function (failReason) {
					$scope.notification = {
				        severity: 'error',
				        msg : failReason.message,
				        show: true
					};	
				});
			}
			$scope.setInstanceDefaultProfile=function(){
				teaObjectService.invoke({
					'agentID'  : $scope.instance.agentId,
		            'agentType': $scope.instance.type.agentType,
		       		'objectType': $scope.instance.type.name,           		
		       		'objectKey': $scope.instance.key,
		       		'operation' : 'setInstanceDefaultProfile',
		       		'methodType':'UPDATE',
		       		'params': {                	
		       			profileName : $scope.instanceInfo.defaultProfile
		             	} 
				}).then(function (data) {
					getInstanceInfo();					
				}, function (failReason) {
					$scope.notification = {
				        severity: 'error',
				        msg : failReason.message,
				        show: true
					};	
				});
			}
			// getInstanceAudit();
			getInstancesAgents();
			getMemeoryPools();
			getGCDetails();			
			getInstanceInfo();
			// getLoggersInfo();
			getAllLogLevels();
			getGlobalVariables();
			getSystemVariables();
			getBEProperties();
			getJVMProperties();
			getLogPatternsAndLogLevel();
			Date.prototype.yyyymmddhhmmss = function() {
				   var yyyy = this.getFullYear();
				   var mm = this.getMonth() < 9 ? "0" + (this.getMonth() + 1) : (this.getMonth() + 1); // getMonth()
																										// is
																										// zero-based
				   var dd  = this.getDate() < 10 ? "0" + this.getDate() : this.getDate();
				   var hh = this.getHours() < 10 ? "0" + this.getHours() : this.getHours();
				   var min = this.getMinutes() < 10 ? "0" + this.getMinutes() : this.getMinutes();
				   var ss = this.getSeconds() < 10 ? "0" + this.getSeconds() : this.getSeconds();
				   return "".concat(yyyy).concat(mm).concat(dd).concat(hh).concat(min).concat(ss);
			};
			$scope.getCurrentTimeStamp = function(){
				var currentDate = new Date();
				return currentDate.yyyymmddhhmmss();
			}
			$scope.getFormattedCPUAndMemoryUsage = function(value){
				var floatValue = value.toFixed(2);
				if(floatValue < 0.01)
					floatValue = 0.01;
				return floatValue;
			}
			$scope.chartSections = ReferenceObjectConfigService.getChartsConfig();
	}, function (failReason) {
		$('#loaderdiv').hide(); 
    })	
}]);
	