package com.tibco.be.views.core.events.io{
	
	import com.tibco.be.views.core.events.EventTypes;
	
	import flash.events.Event;
	
	public class PullDataResponseEvent extends IOEvent{
		
		/**
		 * Flag indicating whether the corresponding pull request failed
		*/
		private var _failed:Boolean;
		
		/**
		 * The data result from the pull request.
		 */
		private var _data:Object;
		
		/**
		 * The corresponding pull request event that generated this response.
		 */
		private var _requestEvent:PullDataRequestEvent;
		
		public function PullDataResponseEvent(data:Object, requestEvent:PullDataRequestEvent=null){
			_data = data;
			_requestEvent = requestEvent;
			_failed = false;
			super(EventTypes.PULL_DATA_RESPONSE, requestEvent.intendedRecipient);
		}
		
		override protected function get details():String{
			return _data == null ? "":_data as String;
		}
		
		public function get data():Object{ return _data; }
		public function get requestEvent():PullDataRequestEvent{ return _requestEvent; }
		public function get failed():Boolean{ return _failed; }
		
		public function set data(value:Object):void{ _data = data; }
		public function set failed(value:Boolean):void{ _failed = value; }
		
		override public function clone():Event{
			var cloned:PullDataResponseEvent = new PullDataResponseEvent(_data, _requestEvent);
			cloned.failed = _failed;
			return cloned;
		}
	}
}