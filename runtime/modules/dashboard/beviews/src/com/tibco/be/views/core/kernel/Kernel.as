 package com.tibco.be.views.core.kernel{
	
	import com.tibco.be.views.core.Configuration;
	import com.tibco.be.views.core.Session;
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.IEventBusListener;
	import com.tibco.be.views.core.events.tasks.ControlRequestEvent;
	import com.tibco.be.views.core.events.tasks.ControlResponseEvent;
	import com.tibco.be.views.core.events.tasks.ServerResponseEvent;
	import com.tibco.be.views.core.io.IOService;
	import com.tibco.be.views.core.logging.LoggingService;
	import com.tibco.be.views.core.tasks.ServerCommandService;
	import com.tibco.be.views.core.ui.mediator.UIMediator;
	import com.tibco.be.views.core.utils.IProgressListener;
	
	import flash.utils.Dictionary;
	
	import mx.controls.Alert;
	import mx.managers.CursorManager;
	import mx.utils.UIDUtil;
	
	/**
	 * The Kernel represents the core of the BEViews system. The Kernel loads all the neccessary services 
	 * to initialize the BEViews framework and facilitates the actual dashboard loading. The Kernel is not 
	 * to be directly instantiated but should be loaded via the KernelLoader
	 */
	public class Kernel implements IProgressListener, IEventBusListener{
		
		internal static var classInstance:Kernel = null;
		
		protected var _configuration:Configuration = null;
		protected var _bootType:String;
		protected var _bootParams:Dictionary;
		protected var _kernelEventListener:IKernelEventListener = null;
		protected var _uiMediator:UIMediator;
		
		/**
		 * The constructor of the Kernel. DO NOT CALL THIS DIRECTLY. 
		 * Use KernelLoader to load the correct type of Kernel
		 */
		function Kernel():void{
			_configuration = Configuration.instance;
			_configuration.progressListener = this;
		}
		
		public static function get instance():Kernel{ return classInstance; }
		
		public function get configuration():Configuration{ return _configuration; }
		public function get uimediator():UIMediator{ return _uiMediator; }
		
		/**
		 * Sets the KernelEventListener. If not set then the internal default kernel event listener is used 
		 * @param kernelEventListener The instance of the kernel listner which will handle the kernel load completion
		 */ 
		 internal function set kernelEventListener(kernelEventListener:IKernelEventListener):void{
		 	_kernelEventListener = kernelEventListener;
		 }
		
		/**
		 * Initializes the kernel. 
		 * Loads the configuration, initializes the logger and the io layer and then calls loaded(...) on KernelEventListener
		 * @param bootType the type of the boot
		 * @param bootParams any boot parameters
		 */
		internal function init(bootType:String, bootParams:Dictionary):void{
			_bootType = bootType;
			_bootParams = bootParams;
			if(_bootParams == null){
				_bootParams == new Dictionary();
			}
			startEventBusServices();
			registerListeners();
			_configuration.hostingURL = _bootParams["hostingURL"];
			var configReq:ControlRequestEvent = new ControlRequestEvent(ControlRequestEvent.GET_SERVER_CONFIG_COMMAND, this);
			configReq.addRequestParameter("timeout", "true");
			EventBus.instance.dispatchEvent(configReq);		 	
		}
		
		//Functions implemented from the ProgressListener
		public function startMainTask(taskName:String, taskUnits:Number=-1):void{ }
		
		public function startSubTask(taskName:String, taskUnits:Number=-1):void{ }
		
		public function worked(taskUnits:Number):Boolean{ return false; }
		
		public function errored(errMsg:String, errorEvent:ServerResponseEvent):void{
			_kernelEventListener.errored(errMsg, errorEvent);
		}
		
		public function completedSubTask():void{ }
		
		public function completedMainTask():void{
			//we call the kernel event listener's loaded API to pass on the control 
			_kernelEventListener.loaded(this, _bootParams);			
		}
		
		protected function handleConfigurationLoaded(response:ControlResponseEvent):void{
			if(!isRecipient(response)){ return; }
			CursorManager.removeBusyCursor();
			if(response.failMessage != ""){
				Alert.show("Failed to load server configuration! Please check server status and try again.", "ERROR");
				return;
			}
			Configuration.instance.handleConfigLoadFinish(response);
			//No longer need to listen for events on the EventBus...
			EventBus.instance.removeEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, handleConfigurationLoaded);
			_kernelEventListener.loaded(this, _bootParams);
		}		

		/**
		* Initalizes the UI Mediator. It is the responsiblity of the Kernel subclasses
		* to instantiate the right UI Mediator. The default implementation does nothing
		*/
		protected function initUIMediator():void{ }
		
		/**
		* Loads the appropriate UIMediator. 
		*/
		internal function loadUIMediator():void{
			initUIMediator();
		}
		
		public function reset():void{
			//clear all listeners from the event bus
			EventBus.instance.reset();
			//clear out any session information
			Session.instance.reset();
			//the event bus reset will wipe service listeners, so reinitialize them
			startEventBusServices();
			//reset the UI
			_uiMediator.uicontroller.currentPageSetId = null;
			_uiMediator.uicontroller.reset();
		}
		
		private function startEventBusServices():void{
			//Initialize EventBus services
			IOService.instance.init(_configuration);
			LoggingService.instance.init(LoggingService.DEBUG_MODE);
			ServerCommandService.instance.init();
		}
		
		public function registerListeners():void{
			EventBus.instance.addEventListener(EventTypes.CONTROL_COMMAND_RESPONSE, handleConfigurationLoaded, this);
		}
		
		public function isRecipient(event:EventBusEvent):Boolean{
			return (event.intendedRecipient == this);
		}				
	}
}