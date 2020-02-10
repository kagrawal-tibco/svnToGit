package com.tibco.be.views.ui.skin{
	
	import flash.display.GradientType;
	import flash.geom.Matrix;
	
	import mx.skins.RectangularBorder;

	public class CommonPanelHeaderSkin extends RectangularBorder{
		
		public function CommonPanelHeaderSkin(){
			super();
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			graphics.clear();
				
			//draw fill
			var m:Matrix = new Matrix();
			m.createGradientBox(unscaledWidth, unscaledHeight, Math.PI/2);
			graphics.beginGradientFill(
				GradientType.LINEAR,
				[0xECECEC, 0xD3D3D3],
				[1, 1],
				[0x00, 0xFF],
				m
			);
			graphics.drawRect(0, 0, unscaledWidth, unscaledHeight);
			graphics.endFill();
			
			//draw grey border (left, top, right)
//			graphics.moveTo(0, unscaledHeight);
//			graphics.lineStyle(1, 0xD3D3D3);
//			graphics.lineTo(0, 0);
//			graphics.lineTo(unscaledWidth, 0);
//			graphics.lineTo(unscaledWidth, unscaledHeight);
			
			//draw white secondary border (top)
			graphics.moveTo(1, 1);
			graphics.lineStyle(1, 0xFFFFFF);
			graphics.lineTo(unscaledWidth, 1);
		}
		
	}
}