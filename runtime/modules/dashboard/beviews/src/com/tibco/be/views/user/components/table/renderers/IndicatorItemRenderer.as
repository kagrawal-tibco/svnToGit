package com.tibco.be.views.user.components.table.renderers{
	
	import com.tibco.be.views.user.components.chart.DataColumn;
	import com.tibco.be.views.user.utils.SimpleIndicator;
	
	import flash.display.DisplayObject;
	
	import mx.core.UITextField;
	
	public class IndicatorItemRenderer extends TextItemRenderer {
		
		private static const INDICATOR_DIM:int = 12;
		
		private var _indicator:SimpleIndicator;
		private var _textAnchor:String;
		private var _textFieldRemoved:Boolean;
		
		public function IndicatorItemRenderer(){
			_indicator = new SimpleIndicator();
			_indicator.color = SimpleIndicator.BLANK;
			_indicator.width = INDICATOR_DIM;
			_indicator.height = INDICATOR_DIM;			
			addChild(_indicator);
			_textFieldRemoved = false;	
		}
		
		override public function configure(headerConfig:XML, columnConfig:XML):void{
			super.configure(headerConfig, columnConfig);
			var showValueConfig:XMLList = columnConfig.typespecificattribute.(@name == "showvalue");
			if(showValueConfig != null && showValueConfig.length() != 0 && showValueConfig[0].text() == "true"){
				_textAnchor = columnConfig.typespecificattribute.(@name == "textanchor")[0].text();
			}
			//enable process fill color by default
			processFillColor = false;
		}
		
		override public function set data(value:Object):void{
			super.data = value;
			var columnData:DataColumn = null;
    		if (value != null && _columnId != ""){ 
				columnData = data[_columnId] as DataColumn;
    		}
			if(columnData == null){
				_indicator.color = SimpleIndicator.BLANK;
				return;
			}
			try{
				_indicator.color = new String(columnData.fillColors.baseColor);
			} catch(error:Error){
				_indicator.color = SimpleIndicator.BLANK;
			}
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
			super.updateDisplayList(unscaledWidth,unscaledHeight);
			if(data == null){
				return;
			}			
			
			var paddingRight:Number = getStyle("paddingRight");
			var paddingLeft:Number = getStyle("paddingLeft");
			var gap:Number = 2;//getStyle("horizontalGap");
			
			var availableTextWidth:int = unscaledWidth - INDICATOR_DIM - gap - paddingRight - paddingLeft;
									
			var indicatorX:int = -1;
			var indicatorY:int = (unscaledHeight - INDICATOR_DIM)/2;
			var textFldX:int = -1;
			//force the measure width to the actual width of the text field		
			textField.width = textField.measuredWidth;
			
			if(_textAnchor == "east"){
				//text field will be on the right hand side 
				if(textField is UITextField){
					UITextField(textField).setStyle("textAlign","left");
				}				
				if(_alignment == "left"){
					//[<left>indicator - text field</left>]
					indicatorX = 0 + paddingRight;
					textFldX = indicatorX + INDICATOR_DIM + gap;
				}
				else if(_alignment == "center"){
					//[<center>indicator - text field</center>]
					//check if the text field will fit in half area of the renderer
					var rightHalfAvailableWidth:int = (unscaledWidth - INDICATOR_DIM)/2 - gap - paddingRight;
					if(textField.width < rightHalfAvailableWidth){
						//yes, the text field will fit in half area 
						indicatorX = (unscaledWidth - INDICATOR_DIM)/2;
						textFldX = indicatorX + INDICATOR_DIM + gap;
					}
					else{
						//no the text field will not fit in the half area, lets check if it can fit in the entire available area 
						if(textField.width >= availableTextWidth ){
							//no the text field is lot bigger , lets limit to the available width
							textField.width = availableTextWidth;
						}
						textFldX = unscaledWidth - paddingRight - textField.width;
						indicatorX = textFldX - gap - INDICATOR_DIM;	
					}					
				}
				else if(_alignment == "right"){
					//[<right>indicator - text field</right>]
					//lets check if text field can fit in the entire available area 
					if(textField.width >= availableTextWidth ){
						//no the text field is lot bigger , lets limit to the available width
						textField.width = availableTextWidth;
					}					
					textFldX = unscaledWidth - paddingRight - textField.width;
					indicatorX = textFldX - gap - INDICATOR_DIM;					
				}				
			}
			else if(_textAnchor == "west"){
				//text field will be on the left hand side 
				if(textField is UITextField){
					UITextField(textField).setStyle("textAlign","right");
				}
				if(_alignment == "left"){
					//[<left>text field-indicator</left>]
					//lets check if text field can fit in the entire available area
					if(textField.width > availableTextWidth){
						//no the text field is lot bigger , lets limit to the available width
						textField.width = availableTextWidth;
					}
					textFldX = 0 + paddingLeft;
					indicatorX = textFldX + textField.width + gap;
				}
				else if(_alignment == "center"){
					//[<center>text field-indicator</center>]
					//check if the text field will fit in half area of the renderer
					var leftHalfAvailableWidth:int = (unscaledWidth - INDICATOR_DIM)/2 - gap - paddingLeft;					
					if(textField.width < leftHalfAvailableWidth){
						//yes, the text field will fit in half area 
						indicatorX = (unscaledWidth - INDICATOR_DIM)/2;
						textFldX = indicatorX - gap - textField.width;
					}
					else{
						//no the text field will not fit in the half area, lets check if it can fit in the entire available area 
						if(textField.width >= availableTextWidth ){
							//no the text field is lot bigger , lets limit to the available width
							textField.width = availableTextWidth;
							indicatorX = unscaledWidth - paddingRight - INDICATOR_DIM;
							textFldX = indicatorX - gap - textField.width;								
						}
						else{
							textFldX = 0 + paddingLeft + ((availableTextWidth - textField.width)/2);
							indicatorX = textFldX + gap + textField.width;							
						}
					}					
				}
				else if(_alignment == "right"){
					//[<right>text field-indicator</right>]
					indicatorX = unscaledWidth - paddingRight - INDICATOR_DIM;
					//lets check if text field can fit in the entire available area
					if(textField.width > availableTextWidth){
						//no the text field is lot bigger , lets limit to the available width
						textField.width = availableTextWidth;
					}					
					textFldX = indicatorX - gap - textField.width;
				}								
			}
			else{
				if(_textFieldRemoved == false){
					removeChild(DisplayObject(textField));
					_textFieldRemoved = true;					
				}
				//we dont have a text field at all 
				if(_alignment == "left"){
					indicatorX = 0 + paddingLeft;
				}
				else if(_alignment == "right"){
					indicatorX = unscaledWidth - INDICATOR_DIM - paddingRight;
				}
				else{
					indicatorX = (unscaledWidth - INDICATOR_DIM)/2;
				}
				textFldX = -1;
			}
			_indicator.move(indicatorX, indicatorY);
			if(textFldX != -1){
				textField.x = textFldX;
			}
					
		}
	}
}