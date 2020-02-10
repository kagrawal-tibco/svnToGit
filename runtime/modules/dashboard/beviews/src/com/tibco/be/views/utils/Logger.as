package com.tibco.be.views.utils{
	
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	
	/**
	 * Logger is a BEViews-specific utility class for simplifying the handling of log messages. Note
	 * that the logging service is a separate module from the BEViews application (independent
	 * service on the EventBus). Thus, it is improper for other non-applicaion modules (IO for
	 * example) to utilize this class.
	*/
	public class Logger{
		
		public static function log(severity:uint, message:String):void{
			EventBus.instance.dispatchEvent(new DefaultLogEvent(severity, message));
		}
		
	}
}