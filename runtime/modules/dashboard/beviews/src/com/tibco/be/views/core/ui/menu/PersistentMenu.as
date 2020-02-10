package com.tibco.be.views.core.ui.menu{

	import com.tibco.be.views.core.kernel.Kernel;
	import com.tibco.be.views.user.dashboard.RelatedBEVChartComponentHolder;
	
	import flash.display.DisplayObject;
	import flash.display.DisplayObjectContainer;
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	import flash.geom.Rectangle;
	
	import mx.controls.Menu;
	import mx.controls.listClasses.IListItemRenderer;
	import mx.controls.menuClasses.IMenuItemRenderer;
	import mx.controls.menuClasses.MenuItemRenderer;
	import mx.core.Application;
	import mx.core.mx_internal;
	import mx.events.FlexMouseEvent;
	import mx.events.InterManagerRequest;
	import mx.events.MenuEvent;
	import mx.managers.ISystemManager;
	import mx.managers.PopUpManager;
 
	use namespace mx_internal;
 	
 	/**
 	 * PersistentMenu adds the following functionality:
 	 * 	Menus (root and open subs) remain shown when a menu item is clicked.
 	 * 	Parent menu items remain highlighted when traversing sub menus.
 	 * 
 	*/
	public class PersistentMenu extends Menu{
		
		private var _isPersistent:Boolean;
		private var _openSubMenus:Array;
		private var _highlightedMenuItem:int;
		private var _shouldHideMenu:Boolean;
 
		private const END_OF_SCREEN_MENU_PADDING:Number = 5;
 
		public function PersistentMenu(isPersistent:Boolean=true){
			super();
			showRoot = true;
			_isPersistent = isPersistent;
			_shouldHideMenu = !isPersistent
			_openSubMenus = new Array();
			_highlightedMenuItem = -1;
			addEventListener(FlexMouseEvent.MOUSE_DOWN_OUTSIDE, handleMouseDownOutside);
			addEventListener(MenuEvent.ITEM_ROLL_OVER, handleItemRollOver);
		}
				
		private function get openSubMenus():Array{ return _openSubMenus; }
		private function get highlightedMenuItem():int{ return _highlightedMenuItem; }
		
		override public function show(xShow:Object=null, yShow:Object=null):void{
			_shouldHideMenu = !_isPersistent;
			super.show(xShow, yShow);
			//Make sure PersistentMenu show in application bounds. Menu only does this for sub charts
			 // Adjust for menus that extend out of bounds
	        var sm:ISystemManager = systemManager.topLevelSystemManager;
	        var sbRoot:DisplayObject = sm.getSandboxRoot();
			var screen:Rectangle;
            var pt:Point = new Point(x, y);
            pt = sbRoot.localToGlobal(pt);
			if(sm != sbRoot){
                var request:InterManagerRequest = new InterManagerRequest(
					InterManagerRequest.SYSTEM_MANAGER_REQUEST, 
					false,
					false,
					"getVisibleApplicationRect"
				); 
                sbRoot.dispatchEvent(request);
                screen = Rectangle(request.value);
            }
            else{
                screen = sm.getVisibleApplicationRect();
            }
                
            var shift:Number = pt.x + width - screen.right;
            if(shift > 0){
                x = Math.max(x - shift, 0);
            }
            shift = pt.y + height - screen.bottom;
            if(shift > 0){
                y = Math.max(y - shift, 0);
            }
		}
		
		override public function hide():void{
			_highlightedMenuItem = -1;
			hideSubMenus();
			super.hide();
		}
		
		private function hideSubMenus():void{
			for each(var subMenu:Menu in _openSubMenus){
				subMenu.hide();
				subMenu.deleteDependentSubMenus();
			}
			deleteDependentSubMenus();
		}
		
		override mx_internal function hideAllMenus():void{
	        if(_shouldHideMenu){
	        	hideSubMenus(); //important, removes mouseDownOutside listeners
	        	var root:Menu = getRootMenu();
	        	root.hide();
        		root.deleteDependentSubMenus();
        		_openSubMenus = new Array();
	        }
	    }
	    
		override mx_internal function openSubMenu(row:IListItemRenderer):void{
			supposedToLoseFocus = true;
			 
			var r:Menu = getRootMenu();
			var menu:Menu;
			 
			if(!IMenuItemRenderer(row).menu){
				hideSubMenus();
				menu = new PersistentMenu();
				menu.parentMenu = this;
				menu.owner = this;
				menu.showRoot = showRoot;
				menu.dataDescriptor = r.dataDescriptor;
				menu.styleName = r;
				menu.labelField = r.labelField;
				menu.labelFunction = r.labelFunction;
				menu.iconField = r.iconField;
				menu.iconFunction = r.iconFunction;
				menu.itemRenderer = r.itemRenderer;
				menu.rowHeight = r.rowHeight;
				menu.scaleY = r.scaleY;
				menu.scaleX = r.scaleX;
				menu.variableRowHeight = r.variableRowHeight;
				 
				if(row.data && _dataDescriptor.isBranch(row.data) && _dataDescriptor.hasChildren(row.data)){
					menu.dataProvider = _dataDescriptor.getChildren(row.data);
				}
				 
				menu.sourceMenuBar = sourceMenuBar;
				menu.sourceMenuBarItem = sourceMenuBarItem;
				 
				IMenuItemRenderer(row).menu = menu;
				PopUpManager.addPopUp(menu, r, false);
				_openSubMenus.push(menu);
			}
			else{
				menu = IMenuItemRenderer(row).menu;
				if(!menu.visible){ hideSubMenus(); }
			}
			
			/* Same as Menu ... */
			var dispObj:DisplayObject = DisplayObject(row);
			var pt:Point = new Point(0,0);
			pt = dispObj.localToGlobal(pt);
			if(dispObj.root){
				pt = dispObj.root.globalToLocal(pt);
			}
			menu.show(pt.x + row.width, pt.y);
			/* END Same as Menu ... */
		}

		override protected function mouseOverHandler(event:MouseEvent):void{
			try{
				var tmpParent:Object;
				for(tmpParent = event.target; tmpParent != null; tmpParent = tmpParent.parent){
					 if(tmpParent is MenuItemRenderer){ break; }
				}
				var mir:MenuItemRenderer = tmpParent as MenuItemRenderer;
				if(mir){
					_highlightedMenuItem = mir.listData.rowIndex;
				}
			}
			catch(error:Error){
				
			}
			finally{
				super.mouseOverHandler(event);
			}
		}
	    
		override protected function drawItem(item:IListItemRenderer, selected:Boolean = false,  highlighted:Boolean = false, caret:Boolean = false, transition:Boolean = false):void{
	        if(!getStyle("useRollOver")){
	            super.drawItem(item, selected, false, false, transition);
	        }
	        else{
	        	if(item is MenuItemRenderer){
		        	var mir:MenuItemRenderer = item as MenuItemRenderer;
		        	var menu:PersistentMenu = mir.listData.owner as PersistentMenu; 
		        	if(menu && menu.openSubMenus.length > 0 && menu.highlightedMenuItem == mir.listData.rowIndex){
		        		highlighted = true;
		        	}
		        }
	            super.drawItem(item, selected, highlighted, caret, transition);
	        }
	    }
	    
	    private function isMenuDescendant(menu:Menu):Boolean{
	    	if(menu == null){ return false; }
	    	if(menu == this){ return true; }
	    	return isMenuDescendant(menu.parentMenu);
	    }
		
		/**
		 * Since sub-menus are also persistent, we have to close them manually when rolling over
		 * other menu items.
		*/ 
		private function handleItemRollOver(event:MenuEvent):void{
			if(event.item is XML){
				var xml:XML = event.item as XML;
				if(xml.children().length() > 0){
					//item will show sub-menu, don't hide sub menus
				}
				else{
					(event.menu as PersistentMenu).hideSubMenus();
				}
			}
		}
		
		private function handleMouseDownOutside(event:Event):void{
			var mouseEvent:MouseEvent = event as MouseEvent;
			var clickedAncestor:Boolean = clickedInAncestorMenu(mouseEvent.stageX, mouseEvent.stageY);
			var clickedRelatedChart:Boolean = Kernel.instance.uimediator.uicontroller.getWindow(mouseEvent.stageX, mouseEvent.stageY) is RelatedBEVChartComponentHolder;
			if(mouseEvent && (clickedAncestor || clickedRelatedChart)){
				return;
			}
			_shouldHideMenu = true;
			hideAllMenus();
		}
		
		private function clickedInAncestorMenu(x:int, y:int):Boolean{
			for(var ancestor:Menu = parentMenu; ancestor != null; ancestor = ancestor.parentMenu){
				if(ancestor.hitTestPoint(x, y)){//x >= ancestor.x && x <= (ancestor.x + ancestor.width) && y >= ancestor.y && y <= (ancestor.y + ancestor.height)){
					return true;
				}
			}
			return false;
		}
		
		public static function createMenu(parent:DisplayObjectContainer, mdp:Object, showRoot:Boolean=true, isPersistent:Boolean=true):PersistentMenu{
			var menu:PersistentMenu = new PersistentMenu(isPersistent);
			menu.tabEnabled = false;
			menu.owner = DisplayObjectContainer(Application.application);
			menu.showRoot = showRoot;
			popUpMenu(menu, parent, mdp);		 
			return menu;
		}

	}

}