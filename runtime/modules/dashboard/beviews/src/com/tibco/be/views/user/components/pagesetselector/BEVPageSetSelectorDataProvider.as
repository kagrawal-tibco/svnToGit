package com.tibco.be.views.user.components.pagesetselector{
	
	import com.tibco.be.views.user.components.table.BEVTableDataProvider;

	public class BEVPageSetSelectorDataProvider extends BEVTableDataProvider {
		
		private var _pageSetRowConfigs:XMLList;
		
		public function BEVPageSetSelectorDataProvider(pagesetrowconfigs:XMLList) {
			super(null);
			this._pageSetRowConfigs = pagesetrowconfigs;
			var pagesetrowconfigscnt:int = pagesetrowconfigs.length();
			for (var i:int = 0 ; i < pagesetrowconfigscnt ; i++) {
				var pagesetrowconfig:XML = _pageSetRowConfigs[i];
				var data:Object = new Object();
				data.id = new String(pagesetrowconfig.@pagesetid);
				data.title = new String(pagesetrowconfig.title[0]);
				data.tooltip = new String(pagesetrowconfig.tooltip[0]);	
				for each (var columnconfig:XML in pagesetrowconfig.columnconfig) {
					data[new String(columnconfig.@id)] = new Object();
				}
				data.last = !(i+1 < pagesetrowconfigscnt);				
				addItem(data);
				_dataset[data.id] = data;
			}			
		}
		
		override public function setData(dataXML:XML):void {
			updateData(dataXML);
		}
		
		override public function updateData(updateXML:XML):void {
			var idx:int = 0;
			for each (var pagesetrowconfig:XML in _pageSetRowConfigs) {
				var dataRows:XMLList = updateXML.datarow.(@id == pagesetrowconfig.@pagesetid);
				if (dataRows != null && dataRows.length() == 1) {
					var data:Object = convertRowXMLToObject(dataRows[0]);
					var existingDataRow:Object = _dataset[data.id];
					if (existingDataRow == null) {
						addItem(data);
						_dataset[data.id] = data;
					}
					else{
						updateDataColumns(idx,existingDataRow,dataRows[0]);
					}
				}
				idx++;
			}			
		}				
		
	}
}