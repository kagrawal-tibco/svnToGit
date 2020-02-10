package com.tibco.cep.ui.monitor.panes.table{
	
	import com.tibco.cep.ui.monitor.events.*;
	import com.tibco.cep.ui.monitor.pages.PanelPage;
	import com.tibco.cep.ui.monitor.panels.GeneralPanel;
	import com.tibco.cep.ui.monitor.util.Logger;
	import com.tibco.cep.ui.monitor.util.XMLNodesConsts;
	
	import flash.events.KeyboardEvent;
	
	import mx.collections.ArrayCollection;
	import mx.containers.VBox;
	import mx.controls.DataGrid;
	import mx.controls.Label;
	import mx.controls.dataGridClasses.DataGridColumn;
	import mx.utils.StringUtil;
	
	public class GVsTableMultDUs extends VBox {
		
		[Bindable]
		protected var _data:ArrayCollection;
		private var _gvsXml:XML;
		
		private var _view:VBox;
		
		private var _table:DataGrid;
		private var _duFqn:String;
		private var _duName:String;
		
		private var nameCol:DataGridColumn;
		private var defValueCol:DataGridColumn;
		private var currValueCol:DataGridColumn;
		
		private var _parentPage:PanelPage;
		private var _parentPanel:GeneralPanel;
		
		private var _cols:Array;

		private var _hasDataChanged:Boolean;	//indicates if the data was changed in the table
		
		private var _changedRows:Array;	//Keep track of the table rows that were changed by the user.
		
		public function GVsTableMultDUs(duFqn:String, gvsXML:XML){

			_duFqn = duFqn;

			_hasDataChanged = false;
			
			_changedRows = new Array();

			var duSplit:Array = duFqn.split("/");
			_duName = duSplit[duSplit.length-1];
				
			//Table wrap because DataGrid object is not a Container.
			_view = new VBox(); 
			_view.x = 0;
			_view.y = 0;	
			_view.id = duFqn;
			_view.setStyle("backgroundColor", 0x778EA1);
			_view.setStyle("dividerAlpha", 0.7);
			_view.setStyle("dividerColor", 0x0099CC);
			_view.percentHeight=100;
			_view.percentWidth=100;
			
			//Table with GVs
			_table = new DataGrid();
			_table.percentWidth = 100;
			_table.styleName = "dataGridMetricStyle";
			_table.headerHeight = 24;
			_table.rowHeight = 28;			

			//set DataGrid (table) data provider			
			_data = new ArrayCollection()
			_table.dataProvider = _data;
			
			//DataGrid columns
			nameCol = new DataGridColumn("Global Variable Name");
			nameCol.dataField = XMLNodesConsts.GV_NODE_NAME_ATTR;
			nameCol.dataTipField = XMLNodesConsts.GV_NODE_NAME_ATTR;
			nameCol.showDataTips = true;
			
			defValueCol = new DataGridColumn("Default Value");
			defValueCol.dataField = XMLNodesConsts.GV_NODE_DEF_VAL_ATTR;
			defValueCol.dataTipField = XMLNodesConsts.GV_NODE_DEF_VAL_ATTR;
			defValueCol.showDataTips = true;

			currValueCol = new DataGridColumn("Current Value");
			currValueCol.dataField = XMLNodesConsts.GV_NODE_CURR_VAL_ATTR;
			currValueCol.dataTipField = XMLNodesConsts.GV_NODE_CURR_VAL_ATTR;
			currValueCol.showDataTips = true;
			currValueCol.editable=true
			
			_cols = new Array();
			_cols.push(nameCol);
			_cols.push(defValueCol);
			_cols.push(currValueCol);
			
			_table.columns = _cols;
			_table.editable = true;

			//Event Listeners
			_table.addEventListener(KeyboardEvent.KEY_DOWN, handleGVsTableEntryChange);
			
			//Create Label
			var label:Label = new Label();
			label.id = duFqn+"#lbl";
			label.text = "Deployment Unit: " + duFqn;
			label.styleName = "labelStyle";
			
			//Add Label and DataGrid table to the wrap
			_view.addChild(label);
			_view.addChild(_table);
			
			fillDisplay(gvsXML);
			
			Logger.logInfo(this,"Global variables table created and event listeners added");  
		}
		
		public function get view():VBox {return _view;}
			
		private function fillDisplay(data:XML):void{
			_data.removeAll();
			for each(var gv:XML in data.GlobalVariable){
				_data.addItem(
					{
						name:String(gv.@name),
						defaultValue:String(gv.@defaultValue),
						currentValue:String(gv.@currentValue)
					}
				);
			}
		}
		
		private function handleGVsTableEntryChange(keyDownEv:KeyboardEvent):void {
			Logger.logDebug(this, "Global Variables table entry changed by the user");
			
			var rowIndex:Number = (keyDownEv.currentTarget as DataGrid).editedItemPosition["rowIndex"];
			
            //Keep track of the table rows that were changed by the user. Only the XML of the rows changed by the
            //user are going to be sent to the server.
			if (_changedRows.indexOf(rowIndex) == -1)
			     _changedRows.push(rowIndex);
						
			_hasDataChanged=true;
		}
		
		public function getGVsXML():String {
			createGVsXML();
			return _gvsXml == null ? null : _gvsXml.toString();
		}
		
		private function createGVsXML():void {
			if (_hasDataChanged) {
				var gvs:Object = _table.dataProvider.source;
				
				_gvsXml = XML(StringUtil.substitute(XMLNodesConsts.GVS_NODE_PATTERN,_duName));
				
				for each (var changedRow:Number in _changedRows) {
					_gvsXml.appendChild(createGVLine(gvs[changedRow]));
				}
			}
			_hasDataChanged=false;
			_changedRows = new Array();      //reset
		} 
		
		private function createGVLine(gvObj:Object):String {
			return StringUtil.substitute(XMLNodesConsts.GV_NODE_PATTERN, 
						gvObj[XMLNodesConsts.GV_NODE_NAME_ATTR],
						gvObj[XMLNodesConsts.GV_NODE_DEF_VAL_ATTR],
						gvObj[XMLNodesConsts.GV_NODE_CURR_VAL_ATTR] );
		}
		
		public function saveGVsState():void {
			createGVsXML();
			Logger.logInfo(this,"Global variables saved.");
		}
		
	}
}