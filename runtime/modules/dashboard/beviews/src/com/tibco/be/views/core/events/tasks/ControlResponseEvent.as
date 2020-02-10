package com.tibco.be.views.core.events.tasks{
	
	import com.tibco.be.views.core.events.EventTypes;
	
	public class ControlResponseEvent extends ServerResponseEvent{
		
		public function ControlResponseEvent(requestEvent:ControlRequestEvent, intendedRecipient:Object=null){
			super(requestEvent, EventTypes.CONTROL_COMMAND_RESPONSE, intendedRecipient);
		}
		
		override public function get logMessage():String{
			return "ControlResponseEvent: " + _request.command;
		}
		
		override protected function get details():String{
			return "command="+command;
		}
		
	}
}