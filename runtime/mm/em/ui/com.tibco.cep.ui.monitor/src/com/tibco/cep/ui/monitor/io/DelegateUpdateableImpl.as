package com.tibco.cep.ui.monitor.io {
	
	import com.tibco.cep.ui.monitor.IUpdateable;
	
	import flash.utils.Dictionary;
	
	public class DelegateUpdateableImpl implements IUpdateable {
		
		protected var _caller:IUpdateable;
		protected var _callBack:IUpdateable;
		
		protected var _additionalStateVariables:Dictionary;
		
		public function DelegateUpdateableImpl(caller:IUpdateable,callBack:IUpdateable) {
			_caller = caller;
			_callBack = callBack;
			_additionalStateVariables = new Dictionary(true);
		}

		public function update(operation:String,data:XML):void {
			if (data.children().length() > 0 && (data.child("errorcode").length() != 0)) {
				if(_callBack != null)_callBack.updateFailure(operation,data.errormessage,0);
				if(_caller != null)_caller.updateFailure(operation,data.errormessage,0);
			}
			else {
				if(_callBack != null)_callBack.update(operation,data);
				if(_caller != null)_caller.update(operation,data);
			}
		}
		
		public function updateFailure(operation:String,message:String, code:uint):void {
			trace("DelegateUpdateableImpl.updateFailure()\n\tOperation: " + operation + "\n\tMessage: " + message + "\n\n");
			if(_callBack != null)_callBack.updateFailure(operation,message,0);
			if(_caller != null)_caller.updateFailure(operation,message,0);
		}
		
		public function addStateVariable(name:String,value:Object):void {
			_additionalStateVariables[name] = value;
		}
		
		public function removeStateVariable(name:String,value:Object):void {
			delete _additionalStateVariables[name];
		}
		
		public function getStateVariable(name:String):Object {
			return _additionalStateVariables[name];
		}
		
	}
}