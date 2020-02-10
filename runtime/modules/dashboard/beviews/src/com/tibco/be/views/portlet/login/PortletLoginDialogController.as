package com.tibco.be.views.portlet.login{

	import com.tibco.be.views.core.Configuration;
	import com.tibco.be.views.core.Session;
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.IEventBusListener;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.events.tasks.ControlRequestEvent;
	import com.tibco.be.views.core.events.tasks.ControlResponseEvent;
	import com.tibco.be.views.utils.Logger;
	
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.MouseEvent;
	import flash.events.TextEvent;
	
	import mx.collections.IList;
	import mx.effects.CompositeEffect;
	import mx.effects.Effect;
	import mx.events.EffectEvent;
	import mx.events.FlexEvent;
	import mx.events.ListEvent;
	import mx.events.StateChangeEvent;
	import mx.managers.CursorManager;
	import mx.utils.StringUtil;
	
	
	public class PortletLoginDialogController extends EventDispatcher implements IEventBusListener{
		
		private var _view:PortletLoginDialog;
		private var _closingEffect:Effect;
		
		public function PortletLoginDialogController(view:PortletLoginDialog){
			_view = view;
			_view.addEventListener(FlexEvent.CREATION_COMPLETE, PortletLoginDialogCreationHandler);
		}
		
		public function set closingEffect(value:Effect):void{
			if(_closingEffect != null){
				_closingEffect.removeEventListener(EffectEvent.EFFECT_END, effectEndHandler);
			}
			_closingEffect = value;
			if(_view != null){
				configureEffects([_closingEffect]);
			}
			_closingEffect.addEventListener(EffectEvent.EFFECT_END,effectEndHandler);
		}
		
		public function init():void {
			registerListeners();
		}
		
		private function configureEffects(effects:Array):void {
			for each (var effect:Effect in effects){
				effect.target = _view;
				if(effect is CompositeEffect){
					configureEffects(CompositeEffect(effect).children);
				}
			}
		}
		
		private function PortletLoginDialogCreationHandler(event:FlexEvent):void {
			_view.currentState = PortletLoginDialog.USER_PROMPT_STATE;
			_view.tb_Username.setFocus();
			_view.tb_Username.addEventListener(TextEvent.TEXT_INPUT, usernameInputHandler);
			_view.loginBtn.addEventListener(MouseEvent.CLICK, loginBtnClickHandler);
			_view.addEventListener(StateChangeEvent.CURRENT_STATE_CHANGING, PortletLoginDialogStateChangingHandler);
			_view.addEventListener(StateChangeEvent.CURRENT_STATE_CHANGE, PortletLoginDialogStateChangeHandler);			
		}
		
		private function usernameInputHandler(event:TextEvent):void {
			if(StringUtil.trim(event.text).length == 0){
				_view.tb_Username.errorString = "No username specified...";
				return;
			}
			else{
				_view.tb_Username.errorString = "";
			}
		}	
		
		private function loginBtnClickHandler(event:MouseEvent):void {
			if(StringUtil.trim(_view.tb_Username.text).length == 0){
				_view.tb_Username.errorString = "No username specified...";
				_view.tb_Username.setFocus();
				return;
			}
			CursorManager.setBusyCursor();
			var loginReq:ControlRequestEvent = new ControlRequestEvent(ControlRequestEvent.LOGIN_COMMAND, this);
			loginReq.addXMLParameter("username", _view.tb_Username.text);
			loginReq.addXMLParameter("rawpassword", _view.tb_Password.text);
			EventBus.instance.dispatchEvent(loginReq);
		}
		
		private function PortletLoginDialogStateChangingHandler(event:StateChangeEvent):void{
			if(event.newState == PortletLoginDialog.ROLE_SELECT_STATE){
				_view.loginBtn.removeEventListener(MouseEvent.CLICK, loginBtnClickHandler);
			}
			else if(event.newState == PortletLoginDialog.USER_PROMPT_STATE){
				_view.loginBtn.addEventListener(MouseEvent.CLICK, loginBtnClickHandler);
			}
		}
		
		private function PortletLoginDialogStateChangeHandler(event:StateChangeEvent):void{
			if(event.newState == PortletLoginDialog.ROLE_SELECT_STATE){
				var rolesComboBoxDP:IList = IList(_view.rolesComboBox.dataProvider);
				rolesComboBoxDP.addItem("Select a role");
				for each(var role:String in Session.instance.roles){
					rolesComboBoxDP.addItem(role);
				}
				_view.rolesComboBox.addEventListener(ListEvent.CHANGE, rolesSelectionHandler);
				_view.prompt = "Select a role to login...";
			}
			else if(event.newState == PortletLoginDialog.USER_PROMPT_STATE){
				_view.tb_Password.text = "";
				(_view.rolesComboBox.dataProvider as IList).removeAll();
			}
		} 
		 
		private function rolesSelectionHandler(event:ListEvent):void {
			var selectedRole:String = String(_view.rolesComboBox.selectedItem);
			if(selectedRole != "Select a role"){
				CursorManager.setBusyCursor();
				validateSelectedRole(selectedRole);
			}
		}
		
		private function fireCompleteEvent():void {
			if(_view == null){
				dispatchEvent(new Event(Event.COMPLETE));
				return;
			}
			if(_view.currentState != PortletLoginDialog.ROLE_SELECT_STATE){
				_view.removeEventListener(StateChangeEvent.CURRENT_STATE_CHANGING, PortletLoginDialogStateChangingHandler);
				_view.removeEventListener(StateChangeEvent.CURRENT_STATE_CHANGE, PortletLoginDialogStateChangeHandler);			
				_view.loginBtn.removeEventListener(MouseEvent.CLICK, loginBtnClickHandler);
			}
			else{
				_view.rolesComboBox.removeEventListener(ListEvent.CHANGE,rolesSelectionHandler);
			}
			
			//Don't leave hanging listener references in the Event Bus
			EventBus.instance.removeEventListener(EventTypes.CONTROL_COMMAND_RESPONSE, handleControlResponse);
				
			//PopUpManager.removePopUp(_PortletLoginDialog);
//			_parent.removeChild(_view);
			_view = null;			
			dispatchEvent(new Event(Event.COMPLETE));			
		}
		
		private function effectEndHandler(event:EffectEvent):void {
			fireCompleteEvent();
		}

		private function handleControlResponse(response:ControlResponseEvent):void{
			if(!isRecipient(response)){ return; }
			Logger.log(DefaultLogEvent.DEBUG, "Processing control response...");
			CursorManager.removeBusyCursor();
			switch(response.command){
				case(ControlRequestEvent.LOGIN_COMMAND):
					Logger.log(DefaultLogEvent.DEBUG, "\tLogin response...");
					if(response.failMessage != ""){
						if(response.failMessage.indexOf("Failed to authenticate") >= 0){
							_view.tb_Password.text = "";
							_view.prompt = response.failMessage;
						}
						else{
							_view.prompt = "Log-in error. Please try again.";
							Logger.log(DefaultLogEvent.CRITICAL, "PortletLoginDialogController.handleControlResponse - " + response.failMessage);
						}
					}
					else{
						_view.prompt = "User authenticated...";
						var getRolesReq:ControlRequestEvent = new ControlRequestEvent(ControlRequestEvent.GET_ROLES_COMMAND);
						EventBus.instance.dispatchEvent(getRolesReq);
					}
					break;
				case(ControlRequestEvent.GET_ROLES_COMMAND):
					Logger.log(DefaultLogEvent.DEBUG, "\tGet roles response...");
					if(response.failMessage != ""){
						_view.prompt = "Could not connect to server, Please try again...";
						Logger.log(DefaultLogEvent.WARNING, "PortletLoginDialogController.handleControlResponse - getRoles: " + response.failMessage);
						return;
					}
					if(Session.instance.roles.length > 1){
						_view.currentState = PortletLoginDialog.ROLE_SELECT_STATE;
					}
					else{
						validateSelectedRole(Session.instance.preferredRole);
					}
					break;
				case(ControlRequestEvent.VALIDATE_ROLE):
					Logger.log(DefaultLogEvent.DEBUG, "\tValidate role response...");
					if(response.failMessage != ""){
						_view.prompt = response.failMessage;
					}
					else if(_view.currentState == PortletLoginDialog.ROLE_SELECT_STATE){
						var selectRoleCmd:ControlRequestEvent = new ControlRequestEvent(ControlRequestEvent.SET_ROLE_COMMAND);
						selectRoleCmd.addXMLParameter("role", response.request.getXMLParameter("role"));
						EventBus.instance.dispatchEvent(selectRoleCmd);
					}
					else{
						if(_closingEffect != null){
							_closingEffect.play();
						}
						_view.currentState = PortletLoginDialog.OPENING_DASHBOARD_STATE;
						fireCompleteEvent();
					}
					break;
				case(ControlRequestEvent.SET_ROLE_COMMAND):
					Logger.log(DefaultLogEvent.DEBUG, "\tSet role response...");
					if(_closingEffect != null){
						_closingEffect.play();	
					}
					_view.currentState = PortletLoginDialog.OPENING_DASHBOARD_STATE;
					fireCompleteEvent();
					break;
				case(ControlRequestEvent.SET_TIMEOUT + String(Configuration.instance.timeOut)):
					Logger.log(DefaultLogEvent.DEBUG, "\tSet timeout response...");
					if(response.failMessage != ""){
						Logger.log(DefaultLogEvent.WARNING, "PortletLoginDialogController.handleControlResponse - Failed to set server timeout value.");
					}
					break;
				default:
					Logger.log(DefaultLogEvent.WARNING, "PortletLoginDialogController.handleControlResponse - Unhandled command \"" + response.command + "\"");
			}
						
		}
		
		private function validateSelectedRole(role:String):void{
			//validate the one role
			var validateRoleReq:ControlRequestEvent =
				new ControlRequestEvent(ControlRequestEvent.VALIDATE_ROLE, this);
			validateRoleReq.addXMLParameter("role", role);
			EventBus.instance.dispatchEvent(validateRoleReq); 
		}
		
		//IEventListener
		public function registerListeners():void{
			EventBus.instance.addEventListener(EventTypes.CONTROL_COMMAND_RESPONSE, handleControlResponse, this);
		}
		public function isRecipient(event:EventBusEvent):Boolean{
			return true;
		}
		
	}
}