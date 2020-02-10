package com.tibco.be.views.user.components.chart.series{
	
	import com.tibco.be.views.user.components.chart.BEVChart;
	
	import flash.filters.BitmapFilterQuality;
	import flash.filters.DropShadowFilter;
	
	import mx.charts.series.PieSeries;

	public class BEVPieSeries extends PieSeries implements IBEVSeries{
		
		private var _seriesConfig:XML;
		private var _actionConfig:XML;
		
		public function BEVPieSeries(seriesConfigObj:BEVSeriesConfig){
			super();
			_seriesConfig = seriesConfigObj.configXML;
			_actionConfig = BEVSeriesUtil.getActionConfig(_seriesConfig);
			field = BEVChart.VALUE_FIELD;
			nameField = BEVChart.CATEGORY_FIELD;
			useHandCursor = true;
			buttonMode = true;
			startAngle = 90; // start at 12 o'clock
//			setStyle("renderDirection", "clockwise");  //not in flex 3
			setStyle("labelPosition", "outside");
			if(seriesConfigObj.labelConfig != null){
				var size:int = parseInt(seriesConfigObj.labelConfig.@fontsize);
				var color:uint = parseInt(seriesConfigObj.labelConfig.@fontcolor);
				var style:String = new String(seriesConfigObj.labelConfig.@fontstyle);
				if(!isNaN(size)){ setStyle("fontSize", size); }
				if(!isNaN(color)){ setStyle("color", color); }
				if(style.indexOf("italic") >= 0){ setStyle("fontStyle", "italic"); }
				if(style.indexOf("bold") >= 0){ setStyle("fontWeight", "bold"); }
			}
			//setStyle("labelPosition", "callout"); 
			filters = [new DropShadowFilter(2, 45, 0x0, 0.8, 2, 2, 0.65, BitmapFilterQuality.HIGH, false, false)];
		}
				
		public function get seriesConfig():XML{ return _seriesConfig; }
		
		public function get actionConfig():XML{ return _actionConfig; }
		
	}
}