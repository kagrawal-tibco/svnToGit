package com.tibco.cep.ui.monitor{
	
	public interface IUpdateable {
		
		function update(operation:String,data:XML):void;
		
		function updateFailure(operation:String,message:String,code:uint):void;
	}
}