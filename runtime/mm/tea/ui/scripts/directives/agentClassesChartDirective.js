/**
 * Created by priyanka on 15/07/15
 */
beConfigModule.directive('agentdiv',  ['teaScopeDecorator', 'teaObjectService', 'ReferenceObjectConfigService', 'chartCreateFactory',
                                     function (teaScopeDecorator, teaObjectService, ReferenceObjectConfigService, chartCreateFactory) {
    
	return {
        	restrict: 'E',
        	scope: {},
        	replace: true,
        	templateUrl: 'partials/templates/directive/agentClassesChartDiv.html',
        	link: function(scope, el, attrs) {
        		teaScopeDecorator(scope);
        		scope.agentClassObj = scope.$parent[attrs.beAgentClassObject];         		
        		if(angular.isDefined(scope.agentClassObj)){        			  
        			getAgentInstancesSummary(scope.agentClassObj);
    			}
        		function getAgentInstancesSummary(agent){
        			teaObjectService.reference({
        				'agentID'  : agent.agentId,
        	            'agentType': agent.type.agentType,
        	       		'objectType': agent.type.name,           		
        	       		'objectKey': agent.key,
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
        		            	scope.showAgent();
        		        };
        			}, function (failReason) {
        				console.log("failed");
        			});	
        		}
        		scope.showAgent = function(){
        			scope.$parent.showAgent(scope.agentClassObj);
        		}
        	}
		};
	}
 ]);