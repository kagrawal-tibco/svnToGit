package com.tibco.be.views.user.components.chart.axis{
	
	import mx.charts.CategoryAxis;
	import mx.charts.chartClasses.AxisLabelSet;

	public class BEVCategoryAxis extends CategoryAxis{
		
		public function BEVCategoryAxis(){
			super();
		}
		
		/**
		 * Work-around for a flex bug that draws a tick mark to the far left side of the chart when
		 * no data is present in vertical bar charts. The bug arises in AxisRenderer.drawTicks. On
		 * lines 3191-3192, it attempts to draw a line using NaN as a coordinate point. This work-
		 * around intercepts the creation of the array that is populated with the NaN used in the
		 * lineTo call.
		*/
		override public function getLabelEstimate():AxisLabelSet{
			var baseVal:AxisLabelSet = super.getLabelEstimate();
	        if(baseVal.ticks != null && baseVal.ticks.length > 0 && isNaN(baseVal.ticks[0])){
	        	baseVal.ticks.pop();
	        }
	        return baseVal;
	    }
		
	}
}