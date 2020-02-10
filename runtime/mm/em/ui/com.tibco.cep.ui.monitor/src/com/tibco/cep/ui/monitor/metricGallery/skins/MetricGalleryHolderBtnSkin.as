package com.tibco.cep.ui.monitor.metricGallery.skins
{
	import flash.display.GradientType;
	import flash.display.Graphics;
	import flash.geom.Matrix;
	
	import mx.skins.ProgrammaticSkin;

	public class MetricGalleryHolderBtnSkin extends ProgrammaticSkin
	{
		public function MetricGalleryHolderBtnSkin(){
			super();
		}
		
		override protected function updateDisplayList(w:Number, h:Number):void{
			var g:Graphics = this.graphics;
			var bgColors:Array = [0x91a4b5, 0xabbac8];
			var bgAlphas:Array = [1, 1];
			var ratios:Array = [60, 255];
			var borderColor:uint = 0x315468;
			var curveRad:int = 4;
			var m:Matrix = new Matrix();
			var r:Number = 90 * Math.PI/180;
			m.createGradientBox(w, h, r);
			
			g.clear();
			
			switch(name){
				case "upSkin":
					bgColors = [0x91a4b5, 0xabbac8];
					bgAlphas = [1, 1];
					break;
				case "overSkin":
				case "downSkin":
					bgColors = [0x91a4b5, 0xabbac8];
					bgAlphas = [1, 1];
					break;										
			}
			
			g.beginFill(borderColor, 1);
			g.drawRoundRectComplex(0, 0, w, h, 0, 0, curveRad, curveRad);
			g.endFill();			
			
			g.beginGradientFill(GradientType.LINEAR, bgColors, bgAlphas, ratios, m);
			g.drawRoundRectComplex(1, 0, w - 2, h - 1, 0, 0, curveRad, curveRad);
			g.endFill();
		}
		
	}
}