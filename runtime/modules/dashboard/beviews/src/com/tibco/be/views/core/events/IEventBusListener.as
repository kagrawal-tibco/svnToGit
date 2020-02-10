package com.tibco.be.views.core.events{
	
	public interface IEventBusListener{
		/**
		 * Sets up all listeners.  Basically just a series of addEventListener calls for each event
		 * this listener will be listening for on the EventBus.
		 */
		function registerListeners():void;
		
		/**
		 * Determines whether or not the event received via the EventBus is intended for this
		 * object.  This provides the ability to go beyond an event's intended recipient by
		 * examining the data within the event.
		 */
		function isRecipient(event:EventBusEvent):Boolean;
		
		/* for copy paste...
		//IEventBusListener
		public function registerListeners():void{
			
		}
		public function isRecipient(event:EventBusEvent):Boolean{
			
		}
		*/
	}
}