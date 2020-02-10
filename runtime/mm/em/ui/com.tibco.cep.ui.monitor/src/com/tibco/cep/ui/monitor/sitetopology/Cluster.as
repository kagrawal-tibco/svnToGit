package com.tibco.cep.ui.monitor.sitetopology
{
	import com.tibco.cep.ui.monitor.util.Util;
	
	import flash.utils.Dictionary;
	
	public class Cluster
	{
		private var _name:String;
		private var _fqName:String;
		private var _id:String;
		
		//dictionary keys are all upper case
		private var _hostIdToHost:Dictionary; 
		private var _hostNameToHostId:Dictionary;
		
		public function Cluster(name:String, parentSiteName:String) {
			_name = name;
			_fqName = "/" + parentSiteName + "/" + name;
			_hostIdToHost = new Dictionary(true); 
			_hostNameToHostId = new Dictionary(true);
		}
		
//		public function get id():String { return _id; }
//		public function set id(id:String):void { _id=id; }
		
		public function get name():String { return _name; }
		public function get fqName():String { return _fqName; }
	
		public function getHost(hostName:String):Host {
			hostName = Util.findSimpleName(hostName);
			 
			return _hostIdToHost[ _hostNameToHostId[hostName.toUpperCase()] ]; 
		}
		
		public function getHostFromId(hostId:String):Host { 
			return  _hostIdToHost[hostId.toUpperCase()]; 
		}

		public function getHosts():Array {
		    var hosts:Array = new Array();
		    for each (var host:Host in _hostIdToHost){
		        hosts.push(host);
		    }
		    return hosts;
		}

		public function addHost(host:Host):void {
			_hostIdToHost[host.id.toUpperCase()] = host;
			_hostNameToHostId[host.name.toUpperCase()]= host.id.toUpperCase(); 
		}
	}
}