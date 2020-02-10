package com.tibco.be.views.user.components.chart{
	
	import mx.charts.HLOCChart;

	public class BEVHRangeChart extends BEVCartesianChart{
		
		public function BEVHRangeChart(config:XML){
			super(new HLOCChart(), config);
		}
		
		private function get myChart():HLOCChart{ return _chart as HLOCChart; }
		
		override public function buildSeriesSet(compConfigXML:XML):void{
			
		}
		
		override protected function setTypeSpecificProperties(properties:XMLList):void{
			
		}
		
	}
}