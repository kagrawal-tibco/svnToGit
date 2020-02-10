package com.tibco.cep.ui.monitor.panels
{
	import com.tibco.cep.ui.monitor.BESystemMonitorController;
	import com.tibco.cep.ui.monitor.TopologyMenuController;
	import com.tibco.cep.ui.monitor.io.PSVRClient;
	import com.tibco.cep.ui.monitor.sitetopology.Host;
	import com.tibco.cep.ui.monitor.util.Logger;
	import com.tibco.cep.ui.monitor.util.TopologyConstants;
	import com.tibco.cep.ui.monitor.util.Util;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	import mx.events.FlexEvent;
	
	public class ThreadAnalyzerPanelController extends PopupPanelController
	{
//		private var _monitoredNode:XML;
		private var _currentPanel:GeneralPanel;
		private var _beSystemMonitorController:BESystemMonitorController;
		private var _view:ThreadAnalyzerPanel;
//		private var _topologyTreeController:TopologyMenuController;
		
		public function ThreadAnalyzerPanelController(monitoredNode:XML, currentPanel:GeneralPanel, 
							topologyTreeController:TopologyMenuController, beSystemMonitorController:BESystemMonitorController) {
																			
			_monitoredNode = monitoredNode;																
			_currentPanel = currentPanel;
			_topologyTreeController = topologyTreeController;
			_beSystemMonitorController = beSystemMonitorController;
		}
		
//		public function get view():ThreadAnalyzerPanel { return _view; } 
		
		public override function createView():void {
			_view = new ThreadAnalyzerPanel();
			var key:String = String(_monitoredNode.@id);
			
			//Thread analyzer is Off and we want to turn it On
			if (_topologyTreeController.puToThreadAnalyzerIsOn[key] == false || 
				_topologyTreeController.puToThreadAnalyzerIsOn[key] == undefined) {
				_view.currentState = "OffToOn";	
			}
			else { //Thread analyzer is On and we want to turn it Off
				_view.currentState = "OnToOff";
			}	
				
			_view.addEventListener(FlexEvent.CREATION_COMPLETE, handleCreationComplete);
			createPopUp(_view, _currentPanel);
		}
		
		private function handleCreationComplete(event:FlexEvent):void {
			setData();
			setFieldValidators();
			dispatchChangeEvents(_view);
			
			_view.btn_OK.addEventListener(MouseEvent.CLICK, OKBtnClickHandler);
			_view.btn_Cancel.addEventListener(MouseEvent.CLICK, CancelBtnClickHandler);
		}
		
		private function setFieldValidators():void { 
			validatorsList = new Array();
			validatorsList.push(setValidator(_view.username, "true"));
			validatorsList.push(setValidator(_view.password, "true"));
			if (_view.currentState == "OffToOn")
				validatorsList.push(setValidator(_view.sampingInterval,"true","int",false,1));	//validator for integers
		}
		
		protected override function setData():void {
			super.setData();
//			var machineName:String = _topologyTreeController.getMachineName(_monitoredNode);
//			var clusterName:String = 
//				Util.getAttributeValue(_monitoredNode,TopologyConstants.CLUSTER_NODE,
//									TopologyConstants.TOP_FILE_CLUSTER_NODE_NAME_ATTR);
//			
//			var host:Host = _topologyTreeController.getCluster(clusterName).getHost(machineName); 
			
			if (_view.currentState == "OffToOn")
				_view.sampingInterval.text="300";		//set default to 300s (5mins)
				
			if (_host == null) {
//				Logger.logWarning(this,"Machine '{0}' not defined in site topology file", machineName);
				_view.ip.text = "Unknown";
				_view.hostName.text = "Unknown";		//TODO: Fix this...
				_view.username.text = "";
				_view.password.text = "";
			} else {
				_view.ip.text = _host.ip; 
				_view.hostName.text = _host.name;
				_view.username.text = PSVRClient.instance.username;       //UI username		//TODO: Confirm this is correct
				_view.password.text = PSVRClient.instance.password;	      //UI password
//				_view.username.text = host.username;
//				_view.password.text = host.password;
			} 
		}
		
		/** Deactivate the OK button */
		protected override function handleInvalidInput(event:Event):void {
			_view.btn_OK.enabled = false;
			_view.btn_OK.toolTip = BTN_OK_TOOLTIP;
		}
		
		/** Activate the OK button */
		protected override function handleValidInput(event:Event):void {
			if (isViewValid()) {//enable button OK
				dispatchChangeEvents(_view);
				_view.btn_OK.enabled = true;
				_view.btn_OK.toolTip = "";
			}
		} 
		
		private function OKBtnClickHandler(event:Event):void {
			var key:String = String(_monitoredNode.@id);
			
//			updateThreadAnalyzerOnOffStatus(key);
			
			//returned value is handled by the BESystemMonitorController update(...) method
			if (!_topologyTreeController.puToThreadAnalyzerIsOn[key]) {	//Start Thread Analyzer
				PSVRClient.instance.startStopThreadAnalyzer(_monitoredNode.@id, 
							true /*_topologyTreeController.puToThreadAnalyzerIsOn[key]*/, 
							""/*_view.threadReportDir.text*/, _view.sampingInterval.text, _view.username.text, 
							_view.password.text, _beSystemMonitorController);
			} else { 															//Stop Thread Analyzer
				PSVRClient.instance.startStopThreadAnalyzer(_monitoredNode.@id, 
									false /*_topologyTreeController.puToThreadAnalyzerIsOn[key]*/, 
					        		"", "", _view.username.text,_view.password.text, _beSystemMonitorController);
			}
			closePopUp();
		}
		
		private function CancelBtnClickHandler(event:Event):void {
			closePopUp();
		}
		
	} //class
} //package