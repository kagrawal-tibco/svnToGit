/**0
 * @author: vasharma
 */
beConfigModule.factory('MetricViewService',['$q','$timeout', 'teaObjectService', 'ReferenceObjectConfigService',function($q,$timeout, teaObjectService, ReferenceObjectConfigService){

	var MetricViewService =
	{		charts:{},
			tables:{},

			getAllViews : function()
			{	
				if(ReferenceObjectConfigService.getChartsConfig()==undefined)
				{
					var defer = $q.defer();

					teaObjectService.invoke({				
						'agentType': 'BusinessEvents',
						'objectType': 'BusinessEvents',
						'objectKey' :'BusinessEvents',
						'operation' : 'getAllChartsConfig',
						'methodType': 'READ',
						'params' :
						{
						}
					}).then( function (viewsConfig) {
						ReferenceObjectConfigService.setChartsConfig(viewsConfig);
					}, function (failReason) {
						defer.reject('Error retrieving charts config :' +failReason);
					});
					return defer.promise;
				}
			},
			getSelfViews : function()
			{	
				if(ReferenceObjectConfigService.getSelfChartsConfig()==undefined)
				{
					var defer = $q.defer();

					teaObjectService.invoke({				
						'agentType': 'BusinessEvents',
						'objectType': 'BusinessEvents',
						'objectKey' :'BusinessEvents',
						'operation' : 'getSelfChartsConfig',
						'methodType': 'READ',
						'params' :
						{
						}
					}).then( function (viewsConfig) {
						ReferenceObjectConfigService.setSelfChartsConfig(viewsConfig);
					}, function (failReason) {
						defer.reject('Error retrieving charts config :' +failReason);
					});
					return defer.promise;
				}
			},
			getViewsForType : function(sectionId,types)
			{	var sections=[];
				if(types!=null && types!=undefined && types.indexOf("beteaagent") == -1)
					sections=ReferenceObjectConfigService.getChartsConfig();
				else
					sections=ReferenceObjectConfigService.getSelfChartsConfig();
				
				var chartsRegistery=[];
				for(var i=0 ; i < sections.length ; i++){
					if(sectionId== sections[i].sectionId){
						chartsRegistery = sections[i].chart;
					}
				}
				var filteredViews=[];
				for(var i=0;i<types.length;i++)
				{
					for (var j=0 ;j<chartsRegistery.length;j++) {
						if (types[i] == chartsRegistery[j].entity) {
								filteredViews.push(chartsRegistery[j]);
						}
					}
				}
				return filteredViews;
			},
	}
	return MetricViewService;
}]);