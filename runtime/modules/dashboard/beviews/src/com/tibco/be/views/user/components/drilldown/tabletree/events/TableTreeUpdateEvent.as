package com.tibco.be.views.user.components.drilldown.tabletree.events{
	
	import com.tibco.be.views.core.events.EventBusEvent;
	
	import flash.utils.Dictionary;
	
	public class TableTreeUpdateEvent extends EventBusEvent{
		
		public static const TABLE_UPDATED:String = "tableTreeUpdatedEvent";
		
		protected var _command:String;
		protected var _rowPath:String;
		protected var _requestParams:Dictionary;
		protected var _resultRowCount:int;
		protected var _isTableExportEnabled:Boolean;
		
		public function TableTreeUpdateEvent(command:String, rowPath:String, requestParams:Dictionary, resultRowCount:int, isTableExportEnabled:Boolean){
			super(TABLE_UPDATED);
			_command = command;
			_rowPath = rowPath;
			_requestParams = requestParams;
			_resultRowCount = resultRowCount;
			_isTableExportEnabled = isTableExportEnabled;			
		}
		
		public function get command():String{ return _command; }
		public function get rowPath():String{ return _rowPath; }
		public function get requestParams():Dictionary{ return _requestParams; }
		public function get resultRowCount():int{ return _resultRowCount; }
		public function get isTableExportEnabled():Boolean{ return _isTableExportEnabled; }
		
		override public function toString():String{
			var thisString:String = "{command:" + _command + ",rowPath:" + _rowPath + ",requestParams:{";
			for(var key:Object in _requestParams){
				thisString += key + ":" + _requestParams[key] + ","
			}
			thisString += "},resultRowCount:" + _resultRowCount + ",isTableExportEnabled: " + _isTableExportEnabled + "}";
			return thisString;
		}
		
	}
}