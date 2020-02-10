package com.tibco.be.views.user.components.chart.renderers.item{
	
	import com.tibco.be.views.user.components.chart.BEVChartDataLabel;
	import com.tibco.be.views.user.components.chart.DataColumn;
	import com.tibco.be.views.user.components.chart.series.IBEVLabeledSeries;
	
	import mx.charts.ChartItem;
	import mx.charts.chartClasses.GraphicsUtilities;
	import mx.core.IDataRenderer;
	import mx.graphics.IFill;
	import mx.graphics.IStroke;
	import mx.graphics.SolidColor;
	import mx.graphics.Stroke;
	import mx.skins.ProgrammaticSkin;
	import mx.styles.StyleManager;
	import mx.utils.ColorUtil;

	public class BEVLabeledItemRenderer extends ProgrammaticSkin implements IDataRenderer{
		
		private var _chartItem:Object;
		private var _textLabel:BEVChartDataLabel;
		private var _labelConfig:XML;
		
		public function BEVLabeledItemRenderer(labelConfig:XML){
			super();
			_labelConfig = labelConfig;
		}
		
		[Inspectable(environment="none")]
		public function get data():Object{ return _chartItem; }
		
		public function set data(value:Object):void{
			if(_chartItem == value){ return; }	
			_chartItem = value;
			invalidateDisplayList();
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			var parentSeries:IBEVLabeledSeries = parent as IBEVLabeledSeries;
			if(data.item is DataColumn && data.item.colID == null){ return; } //destroyed datacolumn
			if(_labelConfig != null){
				if(_textLabel == null){
					_textLabel = parentSeries.createLabel(_labelConfig);
				}
				parentSeries.updateLabel(_textLabel, this, data);
			}
			if(parentSeries.rendererShouldDrawPoint(data)){
				configDataPoint(unscaledWidth, unscaledHeight);
			}
			else{
				graphics.clear();
			}
		}
		
		protected function configDataPoint(unscaledWidth:Number, unscaledHeight:Number):void{
			super.updateDisplayList(unscaledWidth, unscaledHeight);
			var fill:IFill;
			var state:String = "";
			var defaultColor:uint;
			var useAlertColor:Boolean;
			
			if(_chartItem is ChartItem && _chartItem.hasOwnProperty('fill')){
			 	fill = _chartItem.fill;
			 	state = _chartItem.currentState;
			}
			else{
			 	fill = GraphicsUtilities.fillFromStyle(getStyle('fill'));
			}
			 
	        var color:uint;
			var adjustedRadius:Number = 0;
			switch(state){
				case ChartItem.FOCUSED:
				case ChartItem.ROLLOVER:
					if(StyleManager.isValidStyleValue(getStyle('itemRollOverColor'))){
						color = getStyle('itemRollOverColor');
					}
					else{
						color = ColorUtil.adjustBrightness2(GraphicsUtilities.colorFromFill(fill),-20);
					}
					fill = new SolidColor(color);
					adjustedRadius = getStyle('adjustedRadius');
					if(!adjustedRadius){ adjustedRadius = 0; }
					break;
				case ChartItem.DISABLED:
					if(StyleManager.isValidStyleValue(getStyle('itemDisabledColor'))){
						color = getStyle('itemDisabledColor');
					}
					else{
						color = ColorUtil.adjustBrightness2(GraphicsUtilities.colorFromFill(fill),20);
					}
					fill = new SolidColor(GraphicsUtilities.colorFromFill(color));
					break;
				case ChartItem.FOCUSEDSELECTED:
				case ChartItem.SELECTED:
					if(StyleManager.isValidStyleValue(getStyle('itemSelectionColor'))){
						color = getStyle('itemSelectionColor');
					}
					else{
						color = ColorUtil.adjustBrightness2(GraphicsUtilities.colorFromFill(fill),-30);
					}
					fill = new SolidColor(color);
					adjustedRadius = getStyle('adjustedRadius');
					if(!adjustedRadius){ adjustedRadius = 0; }
					break;
			}
			  
			var stroke:IStroke = getStyle("stroke"); //color in chart item's item (data column)
			useAlertColor = (_chartItem.item is DataColumn && _chartItem.item.fillColors != null);
			if(useAlertColor){
				if(fill is SolidColor){
					defaultColor = (fill as SolidColor).color;
					(fill as SolidColor).color = _chartItem.item.fillColors.baseColor;
				}
				if(stroke is Stroke){
					(stroke as Stroke).color = _chartItem.item.fillColors.baseColor;
				}
			}
					
			drawDataPoint(unscaledWidth, unscaledHeight, adjustedRadius, stroke, fill);
			
			//because fill and stroke are references to the fill/stroke for all chart items, we
			//need to reset their color after drawing an alert point
			if(useAlertColor){
				if(stroke is Stroke){ (stroke as Stroke).color = defaultColor; }
				if(fill is SolidColor){ (fill as SolidColor).color = defaultColor; }
			}
		}
		
		protected virtual function drawDataPoint(unscaledWidth:Number, unscaledHeight:Number, adjustedRadius:Number, stroke:IStroke, fill:IFill):void{
			/* virtual */
		}
		
	}
}