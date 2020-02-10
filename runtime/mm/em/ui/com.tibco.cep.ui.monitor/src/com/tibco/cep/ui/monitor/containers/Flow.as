package com.tibco.cep.ui.monitor.containers {
	
	import flash.display.DisplayObject;
	
	import mx.core.Container;
	import mx.core.UIComponent;
	import mx.effects.Move;
	import mx.styles.CSSStyleDeclaration;
	import mx.styles.StyleManager;
	
	[Style(name="horizontalGap", type="Number", format="Length", inherit="no")]
	[Style(name="verticalGap", type="Number", format="Length", inherit="no")]
	[Style(name="leftPadding", type="Number", format="Length", inherit="no")]
	[Style(name="topPadding", type="Number", format="Length", inherit="no")]	
	
	public class Flow extends Container {
		
		private static var defaultStylesInitialized:Boolean = setDefaultStyles();
		private var lastWidth:Number = -1;
		private var lastHeight:Number = -1;
		
		public function Flow() {
			this.setStyle("verticalScrollBarStyleName", "genScrollStyle");	
		}
		
		private static function setDefaultStyles():Boolean {
			
			if (StyleManager.getStyleDeclaration("Flow") == null) {
				var controlStyleDeclaration : CSSStyleDeclaration = new CSSStyleDeclaration();
				controlStyleDeclaration.setStyle("horizontalGap",8);
				controlStyleDeclaration.setStyle("verticalGap",8);
				controlStyleDeclaration.setStyle("leftPadding",4);
				controlStyleDeclaration.setStyle("topPadding",5);
				
				StyleManager.setStyleDeclaration("Flow",controlStyleDeclaration,true);
			}
        	return true;
        }
        
        public function reLayoutItems(animate:Boolean = false):void{
				//we need to relayout the elements 
				var hGap:Number = getStyle("horizontalGap");
				var vGap:Number = getStyle("verticalGap");
				var x:Number = getStyle("leftPadding");
				var y:Number = getStyle("topPadding");
				var children:Array = getChildren();
				for (var i:Number = 0 ; i < children.length ; i++){
					var child:UIComponent = children[i];
					
					//we move to next row iff we have managed to place atleast one component in row 0
					if (x > getStyle("leftPadding") && x + child.width + hGap > unscaledWidth) {
						y = y + child.height + vGap;
						x = getStyle("leftPadding");
					}
					
					if(!animate){ child.move(x,y); }
					else{
						var move:Move = new Move();
						move.xFrom = child.x;
						move.xTo = x;
						move.yFrom = child.y;
						move.yTo = y;
						move.target = child;
						move.play();
					}
					//trace("Moved "+child+"["+child.width+","+child.height+"] to ["+x+","+y+"]");
					x = x + child.width + hGap;
				}        	
        }

		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
			super.updateDisplayList(unscaledWidth,unscaledHeight);
			//trace("updateDisplayList("+unscaledWidth+", "+unscaledHeight+")");
			if (lastWidth != unscaledWidth || lastHeight != unscaledHeight) {
				reLayoutItems();
				lastWidth = unscaledWidth;
				lastHeight = unscaledHeight;
			}			
		}
		
		override public function addChild(child:DisplayObject):DisplayObject{
			lastWidth = -1;
			return super.addChild(child);
		}
		
		override public function removeChild(child:DisplayObject):DisplayObject{
			lastWidth = -1;
			return super.removeChild(child);
		} 
		

	}
}