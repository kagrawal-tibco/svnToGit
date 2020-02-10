package com.tibco.be.views.core.ui.actions{
	
	/**
	 * The IActionContextProvider provides a action context for a specific action config 
	 */ 
	public interface IActionContextProvider{
		
		/**
		 * Returns an action context based on the action config passed in
		 * @param parentActionConfig parent of the action config for which a action context is need. Can be null
		 * @param actionConfig the action config for which a action context is needed 
		 * @return An instance of a action context 
		 */ 
		function getActionContext(parentActionConfig:XML, actionConfig:XML):ActionContext;
	}
}