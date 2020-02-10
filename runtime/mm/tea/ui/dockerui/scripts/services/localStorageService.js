/**
 * Created by priyanka on 19/06/15
 */
beConfigModule.factory('StorageService',['$q','$timeout', 'teaObjectService', 'ReferenceObjectConfigService','teaAuthService',function($q,$timeout, teaObjectService, ReferenceObjectConfigService,teaAuthService){
	
		var beTopLevelObject, applicationObjects,inferenceAgentMethods,processingUnitMethods,ruleAttributes,actionDescriptors,entityHealthValues,loggedInUser,businessEventsAgentInfo;
		var masterHosts;	
		var enableAdditionalCharts=false;
	 	function loadTopLevelObject(){
	 		var defer = $q.defer();
            if (beTopLevelObject !== undefined) {
                defer.resolve(beTopLevelObject);
            }
            else {
            	teaObjectService.load({				
            		'agentType': 'BusinessEvents',
	            	'objectType': 'BusinessEvents'
            	}).then(function (topLevelObject) {	
            		beTopLevelObject = topLevelObject;            		
            		ReferenceObjectConfigService.addReferenceObject("TOP_LEVEL", topLevelObject);
            		defer.resolve(beTopLevelObject);            	
            	}, function (failReason) {            		
            		defer.reject('Error retrieving beTopLevelObject');
            	});
            }
            return defer.promise;
	    }
	 	
	 	function getMasterHosts(){
	 			var defer = $q.defer();
	 			teaObjectService.reference({				
			   		'agentType': 'BusinessEvents',
			        'objectType': 'BusinessEvents',
			        'objectKey' :'BusinessEvents',
			        'reference' : 'MasterHosts'
				}).then(function (hosts) {
					masterHosts=hosts;
					ReferenceObjectConfigService.addReferenceObject("beHosts", hosts);	
					defer.resolve(hosts);
				}, function (failReason) {
					defer.reject('Error retrieving be hosts');
				});
	 			return defer.promise;
		}
	   	function getApplications(){
	   		var defer = $q.defer();
	   		teaObjectService.reference({				
				'agentType': 'BusinessEvents',
	            'objectType': 'BusinessEvents',
				'objectKey' :'BusinessEvents',
	            'reference' : 'Applications'
			}).then(function (applications) {
				applicationObjects = applications;	
				ReferenceObjectConfigService.addReferenceObject("Application", applicationObjects);	
				if(applications.length > 0){
					$q.all([loadTopLevelObject(), getHost(), getMasterHosts(),getProcessingUnits(),getAgentClasses()]).then(function(result){
						defer.resolve(applicationObjects);
				    });
				}else
					defer.resolve(applicationObjects);
				
			}, function (failReason) {
				defer.reject('Error retrieving be applications');
			});
	   		return defer.promise;
	   	}
	   	
	   	function getHost() {	
	   		var defer = $q.defer();
			angular.forEach(applicationObjects, function(app) {
				teaObjectService.reference({
					'agentID'  : app.agentId,
		            'agentType': app.type.agentType,
	           		'objectType': app.type.name,           		
	           		'objectKey': app.key,
	           		'reference' : 'Hosts'           		
				}).then(function (Hosts) {
					ReferenceObjectConfigService.addReferenceObject(app.name +"_Host", Hosts);
					defer.resolve(Hosts);
				}, function (failReason) {
					defer.reject('Error retrieving be application hosts');
				});	 
			})
			 return defer.promise;
		}	
	   	function getInstances() {	
	   		var defer = $q.defer();
	   		angular.forEach(applicationObjects, function(app) {
				teaObjectService.reference({
					'agentID'  : app.agentId,
		            'agentType': app.type.agentType,
	           		'objectType': app.type.name,           		
	           		'objectKey': app.key,
	           		'reference' : 'ServiceInstances'           		
				}).then(function (instances) {					
					ReferenceObjectConfigService.addReferenceObject(app.name+"_Instances", instances);
					defer.resolve(instances);
				}, function (failReason) {
					defer.reject('Error retrieving be application instances');
				});	 
			})
			 return defer.promise;
	   	}	
		function getInstancesOfTeaObject(object) {	
	   		var defer = $q.defer();
			teaObjectService.reference({
				'agentID'  : object.agentId,
		        'agentType': object.type.agentType,
	           	'objectType': object.type.name,           		
	           	'objectKey': object.key,
	           	'reference' : 'ServiceInstances'           		
			}).then(function (instances) {					
				defer.resolve(instances);
			}, function (failReason) {
				defer.reject('Error retrieving instances');
			});	 
			return defer.promise;
	   	}	
	   	function getProcessingUnits() {	
	   		var defer = $q.defer();
	   		angular.forEach(applicationObjects, function(app) {
				teaObjectService.reference({
					'agentID'  : app.agentId,
		            'agentType': app.type.agentType,
	           		'objectType': app.type.name,           		
	           		'objectKey': app.key,
	           		'reference' : 'ProcessingUnits'           		
				}).then(function (processingUnits) {					
					ReferenceObjectConfigService.addReferenceObject(app.name+"_ProcessingUnits", processingUnits);
					defer.resolve(processingUnits);
					getProcessingUnitsAgents(processingUnits,app.name);
				}, function (failReason) {
					defer.reject('Error retrieving be application processing units');
				});	 
			})
			 return defer.promise;
	   	} 
	   	function getAgentClasses() {	
	   		var defer = $q.defer();
	   		angular.forEach(applicationObjects, function(app) {
				teaObjectService.reference({
					'agentID'  : app.agentId,
		            'agentType': app.type.agentType,
	           		'objectType': app.type.name,           		
	           		'objectKey': app.key,
	           		'reference' : 'ApplicationAgents'
				}).then(function (agents) {		
					ReferenceObjectConfigService.addReferenceObject(app.name+"_Agents", agents);
					defer.resolve(agents);					
				}, function (failReason) {
					defer.reject('Error retrieving be application processing units');
				});	 
			})
			 return defer.promise;
	   	} 
	   	function getProcessingUnitsAgents(processingUnits,appName) {		   		
	   		var defer = $q.defer();
	   		angular.forEach(processingUnits, function(pu) {
				teaObjectService.invoke({
					'agentID'  : pu.agentId,
		            'agentType': pu.type.agentType,
	           		'objectType': pu.type.name,           		
	           		'objectKey': pu.key,
	           		'operation' : 'getAgents',
	           		'methodType' : 'READ'
				}).then(function (processingUnitAgents) {
					for(var i=0 ; i<processingUnitAgents.length ; i++){
						processingUnitAgents[i].name = processingUnitAgents[i].agentName; 
					}
					ReferenceObjectConfigService.addReferenceObject(appName+"_"+pu.name+"_Agents", processingUnitAgents);				
					defer.resolve(processingUnitAgents);
				}, function (failReason) {
					defer.reject('Error retrieving be application processing units');
				});	 
			})			
			 return defer.promise;
	   	}	 
	   	function getEnableAdditionalChartsFlag() {	
	   		var defer = $q.defer();
			teaObjectService.invoke({				
				'agentType': 'BusinessEvents',
	            'objectType': 'BusinessEvents',
				'objectKey' :'BusinessEvents',
				'operation' : 'enableAdditionalViews',
	       		'methodType': 'READ',
	       		'params': {
	       		}          
			}).then(function (flag) {
				enableAdditionalCharts=flag;
				ReferenceObjectConfigService.setEnableAdditionalCharts(enableAdditionalCharts);
				 if(Storage)
					 localStorage.setItem('enableAdditionalCharts', JSON.stringify(enableAdditionalCharts));
				
        		defer.resolve(enableAdditionalCharts); 
			}, function (failReason) {
			});
			return defer.promise;
		}
	   
		function getEntityMethods(){
	   		var defer = $q.defer();
	   		teaObjectService.invoke({				
				'agentType': 'BusinessEvents',
	            'objectType': 'BusinessEvents',
				'objectKey' :'BusinessEvents',
				'operation' : 'getEntityMethods',
	       		'methodType': 'READ',
	       		'params': {
	       		}
			}).then(function (methods) {
				processingUnitMethods = methods.Process;
				inferenceAgentMethods = methods.Inference;
			}, function (failReason) {
				defer.reject('Error retrieving be applications');
			});
	   		return defer.promise;
	   	}
		function getRuleAttributesForAllEntities(){
	   		teaObjectService.invoke({				
				'agentType': 'BusinessEvents',
	            'objectType': 'BusinessEvents',
				'objectKey' :'BusinessEvents',
				'operation' : 'getRuleAttributesForAllEntities',
	       		'methodType': 'READ',
	       		'params': {
	       		}
			}).then(function (data) {
				ruleAttributes = data;
			}, function (failReason) {
			});
	   	}
		function getUniversalActionDescriptors(){
	   		teaObjectService.invoke({				
				'agentType': 'BusinessEvents',
	            'objectType': 'BusinessEvents',
				'objectKey' :'BusinessEvents',
				'operation' : 'getActionDescriptors',
	       		'methodType': 'READ',
	       		'params': {
	       		}
			}).then(function (data) {
				actionDescriptors = data;
			}, function (failReason) {
			});
	   	}
		function getEntityHealthValuesMap(){
	   		teaObjectService.invoke({				
				'agentType': 'BusinessEvents',
	            'objectType': 'BusinessEvents',
				'objectKey' :'BusinessEvents',
				'operation' : 'getEntityHealthMap',
	       		'methodType': 'READ',
	       		'params': {
	       		}
			}).then(function (data) {
				entityHealthValues = data;
			}, function (failReason) {
			});
	   	}
		function getLoggedInUserName(){
	   		teaObjectService.invoke({				
				'agentType': 'BusinessEvents',
	            'objectType': 'BusinessEvents',
				'objectKey' :'BusinessEvents',
				'operation' : 'getLoggedInUser',
	       		'methodType': 'READ',
	       		'params': {
	       		}
			}).then(function (data) {
				loggedInUser = data;
			}, function (failReason) {
			});
	   	}
		function getBusinessEventsAgentInfo(){
	   		teaObjectService.invoke({				
				'agentType': 'BusinessEvents',
	            'objectType': 'BusinessEvents',
				'objectKey' :'BusinessEvents',
				'operation' : 'getBusinessEventsAgentInfo',
	       		'methodType': 'READ',
	       		'params': {
	       		}
			}).then(function (data) {
				businessEventsAgentInfo = data;
			}, function (failReason) {
			});
	   	}	
	   	
	   	getApplications();
	   	loadTopLevelObject();	
	   	getEntityMethods();
	   	getMasterHosts();
	   	getPrivileges();
	   	getRuleAttributesForAllEntities();
	   	getUniversalActionDescriptors();
	   	getEntityHealthValuesMap();
	   	getLoggedInUserName();
	   	getBusinessEventsAgentInfo();
	   	getEnableAdditionalChartsFlag();
	   	return {
            getApplication :getApplications,
            getInstancesOfObject : getInstancesOfTeaObject,
            getInferenceMethods: function(){
                return inferenceAgentMethods;
            },
            getPuMethods: function(){
                return processingUnitMethods;
            },
            getRuleAttributes: function(){
                return ruleAttributes;
            },
            getActionDescriptors: function(){
                return angular.copy(actionDescriptors);
            },
            getEnityHealthValueMap : function(){
            	return entityHealthValues;
            },
            getLoggedInUser : function(){
            	return loggedInUser;
            },
            getBEAgentInfo:function(){
            	return businessEventsAgentInfo;
            },
            isEnableAdditionalCharts:function(){
            	return enableAdditionalCharts;
            }
        }
	   	function getPrivileges() {
	        teaAuthService.listPrivileges().then(function(privileges) {
	        	ReferenceObjectConfigService.addReferenceObject("PRIVILEGES", privileges);
	        }, function (failReason) {
				defer.reject('Error while privileges');
			});
	        
	    };
}]);