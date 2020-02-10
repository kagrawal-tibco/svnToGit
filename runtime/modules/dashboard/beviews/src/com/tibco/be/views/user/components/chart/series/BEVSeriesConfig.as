package com.tibco.be.views.user.components.chart.series{
	
	public class BEVSeriesConfig{
		
		private var _seriesConfig:XML;
		private var _chartType:String;
		private var _seriesID:String;
		private var _seriesName:String;
		private var _anchor:String;
		private var _baseColor:uint;
		private var _highlightColor:uint;	
		private var _labelConfig:XML;
		
		public function BEVSeriesConfig(chartType:String, seriesConfigXML:XML){
			_chartType = chartType;
			_seriesConfig = new XML(seriesConfigXML);
			_seriesID = _seriesConfig.@name;
			_seriesName = _seriesConfig.@displayname;
			_anchor = new String(seriesConfigXML.@anchor);
			_baseColor = parseInt(_seriesConfig.@basecolor, 16);
			_highlightColor = parseInt(_seriesConfig.@highlightcolor, 16);
			if(_seriesConfig.valuelabelconfig != undefined && _seriesConfig.valuelabelconfig.length() > 0){
				_labelConfig = _seriesConfig.valuelabelconfig[0] as XML;
			}
		}
		
		public function get configXML():XML{ return _seriesConfig; }
		public function get chartType():String{ return _chartType; }
		public function get seriesID():String{ return _seriesID; }
		public function get seriesName():String{ return _seriesName; }
		public function get anchor():String{ return _anchor; }
		public function get baseColor():uint{ return _baseColor; }
		public function get highlightColor():uint{ return _highlightColor; }
		public function get labelConfig():XML{ return _labelConfig; }

	}
}