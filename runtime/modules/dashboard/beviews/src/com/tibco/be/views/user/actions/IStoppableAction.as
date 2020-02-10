package com.tibco.be.views.user.actions{

	import flash.events.Event;
	
	public interface IStoppableAction{
		
		function stop():void;
		function shouldStopOnEvent(event:Event):Boolean;
		
	}
}