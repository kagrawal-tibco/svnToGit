package com.tibco.be.views.core.tasks{
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.IEventBusListener;
	import com.tibco.be.views.core.events.tasks.ConfigRequestEvent;
	import com.tibco.be.views.core.events.tasks.ControlRequestEvent;
	
	
	/**
	 * SimpleTaskService is responsible for listening to simple task request events on the event bus
	 * and initializing the appropriate ITaskHandler to hadle the actual work.
	 */
	public class ServerCommandService implements IEventBusListener{
		
		private static var _instance:ServerCommandService;
		
		/**
		 * SimpleTaskService constructor. Use of this constructor should be avoided.  Instead, use
		 * the instance property to utilize this singleton class.
		 */
		function ServerCommandService(){}
		
		/**
		 * The instance property provides access to the SimpleTaskService singleton class.
		 */
		public static function get instance():ServerCommandService{
			return _instance == null ? new ServerCommandService():_instance;
		}
		
		/**
		 * Initialize the IOService. Basically just registers all of the IOService's event listeners
		 * with the EventBus.
		 */
		public function init():void{
			_instance = new ServerCommandService();
			registerListeners();
		}
		
		public function handleTaskRequest(event:EventBusEvent):void{
			//Not sure this is even needed
		}
		
		public function handleConfigRequest(event:ConfigRequestEvent):void{
			var processor:ConfigProcessor = new ConfigProcessor(event);
			processor.process();
		}
		
		public function handleControlRequest(event:ControlRequestEvent):void{
			var processor:ControlProcessor = new ControlProcessor(event);
			processor.process();
		}
		
		//IEventBusListener
		public function registerListeners():void{
			EventBus.instance.addEventListener(EventTypes.SIMPLE_TASK, handleTaskRequest);
			EventBus.instance.addEventListener(EventTypes.CONFIG_COMMAND, handleConfigRequest);
			EventBus.instance.addEventListener(EventTypes.CONTROL_COMMAND, handleControlRequest);
		}
		
		public function isRecipient(event:EventBusEvent):Boolean{
			return true;
		}

	}
}