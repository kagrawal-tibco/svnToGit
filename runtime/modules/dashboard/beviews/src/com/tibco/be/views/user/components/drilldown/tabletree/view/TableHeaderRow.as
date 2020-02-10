package com.tibco.be.views.user.components.drilldown.tabletree.view{
	
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.ui.containers.GradientCanvas;
	import com.tibco.be.views.user.components.chart.DataColumn;
	import com.tibco.be.views.user.components.drilldown.tabletree.events.TableSortEvent;
	import com.tibco.be.views.user.components.drilldown.tabletree.model.TableTreeRowConfig;
	import com.tibco.be.views.user.components.drilldown.tabletree.model.TableTreeRowNode;
	import com.tibco.be.views.utils.BEVUtils;
	import com.tibco.be.views.utils.Logger;
	
	import mx.containers.HBox;
	import mx.core.ScrollPolicy;
	import mx.events.FlexEvent;
	
	/**
	 * The table header row displayed as the first row in a type table. This row displays the set
	 * the table's column headers and allows for sorting on a column as well as resizing columns.
	*/
	public class TableHeaderRow extends TableTreeRow implements ITableTreeTableRow{
		
		protected static const DEFAULT_STYLE:String = "tableTreeDataHeaderContent";
		
		/** Collection of the header cells contained in this row. */
		protected var _headerCells:Array;
		/** An empty cell used as a place holder for the row menu */
		protected var _menuCell:GradientCanvas;
		/** The field name used when a sort is applied */
		protected var _sortField:String;
		/** The direction (ascending/descending) applied to the sorted column */
		protected var _sortDirection:String;
		/** Container used to display cells contianing header data */		
		protected var _headerCellContainer:GradientCanvas;
		/** Property to track what scroll position the row should be using */
		protected var _hScrollPosition:int;
		
		public function TableHeaderRow(rowConfig:TableTreeRowConfig, data:TableTreeRowNode, parentRow:TableTreeRow, position:int, indentation:int=0){
			super(rowConfig, data, parentRow, position, indentation, false);
			_sortField = "";
			_sortDirection = TableHeaderCell.NO_SORT;
			_headerCells = [];
		}
		
		override public function get contentWidth():Number{
			return _headerCellContainer == null ? 0:_headerCellContainer.width;
		}
		public function get sortField():String{ return _sortField; }
		public function get sortDirection():String{ return _sortDirection; }
		public function get tableRowHScrollPosition():int{
			if(_headerCellContainer == null){ return _hScrollPosition; }
			return _headerCellContainer.horizontalScrollPosition;
		}
		
		public function set tableRowHScrollPosition(value:int):void{
			if(_hScrollPosition != value){
				_hScrollPosition = value;
				invalidateDisplayList();
			}
		}
		
		public function getRowCells():Array{ return _headerCells; }
		
		override protected function createContentContainer():void{
			_content = new HBox();
			_content.setStyle("backgroundColor", 0xFFFFFF);
			_content.setStyle("horizontalGap", 0);
			_content.horizontalScrollPolicy = ScrollPolicy.OFF;
		}
				
		override protected function setContentContainerStyle():void{
			//the base HBox has no style
		}
		
		override protected function createChildren():void{
			super.createChildren();
			
			_headerCellContainer = new GradientCanvas();
			_headerCellContainer.percentWidth = 100;
			_headerCellContainer.percentHeight = 100;
			_headerCellContainer.horizontalScrollPolicy = ScrollPolicy.OFF;
			_headerCellContainer.verticalScrollPolicy = ScrollPolicy.OFF;
			_headerCellContainer.styleName = DEFAULT_STYLE;
			
			for each(var col:DataColumn in _rowDataNode.dataColumns){
				var cell:TableHeaderCell = createHeaderCell(col);
				_headerCellContainer.addChild(cell);
				_headerCells.push(cell);
			}
			addContent(_headerCellContainer);
			
			if(_headerCells.length > 0){ (_headerCells[0] as TableHeaderCell).setStyle("borderSides", ""); }
			
			//menuCell here is just extra spacing to keep aligned with TableDataRows
			_menuCell = new GradientCanvas();
			_menuCell.width = 9;
			_menuCell.percentHeight = 100;
			_menuCell.setStyle("backgroundGradientStart", 0xEFEFEF);
			_menuCell.setStyle("backgroundGradientEnd", 0xDCDCDC);
			_menuCell.setStyle("borderStyle", "solid");
			_menuCell.setStyle("borderColor", 0x9F9F9F);
			_menuCell.setStyle("borderSides", "top bottom right");
			addContent(_menuCell);
			//don't add _menuCell to _headerCells as it will mess up data column width distribution
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			_menuCell.x = _content.width - _menuCell.width;
			_headerCellContainer.validateNow();
			if(_headerCellContainer.horizontalScrollPosition != _hScrollPosition){
				_headerCellContainer.horizontalScrollPosition = _hScrollPosition;
			}
		}
		
		private function createHeaderCell(headerDataColumn:DataColumn):TableHeaderCell{
			var cell:TableHeaderCell = new TableHeaderCell();
			cell.titleText = headerDataColumn.displayValue == "" ? headerDataColumn.valueObj.toString():headerDataColumn.displayValue;
			cell.sortDirection = headerDataColumn.sortDirection;
			cell.viewConfig = _rowConfig.getColumnConfigById(headerDataColumn.colID);
			if(cell.sortDirection != TableHeaderCell.NO_SORT){
				_sortField = cell.titleText;
				_sortDirection = cell.sortDirection;
			}
			cell.addEventListener(FlexEvent.CREATION_COMPLETE, handleCellCreationComplete);
			return cell;
		}
		
		private function handleCellCreationComplete(event:FlexEvent):void{
			var cell:TableHeaderCell = event.target as TableHeaderCell;
			if(cell == null){
				Logger.log(DefaultLogEvent.WARNING, BEVUtils.getClassName(this) + ".handleCellCreationComplete - Null target cell.");
				return;
			}
			cell.removeEventListener(FlexEvent.CREATION_COMPLETE, handleCellCreationComplete);
			cell.addEventListener(TableSortEvent.SORT_APPLIED, handleSortApplied);
		}
		
		private function handleSortApplied(event:TableSortEvent):void{
			_sortField = event.sortField;
			_sortDirection = event.sortDirection;
			var tableSortEvent:TableSortEvent = new TableSortEvent(event.sortField, event.sortDirection);
			dispatchEvent(tableSortEvent);
		}
	}
}