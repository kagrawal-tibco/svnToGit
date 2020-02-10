package com.tibco.be.views.core{

	public interface IBEVLocalConnectionClient{
		
		//CRITICAL: These function names must match the string names defined in BEVLocalConnectionManager
		function receiveMessage(message:String):void;
		function receiveEventData(eventData:XML):void;
		function receiveLCRequest(lcRequestXML:XML):void;
		function receiveLCResponse(lcResponseXML:XML):void;
		
		/* For copy paste purposes:
		public function receiveMessage(message:String):void{
			
		}
		public function receiveEventData(eventData:XML):void{
			
		}
		public function receiveLCRequest(lcRequestXML:XML):void{
			
		}
		public function receiveLCResponse(lcResponseXML:XML):void{
			
		}
		*/
		
	}
}