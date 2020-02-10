package com.tibco.cep.ui.monitor.util
{
	import mx.utils.ObjectUtil;
	
	public class SortFunction
	{
		private static var _column:String;
		
		public function SortFunction()
		{
		}
		
		public static function set columnDataField(column:String):void {
			_column = column;
		}
		
		public static function get columnDataField():String {
			return _column;
		}
		
		public static function sortNumeric(obj1:Object, obj2:Object):int {
			return ObjectUtil.numericCompare(obj1[_column],obj2[_column]); 
//			return ObjectUtil.numericCompare(obj1.greenCnt,obj2.greenCnt); 
		}

	}
}