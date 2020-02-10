package com.tibco.be.views.core.ui.menu{
	import mx.controls.Menu;
	
	
	/**
	 * Represents the actual menu being shown
	 */ 	
	public interface IBEVMenu{
		
		function get baseMenu():Menu; 
		
		/**
		 * Shows the menu at the specified location 
		 * @param x the x co-ordinate 
		 * @param y the y co-ordinate 
		 * @param hideOtherMenus flag indicating whether all other menus in the UI should be hidden
		 */ 		
		function show(x:int, y:int, hideOtherMenus:Boolean=true):void;
		
		/**
		 * Closes the menu 
		 */ 		
		function close():void;
		
		function addMenuEventListener(type:String, listener:Function):void;
		
		function removeMenuEventListener(type:String, listener:Function):void;
		
	}
}