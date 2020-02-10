package com.tibco.cep.ui.monitor.panels
{
	import flash.utils.Dictionary;

	//This class wraps the information describing each host/machine and user credentials as defined in the topology.st file. 
	//It also includes the CDD and EAR locations of the deployment units mapped to this machine.
	public class TopologyFileProps
	{
		
		//Maps containing the CDD/EAR deployment locations of the deployment units mapped to this machine
		private var _thisHostsDeployedUnitIdToDeployCDDLoc:Dictionary = new Dictionary(true); 
		private var _thisHostsDeployedUnitIdToDeployEARLoc:Dictionary = new Dictionary(true); 
		private var _thisHostsDeployedUnitIdToDeployUnitName:Dictionary = new Dictionary(true);
		
		private var _hostId:String;
		private var _hostName:String;
		private var _ip:String;
		private var _username:String;
		private var _password:String;
		private var _clusterName:String;	//TODO:
		
		public function TopologyFileProps()
		{
		}
		
		public function get thisHostsDeployedUnitIdToDeployUnitName():Dictionary { return _thisHostsDeployedUnitIdToDeployUnitName; }
		
		public function get thisHostsDeployedUnitIdToDeployCDDLoc():Dictionary { return _thisHostsDeployedUnitIdToDeployCDDLoc; }
//		public function set thisHostsDeployedUnitIdToDeployCDDLoc(deployUnitIdToDeployCDDLoc:Dictionary):void { _thisHostsDeployedUnitIdToDeployCDDLoc=deployUnitIdToDeployCDDLoc; }
		
		public function get thisHostsDeployedUnitIdToDeployEARLoc():Dictionary { return _thisHostsDeployedUnitIdToDeployEARLoc; }
//		public function set thisHostsDeployedUnitIdToDeployEARLoc(deployUnitIdToDeployEARLoc:Dictionary):void { _thisHostsDeployedUnitIdToDeployEARLoc=deployUnitIdToDeployEARLoc; }
		
		public function get hostId():String { return _hostId; }
		public function set hostId(hostId:String):void { _hostId=hostId; }
		
		public function get hostName():String { return _hostName; }
		public function set hostName(hostName:String):void { _hostName=hostName; }
		
		public function get ip():String { return _ip; }
		public function set ip(ip:String):void { _ip=ip; }
		
		public function get username():String { return _username; }
		public function set username(username:String):void { _username=username; }
		
		public function get password():String { return _password; }
		public function set password(password:String):void { _password=password; }
		
		/** For the 'host id' specified in the state of this object, put in a map the name, CDD and EAR locations of the deployment 
		 * units mapped to this host. The map key is the deployment unit id */
		public function putThisHostDeploymentInfo(hostIdToMappedDeployUnitsIdsArray:Dictionary, deployUnitIdToDeployCDDLoc:Dictionary, 
												deployUnitIdToDeployEARLoc:Dictionary,deployUnitIdToDeployUnitName:Dictionary):void {
													
			for each (var deployUnitIdInThisHost:String in hostIdToMappedDeployUnitsIdsArray[_hostId]){
				_thisHostsDeployedUnitIdToDeployCDDLoc[deployUnitIdInThisHost] = deployUnitIdToDeployCDDLoc[deployUnitIdInThisHost];
				_thisHostsDeployedUnitIdToDeployEARLoc[deployUnitIdInThisHost] = deployUnitIdToDeployEARLoc[deployUnitIdInThisHost];
				_thisHostsDeployedUnitIdToDeployUnitName[deployUnitIdInThisHost] = deployUnitIdToDeployUnitName[deployUnitIdInThisHost];
			}
		}
		
	} //class
}	//package