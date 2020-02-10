package com.tibco.cep.ui.monitor.io{
	import com.tibco.cep.ui.monitor.IUpdateable;
	import com.tibco.cep.ui.monitor.util.Logger;
	
	import flash.events.Event;
	import flash.events.IOErrorEvent;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import flash.net.URLRequestHeader;
	import flash.net.URLRequestMethod;
	import flash.net.URLVariables;
	import flash.utils.Dictionary;
	
	/**
	 * Stores information about the request to the Presentation Server and handles
	 * requests to the server.
	 */
	public class PSVRAgent {
		
		private var _operation:String;
		private var _request:URLRequest;
		private var _loader:URLLoader;
		private var _target:IUpdateable;
		
		/*public function PSVRAgent(request:URLRequest,target:IUpdateable){
			this._request = request;
			this._target = target;
			this._loader = new URLLoader(this._request);
			this._loader.addEventListener(Event.COMPLETE, this.handleSuccess);
			this._loader.addEventListener(IOErrorEvent.IO_ERROR, this.handleFailure);
		}*/
		
		public function PSVRAgent(operation:String, requestURL:String, queryParams:Dictionary, 
															target:IUpdateable, payload:Object=null){
			_operation = operation;
            _request = new URLRequest(requestURL);
            //To send custom http headers, Flex wants the request to be POST and some POST data
            _request.method = URLRequestMethod.POST;
            //the custom headers
            if (queryParams != null) {
	            var headers:Array = new Array();
	            Logger.logInfo(this,"Sending HTTP request for operation: " + operation); 
	            for (var prop:String in queryParams) {
	            	Logger.logInfo(this,"["+requestURL+"]Submitting "+prop+" as "+queryParams[prop]);
	                headers.push(new URLRequestHeader( prop, String(queryParams[prop]) ) );    
	            }
	            _request.requestHeaders = headers;
	        }
            //the dummy post data
            var variables : URLVariables = new URLVariables();
            variables.time = (new Date()).time;
			_request.data = variables;
            
            if(payload!=null){
	            if(payload is String){
					//User-specified post data as a single, specific string
	            	_request.data = payload;
	            }
	            else{
		            //Build post data variables from payload object
		            for(var key:String in payload){		//TODO: Check this
		            	variables[key] = payload[key];
		            }
		            _request.data = variables;
	           }
	           Logger.logInfo(this,"["+requestURL+"] with payload: " + payload.toString());
            }
            
            _loader = new URLLoader(_request);
            _loader.addEventListener(Event.COMPLETE, handleSuccess);
            _loader.addEventListener(IOErrorEvent.IO_ERROR, handleFailure);
            
            _target = target;
		} 
		
		public function sendRequest():void{
			this._loader.load(this._request);
		}
		
		private function handleSuccess(event:Event):void{
			_loader.removeEventListener(Event.COMPLETE, handleSuccess);
			_loader.removeEventListener(IOErrorEvent.IO_ERROR, handleFailure);
			//do something with the event?
			if(_target !=  null) 
				_target.update(new String(_operation),new XML(this._loader.data));
			
			_loader.close();
			resetRefs();
		}
		
		private function handleFailure(event:IOErrorEvent):void{
			_loader.removeEventListener(Event.COMPLETE, handleSuccess);
			_loader.removeEventListener(IOErrorEvent.IO_ERROR, handleFailure);
			
			if(_target ==  null) 
				return;
				
			var msg:String = event.text;
			if (_operation == "login"){
				msg = "Connection Failed. Try Again...";
			}
			_target.updateFailure(_operation, msg, 0);
			
			_loader.close();
			resetRefs();
		}
		
		//Aiming to speed up GC and eliminate memory leak
		private function resetRefs():void {
			_operation = null;
			_request = null;
			_loader = null;
			_target = null;
		}

	}
}