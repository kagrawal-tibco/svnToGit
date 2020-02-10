package com.tibco.be.views.core.lc{
	
	import com.tibco.be.views.core.BEVLocalConnectionManager;
	import com.tibco.be.views.core.IBEVLocalConnectionClient;
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.IEventBusListener;
	import com.tibco.be.views.core.events.lc.LocalConnectionRequest;
	import com.tibco.be.views.core.events.lc.LocalConnectionResponse;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.utils.Logger;
	
	import flash.utils.Dictionary;
	
	/**
	 * Unlike other BEViews services, the LocalConnectionService listens for events sent via the
	 * LocalConnection as well as the EventBus. Request events are received over the local
	 * connection while responses are generated from event bus events and published over the local
	 * connection.  This processing is performed by an instance of LocalConnectionProcessor to
	 * handle the processing required by the event. 
	*/ 
	public class LocalConnectionService implements IEventBusListener, IBEVLocalConnectionClient{
		
		public static const REQUEST_CHANNEL:String = "_requestChannel";
		public static const RESPONSE_CHANNEL:String = "_responseChannel";
		
		private static var _instance:LocalConnectionService;
		
		public static function get instance():LocalConnectionService{
			if(_instance == null){
				_instance = new LocalConnectionService();
			}
			return _instance;
		}
		
		private var _requestDirectory:Dictionary;
		private var _lcName:String;
		
		function LocalConnectionService(){
			_requestDirectory = new Dictionary();
		}
		
		public function init(localConnectionName:String, listenForRequests:Boolean=true, listenForResponses:Boolean=true):void{
			_lcName = localConnectionName;
			if(localConnectionName == null || localConnectionName == ""){
				Logger.log(DefaultLogEvent.WARNING, "LocalConnectionService.init() - Invalid LocalConnection name.");
				_lcName = "";
			}
			if(listenForRequests){ BEVLocalConnectionManager.instance.registerClient(this, _lcName+REQUEST_CHANNEL); }
			if(listenForResponses){ BEVLocalConnectionManager.instance.registerClient(this, _lcName+RESPONSE_CHANNEL); }
			registerListeners();
		}
		
		/**
		 * Handles a LocalConnectionRequest event dispatched via the event bus. Typically these
		 * events will come from components (i.e. table tree, filter editor, etc.) that need to be 
		 * processed by another Flash movie instance (i.e BEViews). 
		*/
		private function handleLCRequestFromBus(lcRequest:LocalConnectionRequest):void{
			if(!isRecipient(lcRequest)){ return; }
			_requestDirectory[lcRequest.id] = lcRequest;
			new LocalConnectionProcessor(_lcName).processEBRequest(lcRequest);
		}
		
		/**
		 * Handles a LocalConnectionResponse event dispatched via the event bus. Typically these
		 * events will come from a LocalConnectionProcessor trying to return a result to a requestor
		 * waiting for the response to come over the LC. In which case, this function will send
		 * the response via the LC.
		 * 
		 * Alternatively, this response may have been generated from an LC response XML message by a
		 * LC processor. In this case, this function just removes the corresponding request from the
		 * request directory. The LC requesting object will be listening for the response on the bus
		 * and will handle it according to its needs.
		 */
		private function handleLCResponseFromBus(lcResponse:LocalConnectionResponse):void{
			if(!isRecipient(lcResponse)){ return; }
			if(_lcName == ""){
				Logger.log(DefaultLogEvent.WARNING, "LocalConnectoinProcessor.processEBRequest - Sending over \"\" LocalConnection.");
			}
			//If the LC requestor is waiting on this services event bus, remove the directory entry
			//and return. The requestor will handle the response.
			if(_requestDirectory[lcResponse.id] != undefined){
				delete _requestDirectory[lcResponse.id];
				return;
			}
			BEVLocalConnectionManager.instance.sendLCResponse(lcResponse.toLocalConnectionMessage(), _lcName+RESPONSE_CHANNEL);
		}
		
		//IEventBusListener
		public function registerListeners():void{
			EventBus.instance.addEventListener(EventTypes.LC_COMMAND, handleLCRequestFromBus);
			EventBus.instance.addEventListener(EventTypes.LC_COMMAND_RESPONSE, handleLCResponseFromBus);
		}
		public function isRecipient(event:EventBusEvent):Boolean{
			return true;
		}
		//END IEventBusListener
		
		//IBEVLocalConnectionClient
		public function receiveMessage(message:String):void{ /* not used*/ }
		public function receiveEventData(eventData:XML):void{ /* not used*/ }
		public function receiveLCRequest(lcRequestXML:XML):void{
			//convert lcRequestXML to LocalConnectionRequest event
			var lcRequest:LocalConnectionRequest = new LocalConnectionRequest(null, null, null, lcRequestXML);
			new LocalConnectionProcessor(_lcName).processLCRequest(lcRequest);
		}
		public function receiveLCResponse(lcResponseXML:XML):void{
			var reqestId:String = new String(lcResponseXML.id);
			if(_requestDirectory[reqestId] == undefined){
				//All application LocalConnectionService instances will receive responses. Thus,
				//getting a response that corresponds to a request not issued by this service
				//instance is to be expected.
				return;
			}
			var lcRequest:LocalConnectionRequest = _requestDirectory[reqestId] as LocalConnectionRequest;
			//don't delete the entry in the request table yet, wait for the LCResponse to be published on the bus
			new LocalConnectionProcessor(_lcName).procesLCResponse(lcRequest, lcResponseXML);
		}
		//END IBEVLocalConnectionClient

	}
}