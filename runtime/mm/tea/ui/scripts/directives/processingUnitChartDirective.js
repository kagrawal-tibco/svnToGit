/**
 * Created by priyanka on 05/06/15
 */
beConfigModule.directive('processingunitdiv',  ['teaScopeDecorator', 'teaObjectService', 'ReferenceObjectConfigService', 'chartCreateFactory',
                                     function (teaScopeDecorator, teaObjectService, ReferenceObjectConfigService, chartCreateFactory) {
    
	return {
        	restrict: 'E',
        	scope: {},
        	replace: true,
        	templateUrl: 'partials/templates/directive/processingUnitChartDiv.html',
        	link: function(scope, el, attrs) {
        		teaScopeDecorator(scope);
        		scope.processingUnit = scope.$parent[attrs.beProcessingUnitObject];        		
        		if(angular.isDefined(scope.processingUnit)){        			  
        			getProcessingUnitInstancesSummary(scope.processingUnit);
    			}
        		function getProcessingUnitInstancesSummary(pu){
        			teaObjectService.reference({
        				'agentID'  : pu.agentId,
        	            'agentType': pu.type.agentType,
        	       		'objectType': pu.type.name,           		
        	       		'objectKey': pu.key,
        	       		'reference' : 'ServiceInstances'
        			}).then(function (instances) {
        				var okCnt=0 , warningCnt = 0, criticalCnt=0,stoppedCnt=0,otherCnt = 0,runningCnt=0;
        				for(var i = 0 ; i < instances.length;i++){
        					if(instances[i].status.state == 'Stopped'){
        						stoppedCnt++;
        					}else if(instances[i].status.state == 'Running'){
        						if(instances[i].config.healthStatus == 'ok'){
        							okCnt++;
        							runningCnt++;
        						}else if(instances[i].config.healthStatus == 'warning'){
        							warningCnt++;
        							runningCnt++;
        						}else if(instances[i].config.healthStatus == 'critical'){
        							criticalCnt++;
        							runningCnt++;
        						}
        					}else{
        						otherCnt++;
        					}
        				}
        				scope.chartoptions = chartCreateFactory.getChart();
        				if(instances==null || instances.length == 0)
        					scope.chartoptions.subtitle.text = 'Running : 0/0';
        				else{
        					scope.chartoptions.subtitle.text = 'Running : '+runningCnt+'/' +(instances.length);
            				scope.chartoptions.setData(
                                    [
                                        okCnt,
                                        warningCnt,
                                        criticalCnt,
                                        stoppedCnt,
                                        otherCnt
                                    ]
                             );
        				}
        				scope.chartoptions.chart.events.click =function(event) {
        					scope.showProcessingUnit();
        		        };
        			}, function (failReason) {
        				console.log("failed");
        			});	
        		}
        		scope.showProcessingUnit = function(){
        			scope.$parent.showProcessingUnit(scope.processingUnit);
        		}
        		
        	}
		};
	}
 ]);