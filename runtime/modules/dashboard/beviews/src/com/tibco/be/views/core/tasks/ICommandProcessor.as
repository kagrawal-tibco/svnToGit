package com.tibco.be.views.core.tasks{
	
	public interface ICommandProcessor{
		function get command():String;
		function process():void;
		function processorError(functionName:String, message:String):void;
	}
	
}