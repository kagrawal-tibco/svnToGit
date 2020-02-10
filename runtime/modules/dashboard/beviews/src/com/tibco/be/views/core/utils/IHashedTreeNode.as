package com.tibco.be.views.core.utils{
	import flash.utils.Dictionary;
	
	
	public interface IHashedTreeNode{
		function get id():String;
		function get parent():IHashedTreeNode;
		function get children():Dictionary;
		function get payload():Object;
		
		function set id(value:String):void;
		function set parent(value:IHashedTreeNode):void;
		function set children(value:Dictionary):void;
		function set payload(value:Object):void;
		
		function addChild(child:IHashedTreeNode):void;
		function removeChild(child:IHashedTreeNode):IHashedTreeNode;
		function getChild(childId:String):IHashedTreeNode;
	}
}