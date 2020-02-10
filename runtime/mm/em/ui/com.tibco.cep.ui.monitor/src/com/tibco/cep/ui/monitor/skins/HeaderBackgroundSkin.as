package com.tibco.cep.ui.monitor.skins
{
	import flash.display.Graphics;
	
	import mx.skins.ProgrammaticSkin;

	public class HeaderBackgroundSkin extends ProgrammaticSkin
	{
		public function HeaderBackgroundSkin(){
			super();
		}
		
		override protected function updateDisplayList(w:Number, h:Number):void{
			var g:Graphics = this.graphics;
			var yPos:int = 4;
			var lineBaseColor:uint = 0x506e7f;
			var lineCount:int = 7;
			g.clear();
			
			//border
			g.beginFill(0xFFFFFF, 1);
			g.drawRect(0, 0, w, h);
			g.endFill();
						
			//bgcolor
			g.beginFill(0x315468, 1);
			g.drawRect(1, 1, w - 2, h - 1);
			g.endFill();
			
			//draw the lines across
			for(var i:int = 0; i<lineCount;i++){
				g.moveTo(1, yPos);
				g.lineStyle(1, lineBaseColor, 1);
				g.lineTo(w - 2, yPos);
				
				yPos++;
				
				g.moveTo(1, yPos);
				g.lineStyle(1, lineBaseColor, 0.6);
				g.lineTo(w - 2, yPos);
				
				yPos+= 3;
			}			
			
			
		}//end of updateDisplayList
		
	}
}