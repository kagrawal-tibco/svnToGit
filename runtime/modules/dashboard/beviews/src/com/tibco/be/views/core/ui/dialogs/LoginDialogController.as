package com.tibco.be.views.core.ui.dialogs {

	import com.tibco.be.views.core.Configuration;
	import com.tibco.be.views.core.Session;
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.IEventBusListener;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.events.tasks.ConfigRequestEvent;
	import com.tibco.be.views.core.events.tasks.ConfigResponseEvent;
	import com.tibco.be.views.core.events.tasks.ControlRequestEvent;
	import com.tibco.be.views.core.events.tasks.ControlResponseEvent;
	import com.tibco.be.views.core.ui.controls.MessageBox;
	import com.tibco.be.views.utils.Logger;
	
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.MouseEvent;
	import flash.events.TextEvent;
	
	import mx.collections.IList;
	import mx.core.Container;
	import mx.effects.CompositeEffect;
	import mx.effects.Effect;
	import mx.events.EffectEvent;
	import mx.events.FlexEvent;
	import mx.events.ListEvent;
	import mx.events.StateChangeEvent;
	import mx.managers.CursorManager;
	import mx.utils.StringUtil;
	
	
	public class LoginDialogController extends EventDispatcher implements IEventBusListener{
		
		private var _parent:Container;	
		private var _view:LoginDialog;
		private var _initialState:String;
		private var _closingEffect:Effect;
		
		public function LoginDialogController(parent:Container){
			_parent = parent;
			_initialState = LoginDialog.USER_PROMPT_STATE;
		}
		
		public function set initialState(value:String):void{ _initialState = value; }
		
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
			var getConfigReq:ConfigRequestEvent = 
				new ConfigRequestEvent(ConfigRequestEvent.GET_LOGIN_CUSTOMIZATION_COMMAND, this);
			EventBus.instance.dispatchEvent(getConfigReq);
		}
		
		private function configureEffects(effects:Array):void {
			for each (var effect:Effect in effects){
				effect.target = _view;
				if(effect is CompositeEffect){
					configureEffects(CompositeEffect(effect).children);
				}
			}
		}
		
		private function loginDialogCreationHandler(event:FlexEvent):void {
			if(_initialState == LoginDialog.ROLE_SELECT_STATE){
				var rolesComboBoxDP:IList = IList(_view.rolesComboBox.dataProvider);
				rolesComboBoxDP.addItem("Select a role");
				for each(var role:String in Session.instance.roles){
					rolesComboBoxDP.addItem(role);
				}				
				//_loginDialog.rolesformitem.alpha = 1.0;
				_view.rolesComboBox.addEventListener(ListEvent.CHANGE,rolesSelectionHandler);
				_view.prompt = "Select a role to login...";				
			}
			else{
				_view.tb_Username.setFocus();
				_view.tb_Username.addEventListener(TextEvent.TEXT_INPUT, usernameInputHandler);
				_view.loginBtn.addEventListener(MouseEvent.CLICK, loginBtnClickHandler);
				_view.addEventListener(StateChangeEvent.CURRENT_STATE_CHANGING, loginDialogStateChangingHandler);
				_view.addEventListener(StateChangeEvent.CURRENT_STATE_CHANGE, loginDialogStateChangeHandler);			
			}
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
			var loginReq:ControlRequestEvent = new ControlRequestEvent(ControlRequestEvent.LOGIN_COMMAND,this);
			loginReq.addXMLParameter("username", _view.tb_Username.text);
			loginReq.addXMLParameter("rawpassword", _view.tb_Password.text);
			EventBus.instance.dispatchEvent(loginReq);
		}
		
		private function loginDialogStateChangingHandler(event:StateChangeEvent):void{
			if(event.newState == LoginDialog.ROLE_SELECT_STATE){
				_view.loginBtn.removeEventListener(MouseEvent.CLICK, loginBtnClickHandler);
			}
			else if(event.newState == LoginDialog.USER_PROMPT_STATE){
				_view.loginBtn.addEventListener(MouseEvent.CLICK, loginBtnClickHandler);
			}
		}
		
		private function loginDialogStateChangeHandler(event:StateChangeEvent):void{
			if(event.newState == LoginDialog.ROLE_SELECT_STATE){
				var rolesComboBoxDP:IList = IList(_view.rolesComboBox.dataProvider);
				rolesComboBoxDP.addItem("Select a role");
				for each(var role:String in Session.instance.roles){
					rolesComboBoxDP.addItem(role);
				}
				_view.rolesComboBox.addEventListener(ListEvent.CHANGE, rolesSelectionHandler);
				_view.prompt = "Select a role to login...";
			}
			else if(event.newState == LoginDialog.USER_PROMPT_STATE){
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
			if(_view.currentState != LoginDialog.ROLE_SELECT_STATE){
				_view.removeEventListener(StateChangeEvent.CURRENT_STATE_CHANGING, loginDialogStateChangingHandler);
				_view.removeEventListener(StateChangeEvent.CURRENT_STATE_CHANGE, loginDialogStateChangeHandler);			
				_view.loginBtn.removeEventListener(MouseEvent.CLICK, loginBtnClickHandler);
			}
			else{
				_view.rolesComboBox.removeEventListener(ListEvent.CHANGE,rolesSelectionHandler);
			}
			
			//Don't leave hanging listener references in the Event Bus
			EventBus.instance.removeEventListener(EventTypes.CONTROL_COMMAND_RESPONSE, handleControlResponse);
			EventBus.instance.removeEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, handleConfigResponse);
				
			//PopUpManager.removePopUp(_loginDialog);
			_parent.removeChild(_view);
			_view = null;			
			dispatchEvent(new Event(Event.COMPLETE));			
		}
		
		private function effectEndHandler(event:EffectEvent):void {
			fireCompleteEvent();
		}
		
		private function handleConfigResponse(event:ConfigResponseEvent):void{
			if(!isRecipient(event)){ return; }
			if(event.command == ConfigRequestEvent.GET_LOGIN_CUSTOMIZATION_COMMAND){
				if(event.failMessage != ""){
					MessageBox.show(
						null,
						"ERROR",
						"Server Unreachable.",
						null,
						"Please double check that the BEViews Agent is running at the expected address."
					);
					return;
				}
				_view = new LoginDialog();
				if(_closingEffect != null){ configureEffects([_closingEffect]); }
				var loginCustomizationXML:XML = event.dataAsXML;
				_view.title = new String(loginCustomizationXML.@title);
				var imageName:String = new String(loginCustomizationXML.@imageurl);
				if(imageName == ""){
					_view.logoURL = null;
				}
				else{
					_view.logoURL = Configuration.instance.serverBaseURL + imageName;
				}
				_view.currentState = _initialState;
				_view.addEventListener(FlexEvent.CREATION_COMPLETE, loginDialogCreationHandler);
				_parent.addChild(_view);
				return;
			}
			else{
				Logger.log(DefaultLogEvent.WARNING, "LoginDialogController.handleConfigResponse - Unhandled command \"" + event.command + "\"");
			}
		}

		private function handleControlResponse(response:ControlResponseEvent):void{
			if(!isRecipient(response)){ return; }
			CursorManager.removeBusyCursor();
			switch(response.command){
				case(ControlRequestEvent.LOGIN_COMMAND):
					if(response.failMessage != ""){
						if(response.failMessage.indexOf("Failed to authenticate") >= 0){
							_view.tb_Password.text = "";
							_view.prompt = response.failMessage;
						}
						else{
							_view.prompt = "Log-in error. Please try again.";
							Logger.log(DefaultLogEvent.CRITICAL, "LoginDialogController.handleControlResponse - " + response.failMessage);
						}
					}
					else{
						_view.prompt = "User authenticated...";
						var getRolesReq:ControlRequestEvent = new ControlRequestEvent(ControlRequestEvent.GET_ROLES_COMMAND);
						EventBus.instance.dispatchEvent(getRolesReq);
					}
					break;
				case(ControlRequestEvent.GET_ROLES_COMMAND):
					if(response.failMessage != ""){
						_view.prompt = "Could not connect to server, Please try again...";
						Logger.log(DefaultLogEvent.WARNING, "LoginDialogController.handleControlResponse - getRoles: " + response.failMessage);
						return;
					}
					if(Session.instance.roles.length > 1){
						_view.currentState = LoginDialog.ROLE_SELECT_STATE;
					}
					else{
						validateSelectedRole(Session.instance.preferredRole);
					}
					break;
				case(ControlRequestEvent.VALIDATE_ROLE):
					if(response.failMessage != ""){
						_view.prompt = response.failMessage;
					}
					else if(_view.currentState == LoginDialog.ROLE_SELECT_STATE){
						var selectRoleCmd:ControlRequestEvent = new ControlRequestEvent(ControlRequestEvent.SET_ROLE_COMMAND);
						selectRoleCmd.addXMLParameter("role", response.request.getXMLParameter("role"));
						EventBus.instance.dispatchEvent(selectRoleCmd);
					}
					else{
						if(_closingEffect != null){
							_closingEffect.play();
						}
						_view.currentState = LoginDialog.OPENING_DASHBOARD_STATE;
						prepareStreaming();
					}
					break;
				case(ControlRequestEvent.SET_ROLE_COMMAND):
					if(_closingEffect != null){
						_closingEffect.play();	
					}
					_view.currentState = LoginDialog.OPENING_DASHBOARD_STATE;
					if(!Session.instance.streamingChannelCreated){
						prepareStreaming();
					}
					break;
				case(ControlRequestEvent.SET_TIMEOUT + String(Configuration.instance.timeOut)):
					if(response.failMessage != ""){
						Logger.log(DefaultLogEvent.WARNING, "LoginDialogController.handleControlResponse - Failed to set server timeout value.");
					}
					break;
				case(ControlRequestEvent.PREPARE_STREAMING_CONNECTION):
					//ignore
					break;
				case(ControlRequestEvent.START_STREAMING_COMMAND):
					fireCompleteEvent();
					break;
				default:
					Logger.log(DefaultLogEvent.WARNING, "LoginDialogController.handleControlResponse - Unhandled command \"" + response.command + "\"");
			}
						
		}
		
		private function validateSelectedRole(role:String):void{
			//validate the one role
			var validateRoleReq:ControlRequestEvent =
				new ControlRequestEvent(ControlRequestEvent.VALIDATE_ROLE, this);
			validateRoleReq.addXMLParameter("role", role);
			EventBus.instance.dispatchEvent(validateRoleReq); 
		}
		
		private function prepareStreaming():void{
			var prepStreamingReq:ControlRequestEvent = new ControlRequestEvent(
				ControlRequestEvent.PREPARE_STREAMING_CONNECTION,
				this
			);
			EventBus.instance.dispatchEvent(prepStreamingReq);
		}
		
		//IEventListener
		public function registerListeners():void{
			EventBus.instance.addEventListener(EventTypes.CONTROL_COMMAND_RESPONSE, handleControlResponse, this);
			EventBus.instance.addEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, handleConfigResponse, this);
		}
		public function isRecipient(event:EventBusEvent):Boolean{
			return true;
		}
		
	}
}