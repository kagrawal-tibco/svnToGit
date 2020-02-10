package com.tibco.be.views.user.actions{
	
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.IEventBusListener;
	import com.tibco.be.views.core.events.tasks.ConfigRequestEvent;
	import com.tibco.be.views.core.events.tasks.ServerResponseEvent;
	import com.tibco.be.views.core.ui.CommandTypes;
	import com.tibco.be.views.core.ui.actions.AbstractAction;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.dashboard.IBEVContainer;
	
	import flash.display.Sprite;
	
	import mx.controls.Alert;
	import mx.core.ApplicationGlobals;
	import mx.managers.CursorManager;

	public class SaveDashboardAction extends AbstractAction implements IEventBusListener{
		
		
		public function SaveDashboardAction(){
			super();
			registerAction(CommandTypes.SAVE, null, null);
		}
		
		override public function execute(actionContext:ActionContext):void{
			//Code to do save
			//function fetches the layout of all dashboards currently cached
			var dashboards:Array = new Array();
			CursorManager.setBusyCursor();
			registerListeners();
			/*			
			var easeType = mx.transitions.easing.Strong.easeOut;
	
			//show Progress Indicator
			_root.UIHolder.progressIndicator.title = {value:"Save", font:"Arial", color:'FFFFFF', size:10};
			_root.UIHolder.progressIndicator.status = "Saving dashboard layout...";
	
			//show the progress Indicator
			_root.UIHolder.progressIndicator._visible = true;
			_root.UIHolder.obscurer._visible = true;
			var obsATWeen = new mx.transitions.Tween(_root.UIHolder.obscurer, "_alpha", easeType, 0, 70, 1, true);
			var PIATween = new mx.transitions.Tween(_root.UIHolder.progressIndicator, "_alpha", easeType, 0, 100, 1.5, true);
	
			obsATWeen.onMotionFinished = function(item){delete item;};
			//end of progress indicator code
			*/
			
			var container:IBEVContainer = actionContext.target as IBEVContainer;
			/*var layout:XML = container.containerConfig.copy();
			layout.pageconfig.partitionconfig[0].@span = "13%";
			layout.pageconfig.partitionconfig[1].@span = "87%";*/
			var saveLayoutRequest:ConfigRequestEvent = new ConfigRequestEvent(ConfigRequestEvent.SAVE_LAYOUT_COMMAND,this);
			saveLayoutRequest.addXMLParameter("layout", container.containerConfig);
			EventBus.instance.dispatchEvent(saveLayoutRequest);
		}
		

		public function handleResponse(response:ServerResponseEvent):void{
			CursorManager.removeBusyCursor();
			unRegisterListeners();
			if(response.command == ConfigRequestEvent.SAVE_LAYOUT_COMMAND){
				if(response.failMessage != ""){
					Alert.show("Could not connect to server...", "Could not save dashboard layout...", Alert.OK, Sprite(ApplicationGlobals.application));				
				}
				else{
					//var easeType = mx.transitions.easing.Strong.easeOut;
					/*
					_root.UIHolder.progressIndicator.status = "Saved dashboard layout successfully...";
					//hide progressIndicator
					var PITween = new mx.transitions.Tween(_root.UIHolder.progressIndicator, "_alpha", easeType, 100, 0, 1.5, true);
					new mx.transitions.Tween(_root.UIHolder.obscurer, "_alpha", easeType, 70, 0, 1.5, true);
					PITween.onMotionFinished = function(item){
						_root.UIHolder.progressIndicator._visible = false;
						_root.UIHolder.obscurer._visible = false;
						delete item;
					};
					*/
					//Alert.show(String(respObj.responseAsString), "Saved dashboard layout successfully...", Alert.OK, Sprite(ApplicationGlobals.application));
					Alert.show("Saved dashboard layout successfully...", "Info", Alert.OK, Sprite(ApplicationGlobals.application));
				}
			}
		}		
	
		private function saveSucceeded(success:Boolean, strOrXML:Object, stateObj:Object = null):void{
		}
		
		override protected function createNewInstance():AbstractAction{
			return new SaveDashboardAction();
		}		
		
		public function registerListeners():void{
			EventBus.instance.addEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, handleResponse, this);
		}
		public function unRegisterListeners():void{
			EventBus.instance.removeEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, handleResponse);
		}
		public function isRecipient(event:EventBusEvent):Boolean{
			return event.intendedRecipient == this;
		}	
	}
}