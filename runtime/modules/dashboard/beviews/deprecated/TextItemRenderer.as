package com.tibco.be.views.deprecated{
	
	import mx.controls.Label;
	
	
	public class TextItemRenderer extends Label {
		
		protected var _headerConfig:XML;
		protected var _headerConfigSet:Boolean;
		protected var _columnConfig:XML;
		protected var _columnConfigSet:Boolean;
		
		protected var columnId:String;
		protected var alignment:String;
		protected var fontSize:Number;
		protected var _defaultColor:String;
		protected var _defaultFontStyle:String;
		
		public function TextItemRenderer() {
			alignment = null;
			fontSize = -1;
			_defaultColor = null;
			_defaultFontStyle = null;
			_headerConfigSet = false;
			_columnConfigSet = false;
		}
		
		public function set headerConfig(headerConfig:XML):void {
			_headerConfig = headerConfig;
			_headerConfigSet = true;
			if (_columnConfigSet == true) {
				init();
			}
		}
		
		public function set columnConfig(columnConfig:XML):void {
			_columnConfig = columnConfig;
			_columnConfigSet = true;
			if (_headerConfigSet == true) {
				init();
			}			
		}
		
		private function init():void {
			if (_columnConfig != null) {
				columnId = String(_columnConfig.@id);
				alignment = String(_columnConfig.@align);
				if (alignment == null) {
					if (_headerConfig != null) {
						alignment = String(_headerConfig.@align);
					}
					if (alignment == null) {
						alignment = "center";
					}	
				}
				fontSize = Number(_columnConfig.@fontsize);
				if (fontSize <= 0 ) {
					if (_headerConfig != null) {
						fontSize = Number(_headerConfig.@fontsize);
					}
					if (fontSize < 0) {
						fontSize = 10;
					}										
				}
				_defaultColor = String("#"+_columnConfig.@fontcolor);
				_defaultFontStyle = String(_columnConfig.@fontstyle);
				if (_defaultFontStyle == null) {
					if (_headerConfig != null) {
						_defaultFontStyle = String(_headerConfig.@fontstyle);
					}
					if (_defaultFontStyle == null) {
						_defaultFontStyle = "normal";
					}
				}				
			}
		}
		
		public function set defaultColor(fontColor:String):void {
			_defaultColor = fontColor;
			if (_defaultColor.charAt(0) != '#') {
				_defaultColor = "#" + _defaultColor;
			}
		}
		
		public function set defaultFontStyle(fontStyle:String):void {
			_defaultFontStyle = fontStyle;
		}				
		
		public function get columnConfig():XML {
			return _columnConfig;
		}			
		
		override public function set data(value:Object):void {
			if (value == null) {
				return;
			}
			super.data = value;
			var columnData:Object = data[columnId];
			if (columnData == null) {
				return;
			}			
			//alignment
			setStyle("textAlign",alignment);			
			//font size
			setStyle("fontSize",fontSize);
			
			//trace(getQualifiedClassName(this)+"::"+this+"::columnId = "+columnId+"::text="+columnData.displayvalue+"::basecolor="+columnData.basecolor);
			text = columnData.displayvalue;
			if (columnData.tooltip != undefined) {
				toolTip = columnData.tooltip;
			}
			else{
				toolTip = text;
			}
			if (columnData.link != null) {
				useHandCursor = true;
				buttonMode = true;
				mouseChildren = false;
			}
			else{
				useHandCursor = false;
				buttonMode = false;
				mouseChildren = true;
			}
			//change font color if present in value
			var color:String = columnData.color;
			var fontStyle:String = columnData.style;
			if (color == null) {
				color = _defaultColor;
			}
			if (fontStyle == null) {
				fontStyle = _defaultFontStyle;
			}
			//font color
			setStyle("color",color);
			//font style
			if (fontStyle.indexOf("italic") != -1) {
				setStyle("fontStyle","italic");
			}
			else{
				setStyle("fontStyle","normal");
			}
			//font weight
			if (fontStyle.indexOf("bold") != -1) {
				setStyle("fontWeight","bold");
			}
			else{
				setStyle("fontWeight","normal");
			}				
		}
		
	
		/*override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
			trace(getQualifiedClassName(this)+"::"+this+"::columnId = "+columnId+"::text="+text+"::unscaledWidth="+unscaledWidth+"::unscaledHeight="+unscaledHeight);
			super.updateDisplayList(unscaledWidth,unscaledHeight);
		}*/		
	}
}