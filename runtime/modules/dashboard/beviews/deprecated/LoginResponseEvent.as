package com.tibco.be.views.deprecated{
	
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;

	public class LoginResponseEvent extends EventBusEvent{
		
		public static const SUCCESS:int = 0;
		public static const INVALID_LOGIN:int = 2;
		public static const SERVER_FAIL:int = 3;
		
		/**
		 * Code indicating whether the corresponding login request failed
		*/
		private var _loginStatus:int;
		
		/**
		 * The data result from the login request.
		 */
		private var _data:Object;
		
		/**
		 * The corresponding login request event that generated this response.
		 */
		private var _requestEvent:LoginRequestEvent;
		
		public function LoginResponseEvent(data:Object, requestEvent:LoginRequestEvent=null){
			_data = data;
			_requestEvent = requestEvent;
			_loginStatus = SERVER_FAIL;
			super(EventTypes.LOGIN_RESPONSE, requestEvent.intendedRecipient);
		}
		
		public function get data():Object{ return _data; }
		public function set data(value:Object):void{ _data = data; }
		public function get requestEvent():LoginRequestEvent{ return _requestEvent; }
		public function get status():int{ return _loginStatus; }
		public function set status(value:int):void{ _loginStatus = value; }
		
	}
}