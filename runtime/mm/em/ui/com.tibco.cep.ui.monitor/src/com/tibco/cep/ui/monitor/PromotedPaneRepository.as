package com.tibco.cep.ui.monitor{
	import com.tibco.cep.ui.monitor.panes.MetricPane;
	import com.tibco.cep.ui.monitor.panes.MetricPaneFactory;
	import com.tibco.cep.ui.monitor.util.Util;
	
	import flash.events.NetStatusEvent;
	import flash.net.SharedObject;
	import flash.net.SharedObjectFlushStatus;
	import flash.utils.Dictionary;
	
	
	public class PromotedPaneRepository{
		
		public static const SO_NAME:String = "BE_EMM";	//TODO: Rename this to BE_MM?
		public static const MIN_FLUSH_SIZE:int = 10000;
		
		private var _panes:Dictionary;
		private var _sharedObj:SharedObject;
		
		public static var _instance:PromotedPaneRepository;
		
		public function PromotedPaneRepository(){
			_sharedObj = SharedObject.getLocal(SO_NAME);
			loadPanesFromSO();
		}
		
		public static function get instance():PromotedPaneRepository{
			if(_instance == null) _instance = new PromotedPaneRepository();
			return _instance;
		}
		
		/**
		 * Returns an array of MetricPanes constructed from the stored object information. This
		 * function sets MetricPane type (via factory), monitoredNod, and ancestoryPath.
		 */
		public function get panes():Array{
			var returnVal:Array = new Array();
			try{
				for each(var paneObj:Object in _panes){
					if(paneObj == null) continue;
					if(paneObj.type == undefined){
						trace("PromotedPaneRepository.panes - Undefined pane type!");
						continue;
					}
					var pane:MetricPane = MetricPaneFactory.instance.buildMetricPane(paneObj.type as String);
					pane.monitoredNode = XML(paneObj.monitoredNode);
					pane.ancestryPath = String(paneObj.ancestryPath);
					returnVal.push(pane);
				}
			}
			catch(error:Error){
				trace("ERROR: PromotedPaneRepository.panes():\n\t" + error.message);
				returnVal = new Array();
			}
			return returnVal;
		}
		
		public function contains(pane:MetricPane):Boolean{
			return !(_panes[pane.paneId] == undefined || _panes[pane.paneId] == null);
		}
		
		public function addPane(pane:MetricPane):Boolean{
			if(this.contains(pane)){
				Util.infoMessage("Pane already exists in cluster overview.");
				return false;
			}
			var paneData:Object = new Object();
			paneData =
				{
					id:pane.paneId,
					type:pane.type,
					monitoredNode:pane.monitoredNode.toXMLString(),
					ancestryPath:pane.ancestryPath
				};
			_panes[pane.paneId] = paneData;
			Util.infoMessage("Pane successfully added to cluster overview.");
			updateSharedObject();
			return true;
		}
		
		public function removePane(pane:MetricPane):void{
			_panes[pane.paneId] = null;
			delete _sharedObj.data[pane.paneId];
			updateSharedObject();
		}
		
		public function loadPanesFromSO():void{
			_panes = new Dictionary(true);
			for each(var pane:Object in _sharedObj.data.panes){
				if(pane == null) continue;
				/* For debugging...
				var id:String = String(pane.id);
				var node:String = String(pane.monitoredNode);
				var type:String = String(pane.type);
				var ancestry:String = String(pane.ancestryPath);
				//*/
				_panes[String(pane.id)] = {
					id:String(pane.id),
					type:String(pane.type),
					monitoredNode:String(pane.monitoredNode),
					ancestryPath:String(pane.ancestryPath)
				};
			}
		}
		
		/**
		 * Stores panes in the repository to a shared object to be restored on next use of
		 * the UI.
		 */
		public function updateSharedObject():void{
			_sharedObj.data.panes = _panes;
			var flushStatus:String = null;
			try{
				flushStatus = _sharedObj.flush(MIN_FLUSH_SIZE);
			}
			catch(error:Error){
				Util.errorMessage("Could not write SharedObject to disk");
			}
			if(flushStatus != null){
				switch(flushStatus){
					case SharedObjectFlushStatus.PENDING:
						_sharedObj.addEventListener(NetStatusEvent.NET_STATUS, onFlushApproved);
						break;
					case SharedObjectFlushStatus.FLUSHED:
						//all good...
						break;
				}
			}
		}
		
		/**
		 * If the flush requires more space than MIN_FLUSH_SIZE, user is promted to increase.
		 * This callback handles the response to that prompt
		 */
		private function onFlushApproved(event:NetStatusEvent):void{
			switch(event.info.code){
				case "SharedObject.Flush.Success":
					//all good...
					break;
				case "SharedObject.Flush.Failed":
					Util.infoMessage("Denying SharedObject space increase will prevent restoration of UI preferences!");
					break;
			}
			_sharedObj.removeEventListener(NetStatusEvent.NET_STATUS, onFlushApproved);
		}
		
	}
	
	
}