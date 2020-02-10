package com.tibco.be.views.core.logging{
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.IEventBusListener;
	import com.tibco.be.views.core.events.io.PullDataRequestEvent;
	
	/**
	 * LoggingService is responsible for listening to log events on the event bus and initializing
	 * the appropriate ILogHandler to hadle the actual logging work.
	 */
	public class LoggingService implements IEventBusListener{
		
		public static const INFO_MODE:uint = 0x1;
		public static const DEBUG_MODE:uint = 0x2;
		public static const WARNING_MODE:uint = 0x4;
		public static const CRITICAL_MODE:uint = 0x8;
		
		private static var _instance:LoggingService;
		private static var _mode:int = WARNING_MODE;
		
		/**
		 * LoggingService constructor. Use of this constructor should be avoided.  Instead, use the
		 * instance property to utilize this singleton class.
		 */
		function LoggingService(){}
		
		/**
		 * The instance property provides access to the Logging Service singleton class.
		 */
		public static function get instance():LoggingService{
			return _instance == null ? new LoggingService():_instance;
		}
		
		/**
		 * Initialize the DefaultLogEvent. Basically just registers all of the LoggingService's event
		 * listeners with the EventBus.
		 */
		public function init(mode:uint=WARNING_MODE):void{
			_instance = new LoggingService();
			if(mode == CRITICAL_MODE || mode == WARNING_MODE || mode == DEBUG_MODE || mode == INFO_MODE){
				_mode = mode;
			}
			else{
				_mode = WARNING_MODE;
			}
			registerListeners();
		}
		
		/**
		 * Consumes BEViewsEvent and spawns an appropriate ILogHandler to perform the logging
		 * operation.
		 */
		private function handleLogEvent(event:EventBusEvent):void{
			if(!isRecipient(event) || !(event is ILogable) || event.logSeverity < _mode) return;
			else if(event is PullDataRequestEvent){
				new DataPullLogHandler().log(event);
			}
			else{
				new DefaultLogHandler().log(event);
			}
		}
		
		//IEventBusListener
		public function registerListeners():void{
			EventBus.instance.addEventListener(EventTypes.LOG, handleLogEvent);
//			EventBus.instance.addEventListener(EventTypes.PULL_DATA_REQUEST, handleLogEvent);
//			EventBus.instance.addEventListener(EventTypes.STREAM_DATA_REQUEST, handleLogEvent);
//			EventBus.instance.addEventListener(EventTypes.STREAM_DATA_RESPONSE, handleLogEvent);
		}
		
		public function isRecipient(event:EventBusEvent):Boolean{
			return true;
		}

	}
}