package com.tibco.be.views.core.events.tasks{
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;
	
	import flash.events.Event;
	
	
	public class ServerResponseEvent extends EventBusEvent{
		
		/**
		 * When the request is successfully processed and a response XML is received from the server
		 */
		public static const SUCCESS:int = 1;

		/**
		 * Indicates that there was an error with the request sent by the caller
		 */
		public static const REQUEST_ERROR:int = 2;

		/**
		 * This is a flex level error, typically an IO error
		 */
		public static const SYSTEM_ERROR:int = 3;

		/**
		 * This error is received if there is any issue at the Presentation Server end
		 */
		public static const APPLICATION_ERROR:int = 4;
		
		protected var _request:ServerRequestEvent;
		protected var _failMsg:String;
		protected var _data:Object;
		
		public function ServerResponseEvent(request:ServerRequestEvent, type:String=null, intendedRecipient:Object=null){
			super((type==null ? EventTypes.SERVER_RESPONSE:type), intendedRecipient);
			_request = request;
			_failMsg = "";
			_data = null;
		}
		
		//Getters
		public function get request():ServerRequestEvent{ return _request; }
		public function get command():String{ return _request == null ? null:_request.command; }
		public function get failMessage():String{ return _failMsg; }
		public function get data():Object{ return _data; }
		public function get dataAsXML():XML{ return _data == null ? null:XML(_data); }
		public function get dataAsString():String{ return _data == null ? "":String(_data); }
		
		//Setters
		public function set request(value:ServerRequestEvent):void{ _request = value; }
		public function set failMessage(value:String):void{ _failMsg = value; }
		public function set data(value:Object):void{ _data = value; }
		
		override public function clone():Event{
			return new ServerResponseEvent(_request, type, _intendedRecipient);
		}
		
		public function getStateVariable(variableName:String):*{ return _request.getStateVariable(variableName); }
		public function containsStateVariable(variableName:String):Boolean{ return _request.containsStateVariable(variableName); }
		public function getRequestParameter(paramName:String):*{ return _request.getRequestParameter(paramName); }
	}
}