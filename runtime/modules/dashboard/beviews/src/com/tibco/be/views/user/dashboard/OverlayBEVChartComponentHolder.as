package com.tibco.be.views.user.dashboard{
	
	import com.tibco.be.views.core.kernel.Kernel;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.user.dialogs.IPopupWindow;
	
	import flash.events.MouseEvent;
	import flash.filters.DropShadowFilter;
	import flash.geom.Point;
	
	import mx.controls.Button;
	import mx.events.FlexMouseEvent;
	import mx.managers.CursorManager;
	
	public class OverlayBEVChartComponentHolder extends BEVChartComponentHolder implements IPopupWindow{
		
		[Embed(source="/assets/container/close.png")]
		private static var CloseButtonIcon:Class;
		
		[Embed(source="/assets/container/resize.png")]
		private static var ResizeButtonIcon:Class;
		
		[Embed(source="/assets/container/resizecursor.png")]
		private static var ResizeCursorImage:Class;
		
		private var _closeButton:Button;
		private var _resizeCursorID:int;
		private var _resizeButton:Button;
		private var _resizeStartPnt:Point;
		private var _originalHeight:int;
		private var _originalWidth:int;
		
		public function OverlayBEVChartComponentHolder(component:BEVComponent){
			super();
			_component = component;
			_originalHeight = -1;
			_originalWidth = -1;
		}
		
		override protected function init():void{
			super.init();
			setStyle("dropShadowEnabled", true);
			filters = [new DropShadowFilter()];
			hb_TitleBar.removeEventListener(MouseEvent.MOUSE_DOWN, handleDragStart);
			hb_TitleBar.addEventListener(MouseEvent.MOUSE_DOWN, titleBarMouseDownHandler);
			addEventListener(FlexMouseEvent.MOUSE_DOWN_OUTSIDE, outsideMouseClickHandler);
		}
				
		override protected function addHeaderButtons():void{
			//close button 
			_closeButton = new Button();
			_closeButton.width = 16;
			_closeButton.height = 16;
			_closeButton.setStyle("icon", CloseButtonIcon);
			_closeButton.toolTip = "Click to close";
			_closeButton.addEventListener(MouseEvent.CLICK, handleCloseButtonClick);
			hb_CaptionButtons.addChild(_closeButton);
			
			//resize button
			_resizeButton = new Button();
			_resizeButton.setActualSize(12, 12);
			_resizeButton.x = (x+width) - 17;
			_resizeButton.y = (y+height) - 17;
			_resizeButton.setStyle("skin", ResizeButtonIcon);
			_resizeButton.setStyle("overSkin", ResizeButtonIcon);
			_resizeButton.setStyle("downSkin", ResizeButtonIcon);
			_resizeButton.setStyle("disabledSkin", ResizeButtonIcon);
			addChild(_resizeButton);
			
			_resizeButton.addEventListener(MouseEvent.MOUSE_OVER, resizeButtonMouseOverHandler);
			_resizeButton.addEventListener(MouseEvent.MOUSE_OUT, resizeButtonMouseOutHandler);
			_resizeButton.addEventListener(MouseEvent.MOUSE_DOWN, resizeButtonMouseDownHandler);
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			if(_resizeButton != null){
				_resizeButton.move(unscaledWidth - _resizeButton.width, unscaledHeight - _resizeButton.height);
			} 
		}
		
		override public function set height(value:Number):void{
			if(_originalHeight == -1){
				_originalHeight = value;
			}
			super.height = value;
		}
		
		override public function set width(value:Number):void{
			if(_originalWidth == -1){
				_originalWidth = value;
			}
			super.width = value;
		}		
		
		private function handleCloseButtonClick(event:MouseEvent):void{
			close();
		}
		
		private function outsideMouseClickHandler(event:FlexMouseEvent):void{
			close();	
		}
		
		private function resizeButtonMouseOverHandler(event:MouseEvent):void{
			_resizeCursorID = CursorManager.setCursor(ResizeCursorImage);
		}
		
		private function resizeButtonMouseOutHandler(event:MouseEvent):void{
			CursorManager.removeCursor(CursorManager.currentCursorID);
		}	
		
		private function resizeButtonMouseDownHandler(event:MouseEvent):void{
			_resizeStartPnt = new Point(event.stageX,event.stageY);
			systemManager.addEventListener(MouseEvent.MOUSE_MOVE, resizeMouseMoveHandler, true);
			systemManager.addEventListener(MouseEvent.MOUSE_UP, resizeMouseUpHandler, true);
		}
		
		private function resizeMouseMoveHandler(event:MouseEvent):void{
			event.stopImmediatePropagation();
			var widthVariation:int = event.stageX - _resizeStartPnt.x;
			var heightVariation:int = event.stageY - _resizeStartPnt.y;
			var newWidth:int = super.width + widthVariation;
			var newHeight:int = super.height + heightVariation;
			
			if(newWidth > _originalWidth){
				super.width = newWidth;
			}
			if(newHeight > _originalHeight){
				super.height = newHeight;
			}
			invalidateDisplayList();
			_resizeStartPnt.x = event.stageX;
			_resizeStartPnt.y = event.stageY;
		}	
		
		private function resizeMouseUpHandler(event:MouseEvent):void{
			event.stopImmediatePropagation();
			systemManager.removeEventListener(MouseEvent.MOUSE_MOVE, resizeMouseMoveHandler, true);
			systemManager.removeEventListener(MouseEvent.MOUSE_UP, resizeMouseUpHandler, true);			
		}	
		
		private function titleBarMouseDownHandler(event:MouseEvent):void{
			if(event.target == _closeButton){ return; }
			alpha = 0.75;
			startDrag();
			hb_TitleBar.addEventListener(MouseEvent.MOUSE_UP, titleBarMouseUpHandler);
		}	
		
		private function titleBarMouseUpHandler(event:MouseEvent):void{
			alpha = 1.0;
			stopDrag();
			hb_TitleBar.removeEventListener(MouseEvent.MOUSE_UP, titleBarMouseUpHandler);
		}		
					
		
		public function close():void{
			_component.destroy();
			Kernel.instance.uimediator.uicontroller.closeWindow(this);
		}
		
		override public function setComponentInteractions(actionConfig:XML):void{
			//do nothing
		}
		
		override public function getActionContext(parentActionConfig:XML,actionConfig:XML):ActionContext{
			return null;
		}
		
		public function closingWindow():void{
		
		}	
		
	}
}