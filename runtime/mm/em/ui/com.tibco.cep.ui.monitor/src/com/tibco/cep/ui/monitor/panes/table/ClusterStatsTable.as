package com.tibco.cep.ui.monitor.panes.table{
	
	import com.tibco.cep.ui.monitor.panes.MetricPane;
	import com.tibco.cep.ui.monitor.util.Logger;
	import com.tibco.cep.ui.monitor.util.SortFunction;
	
	import flash.events.Event;
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	import mx.containers.Grid;
	import mx.containers.GridItem;
	import mx.containers.GridRow;
	import mx.containers.HBox;
	import mx.controls.DataGrid;
	import mx.controls.Image;
	import mx.controls.Label;
	import mx.controls.dataGridClasses.DataGridColumn;
	import mx.events.DataGridEvent;
	import mx.events.IndexChangedEvent;
	
	public class ClusterStatsTable extends MetricPane {
		
		[Embed("assets/images/cluster_health_normal.png")]
		private static const NORMAL_HEALTH_ICON:Class;
		
		[Embed("assets/images/cluster_health_warning.png")]
		private static const WARNING_HEALTH_ICON:Class;
		
		[Embed("assets/images/cluster_health_critical.png")]
		private static const CRITICAL_HEALTH_ICON:Class;
		
		private var COLUMN_CONFIG:XML = 
			<tableconfig>
				<columnconfig name="Type" field="entityType" datatype="String"/>
				<columnconfig name="Total" field="totalCnt" datatype="Number"/>
				<columnconfig name="Active" field="activeCnt" datatype="Number"/>
				<columnconfig name="Inactive" field="inactiveCnt" datatype="Number"/>
				<columnconfig name="Normal" field="greenCnt" datatype="Number"/>
				<columnconfig name="Warning" field="yellowCnt" datatype="Number"/>
				<columnconfig name="Critical" field="redCnt" datatype="Number"/>
			</tableconfig>;
		
		//cluster health variable 
		protected var _clusterHealth:String;
		
		//cluster health indicator
//		protected var _clusterHealthLbl:Label;
		
		
		//cluster health image indicator
		protected var _clusterHealthIcon:Image;
		
		//cluster children types data holder
		[Bindable]
		protected var _data:ArrayCollection;
		
		protected var _cols:Array;
		
		//cluster children types display
		protected var _display:DataGrid;
		
		//holds the data 
		private var _dataDirectory:Dictionary;
		
		//helper variables for faster access during update cycles
		private var _fieldNames:Array;
		private var _fieldDataTypes:Array;
		private var _fieldPrecision:Array;
		
		//HBoxes for laying out Cluster Health
		private var fhBox:HBox;
		private var bhBox:HBox;
		
		public function ClusterStatsTable(type:String,title:String,expandedPaneParent:MetricPane=null){
			super(title);
			_type = type;
			
			_expandedPaneParent = expandedPaneParent;
			
			//cluster health layout 
			var grid:Grid = new Grid();
			grid.percentWidth = 100;
			grid.percentHeight = 100;
			grid.setStyle("verticalGap", 0);
			
			//cluster table layout 
			var gridRow:GridRow = new GridRow();
			gridRow.percentWidth = 100;
			gridRow.setStyle("paddingBottom", 0);
			gridRow.setStyle("paddingTop", 0);
			
			gridItem = new GridItem();
			gridItem.colSpan = 2;
			
			_display = new DataGrid();
			
			//Event Listeners
			_display.addEventListener(DataGridEvent.HEADER_RELEASE, handleColumnSort);
			_display.addEventListener(IndexChangedEvent.HEADER_SHIFT, handleColumnShift);
			
			_dataDirectory = new Dictionary(true);
			
			_data = _expandedPaneParent == null ? new ArrayCollection() 
												: (_expandedPaneParent as ClusterStatsTable)._data; 
			
			_display.dataProvider = _data;		
			_display.percentWidth = 100;
			_display.verticalScrollPolicy = "auto";
			_display.styleName = "dataGridMetricStyle";
			_display.headerHeight = 24;
			_display.rowHeight = 22;			
			
			gridItem.addChild(_display);
			
			gridRow.addChild(gridItem);
			
			grid.addChild(gridRow);
					
					
			//SET COLUMNS
			//Create the original datagrid, not anexpansion of the orginal data grid
			if (_expandedPaneParent==null) {			
				_cols = new Array();
				_fieldNames = new Array();
				_fieldDataTypes = new Array();
				_fieldPrecision = new Array();
				
				for each (var columnconfig:XML in COLUMN_CONFIG.columnconfig){
					var column:DataGridColumn = new DataGridColumn(columnconfig.@name);
					column.dataField = columnconfig.@field;
					column.headerWordWrap = true;
					
					//use normal String sorting for the column with the entity type
					if (column.dataField != "entityType")
						column.sortCompareFunction = SortFunction.sortNumeric;

//					if (_fieldNames.length == 0){
						column.dataTipField = columnconfig.@name;
						column.showDataTips = true;
//					}
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
			} else { //it means that this instance is an expansion of the orginal data grid
				_cols = (_expandedPaneParent as ClusterStatsTable)._display.columns;
				_fieldNames = (_expandedPaneParent as ClusterStatsTable)._fieldNames; 
				_fieldDataTypes = (_expandedPaneParent as ClusterStatsTable)._fieldDataTypes;
				_fieldPrecision = (_expandedPaneParent as ClusterStatsTable)._fieldPrecision;	
			}
			
			_display.columns = _cols;
			_display.setStyle("text-align", "right");
			
			resize(2);
			addDisplay(grid);
			
			//Setting Cluster Health Layout
			gridRow = new GridRow();
			gridRow.percentWidth = 100;
			gridRow.setStyle("paddingBottom", 0);
			gridRow.setStyle("paddingTop", 0);
			
			var gridItem:GridItem = new GridItem();
			gridItem.percentWidth = 100;
			gridItem.setStyle("horizontalAlign", "right");
			gridRow.addChild(gridItem);
			
			//Empty HBox to be added first to the container (gridItem), thus shifting the HBox with the Cluster
			//Health information (fhBox) to the right. 
			bhBox = new HBox();
            bhBox.percentWidth = 65;
            bhBox.percentHeight = 100;
            			
			fhBox = new HBox();
            fhBox.percentWidth = 35;
            fhBox.percentHeight = 100;
            fhBox.setStyle("backgroundColor", "haloSilver");
            fhBox.setStyle("backgroundAlpha", "0.78");
  			
  			var label:Label = new Label();
			label.percentWidth = 100;
			label.text = "Cluster Health:";
			label.styleName = "hBoxMetricStyle";
			fhBox.addChild(label);
			
			_clusterHealthIcon = new Image();
			_clusterHealthIcon.percentWidth = 15;
			_clusterHealthIcon.setStyle("verticalAlign", "middle");
			fhBox.addChild(_clusterHealthIcon);
			
			gridItem = new GridItem();
			gridItem.percentWidth = 100;

			gridItem.addChild(bhBox);
			gridItem.addChild(fhBox);

			gridRow.addChild(gridItem);
			grid.addChild(gridRow);		
		}
		
		private function handleColumnShift(event:IndexChangedEvent):void {
			if (_expandedPaneParent != null) {
				(_expandedPaneParent as ClusterStatsTable)._display.columns = _display.columns;
			}
		}
		
		/*public override function handlePageResize(w:Number, h:Number):void{
			x = y = 0;
			height = h-4;
			width = w-4;
			setDisplayDimensions();
		}*/
		
//		private function expandDisplay()
		
		protected override  function fillDisplay(data:XML):void{
			if(data==null){
				Logger.logWarning(this,"Attempted to fill data grid with with null data.");
				return;
			}
			//it is the original dataGrid, so it has to be created.
			if (_expandedPaneParent == null) {
				//_data.removeAll();
				var numDataRows:int = 0;
				for each(var xml:XML in data.series.datapoint){
					var category:String = xml.category == undefined ? "undefined":xml.category;
					var rowValues:String = xml.value == undefined ? "":xml.value;
					if(rowValues.length == 0) 
						continue;
					
					//check for Cluster Health
					if (category == "Cluster Health"){
						_clusterHealth = rowValues;
						//_clusterHealthLbl.text = _clusterHealth;
						switch(_clusterHealth.toUpperCase()){
							case("NORMAL"):
								_clusterHealthIcon.data = NORMAL_HEALTH_ICON;
								_clusterHealthIcon.toolTip = "Normal";
								break;
							case("WARNING"):
								_clusterHealthIcon.data = WARNING_HEALTH_ICON;
								_clusterHealthIcon.toolTip = "Warning";
								break;
							case("CRITICAL"):
								_clusterHealthIcon.data = CRITICAL_HEALTH_ICON;
								_clusterHealthIcon.toolTip = "Critical";
								break;
							default:
								_clusterHealthIcon.data = null;
								_clusterHealthIcon.toolTip = "Inactive";
						}
					}
					else {
						//split the value data stream
						var rowData:Array = rowValues.split("#");
					
						//add the category value to the start of the array
						rowData.unshift(category);
					
						//build object of the parts array using the field names array as property name reference
						var dataObj:Object = arrayToTableObject(rowData);
					
						if(_dataDirectory[category] == undefined){	
							_data.addItem(dataObj);
							_dataDirectory[category] = dataObj;
						}
						else{
							var obj:Object = _dataDirectory[category];
							updateObj(obj, rowData);
						}
						numDataRows++;
					}
				}  //for each
			} else {  //it is a maximization of the original dataGrid, so just refresh the dataprovider with the 
					  //updated data, and set the clusterHealth image and tooltip to be the same as the parent DataGrid
				_clusterHealthIcon.data = (_expandedPaneParent as ClusterStatsTable)._clusterHealthIcon.data;
				_clusterHealthIcon.toolTip = (_expandedPaneParent as ClusterStatsTable)._clusterHealthIcon.toolTip;
			}
			
			_display.rowCount = numDataRows;
			_data.refresh();
		}
		
		private function updateObj(obj:Object, vals:Array):void{
			if(_fieldNames.length != vals.length){
				Logger.logError(this, "Object being updated doesn't match update data!");
				return;
			}
			for(var i:int = 0; i < _fieldNames.length; i++){
				obj[_fieldNames[i]] = vals[i]; //updating all for now
			}
		}
		
		private function arrayToTableObject(rowDataArray:Array):Object{
			var dataObj:Object = new Object();
			for (var i:int = 0 ; i < rowDataArray.length ; i++){
				var value : Object = rowDataArray[i];
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
				dataObj[_fieldNames[i]] = value;
			}
			return dataObj;
		}
		
		protected override function updateDisplay(data:XML):void {
			fillDisplay(data);
		}
				
		private function refreshDisplay(event:Event=null):void{
			_data.refresh(); //for some reason the ADG isn't refreshing on scroll...
		}

	}
}