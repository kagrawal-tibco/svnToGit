package com.tibco.be.views.core {
	/**
	 * <p>The log appender provides means by which statements logged via the Logger can be exposed to 
	 * external world. Having a logger with no log appender is equivalent to not having logging at all. 
	 * The following log appenders will be provided initially. The list of appenders can be configured via 
	 * the Configuration class</p>
	 * <ul><li>ConsoleAppender – This is equivalent of using ‘trace’ API in Flash</li>
	 * <li>LocalConnectionAppender – This pushes all the log statements on a well defined local connection</li>
	 * <li>LogFileAppender – This pushes all the log statements to a well defined log file</li>
	 * <li>RemoteConnectionAppender – This POST’s all the log statements over the wire to a http URL. 
	 * The implementation may or may add buffering to alleviate performance problems.  
	 * The posting URL can be http://localhost:6161/insigh/remotelogappender</li>
	 * 
	 * NOTE - currently supporting only ConsoleAppender
	 */

	public interface ILogAppender {
		
		function log(level:Number, logFamily:String, message:String, exception:Object):void;
		function init(params:XML):void;
		
	}
}