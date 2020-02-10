/**
 * @author:vasharma
 */
beConfigModule.directive('beChart',  ['$interval','teaScopeDecorator', 'teaObjectService', 'ReferenceObjectConfigService', 'chartCreateFactory','utilService',
                                                function ($interval,teaScopeDecorator, teaObjectService, ReferenceObjectConfigService, chartCreateFactory,utilService) {

	return {
		restrict: 'E',
		scope: {
			'beEntityObject' : '=',
			'beChartConfig'  : '=',
			'isRefreshed'	 : '=',
			'sectionId'		 : '=',
			'isSelf' 		 : '='
		},
		replace: true,
		templateUrl: 'partials/templates/directive/chartTemplate.html',
		link: function(scope, el, attrs) {
			var cancel;
			scope.dimension=scope.beChartConfig.timeDimension;
			var threshold=scope.beChartConfig.threshold;
			scope.isRefreshDone= false;
			var data = [];
			teaScopeDecorator(scope);

			scope.changeThresholdDimension= function(dimension){	
				scope.dimension = dimension;
				if(cancel)
					$interval.cancel(cancel);
				if(scope.dimension==null||scope.dimension==undefined||scope.dimension=="")
					startPolling(scope.beChartConfig.timeDimension);
				else
					startPolling(scope.dimension);
			}
			scope.$watch('isRefreshed', function(newVal,oldVal){	
				if(newVal !== oldVal){
					scope.isRefreshDone= true;
					if(cancel)
						$interval.cancel(cancel);
					if(scope.dimension==null||scope.dimension==undefined||scope.dimension=="")
						startPolling(scope.beChartConfig.timeDimension);
					else
						startPolling(scope.dimension);
				}
			});
			function startPolling(dimLevel){
				scope.$watch('beEntityObject', function(entity) {        			
					if(angular.isDefined(entity)){
						if(scope.beChartConfig.poller[0].pollingInterval<0)
							getChartData(entity,dimLevel);
						else {
							//Initial render call before scheduling the poller
							getChartData(entity,dimLevel);
							cancel=$interval(function(){getChartData(entity,dimLevel)}, (scope.beChartConfig.poller[0].pollingInterval*1000));
						}
					}
				});
			}
			
			function getChartData(entity,dimLevel){
				if((entity.status!=null&&entity.status.state == "Running")||(entity.config!=null&&entity.config.status == "Running")){
					
					teaObjectService.invoke({
						'agentID'  : entity.agentId,
						'agentType': entity.type.agentType,
						'objectType': entity.type.name,           		
						'objectKey': entity.key,
						'operation' : 'getChartData',
						'methodType':'READ',
						'params': {    
							sectionId:scope.sectionId,
							chartId : scope.beChartConfig.id,
							dimLevel  : dimLevel,
							threshold : threshold,
							appName   : scope.isSelf==false?ReferenceObjectConfigService.getSelectedApp().key:"BusinessEvents"
						}
					}).then(function (chart) {
						
						if(chart !== null){
							
							var chartPlotData=chart.queryData;
							
							if(chart.queryData !== null){
								if(!scope.chartoptions)
									scope.chartoptions = chartCreateFactory.getChartOptions(chart.view); 
								var time = (new Date()).getTime();
								var xData="";
								var yData="";
								for(var i=0;i<chart.view.series.length;i++)
								{
									if(chart.view.isBucketed || chart.view.chartType == 'column')
										data = [];
									else
										data = scope.chartoptions.getData(i);
									if(chartPlotData[chart.view.series[i].name]!=undefined && chartPlotData[chart.view.series[i].name] != null && chartPlotData[chart.view.series[i].name].length !=0){
										for(var j=0;j<chartPlotData[chart.view.series[i].name].length;j++){
											if(chart.view.xconfig.type !='time'){
												xData=chartPlotData[chart.view.series[i].name][j][chart.view.series[i].xattribute];
												yData=chartPlotData[chart.view.series[i].name][j][chart.view.series[i].yattribute];
												scope.chartoptions.xAxis.categories.push(xData);
												if(chart.view.yconfig.type == 'percent' && chart.view.yconfig.precision > 0)
													yData = parseFloat(yData.toFixed(chart.view.yconfig.precision));
												if(yData == undefined || yData == '' || yData == null)
													yData = 0;
												data.push(yData);
											}
											else{
												xData=chartPlotData[chart.view.series[i].name][j][chart.view.series[i].xattribute];
												yData=chartPlotData[chart.view.series[i].name][j][chart.view.series[i].yattribute];
												if(yData!=undefined && chart.view.yconfig.type == 'percent' && chart.view.yconfig.precision > 0)
													yData = parseFloat(yData.toFixed(chart.view.yconfig.precision));
												if(yData == undefined || yData == '' || yData == null)
													yData = 0;
												data.push({x:xData , y:yData});
											}
										}
									}
									if(!chart.view.isBucketed && chart.view.chartType == 'spline' ){
										if(chart.view.maxDataPoints!=undefined && data.length > chart.view.maxDataPoints){
											data.splice(0, data.length-chart.view.maxDataPoints);
										}
									}
									scope.chartoptions.setData(i,data);
								}
							}
						}
					}, function (failReason) {
						console.log("Error occurred while getting chart data : "+failReason);
					});
				}
			}
			
			
			//Initial polling call
			startPolling(scope.beChartConfig.timeDimension);
			
			scope.$on("$destroy",function(event) {
				if(cancel)
					$interval.cancel(cancel);
			});

		}
	};
}
]);