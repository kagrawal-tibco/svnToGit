package com.tibco.cep.ui.monitor.skins
{
	import flash.display.Graphics;
	
	import mx.skins.ProgrammaticSkin;

	public class TopologyMenuTabBtnSkin extends ProgrammaticSkin
	{
		public function TopologyMenuTabBtnSkin(){
			super();
		}
		
		override protected function updateDisplayList(w:Number, h:Number):void{
			var g:Graphics = this.graphics;
			var bgColor:uint;
			var borderColor:uint;
			var curveRad:int = 7;
			var borderSize:int = 2;
			var fillAlpha:Number = 1;
			
			g.clear();
			
			switch(name){
				case "upSkin":
					borderColor = 0xFFFFFF;
					bgColor = 0x6b96a3;
					fillAlpha = 1;
					break;
				case "overSkin":
				case "downSkin":
					borderColor = 0xFFFFFF;
					bgColor = 0x6b96a3;
					fillAlpha = 0.6;
					break;				
			}
			
			//border
			g.beginFill(borderColor, 0.9);
			g.drawRoundRectComplex(0, 0, w, h, curveRad, curveRad, 0, 0);
			g.endFill();
			
			//bg
			g.beginFill(bgColor, fillAlpha);
			g.drawRoundRectComplex(borderSize, borderSize, w - borderSize * 2, h - borderSize * 2, curveRad - 1, curveRad - 1, curveRad - 1, curveRad - 1);
			g.endFill();		
		}
		
	}
}