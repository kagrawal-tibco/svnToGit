package com.tibco.be.views.ui.containers{
	
	import com.tibco.be.views.ui.buttons.ScrollListButton;
	import com.tibco.be.views.user.components.pagesetselector.BEVPageSetRowRenderer;
	
	import flash.display.DisplayObject;
	import flash.events.MouseEvent;
	import flash.events.TimerEvent;
	import flash.utils.Timer;
	
	import mx.containers.Canvas;
	import mx.containers.VBox;
	import mx.controls.List;
	import mx.core.ClassFactory;
	import mx.core.ScrollPolicy;
	import mx.effects.Tween;
	import mx.effects.easing.Quadratic;
	import mx.events.FlexEvent;

	public class ButtonScrollList extends VBox{
		
		protected static const QUANTUM_SCROLL_STEP:Number = 40;
		protected static const SMOOTH_SCROLL_STEP:Number = 0.1;
		protected static const SMOOTH_SCROLL_INTERVAL:Number = 200; //ms
		protected static const SCROLL_BUTTON_HEIGHT:Number = 20;
		
		protected var _upScroller:Timer;
		protected var _downScroller:Timer;
		protected var _upScrollButton:ScrollListButton;
		protected var _downScrollButton:ScrollListButton;
		protected var _scrollContainer:Canvas;
		protected var _itemList:List;
		protected var _showButtons:Boolean;
		
		public function ButtonScrollList(){
			super();
			verticalScrollPolicy = ScrollPolicy.OFF;
			horizontalScrollPolicy = ScrollPolicy.OFF;
			_scrollContainer = new Canvas();
			_upScroller = new Timer(SMOOTH_SCROLL_INTERVAL);
			_downScroller = new Timer(SMOOTH_SCROLL_INTERVAL);
			_showButtons = false;
			
			addEventListener(FlexEvent.CREATION_COMPLETE, handleCreationComplete);
			_upScroller.addEventListener(TimerEvent.TIMER, timedScrollUp);
			_downScroller.addEventListener(TimerEvent.TIMER, timedScrollDown);
		}
		
		private function get itemList():List{
			if(_itemList == null){ _itemList = new List(); }
			return _itemList;
		}
		public function get selectedItem():Object{ return _itemList.selectedItem; }
		
		override public function set width(value:Number):void{
			super.width = value;
			_scrollContainer.width = value;
		}
		override public function set percentWidth(value:Number):void{
			super.percentWidth = value;
			_scrollContainer.percentWidth = value;
		}
		override public function set height(value:Number):void{
			super.height = value;
			_scrollContainer.height = value;
		}
		override public function set percentHeight(value:Number):void{
			super.percentHeight = value;
			_scrollContainer.percentHeight = value;
		}
		public function set dataProvider(value:Object):void{ _itemList.dataProvider = value; }
		public function set rowCount(value:int):void{ _itemList.rowCount = value; }
		public function set selectedIndex(value:int):void{ _itemList.selectedIndex = value; }
		
		private function handleCreationComplete(event:FlexEvent):void{
			_itemList.height = _itemList.dataProvider.length * _itemList.rowHeight;
//			_itemList.width = _scrollContainer.width; 
		}
		
		private function handleScrollRollOver(event:MouseEvent):void{
			var btnTarget:ScrollListButton = event.target as ScrollListButton;
			if(btnTarget == _downScrollButton){
				_downScroller.start();
			}
			else if(btnTarget == _upScrollButton){
				_upScroller.start();
			}
		}
		
		private function handleScrollRollOut(event:MouseEvent):void{
			var btnTarget:ScrollListButton = event.target as ScrollListButton;
			if(btnTarget == _downScrollButton){
				_downScroller.stop();
			}
			else if(btnTarget == _upScrollButton){
				_upScroller.stop();
			}
		}
		
		private function handleScrollClick(event:MouseEvent):void{
			var btnTarget:ScrollListButton = event.target as ScrollListButton;
			if(btnTarget == _downScrollButton){
				quantumScrollDown();
			}
			else if(btnTarget == _upScrollButton){
				quantumScrollUp();
			}
		}
		
		override protected function createChildren():void{
			super.createChildren();
			
			//ideally children would all be created here, but it's possible API calls have already created them
			if(_itemList == null){ _itemList = new List(); }
			if(_downScrollButton == null){ _downScrollButton = new ScrollListButton(ScrollListButton.DOWN_DIRECTION); }
			if(_upScrollButton == null){ _upScrollButton = new ScrollListButton(ScrollListButton.UP_DIRECTION); }
			
			_scrollContainer.verticalScrollPolicy = ScrollPolicy.OFF;
			_scrollContainer.horizontalScrollPolicy = ScrollPolicy.OFF;
			_scrollContainer.styleName = "scrollContainer";
			
			_itemList.percentWidth = 100;
			//_itemList.percentHeight = 100;
			_itemList.editable = false;
			_itemList.verticalScrollPolicy = ScrollPolicy.OFF;
			_itemList.horizontalScrollPolicy = ScrollPolicy.OFF;
			_itemList.styleName = "itemList";
			
			_downScrollButton.percentWidth = 100;
			_downScrollButton.height = SCROLL_BUTTON_HEIGHT;
			
			_upScrollButton.percentWidth = 100;
			_upScrollButton.height = SCROLL_BUTTON_HEIGHT;
			
			_downScrollButton.addEventListener(MouseEvent.ROLL_OVER, handleScrollRollOver);
			_downScrollButton.addEventListener(MouseEvent.ROLL_OUT, handleScrollRollOut);
			_downScrollButton.addEventListener(MouseEvent.CLICK, handleScrollClick);
			_upScrollButton.addEventListener(MouseEvent.ROLL_OVER, handleScrollRollOver);
			_upScrollButton.addEventListener(MouseEvent.ROLL_OUT, handleScrollRollOut);
			_upScrollButton.addEventListener(MouseEvent.CLICK, handleScrollClick);
			
			_scrollContainer.addChild(_itemList);
			
			this.addChild(_scrollContainer);
		}
		
		public function addListItem(item:DisplayObject):DisplayObject{
			return itemList.addChild(item);
		}
				
		public function setListItemRenderer(itemRenderer:ClassFactory):void{
			itemList.itemRenderer = itemRenderer; 
		}
		
		public function addListEventListener(type:String, listener:Function, useCapture:Boolean=false, priority:int=0, useWeakReference:Boolean=false):void{
			itemList.addEventListener(type, listener, useCapture, priority, useWeakReference);
		}
		
		private function quantumScrollUp():void{
			_upScroller.stop();
			if(_scrollContainer.verticalScrollPosition >= 0){
				smoothScroll(false);
			}
		}
		
		private function quantumScrollDown():void{
			_downScroller.stop();
			if(_scrollContainer.verticalScrollPosition <= _scrollContainer.maxVerticalScrollPosition){
				smoothScroll(true);
			}
		}
		
		/**
		 * @param direction Boolean value indicating the direction of the scroll. True is positive direction (down) false is
		 * the negative direction (up)
		*/
		private function smoothScroll(direction:Boolean, step:Number=QUANTUM_SCROLL_STEP):void{
			var smoothScroller:Tween = new Tween(
				this,
				_scrollContainer.verticalScrollPosition,
				_scrollContainer.verticalScrollPosition + (direction ? step:-step),
				SMOOTH_SCROLL_INTERVAL,
				10,
				smoothScrollUpdateHandler,
				smoothScrollEndHandler
			);
			smoothScroller.easingFunction = Quadratic.easeOut;
		}
		
		private function smoothScrollUpdateHandler(value:String):void{
			var scrollPos:Number = Math.round(Number(value));
			if(scrollPos > _scrollContainer.maxVerticalScrollPosition){
				scrollPos = _scrollContainer.maxVerticalScrollPosition;
				_downScroller.stop();
			}
			if(scrollPos <= 0){
				scrollPos = 0;
				_upScroller.stop();
			}
			_scrollContainer.verticalScrollPosition = scrollPos;
		}
		
		private function smoothScrollEndHandler(value:String):void{
			//presence of this function prevents NPE in mx core. We don't need to do anything here.
		}
		
		private function timedScrollDown(event:TimerEvent):void{
			if(_scrollContainer.verticalScrollPosition < _scrollContainer.maxVerticalScrollPosition){
				smoothScroll(true, 5);
			}
		}
		
		private function timedScrollUp(event:TimerEvent):void{
			if(_scrollContainer.verticalScrollPosition > 0){
				smoothScroll(false, 5);
			}
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			if(unscaledHeight < itemList.height){
				showScrollButtons();
			}
			else{
				hideScrollButtons();
			}
		}
		
		private function showScrollButtons():void{
			if(!contains(_upScrollButton)){ addChildAt(_upScrollButton, 0); }
			if(!contains(_downScrollButton)){ addChildAt(_downScrollButton, 2); }
		}
		
		private function hideScrollButtons():void{
			if(contains(_upScrollButton)){ removeChild(_upScrollButton); }
			if(contains(_downScrollButton)){ removeChild(_downScrollButton); }
		}
	}
}
