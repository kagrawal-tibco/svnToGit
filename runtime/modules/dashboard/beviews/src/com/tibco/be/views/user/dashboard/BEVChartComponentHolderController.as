package com.tibco.be.views.user.dashboard{
	
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.kernel.Kernel;
	import com.tibco.be.views.core.ui.IBEVTooltip;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.actions.IActionContextProvider;
	import com.tibco.be.views.core.ui.controls.BEVIconMenuButton;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.core.ui.dashboard.IBEVContainer;
	import com.tibco.be.views.core.ui.menu.DynamicMenuDefinition;
	import com.tibco.be.views.core.ui.menu.IBEVMenu;
	import com.tibco.be.views.utils.Logger;
	
	import mx.controls.Button;
	
	public class BEVChartComponentHolderController{
		
		private var _view:BEVChartComponentHolder;
		
        protected var _maximizeButton:Button;
        protected var _menuButton:BEVIconMenuButton;
		
		public function BEVChartComponentHolderController(view:BEVChartComponentHolder){
			_view = view;
			if(_view.component != null){
				title = _view.component.componentTitle;
			}
		}
		
		protected function set title(value:String):void{
			if(_view.lbl_Title){ _view.lbl_Title.text = value; }
		}
		
		/**
		 * Searches for a component within the current dashboard based on a name 
		 * @param name the name of a component 
		 * @param searchPeers  if true,then the search is only limited to the components within the same parent. 
		 */ 
		public function getComponentByName(name:String, searchPeers:Boolean):BEVComponent{
			return searchComponentByName(name, _view.component.componentContainer, searchPeers);
		}
		
		public function addHeaderButtons():void{
//			_maximizeButton = new Button();
//			_maximizeButton.styleName = "maximizeButton";
//			_maximizeButton.enabled = false;
			
			_menuButton = new BEVIconMenuButton(_view);
			_menuButton.enabled = true;
			
//			_view.hb_CaptionButtons.addChild(_maximizeButton);
			_view.hb_CaptionButtons.addChild(_menuButton);
		}
		
		private function searchComponentByName(name:String, container:IBEVContainer, searchPeers:Boolean):BEVComponent{
			var comp:BEVComponent = container.getComponentByName(name);
			if(searchPeers || comp != null){ return comp; }
			for each(var childContainer: IBEVContainer in container.childContainers){
				comp = childContainer.getComponentByName(name);
				if(comp != null){ return comp; }
			}
			var parentContainer:IBEVContainer = container.containerParent;
			if(parentContainer == null){ return null; }
			return searchComponentByName(name, parentContainer, false);				
		}
		
		public function getActionContext(parentActionConfig:XML, actionConfig:XML):ActionContext{
			throw new Error("Unsupported Operation");
		}
		
		/**
		 * Shows a menu 
		 * @param x the x co-ordinate 
		 * @param y the y co-ordinate 
		 * @param menuDefinition the XML which defines the menu 
		 * @param actionCtxProvider The action context provider 
		 */ 
		public function showMenu(x:int, y:int, menuDefinition:XML, actionCtxProvider:IActionContextProvider):void{
			var menu:IBEVMenu = Kernel.instance.uimediator.menuprovider.getMenuUsingXML(menuDefinition, actionCtxProvider);
			menu.show(x,y);
		}
		
		/**
		 * Shows a menu 
		 * @param x the x co-ordinate 
		 * @param y the y co-ordinate 
		 * @param menuDefinition the DynamicMenuDefinition which defines the menu 
		 * @param actionCtxProvider The action context provider 
		 */		
		public function showDynamicMenu(x:int, y:int, menuDefinition:DynamicMenuDefinition, actionCtxProvider:IActionContextProvider):void{
			var menu:IBEVMenu = Kernel.instance.uimediator.menuprovider.getMenuUsingDynamicDefintion(menuDefinition, actionCtxProvider);
			menu.show(x,y);			
		}
		
		/**
		 * Shows a tooltip 
		 * @param x the x co-ordinate 
		 * @param y the y co-ordinate 
		 * @param tooltip The actual tooltip to be shown
		 */ 
		public function showTooltip(x:int, y:int, tooltip:String):void{
			var tooltipHolder:IBEVTooltip = Kernel.instance.uimediator.tooltipprovider.getTooltip(tooltip);
			tooltipHolder.show(x,y);
		}
		
		/**
		 * Logs a message against the component name 
		 * @param logLevel The level of logging 
		 * @param logMessage The message to be logged 
		 */ 
		public function log(logLevel:Number, logMessage:String):void{
			Logger.log(DefaultLogEvent.INFO, "BEVChartComponentHolder - (" + logLevel + ") " + logMessage);
		}
		
		/**
		 * Handles a exception on behalf of the component 
		 * @param logLevel The level of logging 
		 * @param exceptionMessage The message to be logged 
		 * @param error The exception 
		 */ 
		public function handleException(logLevel:Number, exceptionMessage:String, exception:Error):void{
			Logger.log(DefaultLogEvent.WARNING, "BEVChartComponentHolder - " + exceptionMessage);
			Logger.log(DefaultLogEvent.DEBUG, exception.getStackTrace());
		}
		
		/**
		 * Sets the interactions of the component with the outside world. Typically these
		 * interactions will manifest themselves as a menu.
		 */ 		
		public function setComponentInteractions(actionConfig:XML):void{
			title = _view.component.componentTitle;
			_menuButton.menuConfig = actionConfig;
		}

	}
}