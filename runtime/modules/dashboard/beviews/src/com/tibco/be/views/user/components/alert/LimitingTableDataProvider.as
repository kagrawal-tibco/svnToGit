package com.tibco.be.views.user.components.alert{
	
	import com.tibco.be.views.user.components.table.BEVTableDataProvider;

	public class LimitingTableDataProvider extends BEVTableDataProvider{
		
		private static const DEFAULT_LIMIT:Number = 50;
		
		private var _limit:Number = DEFAULT_LIMIT;
				
		public function LimitingTableDataProvider(dataRowConfig:XML,limit:Number) {
			super(dataRowConfig);
			this._limit = limit;
		}
		
		override public function setData(dataXML:XML):void {
			var dataRows:XMLList = dataXML.datarow.(@templateid == _dataRowConfigXML.@id && @templatetype == "data");
			var rowCnt:Number = 0;
			for each (var rowXML:XML in dataRows) {
				var row:Object = convertRowXMLToObject(rowXML);
				_dataset[row.id] = row;
				addItem(row);
				rowCnt++;
				if (rowCnt >= _limit) {
					break;
				}
			}
		}
		
		override public function updateData(updateXML:XML):void {
			var dataRows:XMLList = updateXML.datarow.(@templateid == _dataRowConfigXML.@id && @templatetype == "data");
			var rowCnt:Number = 0;
			for each (var rowXML:XML in dataRows) {
				var row:Object = convertRowXMLToObject(rowXML);
				_dataset[row.id] = row;
				addItemAt(row,0);
				if (length > _limit) {
					removeItemAt(length-1);
				}
				rowCnt++;
				if (rowCnt >= _limit) {
					break;
				}
			}			
		}		
		
		
	}
}