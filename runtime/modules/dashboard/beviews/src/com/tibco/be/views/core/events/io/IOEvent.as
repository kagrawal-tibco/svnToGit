package com.tibco.be.views.core.events.io{
	
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;

	public class IOEvent extends EventBusEvent{
		
		/** When the request is successfully processed and a response XML is received from the server */
		public static const SUCCESS:int = 1;

		/** Indicates that there was an error with the request sent by the caller */
		public static const REQUEST_ERROR:int = 2;
		
		/** Indicates that there was an error with the response sent by the server */
		public static const RESPONSE_ERROR:int = 3;

		/** This is a flex level error, typically an IO error */
		public static const SYSTEM_ERROR:int = 4;

		public function IOEvent(type:String=null, intendedRecipient:Object=null){
			if(type==null){ type=EventTypes.IO; }
			super(type, intendedRecipient);
		}
		
	}
}