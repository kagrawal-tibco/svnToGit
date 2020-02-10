package com.tibco.be.views.core{
	import mx.collections.ArrayCollection;
	import mx.collections.IList;
	
	
	public class Session{
		
		private static var _instance:Session;
		
		private var _sessionId:String;
		
		//Main session variables
		private var _streamingChannelCreated:Boolean;
		private var _streamingSocketOpen:Boolean;
		
		//User-related session variables
		private var _token:String;
		private var _username:String;
		private var _roles:IList;
		private var _preferredRole:String;
		
		//Session variables for related elements 
		private var _typeId:String;
		//_tupId is shared
		
		public function Session(){
			_token = "";
			_streamingChannelCreated = false;
			_roles = new ArrayCollection();
		}
		
		public static function get instance():Session{
			if(_instance == null) _instance = new Session();
			return _instance;
		}
		
		//Getters
		public function get sessionId():String{ return _sessionId == null ? "":_sessionId; }
		
		public function get streamingChannelCreated():Boolean{ return _streamingChannelCreated; }
		public function get streamingSocketOpen():Boolean{ return _streamingSocketOpen; }
		
		public function get token():String{ return _token; }
		public function get roles():IList{ return _roles; }
		public function get preferredRole():String{ return _preferredRole; }
		public function get username():String{ return _username; }
		
		//Setters
		public function set streamingChannelCreated(value:Boolean):void{ _streamingChannelCreated = value; }
		public function set streamingSocketOpen(value:Boolean):void{ _streamingSocketOpen = value; }
		
		public function set token(value:String):void{ _token = value; }
		public function set roles(value:IList):void{ _roles = value; }
		public function set preferredRole(value:String):void{ _preferredRole = value; }
		public function set username(value:String):void{ _username = value; }
		
		public function set sessionId(value:String):void{ _sessionId = value; }
		
		public function reset():void{
			_sessionId = null;
			_streamingChannelCreated = false;
			_streamingSocketOpen = false;
			_token = null;
			_roles = null;
			_preferredRole = null;
			_sessionId = null;
		}

	}
}