package com.tibco.be.views.user.components.drilldown.tabletree.events{
	
	import flash.events.Event;

	public class TableSortEvent extends Event{
		
		public static const SORT_APPLIED:String = "ddTableSortApplied";
		
		private var _sortField:String;
		private var _sortDirection:String;
		
		public function TableSortEvent(sortField:String, sortDirection:String="", bubbles:Boolean=false, cancelable:Boolean=false){
			super(SORT_APPLIED, bubbles, cancelable);
			_sortField = sortField;
			_sortDirection = sortDirection;
		}
		
		public function get sortField():String{ return _sortField; }
		public function get sortDirection():String{ return _sortDirection; }
		
	}
}