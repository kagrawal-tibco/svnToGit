package com.tibco.cep.ui.monitor.panes.events
{
	import com.tibco.cep.ui.monitor.panes.MetricPane;
	
	import flash.events.Event;

	public class MetricPaneRemoveEvent extends Event
	{
		public static const METRICPANE_REMOVED:String = "metricPaneRemoved";
		
		public var metricPane:MetricPane;
		public var monitoredNode:XML;
		
		public function MetricPaneRemoveEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false){
			super(type, bubbles, cancelable);
		}
		
	}
}