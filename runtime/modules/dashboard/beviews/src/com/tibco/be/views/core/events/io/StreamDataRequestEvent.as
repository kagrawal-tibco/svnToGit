package com.tibco.be.views.core.events.io{
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.tasks.ServerRequestEvent;
	
	import flash.events.Event;
	
	
	public class StreamDataRequestEvent extends ServerRequestEvent{
		
		/** Command sent via socket to initialize streaming service at the server */
		public static const OPEN_SOCKET:String = "opensocket";
		
		/** Command to send data via an open stream channel */
		public static const SEND_DATA:String = "senddata";
		
		/** Command sent via the socket to stop streaming service at the server */
		public static const CLOSE_SOCKET:String = "closesocket";
		
		/** Flag used in place of command in failed data responses */
		public static const ERROR:String = "error";
		
		private var _serverName:String;
		private var _portNumber:Number;
		private var _controlChannelURL:String;
		private var _channelName:String;
		private var _data:Object;
		
		public function StreamDataRequestEvent(channelName:String, command:String="", serverName:String="localhost", portNumber:Number=6162, intendedRecipient:Object=null){
			super(command, EventTypes.STREAM_DATA_REQUEST, intendedRecipient);
			_command = command;
			_channelName = channelName;
			_serverName = serverName;
			_portNumber = portNumber;
		}
		
		public function get serverName():String{ return _serverName; }
		public function get portNumber():Number{ return _portNumber; }
		public function get controlChannelUrl():String{ return _controlChannelURL; }
		public function get channelName():String{ return _channelName; }
		public function get data():Object{ return _data; }
		
		public function set serverName(value:String):void{ _serverName = value; }
		public function set portNumber(value:Number):void{ _portNumber = value; }
		public function set controlChannelUrl(value:String):void{ _controlChannelURL = value; }
		public function set data(value:Object):void{ _data = value; }
		
		override public function get logMessage():String{
			return "StreamDataRequest: \n" +
				"\tServer: \"" + _serverName + "\"\n" +
				"\tPort #: " + _portNumber + "\n" + 
				"\tPath: \"" + _controlChannelURL + "\"\n" +
				"\tCommand: \"" + _command + "\"\n\n"; 
		}
		
		override public function clone():Event{
			var cloned:StreamDataRequestEvent = new StreamDataRequestEvent(_channelName, _command, _serverName, _portNumber, _intendedRecipient);
			cloned.controlChannelUrl = _controlChannelURL;
			cloned.data = _data;
			return cloned;
		}
		
		override protected function get details():String{
			return "(streamcontrol) - " + _serverName + ":"+ String(_portNumber) + "/" + _controlChannelURL 
				+ "[command="+_command+",data="+(_data == null ? "" : _data.toString())+"]";  
		}
	}
}