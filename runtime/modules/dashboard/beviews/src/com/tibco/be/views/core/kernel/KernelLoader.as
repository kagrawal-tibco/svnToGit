package com.tibco.be.views.core.kernel{
	
	import flash.utils.Dictionary;
	
	public final class KernelLoader{
		
		/**
		 * The boot type for standalone version. The stand alone version represents 
		 * the entire BEViews as it exists
		*/ 
		public static var STANDALONE_MODE:String = "standalone";
		
		/**
		 * The boot type for instances launched from an already running instance of BEViews. The
		 * DrillDown application, for instance, will launch from an already-running instance and
		 * open in a new browser window.  The application instance used by DrillDown will use this
		 * mode as it is a child of the main application.
		*/
		public static var CHILD_MODE:String = "childmode";

		/**
		 * The boot type for embedded version. The embedded version represents 
		 * BEViews instances as integrated in portal pages and other applications
		 * For Future Purpose Only
		*/		
		public static var EMBEDDED_MODE:String = "embedded";
		
		/**
		 * The boot type for testing version. 
		 * For Testing Purpose Only
		*/		
		public static var TEST_MODE:String = "test";				
		
		private var _kernel:Kernel = null;
		private var _kernelEventListener:IKernelEventListener = null;
		
		private var _bootParams:Dictionary = null;
		
		/**
		 * Constructor
		 * @param bootType The type of envoirenment to create
		 * @param configURL The URL from where to fetch the configuration. If null, then BEViews will load configuration from local file system
		 * @param userName The name of the user to log in with. Can be null
		 * @param password The password for the user. Can be null
		 * @param role The role of the user. Can be null
		 * @param sToken The security token. If present takes precedence over userName
		*/
		public function KernelLoader(bootType:String, configURL:String=null, sToken:String=null, userName:String=null, password:String=null, role:String=null):void{
		 	switch(bootType){
				case(CHILD_MODE):
					_kernel = new ChildApplicationKernel();
					break;
				case(EMBEDDED_MODE):
					_kernel = new EmbeddedKernel();
					break;
				case(TEST_MODE):
				case(STANDALONE_MODE):
					_kernel = new StandAloneKernel();
					break;			 					 		
				default:
					throw new Error("Invalid["+bootType+"] boot type");
			}
			_bootParams = new Dictionary();
			_bootParams["boottype"] = bootType;
			_bootParams["configurl"] = configURL;
			if(userName != null && password != null && role != null){
	 			_bootParams["username"] = userName;
	 			_bootParams["password"] = password;
	 			_bootParams["role"] = role;	
		 	}
		 	else if(sToken != null){
		 		_bootParams["stoken"] = sToken;
		 	}
		 }//end of constructor
		 
		 public function set params(params:Dictionary):void {
		 	for (var paramname:String in params){
	 			if (_bootParams[paramname] == null) {
	 				_bootParams[paramname] = params[paramname];
	 			}
		 	} 
		 }
		 
		 public function addParam(name:String, value:String):void {
		 	if (name != null) {
		 		_bootParams[name] = value;
		 	}
		 }
		 
		 /**
		 * Sets the kernel listener. Kernel Listener gets call backs whenever the kernel has finished loading. 
		 * This provides means to the Kernel Loader user to decide how to handle kernel load completion
		 * If not set,then the kernel uses an internal default listener which triggers the dashboard loading
		 * @param _kernelEventListener The instance of KernelEventListener which will handle kernel loaded completion
		 */
		 public function set kernelEventListener(kernelEventListener:IKernelEventListener):void{
		 	_kernelEventListener = kernelEventListener;
		 }
		 
		 /**
		 * Triggers the actual loading of the kernel
		 */
		 public function load():void{
		 	if(_kernel != null){
		 		if(_kernelEventListener == null){
		 			_kernelEventListener = new DefaultKernelEventListener();
		 		}
		 		_kernel.kernelEventListener = _kernelEventListener;		 		
		 		Kernel.classInstance = _kernel;
		 		_kernel.init(_bootParams["boottype"] as String, _bootParams);
		 	}		 	
		 }

	}
}