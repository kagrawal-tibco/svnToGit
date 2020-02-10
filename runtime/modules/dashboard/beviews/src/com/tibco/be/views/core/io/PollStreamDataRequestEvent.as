package com.tibco.be.views.core.io {
	
	import com.tibco.be.views.core.events.io.StreamDataRequestEvent;
	
	import flash.events.Event;

	public class PollStreamDataRequestEvent extends StreamDataRequestEvent {
		
		private var _event:StreamDataRequestEvent;
		
		private var _frequency:Number;
		
		private var _reconnectFrequency:Number;
		
		private var _reconnectAttempts:Number;
		
		public function PollStreamDataRequestEvent(event:StreamDataRequestEvent, serverName:String, portNumber:Number, frequency:Number, reconnectFrequency:Number, reconnectAttempts:Number) {
			super(channelName, command, serverName, portNumber, intendedRecipient);
			this._event = event;
			this.serverName = serverName
			this.portNumber = portNumber;
			this._frequency = frequency;
			this._reconnectFrequency = reconnectFrequency;
			this._reconnectAttempts = reconnectAttempts;
			this.controlChannelUrl = _event.controlChannelUrl;
		}
		
		public function get event():StreamDataRequestEvent { return _event; } ;
		
		public function get frequency():Number { return _frequency; } ;
		
		public function get reconnectFrequency():Number { return _reconnectFrequency; } ;
		
		public function get reconnectAttempts():Number { return _reconnectAttempts; } ;
		
		override public function clone():Event {
			var cloned:PollStreamDataRequestEvent = new PollStreamDataRequestEvent(event,serverName,portNumber, _frequency, _reconnectFrequency, _reconnectAttempts);
			cloned.controlChannelUrl = controlChannelUrl;
			cloned.data = data;
			return cloned;
		}
		
	}
}