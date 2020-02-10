package com.tibco.cep.ui.monitor {
	
	import com.tibco.cep.ui.monitor.components.Spinner;
	import com.tibco.cep.ui.monitor.containers.ResultsContainerController;
	import com.tibco.cep.ui.monitor.events.EventTypes;
	import com.tibco.cep.ui.monitor.events.PanelReadyEvent;
	import com.tibco.cep.ui.monitor.events.ServerCrashEvent;
	import com.tibco.cep.ui.monitor.events.TopologyPurgedEvent;
	import com.tibco.cep.ui.monitor.events.TopologyUpdateEvent;
	import com.tibco.cep.ui.monitor.events.TopologyUpdateFailEvent;
	import com.tibco.cep.ui.monitor.io.DelegateUpdateableImpl;
	import com.tibco.cep.ui.monitor.io.PSVRClient;
	import com.tibco.cep.ui.monitor.panels.*;
	import com.tibco.cep.ui.monitor.util.Logger;
	import com.tibco.cep.ui.monitor.util.TopologyConstants;
	import com.tibco.cep.ui.monitor.util.UIConsts;
	import com.tibco.cep.ui.monitor.util.Util;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	import mx.containers.Box;
	import mx.containers.VBox;
	import mx.controls.Button;
	import mx.controls.Label;
	import mx.events.ListEvent;
	
	public class BESystemMonitorController implements IUpdateable{
		
		//views
		private var _view:BESystemMonitor;
		private var _currentPanel:GeneralPanel;
		private var _loadingPanel:Box;
		private var _emptyPanel:Box;
		private var _btnPopupPanel:PopupPanelController;		//Contains the PopUp object created after clicking on a topology button

		private var _results:ResultsContainerController;		//contains the results tabs 

		private var _topologyTreeController:TopologyMenuController;
		private var _lockPanelUpdates:Boolean; //true -> lock enabled
		private var _prevSelectedNode:XML
		
		private var _isStopEnginePending:Boolean;	//Indicates that the StopEngine operation is pending, waiting for the thread analyzer to stop. 
													//It is used in the update(..) callback to stop the engine after stopping the thread analyzer

		public function BESystemMonitorController(view:BESystemMonitor){
			_view = view;
			_results = new ResultsContainerController(_view.resultsContainer);
		}
		
		//invoked by BESystemMonitor on creation complete
		public function init():void{
			_lockPanelUpdates = false;
			_view.topologyMenu.menuTree.addEventListener(ListEvent.ITEM_CLICK, getAndDisplayPanel);
			_topologyTreeController = new TopologyMenuController(_view.topologyMenu);
			_topologyTreeController.view.addEventListener(EventTypes.TOPOLOGY_UPDATE, handleTopologyUpdate);
			_topologyTreeController.view.addEventListener(EventTypes.TOPOLOGY_UPDATE_FAIL, handleTopologyUpdateFail);
			_topologyTreeController.view.addEventListener(EventTypes.TOPOLOGY_PURGED, handleTopologyPurged);
			//Topology buttons-click event listeners
			_topologyTreeController.view.btn_Deploy.addEventListener(MouseEvent.CLICK, handleDeployBtnClick);
			_topologyTreeController.view.btn_Start.addEventListener(MouseEvent.CLICK, handleStartBtnClick);
			_topologyTreeController.view.btn_Stop.addEventListener(MouseEvent.CLICK, handleStopBtnClick);
			_topologyTreeController.view.btn_Pause.addEventListener(MouseEvent.CLICK, handlePauseBtnClick);
			_topologyTreeController.view.btn_Resume.addEventListener(MouseEvent.CLICK, handleResumeBtnClick);
			_topologyTreeController.view.btn_Execute.addEventListener(MouseEvent.CLICK, handleExecuteBtnClick);
			_topologyTreeController.view.btn_ThreadDump.addEventListener(MouseEvent.CLICK, handleThreadDumpBtnClick);
			_topologyTreeController.view.btn_Purge.addEventListener(MouseEvent.CLICK, handlePurgeBtnClick);
			_topologyTreeController.init();
		}
		
		private function closeBttnPopupPanel():void {
			// if one popup window is already open, closes it before opening a new one. To avoid multiple PopUps...
			if( _btnPopupPanel != null && PopupPanelController.isPopUpOpen )
				PopupPanelController.closePopUp();
		}
		
		public function handleExecuteBtnClick(event:Event):void { 
			var nodeToMonitor:XML = _topologyTreeController.selectedItem as XML;
			trace("Execute BUTTON CLICKED for node of type: " + nodeToMonitor.name());
			closeBttnPopupPanel();

			//Execute button clicked to Execute a command in the machine (host)
			if (nodeToMonitor.name()==TopologyConstants.MACHINE_NODE) {	
				_btnPopupPanel = new ExecuteCmdPanelController(nodeToMonitor, _currentPanel, 
													_topologyTreeController, this);
			}
			else {//Execute button clicked to invoke a process or an agent method
				//add monitorableID attribute to the XML object of the selected (method) node because
				//monitorableID is necessary in the method invocation.
				nodeToMonitor.@monitorableID = nodeToMonitor.parent().parent().parent().@id;
				
				//creates pop-up window with method details information received from the server. 
				//_currentPannel is passed as argument because the currentPage needs to be specified as
				//the parent of the pop-up window
				//"this" needs to be passed in because the value returned by the method invocation is handled by 
				//the BESystemMonitorController update(...) method 
				_btnPopupPanel = new MethodInvocationPanelController(nodeToMonitor, _currentPanel, this);
			}
			 
			_btnPopupPanel.createView()
		} //handleExecuteBtnClick
		
		public function handleThreadDumpBtnClick(event:Event):void { 
			var nodeToMonitor:XML = _topologyTreeController.selectedItem as XML;
			trace("Thread Dump BUTTON CLICKED");
			
			closeBttnPopupPanel();
			//Create popup window and if OK button pressed send start/stop thread analyzer request to the server
			_btnPopupPanel = new ThreadAnalyzerPanelController(nodeToMonitor, _currentPanel, 
														_topologyTreeController, this);
			
			_btnPopupPanel.createView();
		} //handleThreadDumpBtnClick
		
		
		
		public function handleDeployBtnClick(event:Event):void { 
			var nodeToMonitor:XML = _topologyTreeController.selectedItem as XML;
			trace("Deploy BUTTON CLICKED");
			
			closeBttnPopupPanel();
			_btnPopupPanel = new DeployPUPanelController(nodeToMonitor, _currentPanel, 
														_topologyTreeController, this);
			_btnPopupPanel.createView();
		}
		
		public function handleInvokeBtnClick(event:Event):void { 
			var nodeToMonitor:XML = _topologyTreeController.selectedItem as XML;
			Logger.logInfo(this, "Execute BUTTON CLICKED");
			closeBttnPopupPanel();
				
			//add monitorableID attribute to the XML of the selected (method) node because
			//monitorableID is necessary in the method invocation.
			nodeToMonitor.@monitorableID = nodeToMonitor.parent().parent().parent().@id;
			
			//creates pop-up window with method description information received from the server. 
			//_currentPannel is passed as argument because the currentPage needs to be specified as
			//the parent of the pop-up window
			//"this" needs to be passed in because the value returned by the method invocation is handled by 
			//the BESystemMonitorController update(...) method 
			_btnPopupPanel = new MethodInvocationPanelController(nodeToMonitor, _currentPanel, this);
			_btnPopupPanel.createView();
			
			//_methodPanel is passed because the method description information received from the server
			//is handled by the MethodInvocationPanelController update(...) method
//			PSVRClient.instance.getMethodPanel(nodeToMonitor.@id, _methodPanel);		//TODO: Check or delete this
		} //handleInvokeBtnClick

		public function handleStartBtnClick(event:Event):void { 
			var nodeToMonitor:XML = _topologyTreeController.selectedItem as XML;
			trace("Start BUTTON CLICKED");
			closeBttnPopupPanel();
			
			if(nodeToMonitor.name() == "process") {			//TODO: Check this is indeed necessary
				_btnPopupPanel = new StartPUPanelController(nodeToMonitor, _currentPanel, _topologyTreeController, this);
				_btnPopupPanel.createView();
			}

			//TODO: to use in the future, in case Start/Stop/Pause/Resume become available at the agent level			
			//			if (monitoredNode.name() == "method")
			//				invoke_agent_start_method
			//			else //invoke process start method	

		} //handleStartBtnClick

		public function handleStopBtnClick(event:Event):void {
			trace("Stop BUTTON CLICKED");
			closeBttnPopupPanel();
			var monitoredNode:XML = _topologyTreeController.selectedItem as XML;
			//If the XML method description changes, we need to change the methodID below to match it.
			var methodID:String = String(monitoredNode.@id)+"#"+"process"+"#"+"Engine"+"#"+"StopEngine";
			if(_topologyTreeController.puToThreadAnalyzerIsOn[String(monitoredNode.@id)]) {
				_isStopEnginePending=true;
				//TA is On, so Stop it first, and then Shutdown the agent (done in the callback).
				PSVRClient.instance.startStopThreadAnalyzer(monitoredNode.@id,false,"", "",
								PSVRClient.instance.username,PSVRClient.instance.password,this);
			}
			else {
				PSVRClient.instance.invokeMethod("null", monitoredNode.@id, methodID,this,"stopengine");
			}

			//TODO: to use in the future, in case stop become available at the agent level
//			if (monitoredNode.name() == "agent")
//				invoke_process_pause_method
		} //handleStopBtnClick

		//pause and suspend are used interchangeably
		public function handlePauseBtnClick(event:Event):void { 
			trace("Pause BUTTON CLICKED");
			closeBttnPopupPanel();
			var monitoredNode:XML = _topologyTreeController.selectedItem as XML;
			//If the XML method description changes, we need to change the methodID to match.
			var methodID:String = String(monitoredNode.@id)+"#"+String(monitoredNode.@type)+"#"+"Agent"+"#"+"Suspend";
			
			PSVRClient.instance.invokePauseBtn("null", monitoredNode.@id, methodID,this);
//			PSVRClient.instance.invokeMethod("null", monitoredNode.@id, methodID,this);   //TODO DELETE 
			
			//TODO: to use in the future, in case pause/suspend become available at the process level
//			if (monitoredNode.name() == "process")
//				invoke_process_pause_method
		} //handlePauseBtnClick
		
		public function handleResumeBtnClick(event:Event):void { 
			trace("Resume BUTTON CLICKED");
			closeBttnPopupPanel();
			var monitoredNode:XML = _topologyTreeController.selectedItem as XML;
			//If the XML method description changes, we need to change the methodID to match.
			var methodID:String = String(monitoredNode.@id)+"#"+String(monitoredNode.@type)+"#"+"Agent"+"#"+"Resume";

			PSVRClient.instance.invokeResumeBtn("null", monitoredNode.@id, methodID,this );
//			PSVRClient.instance.invokeMethod("null", monitoredNode.@id, methodID,this ); TODO DELETE 
			
			//TODO: to use in the future, in case resume become available at the process level
//			if (monitoredNode.name() == "process")
//				invoke_process_resume_method
		} //handleResumeBtnClick
		
		/** Enables/disables topology buttons according to the arguments provided */
		private function setButtons(deploy:Boolean=false, start:Boolean=false, stop:Boolean=false, pause:Boolean=false, 
						resume:Boolean=false, execute:Boolean=false, threadDump:Boolean=false):void {
			_view.topologyMenu.btn_Deploy.enabled = deploy;											
			_view.topologyMenu.btn_Start.enabled = start;
			_view.topologyMenu.btn_Stop.enabled = stop;
			_view.topologyMenu.btn_Pause.enabled = pause;
			_view.topologyMenu.btn_Resume.enabled = resume;
			_view.topologyMenu.btn_Execute.enabled = execute;
			_view.topologyMenu.btn_ThreadDump.enabled = threadDump;
			setTooltips();
		} //setButtons
		
		//shows tooltip if the button is disabled. Otherwise doesn't show
		private function setTooltips():void {
			var buttons:Array = _view.topologyMenu.navbar.getChildren();
			for each (var button:Button in buttons) {
				if (button.enabled)
					button.toolTip = "";
				else {
					switch (button.id) {
						case "btn_Deploy":	button.toolTip="Select a Machine node to enable button"; break;
						case "btn_Start":	button.toolTip="Select a Process Unit node to enable button"; break;
						case "btn_Stop":	button.toolTip="Select a Process Unit node to enable button"; break;
						case "btn_Pause":	button.toolTip="Select an Agent node to enable button"; break;
						case "btn_Resume":	button.toolTip="Select an Agent node to enable button"; break;
						case "btn_Execute":		button.toolTip="Select a Machine node or an operation to enable button"; break;
						case "btn_ThreadDump":	button.toolTip="Select a Process Unit node to enable button"; break;
						default: button.toolTip =""; break;
					}
				}
			}
		} //setTooltips

		public function getAndDisplayPanel(event:Event, isTopologyUpdate:Boolean=false):void {
			//we need to validate if it is a topologyupdate in order to eliminate the "Busy Panel" popups to appear frequently.
			if(_lockPanelUpdates && !isTopologyUpdate){
				Util.infoMessage("Busy updating current panel... Please try again.");
				return;
			}
			
			var selectedNode:XML = _topologyTreeController.selectedItem as XML;
			
			if(selectedNode == null)
				return;                   
			else if ( selectedNode.name() == TopologyConstants.PROCESS_NODE ) { //*************** PROCESS NODE ***********************
				if (selectedNode.@predefined == "true"){	//predefined
					if (selectedNode.@active == "true")
						setButtons(false, false, true, false, false, false, true); //enable stop, thread dump buttons
					else 
						setButtons(false, true);  //enable start button
				} 
				else {	//not predefined
					if (selectedNode.@active == "true")
						setButtons(false, false, true, false, false, false, true); //enable stop, thread dump buttons
					else
						setButtons();	//disable every button
				}
			}
			else if ( selectedNode.name() == TopologyConstants.AGENT_NODE ){ //*************** AGENT NODE ***********************
				//Here it does not matter if it is predefined or not
				//Pause and Resume buttons do not make sense for cacheserver agents, so if node is CS, disable all buttons
				//for now (BE4.0.0 release) the Pause and Resume buttons are going to be disabled for Dashboard agents as well.
				if (selectedNode.@active == "true" && String(selectedNode.@type)!=TopologyConstants.AGENT_TYPE_CACHESERVER &&
						String(selectedNode.@type)!=TopologyConstants.AGENT_TYPE_DASHBOARD ) {	//entity active
					if(_topologyTreeController.agentIDToIsPaused == null || 
					   		_topologyTreeController.agentIDToIsPaused[String(selectedNode.@id)]==undefined ||	
					   		_topologyTreeController.agentIDToIsPaused[String(selectedNode.@id)]==false ) {
						setButtons(false,false,false,true,false);	//it is NOT paused, so enable pause btn
			   		}
					else
						setButtons(false,false,false,false,true); 	//it is paused, so enable resume btn
				} else //entity inactive
					setButtons();								//disable every button. Cacheserver and Dashboard agents (for BE 4.0.0) should always reach this else
			}
			else if (selectedNode.name() == TopologyConstants.MACHINE_NODE) { //*************** MACHINE NODE ***********************
				if (selectedNode.@predefined == "true"){	//predefined
					if (selectedNode.@active == "true") 
						setButtons(true,false,false,false,false,true); 	//activate "Deploy", "Execute" buttons
					else 
					    setButtons(true,false,false,false,false,true); //enable Deploy and Execute button 
				} else {	//not predefined
					if (selectedNode.@active == "true")			
						setButtons(false,false,false,false,false,true); 	//activate "Execute" buttons
					else
						setButtons();			//disable every button
				}
			}
			else if ( selectedNode.name() == TopologyConstants.SITE_NODE ) { //*************** SITE NODE ***********************
				setButtons(); //disables every button
			}
			else if ( selectedNode.name() == TopologyConstants.CLUSTER_NODE ) { //*************** CLUSTER NODE ***********************
				setButtons(); //disables every button
			}
			else if (selectedNode.name() == TopologyConstants.METHODS_NODE ) { //*************** METHODS NODE ***********************
				setButtons();
				if ( _prevSelectedNode != null && _prevSelectedNode.@id == selectedNode.parent().@id )
					return;
				else
					selectedNode = selectedNode.parent();
			}
				//if the user selects a method or a methodGroup directly, this code "automatically" selects the Agent node
				//that is the parent of that method/methodGroup. Disable every button if method group selected 
			else if( selectedNode.name() == TopologyConstants.METHOD_GROUP_NODE ) { //*************** METHODS GROUP NODE ***********************
				setButtons();								
				if ( _prevSelectedNode != null && _prevSelectedNode.@id == selectedNode.parent().parent().@id )
					 return;
				else {
					selectedNode = selectedNode.parent().parent();
				}
			}
			else if (selectedNode.name() == TopologyConstants.METHOD_NODE ) { //*************** METHOD NAME NODE ***********************
				//here it does not matter if it is predefined or not
				if ( selectedNode.parent().parent().parent().@active == false ) { //entity inactive
					setButtons();				//inactive => disable all buttons
					_view.topologyMenu.btn_Execute.toolTip = "Start Process Unit";
				}
				else 	//entity active
					setButtons(false, false, false, false, false, true);	//activate just execute button				
				
				if ( _prevSelectedNode != null && _prevSelectedNode.@id == selectedNode.parent().parent().parent().@id ) 
					 return;
				else
					selectedNode = selectedNode.parent().parent().parent();
			}
			else if (selectedNode.name()== TopologyConstants.CACHED_OBJECTS_NODE) { //*************** CACHED OBJECTS NODE ***********************
				setButtons()	//it's just a table for information purposes, so disable all buttons
			}
			else {
				setButtons(); 		//something went wrong, so disable all buttons to be safe
				Logger.logWarning(this, "Unknown topology node selected"); 
			}
				
			//no point in redoing everything if the panel's already being displayed
			//this code is put in here and not earlier because we need to update which buttons must
			//be enabled first
			if (_currentPanel != null && selectedNode.@id == _currentPanel.monitoredNode.@id) 
				return;
			
			//lock other updates so things don't go too crazy
			_lockPanelUpdates = true;
			_view.topologyMenu.menuTree.enabled = false;
			showPanelChanging();
			
			//Clear the current panel if the selected node is not a method or methodGroup. 	//TODO: I think that I no longer need this XXXXXXXX
			//Note that it is insufficient to just set _currentPanel
			//to a new instance because all currently displayed panes need to be killed
			if(_currentPanel != null && (selectedNode.name()!= TopologyConstants.METHOD_GROUP_NODE || selectedNode.name()!= TopologyConstants.METHOD_NODE) ){
				PSVRClient.instance.unsubscribeAllPanes(this); //also unsubscribes updater
				_currentPanel.clearPanel();	
			}
			
				//Create the new panel object
				_currentPanel =
					new GeneralPanel( selectedNode, _view.dashboardContainer.panelNavigator );
				_currentPanel.addEventListener(EventTypes.SERVER_CRASH, handleServerCrash);

//				//used to avoid refreshing the dashboard container panel when browsing the multiple methods available
//				//for that agent. The dashboard panel is opened just the first time a method is selected
//				_prevSelectedNode = new XML(selectedNode);

				PSVRClient.instance.getPanelComponents(selectedNode.@id, new DelegateUpdateableImpl(this, _currentPanel));
			
			//used to avoid refreshing the dashboard container panel when browsing the multiple methods available
			//for that agent. The dashboard panel is opened just the first time a method is selected
			_prevSelectedNode = new XML(selectedNode);

        } //getAndDisplayPanel
        
        public function handleTopologyUpdate(event:TopologyUpdateEvent):void{
        	if(_currentPanel == null) 
        		return; //can be null if no tree node is selected
			var nodeId:String = _currentPanel.monitoredNode.@id;
			//we need the 'true' value passed in, to avoid the "Busy Panel" popups to show up very frequently
			getAndDisplayPanel(null,true);	
			_currentPanel.update(EventTypes.TOPOLOGY_UPDATE, Util.findNodeIn(nodeId, event.topology));
		}
		
		public function handleTopologyUpdateFail(event:TopologyUpdateFailEvent):void{
        	handleServerCrash(
        		new ServerCrashEvent("Operation " + event.operation + " failed.\n" + event.message, event.code)
    		);
		}
		
		public function handleTopologyPurged(event:TopologyPurgedEvent):void{
			//Topology controller will have done all necessary topology updates and dispatches this
			//event.  All that remains at this point is to update the panel...
			getAndDisplayPanel(null);
		}
		
		//Event listener added by controller
		public function handleServerCrash(event:ServerCrashEvent):void{
			_topologyTreeController = null;
			_currentPanel = null;
			closeBttnPopupPanel();
				
			_view.dispatchEvent(new ServerCrashEvent(event.message, event.code));
		}
		
		private function handlePanelReady(event:PanelReadyEvent):void {
			_lockPanelUpdates = false;
			_view.topologyMenu.menuTree.enabled = true;
			_currentPanel.render();
		}
		
		private function handlePurgeBtnClick(event:Event):void{
			_view.topologyMenu.menuTree.enabled = false;
			PSVRClient.instance.unsubscribeAllPanes(this); //also unsubscribes updater
			
			if(_currentPanel != null) 
				_currentPanel.clearPanel();
			_currentPanel = null;
			
			showPanelChanging();
			_topologyTreeController.incrementPurgeVersion();
			PSVRClient.instance.purgeTopology(this);
		}
		
		private function showPanelChanging():void{
			//This temporary panel is cleared by calls to getAndDisplayPanel
			if(_loadingPanel == null) _loadingPanel = buildLoadingPanel();
			_view.dashboardContainer.panelNavigator.removeAllChildren();
			_view.dashboardContainer.panelNavigator.addChild(_loadingPanel);
		}

		/** 
		 * 	Panel that's displayed while fetching the selected panel from the server.
		 *  Only needs to be built once... thus static.
		 */
		private static function buildLoadingPanel():Box{
			var box:Box = new Box();
			box.percentHeight = 100;
			box.percentWidth = 100;
			box.setStyle("borderStyle", "solid");
			box.setStyle("verticalAlign", "middle");
			box.setStyle("horizontalAlign", "center");
			
			var display:VBox = new VBox();
			display.setStyle("horizontalAlign", "center");
			var title:Label = new Label();
			title.setStyle("fontWeight", "bold");
			title.text = "Loading...";
			var spinner:Spinner = new Spinner();
			spinner.size = 55;
			spinner.tickWidth = 7;
			spinner.numTicks = 10;
			spinner.speed = 1000;
			spinner.setStyle('tickColor', 0x315468);
			
			display.addChild(title);
			display.addChild(spinner);
			box.addChild(display);
			return box;
		}

		private function createPanelPages():void {
			if(_currentPanel.monitoredNode.@active == false) {
				_currentPanel.showPanesInactiveOverlay();
				handlePanelReady(null);
				return;
			}
			var panelLoader:PanelLoader = new PanelLoader(_currentPanel);
			panelLoader.addEventListener(EventTypes.PANEL_READY, handlePanelReady);
			panelLoader.load();
			return;
		}

        public function update(operation:String,data:XML):void{
        	var nodeToMonitor:XML = _topologyTreeController.selectedItem as XML;
        	
			if (operation == "getpanelcomponents") {
				createPanelPages();
				return;
			}
			else if (operation == "unsubscribeallpanes") {
				trace("Unsubscribing all panes...");
				return;
			}
			else if(operation == "purgetopology"){
				_topologyTreeController.update("purgetopology", data);
				getAndDisplayPanel(null);
				return;
			}
			
			/****************** handle operations that return values ******************/
			var isInvokeSuccess:Boolean=false;

			_results.updateData(data);
			
			if (operation == "invokeagentmethod") {
				isInvokeSuccess = _results.load(_topologyTreeController.getFullPath(nodeToMonitor) );
			}
			else if (operation == "startpu"){
				isInvokeSuccess = _results.load(_topologyTreeController.getFullPath(nodeToMonitor),"Start PU");
			} 
			else if (operation == UIConsts.SET_GVS){
				isInvokeSuccess = _results.load(_topologyTreeController.getFullPath(nodeToMonitor),"Save GVs");
			}
			else if (operation == UIConsts.DEPLOY_DU){
				isInvokeSuccess = _results.load(_topologyTreeController.getFullPath(nodeToMonitor),"Deploy DUs");
			}
			else if (operation == "executecommand"){
				isInvokeSuccess = _results.load(_topologyTreeController.getFullPath(nodeToMonitor),"Execute Command");
			}
			else if (operation == "stopengine"){
				isInvokeSuccess = _results.load(_topologyTreeController.getFullPath(nodeToMonitor),"Stop PU" );
				_isStopEnginePending = false;	//Engine stopped
			} 
			else if (operation == "startstopta") {
				//true=>On=>next action is to stop TA
				if(_topologyTreeController.puToThreadAnalyzerIsOn[(nodeToMonitor.@id).toString()])
					isInvokeSuccess = _results.load(_topologyTreeController.getFullPath(nodeToMonitor),"Stop TA");
				else
					isInvokeSuccess = _results.load(_topologyTreeController.getFullPath(nodeToMonitor),"Start TA");
				// TODO: BEWARE OF THE POTENTIAL PROBLEMS THIS CAN BRING. For example, if the user tries to stop
				// the TA, and it fails, the state in the Shared Object remains as On, hence Off panel shows up.
				// If user shuts down the agent, next time it's brought up, the TA Off panel will be prompted. 
				if (isInvokeSuccess)	//otherwise, we do not change the status
					_topologyTreeController.updateThreadAnalyzerOnOffStatus(String(nodeToMonitor.@id));
					
				if (_isStopEnginePending) {	//stop engine after the TA was stopped.
					var methodID:String = String(nodeToMonitor.@id)+"#"+"process"+"#"+"Engine"+"#"+"StopEngine";
					PSVRClient.instance.invokeMethod("null", nodeToMonitor.@id, methodID,this,"stopengine"); 
				}
			}
			else if (operation == "invokeresumebtn") {
				isInvokeSuccess = _results.load(_topologyTreeController.getFullPath(nodeToMonitor),"Resume" );
				if (isInvokeSuccess)	//else do nothing because state did not change
					_topologyTreeController.agentIDToIsPaused[String(nodeToMonitor.@id)]=false;
			}
			else if (operation == "invokepausebtn") {
				isInvokeSuccess = _results.load(_topologyTreeController.getFullPath(nodeToMonitor),"Pause");
				if (isInvokeSuccess)	//else do nothing because state did not change
					_topologyTreeController.agentIDToIsPaused[String(nodeToMonitor.@id)]=true;
			}
			else {
				Logger.logWarning(this,"Unexpected operation occurred: " + operation);
			}

			Logger.logDebug(this,"Operation: " + operation + " invoked with success = " + String(isInvokeSuccess).toUpperCase() );

			//update topology each time a method is invoked because method invocation might 
			//imply changes in the topology (i.e.) Shutdown or Start a PU
			PSVRClient.instance.updateSiteTopology(_topologyTreeController);
			getAndDisplayPanel(null); 
		} //update
		
		public function updateFailure(operation:String,message:String,code:uint):void{
			handleServerCrash(new ServerCrashEvent("Operation " + operation + " failed.\n" + message, 0));
		}
		
	}
}