package com.tibco.be.views.core.tasks{
	import com.tibco.be.views.core.Session;
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.IEventBusListener;
	import com.tibco.be.views.core.events.io.PullDataRequestEvent;
	import com.tibco.be.views.core.events.io.PullDataResponseEvent;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.events.tasks.ConfigRequestEvent;
	import com.tibco.be.views.core.events.tasks.ConfigResponseEvent;
	import com.tibco.be.views.utils.Logger;
	
	import flash.utils.Dictionary;
	
	public class ConfigProcessor extends ServerCommandProcessor implements IEventBusListener{
			
		public function ConfigProcessor(requestEvent:ConfigRequestEvent){
			if(!(requestEvent is ConfigRequestEvent)){
				EventBus.instance.dispatchEvent(
					new DefaultLogEvent(
						DefaultLogEvent.WARNING, 
						"ConfigProcessor - Constructed with non-ConfigRequestEvent"
					)
				);
			}
			super(requestEvent);
			registerListeners();
		}
		
		override public function process():void{
			var dataReq:PullDataRequestEvent;
			var cmd:String;
			var params:Dictionary;
			try{
				if(_request == null){
					trace("ControlProcessor.process - NULL request object.");
					return;
				}
				dataReq = buildDefaultHTTPPullReq();
				params = new Dictionary();
				cmd = _request.command;
				
				//Add any user-specified xml parameters to the temp params dictionary
				if(Session.instance.token != null && Session.instance.token != ""){
					params["stoken"] = Session.instance.token;
				}
				params["command"] = cmd
				for(var key:String in _request.xmlParams){
					params[key] = _request.xmlParams[key];
				}
				
				//Set the request payload (POST data) and send the request
				dataReq.payload = getRequestXMLString(params);
				EventBus.instance.dispatchEvent(dataReq);
			}
			catch(error:Error){
				processorError("process", error.message);
				return;
			}
		}
		
		private function handlePullDataResponse(event:PullDataResponseEvent):void{		
			try{
				var xmlResponse:XML = XML(event.data);
				if(super.errorCheck(event, xmlResponse)){ return; }
				var configResponse:ConfigResponseEvent = new ConfigResponseEvent(_request as ConfigRequestEvent, _request.intendedRecipient);
				var rawContentXML:String = "";
				if(xmlResponse.content != undefined && xmlResponse.content.children().length() > 0){
					rawContentXML = xmlResponse.content.children()[0].toString();
				}
				var xmlContent:XML = new XML(rawContentXML);
				if(String(xmlResponse.status == "OK")){
					configResponse.data = xmlContent;
					switch(configResponse.command){
						case(ConfigRequestEvent.REMOVE_COMPONENT_COMMAND):
							configResponse.broadcast = true;
							break;
					}
				}
				else{
					configResponse.failMessage = String(xmlResponse.status);
					configResponse.data = null;
				}
				EventBus.instance.dispatchEvent(configResponse);
				unRegisterListeners();
			}
			catch(error:Error){
				processorError("handlePullDataResponse", "Failed Execution of '" + _request.command + "'\n" + error.getStackTrace());
				return;
			}
		}
		
		override public function processorError(functionName:String, message:String):void{
			functionName = functionName == null ? "":functionName;
			message = message == null ? "":message;
			
			Logger.log(DefaultLogEvent.WARNING, "ConfigProcessor." + functionName + " - " + message);
			
			var response:ConfigResponseEvent = new ConfigResponseEvent(_request as ConfigRequestEvent, _request.intendedRecipient);
			response.failMessage = message;
			response.data = null;
			EventBus.instance.dispatchEvent(response);
			return;
		}
		
		//IEventBusListener
		override public function registerListeners():void{
			try{
				EventBus.instance.addEventListener(EventTypes.PULL_DATA_RESPONSE, handlePullDataResponse, this);
			}
			catch(error:Error){
				processorError("registerListeners", error.message);
				return;
			}
		}
		
		public function unRegisterListeners():void{
			try{
				EventBus.instance.removeEventListener(EventTypes.PULL_DATA_RESPONSE, handlePullDataResponse);
			}
			catch(error:Error){
				processorError("unRegisterListeners", error.message);
				return;
			}
		}
		
	}
}