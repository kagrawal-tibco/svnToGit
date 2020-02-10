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
	
	public class ExecuteCmdPanelController extends PopupPanelController
	{
		private var _currentPanel:GeneralPanel;
		private var _beSystemMonitorController:BESystemMonitorController;
		private var _view:ExecuteCmdPanel;
		
		public function ExecuteCmdPanelController(monitoredNode:XML, currentPanel:GeneralPanel, 
											topologyTreeController:TopologyMenuController, beSystemMonitorController:BESystemMonitorController) {
			_monitoredNode = monitoredNode;																
			_currentPanel = currentPanel;
			_topologyTreeController = topologyTreeController;
			_beSystemMonitorController = beSystemMonitorController;	
		}

//		public function get view():ExecuteCmdPanel {return _view;}
		
		public override function createView():void {
			_view = new ExecuteCmdPanel();
				
			_view.addEventListener(FlexEvent.CREATION_COMPLETE, handleCreationComplete);
			createPopUp(_view, _currentPanel);
		}
		
//		private function setValidator():void {
//			var validator:Validator = getTypeValidator("java.lang.string");
//			validator.required = true;
//		} 
		
		private function handleCreationComplete(event:FlexEvent):void {
			setFieldValidators();
			setData();
			dispatchChangeEvents(_view);
			
			_view.command.addEventListener(FlexEvent.INVALID, handleInvalidInput);
			_view.command.addEventListener(FlexEvent.VALID, handleValidInput);
			_view.btn_OK.addEventListener(MouseEvent.CLICK, OKBtnClickHandler);
			_view.btn_Cancel.addEventListener(MouseEvent.CLICK, CancelBtnClickHandler);
		}
		
		private function setFieldValidators():void { 
			validatorsList = new Array();
			validatorsList.push(setValidator(_view.username, "true"));
			validatorsList.push(setValidator(_view.password, "true"));
			validatorsList.push(setValidator(_view.command, "true"));
		}
		
		protected override function setData():void {
			super.setData();
//			var machineName:String = _topologyTreeController.getMachineName(_monitoredNode);
//			var clusterName:String = 
//				Util.getAttributeValue(_monitoredNode,TopologyConstants.CLUSTER_NODE,
//									TopologyConstants.TOP_FILE_CLUSTER_NODE_NAME_ATTR);
//			
//			var host:Host = _topologyTreeController.getCluster(clusterName).getHost(machineName); 
			
			if (_host == null) {
//				Logger.logWarning(this,"Machine '{0}' not defined in site topology file", machineName);
				_view.ip.text = "Unknown";
				_view.hostName.text = "Unknown";		//TODO: Fix this...
				_view.username.text = "";
				_view.password.text = "";
			} else {
				_view.ip.text = _host.ip; 
				_view.hostName.text = _host.name;
				_view.username.text = _host.username;
				_view.password.text = _host.password;
			} 
		}
		
		//TODO: VALIDATE FOR EMPTY COMMANDm - Do not
		
		private function OKBtnClickHandler(event:Event):void {
			PSVRClient.instance.executeCommand(_monitoredNode.@id, _view.username.text, _view.password.text, 
												_view.command.text, _beSystemMonitorController);
			//returned value is handled by the BESystemMonitorController update(...) method
			//removes the window after submitting OK		
			closePopUp();									 	
		}
		
		private function CancelBtnClickHandler(event:Event):void {
			closePopUp();	
		}
		
		protected override function handleInvalidInput(event:Event):void {
			_view.btn_OK.enabled = false;
			_view.btn_OK.toolTip = BTN_OK_TOOLTIP;
		}
		
		/** Activate the OK button */
		protected override function handleValidInput(event:Event):void {
			if (isViewValid()) {//enable button OK
				_view.btn_OK.enabled = true;
				_view.btn_OK.toolTip = "";
			}
		}
		
		
		
		
		
	} //class
} //package