package com.tibco.be.views.core.ui.controls{
	
	import mx.containers.ControlBar;
	import mx.styles.CSSStyleDeclaration;
	import mx.styles.StyleManager;

	public class TexturedControlBar extends ControlBar {
		
    	private static var defaultStylesInitialized:Boolean = setDefaultStyles();

		private static function setDefaultStyles():Boolean {
			
			if(StyleManager.getStyleDeclaration("TexturedBar") == null) {
				
				var styleDeclaration:CSSStyleDeclaration = new CSSStyleDeclaration();
				styleDeclaration.setStyle("horizontalAlign","right");
				styleDeclaration.setStyle("verticalAlign","middle");
				styleDeclaration.setStyle("horizontalGap",10);
				styleDeclaration.setStyle("paddingRight",10);
				styleDeclaration.setStyle("paddingBottom",10);
				styleDeclaration.setStyle("paddingTop",10);
				styleDeclaration.setStyle("borderColor", "#808285");
				styleDeclaration.setStyle("borderStyle", "solid");			
				styleDeclaration.setStyle("borderThickness",1);
				
				styleDeclaration.setStyle("alternatingColors",[0xE9E9E9,0xFFFFFF]);
								
				StyleManager.setStyleDeclaration("TexturedControlBar",styleDeclaration,true);
			}
        	return true;
        }
		
		
		public function TexturedControlBar() {
			super();
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			var colors:Array = getStyle("alternatingColors");
			if(colors == null || colors.length != 2) {
				return;
			}
			//we draw the background
			graphics.clear();
			var color:uint = colors[0];
			var y:int = 0;
			while(y < unscaledHeight) {
				graphics.beginFill(color);
				graphics.drawRect(0,y,unscaledWidth,2);
				y = y + 2;
				color = (color == colors[0]) ? colors[1]:colors[0];
				graphics.endFill();
			}
		}
		
	}
}