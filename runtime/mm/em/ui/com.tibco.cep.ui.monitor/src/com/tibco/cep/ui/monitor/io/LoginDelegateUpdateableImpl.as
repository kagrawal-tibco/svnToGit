package com.tibco.cep.ui.monitor.io {
	
	import com.tibco.cep.ui.monitor.IUpdateable;
	
	
	public class LoginDelegateUpdateableImpl extends DelegateUpdateableImpl {
		
		public function LoginDelegateUpdateableImpl(caller:IUpdateable,callBack:IUpdateable) {
			super(caller,callBack);
		}		
		
		
		public override function update(operation:String, data:XML):void{
			if (data.children().length() == 0){
				updateFailure(operation,"Unknown Error",0);
			}
			else {
				super.update(operation,data);	
			}
		}
	}
}