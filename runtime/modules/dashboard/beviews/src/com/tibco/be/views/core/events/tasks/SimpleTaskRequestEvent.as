package com.tibco.be.views.core.events.tasks{
	
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;

	public class SimpleTaskRequestEvent extends EventBusEvent{
		
		public function SimpleTaskRequestEvent(type:String=null, intendedRecipient:Object=null){
			if(type==null){ type=EventTypes.SIMPLE_TASK; }
			super(type, intendedRecipient);
		}
		
	}
}