package com.tibco.cep.ui.monitor.panes
{
	import com.tibco.cep.ui.monitor.IUpdateable;
	import com.tibco.cep.ui.monitor.io.PSVRClient;
	import com.tibco.cep.ui.monitor.util.Logger;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	import mx.controls.DataGrid;
	import mx.controls.dataGridClasses.DataGridColumn;
	import mx.events.DataGridEvent;
	import mx.events.FlexEvent;
	
	public class MethodInvocationPaneController implements IUpdateable{
		private var _view:MethodInvocationPane;
		private var _panelDefinition:XML;
		private var _monitoredNode:XML;
		
		public function MethodInvocationPaneController(panelDefinition:XML, monitoredNode:XML)
		{
			_panelDefinition=panelDefinition;
			_monitoredNode=monitoredNode;
				//create layout of the view
			_view = new MethodInvocationPane();
//			this._methodInvocationPane=methodInvocationPane;
			
					_view.x = 0;
					_view.y = 0;
					_view.width = 1000;
					_view.height = 1000;
					
//					_view.percentWidth = 100;
//            		_view.percentHeight = 100;
			
			_view.addEventListener(FlexEvent.CREATION_COMPLETE, handleCreationComplete);
		}
		
		private function handleCreationComplete(event:FlexEvent):void {
			setData();
			_view.invokeButton.addEventListener(MouseEvent.CLICK, invokeBtnClickHandler);
			_view.argsDataGrid.addEventListener(DataGridEvent.ITEM_EDIT_END, validateInput);
		}
		
		private function validateInput(event:Event):void {
			trace("VALIDATE INPUT sdfMETHOD CALLED");
			event.currentTarget.
			trace (_view.argsDataGrid.selectedItem.toString( ));
		}
		
				//TODO: MouseEvent
		//The Execute button was previously called Invoke, so this action refers to Execute
		private function invokeBtnClickHandler(event:Event):void {
//			getTokenizedParamsStr();
//			_view.argsDataGrid.v
			var parameters:String = "null#null";
			PSVRClient.instance.invokeMethod(parameters, _monitoredNode.@monitorableID, _monitoredNode.@id, this);
			Logger.logInfo("Execute button called");
		}
		
		private function setData():void{
			setName();
			setDescription();
			setArgsGrid();
//			_methodInvocationPane.nameTextBox = 
		} //setData
	
		private function setName():void{
			_view.nameTextBox.text = _panelDefinition.page.methodpaneconfig.name.toString();
			_panelDefinition.text()
		}//setName
		
		private function setDescription():void{
			_view.descTextBox.text = _panelDefinition.page.methodpaneconfig.description.toString();
		}//setDescription
		
		private function setArgsGrid():void{ 
			var methodArgsInfo:ArrayCollection = new ArrayCollection();
		
			for each(var child:XML in _panelDefinition.page.methodpaneconfig.arguments.children()) {
//       		if ( String(child.name()).toLowerCase() == "arg" ) {
       			methodArgsInfo.addItem( { name: String(child.@name),
           			type: String(child.@type), 
           			desc: String(child.@desc), 
           			defaultvalue: String(child.@defaultvalue) } );
//       		}	
	       } //for
	       _view.argsDataGrid.dataProvider = methodArgsInfo;
		} //setArgsGrid
		
		public function get view():MethodInvocationPane { return _view; }

		private function createResultsGrid(resultsXML:XML):void {
			//removes the spacer before creating the dataGrid with the results 
			_view.resultsGrid.removeAllChildren();
		
			var resultsDataGrid:DataGrid = new DataGrid();
			
//			var gridColumn:DataGridColumn = new DataGridColumn();
			
//			_methodInvocationPane.resultsGrid.addChild(resultsGrid);  TODODOOOOOOOOOOO CHECK THISSSS
			
			if(resultsXML.name().toString() == "table") { //it is tabular data
				var cols:Array = new Array();
//				fieldNames = new Array();
//				fieldDataTypes = new Array();
//				fieldPrecision = new Array();

				//sets column titles		
				for each ( var columnconfig:XML in resultsXML.row[0].children() ) {
					var column:DataGridColumn = new DataGridColumn(columnconfig.@name);
					column.dataField = columnconfig.@name;
//					column.headerWordWrap = true;
//					if (fieldNames.length == 0){
//						column.dataTipField = columnconfig.@name;
//						column.showDataTips = true;
//					}
					cols.push(column);
//					fieldNames.push(String(columnconfig.@field));
//					fieldDataTypes.push(String(columnconfig.@datatype));
//					var precision:Object = String(columnconfig.@precision);
//					if (precision != ""){
//						precision = parseInt(columnconfig.@precision);
//						fieldPrecision.push(precision);
//					}
//					else {
//						fieldPrecision.push(null);
//					}
				} //for
				
				//sets the columns for this grid
				resultsDataGrid.columns = cols;
				
			    _view.resultsGrid.addChild(resultsDataGrid);
				
				var rowsData:ArrayCollection = new ArrayCollection();
			
				//iterates over all rows and for each row puts the value corresponding 
				//to each column in rowsData. Values are put in rowData one row at a time. 
				//After each iteration the rowValues dictionary contains the data corresponding to one row.
				//rowsData is used as the dataProvider for the dataGrid 
				for each(var row:XML in resultsXML.children()) {
					var columnNames:XMLList = row.children().@name;
					var columnValues:XMLList = row.children().@value;
					var rowValues:Dictionary = new Dictionary(true);
					
					for (var i:int = 0; i< columnNames.length(); i++) {
						rowValues[columnNames[i].toString()] = columnValues[i];
					}
					
					rowsData.addItem(rowValues);
		       } //for_each
		       
		       resultsDataGrid.dataProvider = rowsData;
			} //if
			else if(resultsXML.name().toString() == "value") { //it is a singular value
				//HUGO: TODO
			}
			else {
				trace("MethodInvocationController.resultsGrid - Unknown data format");
			}
			
			
			
			
		} //createResultsGrid		


		
		public function update(operation:String,data:XML):void {
			createResultsGrid(data);
		}
		
		public function updateFailure(operation:String,message:String,code:uint):void {
			trace("MethodInvocationPaneController - " + operation + ":\n\n" + message);
		}
		
	} //class
} //package