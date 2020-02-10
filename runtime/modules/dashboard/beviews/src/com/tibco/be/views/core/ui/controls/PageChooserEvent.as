package com.tibco.be.views.core.ui.controls{
	
	import flash.events.Event;

	public class PageChooserEvent extends Event{
		
		public static const PAGE_CHANGE:String = "pageChange";
		
		protected var _currentPageNumber:int;
		
		public function PageChooserEvent(currentPageNumber:int){
			super(PAGE_CHANGE); //for now, pageChange is the only type of event
			_currentPageNumber = currentPageNumber;
		}
		
		public function get currentPageNumber():int{ return _currentPageNumber; }
		
	}
}