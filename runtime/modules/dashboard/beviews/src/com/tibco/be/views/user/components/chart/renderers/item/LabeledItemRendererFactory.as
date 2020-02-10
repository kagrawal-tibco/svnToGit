package com.tibco.be.views.user.components.chart.renderers.item{
	
	import mx.core.ClassFactory;

	public class LabeledItemRendererFactory extends ClassFactory{
		
		public static const BOX:String = "square";
		public static const CIRCLE:String = "circle";
		public static const CROSS:String = "cross";
		public static const DIAMOND:String = "diamond";
		public static const TRIANGLE:String = "triangle";
		public static const RANGE:String = "range";
		
		private var _rendererType:String;
		private var _labelConfig:XML;
		
		/**
		 * 
		 * @param rendererType String indicating the type of Labeled Item Renderer to Create. The
		 * possible values are: "square", "circle", "cross", "diamond", and "triangle"
		*/
		public function LabeledItemRendererFactory(labelConfig:XML, rendererType:String){
			_labelConfig = labelConfig;
			_rendererType = rendererType;
			var rendererClass:Class;
			switch(rendererType){
				case(BOX): rendererClass = LabeledBoxItemRenderer; break;
				case(CROSS): rendererClass = LabeledCrossItemRenderer; break;
				case(DIAMOND): rendererClass = LabeledDiamondItemRenderer; break;
				case(TRIANGLE): rendererClass = LabeledTriangleItemRenderer; break;
				case(RANGE): rendererClass = LabeledHLOCItemRenderer; break;
				default:
					_rendererType = CIRCLE;
					rendererClass = LabeledCircleItemRenderer;
					break;
			}
			//super(rendererClass);
		}
		
		override public function newInstance():*{
			switch(_rendererType){
				case(BOX):
					return new LabeledBoxItemRenderer(_labelConfig);
				case(CIRCLE):
					return new LabeledCircleItemRenderer(_labelConfig);
				case(CROSS):
					return new LabeledCrossItemRenderer(_labelConfig);
				case(DIAMOND):
					return new LabeledDiamondItemRenderer(_labelConfig);
				case(RANGE):
					return new LabeledHLOCItemRenderer(_labelConfig);
				case(TRIANGLE):
					return new LabeledTriangleItemRenderer(_labelConfig);
			}
			return null;
		}
		
	}
}