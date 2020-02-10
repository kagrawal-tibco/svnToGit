package com.tibco.cep.ui.monitor.panels{
	
	import com.tibco.cep.ui.monitor.AppConfig;
	import com.tibco.cep.ui.monitor.IUpdateable;
	import com.tibco.cep.ui.monitor.RemovedMetricManager;
	import com.tibco.cep.ui.monitor.containers.PanelNavigator;
	import com.tibco.cep.ui.monitor.events.EventTypes;
	import com.tibco.cep.ui.monitor.events.MetricPaneUpdateFailure;
	import com.tibco.cep.ui.monitor.events.ServerCrashEvent;
	import com.tibco.cep.ui.monitor.io.PSVRClient;
	import com.tibco.cep.ui.monitor.pages.ClusterOverviewPage;
	import com.tibco.cep.ui.monitor.pages.PanelPage;
	import com.tibco.cep.ui.monitor.panes.MetricPane;
	import com.tibco.cep.ui.monitor.panes.MetricPaneFactory;
	
	import flash.utils.Dictionary;
	
	import mx.containers.ApplicationControlBar;
	import mx.controls.Alert;
	import mx.core.UIComponent;

	public class GeneralPanel extends UIComponent implements IUpdateable{
		
		/*Used as a temporary value for _monitoredNode should it disappear from the topology*/
		private static var REMOVED_NODE:XML = <removedNode id="setMe"/>;	//id is set to the MonitoredId of the removed node
	
		protected var _monitoredNode:XML;
		protected var _panelDefinition:XML;
		protected var _panelType:String;
		protected var _pageSet:Array;
		protected var _panelButtonBar:ApplicationControlBar;
		private var _failedPaneDirectory:Dictionary;
		private var _panesContained:int;
		private var _navigator:PanelNavigator;
		
		
		public function GeneralPanel(monitoredNode:XML, navigator:PanelNavigator){
			if(monitoredNode == null) throw new Error("GeneralPanel() - valid monitoredNode required");
			if(navigator == null) throw new Error("GeneralPanel() - valid navigator required");
			_monitoredNode = monitoredNode;
			_panelType = String(_monitoredNode.name());
			_navigator = navigator;
			_pageSet = new Array();
			_failedPaneDirectory = new Dictionary(true);
			_panesContained = 0;
		}
		
		//used to pass the current page to the methodInvocationPanel component. This is necessary
		//because the method_description pop-up window needs a parent UIcomponent over which the 
		//pop-up is going to be displayed
		public function get currentPage():PanelPage {return _navigator.selectedChild as PanelPage;}
		
		public function get navigator():PanelNavigator { return _navigator;	}
		
		public function get panelType():String { return _panelType;	}
		
		public function get pageSet():Array{ return _pageSet; }
		public function get monitoredNode():XML{ return _monitoredNode; }
		
		public function clearPanel():void{
			for each(var page:PanelPage in _pageSet){
				for each(var pane:MetricPane in page.childPanes){
					pane.kill();
					pane.removeEventListener(EventTypes.PANE_UPDATE_FAILURE, handleMetricUpdateFailure);
				}
				page.removeAllChildren();
			}
			_pageSet = new Array();
			//DON'T TOUCH _panelDefinition!!!
		}
		
		//it is in this function that the pages are added to the container
		public function render():void{
			if(_navigator == null)
				throw new Error("GeneralPanel.render() - can't render in null PanelNavigator");
			_navigator.removeAllChildren();
			for each(var page:PanelPage in _pageSet){
				_navigator.addChild(page);
			}
			if(_monitoredNode.@active == undefined || _monitoredNode.@active == "true"){
				//_navigator.setStyle("backgroundColor", 0xFFFFFF);
			}
			else{
				//_navigator.setStyle("backgroundColor", 0xCCCCCC);
			}
			_navigator.selectedTab = 0;		
		}
		
		//only puts the pages in the ArrayList. The pages only get added to the container, hence
		//displayed, when the render() method is called
		private function addPages():void {
			var page:PanelPage = null;

			var pages:XMLList = _panelDefinition.page;
			_panesContained = 0;
			for each(var xmlPage:XML in pages){
				var pageName:String = String(xmlPage.@name);
				if(_panelType == PanelTypes.CLUSTER){
					page = new ClusterOverviewPage(_monitoredNode);
					(page as ClusterOverviewPage).loadPromotedPanes();
					//promoted panes will have unique monitoredNodes
				}
				else if(_panelType == PanelTypes.SITE || _panelType == PanelTypes.CACHE_OBJECTS){
					page = new PanelPage(pageName, false);
				} 
				else {
					page = new PanelPage(pageName, true, _monitoredNode);
				}
				
				addPanesToPage(page, xmlPage, pageName);

				_pageSet.push(page);
			}
		}
		
		private function addPanesToPage(page:PanelPage, xmlPage:XML, pageName:String):void {
			var strPanes:String = String(xmlPage);
			if(strPanes.length > 0) {
				var panes:Array = strPanes.split(",");
				
				_panesContained += panes.length;
				
				//Add panes to pages
				for each(var strPane:String in panes){
					var id:String = _monitoredNode.@id + "_" + pageName;
					if(!RemovedMetricManager.instance.hasPane(id, strPane)) {
						var metricPane:MetricPane = MetricPaneFactory.instance.buildMetricPane(strPane);
						metricPane.monitoredNode = _monitoredNode;
						
						metricPane.addEventListener(EventTypes.PANE_UPDATE_FAILURE, handleMetricUpdateFailure);
						
						page.addBEMetricPane(metricPane);
					}
				}
			}
		}
		
		public function showPanesInactiveOverlay():void{
			for each(var page:PanelPage in _pageSet){
				for each(var metricPane:MetricPane in page.childPanes){
					metricPane.setInactivePaneOverlay();
				}
			}
		}
		
		private function showPanesRemovedOverlay():void{
			for each(var page:PanelPage in _pageSet){
				for each(var metricPane:MetricPane in page.childPanes){
					metricPane.setRemovedFromClusterOverlay();
				}
			}
		}
		
		private function clearPanesOverlay():void{
			for each(var page:PanelPage in _pageSet){
				for each(var metricPane:MetricPane in page.childPanes){
					metricPane.clearOverlays();
				}
			}
		}
		
		private function subscribePanes():void{
			for each(var page:PanelPage in _pageSet){
				for each(var metricPane:MetricPane in page.childPanes){
					PSVRClient.instance.getPaneData(String(_monitoredNode.@id), metricPane, metricPane);
				}
			}
		}
		
		private function unsubscribePanes():void{
			PSVRClient.instance.unsubscribeAllPanes(this);
		}
		
		private function activeStateChanged(newNode:XML):Boolean{
			return 	(newNode.@active == "false" && _monitoredNode.@active == "true") ||		//active => inactive
					(newNode.@active == "true" && _monitoredNode.@active == "false");		//inactive => active
		}
		
		public function handleMetricUpdateFailure(event:MetricPaneUpdateFailure):void{
			if(_failedPaneDirectory[event.updateBatchId] == undefined){
				_failedPaneDirectory[event.updateBatchId] = 0;
			}
			var numFailedPanes:Number = (_failedPaneDirectory[event.updateBatchId] as Number) + 1;
			_failedPaneDirectory[event.updateBatchId] = numFailedPanes;
			//ensure server crash only dispatched at first occurrence
			var thresh:Number = AppConfig.instance.failedPaneThreshold*_panesContained;
			if(numFailedPanes-1 <= thresh && numFailedPanes > thresh){
				dispatchEvent(new ServerCrashEvent(event.message, 0));
			}
		}

		public function update(operation:String, data:XML):void {
			if(operation == "getpanelcomponents") {
				_panelDefinition = data;
				addPages();
				//setButtons();
			}
			else if(operation == EventTypes.TOPOLOGY_UPDATE) {
				if(data == null && _monitoredNode != REMOVED_NODE) {
					//monitoredNode not found in topology
					Alert.show("The cluster entity currently being viewed has been removed from the cluster.", "INFO");
					showPanesRemovedOverlay();
					unsubscribePanes();
					var id:String = new String(_monitoredNode.@id);
					_monitoredNode = REMOVED_NODE;
					_monitoredNode.@id = id; //if the node reappears, we need to be able to identify it for recovery
				}
				else if(data == null && _monitoredNode == REMOVED_NODE){
					//panel being shown is one that is no longer represented by a node in the topology
					return;
				}
				else if(data != null && _monitoredNode == REMOVED_NODE){
					//monitoredNode was missing from topology, now it's back
					_monitoredNode = data;
					if(_monitoredNode.@active == "false"){
						showPanesInactiveOverlay();
						//leave panes unsubscribed
					}
					else{
						clearPanesOverlay();
						subscribePanes();
					}
				}
				else if(activeStateChanged(data)){		
					//refresh _monitoredNode with new data 		
					_monitoredNode = data;
					if(_monitoredNode.@active == "false"){
						showPanesInactiveOverlay();
						unsubscribePanes();
					}
					else{
						clearPanesOverlay();
						subscribePanes();
					}
				}
				else{
					_monitoredNode = data; 
				}
				
				for each(var page:PanelPage in _pageSet){
					page.handleTopologyUpdate(_monitoredNode);
				}
				
			}
			else if(operation == "unsubscribeallpanes"){
				for each(page in _pageSet){
					for each(var metricPane:MetricPane in page.childPanes){
						metricPane.subscribed = false;
					}
				}
			}
		}
		
		public function updateFailure(operation:String,message:String,code:uint):void{
			trace("GeneralPanel - " + operation + ":\n\n" + message);
		}
		
	}
}