package com.tibco.cep.ui.monitor.skins
{
	import flash.display.Graphics;
	
	import mx.skins.ProgrammaticSkin;

	public class LoginControlBarSkin extends ProgrammaticSkin
	{
		public function LoginControlBarSkin(){
			super();
		}
		
		override protected function updateDisplayList(w:Number, h:Number):void{
			var g:Graphics = this.graphics;
			
			g.clear();
			
			//border
			g.beginFill(0xFFFFFF, 1);
			g.drawRect(0, 0, w, h);
			g.endFill();
						
			//bgcolor
			g.beginFill(0x315468, 1);
			g.drawRect(1, 0, w - 2, h - 1);
			g.endFill();
		}//end of updateDisplayList
		
	}
}