package com.tibco.be.views.core.ui.menu{
	
	import com.tibco.be.views.core.ui.actions.IActionContextProvider;
	
	import flash.utils.Dictionary;
	
	/**
	 * The MenuProvider encapsulates the menu creation 
	 */	
	public interface IBEVMenuProvider{
		
		/**
		 * Initialize the menu provider 
		 * @param initParams the initialization parameters 
		 */ 		
		function init(initParams:Dictionary):void;
		
		/**
		 * Returns an instance of a menu. Use this API when we have a properly constructed menu configuration XML and all the 
		 * needed actions have been preregistered with the system. 
		 * @param menuConfig A XML configuration of the menu
		 * @param actionCtxProvider An ActionContextProvider 
		 */ 
		function getMenuUsingXML(menuConfig:XML, actionCtxProvider:IActionContextProvider, usePersistentMenu:Boolean=false):IBEVMenu;
		
		/**
		 * Returns an instance of a menu. Use this API when we don't have a properly constructed menu configuration XML 
		 * and all the needed actions are not preregistered. One could use dynamically created unregistered actions with this API.
		 * @param menu a dynamic definition of the menu
		 * @param actionCtxProvider An ActionContextProvider 
		 */ 
		function getMenuUsingDynamicDefintion(menu:DynamicMenuDefinition, actionCtxProvider:IActionContextProvider):IBEVMenu;
				
	}
}