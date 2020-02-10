package com.tibco.cep.ui.monitor.sitetopology
{
	import com.tibco.cep.ui.monitor.util.Util;
	
	import flash.utils.Dictionary;
	
	public class Host {
		private var _hostId:String;
		private var _fqHostId:String;

		private var _fqName:String;
		private var _hostName:String;
		private var _clusterFqName:String;
		private var _ip:String;
		private var _username:String;
		private var _password:String;
		
		private var _mappedDuIdToDu:Dictionary;
		private var _mappedDuNameToDuId:Dictionary;		//TODO: If necessary
		
		public function Host(parentClusterFqName:String) {
			_clusterFqName = parentClusterFqName;
			_mappedDuIdToDu = new Dictionary(true);
			_mappedDuNameToDuId = new Dictionary(true);
		}
		
		public function get id():String { return _hostId; }
		public function set id(hostId:String):void { _hostId=hostId; }
		
		public function get fqid():String { return _fqHostId; }
		public function set fqid(fqHostId:String):void { _fqHostId=fqHostId; }
		
		public function get name():String { return _hostName; }
		public function set name(hostName:String):void { 
			_hostName=hostName;
			_fqName = _clusterFqName + "/" + hostName; 
		}
		
		public function get fqname():String { return _fqName; }
		
		public function get ip():String { return _ip; }
		public function set ip(ip:String):void { _ip=ip; }
		
		public function get username():String { return _username; }
		public function set username(username:String):void { _username=username; }
		
		public function get password():String { return _password; }
		public function set password(password:String):void { _password=password; }

		public function addDeployUnit(du:DeploymentUnit):void {
			_mappedDuIdToDu[du.id] = du;
			_mappedDuNameToDuId[du.name] = du.id;
		}
		
		public function getDeployUnit(duName:String):DeploymentUnit {
			duName = Util.findSimpleName(duName); 
			return _mappedDuIdToDu[ _mappedDuNameToDuId[duName] ];
		}
		
		public function getDeployUnitFromId(duid:String):DeploymentUnit { 
			return _mappedDuIdToDu[duid];
		}
		
		public function getMappedDus():Array {
		    var dus:Array = new Array();
		    for each (var du:DeploymentUnit in _mappedDuIdToDu){
		        dus.push(du);
		    }
		    return dus;
		}
	}
}