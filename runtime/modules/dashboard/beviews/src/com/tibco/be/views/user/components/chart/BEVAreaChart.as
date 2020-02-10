package com.tibco.be.views.user.components.chart{
	
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.user.components.chart.series.BEVAreaSeries;
	import com.tibco.be.views.user.components.chart.series.BEVLineSeries;
	import com.tibco.be.views.user.components.chart.series.BEVSeriesConfig;
	import com.tibco.be.views.user.utils.ChartMinMax;
	
	import mx.charts.LinearAxis;
	import mx.charts.chartClasses.CartesianChart;
	import mx.charts.series.LineSeries;
	import mx.collections.ArrayCollection;
	
	public class BEVAreaChart extends BEVCartesianChart{
		
		private var _lineThickness:Number;
		private var _showDataPoint:String; //{"none", "edges", "all"}
		private var _plotShape:String; //{"diamond", "circle", "box", "triangle"}
		private var _plotShapeDimension:Number;
		private var _fillOpacity:Number;
		
		public function BEVAreaChart(config:XML){
			super(new CartesianChart(), config);
			_lineThickness = 3;
			_showDataPoint = "none";
			_plotShape = "circle";
			_plotShapeDimension = 5;
			_fillOpacity = 1;
			_chart.dataTipMode = "single";
			_chart.setStyle("paddingLeft", 0);
		}
		
		private function get myChart():CartesianChart{ return _chart as CartesianChart; }

		override public function setChartBoundsAndInterval(minMax:ChartMinMax, forceZeroOrigin:Boolean=false):void{
			forceZeroOrigin = minMax.min > 0 && _vAxis is LinearAxis;
			super.setChartBoundsAndInterval(minMax, forceZeroOrigin);
		}

		override public function buildSeriesSet(vizDataXML:XML):void{
			var seriesConfigs:ArrayCollection = initChartSeriesConfigs(vizDataXML);
			var seriesSet:Array = new Array();
			for(var i:int=0; i < seriesConfigs.length ;i++){
				var seriesConfig:BEVSeriesConfig = seriesConfigs[i] as BEVSeriesConfig;
				if(seriesConfig.chartType == BEVChartFactory.AREA_CHART){
					var areaSeries:BEVAreaSeries = new BEVAreaSeries(seriesConfig, _lineThickness, _showDataPoint, _plotShape, _plotShapeDimension, _fillOpacity);
					areaSeries.dataProvider = _dataProvider.getSeriesData(seriesConfig.seriesID);
					seriesSet.push(areaSeries);
					
					var lineSeries:LineSeries = new BEVLineSeries(seriesConfig, _lineThickness, _showDataPoint, _plotShape, _plotShapeDimension);
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
			for each(var property:XML in properties){
				switch(String(property.@name)){
					case("linethickness"):
						_lineThickness = new Number(property);
						break;
					case("showdatapoint"):
						_showDataPoint = new String(property);
						break;
					case("plotshape"):
						_plotShape = new String(property);
						break;
					case("plotshapedimension"):
						_plotShapeDimension = new Number(property);
						break;
					case("fillopacity"):
						_fillOpacity = (new Number(property))/100;
						break;
				}
			}
		}
		
	}
}