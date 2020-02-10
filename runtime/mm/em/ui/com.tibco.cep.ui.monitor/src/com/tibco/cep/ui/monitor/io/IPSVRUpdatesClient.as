package com.tibco.cep.ui.monitor.io {
	
	import com.tibco.cep.ui.monitor.TopologyMenuController;
	import com.tibco.cep.ui.monitor.panes.MetricPane;
	
	internal interface IPSVRUpdatesClient {
		
		function subscribePane(monitoredEntityID:String,pane:MetricPane):void;
		
		function unsubscribePane(monitoredEntityID:String,pane:MetricPane):void;
		
		function subscribeTopology(controller:TopologyMenuController):void;
		
		function unsubscribeTopology():void;
		
		function unsubscribeAllPanes():void;
		
		function startUpdates():void;
		
		function stopUpdates():void;
		
		function shutdown():void;	
		
	}
}