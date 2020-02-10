package com.tibco.be.views.ui.gallery.skins{
	import mx.skins.ProgrammaticSkin;
	
	public class TitleWindowBackgroundSkin extends ProgrammaticSkin{
		
		public function TitleWindowBackgroundSkin(){
			
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			var cornerRadius:Number = getStyle("cornerRadius");
            var backgroundColor:int = getStyle("backgroundColor");
            var backgroundAlpha:Number = getStyle("backgroundAlpha");
            graphics.clear();
            
            // Background

            drawRoundRect
            (
                0, 0, unscaledWidth, unscaledHeight, 
                {tl: cornerRadius, tr: cornerRadius, bl: 0, br: 0}, 
                backgroundColor, backgroundAlpha
            );
		}

	}
}