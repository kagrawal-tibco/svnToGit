package com.tibco.be.views.user.components.drilldown.tabletree{
	
	import com.tibco.be.views.core.events.EventBus;
	import com.tibco.be.views.core.events.EventBusEvent;
	import com.tibco.be.views.core.events.EventTypes;
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.core.events.tasks.ConfigResponseEvent;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.user.components.drilldown.events.BEVDrillDownRequest;
	import com.tibco.be.views.user.components.drilldown.tabletree.actions.ShowDrillDownExportDialogAction;
	import com.tibco.be.views.user.components.drilldown.tabletree.events.*;
	import com.tibco.be.views.user.components.drilldown.tabletree.model.*;
	import com.tibco.be.views.user.components.drilldown.tabletree.view.*;
	import com.tibco.be.views.utils.BEVUtils;
	import com.tibco.be.views.utils.Logger;
	
	import flash.display.DisplayObject;
	import flash.utils.Dictionary;
	
	import mx.containers.VBox;
	import mx.effects.Fade;
	import mx.effects.Parallel;
	import mx.events.EffectEvent;
	import mx.events.FlexEvent;
	import mx.managers.CursorManager;

	public class BEVTableTreeComponent extends BEVComponent{
		
		//State variables used to determine proper course of action upon receiving the response
		protected static const ACTION_ROW:String = "actionRow_stateVar";
		protected static const TABLE_TREE_COMMAND:String = "tableTreeCommand_stateVar";
		
		//additional component properties
		protected var _seriesid:String;
		protected var _tupid:String;
		protected var _isExportEnabled:Boolean;
		
		//component data
		protected var _model:BEVTableTreeComponentModel;
		
		//view data
		protected var _rowViewConfigs:Dictionary;
		
		//view elements
		protected var _pendingRows:Array;
		protected var _rowContainer:VBox;
		
		//Flag indicating whether a newly created data row should use alternate background styling
		//(Simple way of doing this, but not all that "correct")
		private var _useAltDataRowBackground:Boolean;
		
		public function BEVTableTreeComponent(){
			super();
			_autonomousLoading = false;
			_isExportEnabled = false;
			_model = new BEVTableTreeComponentModel();
			_rowViewConfigs = new Dictionary();
			_pendingRows = [];
			_useAltDataRowBackground = false;
			styleName = "tableTreeComponent";
			//instantiate the show export dialog action to force its registration 
			var action:ShowDrillDownExportDialogAction = new ShowDrillDownExportDialogAction();
		}
		
		protected function get isExportEnabled():Boolean{ return _isExportEnabled; }
		
		protected function setExportEnabled(actionConfig:XML):void{
			_isExportEnabled = false;
			if(actionConfig.actionconfig != undefined){
				var exportConfigList:XMLList = actionConfig.actionconfig.(text == "Export");
				if(exportConfigList.length() > 0){
					var exportConfig:XML = exportConfigList[0] as XML;
					_isExportEnabled = exportConfig.@disabled == "false";
				}
			}
		}
		
		override public function init():void{
			super.init();			
			var getTreeDataReq:BEVDrillDownRequest = new BEVDrillDownRequest(BEVDrillDownRequest.GET_TABLETREE_DATA, this);
			getTreeDataReq.addStateVariable(TABLE_TREE_COMMAND, TableTreeCommands.INITIALIZE);
			EventBus.instance.dispatchEvent(getTreeDataReq);
		}
		
		override protected function createChildren():void{
			_rowContainer = new VBox();
			_rowContainer.x = 10;
			_rowContainer.y = 10;
			_rowContainer.styleName = "tableTreeRowContainer";
			
			//if addRow was called before createChildren, there will be rows that need to be added
			//to the row container
			for(var i:int = 0; i < _pendingRows.length; i++){
				_rowContainer.addChild(_pendingRows[i]);
			}
			_pendingRows = [];
			
			addChild(_rowContainer);
			super.createChildren();
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			_rowContainer.width = unscaledWidth - 20;
			_rowContainer.height = unscaledHeight - 20;
			var lastDataRow:TableDataRow = _rowContainer.getChildAt(_rowContainer.numChildren-1) as TableDataRow;
			if(lastDataRow){
				lastDataRow.addBorderSides("bottom");
			}
		}
		
 		//Currently there is no config for this component so this function should neither be called
 		//nor do anything if something calls it
		override protected function handleConfigSet(compConfigXML:XML):void{
			trace("BEVTableTreeComponent.handleConfigSet - Don't use this. No config for this component.");
		}
		
		//Called when property componentData is set in the parent BEVComponent class
		override protected function handleDataSet(dataXML:XML):void{
			//update model
			_model.setData(dataXML);
			
			//update view via depth-first traversal through model, creating and adding view rows
			if(_rowContainer != null){ _rowContainer.removeAllChildren(); }
			buildRowView(null, _model.rootRow, 0);
		}
				
		/**
		 * Handles component data updates.
		 * @param componentData Represents the update data of the component 
		*/ 
		override public function updateData(componentData:XML):void{
			trace("BEVTableTreeComponent.updateData - Don't use this. All data should come via GET request and use handleSetData.");
		}
		
		private function parseRowViewConfigs(drillDownModel:XML):void{
			BEVUtils.clearDictionary(_rowViewConfigs);
			if(
			  drillDownModel == null ||
			  drillDownModel.textmodel == undefined ||
			  drillDownModel.textmodel.textconfig == undefined ||
			  drillDownModel.textmodel.textconfig.rowconfig == undefined)
			{
				return;
			}
			for each(var row:XML in drillDownModel.textmodel.textconfig.rowconfig){
				var rowId:String = new String(row.@id);
				_rowViewConfigs[rowId] = TableTreeRowConfig.buildFromRowConfigXML(row);
			}
		}
		
		private function buildRowView(parentViewRow:TableTreeRow, rowDataNode:TableTreeRowNode, indentation:int):TableTreeRow{
			var i:int;
			var childData:TableTreeRowNode;
			var newViewRow:TableTreeRow;
			var rowViewConfig:TableTreeRowConfig = _rowViewConfigs[rowDataNode.templateId];
			switch(rowDataNode.visualType){
				case(RowViewTypes.NONE):
					//childtren of empty view rows are rendered at the same indentation level
					for(i = 0; i < rowDataNode.children.length; i++){
						childData = rowDataNode.children[i];
						buildRowView(parentViewRow, childData, indentation);
					}
					return null;
				case(RowViewTypes.TYPE):
					newViewRow = new TypeHeaderRow(rowViewConfig, rowDataNode, parentViewRow, 0, indentation, rowDataNode.parent != null);
					break;
				case(RowViewTypes.TABLE_HEADER):
					newViewRow = new TableHeaderRow(rowViewConfig, rowDataNode, parentViewRow, 0, indentation);
					_useAltDataRowBackground = false;
					break;
				case(RowViewTypes.TABLE_DATA):
					newViewRow = new TableDataRow(rowViewConfig, rowDataNode, parentViewRow, 0, indentation);
					if(_useAltDataRowBackground){
						(newViewRow as TableDataRow).useAlternateContentStyle();
					}
					_useAltDataRowBackground = !_useAltDataRowBackground;
					break;
				case(RowViewTypes.GROUP_BY):
					newViewRow = new GroupByRow(rowViewConfig, rowDataNode, parentViewRow, 0, indentation);
					break;
				default:
					Logger.log(DefaultLogEvent.WARNING, "BEVTableTreeComponent.buildView - Unknown row type (" + rowDataNode.visualType + "). No view for the row will be created.");
					return null;
			}
			newViewRow.addEventListener(FlexEvent.CREATION_COMPLETE, handleRowCreated);
			addRow(newViewRow, parentViewRow);
			for(i = 0; i < rowDataNode.children.length; i++){
				childData = rowDataNode.children[i];
				newViewRow.addChildRow(buildRowView(newViewRow, childData, indentation+1));
			}
			
			return newViewRow;
		}
		
		public function addRow(row:TableTreeRow, parentRow:TableTreeRow=null, childIndex:int=-1):void{
			if(_rowContainer != null){ //might be null if createChildren hasn't been called yet
				if(parentRow == null){
					_rowContainer.addChild(row);
					return;
				}
				//Calling addChildAt causes weird behavior at times in Container.getChildAt. The
				//following extra logic helps avoid that issue...
				var parentPosition:int = _rowContainer.getChildIndex(parentRow);
				var numChildren:int = parentRow.childRows.length;
				if(childIndex > numChildren || childIndex < 0){
					//when childIndex is not provided (i.e. == -1) we treat add as a push
					childIndex = numChildren;
				}
				var candidatePosition:int = parentPosition + childIndex + 1;
				if(candidatePosition >= _rowContainer.numChildren){
					_rowContainer.addChild(row);
				}
				else{
					_rowContainer.addChildAt(row, candidatePosition);
				}
			}
			else{
				_pendingRows.push(row);
			}
			invalidateDisplayList();
		}
		
		private function removeRow(row:TableTreeRow):void{
			if(_rowContainer == null){
				Logger.log(DefaultLogEvent.WARNING, BEVUtils.getClassName(this) + ".removeRow - Tried to remove row from a null row container.");
				return;
			}
			if(row == null || !_rowContainer.contains(row)){
				Logger.log(DefaultLogEvent.WARNING, BEVUtils.getClassName(this) + ".removeRow - Tried to remove row not contained in the row container.");
				return;
			}
			
			removeChildViewRowsFrom(row);
			row.removeAllChildRows();
			
			row.parentRow.removeChildRow(row);
			row.parentRow.rowDataNode.removeChildNode(row.rowDataNode);
			
			removeEventListenersFromRow(row);
			_rowContainer.removeChild(row);
		}
				
		private function removeChildViewRowsFrom(row:TableTreeRow):void{
			for each(var row2Remove:TableTreeRow in row.childRows){
				removeChildViewRowsFrom(row2Remove);
				if(_rowContainer.contains(row2Remove)){ _rowContainer.removeChild(row2Remove); }
				removeEventListenersFromRow(row2Remove);
			}
			row.removeAllChildRows();
		}
		
		private function animateRowRemovals(rows2Remove:Array):void{
			var rowRemoveEffect:Parallel = new Parallel();
			for each(var row2Remove:TableTreeRow in rows2Remove){
				var fadeOut:Fade = new Fade(row2Remove);
				rowRemoveEffect.addChild(fadeOut);
			}
			if(rowRemoveEffect.children.length > 0){
				rowRemoveEffect.addEventListener(EffectEvent.EFFECT_END, handleRowRemovalEffectEnd);
				rowRemoveEffect.play();
			}
		}
		
		private function addEventListenersToRow(row:TableTreeRow):void{
			if(row == null){ return; }
			if(row.isExpandable){
				row.addEventListener(RowExpansionEvent.EXPANSION_CHANGE, handleRowExpansionChange);
			}
			if(row is TypeHeaderRow){
				var typeRow:TypeHeaderRow = row as TypeHeaderRow;
				typeRow.addEventListener(GroupByEvent.GROUPBY_FIELD_CHANGE, handleGroupByChangeRequest);
				typeRow.addEventListener(RowActionEvent.ADD_ROW, handleAddRowRequest);
				typeRow.addEventListener(RowActionEvent.REMOVE_ROW, handleRemoveRowRequest);
			}
			else if(row is TableHeaderRow){
				var headerRow:TableHeaderRow = row as TableHeaderRow;
				headerRow.addEventListener(TableSortEvent.SORT_APPLIED, handleTableSortRequest);
			}
		}
		
		private function removeEventListenersFromRow(row:TableTreeRow):void{
			if(row == null){ return; }
			if(row.isExpandable){
				row.removeEventListener(RowExpansionEvent.EXPANSION_CHANGE, handleRowExpansionChange);
			}
			if(row is TypeHeaderRow){
				var typeRow:TypeHeaderRow = row as TypeHeaderRow;
				typeRow.removeEventListener(GroupByEvent.GROUPBY_FIELD_CHANGE, handleGroupByChangeRequest);
				typeRow.removeEventListener(RowActionEvent.ADD_ROW, handleAddRowRequest);
				typeRow.removeEventListener(RowActionEvent.REMOVE_ROW, handleRemoveRowRequest);
			}
			else if(row is TableHeaderRow){
				var headerRow:TableHeaderRow = row as TableHeaderRow;
				headerRow.removeEventListener(TableSortEvent.SORT_APPLIED, handleTableSortRequest);
			}
		}
		
		private function handleRowCreated(event:FlexEvent):void{
			var row:TableTreeRow = event.target as TableTreeRow;
			if(row == null){ return; }
			row.removeEventListener(FlexEvent.CREATION_COMPLETE, handleRowCreated);
			addEventListenersToRow(row);
		}
		
		
		private function handleRowRemovalEffectEnd(event:EffectEvent):void{
			var effectTarget:DisplayObject = event.target.target as DisplayObject;  //event.target = effect
			if(_rowContainer.contains(effectTarget)){ _rowContainer.removeChild(effectTarget); }
		}
				
		private function handleRowExpansionChange(event:RowExpansionEvent):void{
			CursorManager.setBusyCursor();
			var row:TableTreeRow = event.targetRow;
			if(row.isExpanded){
				//Create and send a getData request for the rows in the expanded
				var getExtendedRowsReq:BEVDrillDownRequest = new BEVDrillDownRequest(BEVDrillDownRequest.GET_TABLETREE_DATA, this);
				getExtendedRowsReq.addXMLParameter("path", row.path);
				if(row is TypeHeaderRow){
					var thr:TypeHeaderRow = row as TypeHeaderRow;
					if(thr.selectedGroupBy != TypeHeaderRowNode.NO_GROUPBY_STRING){
						getExtendedRowsReq.addXMLParameter("groupbyfield", (row as TypeHeaderRow).selectedGroupBy);
					}
				}
				getExtendedRowsReq.addStateVariable(ACTION_ROW, row);
				getExtendedRowsReq.addStateVariable(TABLE_TREE_COMMAND, TableTreeCommands.EXPAND_ROW);
				EventBus.instance.dispatchEvent(getExtendedRowsReq);
			}
			else{
				//Create and send a removedata request that will update the server model 
				var getRemoveRowsReq:BEVDrillDownRequest = new BEVDrillDownRequest(BEVDrillDownRequest.REMOVE_TABLETREE_DATA, this);
				getRemoveRowsReq.addXMLParameter("path", row.path);
				getRemoveRowsReq.addStateVariable(ACTION_ROW, row);
				getRemoveRowsReq.addStateVariable(TABLE_TREE_COMMAND, TableTreeCommands.COLLAPSE_ROW);
				EventBus.instance.dispatchEvent(getRemoveRowsReq);
			}
		}
		
		private function handleTableSortRequest(event:TableSortEvent):void{
			CursorManager.setBusyCursor();
			var row:TableHeaderRow = event.target as TableHeaderRow;
			var parentTypeRow:TypeHeaderRow = row.parentRow as TypeHeaderRow
			if(parentTypeRow == null){
				Logger.log(DefaultLogEvent.WARNING, BEVUtils.getClassName(this) + ".handleTableSortRequest - Null parent type header row.");
				return;
			}
			var sortColReq:BEVDrillDownRequest = new BEVDrillDownRequest(BEVDrillDownRequest.GET_TABLETREE_DATA, this);
			sortColReq.addXMLParameter("path", row.rowDataNode.parent.path);
			sortColReq.addXMLParameter("sortfield", event.sortField);
			sortColReq.addXMLParameter("startindex", String(parentTypeRow.currentPageItemIndex));
			sortColReq.addXMLParameter("sortfielddirection", event.sortDirection);
			sortColReq.addStateVariable(TABLE_TREE_COMMAND, TableTreeCommands.SORT_COLUMN);
			sortColReq.addStateVariable(ACTION_ROW, row)
			EventBus.instance.dispatchEvent(sortColReq);
		}
		
		private function handleGroupByChangeRequest(event:GroupByEvent):void{
			CursorManager.setBusyCursor();
			var groupbyTargetRow:TypeHeaderRow = event.targetRow;  //may be TypeHeaderRow or GroupByRow
			var groupByReq:BEVDrillDownRequest = new BEVDrillDownRequest(BEVDrillDownRequest.GET_TABLETREE_DATA, this);
			if(event.groupByField != TypeHeaderRowNode.NO_GROUPBY_STRING){
				groupByReq.addXMLParameter("groupbyfield", event.groupByField)
			}
			groupByReq.addXMLParameter("path", groupbyTargetRow.path);
			groupByReq.addStateVariable(ACTION_ROW, groupbyTargetRow);
			groupByReq.addStateVariable(TABLE_TREE_COMMAND, TableTreeCommands.APPLY_GROUPBY);
			EventBus.instance.dispatchEvent(groupByReq);
		}
		
		private function handlePageChangeRequest(event:PaginationEvent):void{
			var row:TypeHeaderRow = event.target as TypeHeaderRow;
			if(row == null){
				Logger.log(DefaultLogEvent.WARNING, BEVUtils.getClassName(this) + ".handleTablePageChange - Event generated by a non-TypeHeaderRow.");
				return
			}
			var tableHeaderRow:TableHeaderRow = row.childRows[0] as TableHeaderRow;
			if(tableHeaderRow == null){
				//childRows[0] might have been a scroll row, try childRows[1]
				tableHeaderRow = row.childRows[1] as TableHeaderRow;
				if(tableHeaderRow == null){
					Logger.log(DefaultLogEvent.WARNING, BEVUtils.getClassName(this) + ".handleTablePageChange - Couldn't determine corresponding table header row.");
					return;
				}
			}
			CursorManager.setBusyCursor();
			var paginateReq:BEVDrillDownRequest = new BEVDrillDownRequest(BEVDrillDownRequest.GET_TABLETREE_DATA, this);
			paginateReq.addXMLParameter("path", row.rowDataNode.path);
			paginateReq.addXMLParameter("sortfield", tableHeaderRow.sortField);
			paginateReq.addXMLParameter("sortfielddirection", tableHeaderRow.sortDirection);
			paginateReq.addXMLParameter("startindex", String(event.rowStartIndex));
			paginateReq.addStateVariable(TABLE_TREE_COMMAND, TableTreeCommands.PAGE_CHANGE);
			paginateReq.addStateVariable(ACTION_ROW, row)
			EventBus.instance.dispatchEvent(paginateReq);
		}
		
		override public function handleResponse(response:ConfigResponseEvent):void{
			if(!isRecipient(response)){ return; }
			if(response.failMessage != ""){
			 	Logger.log(DefaultLogEvent.CRITICAL, "Error loading the component data for " + this + " due to ["+response.failMessage+"]");
			 	updateErrorView("Failed to load data due to ["+response.failMessage+"]...");
			}
			else if(response.command == BEVDrillDownRequest.GET_TABLETREE_DATA || response.command == BEVDrillDownRequest.REMOVE_TABLETREE_DATA){
				handleTableTreeDataResponse(response);
			}
			else{
				Logger.log(
					DefaultLogEvent.WARNING,
					BEVUtils.getClassName(this) + ".handleResponse - Unhandled command response for " + response.command
				);
			}
		}
		
		private function handleQueryManagerResponse(response:EventBusEvent):void{
			if(!isRecipient(response)){ return; }
			var getDataReq:BEVDrillDownRequest = new BEVDrillDownRequest(BEVDrillDownRequest.GET_TABLETREE_DATA, this);
			getDataReq.addStateVariable(TABLE_TREE_COMMAND, TableTreeCommands.INITIALIZE);
			EventBus.instance.dispatchEvent(getDataReq);
		}
		
		private function handleTableTreeDataResponse(response:ConfigResponseEvent):void{
			var ttCommand:int = response.getStateVariable(TABLE_TREE_COMMAND);
			var row:TableTreeRow = response.getStateVariable(ACTION_ROW);
			
			parseRowViewConfigs(response.dataAsXML);
			switch(ttCommand){
				case(TableTreeCommands.INITIALIZE):
					handleInitializationResponse(response);
					break;
				case(TableTreeCommands.EXPAND_ROW):
					handleRowExpansionResponse(response, row);
					break;
				case(TableTreeCommands.COLLAPSE_ROW):
					hanldeRowCollapseResponse(response, row);
					break;
				case(TableTreeCommands.SORT_COLUMN):
					handleTableSortResponse(response, row);
					break;
				case(TableTreeCommands.APPLY_GROUPBY):
					handleGroupByResponse(response, row);
					break;
				case(TableTreeCommands.PAGE_CHANGE):
					handlePageChangeResponse(response, row);
					break;
			}
			CursorManager.removeBusyCursor();
			EventBus.instance.dispatchEvent(
				new TableTreeUpdateEvent(
					TableTreeCommands.stringValue(ttCommand),
					row == null ? "":row.path, //null on TableTreeCommands.INITIALIZE
					response.request.xmlParams,
					_rowContainer.numChildren,
					_isExportEnabled
				)
			);
		}
		
		private function handleInitializationResponse(response:ConfigResponseEvent):void{
			_state = DATA_LOADED;
			componentData = response.dataAsXML;
			//set component's actionconfig for menu options 
			if(response.dataAsXML.actionconfig != undefined){
				componentContext.setComponentInteractions(response.dataAsXML.actionconfig[0]);
				setExportEnabled(response.dataAsXML.actionconfig[0]);
			}
			//apply pagination on all expanded type header child tables
			for(var i:int = 0; i < _rowContainer.numChildren; i++){
				var headerRow:TypeHeaderRow = _rowContainer.getChildAt(i) as TypeHeaderRow
				if(headerRow == null){ continue; }
				updatePagination(headerRow);
			}
		}
		
		private function handleRowExpansionResponse(response:ConfigResponseEvent, row:TableTreeRow):void{
			if(row == null){
				Logger.log(DefaultLogEvent.WARNING, BEVUtils.getClassName(this) + ".handleRowExpansionResponse - No row stored in expansion request state. Can't perform expansion.");
				return;
			}
			var rowNodes:Array = _model.addExpansionRowsTo(row.rowDataNode, response.dataAsXML.textmodel[0]);
			for each(var rowNode:TableTreeRowNode in rowNodes){
				row.addChildRow(buildRowView(row, rowNode, row.indentation+1));
			}
			row.handleRowExpansion(rowNodes);
			if(row is TypeHeaderRow){ updatePagination(row, response); }
		}
		
		private function hanldeRowCollapseResponse(response:ConfigResponseEvent, row:TableTreeRow):void{
			if(row == null){
				Logger.log(DefaultLogEvent.WARNING, BEVUtils.getClassName(this) + ".hanldeRowCollapseResponse - No row stored in collapse request state. Can't perform collapse.");
				return;
			}
			_model.removeChildrenOf(row.rowDataNode);
			removeChildViewRowsFrom(row);
			row.handleRowCollapse();
			updatePagination(row, response);
		}
		
		private function handleTableSortResponse(response:ConfigResponseEvent, row:TableTreeRow):void{
			//Sort action is performed on a TableHeaderRow, but the result of the operation
			//returns a set of rows to be added to the TableHeaderRow's parent
			//TypeHeaderRow. We thus use parentTypeRow in place of row.
			var targetTypeRow:TypeHeaderRow = row.parentRow as TypeHeaderRow;
			if(targetTypeRow == null){
				Logger.log(DefaultLogEvent.WARNING, BEVUtils.getClassName(this) + ".handleTableSortResponse - Invalid type header row. Can't perform sort.");
				return;
			}
			var originalScrollPosition:int = targetTypeRow == null ? 0:targetTypeRow.tableHScrollPosition;
			var sortDirection:String = response.request.getXMLParameter("sortfielddirection");
			var startIndex:String = response.request.getXMLParameter("startindex");
			if(sortDirection == TableHeaderCell.NO_SORT || startIndex == "0"){
				refreshRowChildrenWithHeader(response, targetTypeRow);
			}
			else{
				refreshRowChildrenWithoutHeader(response, targetTypeRow);
			}
			//Restore Scroll postion
			targetTypeRow.tableHScrollPosition = originalScrollPosition;
			updatePagination(row, response);
		}
		
		private function handleGroupByResponse(response:ConfigResponseEvent, row:TableTreeRow):void{
			if(row == null){
				Logger.log(DefaultLogEvent.WARNING, BEVUtils.getClassName(this) + ".handleGroupByResponse - No row stored in group-by request state. Can't perform group-by.");
				return;
			}
			refreshRowChildrenWithHeader(response, row);
			updatePagination(row, response);
		}
		
		private function handlePageChangeResponse(response:ConfigResponseEvent, row:TableTreeRow):void{
			var targetTypeRow:TypeHeaderRow = row as TypeHeaderRow;
			if(targetTypeRow == null){
				Logger.log(DefaultLogEvent.WARNING, BEVUtils.getClassName(this) + ".handlePageChangeResponse - Invalid type header row. Can't perform pagination.");
				return;
			}
			var originalScrollPosition:int = targetTypeRow.tableHScrollPosition;
			refreshRowChildrenWithoutHeader(response, targetTypeRow);
			targetTypeRow.tableHScrollPosition = originalScrollPosition;
		}
		
		private function handleAddRowRequest(event:RowActionEvent):void{
			//add the target row as the offset-ith child of the parent row
			if(_rowContainer.contains(event.actionRow)){
				Logger.log(
					DefaultLogEvent.INFO,
					BEVUtils.getClassName(this) + ".handleAddRowRequest - Row is already contained in table tree. Ignoring add request."
				);
			}
			else{
				addRow(event.actionRow, event.parentRow, event.offset);
				event.parentRow.addChildRow(event.actionRow, event.offset);
				event.parentRow.rowDataNode.addChild(event.actionRow.rowDataNode, event.offset);
			}
		}
		
		private function handleRemoveRowRequest(event:RowActionEvent):void{
			if(_rowContainer.contains(event.actionRow)){
				removeRow(event.actionRow);
			}
			else{
				Logger.log(
					DefaultLogEvent.INFO,
					BEVUtils.getClassName(this) + ".handleRemoveRowRequest - Row doesn't exist in table tree. Ignoring remvoal request."
				);
			}
		}
		
		private function refreshRowChildrenWithHeader(rowDataResponse:ConfigResponseEvent, row:TableTreeRow):void{
			_model.removeChildrenOf(row.rowDataNode);
			removeChildViewRowsFrom(row);
			//create and add row specified by the response data as children of the row the groupby was applied to 
			var rowNodes:Array = _model.addExpansionRowsTo(row.rowDataNode, rowDataResponse.dataAsXML.textmodel[0]);
			for each(var rowNode:TableTreeRowNode in rowNodes){
				row.addChildRow(buildRowView(row, rowNode, row.indentation+1));
			}
			//change row's expansion state
			row.isExpanded = true;
			row.invalidateDisplayList(); //will recalculate column widths of newly grouped, sorted, etc. table rows
		}
		
		private function refreshRowChildrenWithoutHeader(rowDataResponse:ConfigResponseEvent, targetTypeRow:TypeHeaderRow):void{
			var tableHeaderRow:TableHeaderRow = targetTypeRow.childRows[0] as TableHeaderRow;
			if(tableHeaderRow == null){
				//childRows[0] might have been a scroll row, try childRows[1]
				tableHeaderRow = targetTypeRow.childRows[1] as TableHeaderRow;
				if(tableHeaderRow == null){
					Logger.log(DefaultLogEvent.WARNING, BEVUtils.getClassName(this) + ".refreshRowChildrenWithoutHeader - Couldn't determine corresponding table header row.");
					return;
				}
			}
			
			//clear out all datarow children of the targetTypeRow
			_model.removeChildrenOf(targetTypeRow.rowDataNode);
			removeChildViewRowsFrom(targetTypeRow);
			
			//add the table header back
			addRow(tableHeaderRow, targetTypeRow);
			addEventListenersToRow(tableHeaderRow); //since it's already been created by Flex (creation complete handler would normally do this)
			targetTypeRow.addChildRow(tableHeaderRow);
			targetTypeRow.rowDataNode.addChild(tableHeaderRow.rowDataNode);
			
			//add all TableDataRows returned in the response
			var rowNodes:Array = _model.addExpansionRowsTo(targetTypeRow.rowDataNode, rowDataResponse.dataAsXML.textmodel[0]);
			for each(var rowNode:TableTreeRowNode in rowNodes){
				targetTypeRow.addChildRow(buildRowView(targetTypeRow, rowNode, targetTypeRow.indentation+1));
			}
			
			//will recalculate column widths of new page's table rows
			targetTypeRow.invalidateDisplayList();
		}
		
		private function updatePagination(row:TableTreeRow, response:ConfigResponseEvent=null):void{
			while(row != null && !(row is TypeHeaderRow)){ row = row.parentRow; }
			if(row == null){
				Logger.log(DefaultLogEvent.DEBUG, BEVUtils.getClassName(this) + ".updatePagination - Could not update pagination with given row.");
				return;
			}
			var typeRow:TypeHeaderRow = row as TypeHeaderRow;
			var addPageListener:Boolean = false;
			if(response == null || response.dataAsXML.textmodel == undefined){
				addPageListener = typeRow.updatePaginationControl();
			}
			else{
				addPageListener = typeRow.updatePaginationControl(response.dataAsXML.textmodel[0] as XML);
			}
			if(addPageListener){ typeRow.addEventListener(PaginationEvent.PAGE_CHANGE, handlePageChangeRequest); }
		}
		
		override public function registerListeners():void{
			EventBus.instance.addEventListener(EventTypes.CONFIG_COMMAND_RESPONSE, handleResponse);
			EventBus.instance.addEventListener(EventTypes.SUCCESSFUL_QUERY_RESPONSE, handleQueryManagerResponse);
		}
		override public function isRecipient(event:EventBusEvent):Boolean{
			return super.isRecipient(event) || event.type == EventTypes.SUCCESSFUL_QUERY_RESPONSE;
		}
		
	}
}