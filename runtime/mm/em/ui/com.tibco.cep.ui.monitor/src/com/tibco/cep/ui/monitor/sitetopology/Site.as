package com.tibco.cep.ui.monitor.sitetopology
{
	import flash.utils.Dictionary;
	
	public class Site
	{
		private var _name:String;
		private var _clusterNameToCluster:Dictionary;	//Key is UPPER CASE
		
		public function Site(name:String)
		{
			_name=name;
			_clusterNameToCluster = new Dictionary(true);
		}
		
		public function get name():String {return _name;}
		
		public function addCluster(cluster:Cluster):void {
			_clusterNameToCluster[cluster.name.toUpperCase()] = cluster;
		}
		
		public function getClusters():Array {
		    var clusters:Array = new Array();
		    for each (var cluster:Cluster in _clusterNameToCluster){
		        clusters.push(cluster);
		    }
		    return clusters;
		}
		
		public function getCluster(clusterName:String):Cluster { 
			return _clusterNameToCluster[clusterName.toUpperCase()]; 
		}

	}
}