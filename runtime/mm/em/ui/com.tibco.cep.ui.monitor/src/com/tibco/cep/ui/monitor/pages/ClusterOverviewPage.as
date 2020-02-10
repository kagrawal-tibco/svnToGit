package com.tibco.cep.ui.monitor.pages{
	import com.tibco.cep.ui.monitor.PromotedPaneRepository;
	import com.tibco.cep.ui.monitor.containers.Flow;
	import com.tibco.cep.ui.monitor.events.EventTypes;
	import com.tibco.cep.ui.monitor.io.PSVRClient;
	import com.tibco.cep.ui.monitor.panes.MetricPane;
	import com.tibco.cep.ui.monitor.panes.MetricPaneType;
	import com.tibco.cep.ui.monitor.panes.table.SystemAlertsTable;
	
	import flash.events.Event;
	
	import mx.containers.VBox;
	import mx.containers.VDividedBox;
	
	
	public class ClusterOverviewPage extends PanelPage{
		public static const TYPE_NAME:String = "clusteroverview";
		
		//Containers
		private var _splitPane:VDividedBox;
		private var _promotedPaneContainer:Flow;
		private var _headerPaneContainer:VBox;
		
		//Components
		private var _clusterStatsTable:MetricPane;
		private var _clusterAlertsTable:SystemAlertsTable;
		private var _promotedPanes:Array;
		
		public function ClusterOverviewPage(monitoredNode:XML){			
			super("Cluster Overview", false, monitoredNode);
			_splitPane = new VDividedBox();
			_promotedPaneContainer = new Flow();
			_headerPaneContainer = new VBox();
			
			_headerPaneContainer.percentWidth = 100;
			_headerPaneContainer.percentHeight = 67;
			_headerPaneContainer.setStyle("horizontalAlign", "center");
			_headerPaneContainer.setStyle("backgroundColor", 0xCED8E4);
			_headerPaneContainer.horizontalScrollPolicy="off";
			
			_promotedPaneContainer.percentWidth = 100;
			_promotedPaneContainer.percentHeight = 33;
			_promotedPaneContainer.setStyle("backgroundColor", 0xCED8E4);		
			
			_splitPane.addChild(_headerPaneContainer);
			_splitPane.addChild(_promotedPaneContainer);
			
			_splitPane.x = 0;
			_splitPane.y = 0;
			_splitPane.setStyle("backgroundColor", 0x778EA1);
			_splitPane.setStyle("dividerAlpha", 0.7);
			_splitPane.setStyle("dividerColor", 0x0099CC);
			
			this.addChild(_splitPane);
		}
		
		/**
		 * Loads panes from the PromotedPaneRepository and adds them to the lower division of
		 * the ClusterOverviewPage
		 */
		public function loadPromotedPanes():void{
			var promotedPanes:Array = PromotedPaneRepository.instance.panes;
			for each(var pane:MetricPane in promotedPanes){
				//remove metric panes that no longer exist in the topology from the 
				//shared object. Don't add them to the promoted pane container
				if(!isPaneInTopology(pane)) {
					PromotedPaneRepository.instance.removePane(pane);
					continue;
				}
				
				pane.displayMode = MetricPane.PROMOTED_DISPLAY;
				pane.addEventListener(EventTypes.PANE_DEMOTED, handlePaneDemoted);

				if(!validatePane(pane)){
					pane.setInvalidClusterComponentOverlay();
				}
				
				_promotedPaneContainer.addChild(pane);
				_childPanes.push(pane);
			}
		}
		
		/**
		 * This override is used to add panes to the upper division of the ClusterOverviewPage
		 */
		public override function addBEMetricPane(metric:MetricPane, size:int=1):void{
			if(metric == null) 
				return;
			else if(metric.type == MetricPaneType.CLUSTER_SUMMARY_TABLE){
				_clusterStatsTable = metric;
				_clusterStatsTable.height = 228;
			}
			else if(metric is SystemAlertsTable){
				_clusterAlertsTable = metric as SystemAlertsTable;
				_clusterAlertsTable.height = 205;
			}
			
			metric.displayMode = MetricPane.STATIC_DISPLAY;
			metric.setDisplayDimensions();
			_headerPaneContainer.addChild(metric);
			_childPanes.push(metric);
			//super.addBEMetricPane(metric, size);
		}
		
		/**
		 * Keep the page content scaled to the size of the container
		 */
		public override function invalidateDisplayList():void{
			//Util.infoMessage("Page\nHeight: " + height + "\nWidth: " + width);
			if(_updatingPanes) 
				return; //IMPORTANT - else cyclic function calls...
			
			_updatingPanes = true;
			
			if( !(isNaN(height) || isNaN(width)) && height > 0 && width > 0){
				_splitPane.height = height;
				_splitPane.width = width-4;
				if(_clusterAlertsTable != null){
					_clusterAlertsTable.width = width-10;
					_clusterAlertsTable.setDisplayDimensions();
				}
			}
			super.invalidateDisplayList();
			_updatingPanes = false;
		}
		
		public override function reArrangeItems(srcItem:MetricPane, destItem:MetricPane):void{
			//DnD is disabled in the header section so only need to handle promoted pane section
			//this.swapChildren(srcItem, destItem);
			var srcIdx:int = _promotedPaneContainer.getChildIndex(srcItem);
			var destIdx:int = _promotedPaneContainer.getChildIndex(destItem);
			var arr:Array = _promotedPaneContainer.getChildren();
			
			var remChild:MetricPane = MetricPane(_promotedPaneContainer.removeChildAt(destIdx));
			_promotedPaneContainer.addChildAt(remChild, srcIdx);			
			
			_metricContainer.reLayoutItems(true);
		}
		
		/**
		 * Removes a pane from the lower, promoted pane section of this page
		 */
		private function handlePaneDemoted(event:Event):void{
			var pane:MetricPane = event.target as MetricPane;
	
			removePromotedPane(pane);
		}
		
		private function removePromotedPane(pane:MetricPane):void {
			//remove the pane from the shared object
			PromotedPaneRepository.instance.removePane(pane);
			
			if (_promotedPaneContainer.contains(pane)) {
				_promotedPaneContainer.removeChild(pane);
				
				var i:Number = _childPanes.indexOf(pane);
				
				if(i >= 0)
					_childPanes.splice(i,1);
				
				PSVRClient.instance.unsubscribePane(pane.paneId, pane, null);
				pane.kill(); 
			}			
		}
		
		//used only to add pane overlays to the promoted panes, in case there are changes in the topology
		public override function handleTopologyUpdate(monitoredNode:XML):void{
			super.handleTopologyUpdate(monitoredNode);
			
			for each(var pane:MetricPane in _childPanes){
				//handle just the promoted panes; in the lower section of page
				if(pane.type != MetricPaneType.CLUSTER_SUMMARY_TABLE && !(pane is SystemAlertsTable)){
					//Check for inactive/invalid panes
					var valid:Boolean = validatePane(pane);

					if(pane.isValid && !valid){ //valid -> invalid
						PSVRClient.instance.unsubscribePane(pane.monitoredNode.@id, pane, null);
						pane.setInvalidClusterComponentOverlay();
					}
					else if(!pane.isValid && valid){ //invalid -> valid
						PSVRClient.instance.subscribePane(pane.monitoredNode.@id, pane, null);
						pane.clearOverlays();
					}
				}
			}
		}
		
	}
}