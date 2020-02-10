package com.tibco.be.views.user.components.chart.renderers.item{
	
	import flash.display.Graphics;
	import flash.geom.Rectangle;
	
	import mx.graphics.IFill;
	import mx.graphics.IStroke;
	
	public class LabeledCrossItemRenderer extends BEVLabeledItemRenderer{
		
		private static var rcFill:Rectangle = new Rectangle();
		
		public var thickness:Number = 2;
		
		public function LabeledCrossItemRenderer(labelConfig:XML){
			super(labelConfig);
		}
		
		override protected function drawDataPoint(unscaledWidth:Number, unscaledHeight:Number, adjustedRadius:Number, stroke:IStroke, fill:IFill):void{
			var w:Number = stroke ? stroke.weight / 2 : 0;
			var w2:Number = 2 * w;
	
			var t2:Number = thickness / 2 + adjustedRadius / 2;
	
			var cx:Number = unscaledWidth / 2;
			var cy:Number = unscaledHeight / 2;
	
			rcFill.left = rcFill.left - adjustedRadius;
			rcFill.top = rcFill.top - adjustedRadius;
			rcFill.right = unscaledWidth;
			rcFill.bottom = unscaledHeight;
	
			var g:Graphics = graphics;
			g.clear();		
			g.moveTo(w, w);
			if(stroke){ stroke.apply(g); }
			g.moveTo(cx - t2, w - adjustedRadius);
			if(fill){ fill.begin(g, rcFill); }
			g.lineTo(cx + t2, w - adjustedRadius);
			g.lineTo(cx + t2, cy - t2);
			g.lineTo(unscaledWidth - w + adjustedRadius, cy - t2);
			g.lineTo(unscaledWidth - w + adjustedRadius, cy + t2);
			g.lineTo(cx + t2, cy + t2);
			g.lineTo(cx + t2, unscaledHeight - w + adjustedRadius);
			g.lineTo(cx - t2, unscaledHeight - w + adjustedRadius);
			g.lineTo(cx - t2, cy + t2);
			g.lineTo(w - adjustedRadius, cy + t2);
			g.lineTo(w - adjustedRadius, cy - t2);
			g.lineTo(cx - t2, cy - t2);
			g.lineTo(cx - t2, w - adjustedRadius);
			if(fill){ fill.end(g); }
		}

	}
}