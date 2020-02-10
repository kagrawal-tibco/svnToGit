package com.tibco.be.views.deprecated{
//	import com.tibco.be.views.core.Logger;
	
	import flash.display.DisplayObject;
	import flash.geom.Point;
	
	import mx.collections.ArrayCollection;
	import mx.collections.IList;
	import mx.core.Container;

	public class Flow extends Container {
		
		private var layoutConfiguration:XML;
		
		private var minCompWidth:int;
		private var minCompHeight:int;
		private var padding:int;
		
		private var maxCols:int;
		private var rows:IList; 
		
		private var maxHeightSpan:int;
		private var maxWidthSpan:int;
		
		private var previousHeight:int;
		private var previousWidth:int;
		private var _prevNumChildren:int;
		
		
		public function Flow(layoutConfiguration:XML = null) {
			super();
			this.layoutConfiguration = layoutConfiguration;
			if(this.layoutConfiguration == null) {
				this.layoutConfiguration = new XML("<layoutconfig type=\"flow\" width=\"200\" height=\"140\" movieurl=\"MetricPanel.swf\"></layoutconfig>");
			}
			rows = new ArrayCollection();
			minCompHeight = new int(layoutConfiguration.@height);
			minCompWidth = new int(layoutConfiguration.@width);
			padding = 0;
			previousHeight = -1;
			previousWidth = -1;
			maxHeightSpan = 1;
			maxWidthSpan = 1;
			_prevNumChildren = numChildren;
		}
		
		/*override protected function commitProperties():void {
			Logger.instance.logA(this,Logger.DEBUG,"Flow.commitProperties()");
			minCompHeight = layoutConfiguration.@height;
			minCompWidth = layoutConfiguration.@width;
			padding = 0;
			previousHeight = -1;
			previousWidth = -1;
			maxHeightSpan = 1;
			maxWidthSpan = 1;
			super.commitProperties();	
		}*/
		
		/*override protected function measure():void {
			Logger.instance.logA(this,Logger.DEBUG,"Flow.measure()");
			var children:Array = getChildren();
			for (var i:int = 0 ; i < children.length ; i++) {
				var child:SynChartComponentHolder = children[i] as SynChartComponentHolder;
				child.height = child.component.layoutconstraints.height * minCompHeight;
				child.width = child.component.layoutconstraints.width * minCompWidth;
				if(child.component.layoutconstraints.height > maxHeightSpan) {
					maxHeightSpan = child.component.layoutconstraints.height;
				}
				if(child.component.layoutconstraints.width > maxWidthSpan) {
					maxWidthSpan = child.component.layoutconstraints.width;
				}
			}
		}*/
		
		override public function addChildAt(child:DisplayObject, index:int):DisplayObject{
//			Logger.instance.logA(this,Logger.DEBUG,"addChildAt("+child+","+index+")");
			super.addChildAt(child,index);
			var chartCompHolder:SynChartComponentHolder = child as SynChartComponentHolder;
			chartCompHolder.height = chartCompHolder.component.layoutConstraints.height * minCompHeight;
			chartCompHolder.width = chartCompHolder.component.layoutConstraints.width * minCompWidth;
			var recomputeMaxHeight:Boolean = chartCompHolder.component.layoutConstraints.height > maxHeightSpan;
			var recomputeMaxWidth:Boolean = chartCompHolder.component.layoutConstraints.width > maxWidthSpan;
			if(recomputeMaxHeight == true || recomputeMaxWidth == true) {
				recomputeSpans(recomputeMaxHeight,recomputeMaxWidth);
			}
			return child;
		}
		
		override public function removeChild(child:DisplayObject):DisplayObject {
//			Logger.instance.logA(this,Logger.DEBUG,"addChild("+child+")");
			super.removeChild(child);
			var chartCompHolder:SynChartComponentHolder = child as SynChartComponentHolder;
			var recomputeMaxHeight:Boolean = chartCompHolder.component.layoutConstraints.height > maxHeightSpan;
			var recomputeMaxWidth:Boolean = chartCompHolder.component.layoutConstraints.width > maxWidthSpan;
			if(recomputeMaxHeight == true || recomputeMaxWidth == true) {
				recomputeSpans(recomputeMaxHeight,recomputeMaxWidth);
			}
			return child;			
		}
		
		override public function removeChildAt(index:int):DisplayObject {
//			Logger.instance.logA(this,Logger.DEBUG,"removeChildAt("+index+")");
			var chartCompHolder:SynChartComponentHolder = super.removeChildAt(index) as SynChartComponentHolder;
			var recomputeMaxHeight:Boolean = chartCompHolder.component.layoutConstraints.height > maxHeightSpan;
			var recomputeMaxWidth:Boolean = chartCompHolder.component.layoutConstraints.width > maxWidthSpan;
			if(recomputeMaxHeight == true || recomputeMaxWidth == true) {
				recomputeSpans(recomputeMaxHeight,recomputeMaxWidth);
			}
			return chartCompHolder;				
		}
		
		override public function removeAllChildren():void {
//			Logger.instance.logA(this,Logger.DEBUG,"removeAllChildren()");
			maxHeightSpan = 1;
			maxWidthSpan = 1;	
		}	
		
		
		private function recomputeSpans(height:Boolean, width:Boolean):void {
//			Logger.instance.logA(this,Logger.DEBUG,"recomputeSpans("+height+","+width+")");
			var children:Array = getChildren();
			for (var i:int = 0 ; i < numChildren ; i++) {
				var child:SynChartComponentHolder = children[i] as SynChartComponentHolder;
				child.height = child.component.layoutConstraints.height * minCompHeight;
				child.width = child.component.layoutConstraints.width * minCompWidth;
				if(height == true && child.component.layoutConstraints.height > maxHeightSpan) {
					maxHeightSpan = child.component.layoutConstraints.height;
				}
				if(width == true && child.component.layoutConstraints.width > maxWidthSpan) {
					maxWidthSpan = child.component.layoutConstraints.width;
				}
			}			
		} 		
			
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
//			Logger.instance.logA(this,Logger.DEBUG,"Flow.updateDisplayList("+unscaledWidth+","+unscaledHeight+")");
			
			//if a child has been added, continue UpdateDisplayList
			if(_prevNumChildren != numChildren){
				//just skip the else if checks...
			}
			//check if the difference between previous width and new width is more then one min component width
			else if(previousWidth != -1 && Math.abs(previousWidth-unscaledWidth) < minCompWidth) {
				//no its not, then don't bother computing anything
				super.updateDisplayList(unscaledWidth, unscaledHeight);
				return;
			}
			
			if(maxWidthSpan * minCompWidth > unscaledWidth) {
				measuredWidth = maxWidthSpan * minCompWidth;
				maxCols = maxWidthSpan;
			}
			else{
				maxCols = Math.floor((unscaledWidth - padding)/(padding + minCompWidth));
				if(maxCols == 0) {
					maxCols = 1;
				}
			}
			var computedHeight:int = 0;
			var children:Array = getChildren();
			for (var i:int = 0 ; i < children.length ; i++) {
				var child:SynChartComponentHolder = children[i] as SynChartComponentHolder;
				var childLoc:Point = findNextFreeCell(child);
				var x:int = childLoc.x*(padding+minCompWidth);
				var y:int = childLoc.y*(padding+minCompHeight);
				var currHeight:int = y + minCompHeight * child.component.layoutConstraints.height;
				if(currHeight > computedHeight) {
					computedHeight = currHeight;
				}
				child.move(x,y);
			}
			measuredHeight = computedHeight;
			//Logger.instance.logA(this,Logger.DEBUG,"Flow.updateDisplayList("+width+","+height+")");
			rows.removeAll();
			previousWidth = unscaledWidth;
			previousHeight = unscaledHeight;
			_prevNumChildren = numChildren;
			super.updateDisplayList(unscaledWidth, unscaledHeight);
		}
		
		private function getRow(rowIdx:int):Array {
			if(rowIdx >= rows.length) {
				var columns:Array = new Array(maxCols);
				rows.addItemAt(columns,rowIdx);
				return columns;
			}
			return rows.getItemAt(rowIdx) as Array;
		}
		
		private function findNextFreeCell(component:SynChartComponentHolder): Point {
//			Logger.instance.logA(this,Logger.DEBUG,"trying to fit "+component);
			var widthSpan:int = component.component.layoutConstraints.width;
			var heightSpan:int = component.component.layoutConstraints.height;			
			var rowIdx:int = 0;
			var currRow:Array = getRow(rowIdx);
			while (true) {
				for (var i:int = 0 ; i < maxCols ; i++ ){
					if(currRow[i] == null) {
						//the given cell is free, but can it fit in the location width wise 
						if(maxCols - i >= widthSpan ) {
							//yes the component has space to fit in the row, 
							//we need to check if there is going to be a overlap column wise
							var noFit:Boolean = false;
							var l:int = i ;
							while(l < i + widthSpan) {
								//Logger.instance.logA(this,Logger.DEBUG,"Checking collision "+rowIdx+","+l+" is "+currRow[l]);
								if(currRow[l] != undefined || currRow[l] != null) {
									//we have a component here 
									noFit = true;
								}
								l++;
							}
							l = rowIdx;
							while(l < rowIdx + heightSpan) {
								//Logger.instance.logA(this,Logger.DEBUG,"Checking collision "+l+","+i+" is "+getRow(l)[i]);
								if(getRow(l)[i] != undefined || getRow(l)[i] != null) {
									//we have a component here 
									noFit = true;
								}
								l++;								
							}
							if(noFit == false ) {
								//we can fit the component in the given cell width wise 
								//now we need to fit the component height wise 
								for (var j:int = 0 ; j < heightSpan ; j++) {
									for (var k:int = 0 ; k < widthSpan ; k++) {
										currRow[i+k] = component;
									}
									if(j + 1 < heightSpan) {
										currRow = getRow(rowIdx+j+1);
									}
								}
								//Logger.instance.logA(this,Logger.DEBUG,"Fitting "+component+" into "+ rowIdx+","+i);
								return new Point(i,rowIdx);	
							}
							else{
								//Logger.instance.logA(this,Logger.DEBUG,"Skipping "+rowIdx+","+i+" since "+component+" will collide with "+currRow[l]);
							}							
						}
						else{
							//Logger.instance.logA(this,Logger.DEBUG,"Skipping "+rowIdx+","+i+" since "+component+" will not fit col span wise");
						}
					}
					else{
						//Logger.instance.logA(this,Logger.DEBUG,"Skipping "+rowIdx+","+i+" since it is filled by "+currRow[i]);
					}
				}
				rowIdx++;
				currRow = getRow(rowIdx);
			}
			throw new Error("could not place "+component);			
		}
		
	}

}