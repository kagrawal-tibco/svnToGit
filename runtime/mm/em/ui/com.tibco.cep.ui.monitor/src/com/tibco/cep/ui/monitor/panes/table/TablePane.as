package com.tibco.cep.ui.monitor.panes.table{
	
	import com.tibco.cep.ui.monitor.panes.MetricPane;
	import com.tibco.cep.ui.monitor.util.RandomXMLGenerator;
	
	import mx.collections.ArrayCollection;
	import mx.controls.DataGrid;
	import mx.controls.dataGridClasses.DataGridColumn;

	public class TablePane extends MetricPane{
				
		[Bindable]
		protected var _data:ArrayCollection;
		
		protected var _display:DataGrid;
		
		private var categoryCol:DataGridColumn;
		private var valueCol:DataGridColumn;
				
		public function TablePane(type:String, title:String=""){
			super(title=="" ? "Default Table Title":title);
			_type = type;
			
			_display = new DataGrid();
			_display.percentWidth = 100;
			
			_data = new ArrayCollection();
			
			_display.styleName = "dataGridMetricStyle";
//			_display.headerHeight = 24;
//			_display.rowHeight = 28;			
					
			categoryCol = new DataGridColumn("Metric");
			valueCol = new DataGridColumn("Value");
			
			categoryCol.dataField = "category";
			valueCol.dataField = "value";
			
			var cols:Array = new Array();
			cols.push(categoryCol);
			cols.push(valueCol);
			_display.columns = cols;

			addDisplay(_display);
		}
		
		protected override  function fillDisplay(data:XML):void{
			var categories:XMLList = data.series.datapoint.category;
			var values:XMLList = data.series.datapoint.value;
			_data.removeAll();
			for(var i:int = 0; i < categories.length(); i++){
				_data.addItem(
					{
						category:categories[i].toString(),
						value:values[i].toString()
					}
				);
			}
			_display.dataProvider = _data;
		}
		
		protected override function updateDisplay(data:XML):void{
			if(RANDOMIZE_UPDATES){
				RandomXMLGenerator.updateRandom(data);				
			}
			fillDisplay(data);
		}
		
	}
}