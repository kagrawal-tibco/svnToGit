package com.tibco.be.views.core{
	/**
	 * Class writes the error string onto the debug console of the flash builder
	 */
	 
	public class ConsoleAppender implements ILogAppender {
		
		public function init(params:XML):void {

		}//end of init
		
		/**
		 * Function writes the message string onto the console
		 * @public
		 * @param debug level
		 * @param family under which this debug message falls
		 * @param the debug message
		 * @param exception object
		 */
		public function log(level:Number, logFamily:String, message:String, exception:Object):void {
			//write the message onto the console
			//var errorStr:String = (new Date()) + "::"+ Logger.getLogString(level) + "::[" + logFamily + "] - " + message;
			var errorStr:String = (new Date()) + "::" + "::[" + logFamily + "] - " + message;
			trace(errorStr);
		}//end of log
		
	}
}