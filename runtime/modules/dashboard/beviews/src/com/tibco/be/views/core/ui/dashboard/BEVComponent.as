package com.tibco.be.views.core.ui.dashboard{
	
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.IEventBusListener;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.events.tasks.ConfigRequestEvent;
	import com.tibco.be.views.core.events.tasks.ConfigResponseEvent;
	import com.tibco.be.views.core.events.tasks.ControlRequestEvent;
	import com.tibco.be.views.core.ui.StreamingUpdatesManager;
	import com.tibco.be.views.core.ui.dashboard.events.ComponentSelectionEvent;
	import com.tibco.be.views.utils.Logger;
	
	import flash.events.Event;
	import flash.geom.Rectangle;
	import flash.utils.Dictionary;
	
	import mx.containers.Canvas;
	import mx.events.FlexEvent;
	
	/** BEVComponent is the base class for all dashboard components in BEViews */ 
	public class BEVComponent extends Canvas implements IEventBusListener{
		
		public static const DASHBOARD_DISPLAY_MODE:int = 0;
		public static const GALLERY_DISPLAY_MODE:int = 1;
		public static const PORTLET_DISPLAY_MODE:int = 2;
		
		protected static const NOT_INITIALIZED: int = -1;
		protected static const CONFIG_LOADED: int = 0;
		protected static const DATA_LOADED: int = 1;
		
		protected static const MANUAL_GET_DATA_REQUEST:String = "manualGetDataReqest";

		private static const COMP_DEF_SHELL:XML =
			<componentconfig 
				id="" 
				name="" 
				title="" 
				type="">
			</componentconfig>;
		
		private static const COMP_DEF_WITH_LAYOUT_CONSTRAINTS_SHELL:XML = 
			<componentconfig 
				id="" 
				name="" 
				title="" 
				type="">
					<layoutconstraints 
						rowspan="" 
						colspan="">
					</layoutconstraints>
			</componentconfig>;
		
		protected var _componentID:String;		
		protected var _componentName:String;		
		protected var _componentTitle:String;
		protected var _componentType:String;
		protected var _componentMode:String;
		protected var _componentHelp:String;
		protected var _layoutConstraints:Rectangle;
		protected var _pixelDimensions:Rectangle;
		
		protected var _container:IBEVContainer;
		protected var _state:int;
		
		protected var _errorView:BEVComponentErrorView;
		protected var _autonomousLoading:Boolean;
		protected var _componentContext:IBEVComponentContext;
		
		protected var _updateQueue:Array;
		protected var _needsToUpdate:Boolean;
		protected var _updateCondensor:IComponentUpdateCompressor;
		
		protected var _displayMode:int;
		
		/** Default Constructor */ 
		public function BEVComponent():void{
			_state = NOT_INITIALIZED;
			_autonomousLoading = true;
			verticalScrollPolicy = "off";
			horizontalScrollPolicy = "off";
			percentWidth = 100;
			percentHeight = 100;
			registerListeners();
		}
		
		public function get componentId():String{ return _componentID;}
		public function get componentName():String{ return _componentName; }
		public function get componentType():String{ return _componentType; }
		public function get componentTitle():String{ return _componentTitle; }
		public function get componentMode():String{ return _componentMode; }
		public function get componentHelp():String{ return _componentHelp; }
		public function get displayMode():int{ return _displayMode; }
		public function get layoutConstraints():Rectangle{ return _layoutConstraints; }
		public function get pixelDimensions():Rectangle{ return _pixelDimensions; }
		public function get componentContainer():IBEVContainer { return _container; }
				
		/**
		 * Returns the current state of autonomous loading. If true then autonomous loading
		 * is on else autonomous loading is off
		 */ 
		public function get autonomousLoading():Boolean{ return _autonomousLoading; }
		
		/**
		 * Returns the component context for this component. BEVComponentContext 
		 * provides means to access the world beyond the component in a systematic 
		 * manner 
		 */ 
		public function get componentContext():IBEVComponentContext{ return _componentContext; }
		
		/**
		 * Returns the internal state of the component. It can be either NOT_INITIALIZED, 
		 * CONFIG_LOADED or DATA_LOADED
		 */ 		
		public function get state():int{ return _state; }
		
		/**
		 * Returns the component definition XML 
		 */ 
		public function get componentDefinition():XML{
			var componentDef:XML = (_layoutConstraints == null) ? COMP_DEF_SHELL.copy():COMP_DEF_WITH_LAYOUT_CONSTRAINTS_SHELL.copy();
			componentDef.@id = _componentID;
			componentDef.@name = _componentName;
			componentDef.@title = _componentTitle;
			componentDef.@type = _componentType;
			if(_layoutConstraints != null){
				componentDef.layoutconstraints.@rowspan = _layoutConstraints.height;
				componentDef.layoutconstraints.@colspan = _layoutConstraints.width;
			}
			return componentDef;
		}
		
		public function set componentId(value:String):void{ _componentID = value; }
 		public function set componentName(value:String):void{ _componentName = value; }
 		public function set componentType(value:String):void{ _componentType = value; }		
		public function set componentTitle(value:String):void{ _componentTitle = value; }
		public function set componentMode(value:String):void{ _componentMode = value; }
		//public function set componentHelp(value:String):void{ _componentHelp = value; }
		public function set displayMode(value:int):void{ _displayMode = value; }
		public function set layoutConstraints(value:Rectangle):void{ _layoutConstraints = value; }
		public function set pixelDimensions(value:Rectangle):void{ _pixelDimensions = value; }
		public function set componentContainer(value:IBEVContainer):void{ _container = value; }		
		
		/**
		 * Sets the autonomous loading for the component. Autonomous loading is on by default. 
		 * If it is turned off, then the component will not attempt to load the configuration and data 
		 * from backend automatically
		 * @param autonomousLoading The autonomous loading flag 
		 */ 
		public function set autonomousLoading(value:Boolean):void{ _autonomousLoading = value; }
		
		public function set componentContext(value:IBEVComponentContext):void{ _componentContext = value; }
		
		public function set componentConfig(componentConfig:XML):void{
			_state = CONFIG_LOADED;
			if(_componentContext != null && componentConfig.actionconfig.length != 0){
				_componentContext.setComponentInteractions(componentConfig.actionconfig[0]);
			}
			_componentHelp = componentConfig.help == undefined ? "":new String(componentConfig.help);
			//Note most properties are set by the BEVComponentFactory when creating an instance
			handleConfigSet(componentConfig);
		}
		
		public function set componentData(componentData:XML):void{
			_state = DATA_LOADED;			
			handleDataSet(componentData);
		}
		
		public function clone():BEVComponent{
			var cloned:BEVComponent = new BEVComponent();
			cloned.componentId = new String(_componentID);
			cloned.componentName = new String(_componentName);
			cloned.componentType = new String(_componentType);
			cloned.componentTitle = new String(_componentTitle);
			cloned.componentMode = new String(_componentMode);
			cloned.displayMode = _displayMode;
			cloned.layoutConstraints = new Rectangle(_layoutConstraints.x, _layoutConstraints.y, _layoutConstraints.width, _layoutConstraints.height);
			cloned.pixelDimensions = new Rectangle(_pixelDimensions.x, _pixelDimensions.y, _pixelDimensions.width, _pixelDimensions.height);
			return cloned;
		}
		
		/** Initializes the component. */
		public function init():void{
			preInit();
			if(_autonomousLoading){
				initiateComponentConfigLoading();		
			}
			addEventListener(FlexEvent.REMOVE, handleComponentRemoval);
		}
		
		/**
		 * Allows the component to initialize itself before the component 
		 * configuration is loaded. Subclasses should override this method
		 */ 
		protected function preInit():void{
			//do nothing
		}
				
		/** Initiates the component config loading */
		protected function initiateComponentConfigLoading():void{
			Logger.log(DefaultLogEvent.INFO, "Getting Component Configuration For "+_componentID+" with mode as "+_componentMode);
			var getConfigReq:ConfigRequestEvent = 
				new ConfigRequestEvent(ConfigRequestEvent.GET_COMPONENT_CONFIG_COMMAND, this);
			if(_componentID != null){
				getConfigReq.addXMLParameter("componentid", _componentID);
			}
			if(_componentMode != null){
				getConfigReq.addXMLParameter("mode", _componentMode);
			}
			EventBus.instance.dispatchEvent(getConfigReq);
		}
		
		/** Initiates the component data loading */
		protected function initiateComponentDataLoading(params:Dictionary=null):void{
			Logger.log(DefaultLogEvent.INFO, "Getting Component Data For "+_componentID+" with mode "+_componentMode);
			var getDataReq:ConfigRequestEvent = 
				new ConfigRequestEvent(ConfigRequestEvent.GET_COMPONENT_DATA_COMMAND, this);
			if(_componentID != null){
				getDataReq.addXMLParameter("componentid", _componentID);
			}
			if(_componentMode != null){
				getDataReq.addXMLParameter("mode", _componentMode);
			}
			for(var key:String in params){
				getDataReq.addStateVariable(key, params[key]);
			}
			EventBus.instance.dispatchEvent(getDataReq);
		}
		
		/** Initiates the component subscription */
		protected function initiateComponentSubscription():void{
			if(StreamingUpdatesManager.instance.isComponentRegistered(this)){ return; }
			StreamingUpdatesManager.instance.registerComponent(this);
			var subscribeReq:ControlRequestEvent =
				new ControlRequestEvent(ControlRequestEvent.SUBSCRIBE_COMMAND, this);
			subscribeReq.addStateVariable("componentName", _componentName);
			EventBus.instance.dispatchEvent(subscribeReq);
		}
		
		protected function initiateComponentPullSubscription():void{
			//TODO: send a subscription request for this component to update via pull-streaming
		}
		
		/** Initiates the component subscription */
		protected function initiateComponentUnSubscription():void{
			StreamingUpdatesManager.instance.unregisterComponent(this);
			var subscribeReq:ControlRequestEvent =
				new ControlRequestEvent(ControlRequestEvent.UNSUBSCRIBE_COMMAND, this);
			subscribeReq.addStateVariable("componentName", _componentName);
			EventBus.instance.dispatchEvent(subscribeReq);
		}
		
		public function handleResponse(respObj:ConfigResponseEvent):void{
			if(!isRecipient(respObj)){ return; }
			if(respObj.command == ConfigRequestEvent.GET_COMPONENT_CONFIG_COMMAND){
				if(respObj.failMessage != ""){
				 	Logger.log(DefaultLogEvent.CRITICAL, "Error loading the component config for " + this + " due to ["+respObj.failMessage+"]");
				 	updateErrorView("Failed to load config due to ["+respObj.failMessage+"]...");
				}
				else{
					_state = CONFIG_LOADED;
					componentConfig = respObj.dataAsXML;
					if(_autonomousLoading){
						initiateComponentDataLoading();
					}
				}
			}
			else if(respObj.command == ConfigRequestEvent.GET_COMPONENT_DATA_COMMAND){
				if(respObj.failMessage != ""){
				 	Logger.log(DefaultLogEvent.CRITICAL, "Error loading the component data for " + this + " due to ["+respObj.failMessage+"]");
				 	updateErrorView("Failed to load data due to ["+respObj.failMessage+"]...");
				}
				else{
					if(respObj.getStateVariable(MANUAL_GET_DATA_REQUEST) == true){
						updateData(respObj.dataAsXML);
						return;
					}
					componentData = respObj.dataAsXML;
					_state = DATA_LOADED;
					if(_autonomousLoading){
						switch(_displayMode){
							case(DASHBOARD_DISPLAY_MODE):
								initiateComponentSubscription();
							case(GALLERY_DISPLAY_MODE):
								//do nothing for now
								break;
							case(PORTLET_DISPLAY_MODE):
								initiateComponentPullSubscription();
								break;
							default:
								Logger.log(DefaultLogEvent.WARNING, "Unknown component display mode: " + _displayMode);
						}
					}
				}
			}
			else if(respObj.command.indexOf(ControlRequestEvent.SUBSCRIBE_COMMAND) > -1){
				if(respObj.failMessage != ""){
				 	Logger.log(DefaultLogEvent.WARNING, "Error subscribing for component updates for " + this + " due to ["+respObj.failMessage+"]");
				 	updateErrorView("Failed to load data due to ["+respObj.failMessage+"]...");
				}
				else{
					//do nothing		
					Logger.log(DefaultLogEvent.INFO, "Successfully Subscribed Component: " + _componentID);			
				}				
			}
			else if(respObj.command == ControlRequestEvent.UNSUBSCRIBE_COMMAND){
				if(respObj.failMessage != ""){
				 	Logger.log(DefaultLogEvent.WARNING, "Error unsubscribing component updates for " + this + " due to ["+respObj.failMessage+"]");
				 	updateErrorView("Failed to load data due to ["+respObj.failMessage+"]...");
				}
				else{
					//do nothing
					Logger.log(DefaultLogEvent.INFO, "Successfully Unsubscribed Component: " + _componentID);
				}				
			}
			else{
				Logger.log(DefaultLogEvent.WARNING, "BEVComponent.handleResponse - Unrecognized Command: " + respObj.command);
			}												
		}

		/**
		 * Handles component config changes. Sub classes should over ride this 
		 * method to parse and handle the component configuration. 
		 * @param oldConfig Represents the configuration of the component before the change, can be null
		 */ 
		protected function handleConfigSet(configXML:XML):void{ /* virtual */ }
		
		/**
		 * Handles component data changes. Sub classes should over ride this 
		 * method to parse and handle the component data. 
		 * @param oldData Represents the data of the component before the change, can be null
		 */ 
		protected function handleDataSet(dataXML:XML):void{ /* virtual */ }
		
		/**
		 * Handles the case where this component is a duplicate of an exisiting component that's
		 * already been registered in the StreamingUpdatesManager.
		*/
		protected function shareStreamingUpdatesWith(registeredComponent:BEVComponent):void{ /*virtual*/ }
		
		/**
		 * Applies new XML data to the component.
		 * 
		 * @param componentData Represents the update data of the component 
		 */ 
		public function updateData(componentData:XML):void{ /* virtual */ }
		
		/**
		 * Pushes data for an update onto the update queue. If _needsToUpdate flag is enabled,
		 * updateData is called.
		 * @param componentData The XML data to use when updating the component
		*/
		public function pushUpdate(componentData:XML):void{
			if(_needsToUpdate){
				_needsToUpdate = false;
				updateData(componentData);
			}
			else{
				_updateQueue.push(componentData);
				if(_updateCondensor != null){
					_updateCondensor.compress(_updateQueue);
				}
			}
		}
		
		public function handleUpdateComplete(event:Event):void{
			//don't update if there's nothing to update with, or if this is being called as the
			//result of the getData operation
			if(_updateQueue.length == 0){
				_needsToUpdate = true;
				return;
			}
			//an update exists, so process it
			try{
				var xmlForUpdate:XML = _updateQueue.shift() as XML;
				updateData(xmlForUpdate);
			}
			catch(error:Error){
				Logger.log(DefaultLogEvent.CRITICAL, "Component.onUpdateComplete - " + error.message);
			}
		}
		
		public function handleComponentRemoval(event:Event):void{ destroy(); }
		
		/**
		 * Reloads the component if autonomous loading is turned on. The level of reload 
		 * depends on the state of the component. If the state is NOT_INITIALIZED, then 
		 * the complete component is reloaded. If the state is CONFIG_LOADED or 
		 * DATA_LOADED then the component data is reloaded. 
		 */ 
		public function refresh():void{
			if(_state == NOT_INITIALIZED){
				initiateComponentConfigLoading();
			}
			else if(_state == CONFIG_LOADED){
				initiateComponentDataLoading();
			}
			else if(_state == DATA_LOADED){
				initiateComponentConfigLoading();
			}
		}
		
		/**
		 * Sends a manual data refresh request to the server. Note: updateData() applies already
		 * received/generated XML data to the component.
		*/
		public function refreshData():void{
			//default is just to refresh, children may override
			var params:Dictionary = new Dictionary();
			params[MANUAL_GET_DATA_REQUEST] = true;
			initiateComponentDataLoading(params);
		}
		
		public function reload():void{
			_state = NOT_INITIALIZED;
			refresh();
		}
		
		/**
		 * Updates the error view with the error message. Removes the component 
		 * artifacts from the real estate and adds a error view
		 * @param errorMsg The error message to be displayed
		 */   
		protected function updateErrorView(errorMsg:String): void{
			if(_errorView != null){
				_errorView.refreshBtn.removeEventListener(FlexEvent.BUTTON_DOWN, handleRefreshBtnClick);
			}
			removeAllChildren();
			if(_errorView == null){
				_errorView = new BEVComponentErrorView();
			}
			addChild(_errorView);
			_errorView.errMsgLbl.text = errorMsg;
			_errorView.refreshBtn.addEventListener(FlexEvent.BUTTON_DOWN, handleRefreshBtnClick);			
		}
		
		/**
		 * Handles the refresh button in error view click. Removes the error view and reloads the 
		 * component 
		 * @event The flex event 
		 */ 
		private function handleRefreshBtnClick(event:FlexEvent):void{
			removeAllChildren();
			this.refresh();
		}
		
		/**
		 * Fires a component selection event 
		 * @param selectionPath the selection path of the component 
		 */ 
		protected function fireComponentSelectionEvent(selectionPath:String):void{
			dispatchEvent(new ComponentSelectionEvent(selectionPath));
		}
		
		/**
		 * Returns a child with in the component navigable by the provided path 
		 * @param path The path of the child in the component 
		 */ 
		public function getChildByPath(path:String):Object {
			return null;
		}
		
		public function registerListeners():void{
			EventBus.instance.addEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, handleResponse, this);
			//Streaming updates handled by StreamingUpdatesManager
		}
		
		public function unregisterListeners():void{
			EventBus.instance.removeEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, handleResponse);
		}
		
		public function isRecipient(event:EventBusEvent):Boolean{
			return event.intendedRecipient == this; 
		}
		
		public function destroy():void{
			StreamingUpdatesManager.instance.unregisterComponent(this);
			EventBus.instance.removeEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, handleResponse);
			removeEventListener(FlexEvent.UPDATE_COMPLETE, handleUpdateComplete);
			removeEventListener(FlexEvent.REMOVE, handleComponentRemoval);
			_componentContext = null;
			_componentID = null;
		}
		
	}
	
}