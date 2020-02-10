package com.tibco.cep.ui.monitor.metricGallery.skins
{
	import flash.display.Graphics;
	
	import mx.skins.ProgrammaticSkin;

	public class RestoreBtnSkin extends ProgrammaticSkin
	{
		public function RestoreBtnSkin(){
			super();
		}
		
		override protected function updateDisplayList(w:Number, h:Number):void{
			var g:Graphics = this.graphics;
			var bgColor:uint = 0xE3EEFB;
			var bgAlpha:Number = 0;
			var curveRad:int = 3;
			
			switch(name){
				case "upSkin":
				case "downSkin":
					bgAlpha = 0;
					break;
				case "overSkin":
					bgAlpha = 1;
					break;
			}
			
			g.clear();
			g.beginFill(bgColor, bgAlpha);
			g.drawRoundRectComplex(0, 0, w, h, curveRad, curveRad, curveRad, curveRad);
			g.endFill();
		}
		
	}
}