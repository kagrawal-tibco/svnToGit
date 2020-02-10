package com.tibco.be.views.ui.shapes{
	
	import mx.containers.VBox;
	
	public class BoxedArrow extends VBox{
		
		private var _arrow:ArrowHeadShape;
		private var _showBox:Boolean;
		
		public function BoxedArrow(){
			super();
			_arrow = new ArrowHeadShape();
			_showBox = true;
			setStyle("borderStyle", "solid");
			setStyle("borderColor", "black");
			setStyle("borderThickness", "1");
		}
		
		public function set color(value:uint):void{ _arrow.color = value; }
		public function set fillArrow(value:Boolean):void{ _arrow.fillArrow = value; }
		public function set arrowRotation(value:Number):void{ _arrow.rotate(value, true); }
		public function set showBox(value:Boolean):void{
			if(value != _showBox){
				_showBox = value;
				if(_showBox){ setStyle("borderStyle", "solid"); }
				else{
					setStyle("borderStyle", "none");
				}
				invalidateDisplayList();
			}
		}
		
		override protected function createChildren():void{
			_arrow.piviotPoint = ArrowHeadShape.CENTER_PIVOT;
			addChild(_arrow);
			super.createChildren();
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			super.updateDisplayList(unscaledWidth, unscaledHeight); 
			_arrow.size = Math.min(unscaledWidth, unscaledHeight);
			_arrow.x = unscaledWidth/2;
			_arrow.y = unscaledHeight/2;
		}

	}
}