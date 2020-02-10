/**
 * Created by Priyanka on 13/08/15
 */
beConfigModule.controller("instanceAgentController",['$scope','$location','$rootScope','$q','$timeout','teaLocation', 'teaObjectService','ReferenceObjectConfigService','StorageService','teaEventNotifications','utilService','MetricViewService',
                                       function ($scope,$location,$rootScope, $q,$timeout, teaLocation, teaObjectService,ReferenceObjectConfigService,StorageService,teaEventNotifications,utilService,MetricViewService){
		
	$('#loaderdiv').show(); 
	$rootScope.loadingComplete.then(function( apps ){
			$('#loaderdiv').hide(); 
			$scope.agent = ReferenceObjectConfigService.getSelectedInstanceAgent();
			$scope.inferenceAgentMethods = StorageService.getInferenceMethods();
			$scope.selectedInstance = ReferenceObjectConfigService.getSelectedInstance(); 
			$scope.check={};
			$scope.check.isSuspended = false;
			$scope.check.isResumed = true;
			
			/*Creating breadCrumbs*/
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
			ReferenceObjectConfigService.getBreadCrumbObject().push($scope.selectedInstance);
			ReferenceObjectConfigService.getBreadCrumbObject().push($scope.agent);
			if(Storage)
				localStorage.setItem("breadCrumbs", JSON.stringify(ReferenceObjectConfigService.getBreadCrumbObject()));
			$scope.breadCrumbs=ReferenceObjectConfigService.getBreadCrumbObject();
			
			$scope.viewType = "operations";
			$scope.setViewType = function(type){
			    	$scope.viewType = type;
			}
			$scope.isSet = function (viewType) {
			        return $scope.viewType === viewType;
			};
			$scope.getOperationInfo = function(method,group){
		    	$scope.operation = method;
		    	$scope.group = group;
		    	$scope.parameters = method.arg;
		    	$scope.result={};
		    	$scope.showResultPanel = false;			    	
			}
			$scope.invokeOperation = function(method){				
				$scope.parametersArray ={};
				for(var i=0 ; i< $scope.parameters.length ; i++){
					var param = $scope.parameters[i];					
					$scope.parametersArray[param.name] = param.defaultvalue;
				}
				$scope.result;
				teaObjectService.invoke({
					'agentID'  : $scope.agent.agentId,
					'agentType': $scope.agent.type.agentType,
					'objectType': $scope.agent.type.name,           		
					'objectKey': $scope.agent.key,
					'operation' : 'invoke',
					'methodType':'READ',
					'params': {                	
						entityName : "Inference",
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
					}
				});	 
			}
			$scope.dismiss =function(){
		   		$scope.notification.show = false;
		   		if(!utilService.getAgentReachable()){
					$location.path("/");
				}
		   	}
			$scope.resume = function(){
				$scope.check.isResumed = true;
				$scope.check.isSuspended = false;
				teaObjectService.invoke({
					'agentID'  : $scope.agent.agentId,
					'agentType': $scope.agent.type.agentType,
					'objectType': $scope.agent.type.name,           		
					'objectKey': $scope.agent.key,
					'operation' : 'resume',
					'methodType':'UPDATE'
				}).then(function (data) {
					$scope.reloadPage();
					/*$scope.notification = {
				            severity: 'info',
				            msg : data,
				            show: true
					}*/
				}, function (failReason) {					
					$scope.notification = {
			            severity: 'error',
			            msg : failReason['message'],
			            show: true
					}
				});	 
			}
			$scope.suspend = function(){
				$scope.check.isSuspended = true;
				$scope.check.isResumed = false;
				teaObjectService.invoke({
					'agentID'  : $scope.agent.agentId,
					'agentType': $scope.agent.type.agentType,
					'objectType': $scope.agent.type.name,           		
					'objectKey': $scope.agent.key,
					'operation' : 'suspend',
					'methodType':'UPDATE'
				}).then(function (data) {
					$scope.reloadPage();
					/*$scope.notification = {
				            severity: 'info',
				            msg : data,
				            show: true
					}*/
				}, function (failReason) {					
					$scope.notification = {
			            severity: 'error',
			            msg : failReason['message'],
			            show: true
					}
				});	 
			}
			var subscribe = true;
			$scope.notify = function (angularEvent, events) {
				console.log("changed");
				console.log("changed123");
			}
			$scope.$on('teaEventNotifications', $scope.notify);

			$scope.doSubscribe = function() {
			    if (subscribe) {
			        subscribe = false;
			        teaEventNotifications.addObjectIdToSubscription($scope.agent.agentId, window.tea.location.info.agentType, $scope.agent.type.name, "", [Event.STATUS_CHANGE]);
			        teaEventNotifications.subscribe();
			     }
			}
			
			$scope.getAgentInfo = function(){
				teaObjectService.invoke({
					'agentID'  : $scope.agent.agentId,
					'agentType': $scope.agent.type.agentType,
					'objectType': $scope.agent.type.name,           		
					'objectKey': $scope.agent.key,
					'operation' : 'getAgentInfo',
					'methodType':'READ'
				}).then(function (data) {
					$scope.agent.config.status = data.status;
				}, function (failReason) {					
					
				});	 
			}
			$scope.reloadPage = function(){
				$scope.getAgentInfo();
				$scope.charts=MetricViewService.getViewsForType(["agent"]);
			}
			$scope.$on('reloadInstancePage', function(e) {  
				$scope.$parent.showAppInstancesPage();            
		    });
			//Check user has permission or not
			$scope.hasPermission = function(permission){
				var privileges=ReferenceObjectConfigService.getReferenceObject('PRIVILEGES');
				return utilService.checkPermission(permission,privileges);
			};
	}, function (failReason) {
		$('#loaderdiv').hide(); 
    })
}]);
	