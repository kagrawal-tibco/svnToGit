package com.tibco.cep.ui.monitor.panes{
	
	import mx.controls.Label;

	public class MetricPaneTitle extends Label{
		
		public function MetricPaneTitle(text:String=""){
			super();
			this.text = text;
			x = 3;
			y = 3;
			percentWidth = 100;
			height = 23;
			styleName = "metricPaneTitleStyle";
			
/* 			setStyle("fontSize",16);
			setStyle("fontWeight","bold");
			setStyle("color","0x0");
			setStyle("textAlign", "center"); */
		}		
	}
}