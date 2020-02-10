package com.tibco.cep.ui.monitor.panes.table{
	
	import com.tibco.cep.ui.monitor.containers.InvalidPaneOverlay;
	import com.tibco.cep.ui.monitor.panes.MetricPane;
	import com.tibco.cep.ui.monitor.util.SortFunction;
	
	import flash.events.Event;
	
	import mx.collections.ArrayCollection;
	import mx.controls.AdvancedDataGrid;
	import mx.controls.advancedDataGridClasses.AdvancedDataGridColumn;
	import mx.core.ClassFactory;
	import mx.events.DataGridEvent;
	import mx.events.IndexChangedEvent;
	import mx.events.ScrollEvent;
	
	public class CachedObjectsTable extends MetricPane {
		
		private var COLUMN_CONFIG:XML = 
			<tableconfig>
				<columnconfig name="Name" field="cObjectName" datatype="String"/>
				<columnconfig name="Count" field="size" datatype="long"/>
				<columnconfig name="Gets" field="numberOfGets" datatype="Number"/>
				<columnconfig name="Puts" field="numberOfPuts" datatype="Number"/>
				<columnconfig name="Get Time" field="avgGetTimeMillis" datatype="Number"/>
				<columnconfig name="Put Time" field="avgPutTimeMillis" datatype="Number"/>
				<columnconfig name="Hit Ratio" field="hitRatio" datatype="Number" precision="2"/>
				<columnconfig name="Max" field="maxSize" datatype="Number"/>
				<columnconfig name="Min" field="minSize" datatype="Number"/>
				<columnconfig name="Expiry Delay" field="expiryDelayMillis" datatype="Number"/>																
			</tableconfig>;
		
		[Bindable]
		protected var _data:ArrayCollection;
		
		protected var _display:AdvancedDataGrid;
		protected var _cols:Array;
		
		private var _fieldNames:Array;
		private var _fieldDataTypes:Array;
		private var _fieldPrecision:Array;
		
		public function CachedObjectsTable(type:String,title:String,expandedPaneParent:MetricPane){
			super(title);
			_type = type;
			
			_expandedPaneParent = expandedPaneParent;
			
			_display = new AdvancedDataGrid();
			
			//set style and dimensions									
			_display.styleName = "dataGridMetricStyle";
			_display.headerHeight = 24;
			_display.rowHeight = 28;			
			_display.percentWidth = 100;
			_display.verticalScrollPolicy = "auto";
			
			//set dataprovider
			_data = _expandedPaneParent == null ? new ArrayCollection() 
												: (_expandedPaneParent as CachedObjectsTable)._data;
			
			_display.dataProvider = _data;
			
			//Event Listeners
			_display.addEventListener(IndexChangedEvent.HEADER_SHIFT, handleColumnShift);
			_display.addEventListener(DataGridEvent.HEADER_RELEASE, handleColumnSort);
			_display.addEventListener(ScrollEvent.SCROLL, refreshDisplay);
			
			//SET COLUMNS
			//Create the original datagrid. It is not an expansion of the original data grid
			if (_expandedPaneParent == null) {
				_cols = new Array();
				_fieldNames = new Array();
				_fieldDataTypes = new Array();
				_fieldPrecision = new Array();
				for each (var columnconfig:XML in COLUMN_CONFIG.columnconfig){
					var column:AdvancedDataGridColumn = new AdvancedDataGridColumn(columnconfig.@name);
					column.dataField = columnconfig.@field;
					column.dataTipField = columnconfig.@name;
					column.showDataTips = true;
					//column.dataTipFunction = parseCellToolTip;
					column.itemRenderer = new ClassFactory(CachedObjectTableItemRenderer);
					
					//use normal String sorting for the column with the name of the cached objects
					if (column.dataField != "cObjectName")
						column.sortCompareFunction = SortFunction.sortNumeric;
					
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
				_cols = (_expandedPaneParent as CachedObjectsTable)._display.columns;
				_fieldNames = (_expandedPaneParent as CachedObjectsTable)._fieldNames; 
				_fieldDataTypes = (_expandedPaneParent as CachedObjectsTable)._fieldDataTypes;
				_fieldPrecision = (_expandedPaneParent as CachedObjectsTable)._fieldPrecision;	
			}
			_display.columns = _cols;
			displayMode = MetricPane.STATIC_DISPLAY;
			addDisplay(_display);
		}
		
		private function handleColumnShift(event:Event):void {
			if (_expandedPaneParent != null) {
				(_expandedPaneParent as CachedObjectsTable)._display.columns = _display.columns;
			}
		}
		
		public override function handlePageResize(w:Number, h:Number):void{
			x = y = 0;
			height = h-4;
			width = w-4;
			setDisplayDimensions();
		}
		
		protected override  function fillDisplay(data:XML):void {
			//it is the original dataGrid, so it has to be created.
			if (_expandedPaneParent == null) {
				_data.removeAll();
				for each(var xml:XML in data.series.datapoint){
					var category:String = xml.category == undefined ? "undefined":xml.category;
					var rowStr:String = xml.value == undefined ? "":xml.value;
					if(rowStr.length == 0) continue;
					
					//split the value data stream
					var parts:Array = rowStr.split("#");
					
					//add the category value to the start of the array
					parts.unshift(category);
					
					//build object of the parts array using the field names array as property name reference
					var dataObj:Object = arrayToTableObject(parts);
					
					_data.addItem(dataObj);
				}
			} 
			//else it is a maximization of the original dataGrid, so just refresh the dataprovider with the updated data
			_data.refresh();
		}
		
		protected override function updateDisplay(data:XML):void{
			var scrollPos:Number = _display.verticalScrollPosition;
			fillDisplay(data);
			_display.verticalScrollPosition = scrollPos;
		}
		
		private function updateObj(obj:Object, vals:Array):void{
			if(_fieldNames.length != vals.length){
				trace(
					"ERROR: CachedObjectsTable.updateObj\n\t" + 
					"Object being updated doesn't match update data!"
				);
				return;
			}
			for(var i:int = 0; i < _fieldNames.length; i++){
				obj[_fieldNames[i]] = vals[i]; //updating all for now
			}
		}
		
		private function arrayToTableObject(array:Array):Object{
			var dataObj:Object = new Object();
			for (var i:int = 0 ; i < array.length ; i++){
				var value:Object = array[i];
				//Use blanks for values not supported by the back-end
				if(_fieldNames[i] == "avgGetTimeMillis" || _fieldNames[i] == "avgPutTimeMillis" || _fieldNames[i] == "maxSize" || _fieldNames[i] == "minSize" || _fieldNames[i] == "expiryDelayMillis"){
					value = "-";
				} 
				else if(_fieldDataTypes[i] == "Number"){
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
				dataObj[_fieldNames[i]] = value;
			}
			return dataObj;
		}
		
		private function refreshDisplay(event:Event=null):void{
			_data.refresh(); //for some reason the ADG isn't refreshing on scroll...
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