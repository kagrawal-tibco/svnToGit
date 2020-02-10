package{

	import flash.events.StatusEvent;
	import flash.net.LocalConnection;
	import flash.utils.Dictionary;
	
	public class BEVLocalConnectionManager{
		
		public static var BEV_LC_NAME:String = "bevlocalconnection";
		
		/**
		 * Name of the method used by the LocalConnection client when receiving a send from the
		 * LocalConnection server. Note a send here corresponds to a receive at the client, thus
		 * the disconnect between variable name and value.
		*/
		public static var BEVIEWS_LC_SEND_MESSAGE:String = "receiveMessage";
		
		private static var _instance:BEVLocalConnectionManager;
		
		private var _serverLC:LocalConnection;
		private var _clientToConnectionMap:Dictionary;
		
		public function BEVLocalConnectionManager(){
			_clientToConnectionMap = new Dictionary();
			_serverLC = new LocalConnection();
			_serverLC.allowDomain("*");
			_serverLC.addEventListener(StatusEvent.STATUS, handleStatusEvent);
		}
		
		private function handleStatusEvent(event:StatusEvent):void{
			//ignore
		}
		
		public static function get instance():BEVLocalConnectionManager{
			if(_instance == null){ _instance = new BEVLocalConnectionManager(); }
			return _instance;
		}
		
		public function sendMessage(message:String, connectionName:String=null):void{
			if(connectionName == null){ connectionName = BEV_LC_NAME; }
			_serverLC.send(connectionName, BEVIEWS_LC_SEND_MESSAGE, message);
		}
		
		public function registerClient(client:IBEVLocalConnectionClient):void{
			var lc:LocalConnection = new LocalConnection();
			lc.connect(BEV_LC_NAME);
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