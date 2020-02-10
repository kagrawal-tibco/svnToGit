package com.tibco.be.views.user.components.chart.renderers.item{

	import flash.display.Graphics;
	import flash.geom.Rectangle;
	
	import mx.graphics.IFill;
	import mx.graphics.IStroke;	

	public class LabeledBoxItemRenderer extends BEVLabeledItemRenderer{

		public function LabeledBoxItemRenderer(labelConfig:XML){
			super(labelConfig);
		}
		
		override protected function drawDataPoint(unscaledWidth:Number, unscaledHeight:Number, adjustedRadius:Number, stroke:IStroke, fill:IFill):void{
			var w:Number = stroke ? stroke.weight / 2 : 0;
			var rc:Rectangle = new Rectangle(
				(w - adjustedRadius),
				(w - adjustedRadius),
				(width - 2 * w + adjustedRadius * 2),
				(height - 2 * w + adjustedRadius * 2)
			);
			var g:Graphics = graphics;
			g.clear();		
			g.moveTo(rc.left,rc.top);
			if(stroke){ stroke.apply(g); }
			if(fill){ fill.begin(g,rc); }
			g.lineTo(rc.right,rc.top);
			g.lineTo(rc.right,rc.bottom);
			g.lineTo(rc.left,rc.bottom);
			g.lineTo(rc.left,rc.top);
			if(fill){ fill.end(g); }
		}
		
	}
}