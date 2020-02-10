package com.tibco.be.views.user.components.drilldown.tabletree.events{
	
	import com.tibco.be.views.user.components.drilldown.tabletree.view.TypeHeaderRow;
	
	import flash.events.Event;

	public class GroupByEvent extends Event{
		
		public static const GROUPBY_FIELD_CHANGE:String = "groupbyFieldChange";
		
		private var _targetRow:TypeHeaderRow;
		private var _groupByField:String;
		
		public function GroupByEvent(targetRow:TypeHeaderRow, groupByField:String, bubbles:Boolean=false, cancelable:Boolean=false){
			super(GROUPBY_FIELD_CHANGE, bubbles, cancelable);
			_targetRow = targetRow;
			_groupByField = groupByField;
		}
		
		public function get targetRow():TypeHeaderRow{ return _targetRow; }
		public function get groupByField():String{ return _groupByField; }
		
	}
}