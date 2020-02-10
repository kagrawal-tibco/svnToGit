package com.tibco.be.views.user.components.drilldown.querymanager.events
{
	import flash.events.Event;

	public class ConditionUpdatedEvent extends Event
	{
		public var label:String;
		public static const IS_HEADER_CHANGED:String = "isHeaderChanged";
		public function ConditionUpdatedEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false,changedLabel:String=null)
		{
			super(type, bubbles, cancelable);
			this.label = changedLabel;
		}
		
		override public function clone():Event {
			return new ConditionUpdatedEvent(type,bubbles,cancelable,label);
		}
	}
}