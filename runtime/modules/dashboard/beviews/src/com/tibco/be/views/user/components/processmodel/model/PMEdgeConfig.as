package com.tibco.be.views.user.components.processmodel.model{
	
	public class PMEdgeConfig{
		
		private var _edgeAlpha:Number;
		private var _boxCircleSize:int;
		private var _arrowSize:int;
		
		private var _startX:int;
		private var _startY:int;
		private var _endX:int;
		private var _endY:int;
		private var _labels:Array;
		private var _startShape:String;
		private var _endShape:String;
		
		public function PMEdgeConfig(x1:int=0, y1:int=0, x2:int=0, y2:int=0, labels:Array=null, startShape:String="", endShape:String=""){
			_startX = x1;
			_startY = y1;
			_endX = x2;
			_endY = y2;
			_startShape = startShape;
			_endShape = endShape;
			_edgeAlpha = 0.7;
			_boxCircleSize = 7;
			_arrowSize = 6;
		}
		
		public function get edgeAlpha():Number{ return _edgeAlpha; }
		public function get boxCircleSize():Number{ return _boxCircleSize; }
		public function get arrowSize():Number{ return _arrowSize; }
		
		public function get startX():int{ return _startX; }
		public function get startY():int{ return _startY; }
		public function get endX():int{ return _endX; }
		public function get endY():int{ return _endY; }
		
		public function get labels():Array{ return _labels; }
		public function get startShape():String{ return _startShape; }
		public function get endShape():String{ return _endShape; }

	}
}