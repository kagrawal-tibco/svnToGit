package com.tibco.be.views.user.components.drilldown.tabletree.view{
	
	import com.tibco.be.views.core.ui.controls.PageChooser;
	import com.tibco.be.views.core.ui.controls.PageChooserEvent;
	import com.tibco.be.views.user.components.drilldown.tabletree.events.PaginationEvent;
	
	import mx.containers.HBox;
	
	
	/**
	 * The ‘pagination row’ represents pagination information. It is an optional row which is shown
	 * only if the type table is showing part of the complete data set. It shows information about
	 * which row set is being shown as well as the total number of rows. It also contains buttons
	 * to navigate the pages in the data set.
	*/
	public class PaginationRow extends TableTreeRow{
		
		private var _pageChooser:PageChooser;
		private var _itemsPerPage:int = 1;
		private var _totalItemCount:int = 0;
		
		public function PaginationRow(parentRow:TableTreeRow, position:int, indentation:int=0){
			super(null, null, parentRow, position, indentation, false);
		}
		
		public function set rowsPerPage(value:int):void{
			if(_pageChooser != null){
				_pageChooser.itemsPerPage = value;
			}
			else{
				_itemsPerPage = value;
			}
		}
		//public function set currentPageNumber(value:int):void{ _currentPageNumber = value; }
		public function set totalRowCount(value:int):void{
			if(_pageChooser != null){
				_pageChooser.totalItemCount = value;
			}
			else{
				_totalItemCount = value;
			}
		}
		
		override protected function createContentContainer():void{
			_content = new HBox();
		}
		
		override protected function setContentContainerStyle():void{
			_content.styleName = "tablePaginationContent";
		}
		
		override protected function createChildren():void{
			super.createChildren();
			_pageChooser = new PageChooser();
			_pageChooser.itemDescriptor = "rows";
			_pageChooser.itemsPerPage = _itemsPerPage;
			_pageChooser.totalItemCount = _totalItemCount;
			_pageChooser.addEventListener(PageChooserEvent.PAGE_CHANGE, handlePageChange);
			addContent(_pageChooser);
		}
		
		protected function handlePageChange(event:PageChooserEvent):void{
			dispatchEvent(new PaginationEvent(_pageChooser.pagesFirstItemIndex));
		}
		
		override protected function drawHirearchalDottedLines():void{}
		
	}
}