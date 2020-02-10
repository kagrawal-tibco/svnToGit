package com.tibco.cep.ui.monitor.events{
	
	import flash.events.Event;

	public class PanelReadyEvent extends Event{
		
		public function PanelReadyEvent(bubbles:Boolean=false, cancelable:Boolean=false){
			super(EventTypes.PANEL_READY, bubbles, cancelable);
		}
		
	}
}