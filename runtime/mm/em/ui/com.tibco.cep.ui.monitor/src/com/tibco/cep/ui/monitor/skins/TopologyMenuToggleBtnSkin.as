package com.tibco.cep.ui.monitor.skins
{
	import flash.display.Graphics;
	
	import mx.skins.ProgrammaticSkin;

	public class TopologyMenuToggleBtnSkin extends ProgrammaticSkin
	{
		public function TopologyMenuToggleBtnSkin(){
			super();
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			var g:Graphics = this.graphics;
			
			g.clear();
		}
		
	}
}