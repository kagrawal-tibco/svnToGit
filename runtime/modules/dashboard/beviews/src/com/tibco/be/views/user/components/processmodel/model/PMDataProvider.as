package com.tibco.be.views.user.components.processmodel.model{

	import flash.utils.Dictionary;
	
	public class PMDataProvider{
		
		private var _nodeToDataMap:Dictionary;
		
		public function PMDataProvider(){
			_nodeToDataMap = new Dictionary();
		}
		
		public function get nodeToDataMap():Dictionary{
			return _nodeToDataMap;
		}
		
		public function hasDataFor(nodeId:String):Boolean{
			return _nodeToDataMap[nodeId] != undefined;
		}
		
		public function getDataFor(nodeId:String):XML{
			return hasDataFor(nodeId) ? _nodeToDataMap[nodeId] as XML:null;
		}
		
		public function setDataFor(nodeId:String, data:XML):void{
			_nodeToDataMap[nodeId] = data;
		}
		
		public function clear():void{
			_nodeToDataMap = new Dictionary();
		}

	}
}