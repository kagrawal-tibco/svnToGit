package com.tibco.be.views.user.components.drilldown.tabletree.model{
	
	public class TableTreeColumnConfig{
		
		private var _id:String;
		private var _type:String;
		private var _align:String;
		private var _fontStyle:String;
		
		public function TableTreeColumnConfig(id:String="", type:String="", align:String="", fontStyle:String=""){
			_id = id;
			_type = type;
			_align = align;
			_fontStyle = fontStyle;
		}
		
		public function get id():String{ return _id; }
		public function get type():String{ return _type; }
		public function get align():String{ return _align; }
		public function get fontStyle():String{ return _fontStyle; }
		
		public function set id(value:String):void{ _id = value; }
		public function set type(value:String):void{ _type = value; }
		public function set align(value:String):void{ _align = value; }
		public function set fontStyle(value:String):void{ _fontStyle = value; }
		
		public static function buildFromColConfigXML(configXML:XML):TableTreeColumnConfig{
			return new TableTreeColumnConfig(
				new String(configXML.@id),
				new String(configXML.@type),
				new String(configXML.@align),
				new String(configXML.@fontstyle)
			);
		}

	}
}