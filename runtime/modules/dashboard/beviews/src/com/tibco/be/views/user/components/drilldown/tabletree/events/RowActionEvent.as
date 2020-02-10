package com.tibco.be.views.user.components.drilldown.tabletree.events{
	
	import com.tibco.be.views.user.components.drilldown.tabletree.view.TableTreeRow;
	
	import flash.events.Event;

	public class RowActionEvent extends Event{
		
		public static const ADD_ROW:String = "addRowActionEvent";
		public static const REMOVE_ROW:String = "removeRowActionEvent";
		public static const EXPAND_ROW:String = "expandRowActionEvent";
		
		protected var _actionRow:TableTreeRow;
		protected var _parentRow:TableTreeRow;
		protected var _offset:int;
		
		/**
		 * Creates a new RowActionEvent
		 * 
		 * @param type The type of RowActionEvent (add, remove, expand)
		 * @param actionRow The row to take action upon
		 * @param parentRow The parent row of the target row
		 * @param offset An offset value that can be used when positioning an added row or locating a row to remove
	     * @param bubbles Specifies whether the event can bubble up the display list hierarchy.
	     * @param cancelable Specifies whether the behavior associated with the event can be prevented.
		*/
		public function RowActionEvent(type:String, actionRow:TableTreeRow, parentRow:TableTreeRow=null, offset:int=0, bubbles:Boolean=false, cancelable:Boolean=false){
			super(type, bubbles, cancelable);
			_actionRow = actionRow;
			_parentRow = parentRow;
			_offset = offset;
		}
		
		public function get actionRow():TableTreeRow{ return _actionRow; }
		public function get parentRow():TableTreeRow{ return _parentRow; }
		public function get offset():int{ return _offset; }
		
	}
}