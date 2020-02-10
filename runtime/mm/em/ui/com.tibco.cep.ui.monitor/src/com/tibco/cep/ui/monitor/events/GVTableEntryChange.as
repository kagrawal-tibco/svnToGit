package com.tibco.cep.ui.monitor.events{
	
	import flash.events.Event;

	public class GVTableEntryChange extends Event{
		
		public function GVTableEntryChange(bubbles:Boolean=false, cancelable:Boolean=false){
			super(EventTypes.GLOBAL_VARIABLE_TABLE_ENTRY_CHANGE, bubbles, cancelable);
		}
		
	}
}