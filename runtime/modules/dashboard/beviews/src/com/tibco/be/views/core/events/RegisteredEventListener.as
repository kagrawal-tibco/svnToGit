package com.tibco.be.views.core.events{
	
	public class RegisteredEventListener{

		private var _type:String;		
		private var _callback:Function;
		private var _intendedRecipient:Object;
		private var _priority:int;
		
		
		public function RegisteredEventListener(type:String, callback:Function, intendedRecipient:Object=null, priority:int=0){
			_type = type;
			_callback = callback;
			_intendedRecipient = intendedRecipient;
			_priority = priority;
		}
		
		/**
		 * The event type this listener is listening for
		 */
		public function get type():String{ return _type; }
		
		/**
		 * The function to call when the event is received.
		 */
		public function get callback():Function{ return _callback; }
		
		/**
		 * A pointer to the object using this listener and expecting events of type _type.
		 */
		public function get intendedRecipient():Object{ return _intendedRecipient; }
		
		/**
		 * Currently not used.
		 */
		public function get priority():int{ return _priority; }
		
		public function isListeningFor(event:EventBusEvent):Boolean{
			//is listening for if...
			return (
				_intendedRecipient == null ||  //the listener has subscribed to all events
				event.intendedRecipient == null ||  //the event is a broadcast event
				event.intendedRecipient == _intendedRecipient  //the listener is listening for the specific event
			);
		}

	}
}