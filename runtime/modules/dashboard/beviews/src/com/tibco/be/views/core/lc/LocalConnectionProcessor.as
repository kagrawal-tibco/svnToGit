package com.tibco.be.views.core.lc{
	
	import com.tibco.be.views.core.BEVLocalConnectionManager;
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.IEventBusListener;
	import com.tibco.be.views.core.events.lc.LocalConnectionRequest;
	import com.tibco.be.views.core.events.lc.LocalConnectionResponse;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.events.tasks.ConfigRequestEvent;
	import com.tibco.be.views.core.events.tasks.ControlRequestEvent;
	import com.tibco.be.views.core.events.tasks.ServerRequestEvent;
	import com.tibco.be.views.core.events.tasks.ServerResponseEvent;
	import com.tibco.be.views.utils.Logger; 
	//import flash.utils.getDefinitionByName;
	
	/**
	 * Handles the publishing of a LocalConnectionRequest to the LocalConnection. 
	*/
	public class LocalConnectionProcessor implements IEventBusListener{
		
		private var _lcRequest:LocalConnectionRequest;
		private var _lcName:String;
		
		public function LocalConnectionProcessor(localConnectionName:String){
			//registerListeners();  //since processLCRequest is the only one that needs to wait for a response, moving this line to that function
			if(localConnectionName == null || localConnectionName == ""){
				Logger.log(DefaultLogEvent.WARNING, "LocalConnectionProcessor() - Invalid LocalConnection name.");
				_lcName = "";
			}
			_lcName = localConnectionName;
		}
				
		/**
		 * Used when a component on this application's event bus sends a LocalConnectionRequest. The
		 * request is translated to an appropriate LocalConnection message and sent over the
		 * LocalConnection. This processor's responsibilities are complete upon publishing the
		 * request event to the LocalConnection. The response will be received by the LC service
		 * via the LC
		*/
		public function processEBRequest(request:LocalConnectionRequest):void{
			if(_lcName == ""){
				Logger.log(DefaultLogEvent.WARNING, "LocalConnectoinProcessor.processEBRequest - Sending over \"\" LocalConnection.");
			}
			BEVLocalConnectionManager.instance.sendLCRequest(request.toLocalConnectionMessage(), _lcName+LocalConnectionService.REQUEST_CHANNEL);
			finish();
		}
		
		/**
		 * Used upon receiving a request via the local connection. Creates the appropriate
		 * processing request event and publishes it to the EventBus for processing.  The result
		 * event generated from that processing will be caught by this processor.
		*/
		public function processLCRequest(lcRequest:LocalConnectionRequest):void{
			registerListeners();
			_lcRequest = lcRequest;
			var serverRequest:ServerRequestEvent;
			switch(lcRequest.eventBusType){
				case(EventTypes.CONFIG_COMMAND):
					serverRequest = new ConfigRequestEvent(lcRequest.command, this);
					break;
				case(EventTypes.CONTROL_COMMAND):
					serverRequest = new ControlRequestEvent(lcRequest.command, this);
					break;
				default:
					Logger.log(DefaultLogEvent.WARNING, "LocalConnectionProcessor.processLCRequest - Unsupported eventBusType: " + lcRequest.eventBusType);
					return;
			}
			for(var key:String in lcRequest.xmlParams){
				serverRequest.addXMLParameter(key, lcRequest.getXMLParameter(key));
			}
			EventBus.instance.dispatchEvent(serverRequest);
			//don't call finish(), still waiting on the response...
		}
		
		/**
		 * Action executed when a response is received over the LocalConnection. This function will
		 * use the corresponding LocalConnectionRequest as well as the xml-formatted
		 * local connection response message to create a LocalConnectionResponse Event to publish on
		 * the event bus. This processor's responsibilities are complete upon publishing the
		 * response event to the EventBus.
		*/
		public function procesLCResponse(request:LocalConnectionRequest, responseXML:XML):void{
			var lcResponse:LocalConnectionResponse = new LocalConnectionResponse(request);
			if(responseXML.data != undefined){
				lcResponse.data = new XML(responseXML.data);
			}
			else if(responseXML.failmessage != undefined){
				lcResponse.failMessage = new String(responseXML.failmessage);
			}
			EventBus.instance.dispatchEvent(lcResponse);
			finish();
		}
		
		/**
		 * When the action taken upon processing a LocalConnectionRequest returns, this function
		 * processes the result. It basically creates a LocalConnectionResponse and publishes it to
		 * the EventBus to be handled by the LocalConnectionService. This processor's
		 * responsibilities are complete upon publishing the response event to the EventBus.
		*/
		private function processEBResponse(response:ServerResponseEvent):void{
			var lcResponse:LocalConnectionResponse = new LocalConnectionResponse(_lcRequest);
			lcResponse.failMessage = response.failMessage;
			lcResponse.data = response.dataAsXML;
			EventBus.instance.dispatchEvent(lcResponse);
			finish();
		}
		
		private function finish():void{
			unRegisterListeners();
		}
		
		public function registerListeners():void{
			EventBus.instance.addEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, processEBResponse, this);
			EventBus.instance.addEventListener(EventTypes.CONTROL_COMMAND_RESPONSE, processEBResponse, this);
		}
		public function isRecipient(event:EventBusEvent):Boolean{
			return event.intendedRecipient == this;
		}
		private function unRegisterListeners():void{
			EventBus.instance.removeEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, processEBResponse);
			EventBus.instance.removeEventListener(EventTypes.CONTROL_COMMAND_RESPONSE, processEBResponse);
		}

	}
}