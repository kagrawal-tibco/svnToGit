package com.tibco.cep.ui.monitor.io {
	
	import com.tibco.cep.ui.monitor.IUpdateable;
	import com.tibco.cep.ui.monitor.panes.MetricPane;
	
	public class RemoteSubscriptionProccessor extends DelegateUpdateableImpl {
		
		private var _monitoredID:String;
		private var _metricPane:MetricPane;
		private var _psvrUpdatesClient:IPSVRUpdatesClient;
		
		public function RemoteSubscriptionProccessor(monitoredID:String,metricPane:MetricPane,psvrUpdatesClient:IPSVRUpdatesClient,caller:IUpdateable,callBack:IUpdateable) {
			_monitoredID = monitoredID;
			_metricPane = metricPane;
			_psvrUpdatesClient = psvrUpdatesClient;
			super(caller,callBack);
		}

		public override function update(operation:String,data:XML):void {
			if (data.children().length() > 0 && (data.child("errorcode").length() != 0)) {
				if(_callBack != null) _callBack.updateFailure(operation,data.errormessage,0);
				if(_caller != null)_caller.updateFailure(operation,data.errormessage,0);
			}
			else {
				if (operation == "subscribepane") {
					_psvrUpdatesClient.subscribePane(_monitoredID,_metricPane);
				}
				else if (operation == "unsubscribepane") {
					_psvrUpdatesClient.unsubscribePane(_monitoredID,_metricPane);
				}
				else if (operation == "unsubscribeallpanes") {
					_psvrUpdatesClient.unsubscribeAllPanes();
				}
				if(_caller != null) _caller.update(operation,data);
				if(_callBack != null) _callBack.update(operation,data);
			}
		}
		
	}
}