package com.tibco.be.views.core.events.lc{
	
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.tasks.ServerResponseEvent;
	
	public class LocalConnectionResponse extends ServerResponseEvent{
		
		private static const LC_XML_EVENT_TEMPLATE:String = "<LocalConnectionResponse><id>##id##</id><eventBusType>##eventBusType##</eventBusType><command>##command##</command></LocalConnectionResponse>";
		
		public function LocalConnectionResponse(lcRequest:LocalConnectionRequest){
			super(lcRequest, EventTypes.LC_COMMAND_RESPONSE, lcRequest.intendedRecipient);
		}
		
		public function get id():String{ return (_request as LocalConnectionRequest).id; }
		
		public function get eventBusType():String{
			switch((_request as LocalConnectionRequest).eventBusType){
				case(EventTypes.CONFIG_COMMAND): return EventTypes.CONFIG_COMMAND_RESPONSE;
				case(EventTypes.CONTROL_COMMAND): return EventTypes.CONTROL_COMMAND_RESPONSE;
				default: return "";
			}
		}
		
		public function toLocalConnectionMessage():XML{
			var messageStr:String = new String(LC_XML_EVENT_TEMPLATE).replace("##id##", id).replace("##eventBusType##", eventBusType).replace("##command##", command);
			var xml:XML = new XML(messageStr);
			if(data != null){
				xml.appendChild(new XML("<data>" + dataAsXML.toXMLString() + "</data>"));
			}
			if(failMessage != ""){
				xml.appendChild(new XML("<failmessage>"+failMessage+"</failmessage>"));
			}
			return xml;
		}

	}
}