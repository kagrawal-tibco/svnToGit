package com.tibco.be.views.user.dashboard{
	
	import com.tibco.be.views.core.kernel.Kernel;
	import com.tibco.be.views.core.ui.dashboard.IBEVContainer;
	import com.tibco.be.views.user.dialogs.IPopupWindow;
	
	import flash.events.MouseEvent;
	import flash.geom.Point;
	
	import mx.controls.Button;
	import mx.events.CloseEvent;
	import mx.events.FlexMouseEvent;
	import mx.managers.CursorManager;
	
	
	public class FloatingBEVPanel extends AbstractBEVPanel implements IPopupWindow{
		
		[Embed(source="/assets/container/close.png")]
		private static var CloseButtonIcon:Class;
		
		[Embed(source="/assets/container/resize.png")]
		private static var ResizeButtonIcon:Class;
		
		[Embed(source="/assets/container/resizecursor.png")]
		private static var ResizeCursorImage:Class;
		
		private var _externallyClosable:Boolean;
		private var _closeButton:Button;
		private var _resizable:Boolean;
		private var _resizeCursorID:int;
		private var _resizeButton:Button;
		private var _resizeStartPnt:Point;
		private var _originalHeight:int;
		private var _originalWidth:int;
		
		public function FloatingBEVPanel(){
			super(null);
			_originalHeight = -1;
			_originalWidth = -1;
			_resizable = true;
		 	_containerID = (new Date()).getTime().toString();
		 	_containerName = "FloatingBEVPanel";
		 	_containerType = "dynamic";
		 	containerTitle = "Untitled";		
		 	externallyClosable = true;
		 	styleName = "floatingBEVPanel";
		}
		
		public function set containerParent(parent:IBEVContainer):void{
			this._parent = parent;
		}
		
		override public function set containerTitle(title:String):void{
			_containerName = title;
			super.containerTitle = title;
		}
		
		override protected function createChildren():void{
			super.createChildren();
			
			//close button 
			_closeButton = new Button();
			_closeButton.setStyle("icon", CloseButtonIcon);
			_closeButton.toolTip = "Click to close";
			_closeButton.addEventListener(MouseEvent.CLICK, handleCloseButtonClick);
			addCaptionButton(_closeButton);
			
			if(_resizable){
				//resize button
				_resizeButton = new Button();
				_resizeButton.setActualSize(12,12);
				_resizeButton.setStyle("skin", ResizeButtonIcon);
				_resizeButton.setStyle("overSkin", ResizeButtonIcon);
				_resizeButton.setStyle("downSkin", ResizeButtonIcon);
				_resizeButton.setStyle("disabledSkin", ResizeButtonIcon);
				rawChildren.addChild(_resizeButton);
				_resizeButton.addEventListener(MouseEvent.MOUSE_OVER, resizeButtonMouseOverHandler);
				_resizeButton.addEventListener(MouseEvent.MOUSE_OUT, resizeButtonMouseOutHandler);
				_resizeButton.addEventListener(MouseEvent.MOUSE_DOWN, resizeButtonMouseDownHandler);
			}			
			
			//titleBar.addEventListener(MouseEvent.MOUSE_DOWN, titleBarMouseDownHandler);
			
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			if(_resizable){
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
		
		
		public function set resizeable(resizeable:Boolean):void{
			_resizable = resizeable;
			if( !_resizable && _resizeButton != null){
				rawChildren.removeChild(_resizeButton);
				_resizeButton.removeEventListener(MouseEvent.MOUSE_OVER, resizeButtonMouseOverHandler);
				_resizeButton.removeEventListener(MouseEvent.MOUSE_OUT, resizeButtonMouseOutHandler);
				_resizeButton.removeEventListener(MouseEvent.MOUSE_DOWN, resizeButtonMouseDownHandler);
				invalidateDisplayList();
			}
		} 
		
		public function get resizeable():Boolean{
			return _resizable;
		}
		
		public function set externallyClosable(externallyClosable:Boolean):void{
			_externallyClosable = externallyClosable;
			if(_externallyClosable){
				addEventListener(FlexMouseEvent.MOUSE_DOWN_OUTSIDE, outsideMouseClickHandler);
			} 
			else{
				removeEventListener(FlexMouseEvent.MOUSE_DOWN_OUTSIDE, outsideMouseClickHandler);
			}
		}
		
		protected function handleCloseButtonClick(event:MouseEvent):void{
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
			trace(event.target);
			trace(event.target == _closeButton);
			if(event.target == _closeButton){ return; }
			alpha = 0.75;
			startDrag();
			titleBar.addEventListener(MouseEvent.MOUSE_UP, titleBarMouseUpHandler);
		}	
		
		private function titleBarMouseUpHandler(event:MouseEvent):void{
			alpha = 1.0;
			stopDrag();
			titleBar.removeEventListener(MouseEvent.MOUSE_UP, titleBarMouseUpHandler);
		}		
		
		public function close():void{
			Kernel.instance.uimediator.uicontroller.closeWindow(this);
			dispatchEvent(new CloseEvent(CloseEvent.CLOSE));
		}
		
		public function closingWindow():void{
			
		}
				
	}
}