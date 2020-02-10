package com.tibco.be.views.core.utils {
	
	import com.tibco.be.views.core.events.tasks.ServerResponseEvent;
	
	/**
	 * This is a utility interface which provides APIs to listen for monitoring messages from a ‘ProgressProvider’
	 */
	public interface IProgressListener {
		function startMainTask(taskName:String,taskUnits:Number = -1):void;
		function startSubTask(taskName:String,taskUnits:Number = -1):void;
		function worked(taskUnits:Number):Boolean;
		function errored(errMsg:String,errorEvent:ServerResponseEvent):void;
		function completedSubTask():void;
		function completedMainTask():void;
	}
}