package com.tibco.be.views.core.io{
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.io.IOEvent;
	import com.tibco.be.views.core.events.io.StreamDataRequestEvent;
	import com.tibco.be.views.core.events.io.StreamDataResponseEvent;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	
	import flash.events.DataEvent;
	import flash.events.Event;
	import flash.events.IOErrorEvent;
	import flash.events.SecurityErrorEvent;
	import flash.net.XMLSocket;
	
	import mx.controls.Alert;
	
	
	public class RemoteStreamingClient extends StreamingClient{

		private var _dataSocket:XMLSocket;
		private var _serverName:String;
		private var _serverPort:Number;
		private var _socketActionListener:Object;
		
		/**
		 * Flag indicating whether the client initialized the socket closure. Flag is obviously
		 * not an ideal solution, but a better way to detect the origin of the close call is unknown
		 */
		private var _clientIssuedClose:Boolean;
		
		public function RemoteStreamingClient(event:StreamDataRequestEvent){
			super();
			_socketActionListener = event.intendedRecipient;
			_channelName = event.channelName;
			_serverName = event.serverName;
			_serverPort = event.portNumber;
		}
		
		public function get isStreamOpen():Boolean{ return (_dataSocket != null && _dataSocket.connected); }
		
		override public function start():void{
			try{
				registerListeners();
				//a new socket is used at each start to prevent weirdness
				_dataSocket = new XMLSocket();
				_dataSocket.addEventListener(Event.CONNECT , handleConnect);
				_dataSocket.addEventListener(DataEvent.DATA , handleData);
				_dataSocket.addEventListener(SecurityErrorEvent.SECURITY_ERROR , handleSecurityErrror);
				_dataSocket.addEventListener(IOErrorEvent.IO_ERROR , handleIOError);
				_dataSocket.addEventListener(Event.CLOSE, handleClose);
				_dataSocket.connect(_serverName, _serverPort);
				//StreamDataResponse sent in connect event handler
			}
			catch(err:Error){
				Alert.show("RemoteStreamingClient: " + err.message, "ERROR");
			}
		}
		
		override public function stop():void{
			if(_dataSocket == null || !_dataSocket.connected){
				log(DefaultLogEvent.WARNING, "RemoteStreamingClient.stop - Invalid state for stop call.");
				return;
			}
			_clientIssuedClose = true;
			unregisterListeners();
			closeSocket();
			//sendCloseEvent done in socket.close event handler (handleClose)
		}

		override public function reset():void{
			throw new Error("Unimplemented Function: RemoteStreamingClient.reset");
		}
		
		/**
		 * Handles data stream connection event
		 * @param event The connect event
		 */ 
		override public function handleConnect(event:Event):void {
			//send socket opened response
			var response:StreamDataResponseEvent = new StreamDataResponseEvent(
				channelName,
				StreamDataRequestEvent.OPEN_SOCKET,
				_socketActionListener
			);
			response.status = IOEvent.SUCCESS;
			response.rawResponse = event;
			EventBus.instance.dispatchEvent(response);
		}
		
		/**
		 * Handles data messages received on the data stream socket.
		 * @param event The received data event
		 */ 
		override public function handleData(event:Event):void {
			var dataEvent:DataEvent = event as DataEvent;
			if(dataEvent == null){
				handleError(event, "RemoteStreamingClient.handleData - Invalid event!");
			}
			//broadcast data received response
			var response:StreamDataResponseEvent = new StreamDataResponseEvent(
				channelName,
				StreamDataRequestEvent.SEND_DATA
				/*broadcast*/
			);
			response.status = IOEvent.SUCCESS;
			response.rawResponse = event;
			response.data = dataEvent.data;
			EventBus.instance.dispatchEvent(response);
		}

		/**
		 * Handles data stream IO errors.
		 * @param event The IOErrorEvent
		 */ 
		override public function handleIOError(event:IOErrorEvent):void {
			handleError(event, "IOError: "+_serverName+" on "+_serverPort+" due to '"+event.text+"'");
		}
		
		/**
		 * Handles data stream security errors
		 * @param event The SecurityErrorEvent
		 */ 
		override public function handleSecurityErrror(event:SecurityErrorEvent):void {
			handleError(event, "SecurityError: "+_serverName+" on "+_serverPort+" due to\n\t'"+event.text+"'");
		}
		
		/**
		 * Handles data stream closures. 
		 * @param event The Event
		 */ 
		override public function handleClose(event:Event):void {
			cleanupDataSocket();
			sendCloseEvent();
			_clientIssuedClose = false;
		}
		
		private function handleStreamRequest(request:StreamDataRequestEvent):void{
			if(!isRecipient(request)) return;
			if(_dataSocket != null && _dataSocket.connected){
				_dataSocket.send(request.data);
			}
			else if(!_dataSocket.connected){
				log(DefaultLogEvent.WARNING, "RemoteStreamingClient.handleStreamRequest - Attempted to send via disconnected socket!");
				log(DefaultLogEvent.DEBUG, request.logMessage);
			}
			else{
				log(DefaultLogEvent.WARNING, "RemoteStreamingClient.handleStreamRequest - Attempted to send via null socket!");
			}
		}
		
		private function closeSocket():void {
			if(_dataSocket != null && _dataSocket.connected){
				//NOTE close event not dispatched when client calls close, so proceed with clean-up after close
				_dataSocket.close();
				handleClose(null);
			}
		}
		
		private function sendCloseEvent():void{
			var response:StreamDataResponseEvent = new StreamDataResponseEvent(
				_channelName,
				StreamDataRequestEvent.CLOSE_SOCKET
				/*broadcast*/
			);
			if(!_clientIssuedClose){
				response.status = StreamDataResponseEvent.SERVER_CRASH;
			}
			else{
				response.status = IOEvent.SUCCESS;
			}
			EventBus.instance.dispatchEvent(response);
		}
		
		private function cleanupDataSocket():void{
			if(_dataSocket == null){ return; }
			if(_dataSocket.connected){
				log(DefaultLogEvent.WARNING, "RemoteStreamingClient.handleSocketClosed - attempted to clean up an active socket.");
				return;
			}
			_dataSocket.removeEventListener(Event.CONNECT , handleConnect);
			_dataSocket.removeEventListener(DataEvent.DATA , handleData);
			_dataSocket.removeEventListener(IOErrorEvent.IO_ERROR , handleIOError);
			_dataSocket.removeEventListener(SecurityErrorEvent.SECURITY_ERROR , handleSecurityErrror);
			_dataSocket.removeEventListener(Event.CLOSE , handleClose);
			_dataSocket = null;
		}
		
		private function handleError(event:Event, message:String):void{
			log(DefaultLogEvent.CRITICAL, "RemoteStreamingClient - " + message);
			var response:StreamDataResponseEvent = new StreamDataResponseEvent(channelName, StreamDataRequestEvent.ERROR);
			response.status = IOEvent.RESPONSE_ERROR;
			response.rawResponse = event;
			response.failMessage = message;
			EventBus.instance.dispatchEvent(response);
			cleanupDataSocket();
		}
		
		override public function registerListeners():void{
			EventBus.instance.addEventListener(EventTypes.STREAM_DATA_REQUEST, handleStreamRequest);
		}
		
		private function unregisterListeners():void{
			EventBus.instance.removeEventListener(EventTypes.STREAM_DATA_REQUEST, handleStreamRequest);
		}
		
		override public function isRecipient(event:EventBusEvent):Boolean{
			if(event is StreamDataRequestEvent){
				return (event as StreamDataRequestEvent).command == StreamDataRequestEvent.SEND_DATA;
			}
			return false;
		}
		
		
	}
}