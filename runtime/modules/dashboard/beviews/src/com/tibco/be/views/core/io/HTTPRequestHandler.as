package com.tibco.be.views.core.io{
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.io.PullDataRequestEvent;
	import com.tibco.be.views.core.events.io.PullDataResponseEvent;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.logging.LoggingService;
	
	import flash.events.Event;
	import flash.events.HTTPStatusEvent;
	import flash.events.IOErrorEvent;
	import flash.events.SecurityErrorEvent;
	import flash.net.URLLoader;
	import flash.net.URLRequest;
	import flash.net.URLRequestHeader;
	import flash.net.URLRequestMethod;
	import flash.net.URLVariables;
	
	/**
	 * Handles a PullDataRequestEvent via the HTTP protocol. Once the data has been fetched,
	 * HTTPRequestHandler publishes the data to the EventBus as a PullDataResponseEvent.
	 */
	public class HTTPRequestHandler implements IIOHandler{
		
		private var _requestEvent:PullDataRequestEvent;
		private var _request:URLRequest;
		private var _response:PullDataResponseEvent;
		private var _urlLoader:URLLoader;
		
		/**
		 * Constructor. Establishes the PullDataRequestEvent to process
		 * @param requestEvent - The PullDataRequestEvent this IIOHandler will process.
		 */
		public function HTTPRequestHandler(requestEvent:PullDataRequestEvent){
			_requestEvent = requestEvent;
		}
		
		/**
		 * Sends a HTTP POST request using parameters defined by the request event.
		 */
		public function start():void{
			var urlString:String = buildHTTPURL();
            _request = new URLRequest(urlString);
            //To send custom http headers, Flex wants the request to be POST and some POST data
            _request.method = URLRequestMethod.POST;
            //the custom headers
            if(_requestEvent.headerData != null) {
	            var headers:Array = new Array();
	            for (var prop:String in _requestEvent.headerData) {
	            	EventBus.instance.dispatchEvent(
	            		new DefaultLogEvent(
	            			DefaultLogEvent.INFO,
	            			"["+urlString+"]Submitting "+prop+" as "+_requestEvent.headerData[prop]
            			)
        			);
	                headers.push(new URLRequestHeader(prop, _requestEvent.headerData[prop]));    
	            }
	            _request.requestHeaders = headers;
	        }
            //the dummy post data to prevent browser-cached responses
//            var variables:URLVariables = new URLVariables();
//            variables.time = (new Date()).time;
//            _request.url += "?" + new Date().time
            
            if(_requestEvent.payload is String){
            	//Modified by Anand to fix BE-10880 on 01/28/2011
            	_request.contentType = "text/xml; charset=UTF-8";
				//User-specified post data as a single, specific string
            	_request.data = _requestEvent.payload;
            }
            else{
	            //Build post data variables from payload object
	            //Modified by Anand to fix BE-10880 on 01/28/2011
	            _request.contentType = "application/x-www-form-urlencoded; charset=UTF-8";
	            var variables:URLVariables = new URLVariables();
	            for(var key:String in _requestEvent.payload){
	            	variables[key] = _requestEvent.payload[key];
	            }
	            _request.data = variables;
           }
            
            _urlLoader = new URLLoader(_request);
            _urlLoader.addEventListener(Event.COMPLETE, handleData);
            _urlLoader.addEventListener(IOErrorEvent.IO_ERROR, handleIOError);
//            _urlLoader.addEventListener(HTTPStatusEvent.HTTP_STATUS, handleStatus);
            _urlLoader.load(_request);
		}
		
		public function stop():void{ /* not used by this IOHandler */ }
		
		public function handleConnect(event:Event):void{ /* not used by this IOHandler */ }
		
		/** Called when the URLLoader.load operation is complete. */
		public function handleData(event:Event):void{
			//Build and publish PullDataResponseEvent
			_response = new PullDataResponseEvent(_urlLoader.data, _requestEvent);
			EventBus.instance.dispatchEvent(_response);
		}
		
		/** Called should the URLLoader.load call return an error */
		public function handleIOError(event:IOErrorEvent):void{
			//Build and publish PullDataResponseEvent
			var response:PullDataResponseEvent = new PullDataResponseEvent(event.text, _requestEvent);
			response.failed = true;
			EventBus.instance.dispatchEvent(response);
		}
		
		public function handleStatus(event:HTTPStatusEvent):void{
			trace("HTTP Status: " + event.toString());
		}
		
		public function handleSecurityErrror(event:SecurityErrorEvent):void{ /* not used by this IOHandler */ }
		
		public function handleClose(event:Event):void{ /* not used by this IOHandler */ }
		
		/**
		 * Constructs a URL string from the PullDataRequestEvent.
		 * Specifically:  http://<host_name>:<port>/<context_path>/<resource_name>?<query_string>
		 */
		private function buildHTTPURL():String{
			var url:String = "";
			if(_requestEvent == null){
				return url;
			}
			if(_requestEvent.hostName == PullDataRequestEvent.DEMO){
				_requestEvent.hostName = "data";
				_requestEvent.contextPath = "demo/";
			}
			else if(_requestEvent.hostName == ""){
				url = ""; //attempt relative path request
			}
			else{
				url = "http://";
			}
			return url +
				_requestEvent.hostName + 
				(_requestEvent.port == "" ? "":":"+_requestEvent.port) + 
				_requestEvent.contextPath +
				_requestEvent.resourceName + 
				(_requestEvent.queryString == "" ? "":"?"+ _requestEvent.queryString);
		}


	}
}