package com.tibco.be.views.core.utils{
	import flash.utils.Dictionary;
	
	
	public class HashedTreeNode implements IHashedTreeNode{
		
		protected var _id:String;
		protected var _parent:IHashedTreeNode;
		protected var _children:Dictionary;
		protected var _payload:Object;
		
		public function HashedTreeNode(id:String=null, parent:IHashedTreeNode=null, children:Dictionary=null){
			_id = id;
			_parent = parent;
			_children = children == null ? new Dictionary():children;	
			_payload = null;
		}

		public function get id():String{ return _id; }
		public function get parent():IHashedTreeNode{ return _parent; }
		public function get children():Dictionary{ return _children; }
		public function get payload():Object{ return _payload; }
		
		public function set id(value:String):void{ _id = value; }
		public function set parent(value:IHashedTreeNode):void{ _parent = value; }
		public function set children(value:Dictionary):void{ _children = value; }
		public function set payload(value:Object):void{ _payload = value; }
		
		public function addChild(child:IHashedTreeNode):void{
			_children[child.id] = child;
		}
		
		public function removeChild(child:IHashedTreeNode):IHashedTreeNode{
			if(child != null && _children[child.id] != undefined){
				delete _children[child.id];
				return child;
			}
			return null;
		}
		
		public function getChild(childId:String):IHashedTreeNode{
			if(_children[childId] != undefined){
				return _children[childId] as IHashedTreeNode;
			}
			return null;
		}
		
	}
}