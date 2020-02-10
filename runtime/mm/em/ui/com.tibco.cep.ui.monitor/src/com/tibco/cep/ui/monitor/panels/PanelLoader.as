package com.tibco.cep.ui.monitor.panels{
	import com.tibco.cep.ui.monitor.IUpdateable;
	import com.tibco.cep.ui.monitor.events.PanelReadyEvent;
	import com.tibco.cep.ui.monitor.pages.PanelPage;
	import com.tibco.cep.ui.monitor.panes.MetricPane;
	
	import flash.events.EventDispatcher;
	
	
	public class PanelLoader extends EventDispatcher implements IUpdateable{
		
		private var _failedPanes:int;
		private var _successPanes:int;
		private var _totalPanes:int;
		private var _panel:GeneralPanel;
		
		public function PanelLoader(panel:GeneralPanel){
			_panel = panel;
		}
		
		public function load():void{
			_totalPanes = 0;
			var page:PanelPage;
			//count #panes before calling init to avoid premature and/or multiple event dispatches
			for each(page in _panel.pageSet){
				_totalPanes += page.childPanes.length;
			}
			for each(page in _panel.pageSet){
				for each(var pane:MetricPane in page.childPanes){
					pane.init(this);
				}
			}
			if(_totalPanes == 0){
				dispatchEvent(new PanelReadyEvent());
			}
		}

		public function update(operation:String,data:XML):void {	//this callback is called after init() is called in MetriPane following pane.init(this) in here (line 32)
			_successPanes++;
			if(_successPanes + _failedPanes == _totalPanes){
				dispatchEvent(new PanelReadyEvent());
			}
		}
		
		public function updateFailure(operation:String,message:String,updateBatchId:uint):void {
			_failedPanes++;
			if(_successPanes + _failedPanes == _totalPanes){
				dispatchEvent(new PanelReadyEvent());
			}
		}

	}
}