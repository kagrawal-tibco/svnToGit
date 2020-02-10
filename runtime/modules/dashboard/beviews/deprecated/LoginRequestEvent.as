package com.tibco.be.views.deprecated{
	import com.tibco.be.views.core.events.EventTypes;
	
	
	public class LoginRequestEvent extends SimpleTaskRequestEvent{
		
		public static const LOGIN_RESOURCE_NAME:String = "login";
		
		private var _username:String;
		private var _password:String;
		
		public function LoginRequestEvent(username:String, password:String, intendedRecipient:Object=null){
			super(EventTypes.LOGIN_TASK, intendedRecipient);
			_username = username;
			_password = password;
		}
		
		public function get username():String{ return _username; }
		public function get password():String{ return _password; }

	}
}