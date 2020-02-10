package com.tibco.cep.ui.monitor.io {
	import com.tibco.cep.ui.monitor.AppConfig;
	import com.tibco.cep.ui.monitor.TopologyMenuController;
	import com.tibco.cep.ui.monitor.panes.MetricPane;
	
	import flash.events.TimerEvent;
	import flash.utils.Dictionary;
	import flash.utils.Timer;
	
	
	internal class TimerBasedPSVRUpdatesClient implements IPSVRUpdatesClient {
		
		private var psvrClient:PSVRClient;
		private var paneMap:Dictionary;
		private var timer:Timer;
		private var queriesFiring:Boolean;
		private var topologyController:TopologyMenuController;
		private var _updateBatchId:uint;
		
		public function TimerBasedPSVRUpdatesClient(psvrClient:PSVRClient) {
			this.psvrClient = psvrClient;
			paneMap = new Dictionary(true);
			queriesFiring = false;
			timer = new Timer(20000);
			timer.addEventListener(TimerEvent.TIMER,fireQueries);
			_updateBatchId = 0;
		}
		
		public function subscribeTopology(controller:TopologyMenuController):void{
			topologyController = controller;
		}
		
		public function unsubscribeTopology():void{
			topologyController = null;
		}

		public function subscribePane(monitoredEntityID:String, pane:MetricPane):void {
			paneMap[pane.paneId] = new Array(monitoredEntityID,pane);
		}
		
		public function unsubscribePane(monitoredEntityID:String, pane:MetricPane):void {
			delete paneMap[pane.paneId];
		}
		
		public function unsubscribeAllPanes():void {
			//removes reference to dictionary with panes, which gets garbage collected
			paneMap = new Dictionary(true);
		}
		
		public function startUpdates():void {
			//config setting done after construction of this, thus need to reset timer's delay
			timer.delay = AppConfig.instance.updateInterval*1000;
			timer.start();
		}
		
		public function stopUpdates():void {
			timer.stop();
		}
		
		public function shutdown():void {
			_updateBatchId = 0;
			timer.reset();
		}
		
		private function fireQueries(event:TimerEvent) :void {
			if(!queriesFiring) {
				queriesFiring = true;
				_updateBatchId++;
				for each (var metricPaneInfo:Array in paneMap) {
					trace("Firing getPaneData for "+metricPaneInfo[1]);
					psvrClient.getPaneData(
						metricPaneInfo[0], //monitoredEntityID
						metricPaneInfo[1], //MetricPane Ojbect
						new DelegateMetricPaneUpdateable(metricPaneInfo[1], _updateBatchId)
					);
				}
				if(topologyController != null){
					trace("Updating Topology...");
					psvrClient.updateSiteTopology(topologyController);
				}
				queriesFiring = false;
			}
		}
	}
}