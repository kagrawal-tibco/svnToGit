package com.tibco.be.views.user.components.chart.series{
	
	import com.tibco.be.views.user.components.chart.BEVChart;
	
	import mx.charts.series.AreaSeries;
	import mx.graphics.SolidColor;
	
	public class BEVAreaSeries extends AreaSeries implements IBEVSeries{
		
		private var _seriesConfig:XML;
		private var _actionConfig:XML;
		
		public function BEVAreaSeries(seriesConfigObj:BEVSeriesConfig, lineThickness:Number, showDataPoint:String, plotShape:String, plotShapeDimension:Number, fillOpacity:Number){
			super();
			_seriesConfig = seriesConfigObj.configXML;
			_actionConfig = BEVSeriesUtil.getActionConfig(_seriesConfig);
			yField = BEVChart.VALUE_FIELD;
			xField = BEVChart.CATEGORY_FIELD;
			useHandCursor = true;
			buttonMode = true;
			setStyle("areaFill", new SolidColor(seriesConfigObj.baseColor, fillOpacity));
		}
		
		public function get seriesConfig():XML{ return _seriesConfig; }
		public function get actionConfig():XML{ return _actionConfig; }
		
	}
}