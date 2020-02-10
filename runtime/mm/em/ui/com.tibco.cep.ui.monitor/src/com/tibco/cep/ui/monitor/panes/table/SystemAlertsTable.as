package com.tibco.cep.ui.monitor.panes.table{
	
	import com.tibco.cep.ui.monitor.containers.InvalidPaneOverlay;
	import com.tibco.cep.ui.monitor.panes.MetricPane;
	import com.tibco.cep.ui.monitor.panes.MetricPaneType;
	import com.tibco.cep.ui.monitor.renderers.ImageRender;
	
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	import mx.controls.DataGrid;
	import mx.controls.Image;
	import mx.controls.dataGridClasses.DataGridColumn;
	import mx.core.ClassFactory;
	import mx.events.IndexChangedEvent;
	
	public class SystemAlertsTable extends MetricPane{
		
		[Embed("assets/images/cluster_health_normal.png")]
		private static const NORMAL_HEALTH_ICON:Class;
		
		[Embed("assets/images/cluster_health_warning.png")]
		private static const WARNING_HEALTH_ICON:Class;
		
		[Embed("assets/images/cluster_health_critical.png")]
		private static const CRITICAL_HEALTH_ICON:Class;
				
		//private static const NORMAL_SEVERITY:String = "@Embed('assets/images/cluster_health_normal.png')";
		//private static const WARNING_SEVERITY:String = "@Embed('assets/images/cluster_health_warning.png')";
		//private static const CRITICAL_SEVERITY:String = "@Embed('assets/images/cluster_health_critical.png')";
		
		private var COLUMN_CONFIG:XML = 
			<tableconfig>
				<columnconfig name="Severity" field="severity" datatype="String" width="0.06"/>
				<columnconfig name="Node Name" field="name" datatype="String" width="0.18"/>
				<columnconfig name="Node Type" field="type" datatype="String" width="0.08"/>
				<columnconfig name="Message" field="message" datatype="String" width="0.48"/>
				<columnconfig name="Received" field="received" datatype="DateTime" width="0.20"/>
			</tableconfig>;		
		
		protected var _data:ArrayCollection;
		protected var _cols:Array;
		
		protected var _display:DataGrid;
		
		//helper variables for faster access during update cycles
		private var _fieldNames:Array;
		private var _fieldDataTypes:Array;
		private var _fieldPrecision:Array;
		
		//status icons
		private var _normalSeverityImg:Image;
		private var _warningSeverityImg:Image;
		private var _criticalSeverityImg:Image;
		
		public function SystemAlertsTable(expandedPaneParent:MetricPane){
			super("System Alerts");
			_type = MetricPaneType.ALERTS_TABLE;
			
			_expandedPaneParent = expandedPaneParent;

			_display = new DataGrid();
			
			//Event Listeners
			_display.addEventListener(IndexChangedEvent.HEADER_SHIFT, handleColumnShift);
			
			_data = _expandedPaneParent == null ? new ArrayCollection() 
												: (_expandedPaneParent as SystemAlertsTable)._data; 
			
			_display.dataProvider = _data;
			_display.percentWidth = 100;
			_display.verticalScrollPolicy = "auto";
			
			_display.styleName = "dataGridMetricStyle";
			_display.headerHeight = 24;
			_display.rowHeight = 22;			
			
			//SET COLUMNS
			//Create the original datagrid. It is not an expansion of the original data grid
			if (_expandedPaneParent == null) {
				_cols = new Array();
				_fieldNames = new Array();
				_fieldDataTypes = new Array();
				_fieldPrecision = new Array();
				for each (var columnconfig:XML in COLUMN_CONFIG.columnconfig){
					var column:DataGridColumn = new DataGridColumn(columnconfig.@name);
					column.dataField = columnconfig.@field;
					column.headerWordWrap = true;
					
					column.dataTipField = columnconfig.@name;
					column.showDataTips = true;
					
					column.width = new Number(columnconfig.@width);	
					if(columnconfig.@field == "severity"){
						column.itemRenderer = new ClassFactory(ImageRender);
					}		
					_cols.push(column);
					_fieldNames.push(String(columnconfig.@field));
					_fieldDataTypes.push(String(columnconfig.@datatype));
					var precision:Object = String(columnconfig.@precision);
					if (precision != ""){
						precision = parseInt(columnconfig.@precision);
						_fieldPrecision.push(precision);
					}
					else {
						_fieldPrecision.push(null);
					}
				}
			} else {
				//it means that this instance is an expansion of the original data grid
				_cols = (_expandedPaneParent as SystemAlertsTable)._display.columns;
				_fieldNames = (_expandedPaneParent as SystemAlertsTable)._fieldNames; 
				_fieldDataTypes = (_expandedPaneParent as SystemAlertsTable)._fieldDataTypes;
				_fieldPrecision = (_expandedPaneParent as SystemAlertsTable)._fieldPrecision;	
			}
			
			_display.columns = _cols;
			_display.setStyle("text-align", "right");
			
			resize(2);
			addDisplay(_display);

		}
		
		private function handleColumnShift(event:Event):void {
			if (_expandedPaneParent != null) {
				(_expandedPaneParent as SystemAlertsTable)._display.columns = _display.columns;
			}
		}
		
		protected override function fillDisplay(data:XML):void{
			//it is the original dataGrid, so it has to be created.
			if (_expandedPaneParent == null) {
				_data.removeAll();
				var numDataRows:int = 0;
				for each(var xml:XML in data.series.datapoint){
					var category:String = xml.category == undefined ? "undefined":xml.category;
					var rowStr:String = xml.value == undefined ? "":xml.value;
					if(rowStr.length == 0) continue;
					//split the value data stream in 3 parts
					var parts:Array = split(rowStr,"|",4);
				
					//build object of the parts array using the field names array as property name reference
					var dataObj:Object = arrayToTableObject(parts);
					_data.addItem(dataObj);
					numDataRows++;
				}
			} 
			//else it is a maximization of the original dataGrid, so just refresh the dataprovider with the updated data
			_display.rowCount = numDataRows;
			_data.refresh();
		}
		
		private function arrayToTableObject(array:Array):Object{
			var dataObj:Object = new Object();
			for (var i:int = 0 ; i < array.length ; i++){
				var value:Object = array[i];
				if(_fieldNames[i] == "severity"){
					var severity:String = value as String;
					switch(severity){
						case("normal"):
							value = NORMAL_HEALTH_ICON;
							dataObj["severityTip"] = "Normal";
							break;
						case("warning"):
							value = WARNING_HEALTH_ICON;
							dataObj["severityTip"] = "Warning";
							break;
						case("critical"):
							value = CRITICAL_HEALTH_ICON;
							dataObj["severityTip"] = "Critical";
							break;
					}
				}
				if (_fieldDataTypes[i] == "Number") {
					if (_fieldPrecision[i] == null){
						value = new Number(value);
					}
					else {
						/* For debugging purposes
						var t:Number = new Number(value);
						var s:String = t.toFixed(fieldPrecision[i]);
						var v:Number = new Number(s);
						//*/
						value = new Number(new Number(value).toFixed(_fieldPrecision[i]));
					}
				}
				else if (_fieldDataTypes[i] == "DateTime") {
					var d:Date = new Date();
					d.time = new Number(value);
					value = d.toLocaleString(); 
				}
				dataObj[_fieldNames[i]] = value;
			}
			return dataObj;
		}		
		
		protected override function updateDisplay(data:XML):void{
			var scrollPos:Number = _display.verticalScrollPosition;
			fillDisplay(data);
			_display.verticalScrollPosition = scrollPos;
		}
		
		
		protected function split(target:String,delimiter:String,count:Number):Array{
			var i:int = 0;
			var splits:Array = new Array(); 
			var startIdx:int = 0;
			var idx:int = target.indexOf(delimiter,startIdx);
			while (idx != -1 && i < count){
				splits.push(target.substr(startIdx,idx-startIdx));
				startIdx = idx+1;
				idx = target.indexOf(delimiter,startIdx);
				i++;
			}
			splits.push(target.substr(startIdx,target.length-startIdx));
			return splits;
		}
		
		/**
		 * Called when this specific parent (cache) node becomes inactive (active="false")
		 */
		public override function setInactivePaneOverlay():void{
			//Only add the inactive pane overlay if it and the invalid component overlay and the not
			//in cluster overlay are not already being displayed
			if(_inactivePane == null){
				_inactivePane = new InvalidPaneOverlay();
				_inactivePane.x = _display.x;
				_inactivePane.y = _display.y;
				_inactivePane.message = "\nEntity Inactive";
				addChild(_inactivePane);
			}
			_inactivePane.visible = true;
			_display.enabled = false;
			setButtons(false, false, false); //disable all buttons
		}

	}
}