package com.tibco.cep.ui.monitor.panels
{
	import com.tibco.cep.ui.monitor.BESystemMonitorController;
	import com.tibco.cep.ui.monitor.IUpdateable;
	import com.tibco.cep.ui.monitor.TopologyMenuController;
	import com.tibco.cep.ui.monitor.io.DelegateUpdateableImpl;
	import com.tibco.cep.ui.monitor.io.PSVRClient;
	import com.tibco.cep.ui.monitor.panes.table.GVsTableMultDUs;
	import com.tibco.cep.ui.monitor.sitetopology.DeploymentUnit;
	import com.tibco.cep.ui.monitor.util.Logger;
	import com.tibco.cep.ui.monitor.util.UIConsts;
	import com.tibco.cep.ui.monitor.util.Util;
	import com.tibco.cep.ui.monitor.util.XMLNodesConsts;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.utils.Dictionary;
	
	import mx.controls.CheckBox;
	import mx.events.FlexEvent;
	import mx.utils.ArrayUtil;
	import mx.utils.StringUtil;
	
	public class DeployPUPanelController extends PopupPanelController implements IUpdateable  
	{
		private var _currentPanel:GeneralPanel;
		private var _beSystemMonitorController:BESystemMonitorController;
		private var _view:DeployPUPanel;
		private var _mappedDuStIdToDuObj:Dictionary;
		private var _stackedDuStIdToStackIndex:Dictionary;
		private var _duFqnToGVsTable:Dictionary;
		private var _gvDuStIdTokenStr:String=""; 
		private var _deployDuStIdTokenStr:String="";
		
		private var _isDuSelected:Boolean;		//Indicates if the user has selected at least one DU for deployment or not
		private var _numCheckboxesSelected:int;  //Indicates the number or checkboxes selected by the user. 
                                                //If no checkboxes selected, the Next> button is disabled
                                                
		private var _slctAllCheckBox:CheckBox                                                 
		
		public function DeployPUPanelController(monitoredNode:XML, currentPanel:GeneralPanel, 
							topologyTreeController:TopologyMenuController, beSystemMonitorController:BESystemMonitorController) {
																			
			_monitoredNode = monitoredNode;																
			_currentPanel = currentPanel;
			_topologyTreeController = topologyTreeController;
			_beSystemMonitorController = beSystemMonitorController;
			_mappedDuStIdToDuObj = new Dictionary(true);
			_duFqnToGVsTable = new Dictionary(true);
			_stackedDuStIdToStackIndex = new Dictionary(true);
		}
		
		//uncomment if createPreview() method in the view class is uncommented
//		public function get duFqnToGVsTable():Dictionary {return _duFqnToGVsTable;}
		
		public override function createView():void {
			_view = new DeployPUPanel();
				
			_view.addEventListener(FlexEvent.CREATION_COMPLETE, handleCreationComplete);
			createPopUp(_view, _currentPanel);
		}
		
		private function handleCreationComplete(event:FlexEvent):void {
			setData();
			setFieldValidators();
			dispatchChangeEvents(_view);
			
			_view.btn_Prev.addEventListener(MouseEvent.CLICK, BtnPrevClickHandler);
			_view.btn_Next.addEventListener(MouseEvent.CLICK, BtnNextClickHandler);
			_view.btn_Finish.addEventListener(MouseEvent.CLICK, BtnFinishClickHandler);
			_view.btn_Cancel.addEventListener(MouseEvent.CLICK, BtnCancelClickHandler);
		}
		
		private function setFieldValidators():void {
			validatorsList = new Array();
			validatorsList.push( setValidator(_view.username, "true") );
			validatorsList.push( setValidator(_view.password, "true") );
		}
		
		protected override function setData():void {
			super.setData();
			
			if (_host == null) {
				_view.ip.text = "Unknown";
				_view.hostName.text = "Unknown";		
				_view.username.text = "";
				_view.password.text = "";
			} else {
				_view.ip.text = _host.ip; 
				_view.hostName.text = _host.name;
				_view.username.text = _host.username;
				_view.password.text = _host.password;
			} 
			addEarAndCddLocToView();
		}
		
		private function addEarAndCddLocToView():void {	
			_slctAllCheckBox = new CheckBox();
			_slctAllCheckBox.label = "Select All Deployment Units";
			_slctAllCheckBox.styleName="labelStyle" 
            _slctAllCheckBox.percentWidth=100;
            _slctAllCheckBox.addEventListener(FlexEvent.VALUE_COMMIT,selectAllEventHandler);
			
			_view.deployPUForm.addChild(_slctAllCheckBox);

            //=================
	
			for each (var du:DeploymentUnit in _host.getMappedDus()) {
				var duFqdn:String = _siteName + "/" + _clusterName + "/" + _host.name + "/" + du.name;
				var duc:DeployUnitContainer = 
						new DeployUnitContainer(duFqdn,du.id,du.deployCddPath,du.deployEarPath, checkBoxEventHandler);
												
				_view.deployPUForm.addChild(duc.container);
				_mappedDuStIdToDuObj[du.id]=duc;
		 	}  
		}
		
		/** Deactivate the Next button */
        protected override function handleInvalidInput(event:Event):void {
            _view.btn_Next.enabled = false;
            _view.btn_Next.toolTip = BTN_OK_TOOLTIP;
        }
        
        /** Activate the Next button */
        protected override function handleValidInput(event:Event):void {
            if (isViewValid()) {//enable button OK
                _view.btn_Next.enabled = !(_numCheckboxesSelected == 0);
                _view.btn_Next.toolTip = "";
            }
        }
		
		private function selectAllEventHandler(event:Event):void {
			for each (var duc : DeployUnitContainer in _mappedDuStIdToDuObj) {
				duc.checkBox.selected = _slctAllCheckBox.selected;
			}
		}
		
		private function checkBoxEventHandler(event:Event):void {
			
			if (event.type != FlexEvent.VALUE_COMMIT) {
				Logger.logWarning("Unexpected event of type '{0}' " + 
						"received in Checkbox handler. No action taken!", event.type);
				return;
			}
			
            _numCheckboxesSelected = (event.target as CheckBox).selected ?_numCheckboxesSelected+1  
                                                                       : _numCheckboxesSelected-1;
                                                                         
	        if (_numCheckboxesSelected < 0 ) _numCheckboxesSelected = 0;
		  	
			
			if (_numCheckboxesSelected == 0) {
			     _view.btn_Next.enabled = false;
			     
			     //to handle the case when select all is selected and the user 
			     //unselects all the individual checkboxes one at a time 
			     if (_slctAllCheckBox.selected)
			         _slctAllCheckBox.selected = false;
			} else {
			     _view.btn_Next.enabled = isViewValid();
			}
		}
		
		private function BtnPrevClickHandler(event:Event):void {
			_view.prev();
		}
		
		private function BtnFinishClickHandler(event:Event):void {
			//Sets the new values for the Global Variables first, and if everything 
			//goes well proceeds with the deployment 			
			var gvsXml:String= buildGVsXML();

			if (gvsXml != "") {	
			     //If changes in GVs, send request to server to set GVs, and then do deployment.
			     //Deployment is handled in the update() method of the BESystemMonitorController class
				PSVRClient.instance.setGVs(_monitoredNode.@id, gvsXml, 
						new DelegateUpdateableImpl(this, _beSystemMonitorController));
			} else { //If no changes in GVs, don't send request to set GVs, just execute deployment action
			     update(UIConsts.SET_GVS,null);
			}
			closePopUp();
		}
		
		private function BtnCancelClickHandler(event:Event):void {
			closePopUp();
		}
		
		private function BtnNextClickHandler(event:Event):void {
			if (_view.stackIndex == 0) {	//first time button next is clicked - must retrieve GVs info
				
				//Builds the tokenized Strings for the DU's for which 
				//GVs info is needed from the server, and for the DU's 
				//that are going to be deployed. 
				buildTokenizedStrs(); 
				
				if(!_isDuSelected) {
					Util.infoMessage("Select at least one deployment unit and click next");    //TODO: Redo this to hide the next button instead of showing popup
					return;
				}
				
				if(_gvDuStIdTokenStr != "") {
					//gets the GVs for the selected DUs if this info was not yet previously retrieved
					//The GVs info for each DU will be retrieved from the server just one time for the duration
					//of the session
					PSVRClient.instance.getGVs(_monitoredNode.@id,_gvDuStIdTokenStr,this)
					//_view.next() - Is handled in the update() method because otherwise the ViewStack
					//is not yet set when the next() method is called.
				} else {
				 	_view.next();
				}
			} else {
				_view.next();
			}
		}
		
		/** TODO: */
		private function buildTokenizedStrs():void {
			_isDuSelected=false;
			_gvDuStIdTokenStr="";
			_deployDuStIdTokenStr="";
			
			//iterates over all the DU's available for deployment
			for (var duStId:String in _mappedDuStIdToDuObj) {
				//if du selected for deployment
				if((_mappedDuStIdToDuObj[duStId] as DeployUnitContainer).checkBox.selected) {
					_isDuSelected=true;
					
					_deployDuStIdTokenStr+=duStId+"#";
										
					//if gvs information for this DU has not yet been read from the server any time, 
					//add it to the tokenized string to do so. It will then store the gvs info locally
					if((_stackedDuStIdToStackIndex[duStId] == undefined)) {  
						_gvDuStIdTokenStr+=duStId+"#";
					}
				 } // if du not selected for deployment 
				 else if (!(_mappedDuStIdToDuObj[duStId] as DeployUnitContainer).checkBox.selected) {
					
					if (!(_stackedDuStIdToStackIndex[duStId] == undefined)) {
						var indexToRemove:int= _stackedDuStIdToStackIndex[duStId];
					
						//remove from stack
						_view.duViewStack.removeChildAt(indexToRemove);
						//remove from dictionary
						delete _stackedDuStIdToStackIndex[duStId]
						updateIndexesInMap(indexToRemove);
					}
				}
			}
			//remove the last #
			_gvDuStIdTokenStr = _gvDuStIdTokenStr.substr(0,_gvDuStIdTokenStr.length - 1);
			_deployDuStIdTokenStr = _deployDuStIdTokenStr.substr(0,_deployDuStIdTokenStr.length - 1);
		}
		
		private function updateIndexesInMap(index:int):void {
			for (var key:String in _stackedDuStIdToStackIndex) {
				if (_stackedDuStIdToStackIndex[key] > index)
					_stackedDuStIdToStackIndex[key]--;
			} 
		}
		
		public function update(operation:String,gvsXML:XML):void {
			if (operation == UIConsts.GET_GVS) {
				Logger.logDebug(this, gvsXML);
				
				var hostFqName:String = String(gvsXML.@[XMLNodesConsts.MACHINE_NODE_NAME_ATTR]);	// /site/cluster/machine/du_name	
				
				//iterates over every GV and builds stack view table
				for each (var gvXML:XML in gvsXML[XMLNodesConsts.GVS_NODE]) {
					var duName:String = String(gvXML.@[XMLNodesConsts.GVS_NODE_DU_NAME_ATTR]);
					var duFqn:String = hostFqName + "/" + duName;
					var gvTable:GVsTableMultDUs;
					if(gvXML.children().length() > 0){
						if (_duFqnToGVsTable[duFqn] == undefined) {
							//Used to send the GVs XML to the server
							_duFqnToGVsTable[duFqn] = new GVsTableMultDUs(duFqn,gvXML);	
						} 
						
						var duStId:String = _cluster.getHost(hostFqName).getDeployUnit(duName).id;
						gvTable = _duFqnToGVsTable[duFqn];
						
						_view.duViewStack.addChild(gvTable.view);	//add table to view stack
						_stackedDuStIdToStackIndex[duStId] = _view.duViewStack.numChildren - 1	//put index of table in view stack in the map
					}
				}
				//after the stack view has been build, select the view's first child
				//The code gets to this update() method when the next > button is clicked for the first time 
				_view.next(); 
			} else if (operation == UIConsts.SET_GVS) {		//TODO: What if something goes wrong with saving the GVs? Handle that case
				//If any GVs values were changed by the user, at this point they have been saved, 
				//so proceed with the deployment.
				//Deployment is handled in the update() method of the BESystemMonitorController class
				PSVRClient.instance.deployDU(_monitoredNode.@id, _deployDuStIdTokenStr, 
					_view.username.text, _view.password.text, _beSystemMonitorController);	

				//TODO: What if something goes wrong just with the deployment of part of the DUs???
			}
		}
		
		public function updateFailure(operation:String,message:String,code:uint):void {
			Logger.logError(this, "Update Failed");
		}  
		
		private function buildGVsXML():String {
			var hasGvValChanged:Boolean = false;
			var xml:XML = XML(StringUtil.substitute(XMLNodesConsts.MACHINE_NODE_PATTERN,_machineName));
			
			var duStIdsToDeploy:Array = _deployDuStIdTokenStr.split("#");
			
			//iterates over all the duFqn for which GV's info was retrieved
			for (var duFqn:String in _duFqnToGVsTable) {
				//if this duFqn is selected for deployment and the user made any changes 
				//to the initial GV values, get the GV XML values changed and build the 
				//XML to send to server to be saved
				if (ArrayUtil.getItemIndex(
						_cluster.getHost(_host.name).getDeployUnit(duFqn).id, 
						duStIdsToDeploy) != -1) {
							
					var gvTable:GVsTableMultDUs = _duFqnToGVsTable[duFqn]
					var gvsXml:String = gvTable.getGVsXML();
					
					if(gvsXml != null && !StringUtil.isWhitespace(gvsXml)) {  
						hasGvValChanged=true;
						xml.appendChild(gvsXml);
					}
				}
			}
			
			return hasGvValChanged ? xml.toString() : "";
		}
		
	} //class
} //package