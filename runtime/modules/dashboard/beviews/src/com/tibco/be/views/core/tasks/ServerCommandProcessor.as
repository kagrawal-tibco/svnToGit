package com.tibco.be.views.core.tasks{
	import com.tibco.be.views.core.Configuration;
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.IEventBusListener;
	import com.tibco.be.views.core.events.io.PullDataRequestEvent;
	import com.tibco.be.views.core.events.io.PullDataResponseEvent;
	import com.tibco.be.views.core.events.io.StreamDataResponseEvent;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.events.tasks.ServerRequestEvent;
	import com.tibco.be.views.core.events.tasks.ServerResponseEvent;
	import com.tibco.be.views.utils.Logger;
	
	import flash.utils.Dictionary;
	
	
	/**
	 * ServerCommandProcessor is intended to handle exactly one request to and corresponding
	 * response from the BEViews server.  The command processor may dispatch new server request
	 * events; however, it may not make chain requests or hanlde more than one response.  Failure
	 * to adhere to these rules will result in memory leaks in the Event Bus.  A command processor
	 * should build, process, hanldeResponse, and finish.
	*/
	public class ServerCommandProcessor implements ICommandProcessor, IEventBusListener{
		
		protected var _request:ServerRequestEvent;
		protected var _response:ServerResponseEvent;
		
		public function ServerCommandProcessor(requestEvent:ServerRequestEvent){
			_request = requestEvent;
		}
		
		public function get command():String{ return _request == null ? "":_request.command; }
		
		public function process():void{}
		public function processorError(functionName:String, message:String):void{}
		public function registerListeners():void{ }
		
		protected function buildDefaultHTTPPullReq():PullDataRequestEvent{
			var dataReq:PullDataRequestEvent = new PullDataRequestEvent(this);
			dataReq.hostName = Configuration.instance.serverName;
			dataReq.port = Configuration.instance.serverPort;
			dataReq.contextPath = Configuration.instance.commandContextPath;
			return dataReq;
		}
		
		public function isRecipient(event:EventBusEvent):Boolean{
			try{
				return event.intendedRecipient == this;
			}
			catch(error:Error){
				processorError("registerListeners", error.message);
			}
			return false;			
		}
		
		protected function errorCheck(event:EventBusEvent, xmlResponse:XML):Boolean{
			if(event is PullDataResponseEvent){
				return checkPullResponse(event as PullDataResponseEvent, xmlResponse);
			}
			else if(event is StreamDataResponseEvent){
				return checkStreamResponse(event as StreamDataResponseEvent, xmlResponse);
			}
			return false;
		}
		
		private function checkPullResponse(event:PullDataResponseEvent, xmlResponse:XML):Boolean{
			try{
				var errString:String = "";
				if(event.failed){
					errString = String(event.data);
					Logger.log(DefaultLogEvent.DEBUG, "Data Pull Failed: " + errString);
				}
				else if(String(xmlResponse.status) == "EXCEPTION"){
					errString = String(xmlResponse.message);
					Logger.log(DefaultLogEvent.DEBUG, "EXCEPTION: " + errString);
				}
				else if(String(xmlResponse.state) == "ERROR"){
					errString = String(xmlResponse.message)
					Logger.log(DefaultLogEvent.DEBUG, "ERROR: " + errString);
				}
				else if(xmlResponse.response.error != undefined ){
					errString = String(xmlResponse.response.error.message);
					Logger.log(DefaultLogEvent.DEBUG, "Processing Error: " + errString);
				}
				
				if(errString == ""){ return false; }
				processorError("checkPullResponse", errString);
				return true;
			}
			catch(error:Error){ 
				return true;
			}
			return false;
		}
		
		private function checkStreamResponse(event:StreamDataResponseEvent, xmlResponse:XML):Boolean{
			try{
				//check for errors...
			}
			catch(error:Error){
				return true;
			}
			return false;
		}
		
		/**
		 * Generates the Presentation Server API Framework XML
		 * @command - The command to execute
		 * @param parameters - The key-value pairs of the request parameters
		 */ 
		public static function getRequestXML(parameters:Dictionary):XML {
			var requestXML:XML = new XML("<request></request>");
			if(parameters == null){ return requestXML; }
			for(var parametername:String in parameters){
				var paramXML:XML = new XML("<parameter name=\""+parametername+"\"><![CDATA[" + new String(parameters[parametername]) + "]]></parameter>");
				requestXML.appendChild(paramXML);
			}
			return requestXML;
		}
		
 		public static function getRequestXMLString(parameters:Dictionary):String{
			return getRequestXML(parameters).toXMLString();
//			var r1:RegExp = new RegExp("\n", "g");
//			var r2:RegExp = new RegExp("\\s+", "g");
//			xmlStr = xmlStr.replace(r1, "");
//			xmlStr = xmlStr.replace(r2, " ");
//			return xmlStr;
		}

		
	}
}