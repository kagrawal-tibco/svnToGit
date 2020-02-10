package com.tibco.be.views.core.logging{
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	
	import flash.events.StatusEvent;
	

	public class DefaultLogHandler implements ILogHandler{
		
		public function DefaultLogHandler(){
			
		}
		
		/**
		 * Takes an ILogable object and outputs the logging data associated with that object
		 */
		public function log(logData:ILogable):void{
			var date:Date = new Date();
			trace(
				date.toDateString() + " - " + 
				date.toLocaleTimeString() + ": " + getSeverityString(logData.logSeverity) + "\n\t" +
				logData.logMessage
			);
//			var lc:LocalConnection = new LocalConnection();
//			lc.addEventListener(StatusEvent.STATUS, onStatus);
//			lc.send("loggingClient", "test");
		}
		
		protected function getSeverityString(severity:uint):String{
			switch(severity){
				case(DefaultLogEvent.INFO): return "INFO"; break;
				case(DefaultLogEvent.DEBUG): return "DEBUG"; break;
				case(DefaultLogEvent.WARNING): return "WARNING"; break;
				case(DefaultLogEvent.CRITICAL): return "CRITICAL"; break;
				default:
					return "UNKNOWN";
			}
		}
		
		public function onStatus(event:StatusEvent):void{
			trace("DefaultLogHandler.onStatus - Local Connection issued status event: (" + event.level + ") " + event.code);
		}
		
	}
}