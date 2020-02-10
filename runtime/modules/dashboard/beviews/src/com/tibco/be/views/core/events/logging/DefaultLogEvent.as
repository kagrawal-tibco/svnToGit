package com.tibco.be.views.core.events.logging{
	
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.logging.ILogable;
	import com.tibco.be.views.core.logging.LoggingService;
	
	import flash.events.Event;

	/**
	 * Default log event provides basic means for issuing log content.
	 */
	public class DefaultLogEvent extends EventBusEvent implements ILogable{
		
		public static const INFO:uint = 0x1;
		public static const DEBUG:uint = 0x2;
		public static const WARNING:uint = 0x4;
		public static const CRITICAL:uint = 0x8;
		
		private var _severity:uint;
		private var _message:String;
		
		public function DefaultLogEvent(severity:uint, message:String){
			super(EventTypes.LOG);
			_severity = severity;
			_message = message;
		}
		
		override public function clone():Event{
			return new DefaultLogEvent(_severity, _message);
		}

		/**
		 * A string representing the severity of the log message. Possible values could be:
		 * "critical", "warning", "normal", "info", etc.
		 */
		override public function get logSeverity():uint{
			return _severity;
		}
		
		/**
		 * The message to display in the log.
		 */
		override public function get logMessage():String{
			return _message;
		}
		
		override protected function get details():String{
			return "Default Log Event (" + _severity + "): " + _message;
		}
		
	}
}