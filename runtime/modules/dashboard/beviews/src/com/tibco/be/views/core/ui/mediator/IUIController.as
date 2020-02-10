package com.tibco.be.views.core.ui.mediator{
	
	import com.tibco.be.views.core.utils.IProgressListener;
	import com.tibco.be.views.user.dialogs.IPopupWindow;
	
	import flash.display.DisplayObject;
	import flash.utils.Dictionary;
	
	import mx.collections.IList;
	import mx.core.UIComponent;
	
	/**
	 * The UIController represents the actual controller of the BEViews dashboard. 
	 * It provides information about the root containers as well as place holder for storing 
	 * global values
	 */ 
	public interface IUIController{
		
		/**
		 * Initializes the UI Controller 
		 * @param bootParams The boot parameters passed to the parent kernel
		 */
		function init(bootParams:Dictionary):void;
		
		/**
		 * Reloads the UI Controller. Typically this API will be called to reload a dashboard
		 * either after a role switch or dashboard switch   
		 */
		function reload(progressListener:IProgressListener):void;
		
		/**
		 * Resets actually resets the controller. Typically should be called when one wants to 
		 * remove the root containers and reset the controller to show login dialog
		 */  
		function reset():void; 
		
		/**
		 * Returns the list of root containers. Note that all root containers will be 
		 * instances of IBEVContainer
		 */  
		function getRootContainers():IList;
		
		/**
		 * Returns the current page set id 
		 */
		function get currentPageSetId():String;
		
		/**
		 * Sets the current page set id 
		 * @param pagesetid - The page set id which is to be set as current page set id 
		 */ 
		function set currentPageSetId(pagesetid: String):void;
		
		/**
		 * Adds a general purpose variable. If a variable of the same name exists, then this value replaces 
		 * the existing value. Returns any existing variable values overrided
		 * @param variableName The name of the variable
		 * @param variableValue The value of the variable
		 */ 
		function addVariable(variableName:String, variableValue:Object):Object;
		
		/**
		 * Returns the value of a variable given the variable name
		 * @param variableName The name of the variable 
		 */ 
		function getVariable(variableName:String):Object;
		
		/**
		 * Opens a popup window in the specified parent display object.
		 * @param window The popup window to open
		 * @param parent The display object that will serve as the parent to the window
		 * @param modal Flag indicating whether or not the rest of the UI is enabled while the window is open
		*/
		function openWindow(window:IPopupWindow, parent:DisplayObject, modal:Boolean):void;
		
		/**
		 * Returns the window open at the provided coordinates.
		*/
		function getWindow(stageX:Number, stageY:Number):IPopupWindow;
		
		/**
		 * Centers an open window vertically and horizontally in the application.
		*/
		function centerWindow(window:IPopupWindow):void
		
		/**
		 * Closes (or attempts to close) the provided window object
		 * @param window The window to close
		*/
		function closeWindow(window:IPopupWindow):void;
		
		/**
		 * Closes all open popup windows.
		*/
		function closeAllWindows():void;

	}
}