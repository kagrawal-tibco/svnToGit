package com.tibco.be.views.core.events.io{
	
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.tasks.ServerResponseEvent;
	
	import flash.events.Event;
			
	public class StreamDataResponseEvent extends ServerResponseEvent{
		
		public static const SERVER_CRASH:int = -1;
		
		private var _channelName:String;
		private var _requestCommand:String;
		private var _rawResponse:Event;
		private var _status:int;
			
		public function StreamDataResponseEvent(respondingChannelName:String, requestCommand:String, intendedRecipient:Object=null){
			super(null, EventTypes.STREAM_DATA_RESPONSE, intendedRecipient);
			_channelName = respondingChannelName;
			_requestCommand = requestCommand;
		}
		
		override protected function get details():String{
			return dataAsString;
		}
		
		public function get channelName():String{ return _channelName; }
		public function get requestCommand():String{ return _requestCommand; }
		public function get rawResponse():Event{ return _rawResponse; }
		public function get status():int{ return _status; }
		
		public function set rawResponse(value:Event):void{ _rawResponse = value; }
		public function set status(value:int):void{ _status = value; }
		
		override public function get command():String{ return _requestCommand; }
		override public function get logMessage():String{
			return "StreamDataResponseMessage:\n" + 
				"\t Channel Name: " + _channelName + "\n" +
				"\t Request Command: " + _requestCommand + "\n" +
				//"\t Raw Response: " + _rawResponse + "\n" +
				"\t Status: " + _status + "\n";// +
				//"\t Data: " + _data+ "\n";
		}
		
		override public function clone():Event{
			var cloned:StreamDataResponseEvent = new StreamDataResponseEvent(_channelName, _requestCommand, _intendedRecipient);
			cloned.status = _status;
			cloned.data = _data;
			return cloned;
		}
		
	}
}