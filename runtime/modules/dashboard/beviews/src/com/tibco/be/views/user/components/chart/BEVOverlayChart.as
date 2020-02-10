package com.tibco.be.views.user.components.chart{
	
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.user.components.chart.series.BEVColumnSeries;
	import com.tibco.be.views.user.components.chart.series.BEVLineSeries;
	import com.tibco.be.views.user.components.chart.series.BEVPlotSeries;
	import com.tibco.be.views.user.components.chart.series.BEVSeriesConfig;
	
	import mx.charts.chartClasses.CartesianChart;
	import mx.charts.series.LineSeries;
	import mx.charts.series.PlotSeries;
	import mx.collections.ArrayCollection;
	import mx.graphics.SolidColor;

	public class BEVOverlayChart extends BEVCartesianChart{
		
		private static const DEFAULT_COL_WIDTH:int = 10;
		
		//These aren't used for the chart itself, only to aid in creating the various series
		//displayed in the chart.  Don't consider them chart properties.
		private var _lineThickness:Number;
		private var _showDataPoint:String; //{"none", "edges", "all"}
		private var _plotShape:String; //{"diamond", "circle", "box", "triangle"}
		private var _plotShapeDimension:Number;
		private var _columnWidth:Number;
		private var _overlap:Number;
		
		private var _columnSeries:Array;
		private var _clusterWidthRatio:Number;
		
		public function BEVOverlayChart(config:XML){
			super(new CartesianChart(), config);
			_lineThickness = 3;
			_showDataPoint = "none";
			_plotShape = "circle";
			_plotShapeDimension = 5;
			_columnWidth = -1;
			_overlap = 0;
			_clusterWidthRatio = 0.8;
		}
		
		private function get myChart():CartesianChart{ return _chart as CartesianChart; }
		
		override public function setDimensions(x:Number, y:Number, width:Number, height:Number):void{
			if(_chart.x != x || _chart.y != y || _chart.width != width || _chart.height != height){
				super.setDimensions(x, y, width, height);
				ChartUtils.configureBarOverlap(_columnSeries, _dataProvider.categoryAxisData.length, plotArea.width, _clusterWidthRatio, _overlap, _columnWidth);
			}
		}
		
		override public function buildSeriesSet(compConfigXML:XML):void{
			var seriesConfigs:ArrayCollection = initChartSeriesConfigs(compConfigXML);
			var seriesSet:Array = new Array();
			_columnSeries = [];
			
			//number of chart types may vary from the number of entries in chartData
			for each(var cTypesXML:XML in compConfigXML.chartconfig.charttypeconfig){
				parseSeriesSpecificProperties(cTypesXML.typespecificattribute);
			}
			for(var i:int=0; i < seriesConfigs.length ;i++){
				var seriesConfig:BEVSeriesConfig = seriesConfigs[i] as BEVSeriesConfig;
				if(seriesConfig.chartType == BEVChartFactory.LINE_CHART){
					var lineSeries:LineSeries = new BEVLineSeries(seriesConfig, _lineThickness, _showDataPoint, _plotShape, _plotShapeDimension);
					lineSeries.dataProvider = _dataProvider.getSeriesData(seriesConfig.seriesID);
					seriesSet.push(lineSeries);
				}
				else if(seriesConfig.chartType == BEVChartFactory.SCATTER_CHART){
					var plotSeries:BEVPlotSeries = new BEVPlotSeries(seriesConfig, _showDataPoint, _plotShape, _plotShapeDimension);
					plotSeries.dataProvider = _dataProvider.getSeriesData(seriesConfig.seriesID);
					seriesSet.push(plotSeries);					
				}
				else if(seriesConfig.chartType == BEVChartFactory.VERTICAL_BAR_CHART){
					var columnSeries:BEVColumnSeries = new BEVColumnSeries(seriesConfig, _columnWidth, _overlap);
					columnSeries.dataProvider = _dataProvider.getSeriesData(seriesConfig.seriesID);
					seriesSet.push(columnSeries);
					_columnSeries.push(columnSeries);
				}
			}
			myChart.series = seriesSet;
		}
		
		override protected function configurePlotArea(chartConfig:XML):void{
			super.configurePlotArea(chartConfig);
			_gridLines.setStyle("verticalShowOrigin", false);
		}
		
		override protected function setTypeSpecificProperties(properties:XMLList):void{
			//Nothing is done here, properties are set as the various series are built since the
			//data for overlay charts contains multiple charttypeconfig nodes vs the usual of having
			//multiple seriesconfig nodes in one charttypeconfig
			//trace("BEViewsOverlayChart.setTypeSpecificProperties");
		}
		
		private function parseSeriesSpecificProperties(properties:XMLList):void{
			if(properties == null){
				error(DefaultLogEvent.WARNING, "Null properties list!", "parseSeriesSpecificProperties");
				return;
			}
			
			for each(var property:XML in properties){
				switch(String(property.@name)){
					//line series
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

					//vBar series
					//there's no setting in studio for column width in an overlay chart, we thus
					//use a default value until a user-specified value becomes available
					_columnWidth = DEFAULT_COL_WIDTH;
					case("width"):
						_columnWidth = new Number(property);
						break;
					case("overlappercentage"):
						_overlap = new Number(property)/100;
						break;
				}	
			}
		}
		
		override public function updateChartData(componentData:XML):void{
			super.updateChartData(componentData);
			ChartUtils.configureBarOverlap(_columnSeries, _dataProvider.categoryAxisData.length, plotArea.width, _clusterWidthRatio, _overlap, _columnWidth);
		}
		
	}
}