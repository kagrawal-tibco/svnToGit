package com.tibco.cep.ui.monitor
{
	import com.tibco.cep.ui.monitor.util.Util;
	
	import flash.events.NetStatusEvent;
	import flash.net.SharedObject;
	import flash.net.SharedObjectFlushStatus;
	import flash.utils.Dictionary;
	
	public class SharedObjHandler
	{
		private var _sharedObj:SharedObject;
		
		private var _sharedObjName:String;
		private var _minFlushSize:int;
	
		public function SharedObjHandler(sharedObjName:String, minFlushSize:int=10000)
		{
			_sharedObjName = sharedObjName;
			_minFlushSize = minFlushSize;
			_sharedObj = SharedObject.getLocal(sharedObjName);
		}
		
		/** retrieves the data saved in the shared object
		 * @param: name of the shared object data property */
		public function getFromSharedObj(sharedObjPropName:String):Object{
			return _sharedObj.data[sharedObjPropName];
		}
		
		/** Updates the shared object with the specified data source. The data is stored in 
		 *  the specified shared object property name.
		 *  @param: name of the property to be added to the shared object 'data' property
		 *  @param: object containing the data to be saved in associative array format
		 * */
		public function updateSharedObject(sharedObjPropName:String, dataSrc:Object):void{ 
			_sharedObj.data[sharedObjPropName] = dataSrc;
			var flushStatus:String = null;
			try{
				flushStatus = _sharedObj.flush(_minFlushSize);
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
		 * If the flush requires more space than _minFlushSize, the user is promted to increase.
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
		
		//It might be handy for debugging or some special case
		public function get sharedObjName():String {return _sharedObjName;}		

	} //class
}	//object