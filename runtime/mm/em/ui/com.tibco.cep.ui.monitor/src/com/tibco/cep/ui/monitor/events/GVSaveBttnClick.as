package com.tibco.cep.ui.monitor.events{
	
	import flash.events.Event;

	public class GVSaveBttnClick extends Event{
		
		public function GVSaveBttnClick(bubbles:Boolean=false, cancelable:Boolean=false){
			super(EventTypes.GLOBAL_VARIABLE_SAVE_BTTN_CLICK, bubbles, cancelable);
		}
		
	}
}