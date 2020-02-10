package com.tibco.be.views.user.actions{
	
	import com.tibco.be.views.core.Session;
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.IEventBusListener;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.events.tasks.ControlRequestEvent;
	import com.tibco.be.views.core.events.tasks.ControlResponseEvent;
	import com.tibco.be.views.core.events.tasks.ServerResponseEvent;
	import com.tibco.be.views.core.kernel.Kernel;
	import com.tibco.be.views.core.ui.CommandTypes;
	import com.tibco.be.views.core.ui.DynamicParamsResolver;
	import com.tibco.be.views.core.ui.actions.AbstractAction;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.actions.ActionRegistry;
	import com.tibco.be.views.core.ui.actions.IDelegatingAction;
	import com.tibco.be.views.core.ui.controls.MessageBox;
	import com.tibco.be.views.core.ui.controls.ProgressDialog;
	import com.tibco.be.views.utils.Logger;
	
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	
	import mx.controls.ComboBox;
	import mx.core.Application;

	public class SwitchRoleAction extends AbstractAction implements IDelegatingAction, IEventBusListener{
		
		private static const SIGN_OUT_ACTION_CONFIG:XML = <actionconfig command="signout"/>;
		
		private var _currentRole:String;
		private var _currentPageSetID:String;
		private var _newRole:String;
		private var _processedPageSetID:String;
		private var _retry:Boolean;
		private var _progressDialog:ProgressDialog;
		private var _respObj:ServerResponseEvent;	
		private var _actionCtx:ActionContext;	
		private var _taskName:String;
		
		public function SwitchRoleAction(){
			super();
			registerAction(CommandTypes.SWITCH_ROLE, null, null);
			_retry = false;
		}
		
		override public function execute(actionContext:ActionContext):void{
			_actionCtx = actionContext;
			//Store current role within the action for rollback feature 
			_currentPageSetID = Kernel.instance.uimediator.uicontroller.currentPageSetId;
			_currentRole = actionContext.getDynamicParamValue(DynamicParamsResolver.CURRENT_ROLE);
			_newRole = actionContext.getDynamicParamValue(DynamicParamsResolver.NEW_ROLE);
			if(_currentRole == _newRole){ return; }
			_processedPageSetID = null;		
			registerListeners();
			validateRole();
		}
		
		private function validateRole():void{
			var validateReq:ControlRequestEvent = new ControlRequestEvent(ControlRequestEvent.VALIDATE_ROLE, this);
			validateReq.addXMLParameter("role", _newRole);
			EventBus.instance.dispatchEvent(validateReq);
		}
		
		private function handleValidateResponse(response:ControlResponseEvent):void{
			//Show a modal progress dialog
			if(response.failMessage != ""){
				MessageBox.show(DisplayObject(Application.application), "Invalid Role", response.failMessage);
				var roleSelectorCombo:ComboBox = _actionCtx.getDynamicParamObject(DynamicParamsResolver.INVOKING_UI_COMPONENT) as ComboBox;
				if(roleSelectorCombo != null){
					roleSelectorCombo.selectedItem = _currentRole;
				}
				unRegisterListeners();
			}
			else{
				_taskName = "Switching role for " + Session.instance.username;
				_progressDialog = ProgressDialog.show(DisplayObjectContainer(Application.application), "Switch Role...", executeUserSwitchRole);
			}
		}
		
		private function executeUserSwitchRole():void{
			_progressDialog.startMainTask(_taskName, -1);
			var changeRoleReq:ControlRequestEvent =
				new ControlRequestEvent(ControlRequestEvent.SWITCH_ROLE_COMMAND, this);
			changeRoleReq.addXMLParameter("role", _newRole);
			EventBus.instance.dispatchEvent(changeRoleReq);
			//f. If the rollback setRole fails then we should tell the user that the system is not functioning properly and Kernel.instance.uimediator.uicontroller.reset() 
		}	
		
		public function handleResponse(response:ControlResponseEvent):void{
			if(!isRecipient(response)){ return; }
			
			if(response.command == ControlRequestEvent.VALIDATE_ROLE){
				handleValidateResponse(response);
			}
			if(response.command == ControlRequestEvent.SWITCH_ROLE_COMMAND){
				//unregister-all sent as initialization the switch role action. This is that
				//command's response...
				Logger.log(DefaultLogEvent.DEBUG, "SwitchRoleAction.handleResponse - Switch Role unsubscribe all response received.");
			}
			else if(response.command == ControlRequestEvent.SET_ROLE_COMMAND){
				_respObj = response;
				_progressDialog.hide(processResponseObject);
				unRegisterListeners();
			}
		}
		
		private function processResponseObject():void{
			if(_respObj.failMessage != ""){
				if(_retry == false){
					MessageBox.show(
						DisplayObjectContainer(Application.application),
						"Switch Role...",
						"Could not switch role to " + _newRole + " for " + Session.instance.username,
						rollBackSetRole,
						null,
						MessageBox.WARNING_TYPE
					);
				}
				else{
					MessageBox.show(
						DisplayObjectContainer(Application.application),
						"Switch Role...",
						"Could not reset role to " + _currentRole + " for " + Session.instance.username,
						triggerLogOut
					);
				}
			}
			else{
				//The setRole returns successfully, then Kernel.instance.uimediator.uicontroller.reload()
				Kernel.instance.uimediator.uicontroller.currentPageSetId = _processedPageSetID;
				Kernel.instance.uimediator.uicontroller.reload(this);
			}
		}
		
		private function rollBackSetRole():void{
			//we set the retry flag on
			_retry = true;
			//we set currently processed role to be the current working role
			_newRole = _currentRole;
			_processedPageSetID = _currentPageSetID;
			//show dialog 
			_taskName = "Resetting role for " + Session.instance.username + "...";
			_progressDialog = ProgressDialog.show(DisplayObjectContainer(Application.application), "Switch Role..." , executeUserSwitchRole);
		}	
		
		private function triggerLogOut():void{
			ActionRegistry.instance.getAction(SIGN_OUT_ACTION_CONFIG).execute(_actionCtx);
		}					
		
		override protected function createNewInstance():AbstractAction{
			return new SwitchRoleAction();
		}
		
		public function startMainTask(taskName:String,taskUnits:Number = -1):void{
			_progressDialog.startSubTask(taskName, taskUnits);
		}
		
		public function startSubTask(taskName:String,taskUnits:Number = -1):void{
			_progressDialog.startSubTask(taskName, taskUnits);
		}
		
		public function worked(taskUnits:Number):Boolean{
			return _progressDialog.worked(taskUnits);
		}
		
		public function errored(errMsg:String,errorEvent:ServerResponseEvent):void{
			if(_retry == false){
				_progressDialog.hide();
				MessageBox.show(DisplayObjectContainer(Application.application), "Switch Role...", errMsg, rollBackSetRole, null, MessageBox.WARNING_TYPE);
			}
			else{
				MessageBox.show(DisplayObjectContainer(Application.application), "Switch Role...", "Could not reset role to "+_currentRole+" for "+Session.instance.username, triggerLogOut);
			}
		}
		
		public function completedSubTask():void{
			_progressDialog.completedSubTask();
		}
		
		public function completedMainTask():void{
			_progressDialog.completedSubTask();
		}	
		
		public function registerListeners():void{
			EventBus.instance.addEventListener(EventTypes.CONTROL_COMMAND_RESPONSE, handleResponse, this);
		}
		public function unRegisterListeners():void{
			EventBus.instance.removeEventListener(EventTypes.CONTROL_COMMAND_RESPONSE, handleResponse);
		}
		public function isRecipient(event:EventBusEvent):Boolean{
			//listen from broadcasts since validate role request failure is broadcast
			if(event.intendedRecipient == this){ return true; }
			var ctrlRes:ControlResponseEvent = event as ControlResponseEvent;
			if(ctrlRes != null){
				return ctrlRes.intendedRecipient == null && ctrlRes.command == ControlRequestEvent.VALIDATE_ROLE;
			}
			return false;
		}				
	}
}