package com.tibco.be.views.user.components.pagesetselector{
	
	import mx.containers.Canvas;
	import mx.controls.Label;
	import mx.core.ScrollPolicy;

	public class BasicPageSetRowRenderer extends Canvas{
		
		private var _leadRenderer:Label;
		
		public function BasicPageSetRowRenderer(){
			horizontalScrollPolicy = ScrollPolicy.OFF;
			verticalScrollPolicy = ScrollPolicy.OFF;
			buttonMode = true;
			useHandCursor = true;
			mouseChildren = false;
			setStyle("borderStyle","solid");
			setStyle("borderSides","bottom");
			setStyle("borderColor","#A9A9A9");
			setStyle("paddingLeft","5");
			setStyle("paddingRight","5");
		}
		
		override protected function createChildren():void{
			_leadRenderer = new Label();
			_leadRenderer.percentWidth = 100;
			addChild(_leadRenderer);
		}
		
		override public function set data(value:Object):void {
			if(value == null){ return; }
			super.data = value;
			if(_leadRenderer != null){ _leadRenderer.text = value.title; }
			toolTip = value.tooltip;
		}
				
	}
}