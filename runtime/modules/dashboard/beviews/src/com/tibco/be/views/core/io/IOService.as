package com.tibco.be.views.core.io{
	import com.tibco.be.views.core.Configuration;
	import com.tibco.be.views.core.events.*;
	import com.tibco.be.views.core.events.io.PullDataRequestEvent;
	import com.tibco.be.views.core.events.io.StreamDataRequestEvent;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	
	import flash.utils.Dictionary;
	
	/**
	 * IOService is responsible for listening to IO request events on the event bus and initializing
	 * the appropriate IIOHandler to hadle the actual i/o work and result construction.
	 */
	public class IOService implements IEventBusListener{
		
		private static var _instance:IOService;
		
		private var _configuration:Configuration;
		
		/** Dictionary of StreamingClients where channelName->StreamingClient */
		private var _dataStreams:Dictionary;
		
		/**
		 * IOService constructor. Use of this constructor should be avoided.  Instead, use the
		 * instance property to utilize this singleton class.
		 */
		function IOService(){
			_dataStreams = new Dictionary();
		}
		
		/** The instance property provides access to the IOService singleton class. */
		public static function get instance():IOService{
			return _instance == null ? new IOService():_instance;
		}
		
		/**
		 * Initialize the IOService. Basically just registers all of the IOService's event listeners
		 * with the EventBus.
		 */
		public function init(configuration:Configuration):void{
			_configuration = configuration;
			registerListeners();
		}
		
		/**
		 * Consumes PullDataRequestEvents and spawns an appropriate IIOHandler to perform the i/o
		 * operation.
		 */
		private function handlePullDataRequest(event:PullDataRequestEvent):void{
			if(event == null){
				EventBus.instance.dispatchEvent(
					new DefaultLogEvent(
						DefaultLogEvent.WARNING, 
						"IOService - Failed Pull Data Request: Null pull event."
					)
				);
				return;
			}
			if(!isRecipient(event)) return;
			
			var handler:IIOHandler;
			switch(event.transportType){
				case(PullDataRequestEvent.HTTP):
					handler = new HTTPRequestHandler(event);
					break;
			}
			if(handler != null){
				handler.start();
			}
			
		}
		
		/** Handles requests to stream data from a server. */
		 private function handleStreamDataRequest(event:StreamDataRequestEvent):void{
		 	if(_dataStreams[event.channelName] == undefined){
		 		//TODO: ideally the streaming client implementation should be determined by the
		 		//IOService's streaming mode
		 		//_dataStreams[event.channelName] = new RemoteStreamingClient(event);
		 		_dataStreams[event.channelName] =  createStreamingClient(event);
		 	}
		 	var handler:IIOHandler = _dataStreams[event.channelName] as StreamingClient;
		 	switch(event.command){
		 		case(StreamDataRequestEvent.OPEN_SOCKET):
		 			handler.start();
		 			break;
		 		case(StreamDataRequestEvent.SEND_DATA):
		 			//picked up by the appropriate StreamingClient
		 			break;
		 		case(StreamDataRequestEvent.CLOSE_SOCKET):
		 			handler.stop();
		 			delete _dataStreams[event.channelName];
		 			break;
		 		default: break;
		 	}
		 	
		 }
		 
		 private function createStreamingClient(event:StreamDataRequestEvent):StreamingClient {
		 	if (_configuration.updateMode == StreamMode.TCP) {
		 		return new RemoteStreamingClient(event);
		 	}
		 	if (_configuration.updateMode == StreamMode.HTTP) {
		 		var httpEvent:PollStreamDataRequestEvent = new PollStreamDataRequestEvent(event,_configuration.serverName,_configuration.updatePort,
		 																					_configuration.updateFrequency, _configuration.updateReconnectFrequency, 
		 																					_configuration.updateReconnectThreshold);
		 		return new RemoteHTTPPollBasedStreamingClient(httpEvent);
		 	}
		 	throw new Error("Invalid streaming mode '"+_configuration.updateMode+"'");
		 }
		
		//IEventBusListener
		public function registerListeners():void{
			EventBus.instance.addEventListener(EventTypes.PULL_DATA_REQUEST, handlePullDataRequest);
			EventBus.instance.addEventListener(EventTypes.STREAM_DATA_REQUEST, handleStreamDataRequest);
		}
		
		public function isRecipient(event:EventBusEvent):Boolean{
			return true;
		}
	}
}