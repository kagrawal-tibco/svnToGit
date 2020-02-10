package com.tibco.be.views.user.dashboard.events{ 
	
	import flash.events.Event;

	public class BEVPanelEvent extends Event {
		
		public static const MAXIMIZE:String = "maximize";
		
		public static const MINIMIZE:String = "minimize";
		
		public static const CLOSE:String = "close";
		
		public static const RESTORE:String = "restore";
		
		public static const START_DRAG:String = "startdrag";
		
		public static const STOP_DRAG:String = "stopdrag";
		
		public function BEVPanelEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false) {
			super(type, bubbles, cancelable);
		}
		
	}
}