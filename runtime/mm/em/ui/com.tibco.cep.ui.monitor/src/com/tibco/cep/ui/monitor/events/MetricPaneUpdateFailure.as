package com.tibco.cep.ui.monitor.events{
	
	import flash.events.Event;

	public class MetricPaneUpdateFailure extends Event{

		private var _updateBatchId:int;
		private var _message:String;
		
		public function MetricPaneUpdateFailure(message:String, updateBatchId:int){
			super(EventTypes.PANE_UPDATE_FAILURE, true, false);
			_message = message;
			_updateBatchId = updateBatchId;
		}
		public function get message():String{ return _message; }
		public function get updateBatchId():int{ return _updateBatchId; }
		
	}
}