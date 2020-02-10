package com.tibco.be.views.ui.buttons{
	
	import flash.display.Graphics;
	import flash.events.MouseEvent;
	
	import mx.containers.Canvas;
	import mx.events.FlexEvent;
	import mx.styles.CSSStyleDeclaration;
	import mx.styles.StyleManager;
	
	[Style (name="upArrowColor", type="uint")]
	[Style (name="upArrowWeight", type="Number")]
	[Style (name="overArrowColor", type="uint")]
	[Style (name="overArrowWeight", type="Number")]
	[Style (name="downArrowColor", type="uint")]
	[Style (name="downArrowWeight", type="Number")]

	/**
	 * Why not just skin a normal button? Good question... A skinned button was attempted but left
	 * drawing artifacts in the player. Also, this gives us the ability to define direction, etc.
	*/
	public class ScrollListButton extends Canvas{
		
		public static const UP_DIRECTION:String = "up";
		public static const DOWN_DIRECTION:String = "down";
		
		protected static const ARROW_SIZE:int = 6;
		
		private static function constructStyle():Boolean{
			var style:CSSStyleDeclaration = StyleManager.getStyleDeclaration("ScrollListButton");
			if(style){
				if(style.getStyle("upArrowColor") == undefined){ style.setStyle("upArrowColor", 0x0); }
				if(style.getStyle("upArrowWeight") == undefined){ style.setStyle("upArrowWeight", 1); }
				if(style.getStyle("overArrowColor") == undefined){ style.setStyle("overArrowColor", 0x0); }
				if(style.getStyle("overArrowWeight") == undefined){ style.setStyle("overArrowWeight", 1); }
				if(style.getStyle("downArrowColor") == undefined){ style.setStyle("downArrowColor", 0x0); }
				if(style.getStyle("downArrowWeight") == undefined){ style.setStyle("downArrowWeight", 1); }
			}
			else{
				style = new CSSStyleDeclaration();
				style.defaultFactory = function():void{
					this.upArrowColor = 0x0;
					this.upArrowWeight = 1;
					this.overArrowColor = 0x0;
					this.overArrowWeight = 1;
					this.downArrowColor = 0x0;
					this.downArrowWeight = 1;
				}
				StyleManager.setStyleDeclaration("ScrollListButton", style, true);
			}
			return true;
		}
		private static var classConstructed:Boolean = constructStyle();
		
		protected var _direction:String;
		
		protected var _arrowColor:uint;
		protected var _arrowWeight:Number;
		
		public function ScrollListButton(arrowDirection:String){
			super();
			direction = arrowDirection;
			addEventListener(MouseEvent.ROLL_OVER, handleMouseRollOver);
			addEventListener(MouseEvent.ROLL_OUT, handleMouseRollOut);
			addEventListener(MouseEvent.MOUSE_DOWN, handleMouseDown);
			addEventListener(MouseEvent.MOUSE_UP, handleMouseUp);
			addEventListener(FlexEvent.CREATION_COMPLETE, handleCreationComplete);
		}
		
		public function get direction():String{ return _direction; }
		
		[Inspectable(type=String, enumeration="up","down", defaultValue="up")]
		public function set direction(value:String):void{
			if(value != UP_DIRECTION && value != DOWN_DIRECTION){ _direction = UP_DIRECTION; }
			else{
				_direction = value;
			}
		}
		
		private function handleCreationComplete(event:FlexEvent):void{
			applyStateStyle("up");
		}
		
		private function applyStateStyle(stateName:String):void{
			var colorStyleName:String = stateName + "ArrowColor";
			var weightStyleName:String = stateName + "ArrowWeight";
			
			_arrowColor = getStyle(colorStyleName);
			_arrowWeight = getStyle(weightStyleName);
			invalidateDisplayList();
		}
		
		private function handleMouseRollOver(event:MouseEvent):void{
			applyStateStyle("over");
		}
		
		private function handleMouseRollOut(event:MouseEvent):void{
			applyStateStyle("up");
		}
		
		private function handleMouseDown(event:MouseEvent):void{
			applyStateStyle("down");
		}
		
		private function handleMouseUp(event:MouseEvent):void{
			applyStateStyle("up");
		}
		
		override protected function updateDisplayList(w:Number, h:Number):void {
			var isUpDirection:Boolean = _direction == UP_DIRECTION;
			
			var g:Graphics = graphics;
			g.clear();
			
			//button hit area
			g.beginFill(0xFFFFFF, 0.0);
			g.drawRect(0, 0, w, h);
			g.endFill();
			
			//base line and direction arrow
			var center:Number = h/2;
			g.lineStyle(_arrowWeight, _arrowColor);
			var arrowBot:Number = center + (isUpDirection ? 1:-1)*ARROW_SIZE/2;
			var arrowTop:Number = center + (isUpDirection ? -1:1)*ARROW_SIZE/2;
			g.moveTo(w/2 - ARROW_SIZE/2, arrowBot);
			g.lineTo(w/2, arrowTop);
			g.lineTo(w/2 + ARROW_SIZE/2, arrowBot);
			var lineY:Number = (isUpDirection ? 0:1)*height + (isUpDirection ? 1:-1)*2;
			g.moveTo(0.2*width, lineY);
			g.lineTo(0.8*width, lineY);
		}
				
	}
}