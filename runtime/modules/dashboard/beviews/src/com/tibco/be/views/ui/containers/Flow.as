package com.tibco.be.views.ui.containers{
//	import com.tibco.be.views.core.Logger;
	
	import com.tibco.be.views.core.ui.dashboard.BEVComponent;
	import com.tibco.be.views.user.dashboard.BEVChartComponentHolder;
	import com.tibco.be.views.user.dashboard.EmbeddedBEVChartComponentHolder;
	
	import flash.display.DisplayObject;
	import flash.geom.Point;
	
	import mx.collections.ArrayCollection;
	import mx.collections.IList;
	import mx.core.Container;
	import mx.core.UIComponent;

	public class Flow extends Container implements IFlow{
		
		private var _layoutConfiguration:XML;
		
		private var _minCompWidth:int;
		private var _minCompHeight:int;
		private var _padding:int;
		
		private var _maxCols:int;
		private var _rows:IList; 
		
		private var _maxHeightSpan:int;
		private var _maxWidthSpan:int;
		
		private var _previousHeight:int;
		private var _previousWidth:int;
		private var _prevNumChildren:int;
		
		private var _visualOrderList:ArrayCollection;
		
		public function Flow(minComponentWidth:int, minComponentHeight:int){
			super();
			_visualOrderList = new ArrayCollection();
			_rows = new ArrayCollection();
			_minCompHeight = minComponentHeight;
			_minCompWidth = minComponentWidth;
			_padding = 5;
			_previousHeight = -1;
			_previousWidth = -1;
			_maxHeightSpan = 1;
			_maxWidthSpan = 1;
			_prevNumChildren = numChildren;
		}
		
		override public function addChildAt(child:DisplayObject, index:int):DisplayObject{
			super.addChildAt(child, index);
			var unscaledWidth:int = child.width;
			var unscaledHeight:int = child.height;
			var recomputeMaxHeight:Boolean = unscaledHeight > _maxHeightSpan;
			var recomputeMaxWidth:Boolean = unscaledWidth > _maxWidthSpan;
			if(recomputeMaxHeight || recomputeMaxWidth){
				recomputeSpans(recomputeMaxHeight, recomputeMaxWidth);
			}
			return child;
		}
		
		override public function removeChild(child:DisplayObject):DisplayObject{
			if(child == null){
				trace("WARNING: Flow.removeChild - Attempted removal of NULL child.");
				return null;
			}
			super.removeChild(child);
			var scaledWidth:int = child.width/_minCompWidth;
			var scaledHeight:int = child.height/_minCompHeight;
			var recomputeMaxWidth:Boolean = scaledWidth > _maxWidthSpan;
			var recomputeMaxHeight:Boolean = scaledWidth > _maxHeightSpan;
			if(recomputeMaxWidth ||recomputeMaxHeight){
				recomputeSpans(recomputeMaxHeight, recomputeMaxWidth);
			}
			return child;			
		}
		
		override public function removeChildAt(index:int):DisplayObject{
//			Logger.instance.logA(this,Logger.DEBUG,"removeChildAt("+index+")");
			var scaledWidth:int = 0;
			var scaledHeight:int = 0;
			var removedObj:DisplayObject = super.removeChildAt(index);
			if(removedObj is BEVChartComponentHolder){ 
				var chartCompHolder:BEVChartComponentHolder = removedObj as BEVChartComponentHolder;
				scaledWidth = chartCompHolder.component.layoutConstraints.width;
				scaledHeight = chartCompHolder.component.layoutConstraints.height;
			}
			else{
				scaledWidth = removedObj.width/_minCompWidth;
				scaledHeight = removedObj.height/_minCompHeight;
			}
			
			var recomputeMaxHeight:Boolean = scaledWidth > _maxHeightSpan;
			var recomputeMaxWidth:Boolean = scaledWidth > _maxWidthSpan;
			if(recomputeMaxHeight || recomputeMaxWidth){
				recomputeSpans(recomputeMaxHeight,recomputeMaxWidth);
			}
			return removedObj;				
		}
		
		override public function removeAllChildren():void{
			_maxHeightSpan = 1;
			_maxWidthSpan = 1;	
		}	
		
		
		private function recomputeSpans(height:Boolean, width:Boolean):void{
			var children:Array = getChildren();
			for(var i:int = 0; i < numChildren; i++){
				var child:DisplayObject = children[i];
				if(child is BEVChartComponentHolder){
					var scch:BEVChartComponentHolder = child as BEVChartComponentHolder;
					var cellWidth:Number = scch.component.layoutConstraints.width;
					var cellHeight:Number = scch.component.layoutConstraints.height;
					child.width = cellWidth * _minCompWidth + (cellWidth-1) * _padding;
					child.height = cellHeight * _minCompHeight + (cellHeight-1) * _padding;
				}
				if(height && child.height/minHeight > _maxHeightSpan){
					_maxHeightSpan = child.height/minHeight;
				}
				if(width && child.width/minWidth > _maxWidthSpan){
					_maxWidthSpan = child.width/minWidth;
				}
			}			
		} 		
			
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
//			Logger.instance.logA(this,Logger.DEBUG,"Flow.updateDisplayList("+unscaledWidth+","+unscaledHeight+")");
			
			//if a child has been added, continue UpdateDisplayList
			if(_prevNumChildren != numChildren){
				//just skip the else if checks...
			}
			//check if the new flow width calls for a recomputation of how many components per row
			else if(_previousWidth != -1 && Math.floor(unscaledWidth/_maxCols) == _minCompWidth){
				//if not, then don't bother computing anything
				super.updateDisplayList(unscaledWidth, unscaledHeight);
				return;
			}
			
			if(_maxWidthSpan * _minCompWidth > unscaledWidth){
				measuredWidth = _maxWidthSpan * _minCompWidth;
				_maxCols = _maxWidthSpan;
			}
			else{
				_maxCols = Math.floor((unscaledWidth - _padding)/(_padding + _minCompWidth));
				if(_maxCols == 0){
					_maxCols = 1;
				}
			}
			var computedHeight:int = 0;
			var children:Array = getChildren();
			for(var i:int = 0; i < children.length; i++){
				var child:UIComponent = children[i];
				var cUnscaledHeight:int = child.height;
				if(child is BEVChartComponentHolder){
					var holder:BEVChartComponentHolder = child as BEVChartComponentHolder;
					cUnscaledHeight = _minCompHeight * holder.component.layoutConstraints.height;
				}
				var childLoc:Point = findNextFreeCell(child);
				var x:int = childLoc.x*(_padding+_minCompWidth);
				var y:int = childLoc.y*(_padding+_minCompHeight);
				
				var currHeight:int = y + cUnscaledHeight;
				if(currHeight > computedHeight){
					computedHeight = currHeight;
				}
				child.move(x,y);
			}
			measuredHeight = computedHeight;
			//Logger.instance.logA(this,Logger.DEBUG,"Flow.updateDisplayList("+width+","+height+")");
			_rows.removeAll();
			_previousWidth = unscaledWidth;
			_previousHeight = unscaledHeight;
			_prevNumChildren = numChildren;
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			_visualOrderList.removeAll();
			for each(var dispObj:DisplayObject in children){
				addToVisualOrderList(dispObj);
			}
		}
		
		private function getRow(rowIdx:int):Array{
			if(rowIdx >= _rows.length){
				var columns:Array = new Array(_maxCols);
				_rows.addItemAt(columns, rowIdx);
				return columns;
			}
			return _rows.getItemAt(rowIdx) as Array;
		}
		
		private function findNextFreeCell(component:UIComponent):Point{
//			Logger.instance.logA(this,Logger.DEBUG,"trying to fit "+component);
			var scaledCompWidth:int = component.width/_minCompWidth;
			var scaledCompHeight:int = component.height/_minCompHeight;
			
			var rowIdx:int = 0;
			var currRow:Array = getRow(rowIdx);
			while(true){
				for(var startCell:int = 0; startCell < _maxCols; startCell++){
					var occupiedRowPos:int;
					var occupiedColPos:int;
					if(currRow[startCell] == null){
						//the given cell is free, but can it fit in the location?
						if(scaledCompWidth+startCell <= _maxCols){ //it's possible the component will fit in this row... 
							//ensure insertion of the component will not interfere with other components
							var canFitComponent:Boolean = true;
							var cellCheckPosition:int = startCell;
							//check horizontally across colums
							for(; cellCheckPosition < startCell+scaledCompWidth && canFitComponent; cellCheckPosition++){
								canFitComponent = currRow[cellCheckPosition] == undefined || currRow[cellCheckPosition] == null;
							}
							//check vertically across rows
							for(cellCheckPosition = rowIdx; cellCheckPosition < rowIdx+scaledCompHeight && canFitComponent; cellCheckPosition++){
								canFitComponent = getRow(cellCheckPosition)[startCell] == undefined || getRow(cellCheckPosition)[startCell] == null;
							}
							
							if(canFitComponent){
								//update the cell structure so that all cells being used to display the component are flagged as such
								for(occupiedRowPos = 0; occupiedRowPos < scaledCompHeight; occupiedRowPos++){
									currRow = getRow(rowIdx+occupiedRowPos);
									for(occupiedColPos = 0; occupiedColPos < scaledCompWidth; occupiedColPos++){
										currRow[startCell+occupiedColPos] = component;
									}
								}
								//Logger.instance.logA(this,Logger.DEBUG,"Fitting "+component+" into "+ rowIdx+","+i);
								return new Point(startCell, rowIdx);	
							}
							//can't fit component, continue looking for space
						}
						else{ //scaledCompWidth+startCell < _maxCols => the row does not have horizontal space for the component
							//if this row has no other components, the current component should be added anyway, else will infinitely look for row with sufficient width
							if(startCell == 0){
								for(occupiedRowPos = 0; occupiedRowPos < scaledCompHeight; occupiedRowPos++){
									currRow = getRow(rowIdx+occupiedRowPos);
									for(occupiedColPos = 0; occupiedColPos < scaledCompWidth; occupiedColPos++){
										currRow[startCell+occupiedColPos] = component;
									}
								}
								return new Point(startCell, rowIdx);
							} 
						}
					}
					else{ //(currRow[startCell] != null) => keep looking for an empty cell
						
					}
				}
				rowIdx++;
				currRow = getRow(rowIdx);
			}
			throw new Error("could not place "+component);			
		}
		
		private function addToVisualOrderList(component:DisplayObject):void{
			if(_visualOrderList == null){
				trace("Flow.addToVisualOrderList - WARNING Null list! Creating one to continue...");
				_visualOrderList = new ArrayCollection();
			}
			if(_visualOrderList.length == 0){
				_visualOrderList.addItem(component);
				return;
			}
			
			var i:int = 0;
			for(; i < _visualOrderList.length; i++){
				var curObj:DisplayObject = _visualOrderList.getItemAt(i) as DisplayObject;
				if(curObj.y < component.y) continue;
				if(curObj.y == component.y && curObj.x < component.x) continue;
				//else => (curObj.y > component.y || (curObj.y == component.y && curObj.x > component.x))
				break;
			}
			_visualOrderList.addItemAt(component, i);
		}
		
		public function getVisualPositionOf(component:DisplayObject):int{
			return _visualOrderList.getItemIndex(component);
		}
		
		public function moveChildRelative(refChild:DisplayObject, moveChild:DisplayObject, offset:int):void{
			var iMove:int = _visualOrderList.getItemIndex(moveChild)
			if(iMove < 0){
				trace("WARNING: Flow.moveChildRelative - Child to move is not contained in Flow.");
				return;
			}
			_visualOrderList.removeItemAt(iMove);
			
			//DON'T DO BEFORE REMOVAL OF THE CHILD-TO-MOVE
			var iRef:int = _visualOrderList.getItemIndex(refChild);
			if(iRef < 0){
				trace("WARNING: Flow.moveChildRelative - Reference child is not contained in Flow.");
				return;
			}
			iMove = Math.max(0, Math.min(_visualOrderList.length, iRef+offset));
			_visualOrderList.addItemAt(moveChild, iMove);
			
			syncActualOrderToVisual();
			
			//refresh display. change _prevNumChildren so updateDisplayList actually does something
			_prevNumChildren = -1;
			invalidateDisplayList();
		}
		
		public function addChildRelative(refChild:DisplayObject, newChild:DisplayObject, offset:int):void{
			var vi:int = _visualOrderList.getItemIndex(refChild);
			var nChildren:int = getChildren().length;
			syncActualOrderToVisual();
			//Once the child order is in-sync with the visual order, we can insert the new component
			addChildAt(newChild, Math.max(0, Math.min(vi+offset, _visualOrderList.length)));
		}
		
		/**
		 * Update the flow's child order to reflect what the user actually sees
		*/
		private function syncActualOrderToVisual():void{
			var nChildren:int = getChildren().length;
			for(var i:int = 0; i < nChildren; i++){
				if(getChildAt(i) == _visualOrderList[i]){ continue; }
				setChildIndex(_visualOrderList[i], i);
			}
		}
		
	}

}