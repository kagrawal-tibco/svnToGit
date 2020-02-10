package com.tibco.cep.ui.monitor.skins
{
	import flash.display.Graphics;
	
	import mx.skins.ProgrammaticSkin;

	public class ScrollThumbSkin extends ProgrammaticSkin
	{
		public function ScrollThumbSkin()
		{
			super();
		}
		
		/**
		 * Override the updateDisplay list and paint the new UI to the hScrollBar while 
		 * discarding the existing one
		 * @protected
		 * @param unscaled with
		 * @param unscaled height
		 */
		override protected function updateDisplayList(w:Number, h:Number):void{
			var g:Graphics = graphics;
			w = 12;
			var curveRad:Number = w/2;
			var borderColor:uint = 0xFFFFFF;
			var bgColor:uint = 0xDAE2D8;
			var borderSize:int = 1;
			
			g.clear();
			
			//border
			g.beginFill(borderColor, 1);
			g.drawRoundRectComplex(-w/ 2, 0, w, h, curveRad, curveRad, curveRad, curveRad);
			g.endFill();
			
			//background
			g.beginFill(bgColor, 1);
			g.drawRoundRectComplex(-w/ 2 + borderSize, borderSize, w - borderSize * 2, h - borderSize * 2, curveRad - 1, curveRad - 1, curveRad - 1, curveRad - 1);
			g.endFill();			
		}//end of updateDisplayList
		
	}
}