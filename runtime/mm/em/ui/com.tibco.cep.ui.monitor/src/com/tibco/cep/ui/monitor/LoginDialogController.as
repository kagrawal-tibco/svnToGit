package com.tibco.cep.ui.monitor {
	
	import com.tibco.cep.ui.monitor.io.PSVRClient;
	
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.KeyboardEvent;
	import flash.events.MouseEvent;
	import flash.events.TextEvent;
	
	import mx.controls.Spacer;
	import mx.core.Application;
	import mx.core.Container;
	import mx.effects.CompositeEffect;
	import mx.effects.Effect;
	import mx.events.EffectEvent;
	import mx.events.FlexEvent;
	import mx.managers.CursorManager;
	import mx.utils.StringUtil;
	
	
	public class LoginDialogController extends EventDispatcher implements IUpdateable {
		
		private var _parent:Container;
		
		private var _topSpacer:Spacer;
		private var _loginDialog:LoginDialog;
		private var _bottomSpacer:Spacer;
		
		private var _closingEffect:Effect;
		
		public function LoginDialogController(parent:Container) {
			this._parent = parent;
		}
		
		public function set closingEffect(closingEffect:Effect):void {
			if (_closingEffect != null) {
				_closingEffect.removeEventListener(EffectEvent.EFFECT_END,effectEndHandler);
			}
			_closingEffect = closingEffect;
			if (_loginDialog != null) {
				configureEffects([_closingEffect]);
			}
			_closingEffect.addEventListener(EffectEvent.EFFECT_END,effectEndHandler);
		}
		
		private function configureEffects(effects:Array):void {
			for each (var effect : Effect in effects) {
				effect.target = _loginDialog;
				if (effect is CompositeEffect) {
					configureEffects(CompositeEffect(effect).children);
				}
			}
		}
		
		public function init():void {
			_loginDialog = new LoginDialog();
			if (_closingEffect != null) {
				configureEffects([_closingEffect]);
			}
			_loginDialog.addEventListener(FlexEvent.CREATION_COMPLETE, loginDialogCreationHandler);
			//add top and bottom space to keep the login dialog in the center of the application
			_topSpacer = new Spacer();
			_topSpacer.percentHeight = 100;
			_topSpacer.percentWidth = 100;
			_parent.addChild(_topSpacer);
			
			_parent.addChild(_loginDialog);
			
			_bottomSpacer = new Spacer();
			_bottomSpacer.percentHeight = 100;
			_bottomSpacer.percentWidth = 100;
			_parent.addChild(_bottomSpacer);				
		}
		
		private function loginDialogCreationHandler(event:FlexEvent):void {
			_loginDialog.removeEventListener(FlexEvent.CREATION_COMPLETE, loginDialogCreationHandler);
			
			_loginDialog.usernamefld.setFocus();
			//_loginDialog.usernamefld.addEventListener(TextEvent.TEXT_INPUT, usernameInputHandler);
			_loginDialog.loginBtn.addEventListener(MouseEvent.CLICK, loginBtnClickHandler);
			_loginDialog.x = Application.application.width/2 - _loginDialog.width/2;
			_loginDialog.y = Application.application.height/2 - _loginDialog.height/2;
		}	
		
		private function usernameInputHandler(event:TextEvent):void {
			if (StringUtil.trim(event.text).length == 0){
				_loginDialog.usernamefld.errorString = "Invalid username specified...";
				_loginDialog.prompt = "Invalid username specified...";
				_loginDialog.usernamefld.invalidateDisplayList();
				return;
			}
			else {
				_loginDialog.usernamefld.errorString = "";
				_loginDialog.prompt = "";
			}
		}	
		
		private function loginBtnClickHandler(event:MouseEvent):void {
			var prompt:String = null;
			var userNameInput:String = StringUtil.trim(_loginDialog.usernamefld.text); 
			if (userNameInput.length == 0){
				prompt = "Blank username specified...";
			}
			else if (userNameInput.indexOf(" ") != -1){
				prompt = "Invalid username specified...";
			}
			if (prompt != null){
				_loginDialog.prompt = prompt;
				_loginDialog.usernamefld.setFocus();
				_loginDialog.usernamefld.addEventListener(KeyboardEvent.KEY_UP,userNameInputKeyHandler);
			}
			else {
				_loginDialog.prompt = "Authenticating...";							
				CursorManager.setBusyCursor();
				PSVRClient.instance.login(_loginDialog.usernamefld.text,_loginDialog.passwordfld.text,this);
			}
		}
		
		private function userNameInputKeyHandler(event:KeyboardEvent):void {
			_loginDialog.prompt = "";
			_loginDialog.usernamefld.removeEventListener(KeyboardEvent.KEY_UP,userNameInputKeyHandler);
		}		
		
		public function update(operation:String,data:XML):void {
			CursorManager.removeBusyCursor();
			if (_closingEffect != null) {
				_closingEffect.play();
			}
			else {
				fireCompleteEvent();
			}
		}
		
		public function updateFailure(operation:String,message:String, code:uint):void {
			CursorManager.removeBusyCursor();
			if (StringUtil.trim(message).length == 0) {
				message = "Unknown error occurred...";
			}
			_loginDialog.prompt = message;
		}
		
		
		private function fireCompleteEvent():void {
			_loginDialog.loginBtn.removeEventListener(MouseEvent.CLICK, loginBtnClickHandler);
			_parent.removeChild(_topSpacer);
			_parent.removeChild(_loginDialog);
			_parent.removeChild(_bottomSpacer);
			_topSpacer = null;
			_loginDialog = null;
			_bottomSpacer = null;			
			dispatchEvent(new Event(Event.COMPLETE));			
		}
		
		private function effectEndHandler(event:EffectEvent):void {
			fireCompleteEvent();
		}		
		
	}
}