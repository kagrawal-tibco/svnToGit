package com.tibco.be.views.core.logging{
	
	public interface ILogable{
		function get logSeverity():uint;
		function get logMessage():String;
	}
}