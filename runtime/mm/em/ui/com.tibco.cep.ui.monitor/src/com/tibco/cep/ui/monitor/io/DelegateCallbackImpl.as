package com.tibco.cep.ui.monitor.io{
	
	import com.tibco.cep.ui.monitor.IUpdateable;
	import com.tibco.cep.ui.monitor.util.Logger;
	
	public class DelegateCallbackImpl implements IUpdateable{
		
		private var _callBack:Function;
		private var _updateable:IUpdateable;
		
		public function DelegateCallbackImpl(callBack:Function, updateable:IUpdateable){
			_callBack = callBack;
			_updateable = updateable;
		}
		
		public function update(operation:String,data:XML):void{
			Logger.logDebug(this,"Calling update in the delegate");
			if(_updateable != null) _updateable.update(operation, data);
			if(_callBack != null) _callBack.call(this);
		}
		
		public function updateFailure(operation:String,message:String,code:uint):void{
			trace(
				"DelegateCallbackImpl.updateFailure\n\t" + 
				"Operation: " + operation + "\n\t" +
				"Message: " + message + "\n\t" + 
				"Code: " + code
			);
			if(_updateable != null) _updateable.updateFailure(operation, message, code);
		}

	}
}