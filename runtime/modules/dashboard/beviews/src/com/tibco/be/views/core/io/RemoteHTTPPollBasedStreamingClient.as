package com.tibco.be.views.core.io {
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.io.IOEvent;
	import com.tibco.be.views.core.events.io.StreamDataRequestEvent;
	import com.tibco.be.views.core.events.io.StreamDataResponseEvent;
	import com.tibco.be.views.core.events.tasks.ConfigRequestEvent;
	import com.tibco.be.views.core.events.tasks.ConfigResponseEvent;
	
	import flash.events.TimerEvent;
	import flash.utils.Timer;
	
	public class RemoteHTTPPollBasedStreamingClient extends StreamingClient {
		
		private var _serverName:String;
		private var _serverPort:Number;
		private var _frequency:Number;
		private var _socketActionListener:Object;
		private var _timer:Timer;
		
		private var _reconnectTryFrequency:Number;
		private var _reconnectAttempts:Number;
		private var _reconnectTimer:Timer;
		
		private var _inReconnectMode:Boolean;
		
		public function RemoteHTTPPollBasedStreamingClient(event:PollStreamDataRequestEvent){
			super();
			var httpEvent:PollStreamDataRequestEvent = event as PollStreamDataRequestEvent;
			if (httpEvent == null) {
				throw new Error("Invalid configuration information for remote http poll updates");
			}
			_serverName = httpEvent.serverName;
			_serverPort = httpEvent.portNumber;
			_frequency = httpEvent.frequency;
			_socketActionListener = httpEvent.event.intendedRecipient;
			_timer = new Timer(_frequency);
			_timer.addEventListener(TimerEvent.TIMER, handleTimerEvent);
			
			_inReconnectMode = false;
			_reconnectTryFrequency = httpEvent.reconnectFrequency;
			_reconnectAttempts = httpEvent.reconnectAttempts;
		}
		
		override public function start():void {
			//register listeners 
			registerListeners();
			//fire the StreamDataRequestEvent.OPEN_SOCKET response event 
			sendOpenSocketResponse();
			//start the timer  
			_timer.start();
		}

		private function sendOpenSocketResponse():void {
			//send socket opened response
			var response:StreamDataResponseEvent = new StreamDataResponseEvent(
				channelName,
				StreamDataRequestEvent.OPEN_SOCKET,
				_socketActionListener
			);
			response.status = IOEvent.SUCCESS;
			response.rawResponse = null;
			EventBus.instance.dispatchEvent(response);
		}
				
		override public function stop():void {
			//stop the timer 
			_timer.reset();
			//unregister listeners 
			unregisterListeners();
			//fire the IOEvent.SUCCESS response event for close socket 
			sendCloseEvent(IOEvent.SUCCESS);
		}
		
		override public function registerListeners():void{
			EventBus.instance.addEventListener(EventTypes.STREAM_DATA_REQUEST, handleStreamRequest);
			EventBus.instance.addEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, handleResponse, this);
		}
		
		private function unregisterListeners():void{
			EventBus.instance.removeEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, handleResponse);
		}
		
		override public function isRecipient(event:EventBusEvent):Boolean{
			if(event is StreamDataRequestEvent){
				return (event as StreamDataRequestEvent).command == StreamDataRequestEvent.SEND_DATA;
			}
			return event.intendedRecipient == this;
		}	
		
		public function handleResponse(respEvent:ConfigResponseEvent):void{
			if(!isRecipient(respEvent)){ return; }
			
			if(respEvent.command == ConfigRequestEvent.GET_ALL_COMPONENTS_DATA){
				trace("RemoteHTTPPollBasedStreamingClient::handleResponse()");
				if (respEvent.failMessage != "") {
					//we have a failure to deal with
					//are we in the reconnect mode ? 
					if (_inReconnectMode == false) {
						//stop the standard timer 
						_timer.stop();
						//switch on the reconnect mode 
						_inReconnectMode = true;
						//create a new timer with the reconnect fequency
						_reconnectTimer = new Timer(_reconnectTryFrequency,_reconnectAttempts);
						//attach the timer handler  
						_reconnectTimer.addEventListener(TimerEvent.TIMER,handleTimerEvent);
						//attach the timer complete handler 
						_reconnectTimer.addEventListener(TimerEvent.TIMER_COMPLETE,handleReconnectTimerComplete);
						//start the timer 
						_reconnectTimer.start();
					} 
				}
				else {
//					trace(respEvent.dataAsXML.toXMLString());
					//are we in the reconnect mode ?
					if (_inReconnectMode == true) {
						//yes we are , reset the reconnect timer 
						_reconnectTimer.reset();
						//reset the mode 
						_inReconnectMode = false;
						//restart the standard timer 
						_timer.start();
					}					
					var response:StreamDataResponseEvent = new StreamDataResponseEvent(
						channelName,
						StreamDataRequestEvent.SEND_DATA
						/*broadcast*/
					);
					response.status = IOEvent.SUCCESS;
					response.rawResponse = null;
					response.data = respEvent.dataAsXML;
					EventBus.instance.dispatchEvent(response);
				}				
			}
		}	
		
		private function handleStreamRequest(request:StreamDataRequestEvent):void{
			if(!isRecipient(request)) return;
			//send socket opened response
			var response:StreamDataResponseEvent = new StreamDataResponseEvent(
				channelName,
				StreamDataRequestEvent.SEND_DATA
				/*broadcast*/
			);
			response.status = IOEvent.SUCCESS;
			response.data = <response><state>OK</state><content><![CDATA[<command name="startstreaming"/>]]></content></response>;
			response.rawResponse = null;
			EventBus.instance.dispatchEvent(response);
			EventBus.instance.removeEventListener(EventTypes.STREAM_DATA_REQUEST, handleStreamRequest);			
		}		
		
		private function handleTimerEvent(event:TimerEvent):void {
			trace("RemoteHTTPPollBasedStreamingClient::handleTimerEvent("+event.target+")");
			var updateRequest:ConfigRequestEvent = new ConfigRequestEvent(ConfigRequestEvent.GET_ALL_COMPONENTS_DATA, this);
			EventBus.instance.dispatchEvent(updateRequest);
		}
		
		private function handleReconnectTimerComplete(event:TimerEvent):void {
			trace("RemoteHTTPPollBasedStreamingClient::handleTimerComplete("+event.target+")");
			sendCloseEvent(StreamDataResponseEvent.SERVER_CRASH);
		}
		
		private function sendCloseEvent(status:int):void{
			var response:StreamDataResponseEvent = new StreamDataResponseEvent(
				channelName,
				StreamDataRequestEvent.CLOSE_SOCKET
				/*broadcast*/
			);
			response.status = status;
			EventBus.instance.dispatchEvent(response);
		}
		
	}
}