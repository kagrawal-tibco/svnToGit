package com.tibco.be.views.core{
	import com.tibco.be.views.core.events.tasks.ControlResponseEvent;
	import com.tibco.be.views.core.io.StreamMode;
	import com.tibco.be.views.core.utils.IProgressListener;
	import com.tibco.be.views.utils.URLParser;
	
	
	public class Configuration{
		
		public static const REMOTE_PROPERTIES_URL:String = "clientconfig.xml";
		public static const LOCAL_PROPERTIES_URL:String = "../properties/configuration.xml";
			
		public static const DEFAULT_UPDATE_FREQUENCY:Number = 5000;
		public static const DEFAULT_UPDATE_RECONNECT_FREQUENCY:Number = 1000;
		public static const DEFAULT_UPDATE_RECONNECT_THRESHOLD:Number = 3;
		public static const DEFAULT_TIME_OUT:Number = -1;
		public static const DEFAULT_DB_CHANNEL_NAME:String = "dashboard";
		
		private static var _instance:Configuration;
		
		//Basic config properties
		private var _isConfigurationLoaded:Boolean = false;
		private var _configXML:XML = null;
		private var _progressListener:IProgressListener = null;
		private var _localConnectionName:String = "";
		private var _hostingURL:String = null;
		
		//Server config
		private var _serverName:String;
		private var _serverPort:String;
		private var _serverBaseURL:String;
		private var _commandContextPath:String;
		private var _updatePort:Number;
		private var _updateMode:String;
		private var _updateFrequency:Number = DEFAULT_UPDATE_FREQUENCY;
		private var _updateReconnectFrequency:Number = DEFAULT_UPDATE_RECONNECT_FREQUENCY;
		private var _updateReconnectThreshold:Number = DEFAULT_UPDATE_RECONNECT_THRESHOLD;		
				
		//Server-specified configuration properties				
		private var _debugOn:Boolean = false;
		private var _xmlDir:String;
		
		//deprecated parameters		
		private var _timeOut:Number = DEFAULT_TIME_OUT;
		
		public function Configuration(){
			
		}
		
		public static function get instance():Configuration{
			if(_instance == null){ _instance = new Configuration(); }
			return _instance;
		}
		
		//----------------------
		//PROPERTIES
		//----------------------
		public function get isConfigurationLoaded():Boolean{ return _isConfigurationLoaded; }
		
		public function get serverName():String{ return _serverName; }
		public function get serverPort():String{ return _serverPort; }
		public function get serverBaseURL():String{ return _serverBaseURL; }
		public function get serverCommandURL():String{ return _serverBaseURL + _commandContextPath; }
		public function get commandContextPath():String{ return _commandContextPath; }
		public function get updatePort():Number{ return _updatePort; }
		public function get updateMode():String{ return _updateMode; }
		public function get updateFrequency():Number{ return _updateFrequency; }
		public function get updateReconnectFrequency():Number{ return _updateReconnectFrequency; }
		public function get updateReconnectThreshold():Number{ return _updateReconnectThreshold; }
		
//
		public function get isDebugOn():Boolean{ return _debugOn; }		
		public function get xmlDir():String{ return _xmlDir; }
		
		/**
		 * @deprecated
		 */ 
		public function get timeOut():Number{ return _timeOut; }	
		public function get localConnectionName():String{ return _localConnectionName; }
		
		
		//----------------------
		//END OF PROPERTIES
		//----------------------	
		
		public function set progressListener(value:IProgressListener):void{ _progressListener = value; }
		public function set localConnectionName(value:String):void{ _localConnectionName = value; }
		public function set hostingURL(value:String):void{ _hostingURL = value; };
		
		 /**
		 * Function gets triggered once the Configuration XML loads fully
		 * @public
		 * @param event object
		 */
		 public function handleConfigLoadFinish(response:ControlResponseEvent):void{
		 	_configXML = new XML(response.data);
		 	_isConfigurationLoaded = true;
		 	extractProperties();
		 }
		 
//		 public function manualSetup(serverName:String, serverPort:String, debug:Boolean, contextPath:String, updatePort:int, reconnectAttempts:int= DEFAULT_UPDATE_RECONNECT_THRESHOLD):void{
//		 	_serverName = serverName;
//			_serverPort = serverPort;
//			_serverBaseURL = "http://" + _serverName + (_serverPort == "" ? "":":" + _serverPort);
//		 	_debugOn = debug;
//		 	_xmlDir = "undefined";
//		 	_commandContextPath = contextPath;
//		 	_updatePort = updatePort;
//		 	_updateReconnectThreshold = reconnectAttempts;
//		 }
		 
		 /**
		 * Function extracts the properties from the loaded config XML
		 * @private
		 */
		 private function extractProperties():void{
		 	//server name 
		 	_serverName = new String(_configXML.hostname);
		 	//server port number
 			_serverPort = new String(_configXML.pullrequestport);
		 	if (_hostingURL != null) {
				var urlParser:URLParser = new URLParser(_hostingURL);
				urlParser.parse();
				if (urlParser.parsedProtocol == "http" || urlParser.parsedProtocol == "https") {
					_serverName = "";
					_serverPort = "";
				}		 		
		 	}
 			//server base url 
 			if (_serverName == "") {
 				_serverBaseURL = "";
 			}
 			else {
				_serverBaseURL = "http://" + _serverName + (_serverPort == "" ? "":":" + _serverPort);
			}
			//debug
		 	_debugOn = (_configXML.debug == true)? true:false;
		 	//xml directory 
		 	_xmlDir = (_configXML.xmldir.@load != false) ? new String(_configXML.xmldir):"undefined";
		 	//conext path 
		 	_commandContextPath = new String(_configXML.pullrequestbaseurl);
		 	//update mode 
		 	_updateMode = new String(_configXML.updatemode);
		 	if (_updateMode != StreamMode.TCP && _updateMode != StreamMode.HTTP) {
		 		_updateMode = StreamMode.TCP;
		 	}
		 	//streaming port 
		 	_updatePort = parseInt(_configXML.streamingport);
		 	//update frequency 
		 	_updateFrequency = parseInt(_configXML.updatefrequency);
		 	if (isNaN(_updateFrequency) == true) {
		 		_updateFrequency = DEFAULT_UPDATE_FREQUENCY;
		 	}
		 	//streaming reconnect threshold 
		 	_updateReconnectThreshold = parseInt(_configXML.streamingreconnectattempts);
		 	if(isNaN(_updateReconnectThreshold) == true) {
		 		_updateReconnectThreshold = DEFAULT_UPDATE_RECONNECT_THRESHOLD;
		 	}
		 	//streaming reconnect frequency 
		 	_updateReconnectFrequency = parseInt(_configXML.streamingreconnectfrequency);
			if(isNaN(_updateReconnectFrequency) == true) {
				_updateReconnectFrequency = DEFAULT_UPDATE_RECONNECT_FREQUENCY;
			}
			trace("_serverName is "+_serverName);
			trace("_serverPort is "+_serverPort);
			trace("_serverBaseURL is "+_serverBaseURL);
		 }
		 
		 public function reset():void{
		 	_isConfigurationLoaded = false;
			_configXML = null;
			_progressListener = null;
			
			//Server config
			_serverName = "";
			_serverPort = "";
			_serverBaseURL = "";
			_commandContextPath = "";
					
			//Server-specified configuration properties				
			_debugOn = false;
			_xmlDir = "";
			_updatePort = 0;
			_updateReconnectFrequency = DEFAULT_UPDATE_RECONNECT_FREQUENCY;
			_updateReconnectThreshold = DEFAULT_UPDATE_RECONNECT_THRESHOLD;		
			_updateFrequency = DEFAULT_UPDATE_FREQUENCY;
			_timeOut = DEFAULT_TIME_OUT;	
		 }
	
	}
}