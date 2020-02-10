package com.tibco.be.views.core.io{
	import flash.events.DataEvent;
	import flash.events.Event;
	import flash.events.IOErrorEvent;
	import flash.events.SecurityErrorEvent;
	
	/**
	 * Defines which methods are required to handle IO requests.
	 */
	public interface IIOHandler{
		function start():void;
		function stop():void;
		
		//Handlers for server events		function handleConnect(event:Event):void;		function handleData(event:Event):void;
		function handleIOError(event:IOErrorEvent):void;		function handleSecurityErrror(event:SecurityErrorEvent):void;		function handleClose(event:Event):void;
	}

}
/* For copy paste purposes...
		public function start():void{
		
		}
				
		public function handleConnect(event:Event):void{
		
		}
		
		public function handleData(event:DataEvent):void{
		
		}
		
		public function handleIOErrror(event:IOErrorEvent):void{
		
		}
		
		public function handleSecurityErrror(event:SecurityErrorEvent):void{
		
		}
		
		public function handleClose(event:Event):void{
			
		}
*/