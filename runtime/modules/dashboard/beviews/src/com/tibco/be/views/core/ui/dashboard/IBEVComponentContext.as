package com.tibco.be.views.core.ui.dashboard{
	
	import com.tibco.be.views.core.ui.actions.IActionContextProvider;
	import com.tibco.be.views.core.ui.menu.DynamicMenuDefinition;
	
	/**
	 * SynComponentContext represents the umbilical cord to connect a component 
	 * to the world containing the component. 
	 */ 
	public interface IBEVComponentContext{
		
		/**
		 * Shows a menu 
		 * @param x the x co-ordinate 
		 * @param y the y co-ordinate 
		 * @param menuDefinition the XML which defines the menu 
		 * @param actionCtxProvider The action context provider 
		 */ 
		function showMenu(x:int, y:int, menuDefinition:XML, actionCtxProvider:IActionContextProvider):void;
		
		/**
		 * Shows a menu 
		 * @param x the x co-ordinate 
		 * @param y the y co-ordinate 
		 * @param menuDefinition the DynamicMenuDefinition which defines the menu 
		 * @param actionCtxProvider The action context provider 
		 */		
		function showDynamicMenu(x:int, y:int, menuDefinition:DynamicMenuDefinition, actionCtxProvider:IActionContextProvider):void;
		
		/**
		 * Shows a tooltip 
		 * @param x the x co-ordinate 
		 * @param y the y co-ordinate 
		 * @param tooltip The actual tooltip to be shown
		 */ 
		function showTooltip(x:int, y:int, tooltip:String):void;
		
		/**
		 * Logs a message against the component name 
		 * @param logLevel The level of logging 
		 * @param logMessage The message to be logged 
		 */ 
		function log(logLevel:Number, logMessage:String):void;
		
		/**
		 * Handles a exception on behalf of the component 
		 * @param logLevel The level of logging 
		 * @param exceptionMessage The message to be logged 
		 * @param error The exception 
		 */ 
		function handleException(logLevel:Number, exceptionMessage:String, exception:Error):void;
		
		/**
		 * Searches for a component within the current dashboard based on a name 
		 * @param name the name of a component 
		 * @param searchPeers  if true,then the search is only limited to the components within the same parent. 
		 * 									The parent is the component's parent
		 */ 
		function getComponentByName(name:String, searchPeers:Boolean):BEVComponent;
		
		/**
		 * Sets the interactions of the component with the outside world. Typically these interactions will
		 * manifest themselves as a menu.
		 */ 		
		function setComponentInteractions(actionConfig:XML):void;
		
	}
}