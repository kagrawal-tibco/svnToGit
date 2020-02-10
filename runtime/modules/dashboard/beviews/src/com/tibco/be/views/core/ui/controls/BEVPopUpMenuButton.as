package com.tibco.be.views.core.ui.controls{
	
	import com.tibco.be.views.core.Kernel;
	import com.tibco.be.views.core.ui.actions.IActionContextProvider;
	import com.tibco.be.views.core.ui.events.BEVMenuEvent;
	import com.tibco.be.views.core.ui.menu.IBEVMenu;
	
	import flash.events.MouseEvent;
	import flash.geom.Point;
	
	import mx.containers.Canvas;
	import mx.controls.Label;
	import mx.core.UIComponent;
	
	public class BEVPopUpMenuButton	extends Canvas{
		
		public static const FILL_MODE:int = 0;
		public static const CONSTRAINED_MODE:int = 0;
		
		private var _actionCtxProvider:IActionContextProvider;
		private var _text:String;
		private var _menuConfig:XML;
		private var _menu:IBEVMenu;
		private var _textLbl:Label;
		private var _downwardArrow:DownwardArrow;
		private var _mouseHovering:Boolean;
		private var _menuShowing:Boolean;
		private var _layoutMode:int;
		private var _xAdjustment:int;
		private var _yAdjustment:int;
		
		public function BEVPopUpMenuButton(actionCtxProvider:IActionContextProvider){
			_actionCtxProvider = actionCtxProvider;
			_layoutMode = FILL_MODE;
			_xAdjustment = 0;
			_yAdjustment = 0;
		}
		
		public function get text():String{ return _text; }
		public function get menuConfig():XML{ return _menuConfig; }
		
		public function set xAdjustment(value:int):void{ _xAdjustment = value; }
		public function set yAdjustment(value:int):void{ _yAdjustment = value; }
		public function set layoutMode(value:int):void{ _layoutMode = value; }
		public function set text(value:String):void{
			_text = value;
			if(_textLbl != null){
				_textLbl.text = _text;
				invalidateDisplayList();
			}
		}
		
		public function set menuConfig(value:XML):void{
			_menuConfig = new XML(value);
			if(_menuConfig != null){
				_menu = Kernel.instance.uimediator.menuprovider.getMenuUsingXML(_menuConfig, _actionCtxProvider, true);
				_menu.addMenuEventListener(BEVMenuEvent.BEVMENU_HIDE, handleBEVMenuHide);
				addEventListener(MouseEvent.CLICK, handleMouseClick);
			}
		}
		
		override protected function createChildren():void{
			super.createChildren();
			if(!_textLbl){
				_textLbl = new Label();
				_textLbl.mouseEnabled = true; 
				_textLbl.text = _text;
				_textLbl.setStyle("color", getStyle("color"));
				addChild(_textLbl);
			}
			if(!_downwardArrow){
				_downwardArrow = new DownwardArrow();
				var arrowContainer:UIComponent = new UIComponent();
				arrowContainer.addChild(_downwardArrow);
				addChild(arrowContainer);
			}
			
			addEventListener(MouseEvent.MOUSE_OVER, handleMouseOver);
			addEventListener(MouseEvent.MOUSE_OUT, handleMouseOut);
		}
		
		override protected function measure():void{
			super.measure();
			this.measuredHeight = _textLbl.measuredHeight + 2;
			this.measuredWidth = _textLbl.measuredWidth + 2 + _downwardArrow.width;
		}
		
		private function handleMouseOver(event:MouseEvent):void{
			_mouseHovering = true;
			invalidateDisplayList();
		}
		
		private function handleMouseOut(event:MouseEvent):void{
			_mouseHovering = _menuShowing;
			invalidateDisplayList();
		}
		
		private function handleMouseClick(event:MouseEvent):void{
			_menuShowing = true;
			var globalPnt:Point = contentToGlobal(new Point(x,y));
			_menu.show(globalPnt.x + _xAdjustment, globalPnt.y + height - 3 + _yAdjustment);
		}	
		
		private function handleBEVMenuHide(event:BEVMenuEvent):void{
			if(event.target == _menu){
				_menuShowing = false;
				_mouseHovering = false;
				invalidateDisplayList();
			}
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			if(_layoutMode == CONSTRAINED_MODE){
				unscaledWidth = _textLbl.textWidth + _downwardArrow.width + 8;
			}
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			graphics.clear();
			
			var compHeight:int = unscaledHeight - 2;
			var horizontalGap:int = 2;
			
			//we will first place the downward arrow
			_downwardArrow.x = unscaledWidth - _downwardArrow.width;
			_downwardArrow.y = (unscaledHeight - _downwardArrow.height)/2 - 2;
			
			//now we will place the text label 
			_textLbl.width = unscaledWidth - _downwardArrow.width - horizontalGap;
			_textLbl.height = compHeight;
			_textLbl.x = 0;
			_textLbl.y = 0;
			
			var color:Number = getStyle("color");
			if(_mouseHovering){
				color = getStyle("rollOverColor");
				//set the color
				graphics.lineStyle(1,color);
				//draw the border around the text label
				graphics.drawRoundRectComplex(1, 1, unscaledWidth, compHeight-5, 1, 1, 1, 1); 
				//draw the line between the text label and the arrow
				graphics.moveTo(_textLbl.width+2, 1);
				graphics.lineTo(_textLbl.width+2, compHeight-4);
			}
			_downwardArrow.color = color;
			_downwardArrow.updateDisplayList(_downwardArrow.width, _downwardArrow.height);
		}	
		
		override public function styleChanged(styleProp:String):void{
			if(styleProp == "color" || styleProp == "rollOverColor"){
				invalidateDisplayList();
			}
			else{
				super.styleChanged(styleProp);
			}
		}		
	}
	
}