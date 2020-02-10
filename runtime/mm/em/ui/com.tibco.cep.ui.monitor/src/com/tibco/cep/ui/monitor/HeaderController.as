package com.tibco.cep.ui.monitor {
	import com.tibco.cep.ui.monitor.io.PSVRClient;
	import com.tibco.cep.ui.monitor.util.Util;
	
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.MouseEvent;
	
	import mx.managers.CursorManager;
	
	internal class HeaderController extends EventDispatcher implements IUpdateable {

		private var _view:Header;
				
		function HeaderController(view:Header) {
			_view = view;
		}
		
		internal function init():void {
			_view.username = PSVRClient.instance.username;
			_view.signOutBtn.addEventListener(MouseEvent.CLICK,handleSignOutBtnClick);
		} 
		
		private function handleSignOutBtnClick(event:MouseEvent):void {
			CursorManager.setBusyCursor();
			PSVRClient.instance.logout(this);
		}
		
		public function update(operation:String,data:XML):void{
			if(operation == "logout"){
				dispatchEvent(new Event(Event.CLOSE));
			}
		}
		
		public function updateFailure(operation:String,message:String, code:uint):void {
			Util.errorMessage("Could not log out "+_view.username);
			dispatchEvent(new Event(Event.CLOSE));
		}		

	}
}