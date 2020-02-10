package com.tibco.be.views.core.utils{
	
	import mx.collections.ArrayCollection;
	
	public class SortedNumericList{
		
		private var _list:ArrayCollection;
		
		//for debugging
//		private var depth:int;
		
		public function SortedNumericList(){
			_list = new ArrayCollection();
		}
		
		public function get length():int{ return _list.length; }
		
		public function get maxValue():Number{
			if(_list.length == 0){ return NaN; }
			return _list.getItemAt(_list.length-1) as Number;
		}
		
		public function get minValue():Number{
			if(_list.length == 0){ return NaN; }
			return _list.getItemAt(0) as Number;
		}
		
		public function getNthRankedValue(n:int):Number{
			if(n < 0 || n >= _list.length){ return NaN; }
			return _list.getItemAt(n) as Number;
		}
		
		public function add(value:Number):void{
			if(_list.length == 0){
				_list.addItem(value);
				return;
			}
//			depth = 0;
			var i:int = searchForPosition(value, 0, _list.length);
			_list.addItemAt(value, i);
			//trace(value.toString() + " in " + depth.toString() + ".");
		}
		
		public function update(oldValue:Number, newValue:Number):void{
			if(oldValue != newValue && remove(oldValue)){
				add(newValue);
			}
		}
		
		public function remove(value:Number):Boolean{
			if(_list.length == 0){ return false; }
//			depth = 0;
			var i:int = searchForValue(value, 0, _list.length-1);
			if(i >= 0){
//				trace(value.toString() + "|" + depth.toString() + "|" + _list.length.toString() + "\t\t" + _list.toString());
				_list.removeItemAt(i);
				return true;
			}
//			trace(value.toString() + " removed failed.");
			return false;
		}
		
		public function clear():void{
			_list.removeAll();
		}
		
		private function searchForValue(value:Number, startIndex:int, endIndex:int):int{
			if(startIndex > endIndex){ return -1; }
//			depth++;
			var currentIndex:int = (startIndex + endIndex)/2;
			if(value < _list[currentIndex]){
				return searchForValue(value, startIndex, currentIndex-1);
			}
			else if(value > _list[currentIndex]){
				return searchForValue(value, currentIndex+1, endIndex);
			}
			else{
				return currentIndex;
			}
		}
		
		private function searchForPosition(value:Number, startIndex:int, endIndex:int):int{
			if(startIndex >= endIndex){ return startIndex; }
//			depth++;
			var currentIndex:int = (startIndex + endIndex)/2;
			if(value < _list[currentIndex]){
				if(currentIndex-1 >= 0 && value >= _list[currentIndex-1]){
					return currentIndex;
				}
				return searchForPosition(value, startIndex, currentIndex-1);
			}
			else if(value > _list[currentIndex]){
				if(currentIndex+1 < _list.length && value <= _list[currentIndex+1]){
					return currentIndex+1;
				}
				return searchForPosition(value, currentIndex+1, endIndex);
			}
			else{
				return currentIndex;
			}
		}

	}
}