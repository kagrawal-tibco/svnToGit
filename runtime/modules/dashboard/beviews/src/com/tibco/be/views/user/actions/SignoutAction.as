package com.tibco.be.views.user.actions{
	
	import com.tibco.be.views.core.ApplicationContainer;
	import com.tibco.be.views.core.Session;
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.IEventBusListener;
	import com.tibco.be.views.core.events.tasks.ControlRequestEvent;
	import com.tibco.be.views.core.events.tasks.ControlResponseEvent;
	import com.tibco.be.views.core.events.tasks.ServerResponseEvent;
	import com.tibco.be.views.core.kernel.Kernel;
	import com.tibco.be.views.core.ui.CommandTypes;
	import com.tibco.be.views.core.ui.actions.AbstractAction;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.controls.MessageBox;
	import com.tibco.be.views.core.ui.controls.ProgressDialog;
	
	import flash.display.DisplayObjectContainer;
	
	import mx.core.Application;
	import mx.utils.StringUtil;

	public class SignoutAction extends AbstractAction implements IEventBusListener{// implements IResponseHandler{
		
		private var _progressDialog:ProgressDialog;
		private var _respObj:ServerResponseEvent;
		
		public function SignoutAction(){
			super();
			registerAction(CommandTypes.SIGNOUT, null, null);
		}
		
		override public function execute(actionContext:ActionContext):void{
			registerListeners();
			_progressDialog = ProgressDialog.show(DisplayObjectContainer(Application.application), "Sign Out...", executeUserSignOut);
		}
		
		public function executeUserSignOut():void{
			Kernel.instance.uimediator.uicontroller.closeAllWindows();
			_progressDialog.startMainTask("Signing Out "+Session.instance.username+"...");
			//we trigger closing of all child browser windows
			ApplicationContainer.userLoggingOut(Session.instance.username);
			//we trigger the actual sign out
			var signoutReq:ControlRequestEvent = null;
			if(Session.instance.streamingSocketOpen){
				//init signout will close the socket and wait for a socket closed event, then proceed
				//with signout.
				signoutReq = new ControlRequestEvent(ControlRequestEvent.INIT_SIGN_OUT_COMMAND, this);
			}
			else{
				signoutReq = new ControlRequestEvent(ControlRequestEvent.SIGN_OUT_COMMAND, this);
			}
			
			if(signoutReq != null){ EventBus.instance.dispatchEvent(signoutReq); }
		}	
		
		public function handleResponse(respObj:ControlResponseEvent):void{
			if(!isRecipient(respObj)){ return; }
			_respObj = respObj;
			_progressDialog.hide(processResponseObject);
		}
		
		private function processResponseObject():void{
			//remove the progress dialog 
			unRegisterListeners();
			if(_respObj.failMessage != ""){
				//show a alert telling user what happened.
				var msgForUser:String = _respObj.dataAsString;
				if (msgForUser == null || StringUtil.trim(msgForUser).length == 0){
					msgForUser = "could not logout "+Session.instance.username;
					trace("ERROR: SignoutAction.processResponseObject - Can't Logout due to:\n\t" + _respObj.failMessage);
				} 
				MessageBox.show(null, "Sign Out...", msgForUser, errMsgBoxOkHandler);
			}
			else{
				Kernel.instance.reset();				
			}
		}
		
		private function errMsgBoxOkHandler():void{
			Kernel.instance.reset();
		}
		
		override protected function createNewInstance():AbstractAction{
			return new SignoutAction();
		}
		
		public function registerListeners():void{
			EventBus.instance.addEventListener(EventTypes.CONTROL_COMMAND_RESPONSE, handleResponse, this);
		}
		
		private function unRegisterListeners():void{
			EventBus.instance.removeEventListener(EventTypes.CONTROL_COMMAND_RESPONSE, handleResponse);
		}
		
		public function isRecipient(event:EventBusEvent):Boolean{
			return(
				(event is ControlResponseEvent) && 
				(event as ControlResponseEvent).command == ControlRequestEvent.SIGN_OUT_COMMAND
			);
		}			
	}
}