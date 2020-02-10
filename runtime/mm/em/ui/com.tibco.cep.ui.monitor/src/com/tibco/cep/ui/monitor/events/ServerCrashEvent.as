package com.tibco.cep.ui.monitor.events{
	
	import flash.events.Event;

	public class ServerCrashEvent extends Event{
	
		private var _code:int;
		private var _message:String;
		
		public function ServerCrashEvent(message:String, code:int){
			super(EventTypes.SERVER_CRASH, true, false);
			_message = message;
			_code = code;
		}
		public function get message():String{ return _message; }
		public function get code():int{ return _code; }
	}
}