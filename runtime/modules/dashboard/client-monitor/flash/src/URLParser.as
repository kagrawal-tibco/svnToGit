package{

	import flash.utils.Dictionary;
	
	public class URLParser{
		
		private static const FULL_URL:int = 0;
		private static const PROTOCOL:int = 2;
		private static const DOMAIN:int = 3;
		private static const PORT:int = 5;
		private static const PATH:int = 6;
		private static const FILE:int = 8;
		private static const QUERY_STRING:int = 10;
		private static const HASH:int = 12;
		
		//										  (-------protocol------------)(--domain-)(--port---) (--path----)(------file name--) (--query--) (-hash)
		private static var _urlRegExp:RegExp = /^((http[s]?|ftp|file):\/\/\/?)?([^:\/\s]+)(:([^\/]*))?((\/\w+)*\/)([\w\-\.]+[^#?\s]+)?(\?([^#]*))?(#(.*))?$/;
		
		private var _urlToParse:String;
		private var _parsedURL:Object; //the Object result of applying the URL parsing RegEx
		private var _queryParams:Dictionary;
		private var _parsedProtocol:String;
		private var _parsedDomain:String;
		private var _parsedPort:String;
		private var _parsedPath:String;
		private var _parsedFileName:String;
		private var _parsedQueryString:String;
		private var _parsedHash:String;
		
		public function URLParser(urlToParse:String=""){
			_urlToParse = urlToParse;
			_queryParams = new Dictionary();
			_parsedProtocol = "";
			_parsedDomain = "";
			_parsedPort = "";
			_parsedPath = "";
			_parsedFileName = "";
			_parsedQueryString = "";
			_parsedHash = "";
		}
		
		public function get parsedProtocol():String{ return _parsedProtocol; }
		public function get parsedDomain():String{ return _parsedDomain; }
		public function get parsedPort():String{ return _parsedPort; }
		public function get parsedPath():String{ return _parsedPath; }
		public function get parsedFileName():String{ return _parsedFileName; }
		public function get parsedQueryString():String{ return _parsedQueryString; }
		public function get parsedHash():String{ return _parsedHash; }
		public function get fullURLPath():String{
			return (_parsedProtocol == "" ? "": _parsedProtocol + "://") + _parsedDomain + (_parsedPort == "" ? "":":" + _parsedPort) + _parsedPath;
		}
		
		public function getQueryParam(name:String):Object{
			return _queryParams[name] ? _queryParams[name]:null;
		}
		
		public function parse(urlToParse:String=""):void{
			if(urlToParse != ""){ _urlToParse = urlToParse;}
			if(_urlToParse == ""){
				trace("URLParser.parseURL - WARNING parsing empty URL");
			}
			_queryParams = new Dictionary();
			try{
				_parsedURL = _urlRegExp.exec(_urlToParse);
				var queryString:String = _parsedURL[QUERY_STRING] as String;
				if(queryString != null){
					var params:Array = queryString.split("&");
					for(var i:int = 0; i < params.length; i++){
						var kvPair:Array = String(params[i]).split("=");
						_queryParams[kvPair[0]] = kvPair[1];
					}
				}
				setProperties();
			}
			catch(error:Error){
				trace("URLParser.parseQueryString - " + error.getStackTrace());
			}
		}
		
		private function setProperties():void{
			_parsedProtocol = getParsedValue(PROTOCOL);
			_parsedDomain = getParsedValue(DOMAIN);
			_parsedPort = getParsedValue(PORT);
			_parsedPath = getParsedValue(PATH);
			_parsedFileName = getParsedValue(FILE);
			_parsedQueryString = getParsedValue(QUERY_STRING);
			_parsedHash = getParsedValue(HASH);
		}
		
		private function getParsedValue(id:int):String{
			if((_parsedURL as Array) == null || (_parsedURL as Array).length <= id || _parsedURL[id] == undefined){
				return "";
			}
			return _parsedURL[id] as String;
		}
	}
}