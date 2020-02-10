package com.tibco.be.views.user.dialogs.businessday{
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.tasks.ServerRequestEvent;
	import com.tibco.be.views.core.events.tasks.ServerResponseEvent;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.user.dashboard.AbstractBEVPanel;
	import com.tibco.be.views.user.dashboard.BEVContentPane;
	import com.tibco.be.views.user.utils.UserUtils;
	
	import flash.display.DisplayObject;
	import flash.events.Event;
	
	import mx.controls.Alert;
	import mx.events.CloseEvent;
	import mx.managers.CursorManager;
	import mx.managers.PopUpManager;
	
	
	public final class BusinessDayEditorController{// implements IResponseHandler {

		private var component:BEVComponent;
		private var panel:AbstractBEVPanel;
		private var dashboard:BEVContentPane;		
			
		private var editorView:BusinessDayEditor;
		private var businessDayEditorGrid:BusinessDayEditorGrid
		
		public function BusinessDayEditorController(component:BEVComponent) {
			this.component = component;
			panel = component.componentContainer as AbstractBEVPanel;
			dashboard = UserUtils.getParentDashboard(panel) as BEVContentPane;
		}
		
		public function init ():void {
			editorView = BusinessDayEditor(PopUpManager.createPopUp(DisplayObject(dashboard), BusinessDayEditor, true));
			editorView.resizeable = false;
			editorView.containerTitle = "Change Business Day Rollover";
			editorView.controlButtonPanel.enableOkButton(false);
			editorView.controlButtonPanel.setOkButtonHandler(editorViewOkButtonHandler);
			editorView.controlButtonPanel.setCancelButtonHandler(editorViewCancelButtonHandler);
			PopUpManager.centerPopUp(editorView);
			 
			var request:ServerRequestEvent = new ServerRequestEvent("dlg/getbusdaymodel",null,this);
			request.addRequestParameter("compid",component.componentId);
			CursorManager.setBusyCursor();
			//PSVRClient.instance.sendRequest(request,this);
			EventBus.instance.dispatchEvent(request);
			
		}
		
		public function handleResponse(response:ServerResponseEvent):void {
			CursorManager.removeBusyCursor();
			if (response.command == "dlg/getbusdaymodel") {
				//if (response.responseStatus != BaseRequestResponse.SUCCESS) {
				if (response.failMessage != ""){
					//we show a alert message
					Alert.show("Could not retrieve business day information for "+component.componentTitle+"...","Business Day Editor...",Alert.OK,dashboard,okClicked);
				}
				else{
					editorView.containerTitle = "Change Business Day Rollover - " + String(response.dataAsXML.comptitle);
					businessDayEditorGrid = new BusinessDayEditorGrid();
					businessDayEditorGrid.percentWidth = 100;
					editorView.gridContainer.addChild(businessDayEditorGrid);
					businessDayEditorGrid.init(response.dataAsXML);
					businessDayEditorGrid.addEventListener(Event.CHANGE, editorViewChangeHandler);
				}
			}
			else if (response.command == "dlg/changebusdaysetting") {
				//if (response.responseStatus != BaseRequestResponse.SUCCESS) {
				if(response.failMessage!= ""){
					//we show a alert message
					Alert.show("Could not save business day information for "+component.componentTitle+"...","Business Day Editor...",Alert.OK,dashboard,okClicked);
				}
				else{
					PopUpManager.removePopUp(editorView);
					CursorManager.setBusyCursor();
					dashboard.enabled = false;
					component.reload();
					//TODO we should wait for the component to finish loading itself 
					CursorManager.removeBusyCursor()
					dashboard.enabled = true;
				}				
			}
		}
		
		private function okClicked(event:CloseEvent):void {
			if (editorView != null) {
				PopUpManager.removePopUp(editorView);
			}
		}
		
		private function editorViewChangeHandler(event:Event):void {
			editorView.controlButtonPanel.enableOkButton(true);
		}			
		
		private function editorViewOkButtonHandler():void {
			var updatedEditorModel:XML = businessDayEditorGrid.updatedEditorModel;
			//var request:BaseRequestResponse = new BaseRequestResponse("dlg/changebusdaysetting");
			var request:ServerRequestEvent = new ServerRequestEvent("dlg/changebusdaysetting");
			request.addRequestParameter("busdaysettings",updatedEditorModel.toXMLString());
			CursorManager.setBusyCursor();
			//PSVRClient.instance.sendRequest(request,this);
			EventBus.instance.dispatchEvent(request);
		}

		private function editorViewCancelButtonHandler():void {
			PopUpManager.removePopUp(editorView);
		
		}
	}
}