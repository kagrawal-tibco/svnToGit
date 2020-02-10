package com.tibco.be.views.user.dashboard{
	
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.IEventBusListener;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.events.tasks.ConfigRequestEvent;
	import com.tibco.be.views.core.events.tasks.ServerResponseEvent;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.core.ui.dashboard.BEVComponentFactory;
	import com.tibco.be.views.core.ui.dashboard.IBEVContainer;
	import com.tibco.be.views.ui.IDraggableWrapper;
	import com.tibco.be.views.ui.containers.Flow;
	import com.tibco.be.views.ui.containers.IFlow;
	import com.tibco.be.views.user.components.processmodel.BEVProcessModelComponent;
	import com.tibco.be.views.user.dashboard.events.BEVPanelEvent;
	import com.tibco.be.views.user.utils.UserUtils;
	import com.tibco.be.views.utils.Logger;
	
	import flash.display.DisplayObject;
	import flash.events.MouseEvent;
	import flash.events.TimerEvent;
	import flash.geom.Rectangle;
	import flash.utils.Timer;
	
	import mx.collections.ArrayCollection;
	import mx.collections.IList;
	import mx.containers.Box;
	import mx.controls.Button;
	import mx.core.Container;
	import mx.core.ScrollPolicy;
	import mx.core.UIComponent;
	import mx.events.DragEvent;
	import mx.events.FlexEvent;
	import mx.managers.DragManager;
	import mx.utils.StringUtil;
	
	public class EmbeddedBEVPanel extends AbstractBEVPanel implements IEventBusListener{
		
		private static const LEFT:int = 0;
		private static const RIGHT:int = 1;
		
		private var _panelCfg:XML;		
		private var _componentList:IList;
		private var _flow:IFlow;
		private var _componentsInitialized:Boolean;
		private var _componentIntializingTimer:Timer;
		private var _defaultComponentWidth:int = 200;
		private var _defaultComponentHeight:int = 147;
		
		private var _dropTarget:BEVChartComponentHolder;
		private var _dropPosition:int;
		
		private var _tmpBGColor:uint;
		
		public function EmbeddedBEVPanel(parentContainer:IBEVContainer){
			super(parentContainer);
			_componentList = new ArrayCollection();
			_componentsInitialized = false;
			horizontalScrollPolicy = ScrollPolicy.OFF;
			verticalScrollPolicy = ScrollPolicy.AUTO;
			clipContent = true;
			styleName = HEADER_ENABLED_PANEL_STYLE;
		}
		
		/**
		 * Returns the container definition XML 
		 */ 
		override public function get containerConfig():XML {
			var panelXML:XML = new XML ("<panelconfig></panelconfig>");
			var panelAttributes:XMLList = _panelCfg.attributes();
			for each (var panelAttribute:XML in panelAttributes){
				var panelAttrName:String = panelAttribute.name();
				if(panelAttrName == "span"){
					var span:int = (height * 100)/parent.height;
					panelXML.@span = span+"%";
				}
				else{
					panelXML["@"+panelAttrName]= panelAttribute;
				}
			}
			var layoutCfg:XML = _panelCfg.layoutconfig[0];
			if(layoutCfg != null){
				panelXML.appendChild(layoutCfg);
			}
			for(var i:int = 0 ; i < _componentList.length ; i++){
				var component:BEVComponent = _componentList[i] as BEVComponent;
				var compXML:XML = new XML("<componentconfig></componentconfig>");
				compXML.@id = component.componentId;
				compXML.@name = component.componentName;
				compXML.@title = component.componentTitle;
				compXML.@type = component.componentType;
				if(component.layoutConstraints != null){
					var layoutconstraintsXML:XML = new XML("<layoutconstraints></layoutconstraints>");
					layoutconstraintsXML.@rowspan = component.layoutConstraints.height;
					layoutconstraintsXML.@colspan = component.layoutConstraints.width;
					compXML.appendChild(layoutconstraintsXML);
				}
				panelXML.appendChild(compXML);
			} 
			return panelXML;
		}
		
		override public function get childComponents():IList{ 
			return _componentList; 
		}
		
		/**
		 * Initializes the container 
		 * @param containerCfg The container definition XML,can be null
		 */  
		override public function init(containerCfg:XML = null):void{
			if(containerCfg == null){
				throw new Error("container configuration XML cannot be null");
			}
			_panelCfg = containerCfg;
			_containerID = _panelCfg.@id;
			_containerName = _panelCfg.@title;
			//check for layout
			var layoutCfg:XML = _panelCfg.layoutconfig == undefined ? null:_panelCfg.layoutconfig[0];
			var component:BEVComponent = null;
		 	if(layoutCfg == null){
		 		component = BEVComponentFactory.instance.getComponentByXML(_panelCfg.componentconfig[0]);
 		 		//set the title of the component as the panel title
		 		var title:String = _panelCfg.componentconfig.@title;
		 		if(title == null || StringUtil.trim(title).length == 0){
		 			//component title is not valid, we will use the panel title
		 			title = _panelCfg.@title;
		 		}		 		
				containerTitle = title;
		 		component.componentContainer = this;
		 		component.componentContext = new BEVPanelComponentContext(this, component);
		 		component.percentHeight = 100;
		 		component.percentWidth = 100;
				addChild(component);
		 		_componentList.addItem(component);
		 	}
		 	else{
		 		containerTitle = _panelCfg.@title;
		 		if(layoutCfg.@width != undefined){ _defaultComponentWidth = layoutCfg.@width; }
		 		if(layoutCfg.@height != undefined){ _defaultComponentHeight = layoutCfg.@height; }
		 		
		 		//add the metric panel to this panel as a container
		 		_flow = new Flow(_defaultComponentWidth, _defaultComponentHeight+BEVChartComponentHolder.HEADER_HEIGHT);
		 		for each(var componentconfig:XML in _panelCfg.componentconfig){
		 			styleName = HEADER_DISABLED_PANEL_STYLE;
		 			component = BEVComponentFactory.instance.getComponentByXML(componentconfig);
		 			if(component.layoutConstraints == null){ component.layoutConstraints = new Rectangle(0, 0, 1, 1); }
		 			var componentHolder:BEVChartComponentHolder = new EmbeddedBEVChartComponentHolder(component);
		 			componentHolder.width = component.layoutConstraints.width * _defaultComponentWidth;
		 			componentHolder.height = component.layoutConstraints.height * _defaultComponentHeight;
		 			component.componentContext = componentHolder; 
		 			component.componentContainer = this;
		 			_flow.addChild(componentHolder);
		 			_componentList.addItem(component);
		 		}
		 		_flow.percentHeight = 100;
		 		_flow.percentWidth = 100;
		 		addChild(_flow as DisplayObject);
		 	}
		 	
		 	addEventListener(FlexEvent.CREATION_COMPLETE, handleCreationComplete);
		 	addEventListener(DragEvent.DRAG_ENTER, handleDragEnter);
		 	addEventListener(DragEvent.DRAG_OVER, handleDragOver);
		 	addEventListener(DragEvent.DRAG_EXIT, handleDragExit);
		 	addEventListener(DragEvent.DRAG_DROP, handleDragDrop);
		 	registerListeners();
		 	
			//TODO use the configuration maximizable/minimizable attributes 
			//TODO clean up event listeners when panel is removed			
		}
		
		/**
		 * Searches for a component in the container 
		 * @param name the name of the component to be searched
		 */  
		override public function getComponentByName(name:String):BEVComponent{
			for each (var component:BEVComponent in _componentList){
				if(component.componentName == name){
					return component;
				}
			}
			return null;
		}		
		
		/**
		 * Adds a component to the container 
		 * @param component The component to be added
		 * @param widthWeightage The width (colspan) for the component
		 * @param heightWeightage The height (rowspan) for the component
		 */ 
		override public function addComponent(component:BEVComponent, widthWeightage: Number = -1,heightWeightage: Number = -1):void{
			var layoutCfg:XML = _panelCfg.layoutconfig[0];
		 	if(layoutCfg == null ){
		 		throw new Error("cannot add "+component+" to "+this+", since it does not support more then one component");
		 	}
		 	component.layoutConstraints = new Rectangle(0, 0, widthWeightage, heightWeightage);
 			var componentHolder:BEVChartComponentHolder = new EmbeddedBEVChartComponentHolder(component);
 			componentHolder.width = component.layoutConstraints.width * _defaultComponentWidth;
 			componentHolder.height = component.layoutConstraints.height * _defaultComponentHeight;
 			component.componentContext = componentHolder; 
 			component.componentContainer = this;
 			_componentList.addItem(component);
		 	_flow.addChildAt(componentHolder, 0);
 			component.init();
		}
		
		public function addComponentRelative(ref:DisplayObject, cmp:DisplayObject, offset:int=0):void{
			//Important to note that idx in _componentList will not be the same as idx for Flow
			var i:int = -1;
			if(ref is BEVChartComponentHolder && cmp is BEVChartComponentHolder){
				var refHolder:BEVChartComponentHolder = ref as BEVChartComponentHolder;
				var newHolder:BEVChartComponentHolder = cmp as BEVChartComponentHolder;
				i = _componentList.getItemIndex(refHolder.component);
				_componentList.addItemAt(newHolder.component, Math.max(_componentList.length, Math.min(0,i+offset)));
				_flow.addChildRelative(ref, cmp, offset);
			}
			else if(ref is BEVComponent && cmp is BEVComponent){
				var refComp:BEVComponent = ref as BEVComponent;
				var newComp:BEVComponent = cmp as BEVComponent;
				i = _componentList.getItemIndex(refComp);
				_componentList.addItemAt(newComp, Math.max(_componentList.length, Math.min(0,i+offset)));
				_flow.addChildRelative(
					refComp.componentContext as BEVChartComponentHolder,
					newComp.componentContext as BEVChartComponentHolder,
					offset
				);
			}
			else{
				trace("ERROR: EmbeddedBEVPanel.addComponentRelative - Unsupported parameters!");
				return;
			}
		}
		
		public function moveComponentRelative(ref:DisplayObject, cmp:DisplayObject, offset:int=0):void{
			var i:int = -1;
			if(ref is BEVChartComponentHolder && cmp is BEVChartComponentHolder){
				var refHolder:BEVChartComponentHolder = ref as BEVChartComponentHolder;
				var newHolder:BEVChartComponentHolder = cmp as BEVChartComponentHolder;
				i = _componentList.getItemIndex(newHolder.component);
				_componentList.removeItemAt(i);
				i = Math.max(0, Math.min(_componentList.length, _componentList.getItemIndex(refHolder.component) + offset));
				_componentList.addItemAt(newHolder.component, i);
				_flow.moveChildRelative(ref, cmp, offset);
			}
			else if(ref is BEVComponent && cmp is BEVComponent){
				var refComp:BEVComponent = ref as BEVComponent;
				var newComp:BEVComponent = cmp as BEVComponent;
				i = _componentList.getItemIndex(newComp);
				_componentList.removeItemAt(i);
				i = Math.max(0, Math.min(_componentList.length, _componentList.getItemIndex(refComp) + offset));
				_componentList.addItemAt(newComp, i);
				_flow.moveChildRelative(
					refComp.componentContext as BEVChartComponentHolder,
					newComp.componentContext as BEVChartComponentHolder,
					offset
				);
			}
		}
		
		/**
		 * Adds a component to the container 
		 * @param component The component to be added
		 * @param widthWeightage The width (colspan) for the component
		 * @param heightWeightage The height (rowspan) for the component
		 */ 		
		override public function removeComponent(component:BEVComponent):void{
			//Important to note that idx in _componentList will not be the same as idx for Flow
			if(component == null){ return; }
			var idx:int = _componentList.getItemIndex(component);
			if(idx == -1){
				throw new Error(title+" does not contain "+component.componentTitle);
			}
			var layoutCfg:XML = _panelCfg.layoutconfig[0];
		 	if(layoutCfg == null ){
		 		removeChild(component);
		 	}
		 	else{
		 		_flow.removeChild(component.componentContext as BEVChartComponentHolder);
		 		_componentList.removeItemAt(idx);
		 	}			
		}
		
		private function handleCreationComplete(event:FlexEvent):void{
	 		var idx:int = 0;
		 	_componentIntializingTimer = new Timer(200,_componentList.length);
		 	_componentIntializingTimer.addEventListener(TimerEvent.TIMER,function(event:TimerEvent):void{
		 		//Logger.instance.logA(this,Logger.DEBUG,containerTitle+"::Creation Complete::Firing init on "+idx);
		 		if(_componentList == null || _componentList.length == 0){
		 			log(DefaultLogEvent.WARNING, "Null or empty component list for [" + toString() + "].", "handleCreationComplete");
		 			_componentIntializingTimer.stop();
		 			return;
		 		}
		 		BEVComponent(_componentList.getItemAt(idx)).init();
		 		idx++;
		 	});
		 	_componentIntializingTimer.addEventListener(TimerEvent.TIMER_COMPLETE,function(event:TimerEvent):void{
		 		_componentsInitialized = true;
		 	});
		 	if(_componentsInitialized == false){
				_componentIntializingTimer.start();
			}
		}
		
		private function handleDragEnter(event:DragEvent):void{
			if(_flow == null){ return; }
			var initiator:UIComponent = event.dragInitiator as UIComponent;
			if(initiator is OverlayBEVChartComponentHolder){ return; }
			else if(!(initiator is BEVChartComponentHolder)){
				//gallery or other outside component
				dragStart(event);
			}
			DragManager.acceptDragDrop(this);
		}
		
		private function handleDragOver(event:DragEvent):void{
			if(_flow == null){ return; }
			if(event.dragInitiator is BEVChartComponentHolder){
				//don't allow inter-panel drag-drops when rearranging components
				if(!_flow.contains(event.dragInitiator as BEVChartComponentHolder)){
					DragManager.showFeedback(DragManager.NONE);
					return;
				}
				handleComponentHolderDragOver(event);
			}
			else if(event.dragInitiator is IDraggableWrapper){
				//Modified by Anand on 01/25/2011 to fix BE-10781
				var compWrapper:IDraggableWrapper = event.dragInitiator as IDraggableWrapper;
				var draggedComponent:BEVComponent = compWrapper.content as BEVComponent;
				//check if the dragged component is in the existing component list
				for each(var existingComponent:BEVComponent in _componentList){
					if(existingComponent.componentId == draggedComponent.componentId){
						//yes it is, we do not allow the drop 
						DragManager.showFeedback(DragManager.NONE);
						return;
					}
				}
				//we can accept the drop
				DragManager.showFeedback(DragManager.LINK);
			}
			else{
				//unsupported drag over
			}
		}
		
		private function handleComponentHolderDragOver(event:DragEvent):void{
			var holderBeingDragged:BEVChartComponentHolder = event.dragInitiator as BEVChartComponentHolder;
//			trace("holderBeingDragged = "+holderBeingDragged.title);
			for(var i:int = 0 ; i < _flow.numChildren ; i++){
				var child:BEVChartComponentHolder = _flow.getChildAt(i) as BEVChartComponentHolder;
				if(child == null) continue;
				if(child.hitTestPoint(event.stageX, event.stageY)){
					if(_dropTarget != null){ _dropTarget.removeDragOverEffect(); }
					_dropTarget = child;
					break;
				}
			}
//			trace("drop target = "+(_dropTarget == null ? "none" : _dropTarget.title));
			if(_dropTarget == holderBeingDragged){ return; }
			if(_dropTarget != null){
				DragManager.showFeedback(DragManager.MOVE);
				if(event.localX <= _dropTarget.x+_dropTarget.width/2){
//					trace("Placing \"" + holderBeingDragged.title + "\" to the left of \"" + _dropTarget.title + "\"");
					_dropTarget.showDragOverEffect(BEVChartComponentHolder.DRAG_OVER_LEFT);
					_dropPosition = LEFT;
				}
				else{
//					trace("Placing \"" + holderBeingDragged.title + "\" to the right of \"" + _dropTarget.title + "\"");
					_dropTarget.showDragOverEffect(BEVChartComponentHolder.DRAG_OVER_RIGHT);
					_dropPosition = RIGHT;
				}
			}			
			else {
				DragManager.showFeedback(DragManager.MOVE);
			}
		}
		
		private function handleDragDrop(event:DragEvent):void{
			if(_flow == null){ return; }
//			trace("Panel - Drag Dropped");
			if(event.dragInitiator is BEVChartComponentHolder){
				handleComponentHolderDragDrop(event);
			}
			else if(event.dragInitiator is IDraggableWrapper){
				handleGalleryDragDrop(event);
			}
			else{
				//unsupported drag drop
			}
		}
		
		private function handleComponentHolderDragDrop(event:DragEvent):void{
			if(_dropTarget == null){
				trace("WARNING: EmbeddedBEVPanel.handleDragDrop - NULL Drop Target.");
				return;
			}
			var holderBeingDragged:BEVChartComponentHolder = null;
			try{
				holderBeingDragged = event.dragInitiator as BEVChartComponentHolder;
				if(holderBeingDragged == null){
					trace("ERROR: EmbeddedBEVPanel.handleDragDrop - Dropped non-BEVComponent.");
					return;
				}
				if(holderBeingDragged == _dropTarget){ return; }
				moveComponentRelative(_dropTarget, holderBeingDragged, _dropPosition); 
				_dropTarget.removeDragOverEffect();
				_dropTarget = null;
			}
			catch(error:Error){
				trace("ERROR: EmbeddedBEVPanel.handleDragDrop - " + error.message + "\n" + error.getStackTrace());
				return;
			}
		}
		
		private function handleGalleryDragDrop(event:DragEvent):void{
			dragDone(event);
			
			var compWrapper:IDraggableWrapper = event.dragInitiator as IDraggableWrapper;
			var newComp:BEVComponent = BEVComponentFactory.instance.cloneComponent(compWrapper.content as BEVComponent);
		 	newComp.componentContainer = this;
		 	
		 	var page:IBEVContainer = UserUtils.getParentDashboard(this);
		 	
			//init performed after addComponent request comes back from server
			var parentPage:Box = parent as Box;
			var addRequest:ConfigRequestEvent = new ConfigRequestEvent(ConfigRequestEvent.ADD_COMPONENT_COMMAND, this);
			addRequest.addXMLParameter("pageid", page.containerId); //parentPage.data.id);
			addRequest.addXMLParameter("panelid", this._containerID);
			addRequest.addXMLParameter("componentid", newComp.componentId);
			addRequest.addStateVariable("draggableWrapper", compWrapper);
			EventBus.instance.dispatchEvent(addRequest);
		}
				
		private function handleDragExit(event:DragEvent):void{
//			trace("Panel - Drag Exit");
			if(event.dragInitiator is BEVChartComponentHolder){
				for(var i:int = 0 ; i < _flow.numChildren; i++){
					var child:BEVChartComponentHolder = _flow.getChildAt(i) as BEVChartComponentHolder;
					if(child == null) return;
					child.setStyle("borderStyle", "none");
				}
			}
			else if(event.dragInitiator is IDraggableWrapper){
				dragDone(event);
			}
			else{
				//unsupported drag exit
			}			
		}
		
		private function dragStart(event:DragEvent):void{
			var dropTarget:EmbeddedBEVPanel = event.currentTarget as EmbeddedBEVPanel;
			_tmpBGColor = getStyle("backgroundColor");
			dropTarget.setStyle("backgroundColor", (_tmpBGColor-0x111111));
			//var c:Number = ColorUtil.adjustBrightness2(themeColor, -25);
			_flow.setStyle("borderColor", 0x77DDFF);
		}
		
		private function dragDone(event:DragEvent):void{
			var dropTarget:EmbeddedBEVPanel = event.currentTarget as EmbeddedBEVPanel;
			dropTarget.setStyle("backgroundColor", _tmpBGColor);
			_flow.setStyle("borderColor", _tmpBGColor);
		}
		
		/**
		 * Handles responses from issued configuration commands
		*/
		private function handleResponse(response:ServerResponseEvent):void{
			if(!isRecipient(response)) return;
			var failed:Boolean = false;
			if(response.failMessage != ""){
				log(DefaultLogEvent.WARNING, response.failMessage, "handleResponse");
				failed = true;
			}
			if(response.command == ConfigRequestEvent.ADD_COMPONENT_COMMAND){
				var wrapper:IDraggableWrapper = response.getStateVariable("draggableWrapper") as IDraggableWrapper; 
				var comp:BEVComponent = wrapper == null ? null:wrapper.content as BEVComponent;
				if(comp == null || failed){
					log(DefaultLogEvent.WARNING, "Failed function: " + ConfigRequestEvent.ADD_COMPONENT_COMMAND + ".", "handleResponse");
					wrapper.handleDropComplete(false);
					return;
				}
				var comp2Add:BEVComponent = BEVComponentFactory.instance.cloneComponent(comp);
				var componentHolder:BEVChartComponentHolder = new EmbeddedBEVChartComponentHolder(comp2Add);
			 	comp2Add.componentContext = componentHolder;
			 	comp2Add.componentContainer = this; 
			 	if(comp2Add.layoutConstraints == null && comp.pixelDimensions != null){
			 		var colSpan:int = comp.pixelDimensions.width / _defaultComponentWidth;
					var rowSpan:int = comp.pixelDimensions.height / _defaultComponentHeight;
			 		comp2Add.layoutConstraints = new Rectangle();
				 	comp2Add.layoutConstraints.width = colSpan;
				 	comp2Add.layoutConstraints.height = rowSpan;
			 	}
			 	componentHolder.width = comp2Add.layoutConstraints.width * _defaultComponentWidth;
 				componentHolder.height = comp2Add.layoutConstraints.height * _defaultComponentHeight;
			 	componentHolder.addEventListener(FlexEvent.CREATION_COMPLETE, initGalleryDroppedComp);
			 	_flow.addChild(componentHolder);
				_componentList.addItem(comp2Add);
				wrapper.handleDropComplete(true);
			}
		}
		
		/**
		 * Initing after adding the holder to the flow is necessary to prevent calls to
		 * BEVChartComponentHolderController before the BEVChartComponentHolder has created it. The
		 * function simply calls init on the BEVComponent contained by the event's target
		 * BEVChartComponentHolder.
		 * 
		 * @param event The FlexEvent dispatched when the holder is graphically created.
		*/
		private function initGalleryDroppedComp(event:FlexEvent):void{
			if(event.target is BEVChartComponentHolder){
				var holder:BEVChartComponentHolder = event.target as BEVChartComponentHolder;
				holder.component.init();
				holder.removeEventListener(FlexEvent.CREATION_COMPLETE, initGalleryDroppedComp);
			}
		}
		
		public function registerListeners():void{
			EventBus.instance.addEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, handleResponse, this);
		}
		
		public function isRecipient(event:EventBusEvent):Boolean{
			return event.intendedRecipient == this;
		}
		
		private function log(severity:uint, message:String, functionName:String=""):void{
			Logger.log(severity, "EmbeddedBEVPanel." + functionName + " - " + message);
		}
		
	}
}