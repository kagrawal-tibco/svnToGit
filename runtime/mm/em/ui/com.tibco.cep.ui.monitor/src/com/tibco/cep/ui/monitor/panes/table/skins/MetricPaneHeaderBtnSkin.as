package com.tibco.cep.ui.monitor.panes.table.skins
{
	import flash.display.Graphics;
	
	import mx.skins.ProgrammaticSkin;

	public class MetricPaneHeaderBtnSkin extends ProgrammaticSkin
	{
		public function MetricPaneHeaderBtnSkin(){
			super();
		}
		
		override protected function updateDisplayList(w:Number, h:Number):void{
			var g:Graphics = this.graphics;
			var bgColor:uint = 0xafb8c2;
			var bgAlpha:Number = 0;
			var curveRad:int = 4;
			
			g.clear();
			
			switch(name){
				case "upSkin":	
				case "selectedUpSkin":
				case "disabledSkin":
					bgAlpha = 0;
					break;
				case "overSkin":	
				case "downSkin":
					bgAlpha = 0.6;
					break;
				default:
					alpha = 0;
					break;
			}
			
			//draw a simple background shape
			g.beginFill(bgColor, bgAlpha);
			g.drawRoundRectComplex(0, 0, w, h, curveRad, curveRad, curveRad, curveRad);
			g.endFill();
			
		}
		
	}
}