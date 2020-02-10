package com.tibco.be.views.core.events{
	
	import com.tibco.be.views.core.BEVLocalConnectionManager;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	
	import flash.utils.Dictionary;
	
	/**
	 * The EventBus class, while not complicated in its implementation, provides an essential  
	 * service to all objects in the BEViews system.  All events dispatched in the system are done 
	 * so via this class.  Thus, by adding specific event listeners to events dispatched by this 
	 * singleton class, objects can effectively subscribe to any type of event generated by the
	 * system.
	 * 
	 * This class, as the name implies, acts as an event bus, on which system components can listen
	 * for events which they're expecting or publish events that arise during computation.
	 * Determiniation of whether or not an event picked off the bus is relevant is up to the
	 * individual component. However, the EventBus does do some work to ensure events destined for
	 * a particular object are only dispatced to that object.
	 * 
	 * The class is intended to be used as a singleton via the instance property.
	 */
	public class EventBus{
		
		private static var _instance:EventBus;
		
		/**
		 * A Dictionary indexed by event type that stores at each entry an array of objects
		 * listening for events of that type.
		 */
		private var _listeners:Dictionary;
		
		/**
		 * EventBus constructor. Use of this constructor should be avoided.  Instead, use the
		 * instance property to utilize this singleton class.
		 */
		function EventBus(){
			_listeners = new Dictionary();
		}
		
		/**
		 * The instance property provides access to the EventBus singleton class.
		 */
		public static function get instance():EventBus{
			if(_instance == null) _instance = new EventBus();
			return _instance;
		}
		
		/**
		 * The dispatchEvent function iterates through the Array of listening objects stored at
		 * event.type index of the listeners Dictionary and invokes the listener's callback function
		 * if appropriate. 
		 */
		public function dispatchEvent(event:EventBusEvent):void{
			var targetListeners:Array;
			try{
				if(_listeners[event.type] == undefined){
					//event has no already-registered listeners
					_listeners[event.type] = new Array();
				}
				targetListeners = _listeners[event.type] as Array;
				for each(var listener:RegisteredEventListener in targetListeners){
					if(listener.isListeningFor(event)){
						listener.callback.call(null, event);
					}
				}
				
//				if(event.intendedRecipient == null){
//					trace("Broadcast Event (" + targetListeners.length + " listeners)\n\t" + event.logMessage);
//				}
//				if(targetListeners.length == 0){
//					trace("WARNING: No one's listening for Event: " + event.type);
//				}
				BEVLocalConnectionManager.instance.sendEventData(event.eventData);
			}
			catch(error:Error){
				//infinite recursion risk...
				dispatchEvent(
					new DefaultLogEvent(
						DefaultLogEvent.CRITICAL,
						"ERROR: EventBus - Failed Event Dispatch - " + error.message + "\n" + error.getStackTrace()
					)
				);
			}
		}
		
		/**
		 * Registers a new event listener with the EventBus. All events of the provided type that
		 * are put on the bus will result in a call to the provided callback function (as long as
		 * the intendedRecipient allows for it).
		 */
		public function addEventListener(eventType:String, listener:Function, intendedRecipient:Object=null):void{
			 if(_listeners[eventType] == null || _listeners[eventType] == undefined){
			 	_listeners[eventType] = new Array();
			 }
			 try{
			 	(_listeners[eventType] as Array).push(new RegisteredEventListener(eventType, listener, intendedRecipient));
			 }
			 catch(error:Error){
			 	dispatchEvent(
			 		new DefaultLogEvent(
			 			DefaultLogEvent.CRITICAL,
			 			"ERROR: EventBus - Failed Add Event Listener - " + error.message
		 			)
		 		);
			 }
		}
		
		public function removeEventListener(eventType:String, listener:Function):void{
			if( _listeners[eventType] == undefined || _listeners[eventType] == null){ return; }
			try{
				var candidateListeners:Array = _listeners[eventType] as Array;
				for(var i:int = 0; i < candidateListeners.length; i++){
					var regListener:RegisteredEventListener = candidateListeners[i] as RegisteredEventListener;
					if( regListener.type == eventType && regListener.callback == listener){
						candidateListeners.splice(i,1);
						i--; //splice causes remaining array entries to have index-1
						//listener function is specific to the instance of the listening object,
						//so removes should only remove one registered listener even if there
						//are multiple, equivilent {<event_type>, <function_name>} entries.
					}
				}
				if(candidateListeners.length == 0){ delete(_listeners[eventType]); }
			}
			catch(error:Error){
				dispatchEvent(
					new DefaultLogEvent(
						DefaultLogEvent.CRITICAL,
						"ERROR: EventBus - Failed Remove Event Listener - " + error.message
					)
				);
			}
		}
		
		public function hasEventListener(eventType:String, listener:Function):Boolean{
			return _listeners[eventType] != undefined && (_listeners[eventType] as RegisteredEventListener).callback == listener;
		}
				
		public function reset():void{
			_instance = new EventBus();
		}
		
	}
}