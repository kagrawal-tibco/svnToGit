package com.tibco.be.views.user.components.drilldown.tabletree.events{
	
	import com.tibco.be.views.user.components.drilldown.tabletree.view.TableTreeRow;
	
	import flash.events.Event;

	public class RowExpansionEvent extends Event{
		
		public static const EXPANSION_CHANGE:String = "rowExpansionChange";
		
		private var _targetRow:TableTreeRow;
		
		public function RowExpansionEvent(targetRow:TableTreeRow, bubbles:Boolean=false, cancelable:Boolean=false){
			super(EXPANSION_CHANGE, bubbles, cancelable);
			_targetRow = targetRow;
		}
		
		public function get targetRow():TableTreeRow{ return _targetRow; }
		
	}
}