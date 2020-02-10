package com.tibco.be.views.core.events.lc{
	
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.tasks.ServerRequestEvent;
	import com.tibco.be.views.core.tasks.ServerCommandProcessor;
	
	import mx.utils.UIDUtil;
	
	public class LocalConnectionRequest extends ServerRequestEvent{
		
		/** Command used by the DrillDown's tree table widget to fetch data */
		public static const GET_DRILLDOWN_DATA:String = "getdata";
		
		/** Command to fetch the DrillDown page layout */
		public static const GET_LAYOUT_COMMAND:String = "getlayout";
		
		private static const LC_REQUEST_MESSAGE_TEMPLATE:String = "<LocalConnectionRequest><id>##id##</id><eventBusType>##eventBusType##</eventBusType><command>##command##</command></LocalConnectionRequest>";
		
		
		/**
		 * ID used to identify this request in the LCService request registry
		*/
		private var _id:String;
		
		/**
		 * The type of event that needs to be created by the receiver of this LCRequest
		*/
		private var _eventBusType:String;
		
		public function LocalConnectionRequest(command:String, intendedRecipient:Object, eventBusType:String=null, lcRequestXML:XML=null){
			super(command, EventTypes.LC_COMMAND, intendedRecipient);
			if(lcRequestXML != null){
				_id = new String(lcRequestXML.id);
				_eventBusType = new String(lcRequestXML.eventBusType);
				this.command = new String(lcRequestXML.command);
				for each(var paramXML:XML in lcRequestXML.request.parameter){
					addXMLParameter(new String(paramXML.@name), new String(paramXML));
				}
			}
			else{
				_id = UIDUtil.getUID(this);
				_eventBusType = eventBusType;
			}
		}
		
		public function get id():String{ return _id; }
		public function get eventBusType():String{ return _eventBusType; }
		
		public function toLocalConnectionMessage():XML{
			var messageStr:String = new String(LC_REQUEST_MESSAGE_TEMPLATE).replace("##id##", _id).replace("##eventBusType##", _eventBusType).replace("##command##", command);
			var xml:XML = new XML(messageStr);
			xml.appendChild(ServerCommandProcessor.getRequestXMLString(_xmlParams));
			return xml;
		}
		
		public static function createFromLocalConnectionMessage(message:XML):LocalConnectionRequest{
			return new LocalConnectionRequest("", "");
		}

	}
}