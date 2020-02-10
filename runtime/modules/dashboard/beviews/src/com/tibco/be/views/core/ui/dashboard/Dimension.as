package com.tibco.be.views.core.ui.dashboard{

	public final class Dimension {
		
		private var __width:int;
		private var __height:int;
		
		public function Dimension(height:int,width:int):void {
			this.__height = height;
			this.__width = width;
		}
		
		public function get width():int {
			return __width;
		}
		
		public function get height():int {
			return __height;
		}
	}
}