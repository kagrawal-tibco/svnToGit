package com.tibco.be.views.user.components.chart{
	
	import mx.controls.Label;
	
	/** 
	 * This class doesn't serve much purpose except to make it easy to change type of UI object
	 * that gets used (i.e. UITextField, Label, etc.).  Also, it's useful to initiate properties.
	*/
	public class BEVChartDataLabel extends Label{
		
		private var _defaultFontColor:uint = 0x0;
		private var _defaultFontWeight:String = "normal";
		private var _defaultFontStyle:String = "normal";
		
		public function BEVChartDataLabel(labelConfig:XML){
			super();
			truncateToFit = false;
			styleName = "dataLabel";
						
			if(labelConfig != null){
				var fontSize:int = parseInt(labelConfig.@fontsize);
				var fontStyle:String = new String(labelConfig.@fontstyle);
				
				_defaultFontColor = parseInt(labelConfig.@fontcolor, 16);
				
				setStyle("fontSize", Math.max(8, fontSize));
				setStyle("color", _defaultFontColor);
				if(fontStyle.indexOf("bold") >= 0){
					_defaultFontWeight = "bold";
					setStyle("fontWeight", _defaultFontWeight);
				}
				if(fontStyle.indexOf("italic") >= 0){
					_defaultFontStyle = "italic";
					setStyle("fontStyle", _defaultFontStyle);
				}
			} 
		}
		
		public function setDefaultFontColor():void{
			var currentColor:uint = parseInt(getStyle("color"), 16);
			if(currentColor != _defaultFontColor){ //don't set style if not needed
				setStyle("color", _defaultFontColor);
			}
		}
		
		public function setDefaultFontWeight():void{
			if(getStyle("fontWeight") != _defaultFontWeight){
				setStyle("fontWeight", _defaultFontWeight);
			}
		}
		
		public function setDefaultFontStyle():void{
			if(getStyle("fontStyle") != _defaultFontStyle){
				setStyle("fontStyle", _defaultFontStyle);
			}
		}
		
		override public function set text(value:String):void{
//			if(getStyle("fontStyle") == "italic"){ value = value + " "; }
			super.text = " " + value + " "; //prevent cut-off and keep centering
		}

	}

//	import flash.display.BlendMode;
//	import flash.text.TextFormat;
//	
//	import mx.core.UITextField;
//	
//	public class BEViewsChartDataLabel extends UITextField{
//		
//		private var _defaultSize:int = 8;
//		private var _defaultColor:uint = 0x0;
//		private var _defaultBold:String = null;
//		private var _defaultItalic:String = null;
//		
//		private var _format:TextFormat;
//		
//		public function BEViewsChartDataLabel(labelConfig:XML){
//			super();
//			background = true;
//			backgroundColor = 0xffffff;
//			border = true;
//			borderColor = 0x0;
//			multiline = false;
//			selectable = false;
//			alpha = .8;
//			blendMode = BlendMode.LAYER;
//			styleName = "dataLabel";
//			
//			if(labelConfig != null){
//				_defaultSize = Math.max(10, parseInt(labelConfig.@fontsize));
//				_defaultColor = parseInt(labelConfig.@fontcolor, 16);
//				var fontStyle:String = new String(labelConfig.@fontstyle);
//				if(fontStyle.indexOf("bold") >= 0){ _defaultBold = "bold"; }
//				if(fontStyle.indexOf("italic") >= 0){ _defaultItalic = "italic"; }
//			}
//			setDefaultFormat();
//		}
//		
//		private function get format():TextFormat{
//			if(_format == null){ setDefaultFormat(); }
//			return _format;
//		}
//		
//		public function set size(value:int):void{ format.size = value; }
//		public function set color(value:uint):void{ format.color = textColor = value; }
//		public function set bold(value:String):void{ format.bold = value; }
//		public function set italic(value:String):void{ format.italic = value; }
//		
//		public function setDefaultSize():void{ format.size = _defaultSize; }
//		public function setDefaultColor():void{ format.color = _defaultColor; }
//		public function setDefaultBold():void{ format.bold = _defaultBold; }
//		public function setDefaultItalic():void{ format.italic = _defaultItalic; }
//		
//		override public function setStyle(styleProp:String, value:*):void{
//			switch(styleProp){
//				case("color"): color = value as uint; break;
//				case("fontWeight"): bold = value is String && String(value).length > 0 ? String(value):null; break;
//				case("fontStyle"): italic = value is String && String(value).length > 0 ? String(value):null; break;
//			}
//		}
//		
//		public function setDefaultFormat():void{
//			_format = new TextFormat();
//			_format.size = _defaultSize;
//			_format.color = _defaultColor;
//			_format.bold = _defaultBold;
//			_format.italic = _defaultItalic;
//		}
//		
//		public function render():void{
//			setTextFormat(_format);
//		}
//		
//	}

}