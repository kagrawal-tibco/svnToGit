package com.tibco.be.views.user.components.drilldown.tabletree.view{
	
	import com.tibco.be.views.core.ui.DynamicParamsResolver;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.actions.IActionContextProvider;
	import com.tibco.be.views.core.ui.controls.BEVIconMenuButton;
	import com.tibco.be.views.core.ui.events.BEVMenuEvent;
	import com.tibco.be.views.user.components.chart.DataColumn;
	import com.tibco.be.views.user.components.drilldown.tabletree.model.TableTreeRowConfig;
	import com.tibco.be.views.user.components.drilldown.tabletree.model.TableTreeRowNode;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	import mx.containers.Canvas;
	import mx.containers.HBox;
	import mx.core.ScrollPolicy;
	import mx.events.FlexEvent;
	import mx.utils.StringUtil;
	
	/**
	 * The table data row represents a single instance of a type. It is selectable. Each type data
	 * row can be expanded to view one or more type header rows.
	*/
	public class TableDataRow extends TableTreeRow implements ITableTreeTableRow, IActionContextProvider{
		
		protected static const DEFAULT_CONTENT_STYLE:String = "tableTreeDataRowContent";
		protected static const ALTERNATE_CONTENT_STYLE:String = "tableTreeDataRowContentAlt";
		protected static const ROLL_OVER_CONTENT_STYLE:String = "tableTreeDataRowContentOver";
		protected static const DEFAULT_BUTTON_CONTAINER_STYLE:String = "tableTreeDataRowButtonContainer";
		protected static const ALTERNATE_BUTTON_CONTAINER_STYLE:String = "tableTreeDataRowButtonContainerAlt";
		protected static const ROLL_OVER_BUTTON_CONTAINER_STYLE:String = "tableTreeDataRowButtonContainerOver";
		
		/** Collection of the data cells contained in this row. */
		protected var _dataCells:Array;
		/** An empty cell used as a place holder for the row menu */
		protected var _menuCell:HBox;
		/** Button displayed when there are actions available for this row, shown on mouse-over */
		protected var _rowMenuButton:BEVIconMenuButton;
		/** Flag to aide in the show/hide functionality of the row menu */
		protected var _isMouseOut:Boolean;
		/** Style name for this row. Data rows alternate background colors, this tracks which is used */
		protected var _contentStyleName:String;
		/** Style name for the container of the row actions button */
		protected var _buttonContainerStyleName:String;
		/** Container used to display cells contianing data */
		protected var _dataCellContainer:Canvas;
		/** Property to track what scroll position the row should be using */
		protected var _hScrollPosition:int;
		
		public function TableDataRow(rowConfig:TableTreeRowConfig, data:TableTreeRowNode, parentRow:TableTreeRow, position:int, indentation:int=0){
			super(rowConfig, data, parentRow, position, indentation, true);
			_dataCells = [];
			_contentStyleName = DEFAULT_CONTENT_STYLE;
			_buttonContainerStyleName = DEFAULT_BUTTON_CONTAINER_STYLE;
		}
		
		override public function get contentWidth():Number{
			return _dataCellContainer == null ? 0:_dataCellContainer.width;
		}
		public function get tableRowHScrollPosition():int{
			if(_dataCellContainer == null){ return _hScrollPosition; }
			return _dataCellContainer.horizontalScrollPosition;
		}
		
		public function set tableRowHScrollPosition(value:int):void{
			if(_hScrollPosition != value){
				_hScrollPosition = value;
				invalidateDisplayList();
			}
		}
		
		public function getRowCells():Array{ return _dataCells; }
		
		override protected function createContentContainer():void{
			_content = new HBox();
			_content.setStyle("horizontalGap", 0);
			_content.horizontalScrollPolicy = ScrollPolicy.OFF;
		}
		
		override protected function setContentContainerStyle():void{
			//the base HBox has no style
		}
		
		override protected function createChildren():void{
			super.createChildren();
			
			_rowMenuButton = new BEVIconMenuButton(this);
			_rowMenuButton.height = 23;
			_rowMenuButton.width = 9;
			_rowMenuButton.styleName = "tableTreeDataRowButton";
			_rowMenuButton.addEventListener(BEVMenuEvent.BEVMENU_HIDE, handleRowMenuHide);
			
			_dataCellContainer = new Canvas();
			_dataCellContainer.percentWidth = 100;
			_dataCellContainer.percentHeight = 100;
			_dataCellContainer.horizontalScrollPolicy = ScrollPolicy.OFF;
			_dataCellContainer.verticalScrollPolicy = ScrollPolicy.OFF;
			_dataCellContainer.styleName = DEFAULT_CONTENT_STYLE;
			_dataCellContainer.addEventListener(FlexEvent.CREATION_COMPLETE, handleDataCellContainerCreated);
			
			for each(var col:DataColumn in _rowDataNode.dataColumns){
				var cell:TableDataCell = createDataCell(col);
				_dataCellContainer.addChild(cell);
				_dataCells.push(cell);
			}
			addContent(_dataCellContainer);
			
			//remove the left border side as it will be drawn by the contianing row
			if(_dataCells.length > 0){ (_dataCells[0] as TableDataCell).setStyle("borderSides", ""); }
			
			//add an additional cell to be used to display a menu button if actionConfig exists
			_menuCell = new HBox();
			_menuCell.width = 9;
			_menuCell.percentHeight = 100;
			_menuCell.styleName = "tableTreeDataRowButtonContainer";
			_menuCell.addEventListener(FlexEvent.CREATION_COMPLETE, handleMenuCellCreated);
			addContent(_menuCell);
			//don't add _menuCell to _dataCells as it will mess up data column width distribution
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			if(_dataCellContainer.horizontalScrollPosition != _hScrollPosition){
				_dataCellContainer.horizontalScrollPosition = _hScrollPosition;
			}
		}
		
		public function useAlternateContentStyle():void{
			_contentStyleName = ALTERNATE_CONTENT_STYLE;
			if(_dataCellContainer != null){ _dataCellContainer.styleName = _contentStyleName; }
			_buttonContainerStyleName = ALTERNATE_BUTTON_CONTAINER_STYLE;
			if(_menuCell != null){ _menuCell.styleName = _buttonContainerStyleName; }
		}
		
		override public function setBorderSides(sides:String):void{
			_content.setStyle("borderSides", sides);
		}
		
		override public function addBorderSides(sidesToAdd:String):void{
			var sides:Array = sidesToAdd.split(" ");
			var currentSides:Array = String(_dataCellContainer.getStyle("borderSides")).split(" ");
			currentSides = currentSides.concat(sides);
			currentSides = currentSides.filter(
				function(element:*, index:int, arr:Array):Boolean{
					return currentSides.indexOf(element) == index;
				}
			);
			var sidesToSet:String = StringUtil.trim(currentSides.join(" "));
			_dataCellContainer.setStyle("borderSides", currentSides.join(" "));
		}
		
		override public function removeBorderSides(sidesToRemove:String):void{
			var sides:Array = sidesToRemove.split(" ");
			var currentSides:Array = String(_dataCellContainer.getStyle("borderSides")).split(" ");
			currentSides = currentSides.filter(
				function(element:*, index:int, arr:Array):Boolean{
					return sides.indexOf(element) < 0;
				}
			);
			var sidesToSet:String = StringUtil.trim(currentSides.join(" "));
			_dataCellContainer.setStyle("borderSides", currentSides.join(" "));
		}
		
		private function handleDataCellContainerCreated(event:FlexEvent):void{
			_dataCellContainer.styleName = _contentStyleName;
		}
		
		private function handleMenuCellCreated(event:FlexEvent):void{
			_menuCell.styleName = _buttonContainerStyleName;
		}
		
		override protected function handleMouseRollOver(mouseEvent:MouseEvent):void{
			_dataCellContainer.styleName = ROLL_OVER_CONTENT_STYLE;
			_menuCell.styleName = ROLL_OVER_BUTTON_CONTAINER_STYLE;
//			_menuCell.addChild(_rowMenuButton);
			if(_rowConfig.actionConfig.children().length() > 0){
				_rowMenuButton.menuConfig = _rowConfig.actionConfig;
				_menuCell.addChild(_rowMenuButton);
			}
			_isMouseOut = false;
		}
		
		override protected function handleMouseRollOut(mouseEvent:MouseEvent):void{
			if(_rowMenuButton.menuShowing){
				_isMouseOut = true;
				return;
			}
			_isMouseOut = false;
			_dataCellContainer.styleName = _contentStyleName;
			_menuCell.styleName = _buttonContainerStyleName;
			if(_menuCell.contains(_rowMenuButton)){
				_menuCell.removeChild(_rowMenuButton);
			}
		}
		
		override public function handleRowExpansion(rowNodes:Array):void{
			if(rowNodes.length == 0){
				shouldDrawHierarchalConnection = true;
				removeExpansionButton();
			}
			else{
				addBorderSides("bottom");
				var nSib:TableTreeRow = nextSibling;
				if(nSib != null){ nSib.addBorderSides("top"); }
			}
			invalidateDisplayList();
		}
		
		override public function handleRowCollapse():void{
			var nSib:TableTreeRow = nextSibling;
			if(nSib != null){ nSib.removeBorderSides("top"); }
			super.handleRowCollapse();
		}
		
		public function getActionContext(parentActionConfig:XML, actionConfig:XML):ActionContext{
			var resolvingMap:DynamicParamsResolver = new DynamicParamsResolver();
			resolvingMap.setDynamicParamValue(DynamicParamsResolver.CURRENTDATAROW_ID_PARAM, _rowDataNode.id);
			resolvingMap.setDynamicParamValue(DynamicParamsResolver.CURRENTDATAROW_LINK_PARAM, _rowDataNode.link);
		 	return new ActionContext(this, resolvingMap);
		}
		
		private function createDataCell(cellDataCoumn:DataColumn):TableDataCell{
			var cell:TableDataCell = new TableDataCell();
			cell.text = cellDataCoumn.displayValue;
			cell.viewConfig = _rowConfig.getColumnConfigById(cellDataCoumn.colID);
			if(cellDataCoumn.link != ""){
				cell.linkUrl = cellDataCoumn.link;
			}
			return cell;
		}
		
		private function handleRowMenuHide(event:Event):void{
			if(_isMouseOut){
				handleMouseRollOut(null);
			}
		}
		
	}
}