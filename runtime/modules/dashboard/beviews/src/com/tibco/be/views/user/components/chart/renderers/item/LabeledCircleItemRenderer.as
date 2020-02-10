package com.tibco.be.views.user.components.chart.renderers.item{
	
	import flash.display.Graphics;
	import flash.geom.Rectangle;
	
	import mx.graphics.IFill;
	import mx.graphics.IStroke;
	
	public class LabeledCircleItemRenderer extends BEVLabeledItemRenderer{
		
		private static var rcFill:Rectangle = new Rectangle();
		
		public function LabeledCircleItemRenderer(labelConfig:XML){
			super(labelConfig);
		}
		
		override protected function drawDataPoint(unscaledWidth:Number, unscaledHeight:Number, adjustedRadius:Number, stroke:IStroke, fill:IFill):void{
			var w:Number = stroke ? stroke.weight / 2 : 0;
	
			rcFill.right = unscaledWidth;
			rcFill.bottom = unscaledHeight;
	
			var g:Graphics = graphics;
			g.clear();		
			if(stroke){ stroke.apply(g); }
			if(fill){ fill.begin(g, rcFill); }
			g.drawEllipse(
				(w - adjustedRadius),
				(w - adjustedRadius),
				(unscaledWidth - 2 * w + adjustedRadius * 2),
				(unscaledHeight - 2 * w + adjustedRadius * 2)
			);
			if(fill){ fill.end(g); }
		}

	}
}