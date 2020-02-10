package com.tibco.be.views.core.utils{
	
	import flash.events.EventDispatcher;
	import flash.utils.Dictionary;
	
	public class HashedTree extends EventDispatcher implements IHashedTree{
		
		protected var _roots:Dictionary;
		protected var _nodeMap:Dictionary;
		
		public function HashedTree(){
			_roots = new Dictionary();
			_nodeMap = new Dictionary();
		}
		
		/*For debugging
		public function get nodeMap():Dictionary{ return _nodeMap; }
		//*/

		public function get roots():Dictionary{ return _roots; }
		
		public function getNodeById(id:String):IHashedTreeNode{
			return _nodeMap[id] as IHashedTreeNode;
		}
		
		public function contains(node:IHashedTreeNode):Boolean{
			return _nodeMap[node] == undefined;
		}
		
		public function insert(node:IHashedTreeNode):Boolean{
			if(node == null){ return false; }
			try{
				//TODO: Potential problem with PMConfigProvider.addNodesAndEdges. May not need to 
				//recursively add nodes to the map.
				addNodeToMap(node);
				if(node.parent == null){
					_roots[node.id] = node;
				}
				else{
					(_nodeMap[node.parent.id] as IHashedTreeNode).addChild(node);
				}
				return true;
			}
			catch(error:Error){
				//log?
			}
			return false;
		}
		
		protected function addNodeToMap(startingNode:IHashedTreeNode):void{
			_nodeMap[startingNode.id] = startingNode;
			for each(var child:IHashedTreeNode in startingNode.children){
				addNodeToMap(child);
			}
		}
		
		public function remove(node:IHashedTreeNode):Array{
			try{
				if(node.parent != null){ delete node.parent.children[node.id]; }
				var removedIds:Array = new Array();
				clearMap(node, removedIds);
				return removedIds;
			}
			catch(error:Error){
				//log?
			}
			return null;
		}
		
		protected function clearMap(startingNode:IHashedTreeNode, removedIds:Array=null):void{
			delete _nodeMap[startingNode.id];
			if(removedIds != null){ removedIds.push(startingNode.id); }
			for each(var child:IHashedTreeNode in startingNode.children){
				clearMap(child, removedIds);
			}
		}
		
		public function traceAncestory(node:IHashedTreeNode):Array{
			var ancestory:Array = new Array();
			var ancestor:IHashedTreeNode = node.parent;
			while(ancestor != null){
				ancestory.push(ancestor);
				ancestor = ancestor.parent;
			}
			return ancestory;
		}
		
		public function traverseAndDo(startingNode:IHashedTreeNode, action:Function, traversalOrder:String="preorder", args:Array=null):void{
			var key:String;
			if(traversalOrder == "preorder"){
				action(startingNode, args);
				for(key in startingNode.children){
					traverseAndDo(startingNode.children[key] as IHashedTreeNode, action, traversalOrder, args);
				}
			}
			else if(traversalOrder == "postorder"){
				for(key in startingNode.children){
					traverseAndDo(startingNode.children[key] as IHashedTreeNode, action, traversalOrder, args);
				}
				action(startingNode, args);
			}
		}
		
	}
}