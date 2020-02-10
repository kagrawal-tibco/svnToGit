package com.tibco.cep.ui.monitor.io
{
	import com.tibco.cep.ui.monitor.IUpdateable;

	internal class DelegateMetricPaneUpdateable implements IUpdateable {
		
		private var callBack:IUpdateable;
		private var updateBatchId:uint;
		
		function DelegateMetricPaneUpdateable(callBack:IUpdateable, updateBatchId:uint) {
			this.callBack = callBack;
			this.updateBatchId = updateBatchId;
		}

		public function update(operation:String, data:XML):void {
			if (operation == "getpanedata") {
				//change the operation name to "updatepanedata" because this update 
				//is triggered periodically for each pane, in the TimerBasedPSVRUpdatesClient class
				callBack.update("updatepanedata",data);		 
			}
		}
		
		public function updateFailure(operation:String, message:String, code:uint):void {
			callBack.updateFailure("updatepanedata",message,updateBatchId);			
		}
		
	}
}