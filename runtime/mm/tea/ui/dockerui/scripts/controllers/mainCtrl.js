/**
 * Created by Priyanka on 18/3/15
 */
var beConfigModule = angular.module('BusinessEvents', ['tea.services', 'tea.directives', 'tea.filters']);

beConfigModule.factory('beResponseInterceptor', function ($q ,$rootScope,$location,utilService) {
    return {
        response: function (response) {
        	utilService.setAgentRechable(true);
            return response;
        },
        responseError: function (response) {
        	if(response.status==500 && response.data.message=="Operation unauthorized" ){
        		utilService.setAgentRechable(true);
        		response.data.message = "You are unauthorized to perform this action.";
        		return $q.reject(response);
        	}else if((response.status==500 && (response.data.message!=null && ( response.data.message.indexOf("Connection refused") > -1 || response.data.message.indexOf("Cannot contact agent url") > -1 ))) || response.status==404){
        		response.data.message = "BE Agent not reachable";
        		utilService.setAgentRechable(false);
        		return $q.reject(response);
        	}else{
        		utilService.setAgentRechable(true);
        		return $q.reject(response);
        	}
        }
    };
});

beConfigModule.config(function ($httpProvider) {
    $httpProvider.interceptors.push('beResponseInterceptor');
});

beConfigModule.directive('ngFileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function (scope, element, attrs) {
            var model = $parse(attrs.ngFileModel);
            var isMultiple = attrs.multiple;
            var modelSetter = model.assign;
            element.bind('change', function () {
                var values = [];
                angular.forEach(element[0].files, function (item) {
                    var value = {
                       // File Name 
                        name: item.name,
                        //File Size 
                        size: item.size,
                        //File URL to view 
                        url: URL.createObjectURL(item),
                        // File Input Value 
                        _file: item
                    };
                    values.push(value);
                });
                
                if(!scope.$$phase){
                scope.$apply(function () {
                    if (isMultiple) {
                        modelSetter(scope, values);
                    } else {
                        modelSetter(scope, values[0]);
                    }
                    var files='';
                    
                    for(var i=0;i<values.length;i++){
                    	files+=values[i].name.replace(/^.*[\\\/]/, '');
                    	if(i<values.length-1){
                    		files+=',';
                    	}
                    }
                    $('#jarFiles').val(files);
                });
                }
                else{
                	if (isMultiple) {
                        modelSetter(scope, values);
                    } else {
                        modelSetter(scope, values[0]);
                    }
                    var files='';
                    
                    for(var i=0;i<values.length;i++){
                    	files+=values[i].name.replace(/^.*[\\\/]/, '');
                    	if(i<values.length-1){
                    		files+=',';
                    	}
                    }
                    $('#jarFiles').val(files);
                }
            });
        }
    };
}]);
beConfigModule.controller("IndexCtrl",['$scope', '$rootScope','$q','$timeout','$location', 'teaLocation' ,'teaObjectService', 'teaScopeDecorator','teaEventNotifications','chartCreateFactory','ReferenceObjectConfigService','StorageService','utilService','teaAuthService','MetricViewService',
                                       function ($scope,$rootScope,$q,$timeout,$location, teaLocation, teaObjectService, teaScopeDecorator ,teaEventNotifications,chartCreateFactory,ReferenceObjectConfigService,StorageService,utilService,teaAuthService,MetricViewService){
	
	   teaScopeDecorator($scope);	
	   var subscribe = true;
	   //Initializing charts config
	   MetricViewService.getAllViews();
	   MetricViewService.getSelfViews();
	   //---------------------------
	   $scope.appObject;
	   $scope.leftNavObject =ReferenceObjectConfigService.getSelectedNavObject();
	   if(ReferenceObjectConfigService.getSelectedApp() != undefined)
		   $scope.selectedAppName = ReferenceObjectConfigService.getSelectedApp().name;
	   var dfd = $q.defer();
	   $rootScope.loadingComplete = dfd.promise;       
	   function populateAllApplications() {
	        var deferredRequest = StorageService.getApplication();
	        deferredRequest.then(function (apps) {
	        	if(apps.length ==0){
	        		ReferenceObjectConfigService.setSelectedApp(undefined);
	        		dfd.resolve(apps); 
	        	}else{
	        		ReferenceObjectConfigService.setAppObjects(apps);
	            	$scope.appObject =apps;
	            	dfd.resolve(apps); 
	        	}
	        }, function (failReason) {
	        	ReferenceObjectConfigService.setSelectedApp(undefined);
        		utilService.setAgentRechable(false);
        		$scope.notification = {
			            severity: 'error',
			            msg :'BE Agent not reachable',
			            show: true
			    };
        		dfd.reject('Error retrieving be applications');
	        });
	   	}   
	 
	   populateAllApplications();
	   $scope.dismiss =function(){
			$scope.notification.show = false;
			if(!utilService.getAgentReachable()){
				$location.path("/");
			}
	   }
	   $scope.showDashboardApplications = function () {
         var BETopLevelObject = ReferenceObjectConfigService.getReferenceObject("TOP_LEVEL");
         $scope.goto(BETopLevelObject);      
       }
	   if (ReferenceObjectConfigService.getSelectedApp() != undefined)
			$scope.selectedApp = ReferenceObjectConfigService.getSelectedApp();
	   
		$scope.$watch('object', function (object) {
	        if (object) {
	            $scope.addToFav(object);
	            $scope.doSubscribe();
	        }
	        var totalAlertCount = localStorage.getItem("prevAlerts");
	        if(totalAlertCount > 0)
	         $("#alertCount").html("(" + totalAlertCount + ")");
	    });
        
	    $scope.notify = function (angularEvent, events) {
	    	if(events[0].type == "CHILDREN_CHANGE" && events[0].data.alertCount > 0){
	    	    $("#alertCount").html("(" + events[0].data.alertCount + ")");
				localStorage.setItem("prevAlerts", events[0].data.alertCount);
	    	}
	    }
	    $scope.$on('teaEventNotifications', $scope.notify);

	    $scope.doSubscribe = function() {
	        if (subscribe && typeof $scope.selectedApp!=='undefined' && null!=$scope.selectedApp) {
	            subscribe = false;
	                teaEventNotifications.addObjectIdToSubscription($scope.selectedApp.agentId,  $scope.selectedApp.type.agentType,  $scope.selectedApp.type.name, "", [Event.CHILDREN_CHANGE]);
	            	teaEventNotifications.subscribe();
	            
	        }
	    }
	   
	   $scope.showDashboardHosts = function () {  // top level machines page	 
			   teaLocation.goto({
		        	 viewName:"beMachinesView",	     	 
		             agentType: "BusinessEvents",
		             objectType: "BusinessEvents",
		             query: {
		            	 ok: "BusinessEvents",
	                     agentId:""
		             }	             
		         }); 
	   }
	   $scope.showAgentStats = function () {  // tea agent monitoring page
		   teaLocation.goto({
	        	 viewName:"selfMonitoringView",	     	 
	             agentType: "BusinessEvents",
	             objectType: "BusinessEvents",
	             query: {
	            	 ok: "BusinessEvents",
                     agentId:""
	             }	             
	         }); 
	   }
	   function setSelectedApp(appName){
		   var appObject =ReferenceObjectConfigService.getReferenceObject("Application");
		   var topLevelObject =ReferenceObjectConfigService.getReferenceObject("TOP_LEVEL");
		   for(var i=0;i<appObject.length;i++) {
			 var appMember =  appObject[i];
			 if (angular.equals(appMember.name, appName)) {
				 ReferenceObjectConfigService.setSelectedApp(appMember);
				 ReferenceObjectConfigService.selectedAppName = appMember.name;
				 if(Storage)
					 localStorage.setItem('selectedApp', JSON.stringify(appMember));	
			 }
		   }
	   }
	   $scope.showInstanceHost = function(hostName) {
		   ReferenceObjectConfigService.setBreadCrumbObject();
		   var hosts = ReferenceObjectConfigService.getReferenceObject(ReferenceObjectConfigService.getSelectedApp().name+"_Host");
		   for(var i=0;i<hosts.length;i++) {
			 var host = hosts[i];
			 if (angular.equals(host.name, hostName)) {
				 ReferenceObjectConfigService.getBreadCrumbObject().push(host);
				 $scope.showHost(host);
			 }
		   }
	   }
	   $scope.selectInstanceAgent =function(agent){
			 ReferenceObjectConfigService.setSelectedInstanceAgent(agent);
			 var instance=ReferenceObjectConfigService.getSelectedInstance();
			 if(Storage)
				 localStorage.setItem("runningAgent",JSON.stringify(agent));
			 teaLocation.goto({
	        	 viewName:"instanceAgent",	     	 
	             agentType: "BusinessEvents",
	             objectType: "ServiceInstance",
	             query: {
	                     ok: instance.key,
	                     agentId: instance.agentId
	             }
	         });      
		}
	   $scope.showAgent = function(agent) {
		    ReferenceObjectConfigService.setSelectedNavObject("agent classes");
		    if(Storage){
		    	localStorage.setItem("navObject", JSON.stringify("agent classes"));
				localStorage.setItem("selectedProcessingUnitAgent", JSON.stringify(agent));
		    }
	    	ReferenceObjectConfigService.setSelectedProcessingUnitAgent(agent);	
	    	$timeout(function() {
	    		$scope.goto(agent);
	      	});
		}
	   $scope.showPUAgent = function(agent) {		   
		    var appObject = ReferenceObjectConfigService.getSelectedApp();
	    	var agents = ReferenceObjectConfigService.getReferenceObject(appObject.name + "_Agents");
	    	for(var i=0;i<agents.length;i++) {
   			 	var agentMember =  agents[i];
   			 	if (angular.equals(agentMember.name, agent.agentName)) {
   			 		ReferenceObjectConfigService.setSelectedProcessingUnitAgent(agentMember);
   			 		agent = agentMember;
   			 	}
   		 	}		    		    	
	    	$scope.showAgent(agent);
	    }
	   $scope.showHost = function(host){
		   	 ReferenceObjectConfigService.setSelectedNavObject("machines"); 
		   	 if(Storage){
		   		localStorage.setItem("navObject",JSON.stringify("machines"));
			    localStorage.setItem("selectedHost",JSON.stringify(host));
		   	 }
			 ReferenceObjectConfigService.setSelectedHost(host);
			 var appObject = ReferenceObjectConfigService.getSelectedApp();
			 $timeout(function() {
				 $scope.goto(host);
		     });
		}
	   $scope.showMasterHost = function(host){
		   	 if(Storage){
			    localStorage.setItem("selectedMasterHost",JSON.stringify(host));
		   	 }
			 ReferenceObjectConfigService.setSelectedMasterHost(host);
			 $scope.goto(host);
	   }
	   $scope.showMasterHostLink = function(host){
		   $scope.masterHosts=ReferenceObjectConfigService.getReferenceObject("beHosts");
		   var referredMasterHost;
		   for(var i=0 ; i <  $scope.masterHosts.length ; i++){
			   if ($scope.masterHosts[i].name == host.name){
				   referredMasterHost = $scope.masterHosts[i];
				   break;
			   }
		   }
		   $scope.showMasterHost(referredMasterHost);
	   }
	   $scope.showInstanceFromMasterHost = function(appName,instanceName){
		   setSelectedApp(appName);
		   var selectedInstance;
		   $scope.selectedApp = ReferenceObjectConfigService.getSelectedApp(); 
		   ReferenceObjectConfigService.setSelectedNavObject("instances");
      	   if(Storage)
      		   localStorage.setItem("navObject",JSON.stringify("instances"));
		   teaObjectService.reference({
               	'agentID'  : $scope.selectedApp.agentId,
               	'agentType': $scope.selectedApp.type.agentType,
               	'objectType': $scope.selectedApp.type.name,
               	'objectKey': $scope.selectedApp.key,
               	'reference' : 'ServiceInstances'
           }).then(function (instances) {
           		for(var i=0;i<instances.length;i++) {
           			if(instances[i].name == instanceName){
           				selectedInstance = instances[i];
           				break;
           			}
           		}
           		$scope.showInstance(selectedInstance);
           }, function (failReason) {
           });
	   }
	   $scope.showInstanceProcessingUnit = function(puName) {
		    ReferenceObjectConfigService.setBreadCrumbObject();
			var pus = ReferenceObjectConfigService.getReferenceObject(ReferenceObjectConfigService.getSelectedApp().name+"_ProcessingUnits");
			for(var i=0;i<pus.length;i++) {
				 var pu = pus[i];
				 if (angular.equals(pu.name, puName)) {
					 ReferenceObjectConfigService.getBreadCrumbObject().push(pu);
					 $scope.showProcessingUnit(pu);
				 }
			 }
	   }
	   $scope.showProcessingUnit = function(processingUnit){
		   	ReferenceObjectConfigService.setSelectedNavObject("processing units"); 
		   	if(Storage){
		   		localStorage.setItem("navObject", JSON.stringify("processing units"));
			   	localStorage.setItem("selectedProcessingUnit",JSON.stringify(processingUnit));
		   	}
			ReferenceObjectConfigService.setSelectedProcessingUnit(processingUnit);
			var appObject = ReferenceObjectConfigService.getSelectedApp();
			$timeout(function() {
				$scope.goto(processingUnit);
		    });
		}
       $scope.showMachines = function(appName){
    	   setSelectedApp(appName);
    	   $scope.showMachinesPage();
       }
       $scope.showAppInstances = function(appName){
    	   setSelectedApp(appName);
    	   $scope.showAppInstancesPage();        
       }
       $scope.reloadPage = function(appName){
    	   setSelectedApp(appName);
    	   var leftNavObject =ReferenceObjectConfigService.getSelectedNavObject();
    	   if(leftNavObject == 'instances'){
    		   $scope.$broadcast ('reloadInstancePage');
    	   } // window.location.reload();
    	   else
    	       $scope.showAppInstancesPage(); 
       }
 	   $scope.showInstance = function(instance){
 		   ReferenceObjectConfigService.setSelectedInstance(instance); 	
 		   var appObject = ReferenceObjectConfigService.getSelectedApp();
 		   if(Storage)
 			   localStorage.setItem("selectedInstance",JSON.stringify(instance));
 		   $scope.goto(instance);
	   }
       $scope.showMachinesPage = function(){ 
    	   teaLocation.goto({
	        	 viewName:"appMachines",	     	 
	             agentType: "BusinessEvents",
	             objectType: "Application",
	             query: {
	                     ok: ReferenceObjectConfigService.getSelectedApp().key,
	                     agentId: ReferenceObjectConfigService.getSelectedApp().agentId
	             }
	         });   
    	   ReferenceObjectConfigService.setSelectedNavObject("machines");
    	   if(Storage)
    		   localStorage.setItem("navObject",JSON.stringify("machines"));
       }
       $scope.showAppInstancesPage = function(){   
      	   var instances=ReferenceObjectConfigService.getReferenceObject(ReferenceObjectConfigService.getSelectedApp().name+"_Instances");
      	   $timeout(function() {
      		 $scope.goto(ReferenceObjectConfigService.getSelectedApp());
      	   });
      	   ReferenceObjectConfigService.setSelectedNavObject("instances");
      	   if(Storage)
      		   localStorage.setItem("navObject",JSON.stringify("instances"));
        }
       $scope.showAppProcessingUnitsPage = function(){ 
      	   teaLocation.goto({
        	 viewName:"appProcessingUnit",	     	 
             agentType: "BusinessEvents",
             objectType: "Application",
             query: {
                 ok: ReferenceObjectConfigService.getSelectedApp().key,
                 agentId: ReferenceObjectConfigService.getSelectedApp().agentId
             }
      	   }); 
      	   ReferenceObjectConfigService.setSelectedNavObject("processing units");
      		if(Storage)
      			localStorage.setItem("navObject",JSON.stringify("processing units"));
        }	   
        $scope.showAppAgentClassePage = function(){ 
        	 teaLocation.goto({
            	 viewName:"applicationAgents",	     	 
                 agentType: "BusinessEvents",
                 objectType: "Application",
                 query: {
                     ok: ReferenceObjectConfigService.getSelectedApp().key,
                     agentId: ReferenceObjectConfigService.getSelectedApp().agentId
                 }
          	   }); 
        	 ReferenceObjectConfigService.setSelectedNavObject("agent classes");
        	 if(Storage)
        		localStorage.setItem("navObject",JSON.stringify("agent classes"));
        }
        $scope.showHealthRulesPage = function(){ 
       	 	teaLocation.goto({
           	 	viewName:"healthRules",	     	 
                agentType: "BusinessEvents",
                objectType: "Application",
                query: {
                    ok: ReferenceObjectConfigService.getSelectedApp().key,
                    agentId: ReferenceObjectConfigService.getSelectedApp().agentId
                }
         	}); 
       	 	ReferenceObjectConfigService.setSelectedNavObject("health rules");
       		if(Storage)
       	 	 localStorage.setItem("navObject",JSON.stringify("health rules"));
        }
        $scope.showAlertsPage = function(){ 
       	 	teaLocation.goto({
           	 	viewName:"alerts",	     	 
                agentType: "BusinessEvents",
                objectType: "Application",
                query: {
                    ok: ReferenceObjectConfigService.getSelectedApp().key,
                    agentId: ReferenceObjectConfigService.getSelectedApp().agentId
                }
         	}); 
       	    localStorage.setItem("prevAlerts", 0);
       	    $("#alertCount").html("");
       	 	ReferenceObjectConfigService.setSelectedNavObject("alert management");
       		if(Storage)
       	 	 localStorage.setItem("navObject",JSON.stringify("alert management"));
        }
        $scope.showProfilesPage = function(){ 
       	 	teaLocation.goto({
           	 	viewName:"profiles",	     	 
                agentType: "BusinessEvents",
                objectType: "Application",
                query: {
                    ok: ReferenceObjectConfigService.getSelectedApp().key,
                    agentId: ReferenceObjectConfigService.getSelectedApp().agentId
                }
         	}); 
       	 	ReferenceObjectConfigService.setSelectedNavObject("profile management");
       		if(Storage)
       	 	 localStorage.setItem("navObject",JSON.stringify("profile management"));
        }
        $scope.breadCrumbsRouting =function(obj){
        	if(obj.type.name === "ProcessingUnit")
        		$scope.showProcessingUnit(obj);
        	else if(obj.type.name === "ServiceInstance")
        		$scope.showInstance(obj);
        	else if(obj.type.name === "ProcessingUnitAgent")
        		$scope.showAgent(obj);
        	else if(obj.type.name === "Host")
        		 $scope.showHost(obj);
        	else if(obj.type.name === "instances")
       		 $scope.showAppInstancesPage();
        	else if(obj.type.name === "machines")
       		 $scope.showMachinesPage();
        	else if(obj.type.name === "processing units")
       		 $scope.showAppProcessingUnitsPage();
        	else if(obj.type.name === "agent classes")
       		 $scope.showAppAgentClassePage();
        	else if(obj.type.name === "Machine Management")
        	 $scope.showDashboardHosts();
        }
        $scope.showAboutWindowForBETea = function(){
        	$scope.copyrights = StorageService.getBEAgentInfo()['Copyrights'];
        	$scope.version = StorageService.getBEAgentInfo()['Version'];
        	$('#aboutBETEA').modal({
    	        show: true,
    	        keyboard: false,
    	        backdrop: 'static'
    	    });
        }
        $scope.openHelpPageForBETea = function(){
        	var url = StorageService.getBEAgentInfo()['DocUrl'];
        	var win = window.open(url, '_blank');
        	win.focus();
        }
}]);
beConfigModule.config(['$compileProvider',
            function ($compileProvider) {
                $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|tel|file|blob):/);
}]);
/*beConfigModule.directive('prettyprint', function() {
    return {
        restrict: 'C',
        link: function postLink(scope, element, attrs) {
        		scope.$parent.$watch(attrs.object, function(){			 
        			data = scope.$parent[attrs.object];
        			if(angular.isDefined(data)){
        				  element.text(vkbeautify.xml(data, 4));
        			}
        		});
        }
    };
});*/
