package com.tibco.be.views.user.components.chart.renderers.item{
	
	import com.tibco.be.views.user.components.chart.BEVChartDataLabel;
	import com.tibco.be.views.user.components.chart.DataColumn;
	
	import flash.display.DisplayObjectContainer;
	import flash.text.TextLineMetrics;
	
	import mx.charts.ChartItem;
	
	public class LabeledItemUtil{
		
		public static const LABEL_Y_OFFSET:int = -13;
		public static const LABEL_X_PADDING:int = 5;
		
		/**
		 * Provides an easy way to get new, uniform instances of BEViewsChartDataLabel.
		 * 
		 * @return new BEViewsChartDataLable
		*/
		public static function createLabel(labelConfig:XML):BEVChartDataLabel{
			var newLabel:BEVChartDataLabel = new BEVChartDataLabel(labelConfig);
			return newLabel;
		}
		
		/**
		 * @param data The ChartItem object (passed as Object) utilized by the LabeledItemRenderer.
		*/
		public static function updateLabel(textLabel:BEVChartDataLabel, data:Object, itemRadius:Number, x:int, y:int, minX:int=0, minY:int=0, maxX:int=9999, maxY:int=9999):void{
			var displayText:String = (data == null || data.item == null) ? "null":data.item.displayValue;
			textLabel.text = displayText;
			if(data is ChartItem && data.item is DataColumn){
				//set colors and styles. don't set style if not needed
				var dc:DataColumn = data.item as DataColumn;
				if(dc.fontColor != DataColumn.COLOR_NONE){
					textLabel.setStyle("color", dc.fontColor);
				}
				else{
					textLabel.setDefaultFontColor();
				}
				
				if(dc.fontStyle != null && dc.fontStyle != "normal"){
					if(dc.fontStyle.indexOf("bold") >= 0){ textLabel.setStyle("fontWeight", "bold"); }
					if(dc.fontStyle.indexOf("italic") >= 0){ textLabel.setStyle("fontStyle", "italic"); }
				}
				else{
					textLabel.setDefaultFontWeight();
					textLabel.setDefaultFontStyle();
				}
			}
			
			var lineMetrics:TextLineMetrics =  textLabel.getLineMetrics(0);
			
			var avgCharWidth:Number = textLabel.textWidth/displayText.length;
			var labelWidth:int =  textLabel.textWidth + avgCharWidth;
			textLabel.height = lineMetrics == null ? 15:lineMetrics.height;
			textLabel.width = labelWidth;
			maxX -= labelWidth;
						
			//move label to be centered above the datapoint but not off chart
			var plannedX:int = x - labelWidth/2 + itemRadius;
			textLabel.x = Math.min(Math.max(minX, plannedX), maxX);
			
			//move label up above datapoint but not out of chart
			textLabel.y = Math.max(minY, y + LABEL_Y_OFFSET);
			//textLabel.y = Math.min(textLabel.y, maxY-textLabel.height);
			
			//textLabel.validateNow();
		}
		
		/**
		 * Check label position against previous labels to ensure no overlap. Overlap is handled by
		 * attempting to move the label below the point instead of above.  If that fails, an attempt
		 * is made to put the label twice as high above the point.  If this also fails, the label is
		 * dropped.
		 * 
		 * @return True if the label was successfully placed. False to drop the label.
		*/
		public static function detectAndFixLabelOverlap(labelContainer:DisplayObjectContainer, label:BEVChartDataLabel, shouldAttemptMoveDown:Boolean=true, shouldAttemptMoveUp:Boolean=false):Boolean{
			//Compare the passed label to all existing labels in labelContainer, attempting to move
			//the label if it overlaps with any existing label. Loop backwards for slightly better
			//performance (if the passed label does overlap with an existing label, it's likely that
			//the existing label will have recently been put in the container).
			//
			//Since there are potentially many series' labels being displayed in the containter,
			//there is no assumption of label order. All existing labels thus need to be checked for
			//overlap.
			for(var i:int = labelContainer.numChildren-1; i >= 0; i--){
				if(!(labelContainer.getChildAt(i) is BEVChartDataLabel)){ continue; }
				
				var existingLabel:BEVChartDataLabel = labelContainer.getChildAt(i) as BEVChartDataLabel;
				if(existingLabel == label){ continue; }
				
				if(!labelsHorizontallyOverlap(label, existingLabel)){
					continue; //no horizontal overlap therefore no overlap
				}
				if(!labelsVerticallyOverlap(label, existingLabel)){
					continue; //horizontal overlap but no vertical overlap therefore no overlap
				}
				else{ //overlap
					if(!shouldAttemptMoveDown){ return false; }
					if(!attemptMoveDown(labelContainer, label)){
						if(shouldAttemptMoveUp){
							return attemptMoveUp(labelContainer, label);
						}
						return false; //unsuccessful attemptMoveDown
					}
					return true; //successful attemptMoveDown
				}
			}
			return true; //no overlap with any previous label
		}
		
		public static function attemptMoveDown(labelContainer:DisplayObjectContainer, label:BEVChartDataLabel):Boolean{
			return attemptMoveLabel(labelContainer, label, label.x, label.y + label.textHeight);
		}
		
		public static function attemptMoveUp(labelContainer:DisplayObjectContainer, label:BEVChartDataLabel):Boolean{
			return attemptMoveLabel(labelContainer, label, label.x, label.y - 2*label.textHeight);
		}
		
		/**
		 * Attempts to move the provided label to the specified (newX, newY) position. Sucess or
		 * faliure is determined by whether or not the new location overlaps another label.
		 * 
		 * @return False on failed attempt. True on successful move. Note the label will be set to
		 * 		the new x and y coordinates if successfully placed.
		*/
		public static function attemptMoveLabel(labelContainer:DisplayObjectContainer, label:BEVChartDataLabel, newX:int, newY:int):Boolean{
			var origX:int = label.x;
			var origY:int = label.y;
			label.x = Math.max(newX, 0);
			label.y = Math.max(newY, 0);
			//search all previously checked labels to make sure moving of the label won't interfere
			//with their space.
			for(var i:int = 0; i < labelContainer.numChildren; i++){
				var lbl2Check:BEVChartDataLabel = labelContainer.getChildAt(i) as BEVChartDataLabel;;
				if(lbl2Check == null) continue;
				if(labelsOverlap(label, lbl2Check)){
					//reset label position and signal caller that move failed
					label.x = origX;
					label.y = origY;
					return false;
				}
			} 
			return true;
		}
		
		public static function labelsOverlap(l1:BEVChartDataLabel, l2:BEVChartDataLabel):Boolean{
			return (labelsHorizontallyOverlap(l1, l2) && labelsVerticallyOverlap(l1, l2));
		}
		
		public static function labelsHorizontallyOverlap(l1:BEVChartDataLabel, l2:BEVChartDataLabel):Boolean{
			return(
				(l1.x >= l2.x && l1.x <= (l2.x + l2.textWidth + LABEL_X_PADDING)) ||
				(l2.x >= l1.x && l2.x <= (l1.x + l1.textWidth + LABEL_X_PADDING))
			);
		}
		
		public static function labelsVerticallyOverlap(l1:BEVChartDataLabel, l2:BEVChartDataLabel):Boolean{
			return(
				(l1.y >= l2.y && l1.y <= (l2.y + l2.height)) ||
				(l2.y >= l1.y && l2.y <= (l1.y + l1.height))
			);
		}

	}
}