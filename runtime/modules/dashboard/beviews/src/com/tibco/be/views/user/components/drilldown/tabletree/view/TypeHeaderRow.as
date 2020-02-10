package com.tibco.be.views.user.components.drilldown.tabletree.view{
	
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.ui.controls.BEVIconMenuButton;
	import com.tibco.be.views.core.ui.controls.PageChooser;
	import com.tibco.be.views.core.ui.controls.PageChooserEvent;
	import com.tibco.be.views.ui.containers.CommonHeaderBar;
	import com.tibco.be.views.user.components.chart.DataColumn;
	import com.tibco.be.views.user.components.drilldown.tabletree.events.GroupByEvent;
	import com.tibco.be.views.user.components.drilldown.tabletree.events.PaginationEvent;
	import com.tibco.be.views.user.components.drilldown.tabletree.events.RowActionEvent;
	import com.tibco.be.views.user.components.drilldown.tabletree.model.TableTreeRowConfig;
	import com.tibco.be.views.user.components.drilldown.tabletree.model.TableTreeRowNode;
	import com.tibco.be.views.user.components.drilldown.tabletree.model.TypeHeaderRowNode;
	import com.tibco.be.views.utils.BEVUtils;
	import com.tibco.be.views.utils.Logger;
	
	import flash.display.DisplayObject;
	
	import mx.controls.ComboBox;
	import mx.controls.Label;
	import mx.controls.Spacer;
	import mx.events.FlexEvent;
	import mx.events.ListEvent;
	import mx.events.ScrollEvent;
	
	/**
	 * The type header row is displayed above a type table. This row provides a group-by drop down
	 * as well as a configuration menu button
	*/
	public class TypeHeaderRow extends TableTreeRow{
		
		public static const MIN_COLUMN_WIDTH:int = 50;
		public static const MAX_COLUMN_WIDTH:int = 500;
		
		protected var _titleLabel:Label;
		protected var _rowPageChooser:PageChooser;
		protected var _groupByLabel:Label;
		protected var _groupBySelector:ComboBox;
		protected var _menuButton:BEVIconMenuButton;
		protected var _tableScrollRow:TableScrollRow;
		
		public function TypeHeaderRow(rowConfig:TableTreeRowConfig, data:TableTreeRowNode, parentRow:TableTreeRow, position:int, indentation:int=0, expandable:Boolean=false){
			super(rowConfig, data, parentRow, position, indentation, expandable);
		}
		
		public function get currentPageItemIndex():int{
			return _rowPageChooser == null ? 0:_rowPageChooser.pagesFirstItemIndex;
		}
		
		public function get typeRowDataNode():TypeHeaderRowNode{
			return _rowDataNode as TypeHeaderRowNode;
		}
		
		public function get selectedGroupBy():String{
			if(_groupBySelector == null){ return TypeHeaderRowNode.NO_GROUPBY_STRING; }
			return String(_groupBySelector.selectedItem);
		}
		
		public function get tableHScrollPosition():int{
			var tableRow:ITableTreeTableRow = null;
			for each(var child:TableTreeRow in _childRows){
				if(child is ITableTreeTableRow){
					tableRow = child as ITableTreeTableRow;
					break;
				}
			}
			if(tableRow == null){ return 0; }
			return tableRow.tableRowHScrollPosition;
		}
		
		public function set tableHScrollPosition(value:int):void{
			for each(var child:TableTreeRow in _childRows){
				if(child is ITableTreeTableRow){
					(child as ITableTreeTableRow).tableRowHScrollPosition = value;
				}
			}
		}
		
		override protected function createChildren():void{
			super.createChildren();
			
			_titleLabel = new Label();
			if(_rowDataNode.dataColumns != null){ //based off of a datarow xml node -> use datacolumn.displayvalue as title
				for each(var dc:DataColumn in _rowDataNode.dataColumns){ //should only be one datacolumn, but no easier way to get it
					_titleLabel.text = dc.displayValue; 
				}
			}
			else{ //textmodel xml node -> title will have been set by the TableTreeRowNode constructor
				_titleLabel.text = _rowDataNode.title;
			}
			addContent(_titleLabel);
			
			var middleSpacer:Spacer = new Spacer();
			middleSpacer.percentWidth = 100;
			addContent(middleSpacer);
			
			if(typeRowDataNode.groupByItems.length > 0){
				_groupByLabel = new Label();
				_groupByLabel.text = "Group By:";
				addContent(_groupByLabel);
				
				_groupBySelector = new ComboBox();
				_groupBySelector.height = 16;
				_groupBySelector.dataProvider = typeRowDataNode.groupByItems;
				addContent(_groupBySelector);
			}
			
			setChildStyleNames();
		}
		
		protected function setChildStyleNames():void{
			if(_titleLabel != null){ _titleLabel.styleName = "tableTreeTypeHeaderRowTitle"; }
			if(_groupByLabel != null){ _groupByLabel.styleName = "tableTreeTypeHeaderGroupByPrompt"; }
			if(_groupBySelector != null){ _groupBySelector.styleName = "tableTreeTypeHeaderGroupBySelector"; }
		}
		
		override protected function createContentContainer():void{
			_content = new CommonHeaderBar();
		}
		
		override protected function setContentContainerStyle():void{
			_content.styleName = "tableTreeTypeRowContent";
		}
		
		override protected function handleCreationComplete(event:FlexEvent):void{
			super.handleCreationComplete(event);
			if(_groupBySelector != null){ _groupBySelector.addEventListener(ListEvent.CHANGE, handleGroupByChange); }
			//trigger update display list so that column widths can be calculated and applied
			invalidateDisplayList();
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			updateTableDisplay();
		}
		
		/**
		 * Determines whether or not the pagination control is needed and updates this TypeHeaderRow
		 * accordingly.
		 * 
		 * @param textModel The textmodel XML node that is used for this row.
		 * @return Flag indicating whether pagination is enabled and the control was added to the display.
		*/
		public function updatePaginationControl(textModel:XML=null):Boolean{
			if(textModel != null){ typeRowDataNode.updatePaginationData(textModel); }
			var nDisplayedDataRows:int = childRows.length;
			 //don't count leading, non-data rows
			for(var i:int = 0; i < childRows.length && !(childRows[i] is TableDataRow); nDisplayedDataRows--, i++){}
			if(nDisplayedDataRows == 0 || nDisplayedDataRows == typeRowDataNode.totalRowsAvailable){
				if(_rowPageChooser != null && _content.contains(_rowPageChooser)){
					removeContent(_rowPageChooser);
				}
				return false;
			}
			if(_rowPageChooser == null){
				_rowPageChooser = new PageChooser();
				_rowPageChooser.itemsPerPage = nDisplayedDataRows;
				_rowPageChooser.totalItemCount = typeRowDataNode.totalRowsAvailable;
				_rowPageChooser.itemDescriptor = "rows";
				_rowPageChooser.currentState = PageChooser.MINIMAL_CONTROLS;
				_rowPageChooser.addEventListener(PageChooserEvent.PAGE_CHANGE, handlePageChange);
			}
			if(!_content.contains(_rowPageChooser)){
				addContentAt(_rowPageChooser, getContentIndex(_groupByLabel));
				return true;
			}
			return false;
		}
		
		protected function handlePageChange(event:PageChooserEvent):void{
			dispatchEvent(new PaginationEvent(_rowPageChooser.pagesFirstItemIndex));
		}
		
		protected function handleGroupByChange(event:ListEvent):void{
			dispatchEvent(new GroupByEvent(this, String(_groupBySelector.selectedItem)));
		}
		
		protected function updateTableDisplay():void{
			//Locate the TableHeaderRow child and validate it to enable further processing
			var childTableHeaderRow:TableHeaderRow = null;
			for each(var childRow:TableTreeRow in _childRows){
				if(childRow is TableHeaderRow){
					childTableHeaderRow = childRow as TableHeaderRow;
					childTableHeaderRow.validateNow();
					break;
				}
			}
			if(childTableHeaderRow == null){
				//this is ok and will happen regularly when a type row is not expanded or is only
				//displaying group-by row children
				Logger.log(
					DefaultLogEvent.DEBUG,
					BEVUtils.getClassName(this) + ".updateTableDisplay - No child TableHeaderRow for this TypeHeaderRow."
				);
				return;
			}
			var contentScrollWidth:int = childTableHeaderRow.contentWidth;
			
			//Ensure the measured columns widths fit the min and max bounds
			var cells:Array = childTableHeaderRow.getRowCells();
			var columnWidthsToSet:Array = new Array(cells.length);
			var i:int = 0;
			var sumOfWidths:int = 0;
			for each(var headerCell:TableHeaderCell in cells){
				var width2Set:int = Math.max(Math.min(headerCell.measuredWidth, MAX_COLUMN_WIDTH), MIN_COLUMN_WIDTH);
				columnWidthsToSet[i++] = width2Set;
				sumOfWidths += width2Set;
			}
			
			//If the columns don't take up the whole row, scale their widths up to fit the row
			if(contentScrollWidth > sumOfWidths){
				for(i = 0; i < columnWidthsToSet.length; i++){
					columnWidthsToSet[i] = (columnWidthsToSet[i]/sumOfWidths) * contentScrollWidth;
				}
			}
			
			//Apply column widths to all cells
			var nextX:int = 0;
			for each(childRow in _childRows){
				var tableRow:ITableTreeTableRow = childRow as ITableTreeTableRow;
				if(tableRow == null){ continue; }
				cells = tableRow.getRowCells();
				nextX = 0;
				for(i = 0; i < cells.length; i++){
					(cells[i] as DisplayObject).width = columnWidthsToSet[i]-1;
					(cells[i] as DisplayObject).x = nextX;
					nextX += columnWidthsToSet[i];
				}
			}
			
			//Determine whether or not horizontal scrolling is required to view all columns
			if((nextX-1) > contentScrollWidth){
				if(_tableScrollRow == null){
					_tableScrollRow = new TableScrollRow(this, 0, indentation+1);
					_tableScrollRow.addEventListener(ScrollEvent.SCROLL, handleColumnScroll);
				}
				_tableScrollRow.maxScrollPosition = (nextX-1) - contentScrollWidth;
				var addRowAction:RowActionEvent = new RowActionEvent(RowActionEvent.ADD_ROW, _tableScrollRow, this, 0);
				dispatchEvent(addRowAction);
			}
			else if(_tableScrollRow != null){
				//may or may not already exist in the component removing the row, up to said container to determine whether removal is required
				var removeRowAction:RowActionEvent = new RowActionEvent(RowActionEvent.REMOVE_ROW, _tableScrollRow);
				dispatchEvent(removeRowAction);
			}
			 
		}
		
		protected function handleColumnScroll(event:ScrollEvent):void{
			tableHScrollPosition = event.position;
		}
		
//		protected function redistributeColWidths():void{
//			var avgColWidths:Array = [];
//			var cells:Array = [];
//			var tableRow:ITableTreeTableRow;
//			var childRow:TableTreeRow;
//			var i:int = 0;
//			
//			//Calculate the set of average column content widths (the width the column "needs")
//			var rowIndex:int = 0;
//			var rowContentWidth:Number = 0;
//			for each(childRow in _childRows){
//				tableRow = childRow as ITableTreeTableRow;
//				if(tableRow == null){ continue; }
//				childRow.validateNow(); //this is necessary else last column gets messed up
//				rowContentWidth = tableRow.contentWidth;
//				cells = tableRow.getRowCells();
//				var colIndex:int = 0;
//				for each(var cell:ITableTreeTableCell in cells){
//					var cellWidth:Number = cell.contentWidth;
//					if(avgColWidths.length == colIndex){
//						//we store {column index, width} inorder to track what order the columns go
//						//in after sorting the avgColWidths array by width
//						avgColWidths.push({"colIndex":colIndex, "width":cellWidth});
//					}
//					else{
//						avgColWidths[colIndex].width = ((rowIndex)*avgColWidths[colIndex].width + cellWidth) / (rowIndex+1);
//					}
//					colIndex++;
//				}
//				rowIndex++;
//			}
//			
//			var sumOfAvgColWidths:Number = 0;
//			for each(var avgColSizeObj:Object in avgColWidths){
//				sumOfAvgColWidths += avgColSizeObj.width;
//			}
//			if(cells.length != avgColWidths.length){
//				Logger.log(DefaultLogEvent.WARNING, "Couldn't distribute table column widths due to inconsistent sizes of size and cell arrays.");
//				return;
//			}
//			
//			//Calculate the width that will be actually given to each column (similar approach taken by BEVLegend)
//			avgColWidths.sortOn("width", Array.NUMERIC);
//			var colWidthsToSet:Array = new Array(avgColWidths.length);
//			var surplusWidth:Number = 0;
//			for(i = 0; i < avgColWidths.length; i++){
//				var colWidthObj:Object = avgColWidths[i];
//				var normalizedColWidth:Number = rowContentWidth*(colWidthObj.width/sumOfAvgColWidths);
//				var widthToSet:Number = 0;
//				if(normalizedColWidth > colWidthObj.width){
//					surplusWidth += normalizedColWidth - colWidthObj.width;
//					widthToSet = colWidthObj.width;
//				}
//				else{
//					widthToSet = normalizedColWidth + surplusWidth/(avgColWidths.length - (i+1));
//					surplusWidth -= surplusWidth/(avgColWidths.length - (i+1));
//				}
//				colWidthsToSet[colWidthObj.colIndex] = widthToSet;
//			}
//			
//			//if there is remaining surplus width, divide it evenly among columns
//			if(surplusWidth > 0){
//				for(i = 0; i < colWidthsToSet.length; i++){
//					colWidthsToSet[i] += surplusWidth/colWidthsToSet.length;
//				}
//			}
//			
//			//apply the calculated widths
//			for each(childRow in _childRows){
//				tableRow = childRow as ITableTreeTableRow;
//				if(tableRow == null){ continue; }
//				cells = tableRow.getRowCells();
//				var nextX:int = 0;
//				for(i = 0; i < cells.length; i++){
//					(cells[i] as DisplayObject).width = colWidthsToSet[i]-1;
//					(cells[i] as DisplayObject).x = nextX;
//					(cells[i] as UIComponent).validateNow();
//					nextX += colWidthsToSet[i];
//				}
//			}
//			
//			//2nd pass - apply calculated column widths
////			for each(childRow in _childRows){
////				tableRow = childRow as ITableTreeTableRow;
////				if(tableRow == null){ continue; }
////				cells = tableRow.getRowCells();
////				var nextX:int = 0;
////				for(var cellIndex:int = 0; cellIndex < avgColWidths.length; cellIndex++){
////					cellWidth = tableRow.contentWidth * (avgColWidths[cellIndex].width/sumOfAvgColWidths) - 1;
////					(cells[cellIndex] as DisplayObject).width = cellWidth;
////					(cells[cellIndex] as DisplayObject).x = nextX;
////					nextX += cellWidth+1;
////				}
////			}
//			
//		}
				
	}
}