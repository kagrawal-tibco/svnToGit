package com.tibco.be.views.core.logging{

	public class DataPullLogHandler extends DefaultLogHandler{
		
		public function DataPullLogHandler(){
			super();
		}
		
		override public function log(logData:ILogable):void{
			var date:Date = new Date();
			trace(
				date.toDateString() + " - " + 
				date.toLocaleTimeString() + ": " + getSeverityString(logData.logSeverity) + "\n\t" +
				"DATA PULL REQUESTED - " + logData.logMessage
			);
		}
		
	}
}