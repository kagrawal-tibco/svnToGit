/**
 * Created by priyanka on 25/05/15
 */
beConfigModule.directive('appdiv',  ['teaScopeDecorator', 'teaObjectService', 'ReferenceObjectConfigService', 'chartCreateFactory',
                                     function (teaScopeDecorator, teaObjectService, ReferenceObjectConfigService, chartCreateFactory) {
    
	return {
        	restrict: 'E',
        	scope: {},
        	replace: true,
        	templateUrl: 'partials/templates/directive/applicationDiv.html',
        	link: function(scope, el, attrs) {
        		teaScopeDecorator(scope);
        		scope.appObj = scope.$parent[attrs.beAppObject];        		
        		if(angular.isDefined(scope.appObj)){        			  
        			getAppInstancesSummary(scope.appObj);
    			}
        		function getAppInstancesSummary(app){
        			teaObjectService.reference({
        				'agentID'  : app.agentId,
        	            'agentType': app.type.agentType,
        	       		'objectType': app.type.name,           		
        	       		'objectKey': app.key,
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
        		            	scope.showInstances(app.name);
        		        };
        			}, function (failReason) {
        				console.log("failed");
        			});	
        		}
        		
        		scope.showInstances = function(appName){
        			scope.$parent.$parent.showAppInstances(appName);
        		}
        		scope.editApplication = function(appName){
        			scope.$parent.$parent.editApplication(appName);
        		}
        		scope.deleteApplication = function(app){
        			scope.$parent.$parent.deleteApplication(app);
        		}
        		scope.loadApplicationLevelCDD = function(app){
        			scope.$parent.$parent.loadApplicationLevelCDD(app);
        		}
        		scope.showHotDeployModal = function(app){
        			scope.$parent.$parent.showHotDeployModal(app);
        		}
        		scope.updateTRAandLog = function(app){
        			scope.$parent.$parent.updateTRAandLog(app);
        		}
        		scope.exportApplication = function(app){
        			scope.$parent.$parent.exportApplication(app);
        		}
        		scope.showAddProfileWizard = function(app){
        			scope.$parent.$parent.showAddProfileWizard(app);
        		}
        		scope.showEditProfileWizard = function(app){
        			scope.$parent.$parent.showEditProfileWizard(app);
        		}
        		scope.showCopyProfileWizard = function(app){
        			scope.$parent.$parent.showCopyProfileWizard(app);
        		}
        		scope.showDeleteProfileWizard = function(app){
        			scope.$parent.$parent.showDeleteProfileWizard(app);
        		}

        	}
		};
	}
 ]);