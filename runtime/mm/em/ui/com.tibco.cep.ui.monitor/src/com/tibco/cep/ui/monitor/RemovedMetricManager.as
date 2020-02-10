package com.tibco.cep.ui.monitor
{
	import com.tibco.cep.ui.monitor.panes.MetricPane;
	import com.tibco.cep.ui.monitor.util.Logger;
	import com.tibco.cep.ui.monitor.util.Util;
	
	import flash.events.NetStatusEvent;
	import flash.net.SharedObject;
	import flash.net.SharedObjectFlushStatus;
	import flash.utils.Dictionary;
	
	/** It is a singleton class */
	public class RemovedMetricManager
	{
		public static const SO_NAME:String = "REM_METRICS";
		public static const MIN_FLUSH_SIZE:int = 10000;
				
		private static var _instance:RemovedMetricManager;
		private var _sharedObj:SharedObject;
		private var _panes:Dictionary;
		
		public function RemovedMetricManager(){
			_sharedObj = SharedObject.getLocal(SO_NAME);
			//clearSharedObj();
			loadPanesFromSO();
		}
		
		public static function get instance():RemovedMetricManager{
			if(_instance == null) _instance = new RemovedMetricManager();
			return _instance;
		}
		
		public function loadPanesFromSO():void{
			_panes = new Dictionary(true);
			for each(var pane:Object in _sharedObj.data.panes){
				if(pane == null) continue;
				_panes[String(pane.soID)] = {
					soID:String(pane.soID),
					id:String(pane.id),
					type:String(pane.type),
					label:String(pane.label),
					monitoredNode:XML(pane.monitoredNode)
				};
			}
		}		
		
		/**
		 * Returns the items that start with the give page ID 
		 * @param pageID
		 */		
		public function getPanesOfPage(page:String):Array{
			var tempPages:Array = new Array();
			var rPattern:RegExp = new RegExp(" ", "g");
			page = page.toLowerCase();	
			page = page.replace(rPattern, "_");		
			
			for each(var pane:Object in _sharedObj.data.panes){
				if(pane == null) continue;
				
				var paneSOID:String = pane.soID;
				if(paneSOID.indexOf(page) == 0){
					tempPages.push(pane);
				}
			}			
			
			return tempPages;
		}//end of getPanesOfPage
		
		private function contains(id:String):Boolean{
			return !(_panes[id] == undefined || _panes[id] == null);
		}		
		
		public function hasPane(pageID:String, paneType:String):Boolean{
			var rPattern:RegExp = new RegExp(" ", "g");
			var page:String = pageID + "~~" + paneType;
			page = page.toLowerCase();
			page = page.replace(rPattern, "_");			
			var retVal:Boolean = contains(page); 
			trace(retVal);			
			return retVal;
		}
		
		public function addPane(page:String, pane:MetricPane):Boolean{
			var rPattern:RegExp = new RegExp(" ", "g");
			page = page.toLowerCase();
			page = page.replace(rPattern, "_");
			var soID:String = page + "~~" + pane.type;
			
			if(this.contains(soID)){
				//Util.infoMessage("Pane already exists in cluster overview.");
				return false;
			}
			var paneData:Object = new Object();
			paneData =
				{
					soID:soID,
					id:pane.paneId,
					type:pane.type,
					label:pane.metricLabel,
					monitoredNode:pane.monitoredNode
				};
				
			_panes[soID] = paneData;
			Util.infoMessage("Pane removed from the page and successfully added to the gallery.");
			updateSharedObject();
			return true;
		}
		
		/**
		 * Removes the metric pane from the list 
		 * @param page
		 * @param paneType
		 */		
		public function removePane(page:String, paneType:String):void{
			var rPattern:RegExp = new RegExp(" ", "g");
			page = page.toLowerCase();
			page = page.replace(rPattern, "_");			
			var soID:String = page + "~~" + paneType;
			_panes[soID] = null;
			delete _sharedObj.data[soID];
			updateSharedObject();
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
						Logger.logWarning(this, "Shared Object Flush Pending...");
						_sharedObj.addEventListener(NetStatusEvent.NET_STATUS, onFlushApproved);
						break;
					case SharedObjectFlushStatus.FLUSHED:
						//all good...
						break;
				}
			}
		}
		
		/**
		 * If the flush requires more space than MIN_FLUSH_SIZE, user is prompted to increase.
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
		
		public function clearSharedObj():void{
			_sharedObj.clear();
		}

	}
}