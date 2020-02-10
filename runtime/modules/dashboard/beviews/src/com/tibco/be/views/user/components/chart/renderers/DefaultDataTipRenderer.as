package com.tibco.be.views.user.components.chart.renderers{
	
	import mx.charts.chartClasses.DataTip;

	public class DefaultDataTipRenderer extends DataTip{
		
		public function DefaultDataTipRenderer(){
			super();
			setStyle("paddingBottom", -10);
			setStyle("paddingRight", -8);
		}
		
	}
}