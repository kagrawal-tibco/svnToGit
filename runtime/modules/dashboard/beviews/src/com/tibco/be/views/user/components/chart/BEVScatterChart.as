package com.tibco.be.views.user.components.chart{
	
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.user.components.chart.series.BEVPlotSeries;
	import com.tibco.be.views.user.components.chart.series.BEVSeriesConfig;
	
	import mx.charts.PlotChart;
	import mx.collections.ArrayCollection;

	public class BEVScatterChart extends BEVCartesianChart{
		
		private var _dataPointsToShow:String; //{"none", "edges", "all"}
		private var _plotShape:String; //{"diamond", "circle", "box", "triangle"}
		private var _plotShapeDimension:Number;
		
		public function BEVScatterChart(config:XML){
			super(new PlotChart(), config);
			_dataPointsToShow = "none";
			_plotShape = "circle";
			_plotShapeDimension = 5;
		}
		
		private function get myChart():PlotChart{ return _chart as PlotChart; }

		override public function buildSeriesSet(compConfigXML:XML):void{
			var seriesConfigs:ArrayCollection = initChartSeriesConfigs(compConfigXML);
			var seriesSet:Array = new Array();
			for(var i:int=0; i < seriesConfigs.length ;i++){
				var seriesConfig:BEVSeriesConfig = seriesConfigs[i] as BEVSeriesConfig;
				if(seriesConfig.chartType == BEVChartFactory.SCATTER_CHART){
					var plotSeries:BEVPlotSeries = new BEVPlotSeries(seriesConfig, _dataPointsToShow, _plotShape, _plotShapeDimension);
					plotSeries.dataProvider = _dataProvider.getSeriesData(seriesConfig.seriesID);
					seriesSet.push(plotSeries);					
				}
			}
			myChart.series = seriesSet;
		}
		
		override protected function setTypeSpecificProperties(properties:XMLList):void{
			if(properties == null){
				error(DefaultLogEvent.WARNING, "Null properties list!", "setTypeSpecificProperties");
				return;
			}
			for each(var property:XML in properties){
				switch(String(property.@name)){
					case("showdatapoint"):
						_dataPointsToShow = new String(property);
						break;
					case("plotshape"):
						_plotShape = new String(property);
						break;
					case("plotshapedimension"):
						_plotShapeDimension = new Number(property);
						break;
				}	
			}
		}
		
	}
}