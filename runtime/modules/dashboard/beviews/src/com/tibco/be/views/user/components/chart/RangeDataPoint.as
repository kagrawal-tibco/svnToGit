package com.tibco.be.views.user.components.chart{
	
	import flash.utils.Dictionary;
	
	public class RangeDataPoint implements IChartDataPoint{
		
		public static const MIN_FIELD_NAME:String = "min";
		public static const CURRENT_FIELD_NAME:String = "current";
		public static const MAX_FIELD_NAME:String = "max";
		
		private var _colID:String;
		private var _min:Number;
		private var _max:Number;
		private var _current:Number;
		private var _minDisplayValue:String;
		private var _maxDisplayValue:String;
		private var _currentDisplayValue:String;
		private var _minToolTip:String;
		private var _maxToolTip:String;
		private var _currentToolTip:String;
		private var _link:String;
		private var _typeSpecificAttributes:Dictionary;
		private var _alertColors:Object;// {baseColor:uint, highlightColor:uint}
		private var _alertFont:Object;// {color:uint, style:String}
		private var _isFlaggedForDeletion:Boolean;
		
		public function get colID():String{ return _colID; }
		public function get value():Number{ return _current; }
		public function get valueObj():Object{ return {min:_min, current:_current, max:_max}; }
		public function get min():Number{ return _min; }
		public function get max():Number{ return _max; }
		public function get current():Number{ return _current; }
		public function get minDisplayValue():String{ return _minDisplayValue; }
		public function get maxDisplayValue():String{ return _maxDisplayValue; }
		public function get currentDisplayValue():String{ return _currentDisplayValue; }
		public function get minToolTip():String{ return _minToolTip; }
		public function get maxToolTip():String{ return _maxToolTip; }
		public function get currentToolTip():String{ return _currentToolTip; }
		public function get link():String{ return _link; }
		public function get typeSpecificAttribs():Dictionary{ return _typeSpecificAttributes; }
		public function get alertColors():Object{ return _alertColors; }
		public function get alertFont():Object{ return _alertFont; }
		public function get isFlaggedForDeletion():Boolean{ return _isFlaggedForDeletion; }
		
		public function set colID(value:String):void{ _colID = value; }
		public function set min(value:Number):void{ _min = value; }
		public function set max(value:Number):void{ _max = value; }
		public function set current(value:Number):void{ _current = value; }
		public function set minDisplayValue(value:String):void{ _minDisplayValue = value; }
		public function set maxDisplayValue(value:String):void{ _maxDisplayValue = value; }
		public function set currentDisplayValue(value:String):void{ _currentDisplayValue = value; }
		public function set minToolTip(value:String):void{ _minToolTip = value; }
		public function set maxToolTip(value:String):void{ _maxToolTip = value; }
		public function set currentToolTip(value:String):void{ _currentToolTip = value; }
		public function set link(value:String):void{ _link = value; }
		public function set typeSpecificAttribs(value:Dictionary):void{ _typeSpecificAttributes = value; }
		public function set alertColors(value:Object):void{ _alertColors = value; }
		public function set alertFont(value:Object):void{ _alertFont = value; }
		
		public function RangeDataPoint(columnXML:XML=null){
			_colID = columnXML != null ? new String(columnXML.@id):"";
			_min = 0;
			_minDisplayValue = "";
			_minToolTip = "";
			_max = 0;
			_maxDisplayValue = "";
			_maxToolTip = "";
			_current = 0;
			_currentDisplayValue = "";
			_currentToolTip = "";
			_link = "";
			_typeSpecificAttributes = new Dictionary();
			_alertColors = null;
			_alertFont = null;
			_isFlaggedForDeletion = false;
		}
		
		public function needsUpdateFrom(newRDP:RangeDataPoint):Boolean{
			return(
				_min != newRDP.min ||
				_max != newRDP.max ||
				_current != newRDP.current
			);
		}
		
		public function updateFrom(newRDP:RangeDataPoint):void{
			if(_colID == newRDP.colID){
				_min = newRDP.min;
				_minDisplayValue = newRDP.minDisplayValue;
				_minToolTip = newRDP.minToolTip;
				_max = newRDP.max;
				_maxDisplayValue = newRDP.maxDisplayValue;
				_maxToolTip = newRDP.maxToolTip;
				_current = newRDP.current;
				_currentDisplayValue = newRDP.currentDisplayValue;
				_currentToolTip = newRDP.currentToolTip;
				_link = newRDP.link;
				_typeSpecificAttributes = newRDP.typeSpecificAttribs;
			}
		}
		
		public function flagForDeletion():void{ _isFlaggedForDeletion = true; }
		
		public function destroy():void{
			_colID = null;
			_min = 0x0;
			_max = 0x0;
			_current = 0x0;
			_minDisplayValue = null;
			_maxDisplayValue = null;
			_currentDisplayValue = null;
			_minToolTip = null;
			_maxToolTip = null;
			_currentToolTip = null;
			_link = null;
			_typeSpecificAttributes = null;
			_alertColors = null;// {baseColor:uint, highlightColor:uint}
			_alertFont = null;// {color:uint, style:String}
		}

	}
}