package com.tibco.cep.ui.monitor.panes.table{
	
	import flash.events.Event;
	import com.tibco.cep.ui.monitor.panes.MetricPane;
	import com.tibco.cep.ui.monitor.panes.MetricPaneType;
	import com.tibco.cep.ui.monitor.util.SortFunction;
	
	import mx.collections.ArrayCollection;
	import mx.controls.DataGrid;
	import mx.controls.dataGridClasses.DataGridColumn;
	import mx.events.DataGridEvent;
	import mx.events.IndexChangedEvent;
	
	public class GarbageCollectionTable extends MetricPane{
		
		[Bindable]
		protected var _data:ArrayCollection;
		
		protected var _display:DataGrid;

		protected var _cols:Array;
		
		private var poolNameCol:DataGridColumn;
		private var upTimeCol:DataGridColumn;
		private var countCol:DataGridColumn;
		private var timeSpentCol:DataGridColumn;
		
		public function GarbageCollectionTable(expandedPaneParent:MetricPane){
			super("Garbage Collection");
			_type = MetricPaneType.GARBAGE_COLLECTION_TABLE;
			
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
												: (_expandedPaneParent as GarbageCollectionTable)._data; 
												
			_display.dataProvider = _data;
			
			//SET COLUMNS
			//Create the original datagrid. It is not an expansion of the original data grid
			if (_expandedPaneParent == null) {
				poolNameCol = new DataGridColumn("Name");
				poolNameCol.dataField = "poolName";
				poolNameCol.dataTipField = "poolName";
				poolNameCol.showDataTips = true;
				
				upTimeCol = new DataGridColumn("Uptime");
				upTimeCol.dataField = "uptime";
				upTimeCol.dataTipField = "uptime";
				upTimeCol.showDataTips = true;
				
				countCol = new DataGridColumn("Count");
				//It's a column that contains numeric values, hence needs a special sorting function.
				countCol.dataField = "count";
				countCol.sortCompareFunction = SortFunction.sortNumeric
				countCol.dataTipField = "count";
				countCol.showDataTips = true; 
				
				timeSpentCol = new DataGridColumn("Time");
				timeSpentCol.dataField = "time";
				timeSpentCol.dataTipField = "time";
				timeSpentCol.showDataTips = true;
				
				_cols = new Array();
				_cols.push(poolNameCol);
				_cols.push(upTimeCol);
				_cols.push(countCol);
				_cols.push(timeSpentCol);
			} else {
				//it means that this instance is an expansion of the original data grid
				_cols = (_expandedPaneParent as GarbageCollectionTable)._display.columns;
			}
			
			_display.columns = _cols;
			
			addDisplay(_display);
		}
		
		private function handleColumnShift(event:Event):void {
			if (_expandedPaneParent != null) {
				(_expandedPaneParent as GarbageCollectionTable)._display.columns = _display.columns;
			}
		}
		
		protected override  function fillDisplay(data:XML):void{
			//it is the original DataGrid, so it has to be created.
			if (_expandedPaneParent == null) {
				var xmlRows:XMLList = data.series.datapoint.value;
				_data.removeAll();
				for each(var xml:XML in data.series.datapoint){
					var rowStr:String = xml.value;
					if(rowStr.length == 0) continue;
					var parts:Array = rowStr.split("#");
					_data.addItem(
						{
							poolName:String(xml.category),
							uptime:parts[0] as String,
							count:parts[1] as String,
							time:parts[2] as String
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