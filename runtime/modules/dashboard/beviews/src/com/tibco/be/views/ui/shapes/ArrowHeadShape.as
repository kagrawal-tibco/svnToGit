package com.tibco.be.views.ui.shapes{
	
	import flash.geom.Matrix;
	
	import mx.core.UIComponent;

	public class ArrowHeadShape extends UIComponent{
		
		public static const BASELINE_PIVOT:String = "baselinePivot";
		public static const APEX_PIVOT:String = "apexPivot";
		public static const CENTER_PIVOT:String = "centerPivot";
		
		private var _fillArrow:Boolean;
		private var _pivotPoint:String;
		private var _color:uint;
		private var _currentRotation:Number;
		
		/**
		 * A rotateable arrow shape. The arrow is drawn so that the apex is pointing towards 3
		 * o'clock (aka 0*pi or 0 degrees). Rotation is then applied as a radian value in the
		 * clockwise direction.
		 * 
		 * @param x The x coordinate of the arrow's apex.
		 * @param y The y coordinate of the arrow's apex.
		*/
		public function ArrowHeadShape(x:int=0, y:int=0, size:int=8, color:uint=0x0, fill:Boolean=true, pivotPosition:String=""){
			super();
			this.x = x;
			this.y = y;
			this.width = size;
			this.height = size;
			_color = color;
			_fillArrow = fill;
			_currentRotation = 0;
			if(pivotPosition != BASELINE_PIVOT && pivotPosition != APEX_PIVOT && pivotPosition != CENTER_PIVOT){
				_pivotPoint = APEX_PIVOT;
			}
			else{
				_pivotPoint = pivotPosition;
			}
			draw();
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			draw();
		}
		
		public function set size(value:int):void{ width = height = value; }
		public function set color(value:uint):void{ _color = value; invalidateDisplayList(); }
		public function set fillArrow(value:Boolean):void{ _fillArrow = value; invalidateDisplayList(); }
		public function set piviotPoint(value:String):void{
			if(value != BASELINE_PIVOT && value != APEX_PIVOT && value != CENTER_PIVOT){
				_pivotPoint = APEX_PIVOT;
			}
			else{
				_pivotPoint = value;
			}
		}
				
		private function draw():void{
			//							|\
			//							| \
			//		BASELINE_PIVOT		*--* 	APEX_PIVOT
			//							| /
			//							|/
			//
			//							|\
			//							| \
			//		CENTER_PIVOT		|*- 
			//							| /
			//							|/
			// * = end point
			//
			var endX:int = -width; //default APEX_PIVOT
			switch(_pivotPoint){
				case(CENTER_PIVOT): endX = -width/2; break;
				case(BASELINE_PIVOT): endX = 0; break;
			}
			graphics.clear();
			graphics.moveTo(endX, -height/2);
			if(_fillArrow){ graphics.beginFill(_color, 1); }
			else{ graphics.lineStyle(1, _color, 1, false, "normal", null, "round"); }
			graphics.lineTo(endX+width, 0);
			graphics.lineTo(endX, height/2);
			if(_fillArrow){
				graphics.endFill();
			}
		}
		
		/**
		 * Rotates the arrow in the _clockwise_ direction the number of radians specified.
		 * @param radians The number of radians to rotate the arrow clockwise.
		*/
		public function rotate(radians:Number, resetFirst:Boolean=false):void{
			if(radians < -2*Math.PI || radians > 2*Math.PI){
				trace("WARNING: ArrowHeadShape.rotate - Invalid radian specification.");
				return;
			}
			if(resetFirst){ radians -= _currentRotation; }
			_currentRotation += radians;
			var m:Matrix = transform.matrix;
			m.translate(-x, -y);
			m.rotate(radians);
			m.translate(x, y);
			transform.matrix = m;
		}
		
	}
}