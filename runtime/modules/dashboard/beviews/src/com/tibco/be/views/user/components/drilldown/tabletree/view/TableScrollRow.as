package com.tibco.be.views.user.components.drilldown.tabletree.view{

	import mx.containers.HBox;
	import mx.core.ScrollPolicy;
	import mx.events.FlexEvent;
	import mx.events.ScrollEvent;
	
	public class TableScrollRow extends TableTreeRow{
		
//		Using this row as a wrapper for a HScrollBar resulted in problems. The bigest of which was
//		that the scroll thumb image was not being scaled properly. Not only did this look bad, but
//		it also caused areas of the scroll bar (towards the end of the bar) to essentially become
//		dead zones where nothing would happen if the thumb was moved over it. The solution was to
//		manipulate _content and utilize its HScrollBar that's mostly under Flex control.
		
		protected var _maxScrollPosition:int;
		/** Resizing causes scrollbar reset, this is used to override that reset */
		protected var _overrideScrollPosition:int;
		
		public function TableScrollRow(parentRow:TableTreeRow, position:int, indentation:int=0){
			super(null, null, parentRow, position, indentation, false);
			height=9;
		}
		
		public function set maxScrollPosition(value:int):void{
			_maxScrollPosition = value;
			invalidateDisplayList();
		}
		
		override protected function createContentContainer():void{
			_content = new HBox();
			_content.horizontalScrollPolicy = ScrollPolicy.ON;
			_content.setStyle("horizontalScrollBarStyleName", "tableTreeTableColumnScrollBar");
			_content.addEventListener(FlexEvent.CREATION_COMPLETE, handleContentCreated);
		}
		
		override protected function setContentContainerStyle():void{
			_content.styleName = "tableScrollerContent";
		}
		
		protected function handleScroll(event:ScrollEvent):void{
			_overrideScrollPosition = event.position;
			dispatchEvent(event);
		}
		
		protected function handleContentCreated(event:FlexEvent):void{
			_content.removeEventListener(FlexEvent.CREATION_COMPLETE, handleContentCreated);
			_content.horizontalScrollBar.height = 9;
			_content.horizontalScrollBar.maxScrollPosition = _maxScrollPosition;
			_content.horizontalScrollBar.addEventListener(ScrollEvent.SCROLL, handleScroll);
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			_content.width = unscaledWidth - _content.x;
			_content.horizontalScrollBar.maxScrollPosition = _maxScrollPosition;
			_content.horizontalScrollPosition = _overrideScrollPosition;
			super.updateDisplayList(unscaledWidth, unscaledHeight);
		}
		
	}
}