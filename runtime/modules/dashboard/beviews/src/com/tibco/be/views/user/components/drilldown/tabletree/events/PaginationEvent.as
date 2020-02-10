package com.tibco.be.views.user.components.drilldown.tabletree.events{
	
	import flash.events.Event;

	public class PaginationEvent extends Event{
		
		public static const PAGE_CHANGE:String = "ddTablePageChange";
		
		private var _rowStartIndex:int;
		
		public function PaginationEvent(rowStartIndex:int, bubbles:Boolean=false, cancelable:Boolean=false){
			super(PAGE_CHANGE, bubbles, cancelable);
			_rowStartIndex = rowStartIndex;
		}
		
		public function get rowStartIndex():int{ return _rowStartIndex; }
		
	}
}