package com.tibco.be.views.user.utils{
	
	public class ChartMinMax{
		
		private var _min:Number;
		private var _max:Number;
		private var _minCategorySum:Number;
		private var _maxCategorySum:Number;
		
		public function ChartMinMax(){
			_min = Number.MAX_VALUE;
			_minCategorySum = Number.MAX_VALUE;
			_max = -Number.MAX_VALUE;
			_maxCategorySum = -Number.MAX_VALUE;
		}
		
		public function get min():Number{ return _min; }
		public function get max():Number{ return _max; }
		public function get minCategorySum():Number{ return _minCategorySum; }
		public function get maxCategorySum():Number{ return _maxCategorySum; }
		
		public function set min(value:Number):void{ _min = value; }
		public function set max(value:Number):void{ _max = value; }
		public function set minCategorySum(value:Number):void{ _minCategorySum = value; }
		public function set maxCategorySum(value:Number):void{ _maxCategorySum = value; }

	}
}