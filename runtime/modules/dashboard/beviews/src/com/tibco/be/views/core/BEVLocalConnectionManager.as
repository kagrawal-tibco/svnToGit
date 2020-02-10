package com.tibco.be.views.core{

	import com.tibco.be.views.core.events.EventBusEvent;
	
	import flash.events.StatusEvent;
	import flash.net.LocalConnection;
	import flash.utils.Dictionary;
	
	public class BEVLocalConnectionManager{
		
		/**
		 * Name of the method used by the LocalConnection client when receiving a send from the
		 * LocalConnection server. Note a send here corresponds to a receive at the client, thus
		 * the disconnect between variable name and value.
		*/
		public static var BEVIEWS_LC_SEND_MESSAGE:String = "receiveMessage";
		public static var BEVIEWS_LC_SEND_EVENT_DATA:String = "receiveEventData";
		public static var BEVIEWS_LC_SEND_REQUEST:String = "receiveLCRequest";
		public static var BEVIEWS_LC_SEND_RESPONSE:String = "receiveLCResponse";
		
		private static var _instance:BEVLocalConnectionManager;
		
		private var _connectionName:String;
		private var _serverLC:LocalConnection;
		private var _clientToConnectionMap:Dictionary;
		
		public function BEVLocalConnectionManager(){
			_clientToConnectionMap = new Dictionary();
			_serverLC = new LocalConnection();
			_serverLC.allowDomain("*");
			_serverLC.addEventListener(StatusEvent.STATUS, handleStatusEvent);
		}
		
		private function handleStatusEvent(event:StatusEvent):void{
			//Alert.show("BEVLocalConnectionManager.status: " + event.toString());
		}
		
		public static function get instance():BEVLocalConnectionManager{
			if(_instance == null){ _instance = new BEVLocalConnectionManager(); }
			return _instance;
		}
		
		public function get connectionName():String{ return _connectionName; }
		public function set connectionName(value:String):void{ _connectionName = value; }
		
		public function sendMessage(message:String, connectionName:String=null):void{
			if(connectionName == null){ connectionName = _connectionName; }
			if(connectionName == null){ return; }
			_serverLC.send(connectionName, BEVIEWS_LC_SEND_MESSAGE, message);
		}
		
		public function sendEventData(eventData:XML, connectionName:String=null):void{
			if(connectionName == null){ connectionName = _connectionName; }
			if(connectionName == null){ return; }
			_serverLC.send(connectionName, BEVIEWS_LC_SEND_EVENT_DATA, eventData);
		}
				
		public function sendLCRequest(localConnectionRequestXML:XML, connectionName:String=null):void{
			if(connectionName == null){ connectionName = _connectionName; }
			if(connectionName == null){ return; }
			_serverLC.send(connectionName, BEVIEWS_LC_SEND_REQUEST, localConnectionRequestXML);
		}
		
		public function sendLCResponse(localConnectionResponseXML:XML, connectionName:String=null):void{
			if(connectionName == null){ connectionName = _connectionName; }
			if(connectionName == null){ return; }
			_serverLC.send(connectionName, BEVIEWS_LC_SEND_RESPONSE, localConnectionResponseXML);
		}
		
		public function registerClient(client:IBEVLocalConnectionClient, connectionName:String=null):void{
			var lc:LocalConnection = new LocalConnection();
			try{
				lc.connect(connectionName == null ? _connectionName:connectionName);
			}
			catch(error:Error){
				trace(error.message);
			}
			lc.client = client;
			_clientToConnectionMap[client] = lc;
		}
		
		public function unregisterClient(client:IBEVLocalConnectionClient):void{
			var clientLC:LocalConnection = _clientToConnectionMap[client] as LocalConnection;
			if(clientLC == null){ return; }
			clientLC.close();
			delete _clientToConnectionMap[client];
		}

	}
}