/**
 * Created by priyanka on 08/11/15
 */
beConfigModule.directive('memoryGroupDiv',  ['teaObjectService','chartCreateFactory','$q',
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
        					// var deferredRequest = getMemorySummary(newValue);
        					// deferredRequest.then(function(memory){
        						 
        						 if(newValue.config.memoryUsage !== null){
                 					scope.chartoptions = chartCreateFactory.getMemoryChartForGroup();  
                                    scope.chartoptions.setData(0,[{x:'' , y:100-newValue.config.memoryUsage.percentUsed}]);
                 					scope.chartoptions.setData(1,[{x:'' , y:newValue.config.memoryUsage.percentUsed}]);
                 				}        				
        					// });
        				 }
        			 }        				 
        		 });       		   
        		
        		function getMemorySummary(instanceObj){
        			var defer = $q.defer(); 
            		teaObjectService.invoke({
                		'agentID'  : instanceObj.agentId,
                	    'agentType': instanceObj.type.agentType,
                	    'objectType': instanceObj.type.name,           		
                	    'objectKey': instanceObj.key,
                	    'operation' : 'getMemoryUsage',
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