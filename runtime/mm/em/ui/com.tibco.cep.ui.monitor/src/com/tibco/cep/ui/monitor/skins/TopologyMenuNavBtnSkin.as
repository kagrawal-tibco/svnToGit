package com.tibco.cep.ui.monitor.skins
{
	import flash.display.Graphics;
	
	import mx.skins.ProgrammaticSkin;

	public class TopologyMenuNavBtnSkin extends ProgrammaticSkin
	{
		public function TopologyMenuNavBtnSkin()
		{
			super();
		}
		
		override protected function updateDisplayList(w:Number, h:Number):void{
			var g:Graphics = this.graphics;
			
			g.clear();

			//bg
			g.beginFill(0xedf1f3, 0);
			g.drawRect(0, 0, w, h);
			g.endFill();
		}		
		
	}
}