package com.tibco.be.views.core.ui.menu{
	
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.ui.CommandTypes;
	import com.tibco.be.views.core.ui.actions.AbstractAction;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.actions.IActionContextProvider;
	import com.tibco.be.views.core.ui.actions.ActionRegistry;
	import com.tibco.be.views.core.ui.events.BEVMenuEvent;
	import com.tibco.be.views.user.actions.IStoppableAction;
	import com.tibco.be.views.utils.Logger;
	
	import flash.display.DisplayObject;
	import flash.events.EventDispatcher;
	import flash.geom.Rectangle;
	import flash.utils.Dictionary;
	
	import mx.controls.Menu;
	import mx.core.Application;
	import mx.core.ClassFactory;
	import mx.events.MenuEvent;

	/**
	 * AbstractSynMenuImpl is a partial stand alone base implementation of the SynMenu. 
	 * It provides structure and all the basic properties needed to create instances of native 
	 * menu class
	 */ 
	public class AbstractBEVMenu extends EventDispatcher implements IBEVMenu{
		
		protected var _bevMenuProviderImpl:BEVMenuProvider;
		protected var _menuIDToMenuConfigLookupDict:Dictionary;
		protected var _actualMenu:Menu;		
		protected var _menuDataProviderXML:XML;
		protected var _actionCtxProvider:IActionContextProvider;
		protected var _executedActions:Array;
		/**
		 * Constructor 
		 * @param synMenuProviderImpl the SynMenuProvideImpl
		 * @param menuDefinitionObj the menu definition. This can either be an XML or DynamicMenuDefinition
		 * @param actionContextProvider the context provider which will be provide the context for executing an action
		 */ 
		function AbstractBEVMenu(bevMenuProviderImpl:BEVMenuProvider, menuDefinitionObj:*, actionContextProvider:IActionContextProvider){
			_bevMenuProviderImpl = bevMenuProviderImpl;
			_actionCtxProvider = actionContextProvider;
			_menuIDToMenuConfigLookupDict = new Dictionary();
			_menuDataProviderXML = parseMenuDefinitionObj(menuDefinitionObj);
			_executedActions = new Array();
			createMenu();
		}
		
		public function get baseMenu():Menu{
			return _actualMenu;
		}
		
		/**
		 * Parses the menu definition object to create a standard menu xml. The generated xml 
		 * is given to the native menu system to generate the menu. Below is a sample of the 
		 * xml which should be generated. <br>
		 * <pre>
		 * 	<ROOT>
		 * 		<menuitem label="..." id="..."' enabled=""/>
		 * 		<menuitem label="..." id="..."' enabled=""/>
		 * 	</ROOT>
		 * </pre>
		 * @param the menu definition. This can either be an XML or DynamicMenuDefinition
		 * @returns a standard xml to populate the menu
		 */  
		protected function parseMenuDefinitionObj(menuDefinitionObj:*):XML{
			return null;
		}
		
		/**
		 * Registers a action definition or action config XML against a id. 
		 */ 
		protected function registerAction(id:String, actionDefOrActionXML:*):void{
			_menuIDToMenuConfigLookupDict[id] = actionDefOrActionXML;
		}
		
		/**
		 * Creates the actual menu. The 'label' attribute from the xml is used to generate the text 
		 */ 
		protected function createMenu():void{
			_actualMenu = new Menu();
			configureMenu();
		}
		
		protected function configureMenu():void{
			if(_actualMenu == null){
				Logger.log(DefaultLogEvent.WARNING, "AbstractBEVMenuImpl.configureMenu - Null menu.");
				return;
			}
			mx.controls.Menu.popUpMenu(_actualMenu, null, _menuDataProviderXML); 
			_actualMenu.showRoot = false;
			_actualMenu.labelField = "@label";
			_actualMenu.variableRowHeight = true;
			_actualMenu.itemRenderer = new ClassFactory(BEVMenuItemRenderer);
			_actualMenu.addEventListener(MenuEvent.ITEM_CLICK, handleMenuItemClick);	
			_actualMenu.addEventListener(MenuEvent.MENU_HIDE, handleMenuHide);		
		}
		
		/**
		 * Handles menu item clicks. Finds the id of the menu item clicked and then finds the 
		 * action config XML or the menu definition. Based on that information the appropriate 
		 * action is obtained and executed 
		 * @param eventObj provides information of which menuitem was clicked 
		 */  
		protected function handleMenuItemClick(event:MenuEvent):void{
			var bounds:Rectangle = event.menu.getBounds(Application.application as DisplayObject);
			var boundsForY:Rectangle = event.itemRenderer.getBounds(Application.application as DisplayObject);			
			bounds.y = boundsForY.y;
			var menuitemXML: XML = event.item as XML;
		 	var id:String = menuitemXML.@id;
		 	var actionDefOrActionXML:* = _menuIDToMenuConfigLookupDict[id];
		 	if(actionDefOrActionXML is XML){
		 		var actioncfgXML:XML = actionDefOrActionXML as XML;
		 		var action:AbstractAction = ActionRegistry.instance.getAction(actioncfgXML);
		 		if(action == null){
		 			Logger.log(DefaultLogEvent.WARNING, "AbstractBEVMenu.handleMenuItemClick - Can't execute null action");
		 			return;
		 		}
		 		var actionCtx:ActionContext = _actionCtxProvider.getActionContext(actioncfgXML.parent(), actioncfgXML);
			}
		 	else{
		 		var actionDef:DynamicMenuDefinition = actionDefOrActionXML as DynamicMenuDefinition;
		 		if(actionDef == null){
		 			Logger.log(DefaultLogEvent.WARNING, "AbstractBEVMenu.handleMenuItemClick - Can't extract action from null menu definition.");
		 			return;
		 		}
		 		action = actionDef.action;
		 		if(action == null){
		 			Logger.log(DefaultLogEvent.WARNING, "AbstractBEVMenu.handleMenuItemClick - Can't execute null action");
		 			return;
		 		}
		 		if(actionDef.parent != null && actionDef.parent.action != null){
		 			actionCtx = _actionCtxProvider.getActionContext(actionDef.parent.action.actionConfig, actionDef.action.actionConfig);
		 		}
		 		else{
		 			actionCtx = _actionCtxProvider.getActionContext(null, actionDef.action.actionConfig);
		 		}
		 	}
		 	if(actionCtx != null){ actionCtx.bounds = bounds; }
		 	
		 	for each(var previousAction:AbstractAction in _executedActions){
		 		var stoppableAction:IStoppableAction = previousAction as IStoppableAction;
	 			if(stoppableAction != null && stoppableAction.shouldStopOnEvent(event)){
	 				stoppableAction.stop();
	 			}
	 		}
	 		
	 		_executedActions.push(action);
		 	action.execute(actionCtx);
		 	if(action.command != CommandTypes.LAUNCH_COMPONENT){
		 		_actualMenu.hide();
		 	}
		}
		
		protected function handleMenuHide(event:MenuEvent):void{
			//trace("INFO: AbstractBEVMenu.handleMenuHide - " + event.menu + ".visible = " + event.menu.visible);
			for each(var previousAction:AbstractAction in _executedActions){
		 		var stoppableAction:IStoppableAction = previousAction as IStoppableAction;
	 			if(stoppableAction != null && stoppableAction.shouldStopOnEvent(event)){
	 				stoppableAction.stop();
	 			}
	 		}
			_executedActions = new Array();
			var bevMenuEvent:BEVMenuEvent = new BEVMenuEvent(event);
			dispatchEvent(bevMenuEvent);
		}
		
		/**
		 * Shows the menu. All other visible menus are hidden 
		 */  
		public function show(x:int, y:int, hideOtherMenus:Boolean=true):void{
			if(hideOtherMenus){ _bevMenuProviderImpl.hideAll(this); }
			_actualMenu.show(x, y-2);
		}
		
		/**
		 * Closes the menu
		 */ 
		public function close():void{
			_actualMenu.hide();
			_bevMenuProviderImpl.remove(this);
		}
		
		public function addMenuEventListener(type:String, listener:Function):void{
		 	super.addEventListener(type,listener);
		}
		
		public function removeMenuEventListener(type:String, listener:Function):void{
			super.removeEventListener(type,listener);
		}		
		
	}
}