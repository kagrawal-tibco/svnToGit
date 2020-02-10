package com.tibco.be.views.user.components.chart{
	
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.user.components.chart.series.BEVHLOCSeries;
	import com.tibco.be.views.utils.Logger;
	
	import mx.charts.ChartItem;
	import mx.charts.HLOCChart;
	import mx.charts.HitData;

	public class BEVVRangeChart extends BEVCartesianChart{
		
		public static const MIN_SERIES_NAME:String = "minvalue";
		public static const CURRENT_SERIES_NAME:String = "currentvalue";
		public static const MAX_SERIES_NAME:String = "maxvalue";
		
		private var _barWidth:Number;
		private var _barColor:uint;
		private var _plotShape:String;
		private var _plotShapeDimension:Number;
		private var _whiskerThickness:Number;
		private var _whiskerWidth:Number;
		private var _whiskerColor:uint;
		
		public function BEVVRangeChart(config:XML){
			super(new HLOCChart(), config);
			myChart.showDataTips = true;
			_barWidth = 1;
			_barColor = 0x0;
			_plotShape = "circle";
			_plotShapeDimension = 5;
			_whiskerThickness = 2;
			_whiskerWidth = 5;
			_whiskerColor = 0x0;
		}
		
		private function get myChart():HLOCChart{ return _chart as HLOCChart; }
		
		override protected function dataTipFunciton(hitData:HitData):String{
			var rdp:RangeDataPoint = hitData.item as RangeDataPoint;
			if(rdp != null && !(rdp.max >= rdp.current && rdp.current >= rdp.min)){
				hitData.contextColor = 0xAA0000;
				return(
					"Invalid Data Point\n" +
					hitData.item.colID + "\n" +
					RangeDataPoint.CURRENT_FIELD_NAME + ": " + rdp.currentToolTip + "\n" +
					RangeDataPoint.MIN_FIELD_NAME + ": " + rdp.minToolTip + "\n" +
					RangeDataPoint.MAX_FIELD_NAME + ": " + rdp.maxToolTip
				);
			}
			return(
				hitData.item.colID + "\n" +
				RangeDataPoint.CURRENT_FIELD_NAME + ": " + rdp.currentToolTip + "\n" +
				RangeDataPoint.MIN_FIELD_NAME + ": " + rdp.minToolTip + "\n" +
				RangeDataPoint.MAX_FIELD_NAME + ": " + rdp.maxToolTip
			);
		}
		
		override protected function buildChartDataProvider(componentConfig:XML):ChartDataProvider{
			return new RangeChartDataProvider(componentConfig.chartconfig[0]);
		}
		
		override public function buildSeriesSet(compConfigXML:XML):void{
			//Range configuration is a special case. Three seriesconfig nodes are used to define
			//values for min, max, and current value at each category point. There are thus X number
			//of datacolumn nodes in each of the three seriesconfig xml nodes (where X is the number
			//of data points in the range series).
			
			var chartConfig:XML = compConfigXML.chartconfig[0];
			var seriesID:String = new String(chartConfig.@componentid);
			var chartTypeXML:XML = chartConfig.charttypeconfig[0];
			
			parseSeriesProperties(chartTypeXML);
			var series:BEVHLOCSeries = new BEVHLOCSeries(chartTypeXML, seriesID, _barWidth, _barColor, _whiskerThickness, _whiskerWidth, _whiskerColor);
			series.dataProvider = _dataProvider.getSeriesData(seriesID);			
			if(series.dataProvider == null){
				Logger.log(DefaultLogEvent.WARNING, "BEVVRangeChart.buildSeriesSet - series (" + seriesID + ") has null dataprovider");
			}
			myChart.series = [series];
		}
		
		override protected function setTypeSpecificProperties(properties:XMLList):void{
			if(properties == null){
				error(DefaultLogEvent.WARNING, "Null properties list!", "setTypeSpecificProperties");
				return;
			}
			for each(var property:XML in properties){
				switch(String(property.@name)){
					case("barwidth"):
						_barWidth = new Number(property);
						break;
					case("plotshape"):
						_plotShape = new String(property);
						break;
					case("plotshapedimension"):
						_plotShapeDimension = new Number(property);
						break;
					case("whiskerthickness"):
						_whiskerThickness = new Number(property);
						break;
					case("whiskerwidth"):
						_whiskerWidth = new Number(property);
						break;
				}	
			}
		}
		
		override public function getPointsByCategory(categoryValue:String):Array{
			categoryValue = categoryValue.replace(/\s/g, "");
			var points:Array = new Array();
			if(myChart.series.length == 0){ return points; }
			var items:Array = myChart.series[0].items;
			var i:int = 0;
			for each(var dataPoint:ChartItem in items){
				try{
					if(dataPoint.item.colID == categoryValue){
						points.push(dataPoint);
						break;
					}
				}
				catch(error:Error){
					trace("BEViewsVRangeChart.getItemsByCategory - " + error.message);
				}
				i++;
			}
			return points;
		}
		
		private function parseSeriesProperties(chartTypeXML:XML):void{
			var sxml:XML = chartTypeXML.seriesconfig.(@name==MIN_SERIES_NAME)[0];
			if(sxml == null){
				Logger.log(DefaultLogEvent.DEBUG, "BEVVRangeChart.parseSeriesProperties - NULL " + MIN_SERIES_NAME + " series conifg XML.");
			}
			else{
				_barColor = parseInt(sxml.@basecolor, 16);
			}
			
			sxml = chartTypeXML.seriesconfig.(@name==CURRENT_SERIES_NAME)[0];
			if(sxml == null){
				Logger.log(DefaultLogEvent.DEBUG, "BEVVRangeChart.parseSeriesProperties - NULL " + CURRENT_SERIES_NAME + " series conifg XML.");
			}
			else{
				_whiskerColor = parseInt(sxml.@basecolor, 16);
			}
		}

	}
}