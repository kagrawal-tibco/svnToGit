/**
 * Created by priyanka on 04/03/16
 */
beConfigModule.directive('chartNavBlock',  ['$timeout','teaScopeDecorator', 'teaObjectService', 'ReferenceObjectConfigService', 'chartCreateFactory','StorageService',
                                     function ($timeout,teaScopeDecorator, teaObjectService, ReferenceObjectConfigService, chartCreateFactory, StorageService) {
    
		return {
	        	restrict: 'E',
	        	scope: {
	        		 'chartSections' : '=',
	        		 'instanceAgentsWithInfo':'=',
	        		 'entity':'=',
	        		 'isRefreshed'	 : '=',
	        		 'gcDetails'	:'=',
	        		 'memeoryPoolsList':'=',
	        		 'isSelf' :'='
	        	},
	        	replace: true,
	        	templateUrl: 'partials/templates/directive/chartNavBlockTemplate.html',
	        	link: function(scope, el, attrs) {
	        		scope.isEnableAdditionalCharts=StorageService.isEnableAdditionalCharts();
	        		console.log("Loading sections");
	        		scope.selectedSectionType="Five Minute Statistics";
	        		if(scope.isEnableAdditionalCharts){
		        		scope.$watch('memeoryPoolsList', function(newVal, oldVal){
		        		    if(newVal!= undefined && newVal.length>0)
		        		    	scope.memoryPoolName = scope.memeoryPoolsList[0];
		        		});
	        		}
	        		
	        		scope.capitalizeFirstLetter = function(string) {
	    			    return string.charAt(0).toUpperCase() + string.slice(1).toLowerCase();
	    			}
	        		scope.setSelectedSection = function(type){
	        			scope.selectedSectionType = type;
	        		}
	        		scope.isSet=function(type){
	        			if(scope.selectedSectionType == type)
	        				return true;
	        			else
	        				return false;
	        		}
	        	}
		};
	}
 ]);