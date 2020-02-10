package com.tibco.be.views.user.components.table{
	
	import com.tibco.be.views.core.ui.CommandTypes;
	import com.tibco.be.views.core.ui.DynamicParamsResolver;
	import com.tibco.be.views.core.ui.actions.AbstractAction;
	import com.tibco.be.views.core.ui.actions.ActionContext;
	import com.tibco.be.views.core.ui.actions.IActionContextProvider;
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.core.ui.menu.IBEVMenu;
	import com.tibco.be.views.user.actions.LaunchInternalLinkAction;
	import com.tibco.be.views.user.components.chart.DataColumn;
	import com.tibco.be.views.user.components.table.renderers.BEVColumnHeaderRendererFactory;
	import com.tibco.be.views.user.components.table.renderers.TextItemRendererFactory;
	import com.tibco.be.views.user.components.table.skins.BEVTableHeaderSeparator;
	
	import flash.events.Event;
	import flash.utils.Dictionary;
	
	import mx.controls.DataGrid;
	import mx.controls.dataGridClasses.DataGridColumn;
	import mx.core.ScrollPolicy;
	import mx.events.ListEvent;

	public class BEVTableComponent extends BEVComponent implements IActionContextProvider{
		
		protected var _internalGrid:DataGrid;
		protected var _headerRowConfigXML:XML;
		protected var _headerRowDataXML:XML;
		protected var _dataRowConfigXML:XML;
		protected var _dataProvider:BEVTableDataProvider;
		protected var _menu:IBEVMenu;
		protected var _selectedColumnCfg:XML;
		protected var _selectedColumnData:DataColumn;
		
		public function BEVTableComponent(){
			super();
			addEventListener(Event.REMOVED_FROM_STAGE, handleRemoved);
		}
		
		/**
		 * Handles component config changes. Sub classes should over ride this 
		 * method to parse and handle the component configuration. 
		 * @param oldConfig Represents the configuration of the component before the change, can be null
		 */ 
		override protected function handleConfigSet(configXML:XML):void{
			if(_internalGrid != null){
				removeChild(_internalGrid);
				_internalGrid = null;
			}
			_internalGrid = new DataGrid();
			//extract all the relevant information
			//header row config has template type as 'header' 
			_headerRowConfigXML = new XML(configXML..rowconfig.(@templatetype == "header")[0]);
			//header data row has template type as 'header' and templateid same as the header row config's id 
			_headerRowDataXML =  new XML(configXML..datarow.(@templatetype == "header" && @templateid == _headerRowConfigXML.@id )[0]);
			//data row config has template type as 'data'
			_dataRowConfigXML = new XML(configXML..rowconfig.(@templatetype == "data")[0]);
			
			var masterRowConfig:XML = _headerRowConfigXML;
			if(_headerRowConfigXML == null && _headerRowDataXML == null){
				_internalGrid.showHeaders = false;
				masterRowConfig = _dataRowConfigXML;
			}
			else{
				//confirm if all the XML's have same number of children
				if(_headerRowConfigXML.columnconfig.length != _headerRowDataXML.datacolumn.length){
					//show error and stop processing in this component
					updateErrorView("Mismatch in column count in header information...");
					return;
				}
				if(_headerRowConfigXML.columnconfig.length != _dataRowConfigXML.columnconfig.length){
					//show error and stop processing in this component
					updateErrorView("Mismatch in column count in data information...");
					return;
				}
			}
			//configure the data grid
			configureDataGrid(configXML.textconfig[0], _headerRowConfigXML, _dataRowConfigXML);
			//configure the data grid columns
			var datagridcolumns:Array = _internalGrid.columns;
			for each(var mastercolumnconfig:XML in masterRowConfig.columnconfig){
				var masterid:String = mastercolumnconfig.@id;
				var datacolumnconfig:XML = _dataRowConfigXML.columnconfig.(@id == masterid)[0];
				//Modified by Anand to fix BE-10061 on 02/01/2011
				var columnWidth:Number = parseFloat(String(datacolumnconfig.@width))/100.00;
				//Modified by Anand to fix BE-11782 on 03/22/2011
				if(columnWidth != 0){
					//add columns who have width more than 0
					var datagridcolumn:DataGridColumn = createDataColumn(masterid, mastercolumnconfig, datacolumnconfig);
					var headerdata:XML = (_headerRowDataXML == null) ? null:_headerRowDataXML.datacolumn.(@id == masterid)[0];
					configureDataColumn(datagridcolumn, masterid, mastercolumnconfig, datacolumnconfig, headerdata, columnWidth);
					datagridcolumns.push(datagridcolumn);
				}
			}
			_internalGrid.columns = datagridcolumns;
			//configure the data provider
			configureDataProvider(_dataRowConfigXML, configXML);
			_internalGrid.dataProvider = _dataProvider;
			addChild(_internalGrid);
			configureEventListeners();
		}
		
		protected function configureDataGrid(gridConfig:XML, headerRowConfigXML:XML, dataRowConfigXML:XML):void{
			_internalGrid.sortableColumns = false;
			_internalGrid.resizableColumns = false;
			_internalGrid.percentWidth = 100;
			//_internalGrid.percentHeight = 100;
			//_internalGrid.height = 20;
			
			//background color 
			setStyle("backgroundColor", "#"+gridConfig.@backgroundcolor);
			
			if(_internalGrid.showHeaders){
				//header configuration 
				_internalGrid.headerHeight = Number(headerRowConfigXML.@height) + 3; //+3 else text not entirely shown
				var headerColor:String = "#"+headerRowConfigXML.@background;
				_internalGrid.setStyle("headerColors", [headerColor,headerColor]);
			}
			
			//row configuration
			_internalGrid.rowHeight = Number(dataRowConfigXML.@height) + 3; //+3 else text not entirely shown
			var rowColor:String = "#"+dataRowConfigXML.@background;
			_internalGrid.setStyle("alternatingItemColors", [rowColor,rowColor]);
			_internalGrid.setStyle("rollOverColor", rowColor);
			_internalGrid.setStyle("selectionColor", rowColor);
			
			//gridlines configuration
			_internalGrid.setStyle("horizontalGridLines", true);
			_internalGrid.setStyle("horizontalGridLineColor", "#"+dataRowConfigXML.borderconfig.@bottom);
			_internalGrid.setStyle("verticalGridLines", false);
			_internalGrid.setStyle("verticalGridLineColor", "#"+dataRowConfigXML.borderconfig.@bottom);
			_internalGrid.setStyle("headerSeparatorSkin", BEVTableHeaderSeparator);
			//we need to set the border color to change the color of the line between the header and the first data row
			_internalGrid.setStyle("borderColor", "#"+dataRowConfigXML.borderconfig.@bottom);
			//scrolling
			_internalGrid.verticalScrollPolicy = ScrollPolicy.OFF;
			
			//border
			_internalGrid.setStyle("borderStyle", "none");
		}
		
		protected function createDataColumn(id:String, masterColumnConfig:XML, dataColumnConfig:XML):DataGridColumn{
			return new DataGridColumn();
		}	
		
		protected function configureDataColumn(column:DataGridColumn, id:String, masterColumnConfig:XML, dataColumnConfig:XML, headerData:XML, width:Number):void{
			if(_internalGrid.showHeaders){
				column.headerText = headerData.displayvalue;
				//configure header style
				column.headerRenderer = new BEVColumnHeaderRendererFactory(masterColumnConfig, headerData);
			}
			column.dataField = new String(dataColumnConfig.@id);
			column.width = width;
			//create renderer factory 
			var itemRendererFactory:TextItemRendererFactory = new TextItemRendererFactory(dataColumnConfig.@type, masterColumnConfig, dataColumnConfig);
			column.itemRenderer = itemRendererFactory; 
		}	
			
		protected function configureDataProvider(dataRowConfigXML:XML, componentConfig:XML):void{
			_dataProvider = new BEVTableDataProvider(dataRowConfigXML);
		}	
		
		/**
		 * Handles component data changes. Sub classes should over ride this 
		 * method to parse and handle the component data. 
		 * @param oldData Represents the data of the component before the change, can be null
		 */ 
		override protected function handleDataSet(dataXML:XML):void{
			_dataProvider.setData(dataXML);
			scaleGridHeight();
		}
		
		override public function refreshData():void {
			_dataProvider.removeAll();
			super.refreshData();
		}
		
		/**
		 * Handles component data updates. Sub classes should over ride this 
		 * method to parse and handle the component data update 
		 * @param componentData Represents the update data of the component 
		 */ 
		override public function updateData(componentData:XML):void{
			_dataProvider.updateData(componentData);
			scaleGridHeight();
		}
		
		private function scaleGridHeight():void{
			//Ideally we could just set rowCount, but it doesn't work the way we want with 0 items.
			//Additionally, we have to manually set the scroll policy because when it's set to auto,
			//container content gets covered by the scroll bar.
			var gridHeight:int = _internalGrid.measureHeightOfItems(0, _dataProvider.length) + _internalGrid.headerHeight + 2;
			_internalGrid.height = gridHeight;
			if(_dataProvider.length != 0){
				//datagrid acts funny (shows ~5 empty rows) when rowCount is set to 0
				_internalGrid.rowCount = _dataProvider.length;
			}
			if(gridHeight > height){ verticalScrollPolicy = ScrollPolicy.ON; }
			else{ verticalScrollPolicy = ScrollPolicy.OFF; }
		}
		
		protected function configureEventListeners():void{
			_internalGrid.addEventListener(ListEvent.ITEM_CLICK, itemClickHandler);
		}
		
		protected function itemClickHandler(event:ListEvent):void{
			var column:DataGridColumn = _internalGrid.columns[event.columnIndex];
			var columnID:String = column.dataField;
			//find the column config by id , since the number of columns in the _internalGrid may not match the number of columnconfigs
			//columnconfigs with '0' width are not added to the table 
			_selectedColumnCfg = _dataRowConfigXML.columnconfig.(@id == columnID)[0];
			if (_selectedColumnCfg == null) {
				_selectedColumnData = null;
				return;
			}
			_selectedColumnData = _dataProvider.getColumnDataById(_dataProvider.getRowDataByIndex(event.rowIndex), columnID);
			if(_selectedColumnCfg.actionconfig != undefined && _selectedColumnCfg.actionconfig.length != 0){
//				_menu = Kernel.instance.uimediator.menuprovider.getMenuUsingXML(_selectedColumnCfg.actionconfig[0],this);
//				_menu.addMenuEventListener(BEVMenuEvent.BEVMENU_HIDE, handleBEVMenuHide);
//				_menu.show(stage.mouseX,stage.mouseY);	
				componentContext.showMenu(
	       			stage.mouseX,
	       			stage.mouseY,
	       			_selectedColumnCfg.actionconfig[0],
	       			this
	   			);		
			}
			else if (_selectedColumnData.link != "" && _selectedColumnData.link != null) {
				var action:AbstractAction = new LaunchInternalLinkAction();
				var params:Dictionary = new Dictionary();
				params["url"] = _selectedColumnData.link;
				action.initByArgs(CommandTypes.LAUNCH_INTERNAL_LINK,true,"Launch Link",new Dictionary(),new Dictionary(),params);
				var actionContext:ActionContext = new ActionContext(this, new DynamicParamsResolver());
				action.execute(actionContext);
				//LaunchInternalLinkAction.launchHACK(_selectedColumnData.link);
			}
		}
		
//		protected function handleBEVMenuHide(event:BEVMenuEvent):void{
//			_menu = null;
//		}
		
		private function handleRemoved(event:Event):void{
			removeEventListener(Event.REMOVED_FROM_STAGE, handleRemoved);
			if(_internalGrid != null){
				_internalGrid.removeEventListener(ListEvent.ITEM_CLICK, itemClickHandler);
			}
		}
		
		public function getActionContext(parentActionConfig:XML, actionConfig:XML):ActionContext{
			var dynamicParamResolver:DynamicParamsResolver = new DynamicParamsResolver();
			dynamicParamResolver.setDynamicParamValue(DynamicParamsResolver.CURRENTDATACOLUMN_LINK_PARAM, _selectedColumnData.link);
			if(_selectedColumnData.typeSpecificAttribs["hrefprms"] != undefined){
				dynamicParamResolver.setDynamicParamValue(DynamicParamsResolver.CURRENTDATACOLUMN_TYPE_SPEC_HREF_PARAMS_PARAM, _selectedColumnData.typeSpecificAttribs["hrefprms"]);
			}
			return new ActionContext(this, dynamicParamResolver);
		}						
				
	}
}