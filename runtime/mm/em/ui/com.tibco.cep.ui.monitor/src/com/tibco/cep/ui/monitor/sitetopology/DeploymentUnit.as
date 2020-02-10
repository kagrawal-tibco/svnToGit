package com.tibco.cep.ui.monitor.sitetopology
{
	import flash.utils.Dictionary;
	
	public class DeploymentUnit {
		private var _id:String;
		private var _name:String;
		private var _deployCddPath:String;
		private var _deployEarPath:String;
//		private var _puIdToPu:Dictionary;		//TODO: If necessary
		
		public function DeploymentUnit(){
		}
		
		public function get id():String { return _id; }
		public function set id(id:String):void { _id=id; }
		
		public function get name():String { return _name; }
		public function set name(name:String):void { _name=name; }  
		
		public function get deployCddPath():String { return _deployCddPath; }
		public function set deployCddPath(deployCddPath:String):void { _deployCddPath=deployCddPath; }
		  
		public function get deployEarPath():String { return _deployEarPath; }
		public function set deployEarPath(deployEarPath:String):void { _deployEarPath=deployEarPath; }  
	}
}