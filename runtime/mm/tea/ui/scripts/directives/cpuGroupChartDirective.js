/**
 * Created by priyanka on 10/11/15
 */
beConfigModule.directive('cpuGroupDiv',  ['teaObjectService','chartCreateFactory','$q',
                                     function (teaObjectService,chartCreateFactory,$q) {
    
	return {
        	restrict: 'E',
        	scope: {
        		 'beInstanceObject': '='
        	},
        	replace: true,
        	templateUrl: 'partials/templates/directive/memoryAndCpuUsageDiv.html',
        	link: function(scope, el, attrs) {
        		 scope.$watch('beInstanceObject', function(newValue) {        			
        			 if(angular.isDefined(newValue)){
        				 if(newValue.status.state == "Running"){
        					 //var deferredRequest = getCPUSummary(newValue);
        					 //deferredRequest.then(function(memory){
        						// newValue.cpu=memory.cpuUsageInPercent;
        						 if(newValue.config.cpuUsage !== null){
        							var cpuUsage=0;
        							if(newValue.config.cpuUsage.cpuUsageInPercent == 0)
        								newValue.config.cpuUsage.cpuUsageInPercent = 0.01;
        							if( newValue.config.cpuUsage.cpuUsageInPercent  > 0 && newValue.config.cpuUsage.cpuUsageInPercent  < 6)
        								cpuUsage = 5;
        							else
        								cpuUsage = newValue.config.cpuUsage.cpuUsageInPercent;
                 					scope.chartoptions = chartCreateFactory.getCpuChartForGroup();  
                                    scope.chartoptions.setData(0,[{x:'' , y:100-cpuUsage}]);
                 					scope.chartoptions.setData(1,[{x:'' , y:cpuUsage}]);
                 					scope.chartoptions.setData(2,[{x:'' , y:newValue.config.cpuUsage.cpuUsageInPercent}]);
                 				}        				
        					 //});
        				 }
        			 }        				 
        		 });       		   
        		
        		function getCPUSummary(instanceObj){
        			var defer = $q.defer(); 
            		teaObjectService.invoke({
                		'agentID'  : instanceObj.agentId,
                	    'agentType': instanceObj.type.agentType,
                	    'objectType': instanceObj.type.name,           		
                	    'objectKey': instanceObj.key,
                	    'operation' : 'getCPUUsage',
                	    'methodType': 'READ'
                	}).then(function (memory) {            				
                		 defer.resolve(memory);
                	}, function (failReason) {
                		 defer.reject('Error retrieving memory usage');
                	});	   
            		return defer.promise;
        		}
        	}
		};
	}
 ]);