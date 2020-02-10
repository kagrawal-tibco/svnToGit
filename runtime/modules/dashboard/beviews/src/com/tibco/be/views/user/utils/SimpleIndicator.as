package com.tibco.be.views.user.utils {
	
	import mx.core.UIComponent;

	public class SimpleIndicator extends UIComponent {
		
		public static const BLANK:String = "-2";
		
		public static const NONE:String = "-1";
		
		private var _color:uint;
		
		private var _showBlank:Boolean;
		
		private var _radiusPercentage:Number;
		
		public function SimpleIndicator() {
			super();
			_showBlank = true;
			_radiusPercentage = 30.00;
		}
		
		public function get color():String{ 
			return String(_color); 
		}
		
		public function set color(color:String):void{
			if (color == BLANK) {
				_showBlank = true;
			}
			else if (color == NONE) {
				_showBlank = false;
				_color = 0xAAAAAA;
			}
			else {
				_showBlank = false;
				_color = uint(color); 
			}
			invalidateDisplayList(); 
		}
		
		public function get radiusPercentage():Number {
			return _radiusPercentage;
		}
		
		public function set radiusPercentage(ratio:Number):void {
			if (ratio < 1 || ratio > 100) {
				throw new Error("Radius should be between 0 and 100");
			}
			_radiusPercentage = ratio;
			invalidateDisplayList();
			
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
			super.updateDisplayList(unscaledWidth,unscaledHeight);
			graphics.clear();
			if (_showBlank == true) {
				//do nothing;
				return;
			}
			//compute the radius 
			var shorterDim:Number = Math.min(unscaledWidth,unscaledHeight);
			var radius:Number = shorterDim * _radiusPercentage/100;
			var centerX:Number = unscaledWidth/2;
			var centerY:Number = unscaledHeight/2;
			//draw the circle
			graphics.beginFill(_color);
			graphics.drawCircle(centerX,centerY,radius);
			graphics.endFill();
		}	
	}
}