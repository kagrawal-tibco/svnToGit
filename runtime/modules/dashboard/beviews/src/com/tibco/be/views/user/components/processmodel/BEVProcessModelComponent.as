package com.tibco.be.views.user.components.processmodel{
	
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.events.tasks.ConfigRequestEvent;
	import com.tibco.be.views.core.events.tasks.ConfigResponseEvent;
	import com.tibco.be.views.core.kernel.Kernel;
	import com.tibco.be.views.core.ui.actions.IActionContextProvider;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.core.ui.menu.IBEVMenu;
	import com.tibco.be.views.core.utils.IHashedTreeNode;
	import com.tibco.be.views.ui.buttons.ExpandCollapseButton;
	import com.tibco.be.views.user.components.processmodel.event.PMNodeClickEvent;
	import com.tibco.be.views.user.components.processmodel.model.PMConfigProvider;
	import com.tibco.be.views.user.components.processmodel.model.PMDataProvider;
	import com.tibco.be.views.user.components.processmodel.model.PMEdgeConfig;
	import com.tibco.be.views.user.components.processmodel.model.PMNodeConfig;
	import com.tibco.be.views.user.components.processmodel.view.PMEdge;
	import com.tibco.be.views.user.components.processmodel.view.PMNode;
	import com.tibco.be.views.utils.BEVUtils;
	import com.tibco.be.views.utils.Logger;
	
	import flash.events.Event;
	import flash.utils.Dictionary;
	
	import mx.effects.Fade;
	import mx.effects.Parallel;
	import mx.effects.Sequence;
	import mx.events.EffectEvent;
	import mx.events.FlexEvent;
	import mx.managers.CursorManager;

	public class BEVProcessModelComponent extends BEVComponent implements IPMEdgeContainer{
		
		//Request state variables
		protected static const RSV_TARGET_NODE:String = "targetNode";
		
		private var _configXML:XML;
		private var _pmNodeMap:Dictionary;
		private var _configProvider:PMConfigProvider;
		private var _edges:Array;
		private var _dataProvider:PMDataProvider;
		
		public function BEVProcessModelComponent(){
			super();
			_pmNodeMap = new Dictionary();
			_configProvider = new PMConfigProvider();
			_edges = new Array();
			verticalScrollPolicy = "auto";
			horizontalScrollPolicy = "auto";
		}
		
		public function get config():XML{ return _configXML; }
		public function get edges():Array{ return _edges; }
		
		/** Since this is the root container for the Process Model, the parent is null */
		public function get parentPMContainer():IPMEdgeContainer{ return null; }
		
		/**
		 * The reference x value that child PMNodes will use to guide where they're placed. Note
		 * this value will be subtracted from child PMNodes' absolute x coordinate, so negative
		 * values here will create a buffer between the edge of this PM Component and its children.
		*/
		public function get absoluteX():int{ return -5; }
		
		/**
		 * The reference y value that child PMNodes will use to guide where they're placed. Note
		 * this value will be subtracted from child PMNodes' absolute y coordinate, so negative
		 * values here will create a buffer between the top of this PM Component and its children.
		*/
		public function get absoluteY():int{ return -10; }
		
		public function set edges(value:Array):void{ _edges = value; }
		
		public function addEdge(edge:PMEdge):void{
			if(edge == null){ return; }
			_edges.push(edge);
			addChild(edge);
		}
		
		override protected function initiateComponentConfigLoading():void{
			Logger.log(DefaultLogEvent.INFO, "Getting Component Configuration For "+_componentID+" with mode as "+_componentMode);
			var getConfigReq:ConfigRequestEvent = 
				new ConfigRequestEvent(ConfigRequestEvent.GET_COMPONENT_CONFIG_COMMAND, this);
			if(_componentID != null){
				getConfigReq.addXMLParameter("componentid", _componentID);
			}
			if(_componentMode != null){
				getConfigReq.addXMLParameter("mode", _componentMode);
			}
			getConfigReq.addXMLParameter("layoutmode", "external");
			EventBus.instance.dispatchEvent(getConfigReq);
		}
		
		//Called when property componentConfig is set in the parent class 
		override protected function handleConfigSet(compConfigXML:XML):void{
			//clear current view and pmnode view directory
			removeAllChildren();
			_pmNodeMap = new Dictionary();
			
			//Setup the config provider and initialize properties
			_configXML = new XML(compConfigXML);
			_configProvider.setConfig(compConfigXML);
			setStyle("backgroundColor", _configProvider.backgroundColor);
			
			//Build the root nodes of the model. Nodes will recursively build and render themselves.
			for each(var pmNodeCfg:PMNodeConfig in _configProvider.rootNodeConfigs){
				buildAndAddPMNode(this, pmNodeCfg);
			}
			
			//add a listener to this so we can cleanup action listeners
			addEventListener(Event.REMOVED_FROM_STAGE, handleThisRemoved);
		}
		
		//Called when property componentData is set in the parent class
		override protected function handleDataSet(dataXML:XML):void{
			_dataProvider = new PMDataProvider();
			updateData(dataXML);
		}
		
		override public function updateData(componentData:XML):void{
			if(_dataProvider == null){ _dataProvider = new PMDataProvider(); }
			var updatedNodes:Dictionary = new Dictionary();
			for each(var nodeData:XML in componentData.datarow){
				var nodeId:String = new String(nodeData.@id);
				updatedNodes[nodeId] = true;
				_dataProvider.setDataFor(nodeId, nodeData);
				var pmNode:PMNode = _pmNodeMap[nodeId] as PMNode;
				if(pmNode == null){ continue; }
				pmNode.setData(nodeData);
			}
			//clear data from nodes not in the update
			for(var pmNodeId:String in _dataProvider.nodeToDataMap){
				if(updatedNodes[pmNodeId] == undefined){
					//clear view data
					if(_pmNodeMap[pmNodeId] != undefined){
						(_pmNodeMap[pmNodeId] as PMNode).setData(null);
					}
					//clear model data
					_dataProvider.setDataFor(pmNodeId, null);
				}
			}
		}
		
		protected function buildAndAddPMNode(parentComponent:IPMEdgeContainer, nodeConfig:PMNodeConfig, defaultEdgeAlpha:int=1):PMNode{
			//build the current PMNode and add it to the given parent UI Component
			var pmNode:PMNode = new PMNode();
//			pmNode.init();
			pmNode.setConfig(nodeConfig, parentComponent);
			pmNode.addEventListener(FlexEvent.CREATION_COMPLETE, handleNodeCreationComplete);
			pmNode.addEventListener(PMNodeClickEvent.PM_NODE_CLICK_EVENT, handleNodeDataClick);
			parentComponent.addChild(pmNode);
			_pmNodeMap[pmNode.id] = pmNode;
			
			//Build and add the PMEdges associated with this PMNode to the given parent
			buildAndAddPMEdges(parentComponent, pmNode, defaultEdgeAlpha);
			
			//Recursively build and add all descendant PMNodes 
			for each(var child:IHashedTreeNode in _configProvider.getChildrenOf(pmNode.id)){
				buildAndAddPMNode(pmNode, child.payload as PMNodeConfig);
			}
			return pmNode;
		}
		
		protected function buildAndAddPMEdges(parentComponent:IPMEdgeContainer, pmNode:PMNode, defaultAlpha:Number=1):void{
			//This may be confusing... At a data level, the PMNode is the owner of the transitions.
			//However, at the view level, the PMNode's parent UI component actually contains the
			//edges.
			for each(var edgeConfig:PMEdgeConfig in _configProvider.getTransitionsFor(pmNode.id)){
				var edge:PMEdge = new PMEdge(edgeConfig, parentComponent);
				edge.alpha = defaultAlpha;
				parentComponent.addEdge(edge);
			}
			//this function is invoked by the recursive buildAndAddPMNode function, thus all that
			//needs to be done is construction and insertion of the edges for this one PMNode
		}
		
		protected function buildAndPlayUpdateEffects(targetNode:PMNode):void{
			//init effects
			var allEffects:Sequence = new Sequence();
			var nodeClearEffects:Parallel = PMUtils.buildDefaultParallel();
			var edgeRemoveEffects:Parallel = PMUtils.buildDefaultParallel(500);
			var nodeUpdateEffects:Parallel = PMUtils.buildDefaultParallel();
			var nodeChildrenEffects:Parallel = PMUtils.buildDefaultParallel();
			var edgeAddEffects:Parallel = PMUtils.buildDefaultParallel(500);
			
			//build effects
			targetNode.buildClearEffects(nodeClearEffects);
			buildEdgeRemoveEffects(targetNode.parentPMContainer, edgeRemoveEffects);
			for each(var pmNodeConfig:PMNodeConfig in _configProvider.rootNodeConfigs){
				buildNodeUpdateEffects(pmNodeConfig, nodeUpdateEffects);
			}
			targetNode.buildChildrenEffects(nodeChildrenEffects, _dataProvider.getDataFor(targetNode.id));
			buildEdgeAddEffects(targetNode.parentPMContainer, edgeAddEffects);
			if(nodeClearEffects.children.length > 0){ allEffects.addChild(nodeClearEffects); }
			if(edgeRemoveEffects.children.length > 0){ allEffects.addChild(edgeRemoveEffects); }
			if(nodeUpdateEffects.children.length > 0){ allEffects.addChild(nodeUpdateEffects); }
			if(nodeChildrenEffects.children.length > 0){ allEffects.addChild(nodeChildrenEffects); }
			if(edgeAddEffects.children.length > 0){ allEffects.addChild(edgeAddEffects); }
			
			//play effects
			allEffects.addEventListener(EffectEvent.EFFECT_END, handleEffectsFinished);
			allEffects.play();
		}
				
		protected function buildEdgeRemoveEffects(edgeContainer:IPMEdgeContainer, edgeRemoveEffects:Parallel):void{
			if(edgeContainer == null){ return; }
			for each(var edge:PMEdge in edgeContainer.edges){
				var fade:Fade = PMUtils.buildDefaultFadeOut(edge);
				fade.addEventListener(EffectEvent.EFFECT_END, edgeContainer.removeEffectComponent);
				edgeRemoveEffects.addChild(fade);
			}
			//recursively visit parent containers and build remove effects for all edges
			if(edgeContainer.parentPMContainer != null){
				buildEdgeRemoveEffects(edgeContainer.parentPMContainer, edgeRemoveEffects);
			}
		}
		
		/** 
		 * Adds/Updates the passed PMNode and recursively visits all its child nodes to perform
		 * update/add.
		 * @param pmNodeConfig The PMNodeConfig object that represents the PMNode to be added/updated
		 * @param nodeEffects The parent effect for all add/update effects.
		*/
		protected function buildNodeUpdateEffects(pmNodeConfig:PMNodeConfig, nodeEffects:Parallel):void{
			var pmNode:PMNode = _pmNodeMap[pmNodeConfig.id] as PMNode;
			if(pmNode != null){
				pmNode.buildUpdateEffects(pmNodeConfig, nodeEffects);
			}
			else{
				var parentComponent:IPMEdgeContainer = null;
				var parentTreeNode:IHashedTreeNode = _configProvider.getParentOf(pmNodeConfig.id); 
				if(parentTreeNode != null){
					var parentCfg:PMNodeConfig = parentTreeNode.payload as PMNodeConfig;
					if(parentCfg != null){
						parentComponent = _pmNodeMap[parentCfg.id] as PMNode;
					}
				}
				if(parentComponent == null){
					parentComponent = this;
				}
				
				pmNode = buildAndAddPMNode(parentComponent, pmNodeConfig, 0);
				pmNode.alpha = 0;
				if(_dataProvider.hasDataFor(pmNode.id)){
					pmNode.setData(_dataProvider.getDataFor(pmNode.id));
				}
			}
			
			for each(var child:IHashedTreeNode in _configProvider.getChildrenOf(pmNode.id)){
				buildNodeUpdateEffects( _configProvider.getConfigFor(child.id), nodeEffects);
			}
		}
		
		protected function buildEdgeAddEffects(edgeContainer:IPMEdgeContainer, edgeAddEffects:Parallel):void{
			if(edgeContainer == null){ return; }
			
			var edgeConfigSet:Array = new Array();
			var childTreeNodes:Dictionary = null;
			if(edgeContainer is PMNode){
				childTreeNodes = _configProvider.getChildrenOf((edgeContainer as PMNode).id);
			}
			else if(edgeContainer is BEVProcessModelComponent){
				childTreeNodes = _configProvider.roots;
			}
			for each(var childTreeNode:IHashedTreeNode in childTreeNodes){
				for each(var transition:PMEdgeConfig in _configProvider.getTransitionsFor(childTreeNode.id)){
					edgeConfigSet.push(transition);
				}
			}
			
			for each(var edgeConfig:PMEdgeConfig in edgeConfigSet){
				var edge:PMEdge = new PMEdge(edgeConfig, edgeContainer);
				edge.alpha = 0;
				edgeContainer.addEdge(edge);
				var fadeIn:Fade = PMUtils.buildDefaultFadeIn(edge);
				edgeAddEffects.addChild(fadeIn);
			}
			
			//recursively visit parent containers and build add effects for all edges
			if(edgeContainer.parentPMContainer){
				buildEdgeAddEffects(edgeContainer.parentPMContainer, edgeAddEffects);
			}
		}
		
		protected function updateConfigModel(updateXML:XML):void{
			_configXML = new XML(updateXML);
			var processConfig:XML = _configXML.processconfig[0] as XML;
			if(processConfig == null){ return; }
			_configProvider.updateConfig(processConfig);
		}
		
		public function removeEffectComponent(event:EffectEvent):void{
			if(contains(event.target.target)){
				removeChild(event.target.target);  //first target is the effect object
			}
		}
		
		protected function handleNodeCreationComplete(event:FlexEvent):void{
			var pmNode:PMNode = event.target as PMNode;
			if(pmNode == null ){ return; }
			if(pmNode.config.expandable){
				pmNode.addExpandCollapseListener(handleNodeExpansionToggle);
				pmNode.addEventListener(Event.REMOVED_FROM_STAGE, handleNodeRemoved);
			}
			pmNode.removeEventListener(FlexEvent.CREATION_COMPLETE, handleNodeCreationComplete);
		}
		
		protected function handleNodeExpansionToggle(event:Event):void{
			var collapseBtn:ExpandCollapseButton = event.target as ExpandCollapseButton;
			if(collapseBtn == null){ return; }
			CursorManager.setBusyCursor();
			enabled = false;
			var targetNode:PMNode = collapseBtn.parent.parent as PMNode;
			var command:String = collapseBtn.expanded ? ConfigRequestEvent.EXPAND_PROCMODEL_NODE:ConfigRequestEvent.COLLAPSE_PROCMODEL_NODE;
			//note the ExpandCollapseButton will already have toggled so the above is valid
			var layoutReq:ConfigRequestEvent = new ConfigRequestEvent(command, this);
			layoutReq.addXMLParameter("activityid", targetNode.id);
			layoutReq.addXMLParameter("processmodel", _configXML.toString());
			layoutReq.addXMLParameter("layoutmode", "external");
			layoutReq.addStateVariable(RSV_TARGET_NODE, targetNode);
			EventBus.instance.dispatchEvent(layoutReq);
		}
		
		protected function handleNodeDataClick(event:PMNodeClickEvent):void{
			var targetNode:PMNode = event.target as PMNode;
			var targetNodeData:XML = _dataProvider.getDataFor(targetNode.config.id);
			if(targetNodeData != null && targetNodeData.datacolumn != undefined){
				var datacols:XMLList = targetNodeData.datacolumn.(@id == event.dataItemId);
				if(datacols.length() > 0){
					targetNodeData = new XML(datacols[0]);
				}
				else{
					Logger.log(DefaultLogEvent.DEBUG, BEVUtils.getClassName(this) + ".handleNodeDataClick - No data for clicked item.");
					return;
				}
			}
			else{
				Logger.log(DefaultLogEvent.DEBUG, BEVUtils.getClassName(this) + ".handleNodeDataClick - No data for clicked node.");
				return;
			}
	       	var actionCtxProvider:IActionContextProvider = new PMNodeActionContextProvider(targetNode, targetNodeData);
	       	
       		if(targetNode.config.dataActionConfig != null){
       			var menu:IBEVMenu = Kernel.instance.uimediator.menuprovider.getMenuUsingXML(targetNode.config.dataActionConfig, actionCtxProvider);
				menu.show(event.stageX, event.stageY);
       		}
		}
		
		override public function handleResponse(response:ConfigResponseEvent):void{
			switch(response.command){
				case(ConfigRequestEvent.GET_COMPONENT_CONFIG_COMMAND):
				case(ConfigRequestEvent.GET_COMPONENT_DATA_COMMAND):
					return super.handleResponse(response);
				case(ConfigRequestEvent.EXPAND_PROCMODEL_NODE):
				case(ConfigRequestEvent.COLLAPSE_PROCMODEL_NODE):
					handleNodeExpansionToggleResponse(response);
					break;
				default:
					Logger.log(DefaultLogEvent.WARNING, BEVUtils.getClassName(this) + ".handleResponse - Unhandled command: " + response.command);
			}
		}
		
		protected function handleNodeExpansionToggleResponse(response:ConfigResponseEvent):void{
			if(!isRecipient(response)){ return; }
			var targetNode:PMNode = response.getStateVariable(RSV_TARGET_NODE) as PMNode;
			
			updateConfigModel(response.dataAsXML);
			
			if(response.command == ConfigRequestEvent.COLLAPSE_PROCMODEL_NODE){
				//remove all node views from the map
				for each(var id:String in _configProvider.removeChildrenOf(targetNode)){
					delete _pmNodeMap[id];
				}
			}
			
			buildAndPlayUpdateEffects(targetNode);
		}
		
		protected function handleEffectsFinished(event:EffectEvent):void{
			CursorManager.removeBusyCursor();
			enabled = true;
		}
		
		protected function handleNodeRemoved(event:Event):void{
			//Don't want to remove listeners if the target is null (obviously) or if the container
			//(this) is in the process of creating a content pane and moving children to it (causes
			//firing of the REMOVED_FROM_STAGE event). We thus check for the two conditions before
			//finalizing removal of the component and cleaning up after it. These checks may prove
			//problematic as the REMOVED_FROM_STAGE events are dispatched asynchronously from the
			//loop that moves children to the content pane. For now at least, it seems to work.
			var targetPMNode:PMNode = event.currentTarget as PMNode;
			if(targetPMNode == null || creatingContentPane){ return; }
			targetPMNode.removeExpandCollapseListener(handleNodeExpansionToggle);
			targetPMNode.removeEventListener(Event.REMOVED_FROM_STAGE, handleNodeRemoved);
		}
		
		 private function handleThisRemoved(event:Event):void{
			removeEventListener(Event.REMOVED_FROM_STAGE, handleThisRemoved);
		}
		
	}
}
