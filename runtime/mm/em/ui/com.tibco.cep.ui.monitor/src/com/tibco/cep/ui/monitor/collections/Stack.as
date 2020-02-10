package com.tibco.cep.ui.monitor.collections{
	
	import mx.collections.ArrayCollection;
	
	public class Stack{
		
		private var stack:ArrayCollection;
		private var capacity:int;
		
		public function Stack(source:Array=null, capacity:int=int.MAX_VALUE){
			this.capacity = Math.abs(capacity);
			if(source == null) source = new Array();
			source = source.reverse(); // head of stack and end of array
			if(this.capacity < source.length){
				source.splice(0,source.length-this.capacity); // trim last entries
			}
			this.stack = new ArrayCollection(source);
		}
		
		public function push(value:Object):Boolean{
			if(value == null) return false;
			stack.addItem(value);
			return true;
		}
		
		public function peek():Object{
			if(stack.length == 0) return null;
			return stack.getItemAt(stack.length-1);
		}
		
		/**
		 * Peek at a specific index in the stack. The top of the stack has an index value of zero
		 * @param index The index of the item to peek at.
		 */
		public function peekAt(index:int):Object{
			index = stack.length - index - 1;
			if(index < 0) return null;
			return stack.getItemAt(index);
		}
		
		public function peekRandom():Object{
			var i:int = Math.floor(Math.random()*stack.length);
			if(i >= stack.length) return null;
			return stack.getItemAt(i);
		}
		
		public function peekRoot():Object{
			if(stack.length == 0) return null;
			return stack.getItemAt(0);
		}
		
		public function pop():Object{
			if(stack.length == 0) return null;
			return stack.removeItemAt(stack.length-1);
		}
		
		public function popRandom():Object{
			var toRemove:Number = Math.floor(Math.random()*stack.length);
			if(toRemove >= stack.length) return null;
			return stack.getItemAt(toRemove);
		}
		
		public function get length():Number{ return stack.length; }

	}
}