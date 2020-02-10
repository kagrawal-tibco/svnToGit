/**
 * @author:vasharma
 */
beConfigModule.directive('chartBlock',  ['$timeout','teaScopeDecorator', 'teaObjectService', 'ReferenceObjectConfigService', 'chartCreateFactory','MetricViewService',
                                     function ($timeout,teaScopeDecorator, teaObjectService, ReferenceObjectConfigService, chartCreateFactory,MetricViewService) {
    
		return {
	        	restrict: 'E',
	        	scope: {
	        		 'beEntityObject' : '=',
	     			 'isRefreshed'	 : '=',
	     			 'sectionId':'=',
	     			 'isSelf': '='
	        	},
	        	replace: true,
	        	templateUrl: 'partials/templates/directive/chartBlockTemplate.html',
	        	link: function(scope, el, attrs) {
	        		  console.log("Loading charts");
	        		  
	        		  if(scope.beEntityObject.type.name == "ServiceInstance")
	        		  //-------GETTING CHARTS CONFIGURATION FOR INSTANCE-------------
		     			scope.chartsByType=MetricViewService.getViewsForType(scope.sectionId,["instance"]);
		     		  //-------------------------------------------------------------------
	        		  else if(scope.beEntityObject.type.name == "Agent")
		     		  //-------GETTING CHARTS CONFIGURATION FOR AGENT-------------
		     			scope.chartsByType=MetricViewService.getViewsForType(scope.sectionId,["agent"]);
		     		  //-------------------------------------------------------------------
	        		  else if(scope.beEntityObject.type.name == "BusinessEvents")
		     		  //-------GETTING CHARTS CONFIGURATION FOR AGENT-------------
		     			scope.chartsByType=MetricViewService.getViewsForType(scope.sectionId,["beteaagent"]);
		     		  //-------------------------------------------------------------------
	        	}
		};
	}
 ]);