package com.tibco.be.views.ui{
	
	//TODO:
	//1) Rename functions to be more descriptive of what they do/handle
	
	public interface IDraggableWrapper{
		/**
		 * The wrapped object.
		*/
		function get content():Object;
		
		/**
		 * When the object is dropped this is performed.
		*/
		function handleMouseDrop():void;
		
		/**
		 * Provides a mechanism by which the drop destination can make a callback to the
		 * DraggableWrapper object on its own terms (i.e. after asynch call).
		 * 
		 * @param success Flag indicating success or failure of the drop operation.
		*/
		function handleDropComplete(success:Boolean):void;
		
	}
}

/* For copy/paste purposes:
	public function get content():Object{
	
	}
	
	public function handleMouseDrop():void{
	
	}
	
	public function handleDropComplete(success:Boolean):void{
	
	}
*/
