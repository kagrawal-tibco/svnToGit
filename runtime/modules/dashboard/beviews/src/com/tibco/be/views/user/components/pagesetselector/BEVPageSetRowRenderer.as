package com.tibco.be.views.user.components.pagesetselector{
	
	import com.tibco.be.views.user.components.table.renderers.IndicatorItemRenderer;
	import com.tibco.be.views.user.components.table.renderers.TextItemRenderer;
	
	import mx.containers.Canvas;
	import mx.controls.Label;
	import mx.core.ScrollPolicy;

	public class BEVPageSetRowRenderer extends Canvas{
		
		private var _columnConfigs:Array;
		private var _leadRenderer:Label;
		private var _textRenderer:TextItemRenderer;
		private var _indicatorRenderer:IndicatorItemRenderer;
		private var _textRendererWidthPercentage:Number;
		private var _indicatorRendererWidthPercentage:Number;
		
		//private var _data:Object;
		
		public function BEVPageSetRowRenderer(){
			_leadRenderer = new Label();
			_textRenderer = new TextItemRenderer();
			_indicatorRenderer = new IndicatorItemRenderer();
			_textRendererWidthPercentage = 0;
			_indicatorRendererWidthPercentage = 0;
			addChild(_leadRenderer);
			addChild(_textRenderer);
			addChild(_indicatorRenderer);
			//setStyle("backgroundColor","#FFFFFF");
			setStyle("borderStyle","solid");
			setStyle("borderSides","bottom");
			setStyle("borderColor","#A9A9A9");
			setStyle("paddingLeft","5");
			setStyle("paddingRight","5");
			horizontalScrollPolicy = ScrollPolicy.OFF;
			verticalScrollPolicy = ScrollPolicy.OFF;
			buttonMode = true;
			useHandCursor = true;
			mouseChildren = false;			
		}
		
		public function set columnConfigs(columnConfigs:Array):void{
			this._columnConfigs = columnConfigs;
			for each(var columnConfig:XML in _columnConfigs){
				if(columnConfig != null){
					var type:String = new String(columnConfig.@type);
					var renderer:TextItemRenderer = _textRenderer;
					if(type == "indicator"){
						renderer = _indicatorRenderer;
						_indicatorRendererWidthPercentage = parseFloat(columnConfig.@width);
					}
					else{
						_textRendererWidthPercentage = parseFloat(columnConfig.@width);
					}
//					renderer.columnConfig = columnConfig;
//					renderer.headerConfig = new XML();
//					renderer.defaultColor = "000000";
//					renderer.defaultFontStyle = "normal";
				}
			}			
		}
		
		override public function set data(value:Object):void {
			if(value == null){
				return;
			}
			super.data = value;
			_leadRenderer.text = value.title;
			toolTip = value.tooltip;
			if(_columnConfigs != null){
				for each(var columnConfig:XML in _columnConfigs){
					if(columnConfig != null){
						var renderer:TextItemRenderer = _textRenderer;
						if(String(columnConfig.@type) == "indicator"){
							_indicatorRenderer.data = value;
						}
						else{
							_textRenderer.data = value;
						}
					}						
				}
			}
//			if(data.last == true){
//				setStyle("borderSides","top left right bottom");	
//			}
//			else{
//				setStyle("borderSides","top left right");
//			}			
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			var paddingRight:Number = getStyle("paddingRight");
			var paddingLeft:Number = getStyle("paddingLeft");
			var gap:Number = 2;//getStyle("horizontalGap");
			  
			var remainingWidth:int = unscaledWidth;
			
			//draw the indicator first
			_indicatorRenderer.width = remainingWidth * _indicatorRendererWidthPercentage/100;
			_indicatorRenderer.height = unscaledHeight;
			_indicatorRenderer.x = unscaledWidth - paddingRight - _indicatorRenderer.width;
			_indicatorRenderer.y = 0;
			remainingWidth = remainingWidth - _indicatorRenderer.width - gap;
			
			//draw the text next
			_textRenderer.width = remainingWidth * _textRendererWidthPercentage/100;
			_textRenderer.height = unscaledHeight; 
			_textRenderer.x = _indicatorRenderer.x - gap - _textRenderer.width;
			_textRenderer.y = 0;
			remainingWidth = remainingWidth - _textRenderer.width - gap;
			
			//draw the label
			_leadRenderer.width = remainingWidth;
			_leadRenderer.height = unscaledHeight; 
			_leadRenderer.x = paddingLeft;
			_leadRenderer.y = 0;
		}
		
	}
}