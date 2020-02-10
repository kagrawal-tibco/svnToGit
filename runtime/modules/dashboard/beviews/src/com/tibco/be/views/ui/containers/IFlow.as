package com.tibco.be.views.ui.containers{
	import flash.display.DisplayObject;
	
	import mx.core.IContainer;
	
	
	public interface IFlow extends IContainer{
		function addChildRelative(refChild:DisplayObject, newChild:DisplayObject, offset:int):void;
		function moveChildRelative(refChild:DisplayObject, newChild:DisplayObject, offset:int):void;
		function getVisualPositionOf(component:DisplayObject):int;
		function setStyle(styleProp:String, newValue:*):void;
	}
}