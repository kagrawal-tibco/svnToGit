/**
 * Created by priyanka on 25/05/15
 */
var highchart = angular.module("highchart", []);

highchart.directive('highChart',['HighChartRegistry',function(HighChartRegistry) {
	var chartIdPrefix = "highchart_";
	var chartCnt = 0;

	function getNewChartId(){
		return chartIdPrefix + ++chartCnt;
	}
	return {
	  	restrict: "A",
		scope: {},
		link: function(scope, el, attrs) {	
			var chartId;
			scope.$parent.$watch(attrs.chartObjectPath, function(){			 // to check if value is changed or not	
					var componentConfig = scope.$parent[attrs.chartObjectPath];
					if(angular.isDefined(componentConfig)){
						renderChart(componentConfig);	
					}
			});
			scope.$parent.$watch(attrs.chartObjectPath+ '.isUpdated', function(oldVal, newVal){	
				if(newVal !== oldVal){
					draw();	
				}
			});
			function draw(){
				var componentConfig = scope.$parent[attrs.chartObjectPath];
				if(HighChartRegistry.getChart(chartId) === undefined){
					renderChart(componentConfig);
				}else{
					for(var i=0;i<HighChartRegistry.getChart(chartId).series.length;i++)
					{
						HighChartRegistry.getChart(chartId).series[i].setData(componentConfig.series[i].data);
					}
					HighChartRegistry.getChart(chartId).xAxis[0].categories = componentConfig.xAxis.categories;
				}
			}
			scope.$on('$destroy', function(event){
				HighChartRegistry.deregisterChart(chartId);
			});
			function renderChart(componentConfig){	
				chartId = componentConfig.chartId;
				el.attr("id", chartId);
				componentConfig.chart.renderTo = el[0];
				var chart = new Highcharts.Chart(componentConfig);	
				if(! angular.isUndefined(chartId))
					HighChartRegistry.registerChart(chartId, chart);
			}
		}
  };
}]);

highchart.factory(
		'HighChartRegistry',
		[function(){
			var chartMap = {};

			return{
				registerChart: function(chartId, chartObject){
					chartMap[chartId] = chartObject;
				},

				deregisterChart: function(gridId){
					delete chartMap[gridId];
				},

				getChart: function(chartId){
					if(angular.isDefined(chartMap[chartId])){
						return chartMap[chartId];
					}
				}
			};
		}]
);
