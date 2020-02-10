package com.tibco.be.views.core.events.tasks{
	
	import com.tibco.be.views.core.events.EventTypes;
	
	public class ConfigResponseEvent extends ServerResponseEvent{
		
		public function ConfigResponseEvent(requestEvent:ConfigRequestEvent, intendedRecipient:Object=null){
			super(requestEvent, EventTypes.CONFIG_COMMAND_RESPONSE, intendedRecipient);
		}
		
		override public function get logMessage():String{
			return "ConfigResponseEvent: " + _request.command;
		}
		
	}
}