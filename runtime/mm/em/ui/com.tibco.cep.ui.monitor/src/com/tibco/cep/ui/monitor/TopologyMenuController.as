package com.tibco.cep.ui.monitor{
	import com.tibco.cep.ui.monitor.events.TopologyPurgedEvent;
	import com.tibco.cep.ui.monitor.events.TopologyUpdateEvent;
	import com.tibco.cep.ui.monitor.events.TopologyUpdateFailEvent;
	import com.tibco.cep.ui.monitor.io.PSVRClient;
	import com.tibco.cep.ui.monitor.sitetopology.Cluster;
	import com.tibco.cep.ui.monitor.sitetopology.DeploymentUnit;
	import com.tibco.cep.ui.monitor.sitetopology.Host;
	import com.tibco.cep.ui.monitor.sitetopology.Site;
	import com.tibco.cep.ui.monitor.util.Logger;
	import com.tibco.cep.ui.monitor.util.TopologyConstants;
	import com.tibco.cep.ui.monitor.util.Util;
	
	import flash.events.Event;
	import flash.system.System;
	import flash.utils.Dictionary;
	
	import mx.collections.XMLListCollection;
	
	public class TopologyMenuController implements IUpdateable{
		//site
		[Embed("assets/images/node_icons/site/active_site_16x16.gif")]
		private var SiteIcon:Class;
		//cluster
		[Embed("assets/images/node_icons/cluster/predefined_active_cluster_16x16.gif")]
		private var ClusterActivePredefIcon:Class;
		
		[Embed("assets/images/node_icons/cluster/nonpredefined_active_cluster_16x16.gif")]
		private var ClusterActiveNotPredefIcon:Class;
		
		[Embed("assets/images/node_icons/cluster/predefined_inactive_cluster_16x16.gif")]
		private var ClusterInactivePredefIcon:Class;
		
		[Embed("assets/images/node_icons/cluster/nonpredefined_inactive_cluster_16x16.gif")]
		private var ClusterInactiveNotPredefIcon:Class;
		//monitoredobjects
		[Embed("assets/images/node_icons/monitoredobjects/monitored_objects_active_16x16.gif")]
		private var MonitoredObjsActiveIcon:Class;
		
		[Embed("assets/images/node_icons/monitoredobjects/monitored_objects_inactive_16x16.gif")]
		private var MonitoredObjsInactiveIcon:Class;
		//machine
		[Embed("assets/images/node_icons/machine/predefined_active_notdeployed_machine_16x16.gif")]
		private var MachineActivePredefNotDeployedIcon:Class;
		
		[Embed("assets/images/node_icons/machine/nonpredefined_active_machine_16x16.gif")]
		private var MachineActiveNotPredefIcon:Class;
		
		[Embed("assets/images/node_icons/machine/predefined_inactive_notdeployed_machine_16x16.gif")]
		private var MachineInactivePredefNotDeployedIcon:Class;
//		--
		[Embed("assets/images/node_icons/machine/predefined_active_deployed_machine_16x16.gif")]
		private var MachineActivePredefDeployedIcon:Class;
		
		[Embed("assets/images/node_icons/machine/predefined_inactive_deployed_machine_16x16.gif")]
		private var MachineInactivePredefDeployedIcon:Class;
//		--
		[Embed("assets/images/node_icons/machine/nonpredefined_inactive_machine_16x16.gif")]
		private var MachineInactiveNotPredefIcon:Class;
		//process
		[Embed("assets/images/node_icons/process/predefined_active_process_16x16.gif")]
		private var ProcessActivePredefIcon:Class;
		
		[Embed("assets/images/node_icons/process/nonpredefined_active_process_16x16.gif")]
		private var ProcessActiveNotPredefIcon:Class;
		
		[Embed("assets/images/node_icons/process/predefined_inactive_process_16x16.gif")]
		private var ProcessInactivePredefIcon:Class;
		
		[Embed("assets/images/node_icons/process/nonpredefined_inactive_process_16x16.gif")]
		private var ProcessInactiveNotPredefIcon:Class;
		//agent
		[Embed("assets/images/node_icons/agent/cs/predefined_active_cacheserver_16x16.gif")]
		private var CacheServerActivePredefIcon:Class;
		
		[Embed("assets/images/node_icons/agent/cs/nonpredefined_active_cacheserver_16x16.gif")]
		private var CacheServerActiveNotPredefIcon:Class;
		
		[Embed("assets/images/node_icons/agent/cs/predefined_inactive_cacheserver_16x16.gif")]
		private var CacheServerInactivePredefIcon:Class;
		
		[Embed("assets/images/node_icons/agent/cs/nonpredefined_inactive_cacheserver_16x16.gif")]
		private var CacheServerInactiveNotPredefIcon:Class;
		
		[Embed("assets/images/node_icons/agent/ia/predefined_active_inferenceagent_16x16.gif")]
		private var InferenceAgentActivePredefIcon:Class;
		
		[Embed("assets/images/node_icons/agent/ia/nonpredefined_active_inferenceagent_16x16.gif")]
		private var InferenceAgentActiveNotPredefIcon:Class;
		
		[Embed("assets/images/node_icons/agent/ia/predefined_inactive_inferenceagent_16x16.gif")]
		private var InferenceAgentInactivePredefIcon:Class;
		
		[Embed("assets/images/node_icons/agent/ia/nonpredefined_inactive_inferenceagent_16x16.gif")]
		private var InferenceAgentInactiveNotPredefIcon:Class;
		
		[Embed("assets/images/node_icons/agent/qa/predefined_active_queryagent_16x16.gif")]
		private var QueryAgentActivePredefIcon:Class;
		
		[Embed("assets/images/node_icons/agent/qa/nonpredefined_active_queryagent_16x16.gif")]
		private var QueryAgentActiveNotPredefIcon:Class;
		
		[Embed("assets/images/node_icons/agent/qa/predefined_inactive_queryagent_16x16.gif")]
		private var QueryAgentInactivePredefIcon:Class;
		
		[Embed("assets/images/node_icons/agent/qa/nonpredefined_inactive_queryagent_16x16.gif")]
		private var QueryAgentInactiveNotPredefIcon:Class;
		
		[Embed("assets/images/node_icons/agent/dashboard/predefined_active_dashboardagent_16x16.gif")]
		private var DashboardAgentActivePredefIcon:Class;
		
		[Embed("assets/images/node_icons/agent/dashboard/nonpredefined_active_dashboardagent_16x16.gif")]
		private var DashboardAgentActiveNotPredefIcon:Class;
		
		[Embed("assets/images/node_icons/agent/dashboard/predefined_inactive_dashboardagent_16x16.gif")]
		private var DashboardAgentInactivePredefIcon:Class;
		
		[Embed("assets/images/node_icons/agent/dashboard/nonpredefined_inactive_dashboardagent_16x16.gif")]
		private var DashboardAgentInactiveNotPredefIcon:Class;
		//method group
		[Embed("assets/images/node_icons/method/managed_method_category_16x16.gif")]
		private var MethodGroupIcon:Class;
		//method
		[Embed("assets/images/node_icons/method/method_16x16.gif")]
		private var MethodIcon:Class;
			
		[Bindable]
		private var _topology:XMLListCollection; //data used by the view Tree
								//_data and _dataList are going to be updated in this.update()
		private var _data:XML; //XML object representing the XML data received from the URLRequest sent to the BEMM server
		private var _dataList:XMLList; //XMLList representing _data (necessary to be used by the _topology XMLListCollection)
		
		
		private static const THREAD_ANALYZER_SHARED_OBJ_PROP_NAME:String = "puThreadAnalyzerStatus";
		private static const AGENT_RESUME_SHARED_OBJ_PROP_NAME:String = "agentResumeStatus";
		private static const TOPOLOGY_MENU_SHARED_OBJ_NAME:String = "TOPOLOGY_MENU_SHARED_OBJ_NAME";
		
		private var _view:TopologyMenu;
		private var _purgeVersion:Number;
		private var _tmpPurgeData:XML;
		
		//The methods layout XML data is downloaded only once per proces and per agent type;
		//on the first time that the process/agent type is selected in the topology tree
		private var _IAMethodsLayoutXML:XML;
		private var _CSMethodsLayoutXML:XML;
		private var _QAMethodsLayoutXML:XML;
		private var _ProcessMethodsLayoutXML:XML;
		
		private var _topologyXMLFile:XML;
		private var _hostNameToTopologyProps:Dictionary;
		private var _puToThreadAnalyzerIsOn:Object;	//For a given PUID, it has value true if TA is started. False or undefined OW
		private var _activePUIDs:Dictionary;		//Uses PUID as key to keep track of active PU's
		
		private var _agentIDToIsPaused:Object;		//For a given AgentID, it has value true if the agent is resumed. False or undefined OW
		private var _activeAgentIDs:Dictionary;		//Uses AgentID as key to keep track of active Agents
		
		private var _sharedObjHandle:SharedObjHandler;
		
		private var _siteNameToSite:Dictionary;
		
		public function TopologyMenuController(view:TopologyMenu){
			_view = view;
			_purgeVersion = 0;
			_activePUIDs = new Dictionary(true);
			_activeAgentIDs = new Dictionary(true);
			_sharedObjHandle = new SharedObjHandler(TOPOLOGY_MENU_SHARED_OBJ_NAME);
			recoverStateFromSharedObj();
		}
		
		private function recoverStateFromSharedObj():void {
			_agentIDToIsPaused = _sharedObjHandle.getFromSharedObj(AGENT_RESUME_SHARED_OBJ_PROP_NAME);
			//If there is nothing in the shared object, creates the object 
			if (_agentIDToIsPaused == null)
				_agentIDToIsPaused = new Object();
				
			_puToThreadAnalyzerIsOn = _sharedObjHandle.getFromSharedObj(THREAD_ANALYZER_SHARED_OBJ_PROP_NAME);
			//If there is nothing in the shared object, creates the object 
			if (_puToThreadAnalyzerIsOn == null)
				_puToThreadAnalyzerIsOn = new Object();
		}
		
		// ****************************** Getter methods *********************************
		public function get topologyXMLFile():XML{ return _topologyXMLFile; }
		
		public function get puToThreadAnalyzerIsOn():Object{ return _puToThreadAnalyzerIsOn; }
		
		public function get agentIDToIsPaused():Object{ return _agentIDToIsPaused; }
		
		public function get view():TopologyMenu{ return _view; }
		
		public function getSite(name:String):Site{ return _siteNameToSite[name]; }
		
		public function init():void {
			PSVRClient.instance.getTopologyXML(this);
			PSVRClient.instance.getProcessMethodsLayout(this);	//stores methods layout in dictionaries
			PSVRClient.instance.getAgentMethodsLayout(this);	//stores methods layout in dictionaries	
		}
		
		//public function get view():TopologyMenu{ return _view; }
		public function get selectedItem():Object{ return _view.menuTree.selectedItem; }
		
		public function incrementPurgeVersion():Number{ return ++_purgeVersion; }
		
		/**
		 * Assigns icons to each elelment in the menu tree.  Also determines whether or not
		 * to enable the purge button.
		 */
		public function assignIcons():void{
			//TODO: Put icons for methods in here...
			
			var nodes:XMLList;
			_view.btn_Purge.enabled = false;
			
			nodes = new XMLList(_topology[0]);
			for each(var site:XML in nodes){
				_view.menuTree.setItemIcon(site, SiteIcon, SiteIcon);
			}
			
			nodes = nodes.cluster;
			for each(var cluster:XML in nodes){
				
				if(cluster.@active == "true" && cluster.@predefined == "true"){
					_view.menuTree.setItemIcon(cluster, ClusterActivePredefIcon, ClusterActivePredefIcon);	
				}
				else if(cluster.@active == "true" && cluster.@predefined == "false"){
					_view.menuTree.setItemIcon(cluster, ClusterActiveNotPredefIcon, ClusterActiveNotPredefIcon);
				}
				else if(cluster.@active == "false" && cluster.@predefined == "true") {
					_view.menuTree.setItemIcon(cluster, ClusterInactivePredefIcon, ClusterInactivePredefIcon);
					_view.btn_Purge.enabled = true;
				}
				else if (cluster.@active == "false" && cluster.@predefined == "false") {
					_view.menuTree.setItemIcon(cluster, ClusterInactiveNotPredefIcon, ClusterInactiveNotPredefIcon);
					_view.btn_Purge.enabled = true;
				}
				else
					Logger.log(this, "WARNING: Unexpected state found in cluster node");
			} //for
			
			for each(var cache:XML in nodes.mcacheobjects){
				if(cache.@active == "true"){
					_view.menuTree.setItemIcon(cache, MonitoredObjsActiveIcon, MonitoredObjsActiveIcon);
				}
				else{
					_view.menuTree.setItemIcon(cache, MonitoredObjsInactiveIcon, MonitoredObjsInactiveIcon);
					_view.btn_Purge.enabled = true;
				}
			}
			
			nodes = nodes.machine;
			for each(var machine:XML in nodes){
				if(machine.@active == "true" && machine.@predefined == "true" && machine.@deployed == "true"){
					_view.menuTree.setItemIcon(machine, MachineActivePredefDeployedIcon, MachineActivePredefDeployedIcon); //TODO:XXXX New icon
				}
				if(machine.@active == "true" && machine.@predefined == "true" && machine.@deployed == "false"){
					_view.menuTree.setItemIcon(machine, MachineActivePredefNotDeployedIcon, MachineActivePredefNotDeployedIcon);
				}
				else if(machine.@active == "true" && machine.@predefined == "false") {
					_view.menuTree.setItemIcon(machine, MachineActiveNotPredefIcon, MachineActiveNotPredefIcon);
				}
				else if (machine.@active == "false" && machine.@predefined == "true" && machine.@deployed == "true") { 
					_view.menuTree.setItemIcon(machine, MachineInactivePredefDeployedIcon, MachineInactivePredefDeployedIcon);  //TODO:XXXX New icon
					_view.btn_Purge.enabled = true;
				}
				else if (machine.@active == "false" && machine.@predefined == "true" && machine.@deployed == "false") { 
					_view.menuTree.setItemIcon(machine, MachineInactivePredefNotDeployedIcon, MachineInactivePredefNotDeployedIcon);
					_view.btn_Purge.enabled = true;
				}
				else if (machine.@active == "false" && machine.@predefined == "false") {
					_view.menuTree.setItemIcon(machine, MachineInactiveNotPredefIcon, MachineInactiveNotPredefIcon);
					_view.btn_Purge.enabled = true;
				}
				else
					Logger.logWarning(this, "Unexpected state found in machine node");
			} //for
			
			nodes = nodes.process;
			for each(var process:XML in nodes){
				if(process.@active == "true" && process.@predefined == "true"){
					_view.menuTree.setItemIcon(process, ProcessActivePredefIcon, ProcessActivePredefIcon);	
				}
				else if(process.@active == "true" && process.@predefined == "false"){
					_view.menuTree.setItemIcon(process, ProcessActiveNotPredefIcon, ProcessActiveNotPredefIcon);
				}
				else if(process.@active == "false" && process.@predefined == "true"){
					_view.menuTree.setItemIcon(process, ProcessInactivePredefIcon, ProcessInactivePredefIcon);
					_view.btn_Purge.enabled = true;
				}
				else if (process.@active == "false" && process.@predefined == "false") {
					_view.menuTree.setItemIcon(process, ProcessInactiveNotPredefIcon, ProcessInactiveNotPredefIcon);
					_view.btn_Purge.enabled = true;
				}
				else
					Logger.log(this, "WARNING: Unexpected state found in process node");
			} //for
			
			//set icons for method groups and methods at the process level
			setMethodIcons(nodes);
			
			nodes = nodes.agent;
			for each(var agent:XML in nodes){
				if(agent.@active == "true" && agent.@predefined == "true"){
					switch(String(agent.@type)){
						case(TopologyConstants.AGENT_TYPE_CACHESERVER):
							_view.menuTree.setItemIcon(agent, CacheServerActivePredefIcon, CacheServerActivePredefIcon);
							break;
						case(TopologyConstants.AGENT_TYPE_INFERENCE):
							_view.menuTree.setItemIcon(agent, InferenceAgentActivePredefIcon, InferenceAgentActivePredefIcon);
							break;
						case(TopologyConstants.AGENT_TYPE_QUERY):
							_view.menuTree.setItemIcon(agent, QueryAgentActivePredefIcon, QueryAgentActivePredefIcon);
							break;
						case(TopologyConstants.AGENT_TYPE_DASHBOARD):
							_view.menuTree.setItemIcon(agent, DashboardAgentActivePredefIcon, DashboardAgentActivePredefIcon);
							break;
					}
				}
				else if(agent.@active == "true" && agent.@predefined == "false"){
					switch(String(agent.@type)){
						case(TopologyConstants.AGENT_TYPE_CACHESERVER):
							_view.menuTree.setItemIcon(agent, CacheServerActiveNotPredefIcon, CacheServerActiveNotPredefIcon);
							break;
						case(TopologyConstants.AGENT_TYPE_INFERENCE):
							_view.menuTree.setItemIcon(agent, InferenceAgentActiveNotPredefIcon, InferenceAgentActiveNotPredefIcon);
							break;
						case(TopologyConstants.AGENT_TYPE_QUERY):
							_view.menuTree.setItemIcon(agent, QueryAgentActiveNotPredefIcon, QueryAgentActiveNotPredefIcon);
							break;
						case(TopologyConstants.AGENT_TYPE_DASHBOARD):
							_view.menuTree.setItemIcon(agent, DashboardAgentActiveNotPredefIcon, DashboardAgentActiveNotPredefIcon);
							break;
					}
				}
				else if(agent.@active == "false" && agent.@predefined == "true") {
					switch(String(agent.@type)){
						case(TopologyConstants.AGENT_TYPE_CACHESERVER):
							_view.menuTree.setItemIcon(agent, CacheServerInactivePredefIcon, CacheServerInactivePredefIcon);
							break;
						case(TopologyConstants.AGENT_TYPE_INFERENCE):
							_view.menuTree.setItemIcon(agent, InferenceAgentInactivePredefIcon, InferenceAgentInactivePredefIcon);
							break;
						case(TopologyConstants.AGENT_TYPE_QUERY):
							_view.menuTree.setItemIcon(agent, QueryAgentInactivePredefIcon, QueryAgentInactivePredefIcon);
							break;
						case(TopologyConstants.AGENT_TYPE_DASHBOARD):
							_view.menuTree.setItemIcon(agent, DashboardAgentInactivePredefIcon, DashboardAgentInactivePredefIcon);
							break;
					}
					_view.btn_Purge.enabled = true;
				}
				else if (agent.@active == "false" && agent.@predefined == "false") {
					switch(String(agent.@type)){
						case(TopologyConstants.AGENT_TYPE_CACHESERVER):
							_view.menuTree.setItemIcon(agent, CacheServerInactiveNotPredefIcon, CacheServerInactiveNotPredefIcon);
							break;
						case(TopologyConstants.AGENT_TYPE_INFERENCE):
							_view.menuTree.setItemIcon(agent, InferenceAgentInactiveNotPredefIcon, InferenceAgentInactiveNotPredefIcon);
							break;
						case(TopologyConstants.AGENT_TYPE_QUERY):
							_view.menuTree.setItemIcon(agent, QueryAgentInactiveNotPredefIcon, QueryAgentInactiveNotPredefIcon);
							break;
						case(TopologyConstants.AGENT_TYPE_DASHBOARD):
							_view.menuTree.setItemIcon(agent, DashboardAgentInactiveNotPredefIcon, DashboardAgentInactiveNotPredefIcon);
							break;
					}
					_view.btn_Purge.enabled = true;
				}
				else
					Logger.log(this, "WARNING: Unexpected state found in agent node");
			} //for
			
			//set icons for method groups and methods at the agent level
			setMethodIcons(nodes);
		} //assignIcons
		
		/** sets the icons for the method groups and methods in the topology tree */
		private function setMethodIcons(nodes:XMLList):void {
			nodes = nodes.methods;
			for each(var methods:XML in nodes){				
				_view.menuTree.setItemIcon(methods, MethodGroupIcon, MethodGroupIcon);
			}
			
			// ======= IF UNCOMMENT THE FOLLOWING 4 LINES, look at lines 360/61 
//			nodes = nodes.methodgroup;
//			for each(var methodGroup:XML in nodes){				
//				_view.menuTree.setItemIcon(methodGroup, MethodGroupIcon, MethodGroupIcon);
//			}

			//set icons for methods
//			nodes = nodes.method;
			nodes = nodes.methodgroup.method;
			for each(var method:XML in nodes){				
				_view.menuTree.setItemIcon(method, MethodIcon, MethodIcon);
				_view.menuTree.dataTipFunction = getMethodTooltip as Function;
				_view.menuTree.showDataTips=true;
			}
		}
					//TODO: Check if I can improve this method
		//Gets the full path for the selected topology node. Each level is separated by "/"
		public function getMachineName(selectedNode:XML):String {
			switch (String(selectedNode.name()).toLowerCase()){
				case TopologyConstants.MACHINE_NODE:
					return String(selectedNode.@name).toLowerCase();
				case TopologyConstants.PROCESS_NODE:
					return String(selectedNode.parent().@name).toLowerCase();
				case TopologyConstants.AGENT_NODE:
					return String(selectedNode.parent().parent().@name).toLowerCase();
				case TopologyConstants.METHODS_NODE:
					return String(selectedNode.parent().parent().parent().@name).toLowerCase();
				case TopologyConstants.METHOD_GROUP_NODE:
					return String(selectedNode.parent().parent().parent().parent().@name).toLowerCase();
				case TopologyConstants.METHOD_NODE:
					return String(selectedNode.parent().parent().parent().parent().parent().@name).toLowerCase();	
				default:
					Logger.logWarning(this, "Machine name not available for the selected node: " + selectedNode.name());
					return "";
			} //switch 
		}
		
		//Gets the full path for the selected topology node. Each level is separated by "/"
		public function getFullPath(selectedNode:XML):String {
			var str:String;
			var level:int = -1;
			switch (String(selectedNode.name()).toLowerCase()){
				case "site":
					level = 0;
					break;
				case "cluster":
					level = 1;
					break;
				case "machine":
					level = 2;
					break;
				case "process":
					level = 3;
					break;
				case "agent":
					level = 4;
					break;
				case "methodgroup":
					level = 5;
					break;
				case "method":
					level = 6;
					break;	
				default:
					trace("WARNING: unrecognized node in the topology tree");
					return "";
			} //switch
			
			var names:Array = new Array(level); 
			for(var i:int = level; i>=0; i--) {
				names[i]=selectedNode.@name;
				selectedNode = selectedNode.parent();
			}
			var fullPath:String = "/";
			for (var j:int=1; j<=level; j++) {
				fullPath=fullPath+names[j]+"/";
			}
			return fullPath.substring(0, fullPath.length-1);  //remove the last "/"
		} //getFullPath
		
		//dataTipFunction property above looks for a property 'label' in the data provider and displays 
		//the value of 'label' as a tooltip.
		private function getMethodTooltip(item:Object):String {
			return String(XML(item).@label);
		}
		
		private function setTopologyData():void{
			insertMethodsInTopgy(_data);					//Need to pass data as argument because it's a recursive function
			//Update UI with the most recent state.  
			removeInactivePUFromThreadOnOffStatusDict();
			updateAgentIDToIsResumed();

			_topology = new XMLListCollection(_dataList);
			_view.dataProvider = _topology;
			_view.menuTree.dataProvider = _topology;
			
			_purgeVersion = Number(_data.@purgeversion);
			
			assignIcons();
		}
		
		public function get topologyData():XMLListCollection {
			return _topology;
		}
		
		private function setProcessMethodsLayout():void{
			_ProcessMethodsLayoutXML = _data;
		}
		
		private function setAgentMethodsLayout():void{
			//parse data into methods layout for each agent type
			for each(var agentNode:XML in _data.agent){
				var type:String = agentNode.@type;
				switch(type.toLowerCase()){
					case(TopologyConstants.AGENT_TYPE_INFERENCE):
						_IAMethodsLayoutXML = agentNode;
						break;
					case(TopologyConstants.AGENT_TYPE_CACHESERVER):
						_CSMethodsLayoutXML = agentNode;
						break;
					case(TopologyConstants.AGENT_TYPE_QUERY):
						_QAMethodsLayoutXML = agentNode;
						break;
					case(TopologyConstants.AGENT_TYPE_DASHBOARD):
						Logger.logWarning(this,"In the future we need to add here methods layout for agents of type dashboard");	//todo
						break;
					default:
						Logger.logWarning(this,"Unrecognized method layout for agent of type: " + type);
				}
			}
			//These two calls are put in here because before setting the subscriptions we need to get
			//the topology, the process methods, and the agent methods 
			PSVRClient.instance.getSiteTopology(this);
			PSVRClient.instance.subscribeTopology(this, this);	//initiates the timer (TimerBasedPSVRUpdatesClient) to refresh topology
		}
		
		private function updateTopologyData():void{		
			_view.saveExpansionState();
			_activePUIDs = new Dictionary(true);				//TODO: Try to find a better place to put this
			_activeAgentIDs = new Dictionary(true);
			insertMethodsInTopgy(_data);						//Need to pass data as argument because it's a recursive function
			removeInactivePUFromThreadOnOffStatusDict();
			updateAgentIDToIsResumed();
			
			_topology = new XMLListCollection(_dataList);
			_view.dataProvider = _topology;
			_view.menuTree.dataProvider = _topology;
			
//			_topology.removeAll();
////			data.uid = "";
//			_topology.addItem(_dataList);
//			_topology.refresh();
			
			_view.restoreExpansionState();
			assignIcons();
			_view.dispatchEvent(new TopologyUpdateEvent(new XML(_data)));
		}//updateTopologyData
		
		/** Iterates over the topology tree XML and calls methods to insert the process and agent methods layout into 
		 * the topology tree */
		private function insertMethodsInTopgy(data:XML):void {
			if(data==null) 
				return;
			
			//Traverse the topology tree and insert the appropriate methods data for process and agent
			//Also puts the active pu's and active agents in a dictionary using puid/agentid as keys
			for each(var child:XML in data.children()){
//				if(String(child.name()).toLowerCase() == TopologyConstants.MACHINE_NODE){	TODO: CONFIRM
//					if (String(child.@deployed).toLowerCase() == "true")
//						child.@label = new String("Deployed on "+child.@lastDeploy);
//					else 
//						child.@label = "Machine not deployed"; 
//				}
				
				if(String(child.name()).toLowerCase() == TopologyConstants.PROCESS_NODE) { 	//TODO: CONFIRM
					if (String(child.@deployed).toLowerCase() == "true" && String(child.@lastDeploy) != "null")
						child.@label = new String("Deployed on "+child.@lastDeploy);
					else if (String(child.@active).toLowerCase() == "false")       //to avoid showing DU not deployed 
						child.@label = "DU not deployed";                          //for active PUs   
					
					addMethodsLayoutXML(child, new XML(_ProcessMethodsLayoutXML) );					
					if (String(child.@active).toLowerCase() == "true")
						_activePUIDs[String(child.@id)]=true;	//Uses ID to keep track of active PU's
				} 
				
				
				if(String(child.name()).toLowerCase() == TopologyConstants.AGENT_NODE){
					if (String(child.@active).toLowerCase() == "true")
						_activeAgentIDs[String(child.@id)]=true;	//Uses ID to keep track of active Agents
						
					switch(String(child.@type).toLowerCase()){
						case(TopologyConstants.AGENT_TYPE_INFERENCE):
							addMethodsLayoutXML(child, new XML(_IAMethodsLayoutXML) );
							break;
						case(TopologyConstants.AGENT_TYPE_CACHESERVER):
							addMethodsLayoutXML(child, new XML(_CSMethodsLayoutXML) );
							break;
						case(TopologyConstants.AGENT_TYPE_QUERY):
							addMethodsLayoutXML(child, new XML(_QAMethodsLayoutXML) );
							break;
						case(TopologyConstants.AGENT_TYPE_DASHBOARD):
							Logger.logWarning(this,"In the future we need to insert here the dashboard methods in the topology");	//todo
							break;
						default:
							Logger.logWarning(this,"Invalid entity type: " + String(child.@type));
					}
				}
				else{
					insertMethodsInTopgy(child);
				}
			}
		}
		
		/** Adds methods layout after inserting ID in every methods node and sub-node*/
		private function addMethodsLayoutXML(node:XML, methodLayout:XML):void{
			if(node == null || methodLayout == null) 
				return;
				
			if (node.name().toString().toLowerCase() == TopologyConstants.AGENT_NODE)
				addIdToAgentMethods(node.@id, node.@type, methodLayout);
			else
				addIdToProcessMethods(node.@id, methodLayout);
				
			for each(var methods:XML in methodLayout.methods){
				node.appendChild(methods);
			}
		}
		
		/** Iterates over every node in the process methodsLayoutXML and adds the "id" attribute to it */
		private function addIdToProcessMethods(nodeID:String, methodsLayoutXML:XML):void {
			for each (var methods:XML in methodsLayoutXML.methods){
				methods.@id = nodeID + "#" + "process"; 
				for each (var group:XML in methods.methodgroup){
					group.@id = methods.@id + "#" + group.@name;
					for each (var method:XML in group.method){
						method.@id = group.@id + "#" + method.@name; 
						//necessary a property with name 'label' to display the tooltip in the topology tree
						method.@label = "Select and click Execute button";
					}
				}
			}
		} //addIdToMethodsLayoutXML
		
		/** Iterates over every node in the agent methodsLayoutXML and adds the "id" attribute to it */
		private function addIdToAgentMethods(nodeID:String, agentType:String, methodsLayoutXML:XML):void {
			for each (var methods:XML in methodsLayoutXML.methods){
				methods.@id = nodeID + "#" + agentType; 
				for each (var group:XML in methods.methodgroup){
					group.@id = methods.@id + "#" + group.@name;
					for each (var method:XML in group.method){
						method.@id = group.@id + "#" + method.@name; 
						//necessary a property with name 'label' to display the tooltip in the topology tree
						method.@label = "Select and click Execute button";
					}
				}
			}
		} //addIdToMethodsLayoutXML

		public function setPurgedTopology():void{
			setTopologyData();
			var selected:String = String(_data.@selected);
			var id:String = String(_data.@id);

			_view.selectedTreeNode = selected != "" ? selected : id;
		}
					
		//remove the PU ID from the on/off thread button dictionary if this pu is no longer active
		private function removeInactivePUFromThreadOnOffStatusDict():void {
			for (var puId:String in puToThreadAnalyzerIsOn) {
				if (_activePUIDs[puId]== undefined) {				//checks if this pu id is no longer active
					Logger.log(this,"PUID: " + puId + " no longer active and removed from the puToThreadOnOffStatus dictionary" );
					delete puToThreadAnalyzerIsOn[puId];		//if no longer active remove it
				}
			}
			_sharedObjHandle.updateSharedObject(THREAD_ANALYZER_SHARED_OBJ_PROP_NAME, _puToThreadAnalyzerIsOn);
		} 
		
		/** Update the status of the thread analyzer on/off dictionary if the startStopThreadAnalyzer operation was successfully invoked 
		 * @param key is PUID */
		public function updateThreadAnalyzerOnOffStatus(key:String):void {
			switch (puToThreadAnalyzerIsOn[key]) {
				case undefined:		//ThreadDump button clicked for the first time, hence this pu not yet in the dictionary.
					puToThreadAnalyzerIsOn[key]=true	  //start Thread Analyzer
					break;
				case true:			//true - TA is running
					puToThreadAnalyzerIsOn[key]=false	  //stop Thread Analyzer
					break;
				case false:			//false - TA is stopped
					puToThreadAnalyzerIsOn[key]=true	  //start Thread Analyzer
					break;
				default:
					Logger.log(this,"WARNING: unexpected key found in puToThreadAnalyzerIsOn dictionary... Investigate!");
			}
		}
		
		private function updateAgentIDToIsResumed():void {
			for (var agentId:String in agentIDToIsPaused) {
				if (_activeAgentIDs[agentId]== undefined) {			//check if this agent id is no longer active
					Logger.log(this,"Agent ID: " + agentId + " no longer active and removed from the agentIDToIsResumed dictionary" );
					delete agentIDToIsPaused[agentId];			//if no longer active remove it
				}
			}
			_sharedObjHandle.updateSharedObject(AGENT_RESUME_SHARED_OBJ_PROP_NAME, _agentIDToIsPaused);
		}
		
		/** remove the namespaces because it creates all sorts of trouble */
		private function removeNameSpaceFromTpgyXMLFile():void {
			var topologyFileStr:String = _topologyXMLFile.toString();
			var xmlnsPattern:RegExp;
			xmlnsPattern = new RegExp("xmlns[^\"]*\"[^\"]*\"", "gi");
			topologyFileStr = topologyFileStr.replace(xmlnsPattern,"");
//			replace "\" with "\\"
//			topologyFileStr = topologyFileStr.replace(/\\/g,"\\\\"); 	//TODO: This might not be necessary when Nick sends the paths with \\
			
			_topologyXMLFile = XML(topologyFileStr); 
		}
		
		/** This function replaces '-' by '_' in the XML element names (nodes) and in their attributes. This is done because 
		 *  adobe flex does not allow names with '-' chars to be used with the XML . notation (eg. n-a.n-b.n-c, but allows
		 *  n_a.n_b.n_c. */
		private function renameXMLObjectNames(xmlObject:XML):void {
			for each (var childNode:XML in xmlObject.children()) {
				childNode.setName( XML(String(childNode.name()).replace(/-/g,"_")) );
				var attribs:XMLList = childNode.attributes()
				for each (var attrib:XML in attribs) {
					attrib.setName(XML(String(attrib.name()).replace(/-/g,"_")));
				}
				renameXMLObjectNames(childNode);	
			}
		}
		
		private function parseSiteTopologyFile():void {
			_siteNameToSite = new Dictionary(true);
			
			for each (var site:XML in _topologyXMLFile[TopologyConstants.SITE_NODE]) {
				var name:String = String(site.@name);

                var s:Site = _siteNameToSite[name] == undefined ? new Site(name) : _siteNameToSite[name];
				
				//this method calls parseHosts() and parseDeployUnits()
				parseCluster(site,s);
				_siteNameToSite[name] = s;
			}
				
			// This line is for debug purposes only
			// (_clusterNameToCluster["AgentGroupCluster"] as Cluster).getHostFromId("M1").getDeployUnitFromId("m1-du");
		}
		
		private function parseCluster(siteNode:XML, site:Site):void {
			for each (var cluster:XML in siteNode[TopologyConstants.TOP_FILE_CLUSTERS_NODE]
									   			 [TopologyConstants.TOP_FILE_CLUSTER_NODE] ) {
									   			 	
				var name:String = String(cluster.@[TopologyConstants.TOP_FILE_CLUSTER_NODE_NAME_ATTR]);
				var c:Cluster = new Cluster(name, site.name);
//				c.id = ID		//TODO: When there is id.

				parseHosts(siteNode,c);
				parseDeployUnits(siteNode,c);
													   			 	
				site.addCluster(c);
   		 	}
		}
		
		private function parseHosts(siteNode:XML, cluster:Cluster):void {
			for each (var hostResource:XML in siteNode[TopologyConstants.TOP_FILE_HOST_RESOURCES_NODE]
													  [TopologyConstants.TOP_FILE_HOST_RESOURCE_NODE]) {
				var host:Host = new Host(cluster.fqName);
														      	
				host.id = hostResource.@[TopologyConstants.TOP_FILE_HOST_RESOURCE_NODE_ID_ATTR];
				host.fqid = createFqid(cluster.name,host.id);
				host.name = hostResource[TopologyConstants.TOP_FILE_HOST_NAME_NODE];
				host.ip = hostResource[TopologyConstants.TOP_FILE_IP_NODE];
				host.username = String(hostResource[TopologyConstants.TOP_FILE_USER_CREDENTIALS_NODE].
								@[TopologyConstants.TOP_FILE_USER_CREDENTIALS_NODE_USERNAME_ATTR]).replace(/\\\\/g,"\\"); 	
				host.password = hostResource[TopologyConstants.TOP_FILE_USER_CREDENTIALS_NODE].
								@[TopologyConstants.TOP_FILE_USER_CREDENTIALS_NODE_PASSWD_ATTR];
				
				cluster.addHost(host);
	      	}
		}
		
		private function parseDeployUnits(siteNode:XML, cluster:Cluster):void {
			
			//key is duId. Value is Array with the HostIds that are hosting the du in key
			var duIdToMappedHostIdArray:Dictionary = new Dictionary(true);
			
			//Parses the DuId to HostId information, i.e., which du is mapped to which host
			for each (var duMap:XML in siteNode[TopologyConstants.TOP_FILE_CLUSTERS_NODE]
													    [TopologyConstants.TOP_FILE_CLUSTER_NODE]
													    [TopologyConstants.TOP_FILE_DEPLOY_MAPPINGS_NODE]
													    [TopologyConstants.TOP_FILE_DEPLOY_MAPPING_NODE] ) {
													    	
				var duId:String = duMap.@[TopologyConstants.TOP_FILE_DEPLOY_MAPPING_NODE_DEPLOY_UNIT_REF_ATTR];
				
				if (duIdToMappedHostIdArray[duId] == undefined) {
					duIdToMappedHostIdArray[duId] = new Array();
				} 
				//this var is just to make the code easier to read
				var hostId:String = duMap.@[TopologyConstants.TOP_FILE_DEPLOY_MAPPING_NODE_HOST_REF_ATTR];
				(duIdToMappedHostIdArray[duId] as Array).push(hostId);
			}
			
			//Parse the actual DU information, and associates each DU with the host 
			for each (var duXml:XML in siteNode[TopologyConstants.TOP_FILE_CLUSTERS_NODE]
										[TopologyConstants.TOP_FILE_CLUSTER_NODE]
										[TopologyConstants.TOP_FILE_DEPLOY_UNITS_NODE]
										[TopologyConstants.TOP_FILE_DEPLOY_UNIT_NODE] ) {
				var du:DeploymentUnit = new DeploymentUnit();
				
				du.id = duXml.@[TopologyConstants.TOP_FILE_DEPLOY_UNIT_NODE_ID_ATTR]
				du.name = duXml.@[TopologyConstants.TOP_FILE_DEPLOY_UNIT_NODE_NAME_ATTR]
				
				du.deployCddPath = String(duXml[TopologyConstants.TOP_FILE_DEPLOY_FILES_NODE]
										  [TopologyConstants.TOP_FILE_CDD_DEPLOY_NODE]).replace(/\\\\/g,"\\");
				
				du.deployEarPath= String(duXml[TopologyConstants.TOP_FILE_DEPLOY_FILES_NODE]
										 		[TopologyConstants.TOP_FILE_EAR_DEPLOY_NODE]).replace(/\\\\/g,"\\");

				//Puts the info of the DU's mapped to this host in the class object representing this host										 		
		 		for each (var hostId1:String in duIdToMappedHostIdArray[du.id]) {
		 			cluster.getHostFromId(hostId1).addDeployUnit(du);	
		 		}
			}
		}
		
		private function createFqid(prefix:String, simpleId:String):String {
			return prefix+"#"+simpleId;
		}
	
		public function update(operation:String,data:XML):void {
			
			Logger.logDebug(this, "Total Memory: " + System.totalMemory);
			
			_data = data;
			_dataList = XMLList(_data);
			
			if (operation == "gettopology") {	//methods layout of processes and agents, plus corresponding ID's, 
				setTopologyData();				//are inserted inside this function call
			}
			else if(operation == "updatetopololgy"){
				var newVersion:Number = Number(data.@purgeversion);
				if(newVersion > _purgeVersion){
					//purge detected
					_purgeVersion = newVersion;
					Util.infoMessage("Remote Purge Detected...", handlePurgeAcknowledged);
					_tmpPurgeData = new XML(data);
				}
				else{
					updateTopologyData();
				}
			}
			else if(operation == "gettopologyxmlfile") {	//gets the site topology file and stores its info
				if (_topologyXMLFile == null) {
					_topologyXMLFile = new XML(data);
					Logger.log(this,"Loading topology.xml");
				}
				else {
					_topologyXMLFile = new XML(data);
					Logger.log(this,"WARNING: topology.xml had already been loaded and was rewritten");
				}
				removeNameSpaceFromTpgyXMLFile();
				renameXMLObjectNames(_topologyXMLFile);
				parseSiteTopologyFile();
			}			 
			else if (operation == "getprocessmethodslayout"){
				setProcessMethodsLayout();
			}
			else if (operation == "getagentmethodslayout"){
				setAgentMethodsLayout();
			}						
			else if(operation == "purgetopology"){
				//this client called for the purge
				setPurgedTopology();
			}
			else if(operation == "subscribetopology"){
				
			}
			else if(operation == "unsubscribetopology"){
				
			}		
			else 
				Logger.log(this,"WARNING: Unexpected update operation: " + operation);	
			
			//To speedup GC
			_data=null;
			_dataList=null;
		} //update

		public function updateFailure(operation:String,message:String, code:uint):void{
			PSVRClient.instance.unsubscribeTopology(this);
			_view.dispatchEvent(new TopologyUpdateFailEvent(operation, message, code));
		}
		
		private function handlePurgeAcknowledged(event:Event):void{
			_data = _tmpPurgeData;
			setPurgedTopology();			
			_tmpPurgeData = null;
			view.dispatchEvent(new TopologyPurgedEvent());
		}

	}//class
}//package

