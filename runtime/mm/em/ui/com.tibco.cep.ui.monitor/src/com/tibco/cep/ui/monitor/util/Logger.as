package com.tibco.cep.ui.monitor.util
{
	import flash.utils.getQualifiedClassName;
	
	import mx.formatters.DateFormatter;
	import mx.utils.StringUtil;
	
	public class Logger
	{
		private static const ERROR:String = "ERROR] ";
		private static const INFO:String = "INFO] ";
		private static const WARNING:String = "WARNING] ";
		private static const DEBUG:String = "DEBUG] ";
		
		private static function getCurrentDateTime():String {               
		    var currentDateTime:Date = new Date();
		    var currentDF:DateFormatter = new DateFormatter();
		    currentDF.formatString = "YYYY MMM DD HH:NN:SS"
		    var dateTimeString:String = currentDF.format(currentDateTime);
		    return dateTimeString;
		}

		/** In the String 'msg', the patterns of the form {0}{1}...{n} will be replaced by the corresponding 
		 *  paramater in 'parms' */	
		public static function log(obj:Object, msg:String, ...params):void {
			var msg1:String = StringUtil.substitute(msg,params);
			trace(getCurrentDateTime() +" ["+flash.utils.getQualifiedClassName(obj)+"][" + msg1);
		}
		
		public static function logError(obj:Object, msg:String, ...params):void {
			log(obj, ERROR + msg, params);
		}
		
		public static function logInfo(obj:Object, msg:String, ...params):void {
			log(obj, INFO + msg, params);
		}
		
		public static function logWarning(obj:Object, msg:String, ...params):void {
			log(obj, WARNING + msg, params);
		}
		
		public static function logDebug(obj:Object, msg:String, ...params):void {
			log(obj, DEBUG + msg, params);
		}
		
		public static function logMsg(msg:String):void {
			trace(msg);
		}
		
	}
}