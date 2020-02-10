/**
 * Created by priyanka on 23/07/15
 */
beConfigModule.directive('instanceMemoryPoolDiv',  ['$timeout','teaScopeDecorator', 'teaObjectService', 'ReferenceObjectConfigService', 'chartCreateFactory',
                                     function ($timeout,teaScopeDecorator, teaObjectService, ReferenceObjectConfigService, chartCreateFactory) {
    
	return {
        	restrict: 'E',
        	scope: {
        		 'beInstanceObject': '=',
        		 'poolName' : '='
        	},
        	replace: true,
        	templateUrl: 'partials/templates/directive/instanceMemoryPoolDiv.html',
        	link: function(scope, el, attrs) {
        		 var timer;
        		 var poolName;
        		 var instanceObj;
        		 var newPoolName = false;        		
        		 var oldPoolName;
        		 
        		 scope.$watchCollection('[poolName, beInstanceObject]', function(newValues, oldValues){
      			   	if(angular.isDefined(newValues[0]) && angular.isDefined(newValues[1])){
      			   		scope.chartoptions = chartCreateFactory.getMemoryPoolChartForInstance(); 
      			   		if(angular.isDefined(oldPoolName) && oldPoolName!=newValues[0]){
      			   			newPoolName = true;
      			   			$timeout.cancel(timer);
      			   			poolName = newValues[0];
      			   			getMemoryPoolSummary(instanceObj);
      			   		}else{
      			   			poolName = newValues[0];
      			   			oldPoolName =  newValues[0];
			   				instanceObj = newValues[1];
			   				getMemoryPoolSummary(instanceObj);
      			   		}
      			   	} 
        		 });   
        		 
        		
        		function getMemoryPoolSummary(instanceObj){ 
        			if(instanceObj.status.state == "Running" || instanceObj.status.state == "running"){
        				var poller = function(){
            				teaObjectService.invoke({
                				'agentID'  : instanceObj.agentId,
                	            'agentType': instanceObj.type.agentType,
                	       		'objectType': instanceObj.type.name,           		
                	       		'objectKey': instanceObj.key,
                	       		'operation' : 'getMemeoryByPoolName',
                	       		'methodType': 'READ',
                	       		'params' :{
                	       			poolName : poolName
                	       		 }
                			}).then(function (memory) {          				
                				timer = $timeout(poller, 1000);
            					if(memory !== null){
                					var time = (new Date()).getTime()
                					var usedMemoryData = scope.chartoptions.getData(0);
                					var committedMemoryData = scope.chartoptions.getData(1);
                					if(newPoolName){
                						newPoolName=false;
                						usedMemoryData.length = 0;
                						committedMemoryData.length = 0;            						
                					}else{
                						 if (usedMemoryData.length > 10) {                                    
                                         	usedMemoryData.splice(0, 2);
                                         }
                                         if (committedMemoryData.length > 10) {                                    
                                         	committedMemoryData.splice(0, 2);
                                         }
                                         usedMemoryData.push({x:time , y:memory.used});
                                         committedMemoryData.push({x:time , y:memory.commited});
                					}                              
                                    scope.chartoptions.setData(0,usedMemoryData);
                                    scope.chartoptions.setData(1,committedMemoryData);
                				}             				
                			}, function (failReason) {
                				console.log("failed");
                			});	   
            			}
            			poller();
        			}
        		}
        		scope.$on("$destroy",function(event) {
                    $timeout.cancel(timer);
                });    
        	}
		};
	}
 ]);