package com.tibco.be.views.core.events{
	
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.logging.ILogable;
	
	import flash.events.Event;
	import flash.utils.getQualifiedClassName;

	public class EventBusEvent extends Event implements ILogable{
		
		/**
		 * Intended recipient is a reference to the object that is intended to receive this event.
		 * It basically provides a way for receivers of this event or the EventBus to determine
		 * whether a prticular IEventListener should receive/process the event. A null value for
		 * _intendedRecipient implies a broadcast event on the EventBus.
		 */
		protected var _intendedRecipient:Object;
		private var _broadcast:Boolean;
		
		public function EventBusEvent(type:String=null, intendedRecipient:Object=null){
			if(type==null) type = EventTypes.BEVIEWS; 
			_intendedRecipient = intendedRecipient;
			_broadcast = false;
			super(type);
		}
				
		/** Getter for the event's intended recipient (null if this is a broadcast event). */
		public function get intendedRecipient():Object{ return _broadcast ? null:_intendedRecipient; }
		
		/** Puts the event in broadcast mode by nullifying the intendedRecipient */
		public function set broadcast(value:Boolean):void{ _broadcast = value; }
		
		//ILogable
		public function get logSeverity():uint{ return DefaultLogEvent.INFO; }
		public function get logMessage():String{ return "EventBusEvent: " + type; }
		
		/**
		 * Extra information about the event. This field is part of the eventData property which is
		 * displayed by any LocalConnection client listening for system events.
		*/
		protected function get details():String{ return ""; }
		
		override public function clone():Event{
			return new EventBusEvent(type, _intendedRecipient);
		}
		
		public function get eventData():XML{
			var fullClassName:String = getQualifiedClassName(this);
			var parts:Array = fullClassName.split(".");
			var name:String = parts[parts.length-1] as String;
			return new XML(
				"<event name=\"" + name + "\">" +
					"<type>" + fullClassName + "</type>" + 
					"<intendedRecepient>" + (_intendedRecipient==null ? "":_intendedRecipient.toString()) + "</intendedRecepient>" +
					"<broadcast>" + _broadcast + "</broadcast>" +
					"<details>" + details + "</details>" +
				"</event>"
			);
		}
		
	}
}