package{
	
	public interface IBEVLocalConnectionClient{
		
		function receiveMessage(message:String):void;
		function receiveEventData(eventData:XML):void;
		
	}
}