package com.tibco.cep.ui.monitor.panes.table.skins
{
	import flash.display.Graphics;
	
	import mx.skins.ProgrammaticSkin;
	
	public class DgSkin extends ProgrammaticSkin
	{
		public function DgSkin()
		{
			super();
		}
	
	    protected override function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void
	    {
		    //var g:Graphics = new Graphics();
			//var headerColor:uint = getStyle("headerColors");
		    graphics.clear();

			//black
			graphics.beginFill(0x000000);
		    graphics.drawRoundRectComplex(0,0,unscaledWidth,unscaledHeight,6,6,0,0);
		    graphics.endFill();
		    
		    //white
		    graphics.beginFill(0xFFFFFF);
		    graphics.drawRoundRectComplex(0,0,unscaledWidth,unscaledHeight - 1,6,6,0,0);
		    graphics.endFill();		    
		    
		    graphics.beginFill(0x778ea1);
		    graphics.drawRoundRectComplex(1,1,unscaledWidth - 2,unscaledHeight - 2,6,6,0,0);
		    graphics.endFill();
		}

	}
}