package com.tibco.be.views.user.actions{
	
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.IEventBusListener;
	import com.tibco.be.views.core.events.tasks.ConfigRequestEvent;
	import com.tibco.be.views.core.events.tasks.ConfigResponseEvent;
	import com.tibco.be.views.core.events.tasks.ServerResponseEvent;
	import com.tibco.be.views.core.ui.CommandTypes;
	import com.tibco.be.views.core.ui.actions.AbstractAction;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.user.dashboard.AbstractBEVPanel;
	import com.tibco.be.views.user.dashboard.BEVContentPane;
	import com.tibco.be.views.user.utils.UserUtils;
	
	import mx.controls.Alert;
	import mx.events.CloseEvent;
	import mx.managers.CursorManager;

	public class RemoveComponentAction extends AbstractAction implements IEventBusListener{ //implements IResponseHandle{
		
		private var component:BEVComponent;
		private var panel:AbstractBEVPanel;
		private var dashboard:BEVContentPane;
		
		public function RemoveComponentAction(){
			super();
			registerAction(CommandTypes.DELETE, "command", "removecomponent");
		}
		
		override public function execute(actionContext:ActionContext):void{
			component = actionContext.target as BEVComponent;
			panel = component.componentContainer as AbstractBEVPanel;
			dashboard = UserUtils.getParentDashboard(panel) as BEVContentPane;
			var removeRequest:ConfigRequestEvent = new ConfigRequestEvent(ConfigRequestEvent.REMOVE_COMPONENT_COMMAND, this);
			removeRequest.addXMLParameter("pageid",dashboard.containerId);
			removeRequest.addXMLParameter("panelid",panel.containerId);
			removeRequest.addXMLParameter("componentid",component.componentId);
			CursorManager.setBusyCursor();
			//dashboard.enabled = false;
			registerListeners();
			EventBus.instance.dispatchEvent(removeRequest);						
		}
		
		//public function handleResponse(response:BaseRequestResponse):void{
		public function handleResponse(response:ServerResponseEvent):void{
			if(!isRecipient(response)){ return; }
			if(response.command == ConfigRequestEvent.REMOVE_COMPONENT_COMMAND){
				unRegisterListeners();
				if(response.failMessage != ""){
					Alert.show("Could not remove "+component.componentTitle+" from "+panel.title+"...","Remove Component...",Alert.OK,dashboard,okClicked);
				}
				else{
					//we can remove the component from the panel since it has been done so in the backend
					panel.removeComponent(component);
					CursorManager.removeBusyCursor()
					dashboard.enabled = true;						
				}
			}
		}	
		
		private function okClicked(event:CloseEvent):void{
			CursorManager.removeBusyCursor()
			dashboard.enabled = true;			
		}
			
		override protected function createNewInstance():AbstractAction{
			return new RemoveComponentAction();
		}
		
		
		public function registerListeners():void{
			EventBus.instance.addEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, handleResponse, this);
		}
		public function unRegisterListeners():void{
			EventBus.instance.removeEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, handleResponse);
		}
		public function isRecipient(event:EventBusEvent):Boolean{
			if(event is ConfigResponseEvent){
				var response:ConfigResponseEvent = event as ConfigResponseEvent;
				return response.intendedRecipient == this || response.command == ConfigRequestEvent.REMOVE_COMPONENT_COMMAND;
			}
			return false
		}
					
	}
}