package com.tibco.be.views.core.ui.controls{
	
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.kernel.Kernel;
	import com.tibco.be.views.core.ui.actions.IActionContextProvider;
	import com.tibco.be.views.core.ui.events.BEVMenuEvent;
	import com.tibco.be.views.core.ui.menu.IBEVMenu;
	import com.tibco.be.views.utils.Logger;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.geom.Point;
	
	import mx.containers.Canvas;
	import mx.controls.Button;
	import mx.core.Container;

	public class BEVIconMenuButton extends Canvas{
		
		public static const SIZE:int = 17;
		
		private var _iconButton:Button;
		private var _menuConfig:XML;
		private var _menu:IBEVMenu;
		private var _actionCtxProvider:IActionContextProvider;
		private var _buttonDepressed:Boolean;
		private var _menuShowing:Boolean;
		
		public function BEVIconMenuButton(actionCtxProvider:IActionContextProvider){
			width = SIZE;
			height = SIZE;
			_actionCtxProvider = actionCtxProvider;
		}
		
		public function get menuConfig():XML{ return _menuConfig; }
		public function get menuShowing():Boolean{ return _menuShowing; }
		
		override public function set enabled(value:Boolean):void{
			super.enabled = value;
			if(_iconButton != null){ 
				_iconButton.enabled = value;
			}
		}
		
		public function set menuConfig(value:XML):void{
			_menuConfig = new XML(value);
			if(_menuConfig.actionconfig.length() == 0){ return; }
			if(_menuConfig != null){
				try{
					_menu = Kernel.instance.uimediator.menuprovider.getMenuUsingXML(_menuConfig, _actionCtxProvider, true);
					_menu.addMenuEventListener(BEVMenuEvent.BEVMENU_HIDE, handleBEVMenuHide);
					addEventListener(MouseEvent.CLICK, handleMouseClick);
				}
				catch(e:Error){
					//This happens regularly in the ComponentTester since there is no Kernel, but we log it just in case
					Logger.log(DefaultLogEvent.WARNING, "BEVIconMenuButton.menuConfig (SET) - Caught: " + e.message);
				}
			}
		}
				
		override protected function createChildren():void{
			super.createChildren();
			_iconButton = new Button();
			_iconButton.percentWidth = 100;
			_iconButton.percentHeight = 100;
			_iconButton.enabled = this.enabled;
			if(getStyle("skin") != null){ _iconButton.setStyle("skin", getStyle("skin")); }
			if(getStyle("downSkin") != null){
				_iconButton.setStyle("downSkin", getStyle("downSkin"));
				_iconButton.setStyle("selectedDownSkin", getStyle("downSkin"));
			}
			if(getStyle("overSkin") != null){
				_iconButton.setStyle("overSkin", getStyle("overSkin"));
				_iconButton.setStyle("selectedOverSkin", getStyle("overSkin"));
			}
			_iconButton.addEventListener(MouseEvent.CLICK, handleMouseClick);
			addChild(_iconButton);
		}
		
		private function handleMouseClick(event:MouseEvent):void{
			if(_menu == null){ return; }
			var parentContainer:Container = parent as Container;
			if(parentContainer == null){
				Logger.log(
					DefaultLogEvent.CRITICAL,
					"BEVIconMenuButton.handleMouseClick - Cannot display menu. Parent is not a Container."
				);
				return;
			}
			_menuShowing = true;
			var menuCoordinates:Point = parentContainer.contentToGlobal(new Point(x, y+height));
			_menu.show(menuCoordinates.x, menuCoordinates.y);
			showButtonDepressed();
		}	
		
		private function handleBEVMenuHide(event:BEVMenuEvent):void{
			if(event.menu == _menu.baseMenu){
				_menuShowing = false;
				invalidateDisplayList();
				dispatchEvent(new Event(BEVMenuEvent.BEVMENU_HIDE));
			}
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			if(_menuShowing && !_buttonDepressed){
				showButtonDepressed();
			}
			else if(!_menuShowing && _buttonDepressed){
				showButtonNormal();
			}
		}
		
		private function showButtonDepressed():void{
			_buttonDepressed = true;
			if(getStyle("downSkin") != null){ _iconButton.setStyle("skin", getStyle("downSkin")); }
		}
		
		private function showButtonNormal():void{
			_buttonDepressed = false;
			if(getStyle("skin") != null){ _iconButton.setStyle("skin", getStyle("skin")); }
		}

	}
	
}