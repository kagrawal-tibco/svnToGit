package com.tibco.be.views.user.components.chart.renderers{
	
	import mx.charts.chartClasses.ChartLabel;

	public class BEVChartLabel extends ChartLabel{
		
		public function BEVChartLabel(){
			super();
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			if(parent == null){ return; }
			super.updateDisplayList(unscaledWidth, unscaledHeight);
		}

	}
}