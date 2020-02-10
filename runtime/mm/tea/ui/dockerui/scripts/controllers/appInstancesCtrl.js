beConfigModule.controller("appInstancesCtrl",['$scope','$location','$q','$http','$rootScope','$timeout', 'teaLocation', 'teaObjectService', 'teaScopeDecorator','ReferenceObjectConfigService','StorageService','teaEventNotifications','utilService',
                                       function ($scope,$location,$q,$http,$rootScope,$timeout, teaLocation, teaObjectService, teaScopeDecorator ,ReferenceObjectConfigService,StorageService,teaEventNotifications,utilService){
	$('#loaderdiv').show(); 
	$rootScope.loadingComplete.then(function( apps ){
		$('#loaderdiv').hide(); 
		$scope.tabViewType="puInstances";
		$scope.mode="group";
		$scope.defaultNameInstance="Instance Name";
		$scope.runningInstances=[];
		$scope.selectedRunningInstances=[];
		$scope.instanceAgents = [];
		$scope.selectedInstancesAgents = [];
		$scope.isOnAgentView= false;
		$scope.search={};
		$scope.check={};
		$scope.check.allStarted = true;
		$scope.check.allStopped = true;
		$scope.check.allDeployed = true;
		$scope.check.allUndeployed= true;
		
		//$scope.newInstanceJmxPort =6600;
		
		$scope.configurationChangesFlags ={};
		$scope.configurationChangesFlags.isSystemVariableChanged = false;
		$scope.configurationChangesFlags.isJVMPropChanged = false;
		$scope.configurationChangesFlags.isBEPropChanged = false;
		$scope.configurationChangesFlags.isDeployLogLevelChanged = false;
		$scope.configurationChangesFlags.isRuntimeLogLevelChanged = false;
		$scope.configurationChangesFlags.isGlobalVariableChanged = false;
		
		$scope.check.allKilled= true;
		$scope.check.isHotDeployAllow = false;
		$scope.processingUnitMethods = StorageService.getPuMethods();
		$('#multiSelectDropDown').multiselect({
			includeSelectAllOption: true,
			disableIfEmpty: true,
            enableFiltering: true
		});
		//Creating breadCrumbs
		ReferenceObjectConfigService.setBreadCrumbObject();
 	   	ReferenceObjectConfigService.getBreadCrumbObject().push({name:'PU Instances',type:{name : 'instances'}});
 		if(Storage)
 			localStorage.setItem("breadCrumbs",JSON.stringify(ReferenceObjectConfigService.getBreadCrumbObject()));
		$scope.breadCrumbs=ReferenceObjectConfigService.getBreadCrumbObject();
		
		$scope.appHosts=ReferenceObjectConfigService.getReferenceObject(ReferenceObjectConfigService.getSelectedApp().name+"_Host");
		$scope.beHosts=ReferenceObjectConfigService.getReferenceObject("beHosts");
		$scope.appProcessingUnits=ReferenceObjectConfigService.getReferenceObject(ReferenceObjectConfigService.getSelectedApp().name+"_ProcessingUnits");
		$scope.appAgents=ReferenceObjectConfigService.getReferenceObject(ReferenceObjectConfigService.getSelectedApp().name+"_Agents");
		
		$scope.instanceData = {};
		
		$scope.filteredValues={};
		$scope.filteredValues.allInstancesChecked = false;
		$scope.filteredValues.isInstanceEntitySelected = false;
		
		
		$scope.hotDeploy={};
		$scope.hotDeploy.earfilesCreateApp =undefined;
		
	    $scope.variablesValueType = "equals";
	    
	    $scope.showEqualInstances = function(){
	    	$scope.variablesValueType = "equals";
	    }
	    $scope.showDifferentInstances = function(){
	    	$scope.variablesValueType = "different";
	    }
	    $scope.isSetVariablesValueType = function (viewType) {
	        return $scope.variablesValueType === viewType;
	    }
		$scope.isTabSet = function (tabViewType) {
	        return $scope.tabViewType === tabViewType;
	    }
	    $scope.setTabViewType = function (tabViewType) {
	        $scope.tabViewType = tabViewType;
	        if(tabViewType == "operation"){
	        	$scope.runningInstances=[];
	        	$scope.tempInstances = angular.copy($scope.instances);
				angular.forEach($scope.tempInstances, function(instance,index) {
					if(instance.status.state=="Running")
						$scope.runningInstances.push(instance);
				});
	        }
	    }	
	    $scope.showOperations = function(){
	    	return getSelectedInstancesList().length > 0; 
	    }
	    $scope.browseFile = function(id){
			$('#'+id).click();
		}
		$scope.setPathValue = function(id,fileId){
			$('#'+id).val($('#'+fileId).val().replace(/^.*[\\\/]/, ''));
		} 
	    $scope.addNewSystemVariable = function(){	
			var selectedInstancesList = getSelectedInstancesList(); 
			var dummy = {deployedValue : null ,effectiveValue:null,hasEqualValue: true,name:null,selectedInstances:selectedInstancesList,value:null,isNew:true};
			$scope.equalVariables.push(dummy);
		}
		$scope.removeSystemVariable = function(index,isEqual){
		    if(isEqual)
				$scope.equalVariables[index].deleted=true;
			else
				$scope.diffVariables[index].deleted=true;
		}
		$scope.addNewBEProperties = function(){	
			var selectedInstancesList = getSelectedInstancesList(); 
			var dummy = {effectiveValue:null,deployedValue : null ,hasEqualValue: true,name:null,selectedInstances:selectedInstancesList,value:null,isNew:true};
			$scope.equalVariables.push(dummy);
		}
		$scope.removeBEProperties = function(index){
			$scope.equalVariables.splice(index, 1); 
		}
		
		var subscribe = true;
	    $scope.notify = function (angularEvent, events) {
	    	if(!events[0].type == "CHILDREN_CHANGE"){
	    		getInstanceInfo();
		    	getApplicationInfo();
	    	}  	
	    }
	    $scope.$on('teaEventNotifications', $scope.notify);    
	    
	    $scope.doSubscribe = function() {
	        if (subscribe) {
	            subscribe = false;
	            angular.forEach($scope.instances, function(instance) {
	            	teaEventNotifications.addObjectIdToSubscription(instance.agentId, window.tea.location.info.agentType, instance.type.name, "", [Event.STATUS_CHANGE]);
	            	
	            })
	          //  teaEventNotifications.addObjectIdToSubscription($scope.selectedApp.agentId,  $scope.selectedApp.type.agentType,  $scope.selectedApp.type.name, "", [Event.CHILDREN_CHANGE]);
	            teaEventNotifications.subscribe();
	        }
	    }
	    $scope.selectAllInstances = function () {			
            for (var i = 0; i < $scope.filteredList.length; i++) {
            	if($scope.filteredList[i].config.predefined)
            		$scope.filteredList[i].isChecked = $scope.filteredValues.allInstancesChecked;
            }
	    }
	$scope.selectInstanceEntity = function () {          
        for (var i = 0; i < $scope.filteredList.length; i++) {
            if (!$scope.filteredList[i].isChecked) {
            	$scope.filteredValues.allInstancesChecked = false;
                return;
            }
        }          
        $scope.filteredValues.allInstancesChecked = true;
    }
	$scope.deleteInstance = function(instance,isMultiDelete){
		$scope.isMultipleInstanceDelete = isMultiDelete;
		$scope.operationMessage="Delete";
		$scope.instanceToDelete =instance; 
		$('#actionConfirmation').modal({
	        show: true,
	        keyboard: false,
	        backdrop: 'static'       
	    });	
	}
	$scope.operation = function(){
		 $('#loaderdiv').show();
		 if($scope.filteredList!=undefined){
				for (var i = 0; i < $scope.filteredList.length; i++) {
		            if ($scope.filteredList[i].isChecked) {
		                var instance = $scope.filteredList[i];
		                instance.config.host.masterHost.jmxPortMap[instance.config.jmxPort]=false;	                   
		            }
		        } 
 		}
		 if($scope.isMultipleInstanceDelete){
			 var selectedInstancesList = getSelectedInstancesList();  
			 teaObjectService.invoke({
				 	'agentID'  : $scope.selectedApp.agentId,
		            'agentType': $scope.selectedApp.type.agentType,
		       		'objectType': $scope.selectedApp.type.name,           		
		       		'objectKey': $scope.selectedApp.key,
		       		'operation' : 'groupDelete',
		       		'methodType': 'UPDATE',
		       		'params': {
						instances : selectedInstancesList,
						version : $scope.selectedApp.config.version
					}
				 }).then(function (resp) {	
					$('#loaderdiv').hide();
					$('#actionConfirmation').modal('hide');
					getInstanceInfo();
					getApplicationInfo();
					/*$scope.notification = {
				            severity: 'info',
				            msg : resp,
				            show: true
				    };*/
				 }, function (failReason) {	
					$('#loaderdiv').hide();
					$('#actionConfirmation').modal('hide');   
					$scope.notification = {
				            severity: 'error',
				            msg :failReason.message,
				            show: true
				    };
				 });
		 }else{
			 teaObjectService.invoke({
				 	'agentID'  : $scope.instanceToDelete.agentId,
		            'agentType': $scope.instanceToDelete.type.agentType,
		       		'objectType': $scope.instanceToDelete.type.name,           		
		       		'objectKey': $scope.instanceToDelete.key,
		       		'operation' : 'delete',
		       		'methodType': 'DELETE', 
		       		'params': {							
							version : $scope.selectedApp.config.version
					}
				 }).then(function (resp) {	
					$('#actionConfirmation').modal('hide');
					$('#loaderdiv').hide();
					var index = $scope.filteredList.indexOf($scope.instanceToDelete);
					var indexInMainList = $scope.instances.indexOf($scope.instanceToDelete);
					$scope.filteredList.splice(index, 1); 
					$scope.instances.splice(indexInMainList, 1);
					
					/*$scope.notification = {
				            severity: 'info',
				            msg : resp,
				            show: true
				    };*/
					getApplicationInfo();
				 }, function (failReason) {		
					$('#actionConfirmation').modal('hide'); 
					$('#loaderdiv').hide();
					$scope.notification = {
				            severity: 'error',
				            msg :failReason.message,
				            show: true
				    };
				 });
		 }
	}
	$scope.showCreateInstance = function(){
		$('#actionConfirmation').hide();
		$scope.instanceData = {};
		$scope.isCopy = false;
		$scope.isEdit = false;
		$scope.isError = false;
		$scope.instanceData.operation = "PU Instance Creation :";
		$scope.instanceData.instanceName="Instance0";
		$scope.instanceData.processingUnit="";                	 
		$scope.instanceData.hostName="";
		$scope.instanceData.jmxPort="";
		$scope.instanceData.deploymentPath="";
		$scope.instanceData.jmxUserName="";
		$scope.instanceData.jmxPassword="";
		$scope.instanceData.deploymentPathList="";
		$scope.instanceData.isMonitorable=$scope.selectedApp.config.monitorableOnly;
		if($scope.instanceData.isMonitorable===true)
			$('#createInstance').css("height","410px");
		
		$('#createInstance').modal({
			show: true,
			keyboard: false,
			backdrop: 'static'
		});
	}
	$scope.showEditInstance = function(instance){
		$('#actionConfirmation').hide();
		$scope.isEdit = true;
		$scope.isCopy = false;
		$scope.isError = false;
		$scope.instanceToEdit =instance; 
		if(!instance.config.deployed)
			$scope.instanceData.operation = "Edit PU Instance :";
		else
			$scope.instanceData.operation = "";
		$scope.instanceData.instanceName = instance.name;
		$scope.instanceData.hostName=instance.config.host.hostName;
		$scope.instanceData.processingUnit=instance.config.puId;
		$scope.instanceData.deploymentPath =instance.config.deploymentPath;
		$scope.instanceData.jmxPort =instance.config.jmxPort;
		$scope.instanceData.jmxUserName=instance.config.jmxUserName;
		$scope.instanceData.jmxPassword=instance.config.jmxPassword;
		$scope.instanceData.deployed=instance.config.deployed;
		$scope.instanceData.beId=instance.config.beId;
		var selectedHost;
		for(var i=0;i< $scope.beHosts.length ; i++){
			if($scope.instanceData.hostName == $scope.beHosts[i].name){
				selectedHost = $scope.beHosts[i];
				break;
			}
		}
		$scope.beDetails=selectedHost.config.be;
		$scope.hostAuthenticated=selectedHost.config.authenticated;
		$scope.instanceData.deploymentPathList = selectedHost.config.deploymentPath;
		$('#createInstance').modal({
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
		$scope.beDetails=selectedHost.config.be;
		$scope.hostAuthenticated=selectedHost.config.authenticated;
		$scope.instanceData.deploymentPathList = selectedHost.config.deploymentPath;
		if(selectedHost.config.deploymentPath && selectedHost.config.deploymentPath!=null && selectedHost.config.deploymentPath!='')
			$scope.instanceData.deploymentPath =selectedHost.config.deploymentPath;
		var jmxPort=$scope.instanceData.jmxPort;
		
		if(jmxPort===undefined || $.trim(jmxPort)===''){
			var isPortSet=false;
			if(undefined ===selectedHost.config.jmxPortMap||selectedHost.config.jmxPortMap.length>0){
				jmxPort=5500;
			}else{
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
			}
			
			$scope.instanceData.jmxPort=jmxPort;
		}
	}
	$scope.saveOrEditInstance = function(){
		if($scope.isEdit)
			$scope.editInstance();
		else if($scope.isCopy){
            $scope.copyInstance();
        }else
			$scope.saveInstance();
	}
	
	function getApplicationInfo(){
		teaObjectService.invoke({
			'agentID'  : $scope.selectedApp.agentId,
			'agentType': $scope.selectedApp.type.agentType,
			'objectType': $scope.selectedApp.type.name,           		
			'objectKey': $scope.selectedApp.key,
			'operation' : 'getApplication',
			'methodType': 'READ'
		}).then(function (data) {
			$scope.appInfo = data;
			$scope.selectedApp.config.author = data.author;
			$scope.selectedApp.config.clusterName = data.clusterName;
			$scope.selectedApp.config.comments = data.comments;
			$scope.selectedApp.config.description = data.description;
			$scope.selectedApp.config.deploymentStatus = data.deploymentStatus;
			$scope.selectedApp.config.version=data.version;
		}, function (failReason) {
		});	 
	}
	$scope.startInstance =function(index){
		var instanceToStart = $scope.filteredList[index];
		$('#loaderdiv').show(); 
		 teaObjectService.invoke({
			'agentID'  : instanceToStart.agentId,
            'agentType': instanceToStart.type.agentType,
      		'objectType': instanceToStart.type.name,           		
      		'objectKey': instanceToStart.key,
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
	}
	$scope.stopInstance =function(index){
		var instanceToStop = $scope.filteredList[index];
		$('#loaderdiv').show(); 
		 teaObjectService.invoke({
			'agentID'  : instanceToStop.agentId,
            'agentType': instanceToStop.type.agentType,
      		'objectType': instanceToStop.type.name,           		
      		'objectKey': instanceToStop.key,
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
	}
	$scope.startApp = function(){
		$('#loaderdiv').show(); 
		var deployedInstancesList=[];
		var unDeployedInstancesList=[];
		for (var i = 0; i < $scope.filteredList.length; i++) {
	       if ($scope.filteredList[i].isChecked && $scope.filteredList[i].config.deployed) {
	           var instance = $scope.filteredList[i];
	           deployedInstancesList.push(instance.key);	                   
	       }else if($scope.filteredList[i].isChecked && !$scope.filteredList[i].config.deployed){
	    	   var instance = $scope.filteredList[i];
	    	   unDeployedInstancesList.push(instance.name); 
	       }
	    } 
		if(unDeployedInstancesList.length >0 ){
			var undeployedInstances = unDeployedInstancesList.toString();
			$scope.notification = {
					severity: 'error',
					msg : 'Cannot start instance/s ' +undeployedInstances + ' which needs deployment.',
					show: true
			};	
		}
		teaObjectService.invoke({
			'agentID'  : $scope.selectedApp.agentId,
			'agentType': $scope.selectedApp.type.agentType,
			'objectType': $scope.selectedApp.type.name,           		
			'objectKey': $scope.selectedApp.key,
			'operation' : 'start',
			'methodType': 'UPDATE',
			'params': {
				instances : deployedInstancesList
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
	$scope.stopApp = function(){
		$('#loaderdiv').show(); 
		var selectedInstancesList = getSelectedInstancesList(); 
		teaObjectService.invoke({
			'agentID'  : $scope.selectedApp.agentId,
			'agentType': $scope.selectedApp.type.agentType,
			'objectType': $scope.selectedApp.type.name,           		
			'objectKey': $scope.selectedApp.key,
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
	$scope.killApp = function(){
		$('#loaderdiv').show(); 
		var selectedInstancesList = getSelectedInstancesList(); 
		teaObjectService.invoke({
			'agentID'  : $scope.selectedApp.agentId,
			'agentType': $scope.selectedApp.type.agentType,
			'objectType': $scope.selectedApp.type.name,           		
			'objectKey': $scope.selectedApp.key,
			'operation' : 'groupKill',
			'methodType': 'UPDATE',
			'params': {
				instances : selectedInstancesList
			}       			
		}).then(function (data) {
			getInstanceInfo();
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
	$scope.deployApp = function(){
		$('#loaderdiv').show(); 		
		var selectedInstancesList = getSelectedInstancesList(); 	
		teaObjectService.invoke({
			'agentID'  : $scope.selectedApp.agentId,
			'agentType': $scope.selectedApp.type.agentType,
			'objectType': $scope.selectedApp.type.name,           		
			'objectKey': $scope.selectedApp.key,
			'operation' : 'deploy',
			'methodType': 'UPDATE',
			'params': {
				instances : selectedInstancesList
			}
		}).then(function (data) {
			getInstanceInfo();
			getApplicationInfo();
			$('#loaderdiv').hide(); 
			if(data.indexOf('PARTIAL FAILURE') > -1){
				$scope.notification = {
			            severity: 'error',
			            msg : data,
			            show: true
				};
			}
				
/*			$scope.notification = {
	            severity: 'info',
	            msg : data,
	            show: true
			};
*/		}, function (failReason) {
			$('#loaderdiv').hide(); 
			$scope.notification = {
	            severity: 'error',
	            msg : failReason.message,
	            show: true
			};
		});	 
	}
	$scope.undeployApp = function(){
		$('#loaderdiv').show(); 
		var selectedInstancesList = getSelectedInstancesList();  
		teaObjectService.invoke({
			'agentID'  : $scope.selectedApp.agentId,
			'agentType': $scope.selectedApp.type.agentType,
			'objectType': $scope.selectedApp.type.name,           		
			'objectKey': $scope.selectedApp.key,
			'operation' : 'undeploy',
			'methodType': 'UPDATE',
			'params': {
				instances : selectedInstancesList
			}
		}).then(function (data) {
			$('#loaderdiv').hide(); 
			getInstanceInfo();
			getApplicationInfo();
			if(data.indexOf('PARTIAL FAILURE') > -1){
				$scope.notification = {
			            severity: 'error',
			            msg : data,
			            show: true
				};
			}
			/*$scope.notification = {
	            severity: 'info',
	            msg :data,
	            show: true
			};*/
		}, function (failReason) {
			$('#loaderdiv').hide(); 
			$scope.notification = {
	            severity: 'error',
	            msg : failReason.message,
	            show: true
			};
		});	 
	}
	$scope.reDeployApp = function(){
		$('#loaderdiv').show(); 
		var selectedInstancesList = getSelectedInstancesList(); 
		teaObjectService.invoke({
			'agentID'  : $scope.selectedApp.agentId,
			'agentType': $scope.selectedApp.type.agentType,
			'objectType': $scope.selectedApp.type.name,           		
			'objectKey': $scope.selectedApp.key,
			'operation' : 'redeploy',
			'methodType': 'UPDATE',
			'params': {
				instances : selectedInstancesList
			}
		}).then(function (data) {
			$('#loaderdiv').hide(); 
			/*$scope.notification = {
	            severity: 'info',
	            msg : data,
	            show: true
			};*/
		}, function (failReason) {
			$('#loaderdiv').hide(); 
			$scope.notification = {
	            severity: 'error',
	            msg : failReason.message,
	            show: true
			};
		});	 
	}
	
	$scope.loadGroupGlobalVariables = function(){	
		$scope.configurationChangesFlags.isGlobalVariableChanged = false;
		$('#loaderdiv').show(); 
		var selectedInstancesList = getSelectedInstancesList(); 
		teaObjectService.invoke({
			'agentID'  : $scope.selectedApp.agentId,
			'agentType': $scope.selectedApp.type.agentType,
			'objectType': $scope.selectedApp.type.name,           		
			'objectKey': $scope.selectedApp.key,
			'operation' : 'loadGroupGlobalVariable',
			'methodType': 'UPDATE',
			'params': {
				instancesKey : selectedInstancesList
			}
		}).then(function (data) {
			$('#loaderdiv').hide(); 
			$scope.groupGlobalVariables = data; 
			$scope.equalVariables =[];
			$scope.diffVariables = [];
			$scope.groupType = "Global Variables";
			$scope.equalTabTitle = "Same value variables";
			$scope.differentTabTitle = "Different value variables";
			for(var i=0 ; i<$scope.groupGlobalVariables.length;i++){
				var gVariable = $scope.groupGlobalVariables[i];
				if(gVariable.hasEqualValue)
				  $scope.equalVariables.push(gVariable);
				else
				  $scope.diffVariables.push(gVariable);
			}
			$('#groupVariables').modal({
		        show: true,
		        keyboard: false,
		        backdrop: 'static'
		    });
		}, function (failReason) {
			$('#loaderdiv').hide(); 
			$scope.notification = {
		            severity: 'error',
		            msg : failReason.message,
		            show: true
			};
		});	 
	}
	$scope.loadGroupSystemVariables = function(){
		$scope.configurationChangesFlags.isSystemVariableChanged = false;
		$('#loaderdiv').show(); 
		var selectedInstancesList = getSelectedInstancesList(); 
		teaObjectService.invoke({
			'agentID'  : $scope.selectedApp.agentId,
			'agentType': $scope.selectedApp.type.agentType,
			'objectType': $scope.selectedApp.type.name,           		
			'objectKey': $scope.selectedApp.key,
			'operation' : 'loadGroupSystemVariable',
			'methodType': 'UPDATE',
			'params': {
				instancesKey : selectedInstancesList
			}
		}).then(function (data) {
			$('#loaderdiv').hide(); 
			$scope.groupSystemVariables = data; 
			$scope.equalVariables =[];
			$scope.diffVariables = [];
			$scope.groupType = "System Properties";
			$scope.equalTabTitle = "Same value properties";
			$scope.differentTabTitle = "Different value properties";
			for(var i=0 ; i<$scope.groupSystemVariables.length;i++){
				var gVariable = $scope.groupSystemVariables[i];
				if(gVariable.hasEqualValue)
				  $scope.equalVariables.push(gVariable);
				else
				  $scope.diffVariables.push(gVariable);
			}
			$('#groupSystemVariables').modal({
		        show: true,
		        keyboard: false,
		        backdrop: 'static'
		    });
		}, function (failReason) {
			$('#loaderdiv').hide(); 
			$scope.notification = {
		            severity: 'error',
		            msg : failReason.message,
		            show: true
			};
		});	 
	}
	$scope.loadGroupBusinessEventsProperties = function(){	
		$scope.configurationChangesFlags.isBEPropChanged = false;
		$('#loaderdiv').show(); 
		var selectedInstancesList = getSelectedInstancesList(); 
		teaObjectService.invoke({
			'agentID'  : $scope.selectedApp.agentId,
			'agentType': $scope.selectedApp.type.agentType,
			'objectType': $scope.selectedApp.type.name,           		
			'objectKey': $scope.selectedApp.key,
			'operation' : 'loadGroupBEProperties',
			'methodType': 'UPDATE',
			'params': {
				instancesKey : selectedInstancesList
			}
		}).then(function (data) {
			$('#loaderdiv').hide(); 
			$scope.groupBEProperties = data; 
			$scope.equalVariables =[];
			$scope.diffVariables = [];
			$scope.groupType = "Business Events Properties";
			$scope.equalTabTitle = "Same value properties";
			$scope.differentTabTitle = "Different value properties";
			for(var i=0 ; i<$scope.groupBEProperties.length;i++){
				var gVariable = $scope.groupBEProperties[i];
				if(gVariable.hasEqualValue)
				  $scope.equalVariables.push(gVariable);
				else
				  $scope.diffVariables.push(gVariable);
			}
			$('#groupBusinessProp').modal({
		        show: true,
		        keyboard: false,
		        backdrop: 'static'
		    });
		}, function (failReason) {
			$('#loaderdiv').hide(); 
			$scope.notification = {
		            severity: 'error',
		            msg : failReason.message,
		            show: true
			};
		});	 
	}
	$scope.loadGroupJVMProperties = function(){	
		$scope.configurationChangesFlags.isJVMPropChanged = false;
		$('#loaderdiv').show(); 
		var selectedInstancesList = getSelectedInstancesList(); 
		teaObjectService.invoke({
			'agentID'  : $scope.selectedApp.agentId,
			'agentType': $scope.selectedApp.type.agentType,
			'objectType': $scope.selectedApp.type.name,           		
			'objectKey': $scope.selectedApp.key,
			'operation' : 'loadGroupJVMProperties',
			'methodType': 'UPDATE',
			'params': {
				instancesKey : selectedInstancesList
			}
		}).then(function (data) {	
			$('#loaderdiv').hide(); 
			$scope.groupJVMProperties = data; 
			$scope.equalVariables =[];
			$scope.diffVariables = [];
			$scope.groupType = "JVM Properties";
			$scope.equalTabTitle = "Same value properties";
			$scope.differentTabTitle = "Different value properties";
			for(var i=0 ; i<$scope.groupJVMProperties.length;i++){
				var gVariable = $scope.groupJVMProperties[i];
				if(gVariable.hasEqualValue)
				  $scope.equalVariables.push(gVariable);
				else
				  $scope.diffVariables.push(gVariable);
			}
			$('#jvmProperties').modal({
		        show: true,
		        keyboard: false,
		        backdrop: 'static'
		    });
		}, function (failReason) {
			$('#loaderdiv').hide(); 
			$scope.notification = {
		            severity: 'error',
		            msg : failReason.message,
		            show: true
			};
		});	 
	}
	function changeInstancesListForDiffValue(){
		for(var i=0 ; i < $scope.diffVariables.length ; i++ ){
			if($scope.diffVariables[i].hasEqualValue){
				$scope.diffVariables[i].selectedInstances = getSelectedInstancesList();
			}
		}
	}
	$scope.saveGroupJVMProperties = function()  {
		$('#loaderdiv').show(); 
		$('#jvmProperties').modal('hide');
		changeInstancesListForDiffValue();
		$scope.groupJVMProperties = $scope.equalVariables.concat($scope.diffVariables);
		teaObjectService.invoke({
			'agentID'  : $scope.selectedApp.agentId,
			'agentType': $scope.selectedApp.type.agentType,
			'objectType': $scope.selectedApp.type.name,           		
			'objectKey': $scope.selectedApp.key,
			'operation' : 'saveGroupJVMproperties',
			'methodType': 'UPDATE',
			'params': {
				groupDeploymentVariables : $scope.groupJVMProperties
			}
		}).then(function (data) {
			$('#loaderdiv').hide(); 
			getInstanceInfo();
			getApplicationInfo();
			/*$scope.notification = {
	            severity: 'info',
	            msg : data,
	            show: true
			};*/
		}, function (failReason) {
			$('#loaderdiv').hide(); 
			$scope.notification = {
		            severity: 'error',
		            msg : failReason.message,
		            show: true
				};
		});	 
	}
	$scope.saveGroupGlobalVariables = function()  {
		$('#loaderdiv').show(); 
		$('#groupVariables').modal('hide'); 
		changeInstancesListForDiffValue();
		$scope.groupGlobalVariables = $scope.equalVariables.concat($scope.diffVariables);
		teaObjectService.invoke({
			'agentID'  : $scope.selectedApp.agentId,
			'agentType': $scope.selectedApp.type.agentType,
			'objectType': $scope.selectedApp.type.name,           		
			'objectKey': $scope.selectedApp.key,
			'operation' : 'saveGroupGlobalVariables',
			'methodType': 'UPDATE',
			'params': {
				groupDeploymentVariables : $scope.groupGlobalVariables
			}
		}).then(function (data) {
			$('#loaderdiv').hide(); 
			getInstanceInfo();
			getApplicationInfo();
			/*$scope.notification = {
	            severity: 'info',
	            msg : data,
	            show: true
			};*/
		}, function (failReason) {
			$('#loaderdiv').hide(); 
			$scope.notification = {
		            severity: 'error',
		            msg : failReason.message,
		            show: true
				};
		});	 
	}
	$scope.saveGroupSystemVariables = function() {
		$('#loaderdiv').show(); 
		$('#groupSystemVariables').modal('hide');
		changeInstancesListForDiffValue();
		$scope.groupSystemVariables = $scope.equalVariables.concat($scope.diffVariables);
		teaObjectService.invoke({
			'agentID'  : $scope.selectedApp.agentId,
			'agentType': $scope.selectedApp.type.agentType,
			'objectType': $scope.selectedApp.type.name,           		
			'objectKey': $scope.selectedApp.key,
			'operation' : 'saveGroupSystemVariables',
			'methodType': 'UPDATE',
			'params': {
				groupDeploymentVariables : $scope.groupSystemVariables
			}
		}).then(function (data) {
			$('#loaderdiv').hide(); 
			getInstanceInfo();
			getApplicationInfo();
			/*$scope.notification = {
	            severity: 'info',
	            msg : data,
	            show: true
			};*/
		}, function (failReason) {
			$('#loaderdiv').hide(); 
			$scope.notification = {
		            severity: 'error',
		            msg : failReason.message,
		            show: true
				};
		});	 
	}
	$scope.saveGroupBEProperties = function()  {
		$('#loaderdiv').show(); 
		$('#groupBusinessProp').modal('hide');
		changeInstancesListForDiffValue();
		$scope.groupBEProperties = $scope.equalVariables.concat($scope.diffVariables);
		teaObjectService.invoke({
			'agentID'  : $scope.selectedApp.agentId,
			'agentType': $scope.selectedApp.type.agentType,
			'objectType': $scope.selectedApp.type.name,           		
			'objectKey': $scope.selectedApp.key,
			'operation' : 'saveGroupBEProperties',
			'methodType': 'UPDATE',
			'params': {
				groupDeploymentVariables : $scope.groupBEProperties
			}
		}).then(function (data) {
			$('#loaderdiv').hide(); 
			getInstanceInfo();
			getApplicationInfo();
			/*$scope.notification = {
	            severity: 'info',
	            msg : data,
	            show: true
			};*/
		}, function (failReason) {
			$('#loaderdiv').hide(); 
			$scope.notification = {
		            severity: 'error',
		            msg : failReason.message,
		            show: true
				};
		});	 
	}
	
	$scope.saveInstance = function(){
		$('#loaderdiv').show();
		teaObjectService.invoke({
			'agentID'  : $scope.selectedApp.agentId,
			'agentType': $scope.selectedApp.type.agentType,
			'objectType': $scope.selectedApp.type.name,           		
			'objectKey': $scope.selectedApp.key,
			'operation' : 'createServiceInstance',
			'methodType': 'UPDATE',
			'params': {                	
				name : $scope.instanceData.instanceName,
				processingUnit : $scope.instanceData.processingUnit,                	 
				hostId : $scope.instanceData.hostName,
				jmxPort :$scope.instanceData.jmxPort,
				deploymentPath : $scope.instanceData.deploymentPath,
				jmxUserName :$scope.instanceData.jmxUserName,
				jmxPassword : $scope.instanceData.jmxPassword,
				version : $scope.selectedApp.config.version,
				beId:$scope.instanceData.beId
         		} 
		}).then(function (data) {
			//$scope.newInstanceJmxPort++;
			$('#loaderdiv').hide(); 
			$('#createInstance').modal('hide');  
			subscribe = true;
			getInstanceInfo();
			getApplicationInfo();
			/*$scope.notification = {
	            severity: 'info',
	            msg : data,
	            show: true
			};*/
		}, function (failReason) {
			$scope.isError = true;
			$scope.errorMessage = failReason.message;
			$('#loaderdiv').hide(); 
		});
	}
	$scope.editInstance = function(){
		$('#loaderdiv').show(); 
		var instance =$scope.instanceToEdit;
		if(instance.config.jmxPort!==$scope.instanceData.jmxPort){
			for(var i=0;i< $scope.beHosts.length ; i++){
				if($scope.instanceData.hostName == $scope.beHosts[i].name){
					var selectedHost = $scope.beHosts[i];
					selectedHost.config.jmxPortMap[instance.config.jmxPort]=false;
					selectedHost.config.jmxPortMap[$scope.instanceData.jmxPort]=true;
					 $scope.beHosts[i]=selectedHost;					
					break;
				}
			}
		}
		teaObjectService.invoke({
			'agentID'  : instance.agentId,
			'agentType': instance.type.agentType,
			'objectType': instance.type.name,           		
			'objectKey': instance.key,
			'operation' : 'edit',
			'methodType': 'UPDATE',
			'params': {                	
				processingUnit : $scope.instanceData.processingUnit,                	 
				hostId : $scope.instanceData.hostName,
				jmxPort :$scope.instanceData.jmxPort,
				deploymentPath : $scope.instanceData.deploymentPath,
				jmxUserName :$scope.instanceData.jmxUserName,
				jmxPassword : $scope.instanceData.jmxPassword,
				version : $scope.instanceData.version,
				beId : $scope.instanceData.beId
         		} 
		}).then(function (data) {
			$('#loaderdiv').hide(); 
			$('#createInstance').modal('hide');   
			getInstanceInfo();
			getApplicationInfo();
			/*$scope.notification = {
	            severity: 'info',
	            msg : data,
	            show: true
			};*/
		}, function (failReason) {
			$('#loaderdiv').hide(); 
			$scope.isError = true;
			$scope.errorMessage = failReason.message;
		});
	}
    $scope.copyInstance = function(){
        var instance =$scope.instanceToEdit;
        $('#loaderdiv').show(); 
        teaObjectService.invoke({
            'agentID'  : instance.agentId,
            'agentType': instance.type.agentType,
            'objectType':instance.type.name,
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
				beId : $scope.selectedApp.config.beId
            }
        }).then(function (data) {
        	$('#loaderdiv').hide(); 
        	//$('.modal-backdrop').hide();
        	$('#createInstance').modal('hide');
        	getInstanceInfo();
        	getApplicationInfo();
            /*$scope.notification = {
                severity: 'info',
                msg : data,
                show: true
            };*/
        }, function (failReason) {
            $('#loaderdiv').hide(); 
            $scope.isError = true;
			$scope.errorMessage = failReason.message;
        });
    	}
    	function getSelectedInstancesList(){
    		var selectedInstancesList = []; 
			if($scope.filteredList!=undefined){
				for (var i = 0; i < $scope.filteredList.length; i++) {
		            if ($scope.filteredList[i].isChecked) {
		                var instance = $scope.filteredList[i];
		                selectedInstancesList.push(instance.key);	                   
		            }
		        } 
    		}
			return selectedInstancesList;
		}
	    $scope.reloadPage = function(){
	    	getInstances();
			getApplicationInfo();
			$scope.configurationChangesFlags.isSystemVariableChanged = false;
			$scope.configurationChangesFlags.isJVMPropChanged = false;
			$scope.configurationChangesFlags.isBEPropChanged = false;
			$scope.configurationChangesFlags.isDeployLogLevelChanged = false;
			$scope.configurationChangesFlags.isRuntimeLogLevelChanged = false;
			$scope.configurationChangesFlags.isGlobalVariableChanged = false;
		}
	    $scope.dismiss =function(){
			$scope.notification.show = false;
			if(!utilService.getAgentReachable()){
				$location.path("/");
			}
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
		$scope.getOperationInfo = function(method,group){
			$('#multiSelectDropDown').multiselect('rebuild');
	    	$scope.operation = method;
	    	$scope.group = group;
	    	$scope.parameters = method.arg;
	    	$scope.result={};
	    	$scope.showResultPanel = false;
		}
		$scope.invokeOperation = function(method){
			if($scope.runningInstances&&$scope.runningInstances.length==0){	
				$scope.notification = {
						severity: 'error',
						msg : 'There are no running instances at the moment.Try again later.',
						show: true
				};	
				return;
			}
			else if($scope.selectedRunningInstances&&$scope.selectedRunningInstances.length==0){	
				$scope.notification = {
						severity: 'error',
						msg : 'Select atleast one running instance from the dropdown to perform operations.',
						show: true
				};	
				return;
			}
			$scope.parametersArray ={};
			var validationErrors = [];
			var index = 0;
			for(var i=0 ; i< $scope.parameters.length ; i++){

				var param = $scope.parameters[i];
				//For parameters other than session name
				if (('Session' !== param.name
						&&  'sessionName' !== param.name )) {
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
					//For session name
				
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
					}else {
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
			
			//Show validation error
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
			
			var resultCopy={headers:{header:[]},rows:{row:[]}};
			$scope.result={};
			//var selectedInstancesList = getSelectedInstancesList(); 
			teaObjectService.invoke({
				'agentID'  : $scope.selectedApp.agentId,
				'agentType': $scope.selectedApp.type.agentType,
				'objectType': $scope.selectedApp.type.name,           		
				'objectKey': $scope.selectedApp.key,
				'operation' : 'groupInvoke',
				'methodType':'READ',
				'params': {                	
					entityName : "Process",
					methodGroup : $scope.group,
					methodName : $scope.operation.name,
					params:$scope.parametersArray,
					instanceList:$scope.selectedRunningInstances,
					groupOperation:true
				} 
			}).then(function (info) {
				var headersAdded=false;
				for(var key in info)
				{	
					if(info[key]!=null){
						var rows =info[key].rows;
						if(!headersAdded){
							if(typeof info[key].headers !== 'undefined' && null != info[key].headers){
								resultCopy.headers.header =info[key].headers.header;
								if(info[key].headers.header.length >0 ){
									resultCopy.headers.header.push({value:"Instance Name"});
								}	
								headersAdded = true;
							}
						}
						if(typeof rows !== 'undefined' && null != rows){						
							for(var row in rows.row){
								rows.row[row].columns.column.push({value:key});
								resultCopy.rows.row.push(rows.row[row]);
							}
						}
					}
				}
				$scope.result=resultCopy;
				$scope.showResultPanel = true;
			}, function (failReason) {					
				$scope.result= undefined;
				$scope.failResult = failReason;
				$scope.notification = {
		            severity: 'error',
		            msg : failReason['message'],
		            show: true
			}});	 
		}
	    $scope.showCopyOperation = function(){
            var selectedInstanceList;
            selectedInstanceList = getSelectedInstancesList();
            return selectedInstanceList.length == 1;
        }
        $scope.showCopyInstance = function(){
            for (var i = 0; i < $scope.filteredList.length; i++) {
                if ($scope.filteredList[i].isChecked) {
                    $scope.instance = $scope.filteredList[i];
                }
            }
            $scope.instanceData = {};
            $scope.instanceToEdit =$scope.instance;
            $scope.isCopy = true;
            $scope.isEdit = false;
            $scope.isError = false;
            $scope.instanceData.operation = "Copy Instance :";
            $scope.instanceData.instanceName = "copyOf"+$scope.instance.name;
            $scope.instanceData.hostName=$scope.instance.config.host.hostName;
            $scope.instanceData.processingUnit=$scope.instance.config.puId;
            $scope.instanceData.deploymentPath =$scope.instance.config.deploymentPath;
            $scope.instanceData.jmxPort =$scope.instance.config.jmxPort;
            $scope.instanceData.jmxUserName=$scope.instance.config.jmxUserName;
			$scope.instanceData.jmxPassword=$scope.instance.config.jmxPassword;
			$scope.instanceData.beId=$scope.instance.config.beId;
			
			var selectedHost;
			for(var i=0;i< $scope.beHosts.length ; i++){
				if($scope.instanceData.hostName == $scope.beHosts[i].name){
					selectedHost = $scope.beHosts[i];
					break;
				}
			}
			$scope.beDetails=selectedHost.config.be;
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
			$('#earfileforHotDeploy').val("");
			$('#earfilepathforHotDeploy').val("");
			$('#hotDeployInstance').modal({
				show: true,
				keyboard: false,
				backdrop: 'static'
			});
		}
		$scope.hotDeployApp = function(earfile){
			if(angular.isUndefined(earfile)){
				$scope.hotDeploy.isFileUploaded = false;
				return;
			}
			$('#loaderdiv').show(); 
			$('#hotDeployInstance').modal('hide'); 
			var selectedInstancesList = getSelectedInstancesList(); 
			var fd = new FormData();       
	        fd.append("file", earfile);
			$http.post("/teas/fileupload/", fd, {
	            transformRequest: angular.identity,
	            headers: {
	                "Content-Type": undefined
	            }
	        }).success(function(data, status, headers, config) {        	  
	 			 teaObjectService.invoke({
	 				'agentID'  : $scope.selectedApp.agentId,
		            'agentType': $scope.selectedApp.type.agentType,
		       		'objectType': $scope.selectedApp.type.name,           		
		       		'objectKey': $scope.selectedApp.key,
	            	'operation': 'hotdeploy',
	                'methodType': 'UPDATE',
	                'params': {                	
	                	earpath : data[0],
	                	instances : selectedInstancesList
	                 	}      		
	 			 }).then(function (resp) { 		
	 				$('#loaderdiv').hide();
	 				getInstanceInfo();
	 				getApplicationInfo();
	 				/*$scope.notification = {
	 			            severity: 'info',
	 			            msg : resp,
	 			            show: true
	 			    };*/
	 			 }, function (failReason) {
	 				$('#loaderdiv').hide();  					
	 				$scope.notification = {
	 			            severity: 'error',
	 			            msg : failReason.message,
	 			            show: true
	 			    };
	 			 });
	        });
		}
		function getInstanceInfo(){	
			var defer = $q.defer();
			$scope.selectedApp = ReferenceObjectConfigService.getSelectedApp(); 
			teaObjectService.reference({
                	'agentID'  : $scope.selectedApp.agentId,
                	'agentType': $scope.selectedApp.type.agentType,
                	'objectType': $scope.selectedApp.type.name,
                	'objectKey': $scope.selectedApp.key,
                	'reference' : 'ServiceInstances'
            	}).then(function (instances) {
            			$scope.instances=instances;
            			ReferenceObjectConfigService.addReferenceObject($scope.selectedApp.name +"_Instances", instances);
            			var instancesList = $scope.instances;
            			var oldFilteredList = angular.copy($scope.filteredList);
            			angular.forEach(instancesList, function(instance,index) {
            				if(instance.status.state == "Running" && instance.config.upTime!= -1){
            					var upTime=utilService.getFormattedDate(instance.config.upTime);
            				}else
            					var upTime = "";
            				instancesList[index].config.upTime=upTime;
            				if(oldFilteredList && oldFilteredList.length == instancesList.length){ //to keep prvious group selection
            					instancesList[index].isChecked =oldFilteredList[index].isChecked;
            				}else if($scope.instanceToEdit && oldFilteredList && index < oldFilteredList.length && $scope.instanceToEdit.name == oldFilteredList[index].name){
            					instancesList[index].isChecked =oldFilteredList[index].isChecked;
            				}else
            					$scope.filteredValues.allInstancesChecked = false;
            			});	
            			$scope.filteredList = angular.copy(instancesList);
            			$scope.showIndividualOperations();
            			$scope.doSubscribe();
            			defer.resolve(instances);
            	}, function (failReason) {
            		defer.reject('Error retrieving app instances');
               });
			return defer.promise;
		}
		$scope.downloadFromURL = function (downloadurl) {
	        $http({
	            method: 'GET',
	            url: downloadurl
	        }).success(function (data) {
	            if (navigator.appVersion && navigator.appVersion.indexOf('MSIE') >= 0) {
	                //The way to make download work on IE
	                navigator.msSaveBlob(new Blob([data], {type: "application/octet-stream"}));
	            } else {
	                //For browsers other than IE, we can just have download=true to support the download of the file.
	                window.location.href = downloadurl + "?download=true";
	            }
	        });
	    };
		$scope.downloadThreadDump = function(){
			$('#loaderdiv').show(); 
			var selectedInstancesList = getSelectedInstancesList();  
			teaObjectService.invoke({
				'agentID'  : $scope.selectedApp.agentId,
				'agentType': $scope.selectedApp.type.agentType,
				'objectType': $scope.selectedApp.type.name,           		
				'objectKey': $scope.selectedApp.key,
				'operation' : 'groupThreadDump',
				'methodType': 'READ',
				'params': {
					instances : selectedInstancesList
				}
			}).then(function (data) {
				$('#loaderdiv').hide(); 
				var url = window.location.protocol + "//" + window.location.host + '/tea/' + data;
                //download the file from the URL
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
		$scope.downloadLogs = function(){
			$('#loaderdiv').show(); 
			var selectedInstancesList = getSelectedInstancesList();  
			teaObjectService.invoke({
				'agentID'  : $scope.selectedApp.agentId,
				'agentType': $scope.selectedApp.type.agentType,
				'objectType': $scope.selectedApp.type.name,           		
				'objectKey': $scope.selectedApp.key,
				'operation' : 'downloadLog',
				'methodType': 'READ',
				'params': {
					instances : selectedInstancesList
				}
			}).then(function (data) {
				$('#loaderdiv').hide(); 
				var url = window.location.protocol + "//" + window.location.host + '/tea/' + data;
                //download the file from the URL
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
		$scope.downloadASLogs = function(){
			$('#loaderdiv').show(); 
			var selectedInstancesList = getSelectedInstancesList();  
			teaObjectService.invoke({
				'agentID'  : $scope.selectedApp.agentId,
				'agentType': $scope.selectedApp.type.agentType,
				'objectType': $scope.selectedApp.type.name,           		
				'objectKey': $scope.selectedApp.key,
				'operation' : 'downloadASLog',
				'methodType': 'READ',
				'params': {
					instances : selectedInstancesList
				}
			}).then(function (data) {
				$('#loaderdiv').hide(); 
				var url = window.location.protocol + "//" + window.location.host + '/tea/' + data;
                //download the file from the URL
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
		$scope.$on('reloadInstancePage', function(e) {  
			$scope.reloadPage();            
	    });
		$scope.filterFunction = function(instance){
			if(($scope.search.name == undefined || $scope.search.name == null || $scope.search.name == "" || (angular.uppercase(instance.name).indexOf(angular.uppercase($scope.search.name)) > -1)) &&
			   ($scope.search.status == undefined || $scope.search.status == null || $scope.search.status == "" || ((angular.uppercase(instance.status.state).indexOf(angular.uppercase($scope.search.status)) > -1)|| (angular.uppercase(instance.config.healthStatus).indexOf(angular.uppercase($scope.search.status)) > -1))) &&
			   ($scope.search.hostName == undefined || $scope.search.hostName == null || $scope.search.hostName == "" || (angular.uppercase(instance.config.host.hostName).indexOf(angular.uppercase($scope.search.hostName)) > -1)) &&
			   ($scope.search.jmxPort == undefined || $scope.search.jmxPort == null || $scope.search.jmxPort == "" || (angular.uppercase(instance.config.jmxPort + '').indexOf(angular.uppercase($scope.search.jmxPort)) > -1)) &&
			   ($scope.search.puId == undefined || $scope.search.puId == null || $scope.search.puId == "" || (angular.uppercase(instance.config.puId).indexOf(angular.uppercase($scope.search.puId)) > -1)) &&
			   ($scope.search.processId == undefined || $scope.search.processId == null || $scope.search.processId == "" || (angular.uppercase(instance.config.processId + '').indexOf(angular.uppercase($scope.search.processId)) > -1)) &&
			   ($scope.search.deploymentStatus == undefined || $scope.search.deploymentStatus == null || $scope.search.deploymentStatus == "" || (angular.uppercase(instance.config.deploymentStatus).indexOf(angular.uppercase($scope.search.deploymentStatus)) > -1))){
				return true;
			}
			else
				return false;
		}
		$scope.showIndividualOperations = function(){
			var selectedInstances =[];
			$scope.check.allStarted = true;
			$scope.check.allStopped = true;
			$scope.check.allDeployed = true;
			$scope.check.allUndeployed= true;
			$scope.check.allKilled= true;
			$scope.check.isHotDeployAllow = false;
			for (var i = 0; i < $scope.filteredList.length; i++) {
	            if ($scope.filteredList[i].isChecked && $scope.filteredList[i].status.state == 'Stopped') {
	            	$scope.check.allStarted = false;
	            }
	            if ($scope.filteredList[i].isChecked && $scope.filteredList[i].status.state == 'Running') {
	            	$scope.check.allStopped = false;
	            }
	            if ($scope.filteredList[i].isChecked && ($scope.filteredList[i].status.state == 'Running' || $scope.filteredList[i].status.state == 'Starting' || $scope.filteredList[i].status.state == 'Stopping')) {
	            	$scope.check.allKilled = false;
	            }
	            if ($scope.filteredList[i].isChecked && $scope.filteredList[i].config.deploymentStatus != 'Deployed') {
	            	$scope.check.allDeployed = false;
	            }
	            if ($scope.filteredList[i].isChecked && $scope.filteredList[i].status.state != 'Running' && ($scope.filteredList[i].config.deploymentStatus == 'Deployed' || $scope.filteredList[i].config.deploymentStatus == 'Needs Re-deployment')) {
	            	$scope.check.allUndeployed = false;
	            }
	            if ($scope.filteredList[i].isChecked && $scope.filteredList[i].config.hotDeployable) {
	            	$scope.check.isHotDeployAllow = true;
	            }
	        } 
		};
		$scope.selectedInstanceAgents = function() {
			$scope.instanceAgents = [];
			if ($scope.selectedRunningInstances
					&& $scope.selectedRunningInstances.length !== 0) {
				teaObjectService
						.invoke(
								{
									'agentID' : $scope.selectedApp.agentId,
									'agentType' : $scope.selectedApp.type.agentType,
									'objectType' : $scope.selectedApp.type.name,
									'objectKey' : $scope.selectedApp.key,
									'operation' : 'getInstanceAgents',
									'methodType' : 'READ',
									'params' : {
										instances : $scope.selectedRunningInstances,
									}
								})
						.then(
								function(
										agents) {
									$scope.instanceAgents = agents;
								},
								function(
										failReason) {
									$scope.result = undefined;
									$scope.failResult = failReason;
									$scope.notification = {
										severity : 'error',
										msg : failReason['message'],
										show : true
									}
								});
			}
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
		//Check user has permission or not
		$scope.hasPermission = function(permission){
			var privileges=ReferenceObjectConfigService.getReferenceObject('PRIVILEGES');
			return utilService.checkPermission(permission,privileges);
		}; 
		function getRuntimeLoggers(){
			var selectedInstancesList = getSelectedInstancesList(); 
			var defer = $q.defer();
			teaObjectService.invoke({
				'agentID'  : $scope.selectedApp.agentId,
				'agentType': $scope.selectedApp.type.agentType,
				'objectType': $scope.selectedApp.type.name,           		
				'objectKey': $scope.selectedApp.key,
				'operation' : 'getLoggers',
				'methodType': 'READ',
				'params': {
					instances : selectedInstancesList
				}
			}).then(function (data) {
				defer.resolve(data);
				$scope.groupRuntimeLoggers = data;
			}, function (failReason) {
				defer.reject('failed');
			});	 
			return defer.promise;
		}
		function getAllLogLevels(){
			$scope.selectedApp = ReferenceObjectConfigService.getSelectedApp();
			teaObjectService.invoke({
				'agentID'  : $scope.selectedApp.agentId,
				'agentType': $scope.selectedApp.type.agentType,
				'objectType': $scope.selectedApp.type.name,           		
				'objectKey': $scope.selectedApp.key,
				'operation' : 'getLogLevels',
				'methodType': 'READ'
			}).then(function (data) {
				$scope.allLogLevels = data;
			}, function (failReason) {
				
			});	 
		}
		$scope.loadGroupLogLevelPatterns = function(){
			$scope.configurationChangesFlags.isDeployLogLevelChanged = false;
			$('#loaderdiv').show(); 
			var selectedInstancesList = getSelectedInstancesList(); 
			teaObjectService.invoke({
				'agentID'  : $scope.selectedApp.agentId,
				'agentType': $scope.selectedApp.type.agentType,
				'objectType': $scope.selectedApp.type.name,           		
				'objectKey': $scope.selectedApp.key,
				'operation' : 'loadGroupLogPatternAndLevel',
				'methodType': 'READ',
				'params': {
					instancesKey : selectedInstancesList
				}
			}).then(function (data) {
				$('#loaderdiv').hide(); 
				$scope.isDeployedLogLevel = true;
				$scope.groupDeployedLogPatterns = data;
				$scope.equalVariables =[];
				$scope.diffVariables = [];
				$scope.groupType = "Deployed Log Levels";
				$scope.equalTabTitle = "Same value patterns";
				$scope.differentTabTitle = "Different value patterns";
				for(var i=0 ; i<$scope.groupDeployedLogPatterns.length;i++){
					var gVariable = $scope.groupDeployedLogPatterns[i];
					if(gVariable.hasEqualValue)
					  $scope.equalVariables.push(gVariable);
					else
					  $scope.diffVariables.push(gVariable);
				}
				$('#groupLogLevel').modal({
			        show: true,
			        keyboard: false,
			        backdrop: 'static'
			    });
			}, function (failReason) {
				$('#loaderdiv').hide(); 
				$scope.notification = {
					       severity: 'error',
					       msg : failReason.message,
					       show: true
						};
			});	 
		}
		$scope.loadGroupRuntimeLoggers = function(){	
			$scope.configurationChangesFlags.isRuntimeLogLevelChanged = false;
			$scope.patternObj = {};
			$scope.patternObj.pattern="";
			$scope.patternObj.value="";
			var deferredRequest =getRuntimeLoggers();
	    	deferredRequest.then(function (data) {
	    		$scope.isDeployedLogLevel = false;
				$scope.groupType = "Runtime Log Levels";
				$scope.equalTabTitle = "Same value Loggers";
				$('#groupLogLevel').modal({
			        show: true,
			        keyboard: false,
			        backdrop: 'static'
			    });
	        }, function (failReason) {
	        	
	        });
		}
		$scope.applyRuntimeLogLevel = function(){
			var selectedInstancesList = getSelectedInstancesList(); 
			var patternMap = new Object();
			patternMap[$scope.patternObj.pattern]=$scope.patternObj.value;
			teaObjectService.invoke({
				'agentID'  : $scope.selectedApp.agentId,
				'agentType': $scope.selectedApp.type.agentType,
				'objectType': $scope.selectedApp.type.name,           		
				'objectKey': $scope.selectedApp.key,
				'operation' : 'applyLogPattern',
				'methodType': 'UPDATE',
				'params': {
					instances : selectedInstancesList,
					logPatternsAndLevel:patternMap
				}
			}).then(function (data) {
				getInstanceInfo();
				getApplicationInfo();
				getRuntimeLoggers();
				/*$scope.notification = {
		            severity: 'info',
		            msg : data,
		            show: true
				};*/
			}, function (failReason) {
				$scope.notification = {
			       severity: 'error',
			       msg : failReason.message,
			       show: true
				};
			});
		}
		$scope.addNewDeployedLogLevel = function(){	
			var selectedInstancesList = getSelectedInstancesList(); 
			var dummy = {deleted:false,deployedValue : null ,description:null,hasEqualValue: true,name:null,selectedInstances:selectedInstancesList,value:null};
			$scope.equalVariables.push(dummy);
		}
		$scope.removeDeployedLogLevel = function(index,isEqual){
			if(isEqual)
				$scope.equalVariables[index].deleted=true;
			else
				$scope.diffVariables[index].deleted=true;
		}
		$scope.saveDeployedLogLevels = function(){		
			$('#groupLogLevel').modal('hide');
			$('#loaderdiv').show(); 
			changeInstancesListForDiffValue();
			$scope.groupDeployedLogPatterns = $scope.equalVariables.concat($scope.diffVariables);
			var selectedInstancesList = getSelectedInstancesList(); 
			teaObjectService.invoke({
				'agentID'  : $scope.selectedApp.agentId,
				'agentType': $scope.selectedApp.type.agentType,
				'objectType': $scope.selectedApp.type.name,           		
				'objectKey': $scope.selectedApp.key,
				'operation' : 'saveGroupLogPatternAndLevel',
				'methodType': 'UPDATE',
				'params': {
					groupLogPatternsAndLevel : $scope.groupDeployedLogPatterns
				}
			}).then(function (data) {
				$('#loaderdiv').hide(); 
				getInstanceInfo();
				getApplicationInfo();
				/*$scope.notification = {
		            severity: 'info',
		            msg : data,
		            show: true
				};*/
			}, function (failReason) {
				$('#loaderdiv').hide(); 
				$scope.notification = {
			        severity: 'error',
			        msg : failReason.message,
			        show: true
				};
			});	 
		}
		function getInstances(){
			$('#loaderdiv').show(); 
			var defferedResp = getInstanceInfo();
			defferedResp.then(function (apps) {
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
		function getAllDeploymentVariables(){
			teaObjectService.invoke({
            	'agentID'  : $scope.selectedApp.agentId,
            	'agentType': $scope.selectedApp.type.agentType,
            	'objectType': $scope.selectedApp.type.name,
            	'objectKey': $scope.selectedApp.key,
            	'operation' : 'loadAllDeploymentVariables',
            	'methodType': 'READ'
        	}).then(function (data) {
        			
        	}, function (failReason) {
            });
		}
	
		getAllLogLevels();
		getInstances();
		getApplicationInfo();
		getAllDeploymentVariables();
	}, function (failReason) {
		$('#loaderdiv').hide(); 
    })	
}]);
