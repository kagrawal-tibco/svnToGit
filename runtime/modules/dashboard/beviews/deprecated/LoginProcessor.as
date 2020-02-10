package com.tibco.be.views.deprecated{
	import com.tibco.be.views.core.Session;
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.IEventBusListener;
	import com.tibco.be.views.core.events.io.PullDataRequestEvent;
	import com.tibco.be.views.core.events.io.PullDataResponseEvent;
	import com.tibco.be.views.core.events.tasks.LoginRequestEvent;
	import com.tibco.be.views.core.events.tasks.LoginResponseEvent;
	
	
	public class LoginProcessor extends TaskProcessor implements IEventBusListener{
		
		private static const LOGIN_CONTEXT_PATH:String = "insight/";
		private static const LOGIN_RESOURCE_NAME:String = "gettoken.do";
		
		private var _request:LoginRequestEvent;
		private var _response:LoginResponseEvent;
		
		public function LoginProcessor(requestEvent:LoginRequestEvent){
			super();
			_request = requestEvent;
			registerListeners();
		}
		
		override public function process():void{
			var params:Dictionary = new Dictionary();
			params["username"] = _request.username;
			params["password"] = _request.password;
			var loginDataReq:PullDataRequestEvent = new PullDataRequestEvent(this);
			loginDataReq.payload = getRequestXML(params);;
			EventBus.instance.dispatchEvent(loginDataReq);
		}
		
		private function handleLoginResponse(event:PullDataResponseEvent):void{
			//get necessary stuff out of event, then publish new loginResponse event
			_response = new LoginResponseEvent(event.data, _request);
			
			var response:String = event.data as String;
			var iToken:Number = response.indexOf("S:");
			response = response.substring(iToken);
			
			if(event.failed){
				_response.status = LoginResponseEvent.SERVER_FAIL;
			}
			else if(iToken < 0) {
				_response.status = LoginResponseEvent.INVALID_LOGIN; //BaseRequestResponse.APPLICATION_ERROR
			}
			else{
				if(iToken == 0){
					Session.instance.token = response;
				}
				else{ //when context path is: /insight/private/
					Session.instance.token = response.substring(0, response.indexOf("'"));	
				}
				
				_response.status = LoginResponseEvent.SUCCESS; //BaseRequestResponse.SUCCESS
			}
			EventBus.instance.dispatchEvent(_response);
		}
		
		//IEventBusListener
		public function registerListeners():void{
			EventBus.instance.addEventListener(EventTypes.PULL_DATA_RESPONSE, handleLoginResponse, this);
		}
		public function isRecipient(event:EventBusEvent):Boolean{
			return event.intendedRecipient == this;
		}
		
	}
}