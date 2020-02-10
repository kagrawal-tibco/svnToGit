package com.tibco.be.views.user.components.chart{
	
	import flash.utils.Dictionary;
	
	public class DataColumn implements IChartDataPoint{
		
		public static const COLOR_NONE:Number = -1;
		
		private var _colID:String;
		private var _value:String;
		private var _displayValue:String;
		private var _tooltip:String;
		private var _link:String;
		private var _fillColors:Object;
		private var _fontColor:Number;
		private var _fontStyle:String;
		private var _sortDirection:String;
		private var _typeSpecificAttribs:Dictionary;
		
		public function get colID():String{ return _colID; }
		public function get value():Number{ return parseFloat(_value); }
		public function get valueObj():Object{ return _value; }
		public function get displayValue():String{ return _displayValue; }
		public function get tooltip():String{ return _tooltip; }
		public function get link():String{ return _link; }
		public function get fillColors():Object{ return _fillColors; }
		public function get fontColor():Number{ return _fontColor; }
		public function get fontStyle():String{ return _fontStyle; }
		public function get sortDirection():String{ return _sortDirection; }
		public function get typeSpecificAttribs():Dictionary{ return _typeSpecificAttribs; }
		
		public function set colID(colID:String):void{ _colID = colID; }
		public function set value(val:Number):void{ _value = String(val); }
		public function set valueObj(valueObj:Object):void{ _value = new String(valueObj); }
		public function set displayValue(displayValue:String):void{ _displayValue = displayValue; }
		public function set tooltip(tooltip:String):void{ _tooltip = tooltip; }
		public function set link(link:String):void{ _link = link ; }
		public function set fillColors(fillColors:Object):void{ _fillColors = fillColors; }
		public function set fontColor(fontColor:Number):void{ _fontColor = fontColor; }
		public function set fontStyle(fontStyle:String):void{ _fontStyle = fontStyle; }
		public function set sortDirection(direction:String):void{ _sortDirection = direction; }
		public function set typeSpecificAttribs(typeSpecificAttribs:Dictionary):void{ _typeSpecificAttribs = typeSpecificAttribs; }		
		
		public function DataColumn(colXML:XML){
			setValues(colXML);
		}
		
		public function updateFrom(updateXML:XML):void{
			_fontColor = COLOR_NONE;
			_fontStyle = null;
			setValues(updateXML);		
		}
		
		private function setValues(colXML:XML):void{
			_colID = new String(colXML.@id);
			_value = colXML.value;
			_displayValue = new String(colXML.displayvalue);
			_tooltip = new String(colXML.tooltip);
			_link = new String(colXML.link);
			_sortDirection = "none"
			_typeSpecificAttribs = new Dictionary();
			
			if(colXML.sortvalue != undefined){
				_sortDirection = new String(colXML.sortvalue);
			}
			if(colXML.font != undefined){
				_fontColor = parseInt(colXML.font.@color, 16);
				if (isNaN(_fontColor) == true) {
					_fontColor = COLOR_NONE;
				}
				_fontStyle = new String(colXML.font.@style);
			}
			else{
				_fontColor = COLOR_NONE;
				_fontStyle = null;
			}
			
			try{
				var baseColor:Number = parseInt(colXML.basecolor, 16);
				var highlightColor:Number = parseInt(colXML.highlightcolor, 16);
				if(isNaN(baseColor) == true){
					_fillColors = null;
				}
				else{
					if(isNaN(highlightColor) == true){
						highlightColor = 0xFFFFFF;
					}
					_fillColors = {"baseColor":baseColor, "highlightColor":highlightColor};	
				}
				
				for each(var attribXML:XML in colXML.typespecificattribute){
					var name:String = new String(attribXML.@name);
					var value:String = new String(attribXML);
					typeSpecificAttribs[name] = value;
				}
			}
			catch(error:Error){
				_fillColors = null;
			}
		}
		
		public function correspondsTo(columnXML:XML):Boolean{
			return (this.colID == String(columnXML.@id));
		}
		
		public function needsUpdateFrom(newData:XML):Boolean{
			var nVal:Number = parseFloat(newData.value);
			var sVal:String = new String(newData.value);
			if(isNaN(nVal)){
				return(
					this.colID == String(newData.@id) &&
					this.displayValue == newData.@displayvalue
				);
				
			}
			return(
				this.colID == String(newData.@id) &&
				this.value != nVal
			);
			
		}
		
		public function destroy():void{
			_colID = null;
			_value = null;
			_displayValue = null;
			_tooltip = null;
			_link = null;
			_fillColors = null;
			_fontColor = COLOR_NONE;
			_fontStyle = null;
			_sortDirection = null;
			_typeSpecificAttribs = null;
		}
		
	}
}