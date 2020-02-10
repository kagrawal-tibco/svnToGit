package com.tibco.be.views.core.ui.menu{
	
	import com.tibco.be.views.core.ui.actions.AbstractAction;
	
	import mx.collections.ArrayCollection;
	import mx.collections.IList;
	import mx.controls.Image;
	
	/**
	 * The DynamicMenuDefinition class provies means to generate a menu in BEViews platform
	 * using dynamic attributes and actions. Typically one would DynamicMenuDefinition to create 
	 * the menu when the menu defining XML is not provided. 
	 */
	public class DynamicMenuDefinition{
		
		/** The text that is shown in the menu */ 
		private var _text:String ;
		
		/** The icon that is shown in the menu */ 
		private var _icon:Image;
		
		/** Decides whether the menu is enabled or not */ 
		private var _enabled:Boolean;
		
		/** The actual action to be executed when the user clicks on the menu */ 
		private var _action:AbstractAction;
		
		/** Sub Menus */  
		private var _childDynamicMenus:IList;
		
		/** Parent Menu Definition. Used when the DynamicMenuDefinition is a sub menu */ 
		private var _parent:DynamicMenuDefinition ;
		
		/**
		 * Creates an instance of DynamicMenuDefinition
		 * @param text The text of the menu 
		 * @param icon The icon of the menu
		 * @param enabled Indicates whether the menu is enabled or not
		 */  
		public function DynamicMenuDefinition(text:String = null, icon:Image = null,enabled:Boolean = true):void{
			_text = text;
			_icon = icon;
			_enabled = enabled;
			_childDynamicMenus = new ArrayCollection();	
		}
		
		/**
		 * Returns the text for the menu. If the action is set then the action's text is returned 
		 */ 
		public function get text():String {
			if(_action == null){ return _text; }
			return _action.text;
		}
		
		/**
		 * Returns the icon for the menu. If the action is set then the action's icon is returned 
		 */ 
		public function get icon():Image{
			if(_action == null){ return _icon; }
			return _action.icon;
		}
		
		/**
		 * Returns the enabled/disabled state for the menu. If the action is set then the action's 
		 * enabled/disabled state is returned 
		 */ 		
		public function get enabled():Boolean{
			if(_action == null){ return _enabled; }
			return action.enabled;
		}
		
		/** Returns the currently set action */ 
		public function get action():AbstractAction{ return _action; }
		
		/** Returns the child sub menus in this DynamicMenuDefinition */ 
		public function get childMenus():IList{ return _childDynamicMenus; }
		
		/** Returns the parent DynamicMenuDefinition. Useful for navigating from child menu to parent menu */ 
		public function get parent():DynamicMenuDefinition{ return _parent; }
		
		/**
		 * Sets the action for the menu
		 * @param action The action 
		 */  
		public function set action(action:AbstractAction):void{ _action = action; }
		
		/**
		 * Sets the child sub menus for this DynamicMenuDefinition. This replaces all 
		 * existing child sub menus 
		 */ 
		public function set childMenu(menus:IList):void{
			_childDynamicMenus = new ArrayCollection();
			for each(var menu:DynamicMenuDefinition in menus){
				addChildMenu(menu);
			}
		}
		
		/**
		 * Sets the parent DynamicMenuDefinition. Useful for navigating from child menu to parent menu
		 * @param parent the parent DynamicMenuDefinition
		 */  
		public function set parent(parent:DynamicMenuDefinition):void{ _parent = parent; }
		
		/**
		 * Adds a new DynamicMenuDefinition as a sub menu 
		 * @param menu The child menu 
		 */ 
		public function addChildMenu(menu:DynamicMenuDefinition):void{
			_childDynamicMenus.addItem(menu);
			menu.parent = this;
		}
	}
}