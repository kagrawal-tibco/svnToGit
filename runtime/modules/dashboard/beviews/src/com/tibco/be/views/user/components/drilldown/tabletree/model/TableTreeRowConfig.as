package com.tibco.be.views.user.components.drilldown.tabletree.model{
	
	import flash.utils.Dictionary;
	
	public class TableTreeRowConfig{
		
		protected var _id:String;
		protected var _templateType:String;
		protected var _height:int;
		protected var _columnConfigs:Dictionary;
		protected var _actionConfig:XML;
		
		public function TableTreeRowConfig(id:String="", templateType:String="", height:int=0, columnConfigs:Dictionary=null, actionConfig:XML=null){
			_id = id;
			_templateType = templateType;
			_height = height;
			_columnConfigs = columnConfigs != null ? columnConfigs:new Dictionary();
			_actionConfig = actionConfig != null ? actionConfig:<root />;
		}
		
		public function get id():String{ return _id; }
		public function get templateType():String{ return _templateType; }
		public function get height():int{ return _height; }
		public function get actionConfig():XML{ return _actionConfig; }
		
		public function set id(value:String):void{ _id = value; }
		public function set templateType(value:String):void{ _templateType = value; }
		public function set height(value:int):void{ _height = value; }
		public function set actionConfig(value:XML):void{ _actionConfig = value; }
		
		public function getColumnConfigById(colId:String):TableTreeColumnConfig{
			if(_columnConfigs[colId] == undefined){ return null; }
			return _columnConfigs[colId] as TableTreeColumnConfig;
		}
		
		public function setColumnConfigsByRowXML(rowXML:XML):void{
			_columnConfigs = new Dictionary();
			for each(var col:XML in rowXML.columnconfig){
				var colId:String = new String(col.@id);
				_columnConfigs[colId] = TableTreeColumnConfig.buildFromColConfigXML(col);
			}
		}
		
		public static function buildFromRowConfigXML(configXML:XML):TableTreeRowConfig{
			var rowConfig:TableTreeRowConfig = new TableTreeRowConfig(
				new String(configXML.@id),
				new String(configXML.@templatetype),
				configXML.@height
			);
			rowConfig.setColumnConfigsByRowXML(configXML);
			rowConfig.actionConfig = configXML.actionconfig != undefined ? configXML.actionconfig[0]:<root />;
			return rowConfig;
		}

	}
}