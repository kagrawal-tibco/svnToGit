package com.tibco.be.views.user.components.chart.renderers.item{
	
	import flash.display.Graphics;
	import flash.geom.Rectangle;
	
	import mx.graphics.IFill;
	import mx.graphics.IStroke;
	
	public class LabeledTriangleItemRenderer extends BEVLabeledItemRenderer{
		
		private static var rcFill:Rectangle = new Rectangle();
		
		public function LabeledTriangleItemRenderer(labelConfig:XML){
			super(labelConfig);
		}
		
		override protected function drawDataPoint(unscaledWidth:Number, unscaledHeight:Number, adjustedRadius:Number, stroke:IStroke, fill:IFill):void{
			var w:Number = stroke ? stroke.weight / 2 : 0;
	
			var cx:Number = unscaledWidth / 2;
	
			rcFill.left = rcFill.left - adjustedRadius;
			rcFill.top = rcFill.top - adjustedRadius
			rcFill.right = unscaledWidth + adjustedRadius;
			rcFill.bottom = unscaledHeight + adjustedRadius;
	
			var g:Graphics = graphics;
			g.clear();		
			g.moveTo(w, w);
			if(stroke){ stroke.apply(g); }
			g.moveTo(w - adjustedRadius, unscaledHeight - w + adjustedRadius);
			if(fill){ fill.begin(g, rcFill); }
			g.lineTo(cx, w - adjustedRadius);
			g.lineTo(unscaledWidth - w + adjustedRadius, unscaledHeight - w + adjustedRadius);
			g.lineTo(w - adjustedRadius, unscaledHeight - w + adjustedRadius);
			if(fill){ fill.end(g); }
		}

	}
}