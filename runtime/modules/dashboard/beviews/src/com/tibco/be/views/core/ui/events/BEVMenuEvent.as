package com.tibco.be.views.core.ui.events{
	
	import flash.events.Event;
	
	import mx.events.MenuEvent;

	public class BEVMenuEvent extends MenuEvent{
		
		public static const BEVMENU_HIDE:String = MenuEvent.MENU_HIDE;
		public static const BEVMENU_SHOW:String = MenuEvent.MENU_SHOW;
		
		public function BEVMenuEvent(event:MenuEvent){
			super(event.type, event.bubbles, event.cancelable, event.menuBar, event.menu, event.item, event.itemRenderer, event.label, event.index);
		}
		
		override public function clone():Event{
			return new BEVMenuEvent(this);
		}
		
	}
}