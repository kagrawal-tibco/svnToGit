package com.tibco.be.views.core.utils{
	import flash.utils.Dictionary;
	
	
	public interface IHashedTree{
		function get roots():Dictionary;
		function contains(node:IHashedTreeNode):Boolean;
		function insert(node:IHashedTreeNode):Boolean;
		function remove(node:IHashedTreeNode):Array;
		function traceAncestory(node:IHashedTreeNode):Array;
		function getNodeById(id:String):IHashedTreeNode;
		function traverseAndDo(startingNode:IHashedTreeNode, action:Function, traversalOrder:String="preorder", args:Array=null):void;
	}
}