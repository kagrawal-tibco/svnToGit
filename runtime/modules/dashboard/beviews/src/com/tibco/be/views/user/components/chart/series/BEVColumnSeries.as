package com.tibco.be.views.user.components.chart.series{
	
	import com.tibco.be.views.core.events.logging.DefaultLogEvent;
	import com.tibco.be.views.user.components.chart.BEVChart;
	import com.tibco.be.views.user.components.chart.BEVChartDataLabel;
	import com.tibco.be.views.user.components.chart.ChartUtils;
	import com.tibco.be.views.user.components.chart.DataColumn;
	import com.tibco.be.views.user.components.chart.renderers.item.LabeledItemRendererFactory;
	import com.tibco.be.views.user.components.chart.renderers.item.LabeledItemUtil;
	import com.tibco.be.views.utils.Logger;
	
	import mx.charts.ChartItem;
	import mx.charts.chartClasses.CartesianChart;
	import mx.charts.series.ColumnSeries;
	import mx.graphics.IFill;
	import mx.skins.ProgrammaticSkin;
	
	public class BEVColumnSeries extends ColumnSeries implements IBEVLabeledSeries{
		
		private static const DEFAULT_COL_MAX_WIDTH:int = 5;
	
		private var _seriesConfigXML:XML;
		private var _actionConfigXML:XML;
		private var _seriesConfig:BEVSeriesConfig;
		private var _calculatedColWidth:int = -1;
		private var _barAlpha:Number;
		private var _labelMaxX:int;
		
		public function BEVColumnSeries(seriesConfigObj:BEVSeriesConfig, maxColWidth:Number, overlapPercentage:Number, barAlpha:Number=1){
			super();
			name = seriesConfigObj.seriesID;
			displayName = seriesConfigObj.seriesName;
			_seriesConfigXML = seriesConfigObj.configXML;
			_actionConfigXML = BEVSeriesUtil.getActionConfig(_seriesConfigXML);
			_seriesConfig = seriesConfigObj;
			_barAlpha = barAlpha;
			_labelMaxX = 0;
			maxColumnWidth = maxColWidth;
			_calculatedColWidth = maxColWidth; //if smaller, will be set by ChartUtils.configureBarOverlap
			yField = BEVChart.VALUE_FIELD;
			xField = BEVChart.CATEGORY_FIELD;
			fillFunction = colorColumn;
			useHandCursor = true;
			buttonMode = true;
			setStyle("itemRenderer", new LabeledItemRendererFactory(seriesConfigObj.labelConfig, LabeledItemRendererFactory.BOX));
			//setStyle("fill", ChartUtils.getHorizontalGradientFill(seriesData));
		}
		
		public function get seriesConfig():XML{ return _seriesConfigXML; }
		public function get actionConfig():XML{ return _actionConfigXML; }
		public function get calculatedColWidth():int{ return _calculatedColWidth; }
		
		public function set calculatedColWidth(value:int):void{ _calculatedColWidth = value; }
		override public function set maxColumnWidth(value:Number):void{
			super.maxColumnWidth = value > 0 ? value:DEFAULT_COL_MAX_WIDTH;
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void{
			//recalculate the maximum x coordinate for labels
			var cChart:CartesianChart = chart as CartesianChart;
			if(cChart != null){
				_labelMaxX = cChart.width - cChart.computedGutters.right - 1;
			}
			super.updateDisplayList(unscaledWidth, unscaledHeight);
		}
		
		private function colorColumn(element:ChartItem, index:Number):IFill{
			var dataCol:DataColumn = null;
			try{
				var series:ColumnSeries = element.element as ColumnSeries;
				dataCol = series.dataProvider[index] as DataColumn;
			}
			catch(error:Error){
				Logger.log(DefaultLogEvent.DEBUG, "BEViewsColumnSeries.colorColumn - " + error.getStackTrace());
			}
			if(dataCol != null && dataCol.fillColors != null){
				return ChartUtils.getSolidFill({
					"baseColor":dataCol.fillColors.baseColor,
					"highlightColor":dataCol.fillColors.highlightColor	
				});
			}
			return ChartUtils.getSolidFill(_seriesConfig, _barAlpha);
		}
		
		//IBEViewsLabeledSeries
		public function createLabel(labelConfig:XML):BEVChartDataLabel{
			return LabeledItemUtil.createLabel(labelConfig); 
		}
		
		public function updateLabel(label:BEVChartDataLabel, skin:ProgrammaticSkin, data:Object):void{
			var cChart:CartesianChart = chart as CartesianChart;
			var leftPad:Number = cChart == null ? 0:cChart.computedGutters.left;
			var topPad:Number = cChart == null ? 0:cChart.computedGutters.top;
			LabeledItemUtil.updateLabel(label, data as ChartItem, 0, skin.x + leftPad + _calculatedColWidth/2 + 1, skin.y + topPad, leftPad, topPad, _labelMaxX);
			if(!LabeledItemUtil.detectAndFixLabelOverlap(chart, label)){
				//overlap existis and cannot be resolved via moving the label => remove the label
				if(chart.contains(label)){ chart.removeChild(label); }
			}
			else{
				chart.addChild(label);
			}	
		}
		//END IBEViewsLabeledSeries
		
		public function rendererShouldDrawPoint(data:Object):Boolean{
			return true; // the point in this case is the whole column
		}
		
	}
}