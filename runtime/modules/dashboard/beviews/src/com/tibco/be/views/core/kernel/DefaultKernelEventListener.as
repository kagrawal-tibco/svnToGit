package com.tibco.be.views.core.kernel{
	
	import com.tibco.be.views.core.Session;
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.IEventBusListener;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.events.tasks.ControlRequestEvent;
	import com.tibco.be.views.core.events.tasks.ServerResponseEvent;
	import com.tibco.be.views.core.ui.controls.MessageBox;
	import com.tibco.be.views.utils.Logger;
	
	import flash.display.DisplayObjectContainer;
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	import mx.collections.IList;
	import mx.core.Application;
	import mx.utils.StringUtil;
	
	/**
	 * The DefaultKernelEventListener represents the default implementation of the 
	 * KernelEventListener. The DefaultKernelEventListener initializes the SynLogin to
	 * decide if the log in dialog is to be shown or not. It also handles the login completion
	 * event from SynLogin to load the UIMediator
	 */ 
	public class DefaultKernelEventListener implements IKernelEventListener, IEventBusListener{
		
		private var _username:String = null;
		private var _password:String = null;
		private var _role:String = null;
		private var _stoken:String = null;	
		
		/**
		 * Invoked when the kernel failes to load
		 * @param msg The error message
		 * @param errorEvent The error which causes the kernel loading to fail
		 */ 
		public function errored(msg:String,errorEvent:ServerResponseEvent):void{
			MessageBox.show(
				DisplayObjectContainer(Application.application),
				"Application Error",
				(msg == null) ? errorEvent.failMessage:msg
			);
		}		
		
		/**
		 * Invoked when the kernel has been completely loaded 
		 * @param kernel The kernel which has been completely loaded 
		 * @param bootParams The boot parameters which were use to boot the kernel
		 */ 
		public function loaded(kernel:Kernel, bootParams:Dictionary):void{
			_username = bootParams["username"] == undefined ? null:String(bootParams["username"]);
			_password = bootParams["password"] == undefined ? null:String(bootParams["password"]);
			_role = bootParams["role"] == undefined ? null:String(bootParams["role"]);
			_stoken = bootParams["stoken"] == undefined ? null:String(bootParams["stoken"]);
			//do we have a token 
			if(_stoken != null && StringUtil.trim(_stoken).length != 0){
				registerListeners();
				//we have a token, fire off token verification
				Session.instance.token = bootParams["stoken"];
				var verifyTokenReq:ControlRequestEvent = new ControlRequestEvent(ControlRequestEvent.VERIFY_TOKEN_COMMAND, this);
				EventBus.instance.dispatchEvent(verifyTokenReq);				
			}
			//do we have a user name 
			else if(_username != null && StringUtil.trim(_username).length != 0){
				/* we have a user, fire off user login
				registerListeners();
				var loginReq:ControlRequestEvent = new ControlRequestEvent(ControlRequestEvent.LOGIN_COMMAND,this);
				loginReq.addXMLParameter("username", _username);
				loginReq.addXMLParameter("rawpassword", _password);
				EventBus.instance.dispatchEvent(loginReq);
				//*/	
				MessageBox.show(
					DisplayObjectContainer(Application.application),
					"Application Error","Automated Login Is Not Supported",
					function close():void{ Kernel.instance.loadUIMediator(); }
				);		
			}
			else{
				//we have nothing , let the application handle the system
				Kernel.instance.loadUIMediator();
			}
		 }
		 
		 public function handleResponse(response:ServerResponseEvent):void{
			if(!isRecipient(response)){ return; }
			unregisterListeners();
			if(response.failMessage != ""){
				if(response.request.command == ControlRequestEvent.LOGIN_COMMAND){
					Session.instance.token = null;
					MessageBox.show(
						DisplayObjectContainer(Application.application),
						"Token Verification Error",
						response.failMessage,
						function close():void{ Kernel.instance.loadUIMediator(); }
					);
				}
				else{
					MessageBox.show(
						DisplayObjectContainer(Application.application),
						"Application Error",
						response.failMessage,
						function close():void{ Kernel.instance.loadUIMediator(); }
					);
				}				
			}
			else{
				if(response.command == ControlRequestEvent.VERIFY_TOKEN_COMMAND){
					//Parse the verify response and set session parameters so the UIController will not load the LoginDialog
					var data:XML = response.dataAsXML;
					Session.instance.username = new String(data.username);
					Session.instance.preferredRole = new String(data.preferredrole);
					var roleNameList:XMLList = data..@name;
					var roles:ArrayCollection = new ArrayCollection();
					for each(var roleName:XML in roleNameList){
						roles.addItem(new String(roleName));
					}
					Session.instance.roles = roles;
					Kernel.instance.loadUIMediator();
				}		 		
		 		else{
					Logger.log(
						DefaultLogEvent.WARNING,
						"DefaultKernelEventListener.loaded - Unhandled command: " + response.command
					);		 			
		 		}		 		
		 	}
		 }
		 
		 public function registerListeners():void{
		 	EventBus.instance.addEventListener(EventTypes.CONTROL_COMMAND_RESPONSE, handleResponse, this);
		 }
		 
		 public function isRecipient(event:EventBusEvent):Boolean{
		 	return event.intendedRecipient == this; //no broadcast events
		 }
		 
		 public function unregisterListeners():void{
		 	EventBus.instance.removeEventListener(EventTypes.CONTROL_COMMAND_RESPONSE, handleResponse);
		 }		 
		 
	}
}
