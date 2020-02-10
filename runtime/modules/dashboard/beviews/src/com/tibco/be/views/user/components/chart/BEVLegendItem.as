package com.tibco.be.views.user.components.chart{
	
	import mx.charts.LegendItem;
	import mx.core.UITextField;

	public class BEVLegendItem extends LegendItem{
		
		public static const MARKER_SIZE:Number = 13;
		
		private var _toolTip:String;
		
		public function BEVLegendItem(){
			super();
		}
		
		public function get textWidth():Number{
			for(var i:int = 0; i < numChildren; i++){
				var text:UITextField = getChildAt(i) as UITextField;
				if(text != null){ return text.textWidth; }
			}
			return 0;
		}
		
		override public function set label(value:String):void{
			super.label = value;
			_toolTip = value;
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			for(var i:int = 0; i < numChildren; i++){
				var text:UITextField = getChildAt(i) as UITextField;
				if(text != null && text.textWidth > width - MARKER_SIZE){
					text.truncateToFit();
					text.toolTip = _toolTip;
				}
			}
			
			/* draw bounds for legend item
			graphics.lineStyle(1, 0);
			graphics.moveTo(0, 0);
			graphics.lineTo(0, unscaledHeight)
			graphics.moveTo(unscaledWidth, 0);
			graphics.lineTo(unscaledWidth, unscaledHeight);
			//*/
		}
		
	}
}