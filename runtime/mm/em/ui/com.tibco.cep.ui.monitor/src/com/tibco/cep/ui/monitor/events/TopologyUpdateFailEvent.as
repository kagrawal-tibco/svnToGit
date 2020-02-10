package com.tibco.cep.ui.monitor.events{
	
	import flash.events.Event;

	public class TopologyUpdateFailEvent extends Event{
		
		private var _operation:String;
		private var _message:String;
		private var _code:int;
		
		public function TopologyUpdateFailEvent(operation:String, message:String, code:uint){
			super(EventTypes.TOPOLOGY_UPDATE_FAIL, true, false);
			_operation = operation;
			_message = message;
			_code = code;
		}
		public function get operation():String{ return _operation; }
		public function get message():String{ return _message; }
		public function get code():int{ return _code; }

	}
}