package com.tibco.be.views.user.utils{
	
	import flash.display.GradientType;
	import flash.geom.Matrix;
	
	import mx.core.UIComponent;
	import mx.styles.CSSStyleDeclaration;
	import mx.styles.StyleManager;
	
	[Style(name="borderColors",type="Array",arrayType="uint",format="Color")]
	
	public class Indicator extends UIComponent{
		
		public static const NONE:String = "None";
		public static const OFF:String = "Off";
		public static const GREEN:String = "Green";
		public static const YELLLOW:String = "Yellow";
		public static const RED:String = "Red";
		public static const USER_DEFINED:String = "UserDefined";  
		
    	private static var defaultStylesInitialized:Boolean = setDefaultStyles();
    	
    	[Inspectable(category="Other",defaultValue="Off",enumeration="None,Off,Green,Yellow,Red,UserDefined",name="state")] 
		private var _state:String;
    	private var _color:uint;

		private static function setDefaultStyles():Boolean{
			if(StyleManager.getStyleDeclaration("Indicator") == null){
				var styleDeclaration:CSSStyleDeclaration = new CSSStyleDeclaration();
				styleDeclaration.setStyle("borderColors",["0xC9D2D6", "0x0D1B30"]);
				StyleManager.setStyleDeclaration("Indicator",styleDeclaration,true);
			}
        	return true;
        }
		
		public function Indicator():void{
			state = NONE;
		}
		
		public function get color():uint{ return _color; }
		public function get state():String{ return this._state; }
		
		public function set color(color:uint):void{
			_color = color;
			_state = USER_DEFINED;
			invalidateProperties();
		}
		public function set state(state:String):void{
			_state = state;
			switch(_state){
				case(NONE):	
				case(OFF):
					_color = 0xAAAAAA;
					break; 	
				case(GREEN):
					_color = 0x0D8A35;
					break;
				case(YELLLOW):
				 	_color = 0xFED21F;
					break;
				case(RED):
					_color = 0xE03408;
					break;
				default:
					break;
			}
			invalidateProperties();
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			super.updateDisplayList(unscaledWidth,unscaledHeight);
			if(_state == NONE){ return; }
			var centerX:int = unscaledWidth/2;
			var centerY:int = unscaledHeight/2;
			var radius:int = Math.min(unscaledWidth,unscaledHeight)/2;
			var gradientColor:uint = getGradientColor();
		 	var bgMatrix:Matrix = new Matrix();
		 	bgMatrix.createGradientBox(radius*2,radius*2);
			graphics.beginGradientFill(GradientType.RADIAL,getStyle("borderColors"),[1.0,1.0],[0,255],bgMatrix);
			graphics.drawCircle(centerX,centerY,radius);
			graphics.endFill();
			radius = radius - 1;
			//graphics.lineStyle(0,0xFFFFFF);
			//graphics.drawCircle(centerX,centerY,radius);
			//radius = radius - 1;
			graphics.lineStyle(0,0x000000);
			bgMatrix.createGradientBox(radius*2,radius*2);
			graphics.beginGradientFill(GradientType.RADIAL,[gradientColor, _color],[1.0,1.0],[0,255],bgMatrix);
			graphics.drawCircle(centerX,centerY,radius);
			graphics.endFill();
		}
		
		//@return The color of this indicator lightened
		private function getGradientColor():uint{
			var r:uint = _color & 0xFF0000;
			var g:uint = _color & 0x00FF00;
			var b:uint = _color & 0x0000FF;
			
			r = r < 0x900000 ? r+0x700000:0xFF0000;
			g = g < 0x9000 ? g+0x7000:0xFF00;
			b = b < 0x90 ? b+0x70:0xFF;
			
			return (r+g+b);
		}
		
	}
}