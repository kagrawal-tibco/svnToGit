package com.tibco.be.views.user.components.chart.renderers.item{
	
	import com.tibco.be.views.user.components.chart.BEVChartDataLabel;
	import com.tibco.be.views.user.components.chart.BEVVRangeChart;
	import com.tibco.be.views.user.components.chart.RangeDataPoint;
	import com.tibco.be.views.user.components.chart.series.IBEVLabeledSeries;
	
	import flash.display.Graphics;
	
	import mx.charts.ChartItem;
	import mx.core.IDataRenderer;
	import mx.graphics.IStroke;
	import mx.graphics.LinearGradientStroke;
	import mx.graphics.Stroke;
	import mx.skins.ProgrammaticSkin;
	import mx.styles.StyleManager;
	import mx.utils.ColorUtil;
	
	public class LabeledHLOCItemRenderer extends ProgrammaticSkin implements IDataRenderer{
		
		private var _chartItem:Object;
		
		private var _currentValueLabel:BEVChartDataLabel;
		private var _minValueLabel:BEVChartDataLabel;
		private var _maxValueLabel:BEVChartDataLabel;
		
		private var _currentLabelConfig:XML;
		private var _minLabelConfig:XML;
		private var _maxLabelConfig:XML;
		
		private var _whiskerY:Number;
		
		public function LabeledHLOCItemRenderer(labelConfig:XML){
			super();
			_currentLabelConfig = labelConfig;
			_whiskerY = 0;
			
			for each(var seriesCfg:XML in labelConfig.seriesconfig){ //special handling for range chart config
				if(seriesCfg.valuelabelconfig == undefined){ continue; }
				var actualLabelConfig:XML = seriesCfg.valuelabelconfig[0] as XML;
				var name:String = String(seriesCfg.@name);
				if(String(seriesCfg.@name) == BEVVRangeChart.CURRENT_SERIES_NAME){
					_currentLabelConfig = new XML(actualLabelConfig);
				}
				else if(String(seriesCfg.@name) == BEVVRangeChart.MIN_SERIES_NAME){
					_minLabelConfig = new XML(actualLabelConfig);
				}
				else if(String(seriesCfg.@name) == BEVVRangeChart.MAX_SERIES_NAME){
					_maxLabelConfig = new XML(actualLabelConfig);
				}
			}
		}
		
		[Inspectable(environment="none")]
		public function get data():Object{ return _chartItem; }
		
		public function get whiskerY():Number{ return _whiskerY; }
		
		public function set data(value:Object):void{
			if(_chartItem == value){ return; }	
			_chartItem = value;
			invalidateDisplayList();
		}
		
		public function isMinLabel(label:BEVChartDataLabel):Boolean{ return label == _minValueLabel; }
		public function isCurrentLabel(label:BEVChartDataLabel):Boolean{ return label == _currentValueLabel; }
		public function isMaxLabel(label:BEVChartDataLabel):Boolean{ return label == _maxValueLabel; }
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			if(data.item is RangeDataPoint && data.item.colID == null){ return; } //destroyed datacolumn
			var parentSeries:IBEVLabeledSeries = parent as IBEVLabeledSeries;
			var rdp:RangeDataPoint = data.item as RangeDataPoint;
			if(rdp != null && !(rdp.max >= rdp.current && rdp.current >= rdp.min)){
				drawErrorPoint(unscaledWidth, unscaledHeight);
				return;
			}
			drawDataPoint(unscaledWidth, unscaledHeight);
			if(_currentLabelConfig != null){
				if(_currentValueLabel == null){
					_currentValueLabel = parentSeries.createLabel(_currentLabelConfig);
				}
				_currentValueLabel.text = rdp.currentDisplayValue;
				parentSeries.updateLabel(_currentValueLabel, this, data);
			}
			if(_minLabelConfig != null){
				if(_minValueLabel == null){
					_minValueLabel = parentSeries.createLabel(_minLabelConfig);
				}
				_minValueLabel.text = rdp.minDisplayValue;
				parentSeries.updateLabel(_minValueLabel, this, data);
			}
			if(_maxLabelConfig != null){
				if(_maxValueLabel == null){
					_maxValueLabel = parentSeries.createLabel(_maxLabelConfig);
				}
				_maxValueLabel.text = rdp.maxDisplayValue;
				parentSeries.updateLabel(_maxValueLabel, this, data);
			}
			
		}
		
		protected function drawErrorPoint(unscaledWidth:Number, unscaledHeight:Number):void{
			var size:Number = 10;
			var left:Number = unscaledWidth/2 - size/2;
			var right:Number = left + size;
			var y:Number = 0;
			
			if(_chartItem){
				var lowValue:Number = Math.max(_chartItem.low,Math.max(_chartItem.high,_chartItem.close));
			    var highValue:Number = Math.min(_chartItem.low,Math.min(_chartItem.high,_chartItem.close));
			    var HLOCHeight:Number = lowValue - highValue;
			    var heightScaleFactor:Number = (HLOCHeight > 0)? (height / HLOCHeight):0;
			    y = (_chartItem.open - highValue) * heightScaleFactor;
			}
			else{
				y = 0.75 * height
			}
			y -= size/2;
			    
			    
			graphics.clear();
			graphics.lineStyle(2, 0xAA0000);
			graphics.moveTo(left, y);
			graphics.lineTo(right, y+size);
			graphics.moveTo(right, y);
			graphics.lineTo(left, y+size);
		}
		
		protected function drawDataPoint(unscaledWidth:Number, unscaledHeight:Number):void{
			var istroke:IStroke = getStyle("stroke");;
			var stroke:Stroke;
			var lgstroke:LinearGradientStroke;
			
			if(istroke is Stroke){
				stroke = Stroke(istroke);
			}
			else if(istroke is LinearGradientStroke){
				lgstroke = LinearGradientStroke(istroke);
			}
			else{
				stroke = new Stroke(getStyle('hlocColor'), istroke.weight);
			}
			
			var iOpenTickStroke:IStroke = getStyle("openTickStroke");
			var openTickStroke:Stroke;
			var lgOpenTickStroke:LinearGradientStroke;
			
			if(iOpenTickStroke is Stroke){
				openTickStroke = Stroke(iOpenTickStroke);
			}
			else if(iOpenTickStroke is LinearGradientStroke){
				lgOpenTickStroke = LinearGradientStroke(iOpenTickStroke);
			}
			else{
				openTickStroke = new Stroke(getStyle('hlocColor'), iOpenTickStroke.weight, 1, false, "normal", "none");
			}
				
			var iCloseTickStroke:IStroke = getStyle("closeTickStroke");
			var closeTickStroke:Stroke;
			var lgCloseTickStroke:LinearGradientStroke;
			
			if(iCloseTickStroke is Stroke){
				closeTickStroke = Stroke(iCloseTickStroke);
			}
			else if(iCloseTickStroke is LinearGradientStroke){
				lgCloseTickStroke = LinearGradientStroke(iCloseTickStroke);
			}
			else{
				closeTickStroke = new Stroke(getStyle('hlocColor'), iCloseTickStroke.weight, 1, false, "normal", "none");
			}
				
			var w2:Number = unscaledWidth / 2;
			var openTickLen:Number = Math.min(w2, getStyle("openTickLength"));
			var closeTickLen:Number = Math.min(w2, getStyle("closeTickLength"));
			var openTick:Number;
			var closeTick:Number;
			var state:String = "";
			var oldColor:uint;
			var oldOpenTickColor:uint;
			var oldCloseTickColor:uint;
			var strokeColor:uint;
			var openTickColor:uint;
			var closeTickColor:uint;
			        
			if(_chartItem){
			    var lowValue:Number = Math.max(_chartItem.low,Math.max(_chartItem.high,_chartItem.close));
			    var highValue:Number = Math.min(_chartItem.low,Math.min(_chartItem.high,_chartItem.close));
			    if(!isNaN(_chartItem.open)){
			        lowValue = Math.max(lowValue,_chartItem.open);
			        highValue = Math.min(highValue,_chartItem.open);
			    }
			
			    var HLOCHeight:Number = lowValue - highValue;
			    var heightScaleFactor:Number = (HLOCHeight > 0)? (height / HLOCHeight):0;
			    
				openTick = (_chartItem.open - highValue) * heightScaleFactor;
			    closeTick = (_chartItem.close - highValue) * heightScaleFactor;
			    state = _chartItem.currentState;
			    if(state && state != ""){
					if(stroke){
			       		strokeColor = stroke.color;
			       		oldColor = stroke.color;
			  		}
			    	else if(lgstroke.entries.length > 0){
			    		strokeColor = lgstroke.entries[0].color;
			    		oldColor = lgstroke.entries[0].color;
			    	}	
			    	if(openTickStroke){
			    		openTickColor = openTickStroke.color;
			    		oldOpenTickColor = openTickStroke.color;
			    	}
			    	else if(lgOpenTickStroke.entries.length > 0){
			        	openTickColor = lgOpenTickStroke.entries[0].color;
			        	oldOpenTickColor = lgOpenTickStroke.entries[0].color;
			     	}
			    	if(closeTickStroke){
			        	closeTickColor = closeTickStroke.color;
			        	oldCloseTickColor = closeTickStroke.color;
			     	}
			    	else if(lgCloseTickStroke.entries.length > 0){
			        	closeTickColor = lgCloseTickStroke.entries[0].color;
			        	oldCloseTickColor = lgCloseTickStroke.entries[0].color;
			     	}           
			 	}
			 	
			 	if(_chartItem.item is RangeDataPoint && _chartItem.item.alertColors != null){
					openTickColor = closeTickColor = _chartItem.item.alertColors.baseColor;
				}
				
			    switch(state){
			        case ChartItem.FOCUSED:
			        case ChartItem.ROLLOVER:
			            if(StyleManager.isValidStyleValue(getStyle('itemRollOverColor'))){
			            	strokeColor = getStyle('itemRollOverColor');
			            }
			            else{
			            	strokeColor = ColorUtil.adjustBrightness2(strokeColor, -20);
			            }
			            openTickColor = ColorUtil.adjustBrightness2(openTickColor, -20);
			            closeTickColor = ColorUtil.adjustBrightness2(closeTickColor, -20);
			            break;
			        case ChartItem.DISABLED:
			            if(StyleManager.isValidStyleValue(getStyle('itemDisabledColor'))){
			            	strokeColor = getStyle('itemDisabledColor');
			            }
			            else{
			            	strokeColor = ColorUtil.adjustBrightness2(strokeColor, 20);
			            }
			            openTickColor = ColorUtil.adjustBrightness2(openTickColor, 20);
			            closeTickColor = ColorUtil.adjustBrightness2(closeTickColor, 20);
			            break;
			        case ChartItem.FOCUSEDSELECTED:
			        case ChartItem.SELECTED:
			            if(StyleManager.isValidStyleValue(getStyle('itemSelectionColor'))){
			            	strokeColor = getStyle('itemSelectionColor');
			            }
			            else{
			            	strokeColor = ColorUtil.adjustBrightness2(strokeColor, -30);
			            }
			            openTickColor = ColorUtil.adjustBrightness2(openTickColor, -30);
			            closeTickColor = ColorUtil.adjustBrightness2(closeTickColor, -30);
			            break;
			    }
			}
			else{
			    openTick = 0.75 * height;
			    closeTick = 0.25 * height;
			}
			
			if(state && state != ""){
				if(stroke){
					stroke.color = strokeColor;
				}
				else if(lgstroke.entries.length > 0){
					lgstroke.entries[0].color = strokeColor;
				}

				if(openTickStroke){
					openTickStroke.color = openTickColor;
				}
				else if(lgOpenTickStroke.entries.length > 0){
					lgOpenTickStroke.entries[0].color = openTickColor;
				}
				
				if(closeTickStroke){
					closeTickStroke.color = closeTickColor;
				}
				else if(lgCloseTickStroke.entries.length > 0){
					lgCloseTickStroke.entries[0].color = closeTickColor;
				}
			}
			var g:Graphics = graphics;
			g.clear();
			if(stroke){
				stroke.apply(g);
			}
			else{
				lgstroke.apply(g);
			}
			g.moveTo(w2, 0);
			g.lineTo(w2, height);
			if(!isNaN(openTick)){
			    if(openTickStroke){
			    	openTickStroke.apply(g);
			    }
			    else{
			    	lgOpenTickStroke.apply(g);
			    }
			    g.moveTo(w2, openTick);
			    g.lineTo(w2 - openTickLen, openTick);
			}
			if(!isNaN(closeTick)){
			    if(closeTickStroke){
			    	closeTickStroke.apply(g);
			    }
			    else{
			    	lgCloseTickStroke.apply(g);
			    }
			    g.moveTo(w2, closeTick);
			    g.lineTo(w2 + closeTickLen, closeTick); 
			}
			
			// Restore to old colors - after selection drawing is done.
			if(state && state != ""){
				if(stroke){
			       	stroke.color = oldColor;
			 	}
			    else if(lgstroke.entries.length > 0){
			       	lgstroke.entries[0].color = oldColor;
			    }
			    
			    if(openTickStroke){
			        openTickStroke.color = oldOpenTickColor;
			    }
			    else if(lgOpenTickStroke.entries.length > 0){
			        lgOpenTickStroke.entries[0].color = oldOpenTickColor;
			    }
			    
			    if(closeTickStroke){
			        closeTickStroke.color = oldCloseTickColor;
			    }
			    else if(lgCloseTickStroke.entries.length > 0){
			        lgCloseTickStroke.entries[0].color = oldCloseTickColor;
			    }
			}
			
			 _whiskerY = openTick;
		}
		
	}
}
