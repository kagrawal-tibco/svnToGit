package com.tibco.be.views.core.ui.dashboard.events {
	
	import flash.events.Event;

	/**
	 * The ComponentSelectionEvent is fired whenever a component is selected by the user in BEViews. 
	 * The ComponentSelectionEvent contains a selection path which provides the path of the actual 
	 * child element selected by the user with in a component 
	 */ 
	public class ComponentSelectionEvent extends Event {
		
		/** The type of the event */ 
		public const TYPE:String = "componentselection";
		
		private var _selectionPath:String;
		
		/**
		 * Constructor 
		 * @param selectionPath The path of the child element clicked on. Can be null
		 */ 
		public function ComponentSelectionEvent(selectionPath:String){
			super(TYPE, false, false);
			_selectionPath = selectionPath;
		}
		
		override public function clone():Event{
			return new ComponentSelectionEvent(_selectionPath);
		}
		
	}
}