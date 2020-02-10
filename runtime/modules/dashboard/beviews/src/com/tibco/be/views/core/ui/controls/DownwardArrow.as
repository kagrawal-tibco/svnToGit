package com.tibco.be.views.core.ui.controls{
	
	import flash.display.Shape;
		
	internal class DownwardArrow extends Shape{
		
		private static const ARROW_WIDTH:Number = 6; 
		private static const ARROW_HEIGHT:Number = 5;		
		private static const TOP_PADDING:int = 3;
		private static const BOTTOM_PADDING:int = 2;
		private static const RIGHT_PADDING:int = 2;
		private static const LEFT_PADDING:int = 2;
		
		private var _width:Number;
		private var _height:Number;
		
		internal var color:uint;
		
		function DownwardArrow():void{
			_width = RIGHT_PADDING + ARROW_WIDTH + LEFT_PADDING;
			_height = TOP_PADDING + ARROW_HEIGHT + BOTTOM_PADDING;
		}
		
		override public function get width():Number{ return _width; } 
		override public function get height():Number{ return _height; }
		
		internal function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			graphics.beginFill(color);
			//we draw the arrow
			//we move to left vertice 
			var drawingX:int = LEFT_PADDING;
			var drawingY:int = TOP_PADDING;
		 	graphics.moveTo(drawingX, drawingY);
		 	//we draw a line from left vertice to right vertice 
		 	drawingX = drawingX + ARROW_WIDTH;  
		 	graphics.lineTo(drawingX, drawingY); 
		 	//we draw a line from right vertice to bottom vertice
		 	drawingX = drawingX - ARROW_WIDTH/2;
		 	drawingY = drawingY + ARROW_HEIGHT;
		 	graphics.lineTo(drawingX, drawingY);
		 	//we draw a line from bottom vertice to left vertice
		 	drawingX = LEFT_PADDING;
		 	drawingY = TOP_PADDING; 
		 	graphics.lineTo(drawingX,drawingY);
		 	graphics.endFill();
		}
		
	}	

}