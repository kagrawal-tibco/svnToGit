package com.tibco.cep.ui.monitor.pages{
	
	import com.tibco.cep.ui.monitor.RemovedMetricManager;
	import com.tibco.cep.ui.monitor.containers.Flow;
	import com.tibco.cep.ui.monitor.io.PSVRClient;
	import com.tibco.cep.ui.monitor.metricGallery.GalleryItem;
	import com.tibco.cep.ui.monitor.metricGallery.MetricGallery;
	import com.tibco.cep.ui.monitor.metricGallery.events.GalleryItemEvent;
	import com.tibco.cep.ui.monitor.panes.MetricPane;
	import com.tibco.cep.ui.monitor.panes.MetricPaneFactory;
	import com.tibco.cep.ui.monitor.panes.events.MetricPaneRemoveEvent;
	import com.tibco.cep.ui.monitor.util.Util;
	
	import flash.display.DisplayObject;
	
	import mx.containers.Box;
	import mx.controls.Label;
	import mx.core.Container;

	public class PanelPage extends Container{
		
		private static const WIDTH_CORRECTION:Number = 0;
		private static const HEIGHT_CORRECTION:Number = -26;

		protected var _childPanes:Array;
		protected var _updatingPanes:Boolean;
		protected var _monitoredNode:XML;
		protected var _metricContainer:Flow;
		
		//private var _panelLoaderRef:PanelLoader;
		private var _gallery:MetricGallery;
		private var _lastWidth:int = -1;
		private var _lastHeight:int = -1;
		
		public function PanelPage(pageLabel:String, enableGallery:Boolean=true, monitoredNode:XML=null){
			super();
			percentWidth = 100;
			percentHeight = 100;
			
			_monitoredNode = monitoredNode;
			_childPanes = new Array();		
			_updatingPanes = false;
			styleName = "panelPageStyle";
			label = pageLabel;

			_metricContainer = new Flow();
//			_metricContainer.setStyle("backgroundColor", "red");
			_metricContainer.y = enableGallery ? -1*HEIGHT_CORRECTION:0;
			super.addChild(_metricContainer);
			
			if(enableGallery){ 
				_gallery = new MetricGallery(_monitoredNode);
				_gallery.y = 0;
				_gallery.pageID = _monitoredNode.@id + "_" + pageLabel;
				_gallery.addEventListener(GalleryItemEvent.RESTORE_METRIC, handleMetricRestore);
				super.addChild(_gallery);
				_gallery.refresh();
			}

		}
		
		public function get childPanes():Array{ return _childPanes; }
		
		public function addBEMetricPane(metric:MetricPane, size:int=1):void{
			if(metric == null) return;
			if(size > 1){
				metric.resize(size);
			}
			_childPanes.push(metric);
			_metricContainer.addChild(metric);
			
			//add an event listener to check the pane close event
			metric.addEventListener(MetricPaneRemoveEvent.METRICPANE_REMOVED, handleMetricPaneRemoved);
		}
		
		/**
		 * Adds a label to the page content. Nothing really uses this function, but it is useful
		 * when debugging.
		 * @param message The text to display in the page as a lablel.
		 */
		public function addLabelMessage(message:String):void{
			var box:Box = new Box();
			box.height = 50;
			box.width = 500;
			
			var innerLabel:Label = new Label();
			innerLabel.text = message;
			innerLabel.setStyle("fontWeight","bold");
			innerLabel.setStyle("fontSize", 16);
			box.addChild(innerLabel);
			
			this.addChild(box);
		}
		
		/**
		 * Validates a pane against this page's monitored node to ensure that the pane exists
		 * within the monitored node.  This is needed when loading panes from the SharedObject that
		 * may have become irrelavant in the server's context.
		 * *This function overwrites the pane's monitored node if the pane is found to be valid.
		 */
		public function validatePane(pane:MetricPane):Boolean{
			var searchResult:XML = Util.searchForAncestory(pane.ancestryPath, _monitoredNode);
			if(searchResult == null || searchResult.@active == "false"){
				pane.monitoredNode.@active = "";
				return false;
			}
			//update monitored node to server's current version IMPORTANT
			pane.monitoredNode = searchResult;
			return true;
		}
		
		public function isPaneInTopology(pane:MetricPane):Boolean {
			var searchResult:XML = Util.searchForAncestory(pane.ancestryPath, _monitoredNode);

			if(searchResult == null)
				return false;
				
			return true;	//it might be inactive and or invalid, but it still exists
		}
		
		public function reArrangeItems(srcItem:MetricPane, destItem:MetricPane):void{
			//this.swapChildren(srcItem, destItem);
			var srcIdx:int = getMetricChildIdx(srcItem);
			var destIdx:int = getMetricChildIdx(destItem);
			var remChild:MetricPane = removeMetricChildAt(destIdx) as MetricPane;
			addMetricPane(remChild, srcIdx);			
			_metricContainer.reLayoutItems(true);
		}
		
		public function handleTopologyUpdate(monitoredNode:XML):void {
			_monitoredNode = monitoredNode;
			
			if (_gallery != null)	// some pages do not have gallery
				_gallery.monitoredNode = monitoredNode;
		}
		
		/**
		 * @private 
		 * Gets triggered when user tries to remove/close a metric
		 */		
		private function handleMetricPaneRemoved(eventObj:MetricPaneRemoveEvent):void{
			var pane:MetricPane = eventObj.metricPane;
			var monitoredNode:XML = eventObj.monitoredNode;
			var id:String = _monitoredNode.@id + "_" + this.label;

			RemovedMetricManager.instance.addPane(id, pane);
			
			_metricContainer.removeChild(pane);
			var i:Number = _childPanes.indexOf(pane);
			if(i >= 0){
				_childPanes.splice(i,1);
			}
			PSVRClient.instance.unsubscribePane(pane.paneId, pane, null);
			pane.kill();			
			
			_metricContainer.reLayoutItems(true);
			_gallery.refresh();
		}
		
		/**
		 * Gets triggered when a metric gets added 
		 * @param eventObj
		 */		
		public function handleMetricRestore(eventObj:GalleryItemEvent):void{
			var galleryItem:GalleryItem = eventObj.galleryItem;
			var metricType:String = galleryItem.paneType;
			var metricPane:MetricPane = MetricPaneFactory.instance.buildMetricPane(metricType);		
			metricPane.monitoredNode = galleryItem.monitoredNode;
			metricPane.restore();
			//add the metric pane
			addBEMetricPane(metricPane);
			_metricContainer.reLayoutItems(true);
			//trace(metricType);
		}
		
		/**
		 * Returns the index of the metric pane in the metric container 
		 * @param item
		 * @return 
		 */		
		public function getMetricChildIdx(item:MetricPane):int{
			return _metricContainer.getChildIndex(item);
		}
		
		/**
		 * Removes a metric pane at the given child index
		 * @param idx
		 * @return 
		 */		
		public function removeMetricChildAt(idx:int):DisplayObject{
			return _metricContainer.removeChildAt(idx);
		}
		
		public function addMetricPane(child:MetricPane, index:int):DisplayObject{
			return _metricContainer.addChildAt(child, index);
		}
		
		public function removeAllMetricPanes():void{
			_metricContainer.removeAllChildren();
		}
		
		override public function invalidateDisplayList():void{
			//Util.infoMessage("Page\nHeight: " + height + "\nWidth: " + width);
			if(_updatingPanes) 
				return; //IMPORTANT -> else cyclic function calls...
			
			_updatingPanes = true;
			
			if( !(isNaN(height) || isNaN(width)) && height > 0 && width > 0){
				for each(var pane:MetricPane in _childPanes){
					pane.handlePageResize(width, height);
				}
				if(_gallery != null){
					_gallery.updateDisplay(width + WIDTH_CORRECTION, height);
				}
			}
			//super.invalidateMetricDisplayList();
			super.invalidateDisplayList();
			_updatingPanes = false;
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
			var newWidth:Number = unscaledWidth + WIDTH_CORRECTION;
			var newHeight:Number = unscaledHeight + 0;
			super.updateDisplayList(unscaledWidth,unscaledHeight);
			//trace("updateDisplayList("+unscaledWidth+", "+unscaledHeight+")");
			if (_lastWidth != newWidth || _lastHeight != newHeight) {
				_metricContainer.width = newWidth;
//				_metricContainer.height = unscaledHeight + HEIGHT_CORRECTION;
				_metricContainer.height = newHeight + HEIGHT_CORRECTION;
				_lastWidth = newWidth;
//				_lastHeight = newHeight;	TODO - why isn't it there?
				_metricContainer.reLayoutItems();
				if(_gallery != null){ _gallery.width = this.width - 4; }
			}			
		}
				
		
				
	}
}