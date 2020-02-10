package com.tibco.be.views.user.dashboard{
	
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.IEventBusListener;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.ui.actions.IActionContextProvider;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.core.ui.dashboard.IBEVContainer;
	import com.tibco.be.views.ui.containers.BEVDividedPanel;
	import com.tibco.be.views.ui.containers.BEVDividedPartition;
	import com.tibco.be.views.user.dashboard.events.BEVPanelEvent;
	import com.tibco.be.views.utils.Logger;
	
	import flash.display.DisplayObjectContainer;
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	import mx.collections.IList;
	import mx.containers.Box;
	import mx.containers.HBox;
	import mx.containers.HDividedBox;
	import mx.containers.VBox;
	import mx.containers.VDividedBox;

	public class BEVContentPane extends VBox implements IBEVContainer, IEventBusListener{
		
		private static var CONTAINER_TYPE:String = "BEVDashboard";
		private static var EMPTY_LIST:IList = new ArrayCollection();		
		
		private var _containerID:String;
		private var _containerName:String;
		private var _containerTitle:String;
		private var _header:BEVHeader;
		private var _partitionsContainer:Box; 
		private var _statusBar:BEVFooter;
		private var _pagesConfig:XML;
		private var _panelIDToPanelRefDict:Dictionary;
		private var _panelList:IList;
		
		private var _showHeader:Boolean;
		private var _showFooter:Boolean;
		
		public function BEVContentPane(showHeader:Boolean=true, showFooter:Boolean=true){
			super();
			_panelIDToPanelRefDict = new Dictionary();
			_panelList = new ArrayCollection();
			setStyle("verticalGap", 0);
			//this.creationPolicy = ContainerCreationPolicy.QUEUED;
			_showHeader = showHeader;
			_showFooter = showFooter;
		}
		
		/**
		 * Initializes the container 
		 * @param containerCfg The container definition XML,can be null
		 */  
		public function init(containerCfg:XML=null):void{ //default null value required by interface
			if(containerCfg == null){
				throw new Error("container configuration XML cannot be null");
			}
			registerListeners();
			_pagesConfig = containerCfg;
			_containerID = new String(_pagesConfig.pageconfig.@id);
			_containerName = new String(_pagesConfig.pageconfig.@name);
			//we add the dashboard header first 
			_header = new BEVHeader();
			//header.creationIndex = 1;	
			if(!_showHeader){
				_header.rightContentVisible = false;
			}
			addChild(_header);
			//get main layout container
			_partitionsContainer = getPartitionsContainer(_pagesConfig);
			//we are using the data property of the partitionsconfig to store page config properties
			_partitionsContainer.data = new XMLList(_pagesConfig.pageconfig.attributes());
			_partitionsContainer.percentHeight = 100;
			_partitionsContainer.percentWidth = 100;
			//partitionsContainer.creationIndex = 2;
			addChild(_partitionsContainer);
			//create container for each partition and add it to main container
			var partitionCfgs:XMLList = new XMLList(_pagesConfig.pageconfig.partitionconfig);
			var partitionCfgsCnt:int = partitionCfgs.length();
			var remainingSpan:int = 100;
			for(var i:int = 0 ; i < partitionCfgsCnt ; i++){
				var partitionConfig:XML = partitionCfgs[i];
				var panelsContainer:Box = getPanelsContainer(partitionConfig);
				//we are using the data property of the panelscontainer to set the partition config attributes
//				var config:Object = new Object();
//				config.id = new String(partitionConfig.@id);
//				config.state = new String(partitionConfig.@state);
//				config.span = new String(partitionConfig.@span);
//				config.dockable = new String(partitionConfig.@dockable);
//				panelsContainer.data = config;
				panelsContainer.data = partitionConfig.attributes();
//				panelsContainer.data.id = new String(partitionConfig.@id);
				panelsContainer.percentHeight = 100;
				if(partitionCfgsCnt == 1 || i == partitionCfgsCnt){
					panelsContainer.percentWidth = remainingSpan;
				}
				else{
					var span:int = parseInt(partitionConfig.@span);
					panelsContainer.percentWidth = span;
					remainingSpan = remainingSpan - span;
				}
				//panelsContainer.creationIndex = i + 1;
				_partitionsContainer.addChild(panelsContainer);
				
				var panelCfgs:XMLList = partitionConfig.panelconfig;
				var panelCfgsCnt:int = panelCfgs.length();
				remainingSpan = 100;
				for(var j:int = 0 ; j < panelCfgsCnt ; j++){
					var panelConfig:XML = panelCfgs[j];
					//var panel:SynPanel = new SynPanel(this);
					var panel:EmbeddedBEVPanel = new EmbeddedBEVPanel(this);
					panel.percentWidth = 100;
					if(panelCfgsCnt == 1 || j == panelCfgsCnt){
						panel.percentHeight = remainingSpan;
					}
					else{
						span = parseInt(panelConfig.@span);
						panel.percentHeight = span;
						remainingSpan = remainingSpan - span;
					}			
					//panel.creationIndex = j + 1; 		
					panelsContainer.addChild(panel);
					_panelList.addItem(panel);
					panel.init(panelConfig);
					_panelIDToPanelRefDict[panel.containerId] = panel;
//					panel.addEventListener(BEVPanelEvent.MINIMIZE, panelMinimizedHandler);
//					panel.addEventListener(BEVPanelEvent.MAXIMIZE, panelMaximizedHandler);
//					panel.addEventListener(BEVPanelEvent.RESTORE, panelRestoredHandler);
//					panel.addEventListener(BEVPanelEvent.CLOSE, panelClosedHandler);
				}				
			}
			//we finally add status bar
			_statusBar = new BEVFooter();
			addChild(_statusBar);
		}
		
		private function getPartitionsContainer(containerConfig:XML):Box{
			var partitionCnt:int = containerConfig.pageconfig.partitionconfig.length();
			var boxContainer:Box = null;
			if(partitionCnt == 1){
				boxContainer = new HBox();
			}
			else{
				boxContainer = new BEVDividedPartition();
				(boxContainer as BEVDividedPartition).liveDragging = true;
			}
			//boxContainer.creationPolicy = ContainerCreationPolicy.QUEUED;
			return boxContainer;
		}
		
		private function getPanelsContainer(partitionConfig:XML):Box{
			var panelCnt:int = partitionConfig.panelconfig.length();
			var boxContainer:Box = null;
			if(panelCnt == 1){
				boxContainer = new VBox();
			}
			else{
				boxContainer = new BEVDividedPanel();
				(boxContainer as BEVDividedPanel).liveDragging = true;
			}
			//boxContainer.creationPolicy = ContainerCreationPolicy.QUEUED;
			return boxContainer;
		}		
		
		/**
		 * Returns the id of the container 
		 */ 
		public function get containerId():String{
			return _containerID;
		}
		
		/**
		 * Returns the name of the container 
		 */ 
		public function get containerName():String{
			return _containerName;
		}
		
		/**
		 * Returns the title of the container 
		 */ 
		public function get containerTitle():String{
			return _containerTitle;
		}
		
		/**
		 * Sets the title of the container 
		 * @param containerTitle The new title of the container 
		 */ 
		public function set containerTitle(containerTitle:String):void{
		 	throw new Error("Unsupported Operation");
		}	
		
		/**
		 * Returns the type of the container
		 */ 
		public function get containerType():String{
			return CONTAINER_TYPE;
		}
		
		/**
		 * Returns the container definition XML 
		 */ 
		public function get containerConfig():XML{
			var pageSetXML:XML = new XML("<pagesconfig></pagesconfig>");
			pageSetXML.@id = _pagesConfig.@id;
			pageSetXML.@name = _pagesConfig.@name;
			pageSetXML.@defaultpage = _pagesConfig.@defaultpage;
			
			var pageXML:XML = new XML("<pageconfig></pageconfig>");
			var pageAttributes:XMLList = _partitionsContainer.data as XMLList;
			for each(var pageAttribute:XML in pageAttributes){
				var pageAttrName:String = pageAttribute.name();
				pageXML["@"+pageAttrName] = pageAttribute;
			}
			
			var panelsContainers:Array = _partitionsContainer.getChildren();
			for(var i:int = 0 ; i < panelsContainers.length ; i++){
				var panelsContainer:Box = panelsContainers[i] as Box;
				var partitionXML:XML = new XML("<partitionconfig></partitionconfig>");
				var partitionAttributes:XMLList = panelsContainer.data as XMLList;
				for each(var partitionAttribute:XML in partitionAttributes){
					var partitionAttrName:String = partitionAttribute.name();
					if(partitionAttrName == "span"){
						var span:int = (panelsContainer.width * 100)/_partitionsContainer.width;
						partitionXML.@span = span+"%";
					}
					else{
						partitionXML["@"+partitionAttrName]= partitionAttribute;
					}
				}
				var panels:Array = panelsContainer.getChildren();
				for(var j:int = 0 ; j < panels.length ; j++){
					var panel:AbstractBEVPanel = panels[j] as AbstractBEVPanel;
					partitionXML.appendChild(panel.containerConfig);
				}
				pageXML.appendChild(partitionXML);
			} 
			
			pageSetXML.appendChild(pageXML);
			return pageSetXML;
		}
		
		/**
		 * Returns the components embedded inside the container
		 */ 
		public function get childComponents():IList{
			return EMPTY_LIST;
		}
		
		/**
		 * Returns the containers embedded inside the container 
		 */ 
		public function get childContainers():IList{
			return new ArrayCollection(_panelList.toArray());
		}
		
		/**
		 * Returns the parent of the container. Can be null
		 */ 
		public function get containerParent():IBEVContainer{
			return null;
		}
		
		/**
		 * Adds a component to the container 
		 * @param component The component to be added
		 * @param widthWeightage The width (colspan) for the component
		 * @param heightWeightage The height (rowspan) for the component
		 */ 
		public function addComponent(component:BEVComponent, widthWeightage: Number = -1,heightWeightage: Number = -1):void{
			throw new Error("Unsupported operation");
		}
		
		/**
		 * Removes a component from the container 
		 * @param component The component to be removed
		 */ 
		public function removeComponent(component:BEVComponent):void{
			throw new Error("Unsupported operation");
		}
				
		/**
		 * Adds a container to the container
		 * @param container The container to be added
		 * @param heightWeightage The height (span) for the container. This is the percentage value
		 * @param widthWeightage The width (span) for the container. This is the percentage value
		 */ 
		public function addContainer(container:IBEVContainer, heightWeightage: Number = 20,widthWeightage: Number = -1):void{
			if(!(container is EmbeddedBEVPanel)){
				Logger.log(DefaultLogEvent.WARNING, "BEVDashboard.addContainer - cannot add a non BEV panel "+container+", ignoring add container request...");
				return;
			}
			var bevPanel:EmbeddedBEVPanel = container as EmbeddedBEVPanel;
			var panelsContainer:Box = null;
			var partitionConfig:XML = null;
			if(_partitionsContainer is HBox){
				//we have only one partition paritioncontainer will contain the panelscontainer  
				panelsContainer = _partitionsContainer.getChildAt(0) as Box;
				partitionConfig = _pagesConfig.pageconfig.partitionconfig[0];
			}
			else if(_partitionsContainer is HDividedBox){
				//we have more then one partition, we will use the last panelscontainer 
				panelsContainer = _partitionsContainer.getChildAt(_partitionsContainer.getChildren().length-1) as Box;
				var partitionConfigs:XMLList = _pagesConfig.pageconfig.partitionconfig;
				partitionConfig = partitionConfigs[partitionConfigs.length()-1]; 
			}
			if(panelsContainer == null){
				Logger.log(DefaultLogEvent.WARNING, "BEVDashboard.addContainer - Could not find a partition to add the "+container+" into, ignoring add container request...");
				return;
			}
			//add the new panel to the panel list
			_panelList.addItem(container);
			//add the panel to the panelid-panel map
			_panelIDToPanelRefDict[container.containerId] = container;
			var containerXML:XML = container.containerConfig;
			containerXML.@span = heightWeightage+"%";
			partitionConfig = compensatePanelHeights(partitionConfig, heightWeightage);
			if(panelsContainer is VBox){
				//trouble
				var existingPanel:EmbeddedBEVPanel = panelsContainer.getChildAt(0) as EmbeddedBEVPanel;
				var newPanelsContainer:VDividedBox = new VDividedBox();
				newPanelsContainer.setStyle("verticalGap", 2);
				newPanelsContainer.liveDragging = true;				
				existingPanel.percentHeight = 100;
				existingPanel.percentWidth = 100;
				newPanelsContainer.addChild(existingPanel);
				_partitionsContainer.removeChild(panelsContainer);
				newPanelsContainer.percentHeight = 100;
				newPanelsContainer.percentWidth = 100;
				_partitionsContainer.addChild(newPanelsContainer);
				//xfer data from old container to the new one
				newPanelsContainer.data = panelsContainer.data;		
				panelsContainer = newPanelsContainer;		
			}
			//we adjust the heights of each panel in the
			for each(var panelCfg:XML in partitionConfig.panelconfig){
				var panelSpan:Number = parseInt(panelCfg.@span);
				var panel:EmbeddedBEVPanel = _panelIDToPanelRefDict[String(panelCfg.@id)];
				panel.percentHeight = panelSpan;
			}				 
			//now we add the new panel
			bevPanel.percentWidth = 100;
			bevPanel.percentHeight = heightWeightage;
			panelsContainer.addChild(bevPanel);
			
			partitionConfig.appendChild(containerXML);
			bevPanel.init();

//			bevPanel.addEventListener(BEVPanelEvent.MINIMIZE, panelMinimizedHandler);
//			bevPanel.addEventListener(BEVPanelEvent.MAXIMIZE, panelMaximizedHandler);
//			bevPanel.addEventListener(BEVPanelEvent.RESTORE, panelRestoredHandler);
//			bevPanel.addEventListener(BEVPanelEvent.CLOSE, panelClosedHandler);			
		}
		
//		private function panelMinimizedHandler(event:BEVPanelEvent):void{
//			var normalPanels:Array = new Array();
//			var panel:EmbeddedBEVPanel = event.target as EmbeddedBEVPanel;
//			var panelParent:DisplayObjectContainer = panel.parent;
//			for(var i:int = 0; i < panelParent.numChildren ; i++){
//				var childPanel:EmbeddedBEVPanel = panelParent.getChildAt(i) as EmbeddedBEVPanel;
//				if(childPanel.state != EmbeddedBEVPanel.MINIMIZED){
//					normalPanels.push(childPanel);
//				}
//			}
//			if(normalPanels.length == 1){
//				var panelToMaximize:EmbeddedBEVPanel  = normalPanels[0] as EmbeddedBEVPanel;
//				panelToMaximize.removeEventListener(BEVPanelEvent.MAXIMIZE,panelMaximizedHandler);
//				panelToMaximize.state = EmbeddedBEVPanel.MAXIMIZED;
//				panelToMaximize.minimizable = false;
//				panelToMaximize.addEventListener(BEVPanelEvent.MAXIMIZE,panelMaximizedHandler);
//			}				
//		}
//		
//		private function panelMaximizedHandler(event:BEVPanelEvent):void{
//			var panel:EmbeddedBEVPanel = event.target as EmbeddedBEVPanel;
//			var panelParent:DisplayObjectContainer = panel.parent;
//			for(var i:int = 0; i < panelParent.numChildren ; i++){
//				var childPanel:EmbeddedBEVPanel = panelParent.getChildAt(i) as EmbeddedBEVPanel;
//				if(childPanel != panel){
//					childPanel.removeEventListener(BEVPanelEvent.MINIMIZE,panelMinimizedHandler);
//					childPanel.state = EmbeddedBEVPanel.MINIMIZED;
//					childPanel.addEventListener(BEVPanelEvent.MINIMIZE,panelMinimizedHandler);
//				}
//			}
//		}
//		
//		private function panelRestoredHandler(event:BEVPanelEvent):void{
//			var panel:EmbeddedBEVPanel = event.target as EmbeddedBEVPanel;
//			var panelParent:DisplayObjectContainer = panel.parent;
//			if(panelParent.numChildren == 1){
//				return;
//			}
//			var idx:int = panelParent.getChildIndex(panel);
//			var nextPnlIdx:int = idx++;
//			if(nextPnlIdx == panelParent.numChildren){
//				nextPnlIdx == idx--;
//			}
//			var childPanel:EmbeddedBEVPanel = panelParent.getChildAt(nextPnlIdx) as EmbeddedBEVPanel;
//			childPanel.removeEventListener(BEVPanelEvent.RESTORE,panelRestoredHandler);
//			childPanel.state = EmbeddedBEVPanel.NORMAL;	
//			childPanel.addEventListener(BEVPanelEvent.RESTORE,panelRestoredHandler);			
//		}
//		
//		private function panelClosedHandler(event:BEVPanelEvent):void{
//			var panel:EmbeddedBEVPanel = event.target as EmbeddedBEVPanel;
//			var panelParent:DisplayObjectContainer = panel.parent;
//			panelParent.removeChild(panel);
//			if(panelParent.numChildren == 1){
//				var onlyPanel:EmbeddedBEVPanel = panelParent.getChildAt(0) as EmbeddedBEVPanel;
//				var newPanelsContainer:VBox = new VBox();
//				newPanelsContainer.setStyle("verticalGap", 2);
//				onlyPanel.percentHeight = 100;
//				onlyPanel.percentWidth = 100;
//				newPanelsContainer.addChild(onlyPanel);
//				var partitionsContainer:DisplayObjectContainer = panelParent.parent;
//				partitionsContainer.removeChild(panelParent);
//				newPanelsContainer.percentHeight = 100;
//				newPanelsContainer.percentWidth = 100;
//				partitionsContainer.addChild(newPanelsContainer);
//			}
//		}		
//				
		private function compensatePanelHeights(partitionConfig:XML,heightWeightage:Number):XML{
			var availableHeightPercentage: Number = 100 - heightWeightage;
			for each(var panelCfg:XML in partitionConfig.panelconfig){
				var panelSpan:Number = parseInt(panelCfg.@span);
				panelSpan = (panelSpan/100 * availableHeightPercentage);
				panelCfg.@span = panelSpan+"%";
			}
			return partitionConfig;
		}
		
		/**
		 * Removes a container from the container
		 * @param container The container to be removed
		 */ 
		public function removeContainer(container:IBEVContainer):void{
			throw new Error("Unsupported Operation");
		}					
		
		/**
		 * Sets the menu configuration XML for the contianer 
		 * @param menuConfig The menu configuration/definition XML 
		 */ 
		public function setInteractions(actionConfig:XML, actionContextProvider:IActionContextProvider):void {
			throw new Error("Unsupported operation");
		}
		
		/**
		 * Searches for a component in the container 
		 * @param name the name of the component to be searched
		 */  
		public function getComponentByName(name:String):BEVComponent{
			return null;
		}
		
//		public function handleResponse(response:ServerResponseEvent):void{
//			if(!isRecipient(response)){ return; }
//			if(response.command == ConfigRequestEvent.GET_PANEL_MENUS_COMMAND){
//				if(response.failMessage != ""){
//					//we do nothing here 
//					EventBus.instance.dispatchEvent(
//						new DefaultLogEvent("WARNING", "Could not retrieve panel menus. " + response.failMessage)
//					);
//				}
//				else{
//				 	var panelMenuXML:XML = response.dataAsXML;
//				 	//run through each panel menu (actionconfig) element
//				 	for(var i:int = 0 ; i < panelMenuXML.element.length() ; i++){
//				 		var panelid:String = new String(panelMenuXML.element[i].@id);
//				 		//fetch the refering panel for the current ID
//				 		var ePanel:EmbeddedBEVPanel = _panelIDToPanelRefDict[panelid];
//				 		if(ePanel != null){
//				 			//set the XML to the panel's menu XML variable
//				 			//it wll then take care of painting the menu
//				 			ePanel.menuConfig = XML(panelMenuXML.element[i].actionconfig[0]);
//				 		}
//				 	}
//				}
//			}
//		}
		
		public function registerListeners():void{
//			EventBus.instance.addEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, handleResponse, this);
		}
		
		public function isRecipient(event:EventBusEvent):Boolean{
			return event.intendedRecipient == this;
		}			
		
	}
}