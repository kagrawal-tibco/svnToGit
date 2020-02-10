package com.tibco.cep.ui.monitor.skins
{
	import flash.display.Graphics;
	
	import mx.skins.ProgrammaticSkin;

	public class DBPanelNavTabBtnSkin extends ProgrammaticSkin
	{
		public function DBPanelNavTabBtnSkin(){
			super();
		}
		
		override protected function updateDisplayList(w:Number, h:Number):void{
			var g:Graphics = this.graphics;
			var bgColor:uint;
			var borderColor:uint;
			var borderSize:int = 2;
			var curveRad:int = 6;
			
			g.clear();
			
			switch(name){
				case "upSkin":
					//border
					g.beginFill(0xffffff, 1);
					g.drawRoundRectComplex(0, 0, w, h, curveRad, curveRad, 0, 0);
					g.endFill();
					
					//background
					g.beginFill(0x437381, 1);
					g.drawRoundRectComplex(borderSize, borderSize, w - borderSize * 2, h - borderSize * 2, curveRad - 1, curveRad - 1, 0, 0);
					g.endFill();						
					break;
				case "overSkin":
				case "downSkin":
					//border
					g.beginFill(0xffffff, 1);
					g.drawRoundRectComplex(0, 0, w, h, curveRad, curveRad, 0, 0);
					g.endFill();
					
					//background
					g.beginFill(0x437381, 1);
					g.drawRoundRectComplex(borderSize, borderSize, w - borderSize * 2, h - borderSize * 2, curveRad - 1, curveRad - 1, 0, 0);
					g.endFill();								
					break;
				case "selectedUpSkin":
					//border
					g.beginFill(0xffffff, 1);
					g.drawRoundRectComplex(0, 0, w, h, curveRad, curveRad, 0, 0);
					g.endFill();
					
					//background
					g.beginFill(0xced8e4, 1);
					g.drawRoundRectComplex(borderSize, borderSize, w - borderSize * 2, h - borderSize, curveRad - 1, curveRad - 1, 0, 0);
					g.endFill();	
					break;
				case "selectedOverSkin":
				case "selectedDownSkin":
					//border
					g.beginFill(0xffffff, 1);
					g.drawRoundRectComplex(0, 0, w, h, curveRad, curveRad, 0, 0);
					g.endFill();
					
					//background
					g.beginFill(0xced8e4, 1);
					g.drawRoundRectComplex(borderSize, borderSize, w - borderSize * 2, h - borderSize, curveRad - 1, curveRad - 1, 0, 0);
					g.endFill();								
					break;					
			}
			
		}//end of updateDisplayList
		
	}
}