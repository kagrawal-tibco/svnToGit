package com.tibco.be.views.ui.shapes{
	
	import mx.containers.Canvas;
	import mx.controls.Image;

	public class LEDIndicator extends Canvas{
		
		public static const SIZE:int = 9;
		
		private var _overlayImage:Image;
		private var _color:uint;
		
		[Embed(source="assets/images/overlays/grey_led_9x9.gif")]
		private var OverlayLED:Class;
		
		public function LEDIndicator(){
			super();
			width = SIZE;
			height = SIZE;
			_color = 0xAAAAAA;
			_overlayImage = new Image();
			_overlayImage.x = _overlayImage.y = 0;
			_overlayImage.width = _overlayImage.height = SIZE;
			_overlayImage.alpha = 0.6;
			_overlayImage.source = OverlayLED;
			this.addChild(_overlayImage);
		}
		
		public function set color(value:uint):void{ _color = value; }
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			graphics.clear();
			graphics.beginFill(_color, .7);
			graphics.drawCircle(SIZE/2, SIZE/2, 4);
			graphics.endFill();
		}
	}
}