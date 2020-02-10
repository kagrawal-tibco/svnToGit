package com.tibco.be.views.core.io{
	
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.IEventBusListener;
	import com.tibco.be.views.core.events.io.StreamDataRequestEvent;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	
	import flash.events.Event;
	import flash.events.IOErrorEvent;
	import flash.events.SecurityErrorEvent;

	public class StreamingClient implements IIOHandler, IEventBusListener{
		
		/** The name of the streaming channel */ 
		protected var _channelName:String;
		
		public function StreamingClient(){}
		
		/** Returns the channel name used */ 
		public function get channelName():String{ return _channelName; }
		
		/** Start the StreamingClient IOService */
		public function start():void{}
		
		/** Stop the StreamingClient IOService */
		public function stop():void{}

		/** Resets the streaming client */ 
		public function reset():void{}
		
		public function handleConnect(event:Event):void{}
		
		public function handleData(event:Event):void{}
		
		public function handleIOError(event:IOErrorEvent):void{}
		
		public function handleSecurityErrror(event:SecurityErrorEvent):void{}
		
		public function handleClose(event:Event):void{}

		public function registerListeners():void{/*virtual*/}
		
		public function isRecipient(event:EventBusEvent):Boolean{
			return (event as StreamDataRequestEvent).channelName == channelName;
		}
		
		protected function log(severity:uint, message:String):void{
			EventBus.instance.dispatchEvent(
				new DefaultLogEvent(severity, message)
			);
		}
		
	}
}