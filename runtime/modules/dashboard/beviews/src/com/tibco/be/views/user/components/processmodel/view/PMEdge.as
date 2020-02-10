package com.tibco.be.views.user.components.processmodel.view{
	
	import com.tibco.be.views.ui.shapes.ArrowHeadShape;
	import com.tibco.be.views.user.components.processmodel.IPMEdgeContainer;
	import com.tibco.be.views.user.components.processmodel.model.PMEdgeConfig;
	
	import mx.core.Container;
	import mx.core.UIComponent;

	public class PMEdge extends UIComponent{
		
		public static const ARROW_TERMINATOR:String = "arrow";
		public static const FILLED_ARROW_TERMINATOR:String = "filled-arrow";
		public static const CIRCLE_TERMINATOR:String = "circle";
		public static const BOX_TERMINATOR:String = "box";
		
		private static const LINE_COLOR:uint = 0x969494;
		private static const TERMINATOR_FILL_COLOR:uint = 0x969494;
		private static const TERMINATOR_FILL_ALPHA:Number = 1;
		
		private var _config:PMEdgeConfig;
		
		/** Offset used to correctly display the view based off of the model's absolute x value */
		private var _viewXAdjustment:int;
		/** Offset used to correctly display the view based off of the model's absolute y value */
		private var _viewYAdjustment:int;

		public function PMEdge(config:PMEdgeConfig, edgeContainer:IPMEdgeContainer){
			super();
			_config = config;
			if(_config != null && edgeContainer != null){
				_viewXAdjustment = -edgeContainer.absoluteX;
				_viewYAdjustment = -edgeContainer.absoluteY;
			}
			else{
				_viewXAdjustment = 0;
				_viewYAdjustment = 0;
			}
		}
		
		public function get startX():int{ return _config.startX + _viewXAdjustment; }
		public function get startY():int{ return _config.startY + _viewYAdjustment; }
		public function get endX():int{ return _config.endX + _viewXAdjustment; }
		public function get endY():int{ return _config.endY + _viewYAdjustment;; }
		public function get labels():Array{ return _config.labels; }
		public function get edgeAlpha():Number{ return _config.edgeAlpha; }
		public function get boxCircleSize():Number{ return _config.boxCircleSize; }
		public function get arrowSize():Number{ return _config.arrowSize; }
		public function get startShape():String{ return _config.startShape; }
		public function get endShape():String{ return _config.endShape; }
		
		public function adjustTo(container:Container, xOffset:Number=0, yOffset:Number=0):void{
			_viewXAdjustment -= (container.x + xOffset);
			_viewYAdjustment -= (container.y + yOffset);
		}
		
		override protected function createChildren():void{
			var line:UIComponent = new UIComponent();
			line.graphics.clear();
			line.graphics.lineStyle(1, LINE_COLOR, edgeAlpha, false, "normal", null, "round");
			line.graphics.moveTo(startX, startY);
			line.graphics.lineTo(endX, endY);
			addChild(line);
			
			addEdgeTerminator(startX, startY, startShape);
			addEdgeTerminator(endX, endY, endShape);
		}
				
		private function addEdgeTerminator(x:int, y:int, shape:String):void{
			switch(shape){
				case(ARROW_TERMINATOR):
					drawArrow(x, y);
					break;
				case(FILLED_ARROW_TERMINATOR):
					drawArrow(x, y, true);
					break;
				case(CIRCLE_TERMINATOR):
					drawCircle(x, y);
					break;
				case(BOX_TERMINATOR):
					drawBox(x, y);
					break;
				default:
					break;
			}
		}
		
		private function drawArrow(x:int, y:int, fill:Boolean=false):void{
			//calculate the roatation to apply to the arrow. Note Flex rotation is done clockwise
			//from 3 o'clock; and remember y grows downward.
			var isStartPoint:Boolean = x == startX && y == startY && (x != endX || y != endY);
			var lineSlope:Number = Math.abs(Number(endY-startY)/Number(endX-startX));
			var rotation:Number = Math.atan(lineSlope);
			if(startX <= endX && startY <= endY){ /*skip other conditionals*/ }			//arrow going down-right
			else if(startX > endX && startY <= endY){ rotation = Math.PI - rotation }	//adjust to down-left
			else if(startX > endX && startY > endY){ rotation += Math.PI;  }			//adjust to up-left
			else if(startX <= endX && startY > endY){ rotation = 2*Math.PI - rotation; }//adjust to up-right
			var arrow:ArrowHeadShape = new ArrowHeadShape(
				x,
				y,
				arrowSize,
				TERMINATOR_FILL_COLOR,
				fill,
				isStartPoint ? ArrowHeadShape.BASELINE_PIVOT:ArrowHeadShape.APEX_PIVOT
			);
			arrow.rotate(rotation);
			addChild(arrow);
		}
		
		private function drawCircle(x:int, y:int):void{
			var circle:UIComponent = new UIComponent();
			circle.x = x;
			circle.y = y;
			circle.graphics.lineStyle();
			circle.graphics.beginFill(TERMINATOR_FILL_COLOR, TERMINATOR_FILL_ALPHA);
			circle.graphics.drawCircle(0, 0, boxCircleSize/2);
			circle.graphics.endFill();
			addChild(circle);
		}
		
		private function drawBox(x:int, y:int):void{
			var box:UIComponent = new UIComponent();
			box.x = x;
			box.y = y;
			box.graphics.lineStyle();
			box.graphics.beginFill(TERMINATOR_FILL_COLOR, TERMINATOR_FILL_ALPHA);
			box.graphics.drawRect(-boxCircleSize/2, -boxCircleSize/2, boxCircleSize, boxCircleSize);
			box.graphics.endFill();
			addChild(box);
		}
		
	}
	
}
