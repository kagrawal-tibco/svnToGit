package com.tibco.be.views.core.events.io{
	import com.tibco.be.views.core.events.EventTypes;
	
	import flash.events.Event;
	import flash.utils.Dictionary;
	
	import mx.core.Application;
	
	
	public class PullDataRequestEvent extends IOEvent{
		
		public static const DEMO:String = "demo";
		public static const HTTP:String = "http";
		
		/**
		 * The transport mechanism to be used when performing the data pull.
		 */
		private var _transportType:String;
		
		/**
		 * Hostname of the server to send the HTTP request to. (i.e. localhost, 127.0.0.1, or tibco.com)
		 */
		private var _hostName:String;
		
		/**
		 * The port number to connect to.
		 */
		private var _port:String;
		
		/**
		 * The context path of the resource you're trying to access. For example in 
		 * http://tibco.com/be/index.html the context path would be "be/"
		 */
		private var _contextPath:String;
		
		/**
		 * The name of the resource to access.  For example: index.html, dowork.php, arrow.jpg, etc.
		 */
		private var _resourceName:String;
		
		/**
		 * The query string to append to HTTP requests.  For example, in the following url
		 * http://service.provider.com/myService.asmx?var1=22&var2=0
		 * the string "var1=22&var2=0" is the query string.
		 */
		private var _queryString:String;
		
		/**
		 * Parameters to be included in the header (if applicable) data of the request.
		 */
		private var _headerData:Dictionary;
		
		/**
		 * The payload data to be sent.  HTTP POST data should be specified as the payload.
		 */
		private var _payload:Object;
		
		public function PullDataRequestEvent(intendedRecipient:Object=null){
			super(EventTypes.PULL_DATA_REQUEST, intendedRecipient);
			_transportType = HTTP;
			_hostName = "";
			_port = "";
			_contextPath = "/";
			_resourceName = "";
			_queryString = "";
			_headerData = null;
			_payload = null;
		}
		
		public function get transportType():String{ return _transportType; }
		public function get hostName():String{ return _hostName; }
		public function get port():String{ return _port; }
		public function get contextPath():String{ return _contextPath; }
		public function get resourceName():String{ return _resourceName; }
		public function get queryString():String{ return _queryString; }
		public function get headerData():Dictionary{ return _headerData; }
		public function get payload():Object{ return _payload; }
		
		public function set transportType(value:String):void{ _transportType = value; }
		public function set hostName(value:String):void{ _hostName = value; }
		public function set port(value:String):void{ _port = value; }
		public function set contextPath(value:String):void{ _contextPath = value; }
		public function set resourceName(value:String):void{ _resourceName = value; }
		public function set queryString(value:String):void{ _queryString = value; }
		public function set headerData(value:Dictionary):void{ _headerData = value; }
		public function set payload(value:Object):void{ _payload = value; }
		
		//ILogable
		override public function get logMessage():String{
			return "("+_transportType+") - " + _hostName + (_port=="" ? "":":"+_port) +  
				_contextPath + _resourceName + (_queryString == "" ? "":"?"+_queryString) + "\n\t" +
				String(_payload);
		}
		
		
		override public function clone():Event{
			var cloned:PullDataRequestEvent = new PullDataRequestEvent(_intendedRecipient);
			cloned.transportType = _transportType;
			cloned.hostName = _hostName;
			cloned.port = _port;
			cloned.contextPath = _contextPath;
			cloned.resourceName = _resourceName;
			cloned.queryString = _queryString;
			cloned.headerData = _headerData;
			cloned.payload = _payload;
			return cloned;
		}
		
		override protected function get details():String{
			return "("+_transportType+") - " + _hostName + (_port=="" ? "":":"+_port) +  
				_contextPath + _resourceName + (_queryString == "" ? "":"?"+_queryString) + 
				"["+ String(_payload) + "]";
		}

	}
}