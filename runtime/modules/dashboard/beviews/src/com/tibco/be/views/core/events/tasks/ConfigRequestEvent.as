package com.tibco.be.views.core.events.tasks{
	import com.tibco.be.views.core.events.EventTypes;
	
	public class ConfigRequestEvent extends ServerRequestEvent{
		
		/** Command to get the login customization information */
		public static const GET_LOGIN_CUSTOMIZATION_COMMAND:String = "getlogincustomization";
		
		/** Command to fetch the dashboard layout */
		public static const GET_LAYOUT_COMMAND:String = "getlayout";
		
		/** Command to fetch the dashboard's header configuration details */
		public static const GET_HEADER_CONFIG_COMMAND:String = "getheaderconfig";
		
		/** Command to search the list of avilable components for the current user/role */
		public static const SEARCH_AVAILABLE_COMPONENTS:String = "searchcomps";
		
		/** Command to fetch a component's configuration details */
		public static const GET_COMPONENT_CONFIG_COMMAND:String = "getcomponentconfig";
		
		/** Command to fetch a component's visualization data details */
		public static const GET_COMPONENT_DATA_COMMAND:String = "getcomponentdata";
		
		/** Command to fetch the panel menus */
		public static const GET_PANEL_MENUS_COMMAND:String = "getpanelmenus";
		
		/** Command to fetch the model for the filter editor dialog */
		public static const GET_FILTER_MODEL_COMMAND:String = "getfeditormodel";
		
		/** Command to update the filter model */
		public static const UPDATE_FILTER_MODEL_COMMAND:String = "updatefeditormodel";
		
		/** Command to set the current pageset */
		public static const SET_CURRENT_PAGESET_COMMAND:String = "setcurrentpageset";
		
		/** Command to save the dashboard layout */ 
		public static const SAVE_LAYOUT_COMMAND:String = "savelayout";
		
		/** Command to save the dashboard layout */ 
		public static const REMOVE_COMPONENT_COMMAND:String = "removecomponent";
		
		/** Command to fetch the gallery components */
		public static const GET_COMPONENT_GALLERY:String = "getgallery";
		
		/** Command to add a component to a panel */
		public static const ADD_COMPONENT_COMMAND:String = "addcomponent";
		
		/** Command to expand a node in a process model */
		public static const EXPAND_PROCMODEL_NODE:String = "expandstatenode";
		
		/** Command to collapse a node in a process model */
		public static const COLLAPSE_PROCMODEL_NODE:String = "collapsestatenode";
		
		/** Command to get data for all the subscribed components */
		public static const GET_ALL_COMPONENTS_DATA:String = "getallcomponentsdata";
		
		public function ConfigRequestEvent(requestCommand:String, intendedRecipient:Object=null){
			super(requestCommand, EventTypes.CONFIG_COMMAND, intendedRecipient);
		}
		
		override public function get logMessage():String{
			return "ConfigRequestEvent: " + command; 
		}
		
	}
}