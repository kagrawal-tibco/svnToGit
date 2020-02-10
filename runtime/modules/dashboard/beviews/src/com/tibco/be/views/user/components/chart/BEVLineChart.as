package com.tibco.be.views.user.components.chart{
	
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.user.components.chart.series.BEVLineSeries;
	import com.tibco.be.views.user.components.chart.series.BEVSeriesConfig;
	
	import mx.charts.LineChart;
	import mx.charts.series.LineSeries;
	import mx.collections.ArrayCollection;
	import mx.events.FlexEvent;

	public class BEVLineChart extends BEVCartesianChart{
		
		private var _lineThickness:Number;
		private var _dataPointsToShow:String; //{"none", "edges", "all"}
		private var _plotShape:String; //{"diamond", "circle", "box", "triangle"}
		private var _plotShapeDimension:Number;
		
		public function BEVLineChart(config:XML){
			super(new LineChart(), config);
			_lineThickness = 3;
			_dataPointsToShow = "none";
			_plotShape = "circle";
			_plotShapeDimension = 5;
		}
		
		private function get myChart():LineChart{ return _chart as LineChart; }
		
		override public function buildSeriesSet(compConfigXML:XML):void{
			var seriesConfigs:ArrayCollection = initChartSeriesConfigs(compConfigXML);
			var seriesSet:Array = new Array();
			for(var i:int=0; i < seriesConfigs.length ;i++){
				var seriesConfig:BEVSeriesConfig = seriesConfigs[i] as BEVSeriesConfig;
				if(seriesConfig.chartType == BEVChartFactory.LINE_CHART){
					var lineSeries:LineSeries = new BEVLineSeries(seriesConfig, _lineThickness, _dataPointsToShow, _plotShape, _plotShapeDimension);
					lineSeries.dataProvider = _dataProvider.getSeriesData(seriesConfig.seriesID);
					seriesSet.push(lineSeries);
				}
			}
			myChart.series = seriesSet;
		}
		
		override protected function setTypeSpecificProperties(properties:XMLList):void{
			if(properties == null){
				error(DefaultLogEvent.WARNING, "Null properties list!", "setTypeSpecificProperties");
				return;
			}
			myChart.seriesFilters = []; //wipes out line shadows
			for each(var property:XML in properties){
				switch(String(property.@name)){
					case("linethickness"):
						_lineThickness = new Number(property);
						break;
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
		
		override protected function handleCreationComplete(event:FlexEvent):void{
			event.target.horizontalAxis.padding = 0.3;
		}
		
	}
}