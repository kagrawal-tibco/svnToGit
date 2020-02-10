package com.tibco.be.views.user.components.table{
	
	import com.tibco.be.views.user.components.IComponentDataProvider;
	import com.tibco.be.views.user.components.chart.DataColumn;
	
	import flash.utils.Dictionary;
	
	import mx.collections.ArrayCollection;
	import mx.collections.Sort;
	import mx.collections.SortField;
	import mx.events.CollectionEvent;
	import mx.events.CollectionEventKind;
	import mx.events.PropertyChangeEvent;
	import mx.events.PropertyChangeEventKind;

	/**
	 * The TableDataProvider stores data for the table component. The incoming XML is parsed and converted into a object
	 * The structure of the object is as follows 
	 * Row Object 
	 * 		id : rowXML@id <used for quick lookup>
	 * 		sortorder : rowXML@sortorder <used for resorting the data after an update>
	 * 		<columnid> : Data Column <Data Column keyed by their id>
	 */ 		 
	public class BEVTableDataProvider extends ArrayCollection implements IComponentDataProvider{
		
		private static const EMPTY_ARRAY:Array = new Array(0);
		
		protected var _dataRowConfigXML:XML;
		protected var _dataset:Dictionary;
		protected var _sort:Sort;
		
		public function BEVTableDataProvider(dataRowConfig:XML){
			_dataRowConfigXML = dataRowConfig;
			_dataset = new Dictionary();
		}
		
		public function setData(dataXML:XML):void{
			var dataRows:XMLList = dataXML.datarow.(@templateid == _dataRowConfigXML.@id && @templatetype == "data");
			for each(var rowXML:XML in dataRows){
				var row:Object = convertRowXMLToObject(rowXML);
				_dataset[row.id] = row;
				addItem(row);
			}
			_sort = new Sort();
			_sort.fields = [new SortField("sortorder")];			
		}
		
		public function getRowDataById(rowid:String):Object{
			return _dataset[rowid];
		}
		
		public function getRowDataByIndex(rowindex:int):Object{
			return getItemAt(rowindex);
		}
		
		public function getColumnDataById(row:Object,columnid:String):DataColumn{
			if(row == null){ return null; }
			return row[columnid] as DataColumn;
		}
		
		public function getColumnDataByIndex(row:Object,columnindex:int):DataColumn{
			if(row == null){ return null; }
			var dataColumnCfgList:XMLList = _dataRowConfigXML.child("columnconfig");
			if(dataColumnCfgList == null || dataColumnCfgList.length() < columnindex){
				return null;
			}
			var columnid:String = String(dataColumnCfgList[columnindex].@id);
			return row[columnid] as DataColumn;
		}
		
		
		protected function convertRowXMLToObject(rowXML:XML):Object{
			var key:String = String(rowXML.@id);
			var row:Object = {id:key, sortorder:Number(rowXML.@sortorder)};
			for each(var columnXML:XML in rowXML.datacolumn){
				row[String(columnXML.@id)] = convertColumnXMLToObject(columnXML);
			}
			return row;
		}
		
		protected function convertColumnXMLToObject(columnXML:XML):DataColumn{
			return new DataColumn(columnXML);
		}

		public function updateData(updateXML:XML):void{
			var sortNeeded:Boolean = false;
			//extract the keys to be retained (can be empty)
			var keysToBeRetained:Array = extractKeysToRetain(updateXML);
			if(keysToBeRetained != null){
				//keep only the keys which are relevant 
				for(var rowKey:String in _dataset){
					if(keysToBeRetained.indexOf(rowKey) == -1){
						//we dont need this row, remove it from the underlying arraycollection 
						removeItemAt(getItemIndex(_dataset[rowKey]));
						//remove from our quick lookup dictionary
						delete _dataset[rowKey];
					}
				}
			}
			//go through the actual data rows 
			var dataRows:XMLList = updateXML.datarow.(@templateid == _dataRowConfigXML.@id && @templatetype == "data");
			for each(var rowXML:XML in dataRows){
				var key:String = String(rowXML.@id);
				//find an existing data row using the lookup dictionary 
				var existingDataRow:Object = _dataset[key];
				//get the location of the existing data row from the array collection vis-a-vis the location of the row in the table
				var idx:int = getItemIndex(existingDataRow);
				if(existingDataRow != null){
					//data row exists , check the sort order, it could have changed 
					if(existingDataRow["sortorder"] != rowXML.@sortorder){
						//update the sort order and turn on the flag for sorting 
						existingDataRow["sortorder"] = Number(rowXML.@sortorder);
						sortNeeded = true; 
					}
					//update the data columns 
					updateDataColumns(idx,existingDataRow,rowXML);
				}
				else{
					//we have a new data row 
					var row:Object = convertRowXMLToObject(rowXML);
					//add the row to the quick lookup dictionary 
					_dataset[row.id] = row;
					//check whether to add the row at the end of the table or somewhere in between 
					if(length < row.sortorder){
						//sort order is more than the length , add to bottom 
						addItem(row);	
					}
					else{
						//inject the row in between 
						addItemAt(row,row.sortorder);
					}
				}
			}
			if(keysToBeRetained == null && dataRows.length() == 0){
				//we have essentially an empty data update , remove all rows 
				for(var rowKey2:String in _dataset){
					//remove it from the underlying arraycollection 
					removeItemAt(getItemIndex(_dataset[rowKey2]));
					//remove from our quick lookup dictionary
					delete _dataset[rowKey2];
				}
			}
			if(sortNeeded == true){
				//we have to sort , get the original source of the array collection 
				var src:Array = source;
				//sort it 
				_sort.sort(src);
				//reset it back to the array collection 
				source = src;
				//refresh the collection 
				refresh(); 
			}
		}	
		
		
		protected function updateDataColumns(existingDataRowIdx:int, existingDataRow:Object,rowXML:XML):void{
			var updatedColIDEvents:Array = new Array(); 
			for each(var columnXML:XML in rowXML.datacolumn){
				var colID:String = String(columnXML.@id);
				var newColObject:DataColumn = convertColumnXMLToObject(columnXML);
				var propertyChangeEvent:PropertyChangeEvent;
				if(existingDataRow[colID] == null){
					existingDataRow[colID] = newColObject;
					propertyChangeEvent = new PropertyChangeEvent(PropertyChangeEvent.PROPERTY_CHANGE);
					propertyChangeEvent.property = String(columnXML.@id);
					propertyChangeEvent.kind = PropertyChangeEventKind.UPDATE;
					propertyChangeEvent.oldValue = null;
					propertyChangeEvent.newValue = existingDataRow[colID];
					propertyChangeEvent.source = existingDataRow;
					updatedColIDEvents.push(propertyChangeEvent);
				}
				else{
					var propertyChangeEvents:Array = mergeColumnObject(existingDataRow[colID], newColObject);
					if(propertyChangeEvents.length != 0){
						propertyChangeEvent = new PropertyChangeEvent(PropertyChangeEvent.PROPERTY_CHANGE);
						propertyChangeEvent.property = String(columnXML.@id);
						propertyChangeEvent.kind = PropertyChangeEventKind.UPDATE;
						propertyChangeEvent.oldValue = existingDataRow[colID];
						propertyChangeEvent.newValue = existingDataRow[colID];
						propertyChangeEvent.source = existingDataRow;
						updatedColIDEvents.push(propertyChangeEvent);
					}
				}
			}
			if(updatedColIDEvents.length > 0){
				dispatchEvent(new CollectionEvent(CollectionEvent.COLLECTION_CHANGE, false, false, CollectionEventKind.UPDATE,existingDataRowIdx, -1, updatedColIDEvents)); 
			}			
		}	
		
		/**
		 * Extracts the keys to be retained from the incoming update. The keys are extracted from the header row. 
		 * Returns null if no header row is found in the incoming update
		 */ 
		protected function extractKeysToRetain(updateXML:XML):Array{
			var dataRows:XMLList = updateXML.datarow.(@templateid == _dataRowConfigXML.@id && @templatetype == "header" && @visualtype == "categorycolumn");
			if(dataRows != null && dataRows.length() == 1){
				var keys:Array = new Array();
				for each(var datacolumn:XML in dataRows[0].datacolumn){
					keys.push(String(datacolumn.@id));
				}
				return keys;
			}
			return null; 
		}
		
		protected function mergeColumnObject(existingColObject:DataColumn, newColObject:DataColumn):Array{
			var changes:Array = new Array();
			//we assume that the newColObject superseeds existing object in terms of details
			var updateProperties:Array = ["value", "valueObj", "displayValue", "tooltip", "link", "fontColor", "fontStyle", "sortDirection"];
			for each(var prop:String in updateProperties){
				var chg:PropertyChangeEvent = mergeProperty(prop, existingColObject, newColObject);
				if(chg != null){ changes.push(chg); }
			}
			if(existingColObject.fillColors != null && newColObject.fillColors != null){
				chg = mergeProperty("baseColor", existingColObject.fillColors, newColObject.fillColors);
			}
			else{
				chg = new PropertyChangeEvent(
							PropertyChangeEvent.PROPERTY_CHANGE,
							false,
							false,
							PropertyChangeEventKind.UPDATE,
							"baseColor",
							existingColObject.fillColors,
							newColObject.fillColors,
							existingColObject
							);
				existingColObject.fillColors = newColObject.fillColors;											
			}
			if(chg != null){ changes.push(chg); }
			return changes;
		}	
		
		private function mergeProperty(propertyName:String, oldSource:Object, newSource:Object):PropertyChangeEvent{
			if(oldSource == null || newSource == null){ return null; }
			var oldVal:Object = oldSource[propertyName];
			var newVal:Object = newSource[propertyName];
			if(newVal != oldVal){
				oldSource[propertyName] = newVal;
				return new PropertyChangeEvent(
					PropertyChangeEvent.PROPERTY_CHANGE,
					false,
					false,
					PropertyChangeEventKind.UPDATE,
					propertyName,
					oldVal,
					newVal,
					oldSource
				);
			}
			return null;
		}
		
		override public function removeAll():void {
			var keys:Array = new Array();
			for (var key:* in _dataset) {
    			keys.push(key);
   			}	
			for each (var key1:* in keys) {
    			delete _dataset[key1];
			}
			super.removeAll();		
		}	
	}
	
}