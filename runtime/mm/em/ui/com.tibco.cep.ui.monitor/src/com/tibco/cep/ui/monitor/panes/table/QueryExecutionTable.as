package com.tibco.cep.ui.monitor.panes.table{
	
	import com.tibco.cep.ui.monitor.panes.MetricPane;
	import com.tibco.cep.ui.monitor.util.SortFunction;
	
	import mx.collections.ArrayCollection;
	import mx.controls.DataGrid;
	import mx.controls.dataGridClasses.DataGridColumn;
	import mx.events.DataGridEvent;
	import mx.events.IndexChangedEvent;
	
	public class QueryExecutionTable extends MetricPane{
		
		[Bindable]
		protected var _data:ArrayCollection;
		
		protected var _display:DataGrid;
		
		private var queryNameCol:DataGridColumn;
		private var pendingCntCol:DataGridColumn;
		private var accumulatedCntCol:DataGridColumn;

		protected var _cols:Array;

		public function QueryExecutionTable(type:String,title:String,expandedPaneParent:MetricPane){
			super(title);
			_type = type;
			
			_expandedPaneParent = expandedPaneParent;
			
			_display = new DataGrid();

			//Event Listeners
			_display.addEventListener(IndexChangedEvent.HEADER_SHIFT, handleColumnShift);
			_display.addEventListener(DataGridEvent.HEADER_RELEASE, handleColumnSort);
			
			_display.percentWidth = 100;
			_display.styleName = "dataGridMetricStyle";
			_display.headerHeight = 24;
			_display.rowHeight = 28;			
			
			_data = _expandedPaneParent == null ? new ArrayCollection() 
												: (_expandedPaneParent as QueryExecutionTable)._data;
			
			_display.dataProvider = _data;
			
			
			//SET COLUMNS
			//Create the original datagrid. It is not an expansion of the orginal data grid
			if (_expandedPaneParent == null) {
				queryNameCol = new DataGridColumn("Query Name");
				queryNameCol.dataField = "queryName";
				queryNameCol.dataTipField = "queryName";
				queryNameCol.showDataTips = true;
				
				pendingCntCol = new DataGridColumn("Pending");
				//These two columns contain numeric values, hence they need a special sorting function.
				pendingCntCol.dataField = "pendingCnt";
				pendingCntCol.sortCompareFunction = SortFunction.sortNumeric;
				pendingCntCol.dataTipField = "pendingCnt";
				pendingCntCol.showDataTips = true;
				
				accumulatedCntCol = new DataGridColumn("Accumulated");
				accumulatedCntCol.dataField = "accumulatedCnt";
				accumulatedCntCol.sortCompareFunction = SortFunction.sortNumeric;
				accumulatedCntCol.dataTipField = "accumulatedCnt";
				accumulatedCntCol.showDataTips = true;
				
				_cols = new Array();
				_cols.push(queryNameCol);
				_cols.push(pendingCntCol);
				_cols.push(accumulatedCntCol);
			} else {
				//it means that this instance is an expansion of the orginal data grid
				_cols = (_expandedPaneParent as QueryExecutionTable)._display.columns;
			} 
			
			_display.columns = _cols;
			
			//resize(2);
			addDisplay(_display);
		}
		
		private function handleColumnShift(event:IndexChangedEvent):void {
			if (_expandedPaneParent != null) {
				(_expandedPaneParent as QueryExecutionTable)._display.columns = _display.columns;
			}
		}
		
		protected override  function fillDisplay(data:XML):void{
			//it is the original dataGrid, so it has to be created.
			if (_expandedPaneParent == null) {
				var xmlRows:XMLList = data.series.datapoint.value;
				_data.removeAll();
				for each(var xml:XML in data.series.datapoint){
					var rowStr:String = xml.value;
					if(rowStr.length == 0) continue;
					var parts:Array = rowStr.split("#");
					_data.addItem(
						{
							queryName:String(xml.category),
							pendingCnt:parts[0] as String,
							accumulatedCnt:parts[1] as String
						}
					);
				}
			}
			//else it is a maximization of the original dataGrid, so just refresh the dataprovider with the updated data
			_display.dataProvider = _data;
		}
		
		protected override function updateDisplay(data:XML):void{
			fillDisplay(data);
		}

	}
}