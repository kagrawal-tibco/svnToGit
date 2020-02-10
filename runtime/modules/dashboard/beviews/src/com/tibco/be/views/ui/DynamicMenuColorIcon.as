package com.tibco.be.views.ui{
	import flash.display.BitmapData;
	
	import mx.core.BitmapAsset;
	
	public class DynamicMenuColorIcon extends BitmapAsset{
		
		public static const DEFAULT_ICON_SIZE:int = 10;
		public static const DEFAULT_ICON_COLOR:uint = 0xFFFFFF;
		
		public function DynamicMenuColorIcon(bitmapData:BitmapData = null, pixelSnapping:String = "auto", smoothing:Boolean = false){
            super(bitmapData, pixelSnapping, smoothing);
            bitmapData = new BitmapData(DEFAULT_ICON_SIZE, DEFAULT_ICON_SIZE, false, DEFAULT_ICON_COLOR);
        }
		
		public function setbMapData(color:uint=0x0, size:int=DEFAULT_ICON_SIZE, transparent:Boolean=false):void{
			bitmapData = new BitmapData(size, size, transparent, color);
		}

	}
}