package com.tibco.be.views.core.ui.dashboard{
	
	import com.tibco.be.views.core.ui.actions.IActionContextProvider;
	
	import mx.collections.IList;
	
	/**
	 * The IBEVContainer represents the container which contains a component. A IBEVContainer can contain 
	 * either component or container but not a combination of both. Examples of IBEVContainer would be 
	 * Dashboard and Panel. The IBEVContainer provides means by which a component can ask 
	 * who its peer components are and then possibly listen for selection changes in them via ISelectionListener.
	 */
	
	public interface IBEVContainer{
		
		/**
		 * Initializes the container 
		 * @param containerCfg The container definition XML,can be null
		 */  
		function init(containerCfg:XML = null):void;
		
		/**
		 * Returns the id of the container 
		 */ 
		function get containerId():String;
		
		/**
		 * Returns the name of the container 
		 */ 
		function get containerName():String;
		
		/**
		 * Returns the title of the container 
		 */ 
		function get containerTitle():String;		
		
		/**
		 * Returns the type of the container
		 */ 
		function get containerType():String;
		
		/**
		 * Returns the container definition XML 
		 */ 
		function get containerConfig():XML;
		
		/**
		 * Returns the components embedded inside the container
		 */ 
		function get childComponents():IList;
		
		/**
		 * Returns the containers embedded inside the container 
		 */ 
		function get childContainers():IList;
		
		/**
		 * Returns the parent of the container. Can be null
		 */ 
		function get containerParent():IBEVContainer;
		
		/**
		 * Sets the title of the container 
		 * @param containerTitle The new title of the container 
		 */ 
		function set containerTitle(containerTitle:String):void;
		
		/**
		 * Adds a component to the container 
		 * @param component The component to be added
		 * @param widthWeightage The width (colspan) for the component
		 * @param heightWeightage The height (rowspan) for the component
		 */ 
		function addComponent(component:BEVComponent, widthWeightage:Number=-1, heightWeightage:Number=-1):void;
		
		/**
		 * Removes a component from the container 
		 * @param component The component to be removed
		 */ 
		function removeComponent(component:BEVComponent):void;		
		
		/**
		 * Adds a container to the container
		 * @param container The container to be added
		 * @param heightWeightage The height (span) for the container. This is the percentage value
		 * @param widthWeightage The width (span) for the container. This is the percentage value
		 */ 
		function addContainer(container:IBEVContainer, heightWeightage:Number=20, widthWeightage:Number=-1):void;
		
		/**
		 * Removes a container from the container
		 * @param container The container to be removed
		 */ 
		function removeContainer(container:IBEVContainer):void;
				
		/**
		 * Sets the menu configuration XML for the contianer 
		 * @param menuConfig The menu configuration/definition XML 
		 */ 
		function setInteractions(actionConfig:XML, actionContextProvider:IActionContextProvider):void;
		
		/**
		 * Searches for a component in the container 
		 * @param name the name of the component to be searched
		 */  
		function getComponentByName(name:String):BEVComponent; 

	}
}