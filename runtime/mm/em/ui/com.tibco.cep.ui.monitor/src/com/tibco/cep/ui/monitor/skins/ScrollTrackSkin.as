package com.tibco.cep.ui.monitor.skins
{
	import flash.display.Graphics;
	
	import mx.controls.scrollClasses.ScrollBar;
	import mx.skins.Border;

	public class ScrollTrackSkin extends Border
	{
		public function ScrollTrackSkin()
		{
			super();
		}
		
		override protected function updateDisplayList(w:Number, h:Number):void
		{
			super.updateDisplayList(w, h);
	
				var bgColor:uint = 0x60616C;
				var g:Graphics = graphics;
				var r:Number = 90 * Math.PI/180;
				
				g.clear();
				if(ScrollBar(this.parent.parent).maxScrollPosition > 0){
					g.beginFill(bgColor, 1);
					g.drawRect(0, 8, w, h - 16);
					g.endFill();
				}else{				
					g.beginFill(bgColor, 1);
					g.drawRect(0, 16, w, h - 32);
					g.endFill();
					
				}
		}//end of updateDisplayList
		
		
	    override public function get measuredWidth():Number{
	        return 16;
	    }
	    
	    override public function get measuredHeight():Number{
	        return 1;
	    }			
		
	}
}