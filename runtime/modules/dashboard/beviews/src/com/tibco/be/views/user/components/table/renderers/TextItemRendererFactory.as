package com.tibco.be.views.user.components.table.renderers{
	
	import mx.core.ClassFactory;

	public class TextItemRendererFactory extends ClassFactory{
		
		public static const DEFAULT_TYPE:String = "defualt";
		public static const INDICATOR_TYPE:String = "indicator";
		
		private var _rendererType:String;
		private var _headerConfig:XML;
		private var _columnConfig:XML;
		
		public function TextItemRendererFactory(rendererType:String, headerConfig:XML, columnConfig:XML){
			_rendererType = rendererType;
			_headerConfig = headerConfig;
			_columnConfig = columnConfig;
			var rendererClass:Class;
			switch(rendererType){
				case(DEFAULT_TYPE): rendererClass = TextItemRenderer; break;
				case(INDICATOR_TYPE): rendererClass = IndicatorItemRenderer; break;
			}
			super(rendererClass);
		}
		
		override public function newInstance():*{
			switch(_rendererType){	
				case(INDICATOR_TYPE):
					var indicatorRenderer:IndicatorItemRenderer = new IndicatorItemRenderer();
					indicatorRenderer.configure(_headerConfig, _columnConfig);
					return indicatorRenderer;
				default:
					var txtRenderer:TextItemRenderer = new TextItemRenderer();
					txtRenderer.configure(_headerConfig, _columnConfig);
					return txtRenderer;
			}
			return null;
		}
		
	}
}